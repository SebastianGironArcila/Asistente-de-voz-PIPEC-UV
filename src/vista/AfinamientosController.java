/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import AsistenteVirtual_v1.SpeechToText_v1;
import BD.HistorialAfinamiento;
import BD.Pacientes;
import com.ibm.watson.speech_to_text.v1.SpeechToText;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.text.Text;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 * FXML Controller class
 *
 * @author Miguel Askar
 */
public class AfinamientosController extends EstructuraBasicaController implements Initializable {

    @FXML
    private Button buttonHome;
    @FXML
    private Button buttonSave;
    @FXML
    private Button buttonConnection;
    @FXML
    private Button buttonHelp;
    @FXML
    private Button buttonSmartcard;
    @FXML
    private Button buttonWavesSection;
    @FXML
    private Button buttonPressureSection;
    @FXML
    private Button buttonPersonalData;
    @FXML
    private Button buttonConfiguration;
    @FXML
    private Button tomarPresionOndas;
    @FXML
    private Text presionMedia;
    @FXML
    private Text pulso;
    @FXML
    private Button peso;
    @FXML
    private Text grasaCorporal;
    @FXML
    private Text porcentajeDeAgua;
    @FXML
    private Text masaMuscular;
    @FXML
    private Text iMC;
    @FXML
    private TableView<HistorialAfinamiento> tablaAfinamientos;
    @FXML
    private TableColumn<HistorialAfinamiento, LocalDate> columnaFechaAfinamiento;
    @FXML
    private TableColumn<HistorialAfinamiento, Double> columnaPesoAfinamiento;
    @FXML
    private TableColumn<HistorialAfinamiento, String> columnaPosicionAfinamiento;
    @FXML
    private TableColumn<HistorialAfinamiento, String> columnaJornadaAfinamiento;
    @FXML
    private TableColumn<HistorialAfinamiento, Integer> columnaDiastolicaAfinamiento;
    @FXML
    private TableColumn<HistorialAfinamiento, Integer> columnaSistolicaAfinamiento;
    @FXML
    private TableColumn<HistorialAfinamiento, Float> columnaGrasaAfinamiento;
    @FXML
    private TableColumn<HistorialAfinamiento, Float> columnaAguaAfinamiento;
    @FXML
    private TableColumn<HistorialAfinamiento, Float> columnaMuscularAfinamiento;
    @FXML
    private TableColumn<HistorialAfinamiento, Float> columnaIMCAfinamiento;
    @FXML
    private TableColumn<HistorialAfinamiento, String> columnaEstadoAfinamiento;
    @FXML
    private TableColumn<HistorialAfinamiento, String> columnaDetallesAfinamiento;
    @FXML
    private Button buscarPaciente;
    @FXML
    private TextArea detalles;
    @FXML
    private ChoiceBox<String> brazo;
    @FXML
    private ChoiceBox<String> posicion;
    @FXML
    private ChoiceBox<String> jornada;
    @FXML
    private ChoiceBox<String> estadoInicial;
    @FXML
    private Button buttonVariabilidad;
    @FXML
    private Button buttonWearables;
    @FXML
    private Text rESPFreq;
    @FXML
    private Text hRECG;
    @FXML
    private Text spO2;
    @FXML
    private Text hRSpO2;
    @FXML
    private Text presionLabel;
    @FXML
    private Text pesoLabel;
    @FXML
    private Button guardarAfinamiento;
    @FXML
    private Button consultaCentrosMedicos;
    @FXML
    private Button consultarFarmacias;
    @FXML
    private Button consultaEspecialistas;
    @FXML
    private Button medicionesAnteriores;
    @FXML
    private ImageView imagenUsuario;
    @FXML
    private Button medicamentos;
    private Pipec programaPrincipal;
    
    private SpeechToText_v1 stt;
 

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.inicializarBotonesEstructura();   
        
        //*******************Desplegables del afinamiento
        ObservableList<String> availableChoices = FXCollections.observableArrayList("Right", "Izquierdo");  
        brazo.setItems(availableChoices);
        brazo.getSelectionModel().selectFirst();
        
