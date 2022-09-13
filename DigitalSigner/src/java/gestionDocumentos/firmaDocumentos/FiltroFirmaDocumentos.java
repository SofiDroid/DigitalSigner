package gestionDocumentos.firmaDocumentos;

import basedatos.tablas.BdDDocumento;
import basedatos.servicios.ServicioDocumentos;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author ihuegal
 */
@Named
@SessionScoped
public class FiltroFirmaDocumentos implements Serializable {
    private List<BdDDocumento> lstDocumentos;

    @Inject
    private ServicioDocumentos servicioDocumentos;

    @PostConstruct
    public void init() {
        lstDocumentos = servicioDocumentos.recuperaDocumentos();
    }

    public List<BdDDocumento> getLstDocumentos() {
        return lstDocumentos;
    }
}
