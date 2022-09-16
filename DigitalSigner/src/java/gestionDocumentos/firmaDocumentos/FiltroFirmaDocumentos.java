package gestionDocumentos.firmaDocumentos;

import basedatos.ColumnBase;
import basedatos.DataSet;
import basedatos.RowCabecera;
import basedatos.servicios.StDDocumento;
import basedatos.tablas.BdDDocumento;
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

/**
 *
 * @author ihuegal
 */
@Named
@SessionScoped
public class FiltroFirmaDocumentos implements Serializable {
    private final Logger LOG = Logger.getLogger(FiltroFirmaDocumentos.class);
    
    private CampoWebCodigo cCoDocumento = null;
    private CampoWebDescripcion cDsDocumento = null;
    private CampoWebFechaRango cFeAlta = null;
    private CampoWebFechaRango cFeDesactivo = null;
    private CampoWebDescripcion cAutoridad = null;
    private CampoWebDescripcion cTipodocumento = null;
    private CampoWebDescripcion cSituaciondoc = null;
    private boolean filtroVisible = true;
    private DataSet dsResultado = null;
    
    //private EdicionDocumento edicionDocumento = null; 
    
    @PostConstruct
    public void init() {
        this.cCoDocumento = new CampoWebCodigo();
        this.cCoDocumento.setLabel(Msg.getString("lbl_FiltroFirmaDocumentos_CoDocumento"));
        this.cCoDocumento.setWidthLabel("70px");
        
        this.cDsDocumento = new CampoWebDescripcion();
        this.cDsDocumento.setLabel(Msg.getString("lbl_FiltroFirmaDocumentos_DsDocumento"));
        this.cDsDocumento.setWidthLabel("70px");
        
        this.cFeAlta = new CampoWebFechaRango();
        this.cFeAlta.setLabel(Msg.getString("lbl_FiltroFirmaDocumentos_FeAlta"));
        this.cFeAlta.setWidthLabel("100px");

        this.cFeDesactivo = new CampoWebFechaRango();
        this.cFeDesactivo.setLabel(Msg.getString("lbl_FiltroFirmaDocumentos_FeDesactivo"));
        this.cFeDesactivo.setWidthLabel("100px");

        this.cAutoridad = new CampoWebDescripcion();
        this.cAutoridad.setLabel(Msg.getString("lbl_FiltroFirmaDocumentos_Autoridad"));
        this.cAutoridad.setWidthLabel("70px");
        
        this.cTipodocumento = new CampoWebDescripcion();
        this.cTipodocumento.setLabel(Msg.getString("lbl_FiltroFirmaDocumentos_TipoDocumento"));
        this.cTipodocumento.setWidthLabel("70px");
        
        this.cSituaciondoc = new CampoWebDescripcion();
        this.cSituaciondoc.setLabel(Msg.getString("lbl_FiltroFirmaDocumentos_SituacionDoc"));
        this.cSituaciondoc.setWidthLabel("100px");
        
        this.dsResultado = new DataSet();
        toggleFiltro(null);
    }

    public void toggleFiltro(ToggleEvent event) {
        if (event != null) {
            this.filtroVisible = (event.getVisibility() == Visibility.VISIBLE);
        }
        if (this.filtroVisible) {
            this.dsResultado.setHeightFiltro("26rem");
        }
        else {
            this.dsResultado.setHeightFiltro("16.5rem");
        }
    }
    
    public CampoWebCodigo getcCoDocumento() {
        return cCoDocumento;
    }

    public void setcCoDocumento(CampoWebCodigo cCoDocumento) {
        this.cCoDocumento = cCoDocumento;
    }

    public CampoWebDescripcion getcDsDocumento() {
        return cDsDocumento;
    }

