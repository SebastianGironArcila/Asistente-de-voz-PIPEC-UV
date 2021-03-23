/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebService;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.stage.StageStyle;

/**
 *
 * @author migma
 */
public class PacienteRemoto {
    
    private String idDispositivo= "1234"; //Este se debe capturar posteriormente desde la BD.
    private int codigoGestor; //Este atributo permite identificar el gestor en el WebService.
    private int testigoPaciente;
    private int testigoMedico;
    private int contadorTramas;
    
    public PacienteRemoto()
    {
        testigoPaciente= 0;
        contadorTramas= 0;
    }
    
    public void conectarConWebService() //Este método se ejecuta primero para enlazarse con el webService
    {
        codigoGestor= crearGestor(idDispositivo);
    }  
    
    public void transmitirTestigo()
    {
        testigoPaciente++;
        enviarTestigoPaciente(codigoGestor, String.valueOf(testigoPaciente));
        
        
    }
    

    public void transmitirTramas(String trama1, String trama2)
    {
        enviarDatosMedicos(codigoGestor, contadorTramas + "$" + trama1 + "$" + trama2);
        contadorTramas++;
    }
    
    public String recibirOrdenesMedico()
    {
        return recibirOrdenes(codigoGestor);
    }
    
    public String recibirTestigo()
    {
        return recibirTestigoMedico(codigoGestor);
    }
    
    
    private static int crearGestor(java.lang.String idDispositivo) {
        WebService.Service service = new WebService.Service();
        WebService.ServiceSoap port = service.getServiceSoap12();
        return port.crearGestor(idDispositivo);
    }

    private static void enviarDatosMedicos(int gestor, java.lang.String datos) {
        WebService.Service service = new WebService.Service();
        WebService.ServiceSoap port = service.getServiceSoap12();
        port.enviarDatosMedicos(gestor, datos);
    }

    private static void enviarTestigoPaciente(int gestor, java.lang.String datos) {
        WebService.Service service = new WebService.Service();
        WebService.ServiceSoap port = service.getServiceSoap12();
        port.enviarTestigoPaciente(gestor, datos);
    }

    private static String recibirOrdenes(int gestor) {
        WebService.Service service = new WebService.Service();
        WebService.ServiceSoap port = service.getServiceSoap12();
        return port.recibirOrdenes(gestor);
    }

    private static String recibirTestigoMedico(int gestor) {
        WebService.Service service = new WebService.Service();
        WebService.ServiceSoap port = service.getServiceSoap12();
        return port.recibirTestigoMedico(gestor);
    }
    
    
    
}
