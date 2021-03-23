
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
 *         &lt;element name="RecibirTestigoMedicoResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "recibirTestigoMedicoResult"
})
@XmlRootElement(name = "RecibirTestigoMedicoResponse")
public class RecibirTestigoMedicoResponse {

    @XmlElement(name = "RecibirTestigoMedicoResult")
    protected String recibirTestigoMedicoResult;

    /**
     * Obtiene el valor de la propiedad recibirTestigoMedicoResult.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRecibirTestigoMedicoResult() {
        return recibirTestigoMedicoResult;
    }

    /**
     * Define el valor de la propiedad recibirTestigoMedicoResult.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRecibirTestigoMedicoResult(String value) {
        this.recibirTestigoMedicoResult = value;
    }

}
