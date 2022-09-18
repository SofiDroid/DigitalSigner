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

/**
 *
 * @author ihuegal
 */
public class Configuraciones {
    
    Logger LOG = Logger.getLogger(Configuraciones.class);
    
    public String recuperaConfiguracion(String coConfiguracion) {
        try {
        Integer idConfiguracion = null;
        
        BdTConfiguracion filtroBdTConfiguracion = new BdTConfiguracion();
        filtroBdTConfiguracion.setCoConfiguracion(coConfiguracion);
        filtroBdTConfiguracion.setFeAlta(new Date());
        filtroBdTConfiguracion.setFeDesactivo(new Date());
        
        StTConfiguracion stTConfiguracion = new StTConfiguracion();
        ArrayList<BdTConfiguracion> listaBdTConfiguracion = stTConfiguracion.filtro(filtroBdTConfiguracion, null);
        if (listaBdTConfiguracion != null && !listaBdTConfiguracion.isEmpty()) {
            idConfiguracion = listaBdTConfiguracion.get(0).getIdConfiguracion();
        }
        
        if (idConfiguracion != null) {
            BdAConfvaluni filtroBdAConfvaluni = new BdAConfvaluni();
            filtroBdAConfvaluni.setIdUnidad(Session.getDatosUsuario().getBdTUnidad().getIdUnidad());
            filtroBdAConfvaluni.setIdConfiguracion(idConfiguracion);
            filtroBdTConfiguracion.setFeAlta(new Date());
            filtroBdTConfiguracion.setFeDesactivo(new Date());

            StAConfvaluni stAConfvaluni = new StAConfvaluni();
            ArrayList<BdAConfvaluni> listaBdAConfvaluni = stAConfvaluni.filtro(filtroBdAConfvaluni, null);
            
            if (listaBdAConfvaluni != null && !listaBdAConfvaluni.isEmpty()) {
                
                if (listaBdAConfvaluni.get(0).getIdConfvalor() != null) {
                    BdTConfvalor filtroBdTConfvalor = new BdTConfvalor();
                    filtroBdTConfvalor.setIdConfvalor(listaBdAConfvaluni.get(0).getIdConfvalor());
                    filtroBdTConfvalor.setFeAlta(new Date());
                    filtroBdTConfvalor.setFeDesactivo(new Date());
                    
                    StTConfvalor stTConfvalor = new StTConfvalor();
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
