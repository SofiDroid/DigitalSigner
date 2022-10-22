package utilidades;

/**
 *
 * @author ihuegal
 */
public class CampoWeb {
    
    public static enum Tipo {
        Codigo,
        Descripcion,
        Fecha,
        FechaRango,
        Lupa,
        Check,
        Numero,
        Password,
        Combo
    }
    
    private String clientId = "";
    private String width = ""; 
    private Integer size = null;
    private String widthLabel = ""; 
    private String maxlength = "";
    private Tipo tipo = Tipo.Codigo;
    private Object value = null;
    private boolean protegido = false;
    private boolean required = false;
    private boolean autocomplete = true;
    private String label = "";
    private String lang = "es";

    public CampoWeb(Tipo tipo) {
        this.tipo = tipo;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }
    
    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public boolean isProtegido() {
        return protegido;
    }

    public void setProtegido(boolean protegido) {
        this.protegido = protegido;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getWidth() {
        return (width != null && !width.isBlank() ? "width: " + width + ";" : "");
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getWidthLabel() {
        return (widthLabel != null && !widthLabel.isBlank() ? "width: " + widthLabel + ";" : "");
    }

    public void setWidthLabel(String widthLabel) {
        this.widthLabel = widthLabel;
    }

    public String getMaxlength() {
        return maxlength;
    }

    public void setMaxlength(String maxlength) {
        this.maxlength = maxlength;
    }

    public String getRequiredChar() {
        return (required ? " <span style='color: red'>*</span>" : "");
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public boolean isAutocomplete() {
        return autocomplete;
    }

    public void setAutocomplete(boolean autocomplete) {
        this.autocomplete = autocomplete;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }
}
