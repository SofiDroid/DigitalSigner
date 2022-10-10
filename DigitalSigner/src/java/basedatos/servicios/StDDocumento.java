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
import basedatos.tablas.BdDDocumento;

/**
 *
 * @author ihuegal
 */
public class StDDocumento extends StBase {
    
    public StDDocumento() {
        //NADA
    }
    
    public ArrayList<BdDDocumento> filtro(BdDDocumento filtroBdDDocumento, EntityManager em) throws Exception {
        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_DOCUMENTO", filtroBdDDocumento.getIdDocumento());
        parametros.put("CO_DOCUMENTO", filtroBdDDocumento.getCoDocumento());
        parametros.put("DS_DOCUMENTO", filtroBdDDocumento.getDsDocumento());
        parametros.put("ID_TIPODOCUMENTO", filtroBdDDocumento.getIdTipodocumento());
        parametros.put("BL_DOCUMENTO", filtroBdDDocumento.getBlDocumento());
        parametros.put("CO_FICHERO", filtroBdDDocumento.getCoFichero());
        parametros.put("CO_EXTENSION", filtroBdDDocumento.getCoExtension());
        parametros.put("ID_SITUACIONDOC", filtroBdDDocumento.getIdSituaciondoc());
        parametros.put("DS_RUTA", filtroBdDDocumento.getDsRuta());
        parametros.put("FE_ALTA", filtroBdDDocumento.getFeAlta());
        parametros.put("FE_DESACTIVO", filtroBdDDocumento.getFeDesactivo());
        parametros.put("USUARIOBD", filtroBdDDocumento.getUsuariobd());
        parametros.put("TSTBD", filtroBdDDocumento.getTstbd());


        ArrayList<LinkedHashMap<String,Object>> lista = executeNativeQueryListParametros(filtroBdDDocumento.getSelectFiltro(), parametros, em);
        if (lista != null && !lista.isEmpty()) {
            return Mapeador.mapea(lista, BdDDocumento.class);
        }
        return null;
    }
    
    public BdDDocumento item(Integer idDocumento, EntityManager em) throws Exception {
        BdDDocumento filtroBdDDocumento = new BdDDocumento();
        filtroBdDDocumento.setIdDocumento(idDocumento);

        
        ArrayList<BdDDocumento> listaBdDDocumento = filtro(filtroBdDDocumento, em);
        if (listaBdDDocumento != null && !listaBdDDocumento.isEmpty()) {
            return listaBdDDocumento.get(0);
        }
        return null;
    }
    
    public int alta(BdDDocumento newBdDDocumento, EntityManager em) throws Exception {

        if(AppInit.TIPO_BASEDATOS == BaseDatos.ORACLE) {
            if (Validation.isNullOrEmpty(newBdDDocumento.getIdDocumento())) {
                throw new RequiredFieldException("ID_DOCUMENTO");
            }
        }
        if (Validation.isNullOrEmpty(newBdDDocumento.getCoDocumento())) {
            throw new RequiredFieldException("CO_DOCUMENTO");
        }
        if (Validation.isNullOrEmpty(newBdDDocumento.getDsDocumento())) {
            throw new RequiredFieldException("DS_DOCUMENTO");
        }
        if (Validation.isNullOrEmpty(newBdDDocumento.getIdTipodocumento())) {
            throw new RequiredFieldException("ID_TIPODOCUMENTO");
        }
        if (Validation.isNullOrEmpty(newBdDDocumento.getCoFichero())) {
            throw new RequiredFieldException("CO_FICHERO");
        }
        if (Validation.isNullOrEmpty(newBdDDocumento.getCoExtension())) {
            throw new RequiredFieldException("CO_EXTENSION");
        }
        if (Validation.isNullOrEmpty(newBdDDocumento.getIdSituaciondoc())) {
            throw new RequiredFieldException("ID_SITUACIONDOC");
        }
        if (Validation.isNullOrEmpty(newBdDDocumento.getFeAlta())) {
            throw new RequiredFieldException("FE_ALTA");
        }
   

        newBdDDocumento.setUsuariobd(Session.getCoUsuario());

        newBdDDocumento.setTstbd(new Date());

        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_DOCUMENTO", newBdDDocumento.getIdDocumento());
        parametros.put("CO_DOCUMENTO", newBdDDocumento.getCoDocumento());
        parametros.put("DS_DOCUMENTO", newBdDDocumento.getDsDocumento());
        parametros.put("ID_TIPODOCUMENTO", newBdDDocumento.getIdTipodocumento());
        parametros.put("BL_DOCUMENTO", newBdDDocumento.getBlDocumento());
        parametros.put("CO_FICHERO", newBdDDocumento.getCoFichero());
        parametros.put("CO_EXTENSION", newBdDDocumento.getCoExtension());
        parametros.put("ID_SITUACIONDOC", newBdDDocumento.getIdSituaciondoc());
        parametros.put("DS_RUTA", newBdDDocumento.getDsRuta());
        parametros.put("FE_ALTA", newBdDDocumento.getFeAlta());
        parametros.put("FE_DESACTIVO", newBdDDocumento.getFeDesactivo());
        parametros.put("USUARIOBD", newBdDDocumento.getUsuariobd());
        parametros.put("TSTBD", newBdDDocumento.getTstbd());


        int registrosInsertados = executeNativeQueryParametros(newBdDDocumento.getInsert(), parametros, em);
        
        if (AppInit.TIPO_BASEDATOS == BaseDatos.SQLSERVER) {
            String sqlPk = "SELECT SCOPE_IDENTITY() as ID_DOCUMENTO";
            ArrayList<LinkedHashMap<String, Object>> listaResultado = executeNativeQueryListParametros(sqlPk, null, em);
            return (Integer)listaResultado.get(0).getOrDefault("ID_DOCUMENTO", registrosInsertados);
        }
        
        return registrosInsertados;
    }

