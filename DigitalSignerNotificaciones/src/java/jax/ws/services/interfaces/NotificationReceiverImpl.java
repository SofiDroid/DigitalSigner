package jax.ws.services.interfaces;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.xml.bind.annotation.XmlElement;
import jax.ws.services.types.DocumentNotificationRequest;

/**
 *
 * @author ihuegal
 */
@WebService(serviceName = "NotificationReceiver")
public class NotificationReceiverImpl {

    /**
     * Este es el metodo de recepción de una notificación
     * de documento firmado.
     * @param documentNotificationRequest
     * @return 
     */
    @WebMethod(operationName = "documentNotification")
    @XmlElement(required = true)
    public String documentNotification(@WebParam(name = "documentNotificationRequest") DocumentNotificationRequest documentNotificationRequest) {
        return "OK";
    }
}
