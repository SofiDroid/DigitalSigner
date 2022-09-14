package basedatos.servicios;

import basedatos.Mapeador;
import basedatos.StBase;
import basedatos.tablas.BdATokenusuario;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import tomcat.persistence.EntityManager;

/**
 *
 * @author ihuegal
 */
public class StATokenusuario extends StBase {
    
    public StATokenusuario() {
        //NADA
    }
    
    public ArrayList<BdATokenusuario> filtro(BdATokenusuario filtroBdATokenusuario, EntityManager em) throws Exception {
        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_TOKENUSUARIO", filtroBdATokenusuario.getIdTokenusuario());
        parametros.put("ID_USUARIO", filtroBdATokenusuario.getIdUsuario());
        parametros.put("DS_TOKEN", filtroBdATokenusuario.getDsToken());
        parametros.put("FE_ALTA", filtroBdATokenusuario.getFeAlta());
        parametros.put("FE_DESACTIVO", filtroBdATokenusuario.getFeDesactivo());
        parametros.put("USUARIOBD", filtroBdATokenusuario.getUsuariobd());
        parametros.put("TSTBD", filtroBdATokenusuario.getTstbd());

        ArrayList<LinkedHashMap<String,Object>> lista = executeNativeQueryListParametros(filtroBdATokenusuario.getSelectFiltro(), parametros, em);
        if (lista != null && !lista.isEmpty()) {
            return Mapeador.mapea(lista, BdATokenusuario.class);
        }
        return null;
    }
    
    public BdATokenusuario item(Integer idTokenusuario, EntityManager em) throws Exception {
        BdATokenusuario filtroBdATokenusuario = new BdATokenusuario();
        filtroBdATokenusuario.setIdTokenusuario(idTokenusuario);
        
        ArrayList<BdATokenusuario> listaBdATokenusuario = filtro(filtroBdATokenusuario, em);
        if (listaBdATokenusuario != null && !listaBdATokenusuario.isEmpty()) {
            return listaBdATokenusuario.get(0);
        }
        return null;
    }
}
