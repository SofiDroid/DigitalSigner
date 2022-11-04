package basedatos.servicios;

import basedatos.Mapeador;
import basedatos.StBase;
import excepciones.RequiredFieldException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import tomcat.persistence.EntityManager;
import utilidades.Validation;
import basedatos.tablas.BdDFirmante;
import seguridad.usuarios.DatosUsuario;

/**
 *
 * @author ihuegal
 */
public class StDFirmante extends StBase {
    
    private DatosUsuario datosUsuario = null;
    
    public StDFirmante(DatosUsuario datosUsuario) {
        this.datosUsuario = datosUsuario;
    }
    
    public ArrayList<BdDFirmante> filtro(BdDFirmante filtroBdDFirmante, EntityManager em) throws Exception {
        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("CO_NIF", filtroBdDFirmante.getCoNif());
        parametros.put("ID_DOCFIRMA", filtroBdDFirmante.getIdDocfirma());


        ArrayList<LinkedHashMap<String,Object>> lista = executeNativeQueryListParametros(filtroBdDFirmante.getSelectFiltro(), parametros, em).getResultListMapped();
        if (lista != null && !lista.isEmpty()) {
            return Mapeador.mapea(lista, BdDFirmante.class);
        }
        return null;
    }
    
    public BdDFirmante item(String coNif, Integer idDocfirma, EntityManager em) throws Exception {
        BdDFirmante filtroBdDFirmante = new BdDFirmante();
        filtroBdDFirmante.setCoNif(coNif);
        filtroBdDFirmante.setIdDocfirma(idDocfirma);

        
        ArrayList<BdDFirmante> listaBdDFirmante = filtro(filtroBdDFirmante, em);
        if (listaBdDFirmante != null && !listaBdDFirmante.isEmpty()) {
            return listaBdDFirmante.get(0);
        }
        return null;
    }
    
    public int alta(BdDFirmante newBdDFirmante, EntityManager em) throws Exception {

        if (Validation.isNullOrEmpty(newBdDFirmante.getCoNif())) {
            throw new RequiredFieldException("CO_NIF");
        }
        if (Validation.isNullOrEmpty(newBdDFirmante.getIdDocfirma())) {
            throw new RequiredFieldException("ID_DOCFIRMA");
        }

        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("CO_NIF", newBdDFirmante.getCoNif());
        parametros.put("ID_DOCFIRMA", newBdDFirmante.getIdDocfirma());


        return executeNativeQueryParametros(newBdDFirmante.getInsert(), parametros, em);
    }

    public int baja(BdDFirmante delBdDFirmante, EntityManager em) throws  Exception {

        if (Validation.isNullOrEmpty(delBdDFirmante.getCoNif())) {
            throw new RequiredFieldException("CO_NIF");
        }
        if (Validation.isNullOrEmpty(delBdDFirmante.getIdDocfirma())) {
            throw new RequiredFieldException("ID_DOCFIRMA");
        }
        

        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("CO_NIF", delBdDFirmante.getCoNif());
        parametros.put("ID_DOCFIRMA", delBdDFirmante.getIdDocfirma());


        return executeNativeQueryParametros(delBdDFirmante.getDelete(), parametros, em);
    }
}

