package basedatos.servicios;

import basedatos.tablas.Pais;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

/**
 *
 * @author ihuegal
 */
@Named
@ApplicationScoped
public class ServicioPaises {

    private List<Pais> countries;
    private Map<Integer, Pais> countriesAsMap;
    private List<Pais> locales;
    private Map<Integer, Pais> localesAsMap;

    @PostConstruct
    public void init() {
        countries = new ArrayList<>();
        locales = new ArrayList<>();

        String[] isoCodes = Locale.getISOCountries();

        for (int i = 0; i < isoCodes.length; i++) {
            Locale locale = new Locale("", isoCodes[i]);
            countries.add(new Pais(i, locale));
        }

        Collections.sort(countries, (Pais c1, Pais c2) -> c1.getName().compareTo(c2.getName()));

        int i = 0;
        locales.add(new Pais(i++, new Locale("es", "ES")));
        locales.add(new Pais(i++, new Locale("ca","ES")));
        locales.add(new Pais(i++, new Locale("gl","ES")));
        locales.add(new Pais(i++, new Locale("eu","ES")));
        //locales.add(new Pais(i++, Locale.US));
        locales.add(new Pais(i++, new Locale("en","UK")));
//        locales.add(new Country(i++, Locale.FRANCE));
//        locales.add(new Country(i++, Locale.GERMANY));
//        locales.add(new Country(i++, Locale.ITALY));
//        locales.add(new Country(i++, Locale.KOREA));
//        locales.add(new Country(i++, new Locale("nl", "NL")));
//        locales.add(new Country(i++, new Locale("pt", "BR")));
//        locales.add(new Country(i++, new Locale("pt", "PT")));
//        locales.add(new Country(i++, new Locale("ar", "SA"), true));
//        locales.add(new Country(i++, new Locale("cs", "CZ")));
//        locales.add(new Country(i++, new Locale("el", "GR")));
//        locales.add(new Country(i++, new Locale("fa", "IR"), true));
//        locales.add(new Country(i++, new Locale("hi", "IN")));
//        locales.add(new Country(i++, new Locale("in", "ID")));
//        locales.add(new Country(i++, new Locale("hr", "HR")));
//        locales.add(new Country(i++, new Locale("hu", "HU")));
//        locales.add(new Country(i++, new Locale("iw", "IL"), true));
//        locales.add(new Country(i++, new Locale("ka", "GE")));
//        locales.add(new Country(i++, new Locale("lt", "LT")));
//        locales.add(new Country(i++, new Locale("lv", "LV")));
//        locales.add(new Country(i++, new Locale("no", "NO")));
//        locales.add(new Country(i++, new Locale("pl", "PL")));
//        locales.add(new Country(i++, new Locale("ro", "RO")));
//        locales.add(new Country(i++, new Locale("ru", "RU")));
//        locales.add(new Country(i++, new Locale("sk", "SK")));
//        locales.add(new Country(i++, new Locale("sl", "SI")));
//        locales.add(new Country(i++, new Locale("sr", "RS")));
//        locales.add(new Country(i++, new Locale("sv", "SE")));
//        locales.add(new Country(i++, new Locale("tr", "TR")));
//        locales.add(new Country(i++, new Locale("uk", "UA")));
//        locales.add(new Country(i++, new Locale("vi", "VN")));
//        locales.add(new Country(i++, Locale.SIMPLIFIED_CHINESE));
//        locales.add(new Country(i++, Locale.TRADITIONAL_CHINESE));
    }

    public List<Pais> getCountries() {
        return new ArrayList<>(countries);
    }

    public Map<Integer, Pais> getCountriesAsMap() {
        if (countriesAsMap == null) {
            countriesAsMap = getCountries().stream().collect(Collectors.toMap(Pais::getId, country -> country));
        }
        return countriesAsMap;
    }

    public List<Pais> getLocales() {
        return new ArrayList<>(locales);
    }

    public Map<Integer, Pais> getLocalesAsMap() {
        if (localesAsMap == null) {
            localesAsMap = getLocales().stream().collect(Collectors.toMap(Pais::getId, country -> country));
        }
        return localesAsMap;
    }
}
