package configuracion.gestion.situacionesXML;

import basedatos.servicios.StTSituacionxml;
import basedatos.tablas.BdTSituacionxml;
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
public class EdicionSituacionesXML implements Serializable {
    private Object parent = null;
    private String paginaRetorno = null;
    
    private CampoWebCodigo cCoSituacionXML = null;
    private CampoWebDescripcion cDsSituacionXML = null;
    private CampoWebFecha cFeAlta = null;
    private CampoWebFecha cFeDesactivo = null;
    
    BdTSituacionxml bdTSituacionXML = null;

    private ModoFormulario modoFormulario = ModoFormulario.CONSULTA;
    
    public EdicionSituacionesXML(Object parent) {
        init(parent, null);
    }
    
    public EdicionSituacionesXML(Object parent, Integer idSituacionXML) {
        init(parent, idSituacionXML);
    }
    
    private void init(Object parent, Integer idSituacionXML) {
        try {
            this.parent = parent;
    
            this.cCoSituacionXML = new CampoWebCodigo();
            this.cCoSituacionXML.setLabel(Msg.getString("lbl_EdicionSituacionesXML_CoSituacionXML"));
            this.cCoSituacionXML.setWidthLabel("100px");
            this.cCoSituacionXML.setRequired(true);
            
            this.cDsSituacionXML = new CampoWebDescripcion();
            this.cDsSituacionXML.setLabel(Msg.getString("lbl_EdicionSituacionesXML_DsSituacionXML"));
            this.cDsSituacionXML.setWidthLabel("100px");
            this.cDsSituacionXML.setRequired(true);
            
            this.cFeAlta = new CampoWebFecha();
            this.cFeAlta.setLabel(Msg.getString("lbl_EdicionSituacionesXML_FeAlta"));
            this.cFeAlta.setWidthLabel("100px");
            this.cFeAlta.setRequired(true);
            
            this.cFeDesactivo = new CampoWebFecha();
            this.cFeDesactivo.setLabel(Msg.getString("lbl_EdicionSituacionesXML_FeDesactivo"));
            this.cFeDesactivo.setWidthLabel("100px");
            
            this.setModoFormulario(ModoFormulario.CONSULTA);

            if (idSituacionXML != null) {
                recuperarRegistro(idSituacionXML);
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
                this.bdTSituacionXML = new BdTSituacionxml();
                this.bdTSituacionXML.setCoSituacionxml(this.cCoSituacionXML.getValue());
                this.bdTSituacionXML.setDsSituacionxml(this.cDsSituacionXML.getValue());
                this.bdTSituacionXML.setFeAlta(this.cFeAlta.getValue());
                this.bdTSituacionXML.setFeDesactivo(this.cFeDesactivo.getValue());
                
                StTSituacionxml stTSituacionXML = new StTSituacionxml();
                stTSituacionXML.alta(this.bdTSituacionXML, null);
                
                if (this.parent instanceof FiltroSituacionesXML filtroSituacionesXML) {
                    filtroSituacionesXML.getDsResultado().refrescarDatos();
                }
                
                Mensajes.showInfo("Información", "Alta realizada correctamente!");
            }
            
            //ACTUALIZACION
            if (this.modoFormulario == ModoFormulario.EDICION) {
                this.bdTSituacionXML.setCoSituacionxml(this.cCoSituacionXML.getValue());
                this.bdTSituacionXML.setDsSituacionxml(this.cDsSituacionXML.getValue());
                this.bdTSituacionXML.setFeAlta(this.cFeAlta.getValue());
                this.bdTSituacionXML.setFeDesactivo(this.cFeDesactivo.getValue());
                
                StTSituacionxml stTSituacionXML = new StTSituacionxml();
                stTSituacionXML.actualiza(this.bdTSituacionXML, null);
                
                if (this.parent instanceof FiltroSituacionesXML filtroSituacionesXML) {
                    ELContext elContext = FacesContext.getCurrentInstance().getELContext();
                    filtroSituacionesXML = (FiltroSituacionesXML)elContext.getELResolver().getValue(elContext, null, "filtroSituacionesXML");
                    filtroSituacionesXML.getDsResultado().actualizarFilaSeleccionada();
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
            StTSituacionxml stTSituacionXML = new StTSituacionxml();
            stTSituacionXML.baja(this.bdTSituacionXML, null);
            
            if (this.parent instanceof FiltroSituacionesXML filtroSituacionesXML) {
                filtroSituacionesXML.getDsResultado().eliminarFilaSeleccionada();
            }

            Mensajes.showInfo("Información", "Registro eliminado correctamente!");
            
            this.setModoFormulario(ModoFormulario.ELIMINADO);
        }
        catch (Exception ex) {
            Mensajes.showException(this.getClass(), ex);
        }
    }
    
    public void limpiar() {
        this.cCoSituacionXML.setValue(null);
        this.cDsSituacionXML.setValue(null);
        this.cFeAlta.setValue(null);
        this.cFeDesactivo.setValue(null);
    }
    
    public String volver() {
        try {
            if (this.modoFormulario == ModoFormulario.EDICION) {
                recuperarRegistro(this.bdTSituacionXML.getIdSituacionxml());
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
        if (Validation.isNullOrEmpty(this.cCoSituacionXML.getValue())) {
            throw new RequiredFieldException(this.cCoSituacionXML.getLabel());
        }
        if (Validation.isNullOrEmpty(this.cDsSituacionXML.getValue())) {
            throw new RequiredFieldException(this.cDsSituacionXML.getLabel());
        }
        if (Validation.isNullOrEmpty(this.cFeAlta.getValue())) {
            throw new RequiredFieldException(this.cFeAlta.getLabel());
        }
    }
    
    private void protegerCampos() throws Exception {
        switch (this.modoFormulario) {
            case CONSULTA, ELIMINADO -> {
                this.cCoSituacionXML.setProtegido(true);
                this.cDsSituacionXML.setProtegido(true);
                this.cFeAlta.setProtegido(true);
                this.cFeDesactivo.setProtegido(true);
            }
            case EDICION -> {
                this.cCoSituacionXML.setProtegido(false);
                this.cDsSituacionXML.setProtegido(false);
                this.cFeAlta.setProtegido(false);
                this.cFeDesactivo.setProtegido(false);
            }
            case ALTA -> {
                limpiar();
                this.cCoSituacionXML.setProtegido(false);
                this.cDsSituacionXML.setProtegido(false);
                this.cFeAlta.setProtegido(false);
                this.cFeDesactivo.setProtegido(false);
            }
            default -> throw new FormModeException();
        }
    }
    
    private void recuperarRegistro(Integer idSituacionXML) throws Exception {
        StTSituacionxml stTSituacionXML = new StTSituacionxml();
        this.bdTSituacionXML = stTSituacionXML.item(idSituacionXML, null);
        if (this.bdTSituacionXML == null) {
            throw new RegistryNotFoundException();
        }
        
        cCoSituacionXML.setValue(this.bdTSituacionXML.getCoSituacionxml());
        cDsSituacionXML.setValue(this.bdTSituacionXML.getDsSituacionxml());
        cFeAlta.setValue(this.bdTSituacionXML.getFeAlta());
        cFeDesactivo.setValue(this.bdTSituacionXML.getFeDesactivo());
    }

    public CampoWebCodigo getcCoSituacionXML() {
        return cCoSituacionXML;
    }

    public void setcCoSituacionXML(CampoWebCodigo cCoSituacionXML) {
        this.cCoSituacionXML = cCoSituacionXML;
    }

    public CampoWebDescripcion getcDsSituacionXML() {
        return cDsSituacionXML;
    }

    public void setcDsSituacionXML(CampoWebDescripcion cDsSituacionXML) {
        this.cDsSituacionXML = cDsSituacionXML;
    }

    public BdTSituacionxml getBdTSituacionXML() {
        return bdTSituacionXML;
    }

    public void setBdTSituacionXML(BdTSituacionxml bdTSituacionXML) {
        this.bdTSituacionXML = bdTSituacionXML;
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
