/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tomcat.persistence;

import java.util.Properties;
import javax.sql.DataSource;
import java.util.logging.Level;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

//
// Estas con las clases especificas de dbcp2 y pool2
// Estas solo se usan en el metodo setupDataSource.
// En un uso normal, tu clases solo interactuaran
// con el API JDBC estandar.
//
import org.apache.log4j.Logger;
import org.apache.tomcat.jdbc.pool.DataSourceFactory;

/**
 *
 * @author ihuegal
 */
public class ConnectionPool {

    /**
     * ENTORNO DEL APLICATIVO
     */
    private static final Logger LOG = Logger.getLogger(ConnectionPool.class);
    
    private static javax.sql.DataSource setupDataSource(String connectionName) {
        try {
            Context initContext = new InitialContext();
            Context envContext  = (Context)initContext.lookup("java:/comp/env");
            
            return (DataSource)envContext.lookup(connectionName);
        } catch (NamingException ex) {
            java.util.logging.Logger.getLogger(ConnectionPool.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    private static javax.sql.DataSource setupDataSource(String url, String user, String password) {
        try {
            Properties prop = new Properties();
            prop.put("jdbcInterceptors","org.apache.tomcat.jdbc.pool.interceptor.ResetAbandonedTimer; org.apache.tomcat.jdbc.pool.interceptor.ConnectionState; org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer; org.apache.tomcat.jdbc.pool.interceptor.QueryTimeoutInterceptor");
            prop.put("driverClassName","oracle.jdbc.OracleDriver");
            prop.put("defaultAutoCommit","false");
            prop.put("rollbackOnReturn","true");
            prop.put("maxActive","10");
            prop.put("maxIdle","8");
            prop.put("minIdle","4");
            prop.put("initialSize","4");
            prop.put("maxWait","5000");
            prop.put("testOnBorrow","true");
            prop.put("testOnReturn","false");
            prop.put("testWhileIdle","true");
            prop.put("timeBetweenEvictionRunsMillis","30000");
            prop.put("minEvictableIdleTimeMillis","100000");
            prop.put("validationQuery","SELECT 1 from dual");
            prop.put("validationInterval","30000");
            prop.put("jmxEnabled","true");
            prop.put("removeAbandoned","true");
            prop.put("removeAbandonedTimeout","300");
            prop.put("logAbandoned","true");
            prop.put("username", user);
            prop.put("password", password);
            prop.put("url", url);
            
            return setupDataSource(prop);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(ConnectionPool.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    private static javax.sql.DataSource setupDataSource(Properties prop) {
        try {
            DataSourceFactory dsf = new DataSourceFactory();
            DataSource ds = dsf.createDataSource(prop);
            
            return ds;
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(ConnectionPool.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static DataSource crearPool(String contextConnectionName) {
        DataSource ds = setupDataSource(contextConnectionName);
        ((org.apache.tomcat.jdbc.pool.DataSource)ds).setJdbcInterceptors(((org.apache.tomcat.jdbc.pool.DataSource)ds).getJdbcInterceptors() + ";tomcat.persistence.RollbackInterceptor");

        LOG.info("### Creado Pool de Conexiones Tomcat JDBC: Contexto -> " + contextConnectionName + " ###");
        LOG.info(getStatus(ds));
        
        return ds;
    }
    
    public static DataSource crearPool(String url, String user, String password) {

        DataSource ds = setupDataSource(url, user, password);
        ((org.apache.tomcat.jdbc.pool.DataSource)ds).setJdbcInterceptors(((org.apache.tomcat.jdbc.pool.DataSource)ds).getJdbcInterceptors() + ";tomcat.persistence.RollbackInterceptor");

        LOG.info("### Creado Pool de Conexiones Tomcat JDBC: User/Password/Url -> " + user + "/" + password + "/" + url + " ###");
        LOG.info(getStatus(ds));
        
        return ds;
    }
    
    public static DataSource crearPool(Properties prop) {

        DataSource ds = setupDataSource(prop);
        ((org.apache.tomcat.jdbc.pool.DataSource)ds).setJdbcInterceptors(((org.apache.tomcat.jdbc.pool.DataSource)ds).getJdbcInterceptors() + ";tomcat.persistence.RollbackInterceptor");

        LOG.info("### Creado Pool de Conexiones Tomcat JDBC: Properties -> " + prop.toString() + " ###");
        LOG.info(getStatus(ds));
        
        return ds;
    }
    
    public static String getStatus(DataSource ds) {
        return String.format("Pool de Conexiones (Activas = %d [%d max] | Idle = %d [%d max] | Max.Wait = %d ms | AutoCommit = %b | CommitOnReturn = %b | Interceptors = %s)", ((org.apache.tomcat.jdbc.pool.DataSource)ds).getActive(), ((org.apache.tomcat.jdbc.pool.DataSource)ds).getMaxActive(), ((org.apache.tomcat.jdbc.pool.DataSource)ds).getNumIdle(), ((org.apache.tomcat.jdbc.pool.DataSource)ds).getMaxIdle(), ((org.apache.tomcat.jdbc.pool.DataSource)ds).getMaxWait(), ((org.apache.tomcat.jdbc.pool.DataSource)ds).getDefaultAutoCommit(), ((org.apache.tomcat.jdbc.pool.DataSource)ds).getCommitOnReturn(), ((org.apache.tomcat.jdbc.pool.DataSource)ds).getJdbcInterceptors());
    }
}
