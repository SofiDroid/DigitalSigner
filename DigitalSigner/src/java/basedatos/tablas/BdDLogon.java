package basedatos.tablas;

import basedatos.InterfazDAO;
import basedatos.OperacionSQL;
import java.util.Date;
import java.util.HashMap;

/**
 *
 * @author ihuegal
 */
public class BdDLogon extends OperacionSQL implements InterfazDAO {
    
    protected Integer idLogon;
    protected Integer idUsuario;
    protected String dsIp;
    protected String dsToken;
    protected Boolean boError;
    protected String dsLlamada;
    protected Date feAlta;
    protected Date feDesactivo;
    protected String usuariobd;
    protected Date tstbd;

    public BdDLogon() {
        // NADA
    }

    @Override
    public Object getClaseMapeada(HashMap<String,Object> bld) throws Exception {
        recuperaValorCampo(this, "idLogon", "ID_LOGON", bld);
        recuperaValorCampo(this, "idUsuario", "ID_USUARIO", bld);
        recuperaValorCampo(this, "dsIp", "DS_IP", bld);
        recuperaValorCampo(this, "dsToken", "DS_TOKEN", bld);
        recuperaValorCampo(this, "boError", "BO_ERROR", bld);
        recuperaValorCampo(this, "dsLlamada", "DS_LLAMADA", bld);
        recuperaValorCampo(this, "feAlta", "FE_ALTA", bld);
        recuperaValorCampo(this, "feDesactivo", "FE_DESACTIVO", bld);
        recuperaValorCampo(this, "usuariobd", "USUARIOBD", bld);
        recuperaValorCampo(this, "tstbd", "TSTBD", bld);
        
        return this; 
    }
    
    public String getSelectFiltro() {
        StringBuilder sb_sql = new StringBuilder("SELECT ");
        sb_sql.append("ID_LOGON");
        sb_sql.append(",ID_USUARIO");
        sb_sql.append(",DS_IP");
        sb_sql.append(",DS_TOKEN");
        sb_sql.append(",BO_ERROR");
        sb_sql.append(",DS_LLAMADA");
        sb_sql.append(",FE_ALTA");
        sb_sql.append(",FE_DESACTIVO");
        sb_sql.append(",USUARIOBD");
        sb_sql.append(",TSTBD");
        
        sb_sql.append(" FROM BD_D_LOGON WHERE 1=1 ");
        if (idLogon != null) {
            sb_sql.append(" AND ID_LOGON = :ID_LOGON");
        }
        if (idUsuario != null) {
            sb_sql.append(" AND ID_USUARIO = :ID_USUARIO");
        }
        if (dsIp != null) {
            sb_sql.append(" AND DS_IP = :DS_IP");
        }
        if (dsToken != null) {
            sb_sql.append(" AND DS_TOKEN = :DS_TOKEN");
        }
        if (boError != null) {
            sb_sql.append(" AND BO_ERROR = :BO_ERROR");
        }
        if (dsLlamada != null) {
            sb_sql.append(" AND DS_LLAMADA = :DS_LLAMADA");
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

    public Integer getIdLogon() {
        return idLogon;
    }

    public void setIdLogon(Integer idLogon) {
        this.idLogon = idLogon;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getDsIp() {
        return dsIp;
    }

    public void setDsIp(String dsIp) {
        this.dsIp = dsIp;
    }

    public String getDsToken() {
        return dsToken;
    }

    public void setDsToken(String dsToken) {
        this.dsToken = dsToken;
    }

    public Boolean getBoError() {
        return boError;
    }

    public void setBoError(Boolean boError) {
        this.boError = boError;
    }

    public String getDsLlamada() {
        return dsLlamada;
    }

    public void setDsLlamada(String dsLlamada) {
        this.dsLlamada = dsLlamada;
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
