package basedatos.servicios;

import basedatos.Mapeador;
import basedatos.StBase;
import excepciones.RequiredFieldException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import tomcat.persistence.EntityManager;
import utilidades.Validation;
import basedatos.tablas.BdAFirmante;
import seguridad.usuarios.DatosUsuario;

/**
 *
 * @author ihuegal
 */
public class StAFirmante extends StBase {
    
    private DatosUsuario datosUsuario = null;
    
    public StAFirmante(DatosUsuario datosUsuario) {
        this.datosUsuario = datosUsuario;
    }
    
    public ArrayList<BdAFirmante> filtro(BdAFirmante filtroBdAFirmante, EntityManager em) throws Exception {
        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("CO_NIF", filtroBdAFirmante.getCoNif());
        parametros.put("ID_DOCFIRMA", filtroBdAFirmante.getIdDocfirma());


        ArrayList<LinkedHashMap<String,Object>> lista = executeNativeQueryListParametros(filtroBdAFirmante.getSelectFiltro(), parametros, em).getResultListMapped();
        if (lista != null && !lista.isEmpty()) {
            return Mapeador.mapea(lista, BdAFirmante.class);
        }
        return null;
    }
    
    public BdAFirmante item(String coNif, Integer idDocfirma, EntityManager em) throws Exception {
        BdAFirmante filtroBdAFirmante = new BdAFirmante();
        filtroBdAFirmante.setCoNif(coNif);
        filtroBdAFirmante.setIdDocfirma(idDocfirma);

        
        ArrayList<BdAFirmante> listaBdAFirmante = filtro(filtroBdAFirmante, em);
        if (listaBdAFirmante != null && !listaBdAFirmante.isEmpty()) {
            return listaBdAFirmante.get(0);
        }
        return null;
    }
    
    public int alta(BdAFirmante newBdAFirmante, EntityManager em) throws Exception {

        if (Validation.isNullOrEmpty(newBdAFirmante.getCoNif())) {
            throw new RequiredFieldException("CO_NIF");
        }
        if (Validation.isNullOrEmpty(newBdAFirmante.getIdDocfirma())) {
            throw new RequiredFieldException("ID_DOCFIRMA");
        }

        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("CO_NIF", newBdAFirmante.getCoNif());
        parametros.put("ID_DOCFIRMA", newBdAFirmante.getIdDocfirma());


        return executeNativeQueryParametros(newBdAFirmante.getInsert(), parametros, em);
    }

    public int baja(BdAFirmante delBdAFirmante, EntityManager em) throws  Exception {

        if (Validation.isNullOrEmpty(delBdAFirmante.getCoNif())) {
            throw new RequiredFieldException("CO_NIF");
        }
        if (Validation.isNullOrEmpty(delBdAFirmante.getIdDocfirma())) {
            throw new RequiredFieldException("ID_DOCFIRMA");
        }
        

        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("CO_NIF", delBdAFirmante.getCoNif());
        parametros.put("ID_DOCFIRMA", delBdAFirmante.getIdDocfirma());


        return executeNativeQueryParametros(delBdAFirmante.getDelete(), parametros, em);
    }
}

