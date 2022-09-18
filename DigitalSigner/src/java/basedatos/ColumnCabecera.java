package basedatos;


/**
 *
 * @author ihuegal
 */
public class ColumnCabecera extends ColumnBase {
    
    protected String title = "";
    protected String valueProperty = "value";
    protected String valueString = "valueString";
    protected String width = "100%";
    protected boolean visible = true;
    protected boolean filtro = false;
    protected String tooltipColumn = null;
    
    public ColumnCabecera(RowCabecera parent) {
        super(parent);
    }

    public String getTitle() {
        return title;
    }

    public ColumnCabecera setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getWidth() {
        return width;
    }

    public ColumnCabecera setWidth(String width) {
        this.width = width;
        return this;
    }

    public String getValueProperty() {
        return valueProperty;
    }

    public void setValueProperty(String valueProperty) {
        this.valueProperty = valueProperty;
    }

    public String getValueString() {
        return valueString;
    }

    public void setValueString(String valueString) {
        this.valueString = valueString;
    }

    public boolean isVisible() {
        return visible;
    }

    public ColumnCabecera setVisible(boolean visible) {
        this.visible = visible;
        return this;
    }

    public boolean isFiltro() {
        return filtro;
    }

    public void setFiltro(boolean filtro) {
        this.filtro = filtro;
    }

    public String getTooltipColumn() {
        return tooltipColumn;
    }

    public void setTooltipColumn(String tooltipColumn) {
        this.tooltipColumn = tooltipColumn;
    }
}
