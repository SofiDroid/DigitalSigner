package basedatos;

import java.lang.reflect.Method;
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
    protected ArrayList<Row> selectedRows = new ArrayList<>();
    protected Boolean selectable = false;
    protected Method rowSelectMethod = null;
    protected Object rowSelectClass = null;
    protected String rowSelectUpdate = "@widgetVar(mensaje)";
    protected String rowSelectColumnaID = null;
    protected String heightFiltro = "27.8rem";
    protected String height = "calc(100vh - " + heightFiltro + ")";

    public DataSet() {
    
    }
    
    public DataSet(String sql, HashMap<String, Object> parametros, String columnaID) throws SQLException {
        this.sql = sql;
        this.parametros = parametros;
        this.rowSelectColumnaID = columnaID;
        recuperarDatos();
    }
    
    public DataSet(String sql, String columnaID) throws SQLException {
        this.sql = sql;
        this.rowSelectColumnaID = columnaID;
        recuperarDatos();
    }

    public void actualizarFilaSeleccionada() throws SQLException {
        String sqlItem = "SELECT * FROM (" + sql + ") DS_T1 WHERE " + this.getSelectedRow().getColumnaID().getName() + " = :" + this.getSelectedRow().getColumnaID().getName() + "_ID";
        HashMap<String, Object> parametrosItem = (HashMap<String, Object>)parametros.clone();
        parametrosItem.put(this.getSelectedRow().getColumnaID().getName() + "_ID", this.getSelectedRow().getColumnaID().getValue());
        
        ArrayList<LinkedHashMap<String,Object>> lista = executeNativeQueryListParametros(sqlItem, parametrosItem, null);
        rellenarFila(lista.get(0), this.getSelectedRow(), false);
    }
    
    public void eliminarFilaSeleccionada() {
        this.getRows().remove(this.getSelectedRow());
    }
    
    public void insertarFilaNueva(Object idNuevaFila) throws SQLException {
        if (this.sql != null)
        {
            String sqlItem = "SELECT * FROM (" + sql + ") DS_T1 WHERE " + this.rowSelectColumnaID + " = :" + this.rowSelectColumnaID + "_ID";
            HashMap<String, Object> parametrosItem = (HashMap<String, Object>)parametros.clone();
            parametrosItem.put(this.rowSelectColumnaID + "_ID", idNuevaFila);

            ArrayList<LinkedHashMap<String,Object>> lista = executeNativeQueryListParametros(sqlItem, parametrosItem, null);
            Row fila = new Row(this);
            fila.index = this.getRowsCount();
            rellenarFila(lista.get(0), fila, true);
        }
    }
    
    public void refrescarDatos() throws SQLException {
        if (this.sql != null) {
            ArrayList<LinkedHashMap<String,Object>> lista = executeNativeQueryListParametros(sql, parametros, null);
            if (lista != null && !lista.isEmpty()) {
                this.getRows().clear();
                this.setSelectedRow(null);
                rellenaDatos(lista);
            }
        }
    }
    
    private void recuperarDatos() throws SQLException {
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
            ColumnCabecera columnaCab = new ColumnCabecera(this.cabecera);
            columnaCab.index = i++;
            columnaCab.name = itemColumn.getKey();
            columnaCab.title = itemColumn.getKey();
            this.cabecera.getColumns().add(columnaCab);
        }
    }
    
    private void rellenaDatos(ArrayList<LinkedHashMap<String,Object>> lista) {
        int f = 0;
        for (LinkedHashMap<String,Object> itemRow : lista) {
            Row fila = new Row(this);
            fila.index = f++;
            rellenarFila(itemRow, fila, true);
        }
    }
    
    private void rellenarFila(LinkedHashMap<String,Object> itemRow, Row fila, boolean nuevaFila) {
        int c = 0;
        for (Map.Entry<String, Object> itemColumn : itemRow.entrySet()) {
            if (nuevaFila) {
                Column columna = new Column(fila);
                columna.setCabecera((ColumnCabecera)this.cabecera.getColumns().get(c));
                columna.index = c++;
                columna.name = itemColumn.getKey();
                columna.value = itemColumn.getValue();
                columna.valueString = columna.recuperaValorString();
                columna.tooltip = columna.valueString;
                fila.getColumns().add(columna);
            }
            else {
                 fila.getColumnName(itemColumn.getKey()).setValue(itemColumn.getValue());
            }
        }
        if (nuevaFila) {
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

    public Method getRowSelectMethod() {
        return rowSelectMethod;
    }

    public void setRowSelectMethod(Method rowSelectMethod) {
        this.rowSelectMethod = rowSelectMethod;
    }

    public Object getRowSelectClass() {
        return rowSelectClass;
    }

    public void setRowSelectClass(Object rowSelectClass) {
        this.rowSelectClass = rowSelectClass;
    }

    public String getRowSelectUpdate() {
        return rowSelectUpdate;
    }

    public void setRowSelectUpdate(String rowSelectUpdate) {
        this.rowSelectUpdate = rowSelectUpdate;
    }
    
    public String onRowSelect() throws Exception {
        return (String)this.rowSelectMethod.invoke(this.rowSelectClass);
    }

    public String getRowSelectColumnaID() {
        return rowSelectColumnaID;
    }

    public void setRowSelectColumnaID(String rowSelectColumnaID) {
        this.rowSelectColumnaID = rowSelectColumnaID;
    }

    public String getHeightFiltro() {
        return heightFiltro;
    }

    public void setHeightFiltro(String heightFiltro) {
        this.heightFiltro = heightFiltro;
        this.height = "calc(100vh - " + this.heightFiltro + ")";
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public ArrayList<Row> getSelectedRows() {
        return selectedRows;
    }

    public void setSelectedRows(ArrayList<Row> selectedRows) {
        this.selectedRows = selectedRows;
    }

    public Boolean getSelectable() {
        return selectable;
    }

    public void setSelectable(Boolean selectable) {
        this.selectable = selectable;
    }

    public DataSet newColumn(String columnName) {
        ColumnCabecera itemColCab = new ColumnCabecera(this.getCabecera());
        itemColCab.index = this.cabecera.columns.size();
        itemColCab.name = columnName;
        itemColCab.title = columnName;
        this.getCabecera().getColumns().add(itemColCab);

        for(Row itemRow : this.getRows()) {
            Column itemCol = new Column(itemRow);
            itemCol.setCabecera(itemColCab);
            itemCol.index = itemRow.columns.size();
            itemCol.name = columnName;
            itemCol.value = null;
            itemCol.valueString = null;
            itemCol.tooltip = columnName;
            
            itemRow.getColumns().add(itemCol);
        }

        return this;
    }
}
