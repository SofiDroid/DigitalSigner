package basedatos.servicios;

import basedatos.Mapeador;
import basedatos.StBase;
import basedatos.tablas.BdTSituacionxml;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import tomcat.persistence.EntityManager;

/**
 *
 * @author ihuegal
 */
public class StTSituacionxml extends StBase {
    
    public StTSituacionxml() {
        //NADA
    }
    
    public ArrayList<BdTSituacionxml> filtro(BdTSituacionxml filtroBdTSituacionxml, EntityManager em) throws Exception {
        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_SITUACIONXML", filtroBdTSituacionxml.getIdSituacionxml());
        parametros.put("CO_SITUACIONXML", filtroBdTSituacionxml.getCoSituacionxml());
        parametros.put("DS_SITUACIONXML", filtroBdTSituacionxml.getDsSituacionxml());
        parametros.put("FE_ALTA", filtroBdTSituacionxml.getFeAlta());
        parametros.put("FE_DESACTIVO", filtroBdTSituacionxml.getFeDesactivo());
        parametros.put("USUARIOBD", filtroBdTSituacionxml.getUsuariobd());
        parametros.put("TSTBD", filtroBdTSituacionxml.getTstbd());

        ArrayList<LinkedHashMap<String,Object>> lista = executeNativeQueryListParametros(filtroBdTSituacionxml.getSelectFiltro(), parametros, em);
        if (lista != null && !lista.isEmpty()) {
            return Mapeador.mapea(lista, BdTSituacionxml.class);
        }
        return null;
    }
    
    public BdTSituacionxml item(Integer idSituacionxml, EntityManager em) throws Exception {
        BdTSituacionxml filtroBdTSituacionxml = new BdTSituacionxml();
        filtroBdTSituacionxml.setIdSituacionxml(idSituacionxml);
        
        ArrayList<BdTSituacionxml> listaBdTSituacionxml = filtro(filtroBdTSituacionxml, em);
        if (listaBdTSituacionxml != null && !listaBdTSituacionxml.isEmpty()) {
            return listaBdTSituacionxml.get(0);
        }
        return null;
    }
}
