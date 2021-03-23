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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
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
public class AjustesController extends EstructuraBasicaController implements Initializable {

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
    @FXML
    private ImageView imagenUsuario;
    @FXML
    private Button botonActualizar;
    @FXML
    private TextField ajustesUsuario;
    @FXML
    private TextField ajustesIdentificacion;
    @FXML
    private TextField ajustesTelefono;
    @FXML
    private TextField ajustesNombre;
    @FXML
    private ChoiceBox<?> ajustesIdioma;
    @FXML
    private Button crearSmartcard;
    @FXML
    private Button actualizarSmartCard;

    
    private Usuarios usuario;
    @FXML
    private PasswordField nuevoPass;
    @FXML
    private PasswordField loginPassword;
    @FXML
    private PasswordField confirmacionPass;
    @FXML
    private Button buttonVariabilidad;
    @FXML
    private Button buttonWearables;
    @FXML
    private Button actualizarPass;
    @FXML
    private Button consultaCentrosMedicos;
    @FXML
    private Button consultarFarmacias;
    @FXML
    private Button consultaEspecialistas;
    @FXML
    private Button medicionesAnteriores;
    @FXML
    private ImageView imagenUsuario1;
    @FXML
    private Button medicamentos;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.inicializarBotonesEstructura();
        
        //Imagen botón de actualizar datos
        Image selected    = new Image(
          "\\images\\botones\\botonGrandeAjustes.png"
        );
        ImageView imagenFondo = new ImageView();
        imagenFondo.setImage(selected);
        botonActualizar.setGraphic(imagenFondo);    
        botonActualizar.setBackground(Background.EMPTY);
        botonActualizar.setPadding(Insets.EMPTY);
        
        //Imagen botón de cambiar contraseña
        selected    = new Image(
          "\\images\\botones\\botonGrandeAjustes.png"
        );
        imagenFondo = new ImageView();
        imagenFondo.setImage(selected);
        actualizarPass.setGraphic(imagenFondo);    
        actualizarPass.setBackground(Background.EMPTY);
        actualizarPass.setPadding(Insets.EMPTY);
        
        //Imagen botón de crear smart card
        selected    = new Image(
          "\\images\\botones\\botonPequeAjustes.png"
        );
        imagenFondo = new ImageView();
        imagenFondo.setImage(selected);
        crearSmartcard.setGraphic(imagenFondo);    
        crearSmartcard.setBackground(Background.EMPTY);
        crearSmartcard.setPadding(Insets.EMPTY);
        
