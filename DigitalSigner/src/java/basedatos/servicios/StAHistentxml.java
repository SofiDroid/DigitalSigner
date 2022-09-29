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
import basedatos.tablas.BdAHistentxml;
import init.AppInit;
import utilidades.BaseDatos;

/**
 *
 * @author ihuegal
 */
public class StAHistentxml extends StBase {
    
    public StAHistentxml() {
        //NADA
    }
    
    public ArrayList<BdAHistentxml> filtro(BdAHistentxml filtroBdAHistentxml, EntityManager em) throws Exception {
        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_HISTENTXML", filtroBdAHistentxml.getIdHistentxml());
        parametros.put("ID_ENTRADAXML", filtroBdAHistentxml.getIdEntradaxml());
        parametros.put("ID_SITUACIONXML", filtroBdAHistentxml.getIdSituacionxml());
        parametros.put("FE_ALTA", filtroBdAHistentxml.getFeAlta());
        parametros.put("FE_DESACTIVO", filtroBdAHistentxml.getFeDesactivo());
        parametros.put("USUARIOBD", filtroBdAHistentxml.getUsuariobd());
        parametros.put("TSTBD", filtroBdAHistentxml.getTstbd());


        ArrayList<LinkedHashMap<String,Object>> lista = executeNativeQueryListParametros(filtroBdAHistentxml.getSelectFiltro(), parametros, em);
        if (lista != null && !lista.isEmpty()) {
            return Mapeador.mapea(lista, BdAHistentxml.class);
        }
        return null;
    }
    
    public BdAHistentxml item(Integer idHistentxml, EntityManager em) throws Exception {
        BdAHistentxml filtroBdAHistentxml = new BdAHistentxml();
        filtroBdAHistentxml.setIdHistentxml(idHistentxml);

        
        ArrayList<BdAHistentxml> listaBdAHistentxml = filtro(filtroBdAHistentxml, em);
        if (listaBdAHistentxml != null && !listaBdAHistentxml.isEmpty()) {
            return listaBdAHistentxml.get(0);
        }
        return null;
    }
    
    public int alta(BdAHistentxml newBdAHistentxml, EntityManager em) throws Exception {

        if(AppInit.TIPO_BASEDATOS == BaseDatos.ORACLE) {
            if (Validation.isNullOrEmpty(newBdAHistentxml.getIdHistentxml())) {
                throw new RequiredFieldException("ID_HISTENTXML");
            }
        }
        if (Validation.isNullOrEmpty(newBdAHistentxml.getIdEntradaxml())) {
            throw new RequiredFieldException("ID_ENTRADAXML");
        }
        if (Validation.isNullOrEmpty(newBdAHistentxml.getIdSituacionxml())) {
            throw new RequiredFieldException("ID_SITUACIONXML");
        }
        if (Validation.isNullOrEmpty(newBdAHistentxml.getFeAlta())) {
            throw new RequiredFieldException("FE_ALTA");
        }
        if (Validation.isNullOrEmpty(newBdAHistentxml.getUsuariobd())) {
            throw new RequiredFieldException("USUARIOBD");
        }
        if (Validation.isNullOrEmpty(newBdAHistentxml.getTstbd())) {
            throw new RequiredFieldException("TSTBD");
        }
   

        newBdAHistentxml.setUsuariobd(Session.getCoUsuario());

        newBdAHistentxml.setTstbd(new Date());

        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_HISTENTXML", newBdAHistentxml.getIdHistentxml());
        parametros.put("ID_ENTRADAXML", newBdAHistentxml.getIdEntradaxml());
        parametros.put("ID_SITUACIONXML", newBdAHistentxml.getIdSituacionxml());
        parametros.put("FE_ALTA", newBdAHistentxml.getFeAlta());
        parametros.put("FE_DESACTIVO", newBdAHistentxml.getFeDesactivo());
        parametros.put("USUARIOBD", newBdAHistentxml.getUsuariobd());
        parametros.put("TSTBD", newBdAHistentxml.getTstbd());


        return executeNativeQueryParametros(newBdAHistentxml.getInsert(), parametros, em);
    }

    public int actualiza(BdAHistentxml upBdAHistentxml, EntityManager em) throws Exception {
        
        if (Validation.isNullOrEmpty(upBdAHistentxml.getIdHistentxml())) {
            throw new RequiredFieldException("ID_HISTENTXML");
        }
        if (Validation.isNullOrEmpty(upBdAHistentxml.getIdEntradaxml())) {
            throw new RequiredFieldException("ID_ENTRADAXML");
        }
        if (Validation.isNullOrEmpty(upBdAHistentxml.getIdSituacionxml())) {
            throw new RequiredFieldException("ID_SITUACIONXML");
        }
        if (Validation.isNullOrEmpty(upBdAHistentxml.getFeAlta())) {
            throw new RequiredFieldException("FE_ALTA");
        }
        if (Validation.isNullOrEmpty(upBdAHistentxml.getUsuariobd())) {
            throw new RequiredFieldException("USUARIOBD");
        }
        if (Validation.isNullOrEmpty(upBdAHistentxml.getTstbd())) {
            throw new RequiredFieldException("TSTBD");
        }


        upBdAHistentxml.setUsuariobd(Session.getCoUsuario());

        upBdAHistentxml.setTstbd(new Date());

        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_HISTENTXML", upBdAHistentxml.getIdHistentxml());
        parametros.put("ID_ENTRADAXML", upBdAHistentxml.getIdEntradaxml());
        parametros.put("ID_SITUACIONXML", upBdAHistentxml.getIdSituacionxml());
        parametros.put("FE_ALTA", upBdAHistentxml.getFeAlta());
        parametros.put("FE_DESACTIVO", upBdAHistentxml.getFeDesactivo());
        parametros.put("USUARIOBD", upBdAHistentxml.getUsuariobd());
        parametros.put("TSTBD", upBdAHistentxml.getTstbd());


        return executeNativeQueryParametros(upBdAHistentxml.getUpdate(), parametros, em);
    }

    public int baja(BdAHistentxml delBdAHistentxml, EntityManager em) throws  Exception {

        if (Validation.isNullOrEmpty(delBdAHistentxml.getIdHistentxml())) {
            throw new RequiredFieldException("ID_HISTENTXML");
        }
        

        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_HISTENTXML", delBdAHistentxml.getIdHistentxml());
        parametros.put("ID_ENTRADAXML", delBdAHistentxml.getIdEntradaxml());
        parametros.put("ID_SITUACIONXML", delBdAHistentxml.getIdSituacionxml());
        parametros.put("FE_ALTA", delBdAHistentxml.getFeAlta());
        parametros.put("FE_DESACTIVO", delBdAHistentxml.getFeDesactivo());
        parametros.put("USUARIOBD", delBdAHistentxml.getUsuariobd());
        parametros.put("TSTBD", delBdAHistentxml.getTstbd());


        return executeNativeQueryParametros(delBdAHistentxml.getDelete(), parametros, em);
    }
}

