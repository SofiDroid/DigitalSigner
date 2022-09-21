package basedatos.servicios;

import basedatos.Mapeador;
import basedatos.StBase;
import excepciones.RequiredFieldException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import tomcat.persistence.EntityManager;
import utilidades.Session;
import utilidades.Validation;
import basedatos.tablas.BdTAutoridad;

/**
 *
 * @author ihuegal
 */
public class StTAutoridad extends StBase {
    
    public StTAutoridad() {
        //NADA
    }
    
    public ArrayList<BdTAutoridad> filtro(BdTAutoridad filtroBdTAutoridad, EntityManager em) throws Exception {
        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_AUTORIDAD", filtroBdTAutoridad.getIdAutoridad());
        parametros.put("CO_AUTORIDAD", filtroBdTAutoridad.getCoAutoridad());
        parametros.put("DS_AUTORIDAD", filtroBdTAutoridad.getDsAutoridad());
        parametros.put("ID_UNIDAD", filtroBdTAutoridad.getIdUnidad());
        parametros.put("FE_ALTA", filtroBdTAutoridad.getFeAlta());
        parametros.put("FE_DESACTIVO", filtroBdTAutoridad.getFeDesactivo());
        parametros.put("USUARIOBD", filtroBdTAutoridad.getUsuariobd());
        parametros.put("TSTBD", filtroBdTAutoridad.getTstbd());


        ArrayList<LinkedHashMap<String,Object>> lista = executeNativeQueryListParametros(filtroBdTAutoridad.getSelectFiltro(), parametros, em);
        if (lista != null && !lista.isEmpty()) {
            return Mapeador.mapea(lista, BdTAutoridad.class);
        }
        return null;
    }
    
    public BdTAutoridad item(Integer idAutoridad, EntityManager em) throws Exception {
        BdTAutoridad filtroBdTAutoridad = new BdTAutoridad();
        filtroBdTAutoridad.setIdAutoridad(idAutoridad);

        
        ArrayList<BdTAutoridad> listaBdTAutoridad = filtro(filtroBdTAutoridad, em);
        if (listaBdTAutoridad != null && !listaBdTAutoridad.isEmpty()) {
            return listaBdTAutoridad.get(0);
        }
        return null;
    }
    
    public int alta(BdTAutoridad newBdTAutoridad, EntityManager em) throws Exception {

        if (Validation.isNullOrEmpty(newBdTAutoridad.getIdAutoridad())) {
            throw new RequiredFieldException("ID_AUTORIDAD");
        }
        if (Validation.isNullOrEmpty(newBdTAutoridad.getCoAutoridad())) {
            throw new RequiredFieldException("CO_AUTORIDAD");
        }
        if (Validation.isNullOrEmpty(newBdTAutoridad.getDsAutoridad())) {
            throw new RequiredFieldException("DS_AUTORIDAD");
        }
        if (Validation.isNullOrEmpty(newBdTAutoridad.getFeAlta())) {
            throw new RequiredFieldException("FE_ALTA");
        }
        if (Validation.isNullOrEmpty(newBdTAutoridad.getUsuariobd())) {
            throw new RequiredFieldException("USUARIOBD");
        }
        if (Validation.isNullOrEmpty(newBdTAutoridad.getTstbd())) {
            throw new RequiredFieldException("TSTBD");
        }
   

        newBdTAutoridad.setUsuariobd(Session.getCoUsuario());

        newBdTAutoridad.setTstbd(new Date());

        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_AUTORIDAD", newBdTAutoridad.getIdAutoridad());
        parametros.put("CO_AUTORIDAD", newBdTAutoridad.getCoAutoridad());
        parametros.put("DS_AUTORIDAD", newBdTAutoridad.getDsAutoridad());
        parametros.put("ID_UNIDAD", newBdTAutoridad.getIdUnidad());
        parametros.put("FE_ALTA", newBdTAutoridad.getFeAlta());
        parametros.put("FE_DESACTIVO", newBdTAutoridad.getFeDesactivo());
        parametros.put("USUARIOBD", newBdTAutoridad.getUsuariobd());
        parametros.put("TSTBD", newBdTAutoridad.getTstbd());


        return executeNativeQueryParametros(newBdTAutoridad.getInsert(), parametros, em);
    }

    public int actualiza(BdTAutoridad upBdTAutoridad, EntityManager em) throws Exception {
        
        if (Validation.isNullOrEmpty(upBdTAutoridad.getIdAutoridad())) {
            throw new RequiredFieldException("ID_AUTORIDAD");
        }
        if (Validation.isNullOrEmpty(upBdTAutoridad.getCoAutoridad())) {
            throw new RequiredFieldException("CO_AUTORIDAD");
        }
        if (Validation.isNullOrEmpty(upBdTAutoridad.getDsAutoridad())) {
            throw new RequiredFieldException("DS_AUTORIDAD");
        }
        if (Validation.isNullOrEmpty(upBdTAutoridad.getFeAlta())) {
            throw new RequiredFieldException("FE_ALTA");
        }
        if (Validation.isNullOrEmpty(upBdTAutoridad.getUsuariobd())) {
            throw new RequiredFieldException("USUARIOBD");
        }
        if (Validation.isNullOrEmpty(upBdTAutoridad.getTstbd())) {
            throw new RequiredFieldException("TSTBD");
        }


        upBdTAutoridad.setUsuariobd(Session.getCoUsuario());

        upBdTAutoridad.setTstbd(new Date());

        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_AUTORIDAD", upBdTAutoridad.getIdAutoridad());
        parametros.put("CO_AUTORIDAD", upBdTAutoridad.getCoAutoridad());
        parametros.put("DS_AUTORIDAD", upBdTAutoridad.getDsAutoridad());
        parametros.put("ID_UNIDAD", upBdTAutoridad.getIdUnidad());
        parametros.put("FE_ALTA", upBdTAutoridad.getFeAlta());
        parametros.put("FE_DESACTIVO", upBdTAutoridad.getFeDesactivo());
        parametros.put("USUARIOBD", upBdTAutoridad.getUsuariobd());
        parametros.put("TSTBD", upBdTAutoridad.getTstbd());


        return executeNativeQueryParametros(upBdTAutoridad.getUpdate(), parametros, em);
    }

    public int baja(BdTAutoridad delBdTAutoridad, EntityManager em) throws  Exception {

        if (Validation.isNullOrEmpty(delBdTAutoridad.getIdAutoridad())) {
            throw new RequiredFieldException("ID_AUTORIDAD");
        }
        

        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_AUTORIDAD", delBdTAutoridad.getIdAutoridad());
        parametros.put("CO_AUTORIDAD", delBdTAutoridad.getCoAutoridad());
        parametros.put("DS_AUTORIDAD", delBdTAutoridad.getDsAutoridad());
        parametros.put("ID_UNIDAD", delBdTAutoridad.getIdUnidad());
        parametros.put("FE_ALTA", delBdTAutoridad.getFeAlta());
        parametros.put("FE_DESACTIVO", delBdTAutoridad.getFeDesactivo());
        parametros.put("USUARIOBD", delBdTAutoridad.getUsuariobd());
        parametros.put("TSTBD", delBdTAutoridad.getTstbd());


        return executeNativeQueryParametros(delBdTAutoridad.getDelete(), parametros, em);
    }
}

