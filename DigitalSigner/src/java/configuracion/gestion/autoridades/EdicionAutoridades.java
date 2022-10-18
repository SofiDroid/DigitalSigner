package configuracion.gestion.autoridades;

import basedatos.ColumnBase;
import basedatos.ColumnCabecera;
import basedatos.DataSet;
import basedatos.Row;
import basedatos.RowCabecera;
import basedatos.servicios.StAAutusu;
import basedatos.servicios.StTAutoridad;
import basedatos.tablas.BdAAutusu;
import basedatos.tablas.BdTAutoridad;
import excepciones.FormModeException;
import excepciones.RegistryNotFoundException;
import excepciones.RequiredFieldException;
import init.AppInit;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import javax.el.ELContext;
import javax.faces.context.FacesContext;
import tomcat.persistence.EntityManager;
import tomcat.persistence.exceptions.SQLReferenceException;
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
public class EdicionAutoridades implements Serializable {
    private Object parent = null;
    private String paginaRetorno = null;
    
    private CampoWebCodigo cCoAutoridad = null;
    private CampoWebDescripcion cDsAutoridad= null;
    private CampoWebFecha cFeAlta = null;
    private CampoWebFecha cFeDesactivo = null;
    private CampoWebLupa cUnidad = null;
    private CampoWebLupa cUsuario = null;
    private DataSet dsUsuarios = null;
    
    BdTAutoridad bdTAutoridad = null;
    ArrayList<Integer> listaUsuariosEliminadosId = null;

    private ModoFormulario modoFormulario = ModoFormulario.CONSULTA;
    
    public EdicionAutoridades(Object parent) {
        init(parent, null);
    }
    
    public EdicionAutoridades(Object parent, Integer idAutoridad) {
        init(parent, idAutoridad);
    }
    
