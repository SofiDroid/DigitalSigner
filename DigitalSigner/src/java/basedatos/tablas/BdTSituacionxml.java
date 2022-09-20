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
public class BdTSituacionxml extends OperacionSQL implements InterfazDAO {
    
    protected Integer idSituacionxml;
    protected String coSituacionxml;
    protected String dsSituacionxml;
    protected Date feAlta;
    protected Date feDesactivo;
    protected String usuariobd;
    protected Date tstbd;

    public BdTSituacionxml() {
        // NADA
    }

    @Override
    public Object getClaseMapeada(HashMap<String,Object> bld) throws Exception {
        recuperaValorCampo(this, "idSituacionxml", "ID_SITUACIONXML", bld);
        recuperaValorCampo(this, "coSituacionxml", "CO_SITUACIONXML", bld);
        recuperaValorCampo(this, "dsSituacionxml", "DS_SITUACIONXML", bld);
        recuperaValorCampo(this, "feAlta", "FE_ALTA", bld);
        recuperaValorCampo(this, "feDesactivo", "FE_DESACTIVO", bld);
        recuperaValorCampo(this, "usuariobd", "USUARIOBD", bld);
        recuperaValorCampo(this, "tstbd", "TSTBD", bld);
        
        return this; 
    }
    
    public String getSelectFiltro() {
        StringBuilder sb_sql = new StringBuilder("SELECT ");
        sb_sql.append("ID_SITUACIONXML");
        sb_sql.append(",CO_SITUACIONXML");
        sb_sql.append(",DS_SITUACIONXML");
        sb_sql.append(",FE_ALTA");
        sb_sql.append(",FE_DESACTIVO");
        sb_sql.append(",USUARIOBD");
        sb_sql.append(",TSTBD");
        
        sb_sql.append(" FROM BD_T_SITUACIONXML WHERE 1=1 ");
        if (idSituacionxml != null) {
            sb_sql.append(" AND ID_SITUACIONXML = :ID_SITUACIONXML");
        }
        if (coSituacionxml != null) {
            sb_sql.append(" AND UPPER(CO_SITUACIONXML) = UPPER(:CO_SITUACIONXML)");
        }
        if (dsSituacionxml != null) {
            sb_sql.append(" AND UPPER(DS_SITUACIONXML) = UPPER(:DS_SITUACIONXML)");
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
        return "SELECT SBD_T_SITUACIONXML.NEXTVAL FROM DUAL";
    }
    
    public String getInsert()
    {
        StringBuilder sb_sql = new StringBuilder("INSERT INTO BD_T_SITUACIONXML (");
        if (AppInit.TIPO_BASEDATOS == BaseDatos.ORACLE) {
            sb_sql.append("ID_SITUACIONXML,");
        }
        sb_sql.append("CO_SITUACIONXML");
        sb_sql.append(",DS_SITUACIONXML");
        sb_sql.append(",FE_ALTA");
        sb_sql.append(",FE_DESACTIVO");
        sb_sql.append(",USUARIOBD");
        sb_sql.append(",TSTBD");
        
        sb_sql.append(") VALUES (");
        if(AppInit.TIPO_BASEDATOS == BaseDatos.ORACLE) {
            if (this.idSituacionxml == null) {
                sb_sql.append(" SBD_T_SITUACIONXML.NEXTVAL");
            } else {
                sb_sql.append(":ID_SITUACIONXML");
            }
            sb_sql.append(",").append("DECODE(:CO_SITUACIONXML, null, NULL, :CO_SITUACIONXML)");
            sb_sql.append(",").append("DECODE(:DS_SITUACIONXML, null, NULL, :DS_SITUACIONXML)");
            sb_sql.append(",").append("DECODE(:FE_ALTA, null, NULL, :FE_ALTA)");
            sb_sql.append(",").append("DECODE(:FE_DESACTIVO, null, NULL, :FE_DESACTIVO)");
            sb_sql.append(",").append("DECODE(:USUARIOBD, null, NULL, :USUARIOBD)");
            sb_sql.append(",").append("DECODE(:TSTBD, null, NULL, :TSTBD)");
        }
        else {
            sb_sql.append(":CO_SITUACIONXML");
            sb_sql.append(",").append(":DS_SITUACIONXML");
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
        StringBuilder sb_sql = new StringBuilder("UPDATE BD_T_SITUACIONXML SET ");
        
        if (AppInit.TIPO_BASEDATOS == BaseDatos.ORACLE) {
            sb_sql.append("CO_SITUACIONXML = ").append("DECODE(:CO_SITUACIONXML, null, NULL, :CO_SITUACIONXML)");
            sb_sql.append(",DS_SITUACIONXML = ").append("DECODE(:DS_SITUACIONXML, null, NULL, :DS_SITUACIONXML)");
            sb_sql.append(",FE_ALTA = ").append("DECODE(:FE_ALTA, null, NULL, :FE_ALTA)");
            sb_sql.append(",FE_DESACTIVO = ").append("DECODE(:FE_DESACTIVO, null, NULL, :FE_DESACTIVO)");
            sb_sql.append(",USUARIOBD = ").append("DECODE(:USUARIOBD, null, NULL, :USUARIOBD)");
            sb_sql.append(",TSTBD = ").append("DECODE(:TSTBD, null, NULL, :TSTBD)");
        }
        else {
            sb_sql.append("CO_SITUACIONXML = :CO_SITUACIONXML");
            sb_sql.append(",DS_SITUACIONXML = :DS_SITUACIONXML");
            sb_sql.append(",FE_ALTA = :FE_ALTA");
            sb_sql.append(",FE_DESACTIVO = :FE_DESACTIVO");
            sb_sql.append(",USUARIOBD = :USUARIOBD");
            sb_sql.append(",TSTBD = :TSTBD");
        }
        sb_sql.append(" WHERE ");
        sb_sql.append("ID_SITUACIONXML = ").append(":ID_SITUACIONXML");
        
        return sb_sql.toString();
    }

    public String getDelete()
    {
        StringBuilder sb_sql = new StringBuilder("DELETE BD_T_SITUACIONXML ");
        sb_sql.append(" WHERE ");
        sb_sql.append("ID_SITUACIONXML = ").append(":ID_SITUACIONXML");
        
        return sb_sql.toString();
    }

    public Integer getIdSituacionxml() {
        return idSituacionxml;
    }

    public void setIdSituacionxml(Integer idSituacionxml) {
        this.idSituacionxml = idSituacionxml;
    }

    public String getCoSituacionxml() {
        return coSituacionxml;
    }

    public void setCoSituacionxml(String coSituacionxml) {
        this.coSituacionxml = coSituacionxml;
    }

    public String getDsSituacionxml() {
        return dsSituacionxml;
    }

    public void setDsSituacionxml(String dsSituacionxml) {
        this.dsSituacionxml = dsSituacionxml;
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
