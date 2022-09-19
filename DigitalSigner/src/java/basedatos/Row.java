package basedatos;

/**
 *
 * @author ihuegal
 */
public class Row extends RowBase{
    
    protected DataSet parent = null;
    
    public Row(DataSet parent) {
        this.parent = parent;
    }
    
    @Override
    public Column getColumnName(String name) {
        return (Column)super.getColumnName(name);
    }

    public Column getColumnaID() {
        if (this.parent.rowSelectColumnaID == null) {
            return (Column)super.getColumns().get(0);
        }
        return (Column)super.getColumnName(this.parent.rowSelectColumnaID);
    }

    public DataSet getParent() {
        return parent;
    }

    public void setParent(DataSet parent) {
        this.parent = parent;
    }
    
    public Object getId() {
        return this.getColumnName(this.parent.rowSelectColumnaID).getValue();
    }
}
