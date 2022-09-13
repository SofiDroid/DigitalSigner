package basedatos.tablas;

import basedatos.InterfazDAO;
import basedatos.OperacionSQL;
import java.util.Date;
import java.util.HashMap;

/**
 *
 * @author ihuegal
 */
public class BdATipousuopcper extends OperacionSQL implements InterfazDAO {
    
    protected Integer idTipousuario;
    protected Integer idOpcionmenu;
    protected Integer idPermiso;
    protected Date feAlta;
    protected Date feDesactivo;
    protected String usuariobd;
    protected Date tstbd;

    public BdATipousuopcper() {
        // NADA
    }

    @Override
    public Object getClaseMapeada(HashMap<String,Object> bld) throws Exception {
        recuperaValorCampo(this, "idTipousuario", "ID_TIPOUSUARIO", bld);
        recuperaValorCampo(this, "idOpcionmenu", "ID_OPCIONMENU", bld);
        recuperaValorCampo(this, "idPermiso", "ID_PERMISO", bld);
        recuperaValorCampo(this, "feAlta", "FE_ALTA", bld);
        recuperaValorCampo(this, "feDesactivo", "FE_DESACTIVO", bld);
        recuperaValorCampo(this, "usuariobd", "USUARIOBD", bld);
        recuperaValorCampo(this, "tstbd", "TSTBD", bld);
        
        return this; 
    }
    
    public String getSelectFiltro() {
        StringBuilder sb_sql = new StringBuilder("SELECT ");
        sb_sql.append("ID_TIPOUSUARIO");
        sb_sql.append(",ID_OPCIONMENU");
        sb_sql.append(",ID_PERMISO");
        sb_sql.append(",FE_ALTA");
        sb_sql.append(",FE_DESACTIVO");
        sb_sql.append(",USUARIOBD");
        sb_sql.append(",TSTBD");
        
        sb_sql.append(" FROM BD_A_TIPOUSUOPCPER WHERE 1=1 ");
        if (idTipousuario != null) {
            sb_sql.append(" AND ID_TIPOUSUARIO = :ID_TIPOUSUARIO");
        }
        if (idOpcionmenu != null) {
            sb_sql.append(" AND ID_OPCIONMENU = :ID_OPCIONMENU");
        }
        if (idPermiso != null) {
            sb_sql.append(" AND ID_PERMISO = :ID_PERMISO");
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

    public Integer getIdTipousuario() {
        return idTipousuario;
    }

    public void setIdTipousuario(Integer idTipousuario) {
        this.idTipousuario = idTipousuario;
    }

    public Integer getIdOpcionmenu() {
        return idOpcionmenu;
    }

    public void setIdOpcionmenu(Integer idOpcionmenu) {
        this.idOpcionmenu = idOpcionmenu;
    }

    public Integer getIdPermiso() {
        return idPermiso;
    }

    public void setIdPermiso(Integer idPermiso) {
        this.idPermiso = idPermiso;
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
