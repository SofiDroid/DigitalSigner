package basedatos.tablas;

import basedatos.InterfazDAO;
import basedatos.OperacionSQL;
import java.util.Date;
import java.util.HashMap;

/**
 *
 * @author ihuegal
 */
public class BdTUnidad extends OperacionSQL implements InterfazDAO {
    
    protected Integer idUnidad;
    private String coUnidad;
    private String dsUnidad;
    private Date feAlta;
    private Date feDesactivo;
    private String usuariobd;
    private Date tstbd;

    public BdTUnidad() {
        // NADA
    }

    @Override
    public Object getClaseMapeada(HashMap<String,Object> bld) throws Exception {
        recuperaValorCampo(this, "idUnidad", "ID_UNIDAD", bld);
        recuperaValorCampo(this, "coUnidad", "CO_UNIDAD", bld);
        recuperaValorCampo(this, "dsUnidad", "DS_UNIDAD", bld);
        recuperaValorCampo(this, "feAlta", "FE_ALTA", bld);
        recuperaValorCampo(this, "feDesactivo", "FE_DESACTIVO", bld);
        recuperaValorCampo(this, "usuariobd", "USUARIOBD", bld);
        recuperaValorCampo(this, "tstbd", "TSTBD", bld);
        
        return this; 
    }
    
    public String getSelectFiltro() {
        StringBuilder sb_sql = new StringBuilder("SELECT ");
        sb_sql.append("ID_UNIDAD");
        sb_sql.append(",CO_UNIDAD");
        sb_sql.append(",DS_UNIDAD");
        sb_sql.append(",FE_ALTA");
        sb_sql.append(",FE_DESACTIVO");
        sb_sql.append(",USUARIOBD");
        sb_sql.append(",TSTBD");
        
        sb_sql.append(" FROM BD_T_UNIDAD WHERE 1=1 ");
        if (idUnidad != null) {
            sb_sql.append(" AND ID_UNIDAD = :ID_UNIDAD");
        }
        if (coUnidad != null) {
            sb_sql.append(" AND UPPER(CO_UNIDAD) = UPPER(:CO_UNIDAD)");
        }
        if (dsUnidad != null) {
            sb_sql.append(" AND UPPER(DS_UNIDAD) = UPPER(:DS_UNIDAD)");
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

    public Integer getIdUnidad() {
        return idUnidad;
    }

    public void setIdUnidad(Integer idUnidad) {
        this.idUnidad = idUnidad;
    }

    public String getCoUnidad() {
        return coUnidad;
    }

    public void setCoUnidad(String coUnidad) {
        this.coUnidad = coUnidad;
    }

    public String getDsUnidad() {
        return dsUnidad;
    }

    public void setDsUnidad(String dsUnidad) {
        this.dsUnidad = dsUnidad;
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
