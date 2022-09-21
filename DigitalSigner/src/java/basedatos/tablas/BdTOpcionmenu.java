package basedatos.tablas;

import basedatos.InterfazDAO;
import basedatos.OperacionSQL;
import init.AppInit;
import java.util.Date;
import java.util.HashMap;
import utilidades.BaseDatos;

/**
 *
 * @author ihuegal
 */
public class BdTOpcionmenu extends OperacionSQL implements InterfazDAO {
    
    protected Integer idOpcionmenu;
    protected Integer enOrden;
    protected String coOpcionmenu;
    protected String dsOpcionmenu;
    protected String dsTitulo;
    protected String dsTooltip;
    protected String dsRuta;
    protected Date feAlta;
    protected Date feDesactivo;
    protected String usuariobd;
    protected Date tstbd;
    protected Integer idOpcionmenupadre;

    public BdTOpcionmenu() {
        // NADA
    }

    @Override
    public Object getClaseMapeada(HashMap<String,Object> bld) throws Exception {
        recuperaValorCampo(this, "idOpcionmenu", "ID_OPCIONMENU", bld);
        recuperaValorCampo(this, "enOrden", "EN_ORDEN", bld);
        recuperaValorCampo(this, "coOpcionmenu", "CO_OPCIONMENU", bld);
        recuperaValorCampo(this, "coOpcionmenu", "CO_OPCIONMENU", bld);
        recuperaValorCampo(this, "dsOpcionmenu", "DS_OPCIONMENU", bld);
        recuperaValorCampo(this, "dsTitulo", "DS_TITULO", bld);
        recuperaValorCampo(this, "dsTooltip", "DS_TOOLTIP", bld);
        recuperaValorCampo(this, "dsRuta", "DS_RUTA", bld);
        recuperaValorCampo(this, "feAlta", "FE_ALTA", bld);
        recuperaValorCampo(this, "feDesactivo", "FE_DESACTIVO", bld);
        recuperaValorCampo(this, "usuariobd", "USUARIOBD", bld);
        recuperaValorCampo(this, "tstbd", "TSTBD", bld);
        recuperaValorCampo(this, "idOpcionmenupadre", "ID_OPCIONMENUPADRE", bld);
        
        return this; 
    }
    
    public String getSelectFiltro() {
        StringBuilder sb_sql = new StringBuilder("SELECT ");
        sb_sql.append("ID_OPCIONMENU");
        sb_sql.append(",EN_ORDEN");
        sb_sql.append(",CO_OPCIONMENU");
        sb_sql.append(",DS_OPCIONMENU");
        sb_sql.append(",DS_TITULO");
        sb_sql.append(",DS_TOOLTIP");
        sb_sql.append(",DS_RUTA");
        sb_sql.append(",FE_ALTA");
        sb_sql.append(",FE_DESACTIVO");
        sb_sql.append(",USUARIOBD");
        sb_sql.append(",TSTBD");
        sb_sql.append(",ID_OPCIONMENUPADRE");
        
        sb_sql.append(" FROM BD_T_OPCIONMENU WHERE 1=1 ");
        if (idOpcionmenu != null) {
            sb_sql.append(" AND ID_OPCIONMENU = :ID_OPCIONMENU");
        }
        if (enOrden != null) {
            sb_sql.append(" AND EN_ORDEN = :EN_ORDEN");
        }
        if (coOpcionmenu != null) {
            sb_sql.append(" AND UPPER(CO_OPCIONMENU) = UPPER(:CO_OPCIONMENU)");
        }
        if (dsOpcionmenu != null) {
            sb_sql.append(" AND UPPER(DS_OPCIONMENU) = UPPER(:DS_OPCIONMENU)");
        }
        if (dsTitulo != null) {
            sb_sql.append(" AND UPPER(DS_TITULO) = UPPER(:DS_TITULO)");
        }
        if (dsTooltip != null) {
            sb_sql.append(" AND UPPER(DS_TOOLTIP) = UPPER(:DS_TOOLTIP)");
        }
        if (dsRuta != null) {
            sb_sql.append(" AND UPPER(DS_RUTA) = UPPER(:DS_RUTA)");
        }
        if (feAlta != null) {
            sb_sql.append(" AND (FE_ALTA <= :FE_ALTA)");
        }
        if (feDesactivo != null) {
            sb_sql.append(" AND (FE_DESACTIVO IS NULL OR FE_DESACTIVO >= :FE_DESACTIVO)");
        }
        if (usuariobd != null) {
            sb_sql.append(" AND USUARIOBD = :USUARIOBD");
        }
        if (tstbd != null) {
            sb_sql.append(" AND TSTBD = :TSTBD");
        }
        if (idOpcionmenupadre != null) {
            sb_sql.append(" AND ID_OPCIONMENUPADRE = :ID_OPCIONMENUPADRE");
        }

        return sb_sql.toString();
    }

