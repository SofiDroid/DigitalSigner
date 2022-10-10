package configuracion.seguridad.usuarios;

import basedatos.servicios.StATokenusuario;
import basedatos.servicios.StTUsuario;
import basedatos.tablas.BdATokenusuario;
import basedatos.tablas.BdTUsuario;
import excepciones.FormModeException;
import excepciones.RegistryNotFoundException;
import excepciones.RequiredFieldException;
import init.AppInit;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import javax.el.ELContext;
import javax.faces.context.FacesContext;
import seguridad.utils.SegUtils;
import tomcat.persistence.EntityManager;
import utilidades.CampoWebCheck;
import utilidades.CampoWebCodigo;
import utilidades.CampoWebDescripcion;
import utilidades.CampoWebFecha;
import utilidades.CampoWebNumero;
import utilidades.CampoWebPassword;
import utilidades.Mensajes;
import utilidades.ModoFormulario;
import utilidades.Msg;
import utilidades.Validation;

/**
 *
 * @author ihuegal
 */
public class EdicionUsuarios implements Serializable {
    private Object parent = null;
    private String paginaRetorno = null;
    
    private CampoWebCodigo cCoNIF = null;
    private CampoWebCodigo cCoUsuario = null;
    private CampoWebPassword cCoPassword1 = null;
    private CampoWebPassword cCoPassword2 = null;
    private CampoWebDescripcion cDsNombre= null;
    private CampoWebDescripcion cDsApellido1 = null;
    private CampoWebDescripcion cDsApellido2 = null;
    private CampoWebNumero cEnIntentos = null;
    private CampoWebNumero cEnIntentosMax = null;
    private CampoWebCheck cBoAdmin = null;
    private CampoWebFecha cFeAlta = null;
    private CampoWebFecha cFeDesactivo = null;
    private CampoWebDescripcion cToken = null;
    
    BdTUsuario bdTUsuario = null;

    private ModoFormulario modoFormulario = ModoFormulario.CONSULTA;
    
    public EdicionUsuarios(Object parent) {
        init(parent, null);
    }
    
    public EdicionUsuarios(Object parent, Integer idUsuario) {
        init(parent, idUsuario);
    }
    
