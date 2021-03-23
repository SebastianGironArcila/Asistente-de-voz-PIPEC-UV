/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import BD.Antecedentesfamiliares;
import BD.AntecedentesfamiliaresPK;
import BD.Antecedentespersonales;
import BD.AntecedentespersonalesPK;
import BD.Pacientes;
import BD.PacientesPK;
import BD.Usuarios;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.JOptionPane;
import javax.xml.bind.DatatypeConverter;

import AsistenteVirtual_v1.SpeechToText_v1;
import smartcard.SmartCard;

/**
 * FXML Controller class
 *
 * @author Miguel Askar
 */
public class EstructuraBasicaController implements Initializable {

    private ImageView currentSectionImage;
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
    private Button buttonBack;
    @FXML
    private Button buttonWavesSection;
    @FXML
    private Button buttonPressureSection;
    @FXML
    private Button buttonPersonalData;
    @FXML
    private Button buttonConfiguration;
        
    
    /**
     * Initializes the controller class.
     */
    
    protected Pipec programaPrincipal;
    
    protected Alert popup; //Se debe usar con su nombre en las clases hijas.
            
    @FXML
    private Label labIdentificacion1;
    @FXML
    private Label labNombre1;
    @FXML
    private Label labApellido1;
    @FXML
    private TextField textIdentificacion1;
    @FXML
    private TextField textNombre1;
    @FXML
    private TextField textNombre2;
    @FXML
    private Label labAdministradora;
    @FXML
    private Label labNombre2;
    @FXML
    private Label labApellido2;
    @FXML
    private TextField textAdministradora;
    @FXML
    private TextField textApellido1;
    @FXML
    private TextField textApellido2;
    @FXML
    private Label labTipoIdentificacion1;
    @FXML
    private ChoiceBox<String> cboxTipoIdentificacion;
    @FXML
    private Label labTelefonoFijo;
    @FXML
    private Label labDepartamento;
    @FXML
    private Label labCelular;
    @FXML
    private Label labGenero;
    @FXML
    private Label labMunicipio;
    @FXML
    private Label labDireccion;
    @FXML
    private Label labFechaNacimiento;
    @FXML
    private Label labZona;
    @FXML
    private TextField textDepartamento;
    @FXML
    private TextField textMunicipio;
    @FXML
    private TextField textTelefonoFijo;
    @FXML
    private TextField textCelular;
    @FXML
    private TextField textDireccion;
    @FXML
    private TextField textEstatura;
    @FXML
    private DatePicker datePickerFechaNacimiento;
    @FXML
    private ChoiceBox<String> textGenero;
    @FXML
    private ChoiceBox<String> textZona;
    @FXML
    private ChoiceBox<String> grupoSanguineo;
    @FXML
    private ChoiceBox<String> raza;
    @FXML
    private Label labMedicamentosPermanentes1;
    @FXML
    private Label labMedicamentosPermanentes2;
    @FXML
    private Label labMedicamentosPermanentes3;
    @FXML
    private Label labMedicamentosPermanentes4;
    @FXML
    private Label labMedicamentosPermanentes5;
    @FXML
    private Label labOtrasSustancias1;
    @FXML
    private Label labOtrasSustancias2;
    @FXML
    private Label labOtrasSustancias3;
    @FXML
    private Label labActividadFisicaMinutos;
    @FXML
    private Label labSumaDias;
    @FXML
    private Label labConviveConFumadores;
    @FXML
    private TextField textMedicamentosPermanentes1;
    @FXML
    private TextField textMedicamentosPermanentes2;
    @FXML
    private TextField textMedicamentosPermanentes3;
    @FXML
    private TextField textMedicamentosPermanentes4;
    @FXML
    private TextField textMedicamentosPermanentes5;
    @FXML
    private TextField textOtrasSustancias1;
    @FXML
    private TextField textOtrasSustancias2;
    @FXML
    private TextField textOtrasSustancias3;
    @FXML
    private Label labDiabetes;
    @FXML
    private Label labHipertension;
    @FXML
    private Label labConsumeLicor;
    @FXML
    private Label labInfartos;
    @FXML
    private ChoiceBox<String> textDiabetes;
    @FXML
    private ChoiceBox<String> textHipertension;
    @FXML
    private ChoiceBox<String> textConviveConFumadores;
    @FXML
    private ChoiceBox<String> textActividadFisicaMinutos;
    @FXML
    private Spinner<Integer> textCosumeLicor;
    @FXML
    private Spinner<Integer> textInfartos;
    @FXML
    private Spinner<Integer> textFumaDias;
    @FXML
    private Spinner<Integer> familiaresDiabetes;
    @FXML
    private Spinner<Integer> familiaresHipertension;
    @FXML
    private Spinner<Integer> familiaresInfartos;
    @FXML
    private Spinner<Integer> familiaresACV;
    @FXML
    private Button consultaCentrosMedicos;
    @FXML
    private Button consultarFarmacias;
    @FXML
    private Button consultaEspecialistas;
    
    private Pacientes paciente;
    @FXML
    private Button buttonVariabilidad;
    @FXML
    protected Button buttonWearables;
    
    protected ImageView fotoTomada;

    @FXML
    private Button medicionesAnteriores;
    @FXML
    private Button medicamentos;
    @FXML
    private ImageView imagenUsuario;
    
    private SpeechToText_v1 stt;

    protected boolean openFoto = true, openHuella = true; //Verifica si las ventanas ya fueron abiertas.
    
    //
    
    
    public void setSpeechToText(SpeechToText_v1 stt){
        this.stt = stt;
    }
    
    public void setTipoID(int tipoID) {
    	if(tipoID==1) {
    		this.cboxTipoIdentificacion.getSelectionModel().select(0);
    	}else if(tipoID==2) {
    		this.cboxTipoIdentificacion.getSelectionModel().select(1);
    	}else if(tipoID==3) {
    		this.cboxTipoIdentificacion.getSelectionModel().select(2);
    	}else if(tipoID==4) {
    		this.cboxTipoIdentificacion.getSelectionModel().select(3);
    	}else if(tipoID==5) {
    		this.cboxTipoIdentificacion.getSelectionModel().select(4);
    	}else if(tipoID==6) {
    		this.cboxTipoIdentificacion.getSelectionModel().select(5);
    	}else if(tipoID==7) {
    		this.cboxTipoIdentificacion.getSelectionModel().select(6);
    	}
    	
    }
    
