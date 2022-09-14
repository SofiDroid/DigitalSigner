package basedatos.servicios;

import basedatos.Mapeador;
import basedatos.StBase;
import basedatos.tablas.BdADocrechazo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import tomcat.persistence.EntityManager;

/**
 *
 * @author ihuegal
 */
public class StADocrechazo extends StBase {
    
    public StADocrechazo() {
        //NADA
    }
    
    public ArrayList<BdADocrechazo> filtro(BdADocrechazo filtroBdADocrechazo, EntityManager em) throws Exception {
        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_DOCRECHAZO", filtroBdADocrechazo.getIdDocrechazo());
        parametros.put("ID_DOCUMENTO", filtroBdADocrechazo.getIdDocumento());
        parametros.put("DS_OBSERVACIONES", filtroBdADocrechazo.getDsObservaciones());
        parametros.put("FE_ALTA", filtroBdADocrechazo.getFeAlta());
        parametros.put("FE_DESACTIVO", filtroBdADocrechazo.getFeDesactivo());
        parametros.put("USUARIOBD", filtroBdADocrechazo.getUsuariobd());
        parametros.put("TSTBD", filtroBdADocrechazo.getTstbd());

        ArrayList<LinkedHashMap<String,Object>> lista = executeNativeQueryListParametros(filtroBdADocrechazo.getSelectFiltro(), parametros, em);
        if (lista != null && !lista.isEmpty()) {
            return Mapeador.mapea(lista, BdADocrechazo.class);
        }
        return null;
    }
    
    public BdADocrechazo item(Integer idDocrechazo, EntityManager em) throws Exception {
        BdADocrechazo filtroBdADocrechazo = new BdADocrechazo();
        filtroBdADocrechazo.setIdDocrechazo(idDocrechazo);
        
        ArrayList<BdADocrechazo> listaBdADocrechazo = filtro(filtroBdADocrechazo, em);
        if (listaBdADocrechazo != null && !listaBdADocrechazo.isEmpty()) {
            return listaBdADocrechazo.get(0);
        }
        return null;
    }
}
