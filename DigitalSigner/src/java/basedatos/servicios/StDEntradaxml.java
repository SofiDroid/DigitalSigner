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
import basedatos.tablas.BdDEntradaxml;

/**
 *
 * @author ihuegal
 */
public class StDEntradaxml extends StBase {
    
    public StDEntradaxml() {
        //NADA
    }
    
    public ArrayList<BdDEntradaxml> filtro(BdDEntradaxml filtroBdDEntradaxml, EntityManager em) throws Exception {
        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_ENTRADAXML", filtroBdDEntradaxml.getIdEntradaxml());
        parametros.put("BL_ENTRADAXML", filtroBdDEntradaxml.getBlEntradaxml());
        parametros.put("ID_DOCUMENTO", filtroBdDEntradaxml.getIdDocumento());
        parametros.put("ID_SITUACIONXML", filtroBdDEntradaxml.getIdSituacionxml());
        parametros.put("DS_RUTA", filtroBdDEntradaxml.getDsRuta());
        parametros.put("FE_ALTA", filtroBdDEntradaxml.getFeAlta());
        parametros.put("FE_DESACTIVO", filtroBdDEntradaxml.getFeDesactivo());
        parametros.put("USUARIOBD", filtroBdDEntradaxml.getUsuariobd());
        parametros.put("TSTBD", filtroBdDEntradaxml.getTstbd());


        ArrayList<LinkedHashMap<String,Object>> lista = executeNativeQueryListParametros(filtroBdDEntradaxml.getSelectFiltro(), parametros, em);
        if (lista != null && !lista.isEmpty()) {
            return Mapeador.mapea(lista, BdDEntradaxml.class);
        }
        return null;
    }
    
    public BdDEntradaxml item(Integer idEntradaxml, EntityManager em) throws Exception {
        BdDEntradaxml filtroBdDEntradaxml = new BdDEntradaxml();
        filtroBdDEntradaxml.setIdEntradaxml(idEntradaxml);

        
        ArrayList<BdDEntradaxml> listaBdDEntradaxml = filtro(filtroBdDEntradaxml, em);
        if (listaBdDEntradaxml != null && !listaBdDEntradaxml.isEmpty()) {
            return listaBdDEntradaxml.get(0);
        }
        return null;
    }
    
    public int alta(BdDEntradaxml newBdDEntradaxml, EntityManager em) throws Exception {

        if (Validation.isNullOrEmpty(newBdDEntradaxml.getIdEntradaxml())) {
            throw new RequiredFieldException("ID_ENTRADAXML");
        }
        if (Validation.isNullOrEmpty(newBdDEntradaxml.getBlEntradaxml())) {
            throw new RequiredFieldException("BL_ENTRADAXML");
        }
        if (Validation.isNullOrEmpty(newBdDEntradaxml.getIdDocumento())) {
            throw new RequiredFieldException("ID_DOCUMENTO");
        }
        if (Validation.isNullOrEmpty(newBdDEntradaxml.getIdSituacionxml())) {
            throw new RequiredFieldException("ID_SITUACIONXML");
        }
        if (Validation.isNullOrEmpty(newBdDEntradaxml.getFeAlta())) {
            throw new RequiredFieldException("FE_ALTA");
        }
        if (Validation.isNullOrEmpty(newBdDEntradaxml.getUsuariobd())) {
            throw new RequiredFieldException("USUARIOBD");
        }
        if (Validation.isNullOrEmpty(newBdDEntradaxml.getTstbd())) {
            throw new RequiredFieldException("TSTBD");
        }
   

        newBdDEntradaxml.setUsuariobd(Session.getCoUsuario());

        newBdDEntradaxml.setTstbd(new Date());

        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_ENTRADAXML", newBdDEntradaxml.getIdEntradaxml());
        parametros.put("BL_ENTRADAXML", newBdDEntradaxml.getBlEntradaxml());
        parametros.put("ID_DOCUMENTO", newBdDEntradaxml.getIdDocumento());
        parametros.put("ID_SITUACIONXML", newBdDEntradaxml.getIdSituacionxml());
        parametros.put("DS_RUTA", newBdDEntradaxml.getDsRuta());
        parametros.put("FE_ALTA", newBdDEntradaxml.getFeAlta());
        parametros.put("FE_DESACTIVO", newBdDEntradaxml.getFeDesactivo());
        parametros.put("USUARIOBD", newBdDEntradaxml.getUsuariobd());
        parametros.put("TSTBD", newBdDEntradaxml.getTstbd());


        return executeNativeQueryParametros(newBdDEntradaxml.getInsert(), parametros, em);
    }

