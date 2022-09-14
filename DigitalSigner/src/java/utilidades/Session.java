/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilidades;

import basedatos.tablas.BdTUsuario;
import javax.el.ELContext;
import javax.faces.context.FacesContext;
import seguridad.usuarios.DatosUsuario;

/**
 *
 * @author ihuegal
 */
public class Session {
    public static DatosUsuario getDatosUsuario() {
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        return (DatosUsuario)elContext.getELResolver().getValue(elContext, null, "datosUsuario");
    }
    
    public static String getCoUsuario() {
        BdTUsuario bdTUsuario = getDatosUsuario().getBdTUsuario();
        return (bdTUsuario != null ? bdTUsuario.getCoUsuario() : null);
    }
}
