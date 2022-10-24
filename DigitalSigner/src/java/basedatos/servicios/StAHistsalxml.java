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
import basedatos.tablas.BdAHistsalxml;
import seguridad.usuarios.DatosUsuario;

/**
 *
 * @author ihuegal
 */
public class StAHistsalxml extends StBase {
    
    private DatosUsuario datosUsuario = null;
    
    public StAHistsalxml(DatosUsuario datosUsuario) {
        this.datosUsuario = datosUsuario;
    }
    
    public ArrayList<BdAHistsalxml> filtro(BdAHistsalxml filtroBdAHistsalxml, EntityManager em) throws Exception {
        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_HISTSALXML", filtroBdAHistsalxml.getIdHistsalxml());
        parametros.put("ID_SALIDAXML", filtroBdAHistsalxml.getIdSalidaxml());
        parametros.put("ID_SITUACIONXML", filtroBdAHistsalxml.getIdSituacionxml());
        parametros.put("FE_ALTA", filtroBdAHistsalxml.getFeAlta());
        parametros.put("FE_DESACTIVO", filtroBdAHistsalxml.getFeDesactivo());
        parametros.put("USUARIOBD", filtroBdAHistsalxml.getUsuariobd());
        parametros.put("TSTBD", filtroBdAHistsalxml.getTstbd());


        ArrayList<LinkedHashMap<String,Object>> lista = executeNativeQueryListParametros(filtroBdAHistsalxml.getSelectFiltro(), parametros, em).getResultListMapped();
        if (lista != null && !lista.isEmpty()) {
            return Mapeador.mapea(lista, BdAHistsalxml.class);
        }
        return null;
    }
    
    public BdAHistsalxml item(Integer idHistsalxml, EntityManager em) throws Exception {
        BdAHistsalxml filtroBdAHistsalxml = new BdAHistsalxml();
        filtroBdAHistsalxml.setIdHistsalxml(idHistsalxml);

        
        ArrayList<BdAHistsalxml> listaBdAHistsalxml = filtro(filtroBdAHistsalxml, em);
        if (listaBdAHistsalxml != null && !listaBdAHistsalxml.isEmpty()) {
            return listaBdAHistsalxml.get(0);
        }
        return null;
    }
    
    public int alta(BdAHistsalxml newBdAHistsalxml, EntityManager em) throws Exception {

        if(AppInit.TIPO_BASEDATOS == BaseDatos.ORACLE) {
            if (Validation.isNullOrEmpty(newBdAHistsalxml.getIdHistsalxml())) {
                throw new RequiredFieldException("ID_HISTSALXML");
            }
        }
        if (Validation.isNullOrEmpty(newBdAHistsalxml.getIdSalidaxml())) {
            throw new RequiredFieldException("ID_SALIDAXML");
        }
        if (Validation.isNullOrEmpty(newBdAHistsalxml.getIdSituacionxml())) {
            throw new RequiredFieldException("ID_SITUACIONXML");
        }
        if (Validation.isNullOrEmpty(newBdAHistsalxml.getFeAlta())) {
            throw new RequiredFieldException("FE_ALTA");
        }
   

        newBdAHistsalxml.setUsuariobd(datosUsuario.getBdTUsuario().getCoUsuario());

        newBdAHistsalxml.setTstbd(new Date());

        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_HISTSALXML", newBdAHistsalxml.getIdHistsalxml());
        parametros.put("ID_SALIDAXML", newBdAHistsalxml.getIdSalidaxml());
        parametros.put("ID_SITUACIONXML", newBdAHistsalxml.getIdSituacionxml());
        parametros.put("FE_ALTA", newBdAHistsalxml.getFeAlta());
        parametros.put("FE_DESACTIVO", newBdAHistsalxml.getFeDesactivo());
        parametros.put("USUARIOBD", newBdAHistsalxml.getUsuariobd());
        parametros.put("TSTBD", newBdAHistsalxml.getTstbd());


        return executeNativeQueryParametros(newBdAHistsalxml.getInsert(), parametros, em);
    }

    public int actualiza(BdAHistsalxml upBdAHistsalxml, EntityManager em) throws Exception {
        
        if (Validation.isNullOrEmpty(upBdAHistsalxml.getIdHistsalxml())) {
            throw new RequiredFieldException("ID_HISTSALXML");
        }
        if (Validation.isNullOrEmpty(upBdAHistsalxml.getIdSalidaxml())) {
            throw new RequiredFieldException("ID_SALIDAXML");
        }
        if (Validation.isNullOrEmpty(upBdAHistsalxml.getIdSituacionxml())) {
            throw new RequiredFieldException("ID_SITUACIONXML");
        }
        if (Validation.isNullOrEmpty(upBdAHistsalxml.getFeAlta())) {
            throw new RequiredFieldException("FE_ALTA");
        }


        upBdAHistsalxml.setUsuariobd(datosUsuario.getBdTUsuario().getCoUsuario());

        upBdAHistsalxml.setTstbd(new Date());

        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_HISTSALXML", upBdAHistsalxml.getIdHistsalxml());
        parametros.put("ID_SALIDAXML", upBdAHistsalxml.getIdSalidaxml());
        parametros.put("ID_SITUACIONXML", upBdAHistsalxml.getIdSituacionxml());
        parametros.put("FE_ALTA", upBdAHistsalxml.getFeAlta());
        parametros.put("FE_DESACTIVO", upBdAHistsalxml.getFeDesactivo());
        parametros.put("USUARIOBD", upBdAHistsalxml.getUsuariobd());
        parametros.put("TSTBD", upBdAHistsalxml.getTstbd());


        return executeNativeQueryParametros(upBdAHistsalxml.getUpdate(), parametros, em);
    }

    public int baja(BdAHistsalxml delBdAHistsalxml, EntityManager em) throws  Exception {

        if (Validation.isNullOrEmpty(delBdAHistsalxml.getIdHistsalxml())) {
            throw new RequiredFieldException("ID_HISTSALXML");
        }
        

        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_HISTSALXML", delBdAHistsalxml.getIdHistsalxml());


        return executeNativeQueryParametros(delBdAHistsalxml.getDelete(), parametros, em);
    }
}

