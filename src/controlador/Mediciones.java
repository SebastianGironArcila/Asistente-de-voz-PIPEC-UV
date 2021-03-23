/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import cliente.AdminDevice;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import vista.Pipec;

/**
 *
 * @author Miguel Askar
 */
public class Mediciones 
{
    AdminDevice admin;
    private Pipec programaPrincipal;
    
    public Mediciones()
    {
        
    }
    
    public void setAdmin(AdminDevice admin)
    {
        this.admin= admin;
    }
    
    public void setProgramaPrincipal(Pipec programaPrincipal) {
        this.programaPrincipal = programaPrincipal ;
    }

    public Pipec getProgramaPrincipal() {
        return programaPrincipal;
    }
    
    public void iniciarPresion()
    {
        admin.enviarComando("manualPressure", 0);
        admin.enviarComando("stopPressure", 0);
        admin.enviarComando("startPressure", 0);
    }
    
    public void leerPresion()
    {
        //Se crea el método que almacenará las señales dentro de un minuto.
            Timer timerMedicionUnMinuto;
            timerMedicionUnMinuto = new Timer();             
            TimerTask taskIniciar = new TimerTask() 
            {                
                @Override
                public void run()
                {                  
                             
                    double presion= admin.staticParameters.readPresDias();
                    double tiempoInicio= System.currentTimeMillis();
                    double tiempoTotal= 0.0;
                    /*while((presion= admin.staticParameters.readPresDias())!=0.0)            
                    {
                        System.out.println("Esperando reinicio de dato");
                    }*/
                    while(presion== admin.staticParameters.readPresDias() && (tiempoTotal= System.currentTimeMillis()-tiempoInicio)<60000)            
                    {
                        //System.out.println("Aún no");
                    }
                    if(tiempoTotal>=60000)
                    {
                        System.out.println("No se pudo obtener la presión, por favor repita la toma de datos");
                        admin.enviarComando("stopPressure", 0);
                    }else
                    {
                      //System.out.println("La presión es: " + admin.staticParameters.readPresSist()+ "/" + admin.staticParameters.readPresDias());  
                        Platform.runLater(new Runnable(){
                        @Override
                        public void run() {
                            programaPrincipal.actualizarPresion(admin.staticParameters.readPresSist(), admin.staticParameters.readPresDias());                                                           
                        }
                        });
                        
                    }
                    
                                                                                       
                
                }
            }; 
            // Empezamos dentro de 60s 
            timerMedicionUnMinuto.schedule(taskIniciar, 500);       
        
    }
    
    public void leerPeso()
    {
        //Se crea el método que almacenará las señales dentro de un minuto.
            Timer timerMedicionUnMinuto;
            timerMedicionUnMinuto = new Timer();             
            TimerTask taskIniciar = new TimerTask() 
            {                
                @Override
                public void run()
                {  
                    admin.solicitarTanita("1116271403", "masculino", "23", "161", "regular");
                             
                    float anteriorPeso= admin.staticParameters.readWeight();
                    double tiempoInicio= System.currentTimeMillis();
                    double tiempoTotal= 0.0;
                    while(admin.staticParameters.readWeight()== anteriorPeso && (tiempoTotal= System.currentTimeMillis()-tiempoInicio)<60000)
                    {
                        //No haga nada mientras la presión no se haya actualizado
                        //System.out.println("Esperando: " + anteriorPeso);                        
                    }  
                    if(tiempoTotal>=60000)
                    {
                        System.out.println("No se pudo obtener el peso, por favor repita la toma de datos");
                    }else
                    {
                      Platform.runLater(new Runnable(){
                        @Override
                        public void run() {
                            programaPrincipal.actualizarPeso(admin.staticParameters.readWeight());                                                           
                        }
                        });
                        
                    }
                }
            }; 
            // Empezamos dentro de 60s 
            timerMedicionUnMinuto.schedule(taskIniciar, 500);
                
    }
    
    
}
