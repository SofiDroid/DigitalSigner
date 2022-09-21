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
public class BdADocextra extends OperacionSQL implements InterfazDAO {
    
    protected Integer idDocextra;
    protected Integer idDocumento;
    protected String coFichero;
    protected String coExtension;
    protected byte[] blFichero;
    protected String dsRuta;
    protected Date feAlta;
    protected Date feDesactivo;
    protected String usuariobd;
    protected Date tstbd;

    public BdADocextra() {
        // NADA
    }

    @Override
    public Object getClaseMapeada(HashMap<String,Object> bld) throws Exception {
        recuperaValorCampo(this, "idDocextra", "ID_DOCEXTRA", bld);
        recuperaValorCampo(this, "idDocumento", "ID_DOCUMENTO", bld);
        recuperaValorCampo(this, "coFichero", "CO_FICHERO", bld);
        recuperaValorCampo(this, "coExtension", "CO_EXTENSION", bld);
        recuperaValorCampo(this, "blFichero", "BL_FICHERO", bld);
        recuperaValorCampo(this, "dsRuta", "DS_RUTA", bld);
        recuperaValorCampo(this, "feAlta", "FE_ALTA", bld);
        recuperaValorCampo(this, "feDesactivo", "FE_DESACTIVO", bld);
        recuperaValorCampo(this, "usuariobd", "USUARIOBD", bld);
        recuperaValorCampo(this, "tstbd", "TSTBD", bld);
        
        return this; 
    }
    
