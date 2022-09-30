package utilidades;

/**
 *
 * @author ihuegal
 */
public class CampoWebCheck extends CampoWeb {
    public CampoWebCheck() {
        super(Tipo.Check);
        super.setWidth(null);
        super.setMaxlength(null);
    }
    
    @Override
    @Deprecated(forRemoval = true)
    public void setValue(Object value) {
        if (value instanceof Boolean boValue) {
            super.setValue(boValue);
        }
        super.setValue(null);
    }
    
    public void setValue(Boolean value) {
        super.setValue(value);
    }

    @Override
    public Boolean getValue() {
        return (Boolean)super.getValue();
    }   
}
