package utilidades;

import java.math.BigDecimal;

/**
 *
 * @author ihuegal
 */
public class CampoWebNumero extends CampoWeb {
    
    protected String moneda = "";
    protected String minValue = "";
    protected String maxValue = "";
    protected Integer numDecimales = null;
    
    public CampoWebNumero() {
        super(Tipo.Numero);
    }

    public void setValueInteger(Integer value) {
        super.setValue(value);
    }

    public void setValueBigDecimal(BigDecimal value) {
        super.setValue(value);
    }
    
    public Integer getValueInteger() {
        if (super.getValue() != null) {
            if (super.getValue() instanceof Integer intValue) {
                return intValue;
            }
            else if (super.getValue() instanceof BigDecimal bigValue) {
                return bigValue.intValue();
            }
            else if (super.getValue() instanceof String stringValue) {
                return Integer.valueOf(stringValue);
            }
            else {
                return null;
            }
        }
        return null;
    }
    
    public BigDecimal getValueBigDecimal() {
        if (super.getValue() != null) {
            if (super.getValue() instanceof Integer intValue) {
                return BigDecimal.valueOf(intValue);
            }
            else if (super.getValue() instanceof BigDecimal bigValue) {
                return bigValue;
            }
            else if (super.getValue() instanceof String stringValue) {
                return BigDecimal.valueOf(Double.valueOf(stringValue));
            }
            else {
                return null;
            }
        }
        return null;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public String getMinValue() {
        return minValue;
    }

    public void setMinValue(String minValue) {
        this.minValue = minValue;
    }

    public String getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(String maxValue) {
        this.maxValue = maxValue;
    }

    public Integer getNumDecimales() {
        if (numDecimales == null) {
            if (super.getValue() instanceof Integer) {
                return 0;
            }
            else if (super.getValue() instanceof BigDecimal) {
                return 2;
            }
            else {
                return 0;
            }
        }
        return numDecimales;
    }

    public void setNumDecimales(Integer numDecimales) {
        this.numDecimales = numDecimales;
    }
}
