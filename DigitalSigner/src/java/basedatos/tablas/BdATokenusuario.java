package basedatos.tablas;

import basedatos.InterfazDAO;
import basedatos.OperacionSQL;
import java.util.Date;
import java.util.HashMap;

/**
 *
 * @author ihuegal
 */
public class BdATokenusuario extends OperacionSQL implements InterfazDAO {
    
    protected Integer idTokenusuario;
    protected Integer idUsuario;
    protected String dsToken;
    protected Date feAlta;
    protected Date feDesactivo;
    protected String usuariobd;
    protected Date tstbd;

    public BdATokenusuario() {
        // NADA
    }

    @Override
    public Object getClaseMapeada(HashMap<String,Object> bld) throws Exception {
        recuperaValorCampo(this, "idTokenusuario", "ID_TOKENUSUARIO", bld);
        recuperaValorCampo(this, "idUsuario", "ID_USUARIO", bld);
        recuperaValorCampo(this, "dsToken", "DS_TOKEN", bld);
        recuperaValorCampo(this, "feAlta", "FE_ALTA", bld);
        recuperaValorCampo(this, "feDesactivo", "FE_DESACTIVO", bld);
        recuperaValorCampo(this, "usuariobd", "USUARIOBD", bld);
        recuperaValorCampo(this, "tstbd", "TSTBD", bld);
        
        return this; 
    }
    
    public String getSelectFiltro() {
        StringBuilder sb_sql = new StringBuilder("SELECT ");
        sb_sql.append("ID_TOKENUSUARIO");
        sb_sql.append(",ID_USUARIO");
        sb_sql.append(",DS_TOKEN");
        sb_sql.append(",FE_ALTA");
        sb_sql.append(",FE_DESACTIVO");
        sb_sql.append(",USUARIOBD");
        sb_sql.append(",TSTBD");
        
        sb_sql.append(" FROM BD_A_TOKENUSUARIO WHERE 1=1 ");
        if (idTokenusuario != null) {
            sb_sql.append(" AND ID_TOKENUSUARIO = :ID_TOKENUSUARIO");
        }
        if (idUsuario != null) {
            sb_sql.append(" AND ID_USUARIO = :ID_USUARIO");
        }
        if (dsToken != null) {
            sb_sql.append(" AND UPPER(DS_TOKEN) = UPPER(:DS_TOKEN)");
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

    public Integer getIdTokenusuario() {
        return idTokenusuario;
    }

    public void setIdTokenusuario(Integer idTokenusuario) {
        this.idTokenusuario = idTokenusuario;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getDsToken() {
        return dsToken;
    }

    public void setDsToken(String dsToken) {
        this.dsToken = dsToken;
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
