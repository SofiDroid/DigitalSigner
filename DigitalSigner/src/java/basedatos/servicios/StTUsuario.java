package basedatos.servicios;

import basedatos.Mapeador;
import basedatos.StBase;
import basedatos.tablas.BdTUsuario;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import tomcat.persistence.EntityManager;

/**
 *
 * @author ihuegal
 */
public class StTUsuario extends StBase {
    
    public StTUsuario() {
        //NADA
    }
    
    public ArrayList<BdTUsuario> filtro(BdTUsuario filtroBdTUsuario, EntityManager em) throws Exception {
        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_USUARIO", filtroBdTUsuario.getIdUsuario());
        parametros.put("CO_NIF", filtroBdTUsuario.getCoNIF());
        parametros.put("CO_USUARIO", filtroBdTUsuario.getCoUsuario());
        parametros.put("CO_PASSWORD", filtroBdTUsuario.getCoPassword());
        parametros.put("DS_NOMBRE", filtroBdTUsuario.getDsNombre());
        parametros.put("DS_APELLIDO1", filtroBdTUsuario.getDsApellido1());
        parametros.put("DS_APELLIDO2", filtroBdTUsuario.getDsApellido2());
        parametros.put("EN_INTENTOS", filtroBdTUsuario.getEnIntentos());
        parametros.put("EN_INTENTOSMAX", filtroBdTUsuario.getEnIntentosmax());
        parametros.put("BO_ADMIN", filtroBdTUsuario.getBoAdmin());
        parametros.put("FE_ALTA", filtroBdTUsuario.getFeAlta());
        parametros.put("FE_DESACTIVO", filtroBdTUsuario.getFeDesactivo());
        parametros.put("USUARIOBD", filtroBdTUsuario.getUsuariobd());
        parametros.put("TSTBD", filtroBdTUsuario.getTstbd());

        ArrayList<LinkedHashMap<String,Object>> lista = executeNativeQueryListParametros(filtroBdTUsuario.getSelectFiltro(), parametros, em);
        if (lista != null && !lista.isEmpty()) {
            return Mapeador.mapea(lista, BdTUsuario.class);
        }
        return null;
    }
    
    public BdTUsuario item(Integer idUsuario, EntityManager em) throws Exception {
        BdTUsuario filtroBdTUsuario = new BdTUsuario();
        filtroBdTUsuario.setIdUsuario(idUsuario);
        
        ArrayList<BdTUsuario> listaBdTUsuario = filtro(filtroBdTUsuario, em);
        if (listaBdTUsuario != null && !listaBdTUsuario.isEmpty()) {
            return listaBdTUsuario.get(0);
        }
        return null;
    }
}
