package basedatos;

import java.lang.reflect.Method;


/**
 *
 * @author ihuegal
 */
public class ColumnBase {
    public enum Tipo {
        TEXTO,
        LINK,
        BOTON,
        BOTON_EDICION,
        IMAGEN,
        CHECKBOX,
        MEDIA
    }
    
    protected RowBase parent;
    protected String name = "";
    protected int index = 0;
    protected Tipo tipo = Tipo.TEXTO;
    protected Method method = null;
    protected Object clase = null;
    protected String update = "@widgetVar(mensaje)";
    protected String tooltip = "";
    protected String oncomplete = "";
    protected String icon = "pi pi-file-pdf";
    protected String styleClass = "button-inline";
    protected boolean rendered = true;
    
    public ColumnBase(RowBase parent) {
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public ColumnBase setTipo(Tipo tipo) {
        this.tipo = tipo;
        return this;
    }

    public Method getMethod() {
        return method;
    }

    public ColumnBase setMethod(Method method) {
        this.method = method;
        return this;
    }

    public ColumnBase setClase(Object clase) {
        this.clase = clase;
        return this;
    }

    public Object getClase() {
        return clase;
    }

    public String getUpdate() {
        return update;
    }

    public ColumnBase setUpdate(String update) {
        this.update = update;
        return this;
    }
    
    public String getOncomplete() {
        return oncomplete;
    }
    
    public ColumnBase setOncomplete(String oncomplete) {
        this.oncomplete = oncomplete;
        return this;
    }
    
    public String ejecutarMetodo() throws Exception {
        return (String)this.method.invoke(this.clase);
    }
    
    public String getTooltip() {
        return tooltip;
    }

    public ColumnBase setTooltip(String tooltip) {
        this.tooltip = tooltip;
        return this;
    }

    public RowBase getParent() {
        return parent;
    }

    public void setParent(RowBase parent) {
        this.parent = parent;
    }

    public String getIcon() {
        return icon;
    }

    public ColumnBase setIcon(String icon) {
        this.icon = icon;
        return this;
    }

    public String getStyleClass() {
        return styleClass;
    }

    public ColumnBase setStyleClass(String styleClass) {
        this.styleClass = styleClass;
        return this;
    }

    public boolean getRendered() {
        return rendered;
    }

    public ColumnBase setRendered(boolean rendered) {
        this.rendered = rendered;
        return this;
    }    
}
