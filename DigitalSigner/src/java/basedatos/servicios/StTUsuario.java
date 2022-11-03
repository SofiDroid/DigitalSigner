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
import basedatos.tablas.BdTUsuario;
import seguridad.usuarios.DatosUsuario;

/**
 *
 * @author ihuegal
 */
public class StTUsuario extends StBase {
    
    private DatosUsuario datosUsuario = null;
    
    public StTUsuario(DatosUsuario datosUsuario) {
        this.datosUsuario = datosUsuario;
    }
    
    public ArrayList<BdTUsuario> filtro(BdTUsuario filtroBdTUsuario, EntityManager em) throws Exception {
        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_USUARIO", filtroBdTUsuario.getIdUsuario());
        parametros.put("CO_NIF", filtroBdTUsuario.getCoNIF());
        parametros.put("CO_USUARIO", filtroBdTUsuario.getCoUsuario());
        parametros.put("CO_PASSWORD", filtroBdTUsuario.getCoPassword());
        parametros.put("DS_NOMBRE", filtroBdTUsuario.getDsNombre());
        parametros.put("DS_APELLIDO1", filtroBdTUsuario.getDsApellido1());
        parametros.put("DS_APELLIDO2", filtroBdTUsuario.getDsApellido2());
        parametros.put("EN_INTENTOS", filtroBdTUsuario.getEnIntentos());
        parametros.put("EN_INTENTOSMAX", filtroBdTUsuario.getEnIntentosmax());
        parametros.put("BO_ADMIN", filtroBdTUsuario.getBoAdmin());
        parametros.put("FE_ALTA", filtroBdTUsuario.getFeAlta());
        parametros.put("FE_DESACTIVO", filtroBdTUsuario.getFeDesactivo());
        parametros.put("USUARIOBD", filtroBdTUsuario.getUsuariobd());
        parametros.put("TSTBD", filtroBdTUsuario.getTstbd());


        ArrayList<LinkedHashMap<String,Object>> lista = executeNativeQueryListParametros(filtroBdTUsuario.getSelectFiltro(), parametros, em).getResultListMapped();
        if (lista != null && !lista.isEmpty()) {
            return Mapeador.mapea(lista, BdTUsuario.class);
        }
        return null;
    }
    
    public BdTUsuario item(Integer idUsuario, boolean boSoloActivos, EntityManager em) throws Exception {
        BdTUsuario filtroBdTUsuario = new BdTUsuario();
        filtroBdTUsuario.setIdUsuario(idUsuario);
        if (boSoloActivos) {
            filtroBdTUsuario.setFeAlta(new Date());
            filtroBdTUsuario.setFeDesactivo(new Date());
        }
        
        ArrayList<BdTUsuario> listaBdTUsuario = filtro(filtroBdTUsuario, em);
        if (listaBdTUsuario != null && !listaBdTUsuario.isEmpty()) {
            return listaBdTUsuario.get(0);
        }
        return null;
    }
    
    public int alta(BdTUsuario newBdTUsuario, EntityManager em) throws Exception {

        if(AppInit.TIPO_BASEDATOS == BaseDatos.ORACLE) {
            if (Validation.isNullOrEmpty(newBdTUsuario.getIdUsuario())) {
                throw new RequiredFieldException("ID_USUARIO");
            }
        }
        if (Validation.isNullOrEmpty(newBdTUsuario.getCoNIF())) {
            throw new RequiredFieldException("CO_NIF");
        }
        if (Validation.isNullOrEmpty(newBdTUsuario.getCoUsuario())) {
            throw new RequiredFieldException("CO_USUARIO");
        }
        if (Validation.isNullOrEmpty(newBdTUsuario.getCoPassword())) {
            throw new RequiredFieldException("CO_PASSWORD");
        }
        if (Validation.isNullOrEmpty(newBdTUsuario.getDsNombre())) {
            throw new RequiredFieldException("DS_NOMBRE");
        }
        if (Validation.isNullOrEmpty(newBdTUsuario.getDsApellido1())) {
            throw new RequiredFieldException("DS_APELLIDO1");
        }
        if (Validation.isNullOrEmpty(newBdTUsuario.getEnIntentos())) {
            throw new RequiredFieldException("EN_INTENTOS");
        }
        if (Validation.isNullOrEmpty(newBdTUsuario.getEnIntentosmax())) {
            throw new RequiredFieldException("EN_INTENTOSMAX");
        }
        if (Validation.isNullOrEmpty(newBdTUsuario.getBoAdmin())) {
            throw new RequiredFieldException("BO_ADMIN");
        }
        if (Validation.isNullOrEmpty(newBdTUsuario.getFeAlta())) {
            throw new RequiredFieldException("FE_ALTA");
        }
   

        newBdTUsuario.setUsuariobd(datosUsuario.getBdTUsuario().getCoUsuario());

        newBdTUsuario.setTstbd(new Date());

        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_USUARIO", newBdTUsuario.getIdUsuario());
        parametros.put("CO_NIF", newBdTUsuario.getCoNIF());
        parametros.put("CO_USUARIO", newBdTUsuario.getCoUsuario());
        parametros.put("CO_PASSWORD", newBdTUsuario.getCoPassword());
        parametros.put("DS_NOMBRE", newBdTUsuario.getDsNombre());
        parametros.put("DS_APELLIDO1", newBdTUsuario.getDsApellido1());
        parametros.put("DS_APELLIDO2", newBdTUsuario.getDsApellido2());
        parametros.put("EN_INTENTOS", newBdTUsuario.getEnIntentos());
        parametros.put("EN_INTENTOSMAX", newBdTUsuario.getEnIntentosmax());
        parametros.put("BO_ADMIN", newBdTUsuario.getBoAdmin());
        parametros.put("FE_ALTA", newBdTUsuario.getFeAlta());
        parametros.put("FE_DESACTIVO", newBdTUsuario.getFeDesactivo());
        parametros.put("USUARIOBD", newBdTUsuario.getUsuariobd());
        parametros.put("TSTBD", newBdTUsuario.getTstbd());


        int result = executeNativeQueryParametros(newBdTUsuario.getInsert(), parametros, em);
        newBdTUsuario.setIdUsuario(result);
        
        return result;
    }

