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
public class BdAConftipodoc extends OperacionSQL implements InterfazDAO {
    
    protected Integer idConftipodoc;
    protected Integer idTipodocumento;
    protected Integer idAutoridad;
    protected Integer enOrden;
    protected String diTipofirma;
    protected String dsFirmaposx;
    protected String dsFirmaposy;
    protected Date feAlta;
    protected Date feDesactivo;
    protected String usuariobd;
    protected Date tstbd;

    public BdAConftipodoc() {
        // NADA
    }

    @Override
    public Object getClaseMapeada(HashMap<String,Object> bld) throws Exception {
        recuperaValorCampo(this, "idConftipodoc", "ID_CONFTIPODOC", bld);
        recuperaValorCampo(this, "idTipodocumento", "ID_TIPODOCUMENTO", bld);
        recuperaValorCampo(this, "idAutoridad", "ID_AUTORIDAD", bld);
        recuperaValorCampo(this, "enOrden", "EN_ORDEN", bld);
        recuperaValorCampo(this, "diTipofirma", "DI_TIPOFIRMA", bld);
        recuperaValorCampo(this, "dsFirmaposx", "DS_FIRMAPOSX", bld);
        recuperaValorCampo(this, "dsFirmaposy", "DS_FIRMAPOSY", bld);
        recuperaValorCampo(this, "feAlta", "FE_ALTA", bld);
        recuperaValorCampo(this, "feDesactivo", "FE_DESACTIVO", bld);
        recuperaValorCampo(this, "usuariobd", "USUARIOBD", bld);
        recuperaValorCampo(this, "tstbd", "TSTBD", bld);
        
        return this; 
    }
    
