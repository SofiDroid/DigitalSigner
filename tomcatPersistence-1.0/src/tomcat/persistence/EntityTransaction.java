/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tomcat.persistence;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ihuegal
 */
public class EntityTransaction {

    private boolean rollbackOnly = false;
    private boolean boTransaction = false;
    private final Connection conn;

    /**
     * Constructor del objeto EntityTransaction que recibe
     * por parametro la conexion asociada.
     * 
     * @param conn - Conexion asociada al EntityManager
     */
    EntityTransaction(Connection conn) {
        this.conn = conn;
    }
        
    /**
     * Marca el inicio de una nueva transaccion. Si la transacción anterior
     * del objeto EntityManager terminó con errores y fue marcada como
     * RollbackOnly devolverá una SQLException indicandolo.
     * 
     * @throws SQLException 
     */
    public void begin() throws SQLException {
        if (this.getRollbackOnly()) {
            throw new SQLException("Connection is marked for rollback only.");
        }
        this.boTransaction = true;
    }

    /**
     * Realiza el commit de la transacción y la marca como finalizada.
     * Si la transacción anterior del objeto EntityManager terminó 
     * con errores y fue marcada como RollbackOnly devolverá una 
     * SQLException indicandolo y no realizará el commit.
     * 
     * @throws SQLException 
     */
    public void commit() throws SQLException {
        if (this.getRollbackOnly()) {
            throw new SQLException("Connection is marked for rollback only.");
        }

        this.boTransaction = false;
        this.conn.commit();
    }

    /**
     * Deshace los cambios de la transaccion actual y la marca como 
     * finalizada.
     * 
     * @throws SQLException 
     */
    public void rollback() {
        try {
            this.boTransaction = false;
            this.conn.rollback();
        } catch (SQLException ex) {
            Logger.getLogger(EntityTransaction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Marca la transaccion como "no valida" y solo podrá
     * realizarse un rollback de la misma.
     */
    public void setRollbackOnly() {
        this.rollbackOnly = true;
    }

    /**
     * Obtiene si la transaccion ha sido marcada como
     * solo para rollback.
     * 
     * @return 
     */
    public boolean getRollbackOnly() {
        return this.rollbackOnly;
    }

    /**
     * Devuelve True si hay una transaccion activa no finalizada ni 
     * con rollback ni con commit.
     * 
     * @return 
     */
    public boolean isActive() {
        return this.boTransaction;
    }
}
