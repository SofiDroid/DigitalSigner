package excepciones;

import javax.faces.application.FacesMessage;
import utilidades.Msg;

/**
 *
 * @author ihuegal
 */
public class InvalidTokenException extends SecurityException {
    
    public InvalidTokenException() {
        super.setSeverity(FacesMessage.SEVERITY_ERROR);
    }
    
    @Override
    public String getMessage() {
        return Msg.getString("err_token_no_valido");
    }
}
