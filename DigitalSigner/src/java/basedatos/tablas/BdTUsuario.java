package basedatos.tablas;

import basedatos.InterfazDAO;
import basedatos.OperacionSQL;
import java.util.Date;
import java.util.HashMap;

/**
 *
 * @author ihuegal
 */
public class BdTUsuario extends OperacionSQL implements InterfazDAO {
    
    protected Integer idUsuario;
    private String coNIF;
    private String coUsuario;
    private String coPassword;
    private String dsNombre;
    private String dsApellido1;
    private String dsApellido2;
    private Integer enIntentos;
    private Integer enIntentosmax;
    private Boolean boAdmin;
    private Date feAlta;
    private Date feDesactivo;
    private String usuariobd;
    private Date tstbd;

    public BdTUsuario() {
        // NADA
    }

    @Override
    public Object getClaseMapeada(HashMap<String,Object> bld) throws Exception {
        recuperaValorCampo(this, "idUsuario", "ID_USUARIO", bld);
        recuperaValorCampo(this, "coNIF", "CO_NIF", bld);
        recuperaValorCampo(this, "coUsuario", "CO_USUARIO", bld);
        recuperaValorCampo(this, "coPassword", "CO_PASSWORD", bld);
        recuperaValorCampo(this, "dsNombre", "DS_NOMBRE", bld);
        recuperaValorCampo(this, "dsApellido1", "DS_APELLIDO1", bld);
        recuperaValorCampo(this, "dsApellido2", "DS_APELLIDO2", bld);
        recuperaValorCampo(this, "enIntentos", "EN_INTENTOS", bld);
        recuperaValorCampo(this, "enIntentosmax", "EN_INTENTOSMAX", bld);
        recuperaValorCampo(this, "boAdmin", "BO_ADMIN", bld);
        recuperaValorCampo(this, "feAlta", "FE_ALTA", bld);
        recuperaValorCampo(this, "feDesactivo", "FE_DESACTIVO", bld);
        recuperaValorCampo(this, "usuariobd", "USUARIOBD", bld);
        recuperaValorCampo(this, "tstbd", "TSTBD", bld);
        
        return this; 
    }
    
    public String getSelectFiltro() {
        StringBuilder sb_sql = new StringBuilder("SELECT ");
        sb_sql.append("ID_USUARIO");
        sb_sql.append(",CO_NIF");
        sb_sql.append(",CO_USUARIO");
        sb_sql.append(",CO_PASSWORD");
        sb_sql.append(",DS_NOMBRE");
        sb_sql.append(",DS_APELLIDO1");
        sb_sql.append(",DS_APELLIDO2");
        sb_sql.append(",EN_INTENTOS");
        sb_sql.append(",EN_INTENTOSMAX");
        sb_sql.append(",BO_ADMIN");
        sb_sql.append(",FE_ALTA");
        sb_sql.append(",FE_DESACTIVO");
        sb_sql.append(",USUARIOBD");
        sb_sql.append(",TSTBD");
        
        sb_sql.append(" FROM BD_T_USUARIO WHERE 1=1 ");
        if (idUsuario != null) {
            sb_sql.append(" AND ID_USUARIO = :ID_USUARIO");
        }
        if (coNIF != null) {
            sb_sql.append(" AND UPPER(CO_NIF) = UPPER(:CO_NIF)");
        }
        if (coUsuario != null) {
            sb_sql.append(" AND UPPER(CO_USUARIO) = UPPER(:CO_USUARIO)");
        }
        if (coPassword != null) {
            sb_sql.append(" AND CO_PASSWORD = :CO_PASSWORD");
        }
        if (dsNombre != null) {
            sb_sql.append(" AND UPPER(DS_NOMBRE) = UPPER(:DS_NOMBRE)");
        }
        if (dsApellido1 != null) {
            sb_sql.append(" AND UPPER(DS_APELLIDO1) = UPPER(:DS_APELLIDO1)");
        }
        if (dsApellido2 != null) {
            sb_sql.append(" AND UPPER(DS_APELLIDO2) = UPPER(:DS_APELLIDO2)");
        }
        if (enIntentos != null) {
            sb_sql.append(" AND EN_INTENTOS = :EN_INTENTOS");
        }
        if (enIntentosmax != null) {
            sb_sql.append(" AND EN_INTENTOSMAX = :EN_INTENTOSMAX");
        }
        if (boAdmin != null) {
            sb_sql.append(" AND BO_ADMIN = :BO_ADMIN");
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
    
    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getCoNIF() {
        return coNIF;
    }

    public void setCoNIF(String coNIF) {
        this.coNIF = coNIF;
    }

    public String getCoUsuario() {
        return coUsuario;
    }

    public void setCoUsuario(String coUsuario) {
        this.coUsuario = coUsuario;
    }

    public String getCoPassword() {
        return coPassword;
    }

    public void setCoPassword(String coPassword) {
        this.coPassword = coPassword;
    }

    public String getDsNombre() {
        return dsNombre;
    }

    public void setDsNombre(String dsNombre) {
        this.dsNombre = dsNombre;
    }

    public String getDsApellido1() {
        return dsApellido1;
    }

    public void setDsApellido1(String dsApellido1) {
        this.dsApellido1 = dsApellido1;
    }

    public String getDsApellido2() {
        return dsApellido2;
    }

    public void setDsApellido2(String dsApellido2) {
        this.dsApellido2 = dsApellido2;
    }

    public Integer getEnIntentos() {
        return enIntentos;
    }

    public void setEnIntentos(Integer enIntentos) {
        this.enIntentos = enIntentos;
    }

    public Integer getEnIntentosmax() {
        return enIntentosmax;
    }

    public void setEnIntentosmax(Integer enIntentosmax) {
        this.enIntentosmax = enIntentosmax;
    }

    public Boolean getBoAdmin() {
        return boAdmin;
    }

    public void setBoAdmin(Boolean boAdmin) {
        this.boAdmin = boAdmin;
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
