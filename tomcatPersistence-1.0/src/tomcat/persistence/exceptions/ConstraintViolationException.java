package tomcat.persistence.exceptions;

/**
 *
 * @author ihuegal
 */
public class ConstraintViolationException extends java.sql.SQLException {
    private String SQL = null;
    
    public ConstraintViolationException(String sqlQuery, Throwable ex) {
        super(ex);
        this.SQL = sqlQuery;
    }
    
    public String getSQL() {
        return SQL;
    }
}
