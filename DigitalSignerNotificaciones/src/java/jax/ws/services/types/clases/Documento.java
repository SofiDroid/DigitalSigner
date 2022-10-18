package jax.ws.services.types.clases;

import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author ihuegal
 */
public class Documento {
    
    private String coSituacionDoc;
    private String coTipoDocumento;
    private String coFichero;
    private String coExtension;
    private byte[] blDocumento;
    private String dsDocumento;
    private String dsObservaciones;

    @XmlElement(required = true)
    public String getCoSituacionDoc() {
        return coSituacionDoc;
    }

    public void setCoSituacionDoc(String coSituacionDoc) {
        this.coSituacionDoc = coSituacionDoc;
    }

    @XmlElement(required = true)
    public String getCoTipoDocumento() {
        return coTipoDocumento;
    }

    public void setCoTipoDocumento(String coTipoDocumento) {
        this.coTipoDocumento = coTipoDocumento;
    }

    @XmlElement(required = true)
    public String getCoFichero() {
        return coFichero;
    }

    public void setCoFichero(String coFichero) {
        this.coFichero = coFichero;
    }

    @XmlElement(required = true)
    public String getCoExtension() {
        return coExtension;
    }

    public void setCoExtension(String coExtension) {
        this.coExtension = coExtension;
    }

    @XmlElement(required = false)
    public byte[] getBlDocumento() {
        return blDocumento;
    }

    public void setBlDocumento(byte[] blDocumento) {
        this.blDocumento = blDocumento;
    }

    @XmlElement(required = false)
    public String getDsDocumento() {
        return dsDocumento;
    }

    public void setDsDocumento(String dsDocumento) {
        this.dsDocumento = dsDocumento;
    }

    @XmlElement(required = false)
    public String getDsObservaciones() {
        return dsObservaciones;
    }

    public void setDsObservaciones(String dsObservaciones) {
        this.dsObservaciones = dsObservaciones;
    }
}
