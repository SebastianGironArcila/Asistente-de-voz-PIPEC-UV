/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import AsistenteVirtual_v1.SpeechToText_v1;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Miguel Askar
 */
public class MenuPrincipalController implements Initializable {

    @FXML
    private Button ajustesMenuPrincipal;
    @FXML
    private Button afinamientoMenuPrincipal;
    @FXML
    private Button personalesMenuPrincipal;
    private Button familiarMenuPrincipal;
    @FXML
    private Button ondasMenuPrincipal;

    /**
     * Initializes the controller class.
     */
    
    private Pipec programaPrincipal;
    private Button ajustes2;
    @FXML
    private Button wearablesMenuPrincipal;
    @FXML
    private Button variabilidadMenuPrincipal;
    
    private SpeechToText_v1 stt;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Imagen botón de ajustes
        Image        selected    = new Image(
          "\\images\\iconosPrincipal\\ajustes.png"
        );
        ImageView    imagenFondo = new ImageView();
        imagenFondo.setImage(selected);
        ajustesMenuPrincipal.setGraphic(imagenFondo);    
        ajustesMenuPrincipal.setBackground(Background.EMPTY);
        ajustesMenuPrincipal.setPadding(Insets.EMPTY);

        //Imagen botón de afinamiento
        selected    = new Image(
          "\\images\\iconosPrincipal\\afinamiento.png"
        );
        imagenFondo = new ImageView();
        imagenFondo.setImage(selected);
        afinamientoMenuPrincipal.setGraphic(imagenFondo);    
        afinamientoMenuPrincipal.setBackground(Background.EMPTY);
        afinamientoMenuPrincipal.setPadding(Insets.EMPTY);
        

        //Imagen botón de datosPersonales
        selected    = new Image(
          "\\images\\iconosPrincipal\\antecedentesPersonales.png"
        );
        imagenFondo = new ImageView();
        imagenFondo.setImage(selected);
        personalesMenuPrincipal.setGraphic(imagenFondo);    
        personalesMenuPrincipal.setBackground(Background.EMPTY);
        personalesMenuPrincipal.setPadding(Insets.EMPTY);
        
        //Imagen botón de wearables
        selected    = new Image(
          "\\images\\iconosPrincipal\\wearables.png"
        );
        imagenFondo = new ImageView();
        imagenFondo.setImage(selected);
        wearablesMenuPrincipal.setGraphic(imagenFondo);    
        wearablesMenuPrincipal.setBackground(Background.EMPTY);
        wearablesMenuPrincipal.setPadding(Insets.EMPTY);
        
        //Imagen botón de variabilidad cardiaca
        selected    = new Image(
          "\\images\\iconosPrincipal\\variabilidad.png"
        );
        imagenFondo = new ImageView();
        imagenFondo.setImage(selected);
        variabilidadMenuPrincipal.setGraphic(imagenFondo);    
        variabilidadMenuPrincipal.setBackground(Background.EMPTY);
        variabilidadMenuPrincipal.setPadding(Insets.EMPTY);
       
