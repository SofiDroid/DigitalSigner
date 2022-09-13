package basedatos;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import tomcat.persistence.EntityManager;
import tomcat.persistence.Query;

/**
 *
 * @author ihuegal
 */
public class ExecuteQuery {

    public ExecuteQuery() {
        // NADA
    }

    public ArrayList<LinkedHashMap<String,Object>> executeNativeQueryListParametros(String sql, HashMap<String, Object> parametros, EntityManager entityManager) throws SQLException {
        try {
            Query sentencia = entityManager.createNativeQuery(sql);
            if (parametros != null) {
                for (Map.Entry<String, Object> item : parametros.entrySet()) {
                    sentencia.setParameter((String) item.getKey(), item.getValue());
                }
            }
            return (ArrayList<LinkedHashMap<String,Object>>) sentencia.getResultListMapped();

        } catch (SQLException e) {
            throw e;
        }
    }
}
