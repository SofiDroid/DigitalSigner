package jax.ws.services.types.clases;

import excepciones.RequiredFieldException;
import javax.xml.bind.annotation.XmlElement;
import utilidades.Validation;

/**
 *
 * @author ihuegal
 */
public class Cabecera {
    
    private String tokenUsuario;
    private String coUnidad;

    @XmlElement(required = true)
    public String getTokenUsuario() throws RequiredFieldException {
        if (Validation.isNullOrEmpty(tokenUsuario)) {
            throw new RequiredFieldException("tokenUsuario");
        }
        return tokenUsuario;
    }

    public void setTokenUsuario(String tokenUsuario) {
        this.tokenUsuario = tokenUsuario;
    }

    @XmlElement(required = true)
    public String getCoUnidad() throws RequiredFieldException {
        if (Validation.isNullOrEmpty(coUnidad)) {
            throw new RequiredFieldException("coUnidad");
        }
        return coUnidad;
    }

    public void setCoUnidad(String coUnidad) {
        this.coUnidad = coUnidad;
    }
}
