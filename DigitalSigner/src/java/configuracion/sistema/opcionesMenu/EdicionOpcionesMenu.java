package configuracion.sistema.opcionesMenu;

import basedatos.servicios.StTOpcionmenu;
import basedatos.tablas.BdTOpcionmenu;
import excepciones.FormModeException;
import excepciones.RegistryNotFoundException;
import excepciones.RequiredFieldException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import javax.el.ELContext;
import javax.faces.context.FacesContext;
import utilidades.CampoWebCodigo;
import utilidades.CampoWebDescripcion;
import utilidades.CampoWebFecha;
import utilidades.CampoWebLupa;
import utilidades.CampoWebNumero;
import utilidades.Mensajes;
import utilidades.ModoFormulario;
import utilidades.Msg;
import utilidades.Session;
import utilidades.Validation;

/**
 *
 * @author ihuegal
 */
public class EdicionOpcionesMenu implements Serializable {
    private Object parent = null;
    private String paginaRetorno = null;
    
    private CampoWebCodigo cCoOpcionMenu = null;
    private CampoWebDescripcion cDsOpcionMenu = null;
    private CampoWebLupa cOpcionMenuPadre = null;
    private CampoWebNumero cEnOrden = null;
    private CampoWebDescripcion cDsTitulo = null;
    private CampoWebDescripcion cDsTooltip = null;
    private CampoWebDescripcion cDsRuta = null;
    private CampoWebFecha cFeAlta = null;
    private CampoWebFecha cFeDesactivo = null;
    
    BdTOpcionmenu bdTOpcionmenu = null;

    private ModoFormulario modoFormulario = ModoFormulario.CONSULTA;
    
    public EdicionOpcionesMenu(Object parent) {
        init(parent, null);
    }
    
    public EdicionOpcionesMenu(Object parent, Integer idOpcionMenu) {
        init(parent, idOpcionMenu);
    }
    
    private void init(Object parent, Integer idOpcionMenu) {
        try {
            this.parent = parent;
    
            this.cCoOpcionMenu = new CampoWebCodigo();
            this.cCoOpcionMenu.setLabel(Msg.getString("lbl_EdicionOpcionesMenu_CoOpcionMenu"));
            this.cCoOpcionMenu.setWidthLabel("100px");
            this.cCoOpcionMenu.setRequired(true);
            
            this.cDsOpcionMenu = new CampoWebDescripcion();
            this.cDsOpcionMenu.setLabel(Msg.getString("lbl_EdicionOpcionesMenu_DsOpcionMenu"));
            this.cDsOpcionMenu.setWidthLabel("100px");
            this.cDsOpcionMenu.setRequired(true);
            
            this.cOpcionMenuPadre = new CampoWebLupa();
            this.cOpcionMenuPadre.setLabel(Msg.getString("lbl_EdicionOpcionesMenu_OpcionMenuPadre"));
            this.cOpcionMenuPadre.setWidthLabel("100px");
            String sql = "SELECT ID_OPCIONMENU, CO_OPCIONMENU + ' - ' + DS_OPCIONMENU as 'Menú' FROM BD_T_OPCIONMENU";
            if (idOpcionMenu != null) {
                sql += " WHERE ID_OPCIONMENU != " + idOpcionMenu;
            }
            this.cOpcionMenuPadre.setConsulta(sql);
            this.cOpcionMenuPadre.setColumnaID("ID_OPCIONMENU");
            this.cOpcionMenuPadre.setColumnaLabel("Menú");

            this.cEnOrden = new CampoWebNumero();
            this.cEnOrden.setLabel(Msg.getString("lbl_EdicionOpcionesMenu_DsEnOrden"));
            this.cEnOrden.setWidthLabel("100px");
            this.cEnOrden.setMinValue(0);
            this.cEnOrden.setNumDecimales(0);
            this.cEnOrden.setSize(2);
            
            this.cDsTitulo = new CampoWebDescripcion();
            this.cDsTitulo.setLabel(Msg.getString("lbl_EdicionOpcionesMenu_DsTitulo"));
            this.cDsTitulo.setWidthLabel("100px");
            this.cDsTitulo.setRequired(true);
            
            this.cDsTooltip = new CampoWebDescripcion();
            this.cDsTooltip.setLabel(Msg.getString("lbl_EdicionOpcionesMenu_DsTooltip"));
            this.cDsTooltip.setWidthLabel("100px");
            
            this.cDsRuta = new CampoWebDescripcion();
            this.cDsRuta.setLabel(Msg.getString("lbl_EdicionOpcionesMenu_DsRuta"));
            this.cDsRuta.setWidthLabel("100px");
            
            this.cFeAlta = new CampoWebFecha();
            this.cFeAlta.setLabel(Msg.getString("lbl_EdicionOpcionesMenu_FeAlta"));
            this.cFeAlta.setWidthLabel("100px");
            this.cFeAlta.setValue(new Date());
            this.cFeAlta.setRequired(true);
            
            this.cFeDesactivo = new CampoWebFecha();
            this.cFeDesactivo.setLabel(Msg.getString("lbl_EdicionOpcionesMenu_FeDesactivo"));
            this.cFeDesactivo.setWidthLabel("100px");
            
            this.setModoFormulario(ModoFormulario.CONSULTA);

            if (idOpcionMenu != null) {
                recuperarRegistro(idOpcionMenu);
            }
            else {
                this.setModoFormulario(ModoFormulario.ALTA);
            }
        }
        catch (Exception ex) {
            try { 
                this.setModoFormulario(ModoFormulario.CONSULTA);
            }
            catch(Exception na) {
                //NADA 
            }
            Mensajes.showException(this.getClass(), ex);
        }
    }

