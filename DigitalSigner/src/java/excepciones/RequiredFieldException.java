package excepciones;

import javax.faces.application.FacesMessage;
import utilidades.Msg;

/**
 *
 * @author ihuegal
 */
public class RequiredFieldException extends GestionException {
    
    protected String campoRequerido = "(null)";
    
    public RequiredFieldException(String campoRequerido) {
        super.setSeverity(FacesMessage.SEVERITY_WARN);
        this.campoRequerido = campoRequerido;
    }
    
    @Override
    public String getMessage() {
        return new Msg().getString("warn_campo_requerido") + " " + campoRequerido;
    }
}
