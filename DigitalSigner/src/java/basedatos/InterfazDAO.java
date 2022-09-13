package basedatos;

import java.util.HashMap;

/**
 *
 * @author ihuegal
 */
public interface InterfazDAO {
    public default Object getClaseMapeada(HashMap<String,Object> bld) throws Exception {
        throw new Exception("Metodo no implementado.");
    }
}
