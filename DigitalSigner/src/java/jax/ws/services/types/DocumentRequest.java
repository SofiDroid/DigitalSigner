package jax.ws.services.types;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import jax.ws.services.types.clases.Cabecera;
import jax.ws.services.types.clases.Extra;
import jax.ws.services.types.clases.Documento;

/**
 *
 * @author ihuegal
 */
public class DocumentRequest {
    
    private Cabecera cabecera;
    private Documento documento;
    private ArrayList<Extra> extras;

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

    @XmlElement(required = false)
    public ArrayList<Extra> getExtras() {
        return extras;
    }

    public void setExtras(ArrayList<Extra> extras) {
        this.extras = extras;
    }
}
