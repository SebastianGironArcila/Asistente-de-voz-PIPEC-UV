from __future__ import division

import re
import sys

from google.cloud import speech
from google.cloud.speech import enums
from google.cloud.speech import types


import pyaudio
from six.moves import queue


RATE = 48000 
CHUNK = int(RATE / 10) 

class MicrophoneStream(object):

    def __init__(self, rate, chunk):
        self._rate = rate
        self._chunk = chunk

        self._buff = queue.Queue()
        self.closed = True

    def __enter__(self):
        self._audio_interface = pyaudio.PyAudio()
        self._audio_stream = self._audio_interface.open(
            format=pyaudio.paInt16,
            channels=1, rate=self._rate,
            input=True, frames_per_buffer=self._chunk,
            stream_callback=self._fill_buffer,
        )

        self.closed = False

        return self

    def __exit__(self, type, value, traceback):
        self._audio_stream.stop_stream()
        self._audio_stream.close()
        self.closed = True
        self._buff.put(None)
        self._audio_interface.terminate()

    def _fill_buffer(self, in_data, frame_count, time_info, status_flags):
        
        self._buff.put(in_data)
        return None, pyaudio.paContinue

    def generator(self):
        while not self.closed:
            chunk = self._buff.get()
            if chunk is None:
                return
            data = [chunk]

            while True:
                try:
                    chunk = self._buff.get(block=False)
                    if chunk is None:
                        return
                    data.append(chunk)
                except queue.Empty:
                    break

            yield b''.join(data)

def listen_print_loop(responses):

    
    num_chars_printed = 0
    for response in responses:
        if not response.results:
            continue

        result = response.results[0]
        if not result.alternatives:
            continue

        transcript = result.alternatives[0].transcript
        

        overwrite_chars = ' ' * (num_chars_printed - len(transcript))

        if not result.is_final:

            num_chars_printed = len(transcript)

        else:

            sys.stdout.write(transcript + overwrite_chars + '\r')
            sys.stdout.flush()
                                    
            if re.search(r'\b(exit|quit)\b', transcript, re.I):
                print('Exiting..')
                break

            num_chars_printed = 0

def main():

    speech_context = speech.types.SpeechContext(phrases=['PIPEC','root','pipec',
        '1','2','3','4','5','6','7','8','9','10','o+','o-','a+','a-','b+','b-','ab+','ab-',
        'caucasian', 'indigena', 'afroamericana', 'mulato', 'mestizo, amerindia' 'nativo de alaska',
        'indioasiática', 'china', 'filipina', 'japonesa', 'coreana', 'vietnamita'])
    #metadata = speech.RecognitionMetadata()
    #metadata.interaction_type = speech.RecognitionMetadata.InteractionType.VOICE_COMMAND
    #metadata.microphone_distance = (
    #speech.RecognitionMetadata.MicrophoneDistance.NEARFIELD
    #)
    #metadata.recording_device_type = (
    #speech.RecognitionMetadata.RecordingDeviceType.PC
    #)

     

    language_code = 'es-CO'   
    
    client = speech.SpeechClient()
    config = types.RecognitionConfig(
        encoding=enums.RecognitionConfig.AudioEncoding.LINEAR16,
        sample_rate_hertz=RATE,
        language_code=language_code,
        enable_word_time_offsets=True,
        model='command_and_search',
        use_enhanced=True,
       # metadata=metadata,
        speech_contexts=[speech_context])

        
    streaming_config = types.StreamingRecognitionConfig(
        config=config,
        interim_results=True)

    with MicrophoneStream(RATE, CHUNK) as stream:
        audio_generator = stream.generator()
        requests = (types.StreamingRecognizeRequest(audio_content=content)
                    for content in audio_generator)

        responses = client.streaming_recognize(streaming_config, requests)

        listen_print_loop(responses)

if __name__ == '__main__':
    main()