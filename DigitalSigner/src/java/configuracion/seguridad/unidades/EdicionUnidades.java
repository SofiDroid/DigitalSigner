package configuracion.seguridad.unidades;

import basedatos.servicios.StTUnidad;
import basedatos.tablas.BdTUnidad;
import excepciones.FormModeException;
import excepciones.RegistryNotFoundException;
import excepciones.RequiredFieldException;
import java.io.Serializable;
import java.util.ArrayList;
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
public class EdicionUnidades implements Serializable {
    private Object parent = null;
    private String paginaRetorno = null;
    
    private CampoWebCodigo cCoUnidad = null;
    private CampoWebDescripcion cDsUnidad= null;
    private CampoWebFecha cFeAlta = null;
    private CampoWebFecha cFeDesactivo = null;
    private CampoWebLupa cUnidadPadre = null;
    
    BdTUnidad bdTUnidad = null;

    private ModoFormulario modoFormulario = ModoFormulario.CONSULTA;
    
    public EdicionUnidades(Object parent) {
        init(parent, null);
    }
    
    public EdicionUnidades(Object parent, Integer idUnidad) {
        init(parent, idUnidad);
    }
    
    private void init(Object parent, Integer idUnidad) {
        try {
            this.parent = parent;
    
            this.cCoUnidad = new CampoWebCodigo();
            this.cCoUnidad.setLabel(Msg.getString("lbl_EdicionUnidades_CoUnidad"));
            this.cCoUnidad.setWidthLabel("100px");
            this.cCoUnidad.setRequired(true);
            
            this.cDsUnidad = new CampoWebDescripcion();
            this.cDsUnidad.setLabel(Msg.getString("lbl_EdicionUnidades_DsUnidad"));
            this.cDsUnidad.setWidthLabel("100px");
            this.cDsUnidad.setRequired(true);
            
            this.cFeAlta = new CampoWebFecha();
            this.cFeAlta.setLabel(Msg.getString("lbl_EdicionUnidades_FeAlta"));
            this.cFeAlta.setWidthLabel("100px");
            this.cFeAlta.setRequired(true);
            
            this.cFeDesactivo = new CampoWebFecha();
            this.cFeDesactivo.setLabel(Msg.getString("lbl_EdicionUnidades_FeDesactivo"));
            this.cFeDesactivo.setWidthLabel("100px");
            
            this.cUnidadPadre = new CampoWebLupa();
            this.cUnidadPadre.setLabel(Msg.getString("lbl_EdicionUnidades_UnidadPadre"));
            this.cUnidadPadre.setWidthLabel("100px");
            String sql = "SELECT ID_UNIDAD, CO_UNIDAD + ' - ' + DS_UNIDAD as Unidad FROM BD_T_UNIDAD";
            if (idUnidad != null) {
                sql += " WHERE ID_UNIDAD != " + idUnidad;
            }
            this.cUnidadPadre.setConsulta(sql);
            this.cUnidadPadre.setColumnaID("ID_UNIDAD");
            this.cUnidadPadre.setColumnaLabel("Unidad");

            this.setModoFormulario(ModoFormulario.CONSULTA);

            if (idUnidad != null) {
                recuperarRegistro(idUnidad);
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
                this.bdTUnidad = new BdTUnidad();
                this.bdTUnidad.setCoUnidad(this.cCoUnidad.getValue());
                this.bdTUnidad.setDsUnidad(this.cDsUnidad.getValue());
                this.bdTUnidad.setFeAlta(this.cFeAlta.getValue());
                this.bdTUnidad.setFeDesactivo(this.cFeDesactivo.getValue());
                this.bdTUnidad.setIdUnidadpadre(this.cUnidadPadre.getId());
                
                StTUnidad stTUnidad = new StTUnidad();
                stTUnidad.alta(this.bdTUnidad, null);
                
                if (this.parent instanceof FiltroUnidades filtroUnidades) {
                    filtroUnidades.getDsResultado().refrescarDatos();
                }
                
                Mensajes.showInfo("Información", "Alta realizada correctamente!");
            }
            
            //ACTUALIZACION
            if (this.modoFormulario == ModoFormulario.EDICION) {
                this.bdTUnidad.setCoUnidad(this.cCoUnidad.getValue());
                this.bdTUnidad.setDsUnidad(this.cDsUnidad.getValue());
                this.bdTUnidad.setFeAlta(this.cFeAlta.getValue());
                this.bdTUnidad.setFeDesactivo(this.cFeDesactivo.getValue());
                this.bdTUnidad.setIdUnidadpadre(this.cUnidadPadre.getId());
                
                StTUnidad stTUnidad = new StTUnidad();
                stTUnidad.actualiza(this.bdTUnidad, null);
                
                if (this.parent instanceof FiltroUnidades filtroUnidades) {
                    ELContext elContext = FacesContext.getCurrentInstance().getELContext();
                    filtroUnidades = (FiltroUnidades)elContext.getELResolver().getValue(elContext, null, "filtroUnidades");
                    filtroUnidades.getDsResultado().actualizarFilaSeleccionada();
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
            BdTUnidad filtroBdTUnidad = new BdTUnidad();
            filtroBdTUnidad.setIdUnidadpadre(this.bdTUnidad.getIdUnidad());
            
            StTUnidad stTUnidad = new StTUnidad();
            ArrayList<BdTUnidad> listaBdTUnidadesHijas = stTUnidad.filtro(filtroBdTUnidad, null);
            if (listaBdTUnidadesHijas != null) {
                Mensajes.showWarn("No se puede eliminar", "Tiene " + listaBdTUnidadesHijas.size() + " unidades hijas, debe eliminarlas primero para eliminar la actual.");
                return;
            }
            
            stTUnidad.baja(this.bdTUnidad, null);
            
            if (this.parent instanceof FiltroUnidades filtroUnidades) {
                filtroUnidades.getDsResultado().eliminarFilaSeleccionada();
            }

            Mensajes.showInfo("Información", "Registro eliminado correctamente!");
            
            this.setModoFormulario(ModoFormulario.ELIMINADO);
        }
        catch (Exception ex) {
            Mensajes.showException(this.getClass(), ex);
        }
    }
    
    public void limpiar() {
        this.cCoUnidad.setValue(null);
        this.cDsUnidad.setValue(null);
        this.cFeAlta.setValue(null);
        this.cFeDesactivo.setValue(null);
        this.cUnidadPadre.setId(null);
    }
    
    public String volver() {
        try {
            if (this.modoFormulario == ModoFormulario.EDICION) {
                recuperarRegistro(this.bdTUnidad.getIdUnidad());
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
        if (Validation.isNullOrEmpty(this.cCoUnidad.getValue())) {
            throw new RequiredFieldException(this.cCoUnidad.getLabel());
        }
        if (Validation.isNullOrEmpty(this.cDsUnidad.getValue())) {
            throw new RequiredFieldException(this.cDsUnidad.getLabel());
        }
        if (Validation.isNullOrEmpty(this.cFeAlta.getValue())) {
            throw new RequiredFieldException(this.cFeAlta.getLabel());
        }
    }
    
    private void protegerCampos() throws Exception {
        switch (this.modoFormulario) {
            case CONSULTA, ELIMINADO -> {
                this.cCoUnidad.setProtegido(true);
                this.cDsUnidad.setProtegido(true);
                this.cFeAlta.setProtegido(true);
                this.cFeDesactivo.setProtegido(true);
                this.cUnidadPadre.setProtegido(true);
            }
            case EDICION -> {
                this.cCoUnidad.setProtegido(false);
                this.cDsUnidad.setProtegido(false);
                this.cFeAlta.setProtegido(false);
                this.cFeDesactivo.setProtegido(false);
                this.cUnidadPadre.setProtegido(false);
            }
            case ALTA -> {
                limpiar();
                this.cCoUnidad.setProtegido(false);
                this.cDsUnidad.setProtegido(false);
                this.cFeAlta.setProtegido(false);
                this.cFeDesactivo.setProtegido(false);
                this.cUnidadPadre.setProtegido(false);
            }
            default -> throw new FormModeException();
        }
    }
    
    private void recuperarRegistro(Integer idUnidad) throws Exception {
        StTUnidad stTUnidad = new StTUnidad();
        this.bdTUnidad = stTUnidad.item(idUnidad, null);
        if (this.bdTUnidad == null) {
            throw new RegistryNotFoundException();
        }
        
        cCoUnidad.setValue(this.bdTUnidad.getCoUnidad());
        cDsUnidad.setValue(this.bdTUnidad.getDsUnidad());
        cFeAlta.setValue(this.bdTUnidad.getFeAlta());
        cFeDesactivo.setValue(this.bdTUnidad.getFeDesactivo());
        cUnidadPadre.setId(this.bdTUnidad.getIdUnidadpadre());
    }
    
    public CampoWebCodigo getcCoUnidad() {
        return cCoUnidad;
    }

    public void setcCoUnidad(CampoWebCodigo cCoUnidad) {
        this.cCoUnidad = cCoUnidad;
    }

    public CampoWebDescripcion getcDsUnidad() {
        return cDsUnidad;
    }

    public void setcDsUnidad(CampoWebDescripcion cDsUnidad) {
        this.cDsUnidad = cDsUnidad;
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

    public CampoWebLupa getcUnidadPadre() {
        return cUnidadPadre;
    }

    public void setcUnidadPadre(CampoWebLupa cUnidadPadre) {
        this.cUnidadPadre = cUnidadPadre;
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

    public BdTUnidad getBdTUnidad() {
        return bdTUnidad;
    }

    public void setBdTUnidad(BdTUnidad bdTUnidad) {
        this.bdTUnidad = bdTUnidad;
    }
}
