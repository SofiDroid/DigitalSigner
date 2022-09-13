package basedatos.servicios;

import basedatos.Mapeador;
import basedatos.StBase;
import basedatos.tablas.BdTTipodocumento;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import tomcat.persistence.EntityManager;

/**
 *
 * @author ihuegal
 */
public class StTTipodocumento extends StBase {
    
    public StTTipodocumento() {
        //NADA
    }
    
    public ArrayList<BdTTipodocumento> filtro(BdTTipodocumento filtroBdTTipodocumento, EntityManager em) throws Exception {
        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_TIPODOCUMENTO", filtroBdTTipodocumento.getIdTipodocumento());
        parametros.put("CO_TIPODOCUMENTO", filtroBdTTipodocumento.getCoTipodocumento());
        parametros.put("DS_TIPODOCUMENTO", filtroBdTTipodocumento.getDsTipodocumento());
        parametros.put("FE_ALTA", filtroBdTTipodocumento.getFeAlta());
        parametros.put("FE_DESACTIVO", filtroBdTTipodocumento.getFeDesactivo());
        parametros.put("USUARIOBD", filtroBdTTipodocumento.getUsuariobd());
        parametros.put("TSTBD", filtroBdTTipodocumento.getTstbd());

        ArrayList<LinkedHashMap<String,Object>> lista = executeNativeQueryListParametros(filtroBdTTipodocumento.getSelectFiltro(), parametros, em);
        if (lista != null && !lista.isEmpty()) {
            return Mapeador.mapea(lista, BdTTipodocumento.class);
        }
        return null;
    }
    
    public BdTTipodocumento item(Integer idTipodocumento, EntityManager em) throws Exception {
        BdTTipodocumento filtroBdTTipodocumento = new BdTTipodocumento();
        filtroBdTTipodocumento.setIdTipodocumento(idTipodocumento);
        
        ArrayList<BdTTipodocumento> listaBdTTipodocumento = filtro(filtroBdTTipodocumento, em);
        if (listaBdTTipodocumento != null && !listaBdTTipodocumento.isEmpty()) {
            return listaBdTTipodocumento.get(0);
        }
        return null;
    }
}
