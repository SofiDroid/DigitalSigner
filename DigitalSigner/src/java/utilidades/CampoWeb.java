package utilidades;

/**
 *
 * @author ihuegal
 */
public class CampoWeb {
    
    public static enum Tipo {
        Codigo,
        Descripcion,
        Fecha,
        FechaRango
    }
    
    private Tipo tipo = Tipo.Codigo;
    private Object value = null;
    private boolean protegido = false;

    public CampoWeb(Tipo tipo) {
        this.tipo = tipo;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }
    
    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public boolean isProtegido() {
        return protegido;
    }

    public void setProtegido(boolean protegido) {
        this.protegido = protegido;
    }
}
