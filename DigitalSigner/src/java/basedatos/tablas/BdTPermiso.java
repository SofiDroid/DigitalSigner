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
public class BdTPermiso extends OperacionSQL implements InterfazDAO {
    
    protected Integer idPermiso;
    protected String coPermiso;
    protected String dsPermiso;
    protected Date feAlta;
    protected Date feDesactivo;
    protected String usuariobd;
    protected Date tstbd;

    public BdTPermiso() {
        // NADA
    }

    @Override
    public Object getClaseMapeada(HashMap<String,Object> bld) throws Exception {
        recuperaValorCampo(this, "idPermiso", "ID_PERMISO", bld);
        recuperaValorCampo(this, "coPermiso", "CO_PERMISO", bld);
        recuperaValorCampo(this, "dsPermiso", "DS_PERMISO", bld);
        recuperaValorCampo(this, "feAlta", "FE_ALTA", bld);
        recuperaValorCampo(this, "feDesactivo", "FE_DESACTIVO", bld);
        recuperaValorCampo(this, "usuariobd", "USUARIOBD", bld);
        recuperaValorCampo(this, "tstbd", "TSTBD", bld);
        
        return this; 
    }
    
    public String getSelectFiltro() {
        StringBuilder sb_sql = new StringBuilder("SELECT ");
        sb_sql.append("ID_PERMISO");
        sb_sql.append(",CO_PERMISO");
        sb_sql.append(",DS_PERMISO");
        sb_sql.append(",FE_ALTA");
        sb_sql.append(",FE_DESACTIVO");
        sb_sql.append(",USUARIOBD");
        sb_sql.append(",TSTBD");
        
        sb_sql.append(" FROM BD_T_PERMISO WHERE 1=1 ");
        if (idPermiso != null) {
            sb_sql.append(" AND ID_PERMISO = :ID_PERMISO");
        }
        if (coPermiso != null) {
            sb_sql.append(" AND UPPER(CO_PERMISO) = UPPER(:CO_PERMISO)");
        }
        if (dsPermiso != null) {
            sb_sql.append(" AND UPPER(DS_PERMISO) = UPPER(:DS_PERMISO)");
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
        return "SELECT SBD_T_PERMISO.NEXTVAL FROM DUAL";
    }
    
    public String getInsert()
    {
        StringBuilder sb_sql = new StringBuilder("INSERT INTO BD_T_PERMISO (");
        if (AppInit.TIPO_BASEDATOS == BaseDatos.ORACLE) {
            sb_sql.append("ID_PERMISO,");
        }
        sb_sql.append("CO_PERMISO");
        sb_sql.append(",DS_PERMISO");
        sb_sql.append(",FE_ALTA");
        sb_sql.append(",FE_DESACTIVO");
        sb_sql.append(",USUARIOBD");
        sb_sql.append(",TSTBD");
        
        sb_sql.append(") VALUES (");
        if(AppInit.TIPO_BASEDATOS == BaseDatos.ORACLE) {
            if (this.idPermiso == null) {
                sb_sql.append(" SBD_T_PERMISO.NEXTVAL");
            } else {
                sb_sql.append(":ID_PERMISO");
            }
            sb_sql.append(",").append("DECODE(:CO_PERMISO, null, NULL, :CO_PERMISO)");
            sb_sql.append(",").append("DECODE(:DS_PERMISO, null, NULL, :DS_PERMISO)");
            sb_sql.append(",").append("DECODE(:FE_ALTA, null, NULL, :FE_ALTA)");
            sb_sql.append(",").append("DECODE(:FE_DESACTIVO, null, NULL, :FE_DESACTIVO)");
            sb_sql.append(",").append("DECODE(:USUARIOBD, null, NULL, :USUARIOBD)");
            sb_sql.append(",").append("DECODE(:TSTBD, null, NULL, :TSTBD)");
        }
        else {
            sb_sql.append(":CO_PERMISO");
            sb_sql.append(",").append(":DS_PERMISO");
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
        StringBuilder sb_sql = new StringBuilder("UPDATE BD_T_PERMISO SET ");
        
        if (AppInit.TIPO_BASEDATOS == BaseDatos.ORACLE) {
            sb_sql.append("CO_PERMISO = ").append("DECODE(:CO_PERMISO, null, NULL, :CO_PERMISO)");
            sb_sql.append(",DS_PERMISO = ").append("DECODE(:DS_PERMISO, null, NULL, :DS_PERMISO)");
            sb_sql.append(",FE_ALTA = ").append("DECODE(:FE_ALTA, null, NULL, :FE_ALTA)");
            sb_sql.append(",FE_DESACTIVO = ").append("DECODE(:FE_DESACTIVO, null, NULL, :FE_DESACTIVO)");
            sb_sql.append(",USUARIOBD = ").append("DECODE(:USUARIOBD, null, NULL, :USUARIOBD)");
            sb_sql.append(",TSTBD = ").append("DECODE(:TSTBD, null, NULL, :TSTBD)");
        }
        else {
            sb_sql.append("CO_PERMISO = :CO_PERMISO");
            sb_sql.append(",DS_PERMISO = :DS_PERMISO");
            sb_sql.append(",FE_ALTA = :FE_ALTA");
            sb_sql.append(",FE_DESACTIVO = :FE_DESACTIVO");
            sb_sql.append(",USUARIOBD = :USUARIOBD");
            sb_sql.append(",TSTBD = :TSTBD");
        }
        sb_sql.append(" WHERE ");
        sb_sql.append("ID_PERMISO = ").append(":ID_PERMISO");
        
        return sb_sql.toString();
    }

    public String getDelete()
    {
        StringBuilder sb_sql = new StringBuilder("DELETE BD_T_PERMISO ");
        sb_sql.append(" WHERE ");
        sb_sql.append("ID_PERMISO = ").append(":ID_PERMISO");
        
        return sb_sql.toString();
    }

    public Integer getIdPermiso() {
        return idPermiso;
    }

    public void setIdPermiso(Integer idPermiso) {
        this.idPermiso = idPermiso;
    }

    public String getCoPermiso() {
        return coPermiso;
    }

    public void setCoPermiso(String coPermiso) {
        this.coPermiso = coPermiso;
    }

    public String getDsPermiso() {
        return dsPermiso;
    }

    public void setDsPermiso(String dsPermiso) {
        this.dsPermiso = dsPermiso;
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
