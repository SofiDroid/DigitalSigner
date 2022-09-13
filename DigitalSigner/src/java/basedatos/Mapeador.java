package basedatos;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 *
 * @author ihuegal
 */
public class Mapeador {

    public static ArrayList mapea(ArrayList<LinkedHashMap<String,Object>> datos, Class<?> instancia) throws Exception {
        ArrayList salida = new ArrayList();

        for (int g1 = 0; g1 < datos.size(); g1++) {
            salida.add(((Class<InterfazDAO>)instancia).getDeclaredConstructor().newInstance().getClaseMapeada(datos.get(g1)));
        }

        return salida;
    }
}
