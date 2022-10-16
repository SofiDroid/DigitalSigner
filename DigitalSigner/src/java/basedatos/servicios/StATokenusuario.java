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
import basedatos.tablas.BdATokenusuario;

/**
 *
 * @author ihuegal
 */
public class StATokenusuario extends StBase {
    
    public StATokenusuario() {
        //NADA
    }
    
    public ArrayList<BdATokenusuario> filtro(BdATokenusuario filtroBdATokenusuario, EntityManager em) throws Exception {
        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_TOKENUSUARIO", filtroBdATokenusuario.getIdTokenusuario());
        parametros.put("ID_USUARIO", filtroBdATokenusuario.getIdUsuario());
        parametros.put("DS_TOKEN", filtroBdATokenusuario.getDsToken());
        parametros.put("FE_ALTA", filtroBdATokenusuario.getFeAlta());
        parametros.put("FE_DESACTIVO", filtroBdATokenusuario.getFeDesactivo());
        parametros.put("USUARIOBD", filtroBdATokenusuario.getUsuariobd());
        parametros.put("TSTBD", filtroBdATokenusuario.getTstbd());


        ArrayList<LinkedHashMap<String,Object>> lista = executeNativeQueryListParametros(filtroBdATokenusuario.getSelectFiltro(), parametros, em).getResultListMapped();
        if (lista != null && !lista.isEmpty()) {
            return Mapeador.mapea(lista, BdATokenusuario.class);
        }
        return null;
    }
    
    public BdATokenusuario item(Integer idTokenusuario, EntityManager em) throws Exception {
        BdATokenusuario filtroBdATokenusuario = new BdATokenusuario();
        filtroBdATokenusuario.setIdTokenusuario(idTokenusuario);

        
        ArrayList<BdATokenusuario> listaBdATokenusuario = filtro(filtroBdATokenusuario, em);
        if (listaBdATokenusuario != null && !listaBdATokenusuario.isEmpty()) {
            return listaBdATokenusuario.get(0);
        }
        return null;
    }
    
    public int alta(BdATokenusuario newBdATokenusuario, EntityManager em) throws Exception {

        if(AppInit.TIPO_BASEDATOS == BaseDatos.ORACLE) {
            if (Validation.isNullOrEmpty(newBdATokenusuario.getIdTokenusuario())) {
                throw new RequiredFieldException("ID_TOKENUSUARIO");
            }
        }
        if (Validation.isNullOrEmpty(newBdATokenusuario.getIdUsuario())) {
            throw new RequiredFieldException("ID_USUARIO");
        }
        if (Validation.isNullOrEmpty(newBdATokenusuario.getDsToken())) {
            throw new RequiredFieldException("DS_TOKEN");
        }
        if (Validation.isNullOrEmpty(newBdATokenusuario.getFeAlta())) {
            throw new RequiredFieldException("FE_ALTA");
        }
   

        newBdATokenusuario.setUsuariobd(Session.getCoUsuario());

        newBdATokenusuario.setTstbd(new Date());

        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_TOKENUSUARIO", newBdATokenusuario.getIdTokenusuario());
        parametros.put("ID_USUARIO", newBdATokenusuario.getIdUsuario());
        parametros.put("DS_TOKEN", newBdATokenusuario.getDsToken());
        parametros.put("FE_ALTA", newBdATokenusuario.getFeAlta());
        parametros.put("FE_DESACTIVO", newBdATokenusuario.getFeDesactivo());
        parametros.put("USUARIOBD", newBdATokenusuario.getUsuariobd());
        parametros.put("TSTBD", newBdATokenusuario.getTstbd());


        return executeNativeQueryParametros(newBdATokenusuario.getInsert(), parametros, em);
    }

    public int actualiza(BdATokenusuario upBdATokenusuario, EntityManager em) throws Exception {
        
        if (Validation.isNullOrEmpty(upBdATokenusuario.getIdTokenusuario())) {
            throw new RequiredFieldException("ID_TOKENUSUARIO");
        }
        if (Validation.isNullOrEmpty(upBdATokenusuario.getIdUsuario())) {
            throw new RequiredFieldException("ID_USUARIO");
        }
        if (Validation.isNullOrEmpty(upBdATokenusuario.getDsToken())) {
            throw new RequiredFieldException("DS_TOKEN");
        }
        if (Validation.isNullOrEmpty(upBdATokenusuario.getFeAlta())) {
            throw new RequiredFieldException("FE_ALTA");
        }


        upBdATokenusuario.setUsuariobd(Session.getCoUsuario());

        upBdATokenusuario.setTstbd(new Date());

        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_TOKENUSUARIO", upBdATokenusuario.getIdTokenusuario());
        parametros.put("ID_USUARIO", upBdATokenusuario.getIdUsuario());
        parametros.put("DS_TOKEN", upBdATokenusuario.getDsToken());
        parametros.put("FE_ALTA", upBdATokenusuario.getFeAlta());
        parametros.put("FE_DESACTIVO", upBdATokenusuario.getFeDesactivo());
        parametros.put("USUARIOBD", upBdATokenusuario.getUsuariobd());
        parametros.put("TSTBD", upBdATokenusuario.getTstbd());


        return executeNativeQueryParametros(upBdATokenusuario.getUpdate(), parametros, em);
    }

    public int baja(BdATokenusuario delBdATokenusuario, EntityManager em) throws  Exception {

        if (Validation.isNullOrEmpty(delBdATokenusuario.getIdTokenusuario())) {
            throw new RequiredFieldException("ID_TOKENUSUARIO");
        }
        

        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_TOKENUSUARIO", delBdATokenusuario.getIdTokenusuario());


        return executeNativeQueryParametros(delBdATokenusuario.getDelete(), parametros, em);
    }

    public int desactivarToken(Integer idUsuario, EntityManager em) throws Exception {
        if (Validation.isNullOrEmpty(idUsuario)) {
            throw new RequiredFieldException("ID_USUARIO");
        }
        
        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_USUARIO", idUsuario);
        parametros.put("FE_DESACTIVO", new Date());
        parametros.put("USUARIOBD", Session.getCoUsuario());
        parametros.put("TSTBD", new Date());

        return executeNativeQueryParametros(new BdATokenusuario().getDesactivarToken(), parametros, em);
    }
}

