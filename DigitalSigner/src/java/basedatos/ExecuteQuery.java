package basedatos;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import tomcat.persistence.EntityManager;
import tomcat.persistence.Query;
import tomcat.persistence.ResultData;

/**
 *
 * @author ihuegal
 */
public class ExecuteQuery {

    public ExecuteQuery() {
        // NADA
    }

    public ResultData executeNativeQueryListParametros(String sql, HashMap<String, Object> parametros, EntityManager entityManager) throws SQLException {
        try {
            Query sentencia = entityManager.createNativeQuery(sql);
            if (parametros != null) {
                for (Map.Entry<String, Object> item : parametros.entrySet()) {
                    sentencia.setParameter((String) item.getKey(), item.getValue());
                }
            }
            return sentencia.getResultListMapped();

        } catch (SQLException e) {
            throw e;
        }
    }
    
    public int executeNativeQueryParametros(String sql, HashMap<String, Object> parametros, EntityManager entityManager) throws Exception {
        try {
            Query sentencia = entityManager.createNativeQuery(sql);
            if (parametros != null) {
                for (Map.Entry<String, Object> item : parametros.entrySet()) {
                    sentencia.setParameter((String) item.getKey(), item.getValue());
                }
            }
            return sentencia.executeUpdate();

        } catch (SQLException e) {
            throw e;
        }
    }
}
