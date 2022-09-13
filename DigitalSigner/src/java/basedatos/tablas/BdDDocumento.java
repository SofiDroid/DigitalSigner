package basedatos.tablas;

/**
 *
 * @author ihuegal
 */
public class BdDDocumento {
    private Integer idDocumento = null;
    private String coDocumento = null;
    private String dsDocumento = null;
    
    public BdDDocumento() {
        // NADA
    }
    
    public BdDDocumento(Integer idDocumento, String coDocumento, String dsDocumento) {
        this.idDocumento = idDocumento;
        this.coDocumento = coDocumento;
        this.dsDocumento = dsDocumento;
    }
    
    public Integer getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(Integer idDocumento) {
        this.idDocumento = idDocumento;
    }

    public String getCoDocumento() {
        return coDocumento;
    }

    public void setCoDocumento(String coDocumento) {
        this.coDocumento = coDocumento;
    }

    public String getDsDocumento() {
        return dsDocumento;
    }

    public void setDsDocumento(String dsDocumento) {
        this.dsDocumento = dsDocumento;
    }
}