    public String getSecuencia()
    {
        return "SELECT SBD_T_OPCIONMENU.NEXTVAL FROM DUAL";
    }
    
    public String getInsert()
    {
        StringBuilder sb_sql = new StringBuilder("INSERT INTO BD_T_OPCIONMENU (");
        if (AppInit.TIPO_BASEDATOS == BaseDatos.ORACLE) {
            sb_sql.append("ID_OPCIONMENU,");
        }
        sb_sql.append("EN_ORDEN");
        sb_sql.append(",CO_OPCIONMENU");
        sb_sql.append(",DS_OPCIONMENU");
        sb_sql.append(",DS_TITULO");
        sb_sql.append(",DS_TOOLTIP");
        sb_sql.append(",DS_RUTA");
        sb_sql.append(",FE_ALTA");
        sb_sql.append(",FE_DESACTIVO");
        sb_sql.append(",USUARIOBD");
        sb_sql.append(",TSTBD");
        sb_sql.append(",ID_OPCIONMENUPADRE");
        
        sb_sql.append(") VALUES (");
        if(AppInit.TIPO_BASEDATOS == BaseDatos.ORACLE) {
            if (this.idOpcionmenu == null) {
                sb_sql.append(" SBD_T_OPCIONMENU.NEXTVAL");
            } else {
                sb_sql.append(":ID_OPCIONMENU");
            }
            sb_sql.append(",").append("DECODE(:EN_ORDEN, null, NULL, :EN_ORDEN)");
            sb_sql.append(",").append("DECODE(:CO_OPCIONMENU, null, NULL, :CO_OPCIONMENU)");
            sb_sql.append(",").append("DECODE(:DS_OPCIONMENU, null, NULL, :DS_OPCIONMENU)");
            sb_sql.append(",").append("DECODE(:DS_TITULO, null, NULL, :DS_TITULO)");
            sb_sql.append(",").append("DECODE(:DS_TOOLTIP, null, NULL, :DS_TOOLTIP)");
            sb_sql.append(",").append("DECODE(:DS_RUTA, null, NULL, :DS_RUTA)");
            sb_sql.append(",").append("DECODE(:FE_ALTA, null, NULL, :FE_ALTA)");
            sb_sql.append(",").append("DECODE(:FE_DESACTIVO, null, NULL, :FE_DESACTIVO)");
            sb_sql.append(",").append("DECODE(:USUARIOBD, null, NULL, :USUARIOBD)");
            sb_sql.append(",").append("DECODE(:TSTBD, null, NULL, :TSTBD)");
            sb_sql.append(",").append("DECODE(:ID_OPCIONMENUPADRE, null, NULL, :ID_OPCIONMENUPADRE)");
        }
        else {
            sb_sql.append(":EN_ORDEN");
            sb_sql.append(",").append(":CO_OPCIONMENU");
            sb_sql.append(",").append(":DS_OPCIONMENU");
            sb_sql.append(",").append(":DS_TITULO");
            sb_sql.append(",").append(":DS_TOOLTIP");
            sb_sql.append(",").append(":DS_RUTA");
            sb_sql.append(",").append(":FE_ALTA");
            sb_sql.append(",").append(":FE_DESACTIVO");
            sb_sql.append(",").append(":USUARIOBD");
            sb_sql.append(",").append(":TSTBD");
            sb_sql.append(",").append(":ID_OPCIONMENUPADRE");
        }
        sb_sql.append(")");
        return sb_sql.toString();
    }

