package basedatos.tablas;

import basedatos.InterfazDAO;
import basedatos.OperacionSQL;
import java.util.Date;
import java.util.HashMap;

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
