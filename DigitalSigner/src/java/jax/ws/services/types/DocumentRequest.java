package jax.ws.services.types;

import jax.ws.services.types.clases.Cabecera;
import jax.ws.services.types.clases.Extras;
import jax.ws.services.types.clases.Documento;

/**
 *
 * @author ihuegal
 */
public class DocumentRequest {
    
    private Cabecera cabecera;
    private Documento documento;
    private Extras extras;

    public Cabecera getCabecera() {
        return cabecera;
    }

    public void setCabecera(Cabecera cabecera) {
        this.cabecera = cabecera;
    }

    public Documento getDocumento() {
        return documento;
    }

    public void setDocumento(Documento documento) {
        this.documento = documento;
    }

    public Extras getExtras() {
        return extras;
    }

    public void setExtras(Extras extras) {
        this.extras = extras;
    }
}
