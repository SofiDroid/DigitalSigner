package basedatos.servicios;

import basedatos.Mapeador;
import basedatos.StBase;
import basedatos.tablas.BdTAutoridad;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import tomcat.persistence.EntityManager;

/**
 *
 * @author ihuegal
 */
public class StTAutoridad extends StBase {
    
    public StTAutoridad() {
        //NADA
    }
    
    public ArrayList<BdTAutoridad> filtro(BdTAutoridad filtroBdTAutoridad, EntityManager em) throws Exception {
        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_AUTORIDAD", filtroBdTAutoridad.getIdAutoridad());
        parametros.put("CO_AUTORIDAD", filtroBdTAutoridad.getCoAutoridad());
        parametros.put("DS_AUTORIDAD", filtroBdTAutoridad.getDsAutoridad());
        parametros.put("ID_UNIDAD", filtroBdTAutoridad.getIdUnidad());
        parametros.put("FE_ALTA", filtroBdTAutoridad.getFeAlta());
        parametros.put("FE_DESACTIVO", filtroBdTAutoridad.getFeDesactivo());
        parametros.put("USUARIOBD", filtroBdTAutoridad.getUsuariobd());
        parametros.put("TSTBD", filtroBdTAutoridad.getTstbd());

        ArrayList<LinkedHashMap<String,Object>> lista = executeNativeQueryListParametros(filtroBdTAutoridad.getSelectFiltro(), parametros, em);
        if (lista != null && !lista.isEmpty()) {
            return Mapeador.mapea(lista, BdTAutoridad.class);
        }
        return null;
    }
    
    public BdTAutoridad item(Integer idAutoridad, EntityManager em) throws Exception {
        BdTAutoridad filtroBdTAutoridad = new BdTAutoridad();
        filtroBdTAutoridad.setIdAutoridad(idAutoridad);
        
        ArrayList<BdTAutoridad> listaBdTAutoridad = filtro(filtroBdTAutoridad, em);
        if (listaBdTAutoridad != null && !listaBdTAutoridad.isEmpty()) {
            return listaBdTAutoridad.get(0);
        }
        return null;
    }
}
