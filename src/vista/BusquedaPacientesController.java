/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import BD.Pacientes;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 * FXML Controller class
 *
 * @author Miguel Askar
 */
public class BusquedaPacientesController implements Initializable {
    
    @FXML
    private TextField infoBusqueda;
    @FXML
    private TableView<Pacientes> tablaPacientes;
    @FXML
    private Button buttonOk;
    @FXML
    private Button buttonCancel;

    private Pipec programaPrincipal;
    
    private Stage thisStage;
    
    @FXML
    private TableColumn<Pacientes, String> primerApellido;
    @FXML
    private TableColumn<Pacientes, String> segundoApellido;
    @FXML
    private TableColumn<Pacientes, String> tercerApellido;
    @FXML
    private TableColumn<Pacientes, String> cuartoApellido;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //tipoID.setCellValueFactory(new PropertyValueFactory<>("tipoid"));
        //numeroID.setCellValueFactory(new PropertyValueFactory<>("identificacion"));
        primerApellido.setCellValueFactory(new PropertyValueFactory<>("apellido1"));
        segundoApellido.setCellValueFactory(new PropertyValueFactory<>("apellido2"));
        tercerApellido.setCellValueFactory(new PropertyValueFactory<>("nombre1"));
        cuartoApellido.setCellValueFactory(new PropertyValueFactory<>("nombre2"));
        
        EntityManager em = Persistence.createEntityManagerFactory("PipecPU").createEntityManager();   
        
        //Query queryMedicionFindAll = em.createNativeQuery("SELECT * FROM pacientes p ORDER BY apellido1 ASC", Pacientes.class);
        List<Pacientes> listPacientes = em.createNamedQuery("Pacientes.findAll", Pacientes.class).getResultList();
        /*Query queryMedicionFindAll = em.createNativeQuery("SELECT e.tipoid AS, e.identificacion, e.nombre1, e.nombre2,"
                + " e.apellido1, e.apellido2 FROM pacientes e", Pacientes.class);*/
        //List<Pacientes> listPacientes = queryMedicionFindAll.getResultList();         
                
        
        tablaPacientes.setItems(FXCollections.observableArrayList(listPacientes));
        tablaPacientes.getSelectionModel().select(0);
        infoBusqueda.requestFocus();
    } 
    
    @FXML
    public void buscarPersonalizado()
    {
        String busqueda= infoBusqueda.getText();
        
        EntityManager em = Persistence.createEntityManagerFactory("PipecPU").createEntityManager();   
        
        Query queryMedicionFindAll = em.createNativeQuery("SELECT * FROM Pacientes WHERE identificacion LIKE \"%" + busqueda
                + "%\" OR nombre1 LIKE\"%" + busqueda + "%\" OR nombre2 LIKE\"%" + busqueda 
                + "%\" OR apellido1 LIKE\"%"+ busqueda +"%\" OR apellido2 LIKE\"%"
                + busqueda + "%\"", Pacientes.class);
        List<Pacientes> listPacientes = queryMedicionFindAll.getResultList();
        tablaPacientes.setItems(FXCollections.observableArrayList(listPacientes));
    }
    
    @FXML
    public void cargarPaciente()
    {
        programaPrincipal.setPaciente(tablaPacientes.getSelectionModel().getSelectedItem());
        programaPrincipal.cargarPaciente();
        this.thisStage.close();
    }
    
    @FXML
    public void cerrarVentana()
    {
        this.thisStage.close();
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
    
}
