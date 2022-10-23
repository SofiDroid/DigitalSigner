
package jax.client.services.interfaces.documentnotification;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the jax.client.services.interfaces.documentnotification package. 
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

    private final static QName _DocumentNotification_QNAME = new QName("http://interfaces.services.ws.jax/", "documentNotification");
    private final static QName _DocumentNotificationResponse_QNAME = new QName("http://interfaces.services.ws.jax/", "documentNotificationResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: jax.client.services.interfaces.documentnotification
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link DocumentNotification }
     * 
     */
    public DocumentNotification createDocumentNotification() {
        return new DocumentNotification();
    }

    /**
     * Create an instance of {@link DocumentNotificationResponse }
     * 
     */
    public DocumentNotificationResponse createDocumentNotificationResponse() {
        return new DocumentNotificationResponse();
    }

    /**
     * Create an instance of {@link DocumentNotificationRequest }
     * 
     */
    public DocumentNotificationRequest createDocumentNotificationRequest() {
        return new DocumentNotificationRequest();
    }

    /**
     * Create an instance of {@link Cabecera }
     * 
     */
    public Cabecera createCabecera() {
        return new Cabecera();
    }

    /**
     * Create an instance of {@link Documento }
     * 
     */
    public Documento createDocumento() {
        return new Documento();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DocumentNotification }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DocumentNotification }{@code >}
     */
    @XmlElementDecl(namespace = "http://interfaces.services.ws.jax/", name = "documentNotification")
    public JAXBElement<DocumentNotification> createDocumentNotification(DocumentNotification value) {
        return new JAXBElement<DocumentNotification>(_DocumentNotification_QNAME, DocumentNotification.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DocumentNotificationResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DocumentNotificationResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://interfaces.services.ws.jax/", name = "documentNotificationResponse")
    public JAXBElement<DocumentNotificationResponse> createDocumentNotificationResponse(DocumentNotificationResponse value) {
        return new JAXBElement<DocumentNotificationResponse>(_DocumentNotificationResponse_QNAME, DocumentNotificationResponse.class, null, value);
    }

}
