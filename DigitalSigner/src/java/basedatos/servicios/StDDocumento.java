package basedatos.servicios;

import basedatos.Mapeador;
import basedatos.StBase;
import basedatos.tablas.BdDDocumento;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import tomcat.persistence.EntityManager;

/**
 *
 * @author ihuegal
 */
public class StDDocumento extends StBase {
    
    public StDDocumento() {
        //NADA
    }
    
    public ArrayList<BdDDocumento> filtro(BdDDocumento filtroBdDDocumento, EntityManager em) throws Exception {
        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_DOCUMENTO", filtroBdDDocumento.getIdDocumento());
        parametros.put("CO_DOCUMENTO", filtroBdDDocumento.getCoDocumento());
        parametros.put("DS_DOCUMENTO", filtroBdDDocumento.getDsDocumento());
        parametros.put("ID_TIPODOCUMENTO", filtroBdDDocumento.getIdTipodocumento());
        parametros.put("CO_FICHERO", filtroBdDDocumento.getCoFichero());
        parametros.put("CO_EXTENSION", filtroBdDDocumento.getCoExtension());
        parametros.put("ID_SITUACIONDOC", filtroBdDDocumento.getIdSituaciondoc());
        parametros.put("DS_RUTA", filtroBdDDocumento.getDsRuta());
        parametros.put("FE_ALTA", filtroBdDDocumento.getFeAlta());
        parametros.put("FE_DESACTIVO", filtroBdDDocumento.getFeDesactivo());
        parametros.put("USUARIOBD", filtroBdDDocumento.getUsuariobd());
        parametros.put("TSTBD", filtroBdDDocumento.getTstbd());

        ArrayList<LinkedHashMap<String,Object>> lista = executeNativeQueryListParametros(filtroBdDDocumento.getSelectFiltro(), parametros, em);
        if (lista != null && !lista.isEmpty()) {
            return Mapeador.mapea(lista, BdDDocumento.class);
        }
        return null;
    }
    
    public BdDDocumento item(Integer idDocumento, EntityManager em) throws Exception {
        BdDDocumento filtroBdDDocumento = new BdDDocumento();
        filtroBdDDocumento.setIdDocumento(idDocumento);
        
        ArrayList<BdDDocumento> listaBdDDocumento = filtro(filtroBdDDocumento, em);
        if (listaBdDDocumento != null && !listaBdDDocumento.isEmpty()) {
            return listaBdDDocumento.get(0);
        }
        return null;
    }
}
