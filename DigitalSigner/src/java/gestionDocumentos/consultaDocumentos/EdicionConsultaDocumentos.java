package gestionDocumentos.consultaDocumentos;

import basedatos.ColumnBase;
import basedatos.ColumnCabecera;
import basedatos.DataSet;
import basedatos.Row;
import basedatos.RowCabecera;
import basedatos.servicios.StAConftipodoc;
import basedatos.servicios.StADocfirma;
import basedatos.servicios.StAFirmante;
import basedatos.servicios.StDDocumento;
import basedatos.servicios.StTAutoridad;
import basedatos.tablas.BdAConftipodoc;
import basedatos.tablas.BdADocfirma;
import basedatos.tablas.BdAFirmante;
import basedatos.tablas.BdDDocumento;
import basedatos.tablas.BdTAutoridad;
import excepciones.FormModeException;
import excepciones.RegistryNotFoundException;
import excepciones.RequiredFieldException;
import init.AppInit;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.stream.Collectors;
import javax.el.ELContext;
import javax.faces.context.FacesContext;
import tomcat.persistence.EntityManager;
import utilidades.CampoWebChips;
import utilidades.CampoWebCodigo;
import utilidades.CampoWebCombo;
import utilidades.CampoWebDescripcion;
import utilidades.CampoWebFecha;
import utilidades.CampoWebLupa;
import utilidades.CampoWebNumero;
import utilidades.CampoWebUpload;
import utilidades.Mensajes;
import utilidades.ModoFormulario;
import utilidades.Msg;
import utilidades.Session;
import utilidades.Validation;

/**
 *
 * @author ihuegal
 */
public class EdicionConsultaDocumentos implements Serializable {
    private Object parent = null;
    private String paginaRetorno = null;
    
    // DOCUMENTO
    private CampoWebCodigo cCoDocumento = null;
    private CampoWebDescripcion cDsDocumento = null;
    private CampoWebFecha cFeAlta = null;
    private CampoWebFecha cFeDesactivo = null;
    private CampoWebLupa cTipoDocumento = null;
    private CampoWebUpload cFuDocumento = null;
    private CampoWebLupa cSituacionDoc = null;
        
    private DataSet dsFirmas = null;
    private ArrayList<Integer> listaFirmasEliminadasId = null;
    
    // FIRMAS
    private CampoWebNumero cEnOrden = null;
    private CampoWebCombo cDiTipoFirma = null;
    private CampoWebLupa cAutoridad = null;
    private CampoWebChips cFirmantes = null;
    private CampoWebNumero cDsFirmaPosX = null;
    private CampoWebNumero cDsFirmaPosY = null;
    private CampoWebFecha cFeAltaFirma = null;
    private CampoWebFecha cFeDesactivoFirma = null;
    
    BdDDocumento bdDDocumento = null;

    private ModoFormulario modoFormulario = ModoFormulario.CONSULTA;
    
    public EdicionConsultaDocumentos(Object parent) {
        init(parent, null);
    }
    
    public EdicionConsultaDocumentos(Object parent, Integer idDocumento) {
        init(parent, idDocumento);
    }
    
