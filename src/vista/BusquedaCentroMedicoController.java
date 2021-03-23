/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import BD.Centromedico;

import BD.Farmacias;
import com.sun.deploy.uitoolkit.impl.fx.HostServicesFactory;
import com.sun.javafx.application.HostServicesDelegate;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 * FXML Controller class
 *
 * @author Miguel Askar
 */
public class BusquedaCentroMedicoController extends BusquedaFarmaciasController implements Initializable {

    @FXML
    private TextField infoBusqueda;
    @FXML
    private TableView<Centromedico> tabla;
    @FXML
    private TableColumn<Centromedico, String> nombre;
    @FXML
    private TableColumn<Centromedico, String> zona;
    @FXML
    private TableColumn<Centromedico, String> direccion;
    @FXML
    private TableColumn<Centromedico, String> telefono;
    @FXML
    private TableColumn<Centromedico, String> geolocalizacion;
    @FXML
    private TableColumn<Centromedico, String> servicios;

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
        geolocalizacion.setCellValueFactory(new PropertyValueFactory<>("gelocalizacion"));
        servicios.setCellValueFactory(new PropertyValueFactory<>("servicios"));
        
        EntityManager em = Persistence.createEntityManagerFactory("PipecPU").createEntityManager();   
        
        //Query queryMedicionFindAll = em.createNativeQuery("SELECT * FROM pacientes p ORDER BY apellido1 ASC", Pacientes.class);
        List<Centromedico> lista = em.createNamedQuery("Centromedico.findAll", Centromedico.class).getResultList();
        /*Query queryMedicionFindAll = em.createNativeQuery("SELECT e.tipoid AS, e.identificacion, e.nombre1, e.nombre2,"
                + " e.apellido1, e.apellido2 FROM pacientes e", Pacientes.class);*/
        //List<Pacientes> listPacientes = queryMedicionFindAll.getResultList();         
                
        
        tabla.setItems(FXCollections.observableArrayList(lista));
        tabla.getSelectionModel().select(0);
        infoBusqueda.requestFocus();
        habilitarGeolocalizacion();
    } 
    
    @FXML
    public void buscarPersonalizado()
    {
        String busqueda= infoBusqueda.getText();
        
        EntityManager em = Persistence.createEntityManagerFactory("PipecPU").createEntityManager();   
        
        Query queryMedicionFindAll = em.createNativeQuery("SELECT * FROM centromedico WHERE nombre LIKE \"%" + busqueda
                +"%\" OR zona LIKE\"%"
                + busqueda + "%\"", Centromedico.class);
        List<Centromedico> lista = queryMedicionFindAll.getResultList();
        tabla.setItems(FXCollections.observableArrayList(lista));
    }
    
    private void habilitarGeolocalizacion()
    {
        System.out.println("Aquí entra");
        tabla.setOnMousePressed(new EventHandler<MouseEvent>() {
        @Override 
        public void handle(MouseEvent event) {
            if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                String link= tabla.getSelectionModel().getSelectedItem().getGelocalizacion();
                openWebpage(link);
            }
    }
        });
    }
    
    public static void openWebpage(String url) {
        try {
            Desktop.getDesktop().browse(new URL(url).toURI());
        } catch (IOException e) {
            //e.printStackTrace();
        } catch (URISyntaxException e) {
            //e.printStackTrace();
        }
    }
    
}
