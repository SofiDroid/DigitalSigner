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
import basedatos.tablas.BdADocobs;

/**
 *
 * @author ihuegal
 */
public class StADocobs extends StBase {
    
    public StADocobs() {
        //NADA
    }
    
    public ArrayList<BdADocobs> filtro(BdADocobs filtroBdADocobs, EntityManager em) throws Exception {
        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_DOCOBS", filtroBdADocobs.getIdDocobs());
        parametros.put("DS_OBSERVACIONES", filtroBdADocobs.getDsObservaciones());
        parametros.put("ID_DOCUMENTO", filtroBdADocobs.getIdDocumento());
        parametros.put("ID_USUARIO", filtroBdADocobs.getIdUsuario());
        parametros.put("ID_AUTORIDAD", filtroBdADocobs.getIdAutoridad());
        parametros.put("FE_ALTA", filtroBdADocobs.getFeAlta());
        parametros.put("FE_DESACTIVO", filtroBdADocobs.getFeDesactivo());
        parametros.put("USUARIOBD", filtroBdADocobs.getUsuariobd());
        parametros.put("TSTBD", filtroBdADocobs.getTstbd());


        ArrayList<LinkedHashMap<String,Object>> lista = executeNativeQueryListParametros(filtroBdADocobs.getSelectFiltro(), parametros, em);
        if (lista != null && !lista.isEmpty()) {
            return Mapeador.mapea(lista, BdADocobs.class);
        }
        return null;
    }
    
    public BdADocobs item(Integer idDocobs, EntityManager em) throws Exception {
        BdADocobs filtroBdADocobs = new BdADocobs();
        filtroBdADocobs.setIdDocobs(idDocobs);

        
        ArrayList<BdADocobs> listaBdADocobs = filtro(filtroBdADocobs, em);
        if (listaBdADocobs != null && !listaBdADocobs.isEmpty()) {
            return listaBdADocobs.get(0);
        }
        return null;
    }
    
    public int alta(BdADocobs newBdADocobs, EntityManager em) throws Exception {

        if (Validation.isNullOrEmpty(newBdADocobs.getIdDocobs())) {
            throw new RequiredFieldException("ID_DOCOBS");
        }
        if (Validation.isNullOrEmpty(newBdADocobs.getIdDocumento())) {
            throw new RequiredFieldException("ID_DOCUMENTO");
        }
        if (Validation.isNullOrEmpty(newBdADocobs.getFeAlta())) {
            throw new RequiredFieldException("FE_ALTA");
        }
        if (Validation.isNullOrEmpty(newBdADocobs.getUsuariobd())) {
            throw new RequiredFieldException("USUARIOBD");
        }
        if (Validation.isNullOrEmpty(newBdADocobs.getTstbd())) {
            throw new RequiredFieldException("TSTBD");
        }
   

        newBdADocobs.setUsuariobd(Session.getCoUsuario());

        newBdADocobs.setTstbd(new Date());

        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_DOCOBS", newBdADocobs.getIdDocobs());
        parametros.put("DS_OBSERVACIONES", newBdADocobs.getDsObservaciones());
        parametros.put("ID_DOCUMENTO", newBdADocobs.getIdDocumento());
        parametros.put("ID_USUARIO", newBdADocobs.getIdUsuario());
        parametros.put("ID_AUTORIDAD", newBdADocobs.getIdAutoridad());
        parametros.put("FE_ALTA", newBdADocobs.getFeAlta());
        parametros.put("FE_DESACTIVO", newBdADocobs.getFeDesactivo());
        parametros.put("USUARIOBD", newBdADocobs.getUsuariobd());
        parametros.put("TSTBD", newBdADocobs.getTstbd());


        return executeNativeQueryParametros(newBdADocobs.getInsert(), parametros, em);
    }

    public int actualiza(BdADocobs upBdADocobs, EntityManager em) throws Exception {
        
        if (Validation.isNullOrEmpty(upBdADocobs.getIdDocobs())) {
            throw new RequiredFieldException("ID_DOCOBS");
        }
        if (Validation.isNullOrEmpty(upBdADocobs.getIdDocumento())) {
            throw new RequiredFieldException("ID_DOCUMENTO");
        }
        if (Validation.isNullOrEmpty(upBdADocobs.getFeAlta())) {
            throw new RequiredFieldException("FE_ALTA");
        }
        if (Validation.isNullOrEmpty(upBdADocobs.getUsuariobd())) {
            throw new RequiredFieldException("USUARIOBD");
        }
        if (Validation.isNullOrEmpty(upBdADocobs.getTstbd())) {
            throw new RequiredFieldException("TSTBD");
        }


        upBdADocobs.setUsuariobd(Session.getCoUsuario());

        upBdADocobs.setTstbd(new Date());

        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_DOCOBS", upBdADocobs.getIdDocobs());
        parametros.put("DS_OBSERVACIONES", upBdADocobs.getDsObservaciones());
        parametros.put("ID_DOCUMENTO", upBdADocobs.getIdDocumento());
        parametros.put("ID_USUARIO", upBdADocobs.getIdUsuario());
        parametros.put("ID_AUTORIDAD", upBdADocobs.getIdAutoridad());
        parametros.put("FE_ALTA", upBdADocobs.getFeAlta());
        parametros.put("FE_DESACTIVO", upBdADocobs.getFeDesactivo());
        parametros.put("USUARIOBD", upBdADocobs.getUsuariobd());
        parametros.put("TSTBD", upBdADocobs.getTstbd());


        return executeNativeQueryParametros(upBdADocobs.getUpdate(), parametros, em);
    }

    public int baja(BdADocobs delBdADocobs, EntityManager em) throws  Exception {

        if (Validation.isNullOrEmpty(delBdADocobs.getIdDocobs())) {
            throw new RequiredFieldException("ID_DOCOBS");
        }
        

        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_DOCOBS", delBdADocobs.getIdDocobs());
        parametros.put("DS_OBSERVACIONES", delBdADocobs.getDsObservaciones());
        parametros.put("ID_DOCUMENTO", delBdADocobs.getIdDocumento());
        parametros.put("ID_USUARIO", delBdADocobs.getIdUsuario());
        parametros.put("ID_AUTORIDAD", delBdADocobs.getIdAutoridad());
        parametros.put("FE_ALTA", delBdADocobs.getFeAlta());
        parametros.put("FE_DESACTIVO", delBdADocobs.getFeDesactivo());
        parametros.put("USUARIOBD", delBdADocobs.getUsuariobd());
        parametros.put("TSTBD", delBdADocobs.getTstbd());


        return executeNativeQueryParametros(delBdADocobs.getDelete(), parametros, em);
    }
}

