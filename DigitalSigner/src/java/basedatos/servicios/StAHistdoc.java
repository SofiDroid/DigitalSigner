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
import basedatos.tablas.BdAHistdoc;
import init.AppInit;
import utilidades.BaseDatos;

/**
 *
 * @author ihuegal
 */
public class StAHistdoc extends StBase {
    
    public StAHistdoc() {
        //NADA
    }
    
    public ArrayList<BdAHistdoc> filtro(BdAHistdoc filtroBdAHistdoc, EntityManager em) throws Exception {
        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_HISTDOC", filtroBdAHistdoc.getIdHistdoc());
        parametros.put("ID_DOCUMENTO", filtroBdAHistdoc.getIdDocumento());
        parametros.put("ID_SITUACIONDOC", filtroBdAHistdoc.getIdSituaciondoc());
        parametros.put("BL_DOCUMENTO", filtroBdAHistdoc.getBlDocumento());
        parametros.put("DS_RUTA", filtroBdAHistdoc.getDsRuta());
        parametros.put("FE_ALTA", filtroBdAHistdoc.getFeAlta());
        parametros.put("FE_DESACTIVO", filtroBdAHistdoc.getFeDesactivo());
        parametros.put("USUARIOBD", filtroBdAHistdoc.getUsuariobd());
        parametros.put("TSTBD", filtroBdAHistdoc.getTstbd());


        ArrayList<LinkedHashMap<String,Object>> lista = executeNativeQueryListParametros(filtroBdAHistdoc.getSelectFiltro(), parametros, em);
        if (lista != null && !lista.isEmpty()) {
            return Mapeador.mapea(lista, BdAHistdoc.class);
        }
        return null;
    }
    
    public BdAHistdoc item(Integer idHistdoc, EntityManager em) throws Exception {
        BdAHistdoc filtroBdAHistdoc = new BdAHistdoc();
        filtroBdAHistdoc.setIdHistdoc(idHistdoc);

        
        ArrayList<BdAHistdoc> listaBdAHistdoc = filtro(filtroBdAHistdoc, em);
        if (listaBdAHistdoc != null && !listaBdAHistdoc.isEmpty()) {
            return listaBdAHistdoc.get(0);
        }
        return null;
    }
    
    public int alta(BdAHistdoc newBdAHistdoc, EntityManager em) throws Exception {

        if (AppInit.TIPO_BASEDATOS == BaseDatos.ORACLE) {
            if (Validation.isNullOrEmpty(newBdAHistdoc.getIdHistdoc())) {
                throw new RequiredFieldException("ID_HISTDOC");
            }
        }
        if (Validation.isNullOrEmpty(newBdAHistdoc.getIdDocumento())) {
            throw new RequiredFieldException("ID_DOCUMENTO");
        }
        if (Validation.isNullOrEmpty(newBdAHistdoc.getIdSituaciondoc())) {
            throw new RequiredFieldException("ID_SITUACIONDOC");
        }
        if (Validation.isNullOrEmpty(newBdAHistdoc.getFeAlta())) {
            throw new RequiredFieldException("FE_ALTA");
        }   

        newBdAHistdoc.setUsuariobd(Session.getCoUsuario());

        newBdAHistdoc.setTstbd(new Date());

        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_HISTDOC", newBdAHistdoc.getIdHistdoc());
        parametros.put("ID_DOCUMENTO", newBdAHistdoc.getIdDocumento());
        parametros.put("ID_SITUACIONDOC", newBdAHistdoc.getIdSituaciondoc());
        parametros.put("BL_DOCUMENTO", newBdAHistdoc.getBlDocumento());
        parametros.put("DS_RUTA", newBdAHistdoc.getDsRuta());
        parametros.put("FE_ALTA", newBdAHistdoc.getFeAlta());
        parametros.put("FE_DESACTIVO", newBdAHistdoc.getFeDesactivo());
        parametros.put("USUARIOBD", newBdAHistdoc.getUsuariobd());
        parametros.put("TSTBD", newBdAHistdoc.getTstbd());


        return executeNativeQueryParametros(newBdAHistdoc.getInsert(), parametros, em);
    }

    public int actualiza(BdAHistdoc upBdAHistdoc, EntityManager em) throws Exception {
        
        if (Validation.isNullOrEmpty(upBdAHistdoc.getIdHistdoc())) {
            throw new RequiredFieldException("ID_HISTDOC");
        }
        if (Validation.isNullOrEmpty(upBdAHistdoc.getIdDocumento())) {
            throw new RequiredFieldException("ID_DOCUMENTO");
        }
        if (Validation.isNullOrEmpty(upBdAHistdoc.getIdSituaciondoc())) {
            throw new RequiredFieldException("ID_SITUACIONDOC");
        }
        if (Validation.isNullOrEmpty(upBdAHistdoc.getFeAlta())) {
            throw new RequiredFieldException("FE_ALTA");
        }
        if (Validation.isNullOrEmpty(upBdAHistdoc.getUsuariobd())) {
            throw new RequiredFieldException("USUARIOBD");
        }
        if (Validation.isNullOrEmpty(upBdAHistdoc.getTstbd())) {
            throw new RequiredFieldException("TSTBD");
        }


        upBdAHistdoc.setUsuariobd(Session.getCoUsuario());

        upBdAHistdoc.setTstbd(new Date());

        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_HISTDOC", upBdAHistdoc.getIdHistdoc());
        parametros.put("ID_DOCUMENTO", upBdAHistdoc.getIdDocumento());
        parametros.put("ID_SITUACIONDOC", upBdAHistdoc.getIdSituaciondoc());
        parametros.put("BL_DOCUMENTO", upBdAHistdoc.getBlDocumento());
        parametros.put("DS_RUTA", upBdAHistdoc.getDsRuta());
        parametros.put("FE_ALTA", upBdAHistdoc.getFeAlta());
        parametros.put("FE_DESACTIVO", upBdAHistdoc.getFeDesactivo());
        parametros.put("USUARIOBD", upBdAHistdoc.getUsuariobd());
        parametros.put("TSTBD", upBdAHistdoc.getTstbd());


        return executeNativeQueryParametros(upBdAHistdoc.getUpdate(), parametros, em);
    }

    public int baja(BdAHistdoc delBdAHistdoc, EntityManager em) throws  Exception {

        if (Validation.isNullOrEmpty(delBdAHistdoc.getIdHistdoc())) {
            throw new RequiredFieldException("ID_HISTDOC");
        }
        

        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_HISTDOC", delBdAHistdoc.getIdHistdoc());
        parametros.put("ID_DOCUMENTO", delBdAHistdoc.getIdDocumento());
        parametros.put("ID_SITUACIONDOC", delBdAHistdoc.getIdSituaciondoc());
        parametros.put("BL_DOCUMENTO", delBdAHistdoc.getBlDocumento());
        parametros.put("DS_RUTA", delBdAHistdoc.getDsRuta());
        parametros.put("FE_ALTA", delBdAHistdoc.getFeAlta());
        parametros.put("FE_DESACTIVO", delBdAHistdoc.getFeDesactivo());
        parametros.put("USUARIOBD", delBdAHistdoc.getUsuariobd());
        parametros.put("TSTBD", delBdAHistdoc.getTstbd());


        return executeNativeQueryParametros(delBdAHistdoc.getDelete(), parametros, em);
    }
}

