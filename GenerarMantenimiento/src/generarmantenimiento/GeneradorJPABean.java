package generarmantenimiento;
import static generarmantenimiento.GeneradorBase.leerColumnas;
import static generarmantenimiento.GeneradorBase.transformarNombreObjeto;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Óscar
 */
public class GeneradorJPABean extends GeneradorBase {
    
    /**
     *
     * @param tabla
     * @param paquete
     * @param autor
     * @param user
     * @param pass
     * @param url
     * @return
     */
    public static String generarJPABean(String tabla, String paquete, String autor, String user, String pass, String url) {
        try {
            ArrayList<GeneradorBase.InfoColumna> listaColumnas = leerColumnas(tabla, user, pass, url);
            if (listaColumnas == null) {
                return "VACIO";
            }

            if (!paquete.startsWith("sidae.")) {
                paquete = "sidae.jpa.beans." + paquete;
            }
            StringBuilder plantilla = leerPlantilla("PLT-JPA-BEAN.java.txt");
            HashMap<String, String> hmMascaras = generarMascarasJPABeans(paquete, tabla, autor, listaColumnas);
            String plantillaMontada = reemplazarMascaras(plantilla, hmMascaras);
            return plantillaMontada;
        } catch (IOException ex) {
            Logger.getLogger(GeneradorJPABean.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    private static HashMap<String, String> generarMascarasJPABeans(String paquete,
            String nombreTabla, String autor, ArrayList<GeneradorBase.InfoColumna> listaColumnas) {
        HashMap<String, String> ret = new HashMap<>();

        calcularNombresYTipos(listaColumnas);
        ArrayList<GeneradorBase.InfoColumna> listaColumnasInsertables = sacarColumnaInsertables(listaColumnas);
        GeneradorBase.InfoColumna identificadorTabla = listaColumnasInsertables.get(0);

        ret.put("#PAQUETE#", paquete);
        ret.put("#NOMBRE_TABLA#", nombreTabla);
        ret.put("#IDENTIFICADOR_TABLA#", identificadorTabla.classColumnName);
        ret.put("#AUTOR#", autor);
        ret.put("#IMPORTS#", generarImports(listaColumnas, true));
        ret.put("#NOMBRE_CLASE#", transformarNombreObjeto(nombreTabla, true));
        ret.put("#ATRIBUTOS_CLASE#", generarAtributos(listaColumnas));
        ret.put("#CAMPOS_GET_SET#", generarCamposGetSet(listaColumnas));
        ret.put("#MAPEO_ATRIBUTOS#", generarMapeoAtributos(listaColumnas));
        ret.put("#SQL_INSERT_CAMPOS#", generarInsertCampos(listaColumnasInsertables));
        ret.put("#SQL_INSERT_ATRIBUTOS#", generarInsertAtributos(nombreTabla, listaColumnasInsertables));
        ret.put("#SQL_CAMPOS#", generarSqlCampos(listaColumnasInsertables));
        ret.put("#RESTRICCIONES_FILTRO#", generarRestriccionesFiltro(listaColumnasInsertables));

        
        ret.put("#SQL_UPDATE_ATRIBUTOS#", generarUpdateAtributos(listaColumnasInsertables));
        ret.put("#WHERE_IDENTIFICADORES#", generarWhereIdentificadores(listaColumnas));
        ret.put("#ATRIBUTOS_IDENTIFICADORES#", generarAtributosIdentificadores(listaColumnas));

        return ret;
    }

    private static String generarAtributos(ArrayList<GeneradorBase.InfoColumna> listaColumnas) {
        StringBuilder sb = new StringBuilder();

        for (GeneradorBase.InfoColumna columna : listaColumnas) {
            sb.append("/** ").append(columna.classColumnTypeComentario).append(" */").append(SALTO_LINEA).append(ESPACIOS);
            sb.append("private ").append(columna.classColumnType).append(" ").append(columna.classColumnName).append(";").append(SALTO_LINEA).append(ESPACIOS);
        }

        return sb.toString();
    }

    private static String generarCamposGetSet(ArrayList<GeneradorBase.InfoColumna> listaColumnas) {
        StringBuilder sb = new StringBuilder();

        for (GeneradorBase.InfoColumna columna : listaColumnas) {
            String classColumnNameMayus = transformarNombrePrimeraMayusculas(columna.classColumnName);

            ///**
            // * Devuelve el campo de mismo nombre
            // * @return idAuxdetdieage
            // */
            //public Integer getIdAuxdetdieage()
            //{
            //    return idAuxdetdieage;
            //}
            String getOrIs = "get";
            if (columna.classColumnType.equals("Boolean")) {
                getOrIs = "is";
            }

            //Campo get
            sb.append("/**").append(SALTO_LINEA).append(ESPACIOS);
            sb.append(" * Devuelve el campo de mismo nombre").append(SALTO_LINEA).append(ESPACIOS);
            sb.append(" * @return ").append(columna.classColumnName).append(SALTO_LINEA).append(ESPACIOS);
            sb.append(" */").append(SALTO_LINEA).append(ESPACIOS);
            sb.append("public ").append(columna.classColumnType).append(" ").append(getOrIs).append(classColumnNameMayus).append("()").append(SALTO_LINEA).append(ESPACIOS);
            sb.append("{").append(SALTO_LINEA).append(ESPACIOS);
            sb.append(ESPACIOS).append("return ").append(columna.classColumnName).append(";").append(SALTO_LINEA).append(ESPACIOS);
            sb.append("}").append(SALTO_LINEA).append(ESPACIOS);
            sb.append(SALTO_LINEA).append(ESPACIOS);
            
            ///**
            // * Función que devuelve el valor del atributo de igual nombre.
            // */
            //public String getFeFinaldetdietasFormat()
            //{
            //    if(feFinaldetdietas == null){
            //        return null;
            //    }
            //    return Formateos.dateToString(feFinaldetdietas, Formateos.Tipo.FECHA__CON_BARRAS);
            //}
            
            //Campo get con formato para fechas
            if(columna.classColumnType.equals("Date") && !columna.COLUMN_NAME.equalsIgnoreCase("TSTBD"))
            {
                sb.append("/**").append(SALTO_LINEA).append(ESPACIOS);
                sb.append(" * Devuelve el campo de mismo nombre formateado").append(SALTO_LINEA).append(ESPACIOS);
                sb.append(" * @return ").append(columna.classColumnName).append(SALTO_LINEA).append(ESPACIOS);
                sb.append(" */").append(SALTO_LINEA).append(ESPACIOS);
                sb.append("public String get").append(classColumnNameMayus).append("Format()").append(SALTO_LINEA).append(ESPACIOS);
                sb.append("{").append(SALTO_LINEA).append(ESPACIOS);
                sb.append(ESPACIOS).append("if(").append(columna.classColumnName).append(" == null)").append(SALTO_LINEA).append(ESPACIOS);
                sb.append(ESPACIOS).append(ESPACIOS).append("return null;").append(SALTO_LINEA).append(ESPACIOS);
                sb.append(ESPACIOS).append("return Formateos.dateToString(").append(columna.classColumnName).append(", Formateos.Tipo.FECHA__CON_BARRAS);").append(SALTO_LINEA).append(ESPACIOS);
                sb.append("}").append(SALTO_LINEA).append(ESPACIOS);
                sb.append(SALTO_LINEA).append(ESPACIOS);
            }

            ///**
            // * Establece el campo de mismo nombre
            // * @param idAuxdetdieage Valor a establecer
            // */
            //public void setIdAuxdetdieage(Integer idAuxdetdieage)
            //{
            //    this.idAuxdetdieage = idAuxdetdieage;
            //}
            //Campo set
            sb.append("/**").append(SALTO_LINEA).append(ESPACIOS);
            sb.append(" * Establece el campo de mismo nombre").append(SALTO_LINEA).append(ESPACIOS);
            sb.append(" * @param ").append(columna.classColumnName).append(" Valor a establecer").append(SALTO_LINEA).append(ESPACIOS);
            sb.append(" */").append(SALTO_LINEA).append(ESPACIOS);
            sb.append("public void set").append(classColumnNameMayus).append("(").append(columna.classColumnType).append(" ").append(columna.classColumnName).append(")").append(SALTO_LINEA).append(ESPACIOS);
            sb.append("{").append(SALTO_LINEA).append(ESPACIOS);
            sb.append(ESPACIOS).append("this.").append(columna.classColumnName).append(" = ").append(columna.classColumnName).append(";").append(SALTO_LINEA).append(ESPACIOS);
            sb.append("}").append(SALTO_LINEA).append(ESPACIOS);
            sb.append(SALTO_LINEA).append(ESPACIOS);
        }

        return sb.toString();
    }

    private static String generarMapeoAtributos(ArrayList<GeneradorBase.InfoColumna> listaColumnas) {
        StringBuilder sb = new StringBuilder();

        for (GeneradorBase.InfoColumna columna : listaColumnas) {
            //idAuxdetdieage = bld[i] == null ? null : ((BigDecimal)bld[i]).intValue(); i++;
            //dsAuxiliarDetDietas = bld[i] == null ? null : bld[i].toString(); i++;
            //imImporte = bld[i] == null ? null : (BigDecimal)bld[i]; i++;
            //date = bld[i] == null ? null : (Date)bld[i]; i++;

//            if ((columna.DATA_TYPE.equals("BLOB") || columna.DATA_TYPE.equals("CLOB"))) {
//                sb.append("/*");
//            }

            if (columna.DATA_TYPE.equals("CLOB"))
            {
                sb.append("try")
                  .append(SALTO_LINEA).append(ESPACIOS4).append("{")
                  .append(SALTO_LINEA).append(ESPACIOS5);
            }
            sb.append(columna.classColumnName).append(" = bld[i] == null ? null : ");

            switch (columna.DATA_TYPE) {
                case "VARCHAR2":
                    sb.append("bld[i].toString()");
                    break;
                case "NUMBER": {
                    if (columna.DATA_SCALE > 0) {
                        sb.append("(BigDecimal)bld[i]");
                    } else if (columna.DATA_PRECISION == 1 && columna.COLUMN_NAME.startsWith("BO_")) {
                        sb.append("((BigDecimal)bld[i]).intValue() == 1");
                    } else {
                        sb.append("((BigDecimal)bld[i]).intValue()");
                    }
                    break;
                }
                case "DATE":
                    sb.append("(Date)bld[i]");
                    break;
                case "BLOB":
                    sb.append("((Blob) bld[i]).getBytes(1, (int) ((Blob)bld[i]).length())");
                    break;
                case "CLOB":
                    sb.append("((Clob) bld[i]).getSubString(1, (int) ((Clob) bld[i]).length())");
                    break;
                default: {
                    System.err.println("Tipo no soportado: " + columna.DATA_TYPE);
                    sb.append("null");
                    break;
                }
            }

            sb.append(";");

            sb.append(" i++;");
            if (columna.DATA_TYPE.equals("CLOB"))
            {
                sb.append(SALTO_LINEA).append(ESPACIOS4).append("}")
                  .append(SALTO_LINEA).append(ESPACIOS4).append("catch (SQLException ex)")
                  .append(SALTO_LINEA).append(ESPACIOS4).append("{")
                  .append(SALTO_LINEA).append(ESPACIOS5).append("//NADA")
                  .append(SALTO_LINEA).append(ESPACIOS4).append("}");
                        
            }
            sb.append(SALTO_LINEA).append(ESPACIOS4);
        }

        return sb.toString();
    }

    private static String generarWhereIdentificadores(ArrayList<GeneradorBase.InfoColumna> listaColumnas) {
        StringBuilder sb = new StringBuilder("sb.append(\" WHERE \");").append(SALTO_LINEA).append(ESPACIOS2);
        int numIndicadores=0;
        for (GeneradorBase.InfoColumna columna : listaColumnas) {
            if (columna.esPrimary) {
                if (numIndicadores > 0) {
                    sb.append("sb.append(\"AND ");
                } else {
                    sb.append("sb.append(\"");
                }
                
                sb.append(columna.COLUMN_NAME).append(" = \").append(").append("\"").append(":").append(columna.COLUMN_NAME).append("\"").append(");").append(SALTO_LINEA).append(ESPACIOS2);
                numIndicadores+=1;
            }
        }
        return sb.toString();
    }

    private static String generarAtributosIdentificadores(ArrayList<GeneradorBase.InfoColumna> listaColumnas) {
        StringBuilder sb = new StringBuilder();

        for (GeneradorBase.InfoColumna columna : listaColumnas) {
            if (columna.esPrimary) {
                if (sb.length() > 0) {
                    sb.append(" + \" , \" + ");
                }
                sb.append(columna.classColumnName);
            }
        }

        return sb.toString();
    }

    private static String generarInsertCampos(ArrayList<GeneradorBase.InfoColumna> listaColumnas) {
        StringBuilder sb = new StringBuilder();

        if (listaColumnas.size() > 0) {
            sb.append("sb.append(\"").append(listaColumnas.get(0).COLUMN_NAME).append("\");").append(SALTO_LINEA).append(ESPACIOS2);
            for (int i = 1; i < listaColumnas.size(); i++) {
                sb.append("sb.append(\"").append(",").append(listaColumnas.get(i).COLUMN_NAME).append("\");").append(SALTO_LINEA).append(ESPACIOS2);
            }
        }

        return sb.toString();
    }

    private static String generarInsertAtributos(String nombreTabla, ArrayList<GeneradorBase.InfoColumna> listaColumnas) {
        StringBuilder sb = new StringBuilder();

        if (listaColumnas.size() > 0) {
            sb.append("if(").append(generarGetColumna(listaColumnas.get(0))).append("==null) {").append(SALTO_LINEA).append(ESPACIOS2);
            sb.append("  sb.append(\" S").append(nombreTabla).append(".nextval \");").append(SALTO_LINEA).append(ESPACIOS2);
            sb.append("} else {").append(SALTO_LINEA).append(ESPACIOS2);
            sb.append("  sb.append(").append("\"").append(":").append(listaColumnas.get(0).COLUMN_NAME).append("\"").append(");").append(SALTO_LINEA).append(ESPACIOS2);
            sb.append("}").append(SALTO_LINEA).append(ESPACIOS2);
            
            for (int i = 1; i < listaColumnas.size(); i++) {
                sb.append(SALTO_LINEA).append(ESPACIOS2);
                sb.append("sb.append(\",\").append(").append("\"").append("DECODE(").append(":").append(listaColumnas.get(i).COLUMN_NAME).append(", null, NULL, ").append(":").append(listaColumnas.get(i).COLUMN_NAME).append(")").append("\"").append(");");
            }
        }

        return sb.toString();
    }

    private static String generarInsertAtributos_valor(GeneradorBase.InfoColumna columna) {
        if (columna.COLUMN_NAME.equalsIgnoreCase("tstbd")) {
            return "\"sysdate\"";
        }

        switch (columna.DATA_TYPE) {
            case "VARCHAR2":
                if (columna.NULLABLE.equals("Y")) {
                    return "(" + columna.classColumnName + " == null ? \"null\" : \"'\" + " + columna.classColumnName + " + \"'\")";
                } else {
                    return "\"'\").append(" + columna.classColumnName + ").append(\"'\"";
                }
            case "NUMBER": {
                if (columna.DATA_SCALE > 0) {
                    return columna.classColumnName;
                } else if (columna.DATA_PRECISION == 1 && columna.COLUMN_NAME.startsWith("BO_")) {
                    if (columna.NULLABLE.equals("Y")) {
                        return "(" + columna.classColumnName + " == null ? \"null\" : " + columna.classColumnName + " ? 1 : 0)";
                    } else {
                        return "(" + columna.classColumnName + " ? 1 : 0)";
                    }
                } else {
                    return columna.classColumnName;
                }
            }
            case "DATE":
                return "Formateos.dateToCompareInQuery(" + columna.classColumnName + ", Formateos.TipoQuery.QUERY_FECHA_Y_HORA__CON_BARRAS_Y_PUNTOS, false)";
            case "BLOB":
            case "CLOB":
            default:
                return columna.classColumnName;
        }
    }

    private static String generarUpdateAtributos(ArrayList<GeneradorBase.InfoColumna> listaColumnas) {
        StringBuilder sb = new StringBuilder();

        ArrayList<GeneradorBase.InfoColumna> listaColumnasSinPK = new ArrayList<>();
        for (GeneradorBase.InfoColumna columna : listaColumnas) {
            if (!columna.esPrimary) {
                listaColumnasSinPK.add(columna);
            }
        }

        if (listaColumnasSinPK.size() > 0) {
            sb.append("sb.append(\"");
            generarUpdateAtributos_unaColumna(sb, listaColumnasSinPK.get(0));
            for (int i = 1; i < listaColumnasSinPK.size(); i++) {
               sb.append(SALTO_LINEA).append(ESPACIOS2);
               sb.append("sb.append(\",");
               generarUpdateAtributos_unaColumna(sb, listaColumnasSinPK.get(i));
            }
        }

        return sb.toString();
    }

    private static void generarUpdateAtributos_unaColumna(StringBuilder sb, GeneradorBase.InfoColumna columna) {
        sb.append(columna.COLUMN_NAME).append(" = \").append(")
                .append("\"").append("DECODE(").append(":").append(columna.COLUMN_NAME).append(", null, NULL, ").append(":").append(columna.COLUMN_NAME).append(")").append("\"").append(");");
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
    
    private static String generarGetColumna(GeneradorBase.InfoColumna columna) {
        StringBuilder sb = new StringBuilder();
        String classColumnNameMayus = transformarNombrePrimeraMayusculas(columna.classColumnName);
        String getOrIs = "get";
        if (columna.classColumnType.equals("Boolean")) {
            getOrIs = "is";
        }
        sb.append(getOrIs).append(classColumnNameMayus).append("()");
        String getter = sb.toString();
        return getter;
    }

    private static String generarRestriccionesFiltro(ArrayList<GeneradorBase.InfoColumna> listaColumnas) {
        StringBuilder sb = new StringBuilder();
        char comillas = '\"';
        for (int i = 0; i < listaColumnas.size(); i++) {
            GeneradorBase.InfoColumna columna = listaColumnas.get(i);
            if(columna.COLUMN_NAME.equals("TSTBD") || columna.COLUMN_NAME.equals("USUARIOBD"))
                continue;
            
            if (i > 0) {
                sb.append(ESPACIOS2);
            }
            sb.append("if (").append(columna.classColumnName).append(" != null) {").append(SALTO_LINEA);
            sb.append(ESPACIOS3).append("sb.append(").append(comillas).append(" AND ")
                    .append(columna.COLUMN_NAME).append(" = ").append(":").append(columna.COLUMN_NAME).append(comillas).append(");");
            sb.append(SALTO_LINEA).append(ESPACIOS2).append("}").append(SALTO_LINEA);
        }
        return sb.toString();
    }
    
    private static String generarSqlCampos(ArrayList<GeneradorBase.InfoColumna> listaColumnas) {
        StringBuilder sb = new StringBuilder();

        if (listaColumnas.size() > 0) {
            sb.append("sb.append(").append("\"").append(listaColumnas.get(0).COLUMN_NAME).append("\");").append(SALTO_LINEA).append(ESPACIOS2);
            for (int i = 1; i < listaColumnas.size(); i++) {
                sb.append("sb.append(").append("\"").append(",").append(listaColumnas.get(i).COLUMN_NAME).append("\");").append(SALTO_LINEA).append(ESPACIOS2);
            }
        }

        return sb.toString();
    }
    
 public static String transformarNombreObjeto(String nombre,Boolean a) {
        
        return GeneradorBase.transformarNombreObjeto(nombre,a);
    }    
    
}
