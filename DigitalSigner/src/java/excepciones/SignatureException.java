package excepciones;

import javax.faces.application.FacesMessage;

/**
 *
 * @author ihuegal
 */
public class SignatureException extends Exception {
    
    private FacesMessage.Severity severity = FacesMessage.SEVERITY_ERROR;

    public FacesMessage.Severity getSeverity() {
        return severity;
    }

    public void setSeverity(FacesMessage.Severity severity) {
        this.severity = severity;
    }
}
