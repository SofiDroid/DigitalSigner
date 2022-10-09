package jax.ws.services.interfaces;

import javax.jws.*;
import javax.xml.bind.annotation.XmlElement;
import jax.ws.services.types.AcuseReciboDocumentResponse;
import jax.ws.services.types.DocumentRequest;
import utilidades.Mensajes;

/**
 *
 * @author ihuegal
 */
@WebService(serviceName="DocumentService")
public class DocumentServiceImpl {

    /**
     * Web service operation
     * @param documentRequest
     * @return 
     */
    @WebMethod(operationName = "sendDocument")
    @XmlElement(required = true)
    public AcuseReciboDocumentResponse sendDocument(@XmlElement(required = true) @WebParam(name = "documentRequest") DocumentRequest documentRequest) {
        AcuseReciboDocumentResponse acuseReciboDocumentResponse = new AcuseReciboDocumentResponse();
        
        try {
            acuseReciboDocumentResponse.getAcuseReciboDocument().setIdEntradaXML(0);
            acuseReciboDocumentResponse.getAcuseReciboDocument().setHash("123123123");
            
        }
        catch (Exception ex) {
            Mensajes.showException(this.getClass(), ex);
            acuseReciboDocumentResponse.getError().setErrorMessage(ex.getMessage());
        }
        
        return acuseReciboDocumentResponse;
    }
}
