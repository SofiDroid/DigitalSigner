package excepciones;

import javax.faces.application.FacesMessage;
import utilidades.Msg;

/**
 *
 * @author ihuegal
 */
public class RegistryNotFoundException extends GestionException {
    
    public RegistryNotFoundException() {
        super.setSeverity(FacesMessage.SEVERITY_ERROR);
    }
    
    @Override
    public String getMessage() {
        return new Msg().getString("err_registro_no_encontrado");
    }
}
