package utilidades;

import java.util.Date;

/**
 *
 * @author ihuegal
 */
public class CampoWebFecha extends CampoWeb {
    public CampoWebFecha() {
        super(Tipo.Fecha);
        super.setWidth("7rem");
    }

    @Override
    public Date getValue() {
        return (Date)super.getValue();
    }

    public void setValue(Date value) {
        super.setValue(value);
    }
}
