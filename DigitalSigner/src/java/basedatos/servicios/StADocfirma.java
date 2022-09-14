package basedatos.servicios;

import basedatos.Mapeador;
import basedatos.StBase;
import basedatos.tablas.BdADocfirma;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import tomcat.persistence.EntityManager;

/**
 *
 * @author ihuegal
 */
public class StADocfirma extends StBase {
    
    public StADocfirma() {
        //NADA
    }
    
    public ArrayList<BdADocfirma> filtro(BdADocfirma filtroBdADocfirma, EntityManager em) throws Exception {
        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_DOCFIRMA", filtroBdADocfirma.getIdDocfirma());
        parametros.put("ID_DOCUMENTO", filtroBdADocfirma.getIdDocumento());
        parametros.put("ID_AUTORIDAD", filtroBdADocfirma.getIdAutoridad());
        parametros.put("EN_ORDEN", filtroBdADocfirma.getEnOrden());
        parametros.put("DS_POSFIRMAX", filtroBdADocfirma.getDsFirmaposx());
        parametros.put("DS_POSFIRMAY", filtroBdADocfirma.getDsFirmaposy());
        parametros.put("FE_FIRMA", filtroBdADocfirma.getFeFirma());
        parametros.put("FE_ALTA", filtroBdADocfirma.getFeAlta());
        parametros.put("FE_DESACTIVO", filtroBdADocfirma.getFeDesactivo());
        parametros.put("USUARIOBD", filtroBdADocfirma.getUsuariobd());
        parametros.put("TSTBD", filtroBdADocfirma.getTstbd());

        ArrayList<LinkedHashMap<String,Object>> lista = executeNativeQueryListParametros(filtroBdADocfirma.getSelectFiltro(), parametros, em);
        if (lista != null && !lista.isEmpty()) {
            return Mapeador.mapea(lista, BdADocfirma.class);
        }
        return null;
    }
    
    public BdADocfirma item(Integer idDocfirma, EntityManager em) throws Exception {
        BdADocfirma filtroBdADocfirma = new BdADocfirma();
        filtroBdADocfirma.setIdDocfirma(idDocfirma);
        
        ArrayList<BdADocfirma> listaBdADocfirma = filtro(filtroBdADocfirma, em);
        if (listaBdADocfirma != null && !listaBdADocfirma.isEmpty()) {
            return listaBdADocfirma.get(0);
        }
        return null;
    }
}
