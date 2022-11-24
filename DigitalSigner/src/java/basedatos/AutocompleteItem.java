package basedatos;

/**
 *
 * @author ihuegal
 */
public class AutocompleteItem {
    protected Integer id;
    protected String label;

    public AutocompleteItem() {
    }

    public AutocompleteItem(Integer id, String label) {
        this.id = id;
        this.label = label;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "AutocompleteItem{" + "id=" + id + ", label=" + label + '}';
    }
}
