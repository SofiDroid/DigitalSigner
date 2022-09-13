package basedatos.tablas;

import basedatos.InterfazDAO;
import basedatos.OperacionSQL;
import java.util.Date;
import java.util.HashMap;

/**
 *
 * @author ihuegal
 */
public class BdTAutoridad extends OperacionSQL implements InterfazDAO {
    
    protected Integer idAutoridad;
    private String coAutoridad;
    private String dsAutoridad;
    private Integer idUnidad;
    private Date feAlta;
    private Date feDesactivo;
    private String usuariobd;
    private Date tstbd;

    public BdTAutoridad() {
        // NADA
    }

    @Override
    public Object getClaseMapeada(HashMap<String,Object> bld) throws Exception {
        recuperaValorCampo(this, "idAutoridad", "ID_AUTORIDAD", bld);
        recuperaValorCampo(this, "coAutoridad", "CO_AUTORIDAD", bld);
        recuperaValorCampo(this, "dsAutoridad", "DS_AUTORIDAD", bld);
        recuperaValorCampo(this, "idUnidad", "ID_UNIDAD", bld);
        recuperaValorCampo(this, "feAlta", "FE_ALTA", bld);
        recuperaValorCampo(this, "feDesactivo", "FE_DESACTIVO", bld);
        recuperaValorCampo(this, "usuariobd", "USUARIOBD", bld);
        recuperaValorCampo(this, "tstbd", "TSTBD", bld);
        
        return this; 
    }
    
    public String getSelectFiltro() {
        StringBuilder sb_sql = new StringBuilder("SELECT ");
        sb_sql.append("ID_AUTORIDAD");
        sb_sql.append(",CO_AUTORIDAD");
        sb_sql.append(",DS_AUTORIDAD");
        sb_sql.append(",ID_UNIDAD");
        sb_sql.append(",FE_ALTA");
        sb_sql.append(",FE_DESACTIVO");
        sb_sql.append(",USUARIOBD");
        sb_sql.append(",TSTBD");
        
        sb_sql.append(" FROM BD_T_AUTORIDAD WHERE 1=1 ");
        if (idAutoridad != null) {
            sb_sql.append(" AND ID_AUTORIDAD = :ID_AUTORIDAD");
        }
        if (coAutoridad != null) {
            sb_sql.append(" AND UPPER(CO_AUTORIDAD) = UPPER(:CO_AUTORIDAD)");
        }
        if (dsAutoridad != null) {
            sb_sql.append(" AND UPPER(DS_AUTORIDAD) = UPPER(:DS_AUTORIDAD)");
        }
        if (idUnidad != null) {
            sb_sql.append(" AND UPPER(ID_UNIDAD) = UPPER(:ID_UNIDAD)");
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

    public Integer getIdAutoridad() {
        return idAutoridad;
    }

    public void setIdAutoridad(Integer idAutoridad) {
        this.idAutoridad = idAutoridad;
    }

    public String getCoAutoridad() {
        return coAutoridad;
    }

    public void setCoAutoridad(String coAutoridad) {
        this.coAutoridad = coAutoridad;
    }

    public String getDsAutoridad() {
        return dsAutoridad;
    }

    public void setDsAutoridad(String dsAutoridad) {
        this.dsAutoridad = dsAutoridad;
    }

    public Integer getIdUnidad() {
        return idUnidad;
    }

    public void setIdUnidad(Integer idUnidad) {
        this.idUnidad = idUnidad;
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
