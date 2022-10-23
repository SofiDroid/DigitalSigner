
package jax.client.services.interfaces.documentnotification;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para documento complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="documento"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="blDocumento" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/&gt;
 *         &lt;element name="coExtension" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="coFichero" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="coSituacionDoc" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="coTipoDocumento" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="dsDocumento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="dsObservaciones" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "documento", propOrder = {
    "blDocumento",
    "coExtension",
    "coFichero",
    "coSituacionDoc",
    "coTipoDocumento",
    "dsDocumento",
    "dsObservaciones"
})
public class Documento {

    protected byte[] blDocumento;
    @XmlElement(required = true)
    protected String coExtension;
    @XmlElement(required = true)
    protected String coFichero;
    @XmlElement(required = true)
    protected String coSituacionDoc;
    @XmlElement(required = true)
    protected String coTipoDocumento;
    protected String dsDocumento;
    protected String dsObservaciones;

    /**
     * Obtiene el valor de la propiedad blDocumento.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getBlDocumento() {
        return blDocumento;
    }

    /**
     * Define el valor de la propiedad blDocumento.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setBlDocumento(byte[] value) {
        this.blDocumento = value;
    }

    /**
     * Obtiene el valor de la propiedad coExtension.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCoExtension() {
        return coExtension;
    }

    /**
     * Define el valor de la propiedad coExtension.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCoExtension(String value) {
        this.coExtension = value;
    }

    /**
     * Obtiene el valor de la propiedad coFichero.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCoFichero() {
        return coFichero;
    }

    /**
     * Define el valor de la propiedad coFichero.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCoFichero(String value) {
        this.coFichero = value;
    }

    /**
     * Obtiene el valor de la propiedad coSituacionDoc.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCoSituacionDoc() {
        return coSituacionDoc;
    }

    /**
     * Define el valor de la propiedad coSituacionDoc.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCoSituacionDoc(String value) {
        this.coSituacionDoc = value;
    }

    /**
     * Obtiene el valor de la propiedad coTipoDocumento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCoTipoDocumento() {
        return coTipoDocumento;
    }

    /**
     * Define el valor de la propiedad coTipoDocumento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCoTipoDocumento(String value) {
        this.coTipoDocumento = value;
    }

    /**
     * Obtiene el valor de la propiedad dsDocumento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDsDocumento() {
        return dsDocumento;
    }

    /**
     * Define el valor de la propiedad dsDocumento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDsDocumento(String value) {
        this.dsDocumento = value;
    }

    /**
     * Obtiene el valor de la propiedad dsObservaciones.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDsObservaciones() {
        return dsObservaciones;
    }

    /**
     * Define el valor de la propiedad dsObservaciones.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDsObservaciones(String value) {
        this.dsObservaciones = value;
    }

}
