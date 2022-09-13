package basedatos;

import init.AppInit;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import tomcat.persistence.EntityManager;

/**
 *
 * @author ihuegal
 */
public class StBase {
    
    protected ArrayList<LinkedHashMap<String,Object>> executeNativeQueryListParametros(String sql, HashMap<String, Object> parametros, EntityManager em) throws SQLException {
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
}
