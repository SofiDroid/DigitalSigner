package gestionDocumentos.consultaDocumentos;

import basedatos.ColumnBase;
import basedatos.ColumnCabecera;
import basedatos.DataSet;
import basedatos.RowCabecera;
import basedatos.servicios.StDDocumento;
import basedatos.tablas.BdDDocumento;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.apache.log4j.Logger;
import utilidades.CampoWebCodigo;
import utilidades.CampoWebDescripcion;
import utilidades.CampoWebFechaRango;
import utilidades.CampoWebLupa;
import utilidades.Formateos;
import utilidades.Mensajes;
import utilidades.Msg;
import utilidades.Session;

/**
 *
 * @author ihuegal
 */
@Named
@SessionScoped
public class FiltroConsultaDocumentos implements Serializable {
    private final Logger LOG = Logger.getLogger(FiltroConsultaDocumentos.class);
    
    private CampoWebCodigo cCoDocumento = null;
    private CampoWebDescripcion cDsDocumento = null;
    private CampoWebFechaRango cFeAlta = null;
    private CampoWebFechaRango cFeDesactivo = null;
    private CampoWebLupa cTipodocumento = null;
    private CampoWebLupa cSituaciondoc = null;
    private CampoWebLupa cAutoridad = null;
    private boolean filtroVisible = true;
    private DataSet dsResultado = null;
    //private EdicionDocumento edicionDocumento = null; 
    
