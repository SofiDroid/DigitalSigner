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
        IMAGEN,
        CHECKBOX
    }
    
    protected String name = "";
    protected int index = 0;
    protected Tipo tipo = Tipo.TEXTO;
    protected Method method = null;
    protected Object clase = null;
    protected String update = "@widgetVar(mensaje)";
    
    public ColumnBase() {
        // NADA        
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
    
    public String ejecutarMetodo() throws Exception {
        return (String)this.method.invoke(this.clase);
    }
}
