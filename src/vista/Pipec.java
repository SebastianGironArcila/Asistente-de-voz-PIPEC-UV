/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;


import AsistenteVirtual_v1.SpeechToText_v1;
import AsistenteVirtual_v1.TextToSpeech_v1;
import BD.Medicion;
import BD.Pacientes;
import BD.Usuarios;
import cliente.AdminDevice;
import controlador.Mediciones;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

/**
 *
 * @author Miguel Askar
 */
public class Pipec extends Application {
    
    private Stage VentanaInterna; //Stage principal para poder derivar ventanas    
    private Stage primaryStage;
    private Stage secondaryStage;
    private BorderPane rootLayout;
    private AdminDevice admin;
    private Mediciones mediciones;
    private Pacientes pacienteEnConsulta;

    private Usuarios usuario;
    private Medicion medicionReproducir;
    private AfinamientosController afinamientos;
    
    private SpeechToText_v1 stt;
    private TextToSpeech_v1 tts;
    
    @Override
    public void start(Stage stage) throws Exception {
            
        
        primaryStage = stage;
        secondaryStage= new Stage();
        primaryStage.setTitle("Autenticación de usuario");
        
        admin= new AdminDevice(this);
        //admin.ConectarTcp(); //Se establece la conexión TCP.
        //admin.conectarWS(); //Este método es para conectar al WS, no lo necesitas y no va a encontrar nada
        //porque no hay nadie conectado.
        
        //Se dejan los parámetros necesarios para escenas secundarias
        secondaryStage.initModality(Modality.WINDOW_MODAL);
        secondaryStage.centerOnScreen();
        secondaryStage.initOwner(primaryStage);
        
        //Creación de la clase de gestión de medicioirnes
        mediciones= new Mediciones();
        mediciones.setAdmin(admin);
        mediciones.setProgramaPrincipal(this);
        this.stt = new SpeechToText_v1();
        
        
        
        //Por último se inicia la vista
        initRootLayout();
        
        
        
    }
    
