/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilidades;

import excepciones.GestionException;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.apache.log4j.Logger;

/**
 *
 * @author ihuegal
 */
public class Mensajes {
    private final ResourceBundle msg = FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{msg}", ResourceBundle.class);
        
    public void addMessage(FacesMessage.Severity severity, String summary, String detail, Class<?> clase, Throwable ex) {
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

    public void showException(Class<?> clase, Throwable ex) {
        if (ex != null && clase != null) {
            if (ex instanceof GestionException exGestion) {
                addMessage(exGestion.getSeverity(), "Error de gestión", ex.getMessage(), clase, ex);
            }
            else {
                addMessage(FacesMessage.SEVERITY_ERROR, "Error no controlado", ex.getMessage(), clase, ex);
            }
        }
    }
    
    public void showInfo(String mensaje) {
        addMessage(FacesMessage.SEVERITY_INFO, null, mensaje, null, null);
    }

    public void showWarn(String mensaje) {
        addMessage(FacesMessage.SEVERITY_WARN, null, mensaje, null, null);
    }

    public void showError(String mensaje) {
        addMessage(FacesMessage.SEVERITY_ERROR, null, mensaje, null, null);
    }

    public void showInfo(String titulo, String mensaje) {
        addMessage(FacesMessage.SEVERITY_INFO, (titulo != null ? titulo : msg.getString("Informacion")), mensaje, null, null);
    }

    public void showWarn(String titulo, String mensaje) {
        addMessage(FacesMessage.SEVERITY_WARN, (titulo != null ? titulo : msg.getString("Aviso")), mensaje, null, null);
    }

    public void showError(String titulo, String mensaje) {
        addMessage(FacesMessage.SEVERITY_ERROR, (titulo != null ? titulo : msg.getString("Error")), mensaje, null, null);
    }
}
