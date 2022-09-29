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
import basedatos.tablas.BdTTipousuario;
import init.AppInit;
import utilidades.BaseDatos;

/**
 *
 * @author ihuegal
 */
public class StTTipousuario extends StBase {
    
    public StTTipousuario() {
        //NADA
    }
    
    public ArrayList<BdTTipousuario> filtro(BdTTipousuario filtroBdTTipousuario, EntityManager em) throws Exception {
        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_TIPOUSUARIO", filtroBdTTipousuario.getIdTipousuario());
        parametros.put("CO_TIPOUSUARIO", filtroBdTTipousuario.getCoTipousuario());
        parametros.put("DS_TIPOUSUARIO", filtroBdTTipousuario.getDsTipousuario());
        parametros.put("ID_UNIDAD", filtroBdTTipousuario.getIdUnidad());
        parametros.put("FE_ALTA", filtroBdTTipousuario.getFeAlta());
        parametros.put("FE_DESACTIVO", filtroBdTTipousuario.getFeDesactivo());
        parametros.put("USUARIOBD", filtroBdTTipousuario.getUsuariobd());
        parametros.put("TSTBD", filtroBdTTipousuario.getTstbd());


        ArrayList<LinkedHashMap<String,Object>> lista = executeNativeQueryListParametros(filtroBdTTipousuario.getSelectFiltro(), parametros, em);
        if (lista != null && !lista.isEmpty()) {
            return Mapeador.mapea(lista, BdTTipousuario.class);
        }
        return null;
    }
    
    public BdTTipousuario item(Integer idTipousuario, EntityManager em) throws Exception {
        BdTTipousuario filtroBdTTipousuario = new BdTTipousuario();
        filtroBdTTipousuario.setIdTipousuario(idTipousuario);

        
        ArrayList<BdTTipousuario> listaBdTTipousuario = filtro(filtroBdTTipousuario, em);
        if (listaBdTTipousuario != null && !listaBdTTipousuario.isEmpty()) {
            return listaBdTTipousuario.get(0);
        }
        return null;
    }
    
    public int alta(BdTTipousuario newBdTTipousuario, EntityManager em) throws Exception {

        if(AppInit.TIPO_BASEDATOS == BaseDatos.ORACLE) {
            if (Validation.isNullOrEmpty(newBdTTipousuario.getIdTipousuario())) {
                throw new RequiredFieldException("ID_TIPOUSUARIO");
            }
        }
        if (Validation.isNullOrEmpty(newBdTTipousuario.getCoTipousuario())) {
            throw new RequiredFieldException("CO_TIPOUSUARIO");
        }
        if (Validation.isNullOrEmpty(newBdTTipousuario.getDsTipousuario())) {
            throw new RequiredFieldException("DS_TIPOUSUARIO");
        }
        if (Validation.isNullOrEmpty(newBdTTipousuario.getFeAlta())) {
            throw new RequiredFieldException("FE_ALTA");
        }
        if (Validation.isNullOrEmpty(newBdTTipousuario.getUsuariobd())) {
            throw new RequiredFieldException("USUARIOBD");
        }
        if (Validation.isNullOrEmpty(newBdTTipousuario.getTstbd())) {
            throw new RequiredFieldException("TSTBD");
        }
   

        newBdTTipousuario.setUsuariobd(Session.getCoUsuario());

        newBdTTipousuario.setTstbd(new Date());

        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_TIPOUSUARIO", newBdTTipousuario.getIdTipousuario());
        parametros.put("CO_TIPOUSUARIO", newBdTTipousuario.getCoTipousuario());
        parametros.put("DS_TIPOUSUARIO", newBdTTipousuario.getDsTipousuario());
        parametros.put("ID_UNIDAD", newBdTTipousuario.getIdUnidad());
        parametros.put("FE_ALTA", newBdTTipousuario.getFeAlta());
        parametros.put("FE_DESACTIVO", newBdTTipousuario.getFeDesactivo());
        parametros.put("USUARIOBD", newBdTTipousuario.getUsuariobd());
        parametros.put("TSTBD", newBdTTipousuario.getTstbd());


        return executeNativeQueryParametros(newBdTTipousuario.getInsert(), parametros, em);
    }

    public int actualiza(BdTTipousuario upBdTTipousuario, EntityManager em) throws Exception {
        
        if (Validation.isNullOrEmpty(upBdTTipousuario.getIdTipousuario())) {
            throw new RequiredFieldException("ID_TIPOUSUARIO");
        }
        if (Validation.isNullOrEmpty(upBdTTipousuario.getCoTipousuario())) {
            throw new RequiredFieldException("CO_TIPOUSUARIO");
        }
        if (Validation.isNullOrEmpty(upBdTTipousuario.getDsTipousuario())) {
            throw new RequiredFieldException("DS_TIPOUSUARIO");
        }
        if (Validation.isNullOrEmpty(upBdTTipousuario.getFeAlta())) {
            throw new RequiredFieldException("FE_ALTA");
        }
        if (Validation.isNullOrEmpty(upBdTTipousuario.getUsuariobd())) {
            throw new RequiredFieldException("USUARIOBD");
        }
        if (Validation.isNullOrEmpty(upBdTTipousuario.getTstbd())) {
            throw new RequiredFieldException("TSTBD");
        }


        upBdTTipousuario.setUsuariobd(Session.getCoUsuario());

        upBdTTipousuario.setTstbd(new Date());

        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_TIPOUSUARIO", upBdTTipousuario.getIdTipousuario());
        parametros.put("CO_TIPOUSUARIO", upBdTTipousuario.getCoTipousuario());
        parametros.put("DS_TIPOUSUARIO", upBdTTipousuario.getDsTipousuario());
        parametros.put("ID_UNIDAD", upBdTTipousuario.getIdUnidad());
        parametros.put("FE_ALTA", upBdTTipousuario.getFeAlta());
        parametros.put("FE_DESACTIVO", upBdTTipousuario.getFeDesactivo());
        parametros.put("USUARIOBD", upBdTTipousuario.getUsuariobd());
        parametros.put("TSTBD", upBdTTipousuario.getTstbd());


        return executeNativeQueryParametros(upBdTTipousuario.getUpdate(), parametros, em);
    }

    public int baja(BdTTipousuario delBdTTipousuario, EntityManager em) throws  Exception {

        if (Validation.isNullOrEmpty(delBdTTipousuario.getIdTipousuario())) {
            throw new RequiredFieldException("ID_TIPOUSUARIO");
        }
        

        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_TIPOUSUARIO", delBdTTipousuario.getIdTipousuario());
        parametros.put("CO_TIPOUSUARIO", delBdTTipousuario.getCoTipousuario());
        parametros.put("DS_TIPOUSUARIO", delBdTTipousuario.getDsTipousuario());
        parametros.put("ID_UNIDAD", delBdTTipousuario.getIdUnidad());
        parametros.put("FE_ALTA", delBdTTipousuario.getFeAlta());
        parametros.put("FE_DESACTIVO", delBdTTipousuario.getFeDesactivo());
        parametros.put("USUARIOBD", delBdTTipousuario.getUsuariobd());
        parametros.put("TSTBD", delBdTTipousuario.getTstbd());


        return executeNativeQueryParametros(delBdTTipousuario.getDelete(), parametros, em);
    }
}

