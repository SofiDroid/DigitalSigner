package seguridad.menus;

import basedatos.DataSet;
import basedatos.Row;
import java.io.Serializable;
import java.util.MissingResourceException;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuElement;
import org.primefaces.model.menu.MenuModel;
import utilidades.Mensajes;
import utilidades.Msg;
import utilidades.Session;

/**
 *
 * @author ihuegal
 */
@Named
@SessionScoped
public class Menu implements Serializable {
    private MenuModel model;
    private DataSet dsMenu;
    private Permisos permisos;
    public Menu() {
        this.model = new DefaultMenuModel();
        cargarMenuUsuarioUnidad(Session.getDatosUsuario().getBdTUsuario().getIdUsuario(),
                            Session.getDatosUsuario().getBdTUnidad().getIdUnidad());
    }

    public MenuModel getModel() {
        return model;
    }

    public void setModel(MenuModel model) {
        this.model = model;
    }
    
    private void cargarMenuUsuarioUnidad(Integer idUsuario, Integer idUnidad) {
        try {
            String sql = """
                        WITH CTE_OPCIONMENU 
                        AS
                        (
                            SELECT
                                CAST(OM.EN_ORDEN as NVARCHAR(MAX)) as ORDEN,
                                OM.ID_OPCIONMENU, 
                                OM.CO_OPCIONMENU, 
                                OM.DS_TITULO, 
                                OM.DS_RUTA,
                                OM.ID_OPCIONMENUPADRE
                            FROM
                                BD_T_OPCIONMENU OM
                            WHERE 
                                OM.ID_OPCIONMENUPADRE IS NULL
                        UNION ALL
                            SELECT
                                cte.ORDEN + '->' + CAST(OM.EN_ORDEN as NVARCHAR(MAX)) as ORDEN,
                                OM.ID_OPCIONMENU, 
                                OM.CO_OPCIONMENU, 
                                OM.DS_TITULO, 
                                OM.DS_RUTA,
                                OM.ID_OPCIONMENUPADRE
                            FROM 
                                BD_T_OPCIONMENU OM
                            INNER JOIN 
                                CTE_OPCIONMENU cte ON (OM.ID_OPCIONMENUPADRE = cte.ID_OPCIONMENU)
                        ),
                        WT_TIPOUSUOPCPER
                        AS
                        (
                            SELECT DISTINCT
                                TUOP.*
                            FROM 
                                BD_T_TIPOUSUARIO tu
                            INNER JOIN
                                BD_A_USUTIPOUSU utu ON (utu.ID_TIPOUSUARIO = tu.ID_TIPOUSUARIO)
                            INNER JOIN
                                BD_A_TIPOUSUOPCPER TUOP ON (tuop.ID_TIPOUSUARIO = tu.ID_TIPOUSUARIO)
                            WHERE tu.ID_UNIDAD = :ID_UNIDAD
                            AND utu.ID_USUARIO = :ID_USUARIO
                        )
                        SELECT 
                            OM.ORDEN,
                            OM.ID_OPCIONMENU, 
                            OM.CO_OPCIONMENU, 
                            OM.DS_TITULO, 
                            OM.DS_RUTA,
                            OM.ID_OPCIONMENUPADRE,
                            (SELECT count(*) FROM BD_T_OPCIONMENU WHERE ID_OPCIONMENUPADRE = OM.ID_OPCIONMENU) as HIJOS,
                            CONVERT(BIT,MAX(CONVERT(INT,ISNULL(TUOP.BO_CONSULTA, CONVERT(BIT,0))))) as BO_CONSULTA,
                            CONVERT(BIT,MAX(CONVERT(INT,ISNULL(TUOP.BO_ALTA, CONVERT(BIT,0))))) as BO_ALTA,
                            CONVERT(BIT,MAX(CONVERT(INT,ISNULL(TUOP.BO_EDICION, CONVERT(BIT,0))))) as BO_EDICION,
                            CONVERT(BIT,MAX(CONVERT(INT,ISNULL(TUOP.BO_BORRADO, CONVERT(BIT,0))))) as BO_BORRADO
                        FROM 
                            CTE_OPCIONMENU OM
                        LEFT JOIN
                            WT_TIPOUSUOPCPER TUOP ON (TUOP.ID_OPCIONMENU = OM.ID_OPCIONMENU)
                        GROUP BY
                            OM.ORDEN,
                            OM.ID_OPCIONMENU, 
                            OM.CO_OPCIONMENU, 
                            OM.DS_TITULO, 
                            OM.DS_RUTA,
                            OM.ID_OPCIONMENUPADRE
                        ORDER BY 
                            OM.ORDEN ASC                        
                         """;
            sql = sql.replaceAll("(?i):ID_USUARIO", idUsuario.toString());
            sql = sql.replaceAll("(?i):ID_UNIDAD", idUnidad.toString());
            dsMenu = new DataSet(sql, "ID_OPCIONMENU");
            if (dsMenu.getRowsCount() > 0) {
                for (Row itemRow : dsMenu.getRows()) {
                    Boolean boConsulta = (Boolean)itemRow.getColumnName("BO_CONSULTA").getValue();
                    Boolean boAlta = (Boolean)itemRow.getColumnName("BO_CONSULTA").getValue();
                    Boolean boEdicion = (Boolean)itemRow.getColumnName("BO_CONSULTA").getValue();
                    Boolean boBorrado = (Boolean)itemRow.getColumnName("BO_CONSULTA").getValue();
                    String idOpcionesMenu = itemRow.getColumnName("ID_OPCIONMENU").getValueString();
                    String idOpcionesMenuPadre = itemRow.getColumnName("ID_OPCIONMENUPADRE").getValueString();
                    String dsTitulo = itemRow.getColumnName("DS_TITULO").getValueString();
                    String dsRuta = itemRow.getColumnName("DS_RUTA").getValueString();
                    try {
                        dsTitulo = Msg.getString(itemRow.getColumnName("DS_TITULO").getValueString());
                    }
                    catch (MissingResourceException na) {
                        //NADA SI NO ENCUENTRA EL RESOURCE PARA IDIOMAS
                    }
                    if (itemRow.getColumnName("HIJOS").getValueInteger() > 0) {
                        DefaultSubMenu submenu = DefaultSubMenu.builder()
                                .id(idOpcionesMenu)
                                .label(dsTitulo)
                                .build();                
                        
                        if (!model.getElements().isEmpty()) {
                            boolean encontrado = false;
                            for (MenuElement itemElement : model.getElements()) {
                                DefaultSubMenu subMenuPadre = enlazarMenu(itemElement, idOpcionesMenuPadre);
                                if (subMenuPadre != null) {
                                    subMenuPadre.getElements().add(submenu);
                                    encontrado = true;
                                    break;
                                }
                            }
                            if (!encontrado) {
                                model.getElements().add(submenu);
                            }
                        }
                        else {
                            model.getElements().add(submenu);
                        }
                    }
                    else {
                        //Si no tiene ningun permiso no muestro la opcion en el menu.
                        if (!boConsulta && !boAlta && !boEdicion && !boBorrado) {
                            continue; //Paso al siguiente elemento
                        }

                        DefaultMenuItem item = DefaultMenuItem.builder()
                                .id(idOpcionesMenu)
                                .value(dsTitulo)
                                /*.icon("pi pi-save")*/
                                .onclick("SeleccionarMenu([{name: 'idOpcionesMenu', value: '" + idOpcionesMenu + "'}]);")
                                .command(dsRuta)
                                .build();
                        if (!model.getElements().isEmpty()) {
                            boolean encontrado = false;
                            for (MenuElement itemElement : model.getElements()) {
                                DefaultSubMenu subMenuPadre = enlazarMenu(itemElement, idOpcionesMenuPadre);
                                if (subMenuPadre != null) {
                                    subMenuPadre.getElements().add(item);
                                    encontrado = true;
                                    break;
                                }
                            }
                            if (!encontrado) {
                                model.getElements().add(item);
                            }
                        }
                        else {
                            model.getElements().add(item);
                        }
                    }
                }
            }
            
            if (!model.getElements().isEmpty()) {
                for (int i = model.getElements().size()-1; i >= 0; i--) {
                    limpiarSubMenusVacios(null, model.getElements().get(i));
                    if (model.getElements().get(i) instanceof DefaultSubMenu) {
                        if (((DefaultSubMenu)model.getElements().get(i)).getElementsCount() == 0)
                        {
                            model.getElements().remove(i);
                        }
                    }
                }
            }
        }
        catch (Exception ex) {
            Mensajes.showException(Menu.class, ex);
        }
    }
    
