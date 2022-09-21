package generarmantenimiento;

import com.microsoft.sqlserver.jdbc.SQLServerDriver;
import static generarmantenimiento.GeneradorSTDS.config;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author omorjim
 */
public abstract class GeneradorBase
{
    
    protected static final Logger logger = Logger.getLogger(GeneradorBase.class.getName());

    /**
     *
     */
    public static final LanzadorConfig config = new LanzadorConfig();
    protected static final String SALTO_LINEA = "\r\n";
    protected static final String ESPACIOS = "    ";
    protected static final String ESPACIOS2 = ESPACIOS + ESPACIOS;
    protected static final String ESPACIOS3 = ESPACIOS + ESPACIOS + ESPACIOS;
    protected static final String ESPACIOS4 = ESPACIOS + ESPACIOS + ESPACIOS + ESPACIOS;
    protected static final String ESPACIOS5 = ESPACIOS + ESPACIOS + ESPACIOS + ESPACIOS + ESPACIOS;

    protected static class InfoColumna {

        public String TABLE_NAME;
        public String COLUMN_NAME;
        public String DATA_TYPE;
        public int DATA_LENGTH;
        public int DATA_PRECISION;
        public int DATA_SCALE;
        public String NULLABLE;
        public boolean esPrimary;

        //Calculados
        public String classColumnName;
        public String classColumnTypeComentario;
        public String classColumnType;
    }
        
