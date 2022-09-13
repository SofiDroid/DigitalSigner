package basedatos.servicios;

import basedatos.Mapeador;
import basedatos.StBase;
import basedatos.tablas.BdTUnidad;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import tomcat.persistence.EntityManager;

/**
 *
 * @author ihuegal
 */
public class StTUnidad extends StBase {
    
    public StTUnidad() {
        //NADA
    }
    
    public ArrayList<BdTUnidad> filtro(BdTUnidad filtroBdTUnidad, EntityManager em) throws Exception {
        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_UNIDAD", filtroBdTUnidad.getIdUnidad());
        parametros.put("CO_UNIDAD", filtroBdTUnidad.getCoUnidad());
        parametros.put("DS_UNIDAD", filtroBdTUnidad.getDsUnidad());
        parametros.put("FE_ALTA", filtroBdTUnidad.getFeAlta());
        parametros.put("FE_DESACTIVO", filtroBdTUnidad.getFeDesactivo());
        parametros.put("USUARIOBD", filtroBdTUnidad.getUsuariobd());
        parametros.put("TSTBD", filtroBdTUnidad.getTstbd());

        ArrayList<LinkedHashMap<String,Object>> lista = executeNativeQueryListParametros(filtroBdTUnidad.getSelectFiltro(), parametros, em);
        if (lista != null && !lista.isEmpty()) {
            return Mapeador.mapea(lista, BdTUnidad.class);
        }
        return null;
    }
    
    public BdTUnidad item(Integer idUnidad, EntityManager em) throws Exception {
        BdTUnidad filtroBdTUnidad = new BdTUnidad();
        filtroBdTUnidad.setIdUnidad(idUnidad);
        
        ArrayList<BdTUnidad> listaBdTUnidad = filtro(filtroBdTUnidad, em);
        if (listaBdTUnidad != null && !listaBdTUnidad.isEmpty()) {
            return listaBdTUnidad.get(0);
        }
        return null;
    }
}
