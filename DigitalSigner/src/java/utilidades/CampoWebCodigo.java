package utilidades;

/**
 *
 * @author ihuegal
 */
public class CampoWebCodigo extends CampoWeb {
    public CampoWebCodigo() {
        super(Tipo.Codigo);
    }
    
    public void setValue(String value) {
        super.setValue(value);
    }

    @Override
    public String getValue() {
        return (String)super.getValue();
    }
}
