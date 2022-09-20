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
public class BdTAutoridad extends OperacionSQL implements InterfazDAO {
    
    protected Integer idAutoridad;
    protected String coAutoridad;
    protected String dsAutoridad;
    protected Integer idUnidad;
    protected Date feAlta;
    protected Date feDesactivo;
    protected String usuariobd;
    protected Date tstbd;

    public BdTAutoridad() {
        // NADA
    }

    @Override
    public Object getClaseMapeada(HashMap<String,Object> bld) throws Exception {
        recuperaValorCampo(this, "idAutoridad", "ID_AUTORIDAD", bld);
        recuperaValorCampo(this, "coAutoridad", "CO_AUTORIDAD", bld);
        recuperaValorCampo(this, "dsAutoridad", "DS_AUTORIDAD", bld);
        recuperaValorCampo(this, "idUnidad", "ID_UNIDAD", bld);
        recuperaValorCampo(this, "feAlta", "FE_ALTA", bld);
        recuperaValorCampo(this, "feDesactivo", "FE_DESACTIVO", bld);
        recuperaValorCampo(this, "usuariobd", "USUARIOBD", bld);
        recuperaValorCampo(this, "tstbd", "TSTBD", bld);
        
        return this; 
    }
    
    public String getSelectFiltro() {
        StringBuilder sb_sql = new StringBuilder("SELECT ");
        sb_sql.append("ID_AUTORIDAD");
        sb_sql.append(",CO_AUTORIDAD");
        sb_sql.append(",DS_AUTORIDAD");
        sb_sql.append(",ID_UNIDAD");
        sb_sql.append(",FE_ALTA");
        sb_sql.append(",FE_DESACTIVO");
        sb_sql.append(",USUARIOBD");
        sb_sql.append(",TSTBD");
        
        sb_sql.append(" FROM BD_T_AUTORIDAD WHERE 1=1 ");
        if (idAutoridad != null) {
            sb_sql.append(" AND ID_AUTORIDAD = :ID_AUTORIDAD");
        }
        if (coAutoridad != null) {
            sb_sql.append(" AND UPPER(CO_AUTORIDAD) = UPPER(:CO_AUTORIDAD)");
        }
        if (dsAutoridad != null) {
            sb_sql.append(" AND UPPER(DS_AUTORIDAD) = UPPER(:DS_AUTORIDAD)");
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
        return "SELECT SBD_T_AUTORIDAD.NEXTVAL FROM DUAL";
    }
    
    public String getInsert()
    {
        StringBuilder sb_sql = new StringBuilder("INSERT INTO BD_T_AUTORIDAD (");
        if (AppInit.TIPO_BASEDATOS == BaseDatos.ORACLE) {
            sb_sql.append("ID_AUTORIDAD,");
        }
        sb_sql.append("CO_AUTORIDAD");
        sb_sql.append(",DS_AUTORIDAD");
        sb_sql.append(",ID_UNIDAD");
        sb_sql.append(",FE_ALTA");
        sb_sql.append(",FE_DESACTIVO");
        sb_sql.append(",USUARIOBD");
        sb_sql.append(",TSTBD");
        
        sb_sql.append(") VALUES (");
        if(AppInit.TIPO_BASEDATOS == BaseDatos.ORACLE) {
            if (this.idAutoridad == null) {
                sb_sql.append(" SBD_T_AUTORIDAD.NEXTVAL");
            } else {
                sb_sql.append(":ID_AUTORIDAD");
            }
            sb_sql.append(",").append("DECODE(:CO_AUTORIDAD, null, NULL, :CO_AUTORIDAD)");
            sb_sql.append(",").append("DECODE(:DS_AUTORIDAD, null, NULL, :DS_AUTORIDAD)");
            sb_sql.append(",").append("DECODE(:ID_UNIDAD, null, NULL, :ID_UNIDAD)");
            sb_sql.append(",").append("DECODE(:FE_ALTA, null, NULL, :FE_ALTA)");
            sb_sql.append(",").append("DECODE(:FE_DESACTIVO, null, NULL, :FE_DESACTIVO)");
            sb_sql.append(",").append("DECODE(:USUARIOBD, null, NULL, :USUARIOBD)");
            sb_sql.append(",").append("DECODE(:TSTBD, null, NULL, :TSTBD)");
        }
        else {
            sb_sql.append(":CO_AUTORIDAD");
            sb_sql.append(",").append(":DS_AUTORIDAD");
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
        StringBuilder sb_sql = new StringBuilder("UPDATE BD_T_AUTORIDAD SET ");
        
        if (AppInit.TIPO_BASEDATOS == BaseDatos.ORACLE) {
            sb_sql.append("CO_AUTORIDAD = ").append("DECODE(:CO_AUTORIDAD, null, NULL, :CO_AUTORIDAD)");
            sb_sql.append(",DS_AUTORIDAD = ").append("DECODE(:DS_AUTORIDAD, null, NULL, :DS_AUTORIDAD)");
            sb_sql.append(",ID_UNIDAD = ").append("DECODE(:ID_UNIDAD, null, NULL, :ID_UNIDAD)");
            sb_sql.append(",FE_ALTA = ").append("DECODE(:FE_ALTA, null, NULL, :FE_ALTA)");
            sb_sql.append(",FE_DESACTIVO = ").append("DECODE(:FE_DESACTIVO, null, NULL, :FE_DESACTIVO)");
            sb_sql.append(",USUARIOBD = ").append("DECODE(:USUARIOBD, null, NULL, :USUARIOBD)");
            sb_sql.append(",TSTBD = ").append("DECODE(:TSTBD, null, NULL, :TSTBD)");
        }
        else {
            sb_sql.append("CO_AUTORIDAD = :CO_AUTORIDAD");
            sb_sql.append(",DS_AUTORIDAD = :DS_AUTORIDAD");
            sb_sql.append(",ID_UNIDAD = :ID_UNIDAD");
            sb_sql.append(",FE_ALTA = :FE_ALTA");
            sb_sql.append(",FE_DESACTIVO = :FE_DESACTIVO");
            sb_sql.append(",USUARIOBD = :USUARIOBD");
            sb_sql.append(",TSTBD = :TSTBD");
        }
        sb_sql.append(" WHERE ");
        sb_sql.append("ID_AUTORIDAD = ").append(":ID_AUTORIDAD");
        
        return sb_sql.toString();
    }

    public String getDelete()
    {
        StringBuilder sb_sql = new StringBuilder("DELETE BD_T_AUTORIDAD ");
        sb_sql.append(" WHERE ");
        sb_sql.append("ID_AUTORIDAD = ").append(":ID_AUTORIDAD");
        
        return sb_sql.toString();
    }

    public Integer getIdAutoridad() {
        return idAutoridad;
    }

    public void setIdAutoridad(Integer idAutoridad) {
        this.idAutoridad = idAutoridad;
    }

    public String getCoAutoridad() {
        return coAutoridad;
    }

    public void setCoAutoridad(String coAutoridad) {
        this.coAutoridad = coAutoridad;
    }

    public String getDsAutoridad() {
        return dsAutoridad;
    }

    public void setDsAutoridad(String dsAutoridad) {
        this.dsAutoridad = dsAutoridad;
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
