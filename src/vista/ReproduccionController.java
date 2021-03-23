/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import BD.Medicion;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Miguel Askar
 */
public class ReproduccionController implements Initializable {

    @FXML
    private LineChart<Number, Number> lc3;
    @FXML
    private NumberAxis xAxis_3;
    @FXML
    private NumberAxis yAxis_3;
    @FXML
    private LineChart<Number, Number> lc4;
    @FXML
    private NumberAxis xAxis_4;
    @FXML
    private NumberAxis yAxis_4;
    @FXML
    private StackPane paneLC3;
    @FXML
    private LineChart<Number, Number> lc2;
    @FXML
    private NumberAxis xAxis_2;
    @FXML
    private NumberAxis yAxis_2;
    @FXML
    private Button cargarMedicionesAnteriores;
    @FXML
    private Button pause;
    @FXML
    private Button stop;
    @FXML
    private Text hRECG;
    @FXML
    private Text spO2;
    @FXML
    private Text hRSpO2;
    @FXML
    private Text presionMedia;
    @FXML
    private Text pulso;
    @FXML
    private Text presionLabel;
    @FXML
    private Text rESPFreq;
    @FXML
    private LineChart<Number, Number> lc1;
    @FXML
    private NumberAxis xAxis;
    @FXML
    private NumberAxis yAxis;
    @FXML
    private ChoiceBox<String> primerSelector;
    @FXML
    private ChoiceBox<String> segundoSelector;
    @FXML
    private ChoiceBox<String> tercerSelector;
    @FXML
    private Button play;

    
    protected Pipec programaPrincipal;
    
    private Stage thisStage;
    
    //Vectores para reproducción
    private ConcurrentLinkedQueue<Number> reprodSPO2 = new ConcurrentLinkedQueue<Number>();
    private ConcurrentLinkedQueue<Number> reprodECG1 = new ConcurrentLinkedQueue<Number>();
    private ConcurrentLinkedQueue<Number> reprodECG2 = new ConcurrentLinkedQueue<Number>();
    private ConcurrentLinkedQueue<Number> reprodRESP = new ConcurrentLinkedQueue<Number>();
    
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
    
    
    //Colas para guardar los datos de graficación
    private XYChart.Series<Number, Number> series1;
    private XYChart.Series<Number, Number> series2;
    private XYChart.Series<Number, Number> series3;
    private XYChart.Series<Number, Number> series4;
    
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
    @FXML
    private Button cambioEstilo;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        inicializarImagenesOndas();
        initChartSignals();
        lc3.setVisible(false);
        
        ObservableList<String> availableChoices = FXCollections.observableArrayList("ECG CH1", "ECG CH2", "SpO2", "RESP"); 
        primerSelector.setItems(availableChoices);
        primerSelector.getSelectionModel().selectFirst();
        
        availableChoices = FXCollections.observableArrayList("ECG CH1", "ECG CH2", "SpO2", "RESP"); 
        segundoSelector.setItems(availableChoices);
        segundoSelector.getSelectionModel().select(2);
        
        availableChoices = FXCollections.observableArrayList("ECG CH1", "ECG CH2", "SpO2", "RESP"); 
        tercerSelector.setItems(availableChoices);
        tercerSelector.getSelectionModel().selectLast();
        
