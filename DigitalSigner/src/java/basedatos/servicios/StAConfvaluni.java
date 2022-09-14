package basedatos.servicios;

import basedatos.Mapeador;
import basedatos.StBase;
import basedatos.tablas.BdAConfvaluni;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import tomcat.persistence.EntityManager;

/**
 *
 * @author ihuegal
 */
public class StAConfvaluni extends StBase {
    
    public StAConfvaluni() {
        //NADA
    }
    
    public ArrayList<BdAConfvaluni> filtro(BdAConfvaluni filtroBdAConfvaluni, EntityManager em) throws Exception {
        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_CONFVALUNI", filtroBdAConfvaluni.getIdConfvaluni());
        parametros.put("ID_UNIDAD", filtroBdAConfvaluni.getIdUnidad());
        parametros.put("ID_CONFIGURACION", filtroBdAConfvaluni.getIdConfiguracion());
        parametros.put("ID_CONFVALOR", filtroBdAConfvaluni.getIdConfvalor());
        parametros.put("DS_VALORLIBRE", filtroBdAConfvaluni.getDsValorlibre());
        parametros.put("FE_ALTA", filtroBdAConfvaluni.getFeAlta());
        parametros.put("FE_DESACTIVO", filtroBdAConfvaluni.getFeDesactivo());
        parametros.put("USUARIOBD", filtroBdAConfvaluni.getUsuariobd());
        parametros.put("TSTBD", filtroBdAConfvaluni.getTstbd());

        ArrayList<LinkedHashMap<String,Object>> lista = executeNativeQueryListParametros(filtroBdAConfvaluni.getSelectFiltro(), parametros, em);
        if (lista != null && !lista.isEmpty()) {
            return Mapeador.mapea(lista, BdAConfvaluni.class);
        }
        return null;
    }
    
    public BdAConfvaluni item(Integer idConfvaluni, EntityManager em) throws Exception {
        BdAConfvaluni filtroBdAConfvaluni = new BdAConfvaluni();
        filtroBdAConfvaluni.setIdConfvaluni(idConfvaluni);
        
        ArrayList<BdAConfvaluni> listaBdAConfvaluni = filtro(filtroBdAConfvaluni, em);
        if (listaBdAConfvaluni != null && !listaBdAConfvaluni.isEmpty()) {
            return listaBdAConfvaluni.get(0);
        }
        return null;
    }
}
