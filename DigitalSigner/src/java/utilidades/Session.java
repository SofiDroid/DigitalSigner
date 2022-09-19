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
import seguridad.usuarios.DatosUsuario;

/**
 *
 * @author ihuegal
 */
public class Session {
    public static DatosUsuario getDatosUsuario() {
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        return (DatosUsuario)elContext.getELResolver().getValue(elContext, null, "datosUsuario");
    }
    
    public static String getCoUsuario() {
        BdTUsuario bdTUsuario = getDatosUsuario().getBdTUsuario();
        return (bdTUsuario != null ? bdTUsuario.getCoUsuario() : null);
    }
    
    public static Object getNamedBean(String nombre) {
        return CDI.current().getBeanManager().getContext(SessionScoped.class).get(CDI.current().getBeanManager().getBeans(nombre).iterator().next());
    }
    
    public static void limpiarOtrosBeans(String beanEnUso) {
        //Cambiar esto y que lo recoja de una lista de propiedades o mirar como sacar solo los de las gestiones.
        /*
        Set<Bean<?>> beans = CDI.current().getBeanManager().getBeans(Object.class, new AnnotationLiteral<Any>() {});
        for (Bean<?> itemBean : beans) {
            if (!itemBean.getBeanClass().getName().equalsIgnoreCase(beanEnUso) 
                    && !itemBean.getBeanClass().getName().equalsIgnoreCase("datosUsuario")) {
                
                AlterableContext ctxSession = (AlterableContext) CDI.current().getBeanManager().getContext(SessionScoped.class);
                for (Bean<?> bean : CDI.current().getBeanManager().getBeans(itemBean.getBeanClass())) {
                    Object instance = ctxSession.get(bean);
                    if (instance != null)
                        ctxSession.destroy(bean);
                }
                
            }
        }
        */
    }
}
