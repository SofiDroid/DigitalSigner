package init;

import java.sql.SQLException;
import javax.servlet.http.HttpServlet;
import org.apache.log4j.Logger;
import javax.sql.DataSource;
import scheduler.Scheduler;
import tomcat.persistence.ConnectionPool;
import tomcat.persistence.EntityManager;
import utilidades.BaseDatos;

/**
 *
 * @author ihuegal
 */

public final class AppInit extends HttpServlet {
    
    public static final BaseDatos TIPO_BASEDATOS = BaseDatos.SQLSERVER; 
    
    private static final Logger LOG = Logger.getLogger(AppInit.class);
    
    private static DataSource dsDigitalSigner=null;
    private Scheduler scheduler = null;
    
    @Override
    public void init() {
        try {
            dsDigitalSigner = ConnectionPool.crearPool("jdbc/DigitalSigner");
            
            // Iniciar scheduler
            if (scheduler != null) {
                scheduler.cancel();
            }
            scheduler = new Scheduler();
            scheduler.start();
            
        } catch (Exception e) {
            LOG.fatal(e);
        }
    }

    @Override
    public void destroy() {
        scheduler.cancel();
        super.destroy();
    }
    
    public static EntityManager getEntityManager() throws SQLException {
        return new EntityManager(dsDigitalSigner.getConnection());
    }
    
    public static String getRutaTempFicherosFirmados() {
        return "";
    }
}
