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
public class BdTTipodocumento extends OperacionSQL implements InterfazDAO {
    
    protected Integer idTipodocumento;
    protected String coTipodocumento;
    protected String dsTipodocumento;
    protected String diFormatofirma;
    protected Date feAlta;
    protected Date feDesactivo;
    protected String usuariobd;
    protected Date tstbd;

    public BdTTipodocumento() {
        // NADA
    }

    @Override
    public Object getClaseMapeada(HashMap<String,Object> bld) throws Exception {
        recuperaValorCampo(this, "idTipodocumento", "ID_TIPODOCUMENTO", bld);
        recuperaValorCampo(this, "coTipodocumento", "CO_TIPODOCUMENTO", bld);
        recuperaValorCampo(this, "dsTipodocumento", "DS_TIPODOCUMENTO", bld);
        recuperaValorCampo(this, "diFormatofirma", "DI_FORMATOFIRMA", bld);
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
        sb_sql.append(",DI_FORMATOFIRMA");
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
        if (diFormatofirma != null) {
            sb_sql.append(" AND DI_FORMATOFIRMA = :DI_FORMATOFIRMA");
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
        return "SELECT SBD_T_TIPODOCUMENTO.NEXTVAL FROM DUAL";
    }
    
    public String getInsert()
    {
        StringBuilder sb_sql = new StringBuilder("INSERT INTO BD_T_TIPODOCUMENTO (");
        if (AppInit.TIPO_BASEDATOS == BaseDatos.ORACLE) {
            sb_sql.append("ID_TIPODOCUMENTO,");
        }
        sb_sql.append("CO_TIPODOCUMENTO");
        sb_sql.append(",DS_TIPODOCUMENTO");
        sb_sql.append(",DI_FORMATOFIRMA");
        sb_sql.append(",FE_ALTA");
        sb_sql.append(",FE_DESACTIVO");
        sb_sql.append(",USUARIOBD");
        sb_sql.append(",TSTBD");
        
        sb_sql.append(") VALUES (");
        if(AppInit.TIPO_BASEDATOS == BaseDatos.ORACLE) {
            if (this.idTipodocumento == null) {
                sb_sql.append(" SBD_T_TIPODOCUMENTO.NEXTVAL");
            } else {
                sb_sql.append(":ID_TIPODOCUMENTO");
            }
            sb_sql.append(",").append("DECODE(:CO_TIPODOCUMENTO, null, NULL, :CO_TIPODOCUMENTO)");
            sb_sql.append(",").append("DECODE(:DS_TIPODOCUMENTO, null, NULL, :DS_TIPODOCUMENTO)");
            sb_sql.append(",").append("DECODE(:DI_FORMATOFIRMA, null, NULL, :DI_FORMATOFIRMA)");
            sb_sql.append(",").append("DECODE(:FE_ALTA, null, NULL, :FE_ALTA)");
            sb_sql.append(",").append("DECODE(:FE_DESACTIVO, null, NULL, :FE_DESACTIVO)");
            sb_sql.append(",").append("DECODE(:USUARIOBD, null, NULL, :USUARIOBD)");
            sb_sql.append(",").append("DECODE(:TSTBD, null, NULL, :TSTBD)");
        }
        else {
            sb_sql.append(":CO_TIPODOCUMENTO");
            sb_sql.append(",").append(":DS_TIPODOCUMENTO");
            sb_sql.append(",").append(":DI_FORMATOFIRMA");
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
        StringBuilder sb_sql = new StringBuilder("UPDATE BD_T_TIPODOCUMENTO SET ");
        
        if (AppInit.TIPO_BASEDATOS == BaseDatos.ORACLE) {
            sb_sql.append("CO_TIPODOCUMENTO = ").append("DECODE(:CO_TIPODOCUMENTO, null, NULL, :CO_TIPODOCUMENTO)");
            sb_sql.append(",DS_TIPODOCUMENTO = ").append("DECODE(:DS_TIPODOCUMENTO, null, NULL, :DS_TIPODOCUMENTO)");
            sb_sql.append(",DI_FORMATOFIRMA = ").append("DECODE(:DI_FORMATOFIRMA, null, NULL, :DI_FORMATOFIRMA)");
            sb_sql.append(",FE_ALTA = ").append("DECODE(:FE_ALTA, null, NULL, :FE_ALTA)");
            sb_sql.append(",FE_DESACTIVO = ").append("DECODE(:FE_DESACTIVO, null, NULL, :FE_DESACTIVO)");
            sb_sql.append(",USUARIOBD = ").append("DECODE(:USUARIOBD, null, NULL, :USUARIOBD)");
            sb_sql.append(",TSTBD = ").append("DECODE(:TSTBD, null, NULL, :TSTBD)");
        }
        else {
            sb_sql.append("CO_TIPODOCUMENTO = :CO_TIPODOCUMENTO");
            sb_sql.append(",DS_TIPODOCUMENTO = :DS_TIPODOCUMENTO");
            sb_sql.append(",DI_FORMATOFIRMA = :DI_FORMATOFIRMA");
            sb_sql.append(",FE_ALTA = :FE_ALTA");
            sb_sql.append(",FE_DESACTIVO = :FE_DESACTIVO");
            sb_sql.append(",USUARIOBD = :USUARIOBD");
            sb_sql.append(",TSTBD = :TSTBD");
        }
        sb_sql.append(" WHERE ");
        sb_sql.append("ID_TIPODOCUMENTO = ").append(":ID_TIPODOCUMENTO");
        
        return sb_sql.toString();
    }

    public String getDelete()
    {
        StringBuilder sb_sql = new StringBuilder("DELETE BD_T_TIPODOCUMENTO ");
        sb_sql.append(" WHERE ");
        sb_sql.append("ID_TIPODOCUMENTO = ").append(":ID_TIPODOCUMENTO");
        
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

    public String getDiFormatofirma() {
        return diFormatofirma;
    }

    public void setDiFormatofirma(String diFormatofirma) {
        this.diFormatofirma = diFormatofirma;
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
