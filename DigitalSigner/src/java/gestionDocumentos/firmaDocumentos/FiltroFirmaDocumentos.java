package gestionDocumentos.firmaDocumentos;

import afirma.AfirmaUtils;
import afirma.ResultadoValidacionFirmas;
import basedatos.ColumnBase;
import basedatos.ColumnCabecera;
import basedatos.DataSet;
import basedatos.Row;
import basedatos.RowCabecera;
import basedatos.servicios.StDDocumento;
import basedatos.servicios.StTTipodocumento;
import basedatos.tablas.BdDDocumento;
import basedatos.tablas.BdTTipodocumento;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.apache.log4j.Logger;
import org.primefaces.context.PrimeRequestContext;
import utilidades.CampoWebCodigo;
import utilidades.CampoWebDescripcion;
import utilidades.CampoWebFechaRango;
import utilidades.CampoWebLupa;
import utilidades.Configuraciones;
import utilidades.Formateos;
import utilidades.Mensajes;
import utilidades.Msg;
import utilidades.Session;
import utilidades.VisorMedia;

/**
 *
 * @author ihuegal
 */
@Named
@SessionScoped
public class FiltroFirmaDocumentos implements Serializable {
    private final Logger LOG = Logger.getLogger(FiltroFirmaDocumentos.class);
    
    private CampoWebCodigo cCoDocumento = null;
    private CampoWebDescripcion cDsDocumento = null;
    private CampoWebFechaRango cFeAlta = null;
    private CampoWebFechaRango cFeDesactivo = null;
    private CampoWebLupa cTipodocumento = null;
    private CampoWebLupa cAutoridad = null;
    private boolean filtroVisible = true;
    private DataSet dsResultado = null;
    private VisorMedia visorDocumentos = null;
    private String mensajeError = null;
    //private EdicionDocumento edicionDocumento = null; 
    
