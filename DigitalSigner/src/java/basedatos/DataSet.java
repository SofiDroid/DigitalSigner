package basedatos;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author ihuegal
 */
public final class DataSet extends StBase {
    
    protected String sql = null;
    protected RowCabecera cabecera = new RowCabecera();
    protected ArrayList<Row> rows = new ArrayList<>();
    protected HashMap<String, Object> parametros = new HashMap<>();
    protected Row selectedRow = null;
    
    public DataSet() {
        // NADA
    }
    
    public DataSet(String sql, HashMap<String, Object> parametros) throws SQLException {
        this.sql = sql;
        this.parametros = parametros;
        recuperarDatos();
    }
    
    public DataSet(String sql) throws SQLException {
        this.sql = sql;
        recuperarDatos();
    }

    public void recuperarDatos() throws SQLException {
        ArrayList<LinkedHashMap<String,Object>> lista = executeNativeQueryListParametros(sql, parametros, null);
        if (lista != null && !lista.isEmpty()) {
            rellenaCabecera(lista);
            rellenaDatos(lista);
        }
    }

    private void rellenaCabecera(ArrayList<LinkedHashMap<String,Object>> lista) {
        int i = 0;
        LinkedHashMap<String,Object> itemRow = lista.get(0);
        for (Map.Entry<String, Object> itemColumn : itemRow.entrySet()) {
            ColumnCabecera columnaCab = new ColumnCabecera();
            columnaCab.index = i++;
            columnaCab.name = itemColumn.getKey();
            columnaCab.title = itemColumn.getKey();
            this.cabecera.getColumns().add(columnaCab);
        }
    }
    
    private void rellenaDatos(ArrayList<LinkedHashMap<String,Object>> lista) {
        int f = 0;
        for (LinkedHashMap<String,Object> itemRow : lista) {
            Row fila = new Row();
            fila.index = f++;
            int c = 0;
            for (Map.Entry<String, Object> itemColumn : itemRow.entrySet()) {
                Column columna = new Column();
                columna.setCabecera((ColumnCabecera)this.cabecera.getColumns().get(c));
                columna.index = c++;
                columna.name = itemColumn.getKey();
                columna.value = itemColumn.getValue();
                columna.valueString = columna.recuperaValorString();
                columna.tooltip = columna.valueString;
                fila.getColumns().add(columna);
            }
            this.rows.add(fila);
        }
    }
    
    public void clear() {
        this.selectedRow = null;
        this.cabecera.columns.clear();
        this.rows.clear();
    }
    
    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public RowCabecera getCabecera() {
        return cabecera;
    }

    public void setCabecera(RowCabecera cabecera) {
        this.cabecera = cabecera;
    }

    public ArrayList<Row> getRows() {
        return rows;
    }

    public void setRows(ArrayList<Row> rows) {
        this.rows = rows;
    }
    
    public int getRowsCount() {
        return (this.rows != null ? this.rows.size() : 0);
    }

    public HashMap<String, Object> getParametros() {
        return parametros;
    }

    public void setParametros(HashMap<String, Object> parametros) {
        this.parametros = parametros;
    }

    public Row getSelectedRow() {
        return selectedRow;
    }

    public void setSelectedRow(Row selectedRow) {
        this.selectedRow = selectedRow;
    }
}