    public void setId(String id) {
    	this.textIdentificacion1.setText(id);
    }
    
    public void setPrimerNombre(String primerNombre) {
    	this.textNombre1.setText(primerNombre);
    }
    
    public void setSegundoNombre(String segundoNombre) {
    	this.textNombre2.setText(segundoNombre);
    }
    
    public void setAdministradora(String admin) {
    	this.textAdministradora.setText(admin);
    }
    
    public void setPrimerApellido(String primerApellido) {
    	this.textApellido1.setText(primerApellido);
    }
    
    public void setSegundoApellido(String segundoApellido) {
    	this.textApellido2.setText(segundoApellido);
    }
    
    public void setMedicamento1(String primerMedicamento) {
    	this.textMedicamentosPermanentes1.setText(primerMedicamento);
    }
    
    public void setMedicamento2(String segundoMedicamento) {
    	this.textMedicamentosPermanentes2.setText(segundoMedicamento);
    }
    public void setMedicamento3(String tercerMedicamento) {
    	this.textMedicamentosPermanentes3.setText(tercerMedicamento);
    }
    public void setMedicamento4(String cuartoMedicamento) {
    	this.textMedicamentosPermanentes4.setText(cuartoMedicamento);
    }
    public void setMedicamento5(String quintoMedicamento) {
    	this.textMedicamentosPermanentes5.setText(quintoMedicamento);
    }
    public void setSustancias1(String sustancias1) {
    	this.textOtrasSustancias1.setText(sustancias1);
    }
    public void setSustancias2(String sustancias2) {
    	this.textOtrasSustancias2.setText(sustancias2);
    }
    public void setSustancias3(String sustancias3) {
    	this.textOtrasSustancias3.setText(sustancias3);
    }
    
    public void setDiabetes(int tipo) {
    	if(tipo==1) {
    		this.textDiabetes.getSelectionModel().select(0);
    	}else if(tipo==2) {
    		this.textDiabetes.getSelectionModel().select(1);
    	}else if(tipo==3) {
    		this.textDiabetes.getSelectionModel().select(2);
    	}
    }
    
    
    public void setHiperTension(int tipo) {
    	if(tipo==1) {
    		this.textHipertension.getSelectionModel().select(0);
    	}else if(tipo==2) {
    		this.textHipertension.getSelectionModel().select(1);
    	}else if(tipo==3) {
    		this.textHipertension.getSelectionModel().select(2);
    	}
    }
    
