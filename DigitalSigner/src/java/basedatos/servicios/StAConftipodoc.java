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
import basedatos.tablas.BdAConftipodoc;
import seguridad.usuarios.DatosUsuario;

/**
 *
 * @author ihuegal
 */
public class StAConftipodoc extends StBase {
    
    private DatosUsuario datosUsuario = null;
    
    public StAConftipodoc(DatosUsuario datosUsuario) {
        this.datosUsuario = datosUsuario;
    }
    
    public ArrayList<BdAConftipodoc> filtro(BdAConftipodoc filtroBdAConftipodoc, EntityManager em) throws Exception {
        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_CONFTIPODOC", filtroBdAConftipodoc.getIdConftipodoc());
        parametros.put("ID_TIPODOCUMENTO", filtroBdAConftipodoc.getIdTipodocumento());
        parametros.put("ID_AUTORIDAD", filtroBdAConftipodoc.getIdAutoridad());
        parametros.put("EN_ORDEN", filtroBdAConftipodoc.getEnOrden());
        parametros.put("DI_TIPOFIRMA", filtroBdAConftipodoc.getDiTipofirma());
        parametros.put("DS_FIRMAPOSX", filtroBdAConftipodoc.getDsFirmaposx());
        parametros.put("DS_FIRMAPOSY", filtroBdAConftipodoc.getDsFirmaposy());
        parametros.put("FE_ALTA", filtroBdAConftipodoc.getFeAlta());
        parametros.put("FE_DESACTIVO", filtroBdAConftipodoc.getFeDesactivo());
        parametros.put("USUARIOBD", filtroBdAConftipodoc.getUsuariobd());
        parametros.put("TSTBD", filtroBdAConftipodoc.getTstbd());


        ArrayList<LinkedHashMap<String,Object>> lista = executeNativeQueryListParametros(filtroBdAConftipodoc.getSelectFiltro(), parametros, em).getResultListMapped();
        if (lista != null && !lista.isEmpty()) {
            return Mapeador.mapea(lista, BdAConftipodoc.class);
        }
        return null;
    }
    
    public BdAConftipodoc item(Integer idConftipodoc, EntityManager em) throws Exception {
        BdAConftipodoc filtroBdAConftipodoc = new BdAConftipodoc();
        filtroBdAConftipodoc.setIdConftipodoc(idConftipodoc);

        
        ArrayList<BdAConftipodoc> listaBdAConftipodoc = filtro(filtroBdAConftipodoc, em);
        if (listaBdAConftipodoc != null && !listaBdAConftipodoc.isEmpty()) {
            return listaBdAConftipodoc.get(0);
        }
        return null;
    }
    
    public int alta(BdAConftipodoc newBdAConftipodoc, EntityManager em) throws Exception {

        if(AppInit.TIPO_BASEDATOS == BaseDatos.ORACLE) {
            if (Validation.isNullOrEmpty(newBdAConftipodoc.getIdConftipodoc())) {
                throw new RequiredFieldException("ID_CONFTIPODOC");
            }
        }
        if (Validation.isNullOrEmpty(newBdAConftipodoc.getIdTipodocumento())) {
            throw new RequiredFieldException("ID_TIPODOCUMENTO");
        }
        if (Validation.isNullOrEmpty(newBdAConftipodoc.getIdAutoridad())) {
            throw new RequiredFieldException("ID_AUTORIDAD");
        }
        if (Validation.isNullOrEmpty(newBdAConftipodoc.getEnOrden())) {
            throw new RequiredFieldException("EN_ORDEN");
        }
        if (Validation.isNullOrEmpty(newBdAConftipodoc.getDiTipofirma())) {
            throw new RequiredFieldException("DI_TIPOFIRMA");
        }
        if (Validation.isNullOrEmpty(newBdAConftipodoc.getDsFirmaposx())) {
            throw new RequiredFieldException("DS_FIRMAPOSX");
        }
        if (Validation.isNullOrEmpty(newBdAConftipodoc.getDsFirmaposy())) {
            throw new RequiredFieldException("DS_FIRMAPOSY");
        }
        if (Validation.isNullOrEmpty(newBdAConftipodoc.getFeAlta())) {
            throw new RequiredFieldException("FE_ALTA");
        }
   

        newBdAConftipodoc.setUsuariobd(datosUsuario.getBdTUsuario().getCoUsuario());

        newBdAConftipodoc.setTstbd(new Date());

        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_CONFTIPODOC", newBdAConftipodoc.getIdConftipodoc());
        parametros.put("ID_TIPODOCUMENTO", newBdAConftipodoc.getIdTipodocumento());
        parametros.put("ID_AUTORIDAD", newBdAConftipodoc.getIdAutoridad());
        parametros.put("EN_ORDEN", newBdAConftipodoc.getEnOrden());
        parametros.put("DI_TIPOFIRMA", newBdAConftipodoc.getDiTipofirma());
        parametros.put("DS_FIRMAPOSX", newBdAConftipodoc.getDsFirmaposx());
        parametros.put("DS_FIRMAPOSY", newBdAConftipodoc.getDsFirmaposy());
        parametros.put("FE_ALTA", newBdAConftipodoc.getFeAlta());
        parametros.put("FE_DESACTIVO", newBdAConftipodoc.getFeDesactivo());
        parametros.put("USUARIOBD", newBdAConftipodoc.getUsuariobd());
        parametros.put("TSTBD", newBdAConftipodoc.getTstbd());


        return executeNativeQueryParametros(newBdAConftipodoc.getInsert(), parametros, em);
    }

