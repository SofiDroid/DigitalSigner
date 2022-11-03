package init.filtros;

import basedatos.servicios.StAUniusu;
import basedatos.servicios.StAUsutipousu;
import basedatos.servicios.StTTipousuario;
import basedatos.servicios.StTUnidad;
import basedatos.servicios.StTUsuario;
import basedatos.tablas.BdAUniusu;
import basedatos.tablas.BdAUsutipousu;
import basedatos.tablas.BdTTipodocumento;
import basedatos.tablas.BdTTipousuario;
import basedatos.tablas.BdTUnidad;
import basedatos.tablas.BdTUsuario;
import excepciones.RegistryNotFoundException;
import init.AppInit;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Date;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.spi.CDI;
import javax.faces.FactoryFinder;
import javax.faces.context.FacesContextFactory;
import javax.faces.lifecycle.Lifecycle;
import javax.faces.lifecycle.LifecycleFactory;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.handler.MessageContext;
import seguridad.usuarios.DatosUsuario;
import tomcat.persistence.EntityManager;
import utilidades.Formateos;
import utilidades.Session;

/**
 *
 * @author ihuegal
 */
public class AuthenticationFilter implements Filter {
//    Logger logger = Logger.getLogger(AuthenticationFilter.class);
    
    private static final boolean debug = false;

    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    private FilterConfig filterConfig = null;
    
    public AuthenticationFilter() {
    }    
    
