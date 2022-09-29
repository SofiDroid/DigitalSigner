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
import basedatos.tablas.BdAUsutipousu;
import init.AppInit;
import utilidades.BaseDatos;

/**
 *
 * @author ihuegal
 */
public class StAUsutipousu extends StBase {
    
    public StAUsutipousu() {
        //NADA
    }
    
    public ArrayList<BdAUsutipousu> filtro(BdAUsutipousu filtroBdAUsutipousu, EntityManager em) throws Exception {
        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_USUARIO", filtroBdAUsutipousu.getIdUsuario());
        parametros.put("ID_TIPOUSUARIO", filtroBdAUsutipousu.getIdTipousuario());
        parametros.put("FE_ALTA", filtroBdAUsutipousu.getFeAlta());
        parametros.put("FE_DESACTIVO", filtroBdAUsutipousu.getFeDesactivo());
        parametros.put("USUARIOBD", filtroBdAUsutipousu.getUsuariobd());
        parametros.put("TSTBD", filtroBdAUsutipousu.getTstbd());


        ArrayList<LinkedHashMap<String,Object>> lista = executeNativeQueryListParametros(filtroBdAUsutipousu.getSelectFiltro(), parametros, em);
        if (lista != null && !lista.isEmpty()) {
            return Mapeador.mapea(lista, BdAUsutipousu.class);
        }
        return null;
    }
    
    public BdAUsutipousu item(Integer idUsuario, Integer idTipousuario, EntityManager em) throws Exception {
        BdAUsutipousu filtroBdAUsutipousu = new BdAUsutipousu();
        filtroBdAUsutipousu.setIdUsuario(idUsuario);
        filtroBdAUsutipousu.setIdTipousuario(idTipousuario);

        
        ArrayList<BdAUsutipousu> listaBdAUsutipousu = filtro(filtroBdAUsutipousu, em);
        if (listaBdAUsutipousu != null && !listaBdAUsutipousu.isEmpty()) {
            return listaBdAUsutipousu.get(0);
        }
        return null;
    }
    
    public int alta(BdAUsutipousu newBdAUsutipousu, EntityManager em) throws Exception {

        if(AppInit.TIPO_BASEDATOS == BaseDatos.ORACLE) {
            if (Validation.isNullOrEmpty(newBdAUsutipousu.getIdUsuario())) {
                throw new RequiredFieldException("ID_USUARIO");
            }
        }
        if(AppInit.TIPO_BASEDATOS == BaseDatos.ORACLE) {
            if (Validation.isNullOrEmpty(newBdAUsutipousu.getIdTipousuario())) {
                throw new RequiredFieldException("ID_TIPOUSUARIO");
            }
        }
        if (Validation.isNullOrEmpty(newBdAUsutipousu.getFeAlta())) {
            throw new RequiredFieldException("FE_ALTA");
        }
        if (Validation.isNullOrEmpty(newBdAUsutipousu.getUsuariobd())) {
            throw new RequiredFieldException("USUARIOBD");
        }
        if (Validation.isNullOrEmpty(newBdAUsutipousu.getTstbd())) {
            throw new RequiredFieldException("TSTBD");
        }
   

        newBdAUsutipousu.setUsuariobd(Session.getCoUsuario());

        newBdAUsutipousu.setTstbd(new Date());

        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_USUARIO", newBdAUsutipousu.getIdUsuario());
        parametros.put("ID_TIPOUSUARIO", newBdAUsutipousu.getIdTipousuario());
        parametros.put("FE_ALTA", newBdAUsutipousu.getFeAlta());
        parametros.put("FE_DESACTIVO", newBdAUsutipousu.getFeDesactivo());
        parametros.put("USUARIOBD", newBdAUsutipousu.getUsuariobd());
        parametros.put("TSTBD", newBdAUsutipousu.getTstbd());


        return executeNativeQueryParametros(newBdAUsutipousu.getInsert(), parametros, em);
    }

    public int actualiza(BdAUsutipousu upBdAUsutipousu, EntityManager em) throws Exception {
        
        if (Validation.isNullOrEmpty(upBdAUsutipousu.getIdUsuario())) {
            throw new RequiredFieldException("ID_USUARIO");
        }
        if (Validation.isNullOrEmpty(upBdAUsutipousu.getIdTipousuario())) {
            throw new RequiredFieldException("ID_TIPOUSUARIO");
        }
        if (Validation.isNullOrEmpty(upBdAUsutipousu.getFeAlta())) {
            throw new RequiredFieldException("FE_ALTA");
        }
        if (Validation.isNullOrEmpty(upBdAUsutipousu.getUsuariobd())) {
            throw new RequiredFieldException("USUARIOBD");
        }
        if (Validation.isNullOrEmpty(upBdAUsutipousu.getTstbd())) {
            throw new RequiredFieldException("TSTBD");
        }


        upBdAUsutipousu.setUsuariobd(Session.getCoUsuario());

        upBdAUsutipousu.setTstbd(new Date());

        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_USUARIO", upBdAUsutipousu.getIdUsuario());
        parametros.put("ID_TIPOUSUARIO", upBdAUsutipousu.getIdTipousuario());
        parametros.put("FE_ALTA", upBdAUsutipousu.getFeAlta());
        parametros.put("FE_DESACTIVO", upBdAUsutipousu.getFeDesactivo());
        parametros.put("USUARIOBD", upBdAUsutipousu.getUsuariobd());
        parametros.put("TSTBD", upBdAUsutipousu.getTstbd());


        return executeNativeQueryParametros(upBdAUsutipousu.getUpdate(), parametros, em);
    }

    public int baja(BdAUsutipousu delBdAUsutipousu, EntityManager em) throws  Exception {

        if (Validation.isNullOrEmpty(delBdAUsutipousu.getIdUsuario())) {
            throw new RequiredFieldException("ID_USUARIO");
        }
        if (Validation.isNullOrEmpty(delBdAUsutipousu.getIdTipousuario())) {
            throw new RequiredFieldException("ID_TIPOUSUARIO");
        }
        

        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_USUARIO", delBdAUsutipousu.getIdUsuario());
        parametros.put("ID_TIPOUSUARIO", delBdAUsutipousu.getIdTipousuario());
        parametros.put("FE_ALTA", delBdAUsutipousu.getFeAlta());
        parametros.put("FE_DESACTIVO", delBdAUsutipousu.getFeDesactivo());
        parametros.put("USUARIOBD", delBdAUsutipousu.getUsuariobd());
        parametros.put("TSTBD", delBdAUsutipousu.getTstbd());


        return executeNativeQueryParametros(delBdAUsutipousu.getDelete(), parametros, em);
    }
}

