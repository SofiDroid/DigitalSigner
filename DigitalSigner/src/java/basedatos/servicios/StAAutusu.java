package basedatos.servicios;

import basedatos.Mapeador;
import basedatos.StBase;
import basedatos.tablas.BdAAutusu;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import tomcat.persistence.EntityManager;

/**
 *
 * @author ihuegal
 */
public class StAAutusu extends StBase {
    
    public StAAutusu() {
        //NADA
    }
    
    public ArrayList<BdAAutusu> filtro(BdAAutusu filtroBdAAutusu, EntityManager em) throws Exception {
        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_AUTORIDAD", filtroBdAAutusu.getIdAutoridad());
        parametros.put("ID_USUARIO", filtroBdAAutusu.getIdUsuario());
        parametros.put("FE_ALTA", filtroBdAAutusu.getFeAlta());
        parametros.put("FE_DESACTIVO", filtroBdAAutusu.getFeDesactivo());
        parametros.put("USUARIOBD", filtroBdAAutusu.getUsuariobd());
        parametros.put("TSTBD", filtroBdAAutusu.getTstbd());

        ArrayList<LinkedHashMap<String,Object>> lista = executeNativeQueryListParametros(filtroBdAAutusu.getSelectFiltro(), parametros, em);
        if (lista != null && !lista.isEmpty()) {
            return Mapeador.mapea(lista, BdAAutusu.class);
        }
        return null;
    }
    
    public BdAAutusu item(Integer idAutoridad, Integer idUsuario, EntityManager em) throws Exception {
        BdAAutusu filtroBdAAutusu = new BdAAutusu();
        filtroBdAAutusu.setIdAutoridad(idAutoridad);
        filtroBdAAutusu.setIdUsuario(idUsuario);
        
        ArrayList<BdAAutusu> listaBdAAutusu = filtro(filtroBdAAutusu, em);
        if (listaBdAAutusu != null && !listaBdAAutusu.isEmpty()) {
            return listaBdAAutusu.get(0);
        }
        return null;
    }
}