    protected static ArrayList<InfoColumna> leerColumnas(String tabla, String user, String pass, String url) {
        Connection conn = null;

        try {
            conn = getConexion(user, pass, url);
            Statement st = conn.createStatement();
            ResultSet result = st.executeQuery(
                    "SELECT c.TABLE_NAME, c.COLUMN_NAME, c.DATA_TYPE, c.CHARACTER_MAXIMUM_LENGTH as DATA_LENGTH,\n" +
                    "    c.NUMERIC_PRECISION as DATA_PRECISION, c.NUMERIC_SCALE as DATA_SCALE, c.IS_NULLABLE as NULLABLE,\n" +
                    "    ( \n" +
                    "        SELECT count(1) \n" +
                    "        FROM \n" +
                    "            INFORMATION_SCHEMA.CONSTRAINT_COLUMN_USAGE ccu\n" +
                    "        INNER JOIN\n" +
                    "            INFORMATION_SCHEMA.TABLE_CONSTRAINTS tc ON (tc.CONSTRAINT_NAME = ccu.CONSTRAINT_NAME\n" +
                    "                                                    AND tc.CONSTRAINT_SCHEMA = ccu.CONSTRAINT_SCHEMA\n" +
                    "                                                    AND tc.CONSTRAINT_CATALOG = ccu.CONSTRAINT_CATALOG)\n" +
                    "        WHERE ccu.TABLE_NAME = c.TABLE_NAME \n" +
                    "            AND tc.CONSTRAINT_TYPE = 'PRIMARY KEY' \n" +
                    "            AND ccu.COLUMN_NAME = c.COLUMN_NAME  \n" +
                    "    ) esPrimary \n" +
                    "FROM INFORMATION_SCHEMA.COLUMNS c \n" +
                    "WHERE c.table_name = '" + tabla + "'\n" +
                    "order by c.ORDINAL_POSITION asc"
            );

            boolean boTieneId = false;
            
            ArrayList<InfoColumna> listaColumnas = new ArrayList<>();
            while (result.next()) {
                InfoColumna info = new InfoColumna();
                info.TABLE_NAME = result.getString("TABLE_NAME");
                info.COLUMN_NAME = result.getString("COLUMN_NAME");
                info.DATA_TYPE = result.getString("DATA_TYPE");
                info.DATA_LENGTH = result.getInt("DATA_LENGTH");
                info.DATA_PRECISION = result.getInt("DATA_PRECISION");
                info.DATA_SCALE = result.getInt("DATA_SCALE");
                info.NULLABLE = result.getString("NULLABLE");
                info.esPrimary = result.getBoolean("esPrimary");
                boTieneId |= info.esPrimary;
                
                listaColumnas.add(info);
            }
            
            if(!boTieneId)
            {
                //Si no encuentro una columna que sea clave primaria, busco una que se llame
                //igual que la tabla y la fuerzo a ser clave.
                //Esto viene por un problema con la BBDD de FOR y las tablas creadas nuevas,
                //no detecta la clave correctamente (mientras PRE o PRO las pilla bien cuando
                //estas tablas llegan a estas BBDDs
                for(GeneradorBase.InfoColumna col : listaColumnas)
                {
                    if(col.COLUMN_NAME.startsWith("ID_")
                        && col.NULLABLE.equals("NO")
                        && col.TABLE_NAME.substring(5).equals(col.COLUMN_NAME.substring(3)))
                    {
                        //Busco una columna que se llame como la tabla
                        logger.log(Level.INFO, "Fuerzo la columna {0} como clave", col.COLUMN_NAME);
                        col.esPrimary = true;
                        break;
                    }
                }
            }

            if (listaColumnas.isEmpty()) {
                throw new IllegalArgumentException("No se han encontrado columnas para: " + tabla);
            }

//            conn.commit();
            return listaColumnas;
        } catch (Exception ex) {
            Logger.getLogger(GeneradorJPABean.class.getName()).log(Level.SEVERE, "ERROR", ex);

            try {
                Thread.sleep(100); //Al escribir en err, lo que llegue después en out puede mezclarse
            } catch (Exception ex1) {

            }

            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException ex1) {
                Logger.getLogger(GeneradorJPABean.class.getName()).log(Level.WARNING, null, ex1);
            }

            return null;
        } finally {
            cierraConexion(conn);
        }
    }
    
    protected static StringBuilder leerPlantilla(String plantilla) throws IOException {
        try (InputStream stream = GeneradorSTDS.class.getResourceAsStream(plantilla)) {
            byte[] buffer = new byte[stream.available()];
            stream.read(buffer);

            String strBuffer = new String(buffer, "UTF-8");
            return new StringBuilder(strBuffer);
        }
    }
    
    protected static void calcularNombresYTipos(ArrayList<InfoColumna> listaColumnas) {
        for (InfoColumna columna : listaColumnas) {
            calcularNombreYTipo(columna);
        }
    }

    protected static void calcularNombreYTipo(InfoColumna columna) {
        columna.classColumnName = transformarNombreObjeto(columna.COLUMN_NAME, false);

        switch (columna.DATA_TYPE.toUpperCase()) {
            case "VARCHAR2": 
            case "VARCHAR": {
                columna.classColumnType = "String";
                columna.classColumnTypeComentario = "Columna " + columna.COLUMN_NAME + " (" + columna.DATA_TYPE + " max: " + (columna.DATA_LENGTH / 4) + ")";
                break;
            }

            case "NUMBER":
            case "NUMERIC": {
                if (columna.DATA_SCALE > 0) {
                    columna.classColumnType = "BigDecimal";
                    columna.classColumnTypeComentario = "Columna " + columna.COLUMN_NAME + " (" + columna.DATA_TYPE + " parte entera: " + columna.DATA_PRECISION + ", parte decimal: " + columna.DATA_SCALE + ")";
                } else if (columna.DATA_PRECISION == 1 && columna.COLUMN_NAME.startsWith("BO_")) {
                    columna.classColumnType = "Boolean";
                    columna.classColumnTypeComentario = "Columna " + columna.COLUMN_NAME + " (" + columna.DATA_TYPE + " parte entera: " + columna.DATA_PRECISION + ")";
                } else {
                    columna.classColumnType = "Integer";
                    columna.classColumnTypeComentario = "Columna " + columna.COLUMN_NAME + " (" + columna.DATA_TYPE + " parte entera: " + columna.DATA_PRECISION + ")";
                }
                break;
            }
            
            case "BIT": {
                columna.classColumnType = "Boolean";
                columna.classColumnTypeComentario = "Columna " + columna.COLUMN_NAME + " (" + columna.DATA_TYPE + " parte entera: " + columna.DATA_PRECISION + ")";
                break;
            }
            
            case "DATE": 
            case "DATETIME": {
                columna.classColumnType = "Date";
                columna.classColumnTypeComentario = "Columna " + columna.COLUMN_NAME + " (" + columna.DATA_TYPE + ")";
                break;
            }

            case "TIMESTAMP(6)": {
                columna.classColumnType = "Date";
                columna.classColumnTypeComentario = "Columna " + columna.COLUMN_NAME + " (" + columna.DATA_TYPE + ")";
                break;
            }

            case "IMAGE":
            case "BLOB": {
                columna.classColumnType = "byte[]";
                columna.classColumnTypeComentario = "Columna " + columna.COLUMN_NAME + " (" + columna.DATA_TYPE + ")";
                break;
            }
            
            case "CLOB": {
                columna.classColumnType = "String";
                columna.classColumnTypeComentario = "Columna " + columna.COLUMN_NAME + " (" + columna.DATA_TYPE  + " max: unlimited)";
                break;
            }

            default: {
                System.err.println("Tipo de columna no identificada: " + columna.DATA_TYPE);

                columna.classColumnType = "Object";
                columna.classColumnTypeComentario = "Columna " + columna.COLUMN_NAME + " (" + columna.DATA_TYPE + " {[/!\\] TIPO NO IDENTIFICADO [/!\\]})";
                break;
            }
        }

        if (columna.esPrimary) {
            columna.classColumnTypeComentario += " [PRIMARY]";
        }
        if (!columna.NULLABLE.equals("YES")) {
            columna.classColumnTypeComentario += " [NOT NULL]";
        }
    }

    protected static ArrayList<InfoColumna> sacarColumnaInsertables(ArrayList<InfoColumna> listaColumnas) {
        ArrayList<InfoColumna> ret = new ArrayList<>();
        for (InfoColumna columna : listaColumnas) {
            if (permiteInsertar(columna)) {
                ret.add(columna);
            }
        }
        return ret;
    }

    protected static boolean permiteInsertar(InfoColumna columna) {
        switch (columna.DATA_TYPE.toUpperCase()) {
            case "VARCHAR2":
            case "VARCHAR":
            case "NUMBER":
            case "NUMERIC":
            case "BIT":
            case "DATE":
            case "DATETIME":
            case "TIMESTAMP(6)":
            case "IMAGE":
            case "BLOB":
            case "CLOB":
                return true;

            default:
                return false;
        }
    }

    /**
     * Cambia el nombre de una tabla o una columna: - pone todo en minúsculas -
     * pone en mayúsculas la letra después de un guión bajo - quita los guiones
     * bajos - (opcional) deja la primera letra en mayúsculas
     *
     * @param nombre
     * @param primeraMayusculas
     * @return
     */
    protected static String transformarNombreObjeto(String nombre, boolean primeraMayusculas) {
        StringBuilder sb = new StringBuilder(nombre.toLowerCase());

        int index = 0;
        while ((index = sb.indexOf("_", index)) >= 0) {
            String letra = "";
            if (sb.length() > index + 1) {
                letra += sb.charAt(index + 1);
            }
            sb.replace(index, index + 2, letra.toUpperCase());
        }

        if (primeraMayusculas) {
            transformarNombrePrimeraMayusculas(sb);
        }

        return sb.toString();
    }

    protected static String transformarNombreClase(String nombre) {
        StringBuilder nombreServicio = new StringBuilder();
        nombreServicio.append("St");
        String[] partesTabla = nombre.split("_");
        String letraTabla = partesTabla[1];
        String nTabla = partesTabla[2].toLowerCase();
        nTabla = transformarNombrePrimeraMayusculas(nTabla);
        nombreServicio.append(letraTabla).append(nTabla);

        return nombreServicio.toString();
    }
    /**
     *
     * @param nombre
     * @return
     */
    protected static String transformarNombrePrimeraMayusculas(String nombre) {
        StringBuilder sb = new StringBuilder(nombre);
        sb = transformarNombrePrimeraMayusculas(sb);
        return sb.toString();
    }

    protected static StringBuilder transformarNombrePrimeraMayusculas(StringBuilder sb) {
        sb.replace(0, 1, (sb.charAt(0) + "").toUpperCase());
        return sb;
    }

    protected static String generarImports(ArrayList<InfoColumna> listaColumnas, boolean boJPA) {
        HashSet<String> hsImports = new HashSet<>();

        for (InfoColumna columna : listaColumnas) {
            switch (columna.DATA_TYPE) {
                case "VARCHAR2":
                case "VARCHAR":
                    //No es necesario
                    break;
                case "NUMBER": 
                case "NUMBERIC": {
                    if (columna.DATA_SCALE > 0) {
                        hsImports.add("java.math.BigDecimal");
                    } else if (columna.DATA_PRECISION != 1) {
                        hsImports.add("java.math.BigDecimal");
                    }
                    break;
                }
                case "DATE":
                case "DATETIME":
                    if (boJPA)
                    {
                        hsImports.add("java.util.Date");
                        if(!columna.COLUMN_NAME.equalsIgnoreCase("TSTBD"))
                            hsImports.add("utilidades.Formateos");
                    }
                    break;
                case "TIMESTAMP(6)":
                    if (boJPA)
                    {
                        hsImports.add("java.util.Date");
                        if(!columna.COLUMN_NAME.equalsIgnoreCase("TSTBD"))
                            hsImports.add("utilidades.Formateos");
                    }
                    break;
                case "BLOB":
                case "IMAGE":
                    if (boJPA)
                    {
                        hsImports.add("java.sql.Blob");
                    }
                    break;
                case "CLOB":
                    if (boJPA)
                    {
                        hsImports.add("java.sql.Clob");
                        hsImports.add("java.sql.SQLException");
                    }
                    break;
                default: {
                    System.err.println("Tipo no soportado: " + columna.DATA_TYPE);
                    break;
                }
            }
        }

        StringBuilder sb = new StringBuilder();

        for (String sImport : hsImports) {
            sb.append("import ").append(sImport).append(";").append(SALTO_LINEA);
        }

        return sb.toString();
    }

    protected static Connection getConexion(String user, String pass, String url) throws Exception {
        Connection conn = null;
        try {
            DriverManager.registerDriver(new SQLServerDriver());
            conn = DriverManager.getConnection(url, user, pass);
            return conn;
        } catch (Exception e) {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (Exception ex) {
                logger.log(Level.WARNING, "ERROR al cerrar conexión: " + config, ex);
            }

            throw e;
        }
    }

    protected static void cierraConexion(Connection conn) {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (Exception ex) {
            logger.log(Level.WARNING, "ERROR al cerrar conexión: " + config, ex);
        }
    }
}
