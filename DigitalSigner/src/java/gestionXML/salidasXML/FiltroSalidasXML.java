package gestionXML.salidasXML;

import basedatos.ColumnBase;
import basedatos.ColumnCabecera;
import basedatos.DataSet;
import basedatos.RowCabecera;
import basedatos.servicios.StDSalidaxml;
import basedatos.tablas.BdDSalidaxml;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.apache.log4j.Logger;
import utilidades.CampoWebFechaRango;
import utilidades.CampoWebLupa;
import utilidades.CampoWebNumero;
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
public class FiltroSalidasXML implements Serializable {
    private final Logger LOG = Logger.getLogger(FiltroSalidasXML.class);
    
    private CampoWebNumero cIdSalidaXML = null;
    private CampoWebFechaRango cFeAlta = null;
    private CampoWebFechaRango cFeDesactivo = null;
    private CampoWebLupa cSituacionxml = null;
    private boolean filtroVisible = true;
    private DataSet dsResultado = null;
    
    @PostConstruct
    public void init() {
        Session.limpiarOtrosBeans(this.getClass().getName());
        
        this.cIdSalidaXML = new CampoWebNumero();
        this.cIdSalidaXML.setLabel(Msg.getString("lbl_FiltroSalidasXML_IdSalidaXML"));
        this.cIdSalidaXML.setWidthLabel("100px");
        
        this.cFeAlta = new CampoWebFechaRango();
        this.cFeAlta.setLabel(Msg.getString("lbl_FiltroSalidasXML_FeAlta"));
        this.cFeAlta.setWidthLabel("100px");

        this.cSituacionxml = new CampoWebLupa();
        this.cSituacionxml.setLabel(Msg.getString("lbl_FiltroSalidasXML_Situacionxml"));
        this.cSituacionxml.setWidthLabel("100px");
        String sql = "SELECT ID_SITUACIONXML, CO_SITUACIONXML + ' - ' + DS_SITUACIONXML as Situación FROM BD_T_SITUACIONXML";
        this.cSituacionxml.setConsulta(sql);
        this.cSituacionxml.setColumnaID("ID_SITUACIONXML");
        this.cSituacionxml.setColumnaLabel("Situación");
        
        this.cFeDesactivo = new CampoWebFechaRango();
        this.cFeDesactivo.setLabel(Msg.getString("lbl_FiltroSalidasXML_FeDesactivo"));
        this.cFeDesactivo.setWidthLabel("100px");

        this.dsResultado = new DataSet();
    }

    public CampoWebNumero getcIdSalidaXML() {
        return cIdSalidaXML;
    }

