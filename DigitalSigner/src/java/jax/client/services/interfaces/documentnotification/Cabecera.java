
package jax.client.services.interfaces.documentnotification;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para cabecera complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="cabecera"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="coUnidad" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "cabecera", propOrder = {
    "coUnidad"
})
public class Cabecera {

    @XmlElement(required = true)
    protected String coUnidad;

    /**
     * Obtiene el valor de la propiedad coUnidad.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCoUnidad() {
        return coUnidad;
    }

    /**
     * Define el valor de la propiedad coUnidad.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCoUnidad(String value) {
        this.coUnidad = value;
    }

}
