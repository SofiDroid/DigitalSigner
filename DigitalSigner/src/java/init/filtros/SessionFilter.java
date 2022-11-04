package init.filtros;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.ResourceHandler;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utilidades.Session;

/**
 *
 * @author ihuegal
 */
public class SessionFilter implements Filter {
  
    private static final boolean DEBUG = false;

    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    private FilterConfig filterConfig = null;
    
    public SessionFilter() {
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
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        
        if (DEBUG) {
            log("AuthenticationFilter:doFilter()");
        }
        
        //doBeforeProcessing(request, response);
        
        Throwable problem = null;
        try {
            //Resources
            boolean isResource = (((HttpServletRequest)request).getRequestURI().contains(ResourceHandler.RESOURCE_IDENTIFIER)
                    || !((HttpServletRequest)request).getHttpServletMapping().getMatchValue().endsWith(".xhtml"));

            boolean isLogin = ((HttpServletRequest)request).getHttpServletMapping().getMatchValue().equalsIgnoreCase("index.xhtml");
            boolean isLogout = ((HttpServletRequest)request).getHttpServletMapping().getMatchValue().equalsIgnoreCase("logout.xhtml");
            
            if (!isResource && !isLogin && !isLogout) {
                try {
                    if (!Session.isSessionActiva()) {
                        // Redirect al login si no está logado.
                        //filterConfig.getServletContext().getRequestDispatcher("/faces/logout.xhtml").forward(request, response);
                        ((HttpServletResponse)response).sendRedirect(request.getServletContext().getContextPath() + "/faces/logout.xhtml");
                        ((HttpServletResponse)response).flushBuffer();
                    }
                    else {
                        chain.doFilter(request, response);
                    }
                } catch (IOException | ServletException ex) {
                    Logger.getLogger(SessionFilter.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else {
                chain.doFilter(request, response);
            }
        } catch (IOException | ServletException t) {
            // If an exception is thrown somewhere down the filter chain,
            // we still want to execute our after processing, and then
            // rethrow the problem after that.
            problem = t;
            //t.printStackTrace();
        }
        
        //doAfterProcessing(request, response);

        // If there was a problem, we want to rethrow it if it is
        // a known type, otherwise log it.
        if (problem != null) {
            if (problem instanceof ServletException servletException) {
                throw servletException;
            }
            if (problem instanceof IOException iOException) {
                throw iOException;
            }
            sendProcessingError(problem, response);
        }
    }

    /**
     * Return the filter configuration object for this fil
     * @return ter.
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
    @Override
    public void destroy() {        
    }

    /**
     * Init method for this filter
     * @param filterConfig
     */
    @Override
    public void init(FilterConfig filterConfig) {        
        this.filterConfig = filterConfig;
        if (filterConfig != null) {
            if (DEBUG) {                
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
        StringBuilder sb = new StringBuilder("AuthenticationFilter(");
        sb.append(filterConfig);
        sb.append(")");
        return (sb.toString());
    }
    
    private void sendProcessingError(Throwable t, ServletResponse response) {
        String stackTrace = getStackTrace(t);        
        
        if (stackTrace != null && !stackTrace.equals("")) {
            try {
                response.setContentType("text/html");
                try (PrintStream ps = new PrintStream(response.getOutputStream()); PrintWriter pw = new PrintWriter(ps)) {
                    pw.print("<html>\n<head>\n<title>Error</title>\n</head>\n<body>\n"); //NOI18N
                    
                    // PENDING! Localize this for next official release
                    pw.print("<h1>The resource did not process correctly</h1>\n<pre>\n");
                    pw.print(stackTrace);
                    pw.print("</pre></body>\n</html>"); //NOI18N
                }
                response.getOutputStream().close();
            } catch (IOException ex) {
            }
        } else {
            try {
                try (PrintStream ps = new PrintStream(response.getOutputStream())) {
                    t.printStackTrace(ps);
                }
                response.getOutputStream().close();
            } catch (IOException ex) {
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
        } catch (IOException ex) {
        }
        return stackTrace;
    }
    
    public void log(String msg) {
        filterConfig.getServletContext().log(msg);        
    }
}
