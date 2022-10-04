package basedatos;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import utilidades.Formateos;

/**
 *
 * @author ihuegal
 */
public class OperacionSQL {

    public String recuperaValorCampo(String campoJava, String campoBD, HashMap<String,Object> bld) throws Exception {
        return (String)recuperaValorCampo((Object)campoJava, campoBD, bld);
    }
    
    public Integer recuperaValorCampo(Integer campoJava, String campoBD, HashMap<String,Object> bld) throws Exception {
        return (Integer)recuperaValorCampo((Object)campoJava, campoBD, bld);
    }
    
    public Short recuperaValorCampo(Short campoJava, String campoBD, HashMap<String,Object> bld) throws Exception {
        return (Short)recuperaValorCampo((Object)campoJava, campoBD, bld);
    }
    
    public BigDecimal recuperaValorCampo(BigDecimal campoJava, String campoBD, HashMap<String,Object> bld) throws Exception {
        return (BigDecimal)recuperaValorCampo((Object)campoJava, campoBD, bld);
    }
    
    public Date recuperaValorCampo(Date campoJava, String campoBD, HashMap<String,Object> bld) throws Exception {
        return (Date)recuperaValorCampo((Object)campoJava, campoBD, bld);
    }
  
    public Boolean recuperaValorCampo(Boolean campoJava, String campoBD, HashMap<String,Object> bld) throws Exception {
        return (Boolean)recuperaValorCampo((Object)campoJava, campoBD, bld);
    }
  
    private Object recuperaValorCampo(Object campoJava, String campoBD, HashMap<String,Object> bld) throws Exception {
        if (bld.containsKey(campoBD.toUpperCase()) && bld.get(campoBD.toUpperCase()) != null) {
            Object valorBD = bld.get(campoBD.toUpperCase());
            if (campoJava instanceof Integer) {
                if (valorBD instanceof BigDecimal valor) {
                    campoJava = valor.intValue();
                }
                else if (valorBD instanceof String valor) {
                    campoJava = Integer.valueOf(valor);
                }
                else if (valorBD instanceof Date) {
                    throw new Exception("EntityManager: No se puede convertir Date a Integer.");
                }
            }
            else if (campoJava instanceof Short) {
                if (valorBD instanceof BigDecimal valor) {
                    campoJava = valor.shortValue();
                }
                else if (valorBD instanceof String valor) {
                    campoJava = Short.valueOf(valor);
                }
                else if (valorBD instanceof Date) {
                    throw new Exception("EntityManager: No se puede convertir Date a Short.");
                }
            }
            else if (campoJava instanceof String) {
                if (valorBD instanceof BigDecimal valor) {
                    campoJava = valor.toString();
                }
                else if (valorBD instanceof String valor) {
                    campoJava = valor;
                }
                else if (valorBD instanceof Date valor) {
                    campoJava = Formateos.dateToString(valor, Formateos.TipoFecha.FECHA);
                }
            }
            else if (campoJava instanceof Date) {
                if (valorBD instanceof BigDecimal) {
                    throw new Exception("EntityManager: No se puede convertir BigDecimal a Date.");
                }
                else if (valorBD instanceof String valor) {
                    campoJava = Formateos.stringToDate(valor, Formateos.TipoFecha.FECHA);
                }
                else if (valorBD instanceof Date valor) {
                    campoJava = valor;
                }
            }
            else if (campoJava instanceof BigDecimal) {
                if (valorBD instanceof BigDecimal valor) {
                    campoJava = valor;
                }
                else if (valorBD instanceof String valor) {
                    throw new Exception("EntityManager: No se puede convertir String a BigDecimal.");
                }
                else if (valorBD instanceof Date valor) {
                    throw new Exception("EntityManager: No se puede convertir Date a BigDecimal.");
                }
            }
            else if (campoJava instanceof Boolean) {
                if (valorBD instanceof BigDecimal valor) {
                    campoJava = (valor.byteValue() == 1);
                }
                else if (valorBD instanceof String valor) {
                    campoJava = (valor.equals("1"));
                }
                else if (valorBD instanceof Date valor) {
                    throw new Exception("EntityManager: No se puede convertir Date a Boolean.");
                }
            }
            else {
                throw new Exception("EntityManager: Conversion de " + campoJava.getClass().getName() + " a " + valorBD.getClass().getName() + " no configurada.");
            }
        }
        return campoJava;
    }
    
