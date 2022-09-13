/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tomcat.persistence.exceptions;

import java.sql.SQLException;

/**
 *
 * @author ihuegal
 */
public class ConstraintViolationException extends SQLException {
    private final String SQL;
    
    public ConstraintViolationException(String sqlQuery, String reason, String SQLState, int vendorCode) {
        super(reason, SQLState, vendorCode);
        this.SQL = sqlQuery;
    }

    public ConstraintViolationException(String sqlQuery, String reason, String SQLState) {
        super(reason, SQLState);
        this.SQL = sqlQuery;
    }

    public ConstraintViolationException(String sqlQuery, String reason) {
        super(reason);
        this.SQL = sqlQuery;
    }

    public ConstraintViolationException(String sqlQuery) {
        super();
        this.SQL = sqlQuery;
    }

    public ConstraintViolationException(String sqlQuery, Throwable cause) {
        super(cause);
        this.SQL = sqlQuery;
    }

    public ConstraintViolationException(String sqlQuery, String reason, Throwable cause) {
        super(reason, cause);
        this.SQL = sqlQuery;
    }

    public ConstraintViolationException(String sqlQuery, String reason, String sqlState, Throwable cause) {
        super(reason, sqlState, cause);
        this.SQL = sqlQuery;
    }

    public ConstraintViolationException(String sqlQuery, String reason, String sqlState, int vendorCode, Throwable cause) {
        super(reason, sqlState, vendorCode, cause);
        this.SQL = sqlQuery;
    }

    public String getSQL() {
        return SQL;
    }
}
