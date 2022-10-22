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
import javax.faces.FactoryFinder;
import javax.faces.context.FacesContext;
import javax.faces.context.FacesContextFactory;
import javax.faces.lifecycle.Lifecycle;
import javax.faces.lifecycle.LifecycleFactory;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import seguridad.usuarios.DatosUsuario;

/**
 *
 * @author ihuegal
 */
public class Session {

    private WebServiceContext context;

    public WebServiceContext getContext() {
        return context;
    }

    public void setContext(WebServiceContext context) {
        this.context = context;
    }
    
    public void crearDatosUsuario() {
        LifecycleFactory lifecycleFactory = (LifecycleFactory) FactoryFinder.getFactory(FactoryFinder.LIFECYCLE_FACTORY);
        FacesContextFactory m_facesContextFactory = (FacesContextFactory) FactoryFinder.getFactory(FactoryFinder.FACES_CONTEXT_FACTORY);
        Lifecycle m_lifecycle = lifecycleFactory.getLifecycle(LifecycleFactory.DEFAULT_LIFECYCLE);

        HttpServletRequest httpRequest = (HttpServletRequest) context.getMessageContext().get(MessageContext.SERVLET_REQUEST);
        HttpServletResponse httpResponse = (HttpServletResponse) context.getMessageContext().get(MessageContext.SERVLET_RESPONSE);
        ServletContext servletContext = (ServletContext) context.getMessageContext().get(MessageContext.SERVLET_CONTEXT);
        m_facesContextFactory.getFacesContext(servletContext, httpRequest, httpResponse, m_lifecycle);

        CDI.current().getBeanManager().getContext(SessionScoped.class).get(CDI.current().getBeanManager().getBeans("datosUsuario").iterator().next());
    }

    public static boolean isSessionActiva() {
        if (FacesContext.getCurrentInstance() != null) {
            DatosUsuario datosUsuario = (DatosUsuario)CDI.current().getBeanManager().getContext(SessionScoped.class).get(CDI.current().getBeanManager().getBeans("datosUsuario").iterator().next());
            if (datosUsuario != null) {
                if (datosUsuario.getBdTUsuario() != null) {
                    return true;
                }
            }
        }
        return false;
    }
    
    
    public static DatosUsuario getDatosUsuario() {
        if (FacesContext.getCurrentInstance() == null) {
            DatosUsuario datosUsuario = (DatosUsuario)CDI.current().getBeanManager().getContext(SessionScoped.class).get(CDI.current().getBeanManager().getBeans("datosUsuario").iterator().next());
            return datosUsuario;
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
        limpiarOtrosBeans(beanEnUso, false, false);
    }
    
    public static void limpiarOtrosBeans(String beanEnUso, boolean boDatosUsuario, boolean boMenu) {
        //Cambiar esto y que lo recoja de una lista de propiedades o mirar como sacar solo los de las gestiones.
        AlterableContext ctxSession = (AlterableContext) CDI.current().getBeanManager().getContext(SessionScoped.class);
        Set<Bean<?>> beans = CDI.current().getBeanManager().getBeans(Object.class, new AnnotationLiteral<Any>() {});
        for (Bean<?> itemBean : beans) {
            if (itemBean.getBeanClass().getDeclaredAnnotation(Named.class) != null && itemBean.getScope() == SessionScoped.class)
            {

                if (!boDatosUsuario && itemBean.getBeanClass().getSimpleName().equals("DatosUsuario")) {
                    continue;
                }
                if (!boMenu && itemBean.getBeanClass().getSimpleName().equals("Menu")) {
                    continue;
                }
                if (beanEnUso != null && itemBean.getBeanClass().getName().equals(beanEnUso)) {
                    continue;
                }
                //REMOVE
                ctxSession.destroy(itemBean);
            }
        }
    }
}
