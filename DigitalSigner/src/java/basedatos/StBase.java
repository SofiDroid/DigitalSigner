package basedatos;

import init.AppInit;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import tomcat.persistence.EntityManager;
import tomcat.persistence.ResultData;

/**
 *
 * @author ihuegal
 */
public class StBase {
    
    protected ResultData executeNativeQueryListParametros(String sql, HashMap<String, Object> parametros, EntityManager em) throws SQLException {
        boolean conexionNueva = (em == null);
        EntityManager entityManager = em;
        try 
        {   
            if (conexionNueva) {
                entityManager = AppInit.getEntityManager();
            }
            
            ExecuteQuery executeQuery = new ExecuteQuery();
            return executeQuery.executeNativeQueryListParametros(sql.trim(), parametros, entityManager);
        } catch (SQLException e) {
            throw e;
        } finally {
            if ((conexionNueva) && (entityManager != null)) {
                entityManager.close();
            }
        }
    }
    
    protected int executeNativeQueryParametros(String sql, HashMap<String, Object> parametros, EntityManager em) throws Exception {
        boolean conexionNueva = (em == null);
        EntityManager entityManager = em;
        try 
        {   
            if (conexionNueva) {
                entityManager = AppInit.getEntityManager();
                entityManager.getTransaction().begin();
            }
            
            ExecuteQuery executeQuery = new ExecuteQuery();
            return executeQuery.executeNativeQueryParametros(sql.trim(), parametros, entityManager);
        } catch (SQLException e) {
            if (entityManager != null && entityManager.getTransaction() != null && entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw e;
        } finally {
            if (conexionNueva && entityManager != null) {
                try {
                    if (entityManager.getTransaction() != null && entityManager.getTransaction().isActive()) {
                        entityManager.getTransaction().commit();
                    }
                }
                finally {
                    entityManager.close();
                }
            }
        }
    }
}
