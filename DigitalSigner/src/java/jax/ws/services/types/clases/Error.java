package jax.ws.services.types.clases;

import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author ihuegal
 */
public class Error {
    private String errorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
