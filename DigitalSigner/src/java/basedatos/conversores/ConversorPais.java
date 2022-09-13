package basedatos.conversores;

import basedatos.servicios.ServicioPaises;
import basedatos.tablas.Pais;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author ihuegal
 */
@Named
@FacesConverter(value = "countryConverter", managed = true)
public class ConversorPais implements Converter<Pais> {
    
    @Inject
    private ServicioPaises countryService;
    
    @Override
    public Pais getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null && value.trim().length() > 0) {
            try {
                return countryService.getLocalesAsMap().get(Integer.parseInt(value));
            } catch (NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid country."));
            }
        } else {
            return null;
        }
    }
    @Override
    public String getAsString(FacesContext context, UIComponent component, Pais value) {
        if (value != null) {
            return String.valueOf(value.getId());
        } else {
            return null;
        }
    }
}
