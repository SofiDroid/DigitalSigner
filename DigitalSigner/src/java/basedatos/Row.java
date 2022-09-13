package basedatos;

/**
 *
 * @author ihuegal
 */
public class Row extends RowBase{
    
    public Row() {
        // NADA
    }
    
    @Override
    public Column getColumnName(String name) {
        return (Column)super.getColumnName(name);
    }
}
