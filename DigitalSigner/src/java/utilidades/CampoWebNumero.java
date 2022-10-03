package utilidades;

import java.math.BigDecimal;

/**
 *
 * @author ihuegal
 */
public class CampoWebNumero extends CampoWeb {
    
    protected String moneda = "";
    protected BigDecimal minValue = BigDecimal.valueOf(Integer.MIN_VALUE);
    protected BigDecimal maxValue = BigDecimal.valueOf(Integer.MAX_VALUE);
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
        if (super.getValue() != null && !super.getValue().toString().isBlank()) {
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
        if (super.getValue() != null && !super.getValue().toString().isBlank()) {
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

    public BigDecimal getMinValue() {
        return minValue;
    }

    public void setMinValue(BigDecimal minValue) {
        this.minValue = minValue;
    }

    public void setMinValue(Integer minValue) {
        if (minValue != null) {
            this.minValue = BigDecimal.valueOf(minValue);
        }
        else {
            this.minValue = BigDecimal.valueOf(Integer.MIN_VALUE);
        }
    }

    public BigDecimal getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(BigDecimal maxValue) {
        this.maxValue = maxValue;
    }

    public void setMaxValue(Integer maxValue) {
        if (maxValue != null) {
            this.maxValue = BigDecimal.valueOf(maxValue);
        }
        else {
            this.maxValue = BigDecimal.valueOf(Integer.MAX_VALUE);
        }
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
