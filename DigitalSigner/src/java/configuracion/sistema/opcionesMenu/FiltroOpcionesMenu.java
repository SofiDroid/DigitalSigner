package configuracion.sistema.opcionesMenu;

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
public class FiltroOpcionesMenu implements Serializable {
    private final Logger LOG = Logger.getLogger(FiltroOpcionesMenu.class);
    
    private CampoWebCodigo cCoOpcionMenu = null;
    private CampoWebDescripcion cDsOpcionMenu = null;
    private CampoWebFechaRango cFeAlta = null;
    private CampoWebFechaRango cFeDesactivo = null;
    private CampoWebDescripcion cDsTitulo = null;
    private CampoWebDescripcion cDsRuta = null;
    private CampoWebLupa cOpcionMenuPadre = null;
    private boolean filtroVisible = true;
    private DataSet dsResultado = null;
    
    private EdicionOpcionesMenu edicionOpcionesMenu = null; 
    
    @PostConstruct
    public void init() {
        Session.limpiarOtrosBeans(this.getClass().getName());

        this.cCoOpcionMenu = new CampoWebCodigo();
        this.cCoOpcionMenu.setLabel(Msg.getString("lbl_FiltroOpcionesMenu_CoOpcionMenu"));
        this.cCoOpcionMenu.setWidthLabel("100px");
        
        this.cDsOpcionMenu = new CampoWebDescripcion();
        this.cDsOpcionMenu.setLabel(Msg.getString("lbl_FiltroOpcionesMenu_DsOpcionMenu"));
        this.cDsOpcionMenu.setWidthLabel("100px");
        
        this.cFeAlta = new CampoWebFechaRango();
        this.cFeAlta.setLabel(Msg.getString("lbl_FiltroOpcionesMenu_FeAlta"));
        this.cFeAlta.setWidthLabel("100px");

        this.cFeDesactivo = new CampoWebFechaRango();
        this.cFeDesactivo.setLabel(Msg.getString("lbl_FiltroOpcionesMenu_FeDesactivo"));
        this.cFeDesactivo.setWidthLabel("100px");
        
        this.cDsTitulo = new CampoWebDescripcion();
        this.cDsTitulo.setLabel(Msg.getString("lbl_FiltroOpcionesMenu_DsTitulo"));
        this.cDsTitulo.setWidthLabel("100px");
        
        this.cDsRuta = new CampoWebDescripcion();
        this.cDsRuta.setLabel(Msg.getString("lbl_FiltroOpcionesMenu_DsRuta"));
        this.cDsRuta.setWidthLabel("100px");
        
        this.cOpcionMenuPadre = new CampoWebLupa();
        this.cOpcionMenuPadre.setLabel(Msg.getString("lbl_FiltroOpcionesMenu_OpcionMenuPadre"));
        this.cOpcionMenuPadre.setWidthLabel("100px");
        String sql = "SELECT ID_OPCIONMENU, CO_OPCIONMENU + ' - ' + DS_OPCIONMENU as 'Menú' FROM BD_T_OPCIONMENU";
        this.cOpcionMenuPadre.setConsulta(sql);
        this.cOpcionMenuPadre.setColumnaID("ID_OPCIONMENU");
        this.cOpcionMenuPadre.setColumnaLabel("Menú");
        
        this.dsResultado = new DataSet();
    }
    
    public CampoWebCodigo getcCoOpcionMenu() {
        return cCoOpcionMenu;
    }

    public void setcCoOpcionMenu(CampoWebCodigo cCoOpcionMenu) {
        this.cCoOpcionMenu = cCoOpcionMenu;
    }

    public CampoWebDescripcion getcDsOpcionMenu() {
        return cDsOpcionMenu;
    }

    public void setcDsOpcionMenu(CampoWebDescripcion cDsOpcionMenu) {
        this.cDsOpcionMenu = cDsOpcionMenu;
    }

    public CampoWebDescripcion getcDsTitulo() {
        return cDsTitulo;
    }

