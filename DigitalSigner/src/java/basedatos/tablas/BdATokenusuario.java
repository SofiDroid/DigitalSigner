package basedatos.tablas;

import basedatos.InterfazDAO;
import basedatos.OperacionSQL;
import init.AppInit;
import java.util.Date;
import java.util.HashMap;
import utilidades.BaseDatos;

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

    public String getSecuencia()
    {
        return "SELECT SBD_A_TOKENUSUARIO.NEXTVAL FROM DUAL";
    }
    
    public String getInsert()
    {
        StringBuilder sb_sql = new StringBuilder("INSERT INTO BD_A_TOKENUSUARIO (");
        if (AppInit.TIPO_BASEDATOS == BaseDatos.ORACLE) {
            sb_sql.append("ID_TOKENUSUARIO,");
        }
        sb_sql.append("ID_USUARIO");
        sb_sql.append(",DS_TOKEN");
        sb_sql.append(",FE_ALTA");
        sb_sql.append(",FE_DESACTIVO");
        sb_sql.append(",USUARIOBD");
        sb_sql.append(",TSTBD");
        
        sb_sql.append(") VALUES (");
        if(AppInit.TIPO_BASEDATOS == BaseDatos.ORACLE) {
            if (this.idTokenusuario == null) {
                sb_sql.append(" SBD_A_TOKENUSUARIO.NEXTVAL");
            } else {
                sb_sql.append(":ID_TOKENUSUARIO");
            }
            sb_sql.append(",").append("DECODE(:ID_USUARIO, null, NULL, :ID_USUARIO)");
            sb_sql.append(",").append("DECODE(:DS_TOKEN, null, NULL, :DS_TOKEN)");
            sb_sql.append(",").append("DECODE(:FE_ALTA, null, NULL, :FE_ALTA)");
            sb_sql.append(",").append("DECODE(:FE_DESACTIVO, null, NULL, :FE_DESACTIVO)");
            sb_sql.append(",").append("DECODE(:USUARIOBD, null, NULL, :USUARIOBD)");
            sb_sql.append(",").append("DECODE(:TSTBD, null, NULL, :TSTBD)");
        }
        else {
            sb_sql.append(":ID_USUARIO");
            sb_sql.append(",").append(":DS_TOKEN");
            sb_sql.append(",").append(":FE_ALTA");
            sb_sql.append(",").append(":FE_DESACTIVO");
            sb_sql.append(",").append(":USUARIOBD");
            sb_sql.append(",").append(":TSTBD");
        }
        sb_sql.append(")");
        return sb_sql.toString();
    }

    public String getUpdate()
    {
        StringBuilder sb_sql = new StringBuilder("UPDATE BD_A_TOKENUSUARIO SET ");
        
        if (AppInit.TIPO_BASEDATOS == BaseDatos.ORACLE) {
            sb_sql.append("ID_USUARIO = ").append("DECODE(:ID_USUARIO, null, NULL, :ID_USUARIO)");
            sb_sql.append(",DS_TOKEN = ").append("DECODE(:DS_TOKEN, null, NULL, :DS_TOKEN)");
            sb_sql.append(",FE_ALTA = ").append("DECODE(:FE_ALTA, null, NULL, :FE_ALTA)");
            sb_sql.append(",FE_DESACTIVO = ").append("DECODE(:FE_DESACTIVO, null, NULL, :FE_DESACTIVO)");
            sb_sql.append(",USUARIOBD = ").append("DECODE(:USUARIOBD, null, NULL, :USUARIOBD)");
            sb_sql.append(",TSTBD = ").append("DECODE(:TSTBD, null, NULL, :TSTBD)");
        }
        else {
            sb_sql.append("ID_USUARIO = :ID_USUARIO");
            sb_sql.append(",DS_TOKEN = :DS_TOKEN");
            sb_sql.append(",FE_ALTA = :FE_ALTA");
            sb_sql.append(",FE_DESACTIVO = :FE_DESACTIVO");
            sb_sql.append(",USUARIOBD = :USUARIOBD");
            sb_sql.append(",TSTBD = :TSTBD");
        }
        sb_sql.append(" WHERE ");
        sb_sql.append("ID_TOKENUSUARIO = ").append(":ID_TOKENUSUARIO");
        
        return sb_sql.toString();
    }

    public String getDelete()
    {
        StringBuilder sb_sql = new StringBuilder("DELETE BD_A_TOKENUSUARIO ");
        sb_sql.append(" WHERE ");
        sb_sql.append("ID_TOKENUSUARIO = ").append(":ID_TOKENUSUARIO");
        
        return sb_sql.toString();
    }

    public String getDesactivarToken()
    {
        return """
                UPDATE BD_A_TOKENUSUARIO SET
                 FE_DESACTIVO = :FE_DESACTIVO,
                 USUARIOBD = :USUARIOBD,
                 TSTBD = :TSTBD
                WHERE
                 FE_DESACTIVO IS NULL
                AND ID_USUARIO = :ID_USUARIO
                """;
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