    public void guardar() {
        try {
            validarCampos();
            
            //ALTA
            if (this.modoFormulario == ModoFormulario.ALTA) {
                this.bdTOpcionmenu = new BdTOpcionmenu();
                this.bdTOpcionmenu.setCoOpcionmenu(this.cCoOpcionMenu.getValue());
                this.bdTOpcionmenu.setDsOpcionmenu(this.cDsOpcionMenu.getValue());
                this.bdTOpcionmenu.setIdOpcionmenupadre(this.cOpcionMenuPadre.getId());
                this.bdTOpcionmenu.setEnOrden(this.cEnOrden.getValueInteger());
                this.bdTOpcionmenu.setDsTitulo(this.cDsTitulo.getValue());
                this.bdTOpcionmenu.setDsTooltip(this.cDsTooltip.getValue());
                this.bdTOpcionmenu.setDsRuta(this.cDsRuta.getValue());
                this.bdTOpcionmenu.setFeAlta(this.cFeAlta.getValue());
                this.bdTOpcionmenu.setFeDesactivo(this.cFeDesactivo.getValue());
                
                StTOpcionmenu stTOpcionmenu = new StTOpcionmenu(Session.getDatosUsuario());
                stTOpcionmenu.alta(this.bdTOpcionmenu, null);
                
                if (this.parent instanceof FiltroOpcionesMenu filtroOpcionesMenu) {
                    filtroOpcionesMenu.getDsResultado().refrescarDatos();
                }
                
                Mensajes.showInfo(Msg.getString("Informacion"), Msg.getString("alta_OK"));
            }
            
            //ACTUALIZACION
            if (this.modoFormulario == ModoFormulario.EDICION) {
                this.bdTOpcionmenu.setCoOpcionmenu(this.cCoOpcionMenu.getValue());
                this.bdTOpcionmenu.setDsOpcionmenu(this.cDsOpcionMenu.getValue());
                this.bdTOpcionmenu.setIdOpcionmenupadre(this.cOpcionMenuPadre.getId());
                this.bdTOpcionmenu.setEnOrden(this.cEnOrden.getValueInteger());
                this.bdTOpcionmenu.setDsTitulo(this.cDsTitulo.getValue());
                this.bdTOpcionmenu.setDsTooltip(this.cDsTooltip.getValue());
                this.bdTOpcionmenu.setDsRuta(this.cDsRuta.getValue());
                this.bdTOpcionmenu.setFeAlta(this.cFeAlta.getValue());
                this.bdTOpcionmenu.setFeDesactivo(this.cFeDesactivo.getValue());
                
                StTOpcionmenu stTOpcionmenu = new StTOpcionmenu(Session.getDatosUsuario());
                stTOpcionmenu.actualiza(this.bdTOpcionmenu, null);
                
                if (this.parent instanceof FiltroOpcionesMenu filtroOpcionesMenu) {
                    ELContext elContext = FacesContext.getCurrentInstance().getELContext();
                    filtroOpcionesMenu = (FiltroOpcionesMenu)elContext.getELResolver().getValue(elContext, null, "filtroOpcionesMenu");
                    filtroOpcionesMenu.getDsResultado().actualizarFilaSeleccionada();
                }
                
                Mensajes.showInfo(Msg.getString("Informacion"), Msg.getString("update_OK"));
            }
            
            this.setModoFormulario(ModoFormulario.CONSULTA);
        }
        catch (Exception ex) {
            Mensajes.showException(this.getClass(), ex);
        }
    }
    
