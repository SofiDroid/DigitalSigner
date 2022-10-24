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
import basedatos.tablas.BdTOpcionmenu;
import seguridad.usuarios.DatosUsuario;

/**
 *
 * @author ihuegal
 */
public class StTOpcionmenu extends StBase {
    
    private DatosUsuario datosUsuario = null;
    
    public StTOpcionmenu(DatosUsuario datosUsuario) {
        this.datosUsuario = datosUsuario;
    }
    
    public ArrayList<BdTOpcionmenu> filtro(BdTOpcionmenu filtroBdTOpcionmenu, EntityManager em) throws Exception {
        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_OPCIONMENU", filtroBdTOpcionmenu.getIdOpcionmenu());
        parametros.put("EN_ORDEN", filtroBdTOpcionmenu.getEnOrden());
        parametros.put("CO_OPCIONMENU", filtroBdTOpcionmenu.getCoOpcionmenu());
        parametros.put("DS_OPCIONMENU", filtroBdTOpcionmenu.getDsOpcionmenu());
        parametros.put("DS_TITULO", filtroBdTOpcionmenu.getDsTitulo());
        parametros.put("DS_TOOLTIP", filtroBdTOpcionmenu.getDsTooltip());
        parametros.put("DS_RUTA", filtroBdTOpcionmenu.getDsRuta());
        parametros.put("FE_ALTA", filtroBdTOpcionmenu.getFeAlta());
        parametros.put("FE_DESACTIVO", filtroBdTOpcionmenu.getFeDesactivo());
        parametros.put("USUARIOBD", filtroBdTOpcionmenu.getUsuariobd());
        parametros.put("TSTBD", filtroBdTOpcionmenu.getTstbd());
        parametros.put("ID_OPCIONMENUPADRE", filtroBdTOpcionmenu.getIdOpcionmenupadre());


        ArrayList<LinkedHashMap<String,Object>> lista = executeNativeQueryListParametros(filtroBdTOpcionmenu.getSelectFiltro(), parametros, em).getResultListMapped();
        if (lista != null && !lista.isEmpty()) {
            return Mapeador.mapea(lista, BdTOpcionmenu.class);
        }
        return null;
    }
    
    public BdTOpcionmenu item(Integer idOpcionmenu, EntityManager em) throws Exception {
        BdTOpcionmenu filtroBdTOpcionmenu = new BdTOpcionmenu();
        filtroBdTOpcionmenu.setIdOpcionmenu(idOpcionmenu);

        
        ArrayList<BdTOpcionmenu> listaBdTOpcionmenu = filtro(filtroBdTOpcionmenu, em);
        if (listaBdTOpcionmenu != null && !listaBdTOpcionmenu.isEmpty()) {
            return listaBdTOpcionmenu.get(0);
        }
        return null;
    }
    
    public int alta(BdTOpcionmenu newBdTOpcionmenu, EntityManager em) throws Exception {

        if(AppInit.TIPO_BASEDATOS == BaseDatos.ORACLE) {
            if (Validation.isNullOrEmpty(newBdTOpcionmenu.getIdOpcionmenu())) {
                throw new RequiredFieldException("ID_OPCIONMENU");
            }
        }
        if (Validation.isNullOrEmpty(newBdTOpcionmenu.getCoOpcionmenu())) {
            throw new RequiredFieldException("CO_OPCIONMENU");
        }
        if (Validation.isNullOrEmpty(newBdTOpcionmenu.getDsOpcionmenu())) {
            throw new RequiredFieldException("DS_OPCIONMENU");
        }
        if (Validation.isNullOrEmpty(newBdTOpcionmenu.getDsTitulo())) {
            throw new RequiredFieldException("DS_TITULO");
        }
        if (Validation.isNullOrEmpty(newBdTOpcionmenu.getFeAlta())) {
            throw new RequiredFieldException("FE_ALTA");
        }
   

        newBdTOpcionmenu.setUsuariobd(datosUsuario.getBdTUsuario().getCoUsuario());

        newBdTOpcionmenu.setTstbd(new Date());

        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_OPCIONMENU", newBdTOpcionmenu.getIdOpcionmenu());
        parametros.put("EN_ORDEN", newBdTOpcionmenu.getEnOrden());
        parametros.put("CO_OPCIONMENU", newBdTOpcionmenu.getCoOpcionmenu());
        parametros.put("DS_OPCIONMENU", newBdTOpcionmenu.getDsOpcionmenu());
        parametros.put("DS_TITULO", newBdTOpcionmenu.getDsTitulo());
        parametros.put("DS_TOOLTIP", newBdTOpcionmenu.getDsTooltip());
        parametros.put("DS_RUTA", newBdTOpcionmenu.getDsRuta());
        parametros.put("FE_ALTA", newBdTOpcionmenu.getFeAlta());
        parametros.put("FE_DESACTIVO", newBdTOpcionmenu.getFeDesactivo());
        parametros.put("USUARIOBD", newBdTOpcionmenu.getUsuariobd());
        parametros.put("TSTBD", newBdTOpcionmenu.getTstbd());
        parametros.put("ID_OPCIONMENUPADRE", newBdTOpcionmenu.getIdOpcionmenupadre());


        return executeNativeQueryParametros(newBdTOpcionmenu.getInsert(), parametros, em);
    }

