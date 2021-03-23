    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AsistenteVirtual_v1;

/**
 *
 * @author Usuario
 */
public class SpeechRecognition {
    
    
    public boolean Estado;
    public boolean Login;
    public boolean pausaActiva; 
    public boolean banderaWelcome;
    public int banderaEntrenar; 
    
    public SpeechRecognition(){
        Estado = false;
        Login = false;
        pausaActiva = false;
        banderaWelcome = false;
        banderaEntrenar = 0;
        
    }
    
    public boolean getEstado(){
        return Estado;
    }
    
    public void setEstado(boolean estado){
        this.Estado = estado;
    }
    
    
    public boolean getEstadoLogin(){
        return Login;
    }
    
    public void setEstadoLogin(boolean estadoLogin){
        this.Login = estadoLogin;
    }
    
      public boolean getPausaActiva(){
        return pausaActiva;
    }
    
    public void setPausaActiva(boolean pausa){
        this.pausaActiva = pausa;
    }
    
    public int getbanderaEntrenar(){
        return banderaEntrenar;
    }
    
    public void setbanderaEntrenar(int banderaEntrenar){
        this.banderaEntrenar = banderaEntrenar;
    }
    
    public boolean getbanderaWelcome(){
        return banderaWelcome;
    }
    
    public void setbanderaWelcome(boolean banderaWelcome){
        this.banderaWelcome = banderaWelcome;
    }
  
    

    
    
}
