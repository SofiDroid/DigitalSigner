package basedatos.servicios;

import basedatos.Mapeador;
import basedatos.StBase;
import basedatos.tablas.BdAConftipodoc;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import tomcat.persistence.EntityManager;

/**
 *
 * @author ihuegal
 */
public class StAConftipodoc extends StBase {
    
    public StAConftipodoc() {
        //NADA
    }
    
    public ArrayList<BdAConftipodoc> filtro(BdAConftipodoc filtroBdAConftipodoc, EntityManager em) throws Exception {
        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_CONFTIPODOC", filtroBdAConftipodoc.getIdConftipodoc());
        parametros.put("ID_TIPODOCUMENTO", filtroBdAConftipodoc.getIdTipodocumento());
        parametros.put("ID_AUTORIDAD", filtroBdAConftipodoc.getIdAutoridad());
        parametros.put("EN_ORDEN", filtroBdAConftipodoc.getEnOrden());
        parametros.put("DS_FIRMAPOSX", filtroBdAConftipodoc.getDsFirmaposx());
        parametros.put("DS_FIRMAPOSY", filtroBdAConftipodoc.getDsFirmaposy());
        parametros.put("FE_ALTA", filtroBdAConftipodoc.getFeAlta());
        parametros.put("FE_DESACTIVO", filtroBdAConftipodoc.getFeDesactivo());
        parametros.put("USUARIOBD", filtroBdAConftipodoc.getUsuariobd());
        parametros.put("TSTBD", filtroBdAConftipodoc.getTstbd());

        ArrayList<LinkedHashMap<String,Object>> lista = executeNativeQueryListParametros(filtroBdAConftipodoc.getSelectFiltro(), parametros, em);
        if (lista != null && !lista.isEmpty()) {
            return Mapeador.mapea(lista, BdAConftipodoc.class);
        }
        return null;
    }
    
    public BdAConftipodoc item(Integer idConftipodoc, EntityManager em) throws Exception {
        BdAConftipodoc filtroBdAConftipodoc = new BdAConftipodoc();
        filtroBdAConftipodoc.setIdConftipodoc(idConftipodoc);
        
        ArrayList<BdAConftipodoc> listaBdAConftipodoc = filtro(filtroBdAConftipodoc, em);
        if (listaBdAConftipodoc != null && !listaBdAConftipodoc.isEmpty()) {
            return listaBdAConftipodoc.get(0);
        }
        return null;
    }
}
