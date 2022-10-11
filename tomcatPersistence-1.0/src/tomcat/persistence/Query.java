/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tomcat.persistence;

import java.sql.Statement;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.sql.SQLException;
import tomcat.persistence.exceptions.ExceptionMapper;

/**
 *
 * @author ihuegal
 */

public class Query {

    private String sql;
    private final EntityManager entityManager;
    private record PosicionTexto(Integer posIni, Integer posFin){}
    private ArrayList<PosicionTexto> listaPosicionTexto = new ArrayList<>();
    private record Parametro(Integer posicion, String nombreParametro, Object valor){};
    private final ArrayList<Parametro> parametros = new ArrayList<>();
    private final ArrayList<Parametro> parametrosPosicionados = new ArrayList<>();
            
    /**
     * Constructor del objeto Query que recibe por parametros la sentencia
     * SQL asociada y el objeto tomcat.persistence.EntityManager de la
     * conexión asociada.
     * 
     * @param sql - Sentencia SQL asociada.
     * @param entityManager - Conexion asociada.
     */
    Query(String sql, EntityManager entityManager) {
        this.sql = sql;
        this.entityManager = entityManager;
        encontrarTextosFijos(0);
    }
    
    /**
     * Obtiene una lista del tipo List<Object[]> con los resultados
     * devueltos por la sentencia sql asociada.
     * 
     * @return List<Object[]>
     * 
     * @throws SQLException 
     */
    @SuppressWarnings("unchecked")
    public List getResultList() throws SQLException {
        if (this.entityManager.getTransaction().getRollbackOnly()) {
            throw new SQLException("Connection is marked for rollback only.");
        }
        
        List result = null;
        
        this.parametrosPosicionados.clear();
        encontrarParametros(0);
        try (CallableStatement stmt = entityManager.getConnection().prepareCall(sql))
        {
            establecerParametros(stmt);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs != null) {
                    result = new ArrayList();
                    while (rs.next()) {
                        ResultSetMetaData metaData = rs.getMetaData();
                        if (metaData.getColumnCount() == 1) {
                            result.add(rs.getObject(1));
                        } else {
                            Object[] fila = new Object[metaData.getColumnCount()];
                            for (int i = 0; i < metaData.getColumnCount(); i++) {
                                fila[i] = rs.getObject((i+1));
                            }
                            result.add(fila);
                        }
                    }
                }
            }
        }
        catch (SQLException ex) {
            
            this.entityManager.getTransaction().setRollbackOnly();
            throw ExceptionMapper.mapear(sql, ex);
        }            
        return result;
    }

    /**
     * Obtiene una lista del tipo List<HashMap<String,Object>> con los resultados
     * devueltos por la sentencia sql asociada.
     * 
     * @return List<HashMap<String,Object>>
     * 
     * @throws SQLException 
     */
    @SuppressWarnings("unchecked")
    public List<LinkedHashMap<String,Object>> getResultListMapped() throws SQLException {
        if (this.entityManager.getTransaction().getRollbackOnly()) {
            throw new SQLException("Connection is marked for rollback only.");
        }
        
        List<LinkedHashMap<String,Object>> result = null;
        this.parametrosPosicionados.clear();
        encontrarParametros(0);
        try (CallableStatement stmt = entityManager.getConnection().prepareCall(sql))
        {
            establecerParametros(stmt);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs != null) {
                    result = new ArrayList();
                    while (rs.next()) {
                        ResultSetMetaData metaData = rs.getMetaData();
                        LinkedHashMap<String,Object> fila = new LinkedHashMap<>();
                        for (int i = 0; i < metaData.getColumnCount(); i++) {
                            fila.put(metaData.getColumnName((i+1)).toUpperCase(), rs.getObject((i+1)));
                        }
                        result.add(fila);
                    }
                }
            }
        }
        catch (SQLException ex) {
            this.entityManager.getTransaction().setRollbackOnly();
            throw ExceptionMapper.mapear(sql, ex);
        }            
        return result;
    }
    
    /**
     * Obtiene una objeto del tipo Object[] con el primer registros
     * devuelto por la sentencia sql asociada.
     * 
     * @return Object[]
     * 
     * @throws SQLException 
     */
    public Object getSingleResult() throws SQLException {
        if (this.entityManager.getTransaction().getRollbackOnly()) {
            throw new SQLException("Connection is marked for rollback only.");
        }

        Object result = null;
        this.parametrosPosicionados.clear();
        encontrarParametros(0);
        try (CallableStatement stmt = entityManager.getConnection().prepareCall(sql))
        {
            establecerParametros(stmt);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs != null) {
                    if (rs.next()) {
                        ResultSetMetaData metaData = rs.getMetaData();
                        if (metaData.getColumnCount() == 1) {
                            result = rs.getObject(1);
                        } else {
                            result = new Object[metaData.getColumnCount()];
                            for (int i = 0; i < metaData.getColumnCount(); i++) {
                                ((Object[])result)[(i+1)] = rs.getObject((i+1));
                            }
                        }
                        return result;
                    }
                }
            }
        }
        catch (SQLException ex) {
            this.entityManager.getTransaction().setRollbackOnly();
            throw ExceptionMapper.mapear(sql, ex);
        } 
        return result;
    }

    /**
     * Obtiene una objeto del tipo HashMap<String,Object> con el primer registros
     * devuelto por la sentencia sql asociada.
     * 
     * @return HashMap<String,Object>
     * 
     * @throws SQLException 
     */
    public LinkedHashMap<String,Object> getSingleResultMapped() throws SQLException {
        if (this.entityManager.getTransaction().getRollbackOnly()) {
            throw new SQLException("Connection is marked for rollback only.");
        }

        LinkedHashMap<String,Object> result = null;
        this.parametrosPosicionados.clear();
        encontrarParametros(0);
        try (CallableStatement stmt = entityManager.getConnection().prepareCall(sql))
        {
            establecerParametros(stmt);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs != null) {
                    if (rs.next()) {
                        ResultSetMetaData metaData = rs.getMetaData();
                        result = new LinkedHashMap<>();
                        for (int i = 0; i < metaData.getColumnCount(); i++) {
                            result.put(metaData.getColumnName((i+1)).toUpperCase(), rs.getObject((i+1)));
                        }
                        return result;
                    }
                }
            }
        }
        catch (SQLException ex) {
            this.entityManager.getTransaction().setRollbackOnly();
            throw ExceptionMapper.mapear(sql, ex);
        } 
        return result;
    }

    /**
     * Ejecuta la sentencia INSERT o UPDATE asocida y devuelve
     * el número de registros insertados o el numero de filas
     * actualizadas respectivamente.
     * 
     * @return Numero de registros insertados o filas actualizadas.
     * @throws SQLException 
     */
    public int executeUpdate() throws SQLException {
        if (this.entityManager.getTransaction().getRollbackOnly()) {
             throw new SQLException("Connection is marked for rollback only.");
        }

        this.parametrosPosicionados.clear();
        encontrarParametros(0);
        
        try (PreparedStatement stmt = entityManager.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
        {
            establecerParametros(stmt);
            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                return 0;
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
                else {
                    return 0;
                }
            }
        }
        catch (SQLException ex) {
            this.entityManager.getTransaction().setRollbackOnly();
            throw ExceptionMapper.mapear(sql, ex);
        }
    }

    /**
     * Establece el valor de un parametro para sustituirlo en la 
     * sentencia SQL asociada.
     * 
     * @param nombreParametro - Nombre del parametro (sin los ":") de la
     * consulta SQL asociada.
     * Ej: "SELECT * FROM BD_D_TABLA WHERE ID_TABLA = :idTabla" el
     * nombreParametro sería "idTabla"
     * 
     * Es sensitivo a mayusculas/minusculas.
     * 
     * @param valor - Valor del parametro, puede ser un entero, una fecha, 
     * una cadena...
     * 
     * @return Retorna el mismo objeto Query para concatenar sentencias 
     * ".setParameter(...)" si se desea.
     */
    public Query setParameter(String nombreParametro, Object valor) {
        parametros.add(new Parametro(null, nombreParametro, valor));
        return this;
    }

    /**
     * Establece el valor de un parametro para sustituirlo en la 
     * sentencia SQL asociada.
     * 
     * @param nombreParametro - Nombre del parametro (sin los ":") de la
     * consulta SQL asociada.
     * Ej: "SELECT * FROM BD_D_TABLA WHERE ID_TABLA = :idTabla" el
     * nombreParametro sería "idTabla"
     * 
     * Es sensitivo a mayusculas/minusculas.
     * 
     * @param valor - Valor del parametro fecha de tipo Date
     * 
     * @param tt - No se usa, existe por compatibilidad con el código anterior.
     * 
     * @return Retorna el mismo objeto Query para concatenar sentencias 
     * ".setParameter(...)" si se desea.
     */
    public Query setParameter(String nombreParametro, Date valor, TemporalType tt) {
        parametros.add(new Parametro(null, nombreParametro, valor));
        return this;
    }

    /**
     * Establece el valor de un parametro para sustituirlo en la 
     * sentencia SQL asociada.
     * 
     * @param nombreParametro - Nombre del parametro (sin los ":") de la
     * consulta SQL asociada.
     * Ej: "SELECT * FROM BD_D_TABLA WHERE ID_TABLA = :idTabla" el
     * nombreParametro sería "idTabla"
     * 
     * Es sensitivo a mayusculas/minusculas.
     * 
     * @param cal - Valor del parametro fecha de tipo Calendar
     * 
     * @param tt - No se usa, existe por compatibilidad con el código anterior.
     * 
     * @return Retorna el mismo objeto Query para concatenar sentencias 
     * ".setParameter(...)" si se desea.
     */
    public Query setParameter(String nombreParametro, Calendar cal, TemporalType tt) {
        parametros.add(new Parametro(null, nombreParametro, cal.getTime()));
        return this;
    }

    /**
     * Establece el valor de un parametro para sustituirlo en la 
     * sentencia SQL asociada.
     * 
     * @param posicion - Posicion del parámetro cuando este se especifica con
     * el simbolo "?" en lugar de un nombre.
     * Ej: "SELECT * FROM BD_D_TABLA WHERE ID_TABLA = ?" la 
     * posicion sería 1.
     * 
     * OJO: el indice comienza en "1" y no en "0".
     * 
     * @param valor - Valor del parametro, puede ser un entero, una fecha, 
     * una cadena...
     * 
     * @return Retorna el mismo objeto Query para concatenar sentencias 
     * ".setParameter(...)" si se desea.
     */
    public Query setParameter(int posicion, Object valor) {
        parametros.add(new Parametro(posicion, null, valor));
        return this;
    }

    /**
     * Establece el valor de un parametro para sustituirlo en la 
     * sentencia SQL asociada.
     * 
     * @param posicion - Posicion del parámetro cuando este se especifica con
     * el simbolo "?" en lugar de un nombre.
     * Ej: "SELECT * FROM BD_D_TABLA WHERE ID_TABLA = ?" la 
     * posicion sería 1.
     * 
     * OJO: el indice comienza en "1" y no en "0".
     * 
     * @param valor - Valor del parametro fecha de tipo Date
     * 
     * @param tt - No se usa, existe por compatibilidad con el código anterior.
     * 
     * @return Retorna el mismo objeto Query para concatenar sentencias 
     * ".setParameter(...)" si se desea.
     */
    public Query setParameter(int posicion, Date valor, TemporalType tt) {
        parametros.add(new Parametro(posicion, null, valor));
        return this;
    }

    /**
     * Establece el valor de un parametro para sustituirlo en la 
     * sentencia SQL asociada.
     * 
     * @param posicion - Posicion del parámetro cuando este se especifica con
     * el simbolo "?" en lugar de un nombre.
     * Ej: "SELECT * FROM BD_D_TABLA WHERE ID_TABLA = ?" la 
     * posicion sería 1.
     * 
     * OJO: el indice comienza en "1" y no en "0".
     * 
     * @param cal - Valor del parametro fecha de tipo Calendar
     * 
     * @param tt - No se usa, existe por compatibilidad con el código anterior.
     * 
     * @return Retorna el mismo objeto Query para concatenar sentencias 
     * ".setParameter(...)" si se desea.
     */
    public Query setParameter(int posicion, Calendar cal, TemporalType tt) {
        parametros.add(new Parametro(posicion, null, cal.getTime()));
        return this;
    }
    
    private boolean contieneParametro(String nombreParametro) {
        return (sql.contains(":" + nombreParametro + " ") || 
                  sql.contains(":" + nombreParametro + ",") || 
                  sql.contains(":" + nombreParametro + ")") ||
                  sql.contains(":" + nombreParametro + "=") ||
                  sql.contains(":" + nombreParametro + "<") ||
                  sql.contains(":" + nombreParametro + ">") ||
                  sql.contains(":" + nombreParametro + "\n") ||
                  sql.endsWith(":" + nombreParametro));
    }

    private int obtenerPosFin(int posIni) {
        ArrayList<Integer> posFin = new ArrayList<>();
        int temp;
        if ((temp = this.sql.indexOf(" ", posIni)) >= 0) posFin.add(temp);
        if ((temp = this.sql.indexOf(",", posIni)) >= 0) posFin.add(temp);
        if ((temp = this.sql.indexOf(")", posIni)) >= 0) posFin.add(temp);
        if ((temp = this.sql.indexOf("=", posIni)) >= 0) posFin.add(temp);
        if ((temp = this.sql.indexOf("<", posIni)) >= 0) posFin.add(temp);
        if ((temp = this.sql.indexOf(">", posIni)) >= 0) posFin.add(temp);
        if ((temp = this.sql.indexOf("\n", posIni)) >= 0) posFin.add(temp);
        if ((temp = this.sql.indexOf("\r", posIni)) >= 0) posFin.add(temp);
        if ((temp = this.sql.indexOf("\t", posIni)) >= 0) posFin.add(temp);
        if ((temp = this.sql.length()) >= 0) posFin.add(temp);
        
        return posFin.stream().mapToInt(i -> i).min().getAsInt();
    }
    
    private Object obtenerValorParametro(String nombreParametro) throws SQLException {
        if (this.parametros != null) {
            for (Parametro itemParametro : this.parametros) {
                if (itemParametro.nombreParametro.equals(nombreParametro))
                    return itemParametro.valor;
            }
        }
        throw new SQLException("Parametro no encontrado: '" + nombreParametro + "'");
    }
    
    private void encontrarTextosFijos(int posIni) {
        posIni = this.sql.indexOf("'", posIni);
        
        if (posIni == -1)
            return;
        
        int posFin = this.sql.indexOf("'", posIni+1);
        
        listaPosicionTexto.add(new PosicionTexto(posIni, posFin));
        
        if (posFin+1 >= this.sql.length())
            return;
        
        encontrarTextosFijos(posFin+1);
    }
    
    private Integer saltarTextoFijo(int posIni, String cadenaBuscada) {
        posIni = this.sql.indexOf(cadenaBuscada, posIni);
        if (listaPosicionTexto != null) {
            for (PosicionTexto item : listaPosicionTexto) {
                if (posIni >= item.posIni && posIni <= item.posFin) {
                    return saltarTextoFijo(item.posFin+1, cadenaBuscada);
                }
            }
        }
        return posIni;
    }
    
    private void reposicionarTextosFijos(Integer posIni, Integer longitud) {
        ArrayList<PosicionTexto> listaTemp = new ArrayList<>();
        PosicionTexto temp;
        if (listaPosicionTexto != null) {
            for (PosicionTexto item : listaPosicionTexto) {
                if (posIni <= item.posIni()) {
                    temp = new PosicionTexto(item.posIni - longitud, item.posFin - longitud);
                }
                else {
                    temp = new PosicionTexto(item.posIni, item.posFin);
                }
                listaTemp.add(temp);
            }
        }
        listaPosicionTexto = listaTemp;
    }
    
    private void encontrarParametros(int posIni) throws SQLException {
        posIni = saltarTextoFijo(posIni,":");
        
        if (posIni == -1)
            return;
        
        posIni++;
        
        int posFin = obtenerPosFin(posIni);
        
        String nombreParametro = this.sql.substring(posIni, posFin);
        this.sql = this.sql.substring(0, posIni-1) + "?" + this.sql.substring(posFin);
        
        int longitud = (posFin - posIni);
        reposicionarTextosFijos(posIni-1, longitud);
        posFin -= longitud;
        
        Object valorParametro = obtenerValorParametro(nombreParametro);
//        if (valorParametro != null) {
            this.parametrosPosicionados.add(new Parametro(this.parametrosPosicionados.size()+1, nombreParametro, valorParametro));
//        }
        
        if (posFin >= this.sql.length())
            return;
        
        encontrarParametros(posFin);
    }
    
    private void establecerParametros(PreparedStatement stmt) throws SQLException {
        if (this.parametrosPosicionados == null || this.parametrosPosicionados.isEmpty())
            return;
        
        if (this.parametrosPosicionados == null || this.parametrosPosicionados.isEmpty()) {
            if (saltarTextoFijo(0, "?") == -1) {
                return;
            }
            for (Parametro item : this.parametros) {
                this.parametrosPosicionados.add(new Parametro(this.parametrosPosicionados.size()+1, null, item.valor));
            }
        }
        
        for (Parametro item : this.parametrosPosicionados)
        {
            if (item.valor() instanceof  Integer valor)
            {
                stmt.setInt(item.posicion(), valor);
            }
            else if (item.valor() instanceof String valor)
            {
                stmt.setString(item.posicion(), valor);
            }
            else if (item.valor() instanceof byte[] valor)
            {
                stmt.setBytes(item.posicion(), valor);
            }
            else if (item.valor() instanceof Date valor) 
            {
                stmt.setDate(item.posicion(), (valor != null ? new java.sql.Date(valor.getTime()) : null));
            }
            else if (item.valor() instanceof Boolean valor)
            {
                stmt.setBoolean(item.posicion(), valor);
            }
            else if (item.valor() instanceof BigDecimal valor)
            {
                stmt.setBigDecimal(item.posicion(), valor);
            }
            else if (item.valor() == null)
            {
                stmt.setNull(item.posicion(), java.sql.Types.NULL);
            }
            else
            {
                throw new SQLException("No se encuentra el tipo de datos para insertar: " + item.toString());
            }
        }
    }
}
