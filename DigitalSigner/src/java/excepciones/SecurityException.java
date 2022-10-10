package excepciones;

import javax.faces.application.FacesMessage;

/**
 *
 * @author ihuegal
 */
public class SecurityException extends GestionException {
    
    private FacesMessage.Severity severity = FacesMessage.SEVERITY_ERROR;

    @Override
    public FacesMessage.Severity getSeverity() {
        return severity;
    }

    @Override
    public void setSeverity(FacesMessage.Severity severity) {
        this.severity = severity;
    }
}
