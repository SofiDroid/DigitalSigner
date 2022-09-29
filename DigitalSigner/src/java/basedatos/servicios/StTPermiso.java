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
import basedatos.tablas.BdTPermiso;

/**
 *
 * @author ihuegal
 */
public class StTPermiso extends StBase {
    
    public StTPermiso() {
        //NADA
    }
    
    public ArrayList<BdTPermiso> filtro(BdTPermiso filtroBdTPermiso, EntityManager em) throws Exception {
        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_PERMISO", filtroBdTPermiso.getIdPermiso());
        parametros.put("CO_PERMISO", filtroBdTPermiso.getCoPermiso());
        parametros.put("DS_PERMISO", filtroBdTPermiso.getDsPermiso());
        parametros.put("FE_ALTA", filtroBdTPermiso.getFeAlta());
        parametros.put("FE_DESACTIVO", filtroBdTPermiso.getFeDesactivo());
        parametros.put("USUARIOBD", filtroBdTPermiso.getUsuariobd());
        parametros.put("TSTBD", filtroBdTPermiso.getTstbd());


        ArrayList<LinkedHashMap<String,Object>> lista = executeNativeQueryListParametros(filtroBdTPermiso.getSelectFiltro(), parametros, em);
        if (lista != null && !lista.isEmpty()) {
            return Mapeador.mapea(lista, BdTPermiso.class);
        }
        return null;
    }
    
    public BdTPermiso item(Integer idPermiso, EntityManager em) throws Exception {
        BdTPermiso filtroBdTPermiso = new BdTPermiso();
        filtroBdTPermiso.setIdPermiso(idPermiso);

        
        ArrayList<BdTPermiso> listaBdTPermiso = filtro(filtroBdTPermiso, em);
        if (listaBdTPermiso != null && !listaBdTPermiso.isEmpty()) {
            return listaBdTPermiso.get(0);
        }
        return null;
    }
    
    public int alta(BdTPermiso newBdTPermiso, EntityManager em) throws Exception {

        if(AppInit.TIPO_BASEDATOS == BaseDatos.ORACLE) {
            if (Validation.isNullOrEmpty(newBdTPermiso.getIdPermiso())) {
                throw new RequiredFieldException("ID_PERMISO");
            }
        }
        if (Validation.isNullOrEmpty(newBdTPermiso.getCoPermiso())) {
            throw new RequiredFieldException("CO_PERMISO");
        }
        if (Validation.isNullOrEmpty(newBdTPermiso.getDsPermiso())) {
            throw new RequiredFieldException("DS_PERMISO");
        }
        if (Validation.isNullOrEmpty(newBdTPermiso.getFeAlta())) {
            throw new RequiredFieldException("FE_ALTA");
        }
   

        newBdTPermiso.setUsuariobd(Session.getCoUsuario());

        newBdTPermiso.setTstbd(new Date());

        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_PERMISO", newBdTPermiso.getIdPermiso());
        parametros.put("CO_PERMISO", newBdTPermiso.getCoPermiso());
        parametros.put("DS_PERMISO", newBdTPermiso.getDsPermiso());
        parametros.put("FE_ALTA", newBdTPermiso.getFeAlta());
        parametros.put("FE_DESACTIVO", newBdTPermiso.getFeDesactivo());
        parametros.put("USUARIOBD", newBdTPermiso.getUsuariobd());
        parametros.put("TSTBD", newBdTPermiso.getTstbd());


        return executeNativeQueryParametros(newBdTPermiso.getInsert(), parametros, em);
    }

    public int actualiza(BdTPermiso upBdTPermiso, EntityManager em) throws Exception {
        
        if (Validation.isNullOrEmpty(upBdTPermiso.getIdPermiso())) {
            throw new RequiredFieldException("ID_PERMISO");
        }
        if (Validation.isNullOrEmpty(upBdTPermiso.getCoPermiso())) {
            throw new RequiredFieldException("CO_PERMISO");
        }
        if (Validation.isNullOrEmpty(upBdTPermiso.getDsPermiso())) {
            throw new RequiredFieldException("DS_PERMISO");
        }
        if (Validation.isNullOrEmpty(upBdTPermiso.getFeAlta())) {
            throw new RequiredFieldException("FE_ALTA");
        }


        upBdTPermiso.setUsuariobd(Session.getCoUsuario());

        upBdTPermiso.setTstbd(new Date());

        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_PERMISO", upBdTPermiso.getIdPermiso());
        parametros.put("CO_PERMISO", upBdTPermiso.getCoPermiso());
        parametros.put("DS_PERMISO", upBdTPermiso.getDsPermiso());
        parametros.put("FE_ALTA", upBdTPermiso.getFeAlta());
        parametros.put("FE_DESACTIVO", upBdTPermiso.getFeDesactivo());
        parametros.put("USUARIOBD", upBdTPermiso.getUsuariobd());
        parametros.put("TSTBD", upBdTPermiso.getTstbd());


        return executeNativeQueryParametros(upBdTPermiso.getUpdate(), parametros, em);
    }

    public int baja(BdTPermiso delBdTPermiso, EntityManager em) throws  Exception {

        if (Validation.isNullOrEmpty(delBdTPermiso.getIdPermiso())) {
            throw new RequiredFieldException("ID_PERMISO");
        }
        

        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_PERMISO", delBdTPermiso.getIdPermiso());


        return executeNativeQueryParametros(delBdTPermiso.getDelete(), parametros, em);
    }
}

