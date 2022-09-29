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
import basedatos.tablas.BdATipousuopcper;

/**
 *
 * @author ihuegal
 */
public class StATipousuopcper extends StBase {
    
    public StATipousuopcper() {
        //NADA
    }
    
    public ArrayList<BdATipousuopcper> filtro(BdATipousuopcper filtroBdATipousuopcper, EntityManager em) throws Exception {
        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_TIPOUSUARIO", filtroBdATipousuopcper.getIdTipousuario());
        parametros.put("ID_OPCIONMENU", filtroBdATipousuopcper.getIdOpcionmenu());
        parametros.put("ID_PERMISO", filtroBdATipousuopcper.getIdPermiso());
        parametros.put("FE_ALTA", filtroBdATipousuopcper.getFeAlta());
        parametros.put("FE_DESACTIVO", filtroBdATipousuopcper.getFeDesactivo());
        parametros.put("USUARIOBD", filtroBdATipousuopcper.getUsuariobd());
        parametros.put("TSTBD", filtroBdATipousuopcper.getTstbd());


        ArrayList<LinkedHashMap<String,Object>> lista = executeNativeQueryListParametros(filtroBdATipousuopcper.getSelectFiltro(), parametros, em);
        if (lista != null && !lista.isEmpty()) {
            return Mapeador.mapea(lista, BdATipousuopcper.class);
        }
        return null;
    }
    
    public BdATipousuopcper item(Integer idTipousuario, Integer idOpcionmenu, Integer idPermiso, EntityManager em) throws Exception {
        BdATipousuopcper filtroBdATipousuopcper = new BdATipousuopcper();
        filtroBdATipousuopcper.setIdTipousuario(idTipousuario);
        filtroBdATipousuopcper.setIdOpcionmenu(idOpcionmenu);
        filtroBdATipousuopcper.setIdPermiso(idPermiso);

        
        ArrayList<BdATipousuopcper> listaBdATipousuopcper = filtro(filtroBdATipousuopcper, em);
        if (listaBdATipousuopcper != null && !listaBdATipousuopcper.isEmpty()) {
            return listaBdATipousuopcper.get(0);
        }
        return null;
    }
    
    public int alta(BdATipousuopcper newBdATipousuopcper, EntityManager em) throws Exception {

        if(AppInit.TIPO_BASEDATOS == BaseDatos.ORACLE) {
            if (Validation.isNullOrEmpty(newBdATipousuopcper.getIdTipousuario())) {
                throw new RequiredFieldException("ID_TIPOUSUARIO");
            }
        }
        if(AppInit.TIPO_BASEDATOS == BaseDatos.ORACLE) {
            if (Validation.isNullOrEmpty(newBdATipousuopcper.getIdOpcionmenu())) {
                throw new RequiredFieldException("ID_OPCIONMENU");
            }
        }
        if(AppInit.TIPO_BASEDATOS == BaseDatos.ORACLE) {
            if (Validation.isNullOrEmpty(newBdATipousuopcper.getIdPermiso())) {
                throw new RequiredFieldException("ID_PERMISO");
            }
        }
        if (Validation.isNullOrEmpty(newBdATipousuopcper.getFeAlta())) {
            throw new RequiredFieldException("FE_ALTA");
        }
   

        newBdATipousuopcper.setUsuariobd(Session.getCoUsuario());

        newBdATipousuopcper.setTstbd(new Date());

        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_TIPOUSUARIO", newBdATipousuopcper.getIdTipousuario());
        parametros.put("ID_OPCIONMENU", newBdATipousuopcper.getIdOpcionmenu());
        parametros.put("ID_PERMISO", newBdATipousuopcper.getIdPermiso());
        parametros.put("FE_ALTA", newBdATipousuopcper.getFeAlta());
        parametros.put("FE_DESACTIVO", newBdATipousuopcper.getFeDesactivo());
        parametros.put("USUARIOBD", newBdATipousuopcper.getUsuariobd());
        parametros.put("TSTBD", newBdATipousuopcper.getTstbd());


        return executeNativeQueryParametros(newBdATipousuopcper.getInsert(), parametros, em);
    }

    public int actualiza(BdATipousuopcper upBdATipousuopcper, EntityManager em) throws Exception {
        
        if (Validation.isNullOrEmpty(upBdATipousuopcper.getIdTipousuario())) {
            throw new RequiredFieldException("ID_TIPOUSUARIO");
        }
        if (Validation.isNullOrEmpty(upBdATipousuopcper.getIdOpcionmenu())) {
            throw new RequiredFieldException("ID_OPCIONMENU");
        }
        if (Validation.isNullOrEmpty(upBdATipousuopcper.getIdPermiso())) {
            throw new RequiredFieldException("ID_PERMISO");
        }
        if (Validation.isNullOrEmpty(upBdATipousuopcper.getFeAlta())) {
            throw new RequiredFieldException("FE_ALTA");
        }


        upBdATipousuopcper.setUsuariobd(Session.getCoUsuario());

        upBdATipousuopcper.setTstbd(new Date());

        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_TIPOUSUARIO", upBdATipousuopcper.getIdTipousuario());
        parametros.put("ID_OPCIONMENU", upBdATipousuopcper.getIdOpcionmenu());
        parametros.put("ID_PERMISO", upBdATipousuopcper.getIdPermiso());
        parametros.put("FE_ALTA", upBdATipousuopcper.getFeAlta());
        parametros.put("FE_DESACTIVO", upBdATipousuopcper.getFeDesactivo());
        parametros.put("USUARIOBD", upBdATipousuopcper.getUsuariobd());
        parametros.put("TSTBD", upBdATipousuopcper.getTstbd());


        return executeNativeQueryParametros(upBdATipousuopcper.getUpdate(), parametros, em);
    }

    public int baja(BdATipousuopcper delBdATipousuopcper, EntityManager em) throws  Exception {

        if (Validation.isNullOrEmpty(delBdATipousuopcper.getIdTipousuario())) {
            throw new RequiredFieldException("ID_TIPOUSUARIO");
        }
        if (Validation.isNullOrEmpty(delBdATipousuopcper.getIdOpcionmenu())) {
            throw new RequiredFieldException("ID_OPCIONMENU");
        }
        if (Validation.isNullOrEmpty(delBdATipousuopcper.getIdPermiso())) {
            throw new RequiredFieldException("ID_PERMISO");
        }
        

        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_TIPOUSUARIO", delBdATipousuopcper.getIdTipousuario());
        parametros.put("ID_OPCIONMENU", delBdATipousuopcper.getIdOpcionmenu());
        parametros.put("ID_PERMISO", delBdATipousuopcper.getIdPermiso());


        return executeNativeQueryParametros(delBdATipousuopcper.getDelete(), parametros, em);
    }
}

