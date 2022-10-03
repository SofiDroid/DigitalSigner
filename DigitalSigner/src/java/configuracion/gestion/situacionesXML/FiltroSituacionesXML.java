package configuracion.gestion.situacionesXML;

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
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.Visibility;
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
public class FiltroSituacionesXML implements Serializable {
    private final Logger LOG = Logger.getLogger(FiltroSituacionesXML.class);
    
    private CampoWebCodigo cCoSituacionXML = null;
    private CampoWebDescripcion cDsSituacionXML = null;
    private CampoWebFechaRango cFeAlta = null;
    private CampoWebFechaRango cFeDesactivo = null;
    private boolean filtroVisible = true;
    private DataSet dsResultado = null;
    
    private EdicionSituacionesXML edicionSituacionesXML = null; 
    
    @PostConstruct
    public void init() {
        Session.limpiarOtrosBeans(this.getClass().getName());

        this.cCoSituacionXML = new CampoWebCodigo();
        this.cCoSituacionXML.setLabel(Msg.getString("lbl_FiltroSituacionesXML_CoSituacionXML"));
        this.cCoSituacionXML.setWidthLabel("70px");
        
        this.cDsSituacionXML = new CampoWebDescripcion();
        this.cDsSituacionXML.setLabel(Msg.getString("lbl_FiltroSituacionesXML_DsSituacionXML"));
        this.cDsSituacionXML.setWidthLabel("70px");
        
        this.cFeAlta = new CampoWebFechaRango();
        this.cFeAlta.setLabel(Msg.getString("lbl_FiltroSituacionesXML_FeAlta"));
        this.cFeAlta.setWidthLabel("100px");

        this.cFeDesactivo = new CampoWebFechaRango();
        this.cFeDesactivo.setLabel(Msg.getString("lbl_FiltroSituacionesXML_FeDesactivo"));
        this.cFeDesactivo.setWidthLabel("100px");
        
        this.dsResultado = new DataSet();
        toggleFiltro(null);
    }
    
    public void toggleFiltro(ToggleEvent event) {
        if (event != null) {
            this.filtroVisible = (event.getVisibility() == Visibility.VISIBLE);
        }
        if (this.filtroVisible) {
            this.dsResultado.setHeightFiltro("21.2rem");
        }
        else {
            this.dsResultado.setHeightFiltro("16.5rem");
        }
    }

    public CampoWebCodigo getcCoSituacionXML() {
        return cCoSituacionXML;
    }

    public void setcCoSituacionXML(CampoWebCodigo cCoSituacionXML) {
        this.cCoSituacionXML = cCoSituacionXML;
    }

    public CampoWebDescripcion getcDsSituacionXML() {
        return cDsSituacionXML;
    }

    public void setcDsSituacionXML(CampoWebDescripcion cDsSituacionXML) {
        this.cDsSituacionXML = cDsSituacionXML;
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

    public EdicionSituacionesXML getEdicionSituacionesXML() {
        return edicionSituacionesXML;
    }

    public void setEdicionSituacionesXML(EdicionSituacionesXML edicionSituacionesXML) {
        this.edicionSituacionesXML = edicionSituacionesXML;
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
            cCoSituacionXML.setValue(null);
            cDsSituacionXML.setValue(null);
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
            String sql = "SELECT ID_SITUACIONXML, CO_SITUACIONXML, DS_SITUACIONXML, FE_ALTA, FE_DESACTIVO FROM BD_T_SITUACIONXML WHERE 1 = 1";
            sql = filtros(sql);

            this.dsResultado = new DataSet(sql, "ID_SITUACIONXML");
            toggleFiltro(null);

            if (this.getDsResultado().getRowsCount() > 0) {
                // Establecer formato de salida
                RowCabecera cabecera = this.dsResultado.getCabecera();

                cabecera.getColumnName("ID_SITUACIONXML")
                        .setVisible(false);

                cabecera.getColumnName("CO_SITUACIONXML")
                        .setTitle("Código")
                        .setWidth("6rem")
                        .setTipo(ColumnBase.Tipo.LINK)
                        .setClase(this)
                        .setMethod(this.getClass().getMethod("verDetalle"))
                        .setUpdate("formulario:panelResultado,formulario:mensaje");

                cabecera.getColumnName("DS_SITUACIONXML")
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
        if (cCoSituacionXML.getValue() != null) {
            sql += " AND UPPER(CO_SITUACIONXML) LIKE '%" + cCoSituacionXML.getValue().toUpperCase() + "%'";
        }
        if (cDsSituacionXML.getValue() != null) {
            sql += " AND UPPER(DS_SITUACIONXML) LIKE '%" + cDsSituacionXML.getValue().toUpperCase() + "%'";
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
            this.edicionSituacionesXML = new EdicionSituacionesXML(this, this.dsResultado.getSelectedRow().getColumnName("ID_SITUACIONXML").getValueInteger());
            this.edicionSituacionesXML.setPaginaRetorno("filtroSituacionesXML");

            return "edicionSituacionesXML";
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
            Mensajes.showError("Error al navegar al detalle", ex.getMessage());
        }
        
        return null;
    }

    public String nuevo() {
        try {
            this.edicionSituacionesXML = new EdicionSituacionesXML(this, null);
            this.edicionSituacionesXML.setPaginaRetorno("filtroSituacionesXML");

            return "edicionSituacionesXML";
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
            Mensajes.showError("Error al navegar al detalle", ex.getMessage());
        }
        
        return null;
    }
}
