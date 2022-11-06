package basedatos.tablas;

import basedatos.InterfazDAO;
import basedatos.OperacionSQL;
import java.util.HashMap;

/**
 *
 * @author ihuegal
 */
public class BdAFirmante extends OperacionSQL implements InterfazDAO {

    protected String coNif;
    protected Integer idDocfirma;

    public BdAFirmante() {
        // NADA
    }

    @Override
    public Object getClaseMapeada(HashMap<String,Object> bld) throws Exception {
        recuperaValorCampo(this, "coNif", "CO_NIF", bld);
        recuperaValorCampo(this, "idDocfirma", "ID_DOCFIRMA", bld);
        
        return this; 
    }
    
    public String getSelectFiltro() {
        StringBuilder sb_sql = new StringBuilder("SELECT ");
        sb_sql.append("CO_NIF");
        sb_sql.append(",ID_DOCFIRMA");
        
        sb_sql.append(" FROM BD_A_FIRMANTE WHERE 1=1 ");
        if (coNif != null) {
            sb_sql.append(" AND CO_NIF = :CO_NIF");
        }
        if (idDocfirma != null) {
            sb_sql.append(" AND ID_DOCFIRMA = :ID_DOCFIRMA");
        }

        return sb_sql.toString();
    }

    public String getInsert()
    {
        StringBuilder sb_sql = new StringBuilder("INSERT INTO BD_A_FIRMANTE (");
        sb_sql.append("CO_NIF");
        sb_sql.append(",ID_DOCFIRMA");
        
        sb_sql.append(") VALUES (");
        sb_sql.append(":CO_NIF");
        sb_sql.append(",").append(":ID_DOCFIRMA");
        sb_sql.append(")");
        return sb_sql.toString();
    }

    public String getDelete()
    {
        StringBuilder sb_sql = new StringBuilder("DELETE BD_A_FIRMANTE ");
        sb_sql.append(" WHERE ");
        sb_sql.append(" CO_NIF = ").append(":CO_NIF");
        sb_sql.append(" AND ID_DOCFIRMA = ").append(":ID_DOCFIRMA");
        
        return sb_sql.toString();
    }

    public String getCoNif() {
        return coNif;
    }

    public void setCoNif(String coNif) {
        this.coNif = coNif;
    }

    public Integer getIdDocfirma() {
        return idDocfirma;
    }

    public void setIdDocfirma(Integer idDocfirma) {
        this.idDocfirma = idDocfirma;
    }
}
