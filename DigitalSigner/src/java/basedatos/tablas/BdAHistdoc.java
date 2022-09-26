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
public class BdAHistdoc extends OperacionSQL implements InterfazDAO {
    
    protected Integer idHistdoc;
    protected Integer idDocumento;
    protected Integer idSituaciondoc;
    protected byte[] blDocumento;
    protected String dsRuta;
    protected Date feAlta;
    protected Date feDesactivo;
    protected String usuariobd;
    protected Date tstbd;

    public BdAHistdoc() {
        // NADA
    }

    @Override
    public Object getClaseMapeada(HashMap<String,Object> bld) throws Exception {
        recuperaValorCampo(this, "idHistdoc", "ID_HISTDOC", bld);
        recuperaValorCampo(this, "idDocumento", "ID_DOCUMENTO", bld);
        recuperaValorCampo(this, "idSituaciondoc", "ID_SITUACIONDOC", bld);
        recuperaValorCampo(this, "blDocumento", "BL_DOCUMENTO", bld);
        recuperaValorCampo(this, "dsRuta", "DS_RUTA", bld);
        recuperaValorCampo(this, "feAlta", "FE_ALTA", bld);
        recuperaValorCampo(this, "feDesactivo", "FE_DESACTIVO", bld);
        recuperaValorCampo(this, "usuariobd", "USUARIOBD", bld);
        recuperaValorCampo(this, "tstbd", "TSTBD", bld);
        
        return this; 
    }
    
