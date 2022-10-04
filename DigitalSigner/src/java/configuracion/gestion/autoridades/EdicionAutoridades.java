package configuracion.gestion.autoridades;

import basedatos.servicios.StTAutoridad;
import basedatos.tablas.BdTAutoridad;
import excepciones.FormModeException;
import excepciones.RegistryNotFoundException;
import excepciones.RequiredFieldException;
import java.io.Serializable;
import javax.el.ELContext;
import javax.faces.context.FacesContext;
import utilidades.CampoWebCodigo;
import utilidades.CampoWebDescripcion;
import utilidades.CampoWebFecha;
import utilidades.CampoWebLupa;
import utilidades.Mensajes;
import utilidades.ModoFormulario;
import utilidades.Msg;
import utilidades.Validation;

/**
 *
 * @author ihuegal
 */
public class EdicionAutoridades implements Serializable {
    private Object parent = null;
    private String paginaRetorno = null;
    
    private CampoWebCodigo cCoAutoridad = null;
    private CampoWebDescripcion cDsAutoridad= null;
    private CampoWebFecha cFeAlta = null;
    private CampoWebFecha cFeDesactivo = null;
    private CampoWebLupa cUnidad = null;
    
    BdTAutoridad bdTAutoridad = null;

    private ModoFormulario modoFormulario = ModoFormulario.CONSULTA;
    
    public EdicionAutoridades(Object parent) {
        init(parent, null);
    }
    
    public EdicionAutoridades(Object parent, Integer idAutoridad) {
        init(parent, idAutoridad);
    }
    
