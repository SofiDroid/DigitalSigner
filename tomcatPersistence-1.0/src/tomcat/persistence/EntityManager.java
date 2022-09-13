/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tomcat.persistence;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase EntityManager personalizada para no usar hibernate,
 * utilizando el pool de conexiones de tomcat jdbc.
 * 
 * @author ihuegal
 */
public class EntityManager implements Closeable {

    private final Connection conn;
    private EntityTransaction transaction = null;
    
    /**
     * Constructor
     * 
     * @param conn - Conexion obtenida del pool con la clase
     * ConnectionPool.getConexionBDU() que devuelve un objeto
     * java.sql.Connection
     */
    public EntityManager(Connection conn)
    {
        this.conn = conn;
    }  
    
    /**
     * Crea un objeto tomcat.persistence.Query similar al de
     * hibernate pero utilizando el pool de conexiones jdbc de
     * tomcat.
     * 
     * @param sql - Sentencia SQL asociada al objeto Query
     * 
     * @return tomcat.persistence.Query
     */
    public Query createNativeQuery(String sql) {
        return new Query(sql, this);
    }

    /**
     * Devuelve la conexion al pool de conexiones.
     * Si la conexion tenia una transaccion abierta y no se cierra
     * al devolver la conexion al pool, este ejecutará un rollback
     * de los cambios. Del mismo que si se produce un abandono de
     * la conexion por timeout o lo que sea.
     */
    @Override
    public void close() {
        try {
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(EntityManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Devuelve True si la conexion no está cerrada.
     * @return 
     */
    public boolean isOpen() {
        try {
            return !conn.isClosed();
        } catch (SQLException ex) {
            Logger.getLogger(EntityManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    /**
     * Devuelve la transaccion de la conexion.
     * Si esta no existen, crea un nuevo objeto EntityTransaction
     * pero no inicia la transacion.
     * 
     * @return tomcat.persistence.EntityTransaction
     */
    public EntityTransaction getTransaction() {
        if (this.transaction == null)
        {
            this.transaction = new EntityTransaction(this.conn);
        }
        return this.transaction;
    }

    /**
     * Devuelve la conexion asociada al objeto EntityManager.
     * 
     * @return java.sql.Connection
     */
    public Connection getConnection() {
        return conn;
    }
}