    public String getSelectFiltro() {
        StringBuilder sb_sql = new StringBuilder("SELECT ");
        sb_sql.append("ID_DOCEXTRA");
        sb_sql.append(",ID_DOCUMENTO");
        sb_sql.append(",CO_FICHERO");
        sb_sql.append(",CO_EXTENSION");
        sb_sql.append(",BL_FICHERO");
        sb_sql.append(",DS_RUTA");
        sb_sql.append(",FE_ALTA");
        sb_sql.append(",FE_DESACTIVO");
        sb_sql.append(",USUARIOBD");
        sb_sql.append(",TSTBD");
        
        sb_sql.append(" FROM BD_A_DOCEXTRA WHERE 1=1 ");
        if (idDocextra != null) {
            sb_sql.append(" AND ID_DOCEXTRA = :ID_DOCEXTRA");
        }
        if (idDocumento != null) {
            sb_sql.append(" AND ID_DOCUMENTO = :ID_DOCUMENTO");
        }
        if (coFichero != null) {
            sb_sql.append(" AND UPPER(CO_FICHERO) = UPPER(:CO_FICHERO)");
        }
        if (coExtension != null) {
            sb_sql.append(" AND UPPER(CO_EXTENSION) = UPPER(:CO_EXTENSION)");
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
        return "SELECT SBD_A_DOCEXTRA.NEXTVAL FROM DUAL";
    }
    
    public String getInsert()
    {
        StringBuilder sb_sql = new StringBuilder("INSERT INTO BD_A_DOCEXTRA (");
        if (AppInit.TIPO_BASEDATOS == BaseDatos.ORACLE) {
            sb_sql.append("ID_DOCEXTRA,");
        }
        sb_sql.append("ID_DOCUMENTO");
        sb_sql.append(",CO_FICHERO");
        sb_sql.append(",CO_EXTENSION");
        sb_sql.append(",BL_FICHERO");
        sb_sql.append(",DS_RUTA");
        sb_sql.append(",FE_ALTA");
        sb_sql.append(",FE_DESACTIVO");
        sb_sql.append(",USUARIOBD");
        sb_sql.append(",TSTBD");
        
        sb_sql.append(") VALUES (");
        if(AppInit.TIPO_BASEDATOS == BaseDatos.ORACLE) {
            if (this.idDocextra == null) {
                sb_sql.append(" SBD_A_DOCEXTRA.NEXTVAL");
            } else {
                sb_sql.append(":ID_DOCEXTRA");
            }
            sb_sql.append(",").append("DECODE(:ID_DOCUMENTO, null, NULL, :ID_DOCUMENTO)");
            sb_sql.append(",").append("DECODE(:CO_FICHERO, null, NULL, :CO_FICHERO)");
            sb_sql.append(",").append("DECODE(:CO_EXTENSION, null, NULL, :CO_EXTENSION)");
            sb_sql.append(",").append("DECODE(:BL_FICHERO, null, NULL, :BL_FICHERO)");
            sb_sql.append(",").append("DECODE(:DS_RUTA, null, NULL, :DS_RUTA)");
            sb_sql.append(",").append("DECODE(:FE_ALTA, null, NULL, :FE_ALTA)");
            sb_sql.append(",").append("DECODE(:FE_DESACTIVO, null, NULL, :FE_DESACTIVO)");
            sb_sql.append(",").append("DECODE(:USUARIOBD, null, NULL, :USUARIOBD)");
            sb_sql.append(",").append("DECODE(:TSTBD, null, NULL, :TSTBD)");
        }
        else {
            sb_sql.append(":ID_DOCUMENTO");
            sb_sql.append(",").append(":CO_FICHERO");
            sb_sql.append(",").append(":CO_EXTENSION");
            sb_sql.append(",").append(":BL_FICHERO");
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
        StringBuilder sb_sql = new StringBuilder("UPDATE BD_A_DOCEXTRA SET ");
        
        if (AppInit.TIPO_BASEDATOS == BaseDatos.ORACLE) {
            sb_sql.append("ID_DOCUMENTO = ").append("DECODE(:ID_DOCUMENTO, null, NULL, :ID_DOCUMENTO)");
            sb_sql.append(",CO_FICHERO = ").append("DECODE(:CO_FICHERO, null, NULL, :CO_FICHERO)");
            sb_sql.append(",CO_EXTENSION = ").append("DECODE(:CO_EXTENSION, null, NULL, :CO_EXTENSION)");
            sb_sql.append(",BL_FICHERO = ").append("DECODE(:BL_FICHERO, null, NULL, :BL_FICHERO)");
            sb_sql.append(",DS_RUTA = ").append("DECODE(:DS_RUTA, null, NULL, :DS_RUTA)");
            sb_sql.append(",FE_ALTA = ").append("DECODE(:FE_ALTA, null, NULL, :FE_ALTA)");
            sb_sql.append(",FE_DESACTIVO = ").append("DECODE(:FE_DESACTIVO, null, NULL, :FE_DESACTIVO)");
            sb_sql.append(",USUARIOBD = ").append("DECODE(:USUARIOBD, null, NULL, :USUARIOBD)");
            sb_sql.append(",TSTBD = ").append("DECODE(:TSTBD, null, NULL, :TSTBD)");
        }
        else {
            sb_sql.append("ID_DOCUMENTO = :ID_DOCUMENTO");
            sb_sql.append(",CO_FICHERO = :CO_FICHERO");
            sb_sql.append(",CO_EXTENSION = :CO_EXTENSION");
            sb_sql.append(",BL_FICHERO = :BL_FICHERO");
            sb_sql.append(",DS_RUTA = :DS_RUTA");
            sb_sql.append(",FE_ALTA = :FE_ALTA");
            sb_sql.append(",FE_DESACTIVO = :FE_DESACTIVO");
            sb_sql.append(",USUARIOBD = :USUARIOBD");
            sb_sql.append(",TSTBD = :TSTBD");
        }
        sb_sql.append(" WHERE ");
        sb_sql.append("ID_DOCEXTRA = ").append(":ID_DOCEXTRA");
        
        return sb_sql.toString();
    }

    public String getDelete()
    {
        StringBuilder sb_sql = new StringBuilder("DELETE BD_A_DOCEXTRA ");
        sb_sql.append(" WHERE ");
        sb_sql.append("ID_DOCEXTRA = ").append(":ID_DOCEXTRA");
        
        return sb_sql.toString();
    }

    public Integer getIdDocextra() {
        return idDocextra;
    }

    public void setIdDocextra(Integer idDocextra) {
        this.idDocextra = idDocextra;
    }

    public Integer getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(Integer idDocumento) {
        this.idDocumento = idDocumento;
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

    public byte[] getBlFichero() {
        return blFichero;
    }

    public void setBlFichero(byte[] blFichero) {
        this.blFichero = blFichero;
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
