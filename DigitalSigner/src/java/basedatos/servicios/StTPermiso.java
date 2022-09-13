package basedatos.servicios;

import basedatos.Mapeador;
import basedatos.StBase;
import basedatos.tablas.BdTPermiso;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import tomcat.persistence.EntityManager;

/**
 *
 * @author ihuegal
 */
public class StTPermiso extends StBase {
    
    public StTPermiso() {
        //NADA
    }
    
    public ArrayList<BdTPermiso> filtro(BdTPermiso filtroBdTPermiso, EntityManager em) throws Exception {
        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_PERMISO", filtroBdTPermiso.getIdPermiso());
        parametros.put("CO_PERMISO", filtroBdTPermiso.getCoPermiso());
        parametros.put("DS_PERMISO", filtroBdTPermiso.getDsPermiso());
        parametros.put("FE_ALTA", filtroBdTPermiso.getFeAlta());
        parametros.put("FE_DESACTIVO", filtroBdTPermiso.getFeDesactivo());
        parametros.put("USUARIOBD", filtroBdTPermiso.getUsuariobd());
        parametros.put("TSTBD", filtroBdTPermiso.getTstbd());

        ArrayList<LinkedHashMap<String,Object>> lista = executeNativeQueryListParametros(filtroBdTPermiso.getSelectFiltro(), parametros, em);
        if (lista != null && !lista.isEmpty()) {
            return Mapeador.mapea(lista, BdTPermiso.class);
        }
        return null;
    }
    
    public BdTPermiso item(Integer idPermiso, EntityManager em) throws Exception {
        BdTPermiso filtroBdTPermiso = new BdTPermiso();
        filtroBdTPermiso.setIdPermiso(idPermiso);
        
        ArrayList<BdTPermiso> listaBdTPermiso = filtro(filtroBdTPermiso, em);
        if (listaBdTPermiso != null && !listaBdTPermiso.isEmpty()) {
            return listaBdTPermiso.get(0);
        }
        return null;
    }
}
