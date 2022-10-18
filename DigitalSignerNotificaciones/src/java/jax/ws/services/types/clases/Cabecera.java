package jax.ws.services.types.clases;

import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author ihuegal
 */
public class Cabecera {
    
    private String coUnidad;

    @XmlElement(required = true)
    public String getCoUnidad() {
        return coUnidad;
    }

    public void setCoUnidad(String coUnidad) {
        this.coUnidad = coUnidad;
    }
}