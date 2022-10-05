/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilidades;

import basedatos.tablas.BdTUsuario;
import java.util.Set;
import javax.el.ELContext;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.context.spi.AlterableContext;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.CDI;
import javax.enterprise.util.AnnotationLiteral;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import seguridad.usuarios.DatosUsuario;

/**
 *
 * @author ihuegal
 */
public class Session {
    public static DatosUsuario getDatosUsuario() {
        if (FacesContext.getCurrentInstance() == null) {
            return (DatosUsuario)CDI.current().getBeanManager().getContext(SessionScoped.class).get(CDI.current().getBeanManager().getBeans("datosUsuario").iterator().next());
        }
        else {
            ELContext elContext = FacesContext.getCurrentInstance().getELContext();
            return (DatosUsuario)elContext.getELResolver().getValue(elContext, null, "datosUsuario");
        }
    }
    
    public static String getCoUsuario() {
        BdTUsuario bdTUsuario = getDatosUsuario().getBdTUsuario();
        return (bdTUsuario != null ? bdTUsuario.getCoUsuario() : null);
    }
    
    public static Object getNamedBean(String nombre) {
        return CDI.current().getBeanManager().getContext(SessionScoped.class).get(CDI.current().getBeanManager().getBeans(nombre).iterator().next());
    }
    
    public static void grabarAtributo(String name, Object value) {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(name, value);
    }
    
    public static Object recuperarAtributo(String name) {
        return FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(name);
    }
    
    public static void limpiarOtrosBeans(String beanEnUso) {
        limpiarOtrosBeans(beanEnUso, false);
    }
    
    public static void limpiarOtrosBeans(String beanEnUso, boolean boTodo) {
        //Cambiar esto y que lo recoja de una lista de propiedades o mirar como sacar solo los de las gestiones.
        AlterableContext ctxSession = (AlterableContext) CDI.current().getBeanManager().getContext(SessionScoped.class);
        Set<Bean<?>> beans = CDI.current().getBeanManager().getBeans(Object.class, new AnnotationLiteral<Any>() {});
        for (Bean<?> itemBean : beans) {
            if (itemBean.getBeanClass().getDeclaredAnnotation(Named.class) != null && itemBean.getScope() == SessionScoped.class)
            {
                if (!boTodo) {
                    if (itemBean.getBeanClass().getSimpleName().equals("DatosUsuario") 
                            || itemBean.getBeanClass().getSimpleName().equals("Menu")
                            || itemBean.getBeanClass().getName().equals(beanEnUso)) {
                        continue;
                    }
                }                    
                //REMOVE
                ctxSession.destroy(itemBean);
            }
        }
    }
}
