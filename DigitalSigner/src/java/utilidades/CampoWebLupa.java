package utilidades;

import basedatos.DataSet;
import basedatos.Row;
import java.sql.SQLException;
import java.util.List;
import org.apache.log4j.Logger;
import org.primefaces.component.autocomplete.AutoComplete;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author ihuegal
 */
public class CampoWebLupa extends CampoWeb {
    private DataSet dataSet;
    private String columnaLabel;
    private Integer queryDelay;
    private String emptyMessage;
    private Integer maxResults;
    private String moreText;
    private String consulta;
    private String columnaID;
    private String update;
    
    public CampoWebLupa() {
        super(Tipo.Lupa);
        super.setMaxlength("255");
        this.queryDelay = 1000;
        this.emptyMessage = "No se encontraron resultados";
        this.maxResults = 5;
        this.moreText = "mas resultados disponibles";
        this.dataSet = new DataSet();
    }
    
    public DataSet getDataSet() {
        return dataSet;
    }

    public void setDataSet(DataSet dataSet) {
        this.dataSet = dataSet;
    }

    public String getColumnaLabel() {
        return columnaLabel;
    }

    public void setColumnaLabel(String columnaLabel) {
        this.columnaLabel = columnaLabel;
    }
    
    public List<Row> completeText(String filterText) {
        try {
            String sql = "SELECT * FROM (" + this.consulta + ") TDS WHERE UPPER(TDS." + this.columnaLabel + ") LIKE UPPER('%" + filterText + "%')";
            this.dataSet = new DataSet(sql, columnaID);
            return this.dataSet.getRows();
        } catch (SQLException ex) {
            Logger.getLogger(CampoWebLupa.class).error(ex.getMessage(), ex);
            Mensajes.showError("Error al recuperar lupa " + this.getLabel(), ex.getMessage());
        }
        return null;
    }
    
    public void buscar() {
        try {
            this.dataSet = new DataSet(consulta, columnaID);
            this.getDataSet().getCabecera().getColumnName(columnaID).setVisible(false);
        } catch (SQLException ex) {
            Logger.getLogger(CampoWebLupa.class).error(ex.getMessage(), ex);
            Mensajes.showError("Error al recuperar lupa " + this.getLabel(), ex.getMessage());
        }
    }
    
    public void onRowSelect(SelectEvent<Row> event) {
        if (event.getObject().getParent() == null) {
            this.dataSet.setSelectedRow(this.dataSet.getRows().get(event.getObject().getIndex()));
        }
        this.setValue(this.dataSet.getSelectedRow().getColumnaID().getValueString());
    }

    public void setValueId(Integer idValue) {
        Row itemRow = recuperaPorValue(idValue);
        this.dataSet.setSelectedRow(itemRow);
        this.setValue(this.dataSet.getSelectedRow().getColumnaID().getValueString());
    }
    
    public Row recuperaPorValue(Integer idValue) {
        try {
            String sql = "SELECT * FROM (" + this.consulta + ") TDS WHERE UPPER(TDS." + this.columnaID + ") = UPPER('" + idValue + "')";
            this.dataSet = new DataSet(sql, columnaID);
            if (this.dataSet.getRowsCount() > 0) {
                return this.dataSet.getRows().get(0);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CampoWebLupa.class).error(ex.getMessage(), ex);
            Mensajes.showError("Error al recuperar lupa " + this.getLabel(), ex.getMessage());
        }
        return null;
    }
    
    public void onClear() {
            this.setValue(null);
    }

    public Integer getQueryDelay() {
        return queryDelay;
    }

    public void setQueryDelay(Integer queryDelay) {
        this.queryDelay = queryDelay;
    }

    public String getEmptyMessage() {
        return emptyMessage;
    }

    public void setEmptyMessage(String emptyMessage) {
        this.emptyMessage = emptyMessage;
    }

    public Integer getMaxResults() {
        return maxResults;
    }

    public void setMaxResults(Integer maxResults) {
        this.maxResults = maxResults;
    }

    public String getMoreText() {
        return moreText;
    }

    public void setMoreText(String moreText) {
        this.moreText = moreText;
    }

    public String getConsulta() {
        return consulta;
    }

    public void setConsulta(String consulta) {
        this.consulta = consulta;
    }

    public String getColumnaID() {
        return columnaID;
    }

    public void setColumnaID(String columnaID) {
        this.columnaID = columnaID;
        if (this.dataSet != null) {
            this.dataSet.setRowSelectColumnaID(columnaID);
        }
    }

    public String getUpdate() {
        return update;
    }

    public void setUpdate(String update) {
        this.update = update;
    }
}
