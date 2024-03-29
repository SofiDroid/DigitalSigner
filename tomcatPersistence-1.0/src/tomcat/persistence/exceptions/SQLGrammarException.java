package tomcat.persistence.exceptions;

/**
 *
 * @author ihuegal
 */
public class SQLGrammarException extends java.sql.SQLException {
    private String SQL = null;
    
    public SQLGrammarException(String sqlQuery, Throwable ex) {
        super(ex);
        this.SQL = sqlQuery;
    }
    
    public String getSQL() {
        return SQL;
    }
}
