package configuracion.seguridad.perfiles;

import basedatos.DataSet;
import basedatos.Row;
import basedatos.servicios.StTTipousuario;
import basedatos.tablas.BdTOpcionmenu;
import basedatos.tablas.BdTTipousuario;
import excepciones.FormModeException;
import excepciones.RegistryNotFoundException;
import excepciones.RequiredFieldException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.el.ELContext;
import javax.faces.context.FacesContext;
import javax.swing.text.Document;
import org.primefaces.model.CheckboxTreeNode;
import org.primefaces.model.TreeNode;
import utilidades.CampoWebCodigo;
import utilidades.CampoWebDescripcion;
import utilidades.CampoWebFecha;
import utilidades.CampoWebLupa;
import utilidades.Mensajes;
import utilidades.ModoFormulario;
import utilidades.Msg;
import utilidades.Validation;

/**
 *
 * @author ihuegal
 */
public class EdicionPerfiles implements Serializable {
    private Object parent = null;
    private String paginaRetorno = null;
    
    private CampoWebCodigo cCoTipousuario = null;
    private CampoWebDescripcion cDsTipousuario= null;
    private CampoWebFecha cFeAlta = null;
    private CampoWebFecha cFeDesactivo = null;
    private CampoWebLupa cUnidad = null;
    
    private TreeNode<BdTOpcionmenu> treeOpcionesMenu = null;
    private BdTOpcionmenu selectedOpcionMenu = null;
    
    BdTTipousuario bdTTipousuario = null;

    private ModoFormulario modoFormulario = ModoFormulario.CONSULTA;
    
    public EdicionPerfiles(Object parent) {
        init(parent, null);
    }
    
    public EdicionPerfiles(Object parent, Integer idTipousuario) {
        init(parent, idTipousuario);
    }
    
    private void init(Object parent, Integer idTipousuario) {
        try {
            this.parent = parent;
    
            this.cCoTipousuario = new CampoWebCodigo();
            this.cCoTipousuario.setLabel(Msg.getString("lbl_EdicionPerfiles_CoTipousuario"));
            this.cCoTipousuario.setWidthLabel("100px");
            this.cCoTipousuario.setRequired(true);
            
            this.cDsTipousuario = new CampoWebDescripcion();
            this.cDsTipousuario.setLabel(Msg.getString("lbl_EdicionPerfiles_DsTipousuario"));
            this.cDsTipousuario.setWidthLabel("100px");
            this.cDsTipousuario.setRequired(true);
            
            this.cFeAlta = new CampoWebFecha();
            this.cFeAlta.setLabel(Msg.getString("lbl_EdicionPerfiles_FeAlta"));
            this.cFeAlta.setWidthLabel("100px");
            this.cFeAlta.setRequired(true);
            
            this.cFeDesactivo = new CampoWebFecha();
            this.cFeDesactivo.setLabel(Msg.getString("lbl_EdicionPerfiles_FeDesactivo"));
            this.cFeDesactivo.setWidthLabel("100px");
            
            this.cUnidad = new CampoWebLupa();
            this.cUnidad.setLabel(Msg.getString("lbl_EdicionPerfiles_Unidad"));
            this.cUnidad.setWidthLabel("100px");
            String sql = "SELECT ID_UNIDAD, CO_UNIDAD + ' - ' + DS_UNIDAD as Unidad FROM BD_T_UNIDAD";
            this.cUnidad.setConsulta(sql);
            this.cUnidad.setColumnaID("ID_UNIDAD");
            this.cUnidad.setColumnaLabel("Unidad");
            this.cUnidad.setRequired(true);
            
            this.treeOpcionesMenu = createCheckboxDocuments();
            
            this.setModoFormulario(ModoFormulario.CONSULTA);

            if (idTipousuario != null) {
                recuperarRegistro(idTipousuario);
            }
            else {
                this.setModoFormulario(ModoFormulario.ALTA);
            }
        }
        catch (Exception ex) {
            try { 
                this.setModoFormulario(ModoFormulario.CONSULTA);
            }
            catch(Exception na) {
                //NADA 
            }
            Mensajes.showException(this.getClass(), ex);
        }
    }

