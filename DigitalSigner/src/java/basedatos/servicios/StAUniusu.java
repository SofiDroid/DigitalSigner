package basedatos.servicios;

import basedatos.Mapeador;
import basedatos.StBase;
import basedatos.tablas.BdAUniusu;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import tomcat.persistence.EntityManager;

/**
 *
 * @author ihuegal
 */
public class StAUniusu extends StBase {
    
    public StAUniusu() {
        //NADA
    }
    
    public ArrayList<BdAUniusu> filtro(BdAUniusu filtroBdAUniusu, EntityManager em) throws Exception {
        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_UNIDAD", filtroBdAUniusu.getIdUnidad());
        parametros.put("ID_USUARIO", filtroBdAUniusu.getIdUsuario());
        parametros.put("FE_ALTA", filtroBdAUniusu.getFeAlta());
        parametros.put("FE_DESACTIVO", filtroBdAUniusu.getFeDesactivo());
        parametros.put("USUARIOBD", filtroBdAUniusu.getUsuariobd());
        parametros.put("TSTBD", filtroBdAUniusu.getTstbd());

        ArrayList<LinkedHashMap<String,Object>> lista = executeNativeQueryListParametros(filtroBdAUniusu.getSelectFiltro(), parametros, em);
        if (lista != null && !lista.isEmpty()) {
            return Mapeador.mapea(lista, BdAUniusu.class);
        }
        return null;
    }
    
    public BdAUniusu item(Integer idUnidad, Integer idUsuario, EntityManager em) throws Exception {
        BdAUniusu filtroBdAUniusu = new BdAUniusu();
        filtroBdAUniusu.setIdUnidad(idUnidad);
        filtroBdAUniusu.setIdUsuario(idUsuario);
        
        ArrayList<BdAUniusu> listaBdAUniusu = filtro(filtroBdAUniusu, em);
        if (listaBdAUniusu != null && !listaBdAUniusu.isEmpty()) {
            return listaBdAUniusu.get(0);
        }
        return null;
    }
}