    public int actualiza(BdTOpcionmenu upBdTOpcionmenu, EntityManager em) throws Exception {
        
        if (Validation.isNullOrEmpty(upBdTOpcionmenu.getIdOpcionmenu())) {
            throw new RequiredFieldException("ID_OPCIONMENU");
        }
        if (Validation.isNullOrEmpty(upBdTOpcionmenu.getCoOpcionmenu())) {
            throw new RequiredFieldException("CO_OPCIONMENU");
        }
        if (Validation.isNullOrEmpty(upBdTOpcionmenu.getDsOpcionmenu())) {
            throw new RequiredFieldException("DS_OPCIONMENU");
        }
        if (Validation.isNullOrEmpty(upBdTOpcionmenu.getDsTitulo())) {
            throw new RequiredFieldException("DS_TITULO");
        }
        if (Validation.isNullOrEmpty(upBdTOpcionmenu.getFeAlta())) {
            throw new RequiredFieldException("FE_ALTA");
        }


        upBdTOpcionmenu.setUsuariobd(datosUsuario.getBdTUsuario().getCoUsuario());

        upBdTOpcionmenu.setTstbd(new Date());

        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_OPCIONMENU", upBdTOpcionmenu.getIdOpcionmenu());
        parametros.put("EN_ORDEN", upBdTOpcionmenu.getEnOrden());
        parametros.put("CO_OPCIONMENU", upBdTOpcionmenu.getCoOpcionmenu());
        parametros.put("DS_OPCIONMENU", upBdTOpcionmenu.getDsOpcionmenu());
        parametros.put("DS_TITULO", upBdTOpcionmenu.getDsTitulo());
        parametros.put("DS_TOOLTIP", upBdTOpcionmenu.getDsTooltip());
        parametros.put("DS_RUTA", upBdTOpcionmenu.getDsRuta());
        parametros.put("FE_ALTA", upBdTOpcionmenu.getFeAlta());
        parametros.put("FE_DESACTIVO", upBdTOpcionmenu.getFeDesactivo());
        parametros.put("USUARIOBD", upBdTOpcionmenu.getUsuariobd());
        parametros.put("TSTBD", upBdTOpcionmenu.getTstbd());
        parametros.put("ID_OPCIONMENUPADRE", upBdTOpcionmenu.getIdOpcionmenupadre());


        return executeNativeQueryParametros(upBdTOpcionmenu.getUpdate(), parametros, em);
    }

    public int baja(BdTOpcionmenu delBdTOpcionmenu, EntityManager em) throws  Exception {

        if (Validation.isNullOrEmpty(delBdTOpcionmenu.getIdOpcionmenu())) {
            throw new RequiredFieldException("ID_OPCIONMENU");
        }
        

        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_OPCIONMENU", delBdTOpcionmenu.getIdOpcionmenu());


        return executeNativeQueryParametros(delBdTOpcionmenu.getDelete(), parametros, em);
    }
}

