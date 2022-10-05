package utilidades;

import java.util.HashMap;

/**
 *
 * @author ihuegal
 */
public class CampoWebCombo extends CampoWeb {
    
    private HashMap<String,String> options = new HashMap<>();
    private String update;
    
    public CampoWebCombo() {
        super(Tipo.Combo);
    }

    public HashMap<String, String> getOptions() {
        return options;
    }

    public void setOptions(HashMap<String, String> options) {
        this.options = options;
    }

    public String getUpdate() {
        return update;
    }

    public void setUpdate(String update) {
        this.update = update;
    }
}