    public int actualiza(BdAConftipodoc upBdAConftipodoc, EntityManager em) throws Exception {
        
        if (Validation.isNullOrEmpty(upBdAConftipodoc.getIdConftipodoc())) {
            throw new RequiredFieldException("ID_CONFTIPODOC");
        }
        if (Validation.isNullOrEmpty(upBdAConftipodoc.getIdTipodocumento())) {
            throw new RequiredFieldException("ID_TIPODOCUMENTO");
        }
        if (Validation.isNullOrEmpty(upBdAConftipodoc.getIdAutoridad())) {
            throw new RequiredFieldException("ID_AUTORIDAD");
        }
        if (Validation.isNullOrEmpty(upBdAConftipodoc.getEnOrden())) {
            throw new RequiredFieldException("EN_ORDEN");
        }
        if (Validation.isNullOrEmpty(upBdAConftipodoc.getDiTipofirma())) {
            throw new RequiredFieldException("DI_TIPOFIRMA");
        }
        if (Validation.isNullOrEmpty(upBdAConftipodoc.getDsFirmaposx())) {
            throw new RequiredFieldException("DS_FIRMAPOSX");
        }
        if (Validation.isNullOrEmpty(upBdAConftipodoc.getDsFirmaposy())) {
            throw new RequiredFieldException("DS_FIRMAPOSY");
        }
        if (Validation.isNullOrEmpty(upBdAConftipodoc.getFeAlta())) {
            throw new RequiredFieldException("FE_ALTA");
        }


        upBdAConftipodoc.setUsuariobd(datosUsuario.getBdTUsuario().getCoUsuario());

        upBdAConftipodoc.setTstbd(new Date());

        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_CONFTIPODOC", upBdAConftipodoc.getIdConftipodoc());
        parametros.put("ID_TIPODOCUMENTO", upBdAConftipodoc.getIdTipodocumento());
        parametros.put("ID_AUTORIDAD", upBdAConftipodoc.getIdAutoridad());
        parametros.put("EN_ORDEN", upBdAConftipodoc.getEnOrden());
        parametros.put("DI_TIPOFIRMA", upBdAConftipodoc.getDiTipofirma());
        parametros.put("DS_FIRMAPOSX", upBdAConftipodoc.getDsFirmaposx());
        parametros.put("DS_FIRMAPOSY", upBdAConftipodoc.getDsFirmaposy());
        parametros.put("FE_ALTA", upBdAConftipodoc.getFeAlta());
        parametros.put("FE_DESACTIVO", upBdAConftipodoc.getFeDesactivo());
        parametros.put("USUARIOBD", upBdAConftipodoc.getUsuariobd());
        parametros.put("TSTBD", upBdAConftipodoc.getTstbd());


        return executeNativeQueryParametros(upBdAConftipodoc.getUpdate(), parametros, em);
    }

    public int baja(BdAConftipodoc delBdAConftipodoc, EntityManager em) throws  Exception {

        if (Validation.isNullOrEmpty(delBdAConftipodoc.getIdConftipodoc())) {
            throw new RequiredFieldException("ID_CONFTIPODOC");
        }
        

        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_CONFTIPODOC", delBdAConftipodoc.getIdConftipodoc());


        return executeNativeQueryParametros(delBdAConftipodoc.getDelete(), parametros, em);
    }
}

