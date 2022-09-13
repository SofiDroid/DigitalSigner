package basedatos.tablas;

import basedatos.InterfazDAO;
import basedatos.OperacionSQL;
import java.util.Date;
import java.util.HashMap;

/**
 *
 * @author ihuegal
 */
public class BdTTipousuario extends OperacionSQL implements InterfazDAO {
    
    protected Integer idTipousuario;
    private String coTipousuario;
    private String dsTipousuario;
    private Integer idUnidad;
    private Date feAlta;
    private Date feDesactivo;
    private String usuariobd;
    private Date tstbd;

    public BdTTipousuario() {
        // NADA
    }

    @Override
    public Object getClaseMapeada(HashMap<String,Object> bld) throws Exception {
        recuperaValorCampo(this, "idTipousuario", "ID_TIPOUSUARIO", bld);
        recuperaValorCampo(this, "coTipousuario", "CO_TIPOUSUARIO", bld);
        recuperaValorCampo(this, "dsTipousuario", "DS_TIPOUSUARIO", bld);
        recuperaValorCampo(this, "idUnidad", "ID_UNIDAD", bld);
        recuperaValorCampo(this, "feAlta", "FE_ALTA", bld);
        recuperaValorCampo(this, "feDesactivo", "FE_DESACTIVO", bld);
        recuperaValorCampo(this, "usuariobd", "USUARIOBD", bld);
        recuperaValorCampo(this, "tstbd", "TSTBD", bld);
        
        return this; 
    }
    
    public String getSelectFiltro() {
        StringBuilder sb_sql = new StringBuilder("SELECT ");
        sb_sql.append("ID_TIPOUSUARIO");
        sb_sql.append(",CO_TIPOUSUARIO");
        sb_sql.append(",DS_TIPOUSUARIO");
        sb_sql.append(",ID_UNIDAD");
        sb_sql.append(",FE_ALTA");
        sb_sql.append(",FE_DESACTIVO");
        sb_sql.append(",USUARIOBD");
        sb_sql.append(",TSTBD");
        
        sb_sql.append(" FROM BD_T_TIPOUSUARIO WHERE 1=1 ");
        if (idTipousuario != null) {
            sb_sql.append(" AND ID_TIPOUSUARIO = :ID_TIPOUSUARIO");
        }
        if (coTipousuario != null) {
            sb_sql.append(" AND UPPER(CO_TIPOUSUARIO) = UPPER(:CO_TIPOUSUARIO)");
        }
        if (dsTipousuario != null) {
            sb_sql.append(" AND UPPER(DS_TIPOUSUARIO) = UPPER(:DS_TIPOUSUARIO)");
        }
        if (idUnidad != null) {
            sb_sql.append(" AND ID_UNIDAD = :ID_UNIDAD");
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

    public Integer getIdTipousuario() {
        return idTipousuario;
    }

    public void setIdTipousuario(Integer idTipousuario) {
        this.idTipousuario = idTipousuario;
    }

    public String getCoTipousuario() {
        return coTipousuario;
    }

    public void setCoTipousuario(String coTipousuario) {
        this.coTipousuario = coTipousuario;
    }

    public String getDsTipousuario() {
        return dsTipousuario;
    }

    public void setDsTipousuario(String dsTipousuario) {
        this.dsTipousuario = dsTipousuario;
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
