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
import basedatos.tablas.BdADocrechazo;

/**
 *
 * @author ihuegal
 */
public class StADocrechazo extends StBase {
    
    public StADocrechazo() {
        //NADA
    }
    
    public ArrayList<BdADocrechazo> filtro(BdADocrechazo filtroBdADocrechazo, EntityManager em) throws Exception {
        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_DOCRECHAZO", filtroBdADocrechazo.getIdDocrechazo());
        parametros.put("ID_DOCUMENTO", filtroBdADocrechazo.getIdDocumento());
        parametros.put("DS_OBSERVACIONES", filtroBdADocrechazo.getDsObservaciones());
        parametros.put("FE_ALTA", filtroBdADocrechazo.getFeAlta());
        parametros.put("FE_DESACTIVO", filtroBdADocrechazo.getFeDesactivo());
        parametros.put("USUARIOBD", filtroBdADocrechazo.getUsuariobd());
        parametros.put("TSTBD", filtroBdADocrechazo.getTstbd());


        ArrayList<LinkedHashMap<String,Object>> lista = executeNativeQueryListParametros(filtroBdADocrechazo.getSelectFiltro(), parametros, em);
        if (lista != null && !lista.isEmpty()) {
            return Mapeador.mapea(lista, BdADocrechazo.class);
        }
        return null;
    }
    
    public BdADocrechazo item(Integer idDocrechazo, EntityManager em) throws Exception {
        BdADocrechazo filtroBdADocrechazo = new BdADocrechazo();
        filtroBdADocrechazo.setIdDocrechazo(idDocrechazo);

        
        ArrayList<BdADocrechazo> listaBdADocrechazo = filtro(filtroBdADocrechazo, em);
        if (listaBdADocrechazo != null && !listaBdADocrechazo.isEmpty()) {
            return listaBdADocrechazo.get(0);
        }
        return null;
    }
    
    public int alta(BdADocrechazo newBdADocrechazo, EntityManager em) throws Exception {

        if(AppInit.TIPO_BASEDATOS == BaseDatos.ORACLE) {
            if (Validation.isNullOrEmpty(newBdADocrechazo.getIdDocrechazo())) {
                throw new RequiredFieldException("ID_DOCRECHAZO");
            }
        }
        if (Validation.isNullOrEmpty(newBdADocrechazo.getIdDocumento())) {
            throw new RequiredFieldException("ID_DOCUMENTO");
        }
        if (Validation.isNullOrEmpty(newBdADocrechazo.getDsObservaciones())) {
            throw new RequiredFieldException("DS_OBSERVACIONES");
        }
        if (Validation.isNullOrEmpty(newBdADocrechazo.getFeAlta())) {
            throw new RequiredFieldException("FE_ALTA");
        }
   

        newBdADocrechazo.setUsuariobd(Session.getCoUsuario());

        newBdADocrechazo.setTstbd(new Date());

        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_DOCRECHAZO", newBdADocrechazo.getIdDocrechazo());
        parametros.put("ID_DOCUMENTO", newBdADocrechazo.getIdDocumento());
        parametros.put("DS_OBSERVACIONES", newBdADocrechazo.getDsObservaciones());
        parametros.put("FE_ALTA", newBdADocrechazo.getFeAlta());
        parametros.put("FE_DESACTIVO", newBdADocrechazo.getFeDesactivo());
        parametros.put("USUARIOBD", newBdADocrechazo.getUsuariobd());
        parametros.put("TSTBD", newBdADocrechazo.getTstbd());


        return executeNativeQueryParametros(newBdADocrechazo.getInsert(), parametros, em);
    }

    public int actualiza(BdADocrechazo upBdADocrechazo, EntityManager em) throws Exception {
        
        if (Validation.isNullOrEmpty(upBdADocrechazo.getIdDocrechazo())) {
            throw new RequiredFieldException("ID_DOCRECHAZO");
        }
        if (Validation.isNullOrEmpty(upBdADocrechazo.getIdDocumento())) {
            throw new RequiredFieldException("ID_DOCUMENTO");
        }
        if (Validation.isNullOrEmpty(upBdADocrechazo.getDsObservaciones())) {
            throw new RequiredFieldException("DS_OBSERVACIONES");
        }
        if (Validation.isNullOrEmpty(upBdADocrechazo.getFeAlta())) {
            throw new RequiredFieldException("FE_ALTA");
        }


        upBdADocrechazo.setUsuariobd(Session.getCoUsuario());

        upBdADocrechazo.setTstbd(new Date());

        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_DOCRECHAZO", upBdADocrechazo.getIdDocrechazo());
        parametros.put("ID_DOCUMENTO", upBdADocrechazo.getIdDocumento());
        parametros.put("DS_OBSERVACIONES", upBdADocrechazo.getDsObservaciones());
        parametros.put("FE_ALTA", upBdADocrechazo.getFeAlta());
        parametros.put("FE_DESACTIVO", upBdADocrechazo.getFeDesactivo());
        parametros.put("USUARIOBD", upBdADocrechazo.getUsuariobd());
        parametros.put("TSTBD", upBdADocrechazo.getTstbd());


        return executeNativeQueryParametros(upBdADocrechazo.getUpdate(), parametros, em);
    }

    public int baja(BdADocrechazo delBdADocrechazo, EntityManager em) throws  Exception {

        if (Validation.isNullOrEmpty(delBdADocrechazo.getIdDocrechazo())) {
            throw new RequiredFieldException("ID_DOCRECHAZO");
        }
        

        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_DOCRECHAZO", delBdADocrechazo.getIdDocrechazo());


        return executeNativeQueryParametros(delBdADocrechazo.getDelete(), parametros, em);
    }
}

