package configuracion.seguridad.perfiles;

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
public class FiltroPerfiles implements Serializable {
    private final Logger LOG = Logger.getLogger(FiltroPerfiles.class);
    
    private CampoWebCodigo cCoTipousuario = null;
    private CampoWebDescripcion cDsTipousuario = null;
    private CampoWebFechaRango cFeAlta = null;
    private CampoWebFechaRango cFeDesactivo = null;
    private boolean filtroVisible = true;
    private DataSet dsResultado = null;
    
    private EdicionPerfiles edicionPerfiles = null; 
    
    @PostConstruct
    public void init() {
        Session.limpiarOtrosBeans(this.getClass().getName());

        this.cCoTipousuario = new CampoWebCodigo();
        this.cCoTipousuario.setLabel(Msg.getString("lbl_FiltroPerfiles_CoTipousuario"));
        this.cCoTipousuario.setWidthLabel("70px");
        
        this.cDsTipousuario = new CampoWebDescripcion();
        this.cDsTipousuario.setLabel(Msg.getString("lbl_FiltroPerfiles_DsTipousuario"));
        this.cDsTipousuario.setWidthLabel("70px");
        
        this.cFeAlta = new CampoWebFechaRango();
        this.cFeAlta.setLabel(Msg.getString("lbl_FiltroPerfiles_FeAlta"));
        this.cFeAlta.setWidthLabel("100px");

        this.cFeDesactivo = new CampoWebFechaRango();
        this.cFeDesactivo.setLabel(Msg.getString("lbl_FiltroPerfiles_FeDesactivo"));
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
    

    public CampoWebCodigo getcCoTipousuario() {
        return cCoTipousuario;
    }

    public void setcCoTipousuario(CampoWebCodigo cCoTipousuario) {
        this.cCoTipousuario = cCoTipousuario;
    }

    public CampoWebDescripcion getcDsTipousuario() {
        return cDsTipousuario;
    }

    public void setcDsTipousuario(CampoWebDescripcion cDsTipousuario) {
        this.cDsTipousuario = cDsTipousuario;
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

    public EdicionPerfiles getEdicionPerfiles() {
        return edicionPerfiles;
    }

    public void setEdicionPerfiles(EdicionPerfiles edicionPerfiles) {
        this.edicionPerfiles = edicionPerfiles;
    }
    
    public void limpiar() {
        try {
            cCoTipousuario.setValue(null);
            cDsTipousuario.setValue(null);
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
            String sql = "SELECT ID_TIPOUSUARIO, CO_TIPOUSUARIO, DS_TIPOUSUARIO, FE_ALTA, FE_DESACTIVO FROM BD_T_TIPOUSUARIO WHERE 1 = 1";
            sql = filtros(sql);

            this.dsResultado = new DataSet(sql, "ID_TIPOUSUARIO");
            toggleFiltro(null);

            if (this.getDsResultado().getRowsCount() > 0) {
                // Establecer formato de salida
                RowCabecera cabecera = this.dsResultado.getCabecera();

                cabecera.getColumnName("ID_TIPOUSUARIO")
                        .setVisible(false);

                cabecera.getColumnName("CO_TIPOUSUARIO")
                        .setTitle("Código")
                        .setWidth("6rem")
                        .setTipo(ColumnBase.Tipo.LINK)
                        .setClase(this)
                        .setMethod(this.getClass().getMethod("verDetalle"))
                        .setUpdate("formulario:panelResultado,formulario:mensaje");

                cabecera.getColumnName("DS_TIPOUSUARIO")
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
        if (cCoTipousuario.getValue() != null) {
            sql += " AND UPPER(CO_TIPOUSUARIO) LIKE '%" + cCoTipousuario.getValue().toUpperCase() + "%'";
        }
        if (cDsTipousuario.getValue() != null) {
            sql += " AND UPPER(DS_TIPOUSUARIO) LIKE '%" + cDsTipousuario.getValue().toUpperCase() + "%'";
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
            this.edicionPerfiles = new EdicionPerfiles(this, this.dsResultado.getSelectedRow().getColumnName("ID_TIPOUSUARIO").getValueInteger());
            this.edicionPerfiles.setPaginaRetorno("filtroPerfiles");

            return "edicionPerfiles";
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
            Mensajes.showError("Error al navegar al detalle", ex.getMessage());
        }
        
        return null;
    }

    public String nuevo() {
        try {
            this.edicionPerfiles = new EdicionPerfiles(this, null);
            this.edicionPerfiles.setPaginaRetorno("filtroPerfiles");

            return "edicionPerfiles";
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
            Mensajes.showError("Error al navegar al detalle", ex.getMessage());
        }
        
        return null;
    }
}
