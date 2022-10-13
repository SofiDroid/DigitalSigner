package utilidades;

import java.lang.reflect.Method;
import java.util.HashMap;

/**
 *
 * @author ihuegal
 */
public class CampoWebCombo extends CampoWeb {
    
    private HashMap<String,String> options = new HashMap<>();
    private String update;
    private Method selectMethod = null;
    private Object selectClass = null;
    
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

    public Method getSelectMethod() {
        return selectMethod;
    }

    public void setSelectMethod(Method selectMethod) {
        this.selectMethod = selectMethod;
    }

    public Object getSelectClass() {
        return selectClass;
    }

    public void setSelectClass(Object selectClass) {
        this.selectClass = selectClass;
    }
    
    public String onSelect() {
        try {
            if (this.selectMethod != null) {
                return (String)this.selectMethod.invoke(this.selectClass);
            }
        } catch (Exception ex) {
            Mensajes.showException(CampoWebCombo.class, ex);
        }
        return null;
    }
}
