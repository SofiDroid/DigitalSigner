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
import basedatos.tablas.BdAConfvaluni;

/**
 *
 * @author ihuegal
 */
public class StAConfvaluni extends StBase {
    
    public StAConfvaluni() {
        //NADA
    }
    
    public ArrayList<BdAConfvaluni> filtro(BdAConfvaluni filtroBdAConfvaluni, EntityManager em) throws Exception {
        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_CONFVALUNI", filtroBdAConfvaluni.getIdConfvaluni());
        parametros.put("ID_UNIDAD", filtroBdAConfvaluni.getIdUnidad());
        parametros.put("ID_CONFIGURACION", filtroBdAConfvaluni.getIdConfiguracion());
        parametros.put("ID_CONFVALOR", filtroBdAConfvaluni.getIdConfvalor());
        parametros.put("DS_VALORLIBRE", filtroBdAConfvaluni.getDsValorlibre());
        parametros.put("FE_ALTA", filtroBdAConfvaluni.getFeAlta());
        parametros.put("FE_DESACTIVO", filtroBdAConfvaluni.getFeDesactivo());
        parametros.put("USUARIOBD", filtroBdAConfvaluni.getUsuariobd());
        parametros.put("TSTBD", filtroBdAConfvaluni.getTstbd());


        ArrayList<LinkedHashMap<String,Object>> lista = executeNativeQueryListParametros(filtroBdAConfvaluni.getSelectFiltro(), parametros, em).getResultListMapped();
        if (lista != null && !lista.isEmpty()) {
            return Mapeador.mapea(lista, BdAConfvaluni.class);
        }
        return null;
    }
    
    public BdAConfvaluni item(Integer idConfvaluni, EntityManager em) throws Exception {
        BdAConfvaluni filtroBdAConfvaluni = new BdAConfvaluni();
        filtroBdAConfvaluni.setIdConfvaluni(idConfvaluni);

        
        ArrayList<BdAConfvaluni> listaBdAConfvaluni = filtro(filtroBdAConfvaluni, em);
        if (listaBdAConfvaluni != null && !listaBdAConfvaluni.isEmpty()) {
            return listaBdAConfvaluni.get(0);
        }
        return null;
    }
    
    public int alta(BdAConfvaluni newBdAConfvaluni, EntityManager em) throws Exception {

        if(AppInit.TIPO_BASEDATOS == BaseDatos.ORACLE) {
            if (Validation.isNullOrEmpty(newBdAConfvaluni.getIdConfvaluni())) {
                throw new RequiredFieldException("ID_CONFVALUNI");
            }
        }
        if (Validation.isNullOrEmpty(newBdAConfvaluni.getIdUnidad())) {
            throw new RequiredFieldException("ID_UNIDAD");
        }
        if (Validation.isNullOrEmpty(newBdAConfvaluni.getIdConfiguracion())) {
            throw new RequiredFieldException("ID_CONFIGURACION");
        }
        if (Validation.isNullOrEmpty(newBdAConfvaluni.getFeAlta())) {
            throw new RequiredFieldException("FE_ALTA");
        }
   

        newBdAConfvaluni.setUsuariobd(Session.getCoUsuario());

        newBdAConfvaluni.setTstbd(new Date());

        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_CONFVALUNI", newBdAConfvaluni.getIdConfvaluni());
        parametros.put("ID_UNIDAD", newBdAConfvaluni.getIdUnidad());
        parametros.put("ID_CONFIGURACION", newBdAConfvaluni.getIdConfiguracion());
        parametros.put("ID_CONFVALOR", newBdAConfvaluni.getIdConfvalor());
        parametros.put("DS_VALORLIBRE", newBdAConfvaluni.getDsValorlibre());
        parametros.put("FE_ALTA", newBdAConfvaluni.getFeAlta());
        parametros.put("FE_DESACTIVO", newBdAConfvaluni.getFeDesactivo());
        parametros.put("USUARIOBD", newBdAConfvaluni.getUsuariobd());
        parametros.put("TSTBD", newBdAConfvaluni.getTstbd());


        return executeNativeQueryParametros(newBdAConfvaluni.getInsert(), parametros, em);
    }

    public int actualiza(BdAConfvaluni upBdAConfvaluni, EntityManager em) throws Exception {
        
        if (Validation.isNullOrEmpty(upBdAConfvaluni.getIdConfvaluni())) {
            throw new RequiredFieldException("ID_CONFVALUNI");
        }
        if (Validation.isNullOrEmpty(upBdAConfvaluni.getIdUnidad())) {
            throw new RequiredFieldException("ID_UNIDAD");
        }
        if (Validation.isNullOrEmpty(upBdAConfvaluni.getIdConfiguracion())) {
            throw new RequiredFieldException("ID_CONFIGURACION");
        }
        if (Validation.isNullOrEmpty(upBdAConfvaluni.getFeAlta())) {
            throw new RequiredFieldException("FE_ALTA");
        }


        upBdAConfvaluni.setUsuariobd(Session.getCoUsuario());

        upBdAConfvaluni.setTstbd(new Date());

        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_CONFVALUNI", upBdAConfvaluni.getIdConfvaluni());
        parametros.put("ID_UNIDAD", upBdAConfvaluni.getIdUnidad());
        parametros.put("ID_CONFIGURACION", upBdAConfvaluni.getIdConfiguracion());
        parametros.put("ID_CONFVALOR", upBdAConfvaluni.getIdConfvalor());
        parametros.put("DS_VALORLIBRE", upBdAConfvaluni.getDsValorlibre());
        parametros.put("FE_ALTA", upBdAConfvaluni.getFeAlta());
        parametros.put("FE_DESACTIVO", upBdAConfvaluni.getFeDesactivo());
        parametros.put("USUARIOBD", upBdAConfvaluni.getUsuariobd());
        parametros.put("TSTBD", upBdAConfvaluni.getTstbd());


        return executeNativeQueryParametros(upBdAConfvaluni.getUpdate(), parametros, em);
    }

    public int baja(BdAConfvaluni delBdAConfvaluni, EntityManager em) throws  Exception {

        if (Validation.isNullOrEmpty(delBdAConfvaluni.getIdConfvaluni())) {
            throw new RequiredFieldException("ID_CONFVALUNI");
        }
        

        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_CONFVALUNI", delBdAConfvaluni.getIdConfvaluni());


        return executeNativeQueryParametros(delBdAConfvaluni.getDelete(), parametros, em);
    }
}

