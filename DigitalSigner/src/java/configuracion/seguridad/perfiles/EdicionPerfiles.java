package configuracion.seguridad.perfiles;

import basedatos.DataSet;
import basedatos.Row;
import basedatos.servicios.StATipousuopcper;
import basedatos.servicios.StTTipousuario;
import basedatos.tablas.BdATipousuopcper;
import basedatos.tablas.BdTTipousuario;
import excepciones.FormModeException;
import excepciones.RegistryNotFoundException;
import excepciones.RequiredFieldException;
import init.AppInit;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.el.ELContext;
import javax.faces.context.FacesContext;
import org.primefaces.model.CheckboxTreeNode;
import org.primefaces.model.TreeNode;
import tomcat.persistence.EntityManager;
import utilidades.CampoWebCodigo;
import utilidades.CampoWebDescripcion;
import utilidades.CampoWebFecha;
import utilidades.CampoWebLupa;
import utilidades.Mensajes;
import utilidades.ModoFormulario;
import utilidades.Msg;
import utilidades.Session;
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

    public class OpcionMenuPermisos {
        private Integer idOpcionMenu = null;
        private String dsTitulo = null;
        private String dsOpcionMenu = null;
        private Integer idOpcionMenuPadre = null;
        private boolean chkConsulta = false;
        private boolean chkAlta = false;
        private boolean chkEdicion = false;
        private boolean chkBorrado = false;
        
        public OpcionMenuPermisos() {
        }
        
        public Integer getIdOpcionMenu() {
            return idOpcionMenu;
        }

        public void setIdOpcionMenu(Integer idOpcionMenu) {
            this.idOpcionMenu = idOpcionMenu;
        }

        public String getDsTitulo() {
            return dsTitulo;
        }

        public void setDsTitulo(String dsTitulo) {
            this.dsTitulo = dsTitulo;
        }

        public String getDsOpcionMenu() {
            return dsOpcionMenu;
        }

        public void setDsOpcionMenu(String dsOpcionMenu) {
            this.dsOpcionMenu = dsOpcionMenu;
        }

        public Integer getIdOpcionMenuPadre() {
            return idOpcionMenuPadre;
        }

        public void setIdOpcionMenuPadre(Integer idOpcionMenuPadre) {
            this.idOpcionMenuPadre = idOpcionMenuPadre;
        }
        
        public boolean isChkConsulta() {
            return chkConsulta;
        }

        public void setChkConsulta(boolean chkConsulta) {
            this.chkConsulta = chkConsulta;
        }

        public boolean isChkAlta() {
            return chkAlta;
        }

        public void setChkAlta(boolean chkAlta) {
            this.chkAlta = chkAlta;
        }

        public boolean isChkEdicion() {
            return chkEdicion;
        }

        public void setChkEdicion(boolean chkEdicion) {
            this.chkEdicion = chkEdicion;
        }

        public boolean isChkBorrado() {
            return chkBorrado;
        }

        public void setChkBorrado(boolean chkBorrado) {
            this.chkBorrado = chkBorrado;
        }
    }
    private TreeNode<OpcionMenuPermisos> treeOpcionesMenu = null;
    
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
            
            this.setModoFormulario(ModoFormulario.CONSULTA);

            if (idTipousuario != null) {
                recuperarRegistro(idTipousuario);
            }
            else {
                this.setModoFormulario(ModoFormulario.ALTA);
                this.treeOpcionesMenu = createCheckboxDocuments(0);
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
            // INICIO TRANSACCION
            try (EntityManager entityManager = AppInit.getEntityManager())
            {
                entityManager.getTransaction().begin();
                try {
                    //ALTA
                    if (this.modoFormulario == ModoFormulario.ALTA) {
                        this.bdTTipousuario = new BdTTipousuario();
                        this.bdTTipousuario.setCoTipousuario(this.cCoTipousuario.getValue());
                        this.bdTTipousuario.setDsTipousuario(this.cDsTipousuario.getValue());
                        this.bdTTipousuario.setFeAlta(this.cFeAlta.getValue());
                        this.bdTTipousuario.setFeDesactivo(this.cFeDesactivo.getValue());
                        this.bdTTipousuario.setIdUnidad(this.cUnidad.getId());

                        StTTipousuario stTTipousuario = new StTTipousuario(Session.getDatosUsuario());
                        stTTipousuario.alta(this.bdTTipousuario, entityManager);

                        grabarCambiosOpcionesMenu(this.treeOpcionesMenu, this.bdTTipousuario, entityManager);
                        
                        entityManager.getTransaction().commit();

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

                        StTTipousuario stTTipousuario = new StTTipousuario(Session.getDatosUsuario());
                        stTTipousuario.actualiza(this.bdTTipousuario, entityManager);

                        grabarCambiosOpcionesMenu(this.treeOpcionesMenu, this.bdTTipousuario, entityManager);

                        entityManager.getTransaction().commit();

                        if (this.parent instanceof FiltroPerfiles filtroPerfiles) {
                            ELContext elContext = FacesContext.getCurrentInstance().getELContext();
                            filtroPerfiles = (FiltroPerfiles)elContext.getELResolver().getValue(elContext, null, "filtroPerfiles");
                            filtroPerfiles.getDsResultado().actualizarFilaSeleccionada();
                        }

                        Mensajes.showInfo("Información", "Actualización realizada correctamente!");
                    }
                }
                catch (Exception ex) {
                    entityManager.getTransaction().rollback();
                    throw ex;
                }
            }
            // FIN TRANSACCION
            this.setModoFormulario(ModoFormulario.CONSULTA);
        }
        catch (Exception ex) {
            Mensajes.showException(this.getClass(), ex);
        }
    }
    
    private void grabarCambiosOpcionesMenu(TreeNode<OpcionMenuPermisos> treeNode, BdTTipousuario bdTTipousuario, EntityManager entityManager) throws Exception {
        if (treeNode.getChildCount() > 0) {
            for (TreeNode<OpcionMenuPermisos> itemNode : treeNode.getChildren()) {
                grabarCambiosOpcionesMenu(itemNode, bdTTipousuario, entityManager);
            }
        }
        else if (treeNode.getData().getIdOpcionMenu() != null) {
            //SOLO LOS NODOS FINALISTAS (SIN HIJOS)
            BdATipousuopcper filtroBdATipousuopcper = new BdATipousuopcper();
            filtroBdATipousuopcper.setIdTipousuario(this.bdTTipousuario.getIdTipousuario());
            filtroBdATipousuopcper.setIdOpcionmenu(treeNode.getData().getIdOpcionMenu());

            StATipousuopcper stATipousuopcper = new StATipousuopcper(Session.getDatosUsuario());
            ArrayList<BdATipousuopcper> listaBdATipousuopcper = stATipousuopcper.filtro(filtroBdATipousuopcper, entityManager);
            if (listaBdATipousuopcper != null) {
                //Si existe el registro, se modifica
                for (BdATipousuopcper itemOpcionMenu : listaBdATipousuopcper) {
                    itemOpcionMenu.setBoConsulta(treeNode.getData().isChkConsulta());
                    itemOpcionMenu.setBoAlta(treeNode.getData().isChkAlta());
                    itemOpcionMenu.setBoEdicion(treeNode.getData().isChkEdicion());
                    itemOpcionMenu.setBoBorrado(treeNode.getData().isChkBorrado());
                    
                    stATipousuopcper.actualiza(itemOpcionMenu, entityManager);
                }
            }
            else {
                //Si no existe el registro, se crea
                BdATipousuopcper newBdATipousuopcper = new BdATipousuopcper();
                newBdATipousuopcper.setIdTipousuario(this.bdTTipousuario.getIdTipousuario());
                newBdATipousuopcper.setIdOpcionmenu(treeNode.getData().getIdOpcionMenu());
                newBdATipousuopcper.setBoConsulta(treeNode.getData().isChkConsulta());
                newBdATipousuopcper.setBoAlta(treeNode.getData().isChkAlta());
                newBdATipousuopcper.setBoEdicion(treeNode.getData().isChkEdicion());
                newBdATipousuopcper.setBoBorrado(treeNode.getData().isChkBorrado());
                newBdATipousuopcper.setFeAlta(this.bdTTipousuario.getFeAlta());

                stATipousuopcper.alta(newBdATipousuopcper, entityManager);
            }
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
            // INICIO TRANSACCION
            try (EntityManager entityManager = AppInit.getEntityManager())
            {
                entityManager.getTransaction().begin();
                try {
                    BdATipousuopcper filtroBdATipousuopcper = new BdATipousuopcper();
                    filtroBdATipousuopcper.setIdTipousuario(this.bdTTipousuario.getIdTipousuario());
                    
                    StATipousuopcper stATipousuopcper = new StATipousuopcper(Session.getDatosUsuario());
                    ArrayList<BdATipousuopcper> listaBdATipousuopcper = stATipousuopcper.filtro(filtroBdATipousuopcper, entityManager);
                    if (listaBdATipousuopcper != null) {
                        for (BdATipousuopcper itemBdATipousuopcper : listaBdATipousuopcper) {
                            stATipousuopcper.baja(itemBdATipousuopcper, entityManager);
                        }
                    }
                                        
                    StTTipousuario stTTipousuario = new StTTipousuario(Session.getDatosUsuario());
                    stTTipousuario.baja(this.bdTTipousuario, entityManager);

                    entityManager.getTransaction().commit();

                    if (this.parent instanceof FiltroPerfiles filtroPerfiles) {
                        filtroPerfiles.getDsResultado().eliminarFilaSeleccionada();
                    }
                }
                catch (Exception ex) {
                    entityManager.getTransaction().rollback();
                    throw ex;
                }
            }
            // FIN TRANSACCION
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
        limpiarNodos(this.treeOpcionesMenu);
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
        StTTipousuario stTTipousuario = new StTTipousuario(Session.getDatosUsuario());
        this.bdTTipousuario = stTTipousuario.item(idTipousuario, null);
        if (this.bdTTipousuario == null) {
            throw new RegistryNotFoundException();
        }
        
        cCoTipousuario.setValue(this.bdTTipousuario.getCoTipousuario());
        cDsTipousuario.setValue(this.bdTTipousuario.getDsTipousuario());
        cFeAlta.setValue(this.bdTTipousuario.getFeAlta());
        cFeDesactivo.setValue(this.bdTTipousuario.getFeDesactivo());        
        cUnidad.setId(this.bdTTipousuario.getIdUnidad());
        
        this.treeOpcionesMenu = createCheckboxDocuments(idTipousuario);
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

    public TreeNode<OpcionMenuPermisos> getTreeOpcionesMenu() {
        return treeOpcionesMenu;
    }

    public void setTreeOpcionesMenu(TreeNode<OpcionMenuPermisos> treeOpcionesMenu) {
        this.treeOpcionesMenu = treeOpcionesMenu;
    }
    
    public TreeNode<OpcionMenuPermisos> buscarNodoPadre(TreeNode<OpcionMenuPermisos> root, Integer idOpcionmenu) {
        if (root.getChildCount() > 0) {
            for (TreeNode<OpcionMenuPermisos> item : root.getChildren()) {
                TreeNode<OpcionMenuPermisos> resul = buscarNodoPadre(item, idOpcionmenu);
                if (resul != null) {
                    return resul;
                }
            }
        }
        if (root.getData().getIdOpcionMenu().compareTo(idOpcionmenu) == 0) {
            return root;
        }
        return null;
    }
    
    private void limpiarNodos(TreeNode<OpcionMenuPermisos> nodo) {
        if (nodo.getChildCount() > 0) {
            for (TreeNode<OpcionMenuPermisos> subNodo : nodo.getChildren()) {
                limpiarNodos(subNodo);
            }
        }
        nodo.getData().setChkConsulta(false);
        nodo.getData().setChkAlta(false);
        nodo.getData().setChkEdicion(false);
        nodo.getData().setChkBorrado(false);
    }
    
    public TreeNode<OpcionMenuPermisos> createCheckboxDocuments(Integer idTipousuario) {
        try {
            TreeNode<OpcionMenuPermisos> root = new CheckboxTreeNode<>(new OpcionMenuPermisos(), null);
            String sql = """
                        SELECT 
                            OM.ID_OPCIONMENU, 
                            OM.DS_OPCIONMENU, 
                            OM.DS_TITULO, 
                            OM.ID_OPCIONMENUPADRE,
                            ISNULL(TUOP.BO_CONSULTA, CONVERT(BIT,0)) as BO_CONSULTA,
                            ISNULL(TUOP.BO_ALTA, CONVERT(BIT,0)) as BO_ALTA,
                            ISNULL(TUOP.BO_EDICION, CONVERT(BIT,0)) as BO_EDICION,
                            ISNULL(TUOP.BO_BORRADO, CONVERT(BIT,0)) as BO_BORRADO
                        FROM 
                            BD_T_OPCIONMENU OM
                        LEFT JOIN
                            BD_A_TIPOUSUOPCPER TUOP ON (TUOP.ID_OPCIONMENU = OM.ID_OPCIONMENU AND TUOP.ID_TIPOUSUARIO = :ID_TIPOUSUARIO)
                        ORDER BY 
                            ID_OPCIONMENUPADRE ASC""";
            sql = sql.replaceAll("(?i):ID_TIPOUSUARIO", idTipousuario.toString());
            DataSet dsOpcionesMenu = new DataSet(sql, "ID_OPCIONMENU");
            for (Row itemRow : dsOpcionesMenu.getRows()) {
                OpcionMenuPermisos item = new OpcionMenuPermisos();
                item.setIdOpcionMenu(itemRow.getColumnName("ID_OPCIONMENU").getValueInteger());
                item.setDsTitulo(itemRow.getColumnName("DS_TITULO").getValueString());
                item.setDsOpcionMenu(itemRow.getColumnName("DS_OPCIONMENU").getValueString());    
                item.setChkConsulta((Boolean)itemRow.getColumnName("BO_CONSULTA").getValue());
                item.setChkAlta((Boolean)itemRow.getColumnName("BO_ALTA").getValue());
                item.setChkEdicion((Boolean)itemRow.getColumnName("BO_EDICION").getValue());
                item.setChkBorrado((Boolean)itemRow.getColumnName("BO_BORRADO").getValue());
                
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
