package basedatos.tablas;

import basedatos.InterfazDAO;
import basedatos.OperacionSQL;
import java.util.Date;
import java.util.HashMap;

/**
 *
 * @author ihuegal
 */
public class BdADocrechazo extends OperacionSQL implements InterfazDAO {
    
    protected Integer idDocrechazo;
    protected Integer idDocumento;
    protected String dsObservaciones;
    protected Date feAlta;
    protected Date feDesactivo;
    protected String usuariobd;
    protected Date tstbd;

    public BdADocrechazo() {
        // NADA
    }

    @Override
    public Object getClaseMapeada(HashMap<String,Object> bld) throws Exception {
        recuperaValorCampo(this, "idDocrechazo", "ID_DOCRECHAZO", bld);
        recuperaValorCampo(this, "idDocumento", "ID_DOCUMENTO", bld);
        recuperaValorCampo(this, "dsObservaciones", "DS_OBSERVACIONES", bld);
        recuperaValorCampo(this, "feAlta", "FE_ALTA", bld);
        recuperaValorCampo(this, "feDesactivo", "FE_DESACTIVO", bld);
        recuperaValorCampo(this, "usuariobd", "USUARIOBD", bld);
        recuperaValorCampo(this, "tstbd", "TSTBD", bld);
        
        return this; 
    }
    
    public String getSelectFiltro() {
        StringBuilder sb_sql = new StringBuilder("SELECT ");
        sb_sql.append("ID_DOCRECHAZO");
        sb_sql.append(",ID_DOCUMENTO");
        sb_sql.append(",DS_OBSERVACIONES");
        sb_sql.append(",FE_ALTA");
        sb_sql.append(",FE_DESACTIVO");
        sb_sql.append(",USUARIOBD");
        sb_sql.append(",TSTBD");
        
        sb_sql.append(" FROM BD_A_DOCRECHAZO WHERE 1=1 ");
        if (idDocrechazo != null) {
            sb_sql.append(" AND ID_DOCRECHAZO = :ID_DOCRECHAZO");
        }
        if (idDocumento != null) {
            sb_sql.append(" AND ID_DOCUMENTO = :ID_DOCUMENTO");
        }
        if (dsObservaciones != null) {
            sb_sql.append(" AND UPPER(DS_OBSERVACIONES) = UPPER(:DS_OBSERVACIONES)");
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

    public Integer getIdDocrechazo() {
        return idDocrechazo;
    }

    public void setIdDocrechazo(Integer idDocrechazo) {
        this.idDocrechazo = idDocrechazo;
    }

    public Integer getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(Integer idDocumento) {
        this.idDocumento = idDocumento;
    }

    public String getDsObservaciones() {
        return dsObservaciones;
    }

    public void setDsObservaciones(String dsObservaciones) {
        this.dsObservaciones = dsObservaciones;
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