    private void init(Object parent, Integer idDocumento) {
        try {
            this.parent = parent;
    
            // TIPO DOCUMENTO
            this.cCoDocumento = new CampoWebCodigo();
            this.cCoDocumento.setLabel(Msg.getString("lbl_EdicionConsultaDocumentos_CoDocumento"));
            this.cCoDocumento.setWidthLabel("100px");
//            this.cCoDocumento.setRequired(true);
            this.cCoDocumento.setProtegido(true);
            
            this.cDsDocumento = new CampoWebDescripcion();
            this.cDsDocumento.setLabel(Msg.getString("lbl_EdicionConsultaDocumentos_DsDocumento"));
            this.cDsDocumento.setWidthLabel("100px");
            this.cDsDocumento.setRequired(true);
            
            this.cFeAlta = new CampoWebFecha();
            this.cFeAlta.setLabel(Msg.getString("lbl_EdicionConsultaDocumentos_FeAlta"));
            this.cFeAlta.setWidthLabel("100px");
            this.cFeAlta.setRequired(true);
            
            this.cFeDesactivo = new CampoWebFecha();
            this.cFeDesactivo.setLabel(Msg.getString("lbl_EdicionConsultaDocumentos_FeDesactivo"));
            this.cFeDesactivo.setWidthLabel("100px");
            
            this.cTipoDocumento = new CampoWebLupa();
            this.cTipoDocumento.setLabel(Msg.getString("lbl_EdicionConsultaDocumentos_TipoDocumento"));
            this.cTipoDocumento.setWidthLabel("100px");
            String sql = "SELECT ID_TIPODOCUMENTO, CO_TIPODOCUMENTO + ' - ' + DS_TIPODOCUMENTO as Tipo FROM BD_T_TIPODOCUMENTO ";
            this.cTipoDocumento.setConsulta(sql);
            this.cTipoDocumento.setColumnaID("ID_TIPODOCUMENTO");
            this.cTipoDocumento.setColumnaLabel("Tipo");
            this.cTipoDocumento.setUpdate("formulario:panelFirmas");
            this.cTipoDocumento.setOnItemChanged(this, this.getClass().getMethod("cTipoDocumento_ItemChanged"));

            this.cFuDocumento = new CampoWebUpload();
            this.cFuDocumento.setLabel(Msg.getString("lbl_EdicionConsultaDocumentos_Documento"));
            this.cFuDocumento.setWidthLabel("100px");
            this.cFuDocumento.setRequired(true);
            
            this.cSituacionDoc = new CampoWebLupa();
            this.cSituacionDoc.setLabel(Msg.getString("lbl_EdicionConsultaDocumentos_SituacionDoc"));
            this.cSituacionDoc.setWidthLabel("100px");
            sql = "SELECT ID_SITUACIONDOC, CO_SITUACIONDOC + ' - ' + DS_SITUACIONDOC as Situación FROM BD_T_SITUACIONDOC";
            this.cSituacionDoc.setConsulta(sql);
            this.cSituacionDoc.setColumnaID("ID_SITUACIONDOC");
            this.cSituacionDoc.setColumnaLabel("Situación");
            
            // LISTADO FIRMAS
            this.dsFirmas = new DataSet();
            this.listaFirmasEliminadasId = new ArrayList<>();
            
            // EDICION DE FIRMA
            this.cEnOrden = new CampoWebNumero();
            this.cEnOrden.setLabel(Msg.getString("lbl_EdicionConsultaDocumentos_EnOrden"));
            this.cEnOrden.setWidthLabel("100px");
            this.cEnOrden.setSize(2);
            this.cEnOrden.setMinValue(0);
            this.cEnOrden.setMaxValue(10);
            this.cEnOrden.setNumDecimales(0);
            this.cEnOrden.setProtegido(true);
            
            this.cDiTipoFirma = new CampoWebCombo();
            this.cDiTipoFirma.setLabel(Msg.getString("lbl_EdicionConsultaDocumentos_DiTipoFirma"));
            this.cDiTipoFirma.setWidthLabel("7em");
            this.cDiTipoFirma.setWidth("100%");
            
            this.cAutoridad = new CampoWebLupa();
            this.cAutoridad.setLabel(Msg.getString("lbl_EdicionConsultaDocumentos_Autoridad"));
            this.cAutoridad.setWidthLabel("100px");
            sql = "SELECT ID_AUTORIDAD, CO_AUTORIDAD + ' - ' + DS_AUTORIDAD as Autoridad FROM BD_T_AUTORIDAD";
            this.cAutoridad.setConsulta(sql);
            this.cAutoridad.setColumnaID("ID_AUTORIDAD");
            this.cAutoridad.setColumnaLabel("Autoridad");
        
            this.cFirmantes = new CampoWebChips();
            this.cFirmantes.setLabel(Msg.getString("lbl_EdicionConsultaDocumentos_Firmantes"));
            this.cFirmantes.setWidthLabel("100px");
            
            this.cDsFirmaPosX = new CampoWebNumero();
            this.cDsFirmaPosX.setLabel(Msg.getString("lbl_EdicionConsultaDocumentos_DsFirmaPosX"));
            this.cDsFirmaPosX.setWidthLabel("100px");
            this.cDsFirmaPosX.setSize(4);
            this.cDsFirmaPosX.setMinValue(0);
            this.cDsFirmaPosX.setMaxValue(9999);
            this.cDsFirmaPosX.setNumDecimales(0);
            
            this.cDsFirmaPosY = new CampoWebNumero();
            this.cDsFirmaPosY.setLabel(Msg.getString("lbl_EdicionConsultaDocumentos_DsFirmaPosY"));
            this.cDsFirmaPosY.setWidthLabel("100px");
            this.cDsFirmaPosY.setSize(4);
            this.cDsFirmaPosY.setMinValue(0);
            this.cDsFirmaPosY.setMaxValue(9999);
            this.cDsFirmaPosY.setNumDecimales(0);

            this.cFeAltaFirma = new CampoWebFecha();
            this.cFeAltaFirma.setLabel(Msg.getString("lbl_EdicionConsultaDocumentos_FeAltaFirma"));
            this.cFeAltaFirma.setWidthLabel("100px");

            this.cFeDesactivoFirma = new CampoWebFecha();
            this.cFeDesactivoFirma.setLabel(Msg.getString("lbl_EdicionConsultaDocumentos_FeDesactivoFirma"));
            this.cFeDesactivoFirma.setWidthLabel("100px");
            
            this.setModoFormulario(ModoFormulario.CONSULTA);
            
            if (idDocumento != null) {
                recuperarRegistro(idDocumento);
                inicializarDataSetFirmas(idDocumento);
                cFuDocumento.setRequired(false);
            }
            else {
                this.setModoFormulario(ModoFormulario.ALTA);
                inicializarDataSetFirmas(null);
                cFuDocumento.setRequired(true);
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

    public void cTipoDocumento_ItemChanged() {
        try {
            if (cTipoDocumento.getValue() != null) {
                String codigo = cTipoDocumento.getValue().getLabel().split(" - ")[0].trim();
                if (codigo.equalsIgnoreCase("GEN-XAdES") || codigo.equalsIgnoreCase("GEN-PAdES")) {
                    //Limpiamos DataSet y habilitamos edicion para configuracion manual de las firmas.
                    if (dsFirmas != null) {
                        this.dsFirmas.setModoFormulario(this.modoFormulario);
                    }
                }
                else {
                    cargarFirmasPorTipoDocumento(cTipoDocumento.getValue().getId());
                    this.dsFirmas.setModoFormulario(ModoFormulario.CONSULTA);
                }
            }
            else {
                if (dsFirmas != null) {
                    this.dsFirmas.setModoFormulario(this.modoFormulario);
                }
            }
        }
        catch (Exception ex) {
            Mensajes.showException(this.getClass(), ex);
        }
    }    
    
    private void cargarFirmasPorTipoDocumento(Integer idTipodocumento) throws Exception {
        
        StAConftipodoc stAConftipodoc = new StAConftipodoc(Session.getDatosUsuario());
        BdAConftipodoc filtroBdAConftipodoc = new BdAConftipodoc();
        filtroBdAConftipodoc.setIdTipodocumento(idTipodocumento);
        filtroBdAConftipodoc.setFeAlta(new Date());
        filtroBdAConftipodoc.setFeDesactivo(new Date());
        ArrayList<BdAConftipodoc> listaBdAConftipodoc = stAConftipodoc.filtro(filtroBdAConftipodoc, null);
        
        if (listaBdAConftipodoc != null && !listaBdAConftipodoc.isEmpty())
        {
            this.dsFirmas.clearRows();
            
            listaBdAConftipodoc = (ArrayList<BdAConftipodoc>) listaBdAConftipodoc.stream()
                    .sorted(Comparator.comparingInt(BdAConftipodoc::getEnOrden))
                    .collect(Collectors.toList());
            
            for (BdAConftipodoc bdAConftipodoc : listaBdAConftipodoc) {
                BdTAutoridad bdTAutoridad = new StTAutoridad(Session.getDatosUsuario()).item(bdAConftipodoc.getIdAutoridad(), null);
                
                Row newRow = this.dsFirmas.newRow();
                newRow.getColumnName("ID_DOCFIRMA").setValue(null);
                newRow.getColumnName("ID_DOCUMENTO").setValue(null);
                newRow.getColumnName("ID_AUTORIDAD").setValue(bdAConftipodoc.getIdAutoridad());
                newRow.getColumnName("EN_ORDEN").setValue(bdAConftipodoc.getEnOrden());
                newRow.getColumnName("DI_TIPOFIRMA").setValue(bdAConftipodoc.getDiTipofirma());
                newRow.getColumnName("DS_TIPOFIRMA").setValue(obtenerDsTipoFirma(bdAConftipodoc.getDiTipofirma()));
                newRow.getColumnName("Autoridad").setValue(bdTAutoridad.getCoAutoridad() + " - " + bdTAutoridad.getDsAutoridad());
                newRow.getColumnName("Firmantes").setValue(null);
                newRow.getColumnName("DS_FIRMAPOSX").setValue(bdAConftipodoc.getDsFirmaposx());
                newRow.getColumnName("DS_FIRMAPOSY").setValue(bdAConftipodoc.getDsFirmaposy());
                newRow.getColumnName("FE_ALTA").setValue(bdAConftipodoc.getFeAlta());
                newRow.getColumnName("FE_DESACTIVO").setValue(bdAConftipodoc.getFeDesactivo());

                this.dsFirmas.getRows().add(newRow);
            }
        }
    }
    
    private String obtenerDsTipoFirma(String diTipofirma) {
        return switch (diTipofirma) {
            case "F" -> "Firma";
            case "CO" -> "Cofirma";
            case "CT" -> "Contrafirma";
            default -> "";
        };
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
                        this.bdDDocumento = new BdDDocumento();
                        this.bdDDocumento.setCoDocumento(null);//this.cCoDocumento.getValue());
                        this.bdDDocumento.setDsDocumento(this.cDsDocumento.getValue());
                        this.bdDDocumento.setIdTipodocumento(this.cTipoDocumento.getId());
                        this.bdDDocumento.setBlDocumento(this.cFuDocumento.getBinFichero());
                        this.bdDDocumento.setCoFichero(this.cFuDocumento.getFilename());
                        this.bdDDocumento.setCoExtension(this.cFuDocumento.getExtension());
                        this.bdDDocumento.setIdSituaciondoc(this.cSituacionDoc.getId());
                        this.bdDDocumento.setFeAlta(this.cFeAlta.getValue());
                        this.bdDDocumento.setFeDesactivo(this.cFeDesactivo.getValue());

                        StDDocumento stDDocumento = new StDDocumento(Session.getDatosUsuario());
                        stDDocumento.alta(this.bdDDocumento, entityManager);

                        if (this.dsFirmas != null) {
                            for (Row itemRow : this.dsFirmas.getRows()) {
                                BdADocfirma newBdADocfirma = new BdADocfirma();
                                newBdADocfirma.setIdDocumento(this.bdDDocumento.getIdDocumento());
                                newBdADocfirma.setIdAutoridad(itemRow.getColumnName("ID_AUTORIDAD").getValueInteger());
                                newBdADocfirma.setEnOrden(itemRow.getColumnName("EN_ORDEN").getValueInteger());
                                newBdADocfirma.setDiTipofirma(itemRow.getColumnName("DI_TIPOFIRMA").getValueString());
                                newBdADocfirma.setDsFirmaposx(itemRow.getColumnName("DS_FIRMAPOSX").getValueString());
                                newBdADocfirma.setDsFirmaposy(itemRow.getColumnName("DS_FIRMAPOSY").getValueString());
                                newBdADocfirma.setFeAlta((Date)itemRow.getColumnName("FE_ALTA").getValue());
                                newBdADocfirma.setFeDesactivo((Date)itemRow.getColumnName("FE_DESACTIVO").getValue());

                                StADocfirma stADocfirma = new StADocfirma(Session.getDatosUsuario());
                                stADocfirma.alta(newBdADocfirma, entityManager);
                                
                                //ALTA FIRMANTES NIF
                                if (itemRow.getColumnName("Firmantes").getValueString() != null) {
                                    String[] listaFirmantes = itemRow.getColumnName("Firmantes").getValueString().split(",");
                                    for (String coNif : listaFirmantes) {
                                        BdAFirmante newBdAFirmante = new BdAFirmante();
                                        newBdAFirmante.setIdDocfirma(newBdADocfirma.getIdDocfirma());
                                        newBdAFirmante.setCoNif(coNif);
                                        
                                        StAFirmante stAFirmante = new StAFirmante(Session.getDatosUsuario());
                                        stAFirmante.alta(newBdAFirmante, entityManager);
                                    }
                                }
                                //FIN ALTA FIRMANTES NIF
                            }
                        }
                        
                        entityManager.getTransaction().commit();
                                
                        if (this.parent instanceof FiltroConsultaDocumentos filtroConsultaDocumentos) {
                            filtroConsultaDocumentos.getDsResultado().refrescarDatos();
                        }

                        Mensajes.showInfo(Msg.getString("Informacion"), Msg.getString("alta_OK"));
                    }

                    //ACTUALIZACION
                    if (this.modoFormulario == ModoFormulario.EDICION) {
                        this.bdDDocumento = new BdDDocumento();
                        this.bdDDocumento.setCoDocumento(this.cCoDocumento.getValue());
                        this.bdDDocumento.setDsDocumento(this.cDsDocumento.getValue());
                        this.bdDDocumento.setIdTipodocumento(this.cTipoDocumento.getId());
                        this.bdDDocumento.setBlDocumento(this.cFuDocumento.getBinFichero());
                        this.bdDDocumento.setCoFichero(this.cFuDocumento.getFilename());
                        this.bdDDocumento.setCoExtension(this.cFuDocumento.getExtension());
                        this.bdDDocumento.setIdSituaciondoc(this.cSituacionDoc.getId());
                        this.bdDDocumento.setFeAlta(this.cFeAlta.getValue());
                        this.bdDDocumento.setFeDesactivo(this.cFeDesactivo.getValue());

                        StDDocumento stDDocumento = new StDDocumento(Session.getDatosUsuario());
                        stDDocumento.actualiza(this.bdDDocumento, entityManager);

                        for (Integer idDocfirma : listaFirmasEliminadasId) {
                            BdADocfirma delBdADocfirma = new BdADocfirma();
                            delBdADocfirma.setIdDocfirma(idDocfirma);
                            
                            StADocfirma stADocfirma = new StADocfirma(Session.getDatosUsuario());
                            stADocfirma.baja(delBdADocfirma, entityManager);
                        }
                        
                        if (this.dsFirmas != null) {
                            for (Row itemRow : this.dsFirmas.getRows()) {
                                if  (itemRow.getColumnName("ID_DOCFIRMA").getValue() == null) {
                                    //ALTA
                                    BdADocfirma newBdADocfirma = new BdADocfirma();
                                    newBdADocfirma.setIdDocumento(this.bdDDocumento.getIdDocumento());
                                    newBdADocfirma.setIdAutoridad(itemRow.getColumnName("ID_AUTORIDAD").getValueInteger());
                                    newBdADocfirma.setEnOrden(itemRow.getColumnName("EN_ORDEN").getValueInteger());
                                    newBdADocfirma.setDiTipofirma(itemRow.getColumnName("DI_TIPOFIRMA").getValueString());
                                    newBdADocfirma.setDsFirmaposx(itemRow.getColumnName("DS_FIRMAPOSX").getValueString());
                                    newBdADocfirma.setDsFirmaposy(itemRow.getColumnName("DS_FIRMAPOSY").getValueString());
                                    newBdADocfirma.setFeAlta((Date)itemRow.getColumnName("FE_ALTA").getValue());
                                    newBdADocfirma.setFeDesactivo((Date)itemRow.getColumnName("FE_DESACTIVO").getValue());

                                    StADocfirma stADocfirma = new StADocfirma(Session.getDatosUsuario());
                                    stADocfirma.alta(newBdADocfirma, entityManager);
                                }
                                else {
                                    //MODIFICACION
                                    BdADocfirma upBdADocfirma = new BdADocfirma();
                                    upBdADocfirma.setIdDocfirma(itemRow.getColumnName("ID_DOCFIRMA").getValueInteger());
                                    upBdADocfirma.setIdDocumento(itemRow.getColumnName("ID_DOCUMENTO").getValueInteger());
                                    upBdADocfirma.setIdAutoridad(itemRow.getColumnName("ID_AUTORIDAD").getValueInteger());
                                    upBdADocfirma.setEnOrden(itemRow.getColumnName("EN_ORDEN").getValueInteger());
                                    upBdADocfirma.setDiTipofirma(itemRow.getColumnName("DI_TIPOFIRMA").getValueString());
                                    upBdADocfirma.setDsFirmaposx(itemRow.getColumnName("DS_FIRMAPOSX").getValueString());
                                    upBdADocfirma.setDsFirmaposy(itemRow.getColumnName("DS_FIRMAPOSY").getValueString());
                                    upBdADocfirma.setFeAlta((Date)itemRow.getColumnName("FE_ALTA").getValue());
                                    upBdADocfirma.setFeDesactivo((Date)itemRow.getColumnName("FE_DESACTIVO").getValue());

                                    StADocfirma stADocfirma = new StADocfirma(Session.getDatosUsuario());
                                    stADocfirma.actualiza(upBdADocfirma, entityManager);
                                }
                            }
                        }

                        entityManager.getTransaction().commit();

                        if (this.parent instanceof FiltroConsultaDocumentos filtroConsultaDocumentos) {
                            ELContext elContext = FacesContext.getCurrentInstance().getELContext();
                            filtroConsultaDocumentos = (FiltroConsultaDocumentos)elContext.getELResolver().getValue(elContext, null, "filtroConsultaDocumentos");
                            filtroConsultaDocumentos.getDsResultado().actualizarFilaSeleccionada();
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
            cTipoDocumento_ItemChanged();
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
                    BdADocfirma filtroBdADocfirma = new BdADocfirma();
                    filtroBdADocfirma.setIdDocumento(this.bdDDocumento.getIdDocumento());
                    StADocfirma stADocfirma = new StADocfirma(Session.getDatosUsuario());
                    ArrayList<BdADocfirma> listaADocfirma = stADocfirma.filtro(filtroBdADocfirma, entityManager);
                    if (listaADocfirma != null && !listaADocfirma.isEmpty()) {
                        for (BdADocfirma itemBdAConftipodoc : listaADocfirma) {
                            stADocfirma.baja(itemBdAConftipodoc, entityManager);
                        }
                    }
                    
                    StDDocumento stDDocumento = new StDDocumento(Session.getDatosUsuario());
                    stDDocumento.baja(this.bdDDocumento, entityManager);

                    entityManager.getTransaction().commit();
                    
                    if (this.parent instanceof FiltroConsultaDocumentos filtroConsultaDocumentos) {
                        filtroConsultaDocumentos.getDsResultado().eliminarFilaSeleccionada();
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
        this.cCoDocumento.setValue(null);
        this.cDsDocumento.setValue(null);
        this.cTipoDocumento.setId(null);
        this.cFuDocumento.setValue(null);
        this.cFuDocumento.setFilename(null);
        this.cFuDocumento.setExtension(null);
        this.cFuDocumento.setBinFichero(null);
        this.cSituacionDoc.setValue(null);
        this.cFeAlta.setValue(null);
        this.cFeDesactivo.setValue(null);
        
        this.cEnOrden.setValueInteger(null);
        this.cDiTipoFirma.setValue(null);
        this.cAutoridad.setId(null);
        this.cFirmantes.setValues(null);
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
                recuperarRegistro(this.bdDDocumento.getIdDocumento());
                inicializarDataSetFirmas(this.bdDDocumento.getIdDocumento());
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
//        if (Validation.isNullOrEmpty(this.cCoDocumento.getValue())) {
//            throw new RequiredFieldException(this.cCoDocumento.getLabel());
//        }
        if (Validation.isNullOrEmpty(this.cDsDocumento.getValue())) {
            throw new RequiredFieldException(this.cDsDocumento.getLabel());
        }
        if (Validation.isNullOrEmpty(this.cTipoDocumento.getId())) {
            throw new RequiredFieldException(this.cTipoDocumento.getLabel());
        }
        if (Validation.isNullOrEmpty(cFuDocumento.getValue()) && Validation.isNullOrEmpty(cFuDocumento.getBinFichero())) {
            throw new RequiredFieldException("Documento adjunto");
        }
        if (Validation.isNullOrEmpty(this.cSituacionDoc.getValue())) {
            throw new RequiredFieldException(this.cSituacionDoc.getLabel());
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
        if (Validation.isNullOrEmpty(this.cAutoridad.getValue())
                && Validation.isNullOrEmpty(this.cFirmantes.getValues())) {
            throw new RequiredFieldException(this.cAutoridad.getLabel() + " o " + this.cFirmantes.getLabel());
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
    
    private void protegerCamposFirma(boolean boProtegido) {
        this.cDiTipoFirma.setProtegido(boProtegido);
        this.cAutoridad.setProtegido(boProtegido);
        this.cFirmantes.setProtegido(boProtegido);
        this.cDsFirmaPosX.setProtegido(boProtegido);
        this.cDsFirmaPosY.setProtegido(boProtegido);
        this.cFeAltaFirma.setProtegido(boProtegido);
        this.cFeDesactivoFirma.setProtegido(boProtegido);
    }
    
    private void protegerCampos() throws Exception {
        switch (this.modoFormulario) {
            case CONSULTA, ELIMINADO -> {
//                this.cCoDocumento.setProtegido(true);
                this.cDsDocumento.setProtegido(true);
                this.cTipoDocumento.setProtegido(true);
                this.cFuDocumento.setProtegido(true);
                this.cSituacionDoc.setProtegido(true);
                this.cFeAlta.setProtegido(true);
                this.cFeDesactivo.setProtegido(true);
                
                protegerCamposFirma(true);
            }
            case EDICION -> {
//                this.cCoDocumento.setProtegido(false);
                this.cDsDocumento.setProtegido(false);
                this.cTipoDocumento.setProtegido(false);
                this.cFuDocumento.setProtegido(false);
                this.cSituacionDoc.setProtegido(false);
                this.cFeAlta.setProtegido(false);
                this.cFeDesactivo.setProtegido(false);
                
                protegerCamposFirma(false);
            }
            case ALTA -> {
                limpiar();
//                this.cCoDocumento.setProtegido(false);
                this.cDsDocumento.setProtegido(false);
                this.cTipoDocumento.setProtegido(false);
                this.cFuDocumento.setProtegido(false);
                this.cSituacionDoc.setProtegido(false);
                this.cFeAlta.setProtegido(false);
                this.cFeDesactivo.setProtegido(false);
                
                protegerCamposFirma(false);
            }
            default -> throw new FormModeException();
        }
    }
    
    private void recuperarRegistro(Integer idDocumento) throws Exception {
        StDDocumento stDDocumento = new StDDocumento(Session.getDatosUsuario());
        this.bdDDocumento = stDDocumento.item(idDocumento, null);
        if (this.bdDDocumento == null) {
            throw new RegistryNotFoundException();
        }
        
        cCoDocumento.setValue(this.bdDDocumento.getCoDocumento());
        cDsDocumento.setValue(this.bdDDocumento.getDsDocumento());
        cFeAlta.setValue(this.bdDDocumento.getFeAlta());
        cFeDesactivo.setValue(this.bdDDocumento.getFeDesactivo());
        cTipoDocumento.setId(this.bdDDocumento.getIdTipodocumento());
        cFuDocumento.setFilename(this.bdDDocumento.getCoFichero());
        cFuDocumento.setExtension(this.bdDDocumento.getCoExtension());
        cFuDocumento.setBinFichero(this.bdDDocumento.getBlDocumento(null));
        cSituacionDoc.setId(this.bdDDocumento.getIdSituaciondoc());
    }

    private void inicializarDataSetFirmas(Integer idDocumento) throws Exception {
        String sql = """
            SELECT 
                T1.ID_DOCFIRMA, 
                T1.ID_DOCUMENTO,
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
                (SELECT STRING_AGG(CO_NIF, ',') FROM BD_A_FIRMANTE WHERE ID_DOCFIRMA = T1.ID_DOCFIRMA) as Firmantes,
                T1.FE_ALTA, 
                T1.FE_DESACTIVO
            FROM 
                BD_A_DOCFIRMA T1
            LEFT JOIN
                BD_T_AUTORIDAD aut ON (aut.ID_AUTORIDAD = T1.ID_AUTORIDAD)
            WHERE 1 = 1
            AND T1.ID_DOCUMENTO = :ID_DOCUMENTO
            ORDER BY
                T1.EN_ORDEN ASC
            """;
        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_DOCUMENTO", idDocumento);

        dsFirmas = new DataSet(sql, parametros, "ID_DOCFIRMA");
        
        // Establecer formato de salida
        RowCabecera cabecera = this.getDsFirmas().getCabecera();

        cabecera.getColumnName("ID_DOCFIRMA")
                .setVisible(false);

        cabecera.getColumnName("ID_DOCUMENTO")
                .setVisible(false);

        cabecera.getColumnName("ID_AUTORIDAD")
                .setVisible(false);

        cabecera.getColumnName("EN_ORDEN")
                .setTitle("Orden")
                .setWidth("6rem")
                .setTipo(ColumnBase.Tipo.LINK)
                .setClase(this)
                .setMethod(this.getClass().getMethod("verDetalle"))
                .setOncomplete("if (!(args && args.validationFailed)) PF('dlgFirmas').show()")
                .setUpdate("formulario:panelFirmas,formulario:mensaje");

        cabecera.getColumnName("DI_TIPOFIRMA")
                .setVisible(false);

        cabecera.getColumnName("DS_TIPOFIRMA")
                .setTitle("Tipo")
                .setWidth("6rem");

        cabecera.getColumnName("Autoridad")
                .setTitle("Autoridad")
                .setWidth("100%");

        cabecera.getColumnName("Firmantes")
                .setTitle("Firmantes")
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
            this.cFirmantes.setValues(null);
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
                newRow.getColumnName("ID_DOCFIRMA").setValue(null);
                newRow.getColumnName("ID_DOCUMENTO").setValue(null);
                newRow.getColumnName("ID_AUTORIDAD").setValue(this.cAutoridad.getId());
                newRow.getColumnName("EN_ORDEN").setValue(this.cEnOrden.getValueInteger());
                newRow.getColumnName("DI_TIPOFIRMA").setValue(this.cDiTipoFirma.getValue());
                newRow.getColumnName("DS_TIPOFIRMA").setValue(this.cDiTipoFirma.getOptions().get(this.cDiTipoFirma.getValue().toString()));
                newRow.getColumnName("Autoridad").setValue((this.cAutoridad.getValue() != null ? this.cAutoridad.getValue().getLabel() : null));
                newRow.getColumnName("Firmantes").setValue((this.cFirmantes.getValues() != null ? String.join(",", this.cFirmantes.getValues()) : null));
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
                editRow.getColumnName("Autoridad").setValue((this.cAutoridad.getValue() != null ? this.cAutoridad.getValue().getLabel() : null));
                editRow.getColumnName("Firmantes").setValue((this.cFirmantes.getValues() != null ? String.join(",", this.cFirmantes.getValues()) : null));
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
            this.cEnOrden.setValueInteger(null);
            this.cDiTipoFirma.setValue(null);
            this.cAutoridad.setId(null);
            this.cFirmantes.setValues(null);
            this.cDsFirmaPosX.setValueInteger(null);
            this.cDsFirmaPosY.setValueInteger(null);
            this.cFeAltaFirma.setValue(null);
            this.cFeDesactivoFirma.setValue(null);
            
            camposFirmaRequeridos(false);
        } catch (Exception ex) {
            Mensajes.showException(this.getClass(), ex);
        }
    }
    
    public void verDetalle() {
        try {
            camposFirmaCargar(this.dsFirmas.getSelectedRow());
            if (this.dsFirmas.getModoFormulario() == ModoFormulario.CONSULTA) {
                protegerCamposFirma(true);
            }
            else {
                protegerCamposFirma(false);
            }
            camposFirmaRequeridos(true);
        } catch (Exception ex) {
            Mensajes.showException(this.getClass(), ex);
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

    public CampoWebCodigo getcCoDocumento() {
        return cCoDocumento;
    }

    public void setcCoDocumento(CampoWebCodigo cCoDocumento) {
        this.cCoDocumento = cCoDocumento;
    }

    public CampoWebDescripcion getcDsDocumento() {
        return cDsDocumento;
    }

    public void setcDsDocumento(CampoWebDescripcion cDsDocumento) {
        this.cDsDocumento = cDsDocumento;
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

    public CampoWebLupa getcTipoDocumento() {
        return cTipoDocumento;
    }

    public void setcTipoDocumento(CampoWebLupa cTipoDocumento) {
        this.cTipoDocumento = cTipoDocumento;
    }

    public CampoWebLupa getcSituacionDoc() {
        return cSituacionDoc;
    }

    public void setcSituacionDoc(CampoWebLupa cSituacionDoc) {
        this.cSituacionDoc = cSituacionDoc;
    }

    public CampoWebChips getcFirmantes() {
        return cFirmantes;
    }

    public void setcFirmantes(CampoWebChips cFirmantes) {
        this.cFirmantes = cFirmantes;
    }

    public BdDDocumento getBdDDocumento() {
        return bdDDocumento;
    }

    public void setBdDDocumento(BdDDocumento bdDDocumento) {
        this.bdDDocumento = bdDDocumento;
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

    public CampoWebUpload getcFuDocumento() {
        return cFuDocumento;
    }

    public void setcFuDocumento(CampoWebUpload cFuDocumento) {
        this.cFuDocumento = cFuDocumento;
    }

    private void camposFirmaRequeridos(boolean boRequerido) {
        this.cEnOrden.setRequired(boRequerido);
        this.cDiTipoFirma.setRequired(boRequerido);
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
        this.cFirmantes.setValues((selectedRow.getColumnName("Firmantes").getValueString() != null ? Arrays.asList(selectedRow.getColumnName("Firmantes").getValueString().split(",")) : null));
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
