package tomcat.persistence.exceptions;

/**
 *
 * @author ihuegal
 */
public class SQLUnknownException extends java.sql.SQLException {
    private String SQL = null;
    
    public SQLUnknownException(String sqlQuery, Throwable ex) {
        super(ex);
        this.SQL = sqlQuery;
    }
    
    public String getSQL() {
        return SQL;
    }
}
