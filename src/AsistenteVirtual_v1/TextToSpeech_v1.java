/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AsistenteVirtual_v1;

import com.ibm.cloud.sdk.core.security.IamAuthenticator;
import com.ibm.watson.text_to_speech.v1.model.SynthesizeOptions;
import com.ibm.watson.text_to_speech.v1.util.WaveUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.bridj.cpp.com.shell.ITaskbarList;

/**
 *
 * @author Usuario
 */
public class TextToSpeech_v1 {
    
    private Timer timer;
    private SpeechRecognition sr;
 
    
    public TextToSpeech_v1(String frase){
        
        timer = new Timer();
        sr = new SpeechRecognition();
        TimerTask task = new TimerTask() {
            
            @Override
            public void run() {
                
                IamAuthenticator authenticator = new IamAuthenticator("PWkQ6g-8G96olgh_l7ij5FED4q2Czo981skb4hdPJVLo");
                com.ibm.watson.text_to_speech.v1.TextToSpeech textToSpeech = new com.ibm.watson.text_to_speech.v1.TextToSpeech(authenticator);
                textToSpeech.setServiceUrl("https://api.us-south.text-to-speech.watson.cloud.ibm.com/instances/351155b3-c1b3-4a0f-a1b5-93bcabdf0ab2");



                try {
                    SynthesizeOptions synthesizeOptions
                            = new SynthesizeOptions.Builder()
                                    .text(frase)
                                    .accept("audio/wav")
                                    .voice("es-LA_SofiaVoice")   // en-US_LisaV2Voice   es-ES_EnriqueVoice  es-LA_SofiaVoice
                                    .build(); 

                    InputStream inputStream
                            = textToSpeech.synthesize(synthesizeOptions).execute().getResult();
                    InputStream in = WaveUtils.reWriteWaveHeader(inputStream);

                    OutputStream out = new FileOutputStream("src/ReconocimientoVoz/frase_PIPEC.wav");
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = in.read(buffer)) > 0) {
                        out.write(buffer, 0, length);
                    }

                    out.close();
                    in.close();
                    inputStream.close();

                } catch (IOException e) {
                    e.printStackTrace();

                }
                
                CountDownLatch syncLatch =  new CountDownLatch(1);
                try {
                    

                    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("src/ReconocimientoVoz/frase_PIPEC.wav").getAbsoluteFile());
                    Clip clip = AudioSystem.getClip();
                    clip.addLineListener(e -> {
                        if(e.getType() == LineEvent.Type.STOP){
                            syncLatch.countDown();
                    }
                    });
                    clip.open(audioInputStream);
                    clip.start();
                 
                    
                } 
                   
                catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                    System.out.println("Error al reproducir el sonido. " + ex);
                }
                
                try {
                    syncLatch.await();
                    sr.setPausaActiva(false);
                    timer.cancel();
                    timer.purge();
                 
                    
                } catch (InterruptedException ex) {
                    Logger.getLogger(TextToSpeech_v1.class.getName()).log(Level.SEVERE, null, ex);
                }


                    }
                }; 
         timer.schedule(task, 0);
         
         
         
                

    }
    
    public void setSTT(SpeechRecognition sr){
        this.sr = sr;
    }
    
    public SpeechRecognition getSTTT(){
           return sr;
    }
            
}
