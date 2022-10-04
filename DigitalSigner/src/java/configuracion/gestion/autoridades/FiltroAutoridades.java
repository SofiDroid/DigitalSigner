package configuracion.gestion.autoridades;

import configuracion.seguridad.unidades.*;
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
public class FiltroAutoridades implements Serializable {
    private final Logger LOG = Logger.getLogger(FiltroAutoridades.class);
    
    private CampoWebCodigo cCoAutoridad = null;
    private CampoWebDescripcion cDsAutoridad= null;
    private CampoWebFechaRango cFeAlta = null;
    private CampoWebFechaRango cFeDesactivo = null;
    private CampoWebLupa cUnidad = null;
    private boolean filtroVisible = true;
    private DataSet dsResultado = null;
    
    private EdicionAutoridades edicionAutoridades = null; 
    
    @PostConstruct
    public void init() {
        Session.limpiarOtrosBeans(this.getClass().getName());

        this.cCoAutoridad = new CampoWebCodigo();
        this.cCoAutoridad.setLabel(Msg.getString("lbl_FiltroAutoridades_CoAutoridad"));
        this.cCoAutoridad.setWidthLabel("100px");
        
        this.cDsAutoridad = new CampoWebDescripcion();
        this.cDsAutoridad.setLabel(Msg.getString("lbl_FiltroAutoridades_DsAutoridad"));
        this.cDsAutoridad.setWidthLabel("100px");
        
        this.cFeAlta = new CampoWebFechaRango();
        this.cFeAlta.setLabel(Msg.getString("lbl_FiltroAutoridades_FeAlta"));
        this.cFeAlta.setWidthLabel("100px");

        this.cFeDesactivo = new CampoWebFechaRango();
        this.cFeDesactivo.setLabel(Msg.getString("lbl_FiltroAutoridades_FeDesactivo"));
        this.cFeDesactivo.setWidthLabel("100px");
        
        this.cUnidad = new CampoWebLupa();
        this.cUnidad.setLabel(Msg.getString("lbl_FiltroAutoridades_Unidad"));
        this.cUnidad.setWidthLabel("100px");
        String sql = "SELECT ID_UNIDAD, CO_UNIDAD + ' - ' + DS_UNIDAD as Unidad FROM BD_T_UNIDAD";
        this.cUnidad.setConsulta(sql);
        this.cUnidad.setColumnaID("ID_UNIDAD");
        this.cUnidad.setColumnaLabel("Unidad");
        
        this.dsResultado = new DataSet();
    }
    
    public CampoWebCodigo getcCoAutoridad() {
        return cCoAutoridad;
    }

    public void setcCoAutoridad(CampoWebCodigo cCoAutoridad) {
        this.cCoAutoridad = cCoAutoridad;
    }

    public CampoWebDescripcion getcDsAutoridad() {
        return cDsAutoridad;
    }

    public void setcDsAutoridad(CampoWebDescripcion cDsAutoridad) {
        this.cDsAutoridad = cDsAutoridad;
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

    public CampoWebLupa getcUnidad() {
        return cUnidad;
    }

    public void setcUnidad(CampoWebLupa cUnidad) {
        this.cUnidad = cUnidad;
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

    public EdicionAutoridades getEdicionAutoridades() {
        return edicionAutoridades;
    }

    public void setEdicionAutoridades(EdicionAutoridades edicionAutoridades) {
        this.edicionAutoridades = edicionAutoridades;
    }
    
    public void limpiar() {
        try {
            cCoAutoridad.setValue(null);
            cDsAutoridad.setValue(null);
            cFeAlta.setValueIni(null);
            cFeAlta.setValueFin(null);
            cFeDesactivo.setValueIni(null);
            cFeDesactivo.setValueFin(null);
            cUnidad.setId(null);
            
            this.dsResultado.clear();
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
            Mensajes.showError("Error al buscar", ex.getMessage());
        }
    }
    
    public void buscar() {
        try {
            String sql = "SELECT ID_AUTORIDAD, CO_AUTORIDAD, DS_AUTORIDAD, FE_ALTA, FE_DESACTIVO FROM BD_T_AUTORIDAD WHERE 1 = 1";
            sql = filtros(sql);

            this.dsResultado = new DataSet(sql, "ID_AUTORIDAD");

            if (this.getDsResultado().getRowsCount() > 0) {
                // Establecer formato de salida
                RowCabecera cabecera = this.dsResultado.getCabecera();

                cabecera.getColumnName("ID_AUTORIDAD")
                        .setVisible(false);

                cabecera.getColumnName("CO_AUTORIDAD")
                        .setTitle("Código")
                        .setWidth("6rem")
                        .setTipo(ColumnBase.Tipo.LINK)
                        .setClase(this)
                        .setMethod(this.getClass().getMethod("verDetalle"))
                        .setUpdate("formulario:panelResultado,formulario:mensaje");

                cabecera.getColumnName("DS_AUTORIDAD")
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
        if (cCoAutoridad.getValue() != null) {
            sql += " AND UPPER(CO_AUTORIDAD) LIKE '%" + cCoAutoridad.getValue().toUpperCase() + "%'";
        }
        if (cDsAutoridad.getValue() != null) {
            sql += " AND UPPER(DS_AUTORIDAD) LIKE '%" + cDsAutoridad.getValue().toUpperCase() + "%'";
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
        if (cUnidad.getId() != null) {
            sql += " AND ID_UNIDAD = " + cUnidad.getId();
        }
        
        return sql;
    }
    
    public String verDetalle() {
        try {
            this.edicionAutoridades = new EdicionAutoridades(this, this.dsResultado.getSelectedRow().getColumnName("ID_AUTORIDAD").getValueInteger());
            this.edicionAutoridades.setPaginaRetorno("filtroAutoridades");
    
            return "edicionAutoridades";
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
            Mensajes.showError("Error al navegar al detalle", ex.getMessage());
        }
        
        return null;
    }

    public String nuevo() {
        try {
            this.edicionAutoridades = new EdicionAutoridades(this, null);
            this.edicionAutoridades.setPaginaRetorno("filtroAutoridades");
            
            return "edicionAutoridades";
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
            Mensajes.showError("Error al navegar al detalle", ex.getMessage());
        }
        
        return null;
    }
}
