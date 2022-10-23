
package jax.client.services.interfaces.mailservice;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.3.5
 * Generated source version: 2.2
 * 
 */
@WebService(name = "MailWebService", targetNamespace = "http://main/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface MailWebService {


    /**
     * 
     * @param destinatarios
     * @param asunto
     * @param textoHTML
     * @param from
     * @param adjuntos
     * @return
     *     returns jax.client.services.interfaces.mailservice.RespuestaMail
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "enviarMail", targetNamespace = "http://main/", className = "jax.client.services.interfaces.mailservice.EnviarMail")
    @ResponseWrapper(localName = "enviarMailResponse", targetNamespace = "http://main/", className = "jax.client.services.interfaces.mailservice.EnviarMailResponse")
    @Action(input = "http://main/MailWebService/enviarMailRequest", output = "http://main/MailWebService/enviarMailResponse")
    public RespuestaMail enviarMail(
        @WebParam(name = "textoHTML", targetNamespace = "")
        String textoHTML,
        @WebParam(name = "asunto", targetNamespace = "")
        String asunto,
        @WebParam(name = "adjuntos", targetNamespace = "")
        List<DataFiles> adjuntos,
        @WebParam(name = "from", targetNamespace = "")
        String from,
        @WebParam(name = "destinatarios", targetNamespace = "")
        List<Destinatarios> destinatarios);

}
