package basedatos.tablas;

import basedatos.InterfazDAO;
import basedatos.OperacionSQL;
import java.util.Date;
import java.util.HashMap;

/**
 *
 * @author ihuegal
 */
public class BdTConfiguracion extends OperacionSQL implements InterfazDAO {
    
    protected Integer idConfiguracion;
    private String coConfiguracion;
    private String dsConfiguracion;
    private Date feAlta;
    private Date feDesactivo;
    private String usuariobd;
    private Date tstbd;

    public BdTConfiguracion() {
        // NADA
    }

    @Override
    public Object getClaseMapeada(HashMap<String,Object> bld) throws Exception {
        recuperaValorCampo(this, "idConfiguracion", "ID_CONFIGURACION", bld);
        recuperaValorCampo(this, "coConfiguracion", "CO_CONFIGURACION", bld);
        recuperaValorCampo(this, "dsConfiguracion", "DS_CONFIGURACION", bld);
        recuperaValorCampo(this, "feAlta", "FE_ALTA", bld);
        recuperaValorCampo(this, "feDesactivo", "FE_DESACTIVO", bld);
        recuperaValorCampo(this, "usuariobd", "USUARIOBD", bld);
        recuperaValorCampo(this, "tstbd", "TSTBD", bld);
        
        return this; 
    }
    
    public String getSelectFiltro() {
        StringBuilder sb_sql = new StringBuilder("SELECT ");
        sb_sql.append("ID_CONFIGURACION");
        sb_sql.append(",CO_CONFIGURACION");
        sb_sql.append(",DS_CONFIGURACION");
        sb_sql.append(",FE_ALTA");
        sb_sql.append(",FE_DESACTIVO");
        sb_sql.append(",USUARIOBD");
        sb_sql.append(",TSTBD");
        
        sb_sql.append(" FROM BD_T_CONFIGURACION WHERE 1=1 ");
        if (idConfiguracion != null) {
            sb_sql.append(" AND ID_CONFIGURACION = :ID_CONFIGURACION");
        }
        if (coConfiguracion != null) {
            sb_sql.append(" AND UPPER(CO_CONFIGURACION) = UPPER(:CO_CONFIGURACION)");
        }
        if (dsConfiguracion != null) {
            sb_sql.append(" AND UPPER(DS_CONFIGURACION) = UPPER(:DS_CONFIGURACION)");
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

    public Integer getIdConfiguracion() {
        return idConfiguracion;
    }

    public void setIdConfiguracion(Integer idConfiguracion) {
        this.idConfiguracion = idConfiguracion;
    }

    public String getCoConfiguracion() {
        return coConfiguracion;
    }

    public void setCoConfiguracion(String coConfiguracion) {
        this.coConfiguracion = coConfiguracion;
    }

    public String getDsConfiguracion() {
        return dsConfiguracion;
    }

    public void setDsConfiguracion(String dsConfiguracion) {
        this.dsConfiguracion = dsConfiguracion;
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
