package basedatos.tablas;

import basedatos.InterfazDAO;
import basedatos.OperacionSQL;
import java.util.Date;
import java.util.HashMap;

/**
 *
 * @author ihuegal
 */
public class BdTOpcionmenu extends OperacionSQL implements InterfazDAO {
    
    protected Integer idOpcionmenu;
    protected String coOpcionmenu;
    protected String dsOpcionmenu;
    protected String dsTitulo;
    protected String dsTooltip;
    protected String dsRuta;
    protected Date feAlta;
    protected Date feDesactivo;
    protected String usuariobd;
    protected Date tstbd;

    public BdTOpcionmenu() {
        // NADA
    }

    @Override
    public Object getClaseMapeada(HashMap<String,Object> bld) throws Exception {
        recuperaValorCampo(this, "idOpcionmenu", "ID_OPCIONMENU", bld);
        recuperaValorCampo(this, "coOpcionmenu", "CO_OPCIONMENU", bld);
        recuperaValorCampo(this, "dsOpcionmenu", "DS_OPCIONMENU", bld);
        recuperaValorCampo(this, "dsTitulo", "DS_TITULO", bld);
        recuperaValorCampo(this, "dsTooltip", "DS_TOOLTIP", bld);
        recuperaValorCampo(this, "dsRuta", "DS_RUTA", bld);
        recuperaValorCampo(this, "feAlta", "FE_ALTA", bld);
        recuperaValorCampo(this, "feDesactivo", "FE_DESACTIVO", bld);
        recuperaValorCampo(this, "usuariobd", "USUARIOBD", bld);
        recuperaValorCampo(this, "tstbd", "TSTBD", bld);
        
        return this; 
    }
    
    public String getSelectFiltro() {
        StringBuilder sb_sql = new StringBuilder("SELECT ");
        sb_sql.append("ID_OPCIONMENU");
        sb_sql.append(",CO_OPCIONMENU");
        sb_sql.append(",DS_OPCIONMENU");
        sb_sql.append(",DS_TITULO");
        sb_sql.append(",DS_TOOLTIP");
        sb_sql.append(",DS_RUTA");
        sb_sql.append(",FE_ALTA");
        sb_sql.append(",FE_DESACTIVO");
        sb_sql.append(",USUARIOBD");
        sb_sql.append(",TSTBD");
        
        sb_sql.append(" FROM BD_T_OPCIONMENU WHERE 1=1 ");
        if (idOpcionmenu != null) {
            sb_sql.append(" AND ID_OPCIONMENU = :ID_OPCIONMENU");
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
}