    public void guardar() {
        try {
            validarCampos();
            
            //ALTA
            if (this.modoFormulario == ModoFormulario.ALTA) {
                this.bdTTipousuario = new BdTTipousuario();
                this.bdTTipousuario.setCoTipousuario(this.cCoTipousuario.getValue());
                this.bdTTipousuario.setDsTipousuario(this.cDsTipousuario.getValue());
                this.bdTTipousuario.setFeAlta(this.cFeAlta.getValue());
                this.bdTTipousuario.setFeDesactivo(this.cFeDesactivo.getValue());
                this.bdTTipousuario.setIdUnidad(this.cUnidad.getId());
                
                StTTipousuario stTTipousuario = new StTTipousuario();
                stTTipousuario.alta(this.bdTTipousuario, null);
                
                if (this.parent instanceof FiltroPerfiles filtroPerfiles) {
                    filtroPerfiles.getDsResultado().refrescarDatos();
                }
                
                Mensajes.showInfo("Información", "Alta realizada correctamente!");
            }
            
            //ACTUALIZACION
            if (this.modoFormulario == ModoFormulario.EDICION) {
                this.bdTTipousuario.setCoTipousuario(this.cCoTipousuario.getValue());
                this.bdTTipousuario.setDsTipousuario(this.cDsTipousuario.getValue());
                this.bdTTipousuario.setFeAlta(this.cFeAlta.getValue());
                this.bdTTipousuario.setFeDesactivo(this.cFeDesactivo.getValue());
                this.bdTTipousuario.setIdUnidad(this.cUnidad.getId());
                
                StTTipousuario stTTipousuario = new StTTipousuario();
                stTTipousuario.actualiza(this.bdTTipousuario, null);
                
                if (this.parent instanceof FiltroPerfiles filtroPerfiles) {
                    ELContext elContext = FacesContext.getCurrentInstance().getELContext();
                    filtroPerfiles = (FiltroPerfiles)elContext.getELResolver().getValue(elContext, null, "filtroPerfiles");
                    filtroPerfiles.getDsResultado().actualizarFilaSeleccionada();
                }
                
                Mensajes.showInfo("Información", "Actualización realizada correctamente!");
            }
            
            this.setModoFormulario(ModoFormulario.CONSULTA);
        }
        catch (Exception ex) {
            Mensajes.showException(this.getClass(), ex);
        }
    }
    
    public void modificar() {
        try {
            this.setModoFormulario(ModoFormulario.EDICION);
        }
        catch (Exception ex) {
            Mensajes.showException(this.getClass(), ex);
        }
    }
    
    public void eliminar() {
        try {
            StTTipousuario stTTipousuario = new StTTipousuario();
            stTTipousuario.baja(this.bdTTipousuario, null);
            
            if (this.parent instanceof FiltroPerfiles filtroPerfiles) {
                filtroPerfiles.getDsResultado().eliminarFilaSeleccionada();
            }

            Mensajes.showInfo("Información", "Registro eliminado correctamente!");
            
            this.setModoFormulario(ModoFormulario.ELIMINADO);
        }
        catch (Exception ex) {
            Mensajes.showException(this.getClass(), ex);
        }
    }
    
    public void limpiar() {
        this.cCoTipousuario.setValue(null);
        this.cDsTipousuario.setValue(null);
        this.cFeAlta.setValue(null);
        this.cFeDesactivo.setValue(null);
        this.cUnidad.setId(null);
    }
    
    public String volver() {
        try {
            if (this.modoFormulario == ModoFormulario.EDICION) {
                recuperarRegistro(this.bdTTipousuario.getIdTipousuario());
                this.setModoFormulario(ModoFormulario.CONSULTA);
            }
            else {
                return this.paginaRetorno;
            }
        }
        catch (Exception ex) {
            Mensajes.showException(this.getClass(), ex);
        }
        return null;
    }

    private void validarCampos() throws RequiredFieldException {
        if (Validation.isNullOrEmpty(this.cCoTipousuario.getValue())) {
            throw new RequiredFieldException(this.cCoTipousuario.getLabel());
        }
        if (Validation.isNullOrEmpty(this.cDsTipousuario.getValue())) {
            throw new RequiredFieldException(this.cDsTipousuario.getLabel());
        }
        if (Validation.isNullOrEmpty(this.cFeAlta.getValue())) {
            throw new RequiredFieldException(this.cFeAlta.getLabel());
        }
        if (Validation.isNullOrEmpty(this.cUnidad.getId())) {
            throw new RequiredFieldException(this.cUnidad.getLabel());
        }
    }
    
    private void protegerCampos() throws Exception {
        switch (this.modoFormulario) {
            case CONSULTA, ELIMINADO -> {
                this.cCoTipousuario.setProtegido(true);
                this.cDsTipousuario.setProtegido(true);
                this.cFeAlta.setProtegido(true);
                this.cFeDesactivo.setProtegido(true);
                this.cUnidad.setProtegido(true);
            }
            case EDICION -> {
                this.cCoTipousuario.setProtegido(false);
                this.cDsTipousuario.setProtegido(false);
                this.cFeAlta.setProtegido(false);
                this.cFeDesactivo.setProtegido(false);
                this.cUnidad.setProtegido(false);
            }
            case ALTA -> {
                limpiar();
                this.cCoTipousuario.setProtegido(false);
                this.cDsTipousuario.setProtegido(false);
                this.cFeAlta.setProtegido(false);
                this.cFeDesactivo.setProtegido(false);
                this.cUnidad.setProtegido(false);
            }
            default -> throw new FormModeException();
        }
    }
    
