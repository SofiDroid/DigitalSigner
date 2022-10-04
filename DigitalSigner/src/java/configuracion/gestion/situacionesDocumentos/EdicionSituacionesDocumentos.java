package configuracion.gestion.situacionesDocumentos;

import basedatos.servicios.StTSituaciondoc;
import basedatos.tablas.BdTSituaciondoc;
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
public class EdicionSituacionesDocumentos implements Serializable {
    private Object parent = null;
    private String paginaRetorno = null;
    
    private CampoWebCodigo cCoSituaciondoc = null;
    private CampoWebDescripcion cDsSituaciondoc = null;
    private CampoWebFecha cFeAlta = null;
    private CampoWebFecha cFeDesactivo = null;
    
    BdTSituaciondoc bdTSituaciondoc = null;

    private ModoFormulario modoFormulario = ModoFormulario.CONSULTA;
    
    public EdicionSituacionesDocumentos(Object parent) {
        init(parent, null);
    }
    
    public EdicionSituacionesDocumentos(Object parent, Integer idSituaciondoc) {
        init(parent, idSituaciondoc);
    }
    
    private void init(Object parent, Integer idSituaciondoc) {
        try {
            this.parent = parent;
    
            this.cCoSituaciondoc = new CampoWebCodigo();
            this.cCoSituaciondoc.setLabel(Msg.getString("lbl_EdicionSituacionesDocumentos_CoSituaciondoc"));
            this.cCoSituaciondoc.setWidthLabel("100px");
            this.cCoSituaciondoc.setRequired(true);
            
            this.cDsSituaciondoc = new CampoWebDescripcion();
            this.cDsSituaciondoc.setLabel(Msg.getString("lbl_EdicionSituacionesDocumentos_DsSituaciondoc"));
            this.cDsSituaciondoc.setWidthLabel("100px");
            this.cDsSituaciondoc.setRequired(true);
            
            this.cFeAlta = new CampoWebFecha();
            this.cFeAlta.setLabel(Msg.getString("lbl_EdicionSituacionesDocumentos_FeAlta"));
            this.cFeAlta.setWidthLabel("100px");
            this.cFeAlta.setRequired(true);
            
            this.cFeDesactivo = new CampoWebFecha();
            this.cFeDesactivo.setLabel(Msg.getString("lbl_EdicionSituacionesDocumentos_FeDesactivo"));
            this.cFeDesactivo.setWidthLabel("100px");
            
            this.setModoFormulario(ModoFormulario.CONSULTA);

            if (idSituaciondoc != null) {
                recuperarRegistro(idSituaciondoc);
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
                this.bdTSituaciondoc = new BdTSituaciondoc();
                this.bdTSituaciondoc.setCoSituaciondoc(this.cCoSituaciondoc.getValue());
                this.bdTSituaciondoc.setDsSituaciondoc(this.cDsSituaciondoc.getValue());
                this.bdTSituaciondoc.setFeAlta(this.cFeAlta.getValue());
                this.bdTSituaciondoc.setFeDesactivo(this.cFeDesactivo.getValue());
                
                StTSituaciondoc stTSituaciondoc = new StTSituaciondoc();
                stTSituaciondoc.alta(this.bdTSituaciondoc, null);
                
                if (this.parent instanceof FiltroSituacionesDocumentos filtroSituacionesDocumentos) {
                    filtroSituacionesDocumentos.getDsResultado().refrescarDatos();
                }
                
                Mensajes.showInfo("Información", "Alta realizada correctamente!");
            }
            
            //ACTUALIZACION
            if (this.modoFormulario == ModoFormulario.EDICION) {
                this.bdTSituaciondoc.setCoSituaciondoc(this.cCoSituaciondoc.getValue());
                this.bdTSituaciondoc.setDsSituaciondoc(this.cDsSituaciondoc.getValue());
                this.bdTSituaciondoc.setFeAlta(this.cFeAlta.getValue());
                this.bdTSituaciondoc.setFeDesactivo(this.cFeDesactivo.getValue());
                
                StTSituaciondoc stTSituaciondoc = new StTSituaciondoc();
                stTSituaciondoc.actualiza(this.bdTSituaciondoc, null);
                
                if (this.parent instanceof FiltroSituacionesDocumentos filtroSituacionesDocumentos) {
                    ELContext elContext = FacesContext.getCurrentInstance().getELContext();
                    filtroSituacionesDocumentos = (FiltroSituacionesDocumentos)elContext.getELResolver().getValue(elContext, null, "filtroSituacionesDocumentos");
                    filtroSituacionesDocumentos.getDsResultado().actualizarFilaSeleccionada();
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
            StTSituaciondoc stTSituaciondoc = new StTSituaciondoc();
            stTSituaciondoc.baja(this.bdTSituaciondoc, null);
            
            if (this.parent instanceof FiltroSituacionesDocumentos filtroSituacionesDocumentos) {
                filtroSituacionesDocumentos.getDsResultado().eliminarFilaSeleccionada();
            }

            Mensajes.showInfo("Información", "Registro eliminado correctamente!");
            
            this.setModoFormulario(ModoFormulario.ELIMINADO);
        }
        catch (Exception ex) {
            Mensajes.showException(this.getClass(), ex);
        }
    }
    
    public void limpiar() {
        this.cCoSituaciondoc.setValue(null);
        this.cDsSituaciondoc.setValue(null);
        this.cFeAlta.setValue(null);
        this.cFeDesactivo.setValue(null);
    }
    
    public String volver() {
        try {
            if (this.modoFormulario == ModoFormulario.EDICION) {
                recuperarRegistro(this.bdTSituaciondoc.getIdSituaciondoc());
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
        if (Validation.isNullOrEmpty(this.cCoSituaciondoc.getValue())) {
            throw new RequiredFieldException(this.cCoSituaciondoc.getLabel());
        }
        if (Validation.isNullOrEmpty(this.cDsSituaciondoc.getValue())) {
            throw new RequiredFieldException(this.cDsSituaciondoc.getLabel());
        }
        if (Validation.isNullOrEmpty(this.cFeAlta.getValue())) {
            throw new RequiredFieldException(this.cFeAlta.getLabel());
        }
    }
    
    private void protegerCampos() throws Exception {
        switch (this.modoFormulario) {
            case CONSULTA, ELIMINADO -> {
                this.cCoSituaciondoc.setProtegido(true);
                this.cDsSituaciondoc.setProtegido(true);
                this.cFeAlta.setProtegido(true);
                this.cFeDesactivo.setProtegido(true);
            }
            case EDICION -> {
                this.cCoSituaciondoc.setProtegido(false);
                this.cDsSituaciondoc.setProtegido(false);
                this.cFeAlta.setProtegido(false);
                this.cFeDesactivo.setProtegido(false);
            }
            case ALTA -> {
                limpiar();
                this.cCoSituaciondoc.setProtegido(false);
                this.cDsSituaciondoc.setProtegido(false);
                this.cFeAlta.setProtegido(false);
                this.cFeDesactivo.setProtegido(false);
            }
            default -> throw new FormModeException();
        }
    }
    
    private void recuperarRegistro(Integer idSituaciondoc) throws Exception {
        StTSituaciondoc stTSituaciondoc = new StTSituaciondoc();
        this.bdTSituaciondoc = stTSituaciondoc.item(idSituaciondoc, null);
        if (this.bdTSituaciondoc == null) {
            throw new RegistryNotFoundException();
        }
        
        cCoSituaciondoc.setValue(this.bdTSituaciondoc.getCoSituaciondoc());
        cDsSituaciondoc.setValue(this.bdTSituaciondoc.getDsSituaciondoc());
        cFeAlta.setValue(this.bdTSituaciondoc.getFeAlta());
        cFeDesactivo.setValue(this.bdTSituaciondoc.getFeDesactivo());
    }

    public CampoWebCodigo getcCoSituaciondoc() {
        return cCoSituaciondoc;
    }

    public void setcCoSituaciondoc(CampoWebCodigo cCoSituaciondoc) {
        this.cCoSituaciondoc = cCoSituaciondoc;
    }

    public CampoWebDescripcion getcDsSituaciondoc() {
        return cDsSituaciondoc;
    }

    public void setcDsSituaciondoc(CampoWebDescripcion cDsSituaciondoc) {
        this.cDsSituaciondoc = cDsSituaciondoc;
    }

    public BdTSituaciondoc getBdTSituaciondoc() {
        return bdTSituaciondoc;
    }

    public void setBdTSituaciondoc(BdTSituaciondoc bdTSituaciondoc) {
        this.bdTSituaciondoc = bdTSituaciondoc;
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