    public void setcIdSalidaXML(CampoWebNumero cIdSalidaXML) {
        this.cIdSalidaXML = cIdSalidaXML;
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

    public CampoWebLupa getcSituacionxml() {
        return cSituacionxml;
    }

    public void setcSituacionxml(CampoWebLupa cSituacionxml) {
        this.cSituacionxml = cSituacionxml;
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

    public void limpiar() {
        try {
            cIdSalidaXML.setValue(null);
            cFeAlta.setValueIni(null);
            cFeAlta.setValueFin(null);
            cFeDesactivo.setValueIni(null);
            cFeDesactivo.setValueFin(null);
            cSituacionxml.setValue(null);
            
            this.dsResultado.clear();
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
            Mensajes.showError("Error al buscar", ex.getMessage());
        }
    }
    
    public void buscar() {
        try {
            String sql = """
                        SELECT
                            sxml.ID_SALIDAXML,
                            sxml.ID_SITUACIONXML,
                            sitxml.CO_SITUACIONXML,
                            sitxml.DS_SITUACIONXML,
                            sxml.FE_ALTA,
                            sxml.FE_DESACTIVO
                        FROM
                            BD_D_SALIDAXML sxml
                        INNER JOIN
                            BD_T_SITUACIONXML sitxml ON (sitxml.ID_SITUACIONXML = sxml.ID_SITUACIONXML)
                        WHERE
                            1 = 1
                        """;
            
            sql = filtros(sql);

            this.dsResultado = new DataSet(sql, "ID_SALIDAXML");

            // Establecer formato de salida
            formateaResultado();
        } catch (NoSuchMethodException | SecurityException | SQLException ex) {
            LOG.error(ex.getMessage(), ex);
            Mensajes.showError("Error al buscar", ex.getMessage());
        }
    }
    
    private String filtros(String sql) {
        
        if (cIdSalidaXML.getValueInteger() != null) {
            sql += " AND sxml.ID_SALIDAXML = " + cIdSalidaXML.getValueInteger() + "";
        }
        if (cFeAlta.getValueIni() != null || cFeAlta.getValueFin() != null) {
            sql += " AND (";
            if (cFeAlta.getValueIni() != null) {
                sql += "(sxml.FE_ALTA >= " + Formateos.dateToSql((Date)cFeAlta.getValueIni()) + ")";
            }
            if (cFeAlta.getValueFin() != null) {
                if (cFeAlta.getValueIni() != null) {
                    sql += " AND ";
                }
                sql += "(sxml.FE_ALTA <= " + Formateos.dateToSql((Date)cFeAlta.getValueFin()) + ")";
            }
            sql += ")";
        }
        if (cFeDesactivo.getValueIni() != null || cFeDesactivo.getValueFin() != null) {
            sql += " AND (";
            if (cFeDesactivo.getValueIni() != null) {
                sql += "(sxml.FE_DESACTIVO >= " + Formateos.dateToSql((Date)cFeDesactivo.getValueIni()) + ")";
            }
            if (cFeDesactivo.getValueFin() != null) {
                if (cFeDesactivo.getValueIni() != null) {
                    sql += " AND ";
                }
                sql += "(sxml.FE_DESACTIVO <= " + Formateos.dateToSql((Date)cFeDesactivo.getValueFin()) + ")";
            }
            sql += ")";
        }
        if (cSituacionxml.getId() != null) {
            sql += " AND sitxml.ID_SITUACIONXML = " + cSituacionxml.getId();
        }
        
        return sql;
    }
    
    public String verDetalle() {
        try {

        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
            Mensajes.showError("Error al navegar al detalle", ex.getMessage());
        }
        
        return null;
    }

    public void verXML() {
        try {
            Integer idSalidaXML = this.dsResultado.getSelectedRow().getColumnaID().getValueInteger();
            BdDSalidaxml bdDSalidaxml = new StDSalidaxml().item(idSalidaXML, null);
            byte[] binDocumento = bdDSalidaxml.getBlSalidaxml(null);
            Session.grabarAtributo("filename", "SALIDA_" + idSalidaXML + ".xml");
            Session.grabarAtributo("binDocumento", binDocumento);
        } catch (Exception ex) {
            Mensajes.showException(this.getClass(), ex);
        }
    }

    public String nuevo() {
        try {

        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
            Mensajes.showError("Error al navegar al detalle", ex.getMessage());
        }
        
        return null;
    }

    private void formateaResultado() throws NoSuchMethodException {

        this.dsResultado.setSelectable(false);
        this.dsResultado.setRowSelectColumnaID("ID_SALIDAXML");
        
        RowCabecera cabecera = this.dsResultado.getCabecera();

        cabecera.getColumnName("ID_SALIDAXML")
                .setTitle("Id Salida")
                .setAlign(ColumnCabecera.ALIGN.RIGHT)
                .setWidth("10em");

        cabecera.getColumnName("ID_SITUACIONXML")
                .setVisible(false);

        cabecera.getColumnName("CO_SITUACIONXML")
                .setVisible(false);

        cabecera.getColumnName("DS_SITUACIONXML")
                .setTitle("Situación")
                .setWidth("100%")
                .setTooltipColumn("CO_SITUACIONXML");

        cabecera.getColumnName("FE_ALTA")
                .setTitle("F. Alta")
                .setAlign(ColumnCabecera.ALIGN.CENTER)
                .setWidth("10em");

        cabecera.getColumnName("FE_DESACTIVO")
                .setTitle("F. Desactivo")
                .setAlign(ColumnCabecera.ALIGN.CENTER)
                .setWidth("10em");

        this.dsResultado.newColumn("btnXML");
        cabecera.getColumnName("btnXML")
                .setTitle("Ver XML")
                .setAlign(ColumnCabecera.ALIGN.CENTER)
                .setWidth("10em")
                .setTipo(ColumnBase.Tipo.DESCARGA)
                .setClase(this)
                .setMethod(this.getClass().getMethod("verXML"))
                .setUpdate("formulario:panelResultado,formulario:mensaje");
    }
}
