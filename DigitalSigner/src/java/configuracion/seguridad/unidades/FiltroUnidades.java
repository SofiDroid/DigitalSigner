package configuracion.seguridad.unidades;

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
public class FiltroUnidades implements Serializable {
    private final Logger LOG = Logger.getLogger(FiltroUnidades.class);
    
    private CampoWebCodigo cCoUnidad = null;
    private CampoWebDescripcion cDsUnidad= null;
    private CampoWebFechaRango cFeAlta = null;
    private CampoWebFechaRango cFeDesactivo = null;
    private CampoWebLupa cUnidadPadre = null;
    private boolean filtroVisible = true;
    private DataSet dsResultado = null;
    
    private EdicionUnidades edicionUnidades = null; 
    
    @PostConstruct
    public void init() {
        Session.limpiarOtrosBeans(this.getClass().getName());

        this.cCoUnidad = new CampoWebCodigo();
        this.cCoUnidad.setLabel(Msg.getString("lbl_FiltroUnidades_CoUnidad"));
        this.cCoUnidad.setWidthLabel("100px");
        
        this.cDsUnidad = new CampoWebDescripcion();
        this.cDsUnidad.setLabel(Msg.getString("lbl_FiltroUnidades_DsUnidad"));
        this.cDsUnidad.setWidthLabel("100px");
        
        this.cFeAlta = new CampoWebFechaRango();
        this.cFeAlta.setLabel(Msg.getString("lbl_FiltroUnidades_FeAlta"));
        this.cFeAlta.setWidthLabel("100px");

        this.cFeDesactivo = new CampoWebFechaRango();
        this.cFeDesactivo.setLabel(Msg.getString("lbl_FiltroUnidades_FeDesactivo"));
        this.cFeDesactivo.setWidthLabel("100px");
        
        this.cUnidadPadre = new CampoWebLupa();
        this.cUnidadPadre.setLabel(Msg.getString("lbl_FiltroUnidades_UnidadPadre"));
        this.cUnidadPadre.setWidthLabel("100px");
        String sql = "SELECT ID_UNIDAD, CO_UNIDAD + ' - ' + DS_UNIDAD as Unidad FROM BD_T_UNIDAD";
        this.cUnidadPadre.setConsulta(sql);
        this.cUnidadPadre.setColumnaID("ID_UNIDAD");
        this.cUnidadPadre.setColumnaLabel("Unidad");
        
        this.dsResultado = new DataSet();
    }
    
    public CampoWebCodigo getcCoUnidad() {
        return cCoUnidad;
    }

    public void setcCoUnidad(CampoWebCodigo cCoUnidad) {
        this.cCoUnidad = cCoUnidad;
    }

    public CampoWebDescripcion getcDsUnidad() {
        return cDsUnidad;
    }

    public void setcDsUnidad(CampoWebDescripcion cDsUnidad) {
        this.cDsUnidad = cDsUnidad;
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

    public CampoWebLupa getcUnidadPadre() {
        return cUnidadPadre;
    }

    public void setcUnidadPadre(CampoWebLupa cUnidadPadre) {
        this.cUnidadPadre = cUnidadPadre;
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

    public EdicionUnidades getEdicionUnidades() {
        return edicionUnidades;
    }

    public void setEdicionUnidades(EdicionUnidades edicionUnidades) {
        this.edicionUnidades = edicionUnidades;
    }
    
    public void limpiar() {
        try {
            cCoUnidad.setValue(null);
            cDsUnidad.setValue(null);
            cFeAlta.setValueIni(null);
            cFeAlta.setValueFin(null);
            cFeDesactivo.setValueIni(null);
            cFeDesactivo.setValueFin(null);
            cUnidadPadre.setValue(null);
            
            this.dsResultado.clear();
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
            Mensajes.showError("Error al buscar", ex.getMessage());
        }
    }
    
    public void buscar() {
        try {
            String sql = "SELECT ID_UNIDAD, CO_UNIDAD, DS_UNIDAD, FE_ALTA, FE_DESACTIVO FROM BD_T_UNIDAD WHERE 1 = 1";
            sql = filtros(sql);

            this.dsResultado = new DataSet(sql, "ID_UNIDAD");
            formatearCabeceras();
        } catch (NoSuchMethodException | SecurityException | SQLException ex) {
            LOG.error(ex.getMessage(), ex);
            Mensajes.showError("Error al buscar", ex.getMessage());
        }
    }
    
    private void formatearCabeceras() throws SecurityException, NoSuchMethodException {
        // Establecer formato de salida
        RowCabecera cabecera = this.dsResultado.getCabecera();

        cabecera.getColumnName("ID_UNIDAD")
                .setVisible(false);

        cabecera.getColumnName("CO_UNIDAD")
                .setTitle("Código")
                .setWidth("6rem")
                .setTipo(ColumnBase.Tipo.LINK)
                .setClase(this)
                .setMethod(this.getClass().getMethod("verDetalle"))
                .setUpdate("formulario:panelResultado,formulario:mensaje");

        cabecera.getColumnName("DS_UNIDAD")
                .setTitle("Descripción")
                .setWidth("100%");

        cabecera.getColumnName("FE_ALTA")
                .setTitle("F. Alta")
                .setWidth("6rem");

        cabecera.getColumnName("FE_DESACTIVO")
                .setTitle("F. Desactivo")
                .setWidth("6rem");
    }
    
    private String filtros(String sql) {
        if (cCoUnidad.getValue() != null) {
            sql += " AND UPPER(CO_UNIDAD) LIKE '%" + cCoUnidad.getValue().toUpperCase() + "%'";
        }
        if (cDsUnidad.getValue() != null) {
            sql += " AND UPPER(DS_UNIDAD) LIKE '%" + cDsUnidad.getValue().toUpperCase() + "%'";
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
        if (cUnidadPadre.getId() != null) {
            sql += " AND ID_UNIDADPADRE = " + cUnidadPadre.getId();
        }
        
        return sql;
    }
    
    public String verDetalle() {
        try {
            this.edicionUnidades = new EdicionUnidades(this, this.dsResultado.getSelectedRow().getColumnName("ID_UNIDAD").getValueInteger());
            this.edicionUnidades.setPaginaRetorno("filtroUnidades");
    
            return "edicionUnidades";
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
            Mensajes.showError("Error al navegar al detalle", ex.getMessage());
        }
        
        return null;
    }

    public String nuevo() {
        try {
            this.edicionUnidades = new EdicionUnidades(this, null);
            this.edicionUnidades.setPaginaRetorno("filtroUnidades");
            
            return "edicionUnidades";
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
            Mensajes.showError("Error al navegar al detalle", ex.getMessage());
        }
        
        return null;
    }
}
