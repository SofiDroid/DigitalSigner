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
import init.AppInit;
import utilidades.BaseDatos;
import basedatos.tablas.BdTSituacionxml;

/**
 *
 * @author ihuegal
 */
public class StTSituacionxml extends StBase {
    
    public StTSituacionxml() {
        //NADA
    }
    
    public ArrayList<BdTSituacionxml> filtro(BdTSituacionxml filtroBdTSituacionxml, EntityManager em) throws Exception {
        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_SITUACIONXML", filtroBdTSituacionxml.getIdSituacionxml());
        parametros.put("CO_SITUACIONXML", filtroBdTSituacionxml.getCoSituacionxml());
        parametros.put("DS_SITUACIONXML", filtroBdTSituacionxml.getDsSituacionxml());
        parametros.put("FE_ALTA", filtroBdTSituacionxml.getFeAlta());
        parametros.put("FE_DESACTIVO", filtroBdTSituacionxml.getFeDesactivo());
        parametros.put("USUARIOBD", filtroBdTSituacionxml.getUsuariobd());
        parametros.put("TSTBD", filtroBdTSituacionxml.getTstbd());


        ArrayList<LinkedHashMap<String,Object>> lista = executeNativeQueryListParametros(filtroBdTSituacionxml.getSelectFiltro(), parametros, em);
        if (lista != null && !lista.isEmpty()) {
            return Mapeador.mapea(lista, BdTSituacionxml.class);
        }
        return null;
    }
    
    public BdTSituacionxml item(Integer idSituacionxml, EntityManager em) throws Exception {
        BdTSituacionxml filtroBdTSituacionxml = new BdTSituacionxml();
        filtroBdTSituacionxml.setIdSituacionxml(idSituacionxml);

        
        ArrayList<BdTSituacionxml> listaBdTSituacionxml = filtro(filtroBdTSituacionxml, em);
        if (listaBdTSituacionxml != null && !listaBdTSituacionxml.isEmpty()) {
            return listaBdTSituacionxml.get(0);
        }
        return null;
    }
    
    public int alta(BdTSituacionxml newBdTSituacionxml, EntityManager em) throws Exception {

        if(AppInit.TIPO_BASEDATOS == BaseDatos.ORACLE) {
            if (Validation.isNullOrEmpty(newBdTSituacionxml.getIdSituacionxml())) {
                throw new RequiredFieldException("ID_SITUACIONXML");
            }
        }
        if (Validation.isNullOrEmpty(newBdTSituacionxml.getCoSituacionxml())) {
            throw new RequiredFieldException("CO_SITUACIONXML");
        }
        if (Validation.isNullOrEmpty(newBdTSituacionxml.getDsSituacionxml())) {
            throw new RequiredFieldException("DS_SITUACIONXML");
        }
        if (Validation.isNullOrEmpty(newBdTSituacionxml.getFeAlta())) {
            throw new RequiredFieldException("FE_ALTA");
        }
   

        newBdTSituacionxml.setUsuariobd(Session.getCoUsuario());

        newBdTSituacionxml.setTstbd(new Date());

        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_SITUACIONXML", newBdTSituacionxml.getIdSituacionxml());
        parametros.put("CO_SITUACIONXML", newBdTSituacionxml.getCoSituacionxml());
        parametros.put("DS_SITUACIONXML", newBdTSituacionxml.getDsSituacionxml());
        parametros.put("FE_ALTA", newBdTSituacionxml.getFeAlta());
        parametros.put("FE_DESACTIVO", newBdTSituacionxml.getFeDesactivo());
        parametros.put("USUARIOBD", newBdTSituacionxml.getUsuariobd());
        parametros.put("TSTBD", newBdTSituacionxml.getTstbd());


        return executeNativeQueryParametros(newBdTSituacionxml.getInsert(), parametros, em);
    }

    public int actualiza(BdTSituacionxml upBdTSituacionxml, EntityManager em) throws Exception {
        
        if (Validation.isNullOrEmpty(upBdTSituacionxml.getIdSituacionxml())) {
            throw new RequiredFieldException("ID_SITUACIONXML");
        }
        if (Validation.isNullOrEmpty(upBdTSituacionxml.getCoSituacionxml())) {
            throw new RequiredFieldException("CO_SITUACIONXML");
        }
        if (Validation.isNullOrEmpty(upBdTSituacionxml.getDsSituacionxml())) {
            throw new RequiredFieldException("DS_SITUACIONXML");
        }
        if (Validation.isNullOrEmpty(upBdTSituacionxml.getFeAlta())) {
            throw new RequiredFieldException("FE_ALTA");
        }


        upBdTSituacionxml.setUsuariobd(Session.getCoUsuario());

        upBdTSituacionxml.setTstbd(new Date());

        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_SITUACIONXML", upBdTSituacionxml.getIdSituacionxml());
        parametros.put("CO_SITUACIONXML", upBdTSituacionxml.getCoSituacionxml());
        parametros.put("DS_SITUACIONXML", upBdTSituacionxml.getDsSituacionxml());
        parametros.put("FE_ALTA", upBdTSituacionxml.getFeAlta());
        parametros.put("FE_DESACTIVO", upBdTSituacionxml.getFeDesactivo());
        parametros.put("USUARIOBD", upBdTSituacionxml.getUsuariobd());
        parametros.put("TSTBD", upBdTSituacionxml.getTstbd());


        return executeNativeQueryParametros(upBdTSituacionxml.getUpdate(), parametros, em);
    }

    public int baja(BdTSituacionxml delBdTSituacionxml, EntityManager em) throws  Exception {

        if (Validation.isNullOrEmpty(delBdTSituacionxml.getIdSituacionxml())) {
            throw new RequiredFieldException("ID_SITUACIONXML");
        }
        

        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_SITUACIONXML", delBdTSituacionxml.getIdSituacionxml());


        return executeNativeQueryParametros(delBdTSituacionxml.getDelete(), parametros, em);
    }
}

