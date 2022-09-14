package seguridad.login;

import basedatos.servicios.StTUsuario;
import basedatos.tablas.BdTUsuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.apache.log4j.Logger;
import seguridad.usuarios.DatosUsuario;
import utilidades.Mensajes;
import utilidades.Session;

/**
 *
 * @author ihuegal
 */
@Named
@SessionScoped
public class LoginForm implements Serializable {

    private String usuario;
    private String password;

    @PostConstruct
    public void init() {
        //Inicializacion
        this.usuario = null;
        this.password = null;
    }

    public String login() {
        try {
            StTUsuario stTUsuario = new StTUsuario();
            BdTUsuario filtroBdTUsuario = new BdTUsuario();
            filtroBdTUsuario.setCoUsuario(this.usuario);
            filtroBdTUsuario.setFeAlta(new Date());
            filtroBdTUsuario.setFeDesactivo(new Date());
            ArrayList<BdTUsuario> listaBdTUsuario = stTUsuario.filtro(filtroBdTUsuario, null);
            
            if (listaBdTUsuario != null && !listaBdTUsuario.isEmpty()) {
                if (listaBdTUsuario.get(0).getCoPassword().equals(this.password))
                {
                    //ACCESO CONCEDIDO
                    Session.getDatosUsuario().setBdTUsuario(listaBdTUsuario.get(0));
                    
                    //Redirecciono al formulario principal
                    return "main";
                }
            }

            //ACCESO DENEGADO
            //MENSAJE
            new Mensajes().showWarn("Acceso denegado", "Usuario o contraseña incorrectos");
            
            return null;
        } catch (Exception ex) {
            Logger.getLogger(LoginForm.class).error(ex);
        }
        return null;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
