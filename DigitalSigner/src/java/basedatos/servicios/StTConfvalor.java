package basedatos.servicios;

import basedatos.Mapeador;
import basedatos.StBase;
import basedatos.tablas.BdTConfvalor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import tomcat.persistence.EntityManager;

/**
 *
 * @author ihuegal
 */
public class StTConfvalor extends StBase {
    
    public StTConfvalor() {
        //NADA
    }
    
    public ArrayList<BdTConfvalor> filtro(BdTConfvalor filtroBdTConfvalor, EntityManager em) throws Exception {
        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_CONFVALOR", filtroBdTConfvalor.getIdConfvalor());
        parametros.put("ID_CONFIGURACION", filtroBdTConfvalor.getIdConfiguracion());
        parametros.put("CO_CONFVALOR", filtroBdTConfvalor.getCoConfvalor());
        parametros.put("DS_CONFVALOR", filtroBdTConfvalor.getDsConfvalor());
        parametros.put("FE_ALTA", filtroBdTConfvalor.getFeAlta());
        parametros.put("FE_DESACTIVO", filtroBdTConfvalor.getFeDesactivo());
        parametros.put("USUARIOBD", filtroBdTConfvalor.getUsuariobd());
        parametros.put("TSTBD", filtroBdTConfvalor.getTstbd());

        ArrayList<LinkedHashMap<String,Object>> lista = executeNativeQueryListParametros(filtroBdTConfvalor.getSelectFiltro(), parametros, em);
        if (lista != null && !lista.isEmpty()) {
            return Mapeador.mapea(lista, BdTConfvalor.class);
        }
        return null;
    }
    
    public BdTConfvalor item(Integer idConfvalor, EntityManager em) throws Exception {
        BdTConfvalor filtroBdTConfvalor = new BdTConfvalor();
        filtroBdTConfvalor.setIdConfvalor(idConfvalor);
        
        ArrayList<BdTConfvalor> listaBdTConfvalor = filtro(filtroBdTConfvalor, em);
        if (listaBdTConfvalor != null && !listaBdTConfvalor.isEmpty()) {
            return listaBdTConfvalor.get(0);
        }
        return null;
    }
}
