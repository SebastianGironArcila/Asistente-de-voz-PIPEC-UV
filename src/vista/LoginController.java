/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import AsistenteVirtual_v1.SpeechRecognition;
import AsistenteVirtual_v1.SpeechToText_v1;
import BD.ConexionDBs;
import BD.Usuarios;
import com.ibm.watson.speech_to_text.v1.SpeechToText;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.swing.text.html.HTMLDocument;
import javax.xml.bind.DatatypeConverter;

/**
 * FXML Controller class
 *
 * @author Miguel Askar
 */
public class LoginController implements Initializable {

    @FXML
    private TextField loginUsuario;
    @FXML
    private PasswordField loginPassword;

    /**
     * Initializes the controller class.
     */
    
    //Initializes programmer-defined attribs
    private Pipec programaPrincipal;
    @FXML
    private Button botonLogin;
    
    private SpeechToText_v1 stt;
    private SpeechRecognition sr;
            
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO        
        Image login    = new Image
        (
          "\\images\\botones\\login.png"
        );
        ImageView imagenFondo = new ImageView();
        imagenFondo.setImage(login);
        botonLogin.setGraphic(imagenFondo); 
        
        botonLogin.setBackground(Background.EMPTY);
        botonLogin.setPadding(Insets.EMPTY);
        loginUsuario.setBackground(Background.EMPTY);
        loginUsuario.setStyle("-fx-text-inner-color: white;");
        loginPassword.setBackground(Background.EMPTY);
        loginPassword.setStyle("-fx-text-inner-color: white;");
        sr = new SpeechRecognition();
    }    
    
    public void setProgramaPrincipal(Pipec programaPrincipal) {
        this.programaPrincipal = programaPrincipal ;
    }

    public Pipec getProgramaPrincipal() {
        return programaPrincipal;
    }
    
    public void iniciarSesion(Usuarios usuario)
    {
        programaPrincipal.irMenuPrincipal(usuario);
    }
    
    /*public void setStt(SpeechToText_v1 stt){
        this.stt = stt;
    }*/
    
    public void setUsername(String username){
        loginUsuario.setText(username);
    }
    
    public void setPassword(String password){
        loginPassword.setText(password);
    }
    
    //Código viejo agregado y por revisar
    Alert popup;
    
    private static SecretKey key;       
    private static Cipher cipher;  
    private static final String algoritmo = "AES";
    private static final int keysize = 16; 
    
    @FXML
    public void autenticarUsuario()
    {
        Image login    = new Image
        (
          "\\images\\botones\\loginSelected.png"
        );
        ImageView imagenFondo = new ImageView();
        imagenFondo.setImage(login);
        botonLogin.setGraphic(imagenFondo);
        Timer timerIniciar;
        timerIniciar = new Timer();             
        TimerTask taskPrimera = new TimerTask() 
        {

            @Override
            public void run()
            {
                Autenticar();
            }
        }; 
        // Empezamos dentro de 0s 
        timerIniciar.schedule(taskPrimera, 10);
    }
    
    
    private void Autenticar() {
        String mensaje;
        try {
            
            EntityManager em = Persistence.createEntityManagerFactory("PipecPU").createEntityManager(); 
            ConexionDBs conexiones = new ConexionDBs();
            String val = conexiones.conectar();
            if (val.equals("OK")) {
                Statement stmt1 = conexiones.db1.createStatement();
                addKey("Fergon");
                String palabra = encriptar(loginPassword.getText());
                mensaje = "Select * From usuarios Where usuario = '" + loginUsuario.getText() + "' and contrasena = '" + palabra + "'";
                ResultSet rs1 = stmt1.executeQuery(mensaje);
                rs1.last();
                int resultado = rs1.getRow();
                if (resultado > 0){
                    rs1.first();                    
                    // Abrir la nueva ventana y cerrar la ventana de autenticar.
                    // programaPrincipal.mostrarVentanaRegPacientes(usuario, nombre, rol);
                    Usuarios usuarioBD= new Usuarios();
                    usuarioBD.setUsuario(rs1.getString("usuario"));
                    usuarioBD.setIdusuario(rs1.getString("idusuario"));
                    usuarioBD.setRol(rs1.getString("rol"));
                    usuarioBD.setNombre(rs1.getString("nombre"));
                    usuarioBD.setTelefono(rs1.getString("telefono"));
                    usuarioBD.setContrasena(rs1.getString("contrasena"));
                    usuarioBD.setBloqueado(rs1.getString("bloqueado"));
                    usuarioBD.setFechaIngreso(rs1.getDate("fecha_ingreso"));
                    usuarioBD.setUltimaModificacion(rs1.getDate("ultima_modificacion"));
                    
                    
                    Timer timerLogin;
                    timerLogin = new Timer();             
                    TimerTask taskIniciar = new TimerTask() 
                    {

                        @Override
                        public void run()
                        {
                            Platform.runLater(new Runnable(){
                                @Override
                                public void run() { 
                                    iniciarSesion(usuarioBD);
                                    Image login    = new Image
                                    (
                                      "\\images\\botones\\login.png"
                                    );
                                    ImageView imagenFondo = new ImageView();
                                    imagenFondo.setImage(login);
                                    botonLogin.setGraphic(imagenFondo);

                                }
                            });

                        }
                    }; 
                    System.out.println("Se loguio bien");
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            sr.setEstadoLogin(true);
                        }
                    });
                    
                    // Empezamos dentro de 60s 
                    timerLogin.schedule(taskIniciar, 100);                    
                    
                } else {
                    Image login    = new Image
                    (
                      "\\images\\botones\\login.png"
                    );
                    ImageView imagenFondo = new ImageView();
                    imagenFondo.setImage(login);
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            botonLogin.setGraphic(imagenFondo);
                        }
                    });
                    
                    
                    popup = new Alert(Alert.AlertType.ERROR);
                    popup.setTitle("Usuario");
                    popup.setHeaderText(null);
                    popup.setContentText("Error, usuario y/o contraseña incorrecto(s)");
                    popup.showAndWait();
                }
                if(rs1!=null) rs1.close();
                conexiones.desconectar();
            } else {
                Image login    = new Image
                (
                  "\\images\\botones\\login.png"
                );
                ImageView imagenFondo = new ImageView();
                imagenFondo.setImage(login);
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        botonLogin.setGraphic(imagenFondo);
                    }
                });
                
                
                popup = new Alert(Alert.AlertType.ERROR);
                popup.setTitle("Error conexión");
                popup.setHeaderText(null);
                popup.setContentText("Error en la conexión de la DB.");
                popup.showAndWait();
            }
        } catch (Exception ex) {
            Image login    = new Image
            (
              "\\images\\botones\\login.png"
            );
            ImageView imagenFondo = new ImageView();
            imagenFondo.setImage(login);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    
                    botonLogin.setGraphic(imagenFondo);
                    popup = new Alert(Alert.AlertType.ERROR);
                    popup.setTitle("Error DB");
                    popup.setHeaderText(null);
                    popup.setContentText("Error en la consulta de la DB.");
                    popup.showAndWait();
                }
            });
           
            
            
        }
    }
    
    public void addKey( String value ){
        byte[] valuebytes = value.getBytes();            
        key = new SecretKeySpec( Arrays.copyOf( valuebytes, keysize ) , algoritmo );      
    }
    
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
    
    @FXML
    public void irARegistro()
    {
        programaPrincipal.cargarRegistro();
    }
    
}
