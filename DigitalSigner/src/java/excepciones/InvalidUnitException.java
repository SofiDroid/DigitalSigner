package excepciones;

import javax.faces.application.FacesMessage;
import utilidades.Msg;

/**
 *
 * @author ihuegal
 */
public class InvalidUnitException extends SecurityException {
    
    public InvalidUnitException() {
        super.setSeverity(FacesMessage.SEVERITY_ERROR);
    }
    
    @Override
    public String getMessage() {
        return Msg.getString("err_unidad_no_valida");
    }
}