        availableChoices = FXCollections.observableArrayList("Seated", "De pie", "Acostado");  
        posicion.setItems(availableChoices);
        posicion.getSelectionModel().selectFirst();
        
        availableChoices = FXCollections.observableArrayList("Morning", "Tarde", "Noche");  
        jornada.setItems(availableChoices);
        jornada.getSelectionModel().selectFirst();
        
        availableChoices = FXCollections.observableArrayList("Resting", "Activo");  
        estadoInicial.setItems(availableChoices);
        estadoInicial.getSelectionModel().selectFirst();
        
        //Imagen botón de tomar peso
        Image selected    = new Image(
          "\\images\\botones\\peso.png"
        );
        ImageView imagenFondo = new ImageView();
        imagenFondo.setImage(selected);
        peso.setGraphic(imagenFondo);    
        peso.setBackground(Background.EMPTY);
        peso.setPadding(Insets.EMPTY);
        
        //Imagen botón de tomar presion
        selected    = new Image(
          "\\images\\botones\\presion.png"
        );
        imagenFondo = new ImageView();
        imagenFondo.setImage(selected);
        tomarPresionOndas.setGraphic(imagenFondo);    
        tomarPresionOndas.setBackground(Background.EMPTY);
        tomarPresionOndas.setPadding(Insets.EMPTY);
        
        //Imagen botón de almacenarAfinamiento
        selected    = new Image(
          "\\images\\botones\\botonAfinamiento.png"
        );
        imagenFondo = new ImageView();
        imagenFondo.setImage(selected);
        guardarAfinamiento.setGraphic(imagenFondo);    
        guardarAfinamiento.setBackground(Background.EMPTY);
        guardarAfinamiento.setPadding(Insets.EMPTY);
        
