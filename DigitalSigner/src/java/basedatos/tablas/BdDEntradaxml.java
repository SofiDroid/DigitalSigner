package basedatos.tablas;

import basedatos.InterfazDAO;
import basedatos.OperacionSQL;
import init.AppInit;
import java.util.Date;
import java.util.HashMap;
import utilidades.BaseDatos;
import utilidades.Ficheros;

/**
 *
 * @author ihuegal
 */
public class BdDEntradaxml extends OperacionSQL implements InterfazDAO {
    
    protected Integer idEntradaxml;
    protected byte[] blEntradaxml;
    protected Integer idDocumento;
    protected Integer idSituacionxml;
    protected String dsRuta;
    protected Date feAlta;
    protected Date feDesactivo;
    protected String usuariobd;
    protected Date tstbd;

    public BdDEntradaxml() {
        // NADA
    }

    @Override
    public Object getClaseMapeada(HashMap<String,Object> bld) throws Exception {
        recuperaValorCampo(this, "idEntradaxml", "ID_ENTRADAXML", bld);
        recuperaValorCampo(this, "blEntradaxml", "BL_ENTRADAXML", bld);
        recuperaValorCampo(this, "idDocumento", "ID_DOCUMENTO", bld);
        recuperaValorCampo(this, "idSituacionxml", "ID_SITUACIONXML", bld);
        recuperaValorCampo(this, "dsRuta", "DS_RUTA", bld);
        recuperaValorCampo(this, "feAlta", "FE_ALTA", bld);
        recuperaValorCampo(this, "feDesactivo", "FE_DESACTIVO", bld);
        recuperaValorCampo(this, "usuariobd", "USUARIOBD", bld);
        recuperaValorCampo(this, "tstbd", "TSTBD", bld);
        
        return this; 
    }
    
