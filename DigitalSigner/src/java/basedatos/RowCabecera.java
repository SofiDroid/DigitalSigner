package basedatos;

/**
 *
 * @author ihuegal
 */
public class RowCabecera extends RowBase{
    
    public RowCabecera() {
        // NADA
    }

    @Override
    public ColumnCabecera getColumnName(String name) {
        return (ColumnCabecera)super.getColumnName(name);
    }
}
