package basedatos.servicios;

import basedatos.Mapeador;
import basedatos.StBase;
import basedatos.tablas.BdAHistentxml;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import tomcat.persistence.EntityManager;

/**
 *
 * @author ihuegal
 */
public class StAHistentxml extends StBase {
    
    public StAHistentxml() {
        //NADA
    }
    
    public ArrayList<BdAHistentxml> filtro(BdAHistentxml filtroBdAHistentxml, EntityManager em) throws Exception {
        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_HISTENTXML", filtroBdAHistentxml.getIdHistentxml());
        parametros.put("ID_ENTRADAXML", filtroBdAHistentxml.getIdEntradaxml());
        parametros.put("ID_SITUACIONXML", filtroBdAHistentxml.getIdSituacionxml());
        parametros.put("FE_ALTA", filtroBdAHistentxml.getFeAlta());
        parametros.put("FE_DESACTIVO", filtroBdAHistentxml.getFeDesactivo());
        parametros.put("USUARIOBD", filtroBdAHistentxml.getUsuariobd());
        parametros.put("TSTBD", filtroBdAHistentxml.getTstbd());

        ArrayList<LinkedHashMap<String,Object>> lista = executeNativeQueryListParametros(filtroBdAHistentxml.getSelectFiltro(), parametros, em);
        if (lista != null && !lista.isEmpty()) {
            return Mapeador.mapea(lista, BdAHistentxml.class);
        }
        return null;
    }
    
    public BdAHistentxml item(Integer idHistentxml, EntityManager em) throws Exception {
        BdAHistentxml filtroBdAHistentxml = new BdAHistentxml();
        filtroBdAHistentxml.setIdHistentxml(idHistentxml);
        
        ArrayList<BdAHistentxml> listaBdAHistentxml = filtro(filtroBdAHistentxml, em);
        if (listaBdAHistentxml != null && !listaBdAHistentxml.isEmpty()) {
            return listaBdAHistentxml.get(0);
        }
        return null;
    }
}
