
package jax.client.services.interfaces.mailservice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para enviarMail complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="enviarMail"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="textoHTML" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="asunto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="adjuntos" type="{http://main/}dataFiles" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="from" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="destinatarios" type="{http://main/}destinatarios" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "enviarMail", propOrder = {
    "textoHTML",
    "asunto",
    "adjuntos",
    "from",
    "destinatarios"
})
public class EnviarMail {

    protected String textoHTML;
    protected String asunto;
    protected List<DataFiles> adjuntos;
    protected String from;
    protected List<Destinatarios> destinatarios;

    /**
     * Obtiene el valor de la propiedad textoHTML.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTextoHTML() {
        return textoHTML;
    }

    /**
     * Define el valor de la propiedad textoHTML.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTextoHTML(String value) {
        this.textoHTML = value;
    }

    /**
     * Obtiene el valor de la propiedad asunto.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAsunto() {
        return asunto;
    }

    /**
     * Define el valor de la propiedad asunto.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAsunto(String value) {
        this.asunto = value;
    }

    /**
     * Gets the value of the adjuntos property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the adjuntos property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAdjuntos().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DataFiles }
     * 
     * 
     */
    public List<DataFiles> getAdjuntos() {
        if (adjuntos == null) {
            adjuntos = new ArrayList<DataFiles>();
        }
        return this.adjuntos;
    }

    /**
     * Obtiene el valor de la propiedad from.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFrom() {
        return from;
    }

    /**
     * Define el valor de la propiedad from.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFrom(String value) {
        this.from = value;
    }

    /**
     * Gets the value of the destinatarios property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the destinatarios property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDestinatarios().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Destinatarios }
     * 
     * 
     */
    public List<Destinatarios> getDestinatarios() {
        if (destinatarios == null) {
            destinatarios = new ArrayList<Destinatarios>();
        }
        return this.destinatarios;
    }

}
