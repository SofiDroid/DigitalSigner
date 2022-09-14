package basedatos.servicios;

import basedatos.Mapeador;
import basedatos.StBase;
import basedatos.tablas.BdDLogon;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import tomcat.persistence.EntityManager;

/**
 *
 * @author ihuegal
 */
public class StDLogon extends StBase {
    
    public StDLogon() {
        //NADA
    }
    
    public ArrayList<BdDLogon> filtro(BdDLogon filtroBdDLogon, EntityManager em) throws Exception {
        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_LOGON", filtroBdDLogon.getIdLogon());
        parametros.put("ID_USUARIO", filtroBdDLogon.getIdUsuario());
        parametros.put("DS_IP", filtroBdDLogon.getDsIp());
        parametros.put("DS_TOKEN", filtroBdDLogon.getDsToken());
        parametros.put("BO_ERROR", filtroBdDLogon.getBoError());
        parametros.put("DS_LLAMADA", filtroBdDLogon.getDsLlamada());
        parametros.put("FE_ALTA", filtroBdDLogon.getFeAlta());
        parametros.put("FE_DESACTIVO", filtroBdDLogon.getFeDesactivo());
        parametros.put("USUARIOBD", filtroBdDLogon.getUsuariobd());
        parametros.put("TSTBD", filtroBdDLogon.getTstbd());

        ArrayList<LinkedHashMap<String,Object>> lista = executeNativeQueryListParametros(filtroBdDLogon.getSelectFiltro(), parametros, em);
        if (lista != null && !lista.isEmpty()) {
            return Mapeador.mapea(lista, BdDLogon.class);
        }
        return null;
    }
    
    public BdDLogon item(Integer idLogon, EntityManager em) throws Exception {
        BdDLogon filtroBdDLogon = new BdDLogon();
        filtroBdDLogon.setIdLogon(idLogon);
        
        ArrayList<BdDLogon> listaBdDLogon = filtro(filtroBdDLogon, em);
        if (listaBdDLogon != null && !listaBdDLogon.isEmpty()) {
            return listaBdDLogon.get(0);
        }
        return null;
    }
}
