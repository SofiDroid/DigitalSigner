package utilidades;

import excepciones.GestionException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.apache.log4j.Logger;

/**
 *
 * @author ihuegal
 */
public class Mensajes {
    public static void addMessage(FacesMessage.Severity severity, String summary, String detail, Class<?> clase, Throwable ex) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
        if (clase != null && ex != null) {
            if (severity == FacesMessage.SEVERITY_ERROR) {
                Logger.getLogger(clase).error(ex.getMessage(), ex);
            }
            else if (severity == FacesMessage.SEVERITY_FATAL) {
                Logger.getLogger(clase).fatal(ex.getMessage(), ex);
            }
        }
    }

    public static void showException(Class<?> clase, Throwable ex) {
        if (ex != null && clase != null) {
            if (ex instanceof GestionException exGestion) {
                addMessage(exGestion.getSeverity(), "Error de gestión", ex.getMessage(), clase, ex);
            }
            else {
                addMessage(FacesMessage.SEVERITY_ERROR, "Error no controlado", ex.getMessage(), clase, ex);
            }
        }
    }
    
    public static void showInfo(String mensaje) {
        addMessage(FacesMessage.SEVERITY_INFO, null, mensaje, null, null);
    }

    public static void showWarn(String mensaje) {
        addMessage(FacesMessage.SEVERITY_WARN, null, mensaje, null, null);
    }

    public static void showError(String mensaje) {
        addMessage(FacesMessage.SEVERITY_ERROR, null, mensaje, null, null);
    }

    public static void showInfo(String titulo, String mensaje) {
        addMessage(FacesMessage.SEVERITY_INFO, (titulo != null ? titulo : Msg.getString("Informacion")), mensaje, null, null);
    }

    public static void showWarn(String titulo, String mensaje) {
        addMessage(FacesMessage.SEVERITY_WARN, (titulo != null ? titulo : Msg.getString("Aviso")), mensaje, null, null);
    }

    public static void showError(String titulo, String mensaje) {
        addMessage(FacesMessage.SEVERITY_ERROR, (titulo != null ? titulo : Msg.getString("Error")), mensaje, null, null);
    }
}
