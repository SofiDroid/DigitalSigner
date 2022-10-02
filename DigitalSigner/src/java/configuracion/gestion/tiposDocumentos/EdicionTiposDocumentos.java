package configuracion.gestion.tiposDocumentos;

import basedatos.servicios.StTTipodocumento;
import basedatos.tablas.BdTTipodocumento;
import excepciones.FormModeException;
import excepciones.RegistryNotFoundException;
import excepciones.RequiredFieldException;
import java.io.Serializable;
import javax.el.ELContext;
import javax.faces.context.FacesContext;
import utilidades.CampoWebCodigo;
import utilidades.CampoWebDescripcion;
import utilidades.CampoWebFecha;
import utilidades.Mensajes;
import utilidades.ModoFormulario;
import utilidades.Msg;
import utilidades.Validation;

/**
 *
 * @author ihuegal
 */
public class EdicionTiposDocumentos implements Serializable {
    private Object parent = null;
    private String paginaRetorno = null;
    
    private CampoWebCodigo cCoTipodocumento = null;
    private CampoWebDescripcion cDsTipodocumento = null;
    private CampoWebFecha cFeAlta = null;
    private CampoWebFecha cFeDesactivo = null;
    
    BdTTipodocumento bdTTipodocumento = null;

    private ModoFormulario modoFormulario = ModoFormulario.CONSULTA;
    
    public EdicionTiposDocumentos(Object parent) {
        init(parent, null);
    }
    
    public EdicionTiposDocumentos(Object parent, Integer idTipodocumento) {
        init(parent, idTipodocumento);
    }
    
