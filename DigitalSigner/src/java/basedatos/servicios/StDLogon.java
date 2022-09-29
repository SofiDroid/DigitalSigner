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
import basedatos.tablas.BdDLogon;

/**
 *
 * @author ihuegal
 */
public class StDLogon extends StBase {
    
    public StDLogon() {
        //NADA
    }
    
    public ArrayList<BdDLogon> filtro(BdDLogon filtroBdDLogon, EntityManager em) throws Exception {
        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_LOGON", filtroBdDLogon.getIdLogon());
        parametros.put("ID_USUARIO", filtroBdDLogon.getIdUsuario());
        parametros.put("DS_IP", filtroBdDLogon.getDsIp());
        parametros.put("DS_TOKEN", filtroBdDLogon.getDsToken());
        parametros.put("BO_ERROR", filtroBdDLogon.getBoError());
        parametros.put("DS_LLAMADA", filtroBdDLogon.getDsLlamada());
        parametros.put("FE_ALTA", filtroBdDLogon.getFeAlta());
        parametros.put("FE_DESACTIVO", filtroBdDLogon.getFeDesactivo());
        parametros.put("USUARIOBD", filtroBdDLogon.getUsuariobd());
        parametros.put("TSTBD", filtroBdDLogon.getTstbd());


        ArrayList<LinkedHashMap<String,Object>> lista = executeNativeQueryListParametros(filtroBdDLogon.getSelectFiltro(), parametros, em);
        if (lista != null && !lista.isEmpty()) {
            return Mapeador.mapea(lista, BdDLogon.class);
        }
        return null;
    }
    
    public BdDLogon item(Integer idLogon, EntityManager em) throws Exception {
        BdDLogon filtroBdDLogon = new BdDLogon();
        filtroBdDLogon.setIdLogon(idLogon);

        
        ArrayList<BdDLogon> listaBdDLogon = filtro(filtroBdDLogon, em);
        if (listaBdDLogon != null && !listaBdDLogon.isEmpty()) {
            return listaBdDLogon.get(0);
        }
        return null;
    }
    
    public int alta(BdDLogon newBdDLogon, EntityManager em) throws Exception {

        if(AppInit.TIPO_BASEDATOS == BaseDatos.ORACLE) {
            if (Validation.isNullOrEmpty(newBdDLogon.getIdLogon())) {
                throw new RequiredFieldException("ID_LOGON");
            }
        }
        if (Validation.isNullOrEmpty(newBdDLogon.getBoError())) {
            throw new RequiredFieldException("BO_ERROR");
        }
        if (Validation.isNullOrEmpty(newBdDLogon.getFeAlta())) {
            throw new RequiredFieldException("FE_ALTA");
        }
   

        newBdDLogon.setUsuariobd(Session.getCoUsuario());

        newBdDLogon.setTstbd(new Date());

        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_LOGON", newBdDLogon.getIdLogon());
        parametros.put("ID_USUARIO", newBdDLogon.getIdUsuario());
        parametros.put("DS_IP", newBdDLogon.getDsIp());
        parametros.put("DS_TOKEN", newBdDLogon.getDsToken());
        parametros.put("BO_ERROR", newBdDLogon.getBoError());
        parametros.put("DS_LLAMADA", newBdDLogon.getDsLlamada());
        parametros.put("FE_ALTA", newBdDLogon.getFeAlta());
        parametros.put("FE_DESACTIVO", newBdDLogon.getFeDesactivo());
        parametros.put("USUARIOBD", newBdDLogon.getUsuariobd());
        parametros.put("TSTBD", newBdDLogon.getTstbd());


        return executeNativeQueryParametros(newBdDLogon.getInsert(), parametros, em);
    }

    public int actualiza(BdDLogon upBdDLogon, EntityManager em) throws Exception {
        
        if (Validation.isNullOrEmpty(upBdDLogon.getIdLogon())) {
            throw new RequiredFieldException("ID_LOGON");
        }
        if (Validation.isNullOrEmpty(upBdDLogon.getBoError())) {
            throw new RequiredFieldException("BO_ERROR");
        }
        if (Validation.isNullOrEmpty(upBdDLogon.getFeAlta())) {
            throw new RequiredFieldException("FE_ALTA");
        }


        upBdDLogon.setUsuariobd(Session.getCoUsuario());

        upBdDLogon.setTstbd(new Date());

        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_LOGON", upBdDLogon.getIdLogon());
        parametros.put("ID_USUARIO", upBdDLogon.getIdUsuario());
        parametros.put("DS_IP", upBdDLogon.getDsIp());
        parametros.put("DS_TOKEN", upBdDLogon.getDsToken());
        parametros.put("BO_ERROR", upBdDLogon.getBoError());
        parametros.put("DS_LLAMADA", upBdDLogon.getDsLlamada());
        parametros.put("FE_ALTA", upBdDLogon.getFeAlta());
        parametros.put("FE_DESACTIVO", upBdDLogon.getFeDesactivo());
        parametros.put("USUARIOBD", upBdDLogon.getUsuariobd());
        parametros.put("TSTBD", upBdDLogon.getTstbd());


        return executeNativeQueryParametros(upBdDLogon.getUpdate(), parametros, em);
    }

    public int baja(BdDLogon delBdDLogon, EntityManager em) throws  Exception {

        if (Validation.isNullOrEmpty(delBdDLogon.getIdLogon())) {
            throw new RequiredFieldException("ID_LOGON");
        }
        

        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_LOGON", delBdDLogon.getIdLogon());


        return executeNativeQueryParametros(delBdDLogon.getDelete(), parametros, em);
    }
}

