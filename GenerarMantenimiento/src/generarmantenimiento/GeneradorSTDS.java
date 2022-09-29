package generarmantenimiento;

import static generarmantenimiento.GeneradorBase.leerColumnas;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ihuegal
 */
public class GeneradorSTDS extends GeneradorBase {

    /**
     *
     * @param tabla
     * @param user
     * @param pass
     * @param url
     * @return
     */
    public static String generarST(String tabla, String user, String pass, String url) {
        try {
            ArrayList<GeneradorBase.InfoColumna> listaColumnas = leerColumnas(tabla, user, pass, url);
            if (listaColumnas == null) {
                return "VACIO";
            }

            StringBuilder plantilla = leerPlantilla("PLT-ST-DS.java.txt");
            HashMap<String, String> hmMascaras = generarMascarasServicioTecnico(tabla, listaColumnas);
            String plantillaMontada = reemplazarMascaras(plantilla, hmMascaras);

            return plantillaMontada;
        } catch (IOException ex) {
            Logger.getLogger(GeneradorSTDS.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    private static HashMap<String, String> generarMascarasServicioTecnico(String nombreTabla, ArrayList<GeneradorBase.InfoColumna> listaColumnas) {
        HashMap<String, String> ret = new HashMap<>();

        calcularNombresYTipos(listaColumnas);
        ArrayList<GeneradorBase.InfoColumna> listaColumnasInsertables = sacarColumnaInsertables(listaColumnas);

        ret.put("#NOMBRE_CLASE#", transformarNombreClase(nombreTabla));
        ret.put("#NOMBRE_CLASE_OBJETO#", transformarNombreObjeto(nombreTabla, true));
        ret.put("#NOMBRE_OBJETO#", transformarNombreObjeto(nombreTabla, false));
        ret.put("#NOMBRE_TABLA#", nombreTabla);
        ret.put("#PARAMETROS_FILTRO#", generarParametrosFiltro(listaColumnasInsertables));
        ret.put("#PARAMETROS_CLAVE_ITEM#", generarParametrosClaveItem(listaColumnasInsertables));
        ret.put("#PARAMETROS_ITEM#", generarParametrosItem(listaColumnasInsertables));
        ret.put("#RESTRICCIONES_ALTA#", generarRestriccionesAlta(listaColumnasInsertables));
        ret.put("#PARAMETROS_ALTA#", generarParametrosAlta(listaColumnasInsertables));
        ret.put("#RESTRICCIONES_ACTUALIZA#", generarRestriccionesActualiza(listaColumnasInsertables));
        ret.put("#PARAMETROS_ACTUALIZA#", generarParametrosActualiza(listaColumnasInsertables));
        ret.put("#RESTRICCIONES_BAJA#", generarRestriccionesBaja(listaColumnasInsertables));
        ret.put("#PARAMETROS_DELETE#", generarParametrosDelete(listaColumnasInsertables));

        return ret;
    }

    //FILTRO
    private static String generarParametrosFiltro(ArrayList<GeneradorBase.InfoColumna> listaColumnas) {
        StringBuilder sb = new StringBuilder();
        
        for (InfoColumna columna : listaColumnas)
        {
            //parametros.put("ID_USUARIO", filtroBdTUsuario.getIdUsuario());
            sb.append(ESPACIOS2)
                    .append("parametros.put(\"")
                    .append(columna.COLUMN_NAME)
                    .append("\", filtro")
                    .append(transformarNombreObjeto(columna.TABLE_NAME, true))
                    .append(".get")
                    .append(transformarNombrePrimeraMayusculas(columna.classColumnName))
                    .append("());\n");
        }
        
        return sb.toString();
    }
    
    //CLAVE_ITEM
    private static String generarParametrosClaveItem(ArrayList<GeneradorBase.InfoColumna> listaColumnas) {
        StringBuilder sb = new StringBuilder();
        
        for (InfoColumna columna : listaColumnas)
        {
            if (columna.esPrimary) {
                if (!sb.isEmpty()) {
                    sb.append(", ");
                }
                //Integer idUsuario, Integer idUnidad
                sb.append(columna.classColumnType).append(" ").append(columna.classColumnName);
            }
        }
        
        return sb.toString();
    }
    
    //ITEM
    private static String generarParametrosItem(ArrayList<GeneradorBase.InfoColumna> listaColumnas) {
        StringBuilder sb = new StringBuilder();
        
        for (InfoColumna columna : listaColumnas)
        {
            if (columna.esPrimary) {
                //filtroBdTUsuario.setIdUsuario(idUsuario);
                sb.append(ESPACIOS2)
                        .append("filtro")
                        .append(transformarNombreObjeto(columna.TABLE_NAME, true))
                        .append(".set")
                        .append(transformarNombrePrimeraMayusculas(columna.classColumnName))
                        .append("(")
                        .append(columna.classColumnName)
                        .append(");\n");
            }
        }
        
        return sb.toString();
    }
    
    // ALTA
    private static String generarRestriccionesAlta(ArrayList<GeneradorBase.InfoColumna> listaColumnas) {
        StringBuilder sb = new StringBuilder();
        
        for (InfoColumna columna : listaColumnas)
        {
            if (columna.NULLABLE.equalsIgnoreCase("NO") && !(columna.COLUMN_NAME.equalsIgnoreCase("USUARIOBD") || columna.COLUMN_NAME.equalsIgnoreCase("TSTBD"))) {
                /*
                if (Validation.isNullOrEmpty(newBdTUnidad.getCoUnidad())) {
                    throw new RequiredFieldException("CO_UNIDAD");
                } 
                */
                boolean validarBD = columna.esPrimary;
                if (validarBD) {
                    sb.append(ESPACIOS2)
                            .append("if(AppInit.TIPO_BASEDATOS == BaseDatos.ORACLE) {\n");
                }
                sb.append((validarBD ? ESPACIOS3 : ESPACIOS2))
                        .append("if (Validation.isNullOrEmpty(new")
                        .append(transformarNombreObjeto(columna.TABLE_NAME, true))
                        .append(".get")
                        .append(transformarNombrePrimeraMayusculas(columna.classColumnName))
                        .append("())) {\n")
                        .append((validarBD ? ESPACIOS4 : ESPACIOS3))
                        .append("throw new RequiredFieldException(\"")
                        .append(columna.COLUMN_NAME)
                        .append("\");\n")
                        .append((validarBD ? ESPACIOS3 : ESPACIOS2))
                        .append("}\n");
                if (validarBD) {
                    sb.append(ESPACIOS2)
                            .append("}\n");
                }
            }
        }
        
        return sb.toString();
    }

    private static String generarParametrosAlta(ArrayList<GeneradorBase.InfoColumna> listaColumnas) {
        StringBuilder sb = new StringBuilder();
        
        for (InfoColumna columna : listaColumnas)
        {            
            /*
            parametros.put("ID_UNIDAD", newBdTUnidad.getIdUnidad());
            */
            sb.append(ESPACIOS2)
                    .append("parametros.put(\"")
                    .append(columna.COLUMN_NAME)
                    .append("\", new")
                    .append(transformarNombreObjeto(columna.TABLE_NAME, true))
                    .append(".get")
                    .append(transformarNombrePrimeraMayusculas(columna.classColumnName))
                    .append("());\n");
        }
        
        return sb.toString();
    }
	
    // UPDATE
    private static String generarRestriccionesActualiza(ArrayList<GeneradorBase.InfoColumna> listaColumnas) {
        StringBuilder sb = new StringBuilder();
        
        for (InfoColumna columna : listaColumnas)
        {
            if (columna.NULLABLE.equalsIgnoreCase("NO") && !(columna.COLUMN_NAME.equalsIgnoreCase("USUARIOBD")  || columna.COLUMN_NAME.equalsIgnoreCase("TSTBD"))) {
                /*
                if (Validation.isNullOrEmpty(upBdTUnidad.getIdUnidad())) {
                    throw new RequiredFieldException("ID_UNIDAD");
                }
                */
                sb.append(ESPACIOS2)
                        .append("if (Validation.isNullOrEmpty(up")
                        .append(transformarNombreObjeto(columna.TABLE_NAME, true))
                        .append(".get")
                        .append(transformarNombrePrimeraMayusculas(columna.classColumnName))
                        .append("())) {\n")
                        .append(ESPACIOS3)
                        .append("throw new RequiredFieldException(\"")
                        .append(columna.COLUMN_NAME)
                        .append("\");\n")
                        .append(ESPACIOS2)
                        .append("}\n");
            }
        }
        
        return sb.toString();
    }

    private static String generarParametrosActualiza(ArrayList<GeneradorBase.InfoColumna> listaColumnas) {
        StringBuilder sb = new StringBuilder();
        
        for (InfoColumna columna : listaColumnas)
        {            
            /*
            parametros.put("ID_UNIDAD", upBdTUnidad.getIdUnidad());
            */
            sb.append(ESPACIOS2)
                    .append("parametros.put(\"")
                    .append(columna.COLUMN_NAME)
                    .append("\", up")
                    .append(transformarNombreObjeto(columna.TABLE_NAME, true))
                    .append(".get")
                    .append(transformarNombrePrimeraMayusculas(columna.classColumnName))
                    .append("());\n");
        }
        
        return sb.toString();
    }

    //DELETE
    private static String generarRestriccionesBaja(ArrayList<GeneradorBase.InfoColumna> listaColumnas) {
        StringBuilder sb = new StringBuilder();
        
        for (InfoColumna columna : listaColumnas)
        {
            if (columna.esPrimary) {
                /*
                if (Validation.isNullOrEmpty(upBdTUnidad.getIdUnidad())) {
                    throw new RequiredFieldException("ID_UNIDAD");
                }
                */
                sb.append(ESPACIOS2)
                        .append("if (Validation.isNullOrEmpty(del")
                        .append(transformarNombreObjeto(columna.TABLE_NAME, true))
                        .append(".get")
                        .append(transformarNombrePrimeraMayusculas(columna.classColumnName))
                        .append("())) {\n")
                        .append(ESPACIOS3)
                        .append("throw new RequiredFieldException(\"")
                        .append(columna.COLUMN_NAME)
                        .append("\");\n")
                        .append(ESPACIOS2)
                        .append("}\n");
            }
        }
        
        return sb.toString();
    }

    private static String generarParametrosDelete(ArrayList<GeneradorBase.InfoColumna> listaColumnas) {
        StringBuilder sb = new StringBuilder();
        
        for (InfoColumna columna : listaColumnas)
        {     
            if (columna.esPrimary) {
                /*
                parametros.put("ID_UNIDAD", delBdTUnidad.getIdUnidad());
                */
                sb.append(ESPACIOS2)
                        .append("parametros.put(\"")
                        .append(columna.COLUMN_NAME)
                        .append("\", del")
                        .append(transformarNombreObjeto(columna.TABLE_NAME, true))
                        .append(".get")
                        .append(transformarNombrePrimeraMayusculas(columna.classColumnName))
                        .append("());\n");
            }
        }
        
        return sb.toString();
    }
    
    private static String reemplazarMascaras(StringBuilder plantilla, HashMap<String, String> mascaras) {
        for (Map.Entry<String, String> valor : mascaras.entrySet()) {
            int index = 0;
            while ((index = plantilla.indexOf(valor.getKey(), index)) >= 0) {
                plantilla.replace(index, index + valor.getKey().length(), valor.getValue());
            }
        }

        return plantilla.toString();
    }

    public static String transformarNombreClase(String nombre) {

        return GeneradorBase.transformarNombreClase(nombre);
    }
}
