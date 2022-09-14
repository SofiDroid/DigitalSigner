package utilidades;

import java.util.Date;

/**
 *
 * @author ihuegal
 */
public class CampoWebFechaRango extends CampoWeb {
    protected Date valueIni = null;
    protected Date valueFin = null;
    
    public CampoWebFechaRango() {
        super(Tipo.FechaRango);
        super.setWidth("7rem");
    }

    public Date getValueIni() {
        return valueIni;
    }

    public void setValueIni(Date valueIni) {
        this.valueIni = valueIni;
    }

    public Date getValueFin() {
        return valueFin;
    }

    public void setValueFin(Date valueFin) {
        this.valueFin = valueFin;
    }
}
