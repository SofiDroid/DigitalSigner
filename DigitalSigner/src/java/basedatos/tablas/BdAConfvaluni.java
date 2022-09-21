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
public class BdAConfvaluni extends OperacionSQL implements InterfazDAO {
    
    protected Integer idConfvaluni;
    protected Integer idUnidad;
    protected Integer idConfiguracion;
    protected Integer idConfvalor;
    protected String dsValorlibre;
    protected Date feAlta;
    protected Date feDesactivo;
    protected String usuariobd;
    protected Date tstbd;

    public BdAConfvaluni() {
        // NADA
    }

    @Override
    public Object getClaseMapeada(HashMap<String,Object> bld) throws Exception {
        recuperaValorCampo(this, "idConfvaluni", "ID_CONFVALUNI", bld);
        recuperaValorCampo(this, "idUnidad", "ID_UNIDAD", bld);
        recuperaValorCampo(this, "idConfiguracion", "ID_CONFIGURACION", bld);
        recuperaValorCampo(this, "idConfvalor", "ID_CONFVALOR", bld);
        recuperaValorCampo(this, "dsValorlibre", "DS_VALORLIBRE", bld);
        recuperaValorCampo(this, "feAlta", "FE_ALTA", bld);
        recuperaValorCampo(this, "feDesactivo", "FE_DESACTIVO", bld);
        recuperaValorCampo(this, "usuariobd", "USUARIOBD", bld);
        recuperaValorCampo(this, "tstbd", "TSTBD", bld);
        
        return this; 
    }
    
