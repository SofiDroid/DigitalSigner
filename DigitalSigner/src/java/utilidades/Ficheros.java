package utilidades;

import basedatos.ExecuteQuery;
import init.AppInit;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import org.apache.log4j.Logger;
import tomcat.persistence.EntityManager;

/**
 *
 * @author ihuegal
 */
public class Ficheros {
    public byte[] recuperaDocumento(Integer idDocumento) {
        String sql = "SELECT BL_DOCUMENTO FROM BD_D_DOCUMENTO WHERE ID_DOCUMENTO = :ID_DOCUMENTO";
        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_DOCUMENTO", idDocumento);
        try (EntityManager entityManager = AppInit.getEntityManager()) {
            ArrayList<LinkedHashMap<String, Object>> resultado = new ExecuteQuery().executeNativeQueryListParametros(sql, parametros, entityManager);
            if (resultado != null && !resultado.isEmpty()) {
                return (byte[])resultado.get(0).get("BL_DOCUMENTO");
            }
            return null;
        } catch (SQLException ex) {
            Logger.getLogger(Ficheros.class).error(ex.getMessage(), ex);
        }
        
        return null;
    }
}
