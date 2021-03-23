/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import java.net.URL;
import java.util.Collections;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Miguel Askar
 */
public class OndaDetalleController implements Initializable {

    @FXML
    private Button cambioEstilo;
    
    
    private String ondaReproducir;
    protected Pipec programaPrincipal;    
    private Stage thisStage;
    @FXML
    private BorderPane root;
    @FXML
    private AnchorPane anchor;
    
    private NumberAxis xAxis;
    private NumberAxis yAxis;
    private LineChart<Number, Number> chart;
    private LineChart<Number, Number> chartZoom;
    
    private int xZoomLower;
    private int yZoomLower;
    private int xZoomUpper;
    private int yZoomUpper;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
    }    
    
    
    
    public void iniciar()
    {
        chart = createChart();        
        chartZoom = createChart();
        chart.setData(generateChartData());
        chartZoom.setData(generateChartData());
        
        NumberAxis xAxisGuardar= (NumberAxis) chartZoom.getXAxis();
        NumberAxis yAxisGuardar= (NumberAxis) chartZoom.getYAxis();
        
        xZoomLower= (int) xAxisGuardar.getLowerBound();
        yZoomLower= (int) yAxisGuardar.getLowerBound();
        xZoomUpper= (int) xAxisGuardar.getUpperBound();
        yZoomUpper= (int) yAxisGuardar.getUpperBound();
        
		
        final StackPane chartContainer = new StackPane();
        final StackPane chartZoomContainer = new StackPane();
        chartContainer.getChildren().add(chart);
        chartZoomContainer.getChildren().add(chartZoom);

        


        final Rectangle zoomRect = new Rectangle();
        zoomRect.setManaged(false);
        zoomRect.setFill(Color.LIGHTSEAGREEN.deriveColor(0, 1, 1, 0.5));
        chartContainer.getChildren().add(zoomRect);

        setUpZooming(zoomRect, chart);

        final HBox controls = new HBox(10);
        controls.setPadding(new Insets(10));
        controls.setAlignment(Pos.CENTER);

        final Button zoomButton = new Button("Zoom");
        final Button resetButton = new Button("Reset");
        zoomButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                doZoom(zoomRect, chart);
            }
        });
		resetButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                final NumberAxis xAxis = (NumberAxis)chart.getXAxis();
                xAxis.setLowerBound(0);
                xAxis.setUpperBound(1000);
                final NumberAxis yAxis = (NumberAxis)chart.getYAxis();
                yAxis.setLowerBound(0);
                yAxis.setUpperBound(1000);
                
                zoomRect.setWidth(0);
                zoomRect.setHeight(0);
            }
        });
		final BooleanBinding disableControls = 
		        zoomRect.widthProperty().lessThan(5)
		        .or(zoomRect.heightProperty().lessThan(5));
		zoomButton.disableProperty().bind(disableControls);
		controls.getChildren().addAll(zoomButton, resetButton);
                
                anchor.getChildren().add(chartContainer);
                chartContainer.setLayoutX(10);
                chartContainer.setLayoutY(10);
                chartContainer.setPrefHeight(300);
                chartContainer.setPrefWidth(1600);
                
                anchor.getChildren().add(chartZoomContainer);
                chartZoomContainer.setLayoutX(10);
                chartZoomContainer.setLayoutY(400);
                chartZoomContainer.setPrefHeight(300);
                chartZoomContainer.setPrefWidth(1600);
                chartZoom.setVisible(false);
                
   		anchor.getChildren().add(controls);
                controls.setLayoutX(2);  
                controls.setLayoutY(2);
    }
    
    
    public LineChart<Number, Number> createChart() {
	    xAxis = createAxis(0, 500);
	    yAxis = createAxis(0, 500);	    
	    LineChart<Number, Number> chartNuevo = new LineChart<>(xAxis, yAxis);
	    chartNuevo.setAnimated(false);
	    chartNuevo.setCreateSymbols(false);	    	                
	    return chartNuevo ;
	}

    private NumberAxis createAxis(int menor, int mayor) {
        final NumberAxis xAxis = new NumberAxis();
	    xAxis.setAutoRanging(false);
	    xAxis.setLowerBound(menor);
	    xAxis.setUpperBound(mayor);
        return xAxis;
    }
    
    private ObservableList<XYChart.Series<Number, Number>> generateChartData() {
        final XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("Data");
        final Random rng = new Random();
        StringTokenizer tokens= new StringTokenizer(ondaReproducir, ", ");
        
        int tamanioX= 500;
        int menorY= 3000;
        int mayorY= 0;
        for (int i=0; i>-1; i++) {
            
            int nuevoDato= Integer.parseInt(tokens.nextToken());
            XYChart.Data<Number, Number> dataPoint = new XYChart.Data<Number, Number>(i, nuevoDato);            
            series.getData().add(dataPoint);
            if(nuevoDato<menorY) menorY= nuevoDato;
            if(nuevoDato>mayorY) mayorY= nuevoDato;            
            if(!tokens.hasMoreTokens()) 
            {
                tamanioX= i;                
                break;
            }
        }
        //xAxis = createAxis(0, tamanioX);
	//yAxis = createAxis(menorY-50, mayorY+50);
        final NumberAxis yAxis = (NumberAxis) chart.getYAxis();
        final NumberAxis xAxis = (NumberAxis) chart.getXAxis();
        final NumberAxis yAxisZoom = (NumberAxis) chartZoom.getYAxis();
        final NumberAxis xAxisZoom = (NumberAxis) chartZoom.getXAxis();
        //xAxis.setLowerBound(xAxis.getLowerBound() + xOffset / xAxisScale);
        xAxis.setUpperBound(tamanioX);
        yAxis.setLowerBound(menorY-50);
        yAxis.setUpperBound(mayorY+50);
        
        xAxisZoom.setUpperBound(tamanioX);
        yAxisZoom.setLowerBound(menorY-50);
        yAxisZoom.setUpperBound(mayorY+50);
	
        return FXCollections.observableArrayList(Collections.singleton(series));
    }
    
    private void setUpZooming(final Rectangle rect, final Node zoomingNode) {
        final ObjectProperty<Point2D> mouseAnchor = new SimpleObjectProperty<>();
        zoomingNode.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mouseAnchor.set(new Point2D(event.getX(), event.getY()));
                rect.setWidth(0);
                rect.setHeight(0);
            }
        });
        zoomingNode.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                double x = event.getX();
                double y = event.getY();
                rect.setX(Math.min(x, mouseAnchor.get().getX()));
                rect.setY(Math.min(y, mouseAnchor.get().getY()));
                rect.setWidth(Math.abs(x - mouseAnchor.get().getX()));
                rect.setHeight(Math.abs(y - mouseAnchor.get().getY()));
            }
        });
    }
    
    private void doZoom(Rectangle zoomRect, LineChart<Number, Number> chart) {
        //Se hace un reset del cuadro de zoom
        final NumberAxis yAxis = (NumberAxis) chartZoom.getYAxis();
        final NumberAxis xAxis = (NumberAxis) chartZoom.getXAxis();
        xAxis.setLowerBound(xZoomLower);
        xAxis.setUpperBound(xZoomUpper);
        yAxis.setLowerBound(yZoomLower);
        yAxis.setUpperBound(yZoomUpper);
        
        
        chartZoom.setVisible(true);
        Point2D zoomTopLeft = new Point2D(zoomRect.getX(), zoomRect.getY());
        Point2D zoomBottomRight = new Point2D(zoomRect.getX() + zoomRect.getWidth(), zoomRect.getY() + zoomRect.getHeight());
        final NumberAxis yAxis1 = (NumberAxis) chart.getYAxis();
        Point2D yAxisInScene = yAxis1.localToScene(0, 0);
        final NumberAxis xAxis1 = (NumberAxis) chart.getXAxis();
        Point2D xAxisInScene = xAxis1.localToScene(0, 0);
        double xOffset = zoomTopLeft.getX() - yAxisInScene.getX() ;
        double yOffset = zoomBottomRight.getY() - xAxisInScene.getY();
        double xAxisScale = xAxis1.getScale();
        double yAxisScale = yAxis1.getScale();        
        
        xAxis.setLowerBound(xAxis.getLowerBound() + xOffset / xAxisScale);
        xAxis.setUpperBound(xAxis.getLowerBound() + zoomRect.getWidth() / xAxisScale);
        yAxis.setLowerBound(yAxis.getLowerBound() + yOffset / yAxisScale);
        yAxis.setUpperBound(yAxis.getLowerBound() - zoomRect.getHeight() / yAxisScale);
        System.out.println(yAxis.getLowerBound() + " " + yAxis.getUpperBound());
        //zoomRect.setWidth(0);
        //zoomRect.setHeight(0);
    }

    @FXML
    private void cambiarEstilo(ActionEvent event) {
    }
    
    public String getOndaReproducir() {
        return ondaReproducir;
    }

    public void setOndaReproducir(String ondaReproducir) {
        this.ondaReproducir = ondaReproducir;
    }
    
    public void setStage(Stage stage)
    {
        this.thisStage= stage;
    }
    
    public void setProgramaPrincipal(Pipec programaPrincipal) 
    {
        this.programaPrincipal = programaPrincipal;        
    }
    
}
