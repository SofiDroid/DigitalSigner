package seguridad.menus;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
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
    private MenuModel model;
    
    @PostConstruct
    public void init() {
        model = new DefaultMenuModel();

        //Gestión de documentos
        DefaultSubMenu submenu = DefaultSubMenu.builder()
                .id("mnuGestionDocumentos")
                .label(Msg.getString("mnuGestionDocumentos"))
                .build();

        DefaultMenuItem item = DefaultMenuItem.builder()
                .id("mnuFirmaDocumentos")
                .value(Msg.getString("mnuFirmaDocumentos"))
                /*.icon("pi pi-save")*/
                .command("/GestionDocumentos/FirmaDocumentos/filtroFirmaDocumentos")
                .build();
        submenu.getElements().add(item);

        item = DefaultMenuItem.builder()
                .id("mnuConsultaDocumentos")
                .value(Msg.getString("mnuConsultaDocumentos"))
                /*.icon("pi pi-save")*/
                .command("/GestionDocumentos/ConsultaDocumentos/filtroConsultaDocumentos")
                .build();
        submenu.getElements().add(item);

        item = DefaultMenuItem.builder()
                .id("mnuInformes")
                .value(Msg.getString("mnuInformes"))
                /*.icon("pi pi-save")*/
                .command("/GestionDocumentos/Informes/filtroInformes")
                .build();
        submenu.getElements().add(item);

        model.getElements().add(submenu);

        
        //Gestión de XML
        submenu = DefaultSubMenu.builder()
                .id("mnuGestionXML")
                .label(Msg.getString("mnuGestionXML"))
                .build();

        item = DefaultMenuItem.builder()
                .id("mnuEntradas")
                .value(Msg.getString("mnuEntradas"))
                /*.icon("pi pi-save")*/
                .command("/GestionXML/Entradas/filtroEntradas")
                .build();
        submenu.getElements().add(item);

        item = DefaultMenuItem.builder()
                .id("mnuSalidas")
                .value(Msg.getString("mnuSalidas"))
                /*.icon("pi pi-save")*/
                .command("/GestionXML/Salidas/filtroSalidas")
                .build();
        submenu.getElements().add(item);

        item = DefaultMenuItem.builder()
                .id("mnuInformes")
                .value(Msg.getString("mnuInformes"))
                /*.icon("pi pi-save")*/
                .command("/GestionXML/Informes/filtroInformes")
                .build();
        submenu.getElements().add(item);

        model.getElements().add(submenu);
        
        
        //Configuración
        submenu = DefaultSubMenu.builder()
                .id("mnuConfiguracion")
                .label(Msg.getString("mnuConfiguracion"))
                .build();

        //Gestión
        DefaultSubMenu submenu2 = DefaultSubMenu.builder()
                .id("mnuGestion")
                .label(Msg.getString("mnuGestion"))
                .build();
        
        item = DefaultMenuItem.builder()
                .id("mnuAutoridades")
                .value(Msg.getString("mnuAutoridades"))
                /*.icon("pi pi-save")*/
                .command("/Configuracion/Gestion/Autoridades/filtroAutoridades")
                .build();
        submenu2.getElements().add(item);
        
        item = DefaultMenuItem.builder()
                .id("mnuTiposDocumentos")
                .value(Msg.getString("mnuTiposDocumentos"))
                /*.icon("pi pi-save")*/
                .command("/Configuracion/Gestion/TiposDocumentos/filtroTiposDocumentos")
                .build();
        submenu2.getElements().add(item);
        
        item = DefaultMenuItem.builder()
                .id("mnuSituacionesDocumentos")
                .value(Msg.getString("mnuSituacionesDocumentos"))
                /*.icon("pi pi-save")*/
                .command("/Configuracion/Gestion/SituacionesDocumentos/filtroSituacionesDocumentos")
                .build();
        submenu2.getElements().add(item);
        
        item = DefaultMenuItem.builder()
                .id("mnuSituacionesXML")
                .value(Msg.getString("mnuSituacionesXML"))
                /*.icon("pi pi-save")*/
                .command("/Configuracion/Gestion/SituacionesXML/filtroSituacionesXML")
                .build();
        submenu2.getElements().add(item);
        
        submenu.getElements().add(submenu2);
        
        //Seguridad
        submenu2 = DefaultSubMenu.builder()
                .id("mnuSeguridad")
                .label(Msg.getString("mnuSeguridad"))
                .build();
        
        item = DefaultMenuItem.builder()
                .id("mnuUsuarios")
                .value(Msg.getString("mnuUsuarios"))
                /*.icon("pi pi-save")*/
                .command("/Configuracion/Seguridad/Usuarios/filtroUsuarios")
                .build();
        submenu2.getElements().add(item);
        
        item = DefaultMenuItem.builder()
                .id("mnuPerfiles")
                .value(Msg.getString("mnuPerfiles"))
                /*.icon("pi pi-save")*/
                .command("/Configuracion/Seguridad/Perfiles/filtroPerfiles")
                .build();
        submenu2.getElements().add(item);
        
        item = DefaultMenuItem.builder()
                .id("mnuUnidades")
                .value(Msg.getString("mnuUnidades"))
                /*.icon("pi pi-save")*/
                .command("/Configuracion/Seguridad/Unidades/filtroUnidades")
                .build();
        submenu2.getElements().add(item);
        
        item = DefaultMenuItem.builder()
                .id("mnuHistoricoAccesos")
                .value(Msg.getString("mnuHistoricoAccesos"))
                /*.icon("pi pi-save")*/
                .command("/Configuracion/Seguridad/HistoricoAccesos/filtroHistoricoAccesos")
                .build();
        submenu2.getElements().add(item);
        
        submenu.getElements().add(submenu2);
        
        //Sistema
        submenu2 = DefaultSubMenu.builder()
                .id("mnuSistema")
                .label(Msg.getString("mnuSistema"))
                .build();
        
        item = DefaultMenuItem.builder()
                .id("mnuOpcionesMenu")
                .value(Msg.getString("mnuOpcionesMenu"))
                /*.icon("pi pi-save")*/
                .command("/Configuracion/Sistema/OpcionesMenu/filtroOpcionesMenu")
                .build();
        submenu2.getElements().add(item);
        
        item = DefaultMenuItem.builder()
                .id("mnuPermisos")
                .value(Msg.getString("mnuPermisos"))
                /*.icon("pi pi-save")*/
                .command("/Configuracion/Sistema/Permisos/filtroPermisos")
                .build();
        submenu2.getElements().add(item);
        
        item = DefaultMenuItem.builder()
                .id("mnuVariablesConfiguracion")
                .value(Msg.getString("mnuVariablesConfiguracion"))
                /*.icon("pi pi-save")*/
                .command("/Configuracion/Sistema/VariablesConfiguracion/fitroVariablesConfiguracion")
                .build();
        submenu2.getElements().add(item);
        
        submenu.getElements().add(submenu2);
        
        //Informes
        submenu2 = DefaultSubMenu.builder()
                .id("mnuInformes")
                .label(Msg.getString("mnuInformes"))
                .build();
        
        item = DefaultMenuItem.builder()
                .id("mnuInformesGestion")
                .value(Msg.getString("mnuInformesGestion"))
                /*.icon("pi pi-save")*/
                .command("/Configuracion/Informes/InformesGestion/filtroInformesGestion")
                .build();
        submenu2.getElements().add(item);
        
        item = DefaultMenuItem.builder()
                .id("mnuInformesSeguridad")
                .value(Msg.getString("mnuInformesSeguridad"))
                /*.icon("pi pi-save")*/
                .command("/Configuracion/Informes/InformesSeguridad/filtroInformesSeguridad")
                .build();
        submenu2.getElements().add(item);
        
        item = DefaultMenuItem.builder()
                .id("mnuInformesSistema")
                .value(Msg.getString("mnuInformesSistema"))
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

    public void setModel(MenuModel model) {
        this.model = model;
    }
}
