/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AsistenteVirtual_v1;


import AsistenteVirtual_v1.SpeechRecognition;
import BD.EntrenamientoAsistente;
import BD.Usuarios;
import com.ibm.cloud.sdk.core.security.IamAuthenticator;
import com.ibm.watson.assistant.v1.Assistant;
import com.ibm.watson.assistant.v1.model.Log;
import com.ibm.watson.assistant.v1.model.MessageInput;
import com.ibm.watson.assistant.v1.model.MessageOptions;
import com.ibm.watson.assistant.v1.model.MessageResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.channels.Pipe;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.application.Platform;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.swing.JOptionPane;

import vista.AfinamientosController;
import vista.EntrenamientoAsistenteController;
import vista.EstructuraBasicaController;
import vista.LoginController;
import vista.MenuPrincipalController;
import vista.OndasController;
import vista.Pipec;


/**
 *
 * @author Usuario
 */
public class SpeechToText_v1{
    
    private String ret, command;
    private SpeechRecognition sr;
    private IamAuthenticator authenticator;
    private Assistant assistant;
    private MessageInput input;  
    private Pipec pipec;
    private Timer timer;
    private int llave;
    private TextToSpeech_v1 tts;
    private LoginController lg;
    private MenuPrincipalController mpc;
    private AfinamientosController afic;
    private OndasController ondasctr;
    private EstructuraBasicaController paciente;
    private EntrenamientoAsistenteController entrenamiento;
    private EstructuraBasicaController datosPaciente;
    private Usuarios usuario;
    private BufferedReader in;
    private boolean bandera;
    
   
    
