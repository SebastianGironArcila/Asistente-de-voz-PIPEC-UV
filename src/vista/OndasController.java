/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import BD.Medicion;
import BD.MedicionPersonalizada;
import BD.Pacientes;
import cliente.AdminDevice;
import controlador.CapturaHuella;
import controlador.ZoomManager;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;

import AsistenteVirtual_v1.SpeechToText_v1;
import smartcard.SmartCard;

/**
 * FXML Controller class
 *
 * @author Miguel Askar
 */
public class OndasController extends EstructuraBasicaController implements Initializable {

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
    private Button btnIniciarSenales;
    @FXML
    private Button iniciarPersonalizada;
    @FXML
    private ChoiceBox<String> intervalo;
    @FXML
    private ChoiceBox<String> duracionMuestra;
    @FXML
    private ChoiceBox<String> duracionExamen;
    @FXML
    private TextArea detallesMedicion;
    @FXML
    private ChoiceBox<String> choiceIntervaloSubmuestreo;
    private Button cargarMedicionesAnteriores;
    @FXML
    private Text hRECG;
    @FXML
    private Text spO2;
    @FXML
    private Text hRSpO2;

    /**
     * Initializes the controller class.
     */
    
    private AdminDevice admin;
    private QueueParametros queueParam;
    private AddToQueue addToQueue;
    private static ExecutorService executor= Executors.newFixedThreadPool(2);
    
    private boolean almacenarMedicion= false;
    
    //Variables necesarias para el control de la reproducción
    private int xSeriesData_spo2 = 0;
    private int xSeriesData_ecg1 = 0;
    private int xSeriesData_ecg2 = 0;
    private int xSeriesData_resp = 0;
    
    private static final int MAX_DATA_POINTS_SPO2 = 500;
    private static final int MAX_DATA_POINTS_ECG = 500;
    private static final int MAX_DATA_POINTS_RESP = 500;
    private static final int Y_MAX_SPO2 = 256;
    private static final int Y_MAX_ECG = 3000;
    private static final int Y_MAX_RESP = 500;
    
    //vectores para guardar datos de la medicion
    Vector<Integer> vSPO2 = new Vector(0,1);
    Vector<Integer> vECG1 = new Vector(0,1);
    Vector<Integer> vECG2 = new Vector(0,1);
    Vector<Integer> vRESP = new Vector(0,1);
    Vector<Integer> vsistolica = new Vector(0,1);
    Vector<Integer> vdiastolica = new Vector(0,1);
    Vector<Integer> vpulso = new Vector(0,1);
    Vector<Integer> vmed = new Vector(0,1);
    Vector<Integer> vECG = new Vector(0,1);
    Vector<Integer> vSPO2text = new Vector(0,1);
    Vector<Integer> vHR = new Vector(0,1);
    Vector<Integer> vRESPtext = new Vector(0,1);
    
    Vector<Integer> SubmuestreoSistolica = new Vector(0,1);
    Vector<Integer> SubmuestreoDiastolica = new Vector(0,1);
    Vector<Integer> SubmuestreoPulso = new Vector(0,1);
    Vector<Integer> SubmuestreoMed = new Vector(0,1);
    Vector<Integer> SubmuestreoECG = new Vector(0,1);
    Vector<Integer> SubmuestreoSPO2text = new Vector(0,1);
    Vector<Integer> SubmuestreoHR = new Vector(0,1);
    Vector<Integer> SubmuestreoRESPtext = new Vector(0,1);    
    Vector<Integer> Submuestreominutos = new Vector(0,1);
    
    //Colas para recibir los datos de la graficación
    private ConcurrentLinkedQueue<Number> dataECG1 = new ConcurrentLinkedQueue<Number>();
    private ConcurrentLinkedQueue<Number> dataECG2 = new ConcurrentLinkedQueue<Number>();
    private ConcurrentLinkedQueue<Number> dataSPO2 = new ConcurrentLinkedQueue<Number>();
    private ConcurrentLinkedQueue<Number> dataRESP = new ConcurrentLinkedQueue<Number>();
    
    //Colas para guardar los datos de graficación
    private XYChart.Series<Number, Number> series1;
    private XYChart.Series<Number, Number> series2;
    private XYChart.Series<Number, Number> series3;
    private XYChart.Series<Number, Number> series4;
    
    
    @FXML
    LineChart<Number, Number> lc4;
    @FXML
    private NumberAxis xAxis_4;
    @FXML
    private NumberAxis yAxis_4;
    @FXML
    LineChart<Number, Number> lc1;
    @FXML
    private NumberAxis xAxis;
    @FXML
    private NumberAxis yAxis;
    @FXML
    LineChart<Number, Number> lc2;
    @FXML
    private NumberAxis xAxis_2;
    @FXML
    private NumberAxis yAxis_2;
    @FXML
    LineChart<Number, Number> lc3;
    @FXML
    private NumberAxis xAxis_3;
    @FXML
    private NumberAxis yAxis_3;
    @FXML
    private Text presionMedia;
    @FXML
    private Text pulso;
    @FXML
    private Text rESPFreq;
    @FXML
    private Button buscarPaciente;
    @FXML
    private Button buttonVariabilidad;
    @FXML
    private Button buttonWearables;
    @FXML
    private Text grasaCorporal;
    @FXML
    private Text porcentajeDeAgua;
    @FXML
    private Text masaMuscular;
    @FXML
    private Text iMC;
    @FXML
    private Button peso;
    @FXML
    private Text presionLabel;
    @FXML
    private Text pesoLabel;
    
    
    
    private Button zoom;
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
    @FXML
    private Text textMedicionPersonalizada;
    @FXML
    private Text textMedicionSimple;
        
