/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author migma
 */
public class AdminSesionController implements Initializable {

    @FXML
    private Button botonCambiarFoto;
    @FXML
    private Button botonSalir;
    @FXML
    private Button botonCerrar;
    
    private Pipec programaPrincipal;
    private Stage thisStage;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void setProgramaPrincipal(Pipec programaPrincipal) {
        this.programaPrincipal = programaPrincipal;
    }

    public Pipec getProgramaPrincipal() {
        return programaPrincipal;
    }
    
    public void setStage(Stage stage)
    {
        this.thisStage= stage;
    }
    
}
