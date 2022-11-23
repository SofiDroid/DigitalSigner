package afirma;

import java.util.Date;

/**
 *
 * @author ihuegal
 */
public class Firmante {
    private String nombre = null;
    private String fecha = null;
    private boolean signValid = true;
    private boolean certValid = true;
    private boolean refValid = true;
    private boolean valid = true;
    private String dsSignValidacion = null;
    private String dsCertValidacion = null;
    private String dsRefValidacion = null;
    private String dsValidacion = null;
    private boolean isTimestamped = false;
    private String timestamp = null;
    private String timestampLimit = null;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public String getDsValidacion() {
        return dsValidacion;
    }

    public void setDsValidacion(String dsValidacion) {
        this.dsValidacion = dsValidacion;
    }

    public boolean isSignValid() {
        return signValid;
    }

    public void setSignValid(boolean signValid) {
        this.signValid = signValid;
    }

    public boolean isCertValid() {
        return certValid;
    }

    public void setCertValid(boolean certValid) {
        this.certValid = certValid;
    }

    public boolean isRefValid() {
        return refValid;
    }

    public void setRefValid(boolean refValid) {
        this.refValid = refValid;
    }

    public String getDsSignValidacion() {
        return dsSignValidacion;
    }

    public void setDsSignValidacion(String dsSignValidacion) {
        this.dsSignValidacion = dsSignValidacion;
    }

    public String getDsCertValidacion() {
        return dsCertValidacion;
    }

    public void setDsCertValidacion(String dsCertValidacion) {
        this.dsCertValidacion = dsCertValidacion;
    }

    public String getDsRefValidacion() {
        return dsRefValidacion;
    }

    public void setDsRefValidacion(String dsRefValidacion) {
        this.dsRefValidacion = dsRefValidacion;
    }

    public boolean isIsTimestamped() {
        return isTimestamped;
    }

    public void setIsTimestamped(boolean isTimestamped) {
        this.isTimestamped = isTimestamped;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getTimestampLimit() {
        return timestampLimit;
    }

    public void setTimestampLimit(String timestampLimit) {
        this.timestampLimit = timestampLimit;
    }
}