    private SpeechToText_v1 stt;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        this.inicializarBotonesEstructura();
        parametrosDesplegables();
        inicializarImagenesOndas();
        initChartSignals();
        
        final double SCALE_DELTA = 1.1;       
        
    }    
    
        
    public void inicializarImagenesOndas()
    {
        /*
        //Imagen botón de play
        Image        selected    = new Image(
          "\\images\\iconosOndas\\play.png"
        );
        ImageView    imagenFondo = new ImageView();
        imagenFondo.setImage(selected);
        play.setGraphic(imagenFondo);    
        play.setBackground(Background.EMPTY);
        
        //Imagen botón de pausa
        selected    = new Image(
          "\\images\\iconosOndas\\pausa.png"
        );
        imagenFondo = new ImageView();
        imagenFondo.setImage(selected);
        pause.setGraphic(imagenFondo);    
        pause.setBackground(Background.EMPTY);
        
        //Imagen botón de stop
        selected    = new Image(
          "\\images\\iconosOndas\\stop.png"
        );
        imagenFondo = new ImageView();
        imagenFondo.setImage(selected);
        stop.setGraphic(imagenFondo);    
        stop.setBackground(Background.EMPTY);*/
        
        
        /*
        //Imagen botón de carga de mediciones anteriores
        Image selected    = new Image
        (
          "\\images\\iconosOndas\\botonCargarMediciones.png"
        );
        ImageView imagenFondo = new ImageView();
        imagenFondo.setImage(selected);
        cargarMedicionesAnteriores.setGraphic(imagenFondo);    
        cargarMedicionesAnteriores.setBackground(Background.EMPTY);
        //cargarMedicionesAnteriores.setStyle("-fx-background-image: url('/images/iconosOndas/botonCargarMediciones.png')");
        //cargarMedicionesAnteriores.setBackground(Background.EMPTY);
        /*
        //Imagen botón de carga de mediciones simples
        btnIniciarSenales.setStyle("-fx-background-image: url('/images/iconosOndas/botonIniciarSimple.png')");
        btnIniciarSenales.setBackground(Background.EMPTY);
        
        //Imagen botón de carga de mediciones personalizadas
        iniciarPersonalizada.setStyle("-fx-background-image: url('/images/iconosOndas/botonIniciarSimple.png')");
        iniciarPersonalizada.setBackground(Background.EMPTY);
        */
        
        //Imagen botón de carga de presión arterial
        //tomarPresionOndas.setStyle("-fx-background-image: url('/images/iconosOndas/botonPresion.png')");
        tomarPresionOndas.setBackground(Background.EMPTY);
        
        //Imagen botón de medición simple
        Image selected    = new Image(
          "\\images\\iconosOndas\\botonIniciarSimple.png"
        );
        ImageView imagenFondo = new ImageView();
        imagenFondo.setImage(selected);
        btnIniciarSenales.setGraphic(imagenFondo);    
        btnIniciarSenales.setBackground(Background.EMPTY);
        btnIniciarSenales.setPadding(Insets.EMPTY);
        
        //Imagen botón de medición personalizada
        selected    = new Image(
          "\\images\\iconosOndas\\botonIniciarSimple.png"
        );
        imagenFondo = new ImageView();
        imagenFondo.setImage(selected);
        iniciarPersonalizada.setGraphic(imagenFondo);    
        iniciarPersonalizada.setBackground(Background.EMPTY);
        iniciarPersonalizada.setPadding(Insets.EMPTY);
        
        //Imagen botón de tomar peso
        selected    = new Image(
          "\\images\\botones\\peso.png"
        );
        imagenFondo = new ImageView();
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
    }
    
 
    
    
    public void setDetalles(String detalles) {
    	this.detallesMedicion.setText(detalles);
    }
    public void setIntervalo(String inter) {
    	if(inter.equalsIgnoreCase("5")) {
    		this.intervalo.getSelectionModel().select(0);
    	}else if(inter.equalsIgnoreCase("10")) {
    		this.intervalo.getSelectionModel().select(1);
    	}else if(inter.equalsIgnoreCase("15")) {
    		this.intervalo.getSelectionModel().select(2);
    	}else if(inter.equalsIgnoreCase("20")) {
    		this.intervalo.getSelectionModel().select(3);
    	}else if(inter.equalsIgnoreCase("25")) {
    		this.intervalo.getSelectionModel().select(4);
    	}else if(inter.equalsIgnoreCase("30")) {
    		this.intervalo.getSelectionModel().select(5);
    	}
    }
    
    public void setDuracionMuestra(String duracion) {
    	if(duracion.equalsIgnoreCase("15")) {
    		this.duracionMuestra.getSelectionModel().select(0);
    	}else if(duracion.equalsIgnoreCase("30")) {
    		this.duracionMuestra.getSelectionModel().select(1);
    		
    	}else if(duracion.equalsIgnoreCase("45")) {
    		this.duracionMuestra.getSelectionModel().select(2);
    	}else if(duracion.equalsIgnoreCase("60")) {
    		this.duracionMuestra.getSelectionModel().select(3);
    	}
    }
    
    public void setDuracionExamen(String duracion) {
    	if(duracion.equalsIgnoreCase("10")) {
    		this.duracionExamen.getSelectionModel().select(0);
    	}else if(duracion.equalsIgnoreCase("20")) {
    		this.duracionExamen.getSelectionModel().select(1);
    	}else if(duracion.equalsIgnoreCase("30")) {
    		this.duracionExamen.getSelectionModel().select(2);
    	}else if(duracion.equalsIgnoreCase("1")) {
    		this.duracionExamen.getSelectionModel().select(3);
    	}else if(duracion.equalsIgnoreCase("130")) {
    		this.duracionExamen.getSelectionModel().select(4);
    	}else if(duracion.equalsIgnoreCase("2")) {
    		this.duracionExamen.getSelectionModel().select(5);
    	}
    }
    
    public void setSubmuestreo(String subms) {
    	if(subms.equalsIgnoreCase("10")) {
    		this.choiceIntervaloSubmuestreo.getSelectionModel().select(0);
    	}else if(subms.equalsIgnoreCase("20")) {
    		this.choiceIntervaloSubmuestreo.getSelectionModel().select(1);
    	}else if(subms.equalsIgnoreCase("30")) {
    		this.choiceIntervaloSubmuestreo.getSelectionModel().select(2);
    	}else if(subms.equalsIgnoreCase("40")) {
    		this.choiceIntervaloSubmuestreo.getSelectionModel().select(3);
    	}else if(subms.equalsIgnoreCase("50")) {
    		this.choiceIntervaloSubmuestreo.getSelectionModel().select(4);
    	}else if(subms.equalsIgnoreCase("60")) {
    		this.choiceIntervaloSubmuestreo.getSelectionModel().select(5);
    	}
    }
    
    
    public void setSpeechToText(SpeechToText_v1 stt){
        this.stt = stt;
    }
    
     
    
    @FXML
    public void irAOndas()
    {
        mostrarMensaje("Ya te encuentras en esta sección");        
    } 

    public AdminDevice getAdmin() {
        return admin;
    }

    public void setAdmin(AdminDevice admin) {
        this.admin = admin;
    }
    
    public void iniciarLecturaSenales() 
    {   
        /*timerIniciar = new Timer();
        admin.switchLectura(); //Se habilita el hilo de lectura.
        TimerTask taskIniciar = new TimerTask() 
        {

            @Override
            public void run()
            {
                hiloPrimeraVez();
            }
        }; 
        // Empezamos dentro de 0s 
        timerIniciar.schedule(taskIniciar, 0);*/
        admin.switchLectura(); //Se habilita el hilo de lectura.

        

        addToQueue = new AddToQueue();
        queueParam= new QueueParametros();

        //addToQueue.run();
        //queueParam.run(); //Mirar si esto soluciona el error de concurrencia.

        executor.execute(addToQueue);
        executor.execute(queueParam);
        prepareTimeline();
        //btnIniciarSenales.setText("Parar"); 
    }
    
    private void prepareTimeline() 
    {
        // Every frame to take any data from queue and add to chart
        new AnimationTimer() {
            @Override public void handle(long now) {
                addDataToSeries();
                addDataToSeries_2();
                addDataToSeries_3();
                addDataToSeries_4();
            }
        }.start();
    }
    
    private void addDataToSeries() {
        for (int i = 0; i < 20; i++) { 
        //-- add 20 numbers to the plot+
            if (dataSPO2.isEmpty()) break;
            series1.getData().add(new XYChart.Data<>(xSeriesData_spo2++, dataSPO2.remove()));
        }
        // remove points to keep us at no more than MAX_DATA_POINTS
        if (series1.getData().size() > MAX_DATA_POINTS_SPO2) {
            series1.getData().remove(0, series1.getData().size() - MAX_DATA_POINTS_SPO2);
        }
        // update 
        xAxis.setLowerBound(xSeriesData_spo2-MAX_DATA_POINTS_SPO2);
        xAxis.setUpperBound(xSeriesData_spo2-1);
    }
    //Saca los datos de las queue y los agrega al chart ecg1
    private void addDataToSeries_2() {
        for (int i = 0; i < 20; i++) { //-- add 20 numbers to the plot+
            if (dataECG1.isEmpty()) break;
            series2.getData().add(new XYChart.Data<>(xSeriesData_ecg1++, dataECG1.remove()));
        }
        // remove points to keep us at no more than MAX_DATA_POINTS
        if (series2.getData().size() > MAX_DATA_POINTS_ECG) {
            series2.getData().remove(0, series2.getData().size() - MAX_DATA_POINTS_ECG);
        }
        // update 
        xAxis_2.setLowerBound(xSeriesData_ecg1-MAX_DATA_POINTS_ECG);
        xAxis_2.setUpperBound(xSeriesData_ecg1-1);
    }
    //Saca los datos de las queue y los agrega al chart ecg2
    private void addDataToSeries_3() {
        for (int i = 0; i < 20; i++) { //-- add 20 numbers to the plot+
            if (dataECG2.isEmpty()) break;
            series3.getData().add(new XYChart.Data<>(xSeriesData_ecg2++, dataECG2.remove()));

        }
        // remove points to keep us at no more than MAX_DATA_POINTS
        if (series3.getData().size() > MAX_DATA_POINTS_ECG) {
            series3.getData().remove(0, series3.getData().size() - MAX_DATA_POINTS_ECG);
        }
        // update 
        xAxis_3.setLowerBound(xSeriesData_ecg2-MAX_DATA_POINTS_ECG);
        xAxis_3.setUpperBound(xSeriesData_ecg2-1);
    }
    //Saca los datos de las queue y los agrega al chart resp
    private void addDataToSeries_4() {
        for (int i = 0; i < 20; i++) { //-- add 20 numbers to the plot+
            if (dataRESP.isEmpty()) break;
            series4.getData().add(new XYChart.Data<>(xSeriesData_resp++, dataRESP.remove()));

        }
        // remove points to keep us at no more than MAX_DATA_POINTS
        if (series4.getData().size() > MAX_DATA_POINTS_RESP) {
            series4.getData().remove(0, series4.getData().size() - MAX_DATA_POINTS_RESP);
        }
        // update 
        xAxis_4.setLowerBound(xSeriesData_resp-MAX_DATA_POINTS_RESP);
        xAxis_4.setUpperBound(xSeriesData_resp-1);
    }
    
    public void initChartSignals(){
    
        xAxis.setForceZeroInRange(false);
        xAxis.setAutoRanging(false);
        xAxis.setLowerBound(0);
        xAxis.setUpperBound(MAX_DATA_POINTS_SPO2);
        //xAxis.setUpperBound(MAX_DATA_POINTS_ECG);
        xAxis.setTickUnit(MAX_DATA_POINTS_SPO2/10);
        //xAxis.setTickUnit(MAX_DATA_POINTS_ECG/10);
     
        xAxis_2.setForceZeroInRange(false);
        xAxis_2.setAutoRanging(false);
        xAxis_2.setLowerBound(0);
        xAxis_2.setUpperBound(MAX_DATA_POINTS_ECG);
        xAxis_2.setTickUnit(MAX_DATA_POINTS_ECG/10);
        
        xAxis_3.setForceZeroInRange(false);
        xAxis_3.setAutoRanging(false);
        xAxis_3.setLowerBound(0);
        xAxis_3.setUpperBound(MAX_DATA_POINTS_ECG);
        xAxis_3.setTickUnit(MAX_DATA_POINTS_ECG/10);
        
        xAxis_4.setForceZeroInRange(false);
        xAxis_4.setAutoRanging(false);
        xAxis_4.setLowerBound(0);
        xAxis_4.setUpperBound(MAX_DATA_POINTS_RESP);
        xAxis_4.setTickUnit(MAX_DATA_POINTS_RESP/10);
        //numero de divisiones en eje Y
        yAxis.setAutoRanging(false);
        yAxis.setLowerBound(0);
        yAxis.setUpperBound(Y_MAX_SPO2);
        yAxis.setTickUnit(Y_MAX_SPO2/10);
        
        yAxis_2.setAutoRanging(false);
        yAxis_2.setLowerBound(0);
        yAxis_2.setUpperBound(Y_MAX_ECG);
        yAxis_2.setTickUnit(Y_MAX_ECG/10);
        
        yAxis_3.setAutoRanging(false);
        yAxis_3.setLowerBound(0);
        yAxis_3.setUpperBound(Y_MAX_ECG);
        yAxis_3.setTickUnit(Y_MAX_ECG/10);
        
        yAxis_4.setAutoRanging(false);
        yAxis_4.setLowerBound(0);
        yAxis_4.setUpperBound(Y_MAX_RESP);
        yAxis_4.setTickUnit(Y_MAX_RESP/10);      
        
        
        /*lc1.getYAxis().setVisible(false);
        lc1.getYAxis().setOpacity(0);
        lc1.getXAxis().setVisible(false);*/
        //lc1.getXAxis().setOpacity(0);
        lc2.getYAxis().setVisible(false);
        lc2.getYAxis().setOpacity(0);
        lc2.getXAxis().setVisible(false);
        lc2.getXAxis().setOpacity(0);
        lc3.getYAxis().setVisible(false);
        lc3.getYAxis().setOpacity(0);
        lc3.getXAxis().setVisible(false);
        lc3.getXAxis().setOpacity(0);
        lc4.getYAxis().setVisible(false);
        lc4.getYAxis().setOpacity(0);
        lc4.getXAxis().setVisible(false);
        lc4.getXAxis().setOpacity(0);
        
        
        lc1.setAnimated(false);
        //lc1.setId("ondaSPO2");
        //lc1.setTitle("Onda SPO2");
        
        lc2.setAnimated(false);
        //lc2.setId("ondaECG1");
        //lc2.setTitle("Onda ECG CH1");
        
        /*lc2.setBorder(new Border(new BorderStroke(Color.BLACK, 
                BorderStrokeStyle.DASHED, CornerRadii.EMPTY, BorderWidths.DEFAULT)));*/
        
        lc3.setAnimated(false);
        //lc3.setId("ondaECG2");
        //lc3.setTitle("Onda ECG CH2");    
        
        lc4.setAnimated(false);
        //lc4.setId("ondaRESP");
        //lc4.setTitle("Onda RESP");

        //-- Chart Series
        series1= new XYChart.Series<>();
        series2= new XYChart.Series<>();
        series3= new XYChart.Series<>();
        series4= new XYChart.Series<>();
        //series = new AreaChart.Series<Number, Number>();
        series1.setName("Spo2");
        lc1.getData().add(series1);      
        
        
        series2.setName("ECG1");
        lc2.getData().add(series2);
        
        series3.setName("ECG2");
        lc3.getData().add(series3);
        
        series4.setName("RESP");
        lc4.getData().add(series4);

    }
    
    public void asignarStaticParameters(int hr, int respRate, int spo2Oxi, int spo2Hr, int presRate, int presDias, int presMed, int presSist) 
        {       
        
        //Se guardan los datos en el vector para luego ser almacenados en BD 
        if(almacenarMedicion)
        {
            vsistolica.add(presSist);
            vdiastolica.add(presDias);
            vpulso.add(presRate);
            vmed.add(presMed);
            vSPO2text.add(spo2Oxi);
            vHR.add(spo2Hr);
            vRESPtext.add(respRate);
            vECG.add(hr);
        }
        
        
        Platform.runLater(new Runnable(){
                        @Override
                        public void run() { 
                            if(respRate!=65436)
                            {
                               rESPFreq.setText(String.valueOf(respRate)); 
                            }else
                            {
                                rESPFreq.setText("---");
                            }
                            if(hr!=65436)
                            {
                               hRECG.setText(String.valueOf(hr)); 
                            }else
                            {
                                hRECG.setText("---");
                            }
                            if(spo2Hr!=65436)
                            {
                               hRSpO2.setText(String.valueOf(spo2Hr));
                            }else
                            {
                                hRSpO2.setText("---");
                            }
                            if(spo2Oxi<=100)
                            {
                               spO2.setText(String.valueOf(spo2Oxi) + "%"); 
                            }else
                            {
                                spO2.setText("---");
                            }
                                   
                            
                            
                            
                        }
                    }); 
        
        
        //Se pintan los datos    
        //pintarHR(hr);
        //pintarSPO2(spo2Oxi, spo2Hr);
        //pintarRespiracion(respRate);
        //pintarPresion(presRate, presDias, presMed, presSist);  
    } 

   
    //Clases internas para el manejo de las ondas
    
    private class AddToQueue implements Runnable {
        public void run() {
            try {

                if (!admin.ecg1Signal.isEmpty()){
                    int auxEcg1= admin.ecg1Signal.readWave();
                    if(almacenarMedicion) vECG1.add(auxEcg1);
                     dataECG1.add(auxEcg1);
                    // System.out.println(ecg1); 
                 } else{
                     //monitor.getData();
                     
                  }
                 if (!admin.ecg2Signal.isEmpty()){
                     int auxEcg2= admin.ecg2Signal.readWave();
                     if(almacenarMedicion) vECG2.add(auxEcg2);
                     dataECG2.add(auxEcg2);
                 } else{
                     //monitor.getData();
                 }
                 if (!admin.respSignal.isEmpty()){
                     int auxResp= admin.respSignal.readWave();
                     if(almacenarMedicion) vRESP.add(auxResp);
                     dataRESP.add(auxResp);
                 }  else{
                     //monitor.getData();
                 }
                  if (!admin.spo2Signal.isEmpty()){
                     //Con la ayuda de esta sección se guardarán los datos mostrados en pantalla dentro de los vectores.
                      
                     int auxSpo2= admin.spo2Signal.readWave();
                     if(almacenarMedicion) vSPO2.add(auxSpo2);
                     dataSPO2.add(auxSpo2);
                     
                 }else{
                      //monitor.getData();
                      
                  }
                       Thread.sleep(7);                
             
              executor.execute(this);             
                
            } catch (InterruptedException ex) {
                //Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Excepción controlada, ejecución terminada durante un Sleep");
            }
        }
    }
    
    private class QueueParametros implements Runnable 
    {
        public void run() {
        try {
            asignarStaticParameters(admin.staticParameters.readHr(),
                    + admin.staticParameters.readResp(),
                    + admin.staticParameters.readSpo2Oxi(),
                    + admin.staticParameters.readSpo2Hr(),
                    + admin.staticParameters.readPresR(),
                    + admin.staticParameters.readPresDias(),
                    + admin.staticParameters.readPresMed(),
                    + admin.staticParameters.readPresSist());
                    
            Thread.sleep(500);
            executor.execute(this);
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }
    }
    
    public void tomarPresion()
    {
        programaPrincipal.tomarPresion();              
    }

    public void actualizarPreion(int sist, int dias)
    {
        tomarPresionOndas.setText(sist + "/" + dias);
    }
    
    @FXML
    public void medicionSimple()
    {
        if(btnIniciarSenales.getText().equals("Start simple measure"))
        {
            //Código para iniciar medición
            btnIniciarSenales.setText("Stop simple measure");
            almacenarMedicion= true;
            //Se crea el método que almacenará las señales dentro de un minuto.
             
            Timer timerMedicionUnMinuto;
            timerMedicionUnMinuto = new Timer();             
            TimerTask taskIniciar = new TimerTask() 
            {   
                @Override
                public void run()
                {
                    almacenarSenales();
                    almacenarMedicion= false;
                    
                    Platform.runLater(new Runnable(){
                        @Override
                        public void run() { 
                            btnIniciarSenales.setText("Start simple measure"); 
                            programaPrincipal.mensajePersonalizado("La medición simple ha terminado con éxito", "Medición simple terminada");
                        }
                    });                    
                }
            }; 
            // Empezamos dentro de 60s 
            timerMedicionUnMinuto.schedule(taskIniciar, 60000);
        }else
        {
            
        }
    }
    
    private static boolean cancelarMedicionPersonalizada= false; //Bandera para saber cuándo la función debe cancelar el proceso.
    private static boolean terminaMedicion= false; //Bandera que controla que no se ejecuten más registros de los necesarios.
    //Timerse necesarios
    private static Timer timerCancelarGuardado;
    private static Timer timerMuestra;
    private static Timer timerMedicion;
    private static Timer TimerSubmuestreo;
    private static Timer timerSubMuestra;
    @FXML
	public void medicionPersonalizada()
    {        
        if (cancelarMedicionPersonalizada)
        {
            cancelarMedicionPersonalizada= false;
            terminaMedicion= true;            
            //Se cancelan los timers de la medición personalizada
            timerCancelarGuardado.cancel();
            timerMuestra.cancel();        
            timerMedicion.cancel();
            timerSubMuestra.cancel();
            //Se almacenan los datos que se hayan alcanzado a tomar del submuestreo
            actualizarMedicionPersonalizada();
            resetVectoresSubmuestreo();
            
            //Se informa que se ya NO se almacenará una medición personalizada
            esPersonalizada= false;
            //Se genera mensaje
            programaPrincipal.mensajePersonalizado("La medición personalizada ha sido cancelada", "Medición personalizada cancelada");

            //Se cambia el testigo de medición personalizada
            //indicador(false, "");

            //Se habilita la medición
            //enableMeasure(true);

        }else
        {
            cancelarMedicionPersonalizada= true;
            terminaMedicion= false;
            int intervalo= obtenerIntervalo()*60*1000;
            int duracionMuestra= obtenerDuracion()*1000;
            int duracionExamen= obtenerDuracionExamen()*60*1000;
            int subMuestreo= obtenerSubmuestreo()*1000;
            
            //Se crea la medición personalizada
            idMedPersonalizada= crearMedicionPersonalizada();
            //Se informa que se almacenará una medición personalizada
            esPersonalizada= true;
            
            //Testigo que advierte de medición personalizada
            //indicador(true, "Medición personalizada en curso");
            
            
            //*******************************************************            
                        
            //Control de duración de muestra.            
            timerMedicion = new Timer();             
            TimerTask taskMedicion = new TimerTask() 
            {
                @Override
                public void run()
                {                    
                    cancelarMedicionPersonalizada= false; //Se habilita que se pueda hacer nuevo medición personalizada
                    terminaMedicion= true;
                    almacenarMedicion= false; //Se desactiva el registro de la medición.
                    //Se cancelan los timers de la medición personalizada
                    timerCancelarGuardado.cancel();
                    timerMuestra.cancel(); 
                    timerSubMuestra.cancel();
                    //Se almacenan los datos del submuestreo
                    actualizarMedicionPersonalizada();
                    resetVectoresSubmuestreo();
                    
                    //Se informa que se ya NO se almacenará una medición personalizada
                    esPersonalizada= false;
                    //Se genera mensaje
                    Platform.runLater(new Runnable(){
                        @Override
                        public void run() {                    
                                //Se cambia el testigo de medición personalizada
                               //indicador(false, "");
                               programaPrincipal.mensajePersonalizado("La medición personalizada ha terminado con éxito", "Medición personalizada terminada");                               
                        }
                    });                    
                    
                    //Se habilita la medición
                    //enableMeasure(true);
                                 
                }
            };   
            //Se lanza el timer que detiene todo el examen.
            timerMedicion.schedule(taskMedicion, duracionExamen); 
            
            //Timer de la muestra
            
            timerMuestra = new Timer();             
            TimerTask taskMuestra = new TimerTask() 
            {

                @Override
                public void run() 
                {                    
                    if(!terminaMedicion) //Si la medición personalizada no ha terminado, continúe
                    {
                       //Se activa el registro de señales
                        almacenarMedicion= true;
                        //Activar testigo de muestreo
                        Platform.runLater(new Runnable(){
                            @Override
                            public void run() {                    
                                //Testigo que advierte de medición personalizada
                                //indicadorMuestreo(true, "Medición en curso \nno retire los dispositivos");
                            }
                        }); 
                    }
                }
            };   
            //Se lanza el timer que habilita el registro de datos.
            timerMuestra.schedule(taskMuestra, 10, intervalo); //Se inicia en 10 para que alcance a verificar si ya se temrinó la medición.
            
            //Timer para guardar los datos durante el submuestreo
            
            timerSubMuestra = new Timer();             
            TimerTask taskSubMuestra = new TimerTask() 
            {

                @Override
                public void run() 
                {                    
                    if(!terminaMedicion) //Si la medición personalizada no ha terminado, continúe
                    {
                       actualizarVectoresSubmuestreo(); 
                    }
                }
            };   
            //Se lanza el timer que registra los datos del submuestreo periodicamente.
            timerSubMuestra.schedule(taskSubMuestra, 10, subMuestreo); //Se inicia en 10 para que alcance a verificar si ya se temrinó la medición.
            
            //Timer para cancelar guardado            
            timerCancelarGuardado = new Timer();             
            TimerTask taskCancelar = new TimerTask() 
            {

                @Override
                public void run()
                {
                    if(!terminaMedicion) //Si la medición personalizada no ha terminado, continúe
                    {
                        almacenarMedicion= false; //Se desactiva el registro de la medición.
                    
                        //Desactivar testigo de muestreo
                        Platform.runLater(new Runnable(){
                            @Override
                            public void run() {                    
                                   //Testigo que advierte de medición personalizada
                                   //indicadorMuestreo(false, "");
                            }
                        });


                        almacenarSenales(); //Se almacenan las señales.
                    }                                        
                }
            };   
            //Se lanza el timer que cancela el guardado y almacena lo registrado.
            timerCancelarGuardado.schedule(taskCancelar, duracionMuestra, intervalo);
/*
            //Control de duración de submuestreo.            
            TimerSubmuestreo = new Timer();             
            TimerTask taskSubmuestreo = new TimerTask() 
            {

                @Override
                public void run()
                {   
                                 
                }
            };   
            //Se lanza el timer controla el submuestreo.
            TimerSubmuestreo.schedule(taskSubmuestreo, subMuestreo, subMuestreo); */
            
        }
    }
    
    private int crearMedicionPersonalizada()
    { 
        EntityManager em = Persistence.createEntityManagerFactory("PipecPU").createEntityManager();
        em.getTransaction().begin();
        int intervaloMedicion= obtenerIntervalo();
        int duracionMuestraMedicion= obtenerDuracion();
        int duracionExamenMedicion= obtenerDuracionExamen();
        int duracionSubmuestreo= obtenerSubmuestreo();
        //AquÃ­ colocas tu objeto tipo Date
        Date date= new Date();
        try {                        
            date =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
        } catch (ParseException ex) {
            Logger.getLogger(MenuPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
        java.sql.Timestamp fechaGuardar = new java.sql.Timestamp(date.getTime());
        MedicionPersonalizada med= new MedicionPersonalizada();
        med.setId(1);
        med.setIntervalo(intervaloMedicion);
        med.setDuracionExamen(duracionExamenMedicion);
        med.setDuracionMuestra(duracionMuestraMedicion);
        med.setSubintervalo(duracionSubmuestreo);
        med.setFecha(fechaGuardar);
        med.setDetalles(detallesMedicion.getText());
        med.setSistolica("");
        med.setDiastolica("");
        med.setPulso("");
        med.setECG("");
        med.setSPO2("");
        med.setHeartRate("");
        med.setRESP("");
        med.setMinutos("");
        
        em.persist(med);                
        em.getTransaction().commit();  
        
        //Obtiendo el ID para guardar cada medicion de la medición personalizada
        Query queryMedicionFindAll = em.createNativeQuery("select * from medicion_personalizada m order by m.id desc", MedicionPersonalizada.class);
                       
        List<MedicionPersonalizada> listMedicion = queryMedicionFindAll.getResultList();
        
        return listMedicion.get(0).getId();     
    }
    
    private void actualizarMedicionPersonalizada()
    {
        EntityManager em = Persistence.createEntityManagerFactory("PipecPU").createEntityManager();
        MedicionPersonalizada medicionPersonalizada= em.find(MedicionPersonalizada.class, idMedPersonalizada);
        em.getTransaction().begin();
        medicionPersonalizada.setSistolica(SubmuestreoSistolica.toString());
        medicionPersonalizada.setDiastolica(SubmuestreoDiastolica.toString());
        medicionPersonalizada.setPulso(SubmuestreoPulso.toString());
        medicionPersonalizada.setECG(SubmuestreoECG.toString());
        medicionPersonalizada.setSPO2(SubmuestreoSPO2text.toString());
        medicionPersonalizada.setHeartRate(SubmuestreoHR.toString());
        medicionPersonalizada.setRESP(SubmuestreoRESPtext.toString());
        medicionPersonalizada.setMinutos(Submuestreominutos.toString());
        em.getTransaction().commit();
        System.out.println("Se guardó registro del submuestreo");
        
    }
    
    public  void resetVectores()
    {
        //vectores para guardar datos de la medicion
        vSPO2 = new Vector(0,1);
        vECG1 = new Vector(0,1);
        vECG2 = new Vector(0,1);
        vRESP = new Vector(0,1);
        vsistolica = new Vector(0,1);
        vdiastolica = new Vector(0,1);
        vpulso = new Vector(0,1);
        vmed = new Vector(0,1);
        vECG = new Vector(0,1);
        vSPO2text = new Vector(0,1);
        vHR = new Vector(0,1);
        vRESPtext = new Vector(0,1);
    }
    
    public void resetVectoresSubmuestreo()
    {   
        SubmuestreoSistolica= new Vector(0,1);
        SubmuestreoDiastolica= new Vector(0,1);
        SubmuestreoPulso= new Vector(0,1);
        SubmuestreoMed= new Vector(0,1);
        SubmuestreoECG= new Vector(0,1);
        SubmuestreoSPO2text= new Vector(0,1);
        SubmuestreoHR= new Vector(0,1);
        SubmuestreoRESPtext= new Vector(0,1);
        contadorSubmuestreo= 1;
    }
    
    //Contador necesario para guardar los segundos del submuestreo
    private static int contadorSubmuestreo= 1;
    public void actualizarVectoresSubmuestreo()
    {   
        int tiempo= contadorSubmuestreo*obtenerSubmuestreo();
        SubmuestreoSistolica.add(admin.staticParameters.readPresSist());
        SubmuestreoDiastolica.add(admin.staticParameters.readPresDias());
        SubmuestreoPulso.add(admin.staticParameters.readPresR());
        SubmuestreoMed.add(admin.staticParameters.readPresMed());
        if(admin.staticParameters.readHr()==65436)
        {
            SubmuestreoECG.add(0);
        }else
        {
            SubmuestreoECG.add(admin.staticParameters.readHr());
        }        
        SubmuestreoSPO2text.add(admin.staticParameters.readSpo2Oxi());
        SubmuestreoHR.add(admin.staticParameters.readSpo2Hr());
        if(admin.staticParameters.readResp()==65436)
        {
            SubmuestreoRESPtext.add(0);
        }else
        {
            SubmuestreoRESPtext.add(admin.staticParameters.readResp());
        }        
        Submuestreominutos.add(tiempo);
        contadorSubmuestreo++;
    }
    
    private boolean esPersonalizada= false; //Determina si la medición a guardar hace parte de una personalizada.
    private int idMedPersonalizada; //Almacena el id de la medición personalizada a asociar con cada medición.
    public void almacenarSenales()
    {
        EntityManager em = Persistence.createEntityManagerFactory("PipecPU").createEntityManager();
        em.getTransaction().begin();                            
        try
        {
            //Obtención de la medición personalizada
            EntityManager emAuxiliar = Persistence.createEntityManagerFactory("PipecPU").createEntityManager();
            Query queryMedicionFindAll = em.createNativeQuery("SELECT * from medicion_personalizada m WHERE id= '1'", MedicionPersonalizada.class);
            if(esPersonalizada)
            {
                queryMedicionFindAll = em.createNativeQuery("SELECT * from medicion_personalizada m WHERE id= '"+ idMedPersonalizada + "'", MedicionPersonalizada.class);
            }                 
            List<MedicionPersonalizada> listMedicion = queryMedicionFindAll.getResultList();
            //--------------------------------------

            Medicion med= new Medicion();
            med.setId(1);
            med.setIdentificacion(programaPrincipal.getPaciente());
            med.setTipoid(programaPrincipal.getPaciente().getPacientesPK().getTipoid());
            med.setIdPersonalizada(listMedicion.get(0));                    
            med.setDetalles(detallesMedicion.getText());

            //Aquí colocas tu objeto tipo Date
            Date date= new Date();
            date =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));                        
            java.sql.Timestamp fechaGuardar = new java.sql.Timestamp(date.getTime());                       

            med.setFecha(fechaGuardar); 
            med.setOndaSPO2(vSPO2.toString());
            med.setOndaECG1(vECG1.toString());
            med.setOndaECG2(vECG2.toString());
            med.setOndaRESP(vRESP.toString());
            med.setPresionSistolica(vsistolica.toString());
            med.setPresionDiastolica(vdiastolica.toString());
            med.setPulso(vpulso.toString());
            med.setMed(vmed.toString());
            med.setEcg(vECG.toString());
            med.setSpo2(vSPO2text.toString());
            med.setHr(vHR.toString());
            med.setResp(vRESPtext.toString());                        
            em.persist(med);

            resetVectores();
        }catch(Exception e)
        {
            System.out.println(e);
        }             
        em.getTransaction().commit();
    }
    
    public void parametrosDesplegables()
    {
        // Asignación de parámetros para desplegables       
        
        ObservableList<String> availableChoices = FXCollections.observableArrayList("5 minutos", "10 minutos", "15 minutos", "20 minutos", "25 minutos", "30 minutos"); 
        intervalo.setItems(availableChoices);
        intervalo.getSelectionModel().selectFirst();        
        
        availableChoices = FXCollections.observableArrayList("15 segundos", "30 segundos", "45 segundos", "60 segundos"); 
        duracionMuestra.setItems(availableChoices);
        duracionMuestra.getSelectionModel().selectFirst();
        
        availableChoices = FXCollections.observableArrayList("10 segundos", "20 segundos", "30 segundos", "40 segundos", "50 segundos",  "60 segundos"); 
        choiceIntervaloSubmuestreo.setItems(availableChoices);
        choiceIntervaloSubmuestreo.getSelectionModel().selectFirst();
        
        availableChoices = FXCollections.observableArrayList("10 minutos", "20 minutos", "30 minutos", "1 hora", "1 hora, 30 minutos", "2 horas"); 
        duracionExamen.setItems(availableChoices);
        duracionExamen.getSelectionModel().selectFirst();
    }
    
    private int obtenerIntervalo()
    {
       String valor= this.intervalo.getSelectionModel().getSelectedItem();
       int resultado= 5;
       switch(valor)
       {
           case "10 minutos":
               resultado= 10;
               break;
           case "15 minutos":
               resultado= 15;
               break;
           case "20 minutos":
               resultado= 20;
               break;
           case "25 minutos":
               resultado= 25;
               break;
           case "30 minutos":
               resultado= 30;
               break;
           default: resultado= 5;
               break;
       }       
       return resultado;
    }
    
    private int obtenerDuracion()
    {
       String valor= this.duracionMuestra.getSelectionModel().getSelectedItem();
       int resultado= 15;
       switch(valor)
       {
           case "30 segundos":
               resultado= 30;
               break;
           case "45 segundos":
               resultado= 45;
               break;
           case "60 segundos":
               resultado= 60;
               break;
           default: resultado= 15;
               break;
       }       
       return resultado;
    }
    
    private int obtenerDuracionExamen()
    {
       String valor= this.duracionExamen.getSelectionModel().getSelectedItem();
       int resultado= 30;
       switch(valor)
       {
           case "20 minutos":
               resultado= 20;
               break;
           case "30 minutos":
               resultado= 30;
               break;
           case "1 hora":
               resultado= 60;
               break;
           case "1 hora, 30 minutos":
               resultado= 90;
               break;
           case "2 horas": //Modificar esto, en este momento está para pruebas.
               resultado= 120;               
               break;
           default: resultado= 10;
               break;
       }       
       return resultado;
    }
    
     private int obtenerSubmuestreo()
    {
       String valor= this.choiceIntervaloSubmuestreo.getSelectionModel().getSelectedItem();
       int resultado= 10;
       switch(valor)
       {
           case "20 minutos":
               resultado= 20;
               break;
           case "30 minutos":
               resultado= 30;
               break;
           case "40 minutos":
               resultado= 40;
               break;
           case "50 minutos":
               resultado= 50;
               break;
           case "60 minutos": //Modificar esto, en este momento está para pruebas.
               resultado= 60;               
               break;
           default: resultado= 10;
               break;
       }       
       return resultado;
    }
     
     @FXML
    public void tomarPeso()
    {
        programaPrincipal.tomarPeso();
    }    

    
    
     
     //Control de smartcard
     
    
     
    
/*
    @FXML
    public void escribirEnTarjeta()
    {
        SmartCard smartCard= new SmartCard();        
        try 
        {       
            smartCard.writeOnCard(obtenerDatos());            
        } catch (Exception ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void actualizarTarjeta()
    {
        SmartCard smartCard= new SmartCard();        
        try 
        {       
            smartCard.updateCard(obtenerDatos());            
        } catch (Exception ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }   */  
    
     public void cargarHuella()
     {
         CapturaHuella capturadorHuella= new CapturaHuella(programaPrincipal.getPrimaryStage(), new Stage(), programaPrincipal);
         //capturadorHuella.show();
     }
     
    
    
}
