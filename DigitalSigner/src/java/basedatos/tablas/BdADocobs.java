package basedatos.tablas;

import basedatos.InterfazDAO;
import basedatos.OperacionSQL;
import java.util.Date;
import java.util.HashMap;

/**
 *
 * @author ihuegal
 */
public class BdADocobs extends OperacionSQL implements InterfazDAO {
    
    protected Integer idDocobs;
    protected String dsObservaciones;
    protected Integer idDocumento;
    protected Integer idUsuario;
    protected Integer idAutoridad;
    protected Date feAlta;
    protected Date feDesactivo;
    protected String usuariobd;
    protected Date tstbd;

    public BdADocobs() {
        // NADA
    }

    @Override
    public Object getClaseMapeada(HashMap<String,Object> bld) throws Exception {
        recuperaValorCampo(this, "idDocobs", "ID_DOCOBS", bld);
        recuperaValorCampo(this, "dsObservaciones", "DS_OBSERVACIONES", bld);
        recuperaValorCampo(this, "idDocumento", "ID_DOCUMENTO", bld);
        recuperaValorCampo(this, "idUsuario", "ID_USUARIO", bld);
        recuperaValorCampo(this, "idAutoridad", "ID_AUTORIDAD", bld);
        recuperaValorCampo(this, "feAlta", "FE_ALTA", bld);
        recuperaValorCampo(this, "feDesactivo", "FE_DESACTIVO", bld);
        recuperaValorCampo(this, "usuariobd", "USUARIOBD", bld);
        recuperaValorCampo(this, "tstbd", "TSTBD", bld);
        
        return this; 
    }
    
    public String getSelectFiltro() {
        StringBuilder sb_sql = new StringBuilder("SELECT ");
        sb_sql.append("ID_DOCRECHAZO");
        sb_sql.append(",DS_OBSERVACIONES");
        sb_sql.append(",ID_DOCUMENTO");
        sb_sql.append(",ID_USUARIO");
        sb_sql.append(",ID_AUTORIDAD");
        sb_sql.append(",FE_ALTA");
        sb_sql.append(",FE_DESACTIVO");
        sb_sql.append(",USUARIOBD");
        sb_sql.append(",TSTBD");
        
        sb_sql.append(" FROM BD_A_DOCOBS WHERE 1=1 ");
        if (idDocobs != null) {
            sb_sql.append(" AND ID_DOCOBS = :ID_DOCOBS");
        }
        if (dsObservaciones != null) {
            sb_sql.append(" AND UPPER(DS_OBSERVACIONES) = UPPER(:DS_OBSERVACIONES)");
        }
        if (idDocumento != null) {
            sb_sql.append(" AND ID_DOCUMENTO = :ID_DOCUMENTO");
        }
        if (idUsuario != null) {
            sb_sql.append(" AND ID_USUARIO = :ID_USUARIO");
        }
        if (idAutoridad != null) {
            sb_sql.append(" AND ID_AUTORIDAD = :ID_AUTORIDAD");
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

    public Integer getIdDocobs() {
        return idDocobs;
    }

    public void setIdDocobs(Integer idDocobs) {
        this.idDocobs = idDocobs;
    }

    public String getDsObservaciones() {
        return dsObservaciones;
    }

    public void setDsObservaciones(String dsObservaciones) {
        this.dsObservaciones = dsObservaciones;
    }

    public Integer getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(Integer idDocumento) {
        this.idDocumento = idDocumento;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getIdAutoridad() {
        return idAutoridad;
    }

    public void setIdAutoridad(Integer idAutoridad) {
        this.idAutoridad = idAutoridad;
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