    public void recuperaValorCampo(Object campoJavaClass, String tipoName, String campoBD, HashMap<String,Object> bld) throws Exception {
        Field campo;
        try {
            campo = campoJavaClass.getClass().getDeclaredField(tipoName);;
        }
        catch (NoSuchFieldException | SecurityException na) {
            campo = campoJavaClass.getClass().getSuperclass().getDeclaredField(tipoName);;
        }
        campo.setAccessible(true);
        
        Class<?> tipo = campo.getType();
        if (bld.containsKey(campoBD.toUpperCase()) && bld.get(campoBD.toUpperCase()) != null) {
            Object valorBD = bld.get(campoBD.toUpperCase());
            if (tipo.getName().contains("Integer") || tipo.getName().equalsIgnoreCase("int")) {
                if (valorBD instanceof BigDecimal valor) {
                    campo.set(campoJavaClass, valor.intValue());
                }
                else if (valorBD instanceof String valor) {
                    campo.set(campoJavaClass, Integer.valueOf(valor));
                }
                else if (valorBD instanceof Date) {
                    throw new Exception("EntityManager: No se puede convertir Date a Integer.");
                }
            }
            else if (tipo.getName().equalsIgnoreCase("Short")) {
                if (valorBD instanceof BigDecimal valor) {
                    campo.set(campoJavaClass, valor.shortValue());
                }
                else if (valorBD instanceof String valor) {
                    campo.set(campoJavaClass, Short.valueOf(valor));
                }
                else if (valorBD instanceof Date) {
                    throw new Exception("EntityManager: No se puede convertir Date a Short.");
                }
            }
            else if (tipo.getName().contains("String")) {
                if (valorBD instanceof BigDecimal valor) {
                    campo.set(campoJavaClass, valor.toString());
                }
                else if (valorBD instanceof String valor) {
                    campo.set(campoJavaClass, valor);
                }
                else if (valorBD instanceof Date valor) {
                    campo.set(campoJavaClass, Formateos.dateToString(valor, Formateos.TipoFecha.FECHA));
                }
            }
            else if (tipo.getName().contains("Date")) {
                if (valorBD instanceof BigDecimal) {
                    throw new Exception("EntityManager: No se puede convertir BigDecimal a Date.");
                }
                else if (valorBD instanceof String valor) {
                    campo.set(campoJavaClass, Formateos.stringToDate(valor, Formateos.TipoFecha.FECHA));
                }
                else if (valorBD instanceof Date valor) {
                    campo.set(campoJavaClass, valor);
                }
            }
            else if (tipo.getName().contains("BigDecimal")) {
                if (valorBD instanceof BigDecimal valor) {
                    campo.set(campoJavaClass, valor);
                }
                else if (valorBD instanceof String) {
                    throw new Exception("EntityManager: No se puede convertir String a BigDecimal.");
                }
                else if (valorBD instanceof Date) {
                    throw new Exception("EntityManager: No se puede convertir Date a BigDecimal.");
                }
            }
            else if (tipo.getName().contains("Boolean")) {
                if (valorBD instanceof BigDecimal valor) {
                    campo.set(campoJavaClass, (valor.byteValue() == 1));
                }
                else if (valorBD instanceof String valor) {
                    campo.set(campoJavaClass, (valor.equals("1")));
                }
                else if (valorBD instanceof Date) {
                    throw new Exception("EntityManager: No se puede convertir Date a Boolean.");
                }
                else if (valorBD instanceof Boolean valor) {
                    campo.set(campoJavaClass, valor);
                }
            }
            else {
                throw new Exception("EntityManager: Conversion de " + tipo.getName() + " a " + valorBD.getClass().getName() + " no configurada.");
            }
        }
    }
}
