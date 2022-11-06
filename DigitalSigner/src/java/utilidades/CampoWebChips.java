package utilidades;

import java.util.List;

/**
 *
 * @author ihuegal
 */
public class CampoWebChips extends CampoWeb {
    
    private boolean addOnPaste = true;
    private String separator = ",";
    private boolean unique = false;
    private Integer maxElements = 10;
    private List<String> values = null;

    public CampoWebChips() {
        super(Tipo.Chips);
        super.setMaxlength("255");
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

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }
}