    @PostConstruct
    public void init() {
        Session.limpiarOtrosBeans(this.getClass().getName());
        
        this.cCoDocumento = new CampoWebCodigo();
        this.cCoDocumento.setLabel(Msg.getString("lbl_FiltroConsultaDocumentos_CoDocumento"));
        this.cCoDocumento.setWidthLabel("70px");
        
        this.cDsDocumento = new CampoWebDescripcion();
        this.cDsDocumento.setLabel(Msg.getString("lbl_FiltroConsultaDocumentos_DsDocumento"));
        this.cDsDocumento.setWidthLabel("70px");
        
        this.cFeAlta = new CampoWebFechaRango();
        this.cFeAlta.setLabel(Msg.getString("lbl_FiltroConsultaDocumentos_FeAlta"));
        this.cFeAlta.setWidthLabel("100px");

        this.cFeDesactivo = new CampoWebFechaRango();
        this.cFeDesactivo.setLabel(Msg.getString("lbl_FiltroConsultaDocumentos_FeDesactivo"));
        this.cFeDesactivo.setWidthLabel("100px");

        this.cTipodocumento = new CampoWebLupa();
        this.cTipodocumento.setLabel(Msg.getString("lbl_FiltroConsultaDocumentos_TipoDocumento"));
        this.cTipodocumento.setWidthLabel("70px");
        String sql = "SELECT ID_TIPODOCUMENTO, CO_TIPODOCUMENTO + ' - ' + DS_TIPODOCUMENTO as Tipo FROM BD_T_TIPODOCUMENTO";
        this.cTipodocumento.setConsulta(sql);
        this.cTipodocumento.setColumnaID("ID_TIPODOCUMENTO");
        this.cTipodocumento.setColumnaLabel("Tipo");
        
        this.cSituaciondoc = new CampoWebLupa();
        this.cSituaciondoc.setLabel(Msg.getString("lbl_FiltroConsultaDocumentos_SituacionDoc"));
        this.cSituaciondoc.setWidthLabel("100px");
        sql = "SELECT ID_SITUACIONDOC, CO_SITUACIONDOC + ' - ' + DS_SITUACIONDOC as Situación FROM BD_T_SITUACIONDOC";
        this.cSituaciondoc.setConsulta(sql);
        this.cSituaciondoc.setColumnaID("ID_SITUACIONDOC");
        this.cSituaciondoc.setColumnaLabel("Situación");
        
        this.cAutoridad = new CampoWebLupa();
        this.cAutoridad.setLabel(Msg.getString("lbl_FiltroConsultaDocumentos_Autoridad"));
        this.cAutoridad.setWidthLabel("70px");
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

    public CampoWebLupa getcSituaciondoc() {
        return cSituaciondoc;
    }

    public void setcSituaciondoc(CampoWebLupa cSituaciondoc) {
        this.cSituaciondoc = cSituaciondoc;
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

    public boolean isFiltroVisible() {
        return filtroVisible;
    }

    public void setFiltroVisible(boolean filtroVisible) {
        this.filtroVisible = filtroVisible;
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
            cSituaciondoc.setValue(null);
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
                            situaciondoc.ID_SITUACIONDOC,
                            situaciondoc.CO_SITUACIONDOC,
                            situaciondoc.DS_SITUACIONDOC,
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
                            BD_T_SITUACIONDOC situaciondoc ON (situaciondoc.ID_SITUACIONDOC = doc.ID_SITUACIONDOC)
                        INNER JOIN
                            BD_A_DOCFIRMA docfirma ON (docfirma.ID_DOCUMENTO = doc.ID_DOCUMENTO)
                        INNER JOIN
                            BD_T_AUTORIDAD aut ON (aut.ID_AUTORIDAD = docfirma.ID_AUTORIDAD)
                        INNER JOIN
                            BD_A_AUTUSU autusu ON (autusu.ID_AUTORIDAD = aut.ID_AUTORIDAD)
                        INNER JOIN
                            BD_T_USUARIO usuario ON (usuario.ID_USUARIO = autusu.ID_USUARIO)
                        INNER JOIN
                            BD_T_UNIDAD uni ON (uni.ID_UNIDAD = aut.ID_UNIDAD)
                        WHERE 1 = 1
                        AND EXISTS(SELECT -1
                                    FROM
                                        BD_A_DOCFIRMA df
                                    INNER JOIN
                                        BD_A_AUTUSU au ON (au.ID_AUTORIDAD = df.ID_AUTORIDAD)
                                    WHERE df.ID_DOCUMENTO = doc.ID_DOCUMENTO
                                    AND au.ID_USUARIO = :ID_USUARIO)
                        """;
            
            sql = filtros(sql);

            this.dsResultado = new DataSet(sql, "ID_DOCUMENTO");

            if (this.getDsResultado().getRowsCount() > 0) {
                // Establecer formato de salida
                formateaResultado();
            }
        } catch (NoSuchMethodException | SecurityException | SQLException ex) {
            LOG.error(ex.getMessage(), ex);
            Mensajes.showError("Error al buscar", ex.getMessage());
        }
    }
    
    private String filtros(String sql) {
        
        sql = sql.replaceAll("(?i):ID_USUARIO", Session.getDatosUsuario().getBdTUsuario().getIdUsuario().toString());
        
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
        if (cSituaciondoc.getId() != null) {
            sql += " AND situaciondoc.ID_SITUACIONDOC = " + cSituaciondoc.getId();
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
            BdDDocumento bdDDocumento = new StDDocumento().item(idDocumento, null);
            Session.grabarAtributo("reportBytes", bdDDocumento.getBlDocumento());
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
            Mensajes.showError("Error al navegar al detalle", ex.getMessage());
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

    private void formateaResultado() throws NoSuchMethodException {

        this.dsResultado.setSelectable(false);
        this.dsResultado.setRowSelectColumnaID("ID_DOCUMENTO");
        
        RowCabecera cabecera = this.dsResultado.getCabecera();

        cabecera.getColumnName("ID_DOCUMENTO")
                .setVisible(false);

        cabecera.getColumnName("FE_ALTA")
                .setTitle("F. Alta")
                .setAlign(ColumnCabecera.ALIGN.CENTER)
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

        cabecera.getColumnName("ID_SITUACIONDOC")
                .setVisible(false);

        cabecera.getColumnName("CO_SITUACIONDOC")
                .setTitle("Situación")
                .setWidth("10em")
                .setTooltipColumn("DS_SITUACIONDOC");

        cabecera.getColumnName("DS_SITUACIONDOC")
                .setVisible(false);

        cabecera.getColumnName("FIRMAS")
                .setTitle("Nº Firmas")
                .setAlign(ColumnCabecera.ALIGN.CENTER)
                .setWidth("10em")
                .setTooltipColumn("FIRMAS");

        this.dsResultado.newColumn("btnDocumento");
        cabecera.getColumnName("btnDocumento")
                .setTitle("Ver Documento")
                .setAlign(ColumnCabecera.ALIGN.CENTER)
                .setWidth("10em")
                .setTipo(ColumnBase.Tipo.MEDIA)
                .setClase(this)
                .setMethod(this.getClass().getMethod("verDocumento"))
                .setUpdate("formulario:panelResultado,formulario:mensaje");
    }
}