    private void init(Object parent, Integer idAutoridad) {
        try {
            this.parent = parent;
    
            this.cCoAutoridad = new CampoWebCodigo();
            this.cCoAutoridad.setLabel(Msg.getString("lbl_EdicionAutoridades_CoAutoridad"));
            this.cCoAutoridad.setWidthLabel("100px");
            this.cCoAutoridad.setRequired(true);
            
            this.cDsAutoridad = new CampoWebDescripcion();
            this.cDsAutoridad.setLabel(Msg.getString("lbl_EdicionAutoridades_DsAutoridad"));
            this.cDsAutoridad.setWidthLabel("100px");
            this.cDsAutoridad.setRequired(true);
            
            this.cFeAlta = new CampoWebFecha();
            this.cFeAlta.setLabel(Msg.getString("lbl_EdicionAutoridades_FeAlta"));
            this.cFeAlta.setWidthLabel("100px");
            this.cFeAlta.setRequired(true);
            
            this.cFeDesactivo = new CampoWebFecha();
            this.cFeDesactivo.setLabel(Msg.getString("lbl_EdicionAutoridades_FeDesactivo"));
            this.cFeDesactivo.setWidthLabel("100px");
            
            this.cUnidad = new CampoWebLupa();
            this.cUnidad.setLabel(Msg.getString("lbl_EdicionAutoridades_Unidad"));
            this.cUnidad.setWidthLabel("100px");
            String sql = "SELECT ID_UNIDAD, CO_UNIDAD + ' - ' + DS_UNIDAD as Unidad FROM BD_T_UNIDAD";
            this.cUnidad.setConsulta(sql);
            this.cUnidad.setColumnaID("ID_UNIDAD");
            this.cUnidad.setColumnaLabel("Unidad");

            this.setModoFormulario(ModoFormulario.CONSULTA);

            if (idAutoridad != null) {
                recuperarRegistro(idAutoridad);
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
                this.bdTAutoridad = new BdTAutoridad();
                this.bdTAutoridad.setCoAutoridad(this.cCoAutoridad.getValue());
                this.bdTAutoridad.setDsAutoridad(this.cDsAutoridad.getValue());
                this.bdTAutoridad.setFeAlta(this.cFeAlta.getValue());
                this.bdTAutoridad.setFeDesactivo(this.cFeDesactivo.getValue());
                this.bdTAutoridad.setIdUnidad(this.cUnidad.getId());
                
                StTAutoridad stTAutoridad = new StTAutoridad();
                stTAutoridad.alta(this.bdTAutoridad, null);
                
                if (this.parent instanceof FiltroAutoridades filtroAutoridades) {
                    filtroAutoridades.getDsResultado().refrescarDatos();
                }
                
                Mensajes.showInfo("Información", "Alta realizada correctamente!");
            }
            
            //ACTUALIZACION
            if (this.modoFormulario == ModoFormulario.EDICION) {
                this.bdTAutoridad.setCoAutoridad(this.cCoAutoridad.getValue());
                this.bdTAutoridad.setDsAutoridad(this.cDsAutoridad.getValue());
                this.bdTAutoridad.setFeAlta(this.cFeAlta.getValue());
                this.bdTAutoridad.setFeDesactivo(this.cFeDesactivo.getValue());
                this.bdTAutoridad.setIdUnidad(this.cUnidad.getId());
                
                StTAutoridad stTAutoridad = new StTAutoridad();
                stTAutoridad.actualiza(this.bdTAutoridad, null);
                
                if (this.parent instanceof FiltroAutoridades filtroAutoridades) {
                    ELContext elContext = FacesContext.getCurrentInstance().getELContext();
                    filtroAutoridades = (FiltroAutoridades)elContext.getELResolver().getValue(elContext, null, "filtroAutoridades");
                    filtroAutoridades.getDsResultado().actualizarFilaSeleccionada();
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
            StTAutoridad stTAutoridad = new StTAutoridad();
            stTAutoridad.baja(this.bdTAutoridad, null);
            
            if (this.parent instanceof FiltroAutoridades filtroAutoridades) {
                filtroAutoridades.getDsResultado().eliminarFilaSeleccionada();
            }

            Mensajes.showInfo("Información", "Registro eliminado correctamente!");
            
            this.setModoFormulario(ModoFormulario.ELIMINADO);
        }
        catch (Exception ex) {
            Mensajes.showException(this.getClass(), ex);
        }
    }
    
    public void limpiar() {
        this.cCoAutoridad.setValue(null);
        this.cDsAutoridad.setValue(null);
        this.cFeAlta.setValue(null);
        this.cFeDesactivo.setValue(null);
        this.cUnidad.setId(null);
    }
    
    public String volver() {
        try {
            if (this.modoFormulario == ModoFormulario.EDICION) {
                recuperarRegistro(this.bdTAutoridad.getIdAutoridad());
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
        if (Validation.isNullOrEmpty(this.cCoAutoridad.getValue())) {
            throw new RequiredFieldException(this.cCoAutoridad.getLabel());
        }
        if (Validation.isNullOrEmpty(this.cDsAutoridad.getValue())) {
            throw new RequiredFieldException(this.cDsAutoridad.getLabel());
        }
        if (Validation.isNullOrEmpty(this.cFeAlta.getValue())) {
            throw new RequiredFieldException(this.cFeAlta.getLabel());
        }
        if (Validation.isNullOrEmpty(this.cUnidad.getId())) {
            throw new RequiredFieldException(this.cUnidad.getLabel());
        }
    }
    
    private void protegerCampos() throws Exception {
        switch (this.modoFormulario) {
            case CONSULTA, ELIMINADO -> {
                this.cCoAutoridad.setProtegido(true);
                this.cDsAutoridad.setProtegido(true);
                this.cFeAlta.setProtegido(true);
                this.cFeDesactivo.setProtegido(true);
                this.cUnidad.setProtegido(true);
            }
            case EDICION -> {
                this.cCoAutoridad.setProtegido(false);
                this.cDsAutoridad.setProtegido(false);
                this.cFeAlta.setProtegido(false);
                this.cFeDesactivo.setProtegido(false);
                this.cUnidad.setProtegido(false);
            }
            case ALTA -> {
                limpiar();
                this.cCoAutoridad.setProtegido(false);
                this.cDsAutoridad.setProtegido(false);
                this.cFeAlta.setProtegido(false);
                this.cFeDesactivo.setProtegido(false);
                this.cUnidad.setProtegido(false);
            }
            default -> throw new FormModeException();
        }
    }
    
    private void recuperarRegistro(Integer idAutoridad) throws Exception {
        StTAutoridad stTAutoridad = new StTAutoridad();
        this.bdTAutoridad = stTAutoridad.item(idAutoridad, null);
        if (this.bdTAutoridad == null) {
            throw new RegistryNotFoundException();
        }
        
        cCoAutoridad.setValue(this.bdTAutoridad.getCoAutoridad());
        cDsAutoridad.setValue(this.bdTAutoridad.getDsAutoridad());
        cFeAlta.setValue(this.bdTAutoridad.getFeAlta());
        cFeDesactivo.setValue(this.bdTAutoridad.getFeDesactivo());
        cUnidad.setId(this.bdTAutoridad.getIdUnidad());
    }
    
    public CampoWebCodigo getcCoAutoridad() {
        return cCoAutoridad;
    }

    public void setcCoAutoridad(CampoWebCodigo cCoAutoridad) {
        this.cCoAutoridad = cCoAutoridad;
    }

    public CampoWebDescripcion getcDsAutoridad() {
        return cDsAutoridad;
    }

    public void setcDsAutoridad(CampoWebDescripcion cDsAutoridad) {
        this.cDsAutoridad = cDsAutoridad;
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

    public CampoWebLupa getcUnidad() {
        return cUnidad;
    }

    public void setcUnidad(CampoWebLupa cUnidad) {
        this.cUnidad = cUnidad;
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

    public BdTAutoridad getBdTAutoridad() {
        return bdTAutoridad;
    }

    public void setBdTAutoridad(BdTAutoridad bdTAutoridad) {
        this.bdTAutoridad = bdTAutoridad;
    }
}
