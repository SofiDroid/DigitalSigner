package tomcat.persistence;

import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 *
 * @author ihuegal
 */
public class ResultData {
    protected ResultSetMetaData metadata = null;
    protected ArrayList<LinkedHashMap<String,Object>> resultListMapped;
    protected ArrayList resultList;

    public ResultSetMetaData getMetadata() {
        return metadata;
    }

    public void setMetadata(ResultSetMetaData metadata) {
        this.metadata = metadata;
    }

    public ArrayList<LinkedHashMap<String,Object>> getResultListMapped() {
        return resultListMapped;
    }

    public void setResultListMapped(ArrayList<LinkedHashMap<String,Object>> resultListMapped) {
        this.resultListMapped = resultListMapped;
    }

    public ArrayList getResultList() {
        return resultList;
    }

    public void setResultList(ArrayList resultList) {
        this.resultList = resultList;
    }
}