    public String getSelectFiltro() {
        StringBuilder sb_sql = new StringBuilder("SELECT ");
        sb_sql.append("ID_CONFTIPODOC");
        sb_sql.append(",ID_TIPODOCUMENTO");
        sb_sql.append(",ID_AUTORIDAD");
        sb_sql.append(",EN_ORDEN");
        sb_sql.append(",DI_TIPOFIRMA");
        sb_sql.append(",DS_FIRMAPOSX");
        sb_sql.append(",DS_FIRMAPOSY");
        sb_sql.append(",FE_ALTA");
        sb_sql.append(",FE_DESACTIVO");
        sb_sql.append(",USUARIOBD");
        sb_sql.append(",TSTBD");
        
        sb_sql.append(" FROM BD_A_CONFTIPODOC WHERE 1=1 ");
        if (idConftipodoc != null) {
            sb_sql.append(" AND ID_CONFTIPODOC = :ID_CONFTIPODOC");
        }
        if (idTipodocumento != null) {
            sb_sql.append(" AND ID_TIPODOCUMENTO = :ID_TIPODOCUMENTO");
        }
        if (idAutoridad != null) {
            sb_sql.append(" AND ID_AUTORIDAD = :ID_AUTORIDAD");
        }
        if (enOrden != null) {
            sb_sql.append(" AND EN_ORDEN = :EN_ORDEN");
        }
        if (diTipofirma != null) {
            sb_sql.append(" AND DI_TIPOFIRMA = :DI_TIPOFIRMA");
        }
        if (dsFirmaposx != null) {
            sb_sql.append(" AND UPPER(DS_FIRMAPOSX) = UPPER(:DS_FIRMAPOSX)");
        }
        if (dsFirmaposy != null) {
            sb_sql.append(" AND UPPER(DS_FIRMAPOSY) = UPPER(:DS_FIRMAPOSY)");
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
        return "SELECT SBD_A_CONFTIPODOC.NEXTVAL FROM DUAL";
    }
    
    public String getInsert()
    {
        StringBuilder sb_sql = new StringBuilder("INSERT INTO BD_A_CONFTIPODOC (");
        if (AppInit.TIPO_BASEDATOS == BaseDatos.ORACLE) {
            sb_sql.append("ID_CONFTIPODOC,");
        }
        sb_sql.append("ID_TIPODOCUMENTO");
        sb_sql.append(",ID_AUTORIDAD");
        sb_sql.append(",EN_ORDEN");
        sb_sql.append(",DI_TIPOFIRMA");
        sb_sql.append(",DS_FIRMAPOSX");
        sb_sql.append(",DS_FIRMAPOSY");
        sb_sql.append(",FE_ALTA");
        sb_sql.append(",FE_DESACTIVO");
        sb_sql.append(",USUARIOBD");
        sb_sql.append(",TSTBD");
        
        sb_sql.append(") VALUES (");
        if(AppInit.TIPO_BASEDATOS == BaseDatos.ORACLE) {
            if (this.idConftipodoc == null) {
                sb_sql.append(" SBD_A_CONFTIPODOC.NEXTVAL");
            } else {
                sb_sql.append(":ID_CONFTIPODOC");
            }
            sb_sql.append(",").append("DECODE(:ID_TIPODOCUMENTO, null, NULL, :ID_TIPODOCUMENTO)");
            sb_sql.append(",").append("DECODE(:ID_AUTORIDAD, null, NULL, :ID_AUTORIDAD)");
            sb_sql.append(",").append("DECODE(:EN_ORDEN, null, NULL, :EN_ORDEN)");
            sb_sql.append(",").append("DECODE(:DI_TIPOFIRMA, null, NULL, :DI_TIPOFIRMA)");
            sb_sql.append(",").append("DECODE(:DS_FIRMAPOSX, null, NULL, :DS_FIRMAPOSX)");
            sb_sql.append(",").append("DECODE(:DS_FIRMAPOSY, null, NULL, :DS_FIRMAPOSY)");
            sb_sql.append(",").append("DECODE(:FE_ALTA, null, NULL, :FE_ALTA)");
            sb_sql.append(",").append("DECODE(:FE_DESACTIVO, null, NULL, :FE_DESACTIVO)");
            sb_sql.append(",").append("DECODE(:USUARIOBD, null, NULL, :USUARIOBD)");
            sb_sql.append(",").append("DECODE(:TSTBD, null, NULL, :TSTBD)");
        }
        else {
            sb_sql.append(":ID_TIPODOCUMENTO");
            sb_sql.append(",").append(":ID_AUTORIDAD");
            sb_sql.append(",").append(":EN_ORDEN");
            sb_sql.append(",").append(":DI_TIPOFIRMA");
            sb_sql.append(",").append(":DS_FIRMAPOSX");
            sb_sql.append(",").append(":DS_FIRMAPOSY");
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
        StringBuilder sb_sql = new StringBuilder("UPDATE BD_A_CONFTIPODOC SET ");
        
        if (AppInit.TIPO_BASEDATOS == BaseDatos.ORACLE) {
            sb_sql.append("ID_TIPODOCUMENTO = ").append("DECODE(:ID_TIPODOCUMENTO, null, NULL, :ID_TIPODOCUMENTO)");
            sb_sql.append(",ID_AUTORIDAD = ").append("DECODE(:ID_AUTORIDAD, null, NULL, :ID_AUTORIDAD)");
            sb_sql.append(",EN_ORDEN = ").append("DECODE(:EN_ORDEN, null, NULL, :EN_ORDEN)");
            sb_sql.append(",DI_TIPOFIRMA = ").append("DECODE(:DI_TIPOFIRMA, null, NULL, :DI_TIPOFIRMA)");
            sb_sql.append(",DS_FIRMAPOSX = ").append("DECODE(:DS_FIRMAPOSX, null, NULL, :DS_FIRMAPOSX)");
            sb_sql.append(",DS_FIRMAPOSY = ").append("DECODE(:DS_FIRMAPOSY, null, NULL, :DS_FIRMAPOSY)");
            sb_sql.append(",FE_ALTA = ").append("DECODE(:FE_ALTA, null, NULL, :FE_ALTA)");
            sb_sql.append(",FE_DESACTIVO = ").append("DECODE(:FE_DESACTIVO, null, NULL, :FE_DESACTIVO)");
            sb_sql.append(",USUARIOBD = ").append("DECODE(:USUARIOBD, null, NULL, :USUARIOBD)");
            sb_sql.append(",TSTBD = ").append("DECODE(:TSTBD, null, NULL, :TSTBD)");
        }
        else {
            sb_sql.append("ID_TIPODOCUMENTO = :ID_TIPODOCUMENTO");
            sb_sql.append(",ID_AUTORIDAD = :ID_AUTORIDAD");
            sb_sql.append(",EN_ORDEN = :EN_ORDEN");
            sb_sql.append(",DI_TIPOFIRMA = :DI_TIPOFIRMA");
            sb_sql.append(",DS_FIRMAPOSX = :DS_FIRMAPOSX");
            sb_sql.append(",DS_FIRMAPOSY = :DS_FIRMAPOSY");
            sb_sql.append(",FE_ALTA = :FE_ALTA");
            sb_sql.append(",FE_DESACTIVO = :FE_DESACTIVO");
            sb_sql.append(",USUARIOBD = :USUARIOBD");
            sb_sql.append(",TSTBD = :TSTBD");
        }
        sb_sql.append(" WHERE ");
        sb_sql.append("ID_CONFTIPODOC = ").append(":ID_CONFTIPODOC");
        
        return sb_sql.toString();
    }

    public String getDelete()
    {
        StringBuilder sb_sql = new StringBuilder("DELETE BD_A_CONFTIPODOC ");
        sb_sql.append(" WHERE ");
        sb_sql.append("ID_CONFTIPODOC = ").append(":ID_CONFTIPODOC");
        
        return sb_sql.toString();
    }

    public Integer getIdConftipodoc() {
        return idConftipodoc;
    }

    public void setIdConftipodoc(Integer idConftipodoc) {
        this.idConftipodoc = idConftipodoc;
    }

    public Integer getIdTipodocumento() {
        return idTipodocumento;
    }

    public void setIdTipodocumento(Integer idTipodocumento) {
        this.idTipodocumento = idTipodocumento;
    }

    public Integer getIdAutoridad() {
        return idAutoridad;
    }

    public void setIdAutoridad(Integer idAutoridad) {
        this.idAutoridad = idAutoridad;
    }

    public Integer getEnOrden() {
        return enOrden;
    }

    public void setEnOrden(Integer enOrden) {
        this.enOrden = enOrden;
    }

    public String getDiTipofirma() {
        return diTipofirma;
    }

    public void setDiTipofirma(String diTipofirma) {
        this.diTipofirma = diTipofirma;
    }

    public String getDsFirmaposx() {
        return dsFirmaposx;
    }

    public void setDsFirmaposx(String dsFirmaposx) {
        this.dsFirmaposx = dsFirmaposx;
    }

    public String getDsFirmaposy() {
        return dsFirmaposy;
    }

    public void setDsFirmaposy(String dsFirmaposy) {
        this.dsFirmaposy = dsFirmaposy;
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
