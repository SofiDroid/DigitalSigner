package basedatos.servicios;

import basedatos.Mapeador;
import basedatos.StBase;
import basedatos.tablas.BdAHistsalxml;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import tomcat.persistence.EntityManager;

/**
 *
 * @author ihuegal
 */
public class StAHistsalxml extends StBase {
    
    public StAHistsalxml() {
        //NADA
    }
    
    public ArrayList<BdAHistsalxml> filtro(BdAHistsalxml filtroBdAHistsalxml, EntityManager em) throws Exception {
        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_HISTSALXML", filtroBdAHistsalxml.getIdHistsalxml());
        parametros.put("ID_SALIDAXML", filtroBdAHistsalxml.getIdSalidaxml());
        parametros.put("ID_SITUACIONXML", filtroBdAHistsalxml.getIdSituacionxml());
        parametros.put("FE_ALTA", filtroBdAHistsalxml.getFeAlta());
        parametros.put("FE_DESACTIVO", filtroBdAHistsalxml.getFeDesactivo());
        parametros.put("USUARIOBD", filtroBdAHistsalxml.getUsuariobd());
        parametros.put("TSTBD", filtroBdAHistsalxml.getTstbd());

        ArrayList<LinkedHashMap<String,Object>> lista = executeNativeQueryListParametros(filtroBdAHistsalxml.getSelectFiltro(), parametros, em);
        if (lista != null && !lista.isEmpty()) {
            return Mapeador.mapea(lista, BdAHistsalxml.class);
        }
        return null;
    }
    
    public BdAHistsalxml item(Integer idHistsalxml, EntityManager em) throws Exception {
        BdAHistsalxml filtroBdAHistsalxml = new BdAHistsalxml();
        filtroBdAHistsalxml.setIdHistsalxml(idHistsalxml);
        
        ArrayList<BdAHistsalxml> listaBdAHistsalxml = filtro(filtroBdAHistsalxml, em);
        if (listaBdAHistsalxml != null && !listaBdAHistsalxml.isEmpty()) {
            return listaBdAHistsalxml.get(0);
        }
        return null;
    }
}