    public String getSelectFiltro() {
        StringBuilder sb_sql = new StringBuilder("SELECT ");
        sb_sql.append("ID_HISTDOC");
        sb_sql.append(",ID_DOCUMENTO");
        sb_sql.append(",ID_SITUACIONDOC");
        sb_sql.append(",BL_DOCUMENTO");
        sb_sql.append(",DS_RUTA");
        sb_sql.append(",FE_ALTA");
        sb_sql.append(",FE_DESACTIVO");
        sb_sql.append(",USUARIOBD");
        sb_sql.append(",TSTBD");
        
        sb_sql.append(" FROM BD_D_DOCUMENTO WHERE 1=1 ");
        if (idHistdoc != null) {
            sb_sql.append(" AND ID_HISTDOC = :ID_HISTDOC");
        }
        if (idDocumento != null) {
            sb_sql.append(" AND ID_DOCUMENTO = :ID_DOCUMENTO");
        }
        if (idSituaciondoc != null) {
            sb_sql.append(" AND ID_SITUACIONDOC = UPPER(:ID_SITUACIONDOC)");
        }
        if (dsRuta != null) {
            sb_sql.append(" AND UPPER(DS_RUTA) = UPPER(:DS_RUTA)");
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
        return "SELECT SBD_A_HISTDOC.NEXTVAL FROM DUAL";
    }
    
    public String getInsert()
    {
        StringBuilder sb_sql = new StringBuilder("INSERT INTO BD_A_HISTDOC (");
        if (AppInit.TIPO_BASEDATOS == BaseDatos.ORACLE) {
            sb_sql.append("ID_HISTDOC,");
        }
        sb_sql.append("ID_DOCUMENTO");
        sb_sql.append(",ID_SITUACIONDOC");
        sb_sql.append(",BL_DOCUMENTO");
        sb_sql.append(",DS_RUTA");
        sb_sql.append(",FE_ALTA");
        sb_sql.append(",FE_DESACTIVO");
        sb_sql.append(",USUARIOBD");
        sb_sql.append(",TSTBD");
        
        sb_sql.append(") VALUES (");
        if(AppInit.TIPO_BASEDATOS == BaseDatos.ORACLE) {
            if (this.idHistdoc == null) {
                sb_sql.append(" SBD_A_HISTDOC.NEXTVAL");
            } else {
                sb_sql.append(":ID_HISTDOC");
            }
            sb_sql.append(",").append("DECODE(:ID_DOCUMENTO, null, NULL, :ID_DOCUMENTO)");
            sb_sql.append(",").append("DECODE(:ID_SITUACIONDOC, null, NULL, :ID_SITUACIONDOC)");
            sb_sql.append(",").append("DECODE(:BL_DOCUMENTO, null, NULL, :BL_DOCUMENTO)");
            sb_sql.append(",").append("DECODE(:DS_RUTA, null, NULL, :DS_RUTA)");
            sb_sql.append(",").append("DECODE(:FE_ALTA, null, NULL, :FE_ALTA)");
            sb_sql.append(",").append("DECODE(:FE_DESACTIVO, null, NULL, :FE_DESACTIVO)");
            sb_sql.append(",").append("DECODE(:USUARIOBD, null, NULL, :USUARIOBD)");
            sb_sql.append(",").append("DECODE(:TSTBD, null, NULL, :TSTBD)");
        }
        else {
            sb_sql.append(":ID_DOCUMENTO");
            sb_sql.append(",").append(":ID_SITUACIONDOC");
            sb_sql.append(",").append(":BL_DOCUMENTO");
            sb_sql.append(",").append(":DS_RUTA");
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
        StringBuilder sb_sql = new StringBuilder("UPDATE BD_A_HISTDOC SET ");
        
        if (AppInit.TIPO_BASEDATOS == BaseDatos.ORACLE) {
            sb_sql.append("ID_DOCUMENTO = ").append("DECODE(:ID_DOCUMENTO, null, NULL, :ID_DOCUMENTO)");
            sb_sql.append(",ID_SITUACIONDOC = ").append("DECODE(:ID_SITUACIONDOC, null, NULL, :ID_SITUACIONDOC)");
            sb_sql.append(",BL_DOCUMENTO = ").append("DECODE(:BL_DOCUMENTO, null, NULL, :BL_DOCUMENTO)");
            sb_sql.append(",DS_RUTA = ").append("DECODE(:DS_RUTA, null, NULL, :DS_RUTA)");
            sb_sql.append(",FE_ALTA = ").append("DECODE(:FE_ALTA, null, NULL, :FE_ALTA)");
            sb_sql.append(",FE_DESACTIVO = ").append("DECODE(:FE_DESACTIVO, null, NULL, :FE_DESACTIVO)");
            sb_sql.append(",USUARIOBD = ").append("DECODE(:USUARIOBD, null, NULL, :USUARIOBD)");
            sb_sql.append(",TSTBD = ").append("DECODE(:TSTBD, null, NULL, :TSTBD)");
        }
        else {
            sb_sql.append("ID_DOCUMENTO = :ID_DOCUMENTO");
            sb_sql.append(",ID_SITUACIONDOC = :ID_SITUACIONDOC");
            sb_sql.append(",BL_DOCUMENTO = :BL_DOCUMENTO");
            sb_sql.append(",DS_RUTA = :DS_RUTA");
            sb_sql.append(",FE_ALTA = :FE_ALTA");
            sb_sql.append(",FE_DESACTIVO = :FE_DESACTIVO");
            sb_sql.append(",USUARIOBD = :USUARIOBD");
            sb_sql.append(",TSTBD = :TSTBD");
        }
        sb_sql.append(" WHERE ");
        sb_sql.append("ID_HISTDOC = ").append(":ID_HISTDOC");
        
        return sb_sql.toString();
    }

    public String getDelete()
    {
        StringBuilder sb_sql = new StringBuilder("DELETE BD_A_HISTDOC ");
        sb_sql.append(" WHERE ");
        sb_sql.append("ID_HISTDOC = ").append(":ID_HISTDOC");
        
        return sb_sql.toString();
    }

    public Integer getIdHistdoc() {
        return idHistdoc;
    }

    public void setIdHistdoc(Integer idHistdoc) {
        this.idHistdoc = idHistdoc;
    }

    public Integer getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(Integer idDocumento) {
        this.idDocumento = idDocumento;
    }

    public Integer getIdSituaciondoc() {
        return idSituaciondoc;
    }

    public void setIdSituaciondoc(Integer idSituaciondoc) {
        this.idSituaciondoc = idSituaciondoc;
    }

    public byte[] getBlDocumento() {
        return blDocumento;
    }

    public void setBlDocumento(byte[] blDocumento) {
        this.blDocumento = blDocumento;
    }

    public String getDsRuta() {
        return dsRuta;
    }

    public void setDsRuta(String dsRuta) {
        this.dsRuta = dsRuta;
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
