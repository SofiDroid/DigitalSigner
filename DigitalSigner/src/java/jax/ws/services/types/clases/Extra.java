package jax.ws.services.types.clases;

import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author ihuegal
 */
public class Extra {
    
    private String coFichero;
    private String coExtension;
    private byte[] blFichero;

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

    @XmlElement(required = true)
    public byte[] getBlFichero() {
        return blFichero;
    }

    public void setBlFichero(byte[] blFichero) {
        this.blFichero = blFichero;
    }
}
