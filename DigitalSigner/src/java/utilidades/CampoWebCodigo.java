package utilidades;

/**
 *
 * @author ihuegal
 */
public class CampoWebCodigo extends CampoWeb {
    public CampoWebCodigo() {
        super(Tipo.Codigo);
        super.setWidth("6rem");
        super.setMaxlength("50");
    }
    
    public void setValue(String value) {
        super.setValue(((value != null && !value.isBlank()) ? value : null));
    }

    @Override
    public String getValue() {
        return ((super.getValue() != null  && !((String)super.getValue()).isBlank()) ? (String)super.getValue() : null);
    }
   
}
