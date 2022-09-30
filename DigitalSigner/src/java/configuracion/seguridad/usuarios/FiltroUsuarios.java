package configuracion.seguridad.usuarios;

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
import utilidades.CampoWebCheckTriState;
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
public class FiltroUsuarios implements Serializable {
    private final Logger LOG = Logger.getLogger(FiltroUsuarios.class);
    
    private CampoWebCodigo cCoNIF = null;
    private CampoWebCodigo cCoUsuario = null;
    private CampoWebDescripcion cDsNombre= null;
    private CampoWebDescripcion cDsApellido1= null;
    private CampoWebDescripcion cDsApellido2= null;
    private CampoWebCheckTriState cBoAdmin= null;
    private CampoWebFechaRango cFeAlta = null;
    private CampoWebFechaRango cFeDesactivo = null;
    private boolean filtroVisible = true;
    private DataSet dsResultado = null;
    
    private EdicionUsuarios edicionUsuarios = null; 
    
    @PostConstruct
    public void init() {
        Session.limpiarOtrosBeans(this.getClass().getName());

        this.cCoNIF = new CampoWebCodigo();
        this.cCoNIF.setLabel(Msg.getString("lbl_FiltroUsuarios_CoNIF"));
        this.cCoNIF.setWidthLabel("100px");
        this.cCoNIF.setMaxlength("9");
        this.cCoNIF.setWidth("9em");
        
        this.cCoUsuario = new CampoWebCodigo();
        this.cCoUsuario.setLabel(Msg.getString("lbl_FiltroUsuarios_CoUsuario"));
        this.cCoUsuario.setWidthLabel("100px");
        
        this.cDsNombre = new CampoWebDescripcion();
        this.cDsNombre.setLabel(Msg.getString("lbl_FiltroUsuarios_DsNombre"));
        this.cDsNombre.setWidthLabel("100px");
        
        this.cDsApellido1 = new CampoWebDescripcion();
        this.cDsApellido1.setLabel(Msg.getString("lbl_FiltroUsuarios_DsApellido1"));
        this.cDsApellido1.setWidthLabel("100px");
        
        this.cDsApellido2 = new CampoWebDescripcion();
        this.cDsApellido2.setLabel(Msg.getString("lbl_FiltroUsuarios_DsApellido2"));
        this.cDsApellido2.setWidthLabel("100px");
        
        this.cBoAdmin = new CampoWebCheckTriState();
        this.cBoAdmin.setLabel(Msg.getString("lbl_FiltroUsuarios_BoAdmin"));
        this.cBoAdmin.setWidthLabel("100px");
        
        this.cFeAlta = new CampoWebFechaRango();
        this.cFeAlta.setLabel(Msg.getString("lbl_FiltroUsuarios_FeAlta"));
        this.cFeAlta.setWidthLabel("100px");

        this.cFeDesactivo = new CampoWebFechaRango();
        this.cFeDesactivo.setLabel(Msg.getString("lbl_FiltroUsuarios_FeDesactivo"));
        this.cFeDesactivo.setWidthLabel("100px");
        
        this.dsResultado = new DataSet();
        toggleFiltro(null);
    }
    
    public void toggleFiltro(ToggleEvent event) {
        if (event != null) {
            this.filtroVisible = (event.getVisibility() == Visibility.VISIBLE);
        }
        if (this.filtroVisible) {
            this.dsResultado.setHeightFiltro("25.8rem");
        }
        else {
            this.dsResultado.setHeightFiltro("16.5rem");
        }
    }

    public CampoWebCodigo getcCoNIF() {
        return cCoNIF;
    }

    public void setcCoNIF(CampoWebCodigo cCoNIF) {
        this.cCoNIF = cCoNIF;
    }

    public CampoWebCodigo getcCoUsuario() {
        return cCoUsuario;
    }

    public void setcCoUsuario(CampoWebCodigo cCoUsuario) {
        this.cCoUsuario = cCoUsuario;
    }

    public CampoWebDescripcion getcDsNombre() {
        return cDsNombre;
    }

    public void setcDsNombre(CampoWebDescripcion cDsNombre) {
        this.cDsNombre = cDsNombre;
    }

    public CampoWebDescripcion getcDsApellido1() {
        return cDsApellido1;
    }

    public void setcDsApellido1(CampoWebDescripcion cDsApellido1) {
        this.cDsApellido1 = cDsApellido1;
    }

    public CampoWebDescripcion getcDsApellido2() {
        return cDsApellido2;
    }

    public void setcDsApellido2(CampoWebDescripcion cDsApellido2) {
        this.cDsApellido2 = cDsApellido2;
    }

