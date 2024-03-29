package basedatos.servicios;

import basedatos.Mapeador;
import basedatos.StBase;
import excepciones.RequiredFieldException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import tomcat.persistence.EntityManager;
import utilidades.Validation;
import init.AppInit;
import utilidades.BaseDatos;
import basedatos.tablas.BdTConfvalor;
import seguridad.usuarios.DatosUsuario;

/**
 *
 * @author ihuegal
 */
public class StTConfvalor extends StBase {
    
    private DatosUsuario datosUsuario = null;
    
    public StTConfvalor(DatosUsuario datosUsuario) {
        this.datosUsuario = datosUsuario;
    }
    
    public ArrayList<BdTConfvalor> filtro(BdTConfvalor filtroBdTConfvalor, EntityManager em) throws Exception {
        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_CONFVALOR", filtroBdTConfvalor.getIdConfvalor());
        parametros.put("ID_CONFIGURACION", filtroBdTConfvalor.getIdConfiguracion());
        parametros.put("CO_CONFVALOR", filtroBdTConfvalor.getCoConfvalor());
        parametros.put("DS_CONFVALOR", filtroBdTConfvalor.getDsConfvalor());
        parametros.put("FE_ALTA", filtroBdTConfvalor.getFeAlta());
        parametros.put("FE_DESACTIVO", filtroBdTConfvalor.getFeDesactivo());
        parametros.put("USUARIOBD", filtroBdTConfvalor.getUsuariobd());
        parametros.put("TSTBD", filtroBdTConfvalor.getTstbd());


        ArrayList<LinkedHashMap<String,Object>> lista = executeNativeQueryListParametros(filtroBdTConfvalor.getSelectFiltro(), parametros, em).getResultListMapped();
        if (lista != null && !lista.isEmpty()) {
            return Mapeador.mapea(lista, BdTConfvalor.class);
        }
        return null;
    }
    
    public BdTConfvalor item(Integer idConfvalor, EntityManager em) throws Exception {
        BdTConfvalor filtroBdTConfvalor = new BdTConfvalor();
        filtroBdTConfvalor.setIdConfvalor(idConfvalor);

        
        ArrayList<BdTConfvalor> listaBdTConfvalor = filtro(filtroBdTConfvalor, em);
        if (listaBdTConfvalor != null && !listaBdTConfvalor.isEmpty()) {
            return listaBdTConfvalor.get(0);
        }
        return null;
    }
    
    public int alta(BdTConfvalor newBdTConfvalor, EntityManager em) throws Exception {

        if(AppInit.TIPO_BASEDATOS == BaseDatos.ORACLE) {
            if (Validation.isNullOrEmpty(newBdTConfvalor.getIdConfvalor())) {
                throw new RequiredFieldException("ID_CONFVALOR");
            }
        }
        if (Validation.isNullOrEmpty(newBdTConfvalor.getIdConfiguracion())) {
            throw new RequiredFieldException("ID_CONFIGURACION");
        }
        if (Validation.isNullOrEmpty(newBdTConfvalor.getCoConfvalor())) {
            throw new RequiredFieldException("CO_CONFVALOR");
        }
        if (Validation.isNullOrEmpty(newBdTConfvalor.getDsConfvalor())) {
            throw new RequiredFieldException("DS_CONFVALOR");
        }
        if (Validation.isNullOrEmpty(newBdTConfvalor.getFeAlta())) {
            throw new RequiredFieldException("FE_ALTA");
        }
   

        newBdTConfvalor.setUsuariobd(datosUsuario.getBdTUsuario().getCoUsuario());

        newBdTConfvalor.setTstbd(new Date());

        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_CONFVALOR", newBdTConfvalor.getIdConfvalor());
        parametros.put("ID_CONFIGURACION", newBdTConfvalor.getIdConfiguracion());
        parametros.put("CO_CONFVALOR", newBdTConfvalor.getCoConfvalor());
        parametros.put("DS_CONFVALOR", newBdTConfvalor.getDsConfvalor());
        parametros.put("FE_ALTA", newBdTConfvalor.getFeAlta());
        parametros.put("FE_DESACTIVO", newBdTConfvalor.getFeDesactivo());
        parametros.put("USUARIOBD", newBdTConfvalor.getUsuariobd());
        parametros.put("TSTBD", newBdTConfvalor.getTstbd());


        return executeNativeQueryParametros(newBdTConfvalor.getInsert(), parametros, em);
    }

    public int actualiza(BdTConfvalor upBdTConfvalor, EntityManager em) throws Exception {
        
        if (Validation.isNullOrEmpty(upBdTConfvalor.getIdConfvalor())) {
            throw new RequiredFieldException("ID_CONFVALOR");
        }
        if (Validation.isNullOrEmpty(upBdTConfvalor.getIdConfiguracion())) {
            throw new RequiredFieldException("ID_CONFIGURACION");
        }
        if (Validation.isNullOrEmpty(upBdTConfvalor.getCoConfvalor())) {
            throw new RequiredFieldException("CO_CONFVALOR");
        }
        if (Validation.isNullOrEmpty(upBdTConfvalor.getDsConfvalor())) {
            throw new RequiredFieldException("DS_CONFVALOR");
        }
        if (Validation.isNullOrEmpty(upBdTConfvalor.getFeAlta())) {
            throw new RequiredFieldException("FE_ALTA");
        }


        upBdTConfvalor.setUsuariobd(datosUsuario.getBdTUsuario().getCoUsuario());

        upBdTConfvalor.setTstbd(new Date());

        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_CONFVALOR", upBdTConfvalor.getIdConfvalor());
        parametros.put("ID_CONFIGURACION", upBdTConfvalor.getIdConfiguracion());
        parametros.put("CO_CONFVALOR", upBdTConfvalor.getCoConfvalor());
        parametros.put("DS_CONFVALOR", upBdTConfvalor.getDsConfvalor());
        parametros.put("FE_ALTA", upBdTConfvalor.getFeAlta());
        parametros.put("FE_DESACTIVO", upBdTConfvalor.getFeDesactivo());
        parametros.put("USUARIOBD", upBdTConfvalor.getUsuariobd());
        parametros.put("TSTBD", upBdTConfvalor.getTstbd());


        return executeNativeQueryParametros(upBdTConfvalor.getUpdate(), parametros, em);
    }

    public int baja(BdTConfvalor delBdTConfvalor, EntityManager em) throws  Exception {

        if (Validation.isNullOrEmpty(delBdTConfvalor.getIdConfvalor())) {
            throw new RequiredFieldException("ID_CONFVALOR");
        }
        

        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_CONFVALOR", delBdTConfvalor.getIdConfvalor());


        return executeNativeQueryParametros(delBdTConfvalor.getDelete(), parametros, em);
    }
}

