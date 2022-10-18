package utilidades;

import basedatos.AutocompleteItem;
import basedatos.DataSet;
import basedatos.Row;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
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
    private AutocompleteItem selectedItem;
    private final ArrayList<String> listaNotIN;
    
    public CampoWebLupa() {
        super(Tipo.Lupa);
        super.setMaxlength("255");
        this.queryDelay = 1000;
        this.emptyMessage = "No se encontraron resultados";
        this.maxResults = 5;
        this.moreText = "mas resultados disponibles";
        this.selectedItem = null;
        this.listaNotIN = new ArrayList<>();
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
    
    public List<AutocompleteItem> completeText(String filterText) {
        try {
            String sql = "SELECT * FROM (" + this.consulta + ") TDS WHERE UPPER(TDS." + this.columnaLabel + ") LIKE UPPER('%" + filterText + "%')";
            if (!this.listaNotIN.isEmpty()) {
                sql = sql + " AND TDS." + this.columnaID + " NOT IN (" + getListaNotIN() + ") ";
            }
            this.dataSet = new DataSet(sql, columnaID);
            return getListaAutocomplete();
        } catch (SQLException ex) {
            Logger.getLogger(CampoWebLupa.class).error(ex.getMessage(), ex);
            Mensajes.showError("Error al recuperar lupa " + this.getLabel(), ex.getMessage());
        }
        return null;
    }
    
    public void buscar() {
        try {
            String sql = "SELECT * FROM (" + this.consulta + ") TDS " + (!this.listaNotIN.isEmpty() ? " WHERE TDS." + this.columnaID + " NOT IN (" + getListaNotIN() + ") " : "");
            this.dataSet = new DataSet(sql, columnaID);
            this.getDataSet().getCabecera().getColumnName(columnaID).setVisible(false);
        } catch (SQLException ex) {
            Logger.getLogger(CampoWebLupa.class).error(ex.getMessage(), ex);
            Mensajes.showError("Error al recuperar lupa " + this.getLabel(), ex.getMessage());
        }
    }
    
    public void onRowSelect(SelectEvent<Row> event) {
        if (event.getObject() != null) {
            Row itemRow = event.getObject();
            this.setValue(new AutocompleteItem(itemRow.getColumnName(this.columnaID).getValueInteger(), itemRow.getColumnName(this.columnaLabel).getValueString()));
        }
        else {
            this.setValue(null);
        }
    }

    public void onItemSelect(SelectEvent<AutocompleteItem> event) {
        this.setValue(event.getObject());
    }

    public void setId(Integer idValue) {
        if (idValue != null) {
            Row itemRow = recuperaPorValue(idValue);
            this.selectedItem = new AutocompleteItem(itemRow.getColumnName(this.columnaID).getValueInteger(), itemRow.getColumnName(this.columnaLabel).getValueString());
        }
        else {
            this.selectedItem = null;
        }
    }
    
    public Integer getId() {
        if (this.selectedItem != null) {
            return this.selectedItem.getId();
        }
        return null;
    }
    
    @Override
    public AutocompleteItem getValue() {
        return this.selectedItem;
    }
    
    @Override
    @Deprecated(forRemoval = true)
    public void setValue(Object item) {
        if (item instanceof AutocompleteItem autocompleteItem) {
            this.selectedItem = autocompleteItem;
        }
        else {
            this.selectedItem = null;
        }
    }
    
    public void setValue(AutocompleteItem item) {
        this.selectedItem = item;
    }
    
    public List<AutocompleteItem> getListaAutocomplete() {
        ArrayList<AutocompleteItem> resultado = new ArrayList<>();
        if (this.dataSet.getRows() != null) {
            for(Row itemRow : this.dataSet.getRows()) {
                resultado.add(new AutocompleteItem(itemRow.getColumnName(this.columnaID).getValueInteger(), itemRow.getColumnName(this.columnaLabel).getValueString()));
            }
        }
        return resultado;
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

    public AutocompleteItem getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(AutocompleteItem selectedItem) {
        this.selectedItem = selectedItem;
    }

    public String getListaNotIN() {
        String resul = "";
        for (String item : listaNotIN) {
            if (!resul.isBlank()) {
                resul += ",";
            }
            resul += "'" + item + "'";
        }
        return resul;
    }
    public void setListaNotIN_Clear() {
        this.listaNotIN.clear();
    }
    
    public void setListaNotIN_Add(String value) {
        if (value != null && !this.listaNotIN.contains(value)) {
            this.listaNotIN.add(value);
        }
    }
    
    public void setListaNotIN_Remove(String value) {
        if (value != null && this.listaNotIN.contains(value)) {
            this.listaNotIN.remove(value);
        }
    }
}
