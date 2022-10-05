package basedatos;

import java.math.BigDecimal;
import java.util.Date;
import utilidades.Formateos;

/**
 *
 * @author ihuegal
 */
public class Column extends ColumnBase {
    
    protected ColumnCabecera cabecera;
    protected Object value = "";
    protected String valueString = "";
    
    public Column(Row parent) {
        super(parent);
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
        this.valueString = recuperaValorString();
    }

    public String getValueString() {
        return valueString;
    }

    public void setValueString(String valueString) {
        this.valueString = valueString;
    }
    
    public Integer getValueInteger() {
        if (this.value != null) {
            if (this.value instanceof BigDecimal valorBigDecimal) {
                return valorBigDecimal.intValue();
            }
            else if (this.value instanceof Integer valorInteger) {
                return valorInteger;
            }
            else if (this.value instanceof Double valorDouble) {
                return valorDouble.intValue();
            }
            else if (this.value instanceof String valorString) {
                return Integer.valueOf(valorString);
            }
            else if (this.value instanceof Date valorDate) {
                return Long.valueOf(valorDate.getTime()).intValue();
            }
            else {
                return null;
            }
        }
        else {
            return null;
        }
    }
    
    public String recuperaValorString() {
        if (this.value != null) {
            if (this.value instanceof Date valorDate) {
                return Formateos.dateToString(valorDate, Formateos.TipoFecha.FECHA);
            }
            else if (this.value instanceof String valorString) {
                return valorString;
            }
            else if (this.value instanceof BigDecimal valorBigDecimal) {
                if (valorBigDecimal.scale() > 0) {
                    return valorBigDecimal.toPlainString();
                }
                else {
                    return valorBigDecimal.toPlainString();
                }
            }
            else {
                return value.toString();
            }
        }
        else {
            return null;
        }
    }

    public ColumnCabecera getCabecera() {
        return this.cabecera;
    }
    
    public void setCabecera(ColumnCabecera cabecera) {
        this.cabecera = cabecera;
    }

    @Override
    public String getTooltip() {
        if (this.cabecera.tooltipColumn != null) {
            return ((Row)this.parent).getColumnName(this.cabecera.tooltipColumn).getValueString();
        }

        return tooltip;
    }
}
