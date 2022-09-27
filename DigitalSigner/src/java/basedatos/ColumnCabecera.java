package basedatos;


/**
 *
 * @author ihuegal
 */
public class ColumnCabecera extends ColumnBase {
    
    public enum ALIGN {
        LEFT("left"),
        CENTER("center"),
        RIGHT("right"),
        JUSTIFY("justify");
        
        private final String value;

        private ALIGN(String value) {
            this.value = value;
        }
        
        public String getValue() {
            return this.value;
        }
    }
    
    protected String title = "";
    protected String valueProperty = "value";
    protected String valueString = "valueString";
    protected String width = "100%";
    protected ALIGN align = ALIGN.LEFT;
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

    public ColumnCabecera setFiltro(boolean filtro) {
        this.filtro = filtro;
        return this;
    }

    public String getTooltipColumn() {
        return tooltipColumn;
    }

    public ColumnCabecera setTooltipColumn(String tooltipColumn) {
        this.tooltipColumn = tooltipColumn;
        return this;
    }

    public String getAlign() {
        return align.getValue();
    }

    public ColumnCabecera setAlign(ALIGN align) {
        this.align = align;
        return this;
    }
}