    public void modificar() {
        try {
            this.setModoFormulario(ModoFormulario.EDICION);
        }
        catch (Exception ex) {
            Mensajes.showException(this.getClass(), ex);
        }
    }
    
    public void eliminar() {
        try {
            BdTOpcionmenu filtroBdTOpcionmenu = new BdTOpcionmenu();
            filtroBdTOpcionmenu.setIdOpcionmenu(this.bdTOpcionmenu.getIdOpcionmenupadre());
            
            StTOpcionmenu stTOpcionmenu = new StTOpcionmenu(Session.getDatosUsuario());
            ArrayList<BdTOpcionmenu> listaBdTOpcionmenuHijas = stTOpcionmenu.filtro(filtroBdTOpcionmenu, null);
            if (listaBdTOpcionmenuHijas != null) {
                Mensajes.showWarn("No se puede eliminar", "Tiene " + listaBdTOpcionmenuHijas.size() + " opciones de menú hijas, debe eliminarlas primero para eliminar la actual.");
                return;
            }
            
            stTOpcionmenu.baja(this.bdTOpcionmenu, null);
            
            if (this.parent instanceof FiltroOpcionesMenu filtroOpcionesMenu) {
                filtroOpcionesMenu.getDsResultado().eliminarFilaSeleccionada();
            }

            Mensajes.showInfo(Msg.getString("Informacion"), "Registro eliminado correctamente!");
            
            this.setModoFormulario(ModoFormulario.ELIMINADO);
        }
        catch (Exception ex) {
            Mensajes.showException(this.getClass(), ex);
        }
    }
    
    public void limpiar() {
        this.cCoOpcionMenu.setValue(null);
        this.cDsOpcionMenu.setValue(null);
        this.cOpcionMenuPadre.setId(null);
        this.cEnOrden.setValueInteger(null);
        this.cDsTitulo.setValue(null);
        this.cDsTooltip.setValue(null);
        this.cDsRuta.setValue(null);
        this.cFeAlta.setValue(new Date());
        this.cFeDesactivo.setValue(null);
    }
    
    public String volver() {
        try {
            if (this.modoFormulario == ModoFormulario.EDICION) {
                recuperarRegistro(this.bdTOpcionmenu.getIdOpcionmenu());
                this.setModoFormulario(ModoFormulario.CONSULTA);
            }
            else {
                return this.paginaRetorno;
            }
        }
        catch (Exception ex) {
            Mensajes.showException(this.getClass(), ex);
        }
        return null;
    }

    private void validarCampos() throws RequiredFieldException {
        if (Validation.isNullOrEmpty(this.cCoOpcionMenu.getValue())) {
            throw new RequiredFieldException(this.cCoOpcionMenu.getLabel());
        }
        if (Validation.isNullOrEmpty(this.cDsOpcionMenu.getValue())) {
            throw new RequiredFieldException(this.cDsOpcionMenu.getLabel());
        }
        if (Validation.isNullOrEmpty(this.cDsTitulo.getValue())) {
            throw new RequiredFieldException(this.cDsTitulo.getLabel());
        }
        if (Validation.isNullOrEmpty(this.cFeAlta.getValue())) {
            throw new RequiredFieldException(this.cFeAlta.getLabel());
        }
    }
    
    private void protegerCampos() throws Exception {
        switch (this.modoFormulario) {
            case CONSULTA, ELIMINADO -> {
                this.cCoOpcionMenu.setProtegido(true);
                this.cDsOpcionMenu.setProtegido(true);
                this.cOpcionMenuPadre.setProtegido(true);
                this.cEnOrden.setProtegido(true);
                this.cDsTitulo.setProtegido(true);
                this.cDsTooltip.setProtegido(true);
                this.cDsRuta.setProtegido(true);
                this.cFeAlta.setProtegido(true);
                this.cFeDesactivo.setProtegido(true);
            }
            case EDICION -> {
                this.cCoOpcionMenu.setProtegido(false);
                this.cDsOpcionMenu.setProtegido(false);
                this.cOpcionMenuPadre.setProtegido(false);
                this.cEnOrden.setProtegido(false);
                this.cDsTitulo.setProtegido(false);
                this.cDsTooltip.setProtegido(false);
                this.cDsRuta.setProtegido(false);
                this.cFeAlta.setProtegido(false);
                this.cFeDesactivo.setProtegido(false);
            }
            case ALTA -> {
                limpiar();
                this.cCoOpcionMenu.setProtegido(false);
                this.cDsOpcionMenu.setProtegido(false);
                this.cOpcionMenuPadre.setProtegido(false);
                this.cEnOrden.setProtegido(false);
                this.cDsTitulo.setProtegido(false);
                this.cDsTooltip.setProtegido(false);
                this.cDsRuta.setProtegido(false);
                this.cFeAlta.setProtegido(false);
                this.cFeDesactivo.setProtegido(false);
            }
            default -> throw new FormModeException();
        }
    }
    
