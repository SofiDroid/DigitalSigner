package configuracion.gestion.tiposDocumentos;

import basedatos.ColumnBase;
import basedatos.ColumnCabecera;
import basedatos.DataSet;
import basedatos.Row;
import basedatos.RowCabecera;
import basedatos.servicios.StAConftipodoc;
import basedatos.servicios.StTTipodocumento;
import basedatos.tablas.BdAConftipodoc;
import basedatos.tablas.BdTTipodocumento;
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
import tomcat.persistence.EntityManager;
import utilidades.CampoWebCodigo;
import utilidades.CampoWebCombo;
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
public class EdicionTiposDocumentos implements Serializable {
    private Object parent = null;
    private String paginaRetorno = null;
    
    // TIPO DOCUMENTO
    private CampoWebCodigo cCoTipodocumento = null;
    private CampoWebDescripcion cDsTipodocumento = null;
    private CampoWebCombo cDiFormatofirma = null;
    private CampoWebFecha cFeAlta = null;
    private CampoWebFecha cFeDesactivo = null;
    private DataSet dsFirmas = null;
    private ArrayList<Integer> listaFirmasEliminadasId = null;
    
    // FIRMAS
    private CampoWebNumero cEnOrden = null;
    private CampoWebCombo cDiTipoFirma = null;
    private CampoWebLupa cAutoridad = null;
    private CampoWebNumero cDsFirmaPosX = null;
    private CampoWebNumero cDsFirmaPosY = null;
    private CampoWebFecha cFeAltaFirma = null;
    private CampoWebFecha cFeDesactivoFirma = null;
    
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
    
            // TIPO DOCUMENTO
            this.cCoTipodocumento = new CampoWebCodigo();
            this.cCoTipodocumento.setLabel(Msg.getString("lbl_EdicionTiposDocumentos_CoTipodocumento"));
            this.cCoTipodocumento.setWidthLabel("100px");
            this.cCoTipodocumento.setRequired(true);
            
            this.cDsTipodocumento = new CampoWebDescripcion();
            this.cDsTipodocumento.setLabel(Msg.getString("lbl_EdicionTiposDocumentos_DsTipodocumento"));
            this.cDsTipodocumento.setWidthLabel("100px");
            this.cDsTipodocumento.setRequired(true);
            
            this.cDiFormatofirma = new CampoWebCombo();
            this.cDiFormatofirma.setLabel("Formato firma");
            this.cDiFormatofirma.setWidthLabel("100px");
            this.cDiFormatofirma.setWidth("6em");
            this.cDiFormatofirma.getOptions().put("XAdES","XAdES");
            this.cDiFormatofirma.getOptions().put("PAdES","PAdES");
            this.cDiFormatofirma.setRequired(true);
            
            this.cFeAlta = new CampoWebFecha();
            this.cFeAlta.setLabel(Msg.getString("lbl_EdicionTiposDocumentos_FeAlta"));
            this.cFeAlta.setWidthLabel("100px");
            this.cFeAlta.setRequired(true);
            
            this.cFeDesactivo = new CampoWebFecha();
            this.cFeDesactivo.setLabel(Msg.getString("lbl_EdicionTiposDocumentos_FeDesactivo"));
            this.cFeDesactivo.setWidthLabel("100px");
            
            // LISTADO FIRMAS
            this.dsFirmas = new DataSet();
            this.listaFirmasEliminadasId = new ArrayList<>();
            
            // EDICION DE FIRMA
            this.cEnOrden = new CampoWebNumero();
            this.cEnOrden.setLabel(Msg.getString("lbl_EdicionTiposDocumentos_EnOrden"));
            this.cEnOrden.setWidthLabel("100px");
            this.cEnOrden.setSize(2);
            this.cEnOrden.setMinValue(0);
            this.cEnOrden.setMaxValue(10);
            this.cEnOrden.setNumDecimales(0);
            this.cEnOrden.setProtegido(true);
            
            this.cDiTipoFirma = new CampoWebCombo();
            this.cDiTipoFirma.setLabel(Msg.getString("lbl_EdicionTiposDocumentos_DiTipoFirma"));
            this.cDiTipoFirma.setWidthLabel("7em");
            this.cDiTipoFirma.setWidth("100%");
            
