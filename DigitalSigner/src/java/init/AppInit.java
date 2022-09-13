package init;

import java.sql.SQLException;
import javax.servlet.http.HttpServlet;
import org.apache.log4j.Logger;
import javax.sql.DataSource;
import tomcat.persistence.ConnectionPool;
import tomcat.persistence.EntityManager;

/**
 *
 * @author ihuegal
 */

public final class AppInit extends HttpServlet {
    
    private static final Logger LOG = Logger.getLogger(AppInit.class);
    
    private static DataSource dsDigitalSigner=null;
    
    @Override
    public void init() {
        try {
            dsDigitalSigner = ConnectionPool.crearPool("jdbc/DigitalSigner");
        } catch (Exception e) {
            LOG.fatal(e);
        }
    }
    
    public static EntityManager getEntityManager() throws SQLException {
        return new EntityManager(dsDigitalSigner.getConnection());
    }
}
