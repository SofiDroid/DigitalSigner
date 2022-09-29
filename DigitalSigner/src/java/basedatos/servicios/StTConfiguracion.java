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
import basedatos.tablas.BdTConfiguracion;
import init.AppInit;
import utilidades.BaseDatos;

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
    
    public int alta(BdTConfiguracion newBdTConfiguracion, EntityManager em) throws Exception {

        if(AppInit.TIPO_BASEDATOS == BaseDatos.ORACLE) {
            if (Validation.isNullOrEmpty(newBdTConfiguracion.getIdConfiguracion())) {
                throw new RequiredFieldException("ID_CONFIGURACION");
            }
        }
        if (Validation.isNullOrEmpty(newBdTConfiguracion.getCoConfiguracion())) {
            throw new RequiredFieldException("CO_CONFIGURACION");
        }
        if (Validation.isNullOrEmpty(newBdTConfiguracion.getDsConfiguracion())) {
            throw new RequiredFieldException("DS_CONFIGURACION");
        }
        if (Validation.isNullOrEmpty(newBdTConfiguracion.getFeAlta())) {
            throw new RequiredFieldException("FE_ALTA");
        }
        if (Validation.isNullOrEmpty(newBdTConfiguracion.getUsuariobd())) {
            throw new RequiredFieldException("USUARIOBD");
        }
        if (Validation.isNullOrEmpty(newBdTConfiguracion.getTstbd())) {
            throw new RequiredFieldException("TSTBD");
        }
   

        newBdTConfiguracion.setUsuariobd(Session.getCoUsuario());

        newBdTConfiguracion.setTstbd(new Date());

        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_CONFIGURACION", newBdTConfiguracion.getIdConfiguracion());
        parametros.put("CO_CONFIGURACION", newBdTConfiguracion.getCoConfiguracion());
        parametros.put("DS_CONFIGURACION", newBdTConfiguracion.getDsConfiguracion());
        parametros.put("FE_ALTA", newBdTConfiguracion.getFeAlta());
        parametros.put("FE_DESACTIVO", newBdTConfiguracion.getFeDesactivo());
        parametros.put("USUARIOBD", newBdTConfiguracion.getUsuariobd());
        parametros.put("TSTBD", newBdTConfiguracion.getTstbd());


        return executeNativeQueryParametros(newBdTConfiguracion.getInsert(), parametros, em);
    }

    public int actualiza(BdTConfiguracion upBdTConfiguracion, EntityManager em) throws Exception {
        
        if (Validation.isNullOrEmpty(upBdTConfiguracion.getIdConfiguracion())) {
            throw new RequiredFieldException("ID_CONFIGURACION");
        }
        if (Validation.isNullOrEmpty(upBdTConfiguracion.getCoConfiguracion())) {
            throw new RequiredFieldException("CO_CONFIGURACION");
        }
        if (Validation.isNullOrEmpty(upBdTConfiguracion.getDsConfiguracion())) {
            throw new RequiredFieldException("DS_CONFIGURACION");
        }
        if (Validation.isNullOrEmpty(upBdTConfiguracion.getFeAlta())) {
            throw new RequiredFieldException("FE_ALTA");
        }
        if (Validation.isNullOrEmpty(upBdTConfiguracion.getUsuariobd())) {
            throw new RequiredFieldException("USUARIOBD");
        }
        if (Validation.isNullOrEmpty(upBdTConfiguracion.getTstbd())) {
            throw new RequiredFieldException("TSTBD");
        }


        upBdTConfiguracion.setUsuariobd(Session.getCoUsuario());

        upBdTConfiguracion.setTstbd(new Date());

        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_CONFIGURACION", upBdTConfiguracion.getIdConfiguracion());
        parametros.put("CO_CONFIGURACION", upBdTConfiguracion.getCoConfiguracion());
        parametros.put("DS_CONFIGURACION", upBdTConfiguracion.getDsConfiguracion());
        parametros.put("FE_ALTA", upBdTConfiguracion.getFeAlta());
        parametros.put("FE_DESACTIVO", upBdTConfiguracion.getFeDesactivo());
        parametros.put("USUARIOBD", upBdTConfiguracion.getUsuariobd());
        parametros.put("TSTBD", upBdTConfiguracion.getTstbd());


        return executeNativeQueryParametros(upBdTConfiguracion.getUpdate(), parametros, em);
    }

    public int baja(BdTConfiguracion delBdTConfiguracion, EntityManager em) throws  Exception {

        if (Validation.isNullOrEmpty(delBdTConfiguracion.getIdConfiguracion())) {
            throw new RequiredFieldException("ID_CONFIGURACION");
        }
        

        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_CONFIGURACION", delBdTConfiguracion.getIdConfiguracion());
        parametros.put("CO_CONFIGURACION", delBdTConfiguracion.getCoConfiguracion());
        parametros.put("DS_CONFIGURACION", delBdTConfiguracion.getDsConfiguracion());
        parametros.put("FE_ALTA", delBdTConfiguracion.getFeAlta());
        parametros.put("FE_DESACTIVO", delBdTConfiguracion.getFeDesactivo());
        parametros.put("USUARIOBD", delBdTConfiguracion.getUsuariobd());
        parametros.put("TSTBD", delBdTConfiguracion.getTstbd());


        return executeNativeQueryParametros(delBdTConfiguracion.getDelete(), parametros, em);
    }
}

