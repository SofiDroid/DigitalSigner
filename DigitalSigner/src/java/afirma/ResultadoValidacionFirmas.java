package afirma;

import java.util.ArrayList;

/**
 *
 * @author ihuegal
 */
public class ResultadoValidacionFirmas {
    private boolean validarFirmas = true;
    private boolean checkCertificates = true;
    private boolean checkCertReferences = true;
    private boolean obtenerFirmantes = true;
    private boolean boValid = false;
    private String dsValidacion = null;
    private ArrayList<Firmante> listaFirmantes = new ArrayList<>();

    public boolean isCheckCertificates() {
        return checkCertificates;
    }

    public void setCheckCertificates(boolean checkCertificates) {
        this.checkCertificates = checkCertificates;
    }

    public boolean isCheckCertReferences() {
        return checkCertReferences;
    }

    public void setCheckCertReferences(boolean checkCertReferences) {
        this.checkCertReferences = checkCertReferences;
    }

    public boolean isBoValid() {
        return boValid;
    }

    public void setBoValid(boolean boValid) {
        this.boValid = boValid;
    }

    public String getDsValidacion() {
        return dsValidacion;
    }

    public void setDsValidacion(String dsValidacion) {
        this.dsValidacion = dsValidacion;
    }

    public ArrayList<Firmante> getListaFirmantes() {
        return listaFirmantes;
    }

    public void setListaFirmantes(ArrayList<Firmante> listaFirmantes) {
        this.listaFirmantes = listaFirmantes;
    }

    public boolean isObtenerFirmantes() {
        return obtenerFirmantes;
    }

    public void setObtenerFirmantes(boolean obtenerFirmantes) {
        this.obtenerFirmantes = obtenerFirmantes;
    }

    public boolean isValidarFirmas() {
        return validarFirmas;
    }

    public void setValidarFirmas(boolean validarFirmas) {
        this.validarFirmas = validarFirmas;
    }
}