        //this.stt.setAfinamientosController(this);
        //this.stt.setProgramaPrincipal(programaPrincipal);
        
       
        
    }

    public void setSpeechToText(SpeechToText_v1 stt){
        this.stt = stt;
    }
    
    
    public void setComboBoxBrazo(String bra){
        if(bra.equalsIgnoreCase("izquierdo")){
            this.brazo.getSelectionModel().select(1);
        }else if(bra.equalsIgnoreCase("derecho")){
            this.brazo.getSelectionModel().select(0);
        }
    }
    
    public void setComboBoxPosicion(String pos){
        if(pos.equalsIgnoreCase("sentado")){
            this.posicion.getSelectionModel().select(0);
        }else if(pos.equalsIgnoreCase("pie")){
            this.posicion.getSelectionModel().select(1);
        }else if(pos.equalsIgnoreCase("acostado")){
            this.posicion.getSelectionModel().select(2);
        }
    }
    
    public void setComboBoxJornada(String jor){
        if(jor.equalsIgnoreCase("ma�ana")){
            this.jornada.getSelectionModel().select(0);
        }else if(jor.equalsIgnoreCase("tarde")){
            this.jornada.getSelectionModel().select(1);
        }else if(jor.equalsIgnoreCase("noche")){
            this.jornada.getSelectionModel().select(2);
        }
    }
    
    public void setComboBoxEstado(String estado){
        if(estado.equalsIgnoreCase("descansando")){
            this.estadoInicial.getSelectionModel().select(0);
        }else if(estado.equalsIgnoreCase("activo")){
            this.estadoInicial.getSelectionModel().select(1);
        }
    }
    
    public void setDetalles(String det){
        this.detalles.setText(det);
    }
    
    public void GuardarAfinamiento(){
        
    }
    
    public void setProgramaPrincipal(Pipec programaPrincipal) {
        this.programaPrincipal = programaPrincipal ;
    }

    public Pipec getProgramaPrincipal() {
        return programaPrincipal;
    }
    
    
    @FXML
    public void irAAfinamiento()
    {
        mostrarMensaje("Ya te encuentras en esta sección"); 
    }
    
    @FXML
    public void tomarPeso()
    {
        programaPrincipal.tomarPeso();
    }
    
    public void actualizarPeso(double peso)
    {
        DecimalFormat formato= new DecimalFormat("#.00");        
        this.pesoLabel.setText(formato.format(peso) + "Kg");
    }
    
    public void llenarTablaAfinamientos()
    { 
       columnaFechaAfinamiento.setCellValueFactory(new PropertyValueFactory<>("fecha"));
       columnaPesoAfinamiento.setCellValueFactory(new PropertyValueFactory<>("peso"));
       columnaPosicionAfinamiento.setCellValueFactory(new PropertyValueFactory<>("posicion"));
       columnaJornadaAfinamiento.setCellValueFactory(new PropertyValueFactory<>("jornada"));
       columnaDiastolicaAfinamiento.setCellValueFactory(new PropertyValueFactory<>("presDiastolica"));
       columnaSistolicaAfinamiento.setCellValueFactory(new PropertyValueFactory<>("presSistolica"));
       columnaGrasaAfinamiento.setCellValueFactory(new PropertyValueFactory<>("GrasaCorporal"));
       columnaAguaAfinamiento.setCellValueFactory(new PropertyValueFactory<>("PorcentajeAgua"));
       columnaMuscularAfinamiento.setCellValueFactory(new PropertyValueFactory<>("MasaMuscular"));
       columnaIMCAfinamiento.setCellValueFactory(new PropertyValueFactory<>("IMC"));
       columnaEstadoAfinamiento.setCellValueFactory(new PropertyValueFactory<>("estadoInicial"));
       columnaDetallesAfinamiento.setCellValueFactory(new PropertyValueFactory<>("Detalles"));      
       
       
       if(programaPrincipal.getPaciente()!=null)
       {
            String identificacion= programaPrincipal.getPaciente().getPacientesPK().getIdentificacion();

            EntityManager em = Persistence.createEntityManagerFactory("PipecPU").createEntityManager();
            Query queryAfinamientoFindAll = em.createNativeQuery("SELECT * from historial_afinamiento m WHERE identificacion= '" + identificacion +"'", HistorialAfinamiento.class);
            List<HistorialAfinamiento> listAfinamientos = queryAfinamientoFindAll.getResultList(); 
            tablaAfinamientos.setItems(FXCollections.observableArrayList(listAfinamientos));   
       }else
       {
           programaPrincipal.mensajePersonalizado("Aún no se ha cargado un paciente", "Paciente no cargado");
       }
       
    }    

    
    @FXML
    private void almacenarAfinamiento(ActionEvent event) {
        
        EntityManager em = Persistence.createEntityManagerFactory("PipecPU").createEntityManager();
        em.getTransaction().begin();                       
        try
        {
            //Aquí colocas tu objeto tipo Date
            Date date= new Date();
            date =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));                        
            java.sql.Timestamp fechaGuardar = new java.sql.Timestamp(date.getTime()); 

            HistorialAfinamiento afi= new HistorialAfinamiento();
            afi.setId(1);
            afi.setIdentificacion(programaPrincipal.getPaciente().getPacientesPK().getIdentificacion());
            afi.setTipoIdentificacion(programaPrincipal.getPaciente().getPacientesPK().getTipoid());                    
            afi.setFecha(fechaGuardar);
            afi.setPeso(Double.parseDouble(pesoLabel.getText().substring(0, pesoLabel.getText().indexOf(" "))));
            afi.setBrazo(brazo.getSelectionModel().getSelectedItem());
            afi.setPosicion(posicion.getSelectionModel().getSelectedItem());
            afi.setJornada(jornada.getSelectionModel().getSelectedItem());
            afi.setEstadoInicial(estadoInicial.getSelectionModel().getSelectedItem());
            int perimetro=0;
            //A continuación se deja una porción de código en caso de que se vaya a capturar el perímetro abdominal
            /*try
            {
                perimetro=Integer.parseInt(perimetroAbdominal.getText());
            }catch(Exception e)
            {
                //no asigna
            }
            //int perimetro= 0;
            try
            {
                perimetro= Integer.parseInt(perimetroAbdominal.getText());
            }catch(Exception e)
            {
                //Se captura la excepción para que no haya problema con el perímetro abdominal
            }       */             

            afi.setPerimetroAbdominal(perimetro);
            afi.setPresDiastolica(Integer.parseInt(presionLabel.getText().substring(presionLabel.getText().indexOf("/")), presionLabel.getText().length()));
            afi.setPresSistolica(Integer.parseInt(presionLabel.getText().substring(0, presionLabel.getText().indexOf("/"))));
            afi.setDetalles(detalles.getText());
            afi.setGrasaCorporal(Float.parseFloat(grasaCorporal.getText()));
            afi.setPorcentajeAgua(Float.parseFloat(porcentajeDeAgua.getText().substring(0, porcentajeDeAgua.getText().indexOf("%")-1)));
            afi.setMasaMuscular(Float.parseFloat(masaMuscular.getText()));
            afi.setImc(Float.parseFloat(iMC.getText()));

            em.persist(afi);
        }catch(Exception e)
        {
            e.printStackTrace();
        } 
        em.getTransaction().commit();
        guardarAfinamiento.setDisable(true);
        llenarTablaAfinamientos();
    }
    
    private void Guardar_Afinamiento(){
        EntityManager em = Persistence.createEntityManagerFactory("PipecPU").createEntityManager();
        em.getTransaction().begin();                       
        try
        {
            //Aquí colocas tu objeto tipo Date
            Date date= new Date();
            date =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));                        
            java.sql.Timestamp fechaGuardar = new java.sql.Timestamp(date.getTime()); 

            HistorialAfinamiento afi= new HistorialAfinamiento();
            afi.setId(1);
            afi.setIdentificacion(programaPrincipal.getPaciente().getPacientesPK().getIdentificacion());
            afi.setTipoIdentificacion(programaPrincipal.getPaciente().getPacientesPK().getTipoid());                    
            afi.setFecha(fechaGuardar);
            afi.setPeso(Double.parseDouble(pesoLabel.getText().substring(0, pesoLabel.getText().indexOf(" "))));
            afi.setBrazo(brazo.getSelectionModel().getSelectedItem());
            afi.setPosicion(posicion.getSelectionModel().getSelectedItem());
            afi.setJornada(jornada.getSelectionModel().getSelectedItem());
            afi.setEstadoInicial(estadoInicial.getSelectionModel().getSelectedItem());
            int perimetro=0;
            //A continuación se deja una porción de código en caso de que se vaya a capturar el perímetro abdominal
            /*try
            {
                perimetro=Integer.parseInt(perimetroAbdominal.getText());
            }catch(Exception e)
            {
                //no asigna
            }
            //int perimetro= 0;
            try
            {
                perimetro= Integer.parseInt(perimetroAbdominal.getText());
            }catch(Exception e)
            {
                //Se captura la excepción para que no haya problema con el perímetro abdominal
            }       */             

            afi.setPerimetroAbdominal(perimetro);
            afi.setPresDiastolica(Integer.parseInt(presionLabel.getText().substring(presionLabel.getText().indexOf("/")), presionLabel.getText().length()));
            afi.setPresSistolica(Integer.parseInt(presionLabel.getText().substring(0, presionLabel.getText().indexOf("/"))));
            afi.setDetalles(detalles.getText());
            afi.setGrasaCorporal(Float.parseFloat(grasaCorporal.getText()));
            afi.setPorcentajeAgua(Float.parseFloat(porcentajeDeAgua.getText().substring(0, porcentajeDeAgua.getText().indexOf("%")-1)));
            afi.setMasaMuscular(Float.parseFloat(masaMuscular.getText()));
            afi.setImc(Float.parseFloat(iMC.getText()));

            em.persist(afi);
        }catch(Exception e)
        {
            e.printStackTrace();
        } 
        em.getTransaction().commit();
        guardarAfinamiento.setDisable(true);
        llenarTablaAfinamientos();
    }

    

}
