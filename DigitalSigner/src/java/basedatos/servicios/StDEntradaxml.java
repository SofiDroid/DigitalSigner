package basedatos.servicios;

import basedatos.Mapeador;
import basedatos.StBase;
import basedatos.tablas.BdAHistentxml;
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
import basedatos.tablas.BdDEntradaxml;
import basedatos.tablas.BdTSituacionxml;
import excepciones.RegistryNotFoundException;
import java.nio.charset.StandardCharsets;

/**
 *
 * @author ihuegal
 */
public class StDEntradaxml extends StBase {
    
    public StDEntradaxml() {
        //NADA
    }
    
    public ArrayList<BdDEntradaxml> filtro(BdDEntradaxml filtroBdDEntradaxml, EntityManager em) throws Exception {
        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_ENTRADAXML", filtroBdDEntradaxml.getIdEntradaxml());
        parametros.put("ID_DOCUMENTO", filtroBdDEntradaxml.getIdDocumento());
        parametros.put("ID_SITUACIONXML", filtroBdDEntradaxml.getIdSituacionxml());
        parametros.put("DS_RUTA", filtroBdDEntradaxml.getDsRuta());
        parametros.put("FE_ALTA", filtroBdDEntradaxml.getFeAlta());
        parametros.put("FE_DESACTIVO", filtroBdDEntradaxml.getFeDesactivo());
        parametros.put("USUARIOBD", filtroBdDEntradaxml.getUsuariobd());
        parametros.put("TSTBD", filtroBdDEntradaxml.getTstbd());


        ArrayList<LinkedHashMap<String,Object>> lista = executeNativeQueryListParametros(filtroBdDEntradaxml.getSelectFiltro(), parametros, em).getResultListMapped();
        if (lista != null && !lista.isEmpty()) {
            return Mapeador.mapea(lista, BdDEntradaxml.class);
        }
        return null;
    }
    
    public BdDEntradaxml item(Integer idEntradaxml, EntityManager em) throws Exception {
        BdDEntradaxml filtroBdDEntradaxml = new BdDEntradaxml();
        filtroBdDEntradaxml.setIdEntradaxml(idEntradaxml);

        
        ArrayList<BdDEntradaxml> listaBdDEntradaxml = filtro(filtroBdDEntradaxml, em);
        if (listaBdDEntradaxml != null && !listaBdDEntradaxml.isEmpty()) {
            return listaBdDEntradaxml.get(0);
        }
        return null;
    }
    
    public int alta(BdDEntradaxml newBdDEntradaxml, EntityManager em) throws Exception {

        if(AppInit.TIPO_BASEDATOS == BaseDatos.ORACLE) {
            if (Validation.isNullOrEmpty(newBdDEntradaxml.getIdEntradaxml())) {
                throw new RequiredFieldException("ID_ENTRADAXML");
            }
        }
        if (Validation.isNullOrEmpty(newBdDEntradaxml.getIdSituacionxml())) {
            throw new RequiredFieldException("ID_SITUACIONXML");
        }
        if (Validation.isNullOrEmpty(newBdDEntradaxml.getFeAlta())) {
            throw new RequiredFieldException("FE_ALTA");
        }
   

        newBdDEntradaxml.setUsuariobd(Session.getCoUsuario());

        newBdDEntradaxml.setTstbd(new Date());

        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_ENTRADAXML", newBdDEntradaxml.getIdEntradaxml());
        parametros.put("BL_ENTRADAXML", newBdDEntradaxml.getBlEntradaxml(em));
        parametros.put("ID_DOCUMENTO", newBdDEntradaxml.getIdDocumento());
        parametros.put("ID_SITUACIONXML", newBdDEntradaxml.getIdSituacionxml());
        parametros.put("DS_RUTA", newBdDEntradaxml.getDsRuta());
        parametros.put("FE_ALTA", newBdDEntradaxml.getFeAlta());
        parametros.put("FE_DESACTIVO", newBdDEntradaxml.getFeDesactivo());
        parametros.put("USUARIOBD", newBdDEntradaxml.getUsuariobd());
        parametros.put("TSTBD", newBdDEntradaxml.getTstbd());

        newBdDEntradaxml.setIdEntradaxml(executeNativeQueryParametros(newBdDEntradaxml.getInsert(), parametros, em));

        return newBdDEntradaxml.getIdEntradaxml();
    }

