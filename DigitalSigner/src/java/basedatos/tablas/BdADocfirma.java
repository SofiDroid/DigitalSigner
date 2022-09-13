package basedatos.tablas;

import basedatos.InterfazDAO;
import basedatos.OperacionSQL;
import java.util.Date;
import java.util.HashMap;

/**
 *
 * @author ihuegal
 */
public class BdADocfirma extends OperacionSQL implements InterfazDAO {
    
    protected Integer idDocfirma;
    protected Integer idDocumento;
    protected Integer idAutoridad;
    protected Integer enOrden;
    protected String dsFirmaposx;
    protected String dsFirmaposy;
    protected Date feFirma;
    protected Date feAlta;
    protected Date feDesactivo;
    protected String usuariobd;
    protected Date tstbd;

    public BdADocfirma() {
        // NADA
    }

    @Override
    public Object getClaseMapeada(HashMap<String,Object> bld) throws Exception {
        recuperaValorCampo(this, "idDocfirma", "ID_DOCFIRMA", bld);
        recuperaValorCampo(this, "idDocumento", "ID_DOCUMENTO", bld);
        recuperaValorCampo(this, "idAutoridad", "ID_AUTORIDAD", bld);
        recuperaValorCampo(this, "enOrden", "EN_ORDEN", bld);
        recuperaValorCampo(this, "dsFirmaposx", "DS_FIRMAPOSX", bld);
        recuperaValorCampo(this, "dsFirmaposy", "DS_FIRMAPOSY", bld);
        recuperaValorCampo(this, "feFirma", "FE_FIRMA", bld);
        recuperaValorCampo(this, "feAlta", "FE_ALTA", bld);
        recuperaValorCampo(this, "feDesactivo", "FE_DESACTIVO", bld);
        recuperaValorCampo(this, "usuariobd", "USUARIOBD", bld);
        recuperaValorCampo(this, "tstbd", "TSTBD", bld);
        
        return this; 
    }
    
    public String getSelectFiltro() {
        StringBuilder sb_sql = new StringBuilder("SELECT ");
        sb_sql.append("ID_DOCFIRMA");
        sb_sql.append(",ID_DOCUMENTO");
        sb_sql.append(",ID_AUTORIDAD");
        sb_sql.append(",EN_ORDEN");
        sb_sql.append(",DS_FIRMAPOSX");
        sb_sql.append(",DS_FIRMAPOSY");
        sb_sql.append(",FE_FIRMA");
        sb_sql.append(",FE_ALTA");
        sb_sql.append(",FE_DESACTIVO");
        sb_sql.append(",USUARIOBD");
        sb_sql.append(",TSTBD");
        
        sb_sql.append(" FROM BD_A_DOCFIRMA WHERE 1=1 ");
        if (idDocfirma != null) {
            sb_sql.append(" AND ID_DOCFIRMA = :ID_DOCFIRMA");
        }
        if (idDocumento != null) {
            sb_sql.append(" AND ID_DOCUMENTO = :ID_DOCUMENTO");
        }
        if (idAutoridad != null) {
            sb_sql.append(" AND ID_AUTORIDAD = :ID_AUTORIDAD");
        }
        if (enOrden != null) {
            sb_sql.append(" AND EN_ORDEN = :EN_ORDEN");
        }
        if (dsFirmaposx != null) {
            sb_sql.append(" AND UPPER(DS_FIRMAPOSX) = UPPER(:DS_FIRMAPOSX)");
        }
        if (dsFirmaposy != null) {
            sb_sql.append(" AND UPPER(DS_FIRMAPOSY) = UPPER(:DS_FIRMAPOSY)");
        }
        if (feFirma != null) {
            sb_sql.append(" AND FE_FIRMA = :FE_FIRMA");
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

    public Integer getIdDocfirma() {
        return idDocfirma;
    }

    public void setIdDocfirma(Integer idDocfirma) {
        this.idDocfirma = idDocfirma;
    }

    public Integer getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(Integer idDocumento) {
        this.idDocumento = idDocumento;
    }

    public Integer getIdAutoridad() {
        return idAutoridad;
    }

    public void setIdAutoridad(Integer idAutoridad) {
        this.idAutoridad = idAutoridad;
    }

    public Integer getEnOrden() {
        return enOrden;
    }

    public void setEnOrden(Integer enOrden) {
        this.enOrden = enOrden;
    }

    public String getDsFirmaposx() {
        return dsFirmaposx;
    }

    public void setDsFirmaposx(String dsFirmaposx) {
        this.dsFirmaposx = dsFirmaposx;
    }

    public String getDsFirmaposy() {
        return dsFirmaposy;
    }

    public void setDsFirmaposy(String dsFirmaposy) {
        this.dsFirmaposy = dsFirmaposy;
    }

    public Date getFeFirma() {
        return feFirma;
    }

    public void setFeFirma(Date feFirma) {
        this.feFirma = feFirma;
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
