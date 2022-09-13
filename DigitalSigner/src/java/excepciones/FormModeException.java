package excepciones;

import javax.faces.application.FacesMessage;
import utilidades.Msg;

/**
 *
 * @author ihuegal
 */
public class FormModeException extends GestionException {
    
    public FormModeException() {
        super.setSeverity(FacesMessage.SEVERITY_ERROR);
    }
    
    @Override
    public String getMessage() {
        return new Msg().getString("err_modo_formulario_no_establecido");
    }
}
