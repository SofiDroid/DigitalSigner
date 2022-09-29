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
import basedatos.tablas.BdAAutusu;

/**
 *
 * @author ihuegal
 */
public class StAAutusu extends StBase {
    
    public StAAutusu() {
        //NADA
    }
    
    public ArrayList<BdAAutusu> filtro(BdAAutusu filtroBdAAutusu, EntityManager em) throws Exception {
        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_AUTORIDAD", filtroBdAAutusu.getIdAutoridad());
        parametros.put("ID_USUARIO", filtroBdAAutusu.getIdUsuario());
        parametros.put("FE_ALTA", filtroBdAAutusu.getFeAlta());
        parametros.put("FE_DESACTIVO", filtroBdAAutusu.getFeDesactivo());
        parametros.put("USUARIOBD", filtroBdAAutusu.getUsuariobd());
        parametros.put("TSTBD", filtroBdAAutusu.getTstbd());


        ArrayList<LinkedHashMap<String,Object>> lista = executeNativeQueryListParametros(filtroBdAAutusu.getSelectFiltro(), parametros, em);
        if (lista != null && !lista.isEmpty()) {
            return Mapeador.mapea(lista, BdAAutusu.class);
        }
        return null;
    }
    
    public BdAAutusu item(Integer idAutoridad, Integer idUsuario, EntityManager em) throws Exception {
        BdAAutusu filtroBdAAutusu = new BdAAutusu();
        filtroBdAAutusu.setIdAutoridad(idAutoridad);
        filtroBdAAutusu.setIdUsuario(idUsuario);

        
        ArrayList<BdAAutusu> listaBdAAutusu = filtro(filtroBdAAutusu, em);
        if (listaBdAAutusu != null && !listaBdAAutusu.isEmpty()) {
            return listaBdAAutusu.get(0);
        }
        return null;
    }
    
    public int alta(BdAAutusu newBdAAutusu, EntityManager em) throws Exception {

        if(AppInit.TIPO_BASEDATOS == BaseDatos.ORACLE) {
            if (Validation.isNullOrEmpty(newBdAAutusu.getIdAutoridad())) {
                throw new RequiredFieldException("ID_AUTORIDAD");
            }
        }
        if(AppInit.TIPO_BASEDATOS == BaseDatos.ORACLE) {
            if (Validation.isNullOrEmpty(newBdAAutusu.getIdUsuario())) {
                throw new RequiredFieldException("ID_USUARIO");
            }
        }
        if (Validation.isNullOrEmpty(newBdAAutusu.getFeAlta())) {
            throw new RequiredFieldException("FE_ALTA");
        }
   

        newBdAAutusu.setUsuariobd(Session.getCoUsuario());

        newBdAAutusu.setTstbd(new Date());

        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_AUTORIDAD", newBdAAutusu.getIdAutoridad());
        parametros.put("ID_USUARIO", newBdAAutusu.getIdUsuario());
        parametros.put("FE_ALTA", newBdAAutusu.getFeAlta());
        parametros.put("FE_DESACTIVO", newBdAAutusu.getFeDesactivo());
        parametros.put("USUARIOBD", newBdAAutusu.getUsuariobd());
        parametros.put("TSTBD", newBdAAutusu.getTstbd());


        return executeNativeQueryParametros(newBdAAutusu.getInsert(), parametros, em);
    }

    public int actualiza(BdAAutusu upBdAAutusu, EntityManager em) throws Exception {
        
        if (Validation.isNullOrEmpty(upBdAAutusu.getIdAutoridad())) {
            throw new RequiredFieldException("ID_AUTORIDAD");
        }
        if (Validation.isNullOrEmpty(upBdAAutusu.getIdUsuario())) {
            throw new RequiredFieldException("ID_USUARIO");
        }
        if (Validation.isNullOrEmpty(upBdAAutusu.getFeAlta())) {
            throw new RequiredFieldException("FE_ALTA");
        }


        upBdAAutusu.setUsuariobd(Session.getCoUsuario());

        upBdAAutusu.setTstbd(new Date());

        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_AUTORIDAD", upBdAAutusu.getIdAutoridad());
        parametros.put("ID_USUARIO", upBdAAutusu.getIdUsuario());
        parametros.put("FE_ALTA", upBdAAutusu.getFeAlta());
        parametros.put("FE_DESACTIVO", upBdAAutusu.getFeDesactivo());
        parametros.put("USUARIOBD", upBdAAutusu.getUsuariobd());
        parametros.put("TSTBD", upBdAAutusu.getTstbd());


        return executeNativeQueryParametros(upBdAAutusu.getUpdate(), parametros, em);
    }

    public int baja(BdAAutusu delBdAAutusu, EntityManager em) throws  Exception {

        if (Validation.isNullOrEmpty(delBdAAutusu.getIdAutoridad())) {
            throw new RequiredFieldException("ID_AUTORIDAD");
        }
        if (Validation.isNullOrEmpty(delBdAAutusu.getIdUsuario())) {
            throw new RequiredFieldException("ID_USUARIO");
        }
        

        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_AUTORIDAD", delBdAAutusu.getIdAutoridad());
        parametros.put("ID_USUARIO", delBdAAutusu.getIdUsuario());


        return executeNativeQueryParametros(delBdAAutusu.getDelete(), parametros, em);
    }
}

