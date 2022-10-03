package configuracion.sistema.permisos;

import basedatos.servicios.StTPermiso;
import basedatos.tablas.BdTPermiso;
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
public class EdicionPermisos implements Serializable {
    private Object parent = null;
    private String paginaRetorno = null;
    
    private CampoWebCodigo cCoPermiso = null;
    private CampoWebDescripcion cDsPermiso = null;
    private CampoWebFecha cFeAlta = null;
    private CampoWebFecha cFeDesactivo = null;
    
    BdTPermiso bdTPermiso = null;

    private ModoFormulario modoFormulario = ModoFormulario.CONSULTA;
    
    public EdicionPermisos(Object parent) {
        init(parent, null);
    }
    
    public EdicionPermisos(Object parent, Integer idPermiso) {
        init(parent, idPermiso);
    }
    
    private void init(Object parent, Integer idPermiso) {
        try {
            this.parent = parent;
    
            this.cCoPermiso = new CampoWebCodigo();
            this.cCoPermiso.setLabel(Msg.getString("lbl_EdicionPermisos_CoPermiso"));
            this.cCoPermiso.setWidthLabel("100px");
            this.cCoPermiso.setRequired(true);
            
            this.cDsPermiso = new CampoWebDescripcion();
            this.cDsPermiso.setLabel(Msg.getString("lbl_EdicionPermisos_DsPermiso"));
            this.cDsPermiso.setWidthLabel("100px");
            this.cDsPermiso.setRequired(true);
            
            this.cFeAlta = new CampoWebFecha();
            this.cFeAlta.setLabel(Msg.getString("lbl_EdicionPermisos_FeAlta"));
            this.cFeAlta.setWidthLabel("100px");
            this.cFeAlta.setRequired(true);
            
            this.cFeDesactivo = new CampoWebFecha();
            this.cFeDesactivo.setLabel(Msg.getString("lbl_EdicionPermisos_FeDesactivo"));
            this.cFeDesactivo.setWidthLabel("100px");
            
            this.setModoFormulario(ModoFormulario.CONSULTA);

            if (idPermiso != null) {
                recuperarRegistro(idPermiso);
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
                this.bdTPermiso = new BdTPermiso();
                this.bdTPermiso.setCoPermiso(this.cCoPermiso.getValue());
                this.bdTPermiso.setDsPermiso(this.cDsPermiso.getValue());
                this.bdTPermiso.setFeAlta(this.cFeAlta.getValue());
                this.bdTPermiso.setFeDesactivo(this.cFeDesactivo.getValue());
                
                StTPermiso stTPermiso = new StTPermiso();
                stTPermiso.alta(this.bdTPermiso, null);
                
                if (this.parent instanceof FiltroPermisos filtroPermisos) {
                    filtroPermisos.getDsResultado().refrescarDatos();
                }
                
                Mensajes.showInfo("Información", "Alta realizada correctamente!");
            }
            
            //ACTUALIZACION
            if (this.modoFormulario == ModoFormulario.EDICION) {
                this.bdTPermiso.setCoPermiso(this.cCoPermiso.getValue());
                this.bdTPermiso.setDsPermiso(this.cDsPermiso.getValue());
                this.bdTPermiso.setFeAlta(this.cFeAlta.getValue());
                this.bdTPermiso.setFeDesactivo(this.cFeDesactivo.getValue());
                
                StTPermiso stTPermiso = new StTPermiso();
                stTPermiso.actualiza(this.bdTPermiso, null);
                
                if (this.parent instanceof FiltroPermisos filtroPermisos) {
                    ELContext elContext = FacesContext.getCurrentInstance().getELContext();
                    filtroPermisos = (FiltroPermisos)elContext.getELResolver().getValue(elContext, null, "filtroPermisos");
                    filtroPermisos.getDsResultado().actualizarFilaSeleccionada();
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
            StTPermiso stTPermiso = new StTPermiso();
            stTPermiso.baja(this.bdTPermiso, null);
            
            if (this.parent instanceof FiltroPermisos filtroPermisos) {
                filtroPermisos.getDsResultado().eliminarFilaSeleccionada();
            }

            Mensajes.showInfo("Información", "Registro eliminado correctamente!");
            
            this.setModoFormulario(ModoFormulario.ELIMINADO);
        }
        catch (Exception ex) {
            Mensajes.showException(this.getClass(), ex);
        }
    }
    
    public void limpiar() {
        this.cCoPermiso.setValue(null);
        this.cDsPermiso.setValue(null);
        this.cFeAlta.setValue(null);
        this.cFeDesactivo.setValue(null);
    }
    
    public String volver() {
        try {
            if (this.modoFormulario == ModoFormulario.EDICION) {
                recuperarRegistro(this.bdTPermiso.getIdPermiso());
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
        if (Validation.isNullOrEmpty(this.cCoPermiso.getValue())) {
            throw new RequiredFieldException(this.cCoPermiso.getLabel());
        }
        if (Validation.isNullOrEmpty(this.cDsPermiso.getValue())) {
            throw new RequiredFieldException(this.cDsPermiso.getLabel());
        }
        if (Validation.isNullOrEmpty(this.cFeAlta.getValue())) {
            throw new RequiredFieldException(this.cFeAlta.getLabel());
        }
    }
    
    private void protegerCampos() throws Exception {
        switch (this.modoFormulario) {
            case CONSULTA, ELIMINADO -> {
                this.cCoPermiso.setProtegido(true);
                this.cDsPermiso.setProtegido(true);
                this.cFeAlta.setProtegido(true);
                this.cFeDesactivo.setProtegido(true);
            }
            case EDICION -> {
                this.cCoPermiso.setProtegido(false);
                this.cDsPermiso.setProtegido(false);
                this.cFeAlta.setProtegido(false);
                this.cFeDesactivo.setProtegido(false);
            }
            case ALTA -> {
                limpiar();
                this.cCoPermiso.setProtegido(false);
                this.cDsPermiso.setProtegido(false);
                this.cFeAlta.setProtegido(false);
                this.cFeDesactivo.setProtegido(false);
            }
            default -> throw new FormModeException();
        }
    }
    
    private void recuperarRegistro(Integer idPermiso) throws Exception {
        StTPermiso stTPermiso = new StTPermiso();
        this.bdTPermiso = stTPermiso.item(idPermiso, null);
        if (this.bdTPermiso == null) {
            throw new RegistryNotFoundException();
        }
        
        cCoPermiso.setValue(this.bdTPermiso.getCoPermiso());
        cDsPermiso.setValue(this.bdTPermiso.getDsPermiso());
        cFeAlta.setValue(this.bdTPermiso.getFeAlta());
        cFeDesactivo.setValue(this.bdTPermiso.getFeDesactivo());
    }

    public CampoWebCodigo getcCoPermiso() {
        return cCoPermiso;
    }

    public void setcCoPermiso(CampoWebCodigo cCoPermiso) {
        this.cCoPermiso = cCoPermiso;
    }

    public CampoWebDescripcion getcDsPermiso() {
        return cDsPermiso;
    }

    public void setcDsPermiso(CampoWebDescripcion cDsPermiso) {
        this.cDsPermiso = cDsPermiso;
    }

    public BdTPermiso getBdTPermiso() {
        return bdTPermiso;
    }

    public void setBdTPermiso(BdTPermiso bdTPermiso) {
        this.bdTPermiso = bdTPermiso;
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
