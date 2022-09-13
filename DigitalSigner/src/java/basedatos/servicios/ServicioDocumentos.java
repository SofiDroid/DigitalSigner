/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package basedatos.servicios;

import basedatos.tablas.BdDDocumento;
import java.io.Serializable;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

/**
 *
 * @author ihuegal
 */
@Named
@ApplicationScoped
public class ServicioDocumentos implements Serializable {
    
    @PostConstruct
    public void init() {
        
    }
    
    public List<BdDDocumento> recuperaDocumentos() {
        List<BdDDocumento> resultado = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            BdDDocumento doc = new BdDDocumento();
            doc.setIdDocumento(i);
            doc.setCoDocumento("DOC_" + i);
            doc.setDsDocumento("DOCUMENTO_" + i);
            resultado.add(doc);
        }
        return resultado;
    }
}
