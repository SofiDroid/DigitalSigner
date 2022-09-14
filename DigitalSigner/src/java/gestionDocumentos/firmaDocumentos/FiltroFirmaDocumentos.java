package gestionDocumentos.firmaDocumentos;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.apache.log4j.Logger;
import utilidades.Mensajes;

/**
 *
 * @author ihuegal
 */
@Named
@SessionScoped
public class FiltroFirmaDocumentos implements Serializable {
    private final Logger LOG = Logger.getLogger(FiltroFirmaDocumentos.class);
    
    @PostConstruct
    public void init() {
        
    }

    public void limpiar() {
        try {
            
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
            new Mensajes().showError("Error al buscar", ex.getMessage());
        }
    }
    
    public void buscar() {
        try {
            
            
            filtros(null);
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
            new Mensajes().showError("Error al buscar", ex.getMessage());
        }
    }
    
    private String filtros(String sql) {
        return sql;
    }
    
    public String verDetalle() {
        return null;
    }
}