    private void doBeforeProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("AuthenticationFilter:DoBeforeProcessing");
            log("request.isSecure() -> " + request.isSecure());
            Object[] certs = (Object[])request.getAttribute("javax.servlet.request.X509Certificate");
            if (certs != null && certs.length > 0) {
                X509Certificate certificado = ((X509Certificate)certs[0]);
                String cn = certificado.getSubjectX500Principal().getName().split(",")[0].substring(3);
                String nombre = cn.split("\\|")[0];
                String nif = cn.split("\\|")[1];
                log("Certificado -> Nombre: " + nombre + ", NIF: " + nif);
            }
            else {
                log("No se ha encontrado el certificado.");
            }
        }
    }    
    
    private void doAfterProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("AuthenticationFilter:DoAfterProcessing");
        }
    }

    /**
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        
        if (debug) {
            log("AuthenticationFilter:doFilter()");
        }
        
        doBeforeProcessing(request, response);
        
        Throwable problem = null;
        try {
            autenticacion(request, response, chain);
            chain.doFilter(request, response);
        } catch (Throwable t) {
            // If an exception is thrown somewhere down the filter chain,
            // we still want to execute our after processing, and then
            // rethrow the problem after that.
            problem = t;
            t.printStackTrace();
        }
        
        doAfterProcessing(request, response);

        // If there was a problem, we want to rethrow it if it is
        // a known type, otherwise log it.
        if (problem != null) {
            if (problem instanceof ServletException) {
                throw (ServletException) problem;
            }
            if (problem instanceof IOException) {
                throw (IOException) problem;
            }
            sendProcessingError(problem, response);
        }
    }

    /**
     * Return the filter configuration object for this filter.
     */
    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    /**
     * Set the filter configuration object for this filter.
     *
     * @param filterConfig The filter configuration object
     */
    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    /**
     * Destroy method for this filter
     */
    public void destroy() {        
    }

    /**
     * Init method for this filter
     */
    public void init(FilterConfig filterConfig) {        
        this.filterConfig = filterConfig;
        if (filterConfig != null) {
            if (debug) {                
                log("AuthenticationFilter:Initializing filter");
            }
        }
    }

    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("AuthenticationFilter()");
        }
        StringBuffer sb = new StringBuffer("AuthenticationFilter(");
        sb.append(filterConfig);
        sb.append(")");
        return (sb.toString());
    }
    
    private void sendProcessingError(Throwable t, ServletResponse response) {
        String stackTrace = getStackTrace(t);        
        
        if (stackTrace != null && !stackTrace.equals("")) {
            try {
                response.setContentType("text/html");
                PrintStream ps = new PrintStream(response.getOutputStream());
                PrintWriter pw = new PrintWriter(ps);                
                pw.print("<html>\n<head>\n<title>Error</title>\n</head>\n<body>\n"); //NOI18N

                // PENDING! Localize this for next official release
                pw.print("<h1>The resource did not process correctly</h1>\n<pre>\n");                
                pw.print(stackTrace);                
                pw.print("</pre></body>\n</html>"); //NOI18N
                pw.close();
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        } else {
            try {
                PrintStream ps = new PrintStream(response.getOutputStream());
                t.printStackTrace(ps);
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        }
    }
    
    public static String getStackTrace(Throwable t) {
        String stackTrace = null;
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            pw.close();
            sw.close();
            stackTrace = sw.getBuffer().toString();
        } catch (Exception ex) {
        }
        return stackTrace;
    }
    
    public void log(String msg) {
        filterConfig.getServletContext().log(msg);        
    }

    public void crearDatosUsuario(HttpServletRequest httpRequest, HttpServletResponse httpResponse, ServletContext servletContext) {
        LifecycleFactory lifecycleFactory = (LifecycleFactory) FactoryFinder.getFactory(FactoryFinder.LIFECYCLE_FACTORY);
        FacesContextFactory m_facesContextFactory = (FacesContextFactory) FactoryFinder.getFactory(FactoryFinder.FACES_CONTEXT_FACTORY);
        Lifecycle m_lifecycle = lifecycleFactory.getLifecycle(LifecycleFactory.DEFAULT_LIFECYCLE);

        m_facesContextFactory.getFacesContext(servletContext, httpRequest, httpResponse, m_lifecycle);

        CDI.current().getBeanManager().getContext(SessionScoped.class).get(CDI.current().getBeanManager().getBeans("datosUsuario").iterator().next());
    }
    
    private void login(BdTUsuario bdTUsuario, DatosUsuario datosUsuarioSystem) throws Exception {
        
        Session.getDatosUsuario().setBdTUsuario(bdTUsuario);

        BdAUniusu filtroBdAUniusu = new BdAUniusu();
        filtroBdAUniusu.setIdUsuario(bdTUsuario.getIdUsuario());
        filtroBdAUniusu.setFeAlta(new Date());
        filtroBdAUniusu.setFeDesactivo(new Date());
        StAUniusu stAUniusu = new StAUniusu(datosUsuarioSystem);
        ArrayList<BdAUniusu> listaBdAUniusu = stAUniusu.filtro(filtroBdAUniusu, null);
        for(BdAUniusu itemBdAUniusu : listaBdAUniusu) {
            BdTUnidad filtroBdTUnidad = new BdTUnidad();
            filtroBdTUnidad.setIdUnidad(itemBdAUniusu.getIdUnidad());
            filtroBdAUniusu.setFeAlta(new Date());
            filtroBdAUniusu.setFeDesactivo(new Date());
            StTUnidad stTUnidad = new StTUnidad(datosUsuarioSystem);
            ArrayList<BdTUnidad> listaBdTUnidad = stTUnidad.filtro(filtroBdTUnidad, null);
            if (listaBdTUnidad != null && !listaBdTUnidad.isEmpty()) {
                Session.getDatosUsuario().getListaBdTUnidad().addAll(listaBdTUnidad);
            }
        }
        Session.getDatosUsuario().cargarComboUnidades();
        //Session.getDatosUsuario().setBdTUnidad(Session.getDatosUsuario().getListaBdTUnidad().get(0));
        Session.getDatosUsuario().getcUnidad().setValue(Session.getDatosUsuario().getListaBdTUnidad().get(0).getIdUnidad().toString());
        Session.getDatosUsuario().setPais(null); //El idioma por defecto, no pasamos por la selección.

        Session.getDatosUsuario().selectOptionUnidad();
    }
    
    private void autenticacion(ServletRequest request, ServletResponse response, FilterChain chain) throws Exception {
        Object[] certs = (Object[])request.getAttribute("javax.servlet.request.X509Certificate");
        if (certs != null && certs.length > 0) {
            X509Certificate certificado = ((X509Certificate)certs[0]);
            String cn = certificado.getSubjectX500Principal().getName().split(",")[0].substring(3);
            String nombre = cn.split("\\|")[0];
            String nif = cn.split("\\|")[1];
            
            DatosUsuario datosUsuarioSystem = new DatosUsuario();
            BdTUsuario bdTUsuarioSystem = new BdTUsuario();
            bdTUsuarioSystem.setCoUsuario("SYSTEM");
            datosUsuarioSystem.setBdTUsuario(bdTUsuarioSystem);
            
            StTUsuario stTUsuario = new StTUsuario(datosUsuarioSystem);
            BdTUsuario filtroBdTUsuario = new BdTUsuario();
            filtroBdTUsuario.setCoNIF(nif);
            filtroBdTUsuario.setFeAlta(new Date());
            filtroBdTUsuario.setFeDesactivo(new Date());
            ArrayList<BdTUsuario> listaBdTUsuario = stTUsuario.filtro(filtroBdTUsuario, null);
            
            if (listaBdTUsuario != null && !listaBdTUsuario.isEmpty()) {
                // ACCESO CONCEDIDO - Existe el usuario
                // Cargar datos del usuario en sesion.
                crearDatosUsuario((HttpServletRequest) request, (HttpServletResponse) response, request.getServletContext());
                login(listaBdTUsuario.get(0), datosUsuarioSystem);
            }
            else {
                // ACCESO CONCEDIDO - No existe el usuario, 
                // lo damos de alta como firmante solo...
                BdTUsuario newBdTUsuario = new BdTUsuario();
                newBdTUsuario.setCoNIF(nif);
                newBdTUsuario.setCoUsuario(nif);
                String[] nombreApellidos = Formateos.separarNombre(nombre);
                newBdTUsuario.setDsNombre(nombreApellidos[0]);
                newBdTUsuario.setDsApellido1(nombreApellidos[1]);
                newBdTUsuario.setDsApellido2(nombreApellidos[2]);
                newBdTUsuario.setCoPassword(null);
                newBdTUsuario.setBoAdmin(false);
                newBdTUsuario.setEnIntentos(0);
                newBdTUsuario.setEnIntentosmax(0);
                newBdTUsuario.setFeAlta(new Date());
                newBdTUsuario.setFeDesactivo(null);
                newBdTUsuario.setUsuariobd("SYSTEM");
                // INICIO TRANSACCION
                try (EntityManager entityManager = AppInit.getEntityManager()) {
                    entityManager.getTransaction().begin();
                    try {
                        stTUsuario.alta(newBdTUsuario, entityManager);
                        
                        StTUnidad stTUnidad = new StTUnidad(datosUsuarioSystem);
                        BdTUnidad filtroBdTUnidad = new BdTUnidad();
                        filtroBdTUnidad.setCoUnidad("UGF");
                        filtroBdTUnidad.setFeAlta(new Date());
                        filtroBdTUnidad.setFeDesactivo(new Date());
                        ArrayList<BdTUnidad> listaBdTUnidad = stTUnidad.filtro(filtroBdTUnidad, entityManager);
                        if (listaBdTUnidad == null || listaBdTUnidad.isEmpty()) {
                            throw new RegistryNotFoundException();
                        }
                        
                        StAUniusu stAUniusu = new StAUniusu(datosUsuarioSystem);
                        BdAUniusu newBdAUniusu = new BdAUniusu();
                        newBdAUniusu.setIdUnidad(listaBdTUnidad.get(0).getIdUnidad());
                        newBdAUniusu.setIdUsuario(newBdTUsuario.getIdUsuario());
                        newBdAUniusu.setFeAlta(new Date());
                        newBdAUniusu.setFeDesactivo(null);
                        
                        stAUniusu.alta(newBdAUniusu, entityManager);
                        
                        StTTipousuario stTTipousuario = new StTTipousuario(datosUsuarioSystem);
                        BdTTipousuario filtroBdTTipousuario = new BdTTipousuario();
                        filtroBdTTipousuario.setCoTipousuario("FIRMANTE");
                        filtroBdTTipousuario.setIdUnidad(newBdAUniusu.getIdUnidad());
                        filtroBdTTipousuario.setFeAlta(new Date());
                        filtroBdTTipousuario.setFeDesactivo(new Date());
                        
                        ArrayList<BdTTipousuario> listaBdTTipousuario = stTTipousuario.filtro(filtroBdTTipousuario, entityManager);
                        if (listaBdTTipousuario == null || listaBdTTipousuario.isEmpty()) {
                            throw new RegistryNotFoundException();
                        }
                        
                        StAUsutipousu stAUsutipousu = new StAUsutipousu(datosUsuarioSystem);
                        BdAUsutipousu newBdAUsutipousu = new BdAUsutipousu();
                        newBdAUsutipousu.setIdUsuario(newBdTUsuario.getIdUsuario());
                        newBdAUsutipousu.setIdTipousuario(listaBdTTipousuario.get(0).getIdTipousuario());
                        newBdAUsutipousu.setFeAlta(new Date());
                        newBdAUsutipousu.setFeDesactivo(null);
                        
                        stAUsutipousu.alta(newBdAUsutipousu, entityManager);
                    }
                    catch (Exception ex) {
                        entityManager.getTransaction().rollback();
                        throw ex;
                    }
                }
                // FIN TRANSACCION

                crearDatosUsuario((HttpServletRequest) request, (HttpServletResponse) response, request.getServletContext());
                login(newBdTUsuario, datosUsuarioSystem);
            }
            
            // Redirecciono al formulario principal
            ((HttpServletResponse)response).sendRedirect(request.getServletContext() + "/main");
            //RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/main");
            //dispatcher.forward(request, response);
        }
        else {
            log("No se ha encontrado el certificado.");
        }
    }
}
