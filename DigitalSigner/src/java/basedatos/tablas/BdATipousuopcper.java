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
public class BdATipousuopcper extends OperacionSQL implements InterfazDAO {
    
    protected Integer idTipousuario;
    protected Integer idOpcionmenu;
    protected Boolean boConsulta;
    protected Boolean boAlta;
    protected Boolean boEdicion;
    protected Boolean boBorrado;
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
        recuperaValorCampo(this, "boConsulta", "BO_CONSULTA", bld);
        recuperaValorCampo(this, "boAlta", "BO_ALTA", bld);
        recuperaValorCampo(this, "boEdicion", "BO_EDICION", bld);
        recuperaValorCampo(this, "boBorrado", "BO_BORRADO", bld);
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
        sb_sql.append(",BO_CONSULTA");
        sb_sql.append(",BO_ALTA");
        sb_sql.append(",BO_EDICION");
        sb_sql.append(",BO_BORRADO");
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
        if (boConsulta != null) {
            sb_sql.append(" AND BO_CONSULTA = :BO_CONSULTA");
        }
        if (boAlta != null) {
            sb_sql.append(" AND BO_ALTA = :BO_ALTA");
        }
        if (boEdicion != null) {
            sb_sql.append(" AND BO_EDICION = :BO_EDICION");
        }
        if (boBorrado != null) {
            sb_sql.append(" AND BO_BORRADO = :BO_BORRADO");
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

    public String getInsert()
    {
        StringBuilder sb_sql = new StringBuilder("INSERT INTO BD_A_TIPOUSUOPCPER (");

        sb_sql.append("ID_TIPOUSUARIO");
        sb_sql.append(",ID_OPCIONMENU");
        sb_sql.append(",BO_CONSULTA");
        sb_sql.append(",BO_ALTA");
        sb_sql.append(",BO_EDICION");
        sb_sql.append(",BO_BORRADO");
        sb_sql.append(",FE_ALTA");
        sb_sql.append(",FE_DESACTIVO");
        sb_sql.append(",USUARIOBD");
        sb_sql.append(",TSTBD");
        
        sb_sql.append(") VALUES (");
        if(AppInit.TIPO_BASEDATOS == BaseDatos.ORACLE) {
            sb_sql.append(":ID_TIPOUSUARIO");
            sb_sql.append(",").append(":ID_OPCIONMENU");
            sb_sql.append(",").append("DECODE(:BO_CONSULTA, null, NULL, :BO_CONSULTA)");
            sb_sql.append(",").append("DECODE(:BO_ALTA, null, NULL, :BO_ALTA)");
            sb_sql.append(",").append("DECODE(:BO_EDICION, null, NULL, :BO_EDICION)");
            sb_sql.append(",").append("DECODE(:BO_BORRADO, null, NULL, :BO_BORRADO)");
            sb_sql.append(",").append("DECODE(:FE_ALTA, null, NULL, :FE_ALTA)");
            sb_sql.append(",").append("DECODE(:FE_DESACTIVO, null, NULL, :FE_DESACTIVO)");
            sb_sql.append(",").append("DECODE(:USUARIOBD, null, NULL, :USUARIOBD)");
            sb_sql.append(",").append("DECODE(:TSTBD, null, NULL, :TSTBD)");
        }
        else {
            sb_sql.append(":ID_TIPOUSUARIO");
            sb_sql.append(",").append(":ID_OPCIONMENU");
            sb_sql.append(",").append(":BO_CONSULTA");
            sb_sql.append(",").append(":BO_ALTA");
            sb_sql.append(",").append(":BO_EDICION");
            sb_sql.append(",").append(":BO_BORRADO");
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
        StringBuilder sb_sql = new StringBuilder("UPDATE BD_A_TIPOUSUOPCPER SET ");
        
        if (AppInit.TIPO_BASEDATOS == BaseDatos.ORACLE) {
            sb_sql.append("BO_CONSULTA = ").append("DECODE(:BO_CONSULTA, null, NULL, :BO_CONSULTA)");
            sb_sql.append(",BO_ALTA = ").append("DECODE(:BO_ALTA, null, NULL, :BO_ALTA)");
            sb_sql.append(",BO_EDICION = ").append("DECODE(:BO_EDICION, null, NULL, :BO_EDICION)");
            sb_sql.append(",BO_BORRADO = ").append("DECODE(:BO_BORRADO, null, NULL, :BO_BORRADO)");
            sb_sql.append(",FE_ALTA = ").append("DECODE(:FE_ALTA, null, NULL, :FE_ALTA)");
            sb_sql.append(",FE_DESACTIVO = ").append("DECODE(:FE_DESACTIVO, null, NULL, :FE_DESACTIVO)");
            sb_sql.append(",USUARIOBD = ").append("DECODE(:USUARIOBD, null, NULL, :USUARIOBD)");
            sb_sql.append(",TSTBD = ").append("DECODE(:TSTBD, null, NULL, :TSTBD)");
        }
        else {
            sb_sql.append("BO_CONSULTA = :BO_CONSULTA");
            sb_sql.append(",BO_ALTA = :BO_ALTA");
            sb_sql.append(",BO_EDICION = :BO_EDICION");
            sb_sql.append(",BO_BORRADO = :BO_BORRADO");
            sb_sql.append(",FE_ALTA = :FE_ALTA");
            sb_sql.append(",FE_DESACTIVO = :FE_DESACTIVO");
            sb_sql.append(",USUARIOBD = :USUARIOBD");
            sb_sql.append(",TSTBD = :TSTBD");
        }
        sb_sql.append(" WHERE ");
        sb_sql.append("ID_TIPOUSUARIO = ").append(":ID_TIPOUSUARIO");
        sb_sql.append(" AND ID_OPCIONMENU = ").append(":ID_OPCIONMENU");
        
        return sb_sql.toString();
    }

    public String getDelete()
    {
        StringBuilder sb_sql = new StringBuilder("DELETE BD_A_TIPOUSUOPCPER ");
        sb_sql.append(" WHERE ");
        sb_sql.append("ID_TIPOUSUARIO = ").append(":ID_TIPOUSUARIO");
        sb_sql.append(" AND ID_OPCIONMENU = ").append(":ID_OPCIONMENU");
        
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

    public Boolean getBoConsulta() {
        return boConsulta;
    }

    public void setBoConsulta(Boolean boConsulta) {
        this.boConsulta = boConsulta;
    }

    public Boolean getBoAlta() {
        return boAlta;
    }

    public void setBoAlta(Boolean boAlta) {
        this.boAlta = boAlta;
    }

    public Boolean getBoEdicion() {
        return boEdicion;
    }

    public void setBoEdicion(Boolean boEdicion) {
        this.boEdicion = boEdicion;
    }

    public Boolean getBoBorrado() {
        return boBorrado;
    }

    public void setBoBorrado(Boolean boBorrado) {
        this.boBorrado = boBorrado;
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
