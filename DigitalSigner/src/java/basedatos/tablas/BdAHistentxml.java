package basedatos.tablas;

import basedatos.InterfazDAO;
import basedatos.OperacionSQL;
import java.util.Date;
import java.util.HashMap;

/**
 *
 * @author ihuegal
 */
public class BdAHistentxml extends OperacionSQL implements InterfazDAO {
    
    protected Integer idHistentxml;
    protected Integer idEntradaxml;
    protected Integer idSituacionxml;
    protected Date feAlta;
    protected Date feDesactivo;
    protected String usuariobd;
    protected Date tstbd;

    public BdAHistentxml() {
        // NADA
    }

    @Override
    public Object getClaseMapeada(HashMap<String,Object> bld) throws Exception {
        recuperaValorCampo(this, "idHistentxml", "ID_HISTENTXML", bld);
        recuperaValorCampo(this, "idEntradaxml", "ID_ENTRADAXML", bld);
        recuperaValorCampo(this, "idSituacionxml", "ID_SITUACIONXML", bld);
        recuperaValorCampo(this, "feAlta", "FE_ALTA", bld);
        recuperaValorCampo(this, "feDesactivo", "FE_DESACTIVO", bld);
        recuperaValorCampo(this, "usuariobd", "USUARIOBD", bld);
        recuperaValorCampo(this, "tstbd", "TSTBD", bld);
        
        return this; 
    }
    
    public String getSelectFiltro() {
        StringBuilder sb_sql = new StringBuilder("SELECT ");
        sb_sql.append("ID_HISTENTXML");
        sb_sql.append(",ID_ENTRADAXML");
        sb_sql.append(",ID_SITUACIONXML");
        sb_sql.append(",FE_ALTA");
        sb_sql.append(",FE_DESACTIVO");
        sb_sql.append(",USUARIOBD");
        sb_sql.append(",TSTBD");
        
        sb_sql.append(" FROM BD_A_HISTENTXML WHERE 1=1 ");
        if (idHistentxml != null) {
            sb_sql.append(" AND ID_HISTENTXML = :ID_HISTSALXML");
        }
        if (idEntradaxml != null) {
            sb_sql.append(" AND ID_ENTRADAXML = :ID_ENTRADAXML");
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

    public Integer getIdHistentxml() {
        return idHistentxml;
    }

    public void setIdHistentxml(Integer idHistentxml) {
        this.idHistentxml = idHistentxml;
    }

    public Integer getIdEntradaxml() {
        return idEntradaxml;
    }

    public void setIdEntradaxml(Integer idEntradaxml) {
        this.idEntradaxml = idEntradaxml;
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
