/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

import WebService.MedicoRemoto;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.StringTokenizer;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author migma
 */
public class ThreadClienteWS extends Thread {
    
    private static MedicoRemoto entrada;
    private PrintWriter salida;
    public PrintWriter salida2;
    Ecg1Signal ecg1Signal;
    Ecg2Signal ecg2Signal;
    Spo2Signal spo2Signal;
    RespSignal respSignal;
    StaticParameters staticParameter;
    //CommandsToRaspberry commands;
    //Monitor monitor;

    AdminDevice admin;
    boolean leerSenales;
    boolean banderaIniciar= false;

    public ThreadClienteWS(Ecg1Signal ecg1Signal,
            Ecg2Signal ecg2Signal, Spo2Signal spo2Signal, RespSignal respSignal, StaticParameters staticParameter,
            AdminDevice admin, boolean leerSenales) throws IOException {
        this.ecg1Signal = ecg1Signal;
        this.ecg2Signal = ecg2Signal;
        this.spo2Signal = spo2Signal;
        this.respSignal = respSignal;
        this.staticParameter = staticParameter;
        //this.commands = commands;
        //this.monitor = monitor;
        //this.connectionState = connectionState;
        this.admin = admin;
        entrada= new MedicoRemoto();
        this.leerSenales= true;
        
        
        start();
        
        
    }
    
    private static String lineaLeida= "vacio";
    
    final Runnable stuffToDo = new Thread() {
        @Override 
        public void run() { 
                String lineaTemporal= entrada.recibirDatos();
                
                if(lineaLeida.startsWith(lineaTemporal.substring(0,lineaTemporal.indexOf("$"))))
                {
                    //lineaLeida= "vacio";
                    //System.out.println("****************************");
                    //lineaLeida= lineaTemporal; //Ver si se puede quitar luego
                    
                }else
                {
                    lineaLeida= lineaTemporal;                    
                }
        }
      };
    