    public int actualiza(BdTUsuario upBdTUsuario, EntityManager em) throws Exception {
        
        if (Validation.isNullOrEmpty(upBdTUsuario.getIdUsuario())) {
            throw new RequiredFieldException("ID_USUARIO");
        }
        if (Validation.isNullOrEmpty(upBdTUsuario.getCoNIF())) {
            throw new RequiredFieldException("CO_NIF");
        }
        if (Validation.isNullOrEmpty(upBdTUsuario.getCoUsuario())) {
            throw new RequiredFieldException("CO_USUARIO");
        }
        if (Validation.isNullOrEmpty(upBdTUsuario.getCoPassword())) {
            throw new RequiredFieldException("CO_PASSWORD");
        }
        if (Validation.isNullOrEmpty(upBdTUsuario.getDsNombre())) {
            throw new RequiredFieldException("DS_NOMBRE");
        }
        if (Validation.isNullOrEmpty(upBdTUsuario.getDsApellido1())) {
            throw new RequiredFieldException("DS_APELLIDO1");
        }
        if (Validation.isNullOrEmpty(upBdTUsuario.getEnIntentos())) {
            throw new RequiredFieldException("EN_INTENTOS");
        }
        if (Validation.isNullOrEmpty(upBdTUsuario.getEnIntentosmax())) {
            throw new RequiredFieldException("EN_INTENTOSMAX");
        }
        if (Validation.isNullOrEmpty(upBdTUsuario.getBoAdmin())) {
            throw new RequiredFieldException("BO_ADMIN");
        }
        if (Validation.isNullOrEmpty(upBdTUsuario.getFeAlta())) {
            throw new RequiredFieldException("FE_ALTA");
        }


        upBdTUsuario.setUsuariobd(datosUsuario.getBdTUsuario().getCoUsuario());

        upBdTUsuario.setTstbd(new Date());

        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_USUARIO", upBdTUsuario.getIdUsuario());
        parametros.put("CO_NIF", upBdTUsuario.getCoNIF());
        parametros.put("CO_USUARIO", upBdTUsuario.getCoUsuario());
        parametros.put("CO_PASSWORD", upBdTUsuario.getCoPassword());
        parametros.put("DS_NOMBRE", upBdTUsuario.getDsNombre());
        parametros.put("DS_APELLIDO1", upBdTUsuario.getDsApellido1());
        parametros.put("DS_APELLIDO2", upBdTUsuario.getDsApellido2());
        parametros.put("EN_INTENTOS", upBdTUsuario.getEnIntentos());
        parametros.put("EN_INTENTOSMAX", upBdTUsuario.getEnIntentosmax());
        parametros.put("BO_ADMIN", upBdTUsuario.getBoAdmin());
        parametros.put("FE_ALTA", upBdTUsuario.getFeAlta());
        parametros.put("FE_DESACTIVO", upBdTUsuario.getFeDesactivo());
        parametros.put("USUARIOBD", upBdTUsuario.getUsuariobd());
        parametros.put("TSTBD", upBdTUsuario.getTstbd());


        return executeNativeQueryParametros(upBdTUsuario.getUpdate(), parametros, em);
    }

    public int baja(BdTUsuario delBdTUsuario, EntityManager em) throws  Exception {

        if (Validation.isNullOrEmpty(delBdTUsuario.getIdUsuario())) {
            throw new RequiredFieldException("ID_USUARIO");
        }
        

        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_USUARIO", delBdTUsuario.getIdUsuario());


        return executeNativeQueryParametros(delBdTUsuario.getDelete(), parametros, em);
    }
}