    public String getUpdate()
    {
        StringBuilder sb_sql = new StringBuilder("UPDATE BD_T_OPCIONMENU SET ");
        
        if (AppInit.TIPO_BASEDATOS == BaseDatos.ORACLE) {
            sb_sql.append("EN_ORDEN = ").append("DECODE(:EN_ORDEN, null, NULL, :EN_ORDEN)");
            sb_sql.append(",CO_OPCIONMENU = ").append("DECODE(:CO_OPCIONMENU, null, NULL, :CO_OPCIONMENU)");
            sb_sql.append(",DS_OPCIONMENU = ").append("DECODE(:DS_OPCIONMENU, null, NULL, :DS_OPCIONMENU)");
            sb_sql.append(",DS_TITULO = ").append("DECODE(:DS_TITULO, null, NULL, :DS_TITULO)");
            sb_sql.append(",DS_TOOLTIP = ").append("DECODE(:DS_TOOLTIP, null, NULL, :DS_TOOLTIP)");
            sb_sql.append(",DS_RUTA = ").append("DECODE(:DS_RUTA, null, NULL, :DS_RUTA)");
            sb_sql.append(",FE_ALTA = ").append("DECODE(:FE_ALTA, null, NULL, :FE_ALTA)");
            sb_sql.append(",FE_DESACTIVO = ").append("DECODE(:FE_DESACTIVO, null, NULL, :FE_DESACTIVO)");
            sb_sql.append(",USUARIOBD = ").append("DECODE(:USUARIOBD, null, NULL, :USUARIOBD)");
            sb_sql.append(",TSTBD = ").append("DECODE(:TSTBD, null, NULL, :TSTBD)");
            sb_sql.append(",ID_OPCIONMENUPADRE = ").append("DECODE(:ID_OPCIONMENUPADRE, null, NULL, :ID_OPCIONMENUPADRE)");
        }
        else {
            sb_sql.append("EN_ORDEN = :EN_ORDEN");
            sb_sql.append(",CO_OPCIONMENU = :CO_OPCIONMENU");
            sb_sql.append(",DS_OPCIONMENU = :DS_OPCIONMENU");
            sb_sql.append(",DS_TITULO = :DS_TITULO");
            sb_sql.append(",DS_TOOLTIP = :DS_TOOLTIP");
            sb_sql.append(",DS_RUTA = :DS_RUTA");
            sb_sql.append(",FE_ALTA = :FE_ALTA");
            sb_sql.append(",FE_DESACTIVO = :FE_DESACTIVO");
            sb_sql.append(",USUARIOBD = :USUARIOBD");
            sb_sql.append(",TSTBD = :TSTBD");
            sb_sql.append(",ID_OPCIONMENUPADRE = :ID_OPCIONMENUPADRE");
        }
        sb_sql.append(" WHERE ");
        sb_sql.append("ID_OPCIONMENU = ").append(":ID_OPCIONMENU");
        
        return sb_sql.toString();
    }

    public String getDelete()
    {
        StringBuilder sb_sql = new StringBuilder("DELETE BD_T_OPCIONMENU ");
        sb_sql.append(" WHERE ");
        sb_sql.append("ID_OPCIONMENU = ").append(":ID_OPCIONMENU");
        
        return sb_sql.toString();
    }

    public Integer getIdOpcionmenu() {
        return idOpcionmenu;
    }

    public void setIdOpcionmenu(Integer idOpcionmenu) {
        this.idOpcionmenu = idOpcionmenu;
    }

    public String getCoOpcionmenu() {
        return coOpcionmenu;
    }

    public void setCoOpcionmenu(String coOpcionmenu) {
        this.coOpcionmenu = coOpcionmenu;
    }

    public String getDsOpcionmenu() {
        return dsOpcionmenu;
    }

    public void setDsOpcionmenu(String dsOpcionmenu) {
        this.dsOpcionmenu = dsOpcionmenu;
    }

    public String getDsTitulo() {
        return dsTitulo;
    }

    public void setDsTitulo(String dsTitulo) {
        this.dsTitulo = dsTitulo;
    }

    public String getDsTooltip() {
        return dsTooltip;
    }

    public void setDsTooltip(String dsTooltip) {
        this.dsTooltip = dsTooltip;
    }

    public String getDsRuta() {
        return dsRuta;
    }

    public void setDsRuta(String dsRuta) {
        this.dsRuta = dsRuta;
    }

    public Date getFeAlta() {
        return feAlta;
    }

    public void setFeAlta(Date feAlta) {
        this.feAlta = feAlta;
    }

    public Date getFeDesactivo() {
        return feDesactivo;
    }

    public void setFeDesactivo(Date feDesactivo) {
        this.feDesactivo = feDesactivo;
    }

    public String getUsuariobd() {
        return usuariobd;
    }

    public void setUsuariobd(String usuariobd) {
        this.usuariobd = usuariobd;
    }

    public Date getTstbd() {
        return tstbd;
    }

    public void setTstbd(Date tstbd) {
        this.tstbd = tstbd;
    }

    public Integer getEnOrden() {
        return enOrden;
    }

    public void setEnOrden(Integer enOrden) {
        this.enOrden = enOrden;
    }

    public Integer getIdOpcionmenupadre() {
        return idOpcionmenupadre;
    }

    public void setIdOpcionmenupadre(Integer idOpcionmenupadre) {
        this.idOpcionmenupadre = idOpcionmenupadre;
    }
}