    private DefaultSubMenu enlazarMenu(MenuElement nodo, String idOpcionMenuPadre) {
        if (nodo instanceof DefaultSubMenu submenu) {
            if (submenu.getId().equals(idOpcionMenuPadre)) {
                return submenu;
            }
            if (!submenu.getElements().isEmpty()) {
                for (MenuElement itemElement : submenu.getElements()) {
                    DefaultSubMenu resultado = enlazarMenu(itemElement, idOpcionMenuPadre);
                    if (resultado != null) {
                        return resultado;
                    }
                }
            }
        }
        return null;
    }
    
    private void limpiarSubMenusVacios(MenuElement nodoPadre, MenuElement nodo) {
        if (nodo instanceof DefaultSubMenu) {
            DefaultSubMenu submenu = (DefaultSubMenu)nodo;
            if (submenu.getElementsCount() != 0) {
                for (int i = submenu.getElements().size()-1; i >= 0; i--) {
                    limpiarSubMenusVacios(nodo, submenu.getElements().get(i));
                }
            }
            
            if (submenu.getElementsCount() == 0) {
                if (nodoPadre != null) {
                    ((DefaultSubMenu)nodoPadre).getElements().remove(submenu);
                }
            }
        }
    }
    
    public Permisos obtenerPermisosMenu() {
        String idOpcionesMenu = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idOpcionesMenu");
        permisos = new Permisos();
        permisos.setIdOpcionesMenu(idOpcionesMenu);
        try {
            for (Row itemRow : this.dsMenu.getRows()) {
                if (idOpcionesMenu.equalsIgnoreCase(itemRow.getColumnName("ID_OPCIONMENU").getValueString())) {
                    permisos.setBoConsulta((Boolean)itemRow.getColumnName("BO_CONSULTA").getValue());
                    permisos.setBoAlta((Boolean)itemRow.getColumnName("BO_ALTA").getValue());
                    permisos.setBoEdicion((Boolean)itemRow.getColumnName("BO_EDICION").getValue());
                    permisos.setBoBorrado((Boolean)itemRow.getColumnName("BO_BORRADO").getValue());
                }
            }
        }
        catch (Exception ex) {
            Mensajes.showException(Menu.class, ex);
        }
        return permisos;
    }

    public Permisos getPermisos() {
        return permisos;
    }

    public void setPermisos(Permisos permisos) {
        this.permisos = permisos;
    }
}