    private Dictionary<List<String>, Integer> dictActivacion;
    private Dictionary<List<String>, Integer> dictDatosPersonales;
    private Dictionary<List<String>, Integer> dictTipoID;
    private Dictionary<List<String>, Integer> dictTipoDiabetes;
    private Dictionary<List<String>, Integer> dictGrupoSanguineo;
    private Dictionary<List<String>, Integer> dictZona_Rasa;
    
    
    public SpeechToText_v1(){
    	
    	dictDatosPersonales = new Hashtable<List<String>, Integer>();
    	dictTipoID = new Hashtable<List<String>, Integer>();
    	dictTipoDiabetes = new Hashtable<List<String>, Integer>();
    	dictGrupoSanguineo = new Hashtable<List<String>, Integer>();
    	dictZona_Rasa =  new Hashtable<List<String>, Integer>();
    	
        dictActivacion = new Hashtable<List<String>, Integer>();
        EntityManager em = Persistence.createEntityManagerFactory("PipecPU").createEntityManager();        
        Query queryMedicionFindAll = em.createNativeQuery("SELECT * from entrenamiento_asistente e", EntrenamientoAsistente.class);
        List<EntrenamientoAsistente> listas = queryMedicionFindAll.getResultList();
        for(int i=0; i<listas.size();i++){
           if(listas.get(i).getFrase()!=null){
               String[] parts = listas.get(i).getFrase().split(",");
               for(int j=0; j<parts.length;j++){
                   List<String> ListWords = new ArrayList<String>();
                   ListWords=Datapreparation(CleanData(parts[j]));
                   dictActivacion.put(ListWords,listas.get(i).getId());
                   
               }         
             
           }
        }

        sr = new SpeechRecognition();
        pipec = new Pipec();
        lg = new LoginController();
        mpc = new MenuPrincipalController();
        afic = new AfinamientosController();
        ondasctr = new OndasController();
        paciente = new EstructuraBasicaController();
        entrenamiento = new EntrenamientoAsistenteController();
        datosPaciente = new EstructuraBasicaController();
        ret = new String(" ");
        bandera=false;
        command = new String(" ");
        
        timer = new Timer();
        TimerTask task = new TimerTask() {
                        
            @Override
            public void run() {
                command = "python src/ReconocimientoVoz/SpeechRecognition.py";
                Process p =null;
                try {
                    p = Runtime.getRuntime().exec(command);
                } catch (IOException ex) {
                    Logger.getLogger(SpeechToText_v1.class.getName()).log(Level.SEVERE, null, ex);
                }
                 in = new BufferedReader(new InputStreamReader(p.getInputStream()));
                while(true){
                    
                     try {
                    	 
                         ret = in.readLine();
                         
                     } catch (IOException ex) {
                         Logger.getLogger(SpeechToText_v1.class.getName()).log(Level.SEVERE, null, ex);
                     }
                    
                     System.out.println("respuesta " + ret);
                     if(ret==null)break;
                     
                     if(ret!=null){
                        
                        llave = Diccionario(Datapreparation(CleanData(ret)));
                        
                        if(llave==0 && sr.getEstado()==true) {
                        	if(sr.getbanderaEntrenar()>1) {
                        	      
                       		 tts = new TextToSpeech_v1("¿Deseas entrenar una frase?");
                       		 sr.setbanderaEntrenar(0);
                       		 ret=" ";
                       		 do {
                       			 
                       			 try {
                       				 
                       				 ret = in.readLine();
                            		
                            	}catch (IOException ex) {
                                    Logger.getLogger(SpeechToText_v1.class.getName()).log(Level.SEVERE, null, ex);
                                }
                       			 
                       			 if(ret!=" ") {
                       				 if(Datapreparation(CleanData(ret)).get(0).equalsIgnoreCase("si")) {
                       					 ret=" ";
                       					 llave=9;
                       					 break;
                       					
                       				 }else{
                       					ret=" ";
                       					tts = new TextToSpeech_v1("OK, estaré pendiente");
                       					break;
                       				 }
                       			 }
                       			 
                       			 
                            	
                       			 
                       		 }while(true);
                       		 
                       		 
                       	}else {
                       		tts = new TextToSpeech_v1("instrucción no reconocida?");
                        	sr.setbanderaEntrenar(sr.getbanderaEntrenar()+1);
                        
                       	}
                        		
                        }
                        
                        if(llave==3) {
                        	sr.setEstado(false);
                        	tts = new TextToSpeech_v1("Asistente desactivado");                        	
                        }
                        
                        if(llave==1){
                        	
                            sr.setEstado(true);
                            ///sr.setPausaActiva(true);
                            tts = new TextToSpeech_v1("Hola, ¿cómo puedo ayudarte?");
                            /*tts.setSTT(sr);
                            
                            while(tts.getSTTT().getPausaActiva()) {
                            	System.out.println("durante" + tts.getSTTT().getPausaActiva());
                            	                   	
                            }
                            
                            
                            System.out.println("despues" + tts.getSTTT().getPausaActiva());
                             */                     
                            /*p.destroyForcibly();
                            
                            try {
								p = Runtime.getRuntime().exec(command);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} */                           
                            
                         
                            /*try {
                            	in.mark(0);
                            	in.readLine();
                            	in.reset();
                            	
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}*/
                           
                            
                            
                        
                          
                        }                     
                        if(llave==2 && sr.getEstado()==true){
             
                            do{


                            	
                            	ret=" ";
                            	tts = new TextToSpeech_v1("por favor, dime el nombre del usuario");


                                
                                try {
                                	
                                	
                                    ret = in.readLine();
                                    
                                    
                                    
                                } catch (IOException ex) {
                                    Logger.getLogger(SpeechToText_v1.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                if(ret!=" "){
                                    
                                    
                                    if((Datapreparation(CleanData(ret))).size()==1){
                                        String username = (Datapreparation(CleanData(ret)).get(0));
                                        Platform.runLater(new Runnable(){            
                                            @Override
                                            public void run() {                    
                                                                                      
                                                lg.setUsername(username);
                                            }

                                        });
                                        break;
                                    }
                                    if(Datapreparation(CleanData(ret)).get(0).equalsIgnoreCase("usuario")){
                                        
                                        String username = (Datapreparation(CleanData(ret)).get(1));
                                        //System.out.println("El usuario es "+ username);
                                        Platform.runLater(new Runnable(){            
                                            @Override
                                            public void run() {                    
                                                                                      
                                                lg.setUsername(username);
                                            }

                                        });
                                        break;  
                                    }else{
                                        tts = new TextToSpeech_v1("Error, intenta de nuevo");                        
                                        continue;
                                    }
                                   
                                }
                            
                            }while(true);
                            
                            ret=" ";
                            tts = new TextToSpeech_v1("Ahora, dime la contraseña");
                            do{
                           
                                try {
                                    ret = in.readLine();
                                } catch (IOException ex) {
                                    Logger.getLogger(SpeechToText_v1.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                if(ret!=" "){
                                    //System.out.println(ret);
                                     if((Datapreparation(CleanData(ret))).size()==1){
                                         String password = (Datapreparation(CleanData(ret)).get(0));
                                        Platform.runLater(new Runnable(){            
                                            @Override
                                            public void run() {                    
                                                                                      
                                                lg.setPassword(password);
                                            }

                                        });
                                        break;
                                    }
                                        //String password = (Datapreparation(CleanData(ret)).get(1));
                                        if(Datapreparation(CleanData(ret)).get(0).equalsIgnoreCase("contrasena")){
                                            String password = (Datapreparation(CleanData(ret)).get(1));
                                            Platform.runLater(new Runnable(){            
                                                    @Override
                                                    public void run() {                    
                                                        
                                                        lg.setPassword(password);
                                                        
                                                    }

                                                });
                                            break;
                                        }
                                    }
                            
                                }while(true);
                                                            
                                 Platform.runLater(new Runnable() {
                                     @Override
                                     public void run() {
                                         lg.autenticarUsuario();
                                     }
                                 });
                           

                            }
      
                        if(llave==4 && sr.getEstado()==true){ 
                            Platform.runLater(new Runnable(){
                                @Override
                                public void run(){  
                                    
                                    pipec.irMenuPrincipal(usuario);
                                }

                            });
                        }
                        
                        if(llave==5 && sr.getEstado()==true){
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    pipec.cargarAfinamientos();
                                }
                            });
                            
                            tts = new TextToSpeech_v1("¿Deseas cargar un paciente?");
                            ret = " ";
                            
                            do{
                                try {
                                    ret = in.readLine();
                                    
                                }catch (IOException ex) {
                                    Logger.getLogger(SpeechToText_v1.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                
                                if(ret!=" "){
                                    String desicion = CleanData(ret);
                                    if(Datapreparation(CleanData(ret)).get(0).equalsIgnoreCase("si") || Diccionario(Datapreparation(CleanData(ret)))==8){
                                        Platform.runLater(new Runnable() {
                                            @Override
                                            public void run() {
                                                pipec.cargarBusquedaPaciente();
                                            }
                                        });                                        
                                                                        
                                        
                                        ret=" ";
                                        tts = new TextToSpeech_v1("¿Deseas almacenar un afinamiento?");
                                        do{
                                            
                                            try {
                                                                                             
                                                ret = in.readLine();
                                    
                                                }catch (IOException ex) {
                                                    Logger.getLogger(SpeechToText_v1.class.getName()).log(Level.SEVERE, null, ex);
                                                }
                                            
                                            if(ret!=" "){
                                                desicion = CleanData(ret);
                                                if(Datapreparation(CleanData(ret)).get(0).equalsIgnoreCase("si")){
                                                    ret=" ";
                                                    tts = new TextToSpeech_v1("¿Qué brazo deseas seleccionar?, puede ser izquierdo o derecho");
                                                    do{
                                                        
                                                        try {
                                                                                                                                                        
                                                            ret = in.readLine();

                                                            }catch (IOException ex) {
                                                                Logger.getLogger(SpeechToText_v1.class.getName()).log(Level.SEVERE, null, ex);
                                                         }
                                                        
                                                        if(ret!=" "){
                                                        	String brazo = Datapreparation(CleanData(ret)).get(0);
                                                        	if(brazo.equalsIgnoreCase("izquierdo") || 
                                                        			brazo.equalsIgnoreCase("derecho")) {
                                                        		
                                                        		Platform.runLater(new Runnable() {
                                                                    @Override
                                                                    public void run() {
                                                                        afic.setComboBoxBrazo(brazo);
                                                                    }
                                                                });
                                                        			
                                                        		
                                                        	}else {
                                									ret=" ";
                                									tts = new TextToSpeech_v1("Error, intenta de nuevo");
                                									continue;
                                                        	}
                                                          
                                                            break;
                                                        }                                                                                                                                                               
                                                                                                           
                                                                                                         
                                                    }while(true);
                                                    
                                                    ret = " ";
                                                    tts = new TextToSpeech_v1("¿Qué posición deseas seleccionar?, puede ser sentado, de pie o acostado");
                                                    do{
                                                        try{
                                                            ret = in.readLine();
                                                            
                                                        }catch(IOException ex){
                                                            Logger.getLogger(SpeechToText_v1.class.getName()).log(Level.SEVERE, null, ex);                                                            
                                                        }
                                                        
                                                        if(ret!=" "){
                                                        	String pos = Datapreparation(CleanData(ret)).get(0);
                                                            if(pos.equalsIgnoreCase("sentado") || pos.equalsIgnoreCase("sentada") || 
                                                            		pos.equalsIgnoreCase("acostado") || pos.equalsIgnoreCase("acostada")||
                                                            		pos.equalsIgnoreCase("pie") || pos.equalsIgnoreCase("parado") || pos.equalsIgnoreCase("parada")){
                                                                Platform.runLater(new Runnable() {
                                                                    @Override
                                                                    public void run() {
                                                                        afic.setComboBoxPosicion(pos);
                                                                    }
                                                                });
                                                                
                                                            }else {
                                                            	ret=" ";
                            									tts = new TextToSpeech_v1("Error, intenta de nuevo");
                            									continue;
                                                            }
                                                            break;
                                                        }                                                        
                                                        
                                                    }while(true);
                                                    
                                                    ret=" ";
                                                    tts = new TextToSpeech_v1("¿Qué jornada deseas seleccionar? puede ser en la mañana, tarde o noche");
                                                    do{
                                                        try{
                                                            ret = in.readLine();
                                                        }catch(IOException ex){
                                                            Logger.getLogger(SpeechToText_v1.class.getName()).log(Level.SEVERE, null, ex);                                                           
                                                        }
                                                        
                                                        if(ret!=" "){
                                                        	String jornada = Datapreparation(CleanData(ret)).get(0);
                                                            if(jornada.equalsIgnoreCase("manana") || jornada.equalsIgnoreCase("tarde") 
                                                            		|| Datapreparation(CleanData(ret)).get(0).equalsIgnoreCase("noche")){
                                                                Platform.runLater(new Runnable() {
                                                                    @Override
                                                                    public void run() {
                                                                        afic.setComboBoxJornada(jornada);
                                                                    }
                                                                });
                                                            }else {
                                                            	ret=" ";
                            									tts = new TextToSpeech_v1("Error, intenta de nuevo");
                            									continue;
                                                            }
                                                            break;
                                                        }
                                                        
                                                    }while(true);
                                                    
                                                    
                                                    
                                                    ret=" ";
                                                    tts = new TextToSpeech_v1("¿Qué estado inicial deseas? puede ser descansando o activo");
                                                    do{
                                                        
                                                        try{
                                                            ret = in.readLine();
                                                            
                                                        }catch(IOException ex){
                                                            Logger.getLogger(SpeechToText_v1.class.getName()).log(Level.SEVERE, null, ex);                                                            
                                                        }
                                                        if(ret!=" "){
                                                        	String estado = Datapreparation(CleanData(ret)).get(0);
                                                            if(estado.equalsIgnoreCase("descansando") || estado.equalsIgnoreCase("activo")){
                                                                Platform.runLater(new Runnable() {
                                                                    @Override
                                                                    public void run() {
                                                                        afic.setComboBoxEstado(estado);
                                                                    }
                                                                });
                                                            }else {
                                                            	ret=" ";
                            									tts = new TextToSpeech_v1("Error, intenta de nuevo");
                            									continue;
                                                            }
                                                            break;
                                                                                                                       
                                                        }                                                       
                                                        
                                                    }while(true);
                                                    
                                                    ret=" ";
                                                    tts = new TextToSpeech_v1("Ahora, dime los detalles");
                                                    do{
                                                        try{
                                                            ret = in.readLine();
                                                            
                                                        }catch(IOException ex){
                                                            Logger.getLogger(SpeechToText_v1.class.getName()).log(Level.SEVERE, null, ex);                                                            
                                                        }
                                                        
                                                        if(ret!=" "){
                                                            Platform.runLater(new Runnable() {
                                                                @Override
                                                                public void run() {
                                                                    afic.setDetalles(ret);
                                                                }
                                                            });
                                                            break;
                                                        }
                                                                                                                
                                                    }while(true);
                                                     
                                                    Platform.runLater(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            
                                                            //afic.GuardarAfinamiento();
                                                        }
                                                    });
                                                    JOptionPane.showMessageDialog(null,"Registrando el afinamiento");
                                                    break;
                                                    
                                                                                                    
                                                    
                                                
                                                }else{
                                                    break;
                                                }
                                           
                                            }
     
                                        }while(true);
                                        break;
                                       
                                        
                         
                                    }else{
                                        break;
                                    }
                                    
                                    
                             
                                }
                   
                            }while(true);
                            
                        }
                        
                        if(llave==6 && sr.getEstado()==true){
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    
                                    pipec.cargarOndas();
                                    
                                }
                            });
                            tts = new TextToSpeech_v1("¿Deseas registrar una medición?");
                            ret=" ";
                            
                            do {
                            	try {
                            		
                            		ret = in.readLine();
                            		
                            	}catch (IOException ex) {
                                    Logger.getLogger(SpeechToText_v1.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            	
                            	if(ret!=" ") {
                            		
                            		if(Datapreparation(CleanData(ret)).get(0).equalsIgnoreCase("si")) {
                            			
                            			ret=" ";
                            			tts = new TextToSpeech_v1("¿Deseas realizar una medición simple o personalizada?");
                            			do {
                            				
                            				try {
                            					
                            					ret = in.readLine();
                            					
                            				}catch (IOException ex) {
                                                Logger.getLogger(SpeechToText_v1.class.getName()).log(Level.SEVERE, null, ex);
                                            }
                            				
                            				if(ret!=" ") {
                            					
                            					if(Datapreparation(CleanData(ret)).get(0).equalsIgnoreCase("simple")) {
                            						ret=" ";
                            						tts = new TextToSpeech_v1("Muy bien, dime los detalles");
                            						
                            						do {
                            							try {
                            								ret = in .readLine(); 
                            							}catch(IOException ex){
                                                            Logger.getLogger(SpeechToText_v1.class.getName()).log(Level.SEVERE, null, ex);
                                                        }
                            							
                            							if(ret!=" ") {
                            								
                            								Platform.runLater(new Runnable() {
        														
        														@Override
        														public void run() {
        															ondasctr.setDetalles(ret);
        															
        														}
        													});
                            								JOptionPane.showMessageDialog(null,"Registrando medicion simple");
                            								break;
                            								/*Platform.runLater(new  Runnable() {
																public void run() {
																	System.out.println("haciedo medicion simple");
																	ondasctr.medicionSimple();
																}
															});
                            								break;*/
                            								
                            							}
                            							
                            						}while(true);
                            						
                            						break;
                            		
                            					}else if(Datapreparation(CleanData(ret)).get(0).equalsIgnoreCase("personalizada")) {
                            						ret = " ";
                            						tts = new TextToSpeech_v1("¿Qué intervalo entre muestras deseas? puede ser 5, 10, 15, 20, 25 o 30 minutos");
                            						do {
                            							
                            							try {
                            								ret = in.readLine();
                            							}catch(IOException ex){
                                                            Logger.getLogger(SpeechToText_v1.class.getName()).log(Level.SEVERE, null, ex);                                                            
                                                        }
                            							if(ret!=" ") {
                            								String intervalo = Datapreparation(extraerNumeros(ret)).get(0);
                            								if(intervalo.equalsIgnoreCase("5") || intervalo.equalsIgnoreCase("10") 
                            										|| intervalo.equalsIgnoreCase("15") || intervalo.equalsIgnoreCase("20")
                            										|| intervalo.equalsIgnoreCase("25") || intervalo.equalsIgnoreCase("30")) {
                            									
                            									Platform.runLater(new Runnable() {
																	@Override
																	public void run() {
																		ondasctr.setIntervalo(intervalo);
																		
																	}
																});
                            									break;
                            									
                            								}else {
                            									ret=" ";
                            									tts = new TextToSpeech_v1("Error, intenta de nuevo");
                            									continue;
                            								}
                            				
                            							}
                            							                          							
                            							
                            						}while(true);
                            						
                            						ret=" ";
                            						tts = new TextToSpeech_v1("¿Qué duración de la muestra deseas? puede ser 15, 30, 45, o 60 segundos");
                            						do {
                            							
                            							try {
                            								
                            								ret=in.readLine();
                            								
                            							}catch(IOException ex){
                            								Logger.getLogger(SpeechToText_v1.class.getName()).log(Level.SEVERE, null, ex);
                            							}
                            							
                            							if(ret!=" ") {
                            								String duracion = Datapreparation(extraerNumeros(ret)).get(0);
                            								if(duracion.equalsIgnoreCase("15") || duracion.equalsIgnoreCase("30") 
                            										|| duracion.equalsIgnoreCase("45") || duracion.equalsIgnoreCase("60")
                            										){
                            									
                            									Platform.runLater(new Runnable() {
																	@Override
																	public void run() {
																		ondasctr.setDuracionMuestra(duracion);
																		
																	}
																});
                            									break;
                            									
                            								}else {
                            									ret=" ";
                            									tts = new TextToSpeech_v1("Error, intenta de nuevo");
                            									continue;
                            								}
                            								
                            					
                            							
                            							}
                           
                            						}while(true);
                            						
                            						
                            						ret=" ";
                            						tts = new TextToSpeech_v1("¿Qué duración de la medición deseas? puede ser 10, 20 o 30 minutos, 1 hora , 1 hora con 30 minutos o 2 horas");
                            						do {
                            							
                            							try {
                            								
                            								ret=in.readLine();
                            								
                            							}catch(IOException ex){
                            								Logger.getLogger(SpeechToText_v1.class.getName()).log(Level.SEVERE, null, ex);
                            							}
                            							
                            							if(ret!=" ") {
                            								String duracion = Datapreparation(extraerNumeros(ret)).get(0);
                            								if(duracion.equalsIgnoreCase("10") || duracion.equalsIgnoreCase("20") 
                            										|| duracion.equalsIgnoreCase("30") || duracion.equalsIgnoreCase("1")
                            										|| duracion.equalsIgnoreCase("130") || duracion.equalsIgnoreCase("2")){
                            									
                            									Platform.runLater(new Runnable() {
																	@Override
																	public void run() {
																		ondasctr.setDuracionExamen(duracion);
																		
																	}
																});
                            									break;
                            								}else {
                            									ret=" ";
                            									tts = new TextToSpeech_v1("Error, intenta de nuevo");
                            									continue;
                            								}
                            								
                            							
                            							}
                           
                            						}while(true);
                            						
                            						
                            						ret=" ";
                            						tts = new TextToSpeech_v1("¿Qué intervalo de submuestreo deseas? puede ser 10, 20, 30, 40, 50 o 60 segundos");
                            						do {
                            							
                            							try {
                            								
                            								ret=in.readLine();
                            								
                            							}catch(IOException ex){
                            								Logger.getLogger(SpeechToText_v1.class.getName()).log(Level.SEVERE, null, ex);
                            							}
                            							
                            							if(ret!=" ") {
                            								String duracion = Datapreparation(extraerNumeros(ret)).get(0);
                            								if(duracion.equalsIgnoreCase("10") || duracion.equalsIgnoreCase("20") 
                            										|| duracion.equalsIgnoreCase("30") || duracion.equalsIgnoreCase("40")
                            										|| duracion.equalsIgnoreCase("50") || duracion.equalsIgnoreCase("60")){
                            									
                            									Platform.runLater(new Runnable() {
																	@Override
																	public void run() {
																		ondasctr.setSubmuestreo(duracion); 
																		
																	}
																});
                            									break;
                            								}else {
                            									ret=" ";
                            									tts = new TextToSpeech_v1("Error, intenta de nuevo");
                            									continue;
                            								}
                            								
                            							
                            							}
                           
                            						}while(true);
                            						
                            						ret=" ";
                            						tts = new TextToSpeech_v1("Ahora, dime los detalles");
                            						do {
                            							try {
                            								ret = in .readLine(); 
                            							}catch(IOException ex){
                                                            Logger.getLogger(SpeechToText_v1.class.getName()).log(Level.SEVERE, null, ex);
                                                        }
                            							
                            							if(ret!=" ") {
                            								
                            								Platform.runLater(new Runnable() {
        														
        														@Override
        														public void run() {
        															ondasctr.setDetalles(ret);
        															
        														}
        													});
                            								//JOptionPane.showMessageDialog(null,"Registrando medicion personalizada");
                            								//break;
                            								Platform.runLater(new  Runnable() {
																public void run() {
																	System.out.println("haciedo medicion personalizada");
																	ondasctr.medicionPersonalizada();
																}
															});
                            								break;
                            								
                            							}
                            							
                            						}while(true);
                            						break;
                          
                            					}
                            				}
                            				
                            			}while(true);
                            			break;
                            			
                            			                          			
                            		}else {
                            			break;
                            		}
                            		
                            	}
                            	
                            }while(true);
                            
                        }
                         if(llave==7 && sr.getEstado()==true){
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    
                                    pipec.cargarEstructuraBasica();
                                    
                                }
                            });
                            
                                                
                       	 tts = new TextToSpeech_v1("¿Deseas que te guíe en la configuración de algún campo?");
                         ret= " ";
                            do {
                            	
                            	
                            	
                            	try {
                            		ret = in.readLine();
                            	}catch(IOException ex) {
                            		Logger.getLogger(SpeechToText_v1.class.getName()).log(Level.SEVERE, null, ex);
                            	}
                            	
                            	
                            	
                            	if(ret!=" ") {
                            		if(Datapreparation(CleanData(ret)).get(0).equalsIgnoreCase("si")) {
                            			
                            			ret=" ";
                            			tts = new TextToSpeech_v1("Dime el nombre del campo o parámetro que deseas configurar");
                            			bandera=false;
                            			do {
                            					
                            				try {
                            					ret = in.readLine();
                            				}catch(IOException ex) {
                            					Logger.getLogger(SpeechToText_v1.class.getName()).log(Level.SEVERE, null, ex);
                            				}
                            				
                            				if(ret!=" ") {
                            					System.out.println(ret);
                            					int llaveDato = Diccionario2(Datapreparation(CleanData(ret)));
                            					
                            					if(llaveDato==1) {
                            						ret=" ";
                            						tts = new TextToSpeech_v1("Ahora dime el tipo de identificación. Esta puede ser cédula de ciudadanía, cedula extranjeria, pasaporte, registro civil, tarjeta de identidad, adulto sin identificación ó menor sin identificación ");
                            						do {
                            							
                            							try {
                            								ret = in.readLine();
                            								
                            							}catch(IOException ex) {
                            								Logger.getLogger(SpeechToText_v1.class.getName()).log(Level.SEVERE, null, ex);
                            							}
                            							
                            							if(ret!=" ") {
                            								int llaveTipoID = Diccionario3(Datapreparation(CleanData(ret)));
                            								if(llaveTipoID>=1 && llaveTipoID<=7)  {
                            									Platform.runLater(new Runnable() {
																	
																	@Override
																	public void run() {
																		datosPaciente.setTipoID(llaveTipoID);
																		
																	}
																});
                            									break;
                            								}else {
                            									ret = " ";
                            									tts = new TextToSpeech_v1("Error, intenta de nuevo");
                            									continue;
                            								}
                            								
                            							}
                            							
                            						}while(true);
                            						break;   
                            					
                            					
                            					}else if(llaveDato==2) {
                            						ret=" ";
                            						tts = new TextToSpeech_v1("Ahora, dime el valor del ID");
                            						
                            						do {
                            							try {
                            								ret = in.readLine();
                            								
                            							}catch(IOException ex) {
                            								Logger.getLogger(SpeechToText_v1.class.getName()).log(Level.SEVERE, null, ex);
                            							}
                            							
                            							if(ret!=" "){
                                							String id;
                                							id =  Datapreparation(CleanData(ret)).get(0);
                                							Platform.runLater(new Runnable() {
																
																@Override
																public void run() {
																	datosPaciente.setId(id);
																
																}
															});
                                							break;
                                							
                                						}
                            							
                                						
                            								
                            						}while(true);
                            						break;   
                            						
                            						
                            						
                            					}else if(llaveDato==3) {
                            						ret=" ";
                            						tts = new TextToSpeech_v1("Ahora, dime el primer nombre");
                            						
                            						do {
                            							try {
                            								ret = in.readLine();
                            								
                            							}catch(IOException ex) {
                            								Logger.getLogger(SpeechToText_v1.class.getName()).log(Level.SEVERE, null, ex);
                            							}
                            							
                            							if(ret!=" "){
                                							String primerNombre;
                                							primerNombre =  Datapreparation(CleanData(ret)).get(0);
                                							Platform.runLater(new Runnable() {
																
																@Override
																public void run() {
																	datosPaciente.setPrimerNombre(primerNombre);
																
																}
															});
                                							break;
                                						}
                                						
                            								
                            						}while(true);
                            						break;   
                            						
                            					}else if(llaveDato==4) {
                            						ret=" ";
                            						tts = new TextToSpeech_v1("Ahora, dime el segundo nombre");
                            						
                            						do {
                            							try {
                            								ret = in.readLine();
                            								
                            							}catch(IOException ex) {
                            								Logger.getLogger(SpeechToText_v1.class.getName()).log(Level.SEVERE, null, ex);
                            							}
                            							
                            							if(ret!=" "){
                                							String segundoNombre;
                                							segundoNombre =  Datapreparation(CleanData(ret)).get(0);
                                							Platform.runLater(new Runnable() {
																
																@Override
																public void run() {
																	datosPaciente.setSegundoNombre(segundoNombre);
																
																}
															});
                                							break;
                                						}
                                						
                            								
                            						}while(true);
                            						break;   
                            						
                            					}else if(llaveDato==5) {
                            						ret=" ";
                            						tts = new TextToSpeech_v1("Ahora, dime la administradora");
                            						
                            						do {
                            							try {
                            								ret = in.readLine();
                            								
                            							}catch(IOException ex) {
                            								Logger.getLogger(SpeechToText_v1.class.getName()).log(Level.SEVERE, null, ex);
                            							}
                            							
                            							if(ret!=" "){
                                							String dato;
                                							dato =  Datapreparation(CleanData(ret)).get(0);
                                							Platform.runLater(new Runnable() {
																
																@Override
																public void run() {
																	datosPaciente.setAdministradora(dato);
																
																}
															});
                                							break;
                                						}
                                						
                            								
                            						}while(true);
                            						break;   
                            					
                            					}else if(llaveDato==6) {
                            						ret=" ";
                            						tts = new TextToSpeech_v1("Ahora, dime el primer apellido");
                            						
                            						do {
                            							try {
                            								ret = in.readLine();
                            								
                            							}catch(IOException ex) {
                            								Logger.getLogger(SpeechToText_v1.class.getName()).log(Level.SEVERE, null, ex);
                            							}
                            							
                            							if(ret!=" "){
                                							String dato;
                                							dato =  Datapreparation(CleanData(ret)).get(0);
                                							Platform.runLater(new Runnable() {
																
																@Override
																public void run() {
																	datosPaciente.setPrimerApellido(dato);
																
																}
															});
                                							break;
                                						}
                                						
                            								
                            						}while(true);
                            						break;   
                            					
                            					}else if(llaveDato==7) {
                            						ret=" ";
                            						tts = new TextToSpeech_v1("Ahora, dime el segundo apellido");
                            						
                            						do {
                            							try {
                            								ret = in.readLine();
                            								
                            							}catch(IOException ex) {
                            								Logger.getLogger(SpeechToText_v1.class.getName()).log(Level.SEVERE, null, ex);
                            							}
                            							
                            							if(ret!=" "){
                                							String dato;
                                							dato =  Datapreparation(CleanData(ret)).get(0);
                                							Platform.runLater(new Runnable() {
																
																@Override
																public void run() {
																	datosPaciente.setSegundoApellido(dato);
																
																}
															});
                                							break;
                                						}
                                						
                            								
                            						}while(true);
                            						break;   
                            						
                            					}else if(llaveDato==8) {
                            						ret=" ";
                            						tts = new TextToSpeech_v1("Ahora, dime el medicamento número 1");
                            						
                            						do {
                            							try {
                            								ret = in.readLine();
                            								
                            							}catch(IOException ex) {
                            								Logger.getLogger(SpeechToText_v1.class.getName()).log(Level.SEVERE, null, ex);
                            							}
                            							
                            							if(ret!=" "){
                                							String dato;
                                							dato =  Datapreparation(CleanData(ret)).get(0);
                                							Platform.runLater(new Runnable() {
																
																@Override
																public void run() {
																	datosPaciente.setMedicamento1(dato);
																
																}
															});
                                							break;
                                						}
                                						
                            								
                            						}while(true);
                            						break;   
                            						
                            					}else if(llaveDato==9) {
                            						ret=" ";
                            						tts = new TextToSpeech_v1("Ahora, dime el medicamento número 2");
                            						
                            						do {
                            							try {
                            								ret = in.readLine();
                            								
                            							}catch(IOException ex) {
                            								Logger.getLogger(SpeechToText_v1.class.getName()).log(Level.SEVERE, null, ex);
                            							}
                            							
                            							if(ret!=" "){
                                							String dato;
                                							dato =  Datapreparation(CleanData(ret)).get(0);
                                							Platform.runLater(new Runnable() {
																
																@Override
																public void run() {
																	datosPaciente.setMedicamento2(dato);
																
																}
															});
                                							break;
                                						}
                                						
                            								
                            						}while(true);
                            						break;    
                            					
                            					}else if(llaveDato==10) {
                            						ret=" ";
                            						tts = new TextToSpeech_v1("Ahora, dime el medicamento número 3");
                            						
                            						do {
                            							try {
                            								ret = in.readLine();
                            								
                            							}catch(IOException ex) {
                            								Logger.getLogger(SpeechToText_v1.class.getName()).log(Level.SEVERE, null, ex);
                            							}
                            							
                            							if(ret!=" "){
                                							String dato;
                                							dato =  Datapreparation(CleanData(ret)).get(0);
                                							Platform.runLater(new Runnable() {
																
																@Override
																public void run() {
																	datosPaciente.setMedicamento3(dato);
																
																}
															});
                                							break;
                                						}
                                						
                            								
                            						}while(true);
                            						break;   
                            						
                            					}else if(llaveDato==11) {
                            						ret=" ";
                            						tts = new TextToSpeech_v1("Ahora, dime el medicamento número 4");
                            						
                            						do {
                            							try {
                            								ret = in.readLine();
                            								
                            							}catch(IOException ex) {
                            								Logger.getLogger(SpeechToText_v1.class.getName()).log(Level.SEVERE, null, ex);
                            							}
                            							
                            							if(ret!=" "){
                                							String dato;
                                							dato =  Datapreparation(CleanData(ret)).get(0);
                                							Platform.runLater(new Runnable() {
																
																@Override
																public void run() {
																	datosPaciente.setMedicamento4(dato);
																
																}
															});
                                							break;
                                						}
                                						
                            								
                            						}while(true);
                            						break;    
                            					
                            					}else if(llaveDato==12) {
                            						ret=" ";
                            						tts = new TextToSpeech_v1("Ahora, dime el medicamento número 5");
                            						
                            						do {
                            							try {
                            								ret = in.readLine();
                            								
                            							}catch(IOException ex) {
                            								Logger.getLogger(SpeechToText_v1.class.getName()).log(Level.SEVERE, null, ex);
                            							}
                            							
                            							if(ret!=" "){
                                							String dato;
                                							dato =  Datapreparation(CleanData(ret)).get(0);
                                							Platform.runLater(new Runnable() {
																
																@Override
																public void run() {
																	datosPaciente.setMedicamento5(dato);
																
																}
															});
                                							break;
                                						}
                                						
                            								
                            						}while(true);
                            						break;   
                            					
                            					}else if(llaveDato==13) {
                            						ret=" ";
                            						tts = new TextToSpeech_v1("Ahora, dime la sustancia numero 1");
                            						
                            						do {
                            							try {
                            								ret = in.readLine();
                            								
                            							}catch(IOException ex) {
                            								Logger.getLogger(SpeechToText_v1.class.getName()).log(Level.SEVERE, null, ex);
                            							}
                            							
                            							if(ret!=" "){
                                							String dato;
                                							dato =  Datapreparation(CleanData(ret)).get(0);
                                							Platform.runLater(new Runnable() {
																
																@Override
																public void run() {
																	datosPaciente.setSustancias1(dato);
																
																}
															});
                                							break;
                                						}
                                						
                            								
                            						}while(true);
                            						break;   
                            						
                            					}else if(llaveDato==14) {
                            						ret=" ";
                            						tts = new TextToSpeech_v1("Ahora, dime la sustancia numero 2");
                            						
                            						do {
                            							try {
                            								ret = in.readLine();
                            								
                            							}catch(IOException ex) {
                            								Logger.getLogger(SpeechToText_v1.class.getName()).log(Level.SEVERE, null, ex);
                            							}
                            							
                            							if(ret!=" "){
                                							String dato;
                                							dato =  Datapreparation(CleanData(ret)).get(0);
                                							Platform.runLater(new Runnable() {
																
																@Override
																public void run() {
																	datosPaciente.setSustancias2(dato);
																
																}
															});
                                							break;
                                						}
                                						
                            								
                            						}while(true);
                            						break;   
                            						
                            					}else if(llaveDato==15) {
                            						ret=" ";
                            						tts = new TextToSpeech_v1("Ahora, dime la sustancia numero 3");
                            						
                            						do {
                            							try {
                            								ret = in.readLine();
                            								
                            							}catch(IOException ex) {
                            								Logger.getLogger(SpeechToText_v1.class.getName()).log(Level.SEVERE, null, ex);
                            							}
                            							
                            							if(ret!=" "){
                                							String dato;
                                							dato =  Datapreparation(CleanData(ret)).get(0);
                                							Platform.runLater(new Runnable() {
																
																@Override
																public void run() {
																	datosPaciente.setSustancias3(dato);
																
																}
															});
                                							break;
                                						}
                                						
                            								
                            						}while(true);
                            						break;   
                            						
                            					}else if(llaveDato==16) {
                            						ret=" ";
                            						tts = new TextToSpeech_v1("Ahora dime el valor. Este puede ser no, tipo 1 ó tipo 2");
                            						
                            						do {
                            							try {
                            								ret = in.readLine();
                            								
                            							}catch(IOException ex) {
                            								Logger.getLogger(SpeechToText_v1.class.getName()).log(Level.SEVERE, null, ex);
                            							}
                            							
                            							if(ret!=" "){
                            								int llaveTipoDiabetes = Diccionario4(Datapreparation(CleanData(ret)));
                            								if(llaveTipoDiabetes>=1 && llaveTipoDiabetes<=3) {
                            									
                            									Platform.runLater(new Runnable() {
																	
																	@Override
																	public void run() {
																		datosPaciente.setDiabetes(llaveTipoDiabetes);
																	
																		
																	}
																});
                            									break;
                            								}else {
                            									ret=" ";
                            									tts = new TextToSpeech_v1("Error");
                            									continue;
                            									
                            								}
                                							
                                						}
                                						
                            								
                            						}while(true);
                            						break;   
                            						
                            				
                            					}else if(llaveDato==17) {
                            						
                            						ret=" ";
                            						tts = new TextToSpeech_v1("Ahora dime el valor. Este puede ser no, primaria o secundaria");
                            						
                            						do {
                            							try {
                            								ret = in.readLine();
                            								
                            							}catch(IOException ex) {
                            								Logger.getLogger(SpeechToText_v1.class.getName()).log(Level.SEVERE, null, ex);
                            							}
                            							
                            							if(ret!=" "){
                            								int llaveTipoDiabetes = Diccionario4(Datapreparation(CleanData(ret)));
                            								if(llaveTipoDiabetes==1|| llaveTipoDiabetes==4 || llaveTipoDiabetes==5) {
                            									
                            									Platform.runLater(new Runnable() {
																	
																	@Override
																	public void run() {
																		datosPaciente.setHiperTension(llaveTipoDiabetes);
																	
																		
																	}
																});
                            									break;
                            								}else {
                            									ret=" ";
                            									tts = new TextToSpeech_v1("Error");
                            									continue;
                            									
                            								}
                                							
                                						}
                                						
                            								
                            						}while(true);
                            						break;   
                            						
                            						
                            						
                            					}
                            					
                            					else if(llaveDato==18) {
                            						ret=" ";
                            						tts = new TextToSpeech_v1("Ahora, dime el valor");
                            						
                            						do {
                            							try {
                            								ret = in.readLine();
                            								
                            							}catch(IOException ex) {
                            								Logger.getLogger(SpeechToText_v1.class.getName()).log(Level.SEVERE, null, ex);
                            							}
                            							
                            							if(ret!=" "){
                                							String dato = Datapreparation(extraerNumeros(ret)).get(0);
                                							Platform.runLater(new Runnable() {
																
																@Override
																public void run() {
																	datosPaciente.setInfartos(Integer.parseInt(dato));
																	
																}
															});
                                							break;
                                						}
                                						
                            								
                            						}while(true);
                            						break;   
                            						
                            					}else if(llaveDato==19) {
                            						ret=" ";
                            						tts = new TextToSpeech_v1("Ahora, dime el valor en dias");
                            						
                            						do {
                            							try {
                            								ret = in.readLine();
                            								
                            							}catch(IOException ex) {
                            								Logger.getLogger(SpeechToText_v1.class.getName()).log(Level.SEVERE, null, ex);
                            							}
                            							
                            							if(ret!=" "){
                                					
                                							String dato = Datapreparation(extraerNumeros(ret)).get(0);
                                							Platform.runLater(new Runnable() {
																
																@Override
																public void run() {
																	datosPaciente.setFuma(Integer.parseInt(dato));
																
																}
															});
                                							break;
                                						}
                                						
                            								
                            						}while(true);
                            						break;   
                            						
                            					}else if(llaveDato==20) {
                            						ret=" ";
                            						tts = new TextToSpeech_v1("Ahora dime tu respuesta. Puede ser, Si o No");
                            						
                            						do {
                            							try {
                            								ret = in.readLine();
                            								
                            							}catch(IOException ex) {
                            								Logger.getLogger(SpeechToText_v1.class.getName()).log(Level.SEVERE, null, ex);
                            							}
                            							
                            							if(ret!=" "){
                            								
                            								if(Datapreparation(CleanData(ret)).get(0).equalsIgnoreCase("si") || Datapreparation(CleanData(ret)).get(0).equalsIgnoreCase("no")) {
                            									
                            									Platform.runLater(new Runnable() {
																	
																	@Override
																	public void run() {
																		datosPaciente.setConviveFumadores(Datapreparation(CleanData(ret)).get(0));
																	
																		
																	}
																});
                            									break;
                            								}else {
                            									ret=" ";
                            									tts = new TextToSpeech_v1("Error");
                            									continue;
                            									
                            								}
                                							
                                						}
                                						
                            								
                            						}while(true);
                            						break;   

                            						
                            					}else if(llaveDato==21) {
                            						
                            						
                            						
                            						ret=" ";
                            						tts = new TextToSpeech_v1("Ahora dime el valor. Este puede ser de 0 a 30 minutos, de 30 a 60 minutos, de 1  a 3 horas o más de 3 horas");
                            						
                            						do {
                            							try {
                            								ret = in.readLine();
                            								
                            							}catch(IOException ex) {
                            								Logger.getLogger(SpeechToText_v1.class.getName()).log(Level.SEVERE, null, ex);
                            							}
                            							
                            							if(ret!=" "){
                            								String intervalo = Datapreparation(extraerNumeros(ret)).get(0);
                            								System.out.println(intervalo);
                            								if(intervalo.equalsIgnoreCase("030") || intervalo.equalsIgnoreCase("3060") || intervalo.equalsIgnoreCase("13") ||
                            										intervalo.equalsIgnoreCase("3") ) {
                            									
                            									Platform.runLater(new Runnable() {
																	
																	@Override
																	public void run() {
																		datosPaciente.setActividadFisica(intervalo);
																		
																	}
																});
                            									
                            									
                            									
                            									break;
                            								}else {
                            									ret=" ";
                            									tts = new TextToSpeech_v1("Error");
                            									continue;
                            									
                            								}
                                							
                                						}
                                						
                            								
                            						}while(true);
                            						break;   
                            						
                            					}else if(llaveDato==22) {
                            						ret=" ";
                            						tts = new TextToSpeech_v1("Ahora, dime el valor en dias");
                            						
                            						do {
                            							try {
                            								ret = in.readLine();
                            								
                            							}catch(IOException ex) {
                            								Logger.getLogger(SpeechToText_v1.class.getName()).log(Level.SEVERE, null, ex);
                            							}
                            							
                            							if(ret!=" "){
                            								String dato = Datapreparation(extraerNumeros(ret)).get(0);
                                							Platform.runLater(new Runnable() {
																
																@Override
																public void run() {
																	datosPaciente.setConsumeLicor(Integer.parseInt(dato));
																
																}
															});
                                							break;
                                						}
                                						
                            								
                            						}while(true);
                            						break;   
                            						
                            					}else if(llaveDato==23) {
                            						ret=" ";
                            						tts = new TextToSpeech_v1("Ahora, dime el valor");
                            						
                            						do {
                            							try {
                            								ret = in.readLine();
                            								
                            							}catch(IOException ex) {
                            								Logger.getLogger(SpeechToText_v1.class.getName()).log(Level.SEVERE, null, ex);
                            							}
                            							
                            							if(ret!=" "){
                            								String dato = Datapreparation(extraerNumeros(ret)).get(0);
                                							Platform.runLater(new Runnable() {
																
																@Override
																public void run() {
																	datosPaciente.setDiabetis(Integer.parseInt(dato));
																
																}
															});
                                							break;
                                						}
                                						
                            								
                            						}while(true);        
                            						break;   
                            						
                            					}else if(llaveDato==24) {
                            						
                            						ret=" ";
                            						tts = new TextToSpeech_v1("Ahora, dime el valor");
                            						
                            						do {
                            							try {
                            								ret = in.readLine();
                            								
                            							}catch(IOException ex) {
                            								Logger.getLogger(SpeechToText_v1.class.getName()).log(Level.SEVERE, null, ex);
                            							}
                            							
                            							if(ret!=" "){
                            								String dato = Datapreparation(extraerNumeros(ret)).get(0);
                                							Platform.runLater(new Runnable() {
																
																@Override
																public void run() {
																	datosPaciente.setHipertensionFamiliar(Integer.parseInt(dato));
																
																}
															});
                                							break;
                                						}
                                						
                            								
                            						}while(true);
                            						break;   
                            						
                            					}else if(llaveDato==25) {
                            						ret=" ";
                            						tts = new TextToSpeech_v1("Ahora, dime el valor");
                            						
                            						do {
                            							try {
                            								ret = in.readLine();
                            								
                            							}catch(IOException ex) {
                            								Logger.getLogger(SpeechToText_v1.class.getName()).log(Level.SEVERE, null, ex);
                            							}
                            							
                            							if(ret!=" "){
                            								String dato = Datapreparation(extraerNumeros(ret)).get(0);
                                							Platform.runLater(new Runnable() {
																
																@Override
																public void run() {
																	datosPaciente.setAtaquesCardiacos(Integer.parseInt(dato));
																
																}
															});
                                							break;
                                						}
                                						
                            								
                            						}while(true);
                            						break;   
                            						
                            					}else if(llaveDato==26) {
                            						ret=" ";
                            						tts = new TextToSpeech_v1("Ahora, dime el valor");
                            						
                            						do {
                            							try {
                            								ret = in.readLine();
                            								
                            							}catch(IOException ex) {
                            								Logger.getLogger(SpeechToText_v1.class.getName()).log(Level.SEVERE, null, ex);
                            							}
                            							
                            							if(ret!=" "){
                            								String dato = Datapreparation(extraerNumeros(ret)).get(0);
                                							Platform.runLater(new Runnable() {
																
																@Override
																public void run() {
																	datosPaciente.setACV(Integer.parseInt(dato));
																
																}
															});
                                							break;
                                						}
                                						
                            								
                            						}while(true);
                            						break;   
                            						
                            					}else if(llaveDato==27) {
                            						
                            						ret=" ";
                            						tts = new TextToSpeech_v1("Ahora, dime el tipo. Este puede ser o positivo, o negativo,a positivo, a negativo, b positivo, b negativo, a b positivo o a b negativo ");
                            						
                            						do {
                            							try {
                            								ret = in.readLine();
                            								
                            							}catch(IOException ex) {
                            								Logger.getLogger(SpeechToText_v1.class.getName()).log(Level.SEVERE, null, ex);
                            							}
                            							
                            							if(ret!=" "){
                            								System.out.println(ret);
                            								int llaveSangre = Diccionario5(Datapreparation(CleanData(ret)));
                            								if(llaveSangre>=0 && llaveSangre<=7) {
                            									System.out.println(llaveSangre);
                            									Platform.runLater(new Runnable() {
    																
    																@Override
    																public void run() {
    																	datosPaciente.setTipoSangre(llaveSangre);
    																
    																}
    															});
                                    							break;
                            								}else {
                            									ret=" ";
                            									tts = new TextToSpeech_v1("Error");
                            									continue;
                            								}
                                							
                                						}
                                						
                            								
                            						}while(true);
                            						break;   
                     
                            					}else if(llaveDato==28) {
                            						ret=" ";
                            						tts = new TextToSpeech_v1("Ahora, dime el número teléfonico");
                            						
                            						do {
                            							try {
                            								ret = in.readLine();
                            								
                            							}catch(IOException ex) {
                            								Logger.getLogger(SpeechToText_v1.class.getName()).log(Level.SEVERE, null, ex);
                            							}
                            							
                            							if(ret!=" "){
                            								String dato =  Datapreparation(CleanData(ret)).get(0);
                                							Platform.runLater(new Runnable() {
																
																@Override
																public void run() {
																	datosPaciente.setTelefono(ret);
																
																}
															});
                                							break;
                                						}
                                						
                            								
                            						}while(true);
                            						break;   
                            						
                            					}else if(llaveDato==29) {
                            						ret=" ";
                            						tts = new TextToSpeech_v1("Ahora, dime la zona. Esta puede ser urbano o rural");
                            							
                            						
                            						do {
                            							try {
                            								ret = in.readLine();
                            								
                            							}catch(IOException ex) {
                            								Logger.getLogger(SpeechToText_v1.class.getName()).log(Level.SEVERE, null, ex);
                            							}
                            							
                            							if(ret!=" "){
                            								System.out.println(ret);
                            								int llaveRaza = Diccionario6(Datapreparation(CleanData(ret)));
                            								if(llaveRaza>=13 && llaveRaza<=14) {
                            									System.out.println(llaveRaza);
                            									Platform.runLater(new Runnable() {
    																
    																@Override
    																public void run() {
    																	datosPaciente.setZona(llaveRaza-13);
    																
    																}
    															});
                                    							break;
                            								}else {
                            									ret=" ";
                            									tts = new TextToSpeech_v1("Error");
                            									continue;
                            								}
                                							
                                						}
                                						
                            								
                            						}while(true);
                            						break;   
                            						
                            						
                            					}else if(llaveDato==30) {
                            						ret=" ";
                            						tts = new TextToSpeech_v1("Ahora, dime el departamento");
                            						
                            						do {
                            							try {
                            								ret = in.readLine();
                            								
                            							}catch(IOException ex) {
                            								Logger.getLogger(SpeechToText_v1.class.getName()).log(Level.SEVERE, null, ex);
                            							}
                            							
                            							if(ret!=" "){
                            								String dato =  Datapreparation(CleanData(ret)).get(0);
                                							Platform.runLater(new Runnable() {
														
																@Override
																public void run() {
																	datosPaciente.setDepartamento(dato);
																
																}
															});
                                							break;
                                						}
                                						
                            								
                            						}while(true);
                            						break;   
                            						
                            					}else if(llaveDato==31) {
                            						
                            						ret=" ";
                            						tts = new TextToSpeech_v1("Ahora, dime el valor en centímetros");
                            						
                            						do {
                            							try {
                            								ret = in.readLine();
                            								
                            							}catch(IOException ex) {
                            								Logger.getLogger(SpeechToText_v1.class.getName()).log(Level.SEVERE, null, ex);
                            							}
                            							
                            							if(ret!=" "){
                            								
                                							Platform.runLater(new Runnable() {
														
																@Override
																public void run() {
																	datosPaciente.setAltura(ret);
																
																}
															});
                                							break;
                                						}
                                						
                            								
                            						}while(true);
                            						break;   
                            						
                            					}else if(llaveDato==32) {
                            						//fecha
                            						ret=" ";
                            						tts = new TextToSpeech_v1("Aún no estoy listo para este campo");
                            						
                            						/*ret=" ";
                            						tts = new TextToSpeech_v1("Ahora, dime la fecha en forma numerica. Primero el dia, luego el mes y por ultimo el año. Ejemplo, el 15 del 05 de 1997" );
                            							
                            						
                            						do {
                            							try {
                            								ret = in.readLine();
                            								
                            							}catch(IOException ex) {
                            								Logger.getLogger(SpeechToText_v1.class.getName()).log(Level.SEVERE, null, ex);
                            							}
                            							
                            							if(ret!=" "){
                            								System.out.println(ret);
                            								String dia = Datapreparation(extraerNumeros(ret)).get(0);
                            								String mes = Datapreparation(extraerNumeros(ret)).get(1);
                            								String anio = Datapreparation(extraerNumeros(ret)).get(2);
                            								System.out.println(dia+"/"+mes+"/"+anio);
                            								break;
                                							
                                						}
                                						
                            								
                            						}while(true);*/
                            						break;                            						
                            						
                            						
                            					}else if(llaveDato==33) {
                            					                            						
                            						ret=" ";
                            						tts = new TextToSpeech_v1("Ahora, dime la raza. Esta puede ser, Caucasian, Indigena, Afroamericana, Mulato, Mestizo, Amerindia o nativo de Alaska, Indioasiática, China, Filipina, Japonesa, Coreana o Vietnamita");
                            							
                            						
                            						do {
                            							try {
                            								ret = in.readLine();
                            								
                            							}catch(IOException ex) {
                            								Logger.getLogger(SpeechToText_v1.class.getName()).log(Level.SEVERE, null, ex);
                            							}
                            							
                            							if(ret!=" "){
                            								System.out.println(ret);
                            								int llaveRaza = Diccionario6(Datapreparation(CleanData(ret)));
                            								if(llaveRaza>=1 && llaveRaza<=12) {
                            									System.out.println(llaveRaza);
                            									Platform.runLater(new Runnable() {
    																
    																@Override
    																public void run() {
    																	datosPaciente.setRaza(llaveRaza);
    																
    																}
    															});
                                    							break;
                            								}else {
                            									ret=" ";
                            									tts = new TextToSpeech_v1("Error");
                            									continue;
                            								}
                                							
                                						}
                                						
                            								
                            						}while(true);
                            						break;   
                            						
                            						
                            						
                            					}else if(llaveDato==34) {
                            						ret=" ";
                            						tts = new TextToSpeech_v1("Ahora, dime el número");
                            						
                            						do {
                            							try {
                            								ret = in.readLine();
                            								
                            							}catch(IOException ex) {
                            								Logger.getLogger(SpeechToText_v1.class.getName()).log(Level.SEVERE, null, ex);
                            							}
                            							
                            							if(ret!=" "){
                            								
                                							Platform.runLater(new Runnable() {
														
																@Override
																public void run() {
																	datosPaciente.setCelular(ret);
																
																}
															});
                                							break;
                                						}
                                						
                            								
                            						}while(true);
                            						break;   
                            						
                            						
                            					}else if(llaveDato==35) {
                            						
                            						
                            						
                            						ret=" ";
                            						tts = new TextToSpeech_v1("Ahora, dime el genero. Esta puede ser femenino o masculino");
                            							
                            						
                            						do {
                            							try {
                            								ret = in.readLine();
                            								
                            							}catch(IOException ex) {
                            								Logger.getLogger(SpeechToText_v1.class.getName()).log(Level.SEVERE, null, ex);
                            							}
                            							
                            							if(ret!=" "){
                            								System.out.println(ret);
                            								int llaveRaza = Diccionario6(Datapreparation(CleanData(ret)));
                            								if(llaveRaza>=15 && llaveRaza<=16) {
                            									System.out.println(llaveRaza);
                            									Platform.runLater(new Runnable() {
    																
    																@Override
    																public void run() {
    																	datosPaciente.setGenero(llaveRaza-15);
    																
    																}
    															});
                                    							break;
                            								}else {
                            									ret=" ";
                            									tts = new TextToSpeech_v1("Error");
                            									continue;
                            								}
                                							
                                						}
                                						
                            								
                            						}while(true);
                            						break;   
                            						
                            						
                            						
                            					}else if(llaveDato==36) {
                            						ret=" ";
                            						tts = new TextToSpeech_v1("Ahora, dime el municipio");
                            						
                            						
                            						do {
                            							try {
                            								ret = in.readLine();
                            								
                            							}catch(IOException ex) {
                            								Logger.getLogger(SpeechToText_v1.class.getName()).log(Level.SEVERE, null, ex);
                            							}
                            							
                            							if(ret!=" "){
                            								String dato =  Datapreparation(CleanData(ret)).get(0);
                                							Platform.runLater(new Runnable() {
														
																@Override
																public void run() {
																	datosPaciente.setMunicipio(dato);
																
																}
															});
                                							break;
                                						}
                                						
                            								
                            						}while(true);
                            						break;   
                            						

                            						
                            					}else if(llaveDato==37) {
                            						ret=" ";
                            						tts = new TextToSpeech_v1("Ahora, dime la dirección");
                            						
                            						
                            						do {
                            							try {
                            								ret = in.readLine();
                            								
                            							}catch(IOException ex) {
                            								Logger.getLogger(SpeechToText_v1.class.getName()).log(Level.SEVERE, null, ex);
                            							}
                            							
                            							if(ret!=" "){
                            								String dato =  Datapreparation(CleanData(ret)).get(0);
                                							Platform.runLater(new Runnable() {
														
																@Override
																public void run() {
																	datosPaciente.setDireccion(ret);
																
																}
															});
                                							break;
                                						}
                                						
                            								
                            						}while(true);
                            						break;
                            						
                            					}else{
                            						ret=" ";
                            						tts = new TextToSpeech_v1("Error,intenta de nuevo");
                            						
                            					}
                            				
                          
                            				}
                            				
                            			}while(true);
                            			
                            			ret=" ";
                            			tts = new TextToSpeech_v1("¿Deseas que te guíe en la configuración de otro campo?");
                            			System.out.println(ret);
                            			do{
                            				
                            				try {
                                        		ret = in.readLine();
                                        	}catch(IOException ex) {
                                        		Logger.getLogger(SpeechToText_v1.class.getName()).log(Level.SEVERE, null, ex);
                                        	}
                                        	
                                        	if(ret!=" ") {
                                        		if(Datapreparation(CleanData(ret)).get(0).equalsIgnoreCase("si")) {
                                        			bandera=true;
                                        			break;   
                                        			
                                        		}
                                        		
                                        	}
                            				
                            			}while(true);
                            			
                            			
                            			
                            			
                           
                            			
                            		}else {
                            			ret = " ";
                            			tts = new TextToSpeech_v1("OK, estare pendiente si deseas configurar alguno");
                            			break;
                            		}
                            	}
                 
                            }while(true);
                            break;
                               
                        }
                         
                          if(llave==8 && sr.getEstado()==true){
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    pipec.cargarBusquedaPaciente();
                                    
                                }
                            });
                            
                            
                        }
                          
                          if(llave==9 && sr.getEstado()==true){
                              int opcion=0;
                              String desicion=" ";
                              String frase=" ";
                              
                              Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    pipec.cargarEntrenamientoAsistente();
                                    //cargarFrasesBD();
                                    
                                }
                            });
                              tts = new TextToSpeech_v1("Selecciona la funcionalidad que deseas asociar a la frase a entrenar, esta la puedes seleccionar diciendome la función o el número asociado");
                              ret=" ";
                              do{
                                   try{
                                       
                                       ret = in.readLine();
                                       
                                   }catch(IOException ex){
                                       Logger.getLogger(SpeechToText_v1.class.getName()).log(Level.SEVERE, null, ex);
                                   }
                                   
                                   if(ret!=" "){
                                       if(Datapreparation(CleanData(ret)).get(0).equalsIgnoreCase("uno") 
                                               || Datapreparation(CleanData(ret)).get(0).equalsIgnoreCase("primero")
                                               || Datapreparation(CleanData(ret)).get(0).equalsIgnoreCase("primera")
                                               || Datapreparation(CleanData(ret)).get(0).equalsIgnoreCase("activar")){
                                           opcion=1;
                                           
                                           break;
                                           
                                       }else if(Datapreparation(CleanData(ret)).get(0).equalsIgnoreCase("dos") 
                                               || Datapreparation(CleanData(ret)).get(0).equalsIgnoreCase("segundo") 
                                               || Datapreparation(CleanData(ret)).get(0).equalsIgnoreCase("segunda") 
                                               || Datapreparation(CleanData(ret)).get(0).equalsIgnoreCase("iniciar") ){
                                           
                                           opcion=2;
                                           
                                           break;
                                           
                                       }else if(Datapreparation(CleanData(ret)).get(0).equalsIgnoreCase("tres") 
                                               || Datapreparation(CleanData(ret)).get(0).equalsIgnoreCase("tercero")
                                               || Datapreparation(CleanData(ret)).get(0).equalsIgnoreCase("tercera") 
                                               || Datapreparation(CleanData(ret)).get(0).equalsIgnoreCase("desactivar")){
                                           opcion=3;
                                           
                                           break;
                                           
                                       }else if(Datapreparation(CleanData(ret)).get(0).equalsIgnoreCase("cuatro") 
                                               || Datapreparation(CleanData(ret)).get(0).equalsIgnoreCase("cuarto")
                                               || Datapreparation(CleanData(ret)).get(0).equalsIgnoreCase("cuarta") 
                                               || Datapreparation(CleanData(ret)).get(0).equalsIgnoreCase("menu") ){
                                           opcion=4;
                                           
                                           break;
                                           
                                       }else if(Datapreparation(CleanData(ret)).get(0).equalsIgnoreCase("cinco") 
                                               || Datapreparation(CleanData(ret)).get(0).equalsIgnoreCase("quinto")
                                               || Datapreparation(CleanData(ret)).get(0).equalsIgnoreCase("quinta")
                                               || Datapreparation(CleanData(ret)).get(0).equalsIgnoreCase("afinamientos") ){
                                           opcion=5;
                                           
                                           break;
                                           
                                       }else if(Datapreparation(CleanData(ret)).get(0).equalsIgnoreCase("seis") 
                                               || Datapreparation(CleanData(ret)).get(0).equalsIgnoreCase("sexto")
                                               || Datapreparation(CleanData(ret)).get(0).equalsIgnoreCase("sexta")
                                               || Datapreparation(CleanData(ret)).get(0).equalsIgnoreCase("mediciones") ){
                                           opcion=6;
                                           
                                           break;
                                           
                                       }else if(Datapreparation(CleanData(ret)).get(0).equalsIgnoreCase("siete") 
                                               || Datapreparation(CleanData(ret)).get(0).equalsIgnoreCase("septimo")
                                               || Datapreparation(CleanData(ret)).get(0).equalsIgnoreCase("septima")
                                               || Datapreparation(CleanData(ret)).get(0).equalsIgnoreCase("registrar") ){
                                           opcion=7;
                                           
                                           break;
                                           
                                       }else if(Datapreparation(CleanData(ret)).get(0).equalsIgnoreCase("ocho") 
                                               || Datapreparation(CleanData(ret)).get(0).equalsIgnoreCase("octavo")
                                               || Datapreparation(CleanData(ret)).get(0).equalsIgnoreCase("octava")
                                               || Datapreparation(CleanData(ret)).get(0).equalsIgnoreCase("cargar") ){
                                           opcion=8;
                                           
                                           break;
                                           
                                       }else if(Datapreparation(CleanData(ret)).get(0).equalsIgnoreCase("nueve") 
                                               || Datapreparation(CleanData(ret)).get(0).equalsIgnoreCase("noveno")
                                               || Datapreparation(CleanData(ret)).get(0).equalsIgnoreCase("novena")
                                               || Datapreparation(CleanData(ret)).get(0).equalsIgnoreCase("entrenar") ){
                                           opcion=9;
                                           
                                           break;
                                           
                                       }else{
                                           tts = tts = new TextToSpeech_v1("Error");
                                           continue;
                                       }
                                       
                                       
                                   }                                                                  
                              
                              }while(true);
                              
                              do{
                                  
                                  if(desicion.equalsIgnoreCase("no"))break;
                                                                                               
                                    tts = new TextToSpeech_v1("Muy bien, ahora dime la frase que deseas entrenar. Una vez empieces a hablar no hagas pausas hasta que la termines ");
                                    ret=" ";
                                    do{

                                        try{

                                            ret = in.readLine();
                                        }catch(IOException ex){
                                            Logger.getLogger(SpeechToText_v1.class.getName()).log(Level.SEVERE, null, ex);
                                        }

                                        if(ret!=" "){
                                            Platform.runLater(new Runnable() {
                                                @Override
                                                public void run() {
                                                    entrenamiento.setFraseNueva(ret);

                                                }
                                            });
                                            frase=ret;
                                            break;
                                        }
                                        

                                    }while(true);
                                    tts = new TextToSpeech_v1("Muy bien, la frase quedo de la siguiente manera, "+ret+", ¿Deseas cambiarla?");
                                    ret=" ";
                                    do{
                                        try{

                                            ret = in.readLine();

                                        }catch(IOException ex){
                                            Logger.getLogger(SpeechToText_v1.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                        
                                        

                                        if(ret!=" "){
                                            if(Datapreparation(CleanData(ret)).get(0).equalsIgnoreCase("no")){
                                                tts= new TextToSpeech_v1("Perfecto, la frase será almacenada");
                                                desicion="no";
                                                cargarFrasesBD(opcion,frase);
                                                Platform.runLater(new Runnable(){ 
                                                    @Override 
                                                    public void run() {
                                                        entrenamiento.cerrarVentana();
                                                    }
                                                });
                                                
                                                break;
                                            }else if(Datapreparation(CleanData(ret)).get(0).equalsIgnoreCase("si")){
                                                frase=" ";
                                                break;
                                            }
                                        }

                                    }while(true);
                              
                              }while(true);
                             
                          }
                          
                          if(llave==10) {
                        	  System.out.println("cerrando");
                        	  Platform.runLater(new Runnable() {
								
								@Override
								public void run() {
									pipec.initRootLayout();
									
								}
							});
                        	  
                          }
          
                    }
                     
                }
                
            }
        };
         timer.schedule(task, 0, 30);
         
        
    }
    
    
    
    
    public void setProgramaPrincipal(Pipec pipec){
        this.pipec = pipec;
    }
    
    public void setLoginController(LoginController lg){
      this.lg = lg;
    }
    
     
     public void setUsuario(Usuarios usu){
         this.usuario = usu;
     }
     
     public void setAfinamientosController(AfinamientosController afic){
         this.afic = afic;
     }
     
     public void setEntrenamientoAsistenteController(EntrenamientoAsistenteController eac){
         this.entrenamiento = eac;
     }
     
     public void setOndasController(OndasController oc) {
    	 this.ondasctr = oc;
     }
     
     public void setEstructuraBasicaController(EstructuraBasicaController ebc) {
    	 this.datosPaciente = ebc;
     }
    
    
 
    public String extraerNumeros(String frase) {
    	String cadena = frase;
    	cadena = CleanData(cadena);
    	cadena  = cadena.replaceAll("\\D+","");
    	return cadena;
    	
    }
    public String CleanData(String frase){
        String cadena = frase;
        cadena = cadena.replaceAll("^\\s*","");  //quita espacio vacios del principio
        cadena = cadena.replaceAll("\\s*$","");  //quita espacio vacios del final
        cadena = cadena.toLowerCase();           // convierte todo a minisculas
        cadena = Normalizer.normalize(cadena, Normalizer.Form.NFD);
        cadena = cadena.replaceAll("[^\\p{ASCII}]", "");  //quita tildes 
        
        return cadena;
        
    }
    
    public List<String> Datapreparation(String fraseClean){
        List<String> stopWords = Arrays.asList(
                "a",
                "al",
                "algo",
                "algunas",
                "algunos",
                "ante",
                "el",
                "ella",
                "un",
                "una",
                "unos",
                "es",
                "son",
                "que",
                "de",
                "la",
                "mi",
                "de",
                "este",
                "esta",
                "ese",
                "esa",
                "era",
                "fueron",
                "las",
                "ala",
                "ir",
                "pues",
                "numero",
                "opcion",
                "en",
                "ver",
                "y",
                "del"
                
               
                              
       
                );               
              
        String cadena = fraseClean;
	StringTokenizer tokens=new StringTokenizer(cadena);   
        List<String> ListWords = new ArrayList<String>();
        
        while(tokens.hasMoreTokens()){
            ListWords.add(tokens.nextToken());
                   
        }
        ListWords.removeAll(stopWords);
        return ListWords;   
        
    }
    
    
    
    public Integer Diccionario6(List<String> palabrasClaves){
    	int key=0;
    	
    	
    	
    	dictZona_Rasa.put(Stream.of("caucasian").collect(Collectors.toList()),1);
    	dictZona_Rasa.put(Stream.of("caucasico").collect(Collectors.toList()),1);
    	dictZona_Rasa.put(Stream.of("caucasica").collect(Collectors.toList()),1);
    	dictZona_Rasa.put(Stream.of("indigena").collect(Collectors.toList()),2);
    	dictZona_Rasa.put(Stream.of("afroamericano").collect(Collectors.toList()),3);
    	dictZona_Rasa.put(Stream.of("afroamericana").collect(Collectors.toList()),3);
    	dictZona_Rasa.put(Stream.of("mulato").collect(Collectors.toList()),4);
    	dictZona_Rasa.put(Stream.of("mulata").collect(Collectors.toList()),4);
    	dictZona_Rasa.put(Stream.of("mestizo").collect(Collectors.toList()),5);
    	dictZona_Rasa.put(Stream.of("mestiza").collect(Collectors.toList()),5);
    	dictZona_Rasa.put(Stream.of("amerindia").collect(Collectors.toList()),6);
    	dictZona_Rasa.put(Stream.of("nativo","alazka").collect(Collectors.toList()),6);
    	dictZona_Rasa.put(Stream.of("amerindia","nativo","alazka").collect(Collectors.toList()),6);
    	dictZona_Rasa.put(Stream.of("indioasiatica").collect(Collectors.toList()),7);
    	dictZona_Rasa.put(Stream.of("indiaasiatica").collect(Collectors.toList()),7);
    	dictZona_Rasa.put(Stream.of("indioasiatico").collect(Collectors.toList()),7);
    	dictZona_Rasa.put(Stream.of("indiaasiatico").collect(Collectors.toList()),7);
    	dictZona_Rasa.put(Stream.of("chino").collect(Collectors.toList()),8);
    	dictZona_Rasa.put(Stream.of("china").collect(Collectors.toList()),8);
    	dictZona_Rasa.put(Stream.of("filipino").collect(Collectors.toList()),9);
    	dictZona_Rasa.put(Stream.of("filipina").collect(Collectors.toList()),9);
    	dictZona_Rasa.put(Stream.of("japones").collect(Collectors.toList()),10);
    	dictZona_Rasa.put(Stream.of("japonesa").collect(Collectors.toList()),10);
    	dictZona_Rasa.put(Stream.of("coreano").collect(Collectors.toList()),11);
    	dictZona_Rasa.put(Stream.of("coreana").collect(Collectors.toList()),11);
    	dictZona_Rasa.put(Stream.of("vietnamita").collect(Collectors.toList()),12);
    	
    	
    	dictZona_Rasa.put(Stream.of("urbano").collect(Collectors.toList()),13);   	
    	dictZona_Rasa.put(Stream.of("urbana").collect(Collectors.toList()),13);
    	dictZona_Rasa.put(Stream.of("rural").collect(Collectors.toList()),14);
    	
    	dictZona_Rasa.put(Stream.of("femenino").collect(Collectors.toList()),15);
    	dictZona_Rasa.put(Stream.of("masculino").collect(Collectors.toList()),16);
    	
    	if(dictZona_Rasa.get(palabrasClaves)==null){
            key=0;
            
        }else{
            
            key =  dictZona_Rasa.get(palabrasClaves);
            
        }
        
        return key;
    	   	   	
    }
    
    
    public Integer Diccionario5(List<String> palabrasClaves){
    	int key=50;
    	
    	
    	dictGrupoSanguineo.put(Stream.of("o","positivo").collect(Collectors.toList()),0);
    	dictGrupoSanguineo.put(Stream.of("o","negativo").collect(Collectors.toList()),1);
    	dictGrupoSanguineo.put(Stream.of("a","positivo").collect(Collectors.toList()),2);
    	dictGrupoSanguineo.put(Stream.of("a","negativo").collect(Collectors.toList()),3);
    	dictGrupoSanguineo.put(Stream.of("b","positivo").collect(Collectors.toList()),4);
    	dictGrupoSanguineo.put(Stream.of("b","negativo").collect(Collectors.toList()),5);
    	dictGrupoSanguineo.put(Stream.of("ab","positivo").collect(Collectors.toList()),6);
    	dictGrupoSanguineo.put(Stream.of("a","b","positivo").collect(Collectors.toList()),6);
    	dictGrupoSanguineo.put(Stream.of("ave","positivo").collect(Collectors.toList()),6);
    	dictGrupoSanguineo.put(Stream.of("ab","negativo").collect(Collectors.toList()),7);
    	dictGrupoSanguineo.put(Stream.of("a","b","negativo").collect(Collectors.toList()),7);
    	dictGrupoSanguineo.put(Stream.of("ave","negativo").collect(Collectors.toList()),7);
    	
    	if(dictGrupoSanguineo.get(palabrasClaves)==null){
            key=50;
            
        }else{
            
            key =  dictGrupoSanguineo.get(palabrasClaves);
            
        }
        
        return key;
    	
    }
    
    
    
     
    
    public Integer Diccionario4(List<String> palabrasClaves){
    	int key=0;
    	
    	dictTipoDiabetes.put(Stream.of("no").collect(Collectors.toList()),1);
    	dictTipoDiabetes.put(Stream.of("tipo","1").collect(Collectors.toList()),2);
    	dictTipoDiabetes.put(Stream.of("tipo", "2").collect(Collectors.toList()),3);
    	dictTipoDiabetes.put(Stream.of("primaria").collect(Collectors.toList()),4);
    	dictTipoDiabetes.put(Stream.of("secundaria").collect(Collectors.toList()),5);
    	dictTipoDiabetes.put(Stream.of("si").collect(Collectors.toList()),6);
    	
    	
    	if(dictTipoDiabetes.get(palabrasClaves)==null){
            key=0;
            
        }else{
            
            key =  dictTipoDiabetes.get(palabrasClaves);
            
        }
        
        return key;
    	
    }
    
    
    public Integer Diccionario3(List<String> palabrasClaves){
    	int key=0;
    	
    	dictTipoID.put(Stream.of("cedula").collect(Collectors.toList()),1);
    	dictTipoID.put(Stream.of("cedula", "ciudadania").collect(Collectors.toList()),1);
    	dictTipoID.put(Stream.of("cedula", "extranjera").collect(Collectors.toList()),2);
    	dictTipoID.put(Stream.of("cedula", "extranjeria").collect(Collectors.toList()),2);
    	dictTipoID.put(Stream.of("pasaporte").collect(Collectors.toList()),3);
    	dictTipoID.put(Stream.of("registro","civil").collect(Collectors.toList()),4);
    	dictTipoID.put(Stream.of("tarjeta","identidad").collect(Collectors.toList()),5);
    	dictTipoID.put(Stream.of("adulto","sin","identificacion").collect(Collectors.toList()),6);
    	dictTipoID.put(Stream.of("menor","sin","identificacion").collect(Collectors.toList()),7);
    	
    	if(dictTipoID.get(palabrasClaves)==null){
            key=0;
            
        }else{
            
            key =  dictTipoID.get(palabrasClaves);
            
        }
        
        return key;
    	
    }
    
    
    public Integer Diccionario2(List<String> palabrasClaves){
    	int key = 0;
    	
    	dictDatosPersonales.put(Stream.of("tipo", "id").collect(Collectors.toList()),1);
    	dictDatosPersonales.put(Stream.of("id").collect(Collectors.toList()),2);
    	dictDatosPersonales.put(Stream.of("primer", "nombre").collect(Collectors.toList()),3);
    	dictDatosPersonales.put(Stream.of("segundo", "nombre").collect(Collectors.toList()),4);
    	dictDatosPersonales.put(Stream.of("administradora").collect(Collectors.toList()),5);
    	dictDatosPersonales.put(Stream.of("primer", "apellido").collect(Collectors.toList()),6);
    	dictDatosPersonales.put(Stream.of("segundo", "apellido").collect(Collectors.toList()),7);
    	dictDatosPersonales.put(Stream.of("medicamento", "1").collect(Collectors.toList()),8);
    	dictDatosPersonales.put(Stream.of("primer", "medicamento").collect(Collectors.toList()),8);
    	dictDatosPersonales.put(Stream.of("medicamento", "2").collect(Collectors.toList()),9);
    	dictDatosPersonales.put(Stream.of("segundo", "medicamento").collect(Collectors.toList()),9);
    	dictDatosPersonales.put(Stream.of("medicamento", "3").collect(Collectors.toList()),10);
    	dictDatosPersonales.put(Stream.of("tercer", "medicamento").collect(Collectors.toList()),10);
    	dictDatosPersonales.put(Stream.of("medicamento", "4").collect(Collectors.toList()),11);
    	dictDatosPersonales.put(Stream.of("cuarto", "medicamento").collect(Collectors.toList()),11);
    	dictDatosPersonales.put(Stream.of("medicamento", "5").collect(Collectors.toList()),12);
    	dictDatosPersonales.put(Stream.of("quinto", "medicamento").collect(Collectors.toList()),12);
    	dictDatosPersonales.put(Stream.of("otras", "sustancias","1").collect(Collectors.toList()),13);
    	dictDatosPersonales.put(Stream.of("otras", "sustancias","2").collect(Collectors.toList()),14);
    	dictDatosPersonales.put(Stream.of("otras", "sustancias","3").collect(Collectors.toList()),15);
    	dictDatosPersonales.put(Stream.of("diabetes").collect(Collectors.toList()),16);
    	dictDatosPersonales.put(Stream.of("hipertension").collect(Collectors.toList()),17);
    	dictDatosPersonales.put(Stream.of("ataques","cardiacos").collect(Collectors.toList()),18);
    	dictDatosPersonales.put(Stream.of("cada","cuanto","fuma").collect(Collectors.toList()),19);
    	dictDatosPersonales.put(Stream.of("vive","con","fumadores").collect(Collectors.toList()),20);
    	dictDatosPersonales.put(Stream.of("convive","con","fumadores").collect(Collectors.toList()),20);
    	dictDatosPersonales.put(Stream.of("actividad","fisica").collect(Collectors.toList()),21);
    	dictDatosPersonales.put(Stream.of("consume","licor").collect(Collectors.toList()),22);
    	dictDatosPersonales.put(Stream.of("diabetes","2").collect(Collectors.toList()),23);   //pendiente
    	dictDatosPersonales.put(Stream.of("hipertension","2").collect(Collectors.toList()),24);
    	dictDatosPersonales.put(Stream.of("ataques","cardiacos","2").collect(Collectors.toList()),25);
    	dictDatosPersonales.put(Stream.of("acv").collect(Collectors.toList()),26);
    	dictDatosPersonales.put(Stream.of("tipo","sangre").collect(Collectors.toList()),27);
    	dictDatosPersonales.put(Stream.of("telefono").collect(Collectors.toList()),28);
    	dictDatosPersonales.put(Stream.of("zona").collect(Collectors.toList()),29);
    	dictDatosPersonales.put(Stream.of("departamento").collect(Collectors.toList()),30);
    	dictDatosPersonales.put(Stream.of("altura").collect(Collectors.toList()),31);
    	dictDatosPersonales.put(Stream.of("fecha","nacimiento").collect(Collectors.toList()),32);
    	dictDatosPersonales.put(Stream.of("raza").collect(Collectors.toList()),33);
    	dictDatosPersonales.put(Stream.of("celular").collect(Collectors.toList()),34);
    	dictDatosPersonales.put(Stream.of("genero").collect(Collectors.toList()),35);
    	dictDatosPersonales.put(Stream.of("municipio").collect(Collectors.toList()),36);
    	dictDatosPersonales.put(Stream.of("direccion").collect(Collectors.toList()),37);
    	
    	
    	
    	
    	
    	
    	if(dictDatosPersonales.get(palabrasClaves)==null){
            key=0;
            
        }else{
            
            key =  dictDatosPersonales.get(palabrasClaves);
            
        }
        
        
        
        return key;
    }
    
    
    
    public Integer Diccionario(List<String> palabrasClaves){
         //Dictionary<List<String>, Integer> dictActivacion
           // = new Hashtable<List<String>, Integer>();
         
        
         
         int key = 0;
        
         
         //Activar asistente
         dictActivacion.put(Stream.of("hola", "pipec").collect(Collectors.toList()),1);
         dictActivacion.put(Stream.of("ola", "pipec").collect(Collectors.toList()),1); 
         dictActivacion.put(Stream.of("hey", "pipec").collect(Collectors.toList()),1); 
         dictActivacion.put(Stream.of("ey", "pipec").collect(Collectors.toList()),1); 
         dictActivacion.put(Stream.of("hole", "pipec").collect(Collectors.toList()),1); 
         dictActivacion.put(Stream.of("ole", "pipec").collect(Collectors.toList()),1); 
         dictActivacion.put(Stream.of("tal", "pipec").collect(Collectors.toList()),1); 
         dictActivacion.put(Stream.of("hola", "pipi").collect(Collectors.toList()),1);
         dictActivacion.put(Stream.of("ola", "pipi").collect(Collectors.toList()),1); 
         dictActivacion.put(Stream.of("hey", "pipi").collect(Collectors.toList()),1); 
         dictActivacion.put(Stream.of("ey", "pipi").collect(Collectors.toList()),1); 
         dictActivacion.put(Stream.of("hole", "pipi").collect(Collectors.toList()),1); 
         dictActivacion.put(Stream.of("ole", "pipi").collect(Collectors.toList()),1); 
         dictActivacion.put(Stream.of("tal", "pipi").collect(Collectors.toList()),1); 
         dictActivacion.put(Stream.of("hola", "pepe").collect(Collectors.toList()),1);
         dictActivacion.put(Stream.of("ola", "pepe").collect(Collectors.toList()),1); 
         dictActivacion.put(Stream.of("hey", "pepe").collect(Collectors.toList()),1); 
         dictActivacion.put(Stream.of("ey", "pepe").collect(Collectors.toList()),1); 
         dictActivacion.put(Stream.of("hole", "pepe").collect(Collectors.toList()),1); 
         dictActivacion.put(Stream.of("ole", "pepe").collect(Collectors.toList()),1); 
         dictActivacion.put(Stream.of("tal", "pepe").collect(Collectors.toList()),1); 
         dictActivacion.put(Stream.of("activar", "asistente").collect(Collectors.toList()),1); 
         
         
         //Login
         dictActivacion.put(Stream.of("iniciar", "sesion").collect(Collectors.toList()),2); 
         dictActivacion.put(Stream.of("ingresar", "plataforma").collect(Collectors.toList()),2); 
         dictActivacion.put(Stream.of("ingresar", "sistema").collect(Collectors.toList()),2); 
         dictActivacion.put(Stream.of("acceder", "plataforma").collect(Collectors.toList()),2); 
         dictActivacion.put(Stream.of("acceder", "sistema").collect(Collectors.toList()),2); 
         dictActivacion.put(Stream.of("entrar", "plataforma").collect(Collectors.toList()),2); 
         dictActivacion.put(Stream.of("entrar", "sistema").collect(Collectors.toList()),2);
         
         
         //Desactivar asistente
         dictActivacion.put(Stream.of("adios", "pipec").collect(Collectors.toList()),3); 
         dictActivacion.put(Stream.of("chao", "sesion").collect(Collectors.toList()),3); 
         dictActivacion.put(Stream.of("desactivar", "PIPEC").collect(Collectors.toList()),3); 
         dictActivacion.put(Stream.of("desactivar", "asistente").collect(Collectors.toList()),3); 
         
         
         //Desplegar menu
         dictActivacion.put(Stream.of("modulo", "menu").collect(Collectors.toList()),4);
         dictActivacion.put(Stream.of("pestana", "menu").collect(Collectors.toList()),4);
         dictActivacion.put(Stream.of("ventana", "menu").collect(Collectors.toList()),4);
         dictActivacion.put(Stream.of("seccion", "menu").collect(Collectors.toList()),4);
         dictActivacion.put(Stream.of("abrir", "menu").collect(Collectors.toList()),4);
         dictActivacion.put(Stream.of("necesito", "menu").collect(Collectors.toList()),4);
         dictActivacion.put(Stream.of("quiero", "menu").collect(Collectors.toList()),4);
         dictActivacion.put(Stream.of("debo", "menu").collect(Collectors.toList()),4);
         dictActivacion.put(Stream.of("tengo", "menu").collect(Collectors.toList()),4);
         dictActivacion.put(Stream.of("menu", "principal").collect(Collectors.toList()),4);
         
         //Desplegar afinamientos
         dictActivacion.put(Stream.of("modulo", "afinamiento").collect(Collectors.toList()),5);
         dictActivacion.put(Stream.of("pestana", "afinamiento").collect(Collectors.toList()),5);
         dictActivacion.put(Stream.of("ventana", "afinamiento").collect(Collectors.toList()),5);
         dictActivacion.put(Stream.of("seccion", "afinamiento").collect(Collectors.toList()),5);
         dictActivacion.put(Stream.of("abrir", "afinamiento").collect(Collectors.toList()),5);
         dictActivacion.put(Stream.of("necesito", "afinamiento").collect(Collectors.toList()),5);
         dictActivacion.put(Stream.of("quiero", "afinamiento").collect(Collectors.toList()),5);
         dictActivacion.put(Stream.of("debo", "afinamiento").collect(Collectors.toList()),5);
         dictActivacion.put(Stream.of("tengo", "afinamiento").collect(Collectors.toList()),5);
         dictActivacion.put(Stream.of("modulo", "afinamientos").collect(Collectors.toList()),5);
         dictActivacion.put(Stream.of("pestana", "afinamientos").collect(Collectors.toList()),5);
         dictActivacion.put(Stream.of("ventana", "afinamientos").collect(Collectors.toList()),5);
         dictActivacion.put(Stream.of("seccion", "afinamientos").collect(Collectors.toList()),5);
         dictActivacion.put(Stream.of("abrir", "afinamientos").collect(Collectors.toList()),5);
         dictActivacion.put(Stream.of("necesito", "afinamientos").collect(Collectors.toList()),5);
         dictActivacion.put(Stream.of("quiero", "afinamientos").collect(Collectors.toList()),5);
         dictActivacion.put(Stream.of("debo", "afinamientos").collect(Collectors.toList()),5);
         dictActivacion.put(Stream.of("tengo", "afinamientos").collect(Collectors.toList()),5);
         dictActivacion.put(Stream.of("registrar", "afinamiento").collect(Collectors.toList()),5);
         dictActivacion.put(Stream.of("agregar", "afinamiento").collect(Collectors.toList()),5);
         dictActivacion.put(Stream.of("adicionar", "afinamiento").collect(Collectors.toList()),5);
         dictActivacion.put(Stream.of("crear", "afinamiento").collect(Collectors.toList()),5);
        
         //Desplegar mediciones
         
         dictActivacion.put(Stream.of("modulo", "mediciones").collect(Collectors.toList()),6);
         dictActivacion.put(Stream.of("pestana", "mediciones").collect(Collectors.toList()),6);
         dictActivacion.put(Stream.of("ventana", "mediciones").collect(Collectors.toList()),6);
         dictActivacion.put(Stream.of("seccion", "mediciones").collect(Collectors.toList()),6);
         dictActivacion.put(Stream.of("abrir", "mediciones").collect(Collectors.toList()),6);
         dictActivacion.put(Stream.of("necesito", "mediciones").collect(Collectors.toList()),6);
         dictActivacion.put(Stream.of("quiero", "mediciones").collect(Collectors.toList()),6);
         dictActivacion.put(Stream.of("debo", "mediciones").collect(Collectors.toList()),6);
         dictActivacion.put(Stream.of("tengo", "mediciones").collect(Collectors.toList()),6);
         dictActivacion.put(Stream.of("modulo", "ondas").collect(Collectors.toList()),6);
         dictActivacion.put(Stream.of("pestana", "ondas").collect(Collectors.toList()),6);
         dictActivacion.put(Stream.of("ventana", "ondas").collect(Collectors.toList()),6);
         dictActivacion.put(Stream.of("seccion", "ondas").collect(Collectors.toList()),6);
         dictActivacion.put(Stream.of("abrir", "ondas").collect(Collectors.toList()),6);
         dictActivacion.put(Stream.of("necesito", "ondas").collect(Collectors.toList()),6);
         dictActivacion.put(Stream.of("quiero", "ondas").collect(Collectors.toList()),6);
         dictActivacion.put(Stream.of("debo", "ondas").collect(Collectors.toList()),6);
         dictActivacion.put(Stream.of("tengo", "ondas").collect(Collectors.toList()),6);
         dictActivacion.put(Stream.of("registrar", "medicion").collect(Collectors.toList()),6);
         dictActivacion.put(Stream.of("agregar", "medicion").collect(Collectors.toList()),6);
         dictActivacion.put(Stream.of("adicionar", "medicion").collect(Collectors.toList()),6);
         dictActivacion.put(Stream.of("crear", "medicion").collect(Collectors.toList()),6);
         
        //Datos personales de los pacientes,
        
         dictActivacion.put(Stream.of("registrar", "paciente").collect(Collectors.toList()),7);
         dictActivacion.put(Stream.of("crear", "paciente").collect(Collectors.toList()),7);
         dictActivacion.put(Stream.of("adicionar", "paciente").collect(Collectors.toList()),7);
         dictActivacion.put(Stream.of("agregar", "paciente").collect(Collectors.toList()),7);
         dictActivacion.put(Stream.of("configurar", "paciente").collect(Collectors.toList()),7);
         dictActivacion.put(Stream.of("configurar","datos", "paciente").collect(Collectors.toList()),7);
         
         //cargar paciente en el modulo de afinamientos
         dictActivacion.put(Stream.of("cargar", "paciente").collect(Collectors.toList()),8);
         dictActivacion.put(Stream.of("buscar", "paciente").collect(Collectors.toList()),8);
         dictActivacion.put(Stream.of("consultar", "paciente").collect(Collectors.toList()),8);
         
         dictActivacion.put(Stream.of("entrenar", "pipec").collect(Collectors.toList()),9);
         dictActivacion.put(Stream.of("entrenar", "pipi").collect(Collectors.toList()),9);
         dictActivacion.put(Stream.of("entrenar", "pepe").collect(Collectors.toList()),9);
         dictActivacion.put(Stream.of("entrenar", "apipe").collect(Collectors.toList()),9);
         dictActivacion.put(Stream.of("entrenar", "apipec").collect(Collectors.toList()),9);
         dictActivacion.put(Stream.of("entrenar", "asistente").collect(Collectors.toList()),9);
         
         dictActivacion.put(Stream.of("cerrar", "sesion").collect(Collectors.toList()),10);
         dictActivacion.put(Stream.of("desactivar", "sesion").collect(Collectors.toList()),10);
         dictActivacion.put(Stream.of("salir", "plataforma").collect(Collectors.toList()),10);
         dictActivacion.put(Stream.of("salir", "sistema").collect(Collectors.toList()),10);
         
         
         if(dictActivacion.get(palabrasClaves)==null){
             key=0;
             
         }else{
             
             key =  dictActivacion.get(palabrasClaves);
             
         }
         
         
         
         return key;
          
               
    }
    
    public void cargarFrasesBD(int opcion, String frase){
        List<String> ListWords = new ArrayList<String>();

        EntityManager em = Persistence.createEntityManagerFactory("PipecPU").createEntityManager();
        Query queryMedicionFindAll = em.createNativeQuery("SELECT * from entrenamiento_asistente e WHERE e.id =" + opcion, EntrenamientoAsistente.class);
        List<EntrenamientoAsistente> listaFrases = queryMedicionFindAll.getResultList();
      
        
        if(listaFrases.get(0).getFrase()==null){
            
            frase = CleanData(frase);
            em.getTransaction().begin();
            listaFrases.get(0).setFrase(frase);
            em.persist(listaFrases.get(0));
            em.getTransaction().commit();
            ListWords=Datapreparation(CleanData(frase));
            dictActivacion.put(ListWords,opcion);
        }else{
            String frases="";
            frase = CleanData(frase);
            
            em.getTransaction().begin();
            frases= listaFrases.get(0).getFrase()+","+frase;
            
            listaFrases.get(0).setFrase(frases);
            em.persist(listaFrases.get(0));
            em.getTransaction().commit();
            ListWords=Datapreparation(CleanData(frase));
            dictActivacion.put(ListWords,opcion);
            
            
        }
               
        
                
                
    }
    
    
           
    

       
}