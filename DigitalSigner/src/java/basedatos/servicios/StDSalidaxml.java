package basedatos.servicios;

import basedatos.Mapeador;
import basedatos.StBase;
import basedatos.tablas.BdDSalidaxml;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import tomcat.persistence.EntityManager;

/**
 *
 * @author ihuegal
 */
public class StDSalidaxml extends StBase {
    
    public StDSalidaxml() {
        //NADA
    }
    
    public ArrayList<BdDSalidaxml> filtro(BdDSalidaxml filtroBdDSalidaxml, EntityManager em) throws Exception {
        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_SALIDAXML", filtroBdDSalidaxml.getIdSalidaxml());
        parametros.put("BL_SALIDAXML", filtroBdDSalidaxml.getBlSalidaxml());
        parametros.put("ID_DOCUMENTO", filtroBdDSalidaxml.getIdDocumento());
        parametros.put("ID_SITUACIONXML", filtroBdDSalidaxml.getIdSituacionxml());
        parametros.put("DS_RUTA", filtroBdDSalidaxml.getDsRuta());
        parametros.put("FE_ALTA", filtroBdDSalidaxml.getFeAlta());
        parametros.put("FE_DESACTIVO", filtroBdDSalidaxml.getFeDesactivo());
        parametros.put("USUARIOBD", filtroBdDSalidaxml.getUsuariobd());
        parametros.put("TSTBD", filtroBdDSalidaxml.getTstbd());

        ArrayList<LinkedHashMap<String,Object>> lista = executeNativeQueryListParametros(filtroBdDSalidaxml.getSelectFiltro(), parametros, em);
        if (lista != null && !lista.isEmpty()) {
            return Mapeador.mapea(lista, BdDSalidaxml.class);
        }
        return null;
    }
    
    public BdDSalidaxml item(Integer idSalidaxml, EntityManager em) throws Exception {
        BdDSalidaxml filtroBdDSalidaxml = new BdDSalidaxml();
        filtroBdDSalidaxml.setIdSalidaxml(idSalidaxml);
        
        ArrayList<BdDSalidaxml> listaBdDSalidaxml = filtro(filtroBdDSalidaxml, em);
        if (listaBdDSalidaxml != null && !listaBdDSalidaxml.isEmpty()) {
            return listaBdDSalidaxml.get(0);
        }
        return null;
    }
}
