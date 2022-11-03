package utilidades;

import init.AppInit;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author ihuegal
 */
public class Formateos {

    public enum TipoFecha {
        NADA(""),
        FECHA("dd/MM/yyyy"),
        FECHA_HORA("dd/MM/yyyy HH:mm"),
        FECHA_HORA_SEGUNDOS("dd/MM/yyyy HH:mm:ss"),
        HORA_MINUTOS("HH:mm"),
        HORA_MINUTOS_SEGUNDOS("HH:mm:ss");

        private final String formato;

        TipoFecha(String formato) {
            this.formato = formato;
        }

        public String getFormato() {
            return this.formato;
        }
    }

    public static String[] separarNombre(String nombreApellidos) {
        String nombre = "";
        String apellido1 = "";
        String apellido2 = "";

        String[] _name = nombreApellidos.split(" ");
        
        if (_name.length == 1) {
            // NOMBRE
            nombre = _name[0];
        }
        else if (_name.length == 2) {
            // APELLIDO1 NOMBRE
            apellido1 = _name[0];
            nombre = _name[1];
        }
        else if (_name.length == 3) {
            // APELLIDO1 APELLIDO2 NOMBRE
            apellido1 = _name[0];
            apellido2 = _name[1];
            nombre = _name[2];
        }
        else if (_name.length >= 4) {
            // APELLIDO1 APELLIDO2 NOMBRE (Con compuestos)
            int i = 0;
            apellido1 += _name[i];
            if (" EL LA LOS DEL DE ".contains(" " + _name[i] + " ")) {
                apellido1 += _name[++i];
                if (" EL LA LOS DEL DE ".contains(" " + _name[i] + " ")) {
                    apellido1 += _name[++i];
                }
            }
            
            apellido2 = _name[++i];
            if (" EL LA LOS DEL DE ".contains(" " + _name[i+1] + " ")) {
                apellido2 += _name[++i];
                if (" EL LA LOS DEL DE ".contains(" " + _name[i+1] + " ")) {
                    apellido2 += _name[++i];
                }
                apellido2 += _name[++i];
            }
            
            while (i < _name.length) {
                nombre += _name[++i];
            }
            if (nombre.isBlank()) {
                nombre = "NOMBRE";
            }
        }
        
        return new String[] {nombre, apellido1, apellido2};
    }
    
    public static String dateToString(Date fecha, TipoFecha tipo) {

        if (fecha == null) {
            return null;
        }

        if (tipo == null) {
            tipo = TipoFecha.FECHA_HORA_SEGUNDOS;
        }

        return new SimpleDateFormat(tipo.getFormato()).format(fecha);
    }
    
    public static Date stringToDate(String fecha, TipoFecha tipo) throws ParseException {

        if (!(fecha != null && !fecha.isEmpty())) {
            return null;
        }

        if (tipo == null) {
            tipo = TipoFecha.FECHA_HORA_SEGUNDOS;
        }

        return new SimpleDateFormat(tipo.getFormato()).parse(fecha);
    }
    
    public static String dateToSql(Date fecha) {
        if (null == AppInit.TIPO_BASEDATOS) {
            return null;
        }
        else return switch (AppInit.TIPO_BASEDATOS) {
            case ORACLE -> "TO_DATE('" + dateToString(fecha, Formateos.TipoFecha.FECHA) + "','DD/MM/YYYY')";
            case SQLSERVER -> "CONVERT(DATETIME,'" + dateToString(fecha, Formateos.TipoFecha.FECHA) + "',103)";
            default -> null;
        };
    }
    
    public static String fechaActualSQL() {
        if (AppInit.TIPO_BASEDATOS == BaseDatos.ORACLE) {
            return "SYSDATE";
        }
        else if (AppInit.TIPO_BASEDATOS == BaseDatos.SQLSERVER) {
            return "CONVERT (date, SYSDATETIME())";
        }
        
        return null;
    }
}
