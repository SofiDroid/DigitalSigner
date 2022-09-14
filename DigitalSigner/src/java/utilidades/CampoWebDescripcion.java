package utilidades;

/**
 *
 * @author ihuegal
 */
public class CampoWebDescripcion extends CampoWeb {
    public CampoWebDescripcion() {
        super(Tipo.Descripcion);
        super.setMaxlength("255");
    }

    public void setValue(String value) {
        super.setValue(value);
    }

    @Override
    public String getValue() {
        return (String)super.getValue();
    }
}
