package basedatos.servicios;

import basedatos.Mapeador;
import basedatos.StBase;
import basedatos.tablas.BdAHistsalxml;
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
import basedatos.tablas.BdDSalidaxml;
import basedatos.tablas.BdTSituacionxml;
import excepciones.RegistryNotFoundException;
import java.nio.charset.StandardCharsets;

/**
 *
 * @author ihuegal
 */
public class StDSalidaxml extends StBase {
    
    public StDSalidaxml() {
        //NADA
    }
    
    public ArrayList<BdDSalidaxml> filtro(BdDSalidaxml filtroBdDSalidaxml, EntityManager em) throws Exception {
        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_SALIDAXML", filtroBdDSalidaxml.getIdSalidaxml());
        parametros.put("BL_SALIDAXML", filtroBdDSalidaxml.getBlSalidaxml());
        parametros.put("ID_DOCUMENTO", filtroBdDSalidaxml.getIdDocumento());
        parametros.put("ID_SITUACIONXML", filtroBdDSalidaxml.getIdSituacionxml());
        parametros.put("DS_RUTA", filtroBdDSalidaxml.getDsRuta());
        parametros.put("FE_ALTA", filtroBdDSalidaxml.getFeAlta());
        parametros.put("FE_DESACTIVO", filtroBdDSalidaxml.getFeDesactivo());
        parametros.put("USUARIOBD", filtroBdDSalidaxml.getUsuariobd());
        parametros.put("TSTBD", filtroBdDSalidaxml.getTstbd());


        ArrayList<LinkedHashMap<String,Object>> lista = executeNativeQueryListParametros(filtroBdDSalidaxml.getSelectFiltro(), parametros, em).getResultListMapped();
        if (lista != null && !lista.isEmpty()) {
            return Mapeador.mapea(lista, BdDSalidaxml.class);
        }
        return null;
    }
    
    public BdDSalidaxml item(Integer idSalidaxml, EntityManager em) throws Exception {
        BdDSalidaxml filtroBdDSalidaxml = new BdDSalidaxml();
        filtroBdDSalidaxml.setIdSalidaxml(idSalidaxml);

        
        ArrayList<BdDSalidaxml> listaBdDSalidaxml = filtro(filtroBdDSalidaxml, em);
        if (listaBdDSalidaxml != null && !listaBdDSalidaxml.isEmpty()) {
            return listaBdDSalidaxml.get(0);
        }
        return null;
    }
    
    public int alta(BdDSalidaxml newBdDSalidaxml, EntityManager em) throws Exception {

        if(AppInit.TIPO_BASEDATOS == BaseDatos.ORACLE) {
            if (Validation.isNullOrEmpty(newBdDSalidaxml.getIdSalidaxml())) {
                throw new RequiredFieldException("ID_SALIDAXML");
            }
        }
        if (Validation.isNullOrEmpty(newBdDSalidaxml.getBlSalidaxml())) {
            throw new RequiredFieldException("BL_SALIDAXML");
        }
        if (Validation.isNullOrEmpty(newBdDSalidaxml.getIdSituacionxml())) {
            throw new RequiredFieldException("ID_SITUACIONXML");
        }
        if (Validation.isNullOrEmpty(newBdDSalidaxml.getFeAlta())) {
            throw new RequiredFieldException("FE_ALTA");
        }
   

        newBdDSalidaxml.setUsuariobd(Session.getCoUsuario());

        newBdDSalidaxml.setTstbd(new Date());

        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_SALIDAXML", newBdDSalidaxml.getIdSalidaxml());
        parametros.put("BL_SALIDAXML", newBdDSalidaxml.getBlSalidaxml());
        parametros.put("ID_DOCUMENTO", newBdDSalidaxml.getIdDocumento());
        parametros.put("ID_SITUACIONXML", newBdDSalidaxml.getIdSituacionxml());
        parametros.put("DS_RUTA", newBdDSalidaxml.getDsRuta());
        parametros.put("FE_ALTA", newBdDSalidaxml.getFeAlta());
        parametros.put("FE_DESACTIVO", newBdDSalidaxml.getFeDesactivo());
        parametros.put("USUARIOBD", newBdDSalidaxml.getUsuariobd());
        parametros.put("TSTBD", newBdDSalidaxml.getTstbd());


        return executeNativeQueryParametros(newBdDSalidaxml.getInsert(), parametros, em);
    }

