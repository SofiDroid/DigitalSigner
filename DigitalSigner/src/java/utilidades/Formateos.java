package utilidades;

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
    
    public enum BaseDatos {
        SQLSERVER,
        ORACLE;
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
    
    public static String dateToSql(Date fecha, BaseDatos tipoBD) {
        if (null == tipoBD) {
            return null;
        }
        else return switch (tipoBD) {
            case ORACLE -> "TO_DATE('" + dateToString(fecha, Formateos.TipoFecha.FECHA) + "','DD/MM/YYYY')";
            case SQLSERVER -> "CONVERT(DATETIME,'" + dateToString(fecha, Formateos.TipoFecha.FECHA) + "',103)";
            default -> null;
        };
    }
}
