package basedatos.servicios;

import basedatos.Mapeador;
import basedatos.StBase;
import basedatos.tablas.BdDEntradaxml;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import tomcat.persistence.EntityManager;

/**
 *
 * @author ihuegal
 */
public class StDEntradaxml extends StBase {
    
    public StDEntradaxml() {
        //NADA
    }
    
    public ArrayList<BdDEntradaxml> filtro(BdDEntradaxml filtroBdDEntradaxml, EntityManager em) throws Exception {
        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_ENTRADAXML", filtroBdDEntradaxml.getIdEntradaxml());
        parametros.put("BL_ENTRADAXML", filtroBdDEntradaxml.getBlEntradaxml());
        parametros.put("ID_DOCUMENTO", filtroBdDEntradaxml.getIdDocumento());
        parametros.put("ID_SITUACIONXML", filtroBdDEntradaxml.getIdSituacionxml());
        parametros.put("DS_RUTA", filtroBdDEntradaxml.getDsRuta());
        parametros.put("FE_ALTA", filtroBdDEntradaxml.getFeAlta());
        parametros.put("FE_DESACTIVO", filtroBdDEntradaxml.getFeDesactivo());
        parametros.put("USUARIOBD", filtroBdDEntradaxml.getUsuariobd());
        parametros.put("TSTBD", filtroBdDEntradaxml.getTstbd());

        ArrayList<LinkedHashMap<String,Object>> lista = executeNativeQueryListParametros(filtroBdDEntradaxml.getSelectFiltro(), parametros, em);
        if (lista != null && !lista.isEmpty()) {
            return Mapeador.mapea(lista, BdDEntradaxml.class);
        }
        return null;
    }
    
    public BdDEntradaxml item(Integer idEntradaxml, EntityManager em) throws Exception {
        BdDEntradaxml filtroBdDEntradaxml = new BdDEntradaxml();
        filtroBdDEntradaxml.setIdEntradaxml(idEntradaxml);
        
        ArrayList<BdDEntradaxml> listaBdDEntradaxml = filtro(filtroBdDEntradaxml, em);
        if (listaBdDEntradaxml != null && !listaBdDEntradaxml.isEmpty()) {
            return listaBdDEntradaxml.get(0);
        }
        return null;
    }
}
