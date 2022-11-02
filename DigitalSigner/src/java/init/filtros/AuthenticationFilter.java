package init.filtros;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Enumeration;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;

/**
 *
 * @author ihuegal
 */
public class AuthenticationFilter implements Filter {
//    Logger logger = Logger.getLogger(AuthenticationFilter.class);
    
    private static final boolean debug = true;

    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    private FilterConfig filterConfig = null;
    
    public AuthenticationFilter() {
    }    
    
    //Método que evalúa si se debe ejecutar la lógica de cada condición/caso
//    private boolean requiresAuthentication(HttpServletRequest req, boolean isAPI) {
//        if (!isAPI) {
//                    //Si no es una API REST
//            return !req.getServletPath().startsWith(LOGOUT_PREFIX) 
//                           &&  req.getHeader(CERT_HEADER) != null
//                           && SecurityContextHolder.getContext().getAuthentication() == null;
//        } else {
//                    //Si es una API REST
//            return req.getServletPath().startsWith(API_PREFIX) 
//                           && req.getHeader(CERT_HEADER) != null;
//        }
//    }

    //Método que devuelve el objeto autenticación con el usuario actual, haciendo uso del
    //servicio creado anteriormente y del UserDetailsService para cargar los detalles del 
    //usuario extraido del certificado
//    private Authentication prepareX509Authentication(HttpServletRequest req)
//        throws CertificateException, UnsupportedEncodingException, Exception {
//            x509Service.extractUserNameFromCert(req.getHeader(CERT_HEADER)).ifPresent(id -> 
//            {
//                final UserDetails details = detailsService.loadUserByUsername(id);
//                if (details != null) {
//                    final Authentication auth = new UsernamePasswordAuthenticationToken(details, details.getPassword(),details.getAuthorities());
//                               //Setea el contexto de seguridad de Spring
//                    InterceptorCommon.setContexts(auth);
//                    log.debug("Loaded authentication for user {}", auth.getName());
//            }
//        });
//        return SecurityContextHolder.getContext().getAuthentication();
//
//    }
    
//    private void autenticacion(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        final HttpServletRequest req = (HttpServletRequest) request;
//        if (requiresAuthentication(req, true)) {
//            logger.debug("Detected header {} in API request, loading temp autenthication");//, CERT_HEADER);
//            try {
//                prepareX509Authentication(req);
//                chain.doFilter(request, response);
//
//            } catch (final Exception e) {
//                        logger.error("Error on X509 REST authentication", e);
//            } finally {
//                logger.debug("Clearing authentication contexts");
//                InterceptorCommon.clearContexts();
//            }
//        } else if (requiresAuthentication(req, false)) {
//            logger.debug("Detected header {} in Non API request, loading autenthication & session", CERT_HEADER);
//            try {
//                final Authentication auth = prepareX509Authentication(req);
//                if (auth != null)
//                    successHandler.onAuthenticationSuccess(req, (HttpServletResponse) response, auth);
//                chain.doFilter(request, response);
//            } catch (final Exception e) {
//                logger.error("Error on X509 authentication", e);
//            }
//        } else {
//            chain.doFilter(request, response);
//        }
//    }
    
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

        // Write code here to process the request and/or response before
        // the rest of the filter chain is invoked.
        // For example, a logging filter might log items on the request object,
        // such as the parameters.
        /*
	for (Enumeration en = request.getParameterNames(); en.hasMoreElements(); ) {
	    String name = (String)en.nextElement();
	    String values[] = request.getParameterValues(name);
	    int n = values.length;
	    StringBuilder buf = new StringBuilder();
	    buf.append(name);
	    buf.append("=");
	    for(int i=0; i < n; i++) {
	        buf.append(values[i]);
	        if (i < n-1)
	            buf.append(",");
	    }
	    log(buf.toString());
	}
         */
    }    
    
    private void doAfterProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("AuthenticationFilter:DoAfterProcessing");
        }

        // Write code here to process the request and/or response after
        // the rest of the filter chain is invoked.
        // For example, a logging filter might log the attributes on the
        // request object after the request has been processed. 
        /*
	for (Enumeration en = request.getAttributeNames(); en.hasMoreElements(); ) {
	    String name = (String)en.nextElement();
	    Object value = request.getAttribute(name);
	    log("attribute: " + name + "=" + value.toString());

	}
         */
        // For example, a filter might append something to the response.
        /*
	PrintWriter respOut = new PrintWriter(response.getWriter());
	respOut.println("<P><B>This has been appended by an intrusive filter.</B>");
         */
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
        
//        autenticacion(request, response, chain);
        
        Throwable problem = null;
        try {
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
    
}
