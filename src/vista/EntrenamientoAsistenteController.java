/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import AsistenteVirtual_v1.SpeechToText_v1;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 *
 * @author Usuario
 */
public class EntrenamientoAsistenteController implements Initializable {

    @FXML
    private TableView<FrasesPIPEC> tablaOpciones;
    @FXML
    private TableColumn<FrasesPIPEC,String> funcion;
    @FXML
    private TableColumn<FrasesPIPEC, String> frases;
    
    
    private Pipec programaPrincipal;
    private Stage thisStage;
    @FXML
    private TextField fraseNueva;
    @FXML
    private TableColumn<FrasesPIPEC, String> numero;
    private SpeechToText_v1 stt;
    
    public void setProgramaPrincipal(Pipec programaPrincipal) {
        this.programaPrincipal = programaPrincipal ;
    }

    public Pipec getProgramaPrincipal() {
        return programaPrincipal;
    } 
    
     public void setStage(Stage stage)
    {
        this.thisStage= stage;
    }
     
     public void setFraseNueva(String frase){
         this.fraseNueva.setText(frase);
     }
     
     public int getIndexTable(){
         return tablaOpciones.getSelectionModel().getSelectedIndex();
     }
     
     
    public void setSpeechToText(SpeechToText_v1 stt){
        this.stt = stt;
    }
    
    public void cerrarVentana()
    {
        this.thisStage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //stt.setEntrenamientoAsistenteController(this);
        //stt.setProgramaPrincipal(programaPrincipal);
        numero.setCellValueFactory(new PropertyValueFactory<>("numero"));
        funcion.setCellValueFactory(new PropertyValueFactory<>("funcion"));
        frases.setCellValueFactory(new PropertyValueFactory<>("frases"));
        
        ArrayList<FrasesPIPEC> lista = new ArrayList();
        lista.add(new FrasesPIPEC ("1","Activar el asistente","Hola PIPEC, hey PIPEC,hole PIPEC,  que tal PIPEC"));
        lista.add(new FrasesPIPEC("2","Iniciar sesion","Iniciar sesion, ingresar a la plataforma, ingresar al sistema, acceder al sistema, acceder a la plataforma, entrar al sistema, entrar a la plataforma"));
        lista.add(new FrasesPIPEC("3","Desactivar el asistente","Adios PIPEC, chao PIPEC, desactivar a PIPEC "));
        lista.add(new FrasesPIPEC ("4","Desplegar menu principal","Menu principal, modulo menu, pestaña menu, ventana menu, seccion menu, abrir el menu, necesito ir al menu, quiero ir al menu, debo ir al menu, tengo que ir al menu"));
        lista.add(new FrasesPIPEC ("5","Desplegar afinamientos","Modulo afinamientos, pestaña afinamientos, ventana afinamientos, seccion afinamientos, abrir afinamientos, necesito ir a afinamientos, quiero ir a afinamientos, debo ir a afinamientos, tengo que ir a afinamientos"));
        lista.add(new FrasesPIPEC ("6","Desplegar mediciones/ondas","Modulo mediciones/ondas, pestaña mediciones/ondas, ventana mediciones/ondas, seccion mediciones/ondas, abrir mediciones/ondas, necesito ir a mediciones/ondas, quiero ir a mediciones/ondas, debo ir a mediciones/ondas, tengo que ir a mediciones/ondas"));
        lista.add(new FrasesPIPEC("7","Registrar paciente","Registrar paciente, crear paciente, adicionar paciente"));
        lista.add(new FrasesPIPEC("8","Cargar paciente en el modulo de afinamientos", "Cargar paciente, consultar paciente, buscar paciente"));
        lista.add(new FrasesPIPEC("9","Entrenar el asistente","Entrenar a PIPEC ,entrenar al asistente"));
        
        
        tablaOpciones.setItems(FXCollections.observableArrayList(lista));
        tablaOpciones.getSelectionModel().select(0);
        
        
    
    }
     
    public static class FrasesPIPEC{
        private final SimpleStringProperty funcion;
        private final SimpleStringProperty frases;
        private final SimpleStringProperty numero;
        private FrasesPIPEC(String num,String func, String fra)
        {
            this.numero = new SimpleStringProperty(num);
            this.funcion = new SimpleStringProperty(func);
            this.frases = new SimpleStringProperty(fra);
            
            
        }
        
        public String getNumero(){
            return numero.get();
        }
        
        public void setNumero(String num){
            this.numero.set(num);
        }
        
         public String getFuncion() {
            return funcion.get();
        }
        public void setFuncion(String fun) {
            this.funcion.set(fun);
        }
       
        public String getFrases() {
            return frases.get();
        }
        public void setFrases(String fra) {
            this.frases.set(fra);
        }
       
       
    }
     
    
}
