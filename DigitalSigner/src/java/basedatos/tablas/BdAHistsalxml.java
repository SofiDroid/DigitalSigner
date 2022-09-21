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
public class BdAHistsalxml extends OperacionSQL implements InterfazDAO {
    
    protected Integer idHistsalxml;
    protected Integer idSalidaxml;
    protected Integer idSituacionxml;
    protected Date feAlta;
    protected Date feDesactivo;
    protected String usuariobd;
    protected Date tstbd;

    public BdAHistsalxml() {
        // NADA
    }

    @Override
    public Object getClaseMapeada(HashMap<String,Object> bld) throws Exception {
        recuperaValorCampo(this, "idHistsalxml", "ID_HISTSALXML", bld);
        recuperaValorCampo(this, "idSalidaxml", "ID_SALIDAXML", bld);
        recuperaValorCampo(this, "idSituacionxml", "ID_SITUACIONXML", bld);
        recuperaValorCampo(this, "feAlta", "FE_ALTA", bld);
        recuperaValorCampo(this, "feDesactivo", "FE_DESACTIVO", bld);
        recuperaValorCampo(this, "usuariobd", "USUARIOBD", bld);
        recuperaValorCampo(this, "tstbd", "TSTBD", bld);
        
        return this; 
    }
    
    public String getSelectFiltro() {
        StringBuilder sb_sql = new StringBuilder("SELECT ");
        sb_sql.append("ID_HISTSALXML");
        sb_sql.append(",ID_SALIDAXML");
        sb_sql.append(",ID_SITUACIONXML");
        sb_sql.append(",FE_ALTA");
        sb_sql.append(",FE_DESACTIVO");
        sb_sql.append(",USUARIOBD");
        sb_sql.append(",TSTBD");
        
        sb_sql.append(" FROM BD_A_HISTSALXML WHERE 1=1 ");
        if (idHistsalxml != null) {
            sb_sql.append(" AND ID_HISTSALXML = :ID_HISTSALXML");
        }
        if (idSalidaxml != null) {
            sb_sql.append(" AND ID_SALIDAXML = :ID_SALIDAXML");
        }
        if (idSituacionxml != null) {
            sb_sql.append(" AND ID_SITUACIONXML = :ID_SITUACIONXML");
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
        return "SELECT SBD_A_HISTSALXML.NEXTVAL FROM DUAL";
    }
    
    public String getInsert()
    {
        StringBuilder sb_sql = new StringBuilder("INSERT INTO BD_A_HISTSALXML (");
        if (AppInit.TIPO_BASEDATOS == BaseDatos.ORACLE) {
            sb_sql.append("ID_HISTSALXML,");
        }
        sb_sql.append("ID_SALIDAXML");
        sb_sql.append(",ID_SITUACIONXML");
        sb_sql.append(",FE_ALTA");
        sb_sql.append(",FE_DESACTIVO");
        sb_sql.append(",USUARIOBD");
        sb_sql.append(",TSTBD");
        
        sb_sql.append(") VALUES (");
        if(AppInit.TIPO_BASEDATOS == BaseDatos.ORACLE) {
            if (this.idHistsalxml == null) {
                sb_sql.append(" SBD_A_HISTSALXML.NEXTVAL");
            } else {
                sb_sql.append(":ID_HISTSALXML");
            }
            sb_sql.append(",").append("DECODE(:ID_SALIDAXML, null, NULL, :ID_SALIDAXML)");
            sb_sql.append(",").append("DECODE(:ID_SITUACIONXML, null, NULL, :ID_SITUACIONXML)");
            sb_sql.append(",").append("DECODE(:FE_ALTA, null, NULL, :FE_ALTA)");
            sb_sql.append(",").append("DECODE(:FE_DESACTIVO, null, NULL, :FE_DESACTIVO)");
            sb_sql.append(",").append("DECODE(:USUARIOBD, null, NULL, :USUARIOBD)");
            sb_sql.append(",").append("DECODE(:TSTBD, null, NULL, :TSTBD)");
        }
        else {
            sb_sql.append(":ID_SALIDAXML");
            sb_sql.append(",").append(":ID_SITUACIONXML");
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
        StringBuilder sb_sql = new StringBuilder("UPDATE BD_A_HISTSALXML SET ");
        
        if (AppInit.TIPO_BASEDATOS == BaseDatos.ORACLE) {
            sb_sql.append("ID_SALIDAXML = ").append("DECODE(:ID_SALIDAXML, null, NULL, :ID_SALIDAXML)");
            sb_sql.append(",ID_SITUACIONXML = ").append("DECODE(:ID_SITUACIONXML, null, NULL, :ID_SITUACIONXML)");
            sb_sql.append(",FE_ALTA = ").append("DECODE(:FE_ALTA, null, NULL, :FE_ALTA)");
            sb_sql.append(",FE_DESACTIVO = ").append("DECODE(:FE_DESACTIVO, null, NULL, :FE_DESACTIVO)");
            sb_sql.append(",USUARIOBD = ").append("DECODE(:USUARIOBD, null, NULL, :USUARIOBD)");
            sb_sql.append(",TSTBD = ").append("DECODE(:TSTBD, null, NULL, :TSTBD)");
        }
        else {
            sb_sql.append("ID_SALIDAXML = :ID_SALIDAXML");
            sb_sql.append(",ID_SITUACIONXML = :ID_SITUACIONXML");
            sb_sql.append(",FE_ALTA = :FE_ALTA");
            sb_sql.append(",FE_DESACTIVO = :FE_DESACTIVO");
            sb_sql.append(",USUARIOBD = :USUARIOBD");
            sb_sql.append(",TSTBD = :TSTBD");
        }
        sb_sql.append(" WHERE ");
        sb_sql.append("ID_HISTSALXML = ").append(":ID_HISTSALXML");
        
        return sb_sql.toString();
    }

    public String getDelete()
    {
        StringBuilder sb_sql = new StringBuilder("DELETE BD_A_HISTSALXML ");
        sb_sql.append(" WHERE ");
        sb_sql.append("ID_HISTSALXML = ").append(":ID_HISTSALXML");
        
        return sb_sql.toString();
    }

    public Integer getIdHistsalxml() {
        return idHistsalxml;
    }

    public void setIdHistsalxml(Integer idHistsalxml) {
        this.idHistsalxml = idHistsalxml;
    }

    public Integer getIdSalidaxml() {
        return idSalidaxml;
    }

    public void setIdSalidaxml(Integer idSalidaxml) {
        this.idSalidaxml = idSalidaxml;
    }

    public Integer getIdSituacionxml() {
        return idSituacionxml;
    }

    public void setIdSituacionxml(Integer idSituacionxml) {
        this.idSituacionxml = idSituacionxml;
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