    public void setInfartos(int  infartos) {
    	
    	SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,infartos,infartos);        
        textInfartos.setValueFactory(valueFactory);
    	
    }
    
    public void setFuma(int dias) {
    	
    	SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,dias,dias);        
    	textFumaDias.setValueFactory(valueFactory);
    }
    
    public void setConviveFumadores(String confirmacion) {
    	if(confirmacion.equalsIgnoreCase("no")) {
    		this.textConviveConFumadores.getSelectionModel().select(0);
    	}else if(confirmacion.equalsIgnoreCase("si")) {
    		this.textConviveConFumadores.getSelectionModel().select(1);
    	}
    }
    
    public void setActividadFisica(String num) {
    	if(num.equalsIgnoreCase("030")) {
    		this.textActividadFisicaMinutos.getSelectionModel().select(0);
    	}else if(num.equalsIgnoreCase("3060")) {
    		this.textActividadFisicaMinutos.getSelectionModel().select(1);
    	}else if(num.equalsIgnoreCase("13")) {
    		this.textActividadFisicaMinutos.getSelectionModel().select(2);
    	}else if(num.equalsIgnoreCase("3")) {
    		this.textActividadFisicaMinutos.getSelectionModel().select(3);
    	}
    }
    
    
    public void setConsumeLicor(int licor) {
    	
    	SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,licor,licor);        
    	textCosumeLicor.setValueFactory(valueFactory);
    }
    
    public void setDiabetis(int diabetis) {
    	
    	SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,diabetis,diabetis);        
    	familiaresDiabetes.setValueFactory(valueFactory);
    }
    
    public void setHipertensionFamiliar(int hipertension) {
    	SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,hipertension,hipertension);        
    	familiaresDiabetes.setValueFactory(valueFactory);
    	
    }
    
    public void setAtaquesCardiacos(int ataquesCardiacos) {
    	
    	SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,ataquesCardiacos,ataquesCardiacos);        
    	familiaresInfartos.setValueFactory(valueFactory);
    }
    
    public void setACV(int acv) {
    	SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,acv,acv);        
    	familiaresACV.setValueFactory(valueFactory);
    	
    	
    }
    
  public void setTipoSangre(int tipo) {
	 this.grupoSanguineo.getSelectionModel().select(tipo);
  }
    
    public void setTelefono(String telefono) {
    	this.textTelefonoFijo.setText(telefono);
    }
    
   public void setZona(int zona) {
	   this.textZona.getSelectionModel().select(zona);
   }
    
    public void setDepartamento(String depar) {
    	this.textDepartamento.setText(depar);
    }
    
    public void setAltura(String alt) {
    	this.textEstatura.setText(alt);
    }
    
    public void setRaza(int raza) {
    	this.raza.getSelectionModel().select(raza-1);
    }
    
    public void setCelular(String cel) {
    	this.textCelular.setText(cel);
    }
    
    public void setGenero(int genero) {
    	this.textGenero.getSelectionModel().select(genero);
    }
    
    public void setMunicipio(String mun) {
    	this.textMunicipio.setText(mun);
    }
    
    public void setDireccion(String dir) {
    	this.textDireccion.setText(dir);
    }
    
    
    
       
    //
    
    public boolean isOpenFoto() {
        return openFoto;
    }

    public void setOpenFoto(boolean openFoto) {
        this.openFoto = openFoto;
    }

    public boolean isOpenHuella() {
        return openHuella;
    }

    public void setOpenHuella(boolean openHuella) {
        this.openHuella = openHuella;
    }

    public Pacientes getPaciente() {
        return paciente;
    }

    public void setPaciente(Pacientes paciente) {
        this.paciente = paciente;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {        
        inicializarBotonesEstructura();
        llenarDesplegables();
        llenarSpinners();
    }
    
    public void inicializarBotonesEstructura()
    {
         //Imagen botón de home
        Image        selected    = new Image(
          "\\images\\iconosEstructura\\home.png"
        );
        ImageView    imagenFondo = new ImageView();
        imagenFondo.setImage(selected);
        buttonHome.setGraphic(imagenFondo);    
        buttonHome.setBackground(Background.EMPTY);
        buttonHome.setPadding(Insets.EMPTY);
        
        //Imagen botón de guardar
        selected    = new Image(
          "\\images\\iconosEstructura\\guardar.png"
        );
        imagenFondo = new ImageView();
        imagenFondo.setImage(selected);
        buttonSave.setGraphic(imagenFondo);    
        buttonSave.setBackground(Background.EMPTY);
        buttonSave.setPadding(Insets.EMPTY);
        
        //Imagen botón de conexión
        selected    = new Image(
          "\\images\\iconosEstructura\\conexion.png"
        );
        imagenFondo = new ImageView();
        imagenFondo.setImage(selected);
        buttonConnection.setGraphic(imagenFondo);    
        buttonConnection.setBackground(Background.EMPTY);
        buttonConnection.setPadding(Insets.EMPTY);
        
        //Imagen botón de ayuda
        selected    = new Image(
          "\\images\\iconosEstructura\\ayuda.png"
        );
        imagenFondo = new ImageView();
        imagenFondo.setImage(selected);
        buttonHelp.setGraphic(imagenFondo);    
        buttonHelp.setBackground(Background.EMPTY);
        buttonHelp.setPadding(Insets.EMPTY);
        
        //Imagen botón de smartcard
        selected    = new Image(
          "\\images\\iconosEstructura\\smartcard.png"
        );
        imagenFondo = new ImageView();
        imagenFondo.setImage(selected);
        buttonSmartcard.setGraphic(imagenFondo);    
        buttonSmartcard.setBackground(Background.EMPTY);
        buttonSmartcard.setPadding(Insets.EMPTY);
        
        //Imagen botón de centros médicos
        selected    = new Image(
          "\\images\\iconosEstructura\\hospitales.png"
        );
        imagenFondo = new ImageView();
        imagenFondo.setImage(selected);
        consultaCentrosMedicos.setGraphic(imagenFondo);    
        consultaCentrosMedicos.setBackground(Background.EMPTY);
        consultaCentrosMedicos.setPadding(Insets.EMPTY);
        
        //Imagen botón de medicamentos
        selected    = new Image(
          "\\images\\iconosEstructura\\medicamentos.png"
        );
        imagenFondo = new ImageView();
        imagenFondo.setImage(selected);
        medicamentos.setGraphic(imagenFondo);    
        medicamentos.setBackground(Background.EMPTY);
        medicamentos.setPadding(Insets.EMPTY);
        
        //Imagen botón de especialistas
        selected    = new Image(
          "\\images\\iconosEstructura\\especialistas.png"
        );
        imagenFondo = new ImageView();
        imagenFondo.setImage(selected);
        consultaEspecialistas.setGraphic(imagenFondo);    
        consultaEspecialistas.setBackground(Background.EMPTY);
        consultaEspecialistas.setPadding(Insets.EMPTY);
        
        //Imagen botón de farmacias
        selected    = new Image(
          "\\images\\iconosEstructura\\farmacias.png"
        );
        imagenFondo = new ImageView();
        imagenFondo.setImage(selected);
        consultarFarmacias.setGraphic(imagenFondo);    
        consultarFarmacias.setBackground(Background.EMPTY);
        consultarFarmacias.setPadding(Insets.EMPTY);
        
        //Imagen botón de mediciones anteriores
        selected    = new Image(
          "\\images\\iconosEstructura\\medicionesPrevias.png"
        );
        imagenFondo = new ImageView();
        imagenFondo.setImage(selected);
        medicionesAnteriores.setGraphic(imagenFondo);    
        medicionesAnteriores.setBackground(Background.EMPTY);
        medicionesAnteriores.setPadding(Insets.EMPTY);
               
        
       
        //Imagen panel lateral - ondas
        selected    = new Image(
          "\\images\\iconosLateral\\ondas.png"
        );
        imagenFondo = new ImageView();
        imagenFondo.setImage(selected);
        buttonWavesSection.setGraphic(imagenFondo);    
        buttonWavesSection.setBackground(Background.EMPTY);
        buttonWavesSection.setPadding(Insets.EMPTY);
        
        //Imagen panel lateral - afinamiento
        selected    = new Image(
          "\\images\\iconosLateral\\afinamiento.png"
        );
        imagenFondo = new ImageView();
        imagenFondo.setImage(selected);
        buttonPressureSection.setGraphic(imagenFondo);    
        buttonPressureSection.setBackground(Background.EMPTY);
        buttonPressureSection.setPadding(Insets.EMPTY);
        
        //Imagen panel lateral - datos personales
        selected    = new Image(
          "\\images\\iconosLateral\\datosPersonales.png"
        );
        imagenFondo = new ImageView();
        imagenFondo.setImage(selected);
        buttonPersonalData.setGraphic(imagenFondo);    
        buttonPersonalData.setBackground(Background.EMPTY);
        buttonPersonalData.setPadding(Insets.EMPTY);
        
        
        //Imagen panel lateral - ajustes
        selected    = new Image(
          "\\images\\iconosLateral\\ajustes.png"
        );
        imagenFondo = new ImageView();
        imagenFondo.setImage(selected);
        buttonConfiguration.setGraphic(imagenFondo);    
        buttonConfiguration.setBackground(Background.EMPTY);  
        buttonConfiguration.setPadding(Insets.EMPTY);
        
        //Imagen panel lateral - variabilidad
        selected    = new Image(
          "\\images\\iconosLateral\\variabilidad.png"
        );
        imagenFondo = new ImageView();
        imagenFondo.setImage(selected);
        buttonVariabilidad.setGraphic(imagenFondo);    
        buttonVariabilidad.setBackground(Background.EMPTY);  
        buttonVariabilidad.setPadding(Insets.EMPTY);
        
        //Imagen panel lateral - wearables
        selected    = new Image(
          "\\images\\iconosLateral\\wearables.png"
        );
        imagenFondo = new ImageView();
        imagenFondo.setImage(selected);
        buttonWearables.setGraphic(imagenFondo);    
        buttonWearables.setBackground(Background.EMPTY);  
        buttonWearables.setPadding(Insets.EMPTY);
    }
    
    public void setProgramaPrincipal(Pipec programaPrincipal) {
        this.programaPrincipal = programaPrincipal ;
    }

    public Pipec getProgramaPrincipal() {
        return programaPrincipal;
    }
    
    /*public void iniciarSesion() //Llama al administrador para iniciar sesión.
    {
        programaPrincipal.irMenuPrincipal();
    }*/
    
    @FXML
    public void irAOndas()
    {
        Image unselected  = new Image(
          "\\images\\iconosLateral\\ondasSelected.png"
        );
        ImageView imagenFondo = new ImageView();
        imagenFondo.setImage(unselected);
        buttonWavesSection.setGraphic(imagenFondo);   
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
            timerMedicionUnMinuto.schedule(taskIniciar, 100);
    }
    
    @FXML
    public void irAAfinamiento()
    {
        Image unselected  = new Image(
          "\\images\\iconosLateral\\AfinamientoSelected.png"
        );
        ImageView imagenFondo = new ImageView();
        imagenFondo.setImage(unselected);
        buttonPressureSection.setGraphic(imagenFondo);   
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
            timerMedicionUnMinuto.schedule(taskIniciar, 100);
    }
    
    @FXML
    private void volverAca() //Este método sólo se llama desde esta clase
    {
        mostrarMensaje("Ya te encuentras en esta sección");
    }
    
    public void irADatosPersonales()
    {
        Image unselected  = new Image(
          "\\images\\iconosLateral\\datosPersonalesSelected.png"
        );
        ImageView imagenFondo = new ImageView();
        imagenFondo.setImage(unselected);
        buttonPersonalData.setGraphic(imagenFondo);   
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
            timerMedicionUnMinuto.schedule(taskIniciar, 100);        
    }  
    
    @FXML
    public void irAAjustes()
    {
        Image unselected  = new Image(
          "\\images\\iconosLateral\\ajustesSelected.png"
        );
        ImageView imagenFondo = new ImageView();
        imagenFondo.setImage(unselected);
        buttonConfiguration.setGraphic(imagenFondo);   
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
            timerMedicionUnMinuto.schedule(taskIniciar, 100);
    }  
    
    @FXML
    public void irAWearables()
    {
        Image unselected  = new Image(
          "\\images\\iconosLateral\\wearablesSelected.png"
        );
        ImageView imagenFondo = new ImageView();
        imagenFondo.setImage(unselected);
        buttonWearables.setGraphic(imagenFondo);   
        
    }  
    
    @FXML
    public void irAVariabilidad()
    {
        Image unselected  = new Image(
          "\\images\\iconosLateral\\variabilidadSelected.png"
        );
        ImageView imagenFondo = new ImageView();
        imagenFondo.setImage(unselected);
        buttonVariabilidad.setGraphic(imagenFondo);           
    }  
   
     
    
    @FXML
    public void irAHome()
    {
        Image unselected  = new Image(
          "\\images\\iconosEstructura\\homeSelected.png"
        );
        ImageView imagenFondo = new ImageView();
        imagenFondo.setImage(unselected);
        buttonHome.setGraphic(imagenFondo);
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
                            programaPrincipal.irMenuPrincipal();
                                                                                       
                        }
                    });
                    
                }
            }; 
            // Empezamos dentro de 60s 
            timerMedicionUnMinuto.schedule(taskIniciar, 100);
    }
    
    @FXML
    public void irAtras()
    {
        Image unselected  = new Image(
          "\\images\\iconosEstructura\\backSelected.png"
        );
        ImageView imagenFondo = new ImageView();
        imagenFondo.setImage(unselected);
        buttonBack.setGraphic(imagenFondo);
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
                            programaPrincipal.irMenuPrincipal();
                                                                                       
                        }
                    });
                    
                }
            }; 
            // Empezamos dentro de 60s 
            timerMedicionUnMinuto.schedule(taskIniciar, 10);
    }
    
    @FXML
    public void irAGuardar()
    {
        Image unselected  = new Image(
          "\\images\\iconosEstructura\\guardarSelected.png"
        );
        ImageView imagenFondo = new ImageView();
        imagenFondo.setImage(unselected);
        buttonSave.setGraphic(imagenFondo);
        JOptionPane.showMessageDialog(null, "Aún no se ha creado la sección de guardado");
    }
    
    @FXML
    public void irAConexion()
    {
        Image unselected  = new Image(
          "\\images\\iconosEstructura\\conexionSelected.png"
        );
        ImageView imagenFondo = new ImageView();
        imagenFondo.setImage(unselected);
        buttonConnection.setGraphic(imagenFondo);
        JOptionPane.showMessageDialog(null, "Aún no se da soporte a la conexión");
    }
    
    @FXML
    public void irAAyuda()
    {
        Image unselected  = new Image(
          "\\images\\iconosEstructura\\ayudaSelected.png"
        );
        ImageView imagenFondo = new ImageView();
        imagenFondo.setImage(unselected);
        buttonHelp.setGraphic(imagenFondo);
        JOptionPane.showMessageDialog(null, "Aún no hay sección de ayuda");
    }
    
    public void irASmartcard()
    {
        Image unselected  = new Image(
          "\\images\\iconosEstructura\\smartcardSelected.png"
        );
        ImageView imagenFondo = new ImageView();
        imagenFondo.setImage(unselected);
        buttonSmartcard.setGraphic(imagenFondo);
        JOptionPane.showMessageDialog(null, "Aún no se integra la Smartcard");
    }
    
    public void mostrarMensaje(String mensaje)
    {
        popup = new Alert(Alert.AlertType.INFORMATION);
        popup.setTitle("PIPEC-UV informa");
        popup.setHeaderText(null);
        popup.setContentText(mensaje);
        popup.showAndWait();
    } 
    
    public void cargarBusquedaPaciente()
    {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                programaPrincipal.cargarBusquedaPaciente();
                
            }
        });
        
    }
    
    public void tomarPresion()
    {
        programaPrincipal.tomarPresion();              
    }
    
    public String leerTarjeta()
    {
        String resultado="";
        SmartCard smartCard= new SmartCard();        
        try 
        {       
            resultado= smartCard.readFromCard();
        } catch (Exception ex) {
            Logger.getLogger(MenuPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultado;
    }
    
    public void llenarDesplegables()
    {
        ObservableList<String> availableChoices = FXCollections.observableArrayList("CC = Cédula ciudadanía", "CE = Cédula de extranjería", "PA = Pasaporte", "RC = Registro civil", "TI = Tarjeta de identidad", "AS = Adulto sin identificación", "MS = Menor sin identificación"); 
        cboxTipoIdentificacion.setItems(availableChoices);
        cboxTipoIdentificacion.getSelectionModel().selectFirst();
        
        availableChoices = FXCollections.observableArrayList("O+", "0-", "A+", "A-", "B+", "B-", "AB+", "AB-"); 
        grupoSanguineo.setItems(availableChoices);
        grupoSanguineo.getSelectionModel().selectFirst();
        
        availableChoices = FXCollections.observableArrayList("Urban", "Rural"); 
        textZona.setItems(availableChoices);
        textZona.getSelectionModel().selectFirst();
        
        availableChoices = FXCollections.observableArrayList("Caucasian", "Indígena", "Afroamericana", "Mulato", "Mestizo", "Amerindia o nativo de Alaska", "Indioasiática", "China", "Filipina",
                "Japonesa", "Coreana", "Vietnamita"); 
        raza.setItems(availableChoices);
        raza.getSelectionModel().selectFirst();
        
        availableChoices = FXCollections.observableArrayList("Female", "Masculino"); 
        textGenero.setItems(availableChoices);
        textGenero.getSelectionModel().selectFirst();
        
        availableChoices = FXCollections.observableArrayList("No", "Tipo 1", "Tipo 2");        
        textDiabetes.setItems(availableChoices);
        textDiabetes.getSelectionModel().selectFirst();
        
        availableChoices = FXCollections.observableArrayList("No", "Primaria", "Secundaria");    
        textHipertension.setItems(availableChoices);
        textHipertension.getSelectionModel().selectFirst();
        
        availableChoices = FXCollections.observableArrayList("No", "Sí");        
        textConviveConFumadores.setItems(availableChoices);
        textConviveConFumadores.getSelectionModel().selectFirst();
        
        availableChoices = FXCollections.observableArrayList("0 to 30 minutes", "30 a 60 miutos", "1 a 3 horas", "Más de 3 horas");        
        textActividadFisicaMinutos.setItems(availableChoices);
        textActividadFisicaMinutos.getSelectionModel().selectFirst();       
        
    }
    
    public void llenarSpinners()
    {
        //*******************Spinner de antecedentes personales
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 5, 0);        
        textInfartos.setValueFactory(valueFactory);
        
        valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 7, 0);        
        textFumaDias.setValueFactory(valueFactory);
        valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 7, 0);        
        textCosumeLicor.setValueFactory(valueFactory);
        
        //*******************Spinner de antecedentes familiares
        valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 20, 0);

        familiaresDiabetes.setValueFactory(valueFactory);  
        valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 20, 0);
        familiaresHipertension.setValueFactory(valueFactory); 
        valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 20, 0);
        familiaresInfartos.setValueFactory(valueFactory); 
        valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 20, 0);
        familiaresACV.setValueFactory(valueFactory); 
    }
    
    @FXML
    public void llenarPaciente()
    {
        if(paciente!=null)
        {
            cboxTipoIdentificacion.getSelectionModel().select(paciente.getPacientesPK().getTipoid());
            cboxTipoIdentificacion.setValue(paciente.getPacientesPK().getTipoid());
            textIdentificacion1.setText(paciente.getPacientesPK().getIdentificacion());
            textAdministradora.setText(paciente.getAdministradora());
            textNombre1.setText(paciente.getNombre1());
            textNombre2.setText(paciente.getNombre2());
            textApellido1.setText(paciente.getApellido1());
            textApellido2.setText(paciente.getApellido2());
            textApellido2.setText(paciente.getApellido2());
            // Datos personales del paciente.
            textEstatura.setText(String.valueOf(paciente.getEstatura()));
            grupoSanguineo.getSelectionModel().select(paciente.getGrupoSanguineo());
            raza.getSelectionModel().select(paciente.getRaza());
                        textTelefonoFijo.setText(paciente.getTelFijo());
            textCelular.setText(paciente.getCelular());
            textDireccion.setText(paciente.getDireccion());            

            int seleccionGenero= 0;
            if(paciente.getGenero().equals("Masculino"))
            {
                seleccionGenero= 1;
            }
            textGenero.getSelectionModel().select(seleccionGenero); //(paciente.getGenero());
            datePickerFechaNacimiento.setValue((paciente.getFNacimiento() != null) ? Instant.ofEpochMilli(paciente.getFNacimiento().getTime()).atZone(ZoneId.systemDefault()).toLocalDate() : null);
            textDepartamento.setText(paciente.getDepartamento());
            textMunicipio.setText(paciente.getMunicipio());
            int seleccionZona= 0;
            if(paciente.getGenero().equals("Rural"))
            {
                seleccionZona= 1;
            }
            textZona.getSelectionModel().select(seleccionZona);
            
            //Cargar foto del paciente
            if (paciente.getFoto() == null) {
                //Imagen cuando no hay foto del paciente
            //currentSectionImage.setImage(new ImageView(foto));
            } else {
                InputStream in1 = new ByteArrayInputStream(paciente.getFoto());
                Image image1 = new Image(in1);                
                currentSectionImage.setImage(image1);
            }
            
            // Antecedentes personales
            Antecedentespersonales ap = buscarAntecedentesPersonales(paciente.getPacientesPK().getIdentificacion());
            textMedicamentosPermanentes1.setText(ap.getMedicamentospermanentes1());
            textMedicamentosPermanentes2.setText(ap.getMedicamentospermanentes2());
            textMedicamentosPermanentes3.setText(ap.getMedicamentospermanentes3());
            textMedicamentosPermanentes4.setText(ap.getMedicamentospermanentes4());
            textMedicamentosPermanentes5.setText(ap.getMedicamentospermanentes5());
            textOtrasSustancias1.setText(ap.getOtrassustancias1());
            textOtrasSustancias2.setText(ap.getOtrassustancias2());
            textOtrasSustancias3.setText(ap.getOtrassustancias3());
            textDiabetes.setValue(ap.getDiabetes());
            textHipertension.setValue(ap.getHipertension());
            textInfartos.getValueFactory().setValue(Integer.parseInt(ap.getInfartos())); 
            textFumaDias.getValueFactory().setValue(Integer.parseInt(ap.getFumadias()));
            textConviveConFumadores.setValue(ap.getConviveconfumadores());
            textActividadFisicaMinutos.setValue(ap.getActividadfisica());
            textCosumeLicor.getValueFactory().setValue(Integer.parseInt(ap.getCosumelicor()));
            // Antecedentes familiares
            Antecedentesfamiliares af = buscarAntecedenteFamiliares(paciente.getPacientesPK().getIdentificacion());
            familiaresDiabetes.getValueFactory().setValue(af.getDiabetes());
            familiaresHipertension.getValueFactory().setValue(af.getHipertension());
            familiaresInfartos.getValueFactory().setValue(af.getInfartos());
            familiaresACV.getValueFactory().setValue(af.getAcv());
            
            
        }
    }
    
    private Antecedentespersonales buscarAntecedentesPersonales(String id) {
        Antecedentespersonales resultado = null;
        EntityManager em = Persistence.createEntityManagerFactory("PipecPU").createEntityManager(); 
        
        List<Antecedentespersonales> list = em.createNamedQuery("Antecedentespersonales.findAll", Antecedentespersonales.class).getResultList();
        for (int i = 0; i < list.size(); i++) {
            Antecedentespersonales obj = list.get(i);
            if (id.equals(obj.getAntecedentespersonalesPK().getIdentificacion())) {
                return obj;
            }
        }
        return resultado;
    }
    
    private Antecedentesfamiliares buscarAntecedenteFamiliares(String id) {
        Antecedentesfamiliares resultado = null;
        EntityManager em = Persistence.createEntityManagerFactory("PipecPU").createEntityManager(); 
        List<Antecedentesfamiliares> list = em.createNamedQuery("Antecedentesfamiliares.findAll", Antecedentesfamiliares.class).getResultList();
        for (int i = 0; i < list.size(); i++) {
            Antecedentesfamiliares obj = list.get(i);
            if (id.equals(obj.getAntecedentesfamiliaresPK().getIdentificacion())) {
                return obj;
            }
        }
        return resultado;
    }
    
    @FXML
    public void limpiarCampos()
    {
            cboxTipoIdentificacion.getSelectionModel().selectFirst();            
            textIdentificacion1.setText(null);
            textAdministradora.setText(null);
            textNombre1.setText(null);
            textNombre2.setText(null);
            textApellido1.setText(null);
            textApellido2.setText(null);
            // Datos personales del paciente.
            textEstatura.setText(null);
            grupoSanguineo.getSelectionModel().select(null);
            raza.getSelectionModel().selectFirst();
            textTelefonoFijo.setText(null);
            textCelular.setText(null);
            textDireccion.setText(null);            
            textGenero.getSelectionModel().selectFirst(); //(paciente.getGenero());
            //Se obtiene la fecha actual
            Date date= new Date();
            try {                        
                date =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
            } catch (ParseException ex) {
                Logger.getLogger(NuevoUsuarioController.class.getName()).log(Level.SEVERE, null, ex);
            }
            java.sql.Timestamp fechaGuardar = new java.sql.Timestamp(date.getTime()); 
            //--------------------------
            datePickerFechaNacimiento.setValue((paciente.getFNacimiento() != null) ? Instant.ofEpochMilli(fechaGuardar.getTime()).atZone(ZoneId.systemDefault()).toLocalDate() : null);
            textDepartamento.setText(null);
            textMunicipio.setText(null);
            int seleccionZona= 0;
            textZona.getSelectionModel().selectFirst();
            
            // Antecedentes personales            
            textMedicamentosPermanentes1.setText(null);
            textMedicamentosPermanentes2.setText(null);
            textMedicamentosPermanentes3.setText(null);
            textMedicamentosPermanentes4.setText(null);
            textMedicamentosPermanentes5.setText(null);
            textOtrasSustancias1.setText(null);
            textOtrasSustancias2.setText(null);
            textOtrasSustancias3.setText(null);
            textDiabetes.getSelectionModel().selectFirst();
            textHipertension.getSelectionModel().selectFirst();
            textInfartos.getValueFactory().setValue(0); 
            textFumaDias.getValueFactory().setValue(0);
            textConviveConFumadores.getSelectionModel().selectFirst();
            textActividadFisicaMinutos.getSelectionModel().selectFirst();
            textCosumeLicor.getValueFactory().setValue(0);
            // Antecedentes familiares
            familiaresDiabetes.getValueFactory().setValue(0);
            familiaresHipertension.getValueFactory().setValue(0);
            familiaresInfartos.getValueFactory().setValue(0);
            familiaresACV.getValueFactory().setValue(0);
    }
    
    @FXML
    public void guardarPaciente()
    {
            Pacientes p = new Pacientes();
            PacientesPK ppk = new PacientesPK(cboxTipoIdentificacion.getValue().toString().substring(0, 2), textIdentificacion1.getText());
            p.setPacientesPK(ppk);
            p.setNombre1(textNombre1.getText());
            p.setNombre2(textNombre2.getText());
            p.setApellido1(textApellido1.getText());
            p.setApellido2(textApellido2.getText());
            p.setAdministradora(textAdministradora.getText());
            // *************************************************
            p.setTelFijo(textTelefonoFijo.getText());
            p.setCelular(textCelular.getText());
            p.setDireccion(textDireccion.getText());
            p.setTipousuario(2); //Siempre son pacientes
            p.setGenero(textGenero.getSelectionModel().getSelectedItem().toString());
            Date date = Date.from(datePickerFechaNacimiento.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            p.setFNacimiento(date);
            p.setDepartamento(textDepartamento.getText());
            p.setMunicipio(textMunicipio.getText());
            p.setZona(textZona.getSelectionModel().getSelectedItem().toString());
            p.setEstatura(Float.parseFloat(textEstatura.getText()));
            p.setGrupoSanguineo(grupoSanguineo.getSelectionModel().getSelectedItem());
            p.setRaza(raza.getSelectionModel().getSelectedItem());
            p.setAutorizacion(1); 
            // *************************************************
            if(currentSectionImage.getImage()!=null)
            {
                BufferedImage bImageFoto = SwingFXUtils.fromFXImage(currentSectionImage.getImage(), null);
                ByteArrayOutputStream baos1 = new ByteArrayOutputStream();
                try {
                    ImageIO.write(bImageFoto, "png", baos1);
                    baos1.flush();
                    p.setFoto(baos1.toByteArray());
                } catch (IOException ex) {
                    Logger.getLogger(EstructuraBasicaController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }                    
            
            //p.setHuella(plantillaHuella.serialize());
            //p.setHuella2(imageInByteHuella);
            EntityManager em = Persistence.createEntityManagerFactory("PipecPU").createEntityManager();
            em.getTransaction().begin();
            boolean opcionNuevo= !existePaciente();
            if (opcionNuevo) {
                registrarUsuario();
                em.persist(p);
            } else {
                em.merge(p);
            }
            em.getTransaction().commit();
            // *************************************************
            // *** Antecedente personales
            // *************************************************
            Antecedentespersonales ap = new Antecedentespersonales();
            AntecedentespersonalesPK appk = new AntecedentespersonalesPK(cboxTipoIdentificacion.getValue().toString().substring(0, 2), textIdentificacion1.getText());
            ap.setAntecedentespersonalesPK(appk);
            ap.setMedicamentospermanentes1(textMedicamentosPermanentes2.getText());
            ap.setMedicamentospermanentes2(textMedicamentosPermanentes2.getText());
            ap.setMedicamentospermanentes3(textMedicamentosPermanentes3.getText());
            ap.setMedicamentospermanentes4(textMedicamentosPermanentes4.getText());
            ap.setMedicamentospermanentes5(textMedicamentosPermanentes5.getText());
            ap.setOtrassustancias1(textOtrasSustancias1.getText());
            ap.setOtrassustancias2(textOtrasSustancias2.getText());
            ap.setOtrassustancias3(textOtrasSustancias3.getText());
            ap.setDiabetes(textDiabetes.getSelectionModel().getSelectedItem().toString());
            ap.setHipertension(textHipertension.getSelectionModel().getSelectedItem().toString());
            ap.setInfartos(textInfartos.getValue().toString());
            ap.setFumadias(textFumaDias.getValue().toString());
            ap.setConviveconfumadores(textConviveConFumadores.getSelectionModel().getSelectedItem().toString());
            ap.setActividadfisica(textActividadFisicaMinutos.getSelectionModel().getSelectedItem().toString());
            ap.setCosumelicor(textCosumeLicor.getValue().toString());
            em = Persistence.createEntityManagerFactory("PipecPU").createEntityManager();
            em.getTransaction().begin();
            if (opcionNuevo) {
                em.persist(ap);
            } else {
                em.merge(ap);
            }
            em.getTransaction().commit();
            // *************************************************
            // *** Antecedente familiares
            // *************************************************
            Antecedentesfamiliares af = new Antecedentesfamiliares();
            AntecedentesfamiliaresPK afpk = new AntecedentesfamiliaresPK(cboxTipoIdentificacion.getValue().toString().substring(0, 2), textIdentificacion1.getText());
            af.setAntecedentesfamiliaresPK(afpk);
            af.setDiabetes(Integer.parseInt(familiaresDiabetes.getValue().toString()));
            af.setHipertension(Integer.parseInt(familiaresHipertension.getValue().toString()));
            af.setInfartos(Integer.parseInt(familiaresInfartos.getValue().toString()));
            af.setAcv(Integer.parseInt(familiaresACV.getValue().toString()));
            em = Persistence.createEntityManagerFactory("PipecPU").createEntityManager();
            em.getTransaction().begin();
            if (opcionNuevo) {
                em.persist(af);
            } else {
                em.merge(af);
            }
            em.getTransaction().commit();
            programaPrincipal.setPaciente(p);
            this.paciente= p;
            llenarPaciente();
            if (opcionNuevo) {
                programaPrincipal.mensajePersonalizado("El paciente con " + p.getPacientesPK().getTipoid() + " N° " + p.getPacientesPK().getIdentificacion() + " se ha creado con éxito", "Nuevo paciente creado");
            } else {
                programaPrincipal.mensajePersonalizado("El paciente con " + p.getPacientesPK().getTipoid() + " N° " + p.getPacientesPK().getIdentificacion() + " ha sido actualizado con éxito", "Paciente actualizado");
            }
    }
    
    //Registro del usuario para un paciente con su id y pass con la identificacion
    public void registrarUsuario()
    {        
        EntityManager em = Persistence.createEntityManagerFactory("PipecPU").createEntityManager(); 
        em.getTransaction().begin(); 
        
        Usuarios usuario= new Usuarios();
        usuario.setUsuario(textIdentificacion1.getText());
        usuario.setIdusuario(textIdentificacion1.getText());
        usuario.setRol("Paciente");
        usuario.setNombre(textNombre1.getText()+ " " + textNombre2.getText());
        usuario.setTelefono(textCelular.getText());
        usuario.setContrasena(encriptar(textIdentificacion1.getText()));
        usuario.setBloqueado("0");
        
        //Se obtiene la fecha actual
        Date date= new Date();
        try {                        
            date =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
        } catch (ParseException ex) {
            Logger.getLogger(NuevoUsuarioController.class.getName()).log(Level.SEVERE, null, ex);
        }
        java.sql.Timestamp fechaGuardar = new java.sql.Timestamp(date.getTime());   
        //--------------------------
        usuario.setFechaIngreso(fechaGuardar);
        usuario.setUltimaModificacion(fechaGuardar);
        em.persist(usuario);
        em.getTransaction().commit();
        
    }
    
    private static Cipher cipher; 
    private static final String algoritmo = "AES";
    private static final int keysize = 16; 
    private static SecretKey key;  
    
    public String encriptar(String palabra) {
        String resultado = "";
        try {
            cipher = Cipher.getInstance( algoritmo );             
            cipher.init( Cipher.ENCRYPT_MODE, key );             
            byte[] textobytes = palabra.getBytes();
            byte[] cipherbytes = cipher.doFinal( textobytes );
            resultado = DatatypeConverter.printBase64Binary(cipherbytes);
            
        } catch (NoSuchAlgorithmException ex) {
            System.err.println( ex.getMessage() );
        } catch (NoSuchPaddingException ex) {
            System.err.println( ex.getMessage() );
        } catch (InvalidKeyException ex) {
            System.err.println( ex.getMessage() );
        } catch (IllegalBlockSizeException ex) {
            System.err.println( ex.getMessage() );
        } catch (BadPaddingException ex) {
            System.err.println( ex.getMessage() );
        }
        return resultado;
    }
    
    private boolean existePaciente()
    {
        boolean resultado= false;
        EntityManager em = Persistence.createEntityManagerFactory("PipecPU").createEntityManager();
        em.getTransaction().begin();
        List<Pacientes> list = em.createNamedQuery("Pacientes.findAll", Pacientes.class).getResultList();
        for (int i = 0; i < list.size(); i++) {
            Pacientes obj = list.get(i);
            String id = textIdentificacion1.getText();
            String tipoId= cboxTipoIdentificacion.getValue().toString().substring(0, 2);
            if (id.equals(obj.getPacientesPK().getIdentificacion()) && tipoId.equals(obj.getPacientesPK().getTipoid())) {
                resultado= true;
                break;
            }
        }
        return resultado;
    }
    
    @FXML
    public void abrirFarmacias()
    {
        programaPrincipal.cargarBusquedaFarmacia();
    }
    
    @FXML
    public void abrirEspecialistas()
    {
        programaPrincipal.cargarBusquedaEspecialistas();
    }
    
    @FXML
    public void abrirCentrosMedicos()
    {
        programaPrincipal.cargarBusquedaCentroMedico();
    }
    
    @FXML
    public void abrirAdminSesion()
    {
        programaPrincipal.cargarAdminSesion();
    }
    
    //Método para dejar los datos listos para escribir en la Smartcard
    public String obtenerDatos()
    {
        String resultado="";
        String identificacion= paciente.getPacientesPK().getIdentificacion();
        EntityManager em = Persistence.createEntityManagerFactory("PipecPU").createEntityManager();
        
        //Consulta del paciente
        Query queryPacienteFindOne = em.createNativeQuery("SELECT * from pacientes p WHERE identificacion= '" + identificacion +"'", Pacientes.class);
        List<Pacientes> listPaciente = queryPacienteFindOne.getResultList();
        Pacientes paciente= listPaciente.get(0);
        
        //Consulta de los antecedentes personales
        
        Query queryPersonalesFindOne = em.createNativeQuery("SELECT * from antecedentespersonales a WHERE identificacion= '" + identificacion +"'", Antecedentespersonales.class);
        List<Antecedentespersonales> listPersonales = queryPersonalesFindOne.getResultList();
        Antecedentespersonales personales= listPersonales.get(0);
        
        //Consulta de los antecedentes familiares
        Query queryFamiliaresFindOne = em.createNativeQuery("SELECT * from antecedentesfamiliares a WHERE identificacion= '" + identificacion +"'", Antecedentesfamiliares.class);
        List<Antecedentesfamiliares> listFamiliares = queryFamiliaresFindOne.getResultList();
        Antecedentesfamiliares familiares= listFamiliares.get(0);
        
        //Toma de datos de paciente
        String tipoId= paciente.getPacientesPK().getTipoid() + ";";
        String id= paciente.getPacientesPK().getIdentificacion() + ";";
        String nombre= paciente.getNombre1() + " " + paciente.getNombre2() + ";";
        String apellido= paciente.getApellido1() + " " + paciente.getApellido2() + ";";
        String administradora= paciente.getAdministradora() + ";";
        String estatura= paciente.getEstatura() + ";";
        
        //Toma de datos de antecedentes personales
        String medicamentos1= personales.getMedicamentospermanentes1() + ";";
        String medicamentos2= personales.getMedicamentospermanentes2() + ";";
        String medicamentos3= personales.getMedicamentospermanentes3() + ";";
        String medicamentos4= personales.getMedicamentospermanentes4() + ";";
        String medicamentos5= personales.getMedicamentospermanentes5() + ";";
        String diabetes= personales.getDiabetes() + ";";
        String hipertension= personales.getHipertension() + ";";
        String infartos= personales.getInfartos()+ ";";
        String fuma= personales.getFumadias() + ";";
        String conviveFumadores= personales.getConviveconfumadores() + ";";
        String actividadFisica= personales.getActividadfisica() + ";";
        String consumeLicor= personales.getCosumelicor() + ";";
        
        //Toma de datos de antecedentes familiares
        String familiaresDiabetes= familiares.getDiabetes() + ";";
        String familiaresHipertension= familiares.getHipertension() + ";";
        String accidentes= familiares.getAcv() + ";";
        
        resultado= tipoId + id+ nombre + apellido + administradora+ "contactoFamiliar(pendiente);" + estatura + 
                medicamentos1 + medicamentos2 + medicamentos3 + medicamentos4 + medicamentos5 + diabetes + hipertension + infartos + fuma + conviveFumadores +
                actividadFisica + consumeLicor + familiaresDiabetes + familiaresHipertension + accidentes + "|";                
        
        return resultado;
    }
    
    @FXML
    public void iniciarDesdeTarjeta()
    {
        String datosTarjeta= leerTarjeta();
        if(!datosTarjeta.equals("Error"))
        {
            StringTokenizer tokenizer= new StringTokenizer(datosTarjeta, ";");
            tokenizer.nextToken();
            String identificacion= tokenizer.nextToken();
        
            EntityManager em = Persistence.createEntityManagerFactory("PipecPU").createEntityManager(); 
            
            Query queryPaciente= em.createNamedQuery("Pacientes.findByIdentificacion", Pacientes.class);
            queryPaciente.setParameter("identificacion", identificacion);
            Pacientes paciente= (Pacientes) queryPaciente.getSingleResult();        
            programaPrincipal.setPaciente(paciente);  
            programaPrincipal.cargarPaciente();
                    
        }else
        {
            programaPrincipal.mensajePersonalizado("Error al leer datos de la tarjeta, inténtelo de nuevo", "Error de lectura");
        }
    }
    
    public void escribirEnTarjeta()
    {
        if(paciente!=null)
        {
            SmartCard smartCard= new SmartCard();        
        try 
        {       
            smartCard.writeOnCard(obtenerDatos());            
            } catch (Exception ex) {
                programaPrincipal.mensajePersonalizado("Error al escribir datos de la tarjeta, inténtelo de nuevo", "Error de escritura");
            }
        }else
        {
            programaPrincipal.mensajePersonalizado("No hay un paciente cargado", "Error de escritura");
        }
        
    }
    
    public void actualizarTarjeta()
    {
        if(paciente!=null)
        {
            SmartCard smartCard= new SmartCard();        
            try 
            {       
                smartCard.updateCard(obtenerDatos());            
            } catch (Exception ex) {
                programaPrincipal.mensajePersonalizado("Error al escribir datos de la tarjeta, inténtelo de nuevo", "Error de lectura");
            }            
        }else
        {
            programaPrincipal.mensajePersonalizado("No hay un paciente cargado", "Error de escritura");
        }
    }
    
    public void actualizarFotoPacente(ImageView foto)
    {
        if(foto!=null)currentSectionImage.setImage(foto.getImage());
        
    }

    @FXML
    private void abrirMedicamentos(ActionEvent event) {
    }
    
    public void abrirCamara()
    {
        programaPrincipal.mostrarVentanaFoto(this);
    }
    
    @FXML
    public void abrirMedicionesAnteriores()
    {
        programaPrincipal.cargarVentanaMultiOnda();
    }
    
    
}
