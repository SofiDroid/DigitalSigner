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
public class BdDDocumento extends OperacionSQL implements InterfazDAO {
    
    protected Integer idDocumento;
    protected String coDocumento;
    protected String dsDocumento;
    protected Integer idTipodocumento;
    protected byte[] blDocumento;
    protected String coFichero;
    protected String coExtension;
    protected Integer idSituaciondoc;
    protected String dsRuta;
    protected Date feAlta;
    protected Date feDesactivo;
    protected String usuariobd;
    protected Date tstbd;

    public BdDDocumento() {
        // NADA
    }

    @Override
    public Object getClaseMapeada(HashMap<String,Object> bld) throws Exception {
        recuperaValorCampo(this, "idDocumento", "ID_DOCUMENTO", bld);
        recuperaValorCampo(this, "coDocumento", "CO_DOCUMENTO", bld);
        recuperaValorCampo(this, "dsDocumento", "DS_DOCUMENTO", bld);
        recuperaValorCampo(this, "idTipodocumento", "ID_TIPODOCUMENTO", bld);
        recuperaValorCampo(this, "blDocumento", "BL_DOCUMENTO", bld);
        recuperaValorCampo(this, "coFichero", "CO_FICHERO", bld);
        recuperaValorCampo(this, "coExtension", "CO_EXTENSION", bld);
        recuperaValorCampo(this, "idSituaciondoc", "ID_SITUACIONDOC", bld);
        recuperaValorCampo(this, "dsRuta", "DS_RUTA", bld);
        recuperaValorCampo(this, "feAlta", "FE_ALTA", bld);
        recuperaValorCampo(this, "feDesactivo", "FE_DESACTIVO", bld);
        recuperaValorCampo(this, "usuariobd", "USUARIOBD", bld);
        recuperaValorCampo(this, "tstbd", "TSTBD", bld);
        
        return this; 
    }
    
