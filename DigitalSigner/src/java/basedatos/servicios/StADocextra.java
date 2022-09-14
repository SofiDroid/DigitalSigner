package basedatos.servicios;

import basedatos.Mapeador;
import basedatos.StBase;
import basedatos.tablas.BdADocextra;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import tomcat.persistence.EntityManager;

/**
 *
 * @author ihuegal
 */
public class StADocextra extends StBase {
    
    public StADocextra() {
        //NADA
    }
    
    public ArrayList<BdADocextra> filtro(BdADocextra filtroBdADocextra, EntityManager em) throws Exception {
        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_DOCEXTRA", filtroBdADocextra.getIdDocextra());
        parametros.put("ID_DOCUMENTO", filtroBdADocextra.getIdDocumento());
        parametros.put("CO_FICHERO", filtroBdADocextra.getCoFichero());
        parametros.put("CO_EXTENSION", filtroBdADocextra.getCoExtension());
        parametros.put("BL_FICHERO", filtroBdADocextra.getBlFichero());
        parametros.put("DS_RUTA", filtroBdADocextra.getDsRuta());
        parametros.put("FE_ALTA", filtroBdADocextra.getFeAlta());
        parametros.put("FE_DESACTIVO", filtroBdADocextra.getFeDesactivo());
        parametros.put("USUARIOBD", filtroBdADocextra.getUsuariobd());
        parametros.put("TSTBD", filtroBdADocextra.getTstbd());

        ArrayList<LinkedHashMap<String,Object>> lista = executeNativeQueryListParametros(filtroBdADocextra.getSelectFiltro(), parametros, em);
        if (lista != null && !lista.isEmpty()) {
            return Mapeador.mapea(lista, BdADocextra.class);
        }
        return null;
    }
    
    public BdADocextra item(Integer idDocextra, EntityManager em) throws Exception {
        BdADocextra filtroBdADocextra = new BdADocextra();
        filtroBdADocextra.setIdDocextra(idDocextra);
        
        ArrayList<BdADocextra> listaBdADocextra = filtro(filtroBdADocextra, em);
        if (listaBdADocextra != null && !listaBdADocextra.isEmpty()) {
            return listaBdADocextra.get(0);
        }
        return null;
    }
}
