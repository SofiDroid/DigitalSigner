package jax.ws.services.types;

import javax.xml.bind.annotation.XmlElement;
import jax.ws.services.types.clases.Cabecera;
import jax.ws.services.types.clases.Documento;

/**
 *
 * @author ihuegal
 */
public class DocumentNotificationRequest {
    
    private Cabecera cabecera;
    private Documento documento;

    @XmlElement(required = true)
    public Cabecera getCabecera() {
        return cabecera;
    }

    public void setCabecera(Cabecera cabecera) {
        this.cabecera = cabecera;
    }

    @XmlElement(required = true)
    public Documento getDocumento() {
        return documento;
    }

    public void setDocumento(Documento documento) {
        this.documento = documento;
    }
}
