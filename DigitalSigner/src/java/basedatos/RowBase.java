package basedatos;

import java.util.ArrayList;

/**
 *
 * @author ihuegal
 */
public class RowBase {
    
    protected ArrayList<ColumnBase> columns = new ArrayList<>();
    protected int index = 0;
    
    public RowBase() {
        // NADA
    }

    public ArrayList<ColumnBase> getColumns() {
        return columns;
    }

    public void setColumns(ArrayList<ColumnBase> columns) {
        this.columns = columns;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
    
    public ColumnBase getColumnName(String name) {
        for (ColumnBase itemColumn : this.columns) {
            if (itemColumn.getName().equalsIgnoreCase(name)) {
                return itemColumn;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "" + index;
    }
}
