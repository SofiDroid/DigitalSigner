package jax.ws.services.types.clases;

/**
 *
 * @author ihuegal
 */
public class Documento {
    
    private String coFichero;
    private String coExtension;
    private byte[] blDocumento;
    private String dsDocumento;
    private String dsObservaciones;

    public String getCoFichero() {
        return coFichero;
    }

    public void setCoFichero(String coFichero) {
        this.coFichero = coFichero;
    }

    public String getCoExtension() {
        return coExtension;
    }

    public void setCoExtension(String coExtension) {
        this.coExtension = coExtension;
    }

    public byte[] getBlDocumento() {
        return blDocumento;
    }

    public void setBlDocumento(byte[] blDocumento) {
        this.blDocumento = blDocumento;
    }

    public String getDsDocumento() {
        return dsDocumento;
    }

    public void setDsDocumento(String dsDocumento) {
        this.dsDocumento = dsDocumento;
    }

    public String getDsObservaciones() {
        return dsObservaciones;
    }

    public void setDsObservaciones(String dsObservaciones) {
        this.dsObservaciones = dsObservaciones;
    }
}