        //Imagen botón de ondas
        selected    = new Image(
          "\\images\\iconosPrincipal\\ondas.png"
        );
        imagenFondo = new ImageView();
        imagenFondo.setImage(selected);
        ondasMenuPrincipal.setGraphic(imagenFondo);    
        ondasMenuPrincipal.setBackground(Background.EMPTY);
        ondasMenuPrincipal.setPadding(Insets.EMPTY);
        //stt = new SpeechToText_v1();
        //stt.setMenuPrincipalController(this);
       
            
            
           
    }
    
    public void setProgramaPrincipal(Pipec programaPrincipal) {
        this.programaPrincipal = programaPrincipal ;
    }
    
   

    public Pipec getProgramaPrincipal() {
        return programaPrincipal;
    }
    
    @FXML
    public void irADatosPersonales()
    {

        Image unselected  = new Image(
          "\\images\\iconosPrincipal\\antecedentesPersonalesSelected.png"
        );
        ImageView imagenFondo = new ImageView();
        imagenFondo.setImage(unselected);
        personalesMenuPrincipal.setGraphic(imagenFondo);
        //Se crea el método que almacenará las señales dentro de un minuto.
            Timer timerMedicionUnMinuto;
            timerMedicionUnMinuto = new Timer();             
            TimerTask taskIniciar = new TimerTask() 
            {
                
                @Override
                public void run()
                {
                    Platform.runLater(new Runnable(){
                        @Override
                        public void run() { 
                            programaPrincipal.cargarEstructuraBasica();
                                                                                       
                        }
                    });
                    
                }
            }; 
            // Empezamos dentro de 60s 
            timerMedicionUnMinuto.schedule(taskIniciar, 10);
                    
        //ondasMenuPrincipal.setGraphic(imagenVieja);
        //programaPrincipal.cargarEstructuraBasica();
        //programaPrincipal.cargarDatosPersonales(panel);
    }  
    
    public void irAAjustes2()
    {
//        Image unselected  = new Image(
//          "\\images\\iconosPrincipal\\ajustes2Selected.png"
//        );
//        ImageView imagenFondo = new ImageView();
//        imagenFondo.setImage(unselected);
//        ajustes2.setGraphic(imagenFondo); 
        JOptionPane.showMessageDialog(null, "Sección en construcción");
    } 
    
    @FXML
    public void irAAfinamiento()
    {
        Image unselected  = new Image(
         "\\images\\iconosPrincipal\\afinamientoSelected.png"
        );
        ImageView imagenFondo = new ImageView();
        imagenFondo.setImage(unselected);
        afinamientoMenuPrincipal.setGraphic(imagenFondo);  
        //Se crea el método que almacenará las señales dentro de un minuto.
            Timer timerMedicionUnMinuto;
            timerMedicionUnMinuto = new Timer();             
            TimerTask taskIniciar = new TimerTask() 
            {
                
                @Override
                public void run()
                {
                    Platform.runLater(new Runnable(){
                        @Override
                        public void run() { 
                            programaPrincipal.cargarAfinamientos();
                        }
                    });
                    
                }
            }; 
            // Empezamos dentro de 60s 
            timerMedicionUnMinuto.schedule(taskIniciar, 10);
    } 
    
       
    @FXML
    public void irAAjustes()
    {
        Image unselected  = new Image(
          "\\images\\iconosPrincipal\\ajustesSelected.png"
        );
        ImageView imagenFondo = new ImageView();
        imagenFondo.setImage(unselected);
        ajustesMenuPrincipal.setGraphic(imagenFondo);  
        //Se crea el método que almacenará las señales dentro de un minuto.
            Timer timerMedicionUnMinuto;
            timerMedicionUnMinuto = new Timer();             
            TimerTask taskIniciar = new TimerTask() 
            {
                
                @Override
                public void run()
                {
                    Platform.runLater(new Runnable(){
                        @Override
                        public void run() { 
                            programaPrincipal.cargarAjustes();
                        }
                    });
                    
                }
            }; 
            // Empezamos dentro de 60s 
            timerMedicionUnMinuto.schedule(taskIniciar, 10);
    }
    
    @FXML
    public void irAOndas()
    {
         
        
        //Imagen botón de datosPersonales
        //Node imagenVieja= ondasMenuPrincipal.getGraphic();
        Image unselected  = new Image(
          "\\images\\iconosPrincipal\\ondasSelected.png"
        );
        ImageView imagenFondo = new ImageView();
        imagenFondo.setImage(unselected);
        ondasMenuPrincipal.setGraphic(imagenFondo);  
        //Se crea el método que almacenará las señales dentro de un minuto.
            Timer timerMedicionUnMinuto;
            timerMedicionUnMinuto = new Timer();             
            TimerTask taskIniciar = new TimerTask() 
            {
                
                @Override
                public void run()
                {
                    Platform.runLater(new Runnable(){
                        @Override
                        public void run() { 
                            programaPrincipal.cargarOndas();                                                                                       
                        }
                    });
                    
                }
            }; 
            // Empezamos dentro de 60s 
            timerMedicionUnMinuto.schedule(taskIniciar, 10);
                    
        //ondasMenuPrincipal.setGraphic(imagenVieja);
        //programaPrincipal.cargarEstructuraBasica();
        //programaPrincipal.cargarDatosPersonales(panel);
    } 
    
    
    
    
}
