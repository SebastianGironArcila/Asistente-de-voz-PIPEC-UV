
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
 *         &lt;element name="RecibirDatosMedicosResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "recibirDatosMedicosResult"
})
@XmlRootElement(name = "RecibirDatosMedicosResponse")
public class RecibirDatosMedicosResponse {

    @XmlElement(name = "RecibirDatosMedicosResult")
    protected String recibirDatosMedicosResult;

    /**
     * Obtiene el valor de la propiedad recibirDatosMedicosResult.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRecibirDatosMedicosResult() {
        return recibirDatosMedicosResult;
    }

    /**
     * Define el valor de la propiedad recibirDatosMedicosResult.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRecibirDatosMedicosResult(String value) {
        this.recibirDatosMedicosResult = value;
    }

}
