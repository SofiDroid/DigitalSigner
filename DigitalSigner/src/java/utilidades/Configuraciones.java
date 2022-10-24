package utilidades;

import basedatos.servicios.StAConfvaluni;
import basedatos.servicios.StTConfiguracion;
import basedatos.servicios.StTConfvalor;
import basedatos.tablas.BdAConfvaluni;
import basedatos.tablas.BdTConfiguracion;
import basedatos.tablas.BdTConfvalor;
import java.util.ArrayList;
import java.util.Date;
import org.apache.log4j.Logger;
import seguridad.usuarios.DatosUsuario;

/**
 *
 * @author ihuegal
 */
public class Configuraciones {
    
    private final Logger LOG = Logger.getLogger(Configuraciones.class);

    private DatosUsuario datosUsuario = null;
    
    public Configuraciones(DatosUsuario datosUsuario) {
        this.datosUsuario = datosUsuario;
    }
    
    public String recuperaConfiguracion(String coConfiguracion) {
        try {
            Integer idConfiguracion = null;

            BdTConfiguracion filtroBdTConfiguracion = new BdTConfiguracion();
            filtroBdTConfiguracion.setCoConfiguracion(coConfiguracion);
            filtroBdTConfiguracion.setFeAlta(new Date());
            filtroBdTConfiguracion.setFeDesactivo(new Date());

            StTConfiguracion stTConfiguracion = new StTConfiguracion(datosUsuario);
            ArrayList<BdTConfiguracion> listaBdTConfiguracion = stTConfiguracion.filtro(filtroBdTConfiguracion, null);
            if (listaBdTConfiguracion != null && !listaBdTConfiguracion.isEmpty()) {
                idConfiguracion = listaBdTConfiguracion.get(0).getIdConfiguracion();
            }

            if (idConfiguracion != null) {
                BdAConfvaluni filtroBdAConfvaluni = new BdAConfvaluni();
                filtroBdAConfvaluni.setIdUnidad(datosUsuario.getBdTUnidad().getIdUnidad());
                filtroBdAConfvaluni.setIdConfiguracion(idConfiguracion);
                filtroBdTConfiguracion.setFeAlta(new Date());
                filtroBdTConfiguracion.setFeDesactivo(new Date());

                StAConfvaluni stAConfvaluni = new StAConfvaluni(datosUsuario);
                ArrayList<BdAConfvaluni> listaBdAConfvaluni = stAConfvaluni.filtro(filtroBdAConfvaluni, null);

                if (listaBdAConfvaluni != null && !listaBdAConfvaluni.isEmpty()) {

                    if (listaBdAConfvaluni.get(0).getIdConfvalor() != null) {
                        BdTConfvalor filtroBdTConfvalor = new BdTConfvalor();
                        filtroBdTConfvalor.setIdConfvalor(listaBdAConfvaluni.get(0).getIdConfvalor());
                        filtroBdTConfvalor.setFeAlta(new Date());
                        filtroBdTConfvalor.setFeDesactivo(new Date());

                        StTConfvalor stTConfvalor = new StTConfvalor(datosUsuario);
                        ArrayList<BdTConfvalor> listaBdTConfvalor = stTConfvalor.filtro(filtroBdTConfvalor, null);
                        if (listaBdTConfvalor != null && !listaBdTConfvalor.isEmpty()) {
                            return listaBdTConfvalor.get(0).getDsConfvalor();
                        }
                    }
                    else {
                        return listaBdAConfvaluni.get(0).getDsValorlibre();
                    }
                }
            }
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
            Mensajes.showError("Error al obtener configuración : " + coConfiguracion, ex.getMessage());
        }
        
        return null;
    }
}
