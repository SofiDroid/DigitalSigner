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
import utilidades.CampoWebCodigo;
import utilidades.CampoWebCombo;
import utilidades.Mensajes;
import utilidades.Msg;
import utilidades.Session;

/**
 *
 * @author ihuegal
 */
@Named
@SessionScoped
public class DatosUsuario implements Serializable {
    
    private CampoWebCombo cUnidad = null;
    private CampoWebCodigo cUsuario = null;
    private ArrayList<BdTUnidad> listaBdTUnidad = new ArrayList<>();
    private BdTUsuario bdTUsuario = null;
    private BdTUnidad bdTUnidad = null;

    private Pais pais;

    @PostConstruct
    public void init() {
        this.cUsuario = new CampoWebCodigo();
        this.cUsuario.setLabel(Msg.getString("lblUsuarioBD"));
        this.cUsuario.setWidthLabel("4em");
        this.cUsuario.setProtegido(true);

        this.cUnidad = new CampoWebCombo();
        this.cUnidad.setLabel("Unidad Activa");
        this.cUnidad.setWidthLabel("7em");
        this.cUnidad.setWidth("35em");
        this.cUnidad.setUpdate("@form");
        this.cUnidad.setSelectClass(this);
        try {
            this.cUnidad.setSelectMethod(this.getClass().getMethod("selectOptionUnidad"));
        } catch (Exception ex) {
            Mensajes.showException(DatosUsuario.class, ex);
        }
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    public BdTUsuario getBdTUsuario() {
        return bdTUsuario;
    }

    public void setBdTUsuario(BdTUsuario bdTUsuario) {
        this.bdTUsuario = bdTUsuario;
        this.cUsuario.setValue(this.bdTUsuario.getCoUsuario());
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

    public CampoWebCombo getcUnidad() {
        return cUnidad;
    }

    public void setcUnidad(CampoWebCombo cUnidad) {
        this.cUnidad = cUnidad;
    }
    
    public void cargarComboUnidades() {
        if (this.listaBdTUnidad != null) {
            for (BdTUnidad itemBdTUnidad : this.listaBdTUnidad) {
                this.cUnidad.getOptions().put(itemBdTUnidad.getIdUnidad().toString(), itemBdTUnidad.getCoUnidad() + " - " + itemBdTUnidad.getDsUnidad());
            }
        }
    }
    
    public void selectOptionUnidad() {
        if (this.cUnidad.getValue() != null && this.listaBdTUnidad != null) {
            for (BdTUnidad itemBdTUnidad : this.listaBdTUnidad) {
                if (itemBdTUnidad.getIdUnidad().toString().equals((String)this.cUnidad.getValue())) {
                    this.setBdTUnidad(itemBdTUnidad);
                    refrescarMenu();
                }
            }
        }
    }
    
    public void refrescarMenu() {
        Session.limpiarOtrosBeans(this.getClass().getName(), false, true);
    }

    public CampoWebCodigo getcUsuario() {
        return cUsuario;
    }

    public void setcUsuario(CampoWebCodigo cUsuario) {
        this.cUsuario = cUsuario;
    }
    
    public String logout() {
        //Limpio todos los beans de sesion
        Session.limpiarOtrosBeans(null, true, true);
        
        //Retorno a la pagina de login
        return "/index";
    }
}
