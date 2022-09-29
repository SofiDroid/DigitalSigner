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
import basedatos.tablas.BdADocextra;
import init.AppInit;
import utilidades.BaseDatos;

/**
 *
 * @author ihuegal
 */
public class StADocextra extends StBase {
    
    public StADocextra() {
        //NADA
    }
    
    public ArrayList<BdADocextra> filtro(BdADocextra filtroBdADocextra, EntityManager em) throws Exception {
        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_DOCEXTRA", filtroBdADocextra.getIdDocextra());
        parametros.put("ID_DOCUMENTO", filtroBdADocextra.getIdDocumento());
        parametros.put("CO_FICHERO", filtroBdADocextra.getCoFichero());
        parametros.put("CO_EXTENSION", filtroBdADocextra.getCoExtension());
        parametros.put("BL_FICHERO", filtroBdADocextra.getBlFichero());
        parametros.put("DS_RUTA", filtroBdADocextra.getDsRuta());
        parametros.put("FE_ALTA", filtroBdADocextra.getFeAlta());
        parametros.put("FE_DESACTIVO", filtroBdADocextra.getFeDesactivo());
        parametros.put("USUARIOBD", filtroBdADocextra.getUsuariobd());
        parametros.put("TSTBD", filtroBdADocextra.getTstbd());


        ArrayList<LinkedHashMap<String,Object>> lista = executeNativeQueryListParametros(filtroBdADocextra.getSelectFiltro(), parametros, em);
        if (lista != null && !lista.isEmpty()) {
            return Mapeador.mapea(lista, BdADocextra.class);
        }
        return null;
    }
    
    public BdADocextra item(Integer idDocextra, EntityManager em) throws Exception {
        BdADocextra filtroBdADocextra = new BdADocextra();
        filtroBdADocextra.setIdDocextra(idDocextra);

        
        ArrayList<BdADocextra> listaBdADocextra = filtro(filtroBdADocextra, em);
        if (listaBdADocextra != null && !listaBdADocextra.isEmpty()) {
            return listaBdADocextra.get(0);
        }
        return null;
    }
    
    public int alta(BdADocextra newBdADocextra, EntityManager em) throws Exception {

        if(AppInit.TIPO_BASEDATOS == BaseDatos.ORACLE) {
            if (Validation.isNullOrEmpty(newBdADocextra.getIdDocextra())) {
                throw new RequiredFieldException("ID_DOCEXTRA");
            }
        }
        if (Validation.isNullOrEmpty(newBdADocextra.getIdDocumento())) {
            throw new RequiredFieldException("ID_DOCUMENTO");
        }
        if (Validation.isNullOrEmpty(newBdADocextra.getCoFichero())) {
            throw new RequiredFieldException("CO_FICHERO");
        }
        if (Validation.isNullOrEmpty(newBdADocextra.getCoExtension())) {
            throw new RequiredFieldException("CO_EXTENSION");
        }
        if (Validation.isNullOrEmpty(newBdADocextra.getFeAlta())) {
            throw new RequiredFieldException("FE_ALTA");
        }
        if (Validation.isNullOrEmpty(newBdADocextra.getUsuariobd())) {
            throw new RequiredFieldException("USUARIOBD");
        }
        if (Validation.isNullOrEmpty(newBdADocextra.getTstbd())) {
            throw new RequiredFieldException("TSTBD");
        }
   

        newBdADocextra.setUsuariobd(Session.getCoUsuario());

        newBdADocextra.setTstbd(new Date());

        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_DOCEXTRA", newBdADocextra.getIdDocextra());
        parametros.put("ID_DOCUMENTO", newBdADocextra.getIdDocumento());
        parametros.put("CO_FICHERO", newBdADocextra.getCoFichero());
        parametros.put("CO_EXTENSION", newBdADocextra.getCoExtension());
        parametros.put("BL_FICHERO", newBdADocextra.getBlFichero());
        parametros.put("DS_RUTA", newBdADocextra.getDsRuta());
        parametros.put("FE_ALTA", newBdADocextra.getFeAlta());
        parametros.put("FE_DESACTIVO", newBdADocextra.getFeDesactivo());
        parametros.put("USUARIOBD", newBdADocextra.getUsuariobd());
        parametros.put("TSTBD", newBdADocextra.getTstbd());


        return executeNativeQueryParametros(newBdADocextra.getInsert(), parametros, em);
    }

