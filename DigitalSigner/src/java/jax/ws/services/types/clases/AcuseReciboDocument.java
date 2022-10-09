package jax.ws.services.types.clases;

import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author ihuegal
 */
public class AcuseReciboDocument {
    private Integer idEntradaXML;
    private String Hash;

    public Integer getIdEntradaXML() {
        return idEntradaXML;
    }

    public void setIdEntradaXML(Integer idEntradaXML) {
        this.idEntradaXML = idEntradaXML;
    }

    public String getHash() {
        return Hash;
    }

    public void setHash(String Hash) {
        this.Hash = Hash;
    }
}
