/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tomcat.persistence;

import java.sql.SQLException;
import org.apache.tomcat.jdbc.pool.ConnectionPool;
import org.apache.tomcat.jdbc.pool.JdbcInterceptor;
import org.apache.tomcat.jdbc.pool.PooledConnection;
import org.apache.log4j.Logger;

/**
 *
 * @author ihuegal
 */
public class RollbackInterceptor extends JdbcInterceptor {

    private static final Logger LOG = Logger.getLogger(RollbackInterceptor.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public void reset(ConnectionPool cp, PooledConnection pc) {
        //NADA
    }

    @Override
    public void disconnected(ConnectionPool parent, PooledConnection con, boolean finalizing) {
        // if its oracle make sure we rollback here before disconnect just in case a running TX is open
        try {
            if (!con.getConnection().getAutoCommit()) {
              LOG.error("Connection " + con.getStackTrace() + " with Auto-Commit false is going to be closed. Doing an explicit Rollback here!");
              try {
                con.getConnection().rollback();
              } catch (SQLException e) {
                LOG.error("Failed to rollback connection " + con.getStackTrace() + " before closing it.", e);
              }
            }
        } catch (SQLException e) {
          LOG.error("Failed to check auto commit of connection " + con.getStackTrace() + "", e);
        }
        super.disconnected(parent, con, finalizing);
    }
    
    
}
