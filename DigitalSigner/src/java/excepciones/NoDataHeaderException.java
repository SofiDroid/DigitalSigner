package excepciones;

import javax.faces.application.FacesMessage;
import utilidades.Msg;

/**
 *
 * @author ihuegal
 */
public class NoDataHeaderException extends GestionException {
    
    public NoDataHeaderException() {
        super.setSeverity(FacesMessage.SEVERITY_ERROR);
    }
    
    @Override
    public String getMessage() {
        return Msg.getString("err_cabecera_no_definida");
    }
}