    private void recuperarRegistro(Integer idOpcionmenu) throws Exception {
        StTOpcionmenu stTOpcionmenu = new StTOpcionmenu(Session.getDatosUsuario());
        this.bdTOpcionmenu = stTOpcionmenu.item(idOpcionmenu, null);
        if (this.bdTOpcionmenu == null) {
            throw new RegistryNotFoundException();
        }
        
        cCoOpcionMenu.setValue(this.bdTOpcionmenu.getCoOpcionmenu());
        cDsOpcionMenu.setValue(this.bdTOpcionmenu.getDsOpcionmenu());
        cOpcionMenuPadre.setId(this.bdTOpcionmenu.getIdOpcionmenupadre());
        cEnOrden.setValueInteger(this.bdTOpcionmenu.getEnOrden());
        cDsTitulo.setValue(this.bdTOpcionmenu.getDsTitulo());
        cDsTooltip.setValue(this.bdTOpcionmenu.getDsTooltip());
        cDsRuta.setValue(this.bdTOpcionmenu.getDsRuta());
        cFeAlta.setValue(this.bdTOpcionmenu.getFeAlta());
        cFeDesactivo.setValue(this.bdTOpcionmenu.getFeDesactivo());
    }

    public CampoWebCodigo getcCoOpcionMenu() {
        return cCoOpcionMenu;
    }

    public void setcCoOpcionMenu(CampoWebCodigo cCoOpcionMenu) {
        this.cCoOpcionMenu = cCoOpcionMenu;
    }

    public CampoWebDescripcion getcDsOpcionMenu() {
        return cDsOpcionMenu;
    }

    public void setcDsOpcionMenu(CampoWebDescripcion cDsOpcionMenu) {
        this.cDsOpcionMenu = cDsOpcionMenu;
    }

    public CampoWebLupa getcOpcionMenuPadre() {
        return cOpcionMenuPadre;
    }

    public void setcOpcionMenuPadre(CampoWebLupa cOpcionMenuPadre) {
        this.cOpcionMenuPadre = cOpcionMenuPadre;
    }

    public CampoWebNumero getcEnOrden() {
        return cEnOrden;
    }

    public void setcEnOrden(CampoWebNumero cEnOrden) {
        this.cEnOrden = cEnOrden;
    }

    public CampoWebDescripcion getcDsTitulo() {
        return cDsTitulo;
    }

    public void setcDsTitulo(CampoWebDescripcion cDsTitulo) {
        this.cDsTitulo = cDsTitulo;
    }

    public CampoWebDescripcion getcDsTooltip() {
        return cDsTooltip;
    }

    public void setcDsTooltip(CampoWebDescripcion cDsTooltip) {
        this.cDsTooltip = cDsTooltip;
    }

    public CampoWebDescripcion getcDsRuta() {
        return cDsRuta;
    }

    public void setcDsRuta(CampoWebDescripcion cDsRuta) {
        this.cDsRuta = cDsRuta;
    }

    public BdTOpcionmenu getBdTOpcionmenu() {
        return bdTOpcionmenu;
    }

    public void setBdTOpcionmenu(BdTOpcionmenu bdTOpcionmenu) {
        this.bdTOpcionmenu = bdTOpcionmenu;
    }

    public CampoWebFecha getcFeAlta() {
        return cFeAlta;
    }

    public void setcFeAlta(CampoWebFecha cFeAlta) {
        this.cFeAlta = cFeAlta;
    }

    public CampoWebFecha getcFeDesactivo() {
        return cFeDesactivo;
    }

    public void setcFeDesactivo(CampoWebFecha cFeDesactivo) {
        this.cFeDesactivo = cFeDesactivo;
    }

    public String getPaginaRetorno() {
        return paginaRetorno;
    }

    public void setPaginaRetorno(String paginaRetorno) {
        this.paginaRetorno = paginaRetorno;
    }

    public ModoFormulario getModoFormulario() {
        return modoFormulario;
    }

    public void setModoFormulario(ModoFormulario modoFormulario) throws Exception {
        this.modoFormulario = modoFormulario;
        protegerCampos();
    }

    public Object getParent() {
        return parent;
    }

    public void setParent(Object parent) {
        this.parent = parent;
    }
}
