package configuracion.seguridad.unidades;

import basedatos.servicios.StTUnidad;
import basedatos.tablas.BdTUnidad;
import excepciones.FormModeException;
import excepciones.RegistryNotFoundException;
import java.io.Serializable;
import utilidades.CampoWebCodigo;
import utilidades.CampoWebDescripcion;
import utilidades.CampoWebFecha;
import utilidades.Mensajes;
import utilidades.ModoFormulario;

/**
 *
 * @author ihuegal
 */
public class EdicionUnidades implements Serializable {
    private String paginaRetorno = null;
    
    private CampoWebCodigo cCoUnidad = null;
    private CampoWebDescripcion cDsUnidad= null;
    private CampoWebFecha cFeAlta = null;
    private CampoWebFecha cFeDesactivo = null;
    
    BdTUnidad bdTUnidad = null;

    private ModoFormulario modoFormulario = ModoFormulario.CONSULTA;
    
    public EdicionUnidades() {
        init(null);
    }
    
    public EdicionUnidades(Integer idUnidad) {
        init(idUnidad);
    }
    
    private void init(Integer idUnidad) {
        try {
            this.cCoUnidad = new CampoWebCodigo();
            this.cDsUnidad = new CampoWebDescripcion();
            this.cFeAlta = new CampoWebFecha();
            this.cFeDesactivo = new CampoWebFecha();
            this.setModoFormulario(ModoFormulario.CONSULTA);

            if (idUnidad != null) {
                recuperarRegistro(idUnidad);
            }
            else {
                this.modoFormulario = ModoFormulario.ALTA;
            }
        }
        catch (Exception ex) {
            this.modoFormulario = ModoFormulario.CONSULTA;
            new Mensajes().showException(this.getClass(), ex);
        }
    }

    public void guardar() {
        try {
            this.setModoFormulario(ModoFormulario.CONSULTA);
        }
        catch (Exception ex) {
            new Mensajes().showException(this.getClass(), ex);
        }
    }
    
    public void modificar() {
        try {
            this.setModoFormulario(ModoFormulario.EDICION);
        }
        catch (Exception ex) {
            new Mensajes().showException(this.getClass(), ex);
        }
    }
    
    public void eliminar() {
        try {
            this.setModoFormulario(ModoFormulario.CONSULTA);
        }
        catch (Exception ex) {
            new Mensajes().showException(this.getClass(), ex);
        }
    }
    
    public void limpiar() {
        this.cCoUnidad.setValue(null);
        this.cDsUnidad.setValue(null);
        this.cFeAlta.setValue(null);
        this.cFeDesactivo.setValue(null);
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
            new Mensajes().showException(this.getClass(), ex);
        }
        return null;
    }

    private void protegerCampos() throws Exception {
        switch (this.modoFormulario) {
            case CONSULTA -> {
                this.cCoUnidad.setProtegido(true);
                this.cDsUnidad.setProtegido(true);
                this.cFeAlta.setProtegido(true);
                this.cFeDesactivo.setProtegido(true);
            }
            case EDICION -> {
                this.cCoUnidad.setProtegido(false);
                this.cDsUnidad.setProtegido(false);
                this.cFeAlta.setProtegido(false);
                this.cFeDesactivo.setProtegido(false);
            }
            case ALTA -> {
                limpiar();
                this.cCoUnidad.setProtegido(false);
                this.cDsUnidad.setProtegido(false);
                this.cFeAlta.setProtegido(false);
                this.cFeDesactivo.setProtegido(false);
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
}
