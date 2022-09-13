package basedatos.servicios;

import basedatos.Mapeador;
import basedatos.StBase;
import basedatos.tablas.BdTOpcionmenu;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import tomcat.persistence.EntityManager;

/**
 *
 * @author ihuegal
 */
public class StTOpcionmenu extends StBase {
    
    public StTOpcionmenu() {
        //NADA
    }
    
    public ArrayList<BdTOpcionmenu> filtro(BdTOpcionmenu filtroBdTOpcionmenu, EntityManager em) throws Exception {
        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_OPCIONMENU", filtroBdTOpcionmenu.getIdOpcionmenu());
        parametros.put("CO_OPCIONMENU", filtroBdTOpcionmenu.getCoOpcionmenu());
        parametros.put("DS_OPCIONMENU", filtroBdTOpcionmenu.getDsOpcionmenu());
        parametros.put("DS_TITULO", filtroBdTOpcionmenu.getDsTitulo());
        parametros.put("DS_TOOLTIP", filtroBdTOpcionmenu.getDsTooltip());
        parametros.put("DS_RUTA", filtroBdTOpcionmenu.getDsRuta());
        parametros.put("FE_ALTA", filtroBdTOpcionmenu.getFeAlta());
        parametros.put("FE_DESACTIVO", filtroBdTOpcionmenu.getFeDesactivo());
        parametros.put("USUARIOBD", filtroBdTOpcionmenu.getUsuariobd());
        parametros.put("TSTBD", filtroBdTOpcionmenu.getTstbd());

        ArrayList<LinkedHashMap<String,Object>> lista = executeNativeQueryListParametros(filtroBdTOpcionmenu.getSelectFiltro(), parametros, em);
        if (lista != null && !lista.isEmpty()) {
            return Mapeador.mapea(lista, BdTOpcionmenu.class);
        }
        return null;
    }
    
    public BdTOpcionmenu item(Integer idOpcionmenu, EntityManager em) throws Exception {
        BdTOpcionmenu filtroBdTOpcionmenu = new BdTOpcionmenu();
        filtroBdTOpcionmenu.setIdOpcionmenu(idOpcionmenu);
        
        ArrayList<BdTOpcionmenu> listaBdTOpcionmenu = filtro(filtroBdTOpcionmenu, em);
        if (listaBdTOpcionmenu != null && !listaBdTOpcionmenu.isEmpty()) {
            return listaBdTOpcionmenu.get(0);
        }
        return null;
    }
}