    private void init(Object parent, Integer idTipodocumento) {
        try {
            this.parent = parent;
    
            this.cCoTipodocumento = new CampoWebCodigo();
            this.cCoTipodocumento.setLabel(Msg.getString("lbl_EdicionTiposDocumentos_CoTipodocumento"));
            this.cCoTipodocumento.setWidthLabel("100px");
            this.cCoTipodocumento.setRequired(true);
            
            this.cDsTipodocumento = new CampoWebDescripcion();
            this.cDsTipodocumento.setLabel(Msg.getString("lbl_EdicionTiposDocumentos_DsTipodocumento"));
            this.cDsTipodocumento.setWidthLabel("100px");
            this.cDsTipodocumento.setRequired(true);
            
            this.cFeAlta = new CampoWebFecha();
            this.cFeAlta.setLabel(Msg.getString("lbl_EdicionTiposDocumentos_FeAlta"));
            this.cFeAlta.setWidthLabel("100px");
            this.cFeAlta.setRequired(true);
            
            this.cFeDesactivo = new CampoWebFecha();
            this.cFeDesactivo.setLabel(Msg.getString("lbl_EdicionTiposDocumentos_FeDesactivo"));
            this.cFeDesactivo.setWidthLabel("100px");
            
            this.setModoFormulario(ModoFormulario.CONSULTA);

            if (idTipodocumento != null) {
                recuperarRegistro(idTipodocumento);
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
                this.bdTTipodocumento = new BdTTipodocumento();
                this.bdTTipodocumento.setCoTipodocumento(this.cCoTipodocumento.getValue());
                this.bdTTipodocumento.setDsTipodocumento(this.cDsTipodocumento.getValue());
                this.bdTTipodocumento.setFeAlta(this.cFeAlta.getValue());
                this.bdTTipodocumento.setFeDesactivo(this.cFeDesactivo.getValue());
                
                StTTipodocumento stTTipodocumento = new StTTipodocumento();
                stTTipodocumento.alta(this.bdTTipodocumento, null);
                
                if (this.parent instanceof FiltroTiposDocumentos filtroTiposDocumentos) {
                    filtroTiposDocumentos.getDsResultado().refrescarDatos();
                }
                
                Mensajes.showInfo("Información", "Alta realizada correctamente!");
            }
            
            //ACTUALIZACION
            if (this.modoFormulario == ModoFormulario.EDICION) {
                this.bdTTipodocumento.setCoTipodocumento(this.cCoTipodocumento.getValue());
                this.bdTTipodocumento.setDsTipodocumento(this.cDsTipodocumento.getValue());
                this.bdTTipodocumento.setFeAlta(this.cFeAlta.getValue());
                this.bdTTipodocumento.setFeDesactivo(this.cFeDesactivo.getValue());
                
                StTTipodocumento stTTipodocumento = new StTTipodocumento();
                stTTipodocumento.actualiza(this.bdTTipodocumento, null);
                
                if (this.parent instanceof FiltroTiposDocumentos filtroTiposDocumentos) {
                    ELContext elContext = FacesContext.getCurrentInstance().getELContext();
                    filtroTiposDocumentos = (FiltroTiposDocumentos)elContext.getELResolver().getValue(elContext, null, "filtroTiposDocumentos");
                    filtroTiposDocumentos.getDsResultado().actualizarFilaSeleccionada();
                }
                
                Mensajes.showInfo("Información", "Actualización realizada correctamente!");
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
            StTTipodocumento stTTipodocumento = new StTTipodocumento();
            stTTipodocumento.baja(this.bdTTipodocumento, null);
            
            if (this.parent instanceof FiltroTiposDocumentos filtroTiposDocumentos) {
                filtroTiposDocumentos.getDsResultado().eliminarFilaSeleccionada();
            }

            Mensajes.showInfo("Información", "Registro eliminado correctamente!");
            
            this.setModoFormulario(ModoFormulario.ELIMINADO);
        }
        catch (Exception ex) {
            Mensajes.showException(this.getClass(), ex);
        }
    }
    
    public void limpiar() {
        this.cCoTipodocumento.setValue(null);
        this.cDsTipodocumento.setValue(null);
        this.cFeAlta.setValue(null);
        this.cFeDesactivo.setValue(null);
    }
    
    public String volver() {
        try {
            if (this.modoFormulario == ModoFormulario.EDICION) {
                recuperarRegistro(this.bdTTipodocumento.getIdTipodocumento());
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
        if (Validation.isNullOrEmpty(this.cCoTipodocumento.getValue())) {
            throw new RequiredFieldException(this.cCoTipodocumento.getLabel());
        }
        if (Validation.isNullOrEmpty(this.cDsTipodocumento.getValue())) {
            throw new RequiredFieldException(this.cDsTipodocumento.getLabel());
        }
        if (Validation.isNullOrEmpty(this.cFeAlta.getValue())) {
            throw new RequiredFieldException(this.cFeAlta.getLabel());
        }
    }
    
    private void protegerCampos() throws Exception {
        switch (this.modoFormulario) {
            case CONSULTA, ELIMINADO -> {
                this.cCoTipodocumento.setProtegido(true);
                this.cDsTipodocumento.setProtegido(true);
                this.cFeAlta.setProtegido(true);
                this.cFeDesactivo.setProtegido(true);
            }
            case EDICION -> {
                this.cCoTipodocumento.setProtegido(false);
                this.cDsTipodocumento.setProtegido(false);
                this.cFeAlta.setProtegido(false);
                this.cFeDesactivo.setProtegido(false);
            }
            case ALTA -> {
                limpiar();
                this.cCoTipodocumento.setProtegido(false);
                this.cDsTipodocumento.setProtegido(false);
                this.cFeAlta.setProtegido(false);
                this.cFeDesactivo.setProtegido(false);
            }
            default -> throw new FormModeException();
        }
    }
    
    private void recuperarRegistro(Integer idTipodocumento) throws Exception {
        StTTipodocumento stTTipodocumento = new StTTipodocumento();
        this.bdTTipodocumento = stTTipodocumento.item(idTipodocumento, null);
        if (this.bdTTipodocumento == null) {
            throw new RegistryNotFoundException();
        }
        
        cCoTipodocumento.setValue(this.bdTTipodocumento.getCoTipodocumento());
        cDsTipodocumento.setValue(this.bdTTipodocumento.getDsTipodocumento());
        cFeAlta.setValue(this.bdTTipodocumento.getFeAlta());
        cFeDesactivo.setValue(this.bdTTipodocumento.getFeDesactivo());
    }

    public CampoWebCodigo getcCoTipodocumento() {
        return cCoTipodocumento;
    }

    public void setcCoTipodocumento(CampoWebCodigo cCoTipodocumento) {
        this.cCoTipodocumento = cCoTipodocumento;
    }

    public CampoWebDescripcion getcDsTipodocumento() {
        return cDsTipodocumento;
    }

    public void setcDsTipodocumento(CampoWebDescripcion cDsTipodocumento) {
        this.cDsTipodocumento = cDsTipodocumento;
    }

    public BdTTipodocumento getBdTTipodocumento() {
        return bdTTipodocumento;
    }

    public void setBdTTipodocumento(BdTTipodocumento bdTTipodocumento) {
        this.bdTTipodocumento = bdTTipodocumento;
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
