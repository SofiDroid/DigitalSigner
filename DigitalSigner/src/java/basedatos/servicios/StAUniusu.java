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
import basedatos.tablas.BdAUniusu;
import init.AppInit;
import utilidades.BaseDatos;

/**
 *
 * @author ihuegal
 */
public class StAUniusu extends StBase {
    
    public StAUniusu() {
        //NADA
    }
    
    public ArrayList<BdAUniusu> filtro(BdAUniusu filtroBdAUniusu, EntityManager em) throws Exception {
        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_UNIDAD", filtroBdAUniusu.getIdUnidad());
        parametros.put("ID_USUARIO", filtroBdAUniusu.getIdUsuario());
        parametros.put("FE_ALTA", filtroBdAUniusu.getFeAlta());
        parametros.put("FE_DESACTIVO", filtroBdAUniusu.getFeDesactivo());
        parametros.put("USUARIOBD", filtroBdAUniusu.getUsuariobd());
        parametros.put("TSTBD", filtroBdAUniusu.getTstbd());


        ArrayList<LinkedHashMap<String,Object>> lista = executeNativeQueryListParametros(filtroBdAUniusu.getSelectFiltro(), parametros, em);
        if (lista != null && !lista.isEmpty()) {
            return Mapeador.mapea(lista, BdAUniusu.class);
        }
        return null;
    }
    
    public BdAUniusu item(Integer idUnidad, Integer idUsuario, EntityManager em) throws Exception {
        BdAUniusu filtroBdAUniusu = new BdAUniusu();
        filtroBdAUniusu.setIdUnidad(idUnidad);
        filtroBdAUniusu.setIdUsuario(idUsuario);

        
        ArrayList<BdAUniusu> listaBdAUniusu = filtro(filtroBdAUniusu, em);
        if (listaBdAUniusu != null && !listaBdAUniusu.isEmpty()) {
            return listaBdAUniusu.get(0);
        }
        return null;
    }
    
    public int alta(BdAUniusu newBdAUniusu, EntityManager em) throws Exception {

        if(AppInit.TIPO_BASEDATOS == BaseDatos.ORACLE) {
            if (Validation.isNullOrEmpty(newBdAUniusu.getIdUnidad())) {
                throw new RequiredFieldException("ID_UNIDAD");
            }
        }
        if(AppInit.TIPO_BASEDATOS == BaseDatos.ORACLE) {
            if (Validation.isNullOrEmpty(newBdAUniusu.getIdUsuario())) {
                throw new RequiredFieldException("ID_USUARIO");
            }
        }
        if (Validation.isNullOrEmpty(newBdAUniusu.getFeAlta())) {
            throw new RequiredFieldException("FE_ALTA");
        }
        if (Validation.isNullOrEmpty(newBdAUniusu.getUsuariobd())) {
            throw new RequiredFieldException("USUARIOBD");
        }
        if (Validation.isNullOrEmpty(newBdAUniusu.getTstbd())) {
            throw new RequiredFieldException("TSTBD");
        }
   

        newBdAUniusu.setUsuariobd(Session.getCoUsuario());

        newBdAUniusu.setTstbd(new Date());

        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_UNIDAD", newBdAUniusu.getIdUnidad());
        parametros.put("ID_USUARIO", newBdAUniusu.getIdUsuario());
        parametros.put("FE_ALTA", newBdAUniusu.getFeAlta());
        parametros.put("FE_DESACTIVO", newBdAUniusu.getFeDesactivo());
        parametros.put("USUARIOBD", newBdAUniusu.getUsuariobd());
        parametros.put("TSTBD", newBdAUniusu.getTstbd());


        return executeNativeQueryParametros(newBdAUniusu.getInsert(), parametros, em);
    }

    public int actualiza(BdAUniusu upBdAUniusu, EntityManager em) throws Exception {
        
        if (Validation.isNullOrEmpty(upBdAUniusu.getIdUnidad())) {
            throw new RequiredFieldException("ID_UNIDAD");
        }
        if (Validation.isNullOrEmpty(upBdAUniusu.getIdUsuario())) {
            throw new RequiredFieldException("ID_USUARIO");
        }
        if (Validation.isNullOrEmpty(upBdAUniusu.getFeAlta())) {
            throw new RequiredFieldException("FE_ALTA");
        }
        if (Validation.isNullOrEmpty(upBdAUniusu.getUsuariobd())) {
            throw new RequiredFieldException("USUARIOBD");
        }
        if (Validation.isNullOrEmpty(upBdAUniusu.getTstbd())) {
            throw new RequiredFieldException("TSTBD");
        }


        upBdAUniusu.setUsuariobd(Session.getCoUsuario());

        upBdAUniusu.setTstbd(new Date());

        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_UNIDAD", upBdAUniusu.getIdUnidad());
        parametros.put("ID_USUARIO", upBdAUniusu.getIdUsuario());
        parametros.put("FE_ALTA", upBdAUniusu.getFeAlta());
        parametros.put("FE_DESACTIVO", upBdAUniusu.getFeDesactivo());
        parametros.put("USUARIOBD", upBdAUniusu.getUsuariobd());
        parametros.put("TSTBD", upBdAUniusu.getTstbd());


        return executeNativeQueryParametros(upBdAUniusu.getUpdate(), parametros, em);
    }

    public int baja(BdAUniusu delBdAUniusu, EntityManager em) throws  Exception {

        if (Validation.isNullOrEmpty(delBdAUniusu.getIdUnidad())) {
            throw new RequiredFieldException("ID_UNIDAD");
        }
        if (Validation.isNullOrEmpty(delBdAUniusu.getIdUsuario())) {
            throw new RequiredFieldException("ID_USUARIO");
        }
        

        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_UNIDAD", delBdAUniusu.getIdUnidad());
        parametros.put("ID_USUARIO", delBdAUniusu.getIdUsuario());
        parametros.put("FE_ALTA", delBdAUniusu.getFeAlta());
        parametros.put("FE_DESACTIVO", delBdAUniusu.getFeDesactivo());
        parametros.put("USUARIOBD", delBdAUniusu.getUsuariobd());
        parametros.put("TSTBD", delBdAUniusu.getTstbd());


        return executeNativeQueryParametros(delBdAUniusu.getDelete(), parametros, em);
    }
}

