package basedatos.servicios;

import basedatos.Mapeador;
import basedatos.StBase;
import basedatos.tablas.BdATipousuopcper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import tomcat.persistence.EntityManager;

/**
 *
 * @author ihuegal
 */
public class StATipousuopcper extends StBase {
    
    public StATipousuopcper() {
        //NADA
    }
    
    public ArrayList<BdATipousuopcper> filtro(BdATipousuopcper filtroBdATipousuopcper, EntityManager em) throws Exception {
        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_TIPOUSUARIO", filtroBdATipousuopcper.getIdTipousuario());
        parametros.put("ID_OPCIONMENU", filtroBdATipousuopcper.getIdOpcionmenu());
        parametros.put("ID_PERMISO", filtroBdATipousuopcper.getIdPermiso());
        parametros.put("FE_ALTA", filtroBdATipousuopcper.getFeAlta());
        parametros.put("FE_DESACTIVO", filtroBdATipousuopcper.getFeDesactivo());
        parametros.put("USUARIOBD", filtroBdATipousuopcper.getUsuariobd());
        parametros.put("TSTBD", filtroBdATipousuopcper.getTstbd());

        ArrayList<LinkedHashMap<String,Object>> lista = executeNativeQueryListParametros(filtroBdATipousuopcper.getSelectFiltro(), parametros, em);
        if (lista != null && !lista.isEmpty()) {
            return Mapeador.mapea(lista, BdATipousuopcper.class);
        }
        return null;
    }
    
    public BdATipousuopcper item(Integer idTipousuario, Integer idOpcionmenu, EntityManager em) throws Exception {
        BdATipousuopcper filtroBdATipousuopcper = new BdATipousuopcper();
        filtroBdATipousuopcper.setIdTipousuario(idTipousuario);
        filtroBdATipousuopcper.setIdOpcionmenu(idOpcionmenu);
        
        ArrayList<BdATipousuopcper> listaBdATipousuopcper = filtro(filtroBdATipousuopcper, em);
        if (listaBdATipousuopcper != null && !listaBdATipousuopcper.isEmpty()) {
            return listaBdATipousuopcper.get(0);
        }
        return null;
    }
}
