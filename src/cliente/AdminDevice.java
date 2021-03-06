package cliente;

import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import vista.Pipec;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Carlos Andres
 */
public class AdminDevice {
    
    ThreadCliente tcpCliente=null;
    ThreadClienteWS tcpClienteWS=null;
    BluetoothClient bluetoothCliente=null;
    public Ecg1Signal ecg1Signal;
    public Ecg2Signal ecg2Signal;
    public Spo2Signal spo2Signal;
    public RespSignal respSignal;
    public StaticParameters staticParameters;
    CommandsToRaspberry commands;
    Monitor monitor;
    ConnectionState connectionState;
    ThreadConsumer consumidor=null;
    Thread hiloConsumidor=null;
    
    private ConcurrentLinkedQueue<Integer> queueECG1 = new ConcurrentLinkedQueue<Integer>();
    private ConcurrentLinkedQueue<Integer> queueECG2 = new ConcurrentLinkedQueue<Integer>();
    private ConcurrentLinkedQueue<Integer> queueSpo2 = new ConcurrentLinkedQueue<Integer>();
    private ConcurrentLinkedQueue<Integer> queueResp = new ConcurrentLinkedQueue<Integer>();    
    
    
    
    public AdminDevice(Pipec mc){
        ecg1Signal=new Ecg1Signal(queueECG1);
        ecg2Signal=new Ecg2Signal(queueECG2);
        spo2Signal=new Spo2Signal(queueSpo2);
        respSignal=new RespSignal(queueResp);
        staticParameters=new StaticParameters();
        commands=new CommandsToRaspberry();
        connectionState=new ConnectionState();  
    }
    
    public void ConectarBluetooth(){
        connectionState.tcpSetState(false);
        connectionState.connectByBluetooth();
        monitor=new Monitor();
        try {
            bluetoothCliente=new BluetoothClient(ecg1Signal,ecg2Signal,spo2Signal,respSignal,staticParameters,commands,monitor,connectionState,this);
            connectionState.reset();
            if(connectionState.blueReadState()){
                consumidor=new ThreadConsumer(ecg1Signal, ecg2Signal, spo2Signal, respSignal, staticParameters,monitor,connectionState);
                hiloConsumidor = new Thread(consumidor, "Consumer1");
                hiloConsumidor.start();
            }
        } catch (IOException ex) {
            //Logger.getLogger(Kiosko_client2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void ConectarTcp(){
        connectionState.blueSetState(false);
        connectionState.connectByTCP();
        monitor=new Monitor();
        try {
            boolean leerSenales= false;
            tcpCliente=new ThreadCliente(ecg1Signal,ecg2Signal,spo2Signal,respSignal,staticParameters,commands,monitor,connectionState,this, leerSenales);
            connectionState.reset();
            if(connectionState.tcpReadState()){
                //consumidor=new ThreadConsumer(ecg1Signal, ecg2Signal, spo2Signal, respSignal, staticParameters,monitor,connectionState, mc);
               // hiloConsumidor = new Thread(consumidor, "Consumer2");
                //hiloConsumidor.start();
            }
        } catch (IOException ex) {
            //Logger.getLogger(Kiosko_client2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }  
    
    public void conectarWS()
    {
        try {
            boolean leerSenales= false;
            tcpClienteWS=new ThreadClienteWS(ecg1Signal,ecg2Signal,spo2Signal,respSignal,staticParameters,this, leerSenales);
        } catch (IOException ex) {
            //Logger.getLogger(Kiosko_client2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void switchLectura()
    {
        if(tcpCliente!=null)
        {
            tcpCliente.switchLectura();      
            //tcpClienteWS.switchLectura();
        }        
    }
    
    public void dispositivoDesconectado(){
        if (hiloConsumidor != null) {
//            try {
//                consumidor.stopThread();
//                hiloConsumidor.join();
//            } catch (InterruptedException ex) {
//                Logger.getLogger(AdminDevice.class.getName()).log(Level.SEVERE, null, ex);
//            }
            consumidor=null;
            hiloConsumidor=null;
            monitor=null;
            System.out.println("Thread successfully stopped.");
        }
    }
    
    public boolean isConnectedTCP()
    {
        return connectionState.tcpReadState();
    }
    
    public boolean isTcpNull()
    {
        return tcpCliente==null;
    }
    
    //M??todo para cerrar todo despu??s de que se pierde la conexi??n con el dispositivo.
    public void desconectarDespuesDeCable()//Este m??todo sirve para desconectar el socket despu??s de un fallo f??sico
    {
        if(connectionState.tcpReadState())
        {
            if(tcpCliente!=null)
            {
              dispositivoDesconectado();
              connectionState.tcpSetState(false);            
              tcpCliente.anularStreams();           

              tcpCliente=null;
         
            }  
        }        
        if(bluetoothCliente!=null)
        {
            dispositivoDesconectado();
            connectionState.blueSetState(false);
            bluetoothCliente.closeStreams();
            bluetoothCliente=null;
        }
    }
    
    public void desconectarCliente(){
        if(tcpCliente!=null){
            
            dispositivoDesconectado();
            connectionState.tcpSetState(false);
            //ojooooooo
            tcpCliente.closeStreams(); 

                    
            tcpCliente=null;
        }
        if(bluetoothCliente!=null){
            dispositivoDesconectado();
            connectionState.blueSetState(false);
            bluetoothCliente.closeStreams();
            bluetoothCliente=null;
        }
    }
    public void enviarComando(String command, int setPoint){
        commands.setCommand(command,setPoint);
        String comando=commands.getCommand();
        if(connectionState.tcpReadState() && !connectionState.blueReadState()){
            if(!comando.equals("")){
                tcpCliente.writeSocket(comando);
                commands.setCommand("",0);
            }
        }else if(connectionState.blueReadState() && !connectionState.tcpReadState()){
            if(!comando.equals("")){
                bluetoothCliente.writeSocket(comando);
                commands.setCommand("",0);
            }
        }
    }
    public void solicitarTanita(String ID, String genero, String edad, String estatura, String tipoActividad){
        commands.setUserTanita(ID,genero,edad,estatura,tipoActividad);
        String comando=commands.getCommand();
                if(connectionState.tcpReadState() && !connectionState.blueReadState()){
            if(!comando.equals("")){
                tcpCliente.writeSocket(comando);
                commands.setCommand("",0);
            }
        }else if(connectionState.blueReadState() && !connectionState.tcpReadState()){
            if(!comando.equals("")){
                bluetoothCliente.writeSocket(comando);
                commands.setCommand("",0);
            }
        }
    }
    
    public boolean isSocketConnected()
    {
        return tcpCliente.isSocketConnected();
    }
}
