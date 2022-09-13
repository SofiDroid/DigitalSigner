package basedatos.servicios;

import basedatos.Mapeador;
import basedatos.StBase;
import basedatos.tablas.BdTConfiguracion;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import tomcat.persistence.EntityManager;

/**
 *
 * @author ihuegal
 */
public class StTConfiguracion extends StBase {
    
    public StTConfiguracion() {
        //NADA
    }
    
    public ArrayList<BdTConfiguracion> filtro(BdTConfiguracion filtroBdTConfiguracion, EntityManager em) throws Exception {
        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_CONFIGURACION", filtroBdTConfiguracion.getIdConfiguracion());
        parametros.put("CO_CONFIGURACION", filtroBdTConfiguracion.getCoConfiguracion());
        parametros.put("DS_CONFIGURACION", filtroBdTConfiguracion.getDsConfiguracion());
        parametros.put("FE_ALTA", filtroBdTConfiguracion.getFeAlta());
        parametros.put("FE_DESACTIVO", filtroBdTConfiguracion.getFeDesactivo());
        parametros.put("USUARIOBD", filtroBdTConfiguracion.getUsuariobd());
        parametros.put("TSTBD", filtroBdTConfiguracion.getTstbd());

        ArrayList<LinkedHashMap<String,Object>> lista = executeNativeQueryListParametros(filtroBdTConfiguracion.getSelectFiltro(), parametros, em);
        if (lista != null && !lista.isEmpty()) {
            return Mapeador.mapea(lista, BdTConfiguracion.class);
        }
        return null;
    }
    
    public BdTConfiguracion item(Integer idConfiguracion, EntityManager em) throws Exception {
        BdTConfiguracion filtroBdTConfiguracion = new BdTConfiguracion();
        filtroBdTConfiguracion.setIdConfiguracion(idConfiguracion);
        
        ArrayList<BdTConfiguracion> listaBdTConfiguracion = filtro(filtroBdTConfiguracion, em);
        if (listaBdTConfiguracion != null && !listaBdTConfiguracion.isEmpty()) {
            return listaBdTConfiguracion.get(0);
        }
        return null;
    }
}
