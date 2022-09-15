package basedatos.servicios;

import basedatos.Mapeador;
import basedatos.StBase;
import basedatos.tablas.BdTUnidad;
import excepciones.RequiredFieldException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import tomcat.persistence.EntityManager;
import utilidades.Session;
import utilidades.Validation;

/**
 *
 * @author ihuegal
 */
public class StTUnidad extends StBase {
    
    public StTUnidad() {
        //NADA
    }
    
    public ArrayList<BdTUnidad> filtro(BdTUnidad filtroBdTUnidad, EntityManager em) throws Exception {
        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_UNIDAD", filtroBdTUnidad.getIdUnidad());
        parametros.put("CO_UNIDAD", filtroBdTUnidad.getCoUnidad());
        parametros.put("DS_UNIDAD", filtroBdTUnidad.getDsUnidad());
        parametros.put("FE_ALTA", filtroBdTUnidad.getFeAlta());
        parametros.put("FE_DESACTIVO", filtroBdTUnidad.getFeDesactivo());
        parametros.put("USUARIOBD", filtroBdTUnidad.getUsuariobd());
        parametros.put("TSTBD", filtroBdTUnidad.getTstbd());

        ArrayList<LinkedHashMap<String,Object>> lista = executeNativeQueryListParametros(filtroBdTUnidad.getSelectFiltro(), parametros, em);
        if (lista != null && !lista.isEmpty()) {
            return Mapeador.mapea(lista, BdTUnidad.class);
        }
        return null;
    }
    
    public BdTUnidad item(Integer idUnidad, EntityManager em) throws Exception {
        BdTUnidad filtroBdTUnidad = new BdTUnidad();
        filtroBdTUnidad.setIdUnidad(idUnidad);
        
        ArrayList<BdTUnidad> listaBdTUnidad = filtro(filtroBdTUnidad, em);
        if (listaBdTUnidad != null && !listaBdTUnidad.isEmpty()) {
            return listaBdTUnidad.get(0);
        }
        return null;
    }
    
    public int alta(BdTUnidad newBdTUnidad, EntityManager em) throws Exception {

        if (Validation.isNullOrEmpty(newBdTUnidad.getCoUnidad())) {
            throw new RequiredFieldException("CO_UNIDAD");
        }        
        if (Validation.isNullOrEmpty(newBdTUnidad.getDsUnidad())) {
            throw new RequiredFieldException("DS_UNIDAD");
        }        
        if (Validation.isNullOrEmpty(newBdTUnidad.getFeAlta())) {
            throw new RequiredFieldException("FE_ALTA");
        }        

        newBdTUnidad.setUsuariobd(Session.getCoUsuario());

        newBdTUnidad.setTstbd(new Date());

        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_UNIDAD", newBdTUnidad.getIdUnidad());
        parametros.put("CO_UNIDAD", newBdTUnidad.getCoUnidad());
        parametros.put("DS_UNIDAD", newBdTUnidad.getDsUnidad());
        parametros.put("FE_ALTA", newBdTUnidad.getFeAlta());
        parametros.put("FE_DESACTIVO", newBdTUnidad.getFeDesactivo());
        parametros.put("USUARIOBD", newBdTUnidad.getUsuariobd());
        parametros.put("TSTBD", newBdTUnidad.getTstbd());

        return executeNativeQueryParametros(newBdTUnidad.getInsert(), parametros, em);
    }

    public int actualiza(BdTUnidad bdTUnidad, EntityManager em) throws Exception {
        
        if (Validation.isNullOrEmpty(bdTUnidad.getIdUnidad())) {
            throw new RequiredFieldException("ID_UNIDAD");
        }        
        if (Validation.isNullOrEmpty(bdTUnidad.getCoUnidad())) {
            throw new RequiredFieldException("CO_UNIDAD");
        }        
        if (Validation.isNullOrEmpty(bdTUnidad.getDsUnidad())) {
            throw new RequiredFieldException("DS_UNIDAD");
        }        
        if (Validation.isNullOrEmpty(bdTUnidad.getFeAlta())) {
            throw new RequiredFieldException("FE_ALTA");
        }

        bdTUnidad.setUsuariobd(Session.getCoUsuario());

        bdTUnidad.setTstbd(new Date());

        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_UNIDAD", bdTUnidad.getIdUnidad());
        parametros.put("CO_UNIDAD", bdTUnidad.getCoUnidad());
        parametros.put("DS_UNIDAD", bdTUnidad.getDsUnidad());
        parametros.put("FE_ALTA", bdTUnidad.getFeAlta());
        parametros.put("FE_DESACTIVO", bdTUnidad.getFeDesactivo());
        parametros.put("USUARIOBD", bdTUnidad.getUsuariobd());
        parametros.put("TSTBD", bdTUnidad.getTstbd());

        return executeNativeQueryParametros(bdTUnidad.getUpdate(), parametros, em);
    }

    public int baja(BdTUnidad bdTUnidad, EntityManager em) throws  Exception {
        
        HashMap<String, Object> parametros = new HashMap<>();

        
        parametros.put("ID_UNIDAD", bdTUnidad.getIdUnidad());

        return executeNativeQueryParametros(bdTUnidad.getDelete(), parametros, em);
    }
}
