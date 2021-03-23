
package WebService;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para anonymous complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="BuscarGestorResult" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "buscarGestorResult"
})
@XmlRootElement(name = "BuscarGestorResponse")
public class BuscarGestorResponse {

    @XmlElement(name = "BuscarGestorResult")
    protected int buscarGestorResult;

    /**
     * Obtiene el valor de la propiedad buscarGestorResult.
     * 
     */
    public int getBuscarGestorResult() {
        return buscarGestorResult;
    }

    /**
     * Define el valor de la propiedad buscarGestorResult.
     * 
     */
    public void setBuscarGestorResult(int value) {
        this.buscarGestorResult = value;
    }

}
