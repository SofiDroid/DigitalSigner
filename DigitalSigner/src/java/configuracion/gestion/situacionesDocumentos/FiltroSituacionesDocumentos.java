package configuracion.gestion.situacionesDocumentos;

import basedatos.ColumnBase;
import basedatos.DataSet;
import basedatos.RowCabecera;
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
public class FiltroSituacionesDocumentos implements Serializable {
    private final Logger LOG = Logger.getLogger(FiltroSituacionesDocumentos.class);
    
    private CampoWebCodigo cCoSituaciondoc = null;
    private CampoWebDescripcion cDsSituaciondoc = null;
    private CampoWebFechaRango cFeAlta = null;
    private CampoWebFechaRango cFeDesactivo = null;
    private boolean filtroVisible = true;
    private DataSet dsResultado = null;
    
    private EdicionSituacionesDocumentos edicionSituacionesDocumentos = null; 
    
    @PostConstruct
    public void init() {
        Session.limpiarOtrosBeans(this.getClass().getName());

        this.cCoSituaciondoc = new CampoWebCodigo();
        this.cCoSituaciondoc.setLabel(Msg.getString("lbl_FiltroSituacionesDocumentos_CoSituaciondoc"));
        this.cCoSituaciondoc.setWidthLabel("70px");
        
        this.cDsSituaciondoc = new CampoWebDescripcion();
        this.cDsSituaciondoc.setLabel(Msg.getString("lbl_FiltroSituacionesDocumentos_DsSituaciondoc"));
        this.cDsSituaciondoc.setWidthLabel("70px");
        
        this.cFeAlta = new CampoWebFechaRango();
        this.cFeAlta.setLabel(Msg.getString("lbl_FiltroSituacionesDocumentos_FeAlta"));
        this.cFeAlta.setWidthLabel("100px");

        this.cFeDesactivo = new CampoWebFechaRango();
        this.cFeDesactivo.setLabel(Msg.getString("lbl_FiltroSituacionesDocumentos_FeDesactivo"));
        this.cFeDesactivo.setWidthLabel("100px");
        
        this.dsResultado = new DataSet();
    }
    
    public CampoWebCodigo getcCoSituaciondoc() {
        return cCoSituaciondoc;
    }

    public void setcCoSituaciondoc(CampoWebCodigo cCoSituaciondoc) {
        this.cCoSituaciondoc = cCoSituaciondoc;
    }

    public CampoWebDescripcion getcDsSituaciondoc() {
        return cDsSituaciondoc;
    }

    public void setcDsSituaciondoc(CampoWebDescripcion cDsSituaciondoc) {
        this.cDsSituaciondoc = cDsSituaciondoc;
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

    public EdicionSituacionesDocumentos getEdicionSituacionesDocumentos() {
        return edicionSituacionesDocumentos;
    }

    public void setEdicionSituacionesDocumentos(EdicionSituacionesDocumentos edicionSituacionesDocumentos) {
        this.edicionSituacionesDocumentos = edicionSituacionesDocumentos;
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
            cCoSituaciondoc.setValue(null);
            cDsSituaciondoc.setValue(null);
            cFeAlta.setValueIni(null);
            cFeAlta.setValueFin(null);
            cFeDesactivo.setValueIni(null);
            cFeDesactivo.setValueFin(null);
            
            this.dsResultado.clear();
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
            Mensajes.showError("Error al buscar", ex.getMessage());
        }
    }
    
    public void buscar() {
        try {
            String sql = "SELECT ID_SITUACIONDOC, CO_SITUACIONDOC, DS_SITUACIONDOC, FE_ALTA, FE_DESACTIVO FROM BD_T_SITUACIONDOC WHERE 1 = 1";
            sql = filtros(sql);

            this.dsResultado = new DataSet(sql, "ID_SITUACIONDOC");

            if (this.getDsResultado().getRowsCount() > 0) {
                // Establecer formato de salida
                RowCabecera cabecera = this.dsResultado.getCabecera();

                cabecera.getColumnName("ID_SITUACIONDOC")
                        .setVisible(false);

                cabecera.getColumnName("CO_SITUACIONDOC")
                        .setTitle("Código")
                        .setWidth("6rem")
                        .setTipo(ColumnBase.Tipo.LINK)
                        .setClase(this)
                        .setMethod(this.getClass().getMethod("verDetalle"))
                        .setUpdate("formulario:panelResultado,formulario:mensaje");

                cabecera.getColumnName("DS_SITUACIONDOC")
                        .setTitle("Descripción")
                        .setWidth("100%");

                cabecera.getColumnName("FE_ALTA")
                        .setTitle("F. Alta")
                        .setWidth("6rem");

                cabecera.getColumnName("FE_DESACTIVO")
                        .setTitle("F. Desactivo")
                        .setWidth("6rem");
            }
        } catch (NoSuchMethodException | SecurityException | SQLException ex) {
            LOG.error(ex.getMessage(), ex);
            Mensajes.showError("Error al buscar", ex.getMessage());
        }
    }
    
    private String filtros(String sql) {
        if (cCoSituaciondoc.getValue() != null) {
            sql += " AND UPPER(CO_SITUACIONDOC) LIKE '%" + cCoSituaciondoc.getValue().toUpperCase() + "%'";
        }
        if (cDsSituaciondoc.getValue() != null) {
            sql += " AND UPPER(DS_SITUACIONDOC) LIKE '%" + cDsSituaciondoc.getValue().toUpperCase() + "%'";
        }
        if (cFeAlta.getValueIni() != null || cFeAlta.getValueFin() != null) {
            sql += " AND (";
            if (cFeAlta.getValueIni() != null) {
                sql += "(FE_ALTA >= " + Formateos.dateToSql((Date)cFeAlta.getValueIni()) + ")";
            }
            if (cFeAlta.getValueFin() != null) {
                if (cFeAlta.getValueIni() != null) {
                    sql += " AND ";
                }
                sql += "(FE_ALTA <= " + Formateos.dateToSql((Date)cFeAlta.getValueFin()) + ")";
            }
            sql += ")";
        }
        if (cFeDesactivo.getValueIni() != null || cFeDesactivo.getValueFin() != null) {
            sql += " AND (";
            if (cFeDesactivo.getValueIni() != null) {
                sql += "(FE_DESACTIVO >= " + Formateos.dateToSql((Date)cFeDesactivo.getValueIni()) + ")";
            }
            if (cFeDesactivo.getValueFin() != null) {
                if (cFeDesactivo.getValueIni() != null) {
                    sql += " AND ";
                }
                sql += "(FE_DESACTIVO <= " + Formateos.dateToSql((Date)cFeDesactivo.getValueFin()) + ")";
            }
            sql += ")";
        }
        
        return sql;
    }
    
    public String verDetalle() {
        try {
            this.edicionSituacionesDocumentos = new EdicionSituacionesDocumentos(this, this.dsResultado.getSelectedRow().getColumnName("ID_SITUACIONDOC").getValueInteger());
            this.edicionSituacionesDocumentos.setPaginaRetorno("filtroSituacionesDocumentos");

            return "edicionSituacionesDocumentos";
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
            Mensajes.showError("Error al navegar al detalle", ex.getMessage());
        }
        
        return null;
    }

    public String nuevo() {
        try {
            this.edicionSituacionesDocumentos = new EdicionSituacionesDocumentos(this, null);
            this.edicionSituacionesDocumentos.setPaginaRetorno("filtroSituacionesDocumentos");

            return "edicionSituacionesDocumentos";
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
            Mensajes.showError("Error al navegar al detalle", ex.getMessage());
        }
        
        return null;
    }
}
