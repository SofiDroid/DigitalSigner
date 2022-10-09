package jax.ws.services.types;

import jax.ws.services.types.clases.Error;
import jax.ws.services.types.clases.AcuseReciboDocument;

/**
 *
 * @author ihuegal
 */
public class AcuseReciboDocumentResponse {
    private AcuseReciboDocument acuseReciboDocument = new AcuseReciboDocument();
    private Error error = new Error();

    public AcuseReciboDocument getAcuseReciboDocument() {
        return acuseReciboDocument;
    }

    public void setAcuseReciboDocument(AcuseReciboDocument acuseReciboDocument) {
        this.acuseReciboDocument = acuseReciboDocument;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }
}