    public int actualiza(BdADocextra upBdADocextra, EntityManager em) throws Exception {
        
        if (Validation.isNullOrEmpty(upBdADocextra.getIdDocextra())) {
            throw new RequiredFieldException("ID_DOCEXTRA");
        }
        if (Validation.isNullOrEmpty(upBdADocextra.getIdDocumento())) {
            throw new RequiredFieldException("ID_DOCUMENTO");
        }
        if (Validation.isNullOrEmpty(upBdADocextra.getCoFichero())) {
            throw new RequiredFieldException("CO_FICHERO");
        }
        if (Validation.isNullOrEmpty(upBdADocextra.getCoExtension())) {
            throw new RequiredFieldException("CO_EXTENSION");
        }
        if (Validation.isNullOrEmpty(upBdADocextra.getFeAlta())) {
            throw new RequiredFieldException("FE_ALTA");
        }
        if (Validation.isNullOrEmpty(upBdADocextra.getUsuariobd())) {
            throw new RequiredFieldException("USUARIOBD");
        }
        if (Validation.isNullOrEmpty(upBdADocextra.getTstbd())) {
            throw new RequiredFieldException("TSTBD");
        }


        upBdADocextra.setUsuariobd(Session.getCoUsuario());

        upBdADocextra.setTstbd(new Date());

        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_DOCEXTRA", upBdADocextra.getIdDocextra());
        parametros.put("ID_DOCUMENTO", upBdADocextra.getIdDocumento());
        parametros.put("CO_FICHERO", upBdADocextra.getCoFichero());
        parametros.put("CO_EXTENSION", upBdADocextra.getCoExtension());
        parametros.put("BL_FICHERO", upBdADocextra.getBlFichero());
        parametros.put("DS_RUTA", upBdADocextra.getDsRuta());
        parametros.put("FE_ALTA", upBdADocextra.getFeAlta());
        parametros.put("FE_DESACTIVO", upBdADocextra.getFeDesactivo());
        parametros.put("USUARIOBD", upBdADocextra.getUsuariobd());
        parametros.put("TSTBD", upBdADocextra.getTstbd());


        return executeNativeQueryParametros(upBdADocextra.getUpdate(), parametros, em);
    }

    public int baja(BdADocextra delBdADocextra, EntityManager em) throws  Exception {

        if (Validation.isNullOrEmpty(delBdADocextra.getIdDocextra())) {
            throw new RequiredFieldException("ID_DOCEXTRA");
        }
        

        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_DOCEXTRA", delBdADocextra.getIdDocextra());
        parametros.put("ID_DOCUMENTO", delBdADocextra.getIdDocumento());
        parametros.put("CO_FICHERO", delBdADocextra.getCoFichero());
        parametros.put("CO_EXTENSION", delBdADocextra.getCoExtension());
        parametros.put("BL_FICHERO", delBdADocextra.getBlFichero());
        parametros.put("DS_RUTA", delBdADocextra.getDsRuta());
        parametros.put("FE_ALTA", delBdADocextra.getFeAlta());
        parametros.put("FE_DESACTIVO", delBdADocextra.getFeDesactivo());
        parametros.put("USUARIOBD", delBdADocextra.getUsuariobd());
        parametros.put("TSTBD", delBdADocextra.getTstbd());


        return executeNativeQueryParametros(delBdADocextra.getDelete(), parametros, em);
    }
}

