package jax.ws.services.types.clases;

import excepciones.RequiredFieldException;
import javax.xml.bind.annotation.XmlElement;
import utilidades.Validation;

/**
 *
 * @author ihuegal
 */
public class Documento {
    
    private String coTipoDocumento;
    private String coFichero;
    private String coExtension;
    private byte[] blDocumento;
    private String dsDocumento;
    private String dsObservaciones;

    @XmlElement(required = true)
    public String getCoTipoDocumento() throws RequiredFieldException {
        if (Validation.isNullOrEmpty(coTipoDocumento)) {
            throw new RequiredFieldException("coTipoDocumento");
        }
        return coTipoDocumento;
    }

    public void setCoTipoDocumento(String coTipoDocumento) {
        this.coTipoDocumento = coTipoDocumento;
    }

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
    public byte[] getBlDocumento() throws RequiredFieldException {
        if (Validation.isNullOrEmpty(blDocumento)) {
            throw new RequiredFieldException("blDocumento");
        }
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