    private void recuperarRegistro(Integer idTipousuario) throws Exception {
        StTTipousuario stTTipousuario = new StTTipousuario();
        this.bdTTipousuario = stTTipousuario.item(idTipousuario, null);
        if (this.bdTTipousuario == null) {
            throw new RegistryNotFoundException();
        }
        
        cCoTipousuario.setValue(this.bdTTipousuario.getCoTipousuario());
        cDsTipousuario.setValue(this.bdTTipousuario.getDsTipousuario());
        cFeAlta.setValue(this.bdTTipousuario.getFeAlta());
        cFeDesactivo.setValue(this.bdTTipousuario.getFeDesactivo());        
        cUnidad.setId(this.bdTTipousuario.getIdUnidad());
    }

    public CampoWebCodigo getcCoTipousuario() {
        return cCoTipousuario;
    }

    public void setcCoTipousuario(CampoWebCodigo cCoTipousuario) {
        this.cCoTipousuario = cCoTipousuario;
    }

    public CampoWebDescripcion getcDsTipousuario() {
        return cDsTipousuario;
    }

    public void setcDsTipousuario(CampoWebDescripcion cDsTipousuario) {
        this.cDsTipousuario = cDsTipousuario;
    }

    public CampoWebFecha getcFeAlta() {
        return cFeAlta;
    }

    public void setcFeAlta(CampoWebFecha cFeAlta) {
        this.cFeAlta = cFeAlta;
    }

    public CampoWebFecha getcFeDesactivo() {
        return cFeDesactivo;
    }

    public void setcFeDesactivo(CampoWebFecha cFeDesactivo) {
        this.cFeDesactivo = cFeDesactivo;
    }

    public CampoWebLupa getcUnidad() {
        return cUnidad;
    }

    public void setcUnidad(CampoWebLupa cUnidad) {
        this.cUnidad = cUnidad;
    }

    public BdTTipousuario getBdTTipousuario() {
        return bdTTipousuario;
    }

    public void setBdTTipousuario(BdTTipousuario bdTTipousuario) {
        this.bdTTipousuario = bdTTipousuario;
    }

    public String getPaginaRetorno() {
        return paginaRetorno;
    }

    public void setPaginaRetorno(String paginaRetorno) {
        this.paginaRetorno = paginaRetorno;
    }

    public ModoFormulario getModoFormulario() {
        return modoFormulario;
    }

    public void setModoFormulario(ModoFormulario modoFormulario) throws Exception {
        this.modoFormulario = modoFormulario;
        protegerCampos();
    }

    public Object getParent() {
        return parent;
    }

    public void setParent(Object parent) {
        this.parent = parent;
    }

    public TreeNode<BdTOpcionmenu> getTreeOpcionesMenu() {
        return treeOpcionesMenu;
    }

    public void setTreeOpcionesMenu(TreeNode<BdTOpcionmenu> treeOpcionesMenu) {
        this.treeOpcionesMenu = treeOpcionesMenu;
    }

    public BdTOpcionmenu getSelectedOpcionMenu() {
        return selectedOpcionMenu;
    }

    public void setSelectedOpcionMenu(BdTOpcionmenu selectedOpcionMenu) {
        this.selectedOpcionMenu = selectedOpcionMenu;
    }
    
    public TreeNode<BdTOpcionmenu> buscarNodoPadre(TreeNode<BdTOpcionmenu> root, Integer idOpcionmenu) {
        if (root.getChildCount() > 0) {
            for (TreeNode<BdTOpcionmenu> item : root.getChildren()) {
                TreeNode<BdTOpcionmenu> resul = buscarNodoPadre(item, idOpcionmenu);
                if (resul != null) {
                    return resul;
                }
            }
        }
        if (root.getData().getIdOpcionmenu().compareTo(idOpcionmenu) == 0) {
            return root;
        }
        return null;
    }
    
    public TreeNode<BdTOpcionmenu> createCheckboxDocuments() {
        try {
            TreeNode<BdTOpcionmenu> root = new CheckboxTreeNode<>(new BdTOpcionmenu(), null);
            
            DataSet dsOpcionesMenu = new DataSet("SELECT ID_OPCIONMENU, DS_OPCIONMENU, DS_TITULO, ID_OPCIONMENUPADRE FROM BD_T_OPCIONMENU ORDER BY ID_OPCIONMENUPADRE ASC", "ID_OPCIONMENU");
            for (Row itemRow : dsOpcionesMenu.getRows()) {
                BdTOpcionmenu item = new BdTOpcionmenu();
                item.setIdOpcionmenu(itemRow.getColumnName("ID_OPCIONMENU").getValueInteger());
                item.setDsTitulo(itemRow.getColumnName("DS_TITULO").getValueString());
                item.setDsOpcionmenu(itemRow.getColumnName("DS_OPCIONMENU").getValueString());
                
                TreeNode padre = root;
                if (itemRow.getColumnName("ID_OPCIONMENUPADRE").getValueInteger() != null) {
                    padre = buscarNodoPadre(root, itemRow.getColumnName("ID_OPCIONMENUPADRE").getValueInteger());
                }
                CheckboxTreeNode checkboxTreeNode = new CheckboxTreeNode(item, padre);
            }

            return root;
        } catch (SQLException ex) {
            Mensajes.showException(this.getClass(), ex);
        }
        return null;
    }
}