    public CampoWebCheckTriState getcBoAdmin() {
        return cBoAdmin;
    }

    public void setcBoAdmin(CampoWebCheckTriState cBoAdmin) {
        this.cBoAdmin = cBoAdmin;
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

    public EdicionUsuarios getEdicionUsuarios() {
        return edicionUsuarios;
    }

    public void setEdicionUsuarios(EdicionUsuarios edicionUsuarios) {
        this.edicionUsuarios = edicionUsuarios;
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
            cCoNIF.setValue(null);
            cCoUsuario.setValue(null);
            cDsNombre.setValue(null);
            cDsApellido1.setValue(null);
            cDsApellido2.setValue(null);
            cBoAdmin.setValue(false);
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
            String sql = "SELECT ID_USUARIO, CO_NIF, CO_USUARIO, DS_NOMBRE, DS_APELLIDO1, DS_APELLIDO2, BO_ADMIN, FE_ALTA, FE_DESACTIVO FROM BD_T_USUARIO WHERE 1 = 1";
            sql = filtros(sql);

            this.dsResultado = new DataSet(sql, "ID_USUARIO");
            toggleFiltro(null);

            if (this.getDsResultado().getRowsCount() > 0) {
                // Establecer formato de salida
                RowCabecera cabecera = this.dsResultado.getCabecera();

                cabecera.getColumnName("ID_USUARIO")
                        .setVisible(false);

                cabecera.getColumnName("CO_NIF")
                        .setTitle("NIF")
                        .setWidth("8em");

                cabecera.getColumnName("CO_USUARIO")
                        .setTitle("Código")
                        .setWidth("7em")
                        .setTipo(ColumnBase.Tipo.LINK)
                        .setClase(this)
                        .setMethod(this.getClass().getMethod("verDetalle"))
                        .setUpdate("formulario:panelResultado,formulario:mensaje");

                cabecera.getColumnName("DS_APELLIDO1")
                        .setTitle("1º Apellido")
                        .setWidth("100%");

                cabecera.getColumnName("DS_APELLIDO2")
                        .setTitle("2º Apellido")
                        .setWidth("100%");

                cabecera.getColumnName("DS_NOMBRE")
                        .setTitle("Nombre")
                        .setWidth("10em");

                cabecera.getColumnName("BO_ADMIN")
                        .setVisible(false);

                cabecera.getColumnName("FE_ALTA")
                        .setTitle("F. Alta")
                        .setWidth("8em");

                cabecera.getColumnName("FE_DESACTIVO")
                        .setTitle("F. Desactivo")
                        .setWidth("8em");
            }
        } catch (NoSuchMethodException | SecurityException | SQLException ex) {
            LOG.error(ex.getMessage(), ex);
            Mensajes.showError("Error al buscar", ex.getMessage());
        }
    }
    
    private String filtros(String sql) {
        if (cCoNIF.getValue() != null) {
            sql += " AND UPPER(CO_NIF) LIKE '%" + cCoNIF.getValue().toUpperCase() + "%'";
        }
        if (cCoUsuario.getValue() != null) {
            sql += " AND UPPER(CO_USUARIO) LIKE '%" + cCoUsuario.getValue().toUpperCase() + "%'";
        }
        if (cDsNombre.getValue() != null) {
            sql += " AND UPPER(DS_NOMBRE) LIKE '%" + cDsNombre.getValue().toUpperCase() + "%'";
        }
        if (cDsApellido1.getValue() != null) {
            sql += " AND UPPER(DS_APELLIDO1) LIKE '%" + cDsApellido1.getValue().toUpperCase() + "%'";
        }
        if (cDsApellido2.getValue() != null) {
            sql += " AND UPPER(DS_APELLIDO2) LIKE '%" + cDsApellido2.getValue().toUpperCase() + "%'";
        }
        if (cBoAdmin.getBooleanValue() != null) {
            sql += " AND BO_ADMIN = " + (cBoAdmin.getBooleanValue() ? "1" : "0") + "";
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
            this.edicionUsuarios = new EdicionUsuarios(this, this.dsResultado.getSelectedRow().getColumnName("ID_USUARIO").getValueInteger());
            this.edicionUsuarios.setPaginaRetorno("filtroUsuarios");
    
            return "edicionUsuarios";
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
            Mensajes.showError("Error al navegar al detalle", ex.getMessage());
        }
        
        return null;
    }

    public String nuevo() {
        try {
            this.edicionUsuarios = new EdicionUsuarios(this, null);
            this.edicionUsuarios.setPaginaRetorno("filtroUsuarios");
            
            return "edicionUsuarios";
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
            Mensajes.showError("Error al navegar al detalle", ex.getMessage());
        }
        
        return null;
    }
}