        //Imagen botón de actualizar smart card
        selected    = new Image(
          "\\images\\botones\\botonPequeAjustes.png"
        );
        imagenFondo = new ImageView();
        imagenFondo.setImage(selected);
        actualizarSmartCard.setGraphic(imagenFondo);    
        actualizarSmartCard.setBackground(Background.EMPTY);
        actualizarSmartCard.setPadding(Insets.EMPTY);
        
    }
    
    @FXML
    public void irAAjustes()
    {
        mostrarMensaje("Ya te encuentras en esta sección"); 
    } 
    
    public void setUsuario(Usuarios usuario)
    {
        this.usuario= usuario;
    }
    
    public Usuarios getUsuario()
    {
        return usuario;
    }
    
    public void cargarUsuario()
    {
        ajustesUsuario.setText(usuario.getUsuario());
        ajustesNombre.setText(usuario.getNombre());
        ajustesTelefono.setText(usuario.getTelefono());
        ajustesIdentificacion.setText(usuario.getIdusuario());
    }
    
    @FXML
    public void actualizarDatos()
    {
        try
        {
            EntityManager em = Persistence.createEntityManagerFactory("PipecPU").createEntityManager(); 
            em.getTransaction().begin(); 
            Usuarios usuarioActualizar= new Usuarios();
            usuarioActualizar.setUsuario(ajustesUsuario.getText());
            usuarioActualizar.setIdusuario(ajustesIdentificacion.getText());
            usuarioActualizar.setRol(usuario.getRol());
            usuarioActualizar.setNombre(ajustesNombre.getText());
            usuarioActualizar.setTelefono(ajustesTelefono.getText());
            usuarioActualizar.setContrasena(usuario.getContrasena());
            usuarioActualizar.setBloqueado("0");
            //Se obtiene la fecha actual
            Date date= new Date();
            try {                        
                date =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
            } catch (ParseException ex) {
                Logger.getLogger(NuevoUsuarioController.class.getName()).log(Level.SEVERE, null, ex);
            }
            java.sql.Timestamp fechaGuardar = new java.sql.Timestamp(date.getTime());   
            //--------------------------
            usuarioActualizar.setFechaIngreso(usuario.getFechaIngreso());
            usuarioActualizar.setUltimaModificacion(fechaGuardar);
            em.merge(usuarioActualizar);
            em.getTransaction().commit();
        
            programaPrincipal.mensajePersonalizado("El usuario " + ajustesUsuario.getText()+ " ha sido actualizado con éxito", "Usuario actualizado con éxito");
            
        }catch(Exception e)
        {
            programaPrincipal.mensajePersonalizado("Error en los datos ingresados, verifique el nombre de usuario", "Error al actualizar");
        }
        
    }
    
    private static SecretKey key;       
    private static Cipher cipher;  
    private static final String algoritmo = "AES";
    private static final int keysize = 16; 
    
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
    public void actualizarPassword()
    {
        String mensaje;
        try {
            
            ConexionDBs conexiones = new ConexionDBs();
            String val = conexiones.conectar();
            if (val.equals("OK")) {
                Statement stmt1 = conexiones.db1.createStatement();
                addKey("Fergon");
                String palabra = encriptar(loginPassword.getText());
                mensaje = "Select * From usuarios Where usuario = '" + usuario.getUsuario() + "' and contrasena = '" + palabra + "'";
                ResultSet rs1 = stmt1.executeQuery(mensaje);
                rs1.last();
                int resultado = rs1.getRow();
                if (resultado > 0){
                    //rs1.first();
                    if(nuevoPass.getText().equals(confirmacionPass.getText()))
                    {
                        actualizarPassBD();
                    }else
                    {
                        programaPrincipal.mensajePersonalizado("Los campos de contraseña no coinciden", "Error al actualizar la contraseña");
                    }                    
                    
                } else {
                    popup = new Alert(Alert.AlertType.ERROR);
                    popup.setTitle("Usuario");
                    popup.setHeaderText(null);
                    popup.setContentText("Error, contraseña del admin incorrecta");
                    popup.showAndWait();
                }
                if(rs1!=null) rs1.close();
                conexiones.desconectar();
            } else {
                popup = new Alert(Alert.AlertType.ERROR);
                popup.setTitle("Error conexión");
                popup.setHeaderText(null);
                popup.setContentText("Error en la conexión de la DB.");
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
    
    private void actualizarPassBD()
    {
        try
        {
            EntityManager em = Persistence.createEntityManagerFactory("PipecPU").createEntityManager(); 
            em.getTransaction().begin(); 
            Usuarios usuarioActualizar= new Usuarios();
            usuarioActualizar.setUsuario(usuario.getUsuario());
            usuarioActualizar.setIdusuario(usuario.getIdusuario());
            usuarioActualizar.setRol(usuario.getRol());
            usuarioActualizar.setNombre(usuario.getNombre());
            usuarioActualizar.setTelefono(usuario.getTelefono());
            usuarioActualizar.setContrasena(encriptar(nuevoPass.getText()));
            usuarioActualizar.setBloqueado("0");
            //Se obtiene la fecha actual
            Date date= new Date();
            try {                        
                date =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
            } catch (ParseException ex) {
                Logger.getLogger(NuevoUsuarioController.class.getName()).log(Level.SEVERE, null, ex);
            }
            java.sql.Timestamp fechaGuardar = new java.sql.Timestamp(date.getTime());
            //--------------------------
            usuarioActualizar.setFechaIngreso(usuario.getFechaIngreso());
            usuarioActualizar.setUltimaModificacion(fechaGuardar);
            em.merge(usuarioActualizar);
            em.getTransaction().commit();
        
            programaPrincipal.mensajePersonalizado("La contraseña del usuario " + ajustesUsuario.getText()+ " ha sido actualizado con éxito", "Contraseña actualizada con éxito");
            
        }catch(Exception e)
        {
            programaPrincipal.mensajePersonalizado("Error en los datos ingresados, verifique el nombre de usuario", "Error al actualizar");
        }
    }
    
    @FXML
    private void escribirEnTarjeta(ActionEvent event) {
    }

    @FXML
    private void actualizarTarjeta(ActionEvent event) {
    }    

    
}
