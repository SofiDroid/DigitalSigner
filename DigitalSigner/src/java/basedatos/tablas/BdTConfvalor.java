package basedatos.tablas;

import basedatos.InterfazDAO;
import basedatos.OperacionSQL;
import java.util.Date;
import java.util.HashMap;

/**
 *
 * @author ihuegal
 */
public class BdTConfvalor extends OperacionSQL implements InterfazDAO {
    
    protected Integer idConfvalor;
    protected Integer idConfiguracion;
    protected String coConfvalor;
    protected String dsConfvalor;
    protected Date feAlta;
    protected Date feDesactivo;
    protected String usuariobd;
    protected Date tstbd;

    public BdTConfvalor() {
        // NADA
    }

    @Override
    public Object getClaseMapeada(HashMap<String,Object> bld) throws Exception {
        recuperaValorCampo(this, "idConfvalor", "ID_CONFVALOR", bld);
        recuperaValorCampo(this, "idConfiguracion", "ID_CONFIGURACION", bld);
        recuperaValorCampo(this, "coConfvalor", "CO_CONFVALOR", bld);
        recuperaValorCampo(this, "dsConfvalor", "DS_CONFVALOR", bld);
        recuperaValorCampo(this, "feAlta", "FE_ALTA", bld);
        recuperaValorCampo(this, "feDesactivo", "FE_DESACTIVO", bld);
        recuperaValorCampo(this, "usuariobd", "USUARIOBD", bld);
        recuperaValorCampo(this, "tstbd", "TSTBD", bld);
        
        return this; 
    }
    
    public String getSelectFiltro() {
        StringBuilder sb_sql = new StringBuilder("SELECT ");
        sb_sql.append("ID_CONFVALOR");
        sb_sql.append(",ID_CONFIGURACION");
        sb_sql.append(",CO_CONFVALOR");
        sb_sql.append(",DS_CONFVALOR");
        sb_sql.append(",FE_ALTA");
        sb_sql.append(",FE_DESACTIVO");
        sb_sql.append(",USUARIOBD");
        sb_sql.append(",TSTBD");
        
        sb_sql.append(" FROM BD_T_CONFVALOR WHERE 1=1 ");
        if (idConfvalor != null) {
            sb_sql.append(" AND ID_CONFVALOR = :ID_CONFVALOR");
        }
        if (idConfiguracion != null) {
            sb_sql.append(" AND ID_CONFIGURACION = :ID_CONFIGURACION");
        }
        if (coConfvalor != null) {
            sb_sql.append(" AND UPPER(CO_CONFVALOR) = UPPER(:CO_CONFVALOR)");
        }
        if (dsConfvalor != null) {
            sb_sql.append(" AND UPPER(DS_CONFVALOR) = UPPER(:DS_CONFVALOR)");
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

    public Integer getIdConfvalor() {
        return idConfvalor;
    }

    public void setIdConfvalor(Integer idConfvalor) {
        this.idConfvalor = idConfvalor;
    }

    public Integer getIdConfiguracion() {
        return idConfiguracion;
    }

    public void setIdConfiguracion(Integer idConfiguracion) {
        this.idConfiguracion = idConfiguracion;
    }

    public String getCoConfvalor() {
        return coConfvalor;
    }

    public void setCoConfvalor(String coConfvalor) {
        this.coConfvalor = coConfvalor;
    }

    public String getDsConfvalor() {
        return dsConfvalor;
    }

    public void setDsConfvalor(String dsConfvalor) {
        this.dsConfvalor = dsConfvalor;
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
