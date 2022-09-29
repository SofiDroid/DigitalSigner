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
import basedatos.tablas.BdTSituaciondoc;

/**
 *
 * @author ihuegal
 */
public class StTSituaciondoc extends StBase {
    
    public StTSituaciondoc() {
        //NADA
    }
    
    public ArrayList<BdTSituaciondoc> filtro(BdTSituaciondoc filtroBdTSituaciondoc, EntityManager em) throws Exception {
        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_SITUACIONDOC", filtroBdTSituaciondoc.getIdSituaciondoc());
        parametros.put("CO_SITUACIONDOC", filtroBdTSituaciondoc.getCoSituaciondoc());
        parametros.put("DS_SITUACIONDOC", filtroBdTSituaciondoc.getDsSituaciondoc());
        parametros.put("FE_ALTA", filtroBdTSituaciondoc.getFeAlta());
        parametros.put("FE_DESACTIVO", filtroBdTSituaciondoc.getFeDesactivo());
        parametros.put("USUARIOBD", filtroBdTSituaciondoc.getUsuariobd());
        parametros.put("TSTBD", filtroBdTSituaciondoc.getTstbd());


        ArrayList<LinkedHashMap<String,Object>> lista = executeNativeQueryListParametros(filtroBdTSituaciondoc.getSelectFiltro(), parametros, em);
        if (lista != null && !lista.isEmpty()) {
            return Mapeador.mapea(lista, BdTSituaciondoc.class);
        }
        return null;
    }
    
    public BdTSituaciondoc item(Integer idSituaciondoc, EntityManager em) throws Exception {
        BdTSituaciondoc filtroBdTSituaciondoc = new BdTSituaciondoc();
        filtroBdTSituaciondoc.setIdSituaciondoc(idSituaciondoc);

        
        ArrayList<BdTSituaciondoc> listaBdTSituaciondoc = filtro(filtroBdTSituaciondoc, em);
        if (listaBdTSituaciondoc != null && !listaBdTSituaciondoc.isEmpty()) {
            return listaBdTSituaciondoc.get(0);
        }
        return null;
    }
    
    public int alta(BdTSituaciondoc newBdTSituaciondoc, EntityManager em) throws Exception {

        if(AppInit.TIPO_BASEDATOS == BaseDatos.ORACLE) {
            if (Validation.isNullOrEmpty(newBdTSituaciondoc.getIdSituaciondoc())) {
                throw new RequiredFieldException("ID_SITUACIONDOC");
            }
        }
        if (Validation.isNullOrEmpty(newBdTSituaciondoc.getCoSituaciondoc())) {
            throw new RequiredFieldException("CO_SITUACIONDOC");
        }
        if (Validation.isNullOrEmpty(newBdTSituaciondoc.getDsSituaciondoc())) {
            throw new RequiredFieldException("DS_SITUACIONDOC");
        }
        if (Validation.isNullOrEmpty(newBdTSituaciondoc.getFeAlta())) {
            throw new RequiredFieldException("FE_ALTA");
        }
   

        newBdTSituaciondoc.setUsuariobd(Session.getCoUsuario());

        newBdTSituaciondoc.setTstbd(new Date());

        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_SITUACIONDOC", newBdTSituaciondoc.getIdSituaciondoc());
        parametros.put("CO_SITUACIONDOC", newBdTSituaciondoc.getCoSituaciondoc());
        parametros.put("DS_SITUACIONDOC", newBdTSituaciondoc.getDsSituaciondoc());
        parametros.put("FE_ALTA", newBdTSituaciondoc.getFeAlta());
        parametros.put("FE_DESACTIVO", newBdTSituaciondoc.getFeDesactivo());
        parametros.put("USUARIOBD", newBdTSituaciondoc.getUsuariobd());
        parametros.put("TSTBD", newBdTSituaciondoc.getTstbd());


        return executeNativeQueryParametros(newBdTSituaciondoc.getInsert(), parametros, em);
    }

    public int actualiza(BdTSituaciondoc upBdTSituaciondoc, EntityManager em) throws Exception {
        
        if (Validation.isNullOrEmpty(upBdTSituaciondoc.getIdSituaciondoc())) {
            throw new RequiredFieldException("ID_SITUACIONDOC");
        }
        if (Validation.isNullOrEmpty(upBdTSituaciondoc.getCoSituaciondoc())) {
            throw new RequiredFieldException("CO_SITUACIONDOC");
        }
        if (Validation.isNullOrEmpty(upBdTSituaciondoc.getDsSituaciondoc())) {
            throw new RequiredFieldException("DS_SITUACIONDOC");
        }
        if (Validation.isNullOrEmpty(upBdTSituaciondoc.getFeAlta())) {
            throw new RequiredFieldException("FE_ALTA");
        }


        upBdTSituaciondoc.setUsuariobd(Session.getCoUsuario());

        upBdTSituaciondoc.setTstbd(new Date());

        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_SITUACIONDOC", upBdTSituaciondoc.getIdSituaciondoc());
        parametros.put("CO_SITUACIONDOC", upBdTSituaciondoc.getCoSituaciondoc());
        parametros.put("DS_SITUACIONDOC", upBdTSituaciondoc.getDsSituaciondoc());
        parametros.put("FE_ALTA", upBdTSituaciondoc.getFeAlta());
        parametros.put("FE_DESACTIVO", upBdTSituaciondoc.getFeDesactivo());
        parametros.put("USUARIOBD", upBdTSituaciondoc.getUsuariobd());
        parametros.put("TSTBD", upBdTSituaciondoc.getTstbd());


        return executeNativeQueryParametros(upBdTSituaciondoc.getUpdate(), parametros, em);
    }

    public int baja(BdTSituaciondoc delBdTSituaciondoc, EntityManager em) throws  Exception {

        if (Validation.isNullOrEmpty(delBdTSituaciondoc.getIdSituaciondoc())) {
            throw new RequiredFieldException("ID_SITUACIONDOC");
        }
        

        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_SITUACIONDOC", delBdTSituaciondoc.getIdSituaciondoc());


        return executeNativeQueryParametros(delBdTSituaciondoc.getDelete(), parametros, em);
    }
}

