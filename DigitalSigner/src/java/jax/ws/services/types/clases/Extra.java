package jax.ws.services.types.clases;

import excepciones.RequiredFieldException;
import javax.xml.bind.annotation.XmlElement;
import utilidades.Validation;

/**
 *
 * @author ihuegal
 */
public class Extra {
    
    private String coFichero;
    private String coExtension;
    private byte[] blFichero;

    @XmlElement(required = true)
    public String getCoFichero() throws RequiredFieldException {
        if (Validation.isNullOrEmpty(coFichero)) {
            throw new RequiredFieldException("coFichero");
        }
        return coFichero;
    }

    public void setCoFichero(String coFichero) {
        this.coFichero = coFichero;
    }

    @XmlElement(required = true)
    public String getCoExtension() throws RequiredFieldException {
        if (Validation.isNullOrEmpty(coExtension)) {
            throw new RequiredFieldException("coExtension");
        }
        return coExtension;
    }

    public void setCoExtension(String coExtension) {
        this.coExtension = coExtension;
    }

    @XmlElement(required = true)
    public byte[] getBlFichero() throws RequiredFieldException {
        if (Validation.isNullOrEmpty(blFichero)) {
            throw new RequiredFieldException("blFichero");
        }
        return blFichero;
    }

    public void setBlFichero(byte[] blFichero) {
        this.blFichero = blFichero;
    }
}