            this.cAutoridad = new CampoWebLupa();
            this.cAutoridad.setLabel(Msg.getString("lbl_EdicionTiposDocumentos_Autoridad"));
            this.cAutoridad.setWidthLabel("100px");
            String sql = "SELECT ID_AUTORIDAD, CO_AUTORIDAD + ' - ' + DS_AUTORIDAD as Autoridad FROM BD_T_AUTORIDAD";
            this.cAutoridad.setConsulta(sql);
            this.cAutoridad.setColumnaID("ID_AUTORIDAD");
            this.cAutoridad.setColumnaLabel("Autoridad");
        
            this.cDsFirmaPosX = new CampoWebNumero();
            this.cDsFirmaPosX.setLabel(Msg.getString("lbl_EdicionTiposDocumentos_DsFirmaPosX"));
            this.cDsFirmaPosX.setWidthLabel("100px");
            this.cDsFirmaPosX.setSize(4);
            this.cDsFirmaPosX.setMinValue(0);
            this.cDsFirmaPosX.setMaxValue(9999);
            this.cDsFirmaPosX.setNumDecimales(0);
            
            this.cDsFirmaPosY = new CampoWebNumero();
            this.cDsFirmaPosY.setLabel(Msg.getString("lbl_EdicionTiposDocumentos_DsFirmaPosY"));
            this.cDsFirmaPosY.setWidthLabel("100px");
            this.cDsFirmaPosY.setSize(4);
            this.cDsFirmaPosY.setMinValue(0);
            this.cDsFirmaPosY.setMaxValue(9999);
            this.cDsFirmaPosY.setNumDecimales(0);

            this.cFeAltaFirma = new CampoWebFecha();
            this.cFeAltaFirma.setLabel(Msg.getString("lbl_EdicionTiposDocumentos_FeAltaFirma"));
            this.cFeAltaFirma.setWidthLabel("100px");

            this.cFeDesactivoFirma = new CampoWebFecha();
            this.cFeDesactivoFirma.setLabel(Msg.getString("lbl_EdicionTiposDocumentos_FeDesactivoFirma"));
            this.cFeDesactivoFirma.setWidthLabel("100px");
            
            this.setModoFormulario(ModoFormulario.CONSULTA);
            
            if (idTipodocumento != null) {
                recuperarRegistro(idTipodocumento);
                inicializarDataSetFirmas(idTipodocumento);
            }
            else {
                this.setModoFormulario(ModoFormulario.ALTA);
                inicializarDataSetFirmas(null);
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
                        this.bdTTipodocumento = new BdTTipodocumento();
                        this.bdTTipodocumento.setCoTipodocumento(this.cCoTipodocumento.getValue());
                        this.bdTTipodocumento.setDsTipodocumento(this.cDsTipodocumento.getValue());
                        this.bdTTipodocumento.setDiFormatofirma((String)this.cDiFormatofirma.getValue());
                        this.bdTTipodocumento.setFeAlta(this.cFeAlta.getValue());
                        this.bdTTipodocumento.setFeDesactivo(this.cFeDesactivo.getValue());

                        StTTipodocumento stTTipodocumento = new StTTipodocumento(Session.getDatosUsuario());
                        stTTipodocumento.alta(this.bdTTipodocumento, entityManager);

                        if (this.dsFirmas != null) {
                            for (Row itemRow : this.dsFirmas.getRows()) {
                                BdAConftipodoc newBdAConftipodoc = new BdAConftipodoc();
                                newBdAConftipodoc.setIdTipodocumento(this.bdTTipodocumento.getIdTipodocumento());
                                newBdAConftipodoc.setIdAutoridad(itemRow.getColumnName("ID_AUTORIDAD").getValueInteger());
                                newBdAConftipodoc.setEnOrden(itemRow.getColumnName("EN_ORDEN").getValueInteger());
                                newBdAConftipodoc.setDiTipofirma(itemRow.getColumnName("DI_TIPOFIRMA").getValueString());
                                newBdAConftipodoc.setDsFirmaposx(itemRow.getColumnName("DS_FIRMAPOSX").getValueString());
                                newBdAConftipodoc.setDsFirmaposy(itemRow.getColumnName("DS_FIRMAPOSY").getValueString());
                                newBdAConftipodoc.setFeAlta((Date)itemRow.getColumnName("FE_ALTA").getValue());
                                newBdAConftipodoc.setFeDesactivo((Date)itemRow.getColumnName("FE_DESACTIVO").getValue());

                                StAConftipodoc stAConftipodoc = new StAConftipodoc(Session.getDatosUsuario());
                                stAConftipodoc.alta(newBdAConftipodoc, entityManager);
                            }
                        }
                        
                        entityManager.getTransaction().commit();

                        if (this.parent instanceof FiltroTiposDocumentos filtroTiposDocumentos) {
                            filtroTiposDocumentos.getDsResultado().refrescarDatos();
                        }

                        Mensajes.showInfo(Msg.getString("Informacion"), Msg.getString("alta_OK"));
                    }

