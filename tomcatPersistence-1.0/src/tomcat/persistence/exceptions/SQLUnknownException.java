package tomcat.persistence.exceptions;

/**
 *
 * @author ihuegal
 */
public class SQLUnknownException extends java.sql.SQLException {
    private String SQL = null;
    private String SQLState = null;
    
    public SQLUnknownException(String sqlQuery, String SQLState, Throwable ex) {
        super(ex);
        this.SQL = sqlQuery;
        this.SQLState = SQLState;
    }
    
    public String getSQL() {
        return SQL;
    }

    @Override
    public String getSQLState() {
        return SQLState;
    }
}
