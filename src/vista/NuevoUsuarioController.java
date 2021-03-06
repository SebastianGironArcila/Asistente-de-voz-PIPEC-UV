/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import BD.ConexionDBs;
import BD.Usuarios;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.xml.bind.DatatypeConverter;

/**
 * FXML Controller class
 *
 * @author Miguel Askar
 */
public class NuevoUsuarioController implements Initializable {

    @FXML
    private Button buttonBack;
    @FXML
    private Button botonCambiarFoto;
    @FXML
    private Button crearUsuario;
    @FXML
    private TextField ajustesUsuario;
    @FXML
    private TextField ajustesIdentificacion;
    @FXML
    private TextField ajustesTelefono;
    @FXML
    private TextField ajustesNombre;
    
    private Pipec programaPrincipal;
    private Stage thisStage;
    
    private static SecretKey key;       
    private static Cipher cipher;  
    private static final String algoritmo = "AES";
    private static final int keysize = 16; 
    @FXML
    private TextField loginUsuario;
    @FXML
    private PasswordField loginPassword;
    @FXML
    private PasswordField contrasenia;
    @FXML
    private PasswordField contrasenia2;
    @FXML
    private ChoiceBox<String> tipoUsuario;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO 
        ObservableList<String> availableChoices = FXCollections.observableArrayList("M??dico", "Administrador"); 
        tipoUsuario.setItems(availableChoices);
        tipoUsuario.getSelectionModel().selectFirst();
    }    

    @FXML
    private void irAtras(ActionEvent event) {
        thisStage.close();
    }
    
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
    
    private Alert popup;
    
    public void CrearUsuario() {
        String mensaje;
        try {
            
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
                    String usuario = rs1.getString("usuario");
                    String nombre = rs1.getString("nombre");
                    String rol = rs1.getString("rol");
                    // Abrir la nueva ventana y cerrar la ventana de autenticar.
                    // programaPrincipal.mostrarVentanaRegPacientes(usuario, nombre, rol);
                    registrarUsuario();
                } else {
                    popup = new Alert(Alert.AlertType.ERROR);
                    popup.setTitle("Usuario");
                    popup.setHeaderText(null);
                    popup.setContentText("Error, usuario y/o contrase??a del admin incorrecto(s)");
                    popup.showAndWait();
                }
                if(rs1!=null) rs1.close();
                conexiones.desconectar();
            } else {
                popup = new Alert(Alert.AlertType.ERROR);
                popup.setTitle("Error conexi??n");
                popup.setHeaderText(null);
                popup.setContentText("Error en la conexi??n de la DB.");
                popup.showAndWait();
            }
        } catch (Exception ex) {
            popup = new Alert(Alert.AlertType.ERROR);
            popup.setTitle("Error DB");
            popup.setHeaderText(null);
            popup.setContentText("Error en la consulta de la DB.");
            popup.showAndWait();
            //ex.printStackTrace();
        }
    }
    
    public void registrarUsuario()
    {        
        EntityManager em = Persistence.createEntityManagerFactory("PipecPU").createEntityManager(); 
        em.getTransaction().begin(); 
        
        Usuarios usuario= new Usuarios();
        usuario.setUsuario(ajustesUsuario.getText());
        usuario.setIdusuario(ajustesIdentificacion.getText());
        usuario.setRol(tipoUsuario.getSelectionModel().getSelectedItem());
        usuario.setNombre(ajustesNombre.getText());
        usuario.setTelefono(ajustesTelefono.getText());
        usuario.setContrasena(encriptar(contrasenia.getText()));
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
        
        programaPrincipal.mensajePersonalizado("El usuario " + ajustesUsuario.getText()+ " ha sido creado con ??xito", "Usuario creado con ??xito");
        thisStage.close();
        
        
    }
    
}
