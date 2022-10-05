package seguridad.login;

import basedatos.servicios.StAUniusu;
import basedatos.servicios.StTUnidad;
import basedatos.servicios.StTUsuario;
import basedatos.tablas.BdAUniusu;
import basedatos.tablas.BdTUnidad;
import basedatos.tablas.BdTUsuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.apache.log4j.Logger;
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
            Session.limpiarOtrosBeans(this.getClass().getName(), true, true);
            StTUsuario stTUsuario = new StTUsuario();
            BdTUsuario filtroBdTUsuario = new BdTUsuario();
            filtroBdTUsuario.setCoUsuario(this.usuario);
            filtroBdTUsuario.setFeAlta(new Date());
            filtroBdTUsuario.setFeDesactivo(new Date());
            ArrayList<BdTUsuario> listaBdTUsuario = stTUsuario.filtro(filtroBdTUsuario, null);
            
            if (listaBdTUsuario != null && !listaBdTUsuario.isEmpty()) {
                if (listaBdTUsuario.get(0).getCoPassword().equals(this.password))
                {
                    //ACCESO CONCEDIDO - Cargar datos del usuario en sesion.
                    Session.getDatosUsuario().setBdTUsuario(listaBdTUsuario.get(0));
                    
                    BdAUniusu filtroBdAUniusu = new BdAUniusu();
                    filtroBdAUniusu.setIdUsuario(listaBdTUsuario.get(0).getIdUsuario());
                    filtroBdAUniusu.setFeAlta(new Date());
                    filtroBdAUniusu.setFeDesactivo(new Date());
                    StAUniusu stAUniusu = new StAUniusu();
                    ArrayList<BdAUniusu> listaBdAUniusu = stAUniusu.filtro(filtroBdAUniusu, null);
                    for(BdAUniusu itemBdAUniusu : listaBdAUniusu) {
                        BdTUnidad filtroBdTUnidad = new BdTUnidad();
                        filtroBdTUnidad.setIdUnidad(itemBdAUniusu.getIdUnidad());
                        filtroBdAUniusu.setFeAlta(new Date());
                        filtroBdAUniusu.setFeDesactivo(new Date());
                        StTUnidad stTUnidad = new StTUnidad();
                        ArrayList<BdTUnidad> listaBdTUnidad = stTUnidad.filtro(filtroBdTUnidad, null);
                        if (listaBdTUnidad != null && !listaBdTUnidad.isEmpty()) {
                            Session.getDatosUsuario().getListaBdTUnidad().addAll(listaBdTUnidad);
                        }
                    }
                    Session.getDatosUsuario().cargarComboUnidades();
                    Session.getDatosUsuario().setBdTUnidad(Session.getDatosUsuario().getListaBdTUnidad().get(0));
                    //Session.getDatosUsuario().getcUnidad().setValue(Session.getDatosUsuario().getListaBdTUnidad().get(0).getIdUnidad().toString());
                            
                    //Redirecciono al formulario principal
                    return "main";
                }
            }

            //ACCESO DENEGADO
            //MENSAJE
            Mensajes.showWarn("Acceso denegado", "Usuario o contraseña incorrectos");
            
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
