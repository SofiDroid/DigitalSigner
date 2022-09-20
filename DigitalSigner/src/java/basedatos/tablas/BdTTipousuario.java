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
public class BdTTipousuario extends OperacionSQL implements InterfazDAO {
    
    protected Integer idTipousuario;
    protected String coTipousuario;
    protected String dsTipousuario;
    protected Integer idUnidad;
    protected Date feAlta;
    protected Date feDesactivo;
    protected String usuariobd;
    protected Date tstbd;

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

    public String getSecuencia()
    {
        return "SELECT SBD_T_TIPOUSUARIO.NEXTVAL FROM DUAL";
    }
    
    public String getInsert()
    {
        StringBuilder sb_sql = new StringBuilder("INSERT INTO BD_T_TIPOUSUARIO (");
        if (AppInit.TIPO_BASEDATOS == BaseDatos.ORACLE) {
            sb_sql.append("ID_TIPOUSUARIO,");
        }
        sb_sql.append("CO_TIPOUSUARIO");
        sb_sql.append(",DS_TIPOUSUARIO");
        sb_sql.append(",ID_UNIDAD");
        sb_sql.append(",FE_ALTA");
        sb_sql.append(",FE_DESACTIVO");
        sb_sql.append(",USUARIOBD");
        sb_sql.append(",TSTBD");
        
        sb_sql.append(") VALUES (");
        if(AppInit.TIPO_BASEDATOS == BaseDatos.ORACLE) {
            if (this.idTipousuario == null) {
                sb_sql.append(" SBD_T_TIPOUSUARIO.NEXTVAL");
            } else {
                sb_sql.append(":ID_TIPOUSUARIO");
            }
            sb_sql.append(",").append("DECODE(:CO_TIPOUSUARIO, null, NULL, :CO_TIPOUSUARIO)");
            sb_sql.append(",").append("DECODE(:DS_TIPOUSUARIO, null, NULL, :DS_TIPOUSUARIO)");
            sb_sql.append(",").append("DECODE(:ID_UNIDAD, null, NULL, :ID_UNIDAD)");
            sb_sql.append(",").append("DECODE(:FE_ALTA, null, NULL, :FE_ALTA)");
            sb_sql.append(",").append("DECODE(:FE_DESACTIVO, null, NULL, :FE_DESACTIVO)");
            sb_sql.append(",").append("DECODE(:USUARIOBD, null, NULL, :USUARIOBD)");
            sb_sql.append(",").append("DECODE(:TSTBD, null, NULL, :TSTBD)");
        }
        else {
            sb_sql.append(":CO_TIPOUSUARIO");
            sb_sql.append(",").append(":DS_TIPOUSUARIO");
            sb_sql.append(",").append(":ID_UNIDAD");
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
        StringBuilder sb_sql = new StringBuilder("UPDATE BD_T_TIPOUSUARIO SET ");
        
        if (AppInit.TIPO_BASEDATOS == BaseDatos.ORACLE) {
            sb_sql.append("CO_TIPOUSUARIO = ").append("DECODE(:CO_TIPOUSUARIO, null, NULL, :CO_TIPOUSUARIO)");
            sb_sql.append(",DS_TIPOUSUARIO = ").append("DECODE(:DS_TIPOUSUARIO, null, NULL, :DS_TIPOUSUARIO)");
            sb_sql.append(",ID_UNIDAD = ").append("DECODE(:ID_UNIDAD, null, NULL, :ID_UNIDAD)");
            sb_sql.append(",FE_ALTA = ").append("DECODE(:FE_ALTA, null, NULL, :FE_ALTA)");
            sb_sql.append(",FE_DESACTIVO = ").append("DECODE(:FE_DESACTIVO, null, NULL, :FE_DESACTIVO)");
            sb_sql.append(",USUARIOBD = ").append("DECODE(:USUARIOBD, null, NULL, :USUARIOBD)");
            sb_sql.append(",TSTBD = ").append("DECODE(:TSTBD, null, NULL, :TSTBD)");
        }
        else {
            sb_sql.append("CO_TIPOUSUARIO = :CO_TIPOUSUARIO");
            sb_sql.append(",DS_TIPOUSUARIO = :DS_TIPOUSUARIO");
            sb_sql.append(",ID_UNIDAD = :ID_UNIDAD");
            sb_sql.append(",FE_ALTA = :FE_ALTA");
            sb_sql.append(",FE_DESACTIVO = :FE_DESACTIVO");
            sb_sql.append(",USUARIOBD = :USUARIOBD");
            sb_sql.append(",TSTBD = :TSTBD");
        }
        sb_sql.append(" WHERE ");
        sb_sql.append("ID_TIPOUSUARIO = ").append(":ID_TIPOUSUARIO");
        
        return sb_sql.toString();
    }

    public String getDelete()
    {
        StringBuilder sb_sql = new StringBuilder("DELETE BD_T_TIPOUSUARIO ");
        sb_sql.append(" WHERE ");
        sb_sql.append("ID_TIPOUSUARIO = ").append(":ID_TIPOUSUARIO");
        
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
