package basedatos.tablas;

import basedatos.InterfazDAO;
import basedatos.OperacionSQL;
import java.util.Date;
import java.util.HashMap;

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
