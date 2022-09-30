package utilidades;

/**
 *
 * @author ihuegal
 */
public class CampoWebCheckTriState extends CampoWeb {
    public CampoWebCheckTriState() {
        super(Tipo.Check);
        super.setWidth(null);
        super.setMaxlength(null);
    }
    
    public Boolean getBooleanValue() {
        if (super.getValue() != null) {
            switch ((String)super.getValue()) {
                case "0" -> { return null; }
                case "1" -> { return true; }
                case "2" -> { return false; }
                default -> { return null; }
            }
        }
        else {
            return null;
        }
    }
    
    public void setBooleanValue(Boolean boValue) {
        if (boValue != null) {
            super.setValue((boValue ? "1" : "2"));
        }
        else {
            super.setValue("0");
        }
    }
}
