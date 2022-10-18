package tomcat.persistence.exceptions;

/**
 *
 * @author ihuegal
 */
public class SQLReferenceException extends java.sql.SQLException {
    private String SQL = null;
    
    public SQLReferenceException(String sqlQuery, Throwable ex) {
        super(ex);
        this.SQL = sqlQuery;
    }
    
    public String getSQL() {
        return SQL;
    }
}