    private void init(Object parent, Integer idAutoridad) {
        try {
            this.parent = parent;
    
            this.cCoAutoridad = new CampoWebCodigo();
            this.cCoAutoridad.setLabel(Msg.getString("lbl_EdicionAutoridades_CoAutoridad"));
            this.cCoAutoridad.setWidthLabel("100px");
            this.cCoAutoridad.setRequired(true);
            
            this.cDsAutoridad = new CampoWebDescripcion();
            this.cDsAutoridad.setLabel(Msg.getString("lbl_EdicionAutoridades_DsAutoridad"));
            this.cDsAutoridad.setWidthLabel("100px");
            this.cDsAutoridad.setRequired(true);
            
            this.cFeAlta = new CampoWebFecha();
            this.cFeAlta.setLabel(Msg.getString("lbl_EdicionAutoridades_FeAlta"));
            this.cFeAlta.setWidthLabel("100px");
            this.cFeAlta.setRequired(true);
            
            this.cFeDesactivo = new CampoWebFecha();
            this.cFeDesactivo.setLabel(Msg.getString("lbl_EdicionAutoridades_FeDesactivo"));
            this.cFeDesactivo.setWidthLabel("100px");
            
            this.cUnidad = new CampoWebLupa();
            this.cUnidad.setLabel(Msg.getString("lbl_EdicionAutoridades_Unidad"));
            this.cUnidad.setWidthLabel("100px");
            String sql = "SELECT ID_UNIDAD, CO_UNIDAD + ' - ' + DS_UNIDAD as Unidad FROM BD_T_UNIDAD";
            this.cUnidad.setConsulta(sql);
            this.cUnidad.setColumnaID("ID_UNIDAD");
            this.cUnidad.setColumnaLabel("Unidad");
            this.cUnidad.setRequired(true);

            this.cUsuario = new CampoWebLupa();
            this.cUsuario.setLabel(Msg.getString("lbl_EdicionAutoridades_Usuario"));
            this.cUsuario.setWidthLabel("100px");
            sql = "SELECT ID_USUARIO, '(' + ISNULL(CO_NIF,'') + ') ' + ISNULL(CO_USUARIO,'') + ' - ' + ISNULL(DS_APELLIDO1,'') + ' ' + ISNULL(DS_APELLIDO2,'') + ', ' + ISNULL(DS_NOMBRE,'') as Usuario FROM BD_T_USUARIO";
            this.cUsuario.setConsulta(sql);
            this.cUsuario.setColumnaID("ID_USUARIO");
            this.cUsuario.setColumnaLabel("Usuario");
            this.cUsuario.setRequired(false);

            // LISTADO USUARIOS
            this.dsUsuarios = new DataSet();
            this.listaUsuariosEliminadosId = new ArrayList<>();
            
            this.setModoFormulario(ModoFormulario.CONSULTA);

            if (idAutoridad != null) {
                recuperarRegistro(idAutoridad);
                inicializarDataSetUsuarios(idAutoridad);
            }
            else {
                this.setModoFormulario(ModoFormulario.ALTA);
                inicializarDataSetUsuarios(idAutoridad);
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

    private void inicializarDataSetUsuarios(Integer idAutoridad) throws Exception {
        String sql = """
            SELECT 
                T1.ID_AUTORIDAD,
                T1.ID_USUARIO,
                '(' + ISNULL(usu.CO_NIF,'') + ') ' + ISNULL(usu.CO_USUARIO,'') + ' - ' + ISNULL(usu.DS_APELLIDO1,'') + ' ' + ISNULL(usu.DS_APELLIDO2,'') + ', ' + ISNULL(usu.DS_NOMBRE,'') as Usuario,
                T1.FE_ALTA,
                T1.FE_DESACTIVO
            FROM 
                BD_A_AUTUSU T1
            INNER JOIN
                BD_T_USUARIO usu ON (usu.ID_USUARIO = T1.ID_USUARIO)
            WHERE 1 = 1
            AND T1.ID_AUTORIDAD = :ID_AUTORIDAD
            """;
        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("ID_AUTORIDAD", idAutoridad);

        this.dsUsuarios = new DataSet(sql, parametros, "ID_USUARIO");
        
        this.cUsuario.setListaNotIN_Clear();
        if (this.dsUsuarios.getRowsCount() > 0) {
            for (Row itemRow : this.dsUsuarios.getRows()) {
                this.cUsuario.setListaNotIN_Add(itemRow.getColumnaID().getValueString());
            }
        }
        
        // Establecer formato de salida
        RowCabecera cabecera = this.getDsUsuarios().getCabecera();

        cabecera.getColumnName("ID_AUTORIDAD")
                .setVisible(false);

        cabecera.getColumnName("ID_USUARIO")
                .setVisible(false);

        cabecera.getColumnName("Usuario")
                .setTitle("Usuario")
                .setWidth("100%");

        cabecera.getColumnName("FE_ALTA")
                .setTitle("F. Alta")
                .setWidth("6rem");

        cabecera.getColumnName("FE_DESACTIVO")
                .setTitle("F. Desactivo")
                .setWidth("6rem");
        
        this.dsUsuarios.newColumn("btnEliminar");
        cabecera.getColumnName("btnEliminar")
                .setTitle("Eliminar")
                .setAlign(ColumnCabecera.ALIGN.CENTER)
                .setWidth("10em")
                .setTipo(ColumnBase.Tipo.BOTON_EDICION)
                .setClase(this)
                .setIcon("pi pi-times-circle")
                .setStyleClass("ui-button-danger")
                .setMethod(this.getClass().getMethod("eliminarUsuario"))
                .setUpdate("formulario:panelUsuarios,formulario:mensaje");
    }
    
    public void eliminarUsuario() {
        try {
            Integer idUsuario = this.dsUsuarios.getSelectedRow().getColumnaID().getValueInteger();
            if (idUsuario != null) {
                listaUsuariosEliminadosId.add(this.dsUsuarios.getSelectedRow().getColumnaID().getValueInteger());
                this.cUsuario.setListaNotIN_Remove(idUsuario.toString());
            }
            this.dsUsuarios.getRows().remove(this.dsUsuarios.getSelectedRow());
            if (this.dsUsuarios != null) {
                for (int i = 0; i < this.dsUsuarios.getRowsCount(); i++) {
                    Row itemRow = this.dsUsuarios.getRows().get(i);
                    itemRow.setIndex(i);
                }
            }
        } catch (Exception ex) {
            Mensajes.showException(this.getClass(), ex);
        }
    }
    
    public void addUsuario() {
        try {
            if (this.cUsuario.getValue() != null) {
                //ALTA
                Row newRow = this.dsUsuarios.newRow();
                newRow.getColumnName("ID_AUTORIDAD").setValue(null);
                newRow.getColumnName("ID_USUARIO").setValue(this.cUsuario.getId());
                newRow.getColumnName("Usuario").setValue(this.cUsuario.getValue().getLabel());
                newRow.getColumnName("FE_ALTA").setValue(new Date());
                newRow.getColumnName("FE_DESACTIVO").setValue(null);

                this.dsUsuarios.getRows().add(newRow);
                this.cUsuario.setListaNotIN_Add(this.cUsuario.getId().toString());
                this.cUsuario.setId(null);
            }
        } catch (Exception ex) {
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
                        this.bdTAutoridad = new BdTAutoridad();
                        this.bdTAutoridad.setCoAutoridad(this.cCoAutoridad.getValue());
                        this.bdTAutoridad.setDsAutoridad(this.cDsAutoridad.getValue());
                        this.bdTAutoridad.setFeAlta(this.cFeAlta.getValue());
                        this.bdTAutoridad.setFeDesactivo(this.cFeDesactivo.getValue());
                        this.bdTAutoridad.setIdUnidad(this.cUnidad.getId());

                        StTAutoridad stTAutoridad = new StTAutoridad();
                        stTAutoridad.alta(this.bdTAutoridad, entityManager);

                        if (this.dsUsuarios != null) {
                            for (Row itemRow : this.dsUsuarios.getRows()) {
                                BdAAutusu newBdAAutusu = new BdAAutusu();
                                newBdAAutusu.setIdAutoridad(this.bdTAutoridad.getIdAutoridad());
                                newBdAAutusu.setIdUsuario(itemRow.getColumnName("ID_USUARIO").getValueInteger());
                                newBdAAutusu.setFeAlta((Date)itemRow.getColumnName("FE_ALTA").getValue());
                                newBdAAutusu.setFeDesactivo((Date)itemRow.getColumnName("FE_DESACTIVO").getValue());

                                StAAutusu stAAutusu = new StAAutusu();
                                stAAutusu.alta(newBdAAutusu, entityManager);
                            }
                        }
                        
                        entityManager.getTransaction().commit();
                        
                        if (this.parent instanceof FiltroAutoridades filtroAutoridades) {
                            filtroAutoridades.getDsResultado().refrescarDatos();
                        }

                        Mensajes.showInfo("Información", "Alta realizada correctamente!");
                    }

                    //ACTUALIZACION
                    if (this.modoFormulario == ModoFormulario.EDICION) {
                        this.bdTAutoridad.setCoAutoridad(this.cCoAutoridad.getValue());
                        this.bdTAutoridad.setDsAutoridad(this.cDsAutoridad.getValue());
                        this.bdTAutoridad.setFeAlta(this.cFeAlta.getValue());
                        this.bdTAutoridad.setFeDesactivo(this.cFeDesactivo.getValue());
                        this.bdTAutoridad.setIdUnidad(this.cUnidad.getId());

                        StTAutoridad stTAutoridad = new StTAutoridad();
                        stTAutoridad.actualiza(this.bdTAutoridad, entityManager);

                        for (Integer idUsuario : listaUsuariosEliminadosId) {
                            BdAAutusu delBdAAutusu = new BdAAutusu();
                            delBdAAutusu.setIdAutoridad(this.bdTAutoridad.getIdAutoridad());
                            delBdAAutusu.setIdUsuario(idUsuario);
                            
                            StAAutusu stAAutusu = new StAAutusu();
                            stAAutusu.baja(delBdAAutusu, entityManager);
                        }
                        
                        if (this.dsUsuarios != null) {
                            for (Row itemRow : this.dsUsuarios.getRows()) {
                                BdAAutusu newBdAAutusu = new BdAAutusu();
                                newBdAAutusu.setIdAutoridad(this.bdTAutoridad.getIdAutoridad());
                                newBdAAutusu.setIdUsuario(itemRow.getColumnName("ID_USUARIO").getValueInteger());

                                StAAutusu stAAutusu = new StAAutusu();
                                ArrayList<BdAAutusu> listaBdAAutusu = stAAutusu.filtro(newBdAAutusu, entityManager);
                                if (listaBdAAutusu == null || listaBdAAutusu.isEmpty()) {
                                    newBdAAutusu.setFeAlta((Date)itemRow.getColumnName("FE_ALTA").getValue());
                                    newBdAAutusu.setFeDesactivo((Date)itemRow.getColumnName("FE_DESACTIVO").getValue());

                                    stAAutusu.alta(newBdAAutusu, entityManager);
                                }
                            }
                        }
                        
                        entityManager.getTransaction().commit();
                        
                        if (this.parent instanceof FiltroAutoridades filtroAutoridades) {
                            ELContext elContext = FacesContext.getCurrentInstance().getELContext();
                            filtroAutoridades = (FiltroAutoridades)elContext.getELResolver().getValue(elContext, null, "filtroAutoridades");
                            filtroAutoridades.getDsResultado().actualizarFilaSeleccionada();
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
                    BdAAutusu filtroBdAAutusu = new BdAAutusu();
                    filtroBdAAutusu.setIdAutoridad(this.bdTAutoridad.getIdAutoridad());
                    StAAutusu stAAutusu = new StAAutusu();
                    ArrayList<BdAAutusu> listaBdAAutusu = stAAutusu.filtro(filtroBdAAutusu, entityManager);
                    if (listaBdAAutusu != null && !listaBdAAutusu.isEmpty()) {
                        for (BdAAutusu itemBdAAutusu : listaBdAAutusu) {
                            stAAutusu.baja(itemBdAAutusu, entityManager);
                        }
                    }

                    StTAutoridad stTAutoridad = new StTAutoridad();
                    stTAutoridad.baja(this.bdTAutoridad, entityManager);

                    entityManager.getTransaction().commit();
                    
                    if (this.parent instanceof FiltroAutoridades filtroAutoridades) {
                        filtroAutoridades.getDsResultado().eliminarFilaSeleccionada();
                    }

                    Mensajes.showInfo("Información", "Registro eliminado correctamente!");
                }
                catch (SQLReferenceException rex) {
                    entityManager.getTransaction().rollback();
                    Mensajes.showWarn("No se pudo eliminar", "El registro está siendo utilizado en otro apartado del programa. Elimine toda relación con este registro para poder eliminarlo.");
                    return;
                }
                catch (Exception ex) {
                    entityManager.getTransaction().rollback();
                    throw ex;
                }
            }
            // FIN TRANSACCION
            this.setModoFormulario(ModoFormulario.ELIMINADO);
        }
        catch (Exception ex) {
            Mensajes.showException(this.getClass(), ex);
        }
    }
    
    public void limpiar() {
        this.cCoAutoridad.setValue(null);
        this.cDsAutoridad.setValue(null);
        this.cFeAlta.setValue(null);
        this.cFeDesactivo.setValue(null);
        this.cUnidad.setId(null);
        this.cUsuario.setId(null);
        
        listaUsuariosEliminadosId.clear();
        if (this.dsUsuarios != null) {
            for (Row itemRow : this.dsUsuarios.getRows()) {
                listaUsuariosEliminadosId.add(itemRow.getColumnaID().getValueInteger());
            }
            this.dsUsuarios.clear();
            this.cUsuario.setListaNotIN_Clear();
        }
    }
    
    public String volver() {
        try {
            if (this.modoFormulario == ModoFormulario.EDICION) {
                recuperarRegistro(this.bdTAutoridad.getIdAutoridad());
                inicializarDataSetUsuarios(this.bdTAutoridad.getIdAutoridad());
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
        if (Validation.isNullOrEmpty(this.cCoAutoridad.getValue())) {
            throw new RequiredFieldException(this.cCoAutoridad.getLabel());
        }
        if (Validation.isNullOrEmpty(this.cDsAutoridad.getValue())) {
            throw new RequiredFieldException(this.cDsAutoridad.getLabel());
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
                this.cCoAutoridad.setProtegido(true);
                this.cDsAutoridad.setProtegido(true);
                this.cFeAlta.setProtegido(true);
                this.cFeDesactivo.setProtegido(true);
                this.cUnidad.setProtegido(true);
                this.cUsuario.setProtegido(true);
            }
            case EDICION -> {
                this.cCoAutoridad.setProtegido(false);
                this.cDsAutoridad.setProtegido(false);
                this.cFeAlta.setProtegido(false);
                this.cFeDesactivo.setProtegido(false);
                this.cUnidad.setProtegido(false);
                this.cUsuario.setProtegido(false);
            }
            case ALTA -> {
                limpiar();
                this.cCoAutoridad.setProtegido(false);
                this.cDsAutoridad.setProtegido(false);
                this.cFeAlta.setProtegido(false);
                this.cFeDesactivo.setProtegido(false);
                this.cUnidad.setProtegido(false);
                this.cUsuario.setProtegido(false);
            }
            default -> throw new FormModeException();
        }
    }
    
    private void recuperarRegistro(Integer idAutoridad) throws Exception {
        StTAutoridad stTAutoridad = new StTAutoridad();
        this.bdTAutoridad = stTAutoridad.item(idAutoridad, null);
        if (this.bdTAutoridad == null) {
            throw new RegistryNotFoundException();
        }
        
        cCoAutoridad.setValue(this.bdTAutoridad.getCoAutoridad());
        cDsAutoridad.setValue(this.bdTAutoridad.getDsAutoridad());
        cFeAlta.setValue(this.bdTAutoridad.getFeAlta());
        cFeDesactivo.setValue(this.bdTAutoridad.getFeDesactivo());
        cUnidad.setId(this.bdTAutoridad.getIdUnidad());        
    }
    
    public CampoWebCodigo getcCoAutoridad() {
        return cCoAutoridad;
    }

    public void setcCoAutoridad(CampoWebCodigo cCoAutoridad) {
        this.cCoAutoridad = cCoAutoridad;
    }

    public CampoWebDescripcion getcDsAutoridad() {
        return cDsAutoridad;
    }

    public void setcDsAutoridad(CampoWebDescripcion cDsAutoridad) {
        this.cDsAutoridad = cDsAutoridad;
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
        if (this.dsUsuarios != null) {
            this.dsUsuarios.setModoFormulario(modoFormulario);
        }
        protegerCampos();
    }

    public Object getParent() {
        return parent;
    }

    public void setParent(Object parent) {
        this.parent = parent;
    }

    public BdTAutoridad getBdTAutoridad() {
        return bdTAutoridad;
    }

    public void setBdTAutoridad(BdTAutoridad bdTAutoridad) {
        this.bdTAutoridad = bdTAutoridad;
    }

    public DataSet getDsUsuarios() {
        return dsUsuarios;
    }

    public void setDsUsuarios(DataSet dsUsuarios) {
        this.dsUsuarios = dsUsuarios;
    }

    public CampoWebLupa getcUsuario() {
        return cUsuario;
    }

    public void setcUsuario(CampoWebLupa cUsuario) {
        this.cUsuario = cUsuario;
    }
}
