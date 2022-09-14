package basedatos.servicios;

import basedatos.Mapeador;
import basedatos.StBase;
import basedatos.tablas.BdAHistdoc;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import tomcat.persistence.EntityManager;

/**
 *
 * @author ihuegal
 */
public class StAHistdoc extends StBase {
    
    public StAHistdoc() {
        //NADA
    }
    
    public ArrayList<BdAHistdoc> filtro(BdAHistdoc filtroBdAHistdoc, EntityManager em) throws Exception {
        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_HISTDOC", filtroBdAHistdoc.getIdHistdoc());
        parametros.put("ID_DOCUMENTO", filtroBdAHistdoc.getIdDocumento());
        parametros.put("ID_SITUACIONDOC", filtroBdAHistdoc.getIdSituaciondoc());
        parametros.put("BL_DOCUMENTO", filtroBdAHistdoc.getBlDocumento());
        parametros.put("DS_RUTA", filtroBdAHistdoc.getDsRuta());
        parametros.put("FE_ALTA", filtroBdAHistdoc.getFeAlta());
        parametros.put("FE_DESACTIVO", filtroBdAHistdoc.getFeDesactivo());
        parametros.put("USUARIOBD", filtroBdAHistdoc.getUsuariobd());
        parametros.put("TSTBD", filtroBdAHistdoc.getTstbd());

        ArrayList<LinkedHashMap<String,Object>> lista = executeNativeQueryListParametros(filtroBdAHistdoc.getSelectFiltro(), parametros, em);
        if (lista != null && !lista.isEmpty()) {
            return Mapeador.mapea(lista, BdAHistdoc.class);
        }
        return null;
    }
    
    public BdAHistdoc item(Integer idHistdoc, EntityManager em) throws Exception {
        BdAHistdoc filtroBdAHistdoc = new BdAHistdoc();
        filtroBdAHistdoc.setIdHistdoc(idHistdoc);
        
        ArrayList<BdAHistdoc> listaBdAHistdoc = filtro(filtroBdAHistdoc, em);
        if (listaBdAHistdoc != null && !listaBdAHistdoc.isEmpty()) {
            return listaBdAHistdoc.get(0);
        }
        return null;
    }
}
