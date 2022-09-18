package seguridad.usuarios;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;
import basedatos.tablas.Pais;
import basedatos.servicios.ServicioPaises;
import basedatos.tablas.BdTUnidad;
import basedatos.tablas.BdTUsuario;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author ihuegal
 */
@Named
@SessionScoped
public class DatosUsuario implements Serializable {
    
    private ArrayList<BdTUnidad> listaBdTUnidad = new ArrayList<>();
    private BdTUsuario bdTUsuario = null;
    private BdTUnidad bdTUnidad = null;

    private Pais pais;
    private List<Pais> paises;
    
    @Inject
    private ServicioPaises servicioPaises;
    
    @PostConstruct
    public void init() {
        //Paises
        paises = servicioPaises.getLocales();
        pais = paises.get(0);
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    public List<Pais> getPaises() {
        return paises;
    }

    public void setPaises(List<Pais> paises) {
        this.paises = paises;
    }

    public ServicioPaises getServicioPaises() {
        return servicioPaises;
    }

    public void setServicioPaises(ServicioPaises servicioPaises) {
        this.servicioPaises = servicioPaises;
    }
    
    public void onPaisChange(SelectEvent e) {
        if (e != null) {
            FacesContext.getCurrentInstance()
               .getViewRoot().setLocale(((Pais)e.getObject()).getLocale());
        }
    } 

    public BdTUsuario getBdTUsuario() {
        return bdTUsuario;
    }

    public void setBdTUsuario(BdTUsuario bdTUsuario) {
        this.bdTUsuario = bdTUsuario;
    }
    
    public String getModoFirma() {
        return "";
    }
    
    public String getIpRemota() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }
        return ipAddress;
    }
    
    public String getIpExtranet() {
        return "0.0.0.0";
    }

    public BdTUnidad getBdTUnidad() {
        return bdTUnidad;
    }

    public void setBdTUnidad(BdTUnidad bdTUnidad) {
        this.bdTUnidad = bdTUnidad;
    }

    public ArrayList<BdTUnidad> getListaBdTUnidad() {
        return listaBdTUnidad;
    }

    public void setListaBdTUnidad(ArrayList<BdTUnidad> listaBdTUnidad) {
        this.listaBdTUnidad = listaBdTUnidad;
    }
}
