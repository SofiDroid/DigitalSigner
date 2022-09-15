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
public class ExceptionMapper extends SQLException {
    
    public ExceptionMapper(String sqlQuery, SQLException ex) throws SQLException {
        switch (ex.getSQLState()) {
            case "2A000", "42000" -> throw new SQLGrammarException(sqlQuery, ex);
            case "40002" -> throw new ConstraintViolationException(sqlQuery, ex);
            default -> throw ex;
        }
    }
    
}