    public int actualiza(BdDSalidaxml upBdDSalidaxml, EntityManager em) throws Exception {
        
        if (Validation.isNullOrEmpty(upBdDSalidaxml.getIdSalidaxml())) {
            throw new RequiredFieldException("ID_SALIDAXML");
        }
        if (Validation.isNullOrEmpty(upBdDSalidaxml.getBlSalidaxml())) {
            throw new RequiredFieldException("BL_SALIDAXML");
        }
        if (Validation.isNullOrEmpty(upBdDSalidaxml.getIdSituacionxml())) {
            throw new RequiredFieldException("ID_SITUACIONXML");
        }
        if (Validation.isNullOrEmpty(upBdDSalidaxml.getFeAlta())) {
            throw new RequiredFieldException("FE_ALTA");
        }


        upBdDSalidaxml.setUsuariobd(Session.getCoUsuario());

        upBdDSalidaxml.setTstbd(new Date());

        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_SALIDAXML", upBdDSalidaxml.getIdSalidaxml());
        parametros.put("BL_SALIDAXML", upBdDSalidaxml.getBlSalidaxml());
        parametros.put("ID_DOCUMENTO", upBdDSalidaxml.getIdDocumento());
        parametros.put("ID_SITUACIONXML", upBdDSalidaxml.getIdSituacionxml());
        parametros.put("DS_RUTA", upBdDSalidaxml.getDsRuta());
        parametros.put("FE_ALTA", upBdDSalidaxml.getFeAlta());
        parametros.put("FE_DESACTIVO", upBdDSalidaxml.getFeDesactivo());
        parametros.put("USUARIOBD", upBdDSalidaxml.getUsuariobd());
        parametros.put("TSTBD", upBdDSalidaxml.getTstbd());


        return executeNativeQueryParametros(upBdDSalidaxml.getUpdate(), parametros, em);
    }

    public int baja(BdDSalidaxml delBdDSalidaxml, EntityManager em) throws  Exception {

        if (Validation.isNullOrEmpty(delBdDSalidaxml.getIdSalidaxml())) {
            throw new RequiredFieldException("ID_SALIDAXML");
        }
        

        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_SALIDAXML", delBdDSalidaxml.getIdSalidaxml());


        return executeNativeQueryParametros(delBdDSalidaxml.getDelete(), parametros, em);
    }

    public Integer grabarSalidaXML(String xmlString, Integer idDocumento, EntityManager entityManager) throws Exception {
        BdTSituacionxml filtroBdTSituacionxml = new BdTSituacionxml();
        filtroBdTSituacionxml.setCoSituacionxml("NUEVO");
        filtroBdTSituacionxml.setFeAlta(new Date());
        filtroBdTSituacionxml.setFeDesactivo(new Date());
        StTSituacionxml stTSituacionxml = new StTSituacionxml();
        ArrayList<BdTSituacionxml> listaBdTSituacionxml = stTSituacionxml.filtro(filtroBdTSituacionxml, entityManager);
        if (listaBdTSituacionxml == null || listaBdTSituacionxml.isEmpty()) {
            throw new RegistryNotFoundException();
        }
        BdDSalidaxml bdDSalidaxml = new BdDSalidaxml();
        bdDSalidaxml.setBlSalidaxml(xmlString.getBytes(StandardCharsets.UTF_8));
        bdDSalidaxml.setIdDocumento(idDocumento);
        bdDSalidaxml.setFeAlta(new Date());
        bdDSalidaxml.setIdSituacionxml(listaBdTSituacionxml.get(0).getIdSituacionxml());
        this.alta(bdDSalidaxml, entityManager);

        return bdDSalidaxml.getIdSalidaxml();
    }

    public void actualizarSalidaXML(Integer idSalidaXML, Integer idDocumento, String coSituacionxml) throws Exception {
        BdTSituacionxml filtroBdTSituacionxml = new BdTSituacionxml();
        filtroBdTSituacionxml.setCoSituacionxml(coSituacionxml);
        filtroBdTSituacionxml.setFeAlta(new Date());
        filtroBdTSituacionxml.setFeDesactivo(new Date());
        StTSituacionxml stTSituacionxml = new StTSituacionxml();
        ArrayList<BdTSituacionxml> listaBdTSituacionxml = stTSituacionxml.filtro(filtroBdTSituacionxml, null);
        if (listaBdTSituacionxml == null || listaBdTSituacionxml.isEmpty()) {
            throw new RegistryNotFoundException();
        }
        
        // INICIO TRANSACCION
        try (EntityManager entityManager = AppInit.getEntityManager()) {
            entityManager.getTransaction().begin();
            try {
                BdDSalidaxml bdDSalidaxml = this.item(idSalidaXML, entityManager);
                bdDSalidaxml.setIdDocumento(idDocumento);
                this.actualiza(bdDSalidaxml, entityManager);

                BdAHistsalxml bdAHistsalxml = new BdAHistsalxml();
                bdAHistsalxml.setIdSalidaxml(bdDSalidaxml.getIdSalidaxml());
                bdAHistsalxml.setIdSituacionxml(bdDSalidaxml.getIdSituacionxml());
                bdAHistsalxml.setFeAlta(new Date());
                StAHistsalxml stAHistsalxml = new StAHistsalxml();
                stAHistsalxml.alta(bdAHistsalxml, entityManager);

                bdDSalidaxml.setIdSituacionxml(listaBdTSituacionxml.get(0).getIdSituacionxml());
                this.actualiza(bdDSalidaxml, entityManager);

                entityManager.getTransaction().commit();
            }
            catch (Exception ex) {
                entityManager.getTransaction().rollback();
                throw ex;
            }
        }
        // FIN TRANSACCION
    }

}

