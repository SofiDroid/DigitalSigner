package gestionXML.entradasXML;

import basedatos.ColumnBase;
import basedatos.ColumnCabecera;
import basedatos.DataSet;
import basedatos.RowCabecera;
import basedatos.servicios.StDEntradaxml;
import basedatos.tablas.BdDEntradaxml;
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
public class FiltroEntradasXML implements Serializable {
    private final Logger LOG = Logger.getLogger(FiltroEntradasXML.class);
    
    private CampoWebNumero cIdEntradaXML = null;
    private CampoWebFechaRango cFeAlta = null;
    private CampoWebFechaRango cFeDesactivo = null;
    private CampoWebLupa cSituacionxml = null;
    private boolean filtroVisible = true;
    private DataSet dsResultado = null;
    
    @PostConstruct
    public void init() {
        Session.limpiarOtrosBeans(this.getClass().getName());
        
        this.cIdEntradaXML = new CampoWebNumero();
        this.cIdEntradaXML.setLabel(Msg.getString("lbl_FiltroEntradasXML_IdEntradaXML"));
        this.cIdEntradaXML.setWidthLabel("100px");
        
        this.cFeAlta = new CampoWebFechaRango();
        this.cFeAlta.setLabel(Msg.getString("lbl_FiltroEntradasXML_FeAlta"));
        this.cFeAlta.setWidthLabel("100px");

        this.cSituacionxml = new CampoWebLupa();
        this.cSituacionxml.setLabel(Msg.getString("lbl_FiltroEntradasXML_Situacionxml"));
        this.cSituacionxml.setWidthLabel("100px");
        String sql = "SELECT ID_SITUACIONXML, CO_SITUACIONXML + ' - ' + DS_SITUACIONXML as Situación FROM BD_T_SITUACIONXML";
        this.cSituacionxml.setConsulta(sql);
        this.cSituacionxml.setColumnaID("ID_SITUACIONXML");
        this.cSituacionxml.setColumnaLabel("Situación");
        
        this.cFeDesactivo = new CampoWebFechaRango();
        this.cFeDesactivo.setLabel(Msg.getString("lbl_FiltroEntradasXML_FeDesactivo"));
        this.cFeDesactivo.setWidthLabel("100px");

        this.dsResultado = new DataSet();
    }

    public CampoWebNumero getcIdEntradaXML() {
        return cIdEntradaXML;
    }

    public void setcIdEntradaXML(CampoWebNumero cIdEntradaXML) {
        this.cIdEntradaXML = cIdEntradaXML;
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
            cIdEntradaXML.setValue(null);
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
                            exml.ID_ENTRADAXML,
                            exml.ID_SITUACIONXML,
                            sitxml.CO_SITUACIONXML,
                            sitxml.DS_SITUACIONXML,
                            exml.FE_ALTA,
                            exml.FE_DESACTIVO
                        FROM
                            BD_D_ENTRADAXML exml
                        INNER JOIN
                            BD_T_SITUACIONXML sitxml ON (sitxml.ID_SITUACIONXML = exml.ID_SITUACIONXML)
                        WHERE
                            1 = 1
                        """;
            
            sql = filtros(sql);

            this.dsResultado = new DataSet(sql, "ID_ENTRADAXML");

            // Establecer formato de salida
            formatearCabeceras();
        } catch (NoSuchMethodException | SecurityException | SQLException ex) {
            LOG.error(ex.getMessage(), ex);
            Mensajes.showError("Error al buscar", ex.getMessage());
        }
    }
    
    private String filtros(String sql) {
        
        if (cIdEntradaXML.getValueInteger() != null) {
            sql += " AND exml.ID_ENTRADAXML = " + cIdEntradaXML.getValueInteger() + "";
        }
        if (cFeAlta.getValueIni() != null || cFeAlta.getValueFin() != null) {
            sql += " AND (";
            if (cFeAlta.getValueIni() != null) {
                sql += "(exml.FE_ALTA >= " + Formateos.dateToSql((Date)cFeAlta.getValueIni()) + ")";
            }
            if (cFeAlta.getValueFin() != null) {
                if (cFeAlta.getValueIni() != null) {
                    sql += " AND ";
                }
                sql += "(exml.FE_ALTA <= " + Formateos.dateToSql((Date)cFeAlta.getValueFin()) + ")";
            }
            sql += ")";
        }
        if (cFeDesactivo.getValueIni() != null || cFeDesactivo.getValueFin() != null) {
            sql += " AND (";
            if (cFeDesactivo.getValueIni() != null) {
                sql += "(exml.FE_DESACTIVO >= " + Formateos.dateToSql((Date)cFeDesactivo.getValueIni()) + ")";
            }
            if (cFeDesactivo.getValueFin() != null) {
                if (cFeDesactivo.getValueIni() != null) {
                    sql += " AND ";
                }
                sql += "(exml.FE_DESACTIVO <= " + Formateos.dateToSql((Date)cFeDesactivo.getValueFin()) + ")";
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
            Integer idEntradaXML = this.dsResultado.getSelectedRow().getColumnaID().getValueInteger();
            BdDEntradaxml bdDEntradaxml = new StDEntradaxml().item(idEntradaXML, null);
            byte[] binDocumento = bdDEntradaxml.getBlEntradaxml(null);
            Session.grabarAtributo("filename", "ENTRADA_" + idEntradaXML + ".xml");
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

    private void formatearCabeceras() throws SecurityException, NoSuchMethodException {

        this.dsResultado.setSelectable(false);
        this.dsResultado.setRowSelectColumnaID("ID_ENTRADAXML");
        
        RowCabecera cabecera = this.dsResultado.getCabecera();

        cabecera.getColumnName("ID_ENTRADAXML")
                .setTitle("Id Entrada")
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
