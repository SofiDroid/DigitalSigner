package basedatos.conversores;

import basedatos.Row;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Named;

/**
 *
 * @author ihuegal
 */
@Named
@FacesConverter(value = "rowConverter", managed = true)
public class ConversorRow implements Converter<Row> {
    
    @Override
    public Row getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null && value.trim().length() > 0) {
            try {
                Row itemRow = new Row(null);
                itemRow.setIndex(Integer.valueOf(value));
                return itemRow; //value;//((Row)((AutoComplete)component).getValue());
            } catch (NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid row."));
            }
        } else {
            return null;
        }
    }
    
    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Row value) {
        if (value != null) {
            return String.valueOf(((Row)value).getIndex());
        } else {
            return null;
        }
    }
}
