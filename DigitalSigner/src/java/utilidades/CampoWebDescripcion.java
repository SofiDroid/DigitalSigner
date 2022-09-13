package utilidades;

/**
 *
 * @author ihuegal
 */
public class CampoWebDescripcion extends CampoWeb {
    public CampoWebDescripcion() {
        super(Tipo.Descripcion);
    }

    public void setValue(String value) {
        super.setValue(value);
    }

    @Override
    public String getValue() {
        return (String)super.getValue();
    }
}
