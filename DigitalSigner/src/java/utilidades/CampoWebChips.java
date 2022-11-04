package utilidades;

/**
 *
 * @author ihuegal
 */
public class CampoWebChips extends CampoWeb {
    
    private boolean addOnPaste = true;
    private String separator = ",";
    private boolean unique = false;
    private Integer maxElements = null;
        
    public CampoWebChips() {
        super(Tipo.Descripcion);
        super.setMaxlength("255");
    }

    public void setValue(String value) {
        super.setValue(((value != null && !value.isBlank()) ? value : null));
    }

    @Override
    public String getValue() {
        return ((super.getValue() != null  && !((String)super.getValue()).isBlank()) ? (String)super.getValue() : null);
    }

    public boolean isAddOnPaste() {
        return addOnPaste;
    }

    public void setAddOnPaste(boolean addOnPaste) {
        this.addOnPaste = addOnPaste;
    }

    public String getSeparator() {
        return separator;
    }

    public void setSeparator(String separator) {
        this.separator = separator;
    }

    public boolean isUnique() {
        return unique;
    }

    public void setUnique(boolean unique) {
        this.unique = unique;
    }

    public Integer getMaxElements() {
        return maxElements;
    }

    public void setMaxElements(Integer maxElements) {
        this.maxElements = maxElements;
    }
}
