package basedatos.tablas;

import basedatos.InterfazDAO;
import basedatos.OperacionSQL;
import java.util.Date;
import java.util.HashMap;
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