    public int actualiza(BdDEntradaxml upBdDEntradaxml, EntityManager em) throws Exception {
        
        if (Validation.isNullOrEmpty(upBdDEntradaxml.getIdEntradaxml())) {
            throw new RequiredFieldException("ID_ENTRADAXML");
        }
        if (Validation.isNullOrEmpty(upBdDEntradaxml.getIdSituacionxml())) {
            throw new RequiredFieldException("ID_SITUACIONXML");
        }
        if (Validation.isNullOrEmpty(upBdDEntradaxml.getFeAlta())) {
            throw new RequiredFieldException("FE_ALTA");
        }


        upBdDEntradaxml.setUsuariobd(Session.getCoUsuario());

        upBdDEntradaxml.setTstbd(new Date());

        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_ENTRADAXML", upBdDEntradaxml.getIdEntradaxml());
        parametros.put("BL_ENTRADAXML", upBdDEntradaxml.getBlEntradaxml(em));
        parametros.put("ID_DOCUMENTO", upBdDEntradaxml.getIdDocumento());
        parametros.put("ID_SITUACIONXML", upBdDEntradaxml.getIdSituacionxml());
        parametros.put("DS_RUTA", upBdDEntradaxml.getDsRuta());
        parametros.put("FE_ALTA", upBdDEntradaxml.getFeAlta());
        parametros.put("FE_DESACTIVO", upBdDEntradaxml.getFeDesactivo());
        parametros.put("USUARIOBD", upBdDEntradaxml.getUsuariobd());
        parametros.put("TSTBD", upBdDEntradaxml.getTstbd());


        return executeNativeQueryParametros(upBdDEntradaxml.getUpdate(), parametros, em);
    }

    public int baja(BdDEntradaxml delBdDEntradaxml, EntityManager em) throws  Exception {

        if (Validation.isNullOrEmpty(delBdDEntradaxml.getIdEntradaxml())) {
            throw new RequiredFieldException("ID_ENTRADAXML");
        }
        

        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_ENTRADAXML", delBdDEntradaxml.getIdEntradaxml());


        return executeNativeQueryParametros(delBdDEntradaxml.getDelete(), parametros, em);
    }

    public Integer grabarEntradaXML(String xmlString) throws Exception {
        BdTSituacionxml filtroBdTSituacionxml = new BdTSituacionxml();
        filtroBdTSituacionxml.setCoSituacionxml("NUEVO");
        filtroBdTSituacionxml.setFeAlta(new Date());
        filtroBdTSituacionxml.setFeDesactivo(new Date());
        StTSituacionxml stTSituacionxml = new StTSituacionxml();
        ArrayList<BdTSituacionxml> listaBdTSituacionxml = stTSituacionxml.filtro(filtroBdTSituacionxml, null);
        if (listaBdTSituacionxml == null || listaBdTSituacionxml.isEmpty()) {
            throw new RegistryNotFoundException();
        }
        
        BdDEntradaxml bdDEntradaxml = new BdDEntradaxml();
        bdDEntradaxml.setBlEntradaxml(xmlString.getBytes(StandardCharsets.UTF_8));
        bdDEntradaxml.setFeAlta(new Date());
        bdDEntradaxml.setIdSituacionxml(listaBdTSituacionxml.get(0).getIdSituacionxml());
        StDEntradaxml stDEntradaxml = new StDEntradaxml();
        stDEntradaxml.alta(bdDEntradaxml, null);

        return bdDEntradaxml.getIdEntradaxml();
    }

    public void actualizarEntradaXML(Integer idEntradaXML, Integer idDocumento, String coSituacionxml) throws Exception {
        // INICIO TRANSACCION
        try (EntityManager entityManager = AppInit.getEntityManager()) {
            entityManager.getTransaction().begin();
            try {
                BdTSituacionxml filtroBdTSituacionxml = new BdTSituacionxml();
                filtroBdTSituacionxml.setCoSituacionxml(coSituacionxml);
                filtroBdTSituacionxml.setFeAlta(new Date());
                filtroBdTSituacionxml.setFeDesactivo(new Date());
                StTSituacionxml stTSituacionxml = new StTSituacionxml();
                ArrayList<BdTSituacionxml> listaBdTSituacionxml = stTSituacionxml.filtro(filtroBdTSituacionxml, entityManager);
                if (listaBdTSituacionxml == null || listaBdTSituacionxml.isEmpty()) {
                    throw new RegistryNotFoundException();
                }

                StDEntradaxml stDEntradaxml = new StDEntradaxml();
                BdDEntradaxml bdDEntradaxml = stDEntradaxml.item(idEntradaXML, entityManager);
                bdDEntradaxml.setIdDocumento(idDocumento);
                stDEntradaxml.actualiza(bdDEntradaxml, entityManager);

                BdAHistentxml bdAHistentxml = new BdAHistentxml();
                bdAHistentxml.setIdEntradaxml(bdDEntradaxml.getIdEntradaxml());
                bdAHistentxml.setIdSituacionxml(bdDEntradaxml.getIdSituacionxml());
                bdAHistentxml.setFeAlta(new Date());
                StAHistentxml stAHistentxml = new StAHistentxml();
                stAHistentxml.alta(bdAHistentxml, entityManager);

                bdDEntradaxml.setIdSituacionxml(listaBdTSituacionxml.get(0).getIdSituacionxml());
                stDEntradaxml.actualiza(bdDEntradaxml, entityManager);
                
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

