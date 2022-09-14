package basedatos.servicios;

import basedatos.Mapeador;
import basedatos.StBase;
import basedatos.tablas.BdADocobs;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import tomcat.persistence.EntityManager;

/**
 *
 * @author ihuegal
 */
public class StADocobs extends StBase {
    
    public StADocobs() {
        //NADA
    }
    
    public ArrayList<BdADocobs> filtro(BdADocobs filtroBdADocobs, EntityManager em) throws Exception {
        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_DOCOBS", filtroBdADocobs.getIdDocobs());
        parametros.put("DS_OBSERVACIONES", filtroBdADocobs.getDsObservaciones());
        parametros.put("ID_DOCUMENTO", filtroBdADocobs.getIdDocumento());
        parametros.put("ID_USUARIO", filtroBdADocobs.getIdUsuario());
        parametros.put("ID_AUTORIDAD", filtroBdADocobs.getIdAutoridad());
        parametros.put("FE_ALTA", filtroBdADocobs.getFeAlta());
        parametros.put("FE_DESACTIVO", filtroBdADocobs.getFeDesactivo());
        parametros.put("USUARIOBD", filtroBdADocobs.getUsuariobd());
        parametros.put("TSTBD", filtroBdADocobs.getTstbd());

        ArrayList<LinkedHashMap<String,Object>> lista = executeNativeQueryListParametros(filtroBdADocobs.getSelectFiltro(), parametros, em);
        if (lista != null && !lista.isEmpty()) {
            return Mapeador.mapea(lista, BdADocobs.class);
        }
        return null;
    }
    
    public BdADocobs item(Integer idDocobs, EntityManager em) throws Exception {
        BdADocobs filtroBdADocobs = new BdADocobs();
        filtroBdADocobs.setIdDocobs(idDocobs);
        
        ArrayList<BdADocobs> listaBdADocobs = filtro(filtroBdADocobs, em);
        if (listaBdADocobs != null && !listaBdADocobs.isEmpty()) {
            return listaBdADocobs.get(0);
        }
        return null;
    }
}
