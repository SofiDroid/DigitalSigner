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
import basedatos.tablas.BdTTipodocumento;

/**
 *
 * @author ihuegal
 */
public class StTTipodocumento extends StBase {
    
    public StTTipodocumento() {
        //NADA
    }
    
    public ArrayList<BdTTipodocumento> filtro(BdTTipodocumento filtroBdTTipodocumento, EntityManager em) throws Exception {
        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_TIPODOCUMENTO", filtroBdTTipodocumento.getIdTipodocumento());
        parametros.put("CO_TIPODOCUMENTO", filtroBdTTipodocumento.getCoTipodocumento());
        parametros.put("DS_TIPODOCUMENTO", filtroBdTTipodocumento.getDsTipodocumento());
        parametros.put("FE_ALTA", filtroBdTTipodocumento.getFeAlta());
        parametros.put("FE_DESACTIVO", filtroBdTTipodocumento.getFeDesactivo());
        parametros.put("USUARIOBD", filtroBdTTipodocumento.getUsuariobd());
        parametros.put("TSTBD", filtroBdTTipodocumento.getTstbd());


        ArrayList<LinkedHashMap<String,Object>> lista = executeNativeQueryListParametros(filtroBdTTipodocumento.getSelectFiltro(), parametros, em);
        if (lista != null && !lista.isEmpty()) {
            return Mapeador.mapea(lista, BdTTipodocumento.class);
        }
        return null;
    }
    
    public BdTTipodocumento item(Integer idTipodocumento, EntityManager em) throws Exception {
        BdTTipodocumento filtroBdTTipodocumento = new BdTTipodocumento();
        filtroBdTTipodocumento.setIdTipodocumento(idTipodocumento);

        
        ArrayList<BdTTipodocumento> listaBdTTipodocumento = filtro(filtroBdTTipodocumento, em);
        if (listaBdTTipodocumento != null && !listaBdTTipodocumento.isEmpty()) {
            return listaBdTTipodocumento.get(0);
        }
        return null;
    }
    
    public int alta(BdTTipodocumento newBdTTipodocumento, EntityManager em) throws Exception {

        if (Validation.isNullOrEmpty(newBdTTipodocumento.getIdTipodocumento())) {
            throw new RequiredFieldException("ID_TIPODOCUMENTO");
        }
        if (Validation.isNullOrEmpty(newBdTTipodocumento.getCoTipodocumento())) {
            throw new RequiredFieldException("CO_TIPODOCUMENTO");
        }
        if (Validation.isNullOrEmpty(newBdTTipodocumento.getDsTipodocumento())) {
            throw new RequiredFieldException("DS_TIPODOCUMENTO");
        }
        if (Validation.isNullOrEmpty(newBdTTipodocumento.getFeAlta())) {
            throw new RequiredFieldException("FE_ALTA");
        }
        if (Validation.isNullOrEmpty(newBdTTipodocumento.getUsuariobd())) {
            throw new RequiredFieldException("USUARIOBD");
        }
        if (Validation.isNullOrEmpty(newBdTTipodocumento.getTstbd())) {
            throw new RequiredFieldException("TSTBD");
        }
   

        newBdTTipodocumento.setUsuariobd(Session.getCoUsuario());

        newBdTTipodocumento.setTstbd(new Date());

        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_TIPODOCUMENTO", newBdTTipodocumento.getIdTipodocumento());
        parametros.put("CO_TIPODOCUMENTO", newBdTTipodocumento.getCoTipodocumento());
        parametros.put("DS_TIPODOCUMENTO", newBdTTipodocumento.getDsTipodocumento());
        parametros.put("FE_ALTA", newBdTTipodocumento.getFeAlta());
        parametros.put("FE_DESACTIVO", newBdTTipodocumento.getFeDesactivo());
        parametros.put("USUARIOBD", newBdTTipodocumento.getUsuariobd());
        parametros.put("TSTBD", newBdTTipodocumento.getTstbd());


        return executeNativeQueryParametros(newBdTTipodocumento.getInsert(), parametros, em);
    }

    public int actualiza(BdTTipodocumento upBdTTipodocumento, EntityManager em) throws Exception {
        
        if (Validation.isNullOrEmpty(upBdTTipodocumento.getIdTipodocumento())) {
            throw new RequiredFieldException("ID_TIPODOCUMENTO");
        }
        if (Validation.isNullOrEmpty(upBdTTipodocumento.getCoTipodocumento())) {
            throw new RequiredFieldException("CO_TIPODOCUMENTO");
        }
        if (Validation.isNullOrEmpty(upBdTTipodocumento.getDsTipodocumento())) {
            throw new RequiredFieldException("DS_TIPODOCUMENTO");
        }
        if (Validation.isNullOrEmpty(upBdTTipodocumento.getFeAlta())) {
            throw new RequiredFieldException("FE_ALTA");
        }
        if (Validation.isNullOrEmpty(upBdTTipodocumento.getUsuariobd())) {
            throw new RequiredFieldException("USUARIOBD");
        }
        if (Validation.isNullOrEmpty(upBdTTipodocumento.getTstbd())) {
            throw new RequiredFieldException("TSTBD");
        }


        upBdTTipodocumento.setUsuariobd(Session.getCoUsuario());

        upBdTTipodocumento.setTstbd(new Date());

        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_TIPODOCUMENTO", upBdTTipodocumento.getIdTipodocumento());
        parametros.put("CO_TIPODOCUMENTO", upBdTTipodocumento.getCoTipodocumento());
        parametros.put("DS_TIPODOCUMENTO", upBdTTipodocumento.getDsTipodocumento());
        parametros.put("FE_ALTA", upBdTTipodocumento.getFeAlta());
        parametros.put("FE_DESACTIVO", upBdTTipodocumento.getFeDesactivo());
        parametros.put("USUARIOBD", upBdTTipodocumento.getUsuariobd());
        parametros.put("TSTBD", upBdTTipodocumento.getTstbd());


        return executeNativeQueryParametros(upBdTTipodocumento.getUpdate(), parametros, em);
    }

    public int baja(BdTTipodocumento delBdTTipodocumento, EntityManager em) throws  Exception {

        if (Validation.isNullOrEmpty(delBdTTipodocumento.getIdTipodocumento())) {
            throw new RequiredFieldException("ID_TIPODOCUMENTO");
        }
        

        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_TIPODOCUMENTO", delBdTTipodocumento.getIdTipodocumento());
        parametros.put("CO_TIPODOCUMENTO", delBdTTipodocumento.getCoTipodocumento());
        parametros.put("DS_TIPODOCUMENTO", delBdTTipodocumento.getDsTipodocumento());
        parametros.put("FE_ALTA", delBdTTipodocumento.getFeAlta());
        parametros.put("FE_DESACTIVO", delBdTTipodocumento.getFeDesactivo());
        parametros.put("USUARIOBD", delBdTTipodocumento.getUsuariobd());
        parametros.put("TSTBD", delBdTTipodocumento.getTstbd());


        return executeNativeQueryParametros(delBdTTipodocumento.getDelete(), parametros, em);
    }
}

