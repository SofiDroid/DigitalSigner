package basedatos.tablas;

import basedatos.InterfazDAO;
import basedatos.OperacionSQL;
import java.util.Date;
import java.util.HashMap;

/**
 *
 * @author ihuegal
 */
public class BdTPermiso extends OperacionSQL implements InterfazDAO {
    
    protected Integer idPermiso;
    private String coPermiso;
    private String dsPermiso;
    private Date feAlta;
    private Date feDesactivo;
    private String usuariobd;
    private Date tstbd;

    public BdTPermiso() {
        // NADA
    }

    @Override
    public Object getClaseMapeada(HashMap<String,Object> bld) throws Exception {
        recuperaValorCampo(this, "idPermiso", "ID_PERMISO", bld);
        recuperaValorCampo(this, "coPermiso", "CO_PERMISO", bld);
        recuperaValorCampo(this, "dsPermiso", "DS_PERMISO", bld);
        recuperaValorCampo(this, "feAlta", "FE_ALTA", bld);
        recuperaValorCampo(this, "feDesactivo", "FE_DESACTIVO", bld);
        recuperaValorCampo(this, "usuariobd", "USUARIOBD", bld);
        recuperaValorCampo(this, "tstbd", "TSTBD", bld);
        
        return this; 
    }
    
    public String getSelectFiltro() {
        StringBuilder sb_sql = new StringBuilder("SELECT ");
        sb_sql.append("ID_PERMISO");
        sb_sql.append(",CO_PERMISO");
        sb_sql.append(",DS_PERMISO");
        sb_sql.append(",FE_ALTA");
        sb_sql.append(",FE_DESACTIVO");
        sb_sql.append(",USUARIOBD");
        sb_sql.append(",TSTBD");
        
        sb_sql.append(" FROM BD_T_PERMISO WHERE 1=1 ");
        if (idPermiso != null) {
            sb_sql.append(" AND ID_PERMISO = :ID_PERMISO");
        }
        if (coPermiso != null) {
            sb_sql.append(" AND UPPER(CO_PERMISO) = UPPER(:CO_PERMISO)");
        }
        if (dsPermiso != null) {
            sb_sql.append(" AND UPPER(DS_PERMISO) = UPPER(:DS_PERMISO)");
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

    public Integer getIdPermiso() {
        return idPermiso;
    }

    public void setIdPermiso(Integer idPermiso) {
        this.idPermiso = idPermiso;
    }

    public String getCoPermiso() {
        return coPermiso;
    }

    public void setCoPermiso(String coPermiso) {
        this.coPermiso = coPermiso;
    }

    public String getDsPermiso() {
        return dsPermiso;
    }

    public void setDsPermiso(String dsPermiso) {
        this.dsPermiso = dsPermiso;
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
