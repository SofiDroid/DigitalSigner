package basedatos.tablas;

import basedatos.InterfazDAO;
import basedatos.OperacionSQL;
import java.util.Date;
import java.util.HashMap;

/**
 *
 * @author ihuegal
 */
public class BdTTipodocumento extends OperacionSQL implements InterfazDAO {
    
    protected Integer idTipodocumento;
    private String coTipodocumento;
    private String dsTipodocumento;
    private Date feAlta;
    private Date feDesactivo;
    private String usuariobd;
    private Date tstbd;

    public BdTTipodocumento() {
        // NADA
    }

    @Override
    public Object getClaseMapeada(HashMap<String,Object> bld) throws Exception {
        recuperaValorCampo(this, "idTipodocumento", "ID_TIPODOCUMENTO", bld);
        recuperaValorCampo(this, "coTipodocumento", "CO_TIPODOCUMENTO", bld);
        recuperaValorCampo(this, "dsTipodocumento", "DS_TIPODOCUMENTO", bld);
        recuperaValorCampo(this, "feAlta", "FE_ALTA", bld);
        recuperaValorCampo(this, "feDesactivo", "FE_DESACTIVO", bld);
        recuperaValorCampo(this, "usuariobd", "USUARIOBD", bld);
        recuperaValorCampo(this, "tstbd", "TSTBD", bld);
        
        return this; 
    }
    
    public String getSelectFiltro() {
        StringBuilder sb_sql = new StringBuilder("SELECT ");
        sb_sql.append("ID_TIPODOCUMENTO");
        sb_sql.append(",CO_TIPODOCUMENTO");
        sb_sql.append(",DS_TIPODOCUMENTO");
        sb_sql.append(",FE_ALTA");
        sb_sql.append(",FE_DESACTIVO");
        sb_sql.append(",USUARIOBD");
        sb_sql.append(",TSTBD");
        
        sb_sql.append(" FROM BD_T_TIPODOCUMENTO WHERE 1=1 ");
        if (idTipodocumento != null) {
            sb_sql.append(" AND ID_TIPODOCUMENTO = :ID_TIPODOCUMENTO");
        }
        if (coTipodocumento != null) {
            sb_sql.append(" AND UPPER(CO_TIPODOCUMENTO) = UPPER(:CO_TIPODOCUMENTO)");
        }
        if (dsTipodocumento != null) {
            sb_sql.append(" AND UPPER(DS_TIPODOCUMENTO) = UPPER(:DS_TIPODOCUMENTO)");
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

    public Integer getIdTipodocumento() {
        return idTipodocumento;
    }

    public void setIdTipodocumento(Integer idTipodocumento) {
        this.idTipodocumento = idTipodocumento;
    }

    public String getCoTipodocumento() {
        return coTipodocumento;
    }

    public void setCoTipodocumento(String coTipodocumento) {
        this.coTipodocumento = coTipodocumento;
    }

    public String getDsTipodocumento() {
        return dsTipodocumento;
    }

    public void setDsTipodocumento(String dsTipodocumento) {
        this.dsTipodocumento = dsTipodocumento;
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
