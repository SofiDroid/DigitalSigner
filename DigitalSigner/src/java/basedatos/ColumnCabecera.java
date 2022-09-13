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
    protected String tooltip = "tooltip";
    
    public ColumnCabecera() {
        // NADA        
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

    public String getTooltip() {
        return tooltip;
    }

    public ColumnCabecera setTooltip(String tooltip) {
        this.tooltip = tooltip;
        return this;
    }
}