    public synchronized void run() {
        String linea = null;
        String[] dato;
        boolean HandShake = true;
        boolean authFlag = false;
        boolean reqFlag = false;
        String authen = "KUV!req!aut";
        String request = "KUV!req!data";
        int[] indRef = {0, 0, 0};
        String lastCommand = "";
        try {//aviable >0 y ready            

            String lineaTemporal="noise$";
            do{
               
                
                //------------------------------
                
                final ExecutorService executor = Executors.newSingleThreadExecutor();
                final Future future = executor.submit(stuffToDo);
                executor.shutdown(); // This does not cancel the already-scheduled task.
                
                future.get(2000, TimeUnit.MILLISECONDS);
                //System.out.println("Esto es lo que lee: " + lineaLeida);                
                                
                if(lineaLeida.startsWith(lineaTemporal.substring(0,lineaTemporal.indexOf("$"))))
                {
                    
                }else
                {lineaTemporal= lineaLeida;
                
                //System.out.println("Esto es lo que lee: " + lineaLeida);
                
                StringTokenizer tokenizer= new StringTokenizer(lineaLeida, "$");
                tokenizer.nextToken();
                
                while(tokenizer.hasMoreElements())
                {
                                       
                     
                    if(!lineaLeida.equals("vacio") && lineaLeida!=null)
                    {
                        //System.out.println("Entra con: " +lineaLeida);
                        linea = tokenizer.nextToken();
                        
                    

                    dato = linea.split("!");
                    //System.out.println(linea);
                    if (HandShake) {

    //System.out.println("********************Entra handshake");
                        if (dato[0].equals("KUV")) {
                            //System.out.println("********************Entra kuv, sigue: "+ dato[1] + ": " + dato[1].equals("onda"));
                            if (dato[1].equals("msg")) {
                                if (dato[2].equals("ok")) {
                                    if (dato[3].equals("type")) {
                                        if (dato[4].equals("aut")) {
                                            authFlag = true;
                                            System.out.println("Verify AUT OK");                                            
                                        }
                                    }
                                }
                            } else if (dato[1].equals("onda") && leerSenales) {
//System.out.println("********************Entra onda");
                                if (dato[2].equals("ecg1")) {
    //      
                                    ecg1Signal.getWave(dato[3]);
    //                                monitor.putData();
                                } else if (dato[2].equals("ecg2")) {
    //                    
                                    ecg2Signal.getWave(dato[3]);
    //                                monitor.putData();
                                } else if (dato[2].equals("spo2")) {
    //                                System.out.println("spo2 recibido");
    //System.out.println("********************Entra spo2");
                                    spo2Signal.getWave(dato[3]);
    //                                monitor.putData();
                                } else if (dato[2].equals("resp")) {
                                    respSignal.getWave(dato[3]);
    //                                monitor.putData();
                                }
                            } else if (dato[1].equals("estaticos")) {
                                // writeSocket(this,request);
                                if (dato[2].equals("hr")) {
                                    staticParameter.getHr(dato[3]);
                                }
                                if (dato[4].equals("rr")) {
                                    staticParameter.getResp(dato[5]);
                                }
                                if (dato[6].equals("spo2oxi")) {
                                    staticParameter.getSpo2Oxi(dato[7]);
                                }
                                if (dato[8].equals("spo2hr")) {
                                    staticParameter.getSpo2Hr(dato[9]);
                                }
                                if (dato[10].equals("pr")) {
                                    staticParameter.getPresR(dato[11]);
                                }
                                if (dato[12].equals("pd")) {
                                    staticParameter.getPresDias(dato[13]);
                                }
                                if (dato[14].equals("pm")) {
                                    staticParameter.getPresMed(dato[15]);
                                }
                                if (dato[16].equals("ps")) {
                                    staticParameter.getPresSist(dato[17]);
                                }
                                if (dato[18].equals("temp")) {
                                    staticParameter.getTemp(dato[19]);
                                    //System.out.println("ESTATICOS RECIBIDO");
                                }
                            } else if (dato[1].equals("tanita")) { //"KUV!tanita!peso!%s!fat!%s!agua!%s!musculo!%s"
                                System.out.println(linea);
                                if (dato[2].equals("peso")) {
                                    staticParameter.getWeight(dato[3]);
                                }
                                if (dato[4].equals("fat")) {
                                    staticParameter.getBodyFat(dato[5]);
                                }
                                if (dato[6].equals("agua")) {
                                    staticParameter.getWaterPercent(dato[7]);
                                }
                                if (dato[8].equals("musculo")) {
                                    staticParameter.getMuscleMass(dato[9]);
                                }
                            } else if (dato[1].equals("msg")) {
                                if (dato[2].equals("ok")) {
                                    if (dato[3].equals("type")) {
                                        if (dato[4].equals("logout")) {
                                            authFlag = false;                                            
                                            break;
                                        }
                                    }
                                }
                            }

                        }
                    }
                    
                    }
                }
               
                }
                
            }while(linea!="vacio");
            //}while(true);
            /*if(lector.isAlive())
            {
                lector.interrupt();
                lector= null;
            }*/
        } catch (java.lang.NullPointerException e1) {
            System.out.println("Llega acá Null");
            e1.printStackTrace();
        } catch (InterruptedException ex) {
            Logger.getLogger(ThreadCliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExecutionException ex) {
            Logger.getLogger(ThreadCliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TimeoutException ex) {
            //admin.mc.mensajeDesconexion();
            System.out.println("Excepción de perdida, controlada");            
        }
            
            //admin.mc.mensajeDesconexion();
            
            //connectionState.tcpSetState(false);
            //closeStreams();            
            admin.desconectarDespuesDeCable();
            //admin.dispositivoDesconectado();
            //System.exit(-1);
        
    }
    
    /*public void switchLectura()
    {
        leerSenales= !leerSenales;        
    }*/
    
    
    
    
}
