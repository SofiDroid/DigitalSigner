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
public class BdTSituaciondoc extends OperacionSQL implements InterfazDAO {
    
    protected Integer idSituaciondoc;
    protected String coSituaciondoc;
    protected String dsSituaciondoc;
    protected Date feAlta;
    protected Date feDesactivo;
    protected String usuariobd;
    protected Date tstbd;

    public BdTSituaciondoc() {
        // NADA
    }

    @Override
    public Object getClaseMapeada(HashMap<String,Object> bld) throws Exception {
        recuperaValorCampo(this, "idSituaciondoc", "ID_SITUACIONDOC", bld);
        recuperaValorCampo(this, "coSituaciondoc", "CO_SITUACIONDOC", bld);
        recuperaValorCampo(this, "dsSituaciondoc", "DS_SITUACIONDOC", bld);
        recuperaValorCampo(this, "feAlta", "FE_ALTA", bld);
        recuperaValorCampo(this, "feDesactivo", "FE_DESACTIVO", bld);
        recuperaValorCampo(this, "usuariobd", "USUARIOBD", bld);
        recuperaValorCampo(this, "tstbd", "TSTBD", bld);
        
        return this; 
    }
    
    public String getSelectFiltro() {
        StringBuilder sb_sql = new StringBuilder("SELECT ");
        sb_sql.append("ID_SITUACIONDOC");
        sb_sql.append(",CO_SITUACIONDOC");
        sb_sql.append(",DS_SITUACIONDOC");
        sb_sql.append(",FE_ALTA");
        sb_sql.append(",FE_DESACTIVO");
        sb_sql.append(",USUARIOBD");
        sb_sql.append(",TSTBD");
        
        sb_sql.append(" FROM BD_T_SITUACIONDOC WHERE 1=1 ");
        if (idSituaciondoc != null) {
            sb_sql.append(" AND ID_SITUACIONDOC = :ID_SITUACIONDOC");
        }
        if (coSituaciondoc != null) {
            sb_sql.append(" AND UPPER(CO_SITUACIONDOC) = UPPER(:CO_SITUACIONDOC)");
        }
        if (dsSituaciondoc != null) {
            sb_sql.append(" AND UPPER(DS_SITUACIONDOC) = UPPER(:DS_SITUACIONDOC)");
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
        return "SELECT SBD_T_SITUACIONDOC.NEXTVAL FROM DUAL";
    }
    
    public String getInsert()
    {
        StringBuilder sb_sql = new StringBuilder("INSERT INTO BD_T_SITUACIONDOC (");
        if (AppInit.TIPO_BASEDATOS == BaseDatos.ORACLE) {
            sb_sql.append("ID_SITUACIONDOC,");
        }
        sb_sql.append("CO_SITUACIONDOC");
        sb_sql.append(",DS_SITUACIONDOC");
        sb_sql.append(",FE_ALTA");
        sb_sql.append(",FE_DESACTIVO");
        sb_sql.append(",USUARIOBD");
        sb_sql.append(",TSTBD");
        
        sb_sql.append(") VALUES (");
        if(AppInit.TIPO_BASEDATOS == BaseDatos.ORACLE) {
            if (this.idSituaciondoc == null) {
                sb_sql.append(" SBD_T_SITUACIONDOC.NEXTVAL");
            } else {
                sb_sql.append(":ID_SITUACIONDOC");
            }
            sb_sql.append(",").append("DECODE(:CO_SITUACIONDOC, null, NULL, :CO_SITUACIONDOC)");
            sb_sql.append(",").append("DECODE(:DS_SITUACIONDOC, null, NULL, :DS_SITUACIONDOC)");
            sb_sql.append(",").append("DECODE(:FE_ALTA, null, NULL, :FE_ALTA)");
            sb_sql.append(",").append("DECODE(:FE_DESACTIVO, null, NULL, :FE_DESACTIVO)");
            sb_sql.append(",").append("DECODE(:USUARIOBD, null, NULL, :USUARIOBD)");
            sb_sql.append(",").append("DECODE(:TSTBD, null, NULL, :TSTBD)");
        }
        else {
            sb_sql.append(":CO_SITUACIONDOC");
            sb_sql.append(",").append(":DS_SITUACIONDOC");
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
        StringBuilder sb_sql = new StringBuilder("UPDATE BD_T_SITUACIONDOC SET ");
        
        if (AppInit.TIPO_BASEDATOS == BaseDatos.ORACLE) {
            sb_sql.append("CO_SITUACIONDOC = ").append("DECODE(:CO_SITUACIONDOC, null, NULL, :CO_SITUACIONDOC)");
            sb_sql.append(",DS_SITUACIONDOC = ").append("DECODE(:DS_SITUACIONDOC, null, NULL, :DS_SITUACIONDOC)");
            sb_sql.append(",FE_ALTA = ").append("DECODE(:FE_ALTA, null, NULL, :FE_ALTA)");
            sb_sql.append(",FE_DESACTIVO = ").append("DECODE(:FE_DESACTIVO, null, NULL, :FE_DESACTIVO)");
            sb_sql.append(",USUARIOBD = ").append("DECODE(:USUARIOBD, null, NULL, :USUARIOBD)");
            sb_sql.append(",TSTBD = ").append("DECODE(:TSTBD, null, NULL, :TSTBD)");
        }
        else {
            sb_sql.append("CO_SITUACIONDOC = :CO_SITUACIONDOC");
            sb_sql.append(",DS_SITUACIONDOC = :DS_SITUACIONDOC");
            sb_sql.append(",FE_ALTA = :FE_ALTA");
            sb_sql.append(",FE_DESACTIVO = :FE_DESACTIVO");
            sb_sql.append(",USUARIOBD = :USUARIOBD");
            sb_sql.append(",TSTBD = :TSTBD");
        }
        sb_sql.append(" WHERE ");
        sb_sql.append("ID_SITUACIONDOC = ").append(":ID_SITUACIONDOC");
        
        return sb_sql.toString();
    }

    public String getDelete()
    {
        StringBuilder sb_sql = new StringBuilder("DELETE BD_T_SITUACIONDOC ");
        sb_sql.append(" WHERE ");
        sb_sql.append("ID_SITUACIONDOC = ").append(":ID_SITUACIONDOC");
        
        return sb_sql.toString();
    }

    public Integer getIdSituaciondoc() {
        return idSituaciondoc;
    }

    public void setIdSituaciondoc(Integer idSituaciondoc) {
        this.idSituaciondoc = idSituaciondoc;
    }

    public String getCoSituaciondoc() {
        return coSituaciondoc;
    }

    public void setCoSituaciondoc(String coSituaciondoc) {
        this.coSituaciondoc = coSituaciondoc;
    }

    public String getDsSituaciondoc() {
        return dsSituaciondoc;
    }

    public void setDsSituaciondoc(String dsSituaciondoc) {
        this.dsSituaciondoc = dsSituaciondoc;
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
