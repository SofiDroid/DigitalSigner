
package jax.client.services.interfaces.mailservice;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the jax.client.services.interfaces.mailservice package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _EnviarMail_QNAME = new QName("http://main/", "enviarMail");
    private final static QName _EnviarMailResponse_QNAME = new QName("http://main/", "enviarMailResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: jax.client.services.interfaces.mailservice
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link EnviarMail }
     * 
     */
    public EnviarMail createEnviarMail() {
        return new EnviarMail();
    }

    /**
     * Create an instance of {@link EnviarMailResponse }
     * 
     */
    public EnviarMailResponse createEnviarMailResponse() {
        return new EnviarMailResponse();
    }

    /**
     * Create an instance of {@link DataFiles }
     * 
     */
    public DataFiles createDataFiles() {
        return new DataFiles();
    }

    /**
     * Create an instance of {@link Destinatarios }
     * 
     */
    public Destinatarios createDestinatarios() {
        return new Destinatarios();
    }

    /**
     * Create an instance of {@link RespuestaMail }
     * 
     */
    public RespuestaMail createRespuestaMail() {
        return new RespuestaMail();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EnviarMail }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link EnviarMail }{@code >}
     */
    @XmlElementDecl(namespace = "http://main/", name = "enviarMail")
    public JAXBElement<EnviarMail> createEnviarMail(EnviarMail value) {
        return new JAXBElement<EnviarMail>(_EnviarMail_QNAME, EnviarMail.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EnviarMailResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link EnviarMailResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://main/", name = "enviarMailResponse")
    public JAXBElement<EnviarMailResponse> createEnviarMailResponse(EnviarMailResponse value) {
        return new JAXBElement<EnviarMailResponse>(_EnviarMailResponse_QNAME, EnviarMailResponse.class, null, value);
    }

}