    public int actualiza(BdDDocumento upBdDDocumento, EntityManager em) throws Exception {
        
        if (Validation.isNullOrEmpty(upBdDDocumento.getIdDocumento())) {
            throw new RequiredFieldException("ID_DOCUMENTO");
        }
        if (Validation.isNullOrEmpty(upBdDDocumento.getCoDocumento())) {
            throw new RequiredFieldException("CO_DOCUMENTO");
        }
        if (Validation.isNullOrEmpty(upBdDDocumento.getDsDocumento())) {
            throw new RequiredFieldException("DS_DOCUMENTO");
        }
        if (Validation.isNullOrEmpty(upBdDDocumento.getIdTipodocumento())) {
            throw new RequiredFieldException("ID_TIPODOCUMENTO");
        }
        if (Validation.isNullOrEmpty(upBdDDocumento.getCoFichero())) {
            throw new RequiredFieldException("CO_FICHERO");
        }
        if (Validation.isNullOrEmpty(upBdDDocumento.getCoExtension())) {
            throw new RequiredFieldException("CO_EXTENSION");
        }
        if (Validation.isNullOrEmpty(upBdDDocumento.getIdSituaciondoc())) {
            throw new RequiredFieldException("ID_SITUACIONDOC");
        }
        if (Validation.isNullOrEmpty(upBdDDocumento.getFeAlta())) {
            throw new RequiredFieldException("FE_ALTA");
        }


        upBdDDocumento.setUsuariobd(Session.getCoUsuario());

        upBdDDocumento.setTstbd(new Date());

        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_DOCUMENTO", upBdDDocumento.getIdDocumento());
        parametros.put("CO_DOCUMENTO", upBdDDocumento.getCoDocumento());
        parametros.put("DS_DOCUMENTO", upBdDDocumento.getDsDocumento());
        parametros.put("ID_TIPODOCUMENTO", upBdDDocumento.getIdTipodocumento());
        parametros.put("BL_DOCUMENTO", upBdDDocumento.getBlDocumento());
        parametros.put("CO_FICHERO", upBdDDocumento.getCoFichero());
        parametros.put("CO_EXTENSION", upBdDDocumento.getCoExtension());
        parametros.put("ID_SITUACIONDOC", upBdDDocumento.getIdSituaciondoc());
        parametros.put("DS_RUTA", upBdDDocumento.getDsRuta());
        parametros.put("FE_ALTA", upBdDDocumento.getFeAlta());
        parametros.put("FE_DESACTIVO", upBdDDocumento.getFeDesactivo());
        parametros.put("USUARIOBD", upBdDDocumento.getUsuariobd());
        parametros.put("TSTBD", upBdDDocumento.getTstbd());


        return executeNativeQueryParametros(upBdDDocumento.getUpdate(), parametros, em);
    }

    public int baja(BdDDocumento delBdDDocumento, EntityManager em) throws  Exception {

        if (Validation.isNullOrEmpty(delBdDDocumento.getIdDocumento())) {
            throw new RequiredFieldException("ID_DOCUMENTO");
        }
        

        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_DOCUMENTO", delBdDDocumento.getIdDocumento());


        return executeNativeQueryParametros(delBdDDocumento.getDelete(), parametros, em);
    }
}

