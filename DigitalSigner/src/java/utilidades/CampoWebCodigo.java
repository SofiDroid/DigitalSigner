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
        super.setValue(value);
    }

    @Override
    public String getValue() {
        return (String)super.getValue();
    }   
}