    public int actualiza(BdDEntradaxml upBdDEntradaxml, EntityManager em) throws Exception {
        
        if (Validation.isNullOrEmpty(upBdDEntradaxml.getIdEntradaxml())) {
            throw new RequiredFieldException("ID_ENTRADAXML");
        }
        if (Validation.isNullOrEmpty(upBdDEntradaxml.getBlEntradaxml())) {
            throw new RequiredFieldException("BL_ENTRADAXML");
        }
        if (Validation.isNullOrEmpty(upBdDEntradaxml.getIdDocumento())) {
            throw new RequiredFieldException("ID_DOCUMENTO");
        }
        if (Validation.isNullOrEmpty(upBdDEntradaxml.getIdSituacionxml())) {
            throw new RequiredFieldException("ID_SITUACIONXML");
        }
        if (Validation.isNullOrEmpty(upBdDEntradaxml.getFeAlta())) {
            throw new RequiredFieldException("FE_ALTA");
        }
        if (Validation.isNullOrEmpty(upBdDEntradaxml.getUsuariobd())) {
            throw new RequiredFieldException("USUARIOBD");
        }
        if (Validation.isNullOrEmpty(upBdDEntradaxml.getTstbd())) {
            throw new RequiredFieldException("TSTBD");
        }


        upBdDEntradaxml.setUsuariobd(Session.getCoUsuario());

        upBdDEntradaxml.setTstbd(new Date());

        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_ENTRADAXML", upBdDEntradaxml.getIdEntradaxml());
        parametros.put("BL_ENTRADAXML", upBdDEntradaxml.getBlEntradaxml());
        parametros.put("ID_DOCUMENTO", upBdDEntradaxml.getIdDocumento());
        parametros.put("ID_SITUACIONXML", upBdDEntradaxml.getIdSituacionxml());
        parametros.put("DS_RUTA", upBdDEntradaxml.getDsRuta());
        parametros.put("FE_ALTA", upBdDEntradaxml.getFeAlta());
        parametros.put("FE_DESACTIVO", upBdDEntradaxml.getFeDesactivo());
        parametros.put("USUARIOBD", upBdDEntradaxml.getUsuariobd());
        parametros.put("TSTBD", upBdDEntradaxml.getTstbd());


        return executeNativeQueryParametros(upBdDEntradaxml.getUpdate(), parametros, em);
    }

    public int baja(BdDEntradaxml delBdDEntradaxml, EntityManager em) throws  Exception {

        if (Validation.isNullOrEmpty(delBdDEntradaxml.getIdEntradaxml())) {
            throw new RequiredFieldException("ID_ENTRADAXML");
        }
        

        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_ENTRADAXML", delBdDEntradaxml.getIdEntradaxml());
        parametros.put("BL_ENTRADAXML", delBdDEntradaxml.getBlEntradaxml());
        parametros.put("ID_DOCUMENTO", delBdDEntradaxml.getIdDocumento());
        parametros.put("ID_SITUACIONXML", delBdDEntradaxml.getIdSituacionxml());
        parametros.put("DS_RUTA", delBdDEntradaxml.getDsRuta());
        parametros.put("FE_ALTA", delBdDEntradaxml.getFeAlta());
        parametros.put("FE_DESACTIVO", delBdDEntradaxml.getFeDesactivo());
        parametros.put("USUARIOBD", delBdDEntradaxml.getUsuariobd());
        parametros.put("TSTBD", delBdDEntradaxml.getTstbd());


        return executeNativeQueryParametros(delBdDEntradaxml.getDelete(), parametros, em);
    }
}

