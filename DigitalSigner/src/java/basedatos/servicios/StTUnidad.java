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
import basedatos.tablas.BdTUnidad;
import init.AppInit;
import utilidades.BaseDatos;

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
        parametros.put("ID_UNIDADPADRE", filtroBdTUnidad.getIdUnidadpadre());


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

        if(AppInit.TIPO_BASEDATOS == BaseDatos.ORACLE) {
            if (Validation.isNullOrEmpty(newBdTUnidad.getIdUnidad())) {
                throw new RequiredFieldException("ID_UNIDAD");
            }
        }
        if (Validation.isNullOrEmpty(newBdTUnidad.getCoUnidad())) {
            throw new RequiredFieldException("CO_UNIDAD");
        }
        if (Validation.isNullOrEmpty(newBdTUnidad.getDsUnidad())) {
            throw new RequiredFieldException("DS_UNIDAD");
        }
        if (Validation.isNullOrEmpty(newBdTUnidad.getFeAlta())) {
            throw new RequiredFieldException("FE_ALTA");
        }
        if (Validation.isNullOrEmpty(newBdTUnidad.getUsuariobd())) {
            throw new RequiredFieldException("USUARIOBD");
        }
        if (Validation.isNullOrEmpty(newBdTUnidad.getTstbd())) {
            throw new RequiredFieldException("TSTBD");
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
        parametros.put("ID_UNIDADPADRE", newBdTUnidad.getIdUnidadpadre());


        return executeNativeQueryParametros(newBdTUnidad.getInsert(), parametros, em);
    }

    public int actualiza(BdTUnidad upBdTUnidad, EntityManager em) throws Exception {
        
        if (Validation.isNullOrEmpty(upBdTUnidad.getIdUnidad())) {
            throw new RequiredFieldException("ID_UNIDAD");
        }
        if (Validation.isNullOrEmpty(upBdTUnidad.getCoUnidad())) {
            throw new RequiredFieldException("CO_UNIDAD");
        }
        if (Validation.isNullOrEmpty(upBdTUnidad.getDsUnidad())) {
            throw new RequiredFieldException("DS_UNIDAD");
        }
        if (Validation.isNullOrEmpty(upBdTUnidad.getFeAlta())) {
            throw new RequiredFieldException("FE_ALTA");
        }
        if (Validation.isNullOrEmpty(upBdTUnidad.getUsuariobd())) {
            throw new RequiredFieldException("USUARIOBD");
        }
        if (Validation.isNullOrEmpty(upBdTUnidad.getTstbd())) {
            throw new RequiredFieldException("TSTBD");
        }


        upBdTUnidad.setUsuariobd(Session.getCoUsuario());

        upBdTUnidad.setTstbd(new Date());

        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_UNIDAD", upBdTUnidad.getIdUnidad());
        parametros.put("CO_UNIDAD", upBdTUnidad.getCoUnidad());
        parametros.put("DS_UNIDAD", upBdTUnidad.getDsUnidad());
        parametros.put("FE_ALTA", upBdTUnidad.getFeAlta());
        parametros.put("FE_DESACTIVO", upBdTUnidad.getFeDesactivo());
        parametros.put("USUARIOBD", upBdTUnidad.getUsuariobd());
        parametros.put("TSTBD", upBdTUnidad.getTstbd());
        parametros.put("ID_UNIDADPADRE", upBdTUnidad.getIdUnidadpadre());


        return executeNativeQueryParametros(upBdTUnidad.getUpdate(), parametros, em);
    }

    public int baja(BdTUnidad delBdTUnidad, EntityManager em) throws  Exception {

        if (Validation.isNullOrEmpty(delBdTUnidad.getIdUnidad())) {
            throw new RequiredFieldException("ID_UNIDAD");
        }
        

        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_UNIDAD", delBdTUnidad.getIdUnidad());
        parametros.put("CO_UNIDAD", delBdTUnidad.getCoUnidad());
        parametros.put("DS_UNIDAD", delBdTUnidad.getDsUnidad());
        parametros.put("FE_ALTA", delBdTUnidad.getFeAlta());
        parametros.put("FE_DESACTIVO", delBdTUnidad.getFeDesactivo());
        parametros.put("USUARIOBD", delBdTUnidad.getUsuariobd());
        parametros.put("TSTBD", delBdTUnidad.getTstbd());
        parametros.put("ID_UNIDADPADRE", delBdTUnidad.getIdUnidadpadre());


        return executeNativeQueryParametros(delBdTUnidad.getDelete(), parametros, em);
    }
}