    public String getSelectFiltro() {
        StringBuilder sb_sql = new StringBuilder("SELECT ");
        sb_sql.append("ID_CONFVALUNI");
        sb_sql.append(",ID_UNIDAD");
        sb_sql.append(",ID_CONFIGURACION");
        sb_sql.append(",ID_CONFVALOR");
        sb_sql.append(",DS_VALORLIBRE");
        sb_sql.append(",FE_ALTA");
        sb_sql.append(",FE_DESACTIVO");
        sb_sql.append(",USUARIOBD");
        sb_sql.append(",TSTBD");
        
        sb_sql.append(" FROM BD_A_CONFVALUNI WHERE 1=1 ");
        if (idConfvaluni != null) {
            sb_sql.append(" AND ID_CONFVALUNI = :ID_CONFVALUNI");
        }
        if (idUnidad != null) {
            sb_sql.append(" AND ID_UNIDAD = :ID_UNIDAD");
        }
        if (idConfiguracion != null) {
            sb_sql.append(" AND ID_CONFIGURACION = :ID_CONFIGURACION");
        }
        if (idConfvalor != null) {
            sb_sql.append(" AND ID_CONFVALOR = :ID_CONFVALOR");
        }
        if (dsValorlibre != null) {
            sb_sql.append(" AND UPPER(DS_VALORLIBRE) = UPPER(:DS_VALORLIBRE)");
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

    public String getSelectFiltroJerarquia() {
        StringBuilder sb_sql = new StringBuilder("");
        String jerarquia = """
                           WITH CTE_UNIDADES 
                           AS
                           (
                               SELECT
                                   1 as LEVEL, UNI.*
                               FROM
                                   BD_T_UNIDAD UNI
                               WHERE 
                                   UNI.ID_UNIDAD = :ID_UNIDAD
                           UNION ALL
                               SELECT
                                   LEVEL + 1 as LEVEL, UNI.*
                               FROM 
                                   BD_T_UNIDAD UNI
                               INNER JOIN 
                                   CTE_UNIDADES cte ON (UNI.ID_UNIDAD = cte.ID_UNIDADPADRE)
                           )
                           """;
  
        sb_sql.append(jerarquia);
        sb_sql.append("SELECT ");
        sb_sql.append("CONFVALUNI.ID_CONFVALUNI");
        sb_sql.append(",CONFVALUNI.ID_UNIDAD");
        sb_sql.append(",CONFVALUNI.ID_CONFIGURACION");
        sb_sql.append(",CONFVALUNI.ID_CONFVALOR");
        sb_sql.append(",CONFVALUNI.DS_VALORLIBRE");
        sb_sql.append(",CONFVALUNI.FE_ALTA");
        sb_sql.append(",CONFVALUNI.FE_DESACTIVO");
        sb_sql.append(",CONFVALUNI.USUARIOBD");
        sb_sql.append(",CONFVALUNI.TSTBD");

        sb_sql.append(" FROM CTE_UNIDADES");
        sb_sql.append(" INNER JOIN");
        sb_sql.append(" BD_A_CONFVALUNI CONFVALUNI ON (CONFVALUNI.ID_UNIDAD = CTE_UNIDADES.ID_UNIDAD)");
        
        sb_sql.append(" WHERE 1=1 ");
        if (idConfvaluni != null) {
            sb_sql.append(" AND CONFVALUNI.ID_CONFVALUNI = :ID_CONFVALUNI");
        }
        if (idUnidad != null) {
            sb_sql.append(" AND CONFVALUNI.ID_UNIDAD = :ID_UNIDAD");
        }
        if (idConfiguracion != null) {
            sb_sql.append(" AND CONFVALUNI.ID_CONFIGURACION = :ID_CONFIGURACION");
        }
        if (idConfvalor != null) {
            sb_sql.append(" AND CONFVALUNI.ID_CONFVALOR = :ID_CONFVALOR");
        }
        if (dsValorlibre != null) {
            sb_sql.append(" AND UPPER(CONFVALUNI.DS_VALORLIBRE) = UPPER(:DS_VALORLIBRE)");
        }
        if (feAlta != null) {
            sb_sql.append(" AND (CONFVALUNI.FE_ALTA <= :FE_ALTA)");
        }
        if (feDesactivo != null) {
            sb_sql.append(" AND (CONFVALUNI.FE_DESACTIVO IS NULL OR FE_DESACTIVO >= :FE_DESACTIVO)");
        }
        if (usuariobd != null) {
            sb_sql.append(" AND CONFVALUNI.USUARIOBD = :USUARIOBD");
        }
        if (tstbd != null) {
            sb_sql.append(" AND CONFVALUNI.TSTBD = :TSTBD");
        }
        sb_sql.append(" ORDER BY CTE_UNIDADES.LEVEL ASC");
        
        return sb_sql.toString();
    }

    public String getSecuencia()
    {
        return "SELECT SBD_A_CONFVALUNI.NEXTVAL FROM DUAL";
    }
    
    public String getInsert()
    {
        StringBuilder sb_sql = new StringBuilder("INSERT INTO BD_A_CONFVALUNI (");
        if (AppInit.TIPO_BASEDATOS == BaseDatos.ORACLE) {
            sb_sql.append("ID_CONFVALUNI,");
        }
        sb_sql.append("ID_UNIDAD");
        sb_sql.append(",ID_CONFIGURACION");
        sb_sql.append(",ID_CONFVALOR");
        sb_sql.append(",DS_VALORLIBRE");
        sb_sql.append(",FE_ALTA");
        sb_sql.append(",FE_DESACTIVO");
        sb_sql.append(",USUARIOBD");
        sb_sql.append(",TSTBD");
        
        sb_sql.append(") VALUES (");
        if(AppInit.TIPO_BASEDATOS == BaseDatos.ORACLE) {
            if (this.idConfvaluni == null) {
                sb_sql.append(" SBD_A_CONFVALUNI.NEXTVAL");
            } else {
                sb_sql.append(":ID_CONFVALUNI");
            }
            sb_sql.append(",").append("DECODE(:ID_UNIDAD, null, NULL, :ID_UNIDAD)");
            sb_sql.append(",").append("DECODE(:ID_CONFIGURACION, null, NULL, :ID_CONFIGURACION)");
            sb_sql.append(",").append("DECODE(:ID_CONFVALOR, null, NULL, :ID_CONFVALOR)");
            sb_sql.append(",").append("DECODE(:DS_VALORLIBRE, null, NULL, :DS_VALORLIBRE)");
            sb_sql.append(",").append("DECODE(:FE_ALTA, null, NULL, :FE_ALTA)");
            sb_sql.append(",").append("DECODE(:FE_DESACTIVO, null, NULL, :FE_DESACTIVO)");
            sb_sql.append(",").append("DECODE(:USUARIOBD, null, NULL, :USUARIOBD)");
            sb_sql.append(",").append("DECODE(:TSTBD, null, NULL, :TSTBD)");
        }
        else {
            sb_sql.append(":ID_UNIDAD");
            sb_sql.append(",").append(":ID_CONFIGURACION");
            sb_sql.append(",").append(":ID_CONFVALOR");
            sb_sql.append(",").append(":DS_VALORLIBRE");
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
        StringBuilder sb_sql = new StringBuilder("UPDATE BD_A_CONFVALUNI SET ");
        
        if (AppInit.TIPO_BASEDATOS == BaseDatos.ORACLE) {
            sb_sql.append("ID_UNIDAD = ").append("DECODE(:ID_UNIDAD, null, NULL, :ID_UNIDAD)");
            sb_sql.append(",ID_CONFIGURACION = ").append("DECODE(:ID_CONFIGURACION, null, NULL, :ID_CONFIGURACION)");
            sb_sql.append(",ID_CONFVALOR = ").append("DECODE(:ID_CONFVALOR, null, NULL, :ID_CONFVALOR)");
            sb_sql.append(",DS_VALORLIBRE = ").append("DECODE(:DS_VALORLIBRE, null, NULL, :DS_VALORLIBRE)");
            sb_sql.append(",FE_ALTA = ").append("DECODE(:FE_ALTA, null, NULL, :FE_ALTA)");
            sb_sql.append(",FE_DESACTIVO = ").append("DECODE(:FE_DESACTIVO, null, NULL, :FE_DESACTIVO)");
            sb_sql.append(",USUARIOBD = ").append("DECODE(:USUARIOBD, null, NULL, :USUARIOBD)");
            sb_sql.append(",TSTBD = ").append("DECODE(:TSTBD, null, NULL, :TSTBD)");
        }
        else {
            sb_sql.append("ID_UNIDAD = :ID_UNIDAD");
            sb_sql.append(",ID_CONFIGURACION = :ID_CONFIGURACION");
            sb_sql.append(",ID_CONFVALOR = :ID_CONFVALOR");
            sb_sql.append(",DS_VALORLIBRE = :DS_VALORLIBRE");
            sb_sql.append(",FE_ALTA = :FE_ALTA");
            sb_sql.append(",FE_DESACTIVO = :FE_DESACTIVO");
            sb_sql.append(",USUARIOBD = :USUARIOBD");
            sb_sql.append(",TSTBD = :TSTBD");
        }
        sb_sql.append(" WHERE ");
        sb_sql.append("ID_CONFVALUNI = ").append(":ID_CONFVALUNI");
        
        return sb_sql.toString();
    }

    public String getDelete()
    {
        StringBuilder sb_sql = new StringBuilder("DELETE BD_A_CONFVALUNI ");
        sb_sql.append(" WHERE ");
        sb_sql.append("ID_CONFVALUNI = ").append(":ID_CONFVALUNI");
        
        return sb_sql.toString();
    }

    public Integer getIdConfvaluni() {
        return idConfvaluni;
    }

    public void setIdConfvaluni(Integer idConfvaluni) {
        this.idConfvaluni = idConfvaluni;
    }

    public Integer getIdUnidad() {
        return idUnidad;
    }

    public void setIdUnidad(Integer idUnidad) {
        this.idUnidad = idUnidad;
    }

    public Integer getIdConfiguracion() {
        return idConfiguracion;
    }

    public void setIdConfiguracion(Integer idConfiguracion) {
        this.idConfiguracion = idConfiguracion;
    }

    public Integer getIdConfvalor() {
        return idConfvalor;
    }

    public void setIdConfvalor(Integer idConfvalor) {
        this.idConfvalor = idConfvalor;
    }

    public String getDsValorlibre() {
        return dsValorlibre;
    }

    public void setDsValorlibre(String dsValorlibre) {
        this.dsValorlibre = dsValorlibre;
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
