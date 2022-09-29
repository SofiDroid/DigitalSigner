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
import basedatos.tablas.BdADocfirma;

/**
 *
 * @author ihuegal
 */
public class StADocfirma extends StBase {
    
    public StADocfirma() {
        //NADA
    }
    
    public ArrayList<BdADocfirma> filtro(BdADocfirma filtroBdADocfirma, EntityManager em) throws Exception {
        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_DOCFIRMA", filtroBdADocfirma.getIdDocfirma());
        parametros.put("ID_DOCUMENTO", filtroBdADocfirma.getIdDocumento());
        parametros.put("ID_AUTORIDAD", filtroBdADocfirma.getIdAutoridad());
        parametros.put("EN_ORDEN", filtroBdADocfirma.getEnOrden());
        parametros.put("DS_FIRMAPOSX", filtroBdADocfirma.getDsFirmaposx());
        parametros.put("DS_FIRMAPOSY", filtroBdADocfirma.getDsFirmaposy());
        parametros.put("FE_FIRMA", filtroBdADocfirma.getFeFirma());
        parametros.put("FE_ALTA", filtroBdADocfirma.getFeAlta());
        parametros.put("FE_DESACTIVO", filtroBdADocfirma.getFeDesactivo());
        parametros.put("USUARIOBD", filtroBdADocfirma.getUsuariobd());
        parametros.put("TSTBD", filtroBdADocfirma.getTstbd());


        ArrayList<LinkedHashMap<String,Object>> lista = executeNativeQueryListParametros(filtroBdADocfirma.getSelectFiltro(), parametros, em);
        if (lista != null && !lista.isEmpty()) {
            return Mapeador.mapea(lista, BdADocfirma.class);
        }
        return null;
    }
    
    public BdADocfirma item(Integer idDocfirma, EntityManager em) throws Exception {
        BdADocfirma filtroBdADocfirma = new BdADocfirma();
        filtroBdADocfirma.setIdDocfirma(idDocfirma);

        
        ArrayList<BdADocfirma> listaBdADocfirma = filtro(filtroBdADocfirma, em);
        if (listaBdADocfirma != null && !listaBdADocfirma.isEmpty()) {
            return listaBdADocfirma.get(0);
        }
        return null;
    }
    
    public int alta(BdADocfirma newBdADocfirma, EntityManager em) throws Exception {

        if(AppInit.TIPO_BASEDATOS == BaseDatos.ORACLE) {
            if (Validation.isNullOrEmpty(newBdADocfirma.getIdDocfirma())) {
                throw new RequiredFieldException("ID_DOCFIRMA");
            }
        }
        if (Validation.isNullOrEmpty(newBdADocfirma.getIdDocumento())) {
            throw new RequiredFieldException("ID_DOCUMENTO");
        }
        if (Validation.isNullOrEmpty(newBdADocfirma.getIdAutoridad())) {
            throw new RequiredFieldException("ID_AUTORIDAD");
        }
        if (Validation.isNullOrEmpty(newBdADocfirma.getEnOrden())) {
            throw new RequiredFieldException("EN_ORDEN");
        }
        if (Validation.isNullOrEmpty(newBdADocfirma.getDsFirmaposx())) {
            throw new RequiredFieldException("DS_FIRMAPOSX");
        }
        if (Validation.isNullOrEmpty(newBdADocfirma.getDsFirmaposy())) {
            throw new RequiredFieldException("DS_FIRMAPOSY");
        }
        if (Validation.isNullOrEmpty(newBdADocfirma.getFeAlta())) {
            throw new RequiredFieldException("FE_ALTA");
        }
   

        newBdADocfirma.setUsuariobd(Session.getCoUsuario());

        newBdADocfirma.setTstbd(new Date());

        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_DOCFIRMA", newBdADocfirma.getIdDocfirma());
        parametros.put("ID_DOCUMENTO", newBdADocfirma.getIdDocumento());
        parametros.put("ID_AUTORIDAD", newBdADocfirma.getIdAutoridad());
        parametros.put("EN_ORDEN", newBdADocfirma.getEnOrden());
        parametros.put("DS_FIRMAPOSX", newBdADocfirma.getDsFirmaposx());
        parametros.put("DS_FIRMAPOSY", newBdADocfirma.getDsFirmaposy());
        parametros.put("FE_FIRMA", newBdADocfirma.getFeFirma());
        parametros.put("FE_ALTA", newBdADocfirma.getFeAlta());
        parametros.put("FE_DESACTIVO", newBdADocfirma.getFeDesactivo());
        parametros.put("USUARIOBD", newBdADocfirma.getUsuariobd());
        parametros.put("TSTBD", newBdADocfirma.getTstbd());


        return executeNativeQueryParametros(newBdADocfirma.getInsert(), parametros, em);
    }

    public int actualiza(BdADocfirma upBdADocfirma, EntityManager em) throws Exception {
        
        if (Validation.isNullOrEmpty(upBdADocfirma.getIdDocfirma())) {
            throw new RequiredFieldException("ID_DOCFIRMA");
        }
        if (Validation.isNullOrEmpty(upBdADocfirma.getIdDocumento())) {
            throw new RequiredFieldException("ID_DOCUMENTO");
        }
        if (Validation.isNullOrEmpty(upBdADocfirma.getIdAutoridad())) {
            throw new RequiredFieldException("ID_AUTORIDAD");
        }
        if (Validation.isNullOrEmpty(upBdADocfirma.getEnOrden())) {
            throw new RequiredFieldException("EN_ORDEN");
        }
        if (Validation.isNullOrEmpty(upBdADocfirma.getDsFirmaposx())) {
            throw new RequiredFieldException("DS_FIRMAPOSX");
        }
        if (Validation.isNullOrEmpty(upBdADocfirma.getDsFirmaposy())) {
            throw new RequiredFieldException("DS_FIRMAPOSY");
        }
        if (Validation.isNullOrEmpty(upBdADocfirma.getFeAlta())) {
            throw new RequiredFieldException("FE_ALTA");
        }


        upBdADocfirma.setUsuariobd(Session.getCoUsuario());

        upBdADocfirma.setTstbd(new Date());

        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_DOCFIRMA", upBdADocfirma.getIdDocfirma());
        parametros.put("ID_DOCUMENTO", upBdADocfirma.getIdDocumento());
        parametros.put("ID_AUTORIDAD", upBdADocfirma.getIdAutoridad());
        parametros.put("EN_ORDEN", upBdADocfirma.getEnOrden());
        parametros.put("DS_FIRMAPOSX", upBdADocfirma.getDsFirmaposx());
        parametros.put("DS_FIRMAPOSY", upBdADocfirma.getDsFirmaposy());
        parametros.put("FE_FIRMA", upBdADocfirma.getFeFirma());
        parametros.put("FE_ALTA", upBdADocfirma.getFeAlta());
        parametros.put("FE_DESACTIVO", upBdADocfirma.getFeDesactivo());
        parametros.put("USUARIOBD", upBdADocfirma.getUsuariobd());
        parametros.put("TSTBD", upBdADocfirma.getTstbd());


        return executeNativeQueryParametros(upBdADocfirma.getUpdate(), parametros, em);
    }

    public int baja(BdADocfirma delBdADocfirma, EntityManager em) throws  Exception {

        if (Validation.isNullOrEmpty(delBdADocfirma.getIdDocfirma())) {
            throw new RequiredFieldException("ID_DOCFIRMA");
        }
        

        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_DOCFIRMA", delBdADocfirma.getIdDocfirma());


        return executeNativeQueryParametros(delBdADocfirma.getDelete(), parametros, em);
    }
}

