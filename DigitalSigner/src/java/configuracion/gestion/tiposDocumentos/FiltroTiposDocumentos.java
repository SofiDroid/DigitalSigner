package configuracion.gestion.tiposDocumentos;

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
public class FiltroTiposDocumentos implements Serializable {
    private final Logger LOG = Logger.getLogger(FiltroTiposDocumentos.class);
    
    private CampoWebCodigo cCoTipodocumento = null;
    private CampoWebDescripcion cDsTipodocumento = null;
    private CampoWebFechaRango cFeAlta = null;
    private CampoWebFechaRango cFeDesactivo = null;
    private boolean filtroVisible = true;
    private DataSet dsResultado = null;
    
    private EdicionTiposDocumentos edicionTiposDocumentos = null; 
    
    @PostConstruct
    public void init() {
        Session.limpiarOtrosBeans(this.getClass().getName());

        this.cCoTipodocumento = new CampoWebCodigo();
        this.cCoTipodocumento.setLabel(Msg.getString("lbl_FiltroTiposDocumentos_CoTipodocumento"));
        this.cCoTipodocumento.setWidthLabel("70px");
        
        this.cDsTipodocumento = new CampoWebDescripcion();
        this.cDsTipodocumento.setLabel(Msg.getString("lbl_FiltroTiposDocumentos_DsTipodocumento"));
        this.cDsTipodocumento.setWidthLabel("70px");
        
        this.cFeAlta = new CampoWebFechaRango();
        this.cFeAlta.setLabel(Msg.getString("lbl_FiltroTiposDocumentos_FeAlta"));
        this.cFeAlta.setWidthLabel("100px");

        this.cFeDesactivo = new CampoWebFechaRango();
        this.cFeDesactivo.setLabel(Msg.getString("lbl_FiltroTiposDocumentos_FeDesactivo"));
        this.cFeDesactivo.setWidthLabel("100px");
        
        this.dsResultado = new DataSet();
    }
    
    public CampoWebCodigo getcCoTipodocumento() {
        return cCoTipodocumento;
    }

    public void setcCoTipodocumento(CampoWebCodigo cCoTipodocumento) {
        this.cCoTipodocumento = cCoTipodocumento;
    }

    public CampoWebDescripcion getcDsTipodocumento() {
        return cDsTipodocumento;
    }

    public void setcDsTipodocumento(CampoWebDescripcion cDsTipodocumento) {
        this.cDsTipodocumento = cDsTipodocumento;
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

    public EdicionTiposDocumentos getEdicionTiposDocumentos() {
        return edicionTiposDocumentos;
    }

    public void setEdicionTiposDocumentos(EdicionTiposDocumentos edicionTiposDocumentos) {
        this.edicionTiposDocumentos = edicionTiposDocumentos;
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
            cCoTipodocumento.setValue(null);
            cDsTipodocumento.setValue(null);
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
            String sql = """
                         SELECT 
                            ID_TIPODOCUMENTO,
                            CO_TIPODOCUMENTO, 
                            DS_TIPODOCUMENTO, 
                            DI_FORMATOFIRMA, 
                            FE_ALTA, 
                            FE_DESACTIVO 
                         FROM 
                            BD_T_TIPODOCUMENTO 
                         WHERE 1 = 1
                         """;
            sql = filtros(sql);

            this.dsResultado = new DataSet(sql, "ID_TIPODOCUMENTO");
            formatearCabeceras();
        } catch (NoSuchMethodException | SecurityException | SQLException ex) {
            LOG.error(ex.getMessage(), ex);
            Mensajes.showError("Error al buscar", ex.getMessage());
        }
    }
    
    private void formatearCabeceras() throws SecurityException, NoSuchMethodException {
        // Establecer formato de salida
        RowCabecera cabecera = this.dsResultado.getCabecera();

        cabecera.getColumnName("ID_TIPODOCUMENTO")
                .setVisible(false);

        cabecera.getColumnName("CO_TIPODOCUMENTO")
                .setTitle("Código")
                .setWidth("6rem")
                .setTipo(ColumnBase.Tipo.LINK)
                .setClase(this)
                .setMethod(this.getClass().getMethod("verDetalle"))
                .setUpdate("formulario:panelResultado,formulario:mensaje");

        cabecera.getColumnName("DS_TIPODOCUMENTO")
                .setTitle("Descripción")
                .setWidth("100%");

        cabecera.getColumnName("DI_FORMATOFIRMA")
                .setTitle("Formato Firma")
                .setWidth("8rem");

        cabecera.getColumnName("FE_ALTA")
                .setTitle("F. Alta")
                .setWidth("6rem");

        cabecera.getColumnName("FE_DESACTIVO")
                .setTitle("F. Desactivo")
                .setWidth("6rem");
    }
    
    private String filtros(String sql) {
        if (cCoTipodocumento.getValue() != null) {
            sql += " AND UPPER(CO_TIPODOCUMENTO) LIKE '%" + cCoTipodocumento.getValue().toUpperCase() + "%'";
        }
        if (cDsTipodocumento.getValue() != null) {
            sql += " AND UPPER(DS_TIPODOCUMENTO) LIKE '%" + cDsTipodocumento.getValue().toUpperCase() + "%'";
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
            this.edicionTiposDocumentos = new EdicionTiposDocumentos(this, this.dsResultado.getSelectedRow().getColumnName("ID_TIPODOCUMENTO").getValueInteger());
            this.edicionTiposDocumentos.setPaginaRetorno("filtroTiposDocumentos");

            return "edicionTiposDocumentos";
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
            Mensajes.showError("Error al navegar al detalle", ex.getMessage());
        }
        
        return null;
    }

    public String nuevo() {
        try {
            this.edicionTiposDocumentos = new EdicionTiposDocumentos(this, null);
            this.edicionTiposDocumentos.setPaginaRetorno("filtroTiposDocumentos");

            return "edicionTiposDocumentos";
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
            Mensajes.showError("Error al navegar al detalle", ex.getMessage());
        }
        
        return null;
    }
}