    @PostConstruct
    public void init() {
        Session.limpiarOtrosBeans(this.getClass().getName());
        
        this.cCoDocumento = new CampoWebCodigo();
        this.cCoDocumento.setLabel(Msg.getString("lbl_FiltroFirmaDocumentos_CoDocumento"));
        this.cCoDocumento.setWidthLabel("70px");
        
        this.cDsDocumento = new CampoWebDescripcion();
        this.cDsDocumento.setLabel(Msg.getString("lbl_FiltroFirmaDocumentos_DsDocumento"));
        this.cDsDocumento.setWidthLabel("70px");
        
        this.cFeAlta = new CampoWebFechaRango();
        this.cFeAlta.setLabel(Msg.getString("lbl_FiltroFirmaDocumentos_FeAlta"));
        this.cFeAlta.setWidthLabel("100px");

        this.cFeDesactivo = new CampoWebFechaRango();
        this.cFeDesactivo.setLabel(Msg.getString("lbl_FiltroFirmaDocumentos_FeDesactivo"));
        this.cFeDesactivo.setWidthLabel("100px");

        this.cTipodocumento = new CampoWebLupa();
        this.cTipodocumento.setLabel(Msg.getString("lbl_FiltroFirmaDocumentos_TipoDocumento"));
        this.cTipodocumento.setWidthLabel("70px");
        String sql = "SELECT ID_TIPODOCUMENTO, CO_TIPODOCUMENTO + ' - ' + DS_TIPODOCUMENTO as Tipo FROM BD_T_TIPODOCUMENTO";
        this.cTipodocumento.setConsulta(sql);
        this.cTipodocumento.setColumnaID("ID_TIPODOCUMENTO");
        this.cTipodocumento.setColumnaLabel("Tipo");
        
        this.cAutoridad = new CampoWebLupa();
        this.cAutoridad.setLabel(Msg.getString("lbl_FiltroFirmaDocumentos_Autoridad"));
        this.cAutoridad.setWidthLabel("100px");
        sql = """
              SELECT 
                  aut.ID_AUTORIDAD, 
                  aut.CO_AUTORIDAD + ' - ' + aut.DS_AUTORIDAD as Autoridad
              FROM
                  BD_T_AUTORIDAD aut
              INNER JOIN
                  BD_A_AUTUSU autusu ON (autusu.ID_AUTORIDAD = aut.ID_AUTORIDAD AND (autusu.FE_ALTA <= CONVERT (date, SYSDATETIME())) AND (autusu.FE_DESACTIVO IS NULL OR autusu.FE_DESACTIVO >= CONVERT (date, SYSDATETIME())))
              INNER JOIN
                  BD_T_USUARIO usuario ON (usuario.ID_USUARIO = autusu.ID_USUARIO)
              INNER JOIN
                  BD_T_UNIDAD uni ON (uni.ID_UNIDAD = aut.ID_UNIDAD)
              WHERE
                  (aut.FE_ALTA <= CONVERT (date, SYSDATETIME())) 
              AND (aut.FE_DESACTIVO IS NULL OR aut.FE_DESACTIVO >= CONVERT (date, SYSDATETIME()))
              AND usuario.ID_USUARIO = """ + Session.getDatosUsuario().getBdTUsuario().getIdUsuario();
        this.cAutoridad.setConsulta(sql);
        this.cAutoridad.setColumnaID("ID_AUTORIDAD");
        this.cAutoridad.setColumnaLabel("Autoridad");
        
        this.dsResultado = new DataSet();
        this.visorDocumentos = new VisorMedia();
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
    
    public CampoWebFechaRango getcFeAlta() {
        return cFeAlta;
    }

    public void setcFeAlta(CampoWebFechaRango cFeAlta) {
        this.cFeAlta = cFeAlta;
    }

    public CampoWebFechaRango getcFeDesactivo() {
        return cFeDesactivo;
    }

    public void setcFeDesactivo(CampoWebFechaRango cFeDesactivo) {
        this.cFeDesactivo = cFeDesactivo;
    }

    public CampoWebLupa getcTipodocumento() {
        return cTipodocumento;
    }

    public void setcTipodocumento(CampoWebLupa cTipodocumento) {
        this.cTipodocumento = cTipodocumento;
    }

    public CampoWebLupa getcAutoridad() {
        return cAutoridad;
    }

    public void setcAutoridad(CampoWebLupa cAutoridad) {
        this.cAutoridad = cAutoridad;
    }

    public DataSet getDsResultado() {
        return dsResultado;
    }

    public void setDsResultado(DataSet dsResultado) {
        this.dsResultado = dsResultado;
    }

    public VisorMedia getVisorDocumentos() {
        return visorDocumentos;
    }

    public void setVisorDocumentos(VisorMedia visorDocumentos) {
        this.visorDocumentos = visorDocumentos;
    }

    public boolean isFiltroVisible() {
        return filtroVisible;
    }

    public void setFiltroVisible(boolean filtroVisible) {
        this.filtroVisible = filtroVisible;
    }

    public String getMensajeError() {
        return mensajeError;
    }

    public void setMensajeError(String mensajeError) {
        this.mensajeError = mensajeError;
    }
/*
    public EdicionUnidades getEdicionUnidades() {
        return edicionUnidades;
    }

    public void setEdicionUnidades(EdicionUnidades edicionUnidades) {
        this.edicionUnidades = edicionUnidades;
    }
*/    
    public void limpiar() {
        try {
            cCoDocumento.setValue(null);
            cDsDocumento.setValue(null);
            cFeAlta.setValueIni(null);
            cFeAlta.setValueFin(null);
            cFeDesactivo.setValueIni(null);
            cFeDesactivo.setValueFin(null);
            cTipodocumento.setValue(null);
            cAutoridad.setValue(null);
            
            this.dsResultado.clear();
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
            Mensajes.showError("Error al buscar", ex.getMessage());
        }
    }
    
    public void buscar() {
        try {
            String sql = """
                         SELECT DISTINCT
                             doc.ID_DOCUMENTO,
                             doc.FE_ALTA,
                             doc.CO_DOCUMENTO,
                             doc.DS_DOCUMENTO,
                             doc.CO_FICHERO,
                             doc.CO_EXTENSION,
                             tipodoc.ID_TIPODOCUMENTO,
                             tipodoc.CO_TIPODOCUMENTO,
                             tipodoc.DS_TIPODOCUMENTO,
                             tipodoc.DI_FORMATOFIRMA,
                             situaciondoc.ID_SITUACIONDOC,
                             situaciondoc.CO_SITUACIONDOC,
                             situaciondoc.DS_SITUACIONDOC,
                             docfirma.ID_DOCFIRMA,
                             docfirma.EN_ORDEN,
                             docfirma.DI_TIPOFIRMA,
                             (SELECT count(*) FROM BD_A_DOCFIRMA aux WHERE aux.ID_DOCUMENTO = doc.ID_DOCUMENTO
                                 AND (aux.FE_ALTA <= CONVERT (date, SYSDATETIME())) AND (aux.FE_DESACTIVO IS NULL OR docfirma.FE_DESACTIVO >= CONVERT (date, SYSDATETIME()))) as TOTAL_FIRMAS,
                             docfirma.FE_FIRMA,
                             aut.ID_AUTORIDAD,
                             aut.CO_AUTORIDAD,
                             aut.DS_AUTORIDAD,
                             uni.ID_UNIDAD,
                             uni.CO_UNIDAD,
                             uni.DS_UNIDAD,
                            (SELECT CAST(count(*) AS VARCHAR) FROM BD_A_DOCFIRMA firmas WHERE firmas.ID_DOCUMENTO = doc.ID_DOCUMENTO AND firmas.FE_FIRMA IS NOT NULL
                                AND (firmas.FE_ALTA <= CONVERT (date, SYSDATETIME())) AND (firmas.FE_DESACTIVO IS NULL OR firmas.FE_DESACTIVO >= CONVERT (date, SYSDATETIME()))) + 
                            ' de ' +
                            (SELECT CAST(count(*) AS VARCHAR) FROM BD_A_DOCFIRMA aux WHERE aux.ID_DOCUMENTO = doc.ID_DOCUMENTO
                                AND (aux.FE_ALTA <= CONVERT (date, SYSDATETIME())) AND (aux.FE_DESACTIVO IS NULL OR aux.FE_DESACTIVO >= CONVERT (date, SYSDATETIME()))) as FIRMAS
                         FROM
                             BD_D_DOCUMENTO doc
                         INNER JOIN
                             BD_T_TIPODOCUMENTO tipodoc ON (tipodoc.ID_TIPODOCUMENTO = doc.ID_TIPODOCUMENTO)
                         INNER JOIN
                             BD_T_SITUACIONDOC situaciondoc ON (situaciondoc.ID_SITUACIONDOC = doc.ID_SITUACIONDOC AND situaciondoc.CO_SITUACIONDOC = 'PENDIENTE_FIRMA')
                         INNER JOIN
                             BD_A_DOCFIRMA docfirma ON (docfirma.ID_DOCUMENTO = doc.ID_DOCUMENTO AND docfirma.FE_FIRMA IS NULL AND (docfirma.FE_ALTA <= CONVERT (date, SYSDATETIME())) AND (docfirma.FE_DESACTIVO IS NULL OR docfirma.FE_DESACTIVO >= CONVERT (date, SYSDATETIME())))
                         INNER JOIN
                             BD_T_AUTORIDAD aut ON (aut.ID_AUTORIDAD = docfirma.ID_AUTORIDAD AND (aut.FE_ALTA <= CONVERT (date, SYSDATETIME())) AND (aut.FE_DESACTIVO IS NULL OR aut.FE_DESACTIVO >= CONVERT (date, SYSDATETIME())))
                         INNER JOIN
                             BD_A_AUTUSU autusu ON (autusu.ID_AUTORIDAD = aut.ID_AUTORIDAD AND (autusu.FE_ALTA <= CONVERT (date, SYSDATETIME())) AND (autusu.FE_DESACTIVO IS NULL OR autusu.FE_DESACTIVO >= CONVERT (date, SYSDATETIME())))
                         INNER JOIN
                             BD_T_USUARIO usuario ON (usuario.ID_USUARIO = autusu.ID_USUARIO)
                         INNER JOIN
                             BD_T_UNIDAD uni ON (uni.ID_UNIDAD = aut.ID_UNIDAD)
                         WHERE 1 = 1
                         AND (SELECT COUNT(*) FROM BD_A_DOCFIRMA WHERE ID_DOCUMENTO = doc.ID_DOCUMENTO AND FE_FIRMA IS NULL AND EN_ORDEN < docfirma.EN_ORDEN) = 0
                         """;
            
            sql = filtros(sql);

            this.dsResultado = new DataSet(sql, "ID_DOCUMENTO");

            // Establecer formato de salida
            formatearCabeceras();
        } catch (NoSuchMethodException | SecurityException | SQLException ex) {
            LOG.error(ex.getMessage(), ex);
            Mensajes.showError("Error al buscar", ex.getMessage());
        }
    }
    
    private String filtros(String sql) {
        
        sql += " AND usuario.ID_USUARIO = " + Session.getDatosUsuario().getBdTUsuario().getIdUsuario();
        
        if (cCoDocumento.getValue() != null) {
            sql += " AND UPPER(doc.CO_DOCUMENTO) LIKE '%" + cCoDocumento.getValue().toUpperCase() + "%'";
        }
        if (cDsDocumento.getValue() != null) {
            sql += " AND UPPER(doc.DS_DOCUMENTO) LIKE '%" + cDsDocumento.getValue().toUpperCase() + "%'";
        }
        if (cFeAlta.getValueIni() != null || cFeAlta.getValueFin() != null) {
            sql += " AND (";
            if (cFeAlta.getValueIni() != null) {
                sql += "(doc.FE_ALTA >= " + Formateos.dateToSql((Date)cFeAlta.getValueIni()) + ")";
            }
            if (cFeAlta.getValueFin() != null) {
                if (cFeAlta.getValueIni() != null) {
                    sql += " AND ";
                }
                sql += "(doc.FE_ALTA <= " + Formateos.dateToSql((Date)cFeAlta.getValueFin()) + ")";
            }
            sql += ")";
        }
        if (cFeDesactivo.getValueIni() != null || cFeDesactivo.getValueFin() != null) {
            sql += " AND (";
            if (cFeDesactivo.getValueIni() != null) {
                sql += "(doc.FE_DESACTIVO >= " + Formateos.dateToSql((Date)cFeDesactivo.getValueIni()) + ")";
            }
            if (cFeDesactivo.getValueFin() != null) {
                if (cFeDesactivo.getValueIni() != null) {
                    sql += " AND ";
                }
                sql += "(doc.FE_DESACTIVO <= " + Formateos.dateToSql((Date)cFeDesactivo.getValueFin()) + ")";
            }
            sql += ")";
        }
        if (cTipodocumento.getId() != null) {
            sql += " AND tipodoc.ID_TIPODOCUMENTO = " + cTipodocumento.getId();
        }
        if (cAutoridad.getId() != null) {
            sql += " AND aut.ID_AUTORIDAD = " + cAutoridad.getId();
        }
        
        return sql;
    }
    
    public String verDetalle() {
        try {
            //this.edicionUnidades = new EdicionUnidades(this, this.dsResultado.getSelectedRow().getColumnName("ID_UNIDAD").getValueInteger());
            //this.edicionUnidades.setPaginaRetorno("filtroUnidades");
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
            Mensajes.showError("Error al navegar al detalle", ex.getMessage());
        }
        
        return null; //"edicionUnidades";
    }

    public String verDocumento() {
        try {
            Integer idDocumento = this.dsResultado.getSelectedRow().getColumnaID().getValueInteger();
            BdDDocumento bdDDocumento = new StDDocumento(Session.getDatosUsuario()).item(idDocumento, null);
            byte[] binDocumento = bdDDocumento.getBlDocumento(null);
            ResultadoValidacionFirmas resultadoValidacionFirmas = null;
            if (bdDDocumento.getCoExtension().equalsIgnoreCase("xsig")) {
                AfirmaUtils afirmaUtils = new AfirmaUtils();
                try {
                    resultadoValidacionFirmas = afirmaUtils.validarFirmas(binDocumento, true, true, true, true);
                }
                catch (Exception na) {
                    LOG.error(na.getMessage(), na);
                    Mensajes.showError("Error al recuperar firmantes del documento", na.getMessage());
                }
                binDocumento = afirmaUtils.recuperarDocumentoXSIG(binDocumento);
            }
            this.visorDocumentos.setFilename(bdDDocumento.getCoFichero());
            this.visorDocumentos.setPlayer("pdf");
            this.visorDocumentos.setResultadoValidacionFirmas(resultadoValidacionFirmas);
            Session.grabarAtributo("reportBytes", binDocumento);
        } catch (Exception ex) {
            Mensajes.showException(this.getClass(), ex);
        }
        
        return null;
    }

    public String nuevo() {
        try {
            //this.edicionUnidades = new EdicionUnidades(this, null);
            //this.edicionUnidades.setPaginaRetorno("filtroUnidades");
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
            Mensajes.showError("Error al navegar al detalle", ex.getMessage());
        }
        
        return null; //"edicionUnidades";
    }

    /**
     * Retorna los certificados admitidos para firmar.
     * 
     * Ejemplo:
     * 
     * filters.1=issuer.rfc2254:(|(CN=*MINISDEF*)(CN=*DEFENSA*));keyusage.nonrepudiation:true;nonexpired:\n
     * filters.2=issuer.rfc2254:(O=*POLICIA*);keyusage.nonrepudiation:true;nonexpired:\n
     * filters.3=issuer.rfc2254:(O=*FNMT*);keyusage.nonrepudiation:true;nonexpired:\n
     * 
     * @return String
     * 
     */
    public String getCertificadosAdmitidos() {
        return new Configuraciones(Session.getDatosUsuario()).recuperaConfiguracion("CERT-ADMITIDOS");
    }
    
    public String getHostServlets() {
        return new Configuraciones(Session.getDatosUsuario()).recuperaConfiguracion("HOST-TRIFASE");
    }
    
    public BdDDocumento getDocumentoByIndex(Integer idxFichero) throws Exception {
        Integer idDocumento = this.dsResultado.getRows().get(idxFichero).getColumnName("ID_DOCUMENTO").getValueInteger();
        return new StDDocumento(Session.getDatosUsuario()).item(idDocumento, null);
    }
    
    public void prepararListaDocumentos() {
        String listaDocumento = "";
        String listaTiposFirma = "";
        String listaFormatosFirma = "";
        if (this.dsResultado.getSelectedRows() != null && !this.dsResultado.getSelectedRows().isEmpty()) {
            for (Row itemSelectedRow : this.dsResultado.getSelectedRows()) {
                if (!listaDocumento.isBlank()) {
                    listaDocumento += ";";
                    listaTiposFirma += ";";
                    listaFormatosFirma += ";";
                }
                listaDocumento += itemSelectedRow.getIndex();
                listaTiposFirma += itemSelectedRow.getColumnName("DI_TIPOFIRMA").getValueString();
                //listaTiposFirma += (itemSelectedRow.getColumnName("EN_ORDEN").getValueInteger() > 1 ? "1" : "0");
                listaFormatosFirma += itemSelectedRow.getColumnName("DI_FORMATOFIRMA").getValueString();
            }
        }
        
        PrimeRequestContext requestContext = PrimeRequestContext.getCurrentInstance();
        requestContext.getCallbackParams().put("listaDocumentos", listaDocumento);
        requestContext.getCallbackParams().put("listaTiposFirma", listaTiposFirma);
        requestContext.getCallbackParams().put("listaFormatosFirma", listaFormatosFirma);
    }
    
    private void formatearCabeceras() throws SecurityException, NoSuchMethodException {

        this.dsResultado.setSelectable(true);
        this.dsResultado.setRowSelectColumnaID("ID_DOCUMENTO");
        
        RowCabecera cabecera = this.dsResultado.getCabecera();

        cabecera.getColumnName("ID_DOCUMENTO")
                .setVisible(false);

        cabecera.getColumnName("FE_ALTA")
                .setTitle("F. Alta")
                .setWidth("10em");

        cabecera.getColumnName("CO_DOCUMENTO")
                .setTitle("Código")
                .setWidth("10em")
                .setTipo(ColumnBase.Tipo.LINK)
                .setClase(this)
                .setMethod(this.getClass().getMethod("verDetalle"))
                .setUpdate("formulario:panelResultado,formulario:mensaje");

        cabecera.getColumnName("DS_DOCUMENTO")
                .setTitle("Descripción")
                .setWidth("100%");

        cabecera.getColumnName("CO_FICHERO")
                .setVisible(false);

        cabecera.getColumnName("CO_EXTENSION")
                .setVisible(false);

        cabecera.getColumnName("ID_TIPODOCUMENTO")
                .setVisible(false);

        cabecera.getColumnName("CO_TIPODOCUMENTO")
                .setTitle("Tipo")
                .setWidth("10em")
                .setTooltipColumn("DS_TIPODOCUMENTO");

        cabecera.getColumnName("DS_TIPODOCUMENTO")
                .setVisible(false);

        cabecera.getColumnName("DI_FORMATOFIRMA")
                .setTitle("Formato")
                .setWidth("6em");

        cabecera.getColumnName("ID_SITUACIONDOC")
                .setVisible(false);

        cabecera.getColumnName("CO_SITUACIONDOC")
                .setTitle("Situación")
                .setWidth("10em")
                .setTooltipColumn("DS_SITUACIONDOC");

        cabecera.getColumnName("DS_SITUACIONDOC")
                .setVisible(false);

        cabecera.getColumnName("ID_DOCFIRMA")
                .setVisible(false);

        cabecera.getColumnName("DI_TIPOFIRMA")
                .setVisible(false);

        cabecera.getColumnName("EN_ORDEN")
                .setVisible(false);

        cabecera.getColumnName("TOTAL_FIRMAS")
                .setVisible(false);

        cabecera.getColumnName("FE_FIRMA")
                .setVisible(false);

        cabecera.getColumnName("ID_AUTORIDAD")
                .setVisible(false);

        cabecera.getColumnName("CO_AUTORIDAD")
                .setTitle("Autoridad")
                .setWidth("10em")
                .setTooltipColumn("DS_AUTORIDAD");

        cabecera.getColumnName("DS_AUTORIDAD")
                .setVisible(false);

        cabecera.getColumnName("ID_UNIDAD")
                .setVisible(false);

        cabecera.getColumnName("CO_UNIDAD")
                .setVisible(false);

        cabecera.getColumnName("DS_UNIDAD")
                .setVisible(false);
        
        
        cabecera.getColumnName("FIRMAS")
                .setTitle("Nº Firmas")
                .setAlign(ColumnCabecera.ALIGN.CENTER)
                .setWidth("10em")
                .setTooltipColumn("FIRMAS");
        
        this.dsResultado.newColumn("btnDocumento");
        cabecera.getColumnName("btnDocumento")
                .setTitle("Ver Documento")
                .setWidth("10em")
                .setTipo(ColumnBase.Tipo.MEDIA)
                .setClase(this)
                .setMethod(this.getClass().getMethod("verDocumento"))
                .setIdVisorMedia("visorDocumentos")
                .setUpdate("formulario:panelResultado,formulario:mensaje");
    }
}
