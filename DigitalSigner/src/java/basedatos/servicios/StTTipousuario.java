package basedatos.servicios;

import basedatos.Mapeador;
import basedatos.StBase;
import basedatos.tablas.BdTTipousuario;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import tomcat.persistence.EntityManager;

/**
 *
 * @author ihuegal
 */
public class StTTipousuario extends StBase {
    
    public StTTipousuario() {
        //NADA
    }
    
    public ArrayList<BdTTipousuario> filtro(BdTTipousuario filtroBdTTipousuario, EntityManager em) throws Exception {
        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_TIPOUSUARIO", filtroBdTTipousuario.getIdTipousuario());
        parametros.put("CO_TIPOUSUARIO", filtroBdTTipousuario.getCoTipousuario());
        parametros.put("DS_TIPOUSUARIO", filtroBdTTipousuario.getDsTipousuario());
        parametros.put("ID_UNIDAD", filtroBdTTipousuario.getIdUnidad());
        parametros.put("FE_ALTA", filtroBdTTipousuario.getFeAlta());
        parametros.put("FE_DESACTIVO", filtroBdTTipousuario.getFeDesactivo());
        parametros.put("USUARIOBD", filtroBdTTipousuario.getUsuariobd());
        parametros.put("TSTBD", filtroBdTTipousuario.getTstbd());

        ArrayList<LinkedHashMap<String,Object>> lista = executeNativeQueryListParametros(filtroBdTTipousuario.getSelectFiltro(), parametros, em);
        if (lista != null && !lista.isEmpty()) {
            return Mapeador.mapea(lista, BdTTipousuario.class);
        }
        return null;
    }
    
    public BdTTipousuario item(Integer idTipousuario, EntityManager em) throws Exception {
        BdTTipousuario filtroBdTTipousuario = new BdTTipousuario();
        filtroBdTTipousuario.setIdTipousuario(idTipousuario);
        
        ArrayList<BdTTipousuario> listaBdTTipousuario = filtro(filtroBdTTipousuario, em);
        if (listaBdTTipousuario != null && !listaBdTTipousuario.isEmpty()) {
            return listaBdTTipousuario.get(0);
        }
        return null;
    }
}
