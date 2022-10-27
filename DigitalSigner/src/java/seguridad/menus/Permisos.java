package seguridad.menus;

/**
 *
 * @author ihuegal
 */
public class Permisos {
    private String idOpcionesMenu = null;
    private Boolean boConsulta = false;
    private Boolean boAlta = false;
    private Boolean boEdicion = false;
    private Boolean boBorrado = false;
    
    public Permisos() {
    }

    public Permisos(String idOpcionesMenu, Boolean boConsulta, Boolean boAlta, Boolean boEdicion, Boolean boBorrado) {
        this.idOpcionesMenu = idOpcionesMenu;
        this.boConsulta = boConsulta;
        this.boAlta = boAlta;
        this.boEdicion = boEdicion;
        this.boBorrado = boBorrado;
    }

    public String getIdOpcionesMenu() {
        return idOpcionesMenu;
    }

    public void setIdOpcionesMenu(String idOpcionesMenu) {
        this.idOpcionesMenu = idOpcionesMenu;
    }

    public Boolean getBoConsulta() {
        return boConsulta;
    }

    public void setBoConsulta(Boolean boConsulta) {
        this.boConsulta = boConsulta;
    }

    public Boolean getBoAlta() {
        return boAlta;
    }

    public void setBoAlta(Boolean boAlta) {
        this.boAlta = boAlta;
    }

    public Boolean getBoEdicion() {
        return boEdicion;
    }

    public void setBoEdicion(Boolean boEdicion) {
        this.boEdicion = boEdicion;
    }

    public Boolean getBoBorrado() {
        return boBorrado;
    }

    public void setBoBorrado(Boolean boBorrado) {
        this.boBorrado = boBorrado;
    }

    public String getLeyenda() {
        return "(" 
                + (this.boConsulta ? "C" : "")
                + (this.boAlta ? "A" : "")
                + (this.boEdicion ? "E" : "")
                + (this.boBorrado ? "B" : "")
                + ")";
    }
}
