package basedatos.servicios;

import basedatos.Mapeador;
import basedatos.StBase;
import basedatos.tablas.BdTSituaciondoc;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import tomcat.persistence.EntityManager;

/**
 *
 * @author ihuegal
 */
public class StTSituaciondoc extends StBase {
    
    public StTSituaciondoc() {
        //NADA
    }
    
    public ArrayList<BdTSituaciondoc> filtro(BdTSituaciondoc filtroBdTSituaciondoc, EntityManager em) throws Exception {
        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_SITUACIONDOC", filtroBdTSituaciondoc.getIdSituaciondoc());
        parametros.put("CO_SITUACIONDOC", filtroBdTSituaciondoc.getCoSituaciondoc());
        parametros.put("DS_SITUACIONDOC", filtroBdTSituaciondoc.getDsSituaciondoc());
        parametros.put("FE_ALTA", filtroBdTSituaciondoc.getFeAlta());
        parametros.put("FE_DESACTIVO", filtroBdTSituaciondoc.getFeDesactivo());
        parametros.put("USUARIOBD", filtroBdTSituaciondoc.getUsuariobd());
        parametros.put("TSTBD", filtroBdTSituaciondoc.getTstbd());

        ArrayList<LinkedHashMap<String,Object>> lista = executeNativeQueryListParametros(filtroBdTSituaciondoc.getSelectFiltro(), parametros, em);
        if (lista != null && !lista.isEmpty()) {
            return Mapeador.mapea(lista, BdTSituaciondoc.class);
        }
        return null;
    }
    
    public BdTSituaciondoc item(Integer idSituaciondoc, EntityManager em) throws Exception {
        BdTSituaciondoc filtroBdTSituaciondoc = new BdTSituaciondoc();
        filtroBdTSituaciondoc.setIdSituaciondoc(idSituaciondoc);
        
        ArrayList<BdTSituaciondoc> listaBdTSituaciondoc = filtro(filtroBdTSituaciondoc, em);
        if (listaBdTSituaciondoc != null && !listaBdTSituaciondoc.isEmpty()) {
            return listaBdTSituaciondoc.get(0);
        }
        return null;
    }
}
