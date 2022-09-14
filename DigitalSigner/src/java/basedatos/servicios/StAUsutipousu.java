package basedatos.servicios;

import basedatos.Mapeador;
import basedatos.StBase;
import basedatos.tablas.BdAUsutipousu;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import tomcat.persistence.EntityManager;

/**
 *
 * @author ihuegal
 */
public class StAUsutipousu extends StBase {
    
    public StAUsutipousu() {
        //NADA
    }
    
    public ArrayList<BdAUsutipousu> filtro(BdAUsutipousu filtroBdAUsutipousu, EntityManager em) throws Exception {
        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_USUARIO", filtroBdAUsutipousu.getIdUsuario());
        parametros.put("ID_TIPOUSUARIO", filtroBdAUsutipousu.getIdTipousuario());
        parametros.put("FE_ALTA", filtroBdAUsutipousu.getFeAlta());
        parametros.put("FE_DESACTIVO", filtroBdAUsutipousu.getFeDesactivo());
        parametros.put("USUARIOBD", filtroBdAUsutipousu.getUsuariobd());
        parametros.put("TSTBD", filtroBdAUsutipousu.getTstbd());

        ArrayList<LinkedHashMap<String,Object>> lista = executeNativeQueryListParametros(filtroBdAUsutipousu.getSelectFiltro(), parametros, em);
        if (lista != null && !lista.isEmpty()) {
            return Mapeador.mapea(lista, BdAUsutipousu.class);
        }
        return null;
    }
    
    public BdAUsutipousu item(Integer idUsuario, Integer idTipousuario, EntityManager em) throws Exception {
        BdAUsutipousu filtroBdAUsutipousu = new BdAUsutipousu();
        filtroBdAUsutipousu.setIdUsuario(idUsuario);
        filtroBdAUsutipousu.setIdTipousuario(idTipousuario);
        
        ArrayList<BdAUsutipousu> listaBdAUsutipousu = filtro(filtroBdAUsutipousu, em);
        if (listaBdAUsutipousu != null && !listaBdAUsutipousu.isEmpty()) {
            return listaBdAUsutipousu.get(0);
        }
        return null;
    }
}
