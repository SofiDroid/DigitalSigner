package configuracion.seguridad.usuarios;

import basedatos.ColumnBase;
import basedatos.ColumnCabecera;
import basedatos.DataSet;
import basedatos.Row;
import basedatos.RowCabecera;
import basedatos.servicios.StATokenusuario;
import basedatos.servicios.StAUniusu;
import basedatos.servicios.StAUsutipousu;
import basedatos.servicios.StTUsuario;
import basedatos.tablas.BdATokenusuario;
import basedatos.tablas.BdAUniusu;
import basedatos.tablas.BdAUsutipousu;
import basedatos.tablas.BdTUsuario;
import excepciones.FormModeException;
import excepciones.RegistryNotFoundException;
import excepciones.RequiredFieldException;
import init.AppInit;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import javax.el.ELContext;
import javax.faces.context.FacesContext;
import seguridad.utils.SegUtils;
import tomcat.persistence.EntityManager;
import tomcat.persistence.exceptions.SQLReferenceException;
import utilidades.CampoWebCheck;
import utilidades.CampoWebCodigo;
import utilidades.CampoWebDescripcion;
import utilidades.CampoWebFecha;
import utilidades.CampoWebLupa;
import utilidades.CampoWebNumero;
import utilidades.CampoWebPassword;
import utilidades.Mensajes;
import utilidades.ModoFormulario;
import utilidades.Msg;
import utilidades.Session;
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
    
    private CampoWebLupa cUnidad = null;
    private DataSet dsUnidades = null;
    
    private CampoWebLupa cPerfil = null;
    private DataSet dsPerfiles = null;
    
    private BdTUsuario bdTUsuario = null;
    private ArrayList<Integer> listaUnidadesEliminadasId = null;
    private ArrayList<Integer> listaPerfilesEliminadosId = null;

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
            this.cFeAlta.setValue(new Date());
            this.cFeAlta.setRequired(true);
            
            this.cFeDesactivo = new CampoWebFecha();
            this.cFeDesactivo.setLabel(Msg.getString("lbl_EdicionUsuarios_FeDesactivo"));
            this.cFeDesactivo.setWidthLabel("100px");
            
            this.cToken = new CampoWebDescripcion();
            this.cToken.setLabel(Msg.getString("lbl_EdicionUsuarios_DsToken"));
            this.cToken.setWidthLabel("100px");
            this.cToken.setMaxlength("64");
            this.cToken.setProtegido(true);
            
            this.cUnidad = new CampoWebLupa();
            this.cUnidad.setLabel(Msg.getString("lbl_EdicionUsuarios_Unidad"));
            this.cUnidad.setWidthLabel("100px");
            String sql = "SELECT ID_UNIDAD, CO_UNIDAD + ' - ' + DS_UNIDAD as Unidad FROM BD_T_UNIDAD";
            this.cUnidad.setConsulta(sql);
            this.cUnidad.setColumnaID("ID_UNIDAD");
            this.cUnidad.setColumnaLabel("Unidad");
            this.cUnidad.setWidth("20em");
            this.cUnidad.setRequired(false);

            this.dsUnidades = new DataSet();
            this.listaUnidadesEliminadasId = new ArrayList<>();
            
            this.cPerfil = new CampoWebLupa();
            this.cPerfil.setLabel(Msg.getString("lbl_EdicionUsuarios_Perfil"));
            this.cPerfil.setWidthLabel("100px");
            sql = "SELECT ID_TIPOUSUARIO, CO_TIPOUSUARIO + ' - ' + DS_TIPOUSUARIO as Perfil FROM BD_T_TIPOUSUARIO";
            this.cPerfil.setConsulta(sql);
            this.cPerfil.setColumnaID("ID_TIPOUSUARIO");
            this.cPerfil.setColumnaLabel("Perfil");
            this.cPerfil.setWidth("20em");
            this.cPerfil.setRequired(false);

            this.dsPerfiles = new DataSet();
            this.listaPerfilesEliminadosId = new ArrayList<>();
            
            this.setModoFormulario(ModoFormulario.CONSULTA);

            if (idUsuario != null) {
                recuperarRegistro(idUsuario);
                inicializarDataSetUnidades(idUsuario);
            }
            else {
                this.setModoFormulario(ModoFormulario.ALTA);
                this.cToken.setValue("");
                inicializarDataSetUnidades(idUsuario);
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

    private void inicializarDataSetUnidades(Integer idUsuario) throws Exception {
        String sql = """
            SELECT 
                T1.ID_UNIDAD,
                T1.ID_USUARIO,
                uni.CO_UNIDAD + ' - ' + uni.DS_UNIDAD as Unidad,
                T1.FE_ALTA,
                T1.FE_DESACTIVO
            FROM 
                BD_A_UNIUSU T1
            INNER JOIN
                BD_T_UNIDAD uni ON (uni.ID_UNIDAD = T1.ID_UNIDAD)
            WHERE 1 = 1
            AND T1.ID_USUARIO = :ID_USUARIO
            """;
        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_USUARIO", idUsuario);

        this.dsUnidades = new DataSet(sql, parametros, "ID_UNIDAD");
        
        this.cUnidad.setListaNotIN_Clear();
        if (this.dsUnidades.getRowsCount() > 0) {
            for (Row itemRow : this.dsUnidades.getRows()) {
                this.cUnidad.setListaNotIN_Add(itemRow.getColumnaID().getValueString());
            }
        }
        
        // Establecer formato de salida
        RowCabecera cabecera = this.getDsUnidades().getCabecera();

        cabecera.getColumnName("ID_UNIDAD")
                .setVisible(false);

        cabecera.getColumnName("ID_USUARIO")
                .setVisible(false);

        cabecera.getColumnName("Unidad")
                .setTitle("Unidad")
                .setWidth("100%");

        cabecera.getColumnName("FE_ALTA")
                .setTitle("F. Alta")
                .setWidth("6rem");

        cabecera.getColumnName("FE_DESACTIVO")
                .setTitle("F. Desactivo")
                .setWidth("6rem");
        
        this.dsUnidades.newColumn("btnEditar");
        cabecera.getColumnName("btnEditar")
                .setTitle("")
                .setTooltip("Editar")
                .setAlign(ColumnCabecera.ALIGN.CENTER)
                .setWidth("21px")
                .setTipo(ColumnBase.Tipo.BOTON)
                .setRendered(true)
                .setClase(this)
                .setIcon("pi pi-pencil")
                .setStyleClass("ui-button-info")
                .setMethod(this.getClass().getMethod("editarUnidad"))
                .setOncomplete("PF('dlgPerfiles').show()")
                .setUpdate("formulario:panelUnidades,formulario:mensaje");

        this.dsUnidades.newColumn("btnEliminar");
        cabecera.getColumnName("btnEliminar")
                .setTitle("")
                .setTooltip("Eliminar")
                .setAlign(ColumnCabecera.ALIGN.CENTER)
                .setWidth("30px")
                .setTipo(ColumnBase.Tipo.BOTON_EDICION)
                .setClase(this)
                .setIcon("pi pi-times-circle")
                .setStyleClass("ui-button-danger")
                .setMethod(this.getClass().getMethod("eliminarUnidad"))
                .setUpdate("formulario:panelUnidades,formulario:mensaje");
    }
    
    private void inicializarDataSetPerfiles(Integer idUsuario, Integer idUnidad) throws Exception {
        String sql = """
            SELECT 
                T1.ID_TIPOUSUARIO,
                tipousu.ID_UNIDAD,
                T1.ID_USUARIO,
                tipousu.CO_TIPOUSUARIO + ' - ' + tipousu.DS_TIPOUSUARIO as Perfil,
                T1.FE_ALTA,
                T1.FE_DESACTIVO
            FROM 
                BD_A_USUTIPOUSU T1
            INNER JOIN
                BD_T_TIPOUSUARIO tipousu ON (tipousu.ID_TIPOUSUARIO = T1.ID_TIPOUSUARIO)
            WHERE 1 = 1
            AND T1.ID_USUARIO = :ID_USUARIO
            AND tipousu.ID_UNIDAD = :ID_UNIDAD
            """;
        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_USUARIO", idUsuario);
        parametros.put("ID_UNIDAD", idUnidad);

        this.dsPerfiles = new DataSet(sql, parametros, "ID_TIPOUSUARIO");
        
        this.cPerfil.setListaNotIN_Clear();
        if (this.dsPerfiles.getRowsCount() > 0) {
            for (Row itemRow : this.dsPerfiles.getRows()) {
                this.cPerfil.setListaNotIN_Add(itemRow.getColumnaID().getValueString());
            }
        }
        
        // Establecer formato de salida
        RowCabecera cabecera = this.getDsPerfiles().getCabecera();

        cabecera.getColumnName("ID_TIPOUSUARIO")
                .setVisible(false);

        cabecera.getColumnName("ID_UNIDAD")
                .setVisible(false);

        cabecera.getColumnName("ID_USUARIO")
                .setVisible(false);

        cabecera.getColumnName("Perfil")
                .setTitle("Perfil")
                .setWidth("100%");

        cabecera.getColumnName("FE_ALTA")
                .setTitle("F. Alta")
                .setWidth("6rem");

        cabecera.getColumnName("FE_DESACTIVO")
                .setTitle("F. Desactivo")
                .setWidth("6rem");
        
        this.dsPerfiles.newColumn("btnEliminar");
        cabecera.getColumnName("btnEliminar")
                .setTitle("")
                .setTooltip("Eliminar")
                .setAlign(ColumnCabecera.ALIGN.CENTER)
                .setWidth("30px")
                .setTipo(ColumnBase.Tipo.BOTON_EDICION)
                .setClase(this)
                .setIcon("pi pi-times-circle")
                .setStyleClass("ui-button-danger")
                .setMethod(this.getClass().getMethod("eliminarPerfil"))
                .setUpdate("formulario:panelPerfil,formulario:tblPefiles:tblPefiles,formulario:mensaje");
    }
    
    public void editarUnidad() {
        try {
            this.cPerfil.setListaNotIN_Clear();
            inicializarDataSetPerfiles(this.bdTUsuario.getIdUsuario(), this.dsUnidades.getSelectedRow().getColumnaID().getValueInteger());
            if (this.dsPerfiles != null) {
                this.dsPerfiles.setModoFormulario(modoFormulario);
            }
            String sql = """
                         SELECT 
                            ID_TIPOUSUARIO, 
                            CO_TIPOUSUARIO + ' - ' + DS_TIPOUSUARIO as Perfil 
                         FROM 
                            BD_T_TIPOUSUARIO
                         WHERE
                            ID_UNIDAD = :ID_UNIDAD
                         """;
            sql = sql.replace(":ID_UNIDAD", this.dsUnidades.getSelectedRow().getColumnaID().getValueString());
            this.cPerfil.setConsulta(sql);
        } catch (Exception ex) {
            Mensajes.showException(this.getClass(), ex);
        }
    }
    
    public void eliminarUnidad() {
        try {
            Integer idUnidad = this.dsUnidades.getSelectedRow().getColumnaID().getValueInteger();
            if (idUnidad != null) {
                listaUnidadesEliminadasId.add(this.dsUnidades.getSelectedRow().getColumnaID().getValueInteger());
                this.cUnidad.setListaNotIN_Remove(idUnidad.toString());
            }
            this.dsUnidades.getRows().remove(this.dsUnidades.getSelectedRow());
            if (this.dsUnidades != null) {
                for (int i = 0; i < this.dsUnidades.getRowsCount(); i++) {
                    Row itemRow = this.dsUnidades.getRows().get(i);
                    itemRow.setIndex(i);
                }
            }
        } catch (Exception ex) {
            Mensajes.showException(this.getClass(), ex);
        }
    }
    
    public void eliminarPerfil() {
        try {
            Integer idTipousuario = this.dsPerfiles.getSelectedRow().getColumnaID().getValueInteger();
            if (idTipousuario != null) {
                listaPerfilesEliminadosId.add(this.dsPerfiles.getSelectedRow().getColumnaID().getValueInteger());
                this.cPerfil.setListaNotIN_Remove(idTipousuario.toString());
            }
            this.dsPerfiles.getRows().remove(this.dsPerfiles.getSelectedRow());
            if (this.dsPerfiles != null) {
                for (int i = 0; i < this.dsPerfiles.getRowsCount(); i++) {
                    Row itemRow = this.dsPerfiles.getRows().get(i);
                    itemRow.setIndex(i);
                }
            }
        } catch (Exception ex) {
            Mensajes.showException(this.getClass(), ex);
        }
    }
    
    public void addUnidad() {
        try {
            if (this.cUnidad.getValue() != null) {
                //ALTA
                Row newRow = this.dsUnidades.newRow();
                newRow.getColumnName("ID_USUARIO").setValue(null);
                newRow.getColumnName("ID_UNIDAD").setValue(this.cUnidad.getId());
                newRow.getColumnName("Unidad").setValue(this.cUnidad.getValue().getLabel());
                newRow.getColumnName("FE_ALTA").setValue(new Date());
                newRow.getColumnName("FE_DESACTIVO").setValue(null);

                this.dsUnidades.getRows().add(newRow);
                this.cUnidad.setListaNotIN_Add(this.cUnidad.getId().toString());
                this.cUnidad.setId(null);
            }
        } catch (Exception ex) {
            Mensajes.showException(this.getClass(), ex);
        }
    }
    
    public void addPerfil() {
        try {
            if (this.cPerfil.getValue() != null) {
                //ALTA
                Row newRow = this.dsPerfiles.newRow();
                newRow.getColumnName("ID_TIPOUSUARIO").setValue(this.cPerfil.getId());
                newRow.getColumnName("ID_USUARIO").setValue(this.bdTUsuario.getIdUsuario());
                newRow.getColumnName("ID_UNIDAD").setValue(this.dsUnidades.getSelectedRow().getColumnaID().getValueInteger());
                newRow.getColumnName("Perfil").setValue(this.cPerfil.getValue().getLabel());
                newRow.getColumnName("FE_ALTA").setValue(new Date());
                newRow.getColumnName("FE_DESACTIVO").setValue(null);

                this.dsPerfiles.getRows().add(newRow);
                this.cPerfil.setListaNotIN_Add(this.cPerfil.getId().toString());
                this.cPerfil.setId(null);
            }
        } catch (Exception ex) {
            Mensajes.showException(this.getClass(), ex);
        }
    }
    
    public void guardarPerfil() {
        try {
            // INICIO TRANSACCION
            try (EntityManager entityManager = AppInit.getEntityManager())
            {
                entityManager.getTransaction().begin();
                try {
                    
                    for (Integer idTipousuario : listaPerfilesEliminadosId) {
                        BdAUsutipousu delBdAUsutipousu = new BdAUsutipousu();
                        delBdAUsutipousu.setIdUsuario(this.bdTUsuario.getIdUsuario());
                        delBdAUsutipousu.setIdTipousuario(idTipousuario);

                        StAUsutipousu stAUsutipousu = new StAUsutipousu(Session.getDatosUsuario());
                        stAUsutipousu.baja(delBdAUsutipousu, entityManager);
                    }

                    if (this.dsPerfiles != null) {
                        for (Row itemRow : this.dsPerfiles.getRows()) {
                            BdAUsutipousu newBdAUsutipousu = new BdAUsutipousu();
                            newBdAUsutipousu.setIdUsuario(this.bdTUsuario.getIdUsuario());
                            newBdAUsutipousu.setIdTipousuario(itemRow.getColumnaID().getValueInteger());

                            StAUsutipousu stAUsutipousu = new StAUsutipousu(Session.getDatosUsuario());
                            ArrayList<BdAUsutipousu> listaBdAUsutipousu = stAUsutipousu.filtro(newBdAUsutipousu, entityManager);
                            if (listaBdAUsutipousu == null || listaBdAUsutipousu.isEmpty()) {
                                newBdAUsutipousu.setFeAlta((Date)itemRow.getColumnName("FE_ALTA").getValue());
                                newBdAUsutipousu.setFeDesactivo((Date)itemRow.getColumnName("FE_DESACTIVO").getValue());

                                stAUsutipousu.alta(newBdAUsutipousu, entityManager);
                            }
                        }
                    }
                    
                    entityManager.getTransaction().commit();
                    
                    Mensajes.showInfo(Msg.getString("Informacion"), Msg.getString("update_OK"));
                }
                catch (Exception ex) {
                    entityManager.getTransaction().rollback();
                    throw ex;
                }
            }
            // FIN TRANSACCION
        } catch (Exception ex) {
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

                        StTUsuario stTUsuario = new StTUsuario(Session.getDatosUsuario());
                        stTUsuario.alta(this.bdTUsuario, entityManager);

                        //GUARDAR TOKEN GENERADO
                        if (this.cToken.getValue() != null && !this.cToken.getValue().isBlank()) {
                            BdATokenusuario newBdATokenusuario = new BdATokenusuario();
                            newBdATokenusuario.setIdUsuario(this.bdTUsuario.getIdUsuario());
                            newBdATokenusuario.setDsToken(this.cToken.getValue());
                            newBdATokenusuario.setFeAlta(new Date());

                            StATokenusuario stATokenusuario = new StATokenusuario(Session.getDatosUsuario());
                            stATokenusuario.alta(newBdATokenusuario, entityManager);
                        }
                        
                        if (this.dsUnidades != null) {
                            for (Row itemRow : this.dsUnidades.getRows()) {
                                BdAUniusu newBdAUniusu = new BdAUniusu();
                                newBdAUniusu.setIdUsuario(this.bdTUsuario.getIdUsuario());
                                newBdAUniusu.setIdUsuario(itemRow.getColumnName("ID_UNIDAD").getValueInteger());
                                newBdAUniusu.setFeAlta((Date)itemRow.getColumnName("FE_ALTA").getValue());
                                newBdAUniusu.setFeDesactivo((Date)itemRow.getColumnName("FE_DESACTIVO").getValue());

                                StAUniusu stAUniusu = new StAUniusu(Session.getDatosUsuario());
                                stAUniusu.alta(newBdAUniusu, entityManager);
                            }
                        }
                        
                        entityManager.getTransaction().commit();

                        if (this.parent instanceof FiltroUsuarios filtroUsuarios) {
                            filtroUsuarios.getDsResultado().refrescarDatos();
                        }

                        Mensajes.showInfo(Msg.getString("Informacion"), Msg.getString("alta_OK"));
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

                        StTUsuario stTUsuario = new StTUsuario(Session.getDatosUsuario());
                        stTUsuario.actualiza(this.bdTUsuario, entityManager);

                        //GUARDAR TOKEN GENERADO
                        if (this.cToken.getValue() != null && !this.cToken.getValue().isBlank()) {
                            BdATokenusuario filtroBdATokenusuario = new BdATokenusuario();
                            filtroBdATokenusuario.setIdUsuario(this.bdTUsuario.getIdUsuario());
                            filtroBdATokenusuario.setDsToken(this.cToken.getValue());
                            StATokenusuario stATokenusuario = new StATokenusuario(Session.getDatosUsuario());
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

                        if (!listaUnidadesEliminadasId.isEmpty()) {
                            String inUnidad = "";
                            for (Integer idUnidad : listaUnidadesEliminadasId) {
                                if (!inUnidad.isEmpty()) {
                                    inUnidad += ",";
                                }
                                inUnidad += idUnidad + "";
                            }
                            //BAJA DE PERFILES ASOCIADOS A SU UNIDAD
                            String sql = """
                                         SELECT DISTINCT
                                            T3.ID_UNIDAD,
                                            T3.CO_UNIDAD + ' - ' + T3.DS_UNIDAD as Unidad
                                         FROM 
                                            BD_A_USUTIPOUSU T1
                                         INNER JOIN 
                                            BD_T_TIPOUSUARIO T2 ON (T2.ID_TIPOUSUARIO = T1.ID_TIPOUSUARIO)
                                         INNER JOIN
                                            BD_T_UNIDAD T3 ON (T3.ID_UNIDAD = T2.ID_UNIDAD)
                                         WHERE
                                            T1.ID_USUARIO = :ID_USUARIO
                                         AND T2.ID_UNIDAD IN (:IN_UNIDAD)
                                         """;
                            sql = sql.replace(":ID_USUARIO", "" + this.bdTUsuario.getIdUsuario());
                            sql = sql.replace(":IN_UNIDAD", "" + inUnidad);

                            DataSet dsPerfilesAsociados = new DataSet(sql, "ID_UNIDAD");
                            if (dsPerfilesAsociados.getRowsCount() > 0) {
                                String sPerfilesAsociados = "No se pueden eliminar las siguientes unidades por tener perfiles asociados:<br/>";
                                for (Row itemRow : dsPerfilesAsociados.getRows()) {
                                    sPerfilesAsociados += " - " + itemRow.getColumnName("Unidad").getValueString() + "<br/>";
                                }
                                entityManager.getTransaction().rollback();
                                Mensajes.showWarn("No se puede eliminar", sPerfilesAsociados);
                                return;
                            }
                        }
                        
                        for (Integer idUnidad : listaUnidadesEliminadasId) {
                            // BADA DE UNIDAD
                            BdAUniusu delBdAUniusu = new BdAUniusu();
                            delBdAUniusu.setIdUsuario(this.bdTUsuario.getIdUsuario());
                            delBdAUniusu.setIdUnidad(idUnidad);
                            
                            StAUniusu stAUniusu = new StAUniusu(Session.getDatosUsuario());
                            stAUniusu.baja(delBdAUniusu, entityManager);
                        }
                        
                        if (this.dsUnidades != null) {
                            for (Row itemRow : this.dsUnidades.getRows()) {
                                BdAUniusu newBdAUniusu = new BdAUniusu();
                                newBdAUniusu.setIdUsuario(this.bdTUsuario.getIdUsuario());
                                newBdAUniusu.setIdUnidad(itemRow.getColumnName("ID_UNIDAD").getValueInteger());

                                StAUniusu stAUniusu = new StAUniusu(Session.getDatosUsuario());
                                ArrayList<BdAUniusu> listaBdAUniusu = stAUniusu.filtro(newBdAUniusu, entityManager);
                                if (listaBdAUniusu == null || listaBdAUniusu.isEmpty()) {
                                    newBdAUniusu.setFeAlta((Date)itemRow.getColumnName("FE_ALTA").getValue());
                                    newBdAUniusu.setFeDesactivo((Date)itemRow.getColumnName("FE_DESACTIVO").getValue());

                                    stAUniusu.alta(newBdAUniusu, entityManager);
                                }
                            }
                        }
                        
                        entityManager.getTransaction().commit();

                        if (this.parent instanceof FiltroUsuarios filtroUsuarios) {
                            ELContext elContext = FacesContext.getCurrentInstance().getELContext();
                            filtroUsuarios = (FiltroUsuarios)elContext.getELResolver().getValue(elContext, null, "filtroUsuarios");
                            filtroUsuarios.getDsResultado().actualizarFilaSeleccionada();
                        }

                        Mensajes.showInfo(Msg.getString("Informacion"), Msg.getString("update_OK"));
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
            // INICIO TRANSACCION
            try (EntityManager entityManager = AppInit.getEntityManager())
            {
                entityManager.getTransaction().begin();
                try {
                    //BAJA DE PERFILES ASOCIADOS A SU UNIDAD
                    String sql = """
                                 SELECT DISTINCT
                                    T3.ID_UNIDAD,
                                    T3.CO_UNIDAD + ' - ' + T3.DS_UNIDAD as Unidad
                                 FROM 
                                    BD_A_USUTIPOUSU T1
                                 INNER JOIN 
                                    BD_T_TIPOUSUARIO T2 ON (T2.ID_TIPOUSUARIO = T1.ID_TIPOUSUARIO)
                                 INNER JOIN
                                    BD_T_UNIDAD T3 ON (T3.ID_UNIDAD = T2.ID_UNIDAD)
                                 WHERE
                                    T1.ID_USUARIO = :ID_USUARIO
                                 """;
                    sql = sql.replace(":ID_USUARIO", "" + this.bdTUsuario.getIdUsuario());

                    DataSet dsPerfilesAsociados = new DataSet(sql, "ID_UNIDAD");
                    if (dsPerfilesAsociados.getRowsCount() > 0) {
                        String sPerfilesAsociados = "No se pueden eliminar las siguientes unidades por tener perfiles asociados:<br/>";
                        for (Row itemRow : dsPerfilesAsociados.getRows()) {
                            sPerfilesAsociados += " - " + itemRow.getColumnName("Unidad").getValueString() + "<br/>";
                        }
                        entityManager.getTransaction().rollback();
                        Mensajes.showWarn("No se puede eliminar", sPerfilesAsociados);
                        return;
                    }
                    
                    BdAUniusu filtroBdAUniusu = new BdAUniusu();
                    filtroBdAUniusu.setIdUsuario(this.bdTUsuario.getIdUsuario());
                    StAUniusu stAUniusu = new StAUniusu(Session.getDatosUsuario());
                    ArrayList<BdAUniusu> listaBdAUniusu = stAUniusu.filtro(filtroBdAUniusu, entityManager);
                    if (listaBdAUniusu != null && !listaBdAUniusu.isEmpty()) {
                        for (BdAUniusu itemBdAUniusu : listaBdAUniusu) {
                            stAUniusu.baja(itemBdAUniusu, entityManager);
                        }
                    }
                    
                    StTUsuario stTUsuario = new StTUsuario(Session.getDatosUsuario());
                    stTUsuario.baja(this.bdTUsuario, entityManager);

                    entityManager.getTransaction().commit();
                    
                    if (this.parent instanceof FiltroUsuarios filtroUsuarios) {
                        filtroUsuarios.getDsResultado().eliminarFilaSeleccionada();
                    }

                    Mensajes.showInfo(Msg.getString("Informacion"), "Registro eliminado correctamente!");
                }
                catch (SQLReferenceException rex) {
                    entityManager.getTransaction().rollback();
                    Mensajes.showWarn("No se pudo eliminar", "El registro está siendo utilizado en otro apartado del programa. Elimine toda relación con este registro para poder eliminarlo.");
                    return;
                }
                catch (Exception ex) {
                    entityManager.getTransaction().rollback();
                    throw ex;
                }
            }
            // FIN TRANSACCION
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
        this.cFeAlta.setValue(new Date());
        this.cFeDesactivo.setValue(null);
        this.cUnidad.setId(null);
        
        listaUnidadesEliminadasId.clear();
        if (this.dsUnidades != null) {
            for (Row itemRow : this.dsUnidades.getRows()) {
                listaUnidadesEliminadasId.add(itemRow.getColumnaID().getValueInteger());
            }
            this.dsUnidades.clear();
            this.cUnidad.setListaNotIN_Clear();
        }
    }
    
    public String volver() {
        try {
            if (this.modoFormulario == ModoFormulario.EDICION) {
                recuperarRegistro(this.bdTUsuario.getIdUsuario());
                inicializarDataSetUnidades(this.bdTUsuario.getIdUsuario());
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
                this.cUnidad.setProtegido(true);
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
                this.cUnidad.setProtegido(false);
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
                this.cUnidad.setProtegido(false);
            }
            default -> throw new FormModeException();
        }
    }
    
    private void recuperarRegistro(Integer idUsuario) throws Exception {
        StTUsuario stTUsuario = new StTUsuario(Session.getDatosUsuario());
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
        
        StATokenusuario stATokenusuario = new StATokenusuario(Session.getDatosUsuario());
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
        if (this.dsUnidades != null) {
            this.dsUnidades.setModoFormulario(modoFormulario);
        }
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

    public CampoWebLupa getcUnidad() {
        return cUnidad;
    }

    public void setcUnidad(CampoWebLupa cUnidad) {
        this.cUnidad = cUnidad;
    }

    public DataSet getDsUnidades() {
        return dsUnidades;
    }

    public void setDsUnidades(DataSet dsUnidades) {
        this.dsUnidades = dsUnidades;
    }

    public CampoWebLupa getcPerfil() {
        return cPerfil;
    }

    public void setcPerfil(CampoWebLupa cPerfil) {
        this.cPerfil = cPerfil;
    }

    public DataSet getDsPerfiles() {
        return dsPerfiles;
    }

    public void setDsPerfiles(DataSet dsPerfiles) {
        this.dsPerfiles = dsPerfiles;
    }
}
