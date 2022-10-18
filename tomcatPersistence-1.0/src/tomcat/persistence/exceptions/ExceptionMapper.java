/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tomcat.persistence.exceptions;

/**
 *
 * @author ihuegal
 */
public class ExceptionMapper {
    public static java.sql.SQLException mapear(String sql, Throwable ex) throws java.sql.SQLException {
        if (((java.sql.SQLException)ex).getSQLState() == null) {
            throw new SQLUnknownException(sql, null, ex);
        }
        switch (((java.sql.SQLException)ex).getSQLState()) { 
            case "2A000", "42000" -> throw new SQLGrammarException(sql, ex);
            case "23000" -> throw new SQLReferenceException(sql, ex);
            case "40002" -> throw  new ConstraintViolationException(sql, ex);
            default -> throw new SQLUnknownException(sql, ((java.sql.SQLException)ex).getSQLState(), ex);
        }
    }
}
