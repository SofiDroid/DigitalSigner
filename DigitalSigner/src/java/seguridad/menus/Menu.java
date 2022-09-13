package seguridad.menus;

import java.io.Serializable;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;
import utilidades.Msg;

/**
 *
 * @author ihuegal
 */
@Named
@SessionScoped
public class Menu implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private MenuModel model;
    
    @PostConstruct
    public void init() {
        Msg msg = new Msg();
        
        model = new DefaultMenuModel();

        //Gestión de documentos
        DefaultSubMenu submenu = DefaultSubMenu.builder()
                .label(msg.getString("mnuGestionDocumentos"))
                .build();

        DefaultMenuItem item = DefaultMenuItem.builder()
                .value(msg.getString("mnuFirmaDocumentos"))
                /*.icon("pi pi-save")*/
                .command("/GestionDocumentos/FirmaDocumentos/filtroFirmaDocumentos")
                .build();
        submenu.getElements().add(item);

        item = DefaultMenuItem.builder()
                .value(msg.getString("mnuConsultaDocumentos"))
                /*.icon("pi pi-save")*/
                .command("/GestionDocumentos/FirmaDocumentos/filtroConsultaDocumentos")
                .build();
        submenu.getElements().add(item);

        item = DefaultMenuItem.builder()
                .value(msg.getString("mnuInformes"))
                /*.icon("pi pi-save")*/
                .command("/GestionDocumentos/FirmaDocumentos/filtroInformes")
                .build();
        submenu.getElements().add(item);

        model.getElements().add(submenu);

        
        //Gestión de XML
        submenu = DefaultSubMenu.builder()
                .label(msg.getString("mnuGestionXML"))
                .build();

        item = DefaultMenuItem.builder()
                .value(msg.getString("mnuEntradas"))
                /*.icon("pi pi-save")*/
                .command("/GestionXML/Entradas/filtroEntradas")
                .build();
        submenu.getElements().add(item);

        item = DefaultMenuItem.builder()
                .value(msg.getString("mnuSalidas"))
                /*.icon("pi pi-save")*/
                .command("/GestionXML/Salidas/filtroSalidas")
                .build();
        submenu.getElements().add(item);

        item = DefaultMenuItem.builder()
                .value(msg.getString("mnuInformes"))
                /*.icon("pi pi-save")*/
                .command("/GestionXML/Informes/filtroInformes")
                .build();
        submenu.getElements().add(item);

        model.getElements().add(submenu);
        
        
        //Configuración
        submenu = DefaultSubMenu.builder()
                .label(msg.getString("mnuConfiguracion"))
                .build();

        //Gestión
        DefaultSubMenu submenu2 = DefaultSubMenu.builder()
                .label(msg.getString("mnuGestion"))
                .build();
        
        item = DefaultMenuItem.builder()
                .value(msg.getString("mnuAutoridades"))
                /*.icon("pi pi-save")*/
                .command("/Configuracion/Gestion/Autoridades/filtroAutoridades")
                .build();
        submenu2.getElements().add(item);
        
        item = DefaultMenuItem.builder()
                .value(msg.getString("mnuTiposDocumentos"))
                /*.icon("pi pi-save")*/
                .command("/Configuracion/Gestion/TiposDocumentos/filtroTiposDocumentos")
                .build();
        submenu2.getElements().add(item);
        
        item = DefaultMenuItem.builder()
                .value(msg.getString("mnuSituacionesDocumentos"))
                /*.icon("pi pi-save")*/
                .command("/Configuracion/Gestion/SituacionesDocumentos/filtroSituacionesDocumentos")
                .build();
        submenu2.getElements().add(item);
        
        item = DefaultMenuItem.builder()
                .value(msg.getString("mnuSituacionesXML"))
                /*.icon("pi pi-save")*/
                .command("/Configuracion/Gestion/SituacionesXML/filtroSituacionesXML")
                .build();
        submenu2.getElements().add(item);
        
        submenu.getElements().add(submenu2);
        
        //Seguridad
        submenu2 = DefaultSubMenu.builder()
                .label(msg.getString("mnuSeguridad"))
                .build();
        
        item = DefaultMenuItem.builder()
                .value(msg.getString("mnuUsuarios"))
                /*.icon("pi pi-save")*/
                .command("/Configuracion/Seguridad/Usuarios/filtroUsuarios")
                .build();
        submenu2.getElements().add(item);
        
        item = DefaultMenuItem.builder()
                .value(msg.getString("mnuPerfiles"))
                /*.icon("pi pi-save")*/
                .command("/Configuracion/Seguridad/Perfiles/filtroPerfiles")
                .build();
        submenu2.getElements().add(item);
        
        item = DefaultMenuItem.builder()
                .value(msg.getString("mnuUnidades"))
                /*.icon("pi pi-save")*/
                .command("/Configuracion/Seguridad/Unidades/filtroUnidades")
                .build();
        submenu2.getElements().add(item);
        
        item = DefaultMenuItem.builder()
                .value(msg.getString("mnuHistoricoAccesos"))
                /*.icon("pi pi-save")*/
                .command("/Configuracion/Seguridad/HistoricoAccesos/filtroHistoricoAccesos")
                .build();
        submenu2.getElements().add(item);
        
        submenu.getElements().add(submenu2);
        
        //Sistema
        submenu2 = DefaultSubMenu.builder()
                .label(msg.getString("mnuSistema"))
                .build();
        
        item = DefaultMenuItem.builder()
                .value(msg.getString("mnuOpcionesMenu"))
                /*.icon("pi pi-save")*/
                .command("/Configuracion/Sistema/OpcionesMenu/filtroOpcionesMenu")
                .build();
        submenu2.getElements().add(item);
        
        item = DefaultMenuItem.builder()
                .value(msg.getString("mnuPermisos"))
                /*.icon("pi pi-save")*/
                .command("/Configuracion/Sistema/Permisos/filtroPermisos")
                .build();
        submenu2.getElements().add(item);
        
        item = DefaultMenuItem.builder()
                .value(msg.getString("mnuVariablesConfiguracion"))
                /*.icon("pi pi-save")*/
                .command("/Configuracion/Sistema/VariablesConfiguracion/fitroVariablesConfiguracion")
                .build();
        submenu2.getElements().add(item);
        
        submenu.getElements().add(submenu2);
        
        //Informes
        submenu2 = DefaultSubMenu.builder()
                .label(msg.getString("mnuInformes"))
                .build();
        
        item = DefaultMenuItem.builder()
                .value(msg.getString("mnuInformesGestion"))
                /*.icon("pi pi-save")*/
                .command("/Configuracion/Informes/InformesGestion/filtroInformesGestion")
                .build();
        submenu2.getElements().add(item);
        
        item = DefaultMenuItem.builder()
                .value(msg.getString("mnuInformesSeguridad"))
                /*.icon("pi pi-save")*/
                .command("/Configuracion/Informes/InformesSeguridad/filtroInformesSeguridad")
                .build();
        submenu2.getElements().add(item);
        
        item = DefaultMenuItem.builder()
                .value(msg.getString("mnuInformesSistema"))
                /*.icon("pi pi-save")*/
                .command("/Configuracion/Informes/InformesSistema/filtroInformesSistema")
                .build();
        submenu2.getElements().add(item);
        
        submenu.getElements().add(submenu2);
        
        model.getElements().add(submenu);
    }

    public MenuModel getModel() {
        return model;
    }
}
