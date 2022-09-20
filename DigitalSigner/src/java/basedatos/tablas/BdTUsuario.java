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
public class BdTUsuario extends OperacionSQL implements InterfazDAO {
    
    protected Integer idUsuario;
    protected String coNIF;
    protected String coUsuario;
    protected String coPassword;
    protected String dsNombre;
    protected String dsApellido1;
    protected String dsApellido2;
    protected Integer enIntentos;
    protected Integer enIntentosmax;
    protected Boolean boAdmin;
    protected Date feAlta;
    protected Date feDesactivo;
    protected String usuariobd;
    protected Date tstbd;

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
    
    public String getSecuencia()
    {
        return "SELECT SBD_T_USUARIO.NEXTVAL FROM DUAL";
    }
    
    public String getInsert()
    {
        StringBuilder sb_sql = new StringBuilder("INSERT INTO BD_T_USUARIO (");
        if (AppInit.TIPO_BASEDATOS == BaseDatos.ORACLE) {
            sb_sql.append("ID_USUARIO,");
        }
        sb_sql.append("CO_NIF");
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
        
        sb_sql.append(") VALUES (");
        if(AppInit.TIPO_BASEDATOS == BaseDatos.ORACLE) {
            if (this.idUsuario == null) {
                sb_sql.append(" SBD_T_USUARIO.NEXTVAL");
            } else {
                sb_sql.append(":ID_USUARIO");
            }
            sb_sql.append(",").append("DECODE(:CO_NIF, null, NULL, :CO_NIF)");
            sb_sql.append(",").append("DECODE(:CO_USUARIO, null, NULL, :CO_USUARIO)");
            sb_sql.append(",").append("DECODE(:CO_PASSWORD, null, NULL, :CO_PASSWORD)");
            sb_sql.append(",").append("DECODE(:DS_NOMBRE, null, NULL, :DS_NOMBRE)");
            sb_sql.append(",").append("DECODE(:DS_APELLIDO1, null, NULL, :DS_APELLIDO1)");
            sb_sql.append(",").append("DECODE(:DS_APELLIDO2, null, NULL, :DS_APELLIDO2)");
            sb_sql.append(",").append("DECODE(:EN_INTENTOS, null, NULL, :EN_INTENTOS)");
            sb_sql.append(",").append("DECODE(:EN_INTENTOSMAX, null, NULL, :EN_INTENTOSMAX)");
            sb_sql.append(",").append("DECODE(:BO_ADMIN, null, NULL, :BO_ADMIN)");
            sb_sql.append(",").append("DECODE(:FE_ALTA, null, NULL, :FE_ALTA)");
            sb_sql.append(",").append("DECODE(:FE_DESACTIVO, null, NULL, :FE_DESACTIVO)");
            sb_sql.append(",").append("DECODE(:USUARIOBD, null, NULL, :USUARIOBD)");
            sb_sql.append(",").append("DECODE(:TSTBD, null, NULL, :TSTBD)");
        }
        else {
            sb_sql.append(":CO_NIF");
            sb_sql.append(",").append(":CO_USUARIO");
            sb_sql.append(",").append(":CO_PASSWORD");
            sb_sql.append(",").append(":DS_NOMBRE");
            sb_sql.append(",").append(":DS_APELLIDO1");
            sb_sql.append(",").append(":DS_APELLIDO2");
            sb_sql.append(",").append(":EN_INTENTOS");
            sb_sql.append(",").append(":EN_INTENTOSMAX");
            sb_sql.append(",").append(":BO_ADMIN");
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
        StringBuilder sb_sql = new StringBuilder("UPDATE BD_T_USUARIO SET ");
        
        if (AppInit.TIPO_BASEDATOS == BaseDatos.ORACLE) {
            sb_sql.append("CO_NIF = ").append("DECODE(:CO_NIF, null, NULL, :CO_NIF)");
            sb_sql.append(",CO_USUARIO = ").append("DECODE(:CO_USUARIO, null, NULL, :CO_USUARIO)");
            sb_sql.append(",CO_PASSWORD = ").append("DECODE(:CO_PASSWORD, null, NULL, :CO_PASSWORD)");
            sb_sql.append(",DS_NOMBRE = ").append("DECODE(:DS_NOMBRE, null, NULL, :DS_NOMBRE)");
            sb_sql.append(",DS_APELLIDO1 = ").append("DECODE(:DS_APELLIDO1, null, NULL, :DS_APELLIDO1)");
            sb_sql.append(",DS_APELLIDO2 = ").append("DECODE(:DS_APELLIDO2, null, NULL, :DS_APELLIDO2)");
            sb_sql.append(",EN_INTENTOS = ").append("DECODE(:EN_INTENTOS, null, NULL, :EN_INTENTOS)");
            sb_sql.append(",EN_INTENTOSMAX = ").append("DECODE(:EN_INTENTOSMAX, null, NULL, :EN_INTENTOSMAX)");
            sb_sql.append(",BO_ADMIN = ").append("DECODE(:BO_ADMIN, null, NULL, :BO_ADMIN)");
            sb_sql.append(",FE_ALTA = ").append("DECODE(:FE_ALTA, null, NULL, :FE_ALTA)");
            sb_sql.append(",FE_DESACTIVO = ").append("DECODE(:FE_DESACTIVO, null, NULL, :FE_DESACTIVO)");
            sb_sql.append(",USUARIOBD = ").append("DECODE(:USUARIOBD, null, NULL, :USUARIOBD)");
            sb_sql.append(",TSTBD = ").append("DECODE(:TSTBD, null, NULL, :TSTBD)");
        }
        else {
            sb_sql.append("CO_NIF = :CO_NIF");
            sb_sql.append(",CO_USUARIO = :CO_USUARIO");
            sb_sql.append(",CO_PASSWORD = :CO_PASSWORD");
            sb_sql.append(",DS_NOMBRE = :DS_NOMBRE");
            sb_sql.append(",DS_APELLIDO1 = :DS_APELLIDO1");
            sb_sql.append(",DS_APELLIDO2 = :DS_APELLIDO2");
            sb_sql.append(",EN_INTENTOS = :EN_INTENTOS");
            sb_sql.append(",EN_INTENTOSMAX = :EN_INTENTOSMAX");
            sb_sql.append(",BO_ADMIN = :BO_ADMIN");
            sb_sql.append(",FE_ALTA = :FE_ALTA");
            sb_sql.append(",FE_DESACTIVO = :FE_DESACTIVO");
            sb_sql.append(",USUARIOBD = :USUARIOBD");
            sb_sql.append(",TSTBD = :TSTBD");
        }
        sb_sql.append(" WHERE ");
        sb_sql.append("ID_USUARIO = ").append(":ID_USUARIO");
        
        return sb_sql.toString();
    }

    public String getDelete()
    {
        StringBuilder sb_sql = new StringBuilder("DELETE BD_T_USUARIO ");
        sb_sql.append(" WHERE ");
        sb_sql.append("ID_USUARIO = ").append(":ID_USUARIO");
        
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