    public void setcDsDocumento(CampoWebDescripcion cDsDocumento) {
        this.cDsDocumento = cDsDocumento;
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

    public CampoWebDescripcion getcAutoridad() {
        return cAutoridad;
    }

    public void setcAutoridad(CampoWebDescripcion cAutoridad) {
        this.cAutoridad = cAutoridad;
    }

    public CampoWebDescripcion getcTipodocumento() {
        return cTipodocumento;
    }

    public void setcTipodocumento(CampoWebDescripcion cTipodocumento) {
        this.cTipodocumento = cTipodocumento;
    }

    public CampoWebDescripcion getcSituaciondoc() {
        return cSituaciondoc;
    }

    public void setcSituaciondoc(CampoWebDescripcion cSituaciondoc) {
        this.cSituaciondoc = cSituaciondoc;
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
/*
    public EdicionUnidades getEdicionUnidades() {
        return edicionUnidades;
    }

    public void setEdicionUnidades(EdicionUnidades edicionUnidades) {
        this.edicionUnidades = edicionUnidades;
    }
*/    
    public void limpiar() {
        try {
            cCoDocumento.setValue(null);
            cDsDocumento.setValue(null);
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
            String sql = "SELECT ID_DOCUMENTO, CO_DOCUMENTO, DS_DOCUMENTO, FE_ALTA, FE_DESACTIVO FROM BD_D_DOCUMENTO WHERE 1 = 1";
            sql = filtros(sql);

            this.dsResultado = new DataSet(sql, "ID_DOCUMENTO");
            toggleFiltro(null);

            if (this.getDsResultado().getRowsCount() > 0) {
                // Establecer formato de salida
                RowCabecera cabecera = this.dsResultado.getCabecera();

                cabecera.getColumnName("ID_DOCUMENTO")
                        .setVisible(false);

                cabecera.getColumnName("CO_DOCUMENTO")
                        .setTitle("Código")
                        .setWidth("6rem")
                        .setTipo(ColumnBase.Tipo.LINK)
                        .setClase(this)
                        .setMethod(this.getClass().getMethod("verDetalle"))
                        .setUpdate("formulario:panelResultado,formulario:mensaje");

                cabecera.getColumnName("DS_DOCUMENTO")
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
        if (cCoDocumento.getValue() != null) {
            sql += " AND UPPER(CO_DOCUMENTO) LIKE '%" + cCoDocumento.getValue().toUpperCase() + "%'";
        }
        if (cDsDocumento.getValue() != null) {
            sql += " AND UPPER(DS_DOCUMENTO) LIKE '%" + cDsDocumento.getValue().toUpperCase() + "%'";
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
            //this.edicionUnidades = new EdicionUnidades(this, this.dsResultado.getSelectedRow().getColumnName("ID_UNIDAD").getValueInteger());
            //this.edicionUnidades.setPaginaRetorno("filtroUnidades");
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
            Mensajes.showError("Error al navegar al detalle", ex.getMessage());
        }
        
        return null; //"edicionUnidades";
    }

    public String nuevo() {
        try {
            //this.edicionUnidades = new EdicionUnidades(this, null);
            //this.edicionUnidades.setPaginaRetorno("filtroUnidades");
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
            Mensajes.showError("Error al navegar al detalle", ex.getMessage());
        }
        
        return null; //"edicionUnidades";
    }

    /**
     * Retorna los certificados admitidos para firmar.
     * 
     * Ejemplo:
     * 
     * filters.1=issuer.rfc2254:(|(CN=*MINISDEF*)(CN=*DEFENSA*));keyusage.nonrepudiation:true;nonexpired:\n
     * filters.2=issuer.rfc2254:(O=*POLICIA*);keyusage.nonrepudiation:true;nonexpired:\n
     * filters.3=issuer.rfc2254:(O=*FNMT*);keyusage.nonrepudiation:true;nonexpired:\n
     * 
     * @return String
     * 
     */
    public String getCertificadosAdmitidos() {
        return "filters=keyusage.nonrepudiation:true;\\n";
    }
    
    public String getHostServlets() {
        return "http://localhost:92/firmaServlets/";
    }
    
    public BdDDocumento getDocumentoByIndex(Integer idxFichero) throws Exception {
        Integer idDocumento = this.dsResultado.getRows().get(idxFichero).getColumnName("ID_DOCUMENTO").getValueInteger();
        return new StDDocumento().item(idDocumento, null);
    }
    
    public String getListaDocumentosFirma() {
        return "0";
    }
    
    public String getTipoDocumentosFirma() {
        return "0";
    }
}