    public String getSelectFiltro() {
        StringBuilder sb_sql = new StringBuilder("SELECT ");
        sb_sql.append("ID_DOCUMENTO");
        sb_sql.append(",CO_DOCUMENTO");
        sb_sql.append(",DS_DOCUMENTO");
        sb_sql.append(",ID_TIPODOCUMENTO");
        sb_sql.append(",CO_FICHERO");
        sb_sql.append(",CO_EXTENSION");
        sb_sql.append(",ID_SITUACIONDOC");
        sb_sql.append(",DS_RUTA");
        sb_sql.append(",FE_ALTA");
        sb_sql.append(",FE_DESACTIVO");
        sb_sql.append(",USUARIOBD");
        sb_sql.append(",TSTBD");
        
        sb_sql.append(" FROM BD_D_DOCUMENTO WHERE 1=1 ");
        if (idDocumento != null) {
            sb_sql.append(" AND ID_DOCUMENTO = :ID_DOCUMENTO");
        }
        if (coDocumento != null) {
            sb_sql.append(" AND UPPER(CO_DOCUMENTO) = UPPER(:CO_DOCUMENTO)");
        }
        if (dsDocumento != null) {
            sb_sql.append(" AND UPPER(DS_DOCUMENTO) = UPPER(:DS_DOCUMENTO)");
        }
        if (idTipodocumento != null) {
            sb_sql.append(" AND ID_TIPODOCUMENTO = :ID_TIPODOCUMENTO");
        }
        if (coFichero != null) {
            sb_sql.append(" AND UPPER(CO_FICHERO) = UPPER(:CO_FICHERO)");
        }
        if (coExtension != null) {
            sb_sql.append(" AND UPPER(CO_EXTENSION) = UPPER(:CO_EXTENSION)");
        }
        if (idSituaciondoc != null) {
            sb_sql.append(" AND ID_SITUACIONDOC = :ID_SITUACIONDOC");
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
        return "SELECT SBD_D_DOCUMENTO.NEXTVAL FROM DUAL";
    }
    
    public String getInsert()
    {
        StringBuilder sb_sql = new StringBuilder("INSERT INTO BD_D_DOCUMENTO (");
        if (AppInit.TIPO_BASEDATOS == BaseDatos.ORACLE) {
            sb_sql.append("ID_DOCUMENTO,");
        }
        sb_sql.append("CO_DOCUMENTO");
        sb_sql.append(",DS_DOCUMENTO");
        sb_sql.append(",ID_TIPODOCUMENTO");
        sb_sql.append(",BL_DOCUMENTO");
        sb_sql.append(",CO_FICHERO");
        sb_sql.append(",CO_EXTENSION");
        sb_sql.append(",ID_SITUACIONDOC");
        sb_sql.append(",DS_RUTA");
        sb_sql.append(",FE_ALTA");
        sb_sql.append(",FE_DESACTIVO");
        sb_sql.append(",USUARIOBD");
        sb_sql.append(",TSTBD");
        
        sb_sql.append(") VALUES (");
        if(AppInit.TIPO_BASEDATOS == BaseDatos.ORACLE) {
            if (this.idTipodocumento == null) {
                sb_sql.append(" SBD_D_DOCUMENTO.NEXTVAL");
            } else {
                sb_sql.append(":ID_DOCUMENTO");
            }
            sb_sql.append(",").append("DECODE(:CO_DOCUMENTO, null, SBD_D_DOCUMENTO.CURRVAL, :CO_DOCUMENTO)");
            sb_sql.append(",").append("DECODE(:DS_DOCUMENTO, null, NULL, :DS_DOCUMENTO)");
            sb_sql.append(",").append("DECODE(:ID_TIPODOCUMENTO, null, NULL, :ID_TIPODOCUMENTO)");
            sb_sql.append(",").append("DECODE(:BL_DOCUMENTO, null, NULL, :BL_DOCUMENTO)");
            sb_sql.append(",").append("DECODE(:CO_FICHERO, null, NULL, :CO_FICHERO)");
            sb_sql.append(",").append("DECODE(:CO_EXTENSION, null, NULL, :CO_EXTENSION)");
            sb_sql.append(",").append("DECODE(:ID_SITUACIONDOC, null, NULL, :ID_SITUACIONDOC)");
            sb_sql.append(",").append("DECODE(:DS_RUTA, null, NULL, :DS_RUTA)");
            sb_sql.append(",").append("DECODE(:FE_ALTA, null, NULL, :FE_ALTA)");
            sb_sql.append(",").append("DECODE(:FE_DESACTIVO, null, NULL, :FE_DESACTIVO)");
            sb_sql.append(",").append("DECODE(:USUARIOBD, null, NULL, :USUARIOBD)");
            sb_sql.append(",").append("DECODE(:TSTBD, null, NULL, :TSTBD)");
        }
        else {
            sb_sql.append("CASE WEHN :CO_DOCUMENTO IS NULL THEN IDENT_CURRENT('BD_D_DOCUMENTO') ELSE :CO_DOCUMENTO END");
            sb_sql.append(",").append(":DS_DOCUMENTO");
            sb_sql.append(",").append(":ID_TIPODOCUMENTO");
            sb_sql.append(",").append(":BL_DOCUMENTO");
            sb_sql.append(",").append(":CO_FICHERO");
            sb_sql.append(",").append(":CO_EXTENSION");
            sb_sql.append(",").append(":ID_SITUACIONDOC");
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
        StringBuilder sb_sql = new StringBuilder("UPDATE BD_D_DOCUMENTO SET ");
        
        if (AppInit.TIPO_BASEDATOS == BaseDatos.ORACLE) {
            sb_sql.append("CO_DOCUMENTO = ").append("DECODE(:CO_DOCUMENTO, null, NULL, :CO_DOCUMENTO)");
            sb_sql.append(",DS_DOCUMENTO = ").append("DECODE(:DS_DOCUMENTO, null, NULL, :DS_DOCUMENTO)");
            sb_sql.append(",ID_TIPODOCUMENTO = ").append("DECODE(:ID_TIPODOCUMENTO, null, NULL, :ID_TIPODOCUMENTO)");
            sb_sql.append(",BL_DOCUMENTO = ").append("DECODE(:BL_DOCUMENTO, null, NULL, :BL_DOCUMENTO)");
            sb_sql.append(",CO_FICHERO = ").append("DECODE(:CO_FICHERO, null, NULL, :CO_FICHERO)");
            sb_sql.append(",CO_EXTENSION = ").append("DECODE(:CO_EXTENSION, null, NULL, :CO_EXTENSION)");
            sb_sql.append(",ID_SITUACIONDOC = ").append("DECODE(:ID_SITUACIONDOC, null, NULL, :ID_SITUACIONDOC)");
            sb_sql.append(",DS_RUTA = ").append("DECODE(:DS_RUTA, null, NULL, :DS_RUTA)");
            sb_sql.append(",FE_ALTA = ").append("DECODE(:FE_ALTA, null, NULL, :FE_ALTA)");
            sb_sql.append(",FE_DESACTIVO = ").append("DECODE(:FE_DESACTIVO, null, NULL, :FE_DESACTIVO)");
            sb_sql.append(",USUARIOBD = ").append("DECODE(:USUARIOBD, null, NULL, :USUARIOBD)");
            sb_sql.append(",TSTBD = ").append("DECODE(:TSTBD, null, NULL, :TSTBD)");
        }
        else {
            sb_sql.append("CO_DOCUMENTO = :CO_DOCUMENTO");
            sb_sql.append(",DS_DOCUMENTO = :DS_DOCUMENTO");
            sb_sql.append(",ID_TIPODOCUMENTO = :ID_TIPODOCUMENTO");
            sb_sql.append(",BL_DOCUMENTO = :BL_DOCUMENTO");
            sb_sql.append(",CO_FICHERO = :CO_FICHERO");
            sb_sql.append(",CO_EXTENSION = :CO_EXTENSION");
            sb_sql.append(",ID_SITUACIONDOC = :ID_SITUACIONDOC");
            sb_sql.append(",DS_RUTA = :DS_RUTA");
            sb_sql.append(",FE_ALTA = :FE_ALTA");
            sb_sql.append(",FE_DESACTIVO = :FE_DESACTIVO");
            sb_sql.append(",USUARIOBD = :USUARIOBD");
            sb_sql.append(",TSTBD = :TSTBD");
        }
        sb_sql.append(" WHERE ");
        sb_sql.append("ID_DOCUMENTO = ").append(":ID_DOCUMENTO");
        
        return sb_sql.toString();
    }

    public String getDelete()
    {
        StringBuilder sb_sql = new StringBuilder("DELETE BD_D_DOCUMENTO ");
        sb_sql.append(" WHERE ");
        sb_sql.append("ID_DOCUMENTO = ").append(":ID_DOCUMENTO");
        
        return sb_sql.toString();
    }

    public Integer getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(Integer idDocumento) {
        this.idDocumento = idDocumento;
    }

    public String getCoDocumento() {
        return coDocumento;
    }

    public void setCoDocumento(String coDocumento) {
        this.coDocumento = coDocumento;
    }

    public String getDsDocumento() {
        return dsDocumento;
    }

    public void setDsDocumento(String dsDocumento) {
        this.dsDocumento = dsDocumento;
    }

    public Integer getIdTipodocumento() {
        return idTipodocumento;
    }

    public void setIdTipodocumento(Integer idTipodocumento) {
        this.idTipodocumento = idTipodocumento;
    }

    public byte[] getBlDocumento() {
        if (this.idDocumento != null && this.blDocumento == null) {
            this.blDocumento = new Ficheros().recuperaDocumento(this.idDocumento); 
        }
        return blDocumento;
    }

    public void setBlDocumento(byte[] blDocumento) {
        this.blDocumento = blDocumento;
    }

    public String getCoFichero() {
        return coFichero;
    }

    public void setCoFichero(String coFichero) {
        this.coFichero = coFichero;
    }

    public String getCoExtension() {
        return coExtension;
    }

    public void setCoExtension(String coExtension) {
        this.coExtension = coExtension;
    }

    public Integer getIdSituaciondoc() {
        return idSituaciondoc;
    }

    public void setIdSituaciondoc(Integer idSituaciondoc) {
        this.idSituaciondoc = idSituaciondoc;
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