    public void initRootLayout() //Primer inicio, carga la vista de Login.
    {
    
    		String frase="Bienvenido a la plataforma PIPEC";
            this.tts = new TextToSpeech_v1(frase);
    	
        
        
        try {
            // Load root layout from fxml file
             
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Pipec.class.getResource("/vista/Login.fxml"));
            rootLayout = (BorderPane) loader.load();
            
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            //scene.getStylesheets().add("/vista/DarkTheme.css");
            
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            
            LoginController controller = loader.getController();
            controller.setProgramaPrincipal(this);
            
            this.stt.setLoginController(controller);
            this.stt.setProgramaPrincipal(this);
            
           
            primaryStage.show();
            
        } catch (IOException ex) {
            System.out.println("Error en el sistema: " + ex.toString());
        }
    }
    
    
     public void setStt(SpeechToText_v1 stt){
        this.stt = stt;
    }
    
     
    public void irMenuPrincipal(Usuarios usuario) //Carga el menú principal.
    {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Pipec.class.getResource("/vista/MenuPrincipal.fxml"));
            BorderPane menu = (BorderPane) loader.load();
            //ventanaPrincipal = new Stage();
            //ventanaPrincipal.initModality(Modality.APPLICATION_MODAL);
            //ventanaPrincipal.setTitle("Menú principal");
            primaryStage.setTitle("Menú principal");
            
            Scene scene = new Scene(menu);
            //scene.getStylesheets().add("/vista/menu.css");
            //ventanaPrincipal.setScene(scene);
            primaryStage.setScene(scene);
            MenuPrincipalController menuPrincipal = loader.getController();    
            this.usuario= usuario;            
            
            this.stt.setUsuario(usuario);
            menuPrincipal.setProgramaPrincipal(this); 
            
            
            
           /* ventanaPrincipal.show();
            ventanaPrincipal.setResizable(false);
            Rectangle2D ventanaPrimariaLimites = Screen.getPrimary().getVisualBounds();
            ventanaPrincipal.setX(ventanaPrimariaLimites.getMinX());
            ventanaPrincipal.setY(ventanaPrimariaLimites.getMinY());
            ventanaPrincipal.setWidth(ventanaPrimariaLimites.getWidth());
            ventanaPrincipal.setHeight(ventanaPrimariaLimites.getHeight());*/
            //primaryStage.close();
            //primaryStage = ventanaPrincipal;
            primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>(){
            @Override public void handle(WindowEvent event) {
                //event.consume(); //Consumar el evento
                //menuPrincipal.cerrarPrograma();
                System.exit(0);
            }  
        });
        } catch (Exception ex) {
            System.out.println("Error del sistema: " + ex.toString());
            ex.printStackTrace();
        }
    }
   
    
   public void irMenuPrincipal() //Carga el menú principal.
    {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Pipec.class.getResource("/vista/MenuPrincipal.fxml"));
            BorderPane menu = (BorderPane) loader.load();
            //ventanaPrincipal = new Stage();
            //ventanaPrincipal.initModality(Modality.APPLICATION_MODAL);
            //ventanaPrincipal.setTitle("Menú principal");
            primaryStage.setTitle("Menú principal");
            
            Scene scene = new Scene(menu);
            //scene.getStylesheets().add("/vista/menu.css");
            //ventanaPrincipal.setScene(scene);
            primaryStage.setScene(scene);
            MenuPrincipalController menuPrincipal = loader.getController();    
            
            menuPrincipal.setProgramaPrincipal(this);            
            
            //stt.setMenuPrincipalController(menuPrincipal);
            
           /* ventanaPrincipal.show();
            ventanaPrincipal.setResizable(false);
            Rectangle2D ventanaPrimariaLimites = Screen.getPrimary().getVisualBounds();
            ventanaPrincipal.setX(ventanaPrimariaLimites.getMinX());
            ventanaPrincipal.setY(ventanaPrimariaLimites.getMinY());
            ventanaPrincipal.setWidth(ventanaPrimariaLimites.getWidth());
            ventanaPrincipal.setHeight(ventanaPrimariaLimites.getHeight());*/
            //primaryStage.close();
            //primaryStage = ventanaPrincipal;
            primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>(){
            @Override public void handle(WindowEvent event) {
                //event.consume(); //Consumar el evento
                //menuPrincipal.cerrarPrograma();
                System.exit(0);
            }  
        });
        } catch (Exception ex) {
            //System.out.println("Error del sistema: " + ex.toString());
            ex.printStackTrace();
        }
    }
    

    public void cargarEstructuraBasica() //Clase original, se usa como padre para reutilizar código. Carga la historia clínica.
    {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Pipec.class.getResource("/vista/EstructuraBasica.fxml"));
            BorderPane basica = (BorderPane) loader.load();
            //ventanaPrincipal = new Stage();
            //ventanaPrincipal.initModality(Modality.APPLICATION_MODAL);
            primaryStage.setTitle("Menú estructura");
            
            Scene scene = new Scene(basica);
            //scene.getStylesheets().add("/vista/menu.css");
            primaryStage.setScene(scene);
            EstructuraBasicaController estructuraBasica = loader.getController();           
            
            estructuraBasica.setProgramaPrincipal(this); 
            estructuraBasica.setPaciente(pacienteEnConsulta);
            estructuraBasica.llenarPaciente();
            //estructuraBasica.actualizarFotoPacente(fotoTomada);
            
            
            estructuraBasica.setSpeechToText(this.stt);
            this.stt.setEstructuraBasicaController(estructuraBasica);
            this.stt.setProgramaPrincipal(this);
            
         //   stt.setPacienteController(estructuraBasica);
            //return estructuraBasica.retornarPanelParaVistasInternas();
            
        } catch (Exception ex) {
            //System.out.println("Error del sistema: " + ex.toString());
            //return null;
            ex.printStackTrace();
        }
    }
    
    public void cargarRegistro()
    {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Pipec.class.getResource("/vista/NuevoUsuario.fxml"));
            BorderPane basica = (BorderPane) loader.load();
            //ventanaPrincipal = new Stage();
            //ventanaPrincipal.initModality(Modality.APPLICATION_MODAL);
            secondaryStage.setTitle("Registro de usuario nuevo");
            
            Scene scene = new Scene(basica);
            //scene.getStylesheets().add("/vista/menu.css");
            secondaryStage.setScene(scene);
            
            NuevoUsuarioController nuevoUsuario = loader.getController();              
            nuevoUsuario.setProgramaPrincipal(this);
            nuevoUsuario.setStage(secondaryStage);
            
            secondaryStage.show();

            //return estructuraBasica.retornarPanelParaVistasInternas();
            
        } catch (Exception ex) {
            System.out.println("Error del sistema: " + ex.toString());
            //return null;
        }
    }
    
    private OndasController ondas;
    public void cargarOndas() //Carga la vista de Ondas
    {
       try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Pipec.class.getResource("/vista/Ondas.fxml"));
            BorderPane basica = (BorderPane) loader.load();
            //ventanaPrincipal = new Stage();
            //ventanaPrincipal.initModality(Modality.APPLICATION_MODAL);
            primaryStage.setTitle("Menú ondas");
            
            Scene scene = new Scene(basica);
            //scene.getStylesheets().add("/vista/menu.css");
            primaryStage.setScene(scene);
            ondas = loader.getController();  
            ondas.setAdmin(admin);
            
            
            
            //ondas.actualizarFotoPacente(fotoTomada);
            ondas.setProgramaPrincipal(this);
            ondas.setSpeechToText(this.stt);
       
            this.stt.setOndasController(ondas);
            this.stt.setProgramaPrincipal(this);
            ondas.setSpeechToText(this.stt);
            
            //ondas.iniciarLecturaSenales();
          //  stt.setOndasController(ondas);
            //return estructuraBasica.retornarPanelParaVistasInternas();
            
        } catch (Exception ex) {
            System.out.println("Error del sistema: " + ex.toString());
            ex.printStackTrace();
            //return null;
        }
    }
    
    
    public void cargarAfinamientos()
    {        
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Pipec.class.getResource("/vista/Afinamientos.fxml"));
            BorderPane basica = (BorderPane) loader.load();
            //ventanaPrincipal = new Stage();
            //ventanaPrincipal.initModality(Modality.APPLICATION_MODAL);
            primaryStage.setTitle("Menú afinamientos");
            
            Scene scene = new Scene(basica);
            //scene.getStylesheets().add("/vista/menu.css");
            primaryStage.setScene(scene);
            afinamientos = loader.getController();           
            
            this.afinamientos.setProgramaPrincipal(this);           
            this.afinamientos.llenarTablaAfinamientos();
            //this.afinamientos.actualizarFotoPacente(fotoTomada);
            
            this.stt.setAfinamientosController(this.afinamientos);
            this.stt.setProgramaPrincipal(this);
            this.afinamientos.setSpeechToText(this.stt);
            
            //return estructuraBasica.retornarPanelParaVistasInternas();
            
        } catch (Exception ex) {
            System.out.println("Error del sistema afin: " + ex.toString());
            //ex.printStackTrace();
            //return null;
        }
    }  
    
    public void cargarAjustes()
    {        
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Pipec.class.getResource("/vista/Ajustes.fxml"));
            BorderPane basica = (BorderPane) loader.load();
            //ventanaPrincipal = new Stage();
            //ventanaPrincipal.initModality(Modality.APPLICATION_MODAL);
            primaryStage.setTitle("Menú afinamientos");
            
            Scene scene = new Scene(basica);
            //scene.getStylesheets().add("/vista/menu.css");
            primaryStage.setScene(scene);
            AjustesController ajustes = loader.getController();           
            
            ajustes.setProgramaPrincipal(this); 
            ajustes.setUsuario(usuario);
            ajustes.cargarUsuario();
            //return estructuraBasica.retornarPanelParaVistasInternas();
            
        } catch (Exception ex) {
            System.out.println("Error del sistema: " + ex.toString());
            ex.printStackTrace();
            //return null;
        }
    }
    
    public void cargarEntrenamientoAsistente(){
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Pipec.class.getResource("/vista/EntrenamientoAsistente.fxml"));
            rootLayout = (BorderPane) loader.load();
            
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            //scene.getStylesheets().add("/vista/DarkTheme.css");
            
            secondaryStage.setScene(scene);
            secondaryStage.setResizable(true);
            
            EntrenamientoAsistenteController controller = loader.getController();
            controller.setProgramaPrincipal(this);
            controller.setStage(secondaryStage);
            controller.setSpeechToText(this.stt);
            this.stt.setEntrenamientoAsistenteController(controller);
            
            
            secondaryStage.show();
            
            
        } catch (IOException ex) {
            System.out.println("Error en el sistema: " + ex.toString());
            //ex.printStackTrace();
        }
    }
    
    public void cargarBusquedaPaciente()
    {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Pipec.class.getResource("/vista/BusquedaPacientes.fxml"));
            rootLayout = (BorderPane) loader.load();
            
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            //scene.getStylesheets().add("/vista/DarkTheme.css");
            
            secondaryStage.setScene(scene);
            secondaryStage.setResizable(true);
            
            BusquedaPacientesController controller = loader.getController();
            controller.setProgramaPrincipal(this);
            controller.setStage(secondaryStage);
            
            secondaryStage.show();
            
            
        } catch (IOException ex) {
            System.out.println("Error en el sistema: " + ex.toString());
            //ex.printStackTrace();
        }
    }
    
    public void cargarBusquedaFarmacia()
    {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Pipec.class.getResource("/vista/BusquedaFarmacias.fxml"));
            rootLayout = (BorderPane) loader.load();
            
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            //scene.getStylesheets().add("/vista/DarkTheme.css");
            
            secondaryStage.setScene(scene);
            secondaryStage.setResizable(true);
            
            BusquedaFarmaciasController controller = loader.getController();
            controller.setProgramaPrincipal(this);
            controller.setStage(secondaryStage);
            
            secondaryStage.show();
            
            
        } catch (IOException ex) {
            System.out.println("Error en el sistema: " + ex.toString());
            //ex.printStackTrace();
        }
    }
    
    public void cargarBusquedaEspecialistas()
    {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Pipec.class.getResource("/vista/BusquedaEspecialistas.fxml"));
            rootLayout = (BorderPane) loader.load();
            
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            //scene.getStylesheets().add("/vista/DarkTheme.css");
            
            secondaryStage.setScene(scene);
            secondaryStage.setResizable(true);
            
            BusquedaEspecialistasController controller = loader.getController();
            controller.setProgramaPrincipal(this);
            controller.setStage(secondaryStage);
            
            secondaryStage.show();
            
            
        } catch (IOException ex) {
            System.out.println("Error en el sistema: " + ex.toString());
            //ex.printStackTrace();
        }
    }
    
    public void cargarBusquedaCentroMedico()
    {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Pipec.class.getResource("/vista/BusquedaCentroMedico.fxml"));
            rootLayout = (BorderPane) loader.load();
            
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            //scene.getStylesheets().add("/vista/DarkTheme.css");
            
            secondaryStage.setScene(scene);
            secondaryStage.setResizable(true);
            
            BusquedaCentroMedicoController controller = loader.getController();
            controller.setProgramaPrincipal(this);
            controller.setStage(secondaryStage);
            
            secondaryStage.show();
            
            
        } catch (IOException ex) {
            System.out.println("Error en el sistema: " + ex.toString());
            //ex.printStackTrace();
        }
    }
    
    public void cargarVentanaReproduccion() //Método para mostrar la ventana donde se buscan las mediciones almacenadas del paciente
    {
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Pipec.class.getResource("/vista/BusquedaMedicion.fxml"));
            VBox menu = (VBox) loader.load();
            Stage ventana = new Stage();
            ventana.initOwner(primaryStage);
            ventana.setTitle("Busqueda mediciones");
            
            
            Scene scene = new Scene(menu);
            //scene.getStylesheets().add("/vista/menu.css");
            ventana.setScene(scene);
            BusquedaMedicionController controller = loader.getController();
            
            controller.setProgramaPrincipal(this);
            controller.setStage(ventana);
            controller.llenarTabla();
            ventana.show();
            ventana.setResizable(false);
            Rectangle2D ventanaPrimariaLimites = new Rectangle2D(640, 300, 640, 453);
            ventana.setX(ventanaPrimariaLimites.getMinX());
            ventana.setY(ventanaPrimariaLimites.getMinY());
            ventana.setWidth(ventanaPrimariaLimites.getWidth());
            ventana.setHeight(ventanaPrimariaLimites.getHeight());
            //primaryStage.close();
            
            //controller.setProgramaPrincipal(this);
            ventana.setOnCloseRequest(new EventHandler<WindowEvent>(){            
            @Override public void handle(WindowEvent event) {
                //event.consume(); //Consumar el evento
                ventana.close();
                
            }  
        });
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Error del sistema: " + ex.toString());
        }      

    }
    
    public void cargarVentanaMultiOnda() //Método para mostrar la ventana donde se buscan las mediciones almacenadas del paciente
    {
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Pipec.class.getResource("/vista/Reproduccion.fxml"));
            BorderPane menu = (BorderPane) loader.load();
            Stage ventana = new Stage();
            ventana.initOwner(primaryStage);
            ventana.setTitle("Reproducción de mediciones anteriores");
            
            
            Scene scene = new Scene(menu);
            //scene.getStylesheets().add("/vista/menu.css");
            ventana.setScene(scene);
            ReproduccionController controller = loader.getController();
            
            controller.setProgramaPrincipal(this);
            controller.setStage(ventana);
            
            ventana.show();
            ventana.setResizable(false);
            Rectangle2D ventanaPrimariaLimites = new Rectangle2D(10, 10, 1766, 990);
            ventana.setX(ventanaPrimariaLimites.getMinX());
            ventana.setY(ventanaPrimariaLimites.getMinY());
            ventana.setWidth(ventanaPrimariaLimites.getWidth());
            ventana.setHeight(ventanaPrimariaLimites.getHeight());
            //primaryStage.close();
            
            //controller.setProgramaPrincipal(this);
            ventana.setOnCloseRequest(new EventHandler<WindowEvent>(){            
            @Override public void handle(WindowEvent event) {
                //event.consume(); //Consumar el evento
                ventana.close();
                
            }  
        });
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Error del sistema: " + ex.toString());
        }      

    }
    
    private String ondaDetalle; //Este String almacena la onda que se va a abrir en la sección de Zoom.
    
    public void setOndaDetalle(String ondaRecibida)
    {
        ondaDetalle= ondaRecibida;
    }
    
    public void cargarVentanaDetalleOnda() //Método para mostrar la ventana donde se buscan las mediciones almacenadas del paciente
    {
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Pipec.class.getResource("/vista/OndaDetalle.fxml"));
            BorderPane menu = (BorderPane) loader.load();
            Stage ventana = new Stage();
            ventana.initOwner(primaryStage);
            ventana.setTitle("Detalle de Onda");
            
            
            Scene scene = new Scene(menu);
            //scene.getStylesheets().add("/vista/menu.css");
            ventana.setScene(scene);
            OndaDetalleController controller = loader.getController();
            
            controller.setProgramaPrincipal(this);
            controller.setStage(ventana);
            controller.setOndaReproducir(ondaDetalle);
            controller.iniciar();
            //controller.llenarChart();
            
            ventana.show();
            ventana.setResizable(false);
            Rectangle2D ventanaPrimariaLimites = new Rectangle2D(10, 10, 1766, 990);
            ventana.setX(ventanaPrimariaLimites.getMinX());
            ventana.setY(ventanaPrimariaLimites.getMinY());
            ventana.setWidth(ventanaPrimariaLimites.getWidth());
            ventana.setHeight(ventanaPrimariaLimites.getHeight());
            //primaryStage.close();
            
            //controller.setProgramaPrincipal(this);
            ventana.setOnCloseRequest(new EventHandler<WindowEvent>(){            
            @Override public void handle(WindowEvent event) {
                //event.consume(); //Consumar el evento
                ventana.close();
                
            }  
        });
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Error del sistema: " + ex.toString());
        }      

    }
    
    public void mostrarVentanaCharts(int idPersonalizado) //Método para mostrar la ventana donde van las gráficas de las mediciones personalizadas.
    {
        try {            
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Pipec.class.getResource("/vista/graficasEstadisticas.fxml"));
            VBox menu = (VBox) loader.load();
            Stage ventana = new Stage();
            ventana.initOwner(primaryStage);
            ventana.setTitle("Resultados medición personalizada");
            
            
            Scene scene = new Scene(menu);
            scene.getStylesheets().add("/vista/menu.css");
            ventana.setScene(scene);
            GraficasEstadisticasController controller = loader.getController();
            
            controller.setProgramaPrincipal(this);
            controller.setStage(ventana);
                
            controller.setIdPersonalizado(idPersonalizado);
            controller.obtenerMediciones();
            controller.dibujarCharts();
            ventana.show();
            ventana.setResizable(false);
            Rectangle2D ventanaPrimariaLimites = new Rectangle2D(10, 10, 1293, 1000);
            ventana.setX(ventanaPrimariaLimites.getMinX());
            ventana.setY(ventanaPrimariaLimites.getMinY());
            ventana.setWidth(ventanaPrimariaLimites.getWidth());
            ventana.setHeight(ventanaPrimariaLimites.getHeight());
            //primaryStage.close();
            
            //controller.setProgramaPrincipal(this);
            ventana.setOnCloseRequest(new EventHandler<WindowEvent>(){            
            @Override public void handle(WindowEvent event) {
                //event.consume(); //Consumar el evento
                ventana.close();
                
            }  
        });
        } catch (Exception ex) {       
            ex.printStackTrace();
            System.out.println("Error del sistema: " + ex.toString());
        }  
    }
    
    public void cargarAdminSesion()
    {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Pipec.class.getResource("/vista/AdminSesion.fxml"));
            BorderPane basica = (BorderPane) loader.load();
            //ventanaPrincipal = new Stage();
            //ventanaPrincipal.initModality(Modality.APPLICATION_MODAL);
            secondaryStage.setTitle("Administrar sesión");
            
            Scene scene = new Scene(basica);
            //scene.getStylesheets().add("/vista/menu.css");
            secondaryStage.setScene(scene);
            
            AdminSesionController adminSesion = loader.getController();              
            adminSesion.setProgramaPrincipal(this);
            adminSesion.setStage(secondaryStage);
            
            secondaryStage.show();

            //return estructuraBasica.retornarPanelParaVistasInternas();
            
        } catch (Exception ex) {
            System.out.println("Error del sistema: " + ex.toString());
            //return null;
        }
    }
    
    private Stage ventanaWebCam = null;    
    private WebCam webCam = null;
    public void mostrarVentanaFoto(EstructuraBasicaController refController) {
        if (refController.isOpenFoto()) {
        try {
            refController.setOpenFoto(false);
            if (ventanaWebCam==null){
                ventanaWebCam = new Stage();
                webCam = new WebCam(primaryStage, ventanaWebCam, refController);
                Scene scene = new Scene(webCam);                
                ventanaWebCam.setScene(scene);
//                ventanaWebCam.setHeight(350);
//                ventanaWebCam.setWidth(300);
//                ventanaWebCam.centerOnScreen();
//                ventanaWebCam.setResizable(false);
                ventanaWebCam.show();
            } else {
                ventanaWebCam.show();
                webCam.startWebCamCamera();
//                webCam.startWebCamStream();
            }
        } catch (Exception ex) {
            
        }
        } else {
            mensajePersonalizado("La ventana de la cámara ya está abierta", "Venata en uso");
            /*Dialogs.create()
                .title("Registro asistencia")
                .masthead("La ventana ya esta abierta.")
                .showInformation();*/
        }
    }
    
    
    
    public void tomarPresion()
    {
        mediciones.iniciarPresion();
        mediciones.leerPresion();
    }
    
    public void tomarPeso()
    {
        mediciones.leerPeso();       
    }
    
    public void actualizarPeso(double peso)
    {
        afinamientos.actualizarPeso(peso);
    }
    
    public void actualizarPresion(int sis, int dias)
    {
        ondas.actualizarPreion(sis, dias);
    }
    
    public void setPaciente(Pacientes paciente)
    {
        this.pacienteEnConsulta= paciente;                
    }
    
    public Pacientes getPaciente()
    {
        return pacienteEnConsulta;
    }
    
    //Este método guarda la foto del paciente para que sea cargada en cualquier pantalla
    private ImageView fotoTomada;
    public void cargarPaciente()
    {
        if(pacienteEnConsulta.getFoto()!=null)
        {
            InputStream in1 = new ByteArrayInputStream(pacienteEnConsulta.getFoto());
            Image image1 = new Image(in1);
            fotoTomada = new ImageView(image1); 
        }
               
    }
    
    public void mensajePersonalizado(String mensaje, String titulo)
    {
        Alert dialogoAlerta= new Alert(Alert.AlertType.INFORMATION);
        dialogoAlerta.setTitle(titulo);
        dialogoAlerta.setHeaderText(null);
        dialogoAlerta.setContentText(mensaje);
        dialogoAlerta.initStyle(StageStyle.UTILITY);
        dialogoAlerta.showAndWait(); 
    }
    
    public Stage getPrimaryStage()
    {
        return primaryStage;
    }

    public void setMedicionReproducir(Medicion medicion)
    {
        this.medicionReproducir= medicion;    
    }
    
    public Medicion getMedicionReproducir()
    {
        return medicionReproducir;    
    }
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       
        launch(args);
    }
    
}
