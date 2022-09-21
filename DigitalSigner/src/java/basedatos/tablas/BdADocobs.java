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

    public String getSecuencia()
    {
        return "SELECT SBD_A_DOCOBS.NEXTVAL FROM DUAL";
    }
    
    public String getInsert()
    {
        StringBuilder sb_sql = new StringBuilder("INSERT INTO BD_A_DOCOBS (");
        if (AppInit.TIPO_BASEDATOS == BaseDatos.ORACLE) {
            sb_sql.append("ID_DOCOBS,");
        }
        sb_sql.append("DS_OBSERVACIONES");
        sb_sql.append(",ID_DOCUMENTO");
        sb_sql.append(",ID_USUARIO");
        sb_sql.append(",ID_AUTORIDAD");
        sb_sql.append(",FE_ALTA");
        sb_sql.append(",FE_DESACTIVO");
        sb_sql.append(",USUARIOBD");
        sb_sql.append(",TSTBD");
        
        sb_sql.append(") VALUES (");
        if(AppInit.TIPO_BASEDATOS == BaseDatos.ORACLE) {
            if (this.idDocobs == null) {
                sb_sql.append(" SBD_A_DOCOBS.NEXTVAL");
            } else {
                sb_sql.append(":ID_DOCOBS");
            }
            sb_sql.append(",").append("DECODE(:DS_OBSERVACIONES, null, NULL, :DS_OBSERVACIONES)");
            sb_sql.append(",").append("DECODE(:ID_DOCUMENTO, null, NULL, :ID_DOCUMENTO)");
            sb_sql.append(",").append("DECODE(:ID_USUARIO, null, NULL, :ID_USUARIO)");
            sb_sql.append(",").append("DECODE(:ID_AUTORIDAD, null, NULL, :ID_AUTORIDAD)");
            sb_sql.append(",").append("DECODE(:FE_ALTA, null, NULL, :FE_ALTA)");
            sb_sql.append(",").append("DECODE(:FE_DESACTIVO, null, NULL, :FE_DESACTIVO)");
            sb_sql.append(",").append("DECODE(:USUARIOBD, null, NULL, :USUARIOBD)");
            sb_sql.append(",").append("DECODE(:TSTBD, null, NULL, :TSTBD)");
        }
        else {
            sb_sql.append(":ID_DOCOBS");
            sb_sql.append(",").append(":DS_OBSERVACIONES");
            sb_sql.append(",").append(":ID_DOCUMENTO");
            sb_sql.append(",").append(":ID_USUARIO");
            sb_sql.append(",").append(":ID_AUTORIDAD");
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
        StringBuilder sb_sql = new StringBuilder("UPDATE BD_A_DOCOBS SET ");
        
        if (AppInit.TIPO_BASEDATOS == BaseDatos.ORACLE) {
            sb_sql.append("DS_OBSERVACIONES = ").append("DECODE(:DS_OBSERVACIONES, null, NULL, :DS_OBSERVACIONES)");
            sb_sql.append(",ID_DOCUMENTO = ").append("DECODE(:ID_DOCUMENTO, null, NULL, :ID_DOCUMENTO)");
            sb_sql.append(",ID_USUARIO = ").append("DECODE(:ID_USUARIO, null, NULL, :ID_USUARIO)");
            sb_sql.append(",ID_AUTORIDAD = ").append("DECODE(:ID_AUTORIDAD, null, NULL, :ID_AUTORIDAD)");
            sb_sql.append(",FE_ALTA = ").append("DECODE(:FE_ALTA, null, NULL, :FE_ALTA)");
            sb_sql.append(",FE_DESACTIVO = ").append("DECODE(:FE_DESACTIVO, null, NULL, :FE_DESACTIVO)");
            sb_sql.append(",USUARIOBD = ").append("DECODE(:USUARIOBD, null, NULL, :USUARIOBD)");
            sb_sql.append(",TSTBD = ").append("DECODE(:TSTBD, null, NULL, :TSTBD)");
        }
        else {
            sb_sql.append("DS_OBSERVACIONES = :DS_OBSERVACIONES");
            sb_sql.append(",ID_DOCUMENTO = :ID_DOCUMENTO");
            sb_sql.append(",ID_USUARIO = :ID_USUARIO");
            sb_sql.append(",ID_AUTORIDAD = :ID_AUTORIDAD");
            sb_sql.append(",FE_ALTA = :FE_ALTA");
            sb_sql.append(",FE_DESACTIVO = :FE_DESACTIVO");
            sb_sql.append(",USUARIOBD = :USUARIOBD");
            sb_sql.append(",TSTBD = :TSTBD");
        }
        sb_sql.append(" WHERE ");
        sb_sql.append("ID_DOCOBS = ").append(":ID_DOCOBS");
        
        return sb_sql.toString();
    }

    public String getDelete()
    {
        StringBuilder sb_sql = new StringBuilder("DELETE BD_A_DOCOBS ");
        sb_sql.append(" WHERE ");
        sb_sql.append("ID_DOCOBS = ").append(":ID_DOCOBS");
        
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
