/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import BD.Farmacias;
import BD.Pacientes;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 * FXML Controller class
 *
 * @author Miguel Askar
 */
public class BusquedaFarmaciasController implements Initializable {

    @FXML
    private TextField infoBusqueda;
    @FXML
    private TableView<Farmacias> tabla;
    @FXML
    private TableColumn<Farmacias, String> nombre;
    @FXML
    private TableColumn<Farmacias, String> zona;
    @FXML
    private TableColumn<Farmacias, String> direccion;
    @FXML
    private TableColumn<Farmacias, String> telefono;

    
    private Pipec programaPrincipal;
    
    private Stage thisStage;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        zona.setCellValueFactory(new PropertyValueFactory<>("zona"));
        direccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        telefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        
        EntityManager em = Persistence.createEntityManagerFactory("PipecPU").createEntityManager();   
        
        //Query queryMedicionFindAll = em.createNativeQuery("SELECT * FROM pacientes p ORDER BY apellido1 ASC", Pacientes.class);
        List<Farmacias> lista = em.createNamedQuery("Farmacias.findAll", Farmacias.class).getResultList();
        /*Query queryMedicionFindAll = em.createNativeQuery("SELECT e.tipoid AS, e.identificacion, e.nombre1, e.nombre2,"
                + " e.apellido1, e.apellido2 FROM pacientes e", Pacientes.class);*/
        //List<Pacientes> listPacientes = queryMedicionFindAll.getResultList();         
                
        
        tabla.setItems(FXCollections.observableArrayList(lista));
        tabla.getSelectionModel().select(0);
        infoBusqueda.requestFocus();
    } 
    
    @FXML
    public void buscarPersonalizado()
    {
        String busqueda= infoBusqueda.getText();
        
        EntityManager em = Persistence.createEntityManagerFactory("PipecPU").createEntityManager();   
        
        Query queryMedicionFindAll = em.createNativeQuery("SELECT * FROM farmacias WHERE nombre LIKE \"%" + busqueda
                +"%\" OR zona LIKE\"%"
                + busqueda + "%\"", Farmacias.class);
        List<Farmacias> lista = queryMedicionFindAll.getResultList();
        tabla.setItems(FXCollections.observableArrayList(lista));
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
