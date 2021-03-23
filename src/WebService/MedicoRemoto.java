/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebService;

import javafx.scene.control.Alert;
import javafx.stage.StageStyle;

/**
 *
 * @author migma
 */
public class MedicoRemoto 
{
    private int codigoGestor;
    private int testigoPaciente;
    private int testigoMedico;
    
    public MedicoRemoto()
    {
        
        testigoMedico= 0;       
    }
    
    public void conectarConDispositivo(String idDispositivo)
    {
        int gestor= buscarGestor("MHU");
        if(gestor==0)
        {
            mensajePersonalizado("Verifique que el paciente esté conectado a la red", "Dispositivo del paciente no encontrado");
        }else
        {
            System.out.println("Conexión establecida con el paciente Conexión exitosa");
            mensajePersonalizado("Conexión establecida con el paciente", "Conexión exitosa");
            codigoGestor= gestor;
        }
    }
    
    public void transmitirTestigo()
    {
        testigoMedico++;
        enviarTestigoMedico(codigoGestor, String.valueOf(testigoMedico));
    }
    
    public String recibirDatos()
    {
        return recibirDatosMedicos(codigoGestor);
    }
    
    public void enviarOrdenes(String orden)
    {
        enviarOrdenes(codigoGestor, orden);
    }
    
    public String recibirTestigo()
    {
        return recibirTestigoPaciente(codigoGestor);
    }
    
    public void enviarTramas(String tramas)
    {
        enviarDatosMedicos(codigoGestor, tramas);
    }

    private static int buscarGestor(java.lang.String idDispositivo) {
        WebService.Service service = new WebService.Service();
        WebService.ServiceSoap port = service.getServiceSoap12();
        return port.buscarGestor(idDispositivo);
    }

    private static String recibirDatosMedicos(int gestor) {
        WebService.Service service = new WebService.Service();
        WebService.ServiceSoap port = service.getServiceSoap12();
        return port.recibirDatosMedicos(gestor);
    }

    private static void enviarOrdenes(int gestor, java.lang.String datos) {
        WebService.Service service = new WebService.Service();
        WebService.ServiceSoap port = service.getServiceSoap12();
        port.enviarOrdenes(gestor, datos);
    }
        

    private static String recibirTestigoPaciente(int gestor) {
        WebService.Service service = new WebService.Service();
        WebService.ServiceSoap port = service.getServiceSoap12();
        return port.recibirTestigoPaciente(gestor);
    }   
    

    private static void enviarTestigoMedico(int gestor, java.lang.String datos) {
        WebService.Service service = new WebService.Service();
        WebService.ServiceSoap port = service.getServiceSoap12();
        port.enviarTestigoMedico(gestor, datos);
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

    private static void enviarDatosMedicos(int gestor, java.lang.String datos) {
        WebService.Service service = new WebService.Service();
        WebService.ServiceSoap port = service.getServiceSoap();
        port.enviarDatosMedicos(gestor, datos);
    }
    
    
    
    
    
}
