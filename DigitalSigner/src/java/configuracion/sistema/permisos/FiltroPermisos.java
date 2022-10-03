package configuracion.sistema.permisos;

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
public class FiltroPermisos implements Serializable {
    private final Logger LOG = Logger.getLogger(FiltroPermisos.class);
    
    private CampoWebCodigo cCoPermiso = null;
    private CampoWebDescripcion cDsPermiso = null;
    private CampoWebFechaRango cFeAlta = null;
    private CampoWebFechaRango cFeDesactivo = null;
    private boolean filtroVisible = true;
    private DataSet dsResultado = null;
    
    private EdicionPermisos edicionPermisos = null; 
    
    @PostConstruct
    public void init() {
        Session.limpiarOtrosBeans(this.getClass().getName());

        this.cCoPermiso = new CampoWebCodigo();
        this.cCoPermiso.setLabel(Msg.getString("lbl_FiltroPermisos_CoPermiso"));
        this.cCoPermiso.setWidthLabel("70px");
        
        this.cDsPermiso = new CampoWebDescripcion();
        this.cDsPermiso.setLabel(Msg.getString("lbl_FiltroPermisos_DsPermiso"));
        this.cDsPermiso.setWidthLabel("70px");
        
        this.cFeAlta = new CampoWebFechaRango();
        this.cFeAlta.setLabel(Msg.getString("lbl_FiltroPermisos_FeAlta"));
        this.cFeAlta.setWidthLabel("100px");

        this.cFeDesactivo = new CampoWebFechaRango();
        this.cFeDesactivo.setLabel(Msg.getString("lbl_FiltroPermisos_FeDesactivo"));
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

    public CampoWebCodigo getcCoPermiso() {
        return cCoPermiso;
    }

    public void setcCoPermiso(CampoWebCodigo cCoPermiso) {
        this.cCoPermiso = cCoPermiso;
    }

    public CampoWebDescripcion getcDsPermiso() {
        return cDsPermiso;
    }

    public void setcDsPermiso(CampoWebDescripcion cDsPermiso) {
        this.cDsPermiso = cDsPermiso;
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

    public EdicionPermisos getEdicionPermisos() {
        return edicionPermisos;
    }

    public void setEdicionPermisos(EdicionPermisos edicionPermisos) {
        this.edicionPermisos = edicionPermisos;
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
            cCoPermiso.setValue(null);
            cDsPermiso.setValue(null);
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
            String sql = "SELECT ID_PERMISO, CO_PERMISO, DS_PERMISO, FE_ALTA, FE_DESACTIVO FROM BD_T_PERMISO WHERE 1 = 1";
            sql = filtros(sql);

            this.dsResultado = new DataSet(sql, "ID_PERMISO");
            toggleFiltro(null);

            if (this.getDsResultado().getRowsCount() > 0) {
                // Establecer formato de salida
                RowCabecera cabecera = this.dsResultado.getCabecera();

                cabecera.getColumnName("ID_PERMISO")
                        .setVisible(false);

                cabecera.getColumnName("CO_PERMISO")
                        .setTitle("Código")
                        .setWidth("6rem")
                        .setTipo(ColumnBase.Tipo.LINK)
                        .setClase(this)
                        .setMethod(this.getClass().getMethod("verDetalle"))
                        .setUpdate("formulario:panelResultado,formulario:mensaje");

                cabecera.getColumnName("DS_PERMISO")
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
        if (cCoPermiso.getValue() != null) {
            sql += " AND UPPER(CO_PERMISO) LIKE '%" + cCoPermiso.getValue().toUpperCase() + "%'";
        }
        if (cDsPermiso.getValue() != null) {
            sql += " AND UPPER(DS_PERMISO) LIKE '%" + cDsPermiso.getValue().toUpperCase() + "%'";
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
            this.edicionPermisos = new EdicionPermisos(this, this.dsResultado.getSelectedRow().getColumnName("ID_PERMISO").getValueInteger());
            this.edicionPermisos.setPaginaRetorno("filtroPermisos");

            return "edicionPermisos";
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
            Mensajes.showError("Error al navegar al detalle", ex.getMessage());
        }
        
        return null;
    }

    public String nuevo() {
        try {
            this.edicionPermisos = new EdicionPermisos(this, null);
            this.edicionPermisos.setPaginaRetorno("filtroPermisos");

            return "edicionPermisos";
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
            Mensajes.showError("Error al navegar al detalle", ex.getMessage());
        }
        
        return null;
    }
}