        primerSelector.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() 
        {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                if(((int)number2)!=-1)
                {
                    deseleccionarViejo(1, (int)number2); 
                    int primero= (int) number2;
                    int segundo= segundoSelector.getSelectionModel().getSelectedIndex();
                    int tercero= tercerSelector.getSelectionModel().getSelectedIndex();                    
                    //System.out.println(primero+","+ segundo+","+ tercero);
                    int[] chartsCambiar= {primero, segundo, tercero};
                    activarOndas(chartsCambiar);
                }
            }
        });
        
        segundoSelector.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() 
        {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                if(((int)number2)!=-1)
                {
                    deseleccionarViejo(2, (int)number2);  
                    int segundo= (int) number2;
                    int tercero= tercerSelector.getSelectionModel().getSelectedIndex();
                    int primero= primerSelector.getSelectionModel().getSelectedIndex();
                    //System.out.println(primero+","+ segundo+","+ tercero);
                    int[] chartsCambiar= {primero, segundo, tercero};
                    activarOndas(chartsCambiar);
                }
            }
        });
        
        tercerSelector.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() 
        {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                if(((int)number2)!=-1)
                {
                    deseleccionarViejo(3, (int)number2);  
                    int segundo= segundoSelector.getSelectionModel().getSelectedIndex();
                    int tercero= (int) number2;
                    int primero= primerSelector.getSelectionModel().getSelectedIndex();
                    //System.out.println(primero+","+ segundo+","+ tercero);
                    int[] chartsCambiar= {primero, segundo, tercero};
                    activarOndas(chartsCambiar); 
                }

            }
        });
        
        //Se asegura el inicio para las ondas triples
        int primero= primerSelector.getSelectionModel().getSelectedIndex();
        int segundo= segundoSelector.getSelectionModel().getSelectedIndex();
        int tercero= tercerSelector.getSelectionModel().getSelectedIndex();                    
        //System.out.println(primero+","+ segundo+","+ tercero);
        int[] chartsCambiar= {primero, segundo, tercero};
        activarOndas(chartsCambiar); 

        //Selectores
        primerSelector.setVisible(true);
        segundoSelector.setVisible(true);
        tercerSelector.setVisible(true);

        //Cambio del booleano de control
        estiloTriple= true;
        
        
        
    }
    
    private boolean estiloTriple= true; //Este booleano permite saber en qué tipo de visualización está
    @FXML
    public void cambiarEstilo()
    {
        if(estiloTriple)
        {
            //Onda ECG CH1
            lc2.setPrefWidth(600);
            lc2.setPrefHeight(320);
            lc2.setLayoutX(12);
            lc2.setLayoutY(100);
            lc2.setVisible(true);
            
            //Onda ECG CH2
            lc3.setPrefWidth(600);
            lc3.setPrefHeight(320);
            lc3.setLayoutX(12);
            lc3.setLayoutY(500);
            lc3.setVisible(true);
            
            //Onda SpO2
            lc1.setPrefWidth(600);
            lc1.setPrefHeight(320);
            lc1.setLayoutX(700);
            lc1.setLayoutY(100);
            lc1.setVisible(true);
            
            //Onda RESP
            lc4.setPrefWidth(600);
            lc4.setPrefHeight(320);
            lc4.setLayoutX(700);
            lc4.setLayoutY(500);
            lc4.setVisible(true);
            
            //Selectores
            primerSelector.setVisible(false);
            segundoSelector.setVisible(false);
            tercerSelector.setVisible(false);
            
            //Cambio del booleano de control
            estiloTriple= false;
            
        }else
        {
            //Se reestablece tamaño largo
            lc1.setPrefWidth(1300);
            lc1.setPrefHeight(300);
            lc1.setLayoutX(12);
            lc1.setLayoutY(352);
            
            lc2.setPrefWidth(1300);
            lc2.setPrefHeight(300);
            lc2.setLayoutX(12);
            lc2.setLayoutY(38);
            
            lc3.setPrefWidth(1300);
            lc3.setPrefHeight(300);
            lc3.setLayoutX(1);
            lc3.setLayoutY(213);
            
            lc4.setPrefWidth(1300);
            lc4.setPrefHeight(300);
            lc4.setLayoutX(12);
            lc4.setLayoutY(679);
            
            //Se usa la reacomodación de acuerdo a los selectores
            int primero= primerSelector.getSelectionModel().getSelectedIndex();
            int segundo= segundoSelector.getSelectionModel().getSelectedIndex();
            int tercero= tercerSelector.getSelectionModel().getSelectedIndex();                    
            //System.out.println(primero+","+ segundo+","+ tercero);
            int[] chartsCambiar= {primero, segundo, tercero};
            activarOndas(chartsCambiar); 

            //Selectores
            primerSelector.setVisible(true);
            segundoSelector.setVisible(true);
            tercerSelector.setVisible(true);
            
            //Control de desfase de layout
            
            
            //Cambio del booleano de control
            estiloTriple= true;
        }
    }
    
    public void deseleccionarViejo(int seleccionado, int seleccion)
    {
        if(seleccionado==1)
        {            
               if(segundoSelector.getSelectionModel().getSelectedIndex()== seleccion) segundoSelector.getSelectionModel().clearSelection();
               if(tercerSelector.getSelectionModel().getSelectedIndex() ==seleccion) tercerSelector.getSelectionModel().clearSelection();
        }else
        {
            if(seleccionado==2)
            {
                if(primerSelector.getSelectionModel().getSelectedIndex() ==seleccion) primerSelector.getSelectionModel().clearSelection();;
                if(tercerSelector.getSelectionModel().getSelectedIndex() ==seleccion) tercerSelector.getSelectionModel().clearSelection();
            }else
            {
                if(primerSelector.getSelectionModel().getSelectedIndex() ==seleccion) primerSelector.getSelectionModel().clearSelection();
                if(segundoSelector.getSelectionModel().getSelectedIndex() ==seleccion) segundoSelector.getSelectionModel().clearSelection();
            }
        }
               
    }
    
    
    public void activarOndas(int[] activar)
    { 
        lc1.setVisible(false);
        lc2.setVisible(false);
        lc3.setVisible(false);
        lc4.setVisible(false);
        for(int i= 0; i<3; i++)
        {
            if(activar[i]!=-1)
            {                
                activarOndaIndividual(obtenerChart(activar[i]), i+1);
            }
        }        
    }
    
    public LineChart obtenerChart(int index)
    {        
        switch(index)
        {
            case 0:
                return lc2;
            case 1:
                return lc3;
            case 2:
                return lc1;
            default:
                return lc4;                
        }
    }
    
    public void activarOndaIndividual(LineChart chart, int posicion)
    {
        if(posicion==1)
        {
            chart.setLayoutX(12);
            chart.setLayoutY(38);
            chart.setVisible(true);
        }else
        {
            if(posicion==2)
            {
                chart.setLayoutX(12);
                chart.setLayoutY(352);
                chart.setVisible(true);
            }else
            {
                chart.setLayoutX(12);
                chart.setLayoutY(679);
                chart.setVisible(true);
            }
        }
    }
    
    @FXML
    public void cargarAnteriores()
    {
        programaPrincipal.cargarVentanaReproduccion();
    }
    
    @FXML
    public void pausarReproduccion()
    {
        pausar= !pausar;
        
    }
    
    
     
    @FXML
    private void reproducirMedicion()
    { 
        Timer timer;
        timer = new Timer();
        if(play.isDisable()) //Entra cuando se presiona "Detener"
        {
            timer.cancel();
            timer.purge();
            reprodSPO2.clear();
            reprodECG1.clear();
            reprodECG2.clear();
            reprodRESP.clear();            
            
            stop.setDisable(true);
            pause.setDisable(true);
            play.setDisable(false);
            
            //método para activar los botones
            //enableMeasure(true); 
            
        }else //Entra cuando se prediona "Reproducir"
        {
            stop.setDisable(false);
            pause.setDisable(false);
            play.setDisable(true);
            
            //método para activar los botones
            //enableMeasure(true); 

            pausar= false; //Ya que se detiene la reproducción, ya no se puede pausar, si estaba en pausa, se cancela.
            //pausarReproduccion.setText("Pausar");            
            
            
            Medicion medicionReproducir= this.programaPrincipal.getMedicionReproducir();            
            String SPO2= medicionReproducir.getOndaSPO2().substring(1, medicionReproducir.getOndaSPO2().length()-1);
            StringTokenizer tokens= new StringTokenizer(SPO2, ", ");
            while(tokens.hasMoreTokens())
            {
                reprodSPO2.add(Integer.parseInt(tokens.nextToken()));
            }
            //Ahora para ECG1
            String ECG1= medicionReproducir.getOndaECG1().substring(1, medicionReproducir.getOndaECG1().length()-1);
            tokens= new StringTokenizer(ECG1, ", ");
            while(tokens.hasMoreTokens())
            {
                reprodECG1.add(Integer.parseInt(tokens.nextToken()));
            }
            
            String ECG2= medicionReproducir.getOndaECG2().substring(1, medicionReproducir.getOndaECG2().length()-1);
            tokens= new StringTokenizer(ECG2, ", ");
            while(tokens.hasMoreTokens())
            {
                reprodECG2.add(Integer.parseInt(tokens.nextToken()));
            } 
            
            String RESP= medicionReproducir.getOndaRESP().substring(1, medicionReproducir.getOndaRESP().length()-1);
            tokens= new StringTokenizer(RESP, ", ");
            
            while(tokens.hasMoreTokens())
            {
                reprodRESP.add(Integer.parseInt(tokens.nextToken()));                
            }     
            
            //Se llenan los otros vectores
            llenarVectoresNoOndas();    
            
            TimerTask task = new TimerTask() 
            {
            
                @Override
                public void run()
                {
                    reproducirSPO2();
                    reproducirECG1();
                    reproducirECG2();
                    reproducirRESP();
                    try 
                    {
                        if(reprodSPO2.isEmpty() && reprodECG1.isEmpty() && reprodECG2.isEmpty() && reprodRESP.isEmpty())
                        {
                            reprodSPO2.clear();
                            reprodECG1.clear();
                            reprodECG2.clear();
                            reprodRESP.clear();
                            //enableMeasure(true);
                            stop.setDisable(true);
                            pause.setDisable(true);
                            timer.cancel();
                            timer.purge();
                        }
                    } catch (Throwable ex) 
                    {
                        Logger.getLogger(OndasController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            };
            // Empezamos dentro de 0ms y luego lanzamos la tarea cada 30ms
            timer.schedule(task, 0, 30);
            
            //Timer para actualizar los datos de los cuadros inferiores
            TimerTask taskValores = new TimerTask() 
            {
            
                @Override
                public void run()
                { 
                    if(vSPO2text.size()>0)
                    {
                        asignarStaticParameters(vHR.remove(0),
                        + vRESPtext.remove(0),
                        + vSPO2.remove(0),
                        + vSPO2text.remove(0),
                        + vpulso.remove(0),
                        + vdiastolica.remove(0),
                        + vmed.remove(0),
                        + vsistolica.remove(0));
                    }//De lo contrario, se acabaron los datos a presentar.
                     
                }
            };
            // Empezamos dentro de 0ms y luego lanzamos la tarea cada 30ms
            timer.schedule(taskValores, 0, 500);
            
        }  
        
        
        
 
    } 
    
    public void llenarVectoresNoOndas()
    {
        
        Medicion medicionReproducir= this.programaPrincipal.getMedicionReproducir();            
        //A continuación se obtienen los datos que no son ondas
        String Spo2= medicionReproducir.getSpo2().substring(1, medicionReproducir.getSpo2().length()-1);
        StringTokenizer tokens= new StringTokenizer(Spo2, ", ");

        while(tokens.hasMoreTokens())
        {
            vSPO2.add(Integer.parseInt(tokens.nextToken()));                
        } 

        String Spo2Text= medicionReproducir.getSpo2().substring(1, medicionReproducir.getSpo2().length()-1);
        tokens= new StringTokenizer(Spo2, ", ");

        while(tokens.hasMoreTokens())
        {
            vSPO2text.add(Integer.parseInt(tokens.nextToken()));                
        } 

        String sistolica= medicionReproducir.getPresionSistolica().substring(1, medicionReproducir.getPresionSistolica().length()-1);
        tokens= new StringTokenizer(sistolica, ", ");

        while(tokens.hasMoreTokens())
        {
            vsistolica.add(Integer.parseInt(tokens.nextToken()));                
        } 

        String diastolica= medicionReproducir.getPresionDiastolica().substring(1, medicionReproducir.getPresionDiastolica().length()-1);
        tokens= new StringTokenizer(diastolica, ", ");

        while(tokens.hasMoreTokens())
        {
            vdiastolica.add(Integer.parseInt(tokens.nextToken()));                
        } 

        String pulso= medicionReproducir.getPulso().substring(1, medicionReproducir.getPulso().length()-1);
        tokens= new StringTokenizer(pulso, ", ");

        while(tokens.hasMoreTokens())
        {
            vpulso.add(Integer.parseInt(tokens.nextToken()));                
        } 

        String med= medicionReproducir.getMed().substring(1, medicionReproducir.getMed().length()-1);
        tokens= new StringTokenizer(med, ", ");

        while(tokens.hasMoreTokens())
        {
            vmed.add(Integer.parseInt(tokens.nextToken()));                
        } 

        String ecg= medicionReproducir.getEcg().substring(1, medicionReproducir.getEcg().length()-1);
        tokens= new StringTokenizer(ecg, ", ");

        while(tokens.hasMoreTokens())
        {
            vECG.add(Integer.parseInt(tokens.nextToken()));                
        } 

        String hr= medicionReproducir.getHr().substring(1, medicionReproducir.getHr().length()-1);
        tokens= new StringTokenizer(hr, ", ");

        while(tokens.hasMoreTokens())
        {
            vHR.add(Integer.parseInt(tokens.nextToken()));                
        } 

        String respText= medicionReproducir.getResp().substring(1, medicionReproducir.getResp().length()-1);
        tokens= new StringTokenizer(respText, ", ");

        while(tokens.hasMoreTokens())
        {
            vRESPtext.add(Integer.parseInt(tokens.nextToken()));                
        }   
    }
    
    public void asignarStaticParameters(int hr, int respRate, int spo2Oxi, int spo2Hr, int presRate, int presDias, int presMed, int presSist) 
        {
        Platform.runLater(new Runnable(){
                        @Override
                        public void run() { 
                            if(respRate!=65436)
                            {
                               rESPFreq.setText(respRate+ " BrPM"); 
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

    } 
    
    private boolean pausar= false;
    public void reproducirSPO2()
    {
           for (int i = 0; i < 3; i++) { //-- add 20 numbers to the plot+

                if (reprodSPO2.isEmpty()) break;            
                series1.getData().add(new XYChart.Data<>(xSeriesData_spo2++, reprodSPO2.remove()));
            }
            while(pausar)
            {
                //No hace nada mientras esté pausado
            }
            // remove points to keep us at no more than MAX_DATA_POINTS
            if (series1.getData().size() > MAX_DATA_POINTS_SPO2) {
                series1.getData().remove(0, series1.getData().size() - MAX_DATA_POINTS_SPO2);
            }
            // update 
            xAxis.setLowerBound(xSeriesData_spo2-MAX_DATA_POINTS_SPO2);
            xAxis.setUpperBound(xSeriesData_spo2-1);

    }

    public void reproducirECG1()
    {

            for (int i = 0; i < 3; i++) { //-- add 20 numbers to the plot+
                if (reprodECG1.isEmpty()) break;            
                series2.getData().add(new XYChart.Data<>(xSeriesData_ecg1++, reprodECG1.remove()));

            }
            while(pausar)
            {
                //No hace nada mientras esté pausado
            }
            // remove points to keep us at no more than MAX_DATA_POINTS
            if (series2.getData().size() > MAX_DATA_POINTS_ECG) {
                series2.getData().remove(0, series2.getData().size() - MAX_DATA_POINTS_ECG);
            }
            // update 
            xAxis_2.setLowerBound(xSeriesData_ecg1-MAX_DATA_POINTS_ECG);
            xAxis_2.setUpperBound(xSeriesData_ecg1-1);    
    }

    public void reproducirECG2()
    {

            for (int i = 0; i < 3; i++) { //-- add 20 numbers to the plot+
                if (reprodECG2.isEmpty()) break;
                series3.getData().add(new XYChart.Data<>(xSeriesData_ecg2++, reprodECG2.remove()));

            }
            while(pausar)
            {
                //No hace nada mientras esté pausado
            }
            // remove points to keep us at no more than MAX_DATA_POINTS
            if (series3.getData().size() > MAX_DATA_POINTS_ECG) {
                series3.getData().remove(0, series3.getData().size() - MAX_DATA_POINTS_ECG);
            }
            // update 
            xAxis_3.setLowerBound(xSeriesData_ecg2-MAX_DATA_POINTS_ECG);
            xAxis_3.setUpperBound(xSeriesData_ecg2-1);   
    }

    public void reproducirRESP()
    {    
            for (int i = 0; i < 3; i++) { //-- add 20 numbers to the plot+
                if (reprodRESP.isEmpty()) break;
                series4.getData().add(new XYChart.Data<>(xSeriesData_resp++, reprodRESP.remove()));

            }
            while(pausar)
            {
                //No hace nada mientras esté pausado
            }
            // remove points to keep us at no more than MAX_DATA_POINTS
            if (series4.getData().size() > MAX_DATA_POINTS_RESP) {
                series4.getData().remove(0, series4.getData().size() - MAX_DATA_POINTS_RESP);
            }
            // update 
            xAxis_4.setLowerBound(xSeriesData_resp-MAX_DATA_POINTS_RESP);
            xAxis_4.setUpperBound(xSeriesData_resp-1);  
    }
    
    public void inicializarImagenesOndas()
    {
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
        stop.setBackground(Background.EMPTY);
        
        
        
        //Imagen botón de carga de mediciones anteriores
        selected    = new Image
        (
          "\\images\\iconosOndas\\botonCargarMediciones.png"
        );
        imagenFondo = new ImageView();
        imagenFondo.setImage(selected);
        cargarMedicionesAnteriores.setGraphic(imagenFondo);    
        cargarMedicionesAnteriores.setBackground(Background.EMPTY);
        //cargarMedicionesAnteriores.setStyle("-fx-background-image: url('/images/iconosOndas/botonCargarMediciones.png')");
        //cargarMedicionesAnteriores.setBackground(Background.EMPTY);
        
        
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
        lc1.setTitle("Onda SPO2");
        
        lc2.setAnimated(false);
        //lc2.setId("ondaECG1");
        lc2.setTitle("Onda ECG CH1");
        
        /*lc2.setBorder(new Border(new BorderStroke(Color.BLACK, 
                BorderStrokeStyle.DASHED, CornerRadii.EMPTY, BorderWidths.DEFAULT)));*/
        
        lc3.setAnimated(false);
        //lc3.setId("ondaECG2");
        lc3.setTitle("Onda ECG CH2");    
        
        lc4.setAnimated(false);
        //lc4.setId("ondaRESP");
        lc4.setTitle("Onda RESP");

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
    
    
    //Getters y Setters
    
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
    
    public void cargarDetalleOndaECG1()            
    {
        Medicion medicionReproducir= this.programaPrincipal.getMedicionReproducir();            
        String Onda= medicionReproducir.getOndaECG1().substring(1, medicionReproducir.getOndaECG1().length()-1);
        this.programaPrincipal.setOndaDetalle(Onda);
        this.programaPrincipal.cargarVentanaDetalleOnda();
    }
    
    public void cargarDetalleOndaECG2()
    {
        Medicion medicionReproducir= this.programaPrincipal.getMedicionReproducir();            
        String Onda= medicionReproducir.getOndaECG2().substring(1, medicionReproducir.getOndaECG2().length()-1);
        this.programaPrincipal.setOndaDetalle(Onda);
        this.programaPrincipal.cargarVentanaDetalleOnda();
    }
    
    public void cargarDetalleOndaSPO2()
    {
        Medicion medicionReproducir= this.programaPrincipal.getMedicionReproducir();            
        String Onda= medicionReproducir.getOndaSPO2().substring(1, medicionReproducir.getOndaSPO2().length()-1);
        this.programaPrincipal.setOndaDetalle(Onda);
        this.programaPrincipal.cargarVentanaDetalleOnda();
    }
    
    public void cargarDetalleOndaRESP()
    {
        Medicion medicionReproducir= this.programaPrincipal.getMedicionReproducir();            
        String Onda= medicionReproducir.getOndaRESP().substring(1, medicionReproducir.getOndaRESP().length()-1);
        this.programaPrincipal.setOndaDetalle(Onda);
        this.programaPrincipal.cargarVentanaDetalleOnda();
    }
    
    
   
    
}
