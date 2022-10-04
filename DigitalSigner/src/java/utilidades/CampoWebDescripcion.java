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
        super.setValue(((value != null && !value.isBlank()) ? value : null));
    }

    @Override
    public String getValue() {
        return ((super.getValue() != null  && !((String)super.getValue()).isBlank()) ? (String)super.getValue() : null);
    }
}
