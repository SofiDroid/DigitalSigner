package utilidades;

import java.util.ResourceBundle;
import javax.faces.context.FacesContext;

/**
 *
 * @author ihuegal
 */
public class Msg {
    public static String getString(String key) {
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle msg = context.getApplication().evaluateExpressionGet(context, "#{msg}", ResourceBundle.class);
        
        return msg.getString(key);
    }
}