    public void setcDsTitulo(CampoWebDescripcion cDsTitulo) {
        this.cDsTitulo = cDsTitulo;
    }

    public CampoWebDescripcion getcDsRuta() {
        return cDsRuta;
    }

    public void setcDsRuta(CampoWebDescripcion cDsRuta) {
        this.cDsRuta = cDsRuta;
    }

    public CampoWebLupa getcOpcionMenuPadre() {
        return cOpcionMenuPadre;
    }

    public void setcOpcionMenuPadre(CampoWebLupa cOpcionMenuPadre) {
        this.cOpcionMenuPadre = cOpcionMenuPadre;
    }

    public EdicionOpcionesMenu getEdicionOpcionesMenu() {
        return edicionOpcionesMenu;
    }

    public void setEdicionOpcionesMenu(EdicionOpcionesMenu edicionOpcionesMenu) {
        this.edicionOpcionesMenu = edicionOpcionesMenu;
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
    
    public void limpiar() {
        try {
            cCoOpcionMenu.setValue(null);
            cDsOpcionMenu.setValue(null);
            cFeAlta.setValueIni(null);
            cFeAlta.setValueFin(null);
            cFeDesactivo.setValueIni(null);
            cFeDesactivo.setValueFin(null);
            cDsTitulo.setValue(null);
            cDsRuta.setValue(null);
            cOpcionMenuPadre.setValue(null);
            
            this.dsResultado.clear();
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
            Mensajes.showError("Error al buscar", ex.getMessage());
        }
    }
    
    public void buscar() {
        try {
            String sql = "SELECT ID_OPCIONMENU, CO_OPCIONMENU, DS_OPCIONMENU, FE_ALTA, FE_DESACTIVO FROM BD_T_OPCIONMENU WHERE 1 = 1";
            sql = filtros(sql);

            this.dsResultado = new DataSet(sql, "ID_OPCIONMENU");

            if (this.getDsResultado().getRowsCount() > 0) {
                // Establecer formato de salida
                RowCabecera cabecera = this.dsResultado.getCabecera();

                cabecera.getColumnName("ID_OPCIONMENU")
                        .setVisible(false);

                cabecera.getColumnName("CO_OPCIONMENU")
                        .setTitle("Código")
                        .setWidth("6rem")
                        .setTipo(ColumnBase.Tipo.LINK)
                        .setClase(this)
                        .setMethod(this.getClass().getMethod("verDetalle"))
                        .setUpdate("formulario:panelResultado,formulario:mensaje");

                cabecera.getColumnName("DS_OPCIONMENU")
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
        if (cCoOpcionMenu.getValue() != null) {
            sql += " AND UPPER(CO_OPCIONMENU) LIKE '%" + cCoOpcionMenu.getValue().toUpperCase() + "%'";
        }
        if (cDsOpcionMenu.getValue() != null) {
            sql += " AND UPPER(DS_OPCIONMENU) LIKE '%" + cDsOpcionMenu.getValue().toUpperCase() + "%'";
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
        if (cOpcionMenuPadre.getId() != null) {
            sql += " AND ID_OPCIONMENUPADRE = " + cOpcionMenuPadre.getId();
        }
        
        return sql;
    }
    
    public String verDetalle() {
        try {
            this.edicionOpcionesMenu = new EdicionOpcionesMenu(this, this.dsResultado.getSelectedRow().getColumnName("ID_OPCIONMENU").getValueInteger());
            this.edicionOpcionesMenu.setPaginaRetorno("filtroOpcionesMenu");
    
            return "edicionOpcionesMenu";
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
            Mensajes.showError("Error al navegar al detalle", ex.getMessage());
        }
        
        return null;
    }

    public String nuevo() {
        try {
            this.edicionOpcionesMenu = new EdicionOpcionesMenu(this, null);
            this.edicionOpcionesMenu.setPaginaRetorno("filtroOpcionesMenu");
            
            return "edicionOpcionesMenu";
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
            Mensajes.showError("Error al navegar al detalle", ex.getMessage());
        }
        
        return null;
    }
}