    private void init(Object parent, Integer idUsuario) {
        try {
            this.parent = parent;
    
            this.cCoNIF = new CampoWebCodigo();
            this.cCoNIF.setLabel(Msg.getString("lbl_EdicionUsuarios_CoNIF"));
            this.cCoNIF.setWidthLabel("100px");
            this.cCoNIF.setRequired(true);
            
            this.cCoUsuario = new CampoWebCodigo();
            this.cCoUsuario.setLabel(Msg.getString("lbl_EdicionUsuarios_CoUsuario"));
            this.cCoUsuario.setWidthLabel("100px");
            this.cCoUsuario.setAutocomplete(false);
            this.cCoUsuario.setRequired(true);
            
            this.cCoPassword1 = new CampoWebPassword();
            this.cCoPassword1.setLabel(Msg.getString("lbl_EdicionUsuarios_CoPassword1"));
            this.cCoPassword1.setWidthLabel("100px");
            this.cCoPassword1.setSize(20);
            this.cCoPassword1.setFeedback(true);
            this.cCoPassword1.setToggleMask(true);
            this.cCoPassword1.setMaxlength("20");
            this.cCoPassword1.setAutocomplete(false);
            this.cCoPassword1.setIgnoreLastPass(true);
            this.cCoPassword1.setRequired(true);
            
            this.cCoPassword2 = new CampoWebPassword();
            this.cCoPassword2.setLabel(Msg.getString("lbl_EdicionUsuarios_CoPassword2"));
            this.cCoPassword2.setWidthLabel("100px");
            this.cCoPassword2.setSize(20);
            this.cCoPassword2.setFeedback(true);
            this.cCoPassword2.setToggleMask(true);
            this.cCoPassword2.setMaxlength("20");
            this.cCoPassword2.setAutocomplete(false);
            this.cCoPassword2.setIgnoreLastPass(true);
            this.cCoPassword2.setRequired(true);
            
            this.cDsNombre = new CampoWebDescripcion();
            this.cDsNombre.setLabel(Msg.getString("lbl_EdicionUsuarios_DsNombre"));
            this.cDsNombre.setWidthLabel("100px");
            this.cDsNombre.setRequired(true);
            
            this.cDsApellido1 = new CampoWebDescripcion();
            this.cDsApellido1.setLabel(Msg.getString("lbl_EdicionUsuarios_DsApellido1"));
            this.cDsApellido1.setWidthLabel("100px");
            this.cDsApellido1.setRequired(true);
            
            this.cDsApellido2 = new CampoWebDescripcion();
            this.cDsApellido2.setLabel(Msg.getString("lbl_EdicionUsuarios_DsApellido2"));
            this.cDsApellido2.setWidthLabel("100px");
            
            this.cEnIntentos = new CampoWebNumero();
            this.cEnIntentos.setLabel(Msg.getString("lbl_EdicionUsuarios_EnIntentos"));
            this.cEnIntentos.setWidthLabel("100px");
            this.cEnIntentos.setSize(2);
            this.cEnIntentos.setRequired(true);
            this.cEnIntentos.setMinValue(0);
            this.cEnIntentos.setMaxValue(10);
            this.cEnIntentos.setNumDecimales(0);
            
            this.cEnIntentosMax = new CampoWebNumero();
            this.cEnIntentosMax.setLabel(Msg.getString("lbl_EdicionUsuarios_EnIntentosMax"));
            this.cEnIntentosMax.setWidthLabel("100px");
            this.cEnIntentosMax.setSize(2);
            this.cEnIntentosMax.setRequired(true);
            this.cEnIntentosMax.setMinValue(0);
            this.cEnIntentosMax.setMaxValue(10);
            this.cEnIntentosMax.setNumDecimales(0);
            
            this.cBoAdmin = new CampoWebCheck();
            this.cBoAdmin.setLabel(Msg.getString("lbl_EdicionUsuarios_BoAdmin"));
            this.cBoAdmin.setWidthLabel("100px");
            
            this.cFeAlta = new CampoWebFecha();
            this.cFeAlta.setLabel(Msg.getString("lbl_EdicionUsuarios_FeAlta"));
            this.cFeAlta.setWidthLabel("100px");
            this.cFeAlta.setRequired(true);
            
            this.cFeDesactivo = new CampoWebFecha();
            this.cFeDesactivo.setLabel(Msg.getString("lbl_EdicionUsuarios_FeDesactivo"));
            this.cFeDesactivo.setWidthLabel("100px");
            
            this.cToken = new CampoWebDescripcion();
            this.cToken.setLabel(Msg.getString("lbl_EdicionUsuarios_DsToken"));
            this.cToken.setWidthLabel("100px");
            this.cToken.setMaxlength("64");
            this.cToken.setProtegido(true);
            
            
            this.setModoFormulario(ModoFormulario.CONSULTA);

            if (idUsuario != null) {
                recuperarRegistro(idUsuario);
            }
            else {
                this.setModoFormulario(ModoFormulario.ALTA);
                this.cToken.setValue("");
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
            // INICIO TRANSACCION
            try (EntityManager entityManager = AppInit.getEntityManager())
            {
                entityManager.getTransaction().begin();
                try {
                    //ALTA
                    if (this.modoFormulario == ModoFormulario.ALTA) {
                        this.bdTUsuario = new BdTUsuario();
                        this.bdTUsuario.setCoNIF(this.cCoNIF.getValue());
                        this.bdTUsuario.setCoUsuario(this.cCoUsuario.getValue());
                        this.bdTUsuario.setCoPassword(this.cCoPassword1.getValue());
                        this.bdTUsuario.setDsNombre(this.cDsNombre.getValue());
                        this.bdTUsuario.setDsApellido1(this.cDsApellido1.getValue());
                        this.bdTUsuario.setDsApellido2(this.cDsApellido2.getValue());
                        this.bdTUsuario.setEnIntentos(this.cEnIntentos.getValueInteger());
                        this.bdTUsuario.setEnIntentosmax(this.cEnIntentosMax.getValueInteger());
                        this.bdTUsuario.setBoAdmin(this.cBoAdmin.getValue());
                        this.bdTUsuario.setFeAlta(this.cFeAlta.getValue());
                        this.bdTUsuario.setFeDesactivo(this.cFeDesactivo.getValue());

                        StTUsuario stTUsuario = new StTUsuario();
                        stTUsuario.alta(this.bdTUsuario, entityManager);

                        //GUARDAR TOKEN GENERADO
                        if (this.cToken.getValue() != null && !this.cToken.getValue().isBlank()) {
                            BdATokenusuario newBdATokenusuario = new BdATokenusuario();
                            newBdATokenusuario.setIdUsuario(this.bdTUsuario.getIdUsuario());
                            newBdATokenusuario.setDsToken(this.cToken.getValue());
                            newBdATokenusuario.setFeAlta(new Date());

                            StATokenusuario stATokenusuario = new StATokenusuario();
                            stATokenusuario.alta(newBdATokenusuario, entityManager);
                        }
                        
                        entityManager.getTransaction().commit();

                        if (this.parent instanceof FiltroUsuarios filtroUsuarios) {
                            filtroUsuarios.getDsResultado().refrescarDatos();
                        }

                        Mensajes.showInfo("Información", "Alta realizada correctamente!");
                    }

                    //ACTUALIZACION
                    if (this.modoFormulario == ModoFormulario.EDICION) {
                        this.bdTUsuario.setCoNIF(this.cCoNIF.getValue());
                        this.bdTUsuario.setCoUsuario(this.cCoUsuario.getValue());
                        this.bdTUsuario.setCoPassword(this.cCoPassword1.getValue());
                        this.bdTUsuario.setDsNombre(this.cDsNombre.getValue());
                        this.bdTUsuario.setDsApellido1(this.cDsApellido1.getValue());
                        this.bdTUsuario.setDsApellido2(this.cDsApellido2.getValue());
                        this.bdTUsuario.setEnIntentos(this.cEnIntentos.getValueInteger());
                        this.bdTUsuario.setEnIntentosmax(this.cEnIntentosMax.getValueInteger());
                        this.bdTUsuario.setBoAdmin(this.cBoAdmin.getValue());
                        this.bdTUsuario.setFeAlta(this.cFeAlta.getValue());
                        this.bdTUsuario.setFeDesactivo(this.cFeDesactivo.getValue());

                        StTUsuario stTUsuario = new StTUsuario();
                        stTUsuario.actualiza(this.bdTUsuario, entityManager);

                        //GUARDAR TOKEN GENERADO
                        if (this.cToken.getValue() != null && !this.cToken.getValue().isBlank()) {
                            BdATokenusuario filtroBdATokenusuario = new BdATokenusuario();
                            filtroBdATokenusuario.setIdUsuario(this.bdTUsuario.getIdUsuario());
                            filtroBdATokenusuario.setDsToken(this.cToken.getValue());
                            StATokenusuario stATokenusuario = new StATokenusuario();
                            ArrayList<BdATokenusuario> listaBdATokenusuario = stATokenusuario.filtro(filtroBdATokenusuario, entityManager);
                            //Solo si ha cambiado el token de ese usuario lo guardo
                            if (listaBdATokenusuario == null || listaBdATokenusuario.isEmpty()) {
                                //Desactivo si habia alguno.
                                stATokenusuario.desactivarToken(this.bdTUsuario.getIdUsuario(), entityManager);
                                
                                BdATokenusuario newBdATokenusuario = new BdATokenusuario();
                                newBdATokenusuario.setIdUsuario(this.bdTUsuario.getIdUsuario());
                                newBdATokenusuario.setDsToken(this.cToken.getValue());
                                newBdATokenusuario.setFeAlta(new Date());
                                stATokenusuario.alta(newBdATokenusuario, entityManager);
                            }
                        }

                        entityManager.getTransaction().commit();

                        if (this.parent instanceof FiltroUsuarios filtroUsuarios) {
                            ELContext elContext = FacesContext.getCurrentInstance().getELContext();
                            filtroUsuarios = (FiltroUsuarios)elContext.getELResolver().getValue(elContext, null, "filtroUsuarios");
                            filtroUsuarios.getDsResultado().actualizarFilaSeleccionada();
                        }

                        Mensajes.showInfo("Información", "Actualización realizada correctamente!");
                    }
            }
                catch (Exception ex) {
                    entityManager.getTransaction().rollback();
                    throw ex;
                }
            }
            // FIN TRANSACCION
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
            StTUsuario stTUsuario = new StTUsuario();
            stTUsuario.baja(this.bdTUsuario, null);
            
            if (this.parent instanceof FiltroUsuarios filtroUsuarios) {
                filtroUsuarios.getDsResultado().eliminarFilaSeleccionada();
            }

            Mensajes.showInfo("Información", "Registro eliminado correctamente!");
            
            this.setModoFormulario(ModoFormulario.ELIMINADO);
        }
        catch (Exception ex) {
            Mensajes.showException(this.getClass(), ex);
        }
    }
    
    public void generarToken() {
        try {
            this.cToken.setValue(SegUtils.generarTokenRandom());
        }
        catch (Exception ex) {
            Mensajes.showException(this.getClass(), ex);
        }
    }
    
    public void limpiar() {
        this.cCoNIF.setValue(null);
        this.cCoUsuario.setValue(null);
        this.cCoPassword1.setValue(null);
        this.cCoPassword2.setValue(null);
        this.cDsNombre.setValue(null);
        this.cDsApellido1.setValue(null);
        this.cDsApellido2.setValue(null);
        this.cEnIntentos.setValue(null);
        this.cEnIntentosMax.setValue(null);
        this.cBoAdmin.setValue(null);
        this.cFeAlta.setValue(null);
        this.cFeDesactivo.setValue(null);
    }
    
    public String volver() {
        try {
            if (this.modoFormulario == ModoFormulario.EDICION) {
                recuperarRegistro(this.bdTUsuario.getIdUsuario());
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
        if (Validation.isNullOrEmpty(this.cCoNIF.getValue())) {
            throw new RequiredFieldException(this.cCoNIF.getLabel());
        }
        if (Validation.isNullOrEmpty(this.cCoUsuario.getValue())) {
            throw new RequiredFieldException(this.cCoUsuario.getLabel());
        }
        if (Validation.isNullOrEmpty(this.cCoPassword1.getValue())) {
            throw new RequiredFieldException(this.cCoPassword1.getLabel());
        }
        if (Validation.isNullOrEmpty(this.cCoPassword2.getValue())) {
            throw new RequiredFieldException(this.cCoPassword2.getLabel());
        }
        if (Validation.isNullOrEmpty(this.cDsNombre.getValue())) {
            throw new RequiredFieldException(this.cDsNombre.getLabel());
        }
        if (Validation.isNullOrEmpty(this.cDsApellido1.getValue())) {
            throw new RequiredFieldException(this.cDsApellido1.getLabel());
        }
        if (Validation.isNullOrEmpty(this.cEnIntentos.getValueInteger())) {
            throw new RequiredFieldException(this.cEnIntentos.getLabel());
        }
        if (Validation.isNullOrEmpty(this.cEnIntentosMax.getValueInteger())) {
            throw new RequiredFieldException(this.cEnIntentosMax.getLabel());
        }
        if (Validation.isNullOrEmpty(this.cFeAlta.getValue())) {
            throw new RequiredFieldException(this.cFeAlta.getLabel());
        }
    }
    
    private void protegerCampos() throws Exception {
        switch (this.modoFormulario) {
            case CONSULTA, ELIMINADO -> {
                this.cCoNIF.setProtegido(true);
                this.cCoUsuario.setProtegido(true);
                this.cCoPassword1.setProtegido(true);
                this.cCoPassword2.setProtegido(true);
                this.cDsNombre.setProtegido(true);
                this.cDsApellido1.setProtegido(true);
                this.cDsApellido2.setProtegido(true);
                this.cEnIntentos.setProtegido(true);
                this.cEnIntentosMax.setProtegido(true);
                this.cBoAdmin.setProtegido(true);
                this.cFeAlta.setProtegido(true);
                this.cFeDesactivo.setProtegido(true);
            }
            case EDICION -> {
                this.cCoNIF.setProtegido(false);
                this.cCoUsuario.setProtegido(false);
                this.cCoPassword1.setProtegido(false);
                this.cCoPassword2.setProtegido(false);
                this.cDsNombre.setProtegido(false);
                this.cDsApellido1.setProtegido(false);
                this.cDsApellido2.setProtegido(false);
                this.cEnIntentos.setProtegido(false);
                this.cEnIntentosMax.setProtegido(false);
                this.cBoAdmin.setProtegido(false);
                this.cFeAlta.setProtegido(false);
                this.cFeDesactivo.setProtegido(false);
            }
            case ALTA -> {
                limpiar();
                this.cCoNIF.setProtegido(false);
                this.cCoUsuario.setProtegido(false);
                this.cCoPassword1.setProtegido(false);
                this.cCoPassword2.setProtegido(false);
                this.cDsNombre.setProtegido(false);
                this.cDsApellido1.setProtegido(false);
                this.cDsApellido2.setProtegido(false);
                this.cEnIntentos.setProtegido(false);
                this.cEnIntentosMax.setProtegido(false);
                this.cBoAdmin.setProtegido(false);
                this.cFeAlta.setProtegido(false);
                this.cFeDesactivo.setProtegido(false);
            }
            default -> throw new FormModeException();
        }
    }
    
    private void recuperarRegistro(Integer idUsuario) throws Exception {
        StTUsuario stTUsuario = new StTUsuario();
        this.bdTUsuario = stTUsuario.item(idUsuario, false, null);
        if (this.bdTUsuario == null) {
            throw new RegistryNotFoundException();
        }
        
        cCoNIF.setValue(this.bdTUsuario.getCoNIF());
        cCoUsuario.setValue(this.bdTUsuario.getCoUsuario());
        cCoPassword1.setValue(this.bdTUsuario.getCoPassword());
        cCoPassword2.setValue(this.bdTUsuario.getCoPassword());
        cDsNombre.setValue(this.bdTUsuario.getDsNombre());
        cDsApellido1.setValue(this.bdTUsuario.getDsApellido1());
        cDsApellido2.setValue(this.bdTUsuario.getDsApellido2());
        cEnIntentos.setValue(this.bdTUsuario.getEnIntentos());
        cEnIntentosMax.setValue(this.bdTUsuario.getEnIntentosmax());
        cBoAdmin.setValue(this.bdTUsuario.getBoAdmin());
        cFeAlta.setValue(this.bdTUsuario.getFeAlta());
        cFeDesactivo.setValue(this.bdTUsuario.getFeDesactivo());
        
        BdATokenusuario filtroBdATokenusuario = new BdATokenusuario();
        filtroBdATokenusuario.setIdUsuario(this.bdTUsuario.getIdUsuario());
        filtroBdATokenusuario.setFeAlta(new Date());
        filtroBdATokenusuario.setFeDesactivo(new Date());
        
        StATokenusuario stATokenusuario = new StATokenusuario();
        ArrayList<BdATokenusuario> listaBdATokenusuario = stATokenusuario.filtro(filtroBdATokenusuario, null);
        if (listaBdATokenusuario != null && !listaBdATokenusuario.isEmpty()) {
            this.cToken.setValue(listaBdATokenusuario.get(0).getDsToken());
        }
        else {
            this.cToken.setValue(null);
        }
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

    public CampoWebCodigo getcCoNIF() {
        return cCoNIF;
    }

    public void setcCoNIF(CampoWebCodigo cCoNIF) {
        this.cCoNIF = cCoNIF;
    }

    public CampoWebCodigo getcCoUsuario() {
        return cCoUsuario;
    }

    public void setcCoUsuario(CampoWebCodigo cCoUsuario) {
        this.cCoUsuario = cCoUsuario;
    }

    public CampoWebPassword getcCoPassword1() {
        return cCoPassword1;
    }

    public void setcCoPassword1(CampoWebPassword cCoPassword1) {
        this.cCoPassword1 = cCoPassword1;
    }

    public CampoWebPassword getcCoPassword2() {
        return cCoPassword2;
    }

    public void setcCoPassword2(CampoWebPassword cCoPassword2) {
        this.cCoPassword2 = cCoPassword2;
    }

    public CampoWebDescripcion getcDsNombre() {
        return cDsNombre;
    }

    public void setcDsNombre(CampoWebDescripcion cDsNombre) {
        this.cDsNombre = cDsNombre;
    }

    public CampoWebDescripcion getcDsApellido1() {
        return cDsApellido1;
    }

    public void setcDsApellido1(CampoWebDescripcion cDsApellido1) {
        this.cDsApellido1 = cDsApellido1;
    }

    public CampoWebDescripcion getcDsApellido2() {
        return cDsApellido2;
    }

    public void setcDsApellido2(CampoWebDescripcion cDsApellido2) {
        this.cDsApellido2 = cDsApellido2;
    }

    public CampoWebNumero getcEnIntentos() {
        return cEnIntentos;
    }

    public void setcEnIntentos(CampoWebNumero cEnIntentos) {
        this.cEnIntentos = cEnIntentos;
    }

    public CampoWebNumero getcEnIntentosMax() {
        return cEnIntentosMax;
    }

    public void setcEnIntentosMax(CampoWebNumero cEnIntentosMax) {
        this.cEnIntentosMax = cEnIntentosMax;
    }

    public CampoWebCheck getcBoAdmin() {
        return cBoAdmin;
    }

    public void setcBoAdmin(CampoWebCheck cBoAdmin) {
        this.cBoAdmin = cBoAdmin;
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

    public CampoWebDescripcion getcToken() {
        return cToken;
    }

    public void setcToken(CampoWebDescripcion cToken) {
        this.cToken = cToken;
    }

    public BdTUsuario getBdTUsuario() {
        return bdTUsuario;
    }

    public void setBdTUsuario(BdTUsuario bdTUsuario) {
        this.bdTUsuario = bdTUsuario;
    }
}