    public String getSelectFiltro() {
        StringBuilder sb_sql = new StringBuilder("SELECT ");
        sb_sql.append("ID_ENTRADAXML");
        sb_sql.append(",ID_DOCUMENTO");
        sb_sql.append(",ID_SITUACIONXML");
        sb_sql.append(",DS_RUTA");
        sb_sql.append(",FE_ALTA");
        sb_sql.append(",FE_DESACTIVO");
        sb_sql.append(",USUARIOBD");
        sb_sql.append(",TSTBD");
        
        sb_sql.append(" FROM BD_D_ENTRADAXML WHERE 1=1 ");
        if (idEntradaxml != null) {
            sb_sql.append(" AND ID_ENTRADAXML = :ID_ENTRADAXML");
        }
        if (idDocumento != null) {
            sb_sql.append(" AND ID_DOCUMENTO = :ID_DOCUMENTO");
        }
        if (idSituacionxml != null) {
            sb_sql.append(" AND ID_SITUACIONXML = :ID_SITUACIONXML");
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
        return "SELECT SBD_D_ENTRADAXML.NEXTVAL FROM DUAL";
    }
    
    public String getInsert()
    {
        StringBuilder sb_sql = new StringBuilder("INSERT INTO BD_D_ENTRADAXML (");
        if (AppInit.TIPO_BASEDATOS == BaseDatos.ORACLE) {
            sb_sql.append("ID_ENTRADAXML,");
        }
        sb_sql.append("BL_ENTRADAXML");
        sb_sql.append(",ID_DOCUMENTO");
        sb_sql.append(",ID_SITUACIONXML");
        sb_sql.append(",DS_RUTA");
        sb_sql.append(",FE_ALTA");
        sb_sql.append(",FE_DESACTIVO");
        sb_sql.append(",USUARIOBD");
        sb_sql.append(",TSTBD");
        
        sb_sql.append(") VALUES (");
        if(AppInit.TIPO_BASEDATOS == BaseDatos.ORACLE) {
            if (this.idEntradaxml == null) {
                sb_sql.append(" SBD_D_ENTRADAXML.NEXTVAL");
            } else {
                sb_sql.append(":ID_ENTRADAXML");
            }
            sb_sql.append(",").append("DECODE(:BL_ENTRADAXML, null, NULL, :BL_ENTRADAXML)");
            sb_sql.append(",").append("DECODE(:ID_DOCUMENTO, null, NULL, :ID_DOCUMENTO)");
            sb_sql.append(",").append("DECODE(:ID_SITUACIONXML, null, NULL, :ID_SITUACIONXML)");
            sb_sql.append(",").append("DECODE(:DS_RUTA, null, NULL, :DS_RUTA)");
            sb_sql.append(",").append("DECODE(:FE_ALTA, null, NULL, :FE_ALTA)");
            sb_sql.append(",").append("DECODE(:FE_DESACTIVO, null, NULL, :FE_DESACTIVO)");
            sb_sql.append(",").append("DECODE(:USUARIOBD, null, NULL, :USUARIOBD)");
            sb_sql.append(",").append("DECODE(:TSTBD, null, NULL, :TSTBD)");
        }
        else {
            sb_sql.append(":BL_ENTRADAXML");
            sb_sql.append(",").append(":ID_DOCUMENTO");
            sb_sql.append(",").append(":ID_SITUACIONXML");
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
        StringBuilder sb_sql = new StringBuilder("UPDATE BD_D_ENTRADAXML SET ");
        
        if (AppInit.TIPO_BASEDATOS == BaseDatos.ORACLE) {
            sb_sql.append("BL_ENTRADAXML = ").append("DECODE(:BL_ENTRADAXML, null, NULL, :BL_ENTRADAXML)");
            sb_sql.append(",ID_DOCUMENTO = ").append("DECODE(:ID_DOCUMENTO, null, NULL, :ID_DOCUMENTO)");
            sb_sql.append(",ID_SITUACIONXML = ").append("DECODE(:ID_SITUACIONXML, null, NULL, :ID_SITUACIONXML)");
            sb_sql.append(",DS_RUTA = ").append("DECODE(:DS_RUTA, null, NULL, :DS_RUTA)");
            sb_sql.append(",FE_ALTA = ").append("DECODE(:FE_ALTA, null, NULL, :FE_ALTA)");
            sb_sql.append(",FE_DESACTIVO = ").append("DECODE(:FE_DESACTIVO, null, NULL, :FE_DESACTIVO)");
            sb_sql.append(",USUARIOBD = ").append("DECODE(:USUARIOBD, null, NULL, :USUARIOBD)");
            sb_sql.append(",TSTBD = ").append("DECODE(:TSTBD, null, NULL, :TSTBD)");
        }
        else {
            sb_sql.append("BL_ENTRADAXML = :BL_ENTRADAXML");
            sb_sql.append(",ID_DOCUMENTO = :ID_DOCUMENTO");
            sb_sql.append(",ID_SITUACIONXML = :ID_SITUACIONXML");
            sb_sql.append(",DS_RUTA = :DS_RUTA");
            sb_sql.append(",FE_ALTA = :FE_ALTA");
            sb_sql.append(",FE_DESACTIVO = :FE_DESACTIVO");
            sb_sql.append(",USUARIOBD = :USUARIOBD");
            sb_sql.append(",TSTBD = :TSTBD");
        }
        sb_sql.append(" WHERE ");
        sb_sql.append("ID_ENTRADAXML = ").append(":ID_ENTRADAXML");
        
        return sb_sql.toString();
    }

    public String getDelete()
    {
        StringBuilder sb_sql = new StringBuilder("DELETE BD_D_ENTRADAXML ");
        sb_sql.append(" WHERE ");
        sb_sql.append("ID_ENTRADAXML = ").append(":ID_ENTRADAXML");
        
        return sb_sql.toString();
    }

    public Integer getIdEntradaxml() {
        return idEntradaxml;
    }

    public void setIdEntradaxml(Integer idEntradaxml) {
        this.idEntradaxml = idEntradaxml;
    }

    public byte[] getBlEntradaxml() {
        if (this.idEntradaxml != null && this.blEntradaxml == null) {
            this.blEntradaxml = new Ficheros().recuperaEntradaXML(this.idEntradaxml); 
        }
        return blEntradaxml;
    }

    public void setBlEntradaxml(byte[] blEntradaxml) {
        this.blEntradaxml = blEntradaxml;
    }

    public Integer getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(Integer idDocumento) {
        this.idDocumento = idDocumento;
    }

    public Integer getIdSituacionxml() {
        return idSituacionxml;
    }

    public void setIdSituacionxml(Integer idSituacionxml) {
        this.idSituacionxml = idSituacionxml;
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
