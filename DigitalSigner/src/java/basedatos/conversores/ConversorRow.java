package basedatos.conversores;

import basedatos.AutocompleteItem;
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
public class ConversorRow implements Converter<AutocompleteItem> {
    
    @Override
    public AutocompleteItem getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null && value.trim().length() > 0) {
            try {
                return new AutocompleteItem(Integer.valueOf(value.split("#")[0]), value.split("#")[1]);
            } catch (NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid row."));
            }
        } else {
            return null;
        }
    }
    
    @Override
    public String getAsString(FacesContext fc, UIComponent uic, AutocompleteItem value) {
        if (value != null && value.getId() != null) {
            return value.getId() + "#" + value.getLabel();
        } else {
            return null;
        }
    }
}