                    //ACTUALIZACION
                    if (this.modoFormulario == ModoFormulario.EDICION) {
                        this.bdTTipodocumento.setCoTipodocumento(this.cCoTipodocumento.getValue());
                        this.bdTTipodocumento.setDsTipodocumento(this.cDsTipodocumento.getValue());
                        this.bdTTipodocumento.setDiFormatofirma((String)this.cDiFormatofirma.getValue());
                        this.bdTTipodocumento.setFeAlta(this.cFeAlta.getValue());
                        this.bdTTipodocumento.setFeDesactivo(this.cFeDesactivo.getValue());

                        StTTipodocumento stTTipodocumento = new StTTipodocumento(Session.getDatosUsuario());
                        stTTipodocumento.actualiza(this.bdTTipodocumento, entityManager);

                        for (Integer idConftipodoc : listaFirmasEliminadasId) {
                            BdAConftipodoc delBdAConftipodoc = new BdAConftipodoc();
                            delBdAConftipodoc.setIdConftipodoc(idConftipodoc);
                            
                            StAConftipodoc stAConftipodoc = new StAConftipodoc(Session.getDatosUsuario());
                            stAConftipodoc.baja(delBdAConftipodoc, entityManager);
                        }
                        
                        if (this.dsFirmas != null) {
                            for (Row itemRow : this.dsFirmas.getRows()) {
                                if  (itemRow.getColumnName("ID_CONFTIPODOC").getValue() == null) {
                                    //ALTA
                                    BdAConftipodoc newBdAConftipodoc = new BdAConftipodoc();
                                    newBdAConftipodoc.setIdTipodocumento(this.bdTTipodocumento.getIdTipodocumento());
                                    newBdAConftipodoc.setIdAutoridad(itemRow.getColumnName("ID_AUTORIDAD").getValueInteger());
                                    newBdAConftipodoc.setEnOrden(itemRow.getColumnName("EN_ORDEN").getValueInteger());
                                    newBdAConftipodoc.setDiTipofirma(itemRow.getColumnName("DI_TIPOFIRMA").getValueString());
                                    newBdAConftipodoc.setDsFirmaposx(itemRow.getColumnName("DS_FIRMAPOSX").getValueString());
                                    newBdAConftipodoc.setDsFirmaposy(itemRow.getColumnName("DS_FIRMAPOSY").getValueString());
                                    newBdAConftipodoc.setFeAlta((Date)itemRow.getColumnName("FE_ALTA").getValue());
                                    newBdAConftipodoc.setFeDesactivo((Date)itemRow.getColumnName("FE_DESACTIVO").getValue());

                                    StAConftipodoc stAConftipodoc = new StAConftipodoc(Session.getDatosUsuario());
                                    stAConftipodoc.alta(newBdAConftipodoc, entityManager);
                                }
                                else {
                                    //MODIFICACION
                                    BdAConftipodoc upBdAConftipodoc = new BdAConftipodoc();
                                    upBdAConftipodoc.setIdConftipodoc(itemRow.getColumnName("ID_CONFTIPODOC").getValueInteger());
                                    upBdAConftipodoc.setIdTipodocumento(itemRow.getColumnName("ID_TIPODOCUMENTO").getValueInteger());
                                    upBdAConftipodoc.setIdAutoridad(itemRow.getColumnName("ID_AUTORIDAD").getValueInteger());
                                    upBdAConftipodoc.setEnOrden(itemRow.getColumnName("EN_ORDEN").getValueInteger());
                                    upBdAConftipodoc.setDiTipofirma(itemRow.getColumnName("DI_TIPOFIRMA").getValueString());
                                    upBdAConftipodoc.setDsFirmaposx(itemRow.getColumnName("DS_FIRMAPOSX").getValueString());
                                    upBdAConftipodoc.setDsFirmaposy(itemRow.getColumnName("DS_FIRMAPOSY").getValueString());
                                    upBdAConftipodoc.setFeAlta((Date)itemRow.getColumnName("FE_ALTA").getValue());
                                    upBdAConftipodoc.setFeDesactivo((Date)itemRow.getColumnName("FE_DESACTIVO").getValue());

                                    StAConftipodoc stAConftipodoc = new StAConftipodoc(Session.getDatosUsuario());
                                    stAConftipodoc.actualiza(upBdAConftipodoc, entityManager);
                                }
                            }
                        }

                        entityManager.getTransaction().commit();

                        if (this.parent instanceof FiltroTiposDocumentos filtroTiposDocumentos) {
                            ELContext elContext = FacesContext.getCurrentInstance().getELContext();
                            filtroTiposDocumentos = (FiltroTiposDocumentos)elContext.getELResolver().getValue(elContext, null, "filtroTiposDocumentos");
                            filtroTiposDocumentos.getDsResultado().actualizarFilaSeleccionada();
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
                    BdAConftipodoc filtroBdAConftipodoc = new BdAConftipodoc();
                    filtroBdAConftipodoc.setIdTipodocumento(this.bdTTipodocumento.getIdTipodocumento());
                    StAConftipodoc stAConftipodoc = new StAConftipodoc(Session.getDatosUsuario());
                    ArrayList<BdAConftipodoc> listaAConftipodoc = stAConftipodoc.filtro(filtroBdAConftipodoc, entityManager);
                    if (listaAConftipodoc != null && !listaAConftipodoc.isEmpty()) {
                        for (BdAConftipodoc itemBdAConftipodoc : listaAConftipodoc) {
                            stAConftipodoc.baja(itemBdAConftipodoc, entityManager);
                        }
                    }
                    
                    StTTipodocumento stTTipodocumento = new StTTipodocumento(Session.getDatosUsuario());
                    stTTipodocumento.baja(this.bdTTipodocumento, entityManager);

                    entityManager.getTransaction().commit();
                    
                    if (this.parent instanceof FiltroTiposDocumentos filtroTiposDocumentos) {
                        filtroTiposDocumentos.getDsResultado().eliminarFilaSeleccionada();
                    }

                    Mensajes.showInfo(Msg.getString("Informacion"), "Registro eliminado correctamente!");
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
    
    public void limpiar() {
        this.cCoTipodocumento.setValue(null);
        this.cDsTipodocumento.setValue(null);
        this.cDiFormatofirma.setValue(null);
        this.cFeAlta.setValue(null);
        this.cFeDesactivo.setValue(null);
        
        this.cEnOrden.setValueInteger(null);
        this.cDiTipoFirma.setValue(null);
        this.cAutoridad.setId(null);
        this.cDsFirmaPosX.setValueInteger(null);
        this.cDsFirmaPosY.setValueInteger(null);
        this.cFeAltaFirma.setValue(null);
        this.cFeDesactivoFirma.setValue(null);
        
        listaFirmasEliminadasId.clear();
        if (this.dsFirmas != null) {
            for (Row itemRow : this.dsFirmas.getRows()) {
                listaFirmasEliminadasId.add(itemRow.getColumnaID().getValueInteger());
            }
            this.dsFirmas.clear();
        }
    }
    
    public String volver() {
        try {
            if (this.modoFormulario == ModoFormulario.EDICION) {
                recuperarRegistro(this.bdTTipodocumento.getIdTipodocumento());
                inicializarDataSetFirmas(this.bdTTipodocumento.getIdTipodocumento());
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
        if (Validation.isNullOrEmpty(this.cDiFormatofirma.getValue())) {
            throw new RequiredFieldException(this.cDiFormatofirma.getLabel());
        }
        if (Validation.isNullOrEmpty(this.cFeAlta.getValue())) {
            throw new RequiredFieldException(this.cFeAlta.getLabel());
        }
    }
    
    private void validarCamposFirma() throws RequiredFieldException {
        //VALIDAR CAMPOS DE LA FIRMA
        if (Validation.isNullOrEmpty(this.cEnOrden.getValue())) {
            throw new RequiredFieldException(this.cEnOrden.getLabel());
        }
        if (Validation.isNullOrEmpty(this.cDiTipoFirma.getValue())) {
            throw new RequiredFieldException(this.cDiTipoFirma.getLabel());
        }
        if (Validation.isNullOrEmpty(this.cAutoridad.getValue())) {
            throw new RequiredFieldException(this.cAutoridad.getLabel());
        }
        if (Validation.isNullOrEmpty(this.cDsFirmaPosX.getValue())) {
            throw new RequiredFieldException(this.cDsFirmaPosX.getLabel());
        }
        if (Validation.isNullOrEmpty(this.cDsFirmaPosY.getValue())) {
            throw new RequiredFieldException(this.cDsFirmaPosY.getLabel());
        }
        if (Validation.isNullOrEmpty(this.cFeAltaFirma.getValue())) {
            throw new RequiredFieldException(this.cFeAltaFirma.getLabel());
        }
    }
    
    private void protegerCampos() throws Exception {
        switch (this.modoFormulario) {
            case CONSULTA, ELIMINADO -> {
                this.cCoTipodocumento.setProtegido(true);
                this.cDsTipodocumento.setProtegido(true);
                this.cDiFormatofirma.setProtegido(true);
                this.cFeAlta.setProtegido(true);
                this.cFeDesactivo.setProtegido(true);
                
                this.cDiTipoFirma.setProtegido(true);
                this.cAutoridad.setProtegido(true);
                this.cDsFirmaPosX.setProtegido(true);
                this.cDsFirmaPosY.setProtegido(true);
                this.cFeAltaFirma.setProtegido(true);
                this.cFeDesactivoFirma.setProtegido(true);
            }
            case EDICION -> {
                this.cCoTipodocumento.setProtegido(false);
                this.cDsTipodocumento.setProtegido(false);
                this.cDiFormatofirma.setProtegido(false);
                this.cFeAlta.setProtegido(false);
                this.cFeDesactivo.setProtegido(false);
                
                this.cDiTipoFirma.setProtegido(false);
                this.cAutoridad.setProtegido(false);
                this.cDsFirmaPosX.setProtegido(false);
                this.cDsFirmaPosY.setProtegido(false);
                this.cFeAltaFirma.setProtegido(false);
                this.cFeDesactivoFirma.setProtegido(false);
            }
            case ALTA -> {
                limpiar();
                this.cCoTipodocumento.setProtegido(false);
                this.cDsTipodocumento.setProtegido(false);
                this.cDiFormatofirma.setProtegido(false);
                this.cFeAlta.setProtegido(false);
                this.cFeDesactivo.setProtegido(false);
                
                this.cDiTipoFirma.setProtegido(false);
                this.cAutoridad.setProtegido(false);
                this.cDsFirmaPosX.setProtegido(false);
                this.cDsFirmaPosY.setProtegido(false);
                this.cFeAltaFirma.setProtegido(false);
                this.cFeDesactivoFirma.setProtegido(false);
            }
            default -> throw new FormModeException();
        }
    }
    
    private void recuperarRegistro(Integer idTipodocumento) throws Exception {
        StTTipodocumento stTTipodocumento = new StTTipodocumento(Session.getDatosUsuario());
        this.bdTTipodocumento = stTTipodocumento.item(idTipodocumento, null);
        if (this.bdTTipodocumento == null) {
            throw new RegistryNotFoundException();
        }
        
        cCoTipodocumento.setValue(this.bdTTipodocumento.getCoTipodocumento());
        cDsTipodocumento.setValue(this.bdTTipodocumento.getDsTipodocumento());
        cDiFormatofirma.setValue(this.bdTTipodocumento.getDiFormatofirma());
        cFeAlta.setValue(this.bdTTipodocumento.getFeAlta());
        cFeDesactivo.setValue(this.bdTTipodocumento.getFeDesactivo());
    }

    private void inicializarDataSetFirmas(Integer idTipodocumento) throws Exception {
        String sql = """
            SELECT 
                T1.ID_CONFTIPODOC, 
                T1.ID_TIPODOCUMENTO,
                T1.ID_AUTORIDAD,
                T1.EN_ORDEN,
                T1.DI_TIPOFIRMA, 
                (CASE
                    WHEN T1.DI_TIPOFIRMA = 'F' THEN 'Firma'
                    WHEN T1.DI_TIPOFIRMA = 'CO' THEN 'Cofirma'
                    WHEN T1.DI_TIPOFIRMA = 'CT' THEN 'Contrafirma'
                    ELSE 'Desconocido'
                END) as DS_TIPOFIRMA,
                T1.DS_FIRMAPOSX, 
                T1.DS_FIRMAPOSY, 
                aut.CO_AUTORIDAD + ' - ' + aut.DS_AUTORIDAD as Autoridad,
                T1.FE_ALTA, 
                T1.FE_DESACTIVO
            FROM 
                BD_A_CONFTIPODOC T1
            INNER JOIN
                BD_T_AUTORIDAD aut ON (aut.ID_AUTORIDAD = T1.ID_AUTORIDAD)
            WHERE 1 = 1
            AND T1.ID_TIPODOCUMENTO = :ID_TIPODOCUMENTO
            ORDER BY
                T1.EN_ORDEN ASC
            """;
        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_TIPODOCUMENTO", idTipodocumento);

        dsFirmas = new DataSet(sql, parametros, "ID_CONFTIPODOC");
        
        // Establecer formato de salida
        RowCabecera cabecera = this.getDsFirmas().getCabecera();

        cabecera.getColumnName("ID_CONFTIPODOC")
                .setVisible(false);

        cabecera.getColumnName("ID_TIPODOCUMENTO")
                .setVisible(false);

        cabecera.getColumnName("ID_AUTORIDAD")
                .setVisible(false);

        cabecera.getColumnName("EN_ORDEN")
                .setTitle("Órden")
                .setWidth("6rem")
                .setTipo(ColumnBase.Tipo.LINK)
                .setClase(this)
                .setMethod(this.getClass().getMethod("verDetalle"))
                .setOncomplete("PF('dlgFirmas').show()")
                .setUpdate("formulario:panelFirmas,formulario:mensaje");

        cabecera.getColumnName("DI_TIPOFIRMA")
                .setVisible(false);

        cabecera.getColumnName("DS_TIPOFIRMA")
                .setTitle("Tipo")
                .setWidth("6rem");

        cabecera.getColumnName("Autoridad")
                .setTitle("Autoridad")
                .setWidth("100%");

        cabecera.getColumnName("DS_FIRMAPOSX")
                .setVisible(false);

        cabecera.getColumnName("DS_FIRMAPOSY")
                .setVisible(false);

        cabecera.getColumnName("FE_ALTA")
                .setTitle("F. Alta")
                .setWidth("6rem");

        cabecera.getColumnName("FE_DESACTIVO")
                .setTitle("F. Desactivo")
                .setWidth("6rem");
        
        this.dsFirmas.newColumn("btnEliminar");
        cabecera.getColumnName("btnEliminar")
                .setTitle("Eliminar")
                .setAlign(ColumnCabecera.ALIGN.CENTER)
                .setWidth("10em")
                .setTipo(ColumnBase.Tipo.BOTON_EDICION)
                .setClase(this)
                .setIcon("pi pi-times-circle")
                .setStyleClass("ui-button-danger")
                .setMethod(this.getClass().getMethod("eliminarFirma"))
                .setUpdate("formulario:panelFirmas,formulario:mensaje");
    }
    
    public void nuevaFirma() {
        try {
            this.cEnOrden.setValueInteger(this.dsFirmas.getRowsCount() + 1);
            opcionesDiTipoFirma(this.cEnOrden.getValueInteger());
            //this.cDiTipoFirma.setValue(null);
            this.cAutoridad.setId(null);
            this.cDsFirmaPosX.setValueInteger(null);
            this.cDsFirmaPosY.setValueInteger(null);
            this.cFeAltaFirma.setValue(null);
            this.cFeDesactivoFirma.setValue(null);
            
            camposFirmaRequeridos(true);
        } catch (Exception ex) {
            Mensajes.showException(this.getClass(), ex);
        }
    }
    
    public void guardarFirma() {
        try {
            // VALIDAR CAMPOS FIRMA
            validarCamposFirma();
            
            //ACTUALIZAR O GRABAR DATOS EN EL GRID (EN MEMORIA)
            if (this.cEnOrden.getValueInteger().compareTo(this.dsFirmas.getRowsCount()) > 0)
            {
                //ALTA
                Row newRow = this.dsFirmas.newRow();
                newRow.getColumnName("ID_CONFTIPODOC").setValue(null);
                newRow.getColumnName("ID_TIPODOCUMENTO").setValue(null);
                newRow.getColumnName("ID_AUTORIDAD").setValue(this.cAutoridad.getId());
                newRow.getColumnName("EN_ORDEN").setValue(this.cEnOrden.getValueInteger());
                newRow.getColumnName("DI_TIPOFIRMA").setValue(this.cDiTipoFirma.getValue());
                newRow.getColumnName("DS_TIPOFIRMA").setValue(this.cDiTipoFirma.getOptions().get(this.cDiTipoFirma.getValue().toString()));
                newRow.getColumnName("Autoridad").setValue(this.cAutoridad.getValue().getLabel());
                newRow.getColumnName("DS_FIRMAPOSX").setValue(this.cDsFirmaPosX.getValueInteger());
                newRow.getColumnName("DS_FIRMAPOSY").setValue(this.cDsFirmaPosY.getValueInteger());
                newRow.getColumnName("FE_ALTA").setValue(this.cFeAltaFirma.getValue());
                newRow.getColumnName("FE_DESACTIVO").setValue(this.cFeDesactivoFirma.getValue());
                
                this.dsFirmas.getRows().add(newRow);
            }
            else {
                //MODIFICACION
                Row editRow = this.dsFirmas.getSelectedRow();

                editRow.getColumnName("ID_AUTORIDAD").setValue(this.cAutoridad.getId());
                editRow.getColumnName("EN_ORDEN").setValue(this.cEnOrden.getValueInteger());
                editRow.getColumnName("DI_TIPOFIRMA").setValue(this.cDiTipoFirma.getValue());
                editRow.getColumnName("DS_TIPOFIRMA").setValue(this.cDiTipoFirma.getOptions().get(this.cDiTipoFirma.getValue().toString()));
                editRow.getColumnName("Autoridad").setValue(this.cAutoridad.getValue().getLabel());
                editRow.getColumnName("DS_FIRMAPOSX").setValue(this.cDsFirmaPosX.getValueInteger());
                editRow.getColumnName("DS_FIRMAPOSY").setValue(this.cDsFirmaPosY.getValueInteger());
                editRow.getColumnName("FE_ALTA").setValue(this.cFeAltaFirma.getValue());
                editRow.getColumnName("FE_DESACTIVO").setValue(this.cFeDesactivoFirma.getValue());
            }
            
            camposFirmaRequeridos(false);
        } catch (Exception ex) {
            Mensajes.showException(this.getClass(), ex);
        }
    }
    
    public void eliminarFirma() {
        try {
            Integer idConftipodoc = this.dsFirmas.getSelectedRow().getColumnaID().getValueInteger();
            if (idConftipodoc != null) {
                listaFirmasEliminadasId.add(this.dsFirmas.getSelectedRow().getColumnaID().getValueInteger());
            }
            this.dsFirmas.getRows().remove(this.dsFirmas.getSelectedRow());
            if (this.dsFirmas != null) {
                for (int i = 0; i < this.dsFirmas.getRowsCount(); i++) {
                    Row itemRow = this.dsFirmas.getRows().get(i);
                    if (i == 0) {
                        itemRow.getColumnName("DI_TIPOFIRMA").setValueString("F");
                        itemRow.getColumnName("DS_TIPOFIRMA").setValue("Firma");
                    }
                    itemRow.setIndex(i);
                    itemRow.getColumnName("EN_ORDEN").setValue((i+1));
                }
            }
        } catch (Exception ex) {
            Mensajes.showException(this.getClass(), ex);
        }
    }
    
    public void cerrarEdicionCamposFirma() {
        try {
            camposFirmaRequeridos(false);
        } catch (Exception ex) {
            Mensajes.showException(this.getClass(), ex);
        }
    }
    
    public void verDetalle() {
        try {
            camposFirmaCargar(this.dsFirmas.getSelectedRow());
            camposFirmaRequeridos(true);
        } catch (Exception ex) {
            Mensajes.showException(this.getClass(), ex);
        }
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

    public CampoWebCombo getcDiFormatofirma() {
        return cDiFormatofirma;
    }

    public void setcDiFormatofirma(CampoWebCombo cDiFormatofirma) {
        this.cDiFormatofirma = cDiFormatofirma;
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
        if (this.dsFirmas != null) {
            this.dsFirmas.setModoFormulario(modoFormulario);
        }
        protegerCampos();
    }

    public Object getParent() {
        return parent;
    }

    public void setParent(Object parent) {
        this.parent = parent;
    }

    public DataSet getDsFirmas() {
        return dsFirmas;
    }

    public void setDsFirmas(DataSet dsFirmas) {
        this.dsFirmas = dsFirmas;
    }

    public CampoWebNumero getcEnOrden() {
        return cEnOrden;
    }

    public void setcEnOrden(CampoWebNumero cEnOrden) {
        this.cEnOrden = cEnOrden;
    }

    public CampoWebCombo getcDiTipoFirma() {
        return cDiTipoFirma;
    }

    public void setcDiTipoFirma(CampoWebCombo cDiTipoFirma) {
        this.cDiTipoFirma = cDiTipoFirma;
    }

    public CampoWebLupa getcAutoridad() {
        return cAutoridad;
    }

    public void setcAutoridad(CampoWebLupa cAutoridad) {
        this.cAutoridad = cAutoridad;
    }

    public CampoWebNumero getcDsFirmaPosX() {
        return cDsFirmaPosX;
    }

    public void setcDsFirmaPosX(CampoWebNumero cDsFirmaPosX) {
        this.cDsFirmaPosX = cDsFirmaPosX;
    }

    public CampoWebNumero getcDsFirmaPosY() {
        return cDsFirmaPosY;
    }

    public void setcDsFirmaPosY(CampoWebNumero cDsFirmaPosY) {
        this.cDsFirmaPosY = cDsFirmaPosY;
    }

    public CampoWebFecha getcFeAltaFirma() {
        return cFeAltaFirma;
    }

    public void setcFeAltaFirma(CampoWebFecha cFeAltaFirma) {
        this.cFeAltaFirma = cFeAltaFirma;
    }

    public CampoWebFecha getcFeDesactivoFirma() {
        return cFeDesactivoFirma;
    }

    public void setcFeDesactivoFirma(CampoWebFecha cFeDesactivoFirma) {
        this.cFeDesactivoFirma = cFeDesactivoFirma;
    }

    private void camposFirmaRequeridos(boolean boRequerido) {
        this.cEnOrden.setRequired(boRequerido);
        this.cDiTipoFirma.setRequired(boRequerido);
        this.cAutoridad.setRequired(boRequerido);
        this.cDsFirmaPosX.setRequired(boRequerido);
        this.cDsFirmaPosY.setRequired(boRequerido);
        this.cFeAltaFirma.setRequired(boRequerido);
    }

    private void camposFirmaCargar(Row selectedRow) throws Exception {
        this.cEnOrden.setValueInteger(selectedRow.getColumnName("EN_ORDEN").getValueInteger());
        opcionesDiTipoFirma(this.cEnOrden.getValueInteger());
        if (selectedRow.getColumnName("DI_TIPOFIRMA").getValue() != null) {
            this.cDiTipoFirma.setValue(selectedRow.getColumnName("DI_TIPOFIRMA").getValueString());
        }
        this.cAutoridad.setId(selectedRow.getColumnName("ID_AUTORIDAD").getValueInteger());
        this.cDsFirmaPosX.setValueInteger(selectedRow.getColumnName("DS_FIRMAPOSX").getValueInteger());
        this.cDsFirmaPosY.setValueInteger(selectedRow.getColumnName("DS_FIRMAPOSY").getValueInteger());
        this.cFeAltaFirma.setValue(selectedRow.getColumnName("FE_ALTA").getValue());
        this.cFeDesactivoFirma.setValue(selectedRow.getColumnName("FE_DESACTIVO").getValue());
    }
    
    private void opcionesDiTipoFirma(Integer enOrden) {
        this.cDiTipoFirma.getOptions().clear();
        if (enOrden != null && enOrden.compareTo(1) == 0) {
            this.cDiTipoFirma.getOptions().put("F", "Firma");
            this.cDiTipoFirma.setValue("F");
            this.cDiTipoFirma.setProtegido(true);
        }
        else if (enOrden != null && enOrden.compareTo(1) > 0) {
            this.cDiTipoFirma.getOptions().put("CO", "Cofirma");
            this.cDiTipoFirma.getOptions().put("CT", "Contrafirma");
            this.cDiTipoFirma.setProtegido(!(modoFormulario == ModoFormulario.ALTA || modoFormulario == ModoFormulario.EDICION));
        }
        else {
            this.cDiTipoFirma.getOptions().put("F", "Firma");
            this.cDiTipoFirma.getOptions().put("CO", "Cofirma");
            this.cDiTipoFirma.getOptions().put("CT", "Contrafirma");
            this.cDiTipoFirma.setProtegido(!(modoFormulario == ModoFormulario.ALTA || modoFormulario == ModoFormulario.EDICION));
        }
    }
}
