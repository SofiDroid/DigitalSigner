/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package init;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.log4j.Logger;

/**
 * Clase de inicio de propiedades.
 * 
 * @author Iván Huertas Galeano (Alhambra Systems S.A.)
 */
public final class AppInit implements ServletContextListener {

    private static final Logger LOG = Logger.getLogger(AppInit.class);
    
    /**
     * Entorno del WebService
     */
    private static InitialContext ctx=null;
    private static Context envCtx=null; 
    
    /** 
     * Los distintos content type conocidos 
     */
    private static final Properties contentTypes = new Properties();
    
    /**
     * Variable que almacena las propiedades de configuración
     */
    private static Properties config;
    
    
    /**
     * Propiedades del servidor de correo
     */
    private static String servidorCorreo;
    private static Integer puertoServidorCorreo;
    private static String senderServidorCorreo;
    private static String usuarioServidorCorreo;
    private static String passwordServidorCorreo;

    /**
     * Constructor privado
     */
    public AppInit() {
        
    }

    /**
     * GET
     * @return 
     */
    public static Properties getConfig() {
        return config;
    }

    /**
     * SET
     * @param config 
     */
    public static void setConfig(Properties config) {
        AppInit.config = config;
    }

    /**
     * GET
     * @return Propiedad solicitada
     * @param ext Extensión del fichero
     */
    public static synchronized String getContentType(String ext) {
        return contentTypes.getProperty(ext);
    }
    
    /**
     * GET
     * @return 
     */
    public static String getServidorCorreo() {
        return servidorCorreo;
    }

    /**
     * SET
     * @param servidorCorreo 
     */
    public static void setServidorCorreo(String servidorCorreo) {
        AppInit.servidorCorreo = servidorCorreo;
    }

    /**
     * GET
     * @return 
     */
    public static Integer getPuertoServidorCorreo() {
        return puertoServidorCorreo;
    }

    /**
     * SET
     * @param puertoServidorCorreo 
     */
    public static void setPuertoServidorCorreo(Integer puertoServidorCorreo) {
        AppInit.puertoServidorCorreo = puertoServidorCorreo;
    }

    /**
     * GET
     * @return 
     */
    public static String getSenderServidorCorreo() {
        return senderServidorCorreo;
    }

    /**
     * SET
     * @param senderServidorCorreo 
     */
    public static void setSenderServidorCorreo(String senderServidorCorreo) {
        AppInit.senderServidorCorreo = senderServidorCorreo;
    }

    /**
     * GET
     * @return 
     */
    public static String getUsuarioServidorCorreo() {
        return usuarioServidorCorreo;
    }

    /**
     * SET
     * @param usuarioServidorCorreo 
     */
    public static void setUsuarioServidorCorreo(String usuarioServidorCorreo) {
        AppInit.usuarioServidorCorreo = usuarioServidorCorreo;
    }

    /**
     * GET
     * @return 
     */
    public static String getPasswordServidorCorreo() {
        return passwordServidorCorreo;
    }

    /**
     * SET
     * @param passwordServidorCorreo 
     */
    public static void setPasswordServidorCorreo(String passwordServidorCorreo) {
        AppInit.passwordServidorCorreo = passwordServidorCorreo;
    }

    /**
     * Función que carga las propiedades del fichero "config.properties"
     * 
     * @throws Exception 
     */
    private void cargarPropiedades() throws Exception {
        LOG.info("Cargando fichero de configuración...");

        String fs = File.separator;
        String rutaBaseLocal = null;
        String entConfig;

        // Obtenemos el contexto de nuestro entorno. La cadena
        ctx = new InitialContext();
        envCtx = (Context) ctx.lookup("java:comp/env");

        entConfig = System.getenv("config.properties");
        if ((entConfig == null) || ("".equals(entConfig)))
        {
            entConfig = System.getProperty ("config.properties");
        }
        if ((entConfig == null) || ("".equals(entConfig)))
        {
            try {
                entConfig = (String) envCtx.lookup ("config.properties");
            } catch (Exception e) {
            }
        }

        if (entConfig != null)
        {
            // Hay variable de entorno, miro a ver si hay fichero
            final File configFile = new File(entConfig, "config.properties").getCanonicalFile();
            if (configFile.isFile() && configFile.canRead())
            {
                rutaBaseLocal = entConfig + fs;
            }
        }

        if (rutaBaseLocal == null)
        {
            rutaBaseLocal = System.getenv("CATALINA_HOME") + fs + "conf" + fs;
        }

        LOG.info("Ruta ruta fichero configuración: " + rutaBaseLocal + "config.properties");

        AppInit.config = new Properties();
        AppInit.config.load(new FileInputStream(rutaBaseLocal + "config.properties"));

        AppInit.servidorCorreo = AppInit.config.getProperty("servidorCorreo");
        AppInit.puertoServidorCorreo = Integer.valueOf(AppInit.config.getProperty("puertoServidorCorreo", "25"));
        AppInit.usuarioServidorCorreo = AppInit.config.getProperty("usuarioServidorCorreo");
        AppInit.passwordServidorCorreo = AppInit.config.getProperty("passwordServidorCorreo");

        AppInit.senderServidorCorreo = AppInit.config.getProperty("senderServidorCorreo");

        LOG.info("Fichero de configuración cargado correctamente!");
    }

    private void cargarContenTypes() throws Exception {
        LOG.info("Cargando contentTypes....");
        
        AppInit.contentTypes.clear();
        AppInit.contentTypes.load(AppInit.class.getResourceAsStream("contentTypes.properties"));
        
        LOG.info("ContentTypes cargados correctamente.");
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            // Constructor privado para que no me dupliquen la clase.
            LOG.info("Inicializando WebService...");

            cargarContenTypes();
            cargarPropiedades();

            LOG.info("Servicio inicializado.");
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // NADA
    }
}
