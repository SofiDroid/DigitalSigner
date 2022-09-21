package generarmantenimiento;

import org.w3c.dom.Element;

/**
 *
 * @author Óscar
 */
public class ServidorConfig {

    public String nombre,

    /**
     *
     */
    user,

    /**
     *
     */
    pass,

    /**
     *
     */
    url;

    /**
     *
     * @param nombre
     * @param user
     * @param pass
     * @param url
     */
    public ServidorConfig(String nombre, String user, String pass, String url) {
        this.nombre = nombre;
        this.user = user;
        this.pass = pass;
        this.url = url;
    }

    /**
     *
     */
    public ServidorConfig() {

    }

    /**
     *
     * @param element
     */
    public ServidorConfig(Element element) {
        this.nombre = element.getAttribute("name");
        this.user = element.getAttribute("user");
        this.pass = element.getAttribute("pass");
        this.url = element.getAttribute("url");
    }

    @Override
    public String toString() {
        return nombre;
    }
}
