package servlets.firmaelectronica;

import afirma.AfirmaUtils;
import afirma.ResultadoValidacionFirmas;
import basedatos.tablas.BdDDocumento;
import gestionDocumentos.firmaDocumentos.FiltroFirmaDocumentos;
import init.AppInit;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.faces.FacesException;
import javax.faces.FactoryFinder;
import javax.faces.context.FacesContextFactory;
import javax.faces.lifecycle.Lifecycle;
import javax.faces.lifecycle.LifecycleFactory;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.tomcat.util.codec.binary.Base64;
import seguridad.usuarios.DatosUsuario;
import utilidades.Configuraciones;
import utilidades.Session;

/**
 *
 * @author ihuegal
 */
public class EntradaFicheroAutoFirma extends HttpServlet 
{
    private final Logger LOG = Logger.getLogger(EntradaFicheroAutoFirma.class);

    private FacesContextFactory m_facesContextFactory;
    private Lifecycle m_lifecycle;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            LifecycleFactory lifecycleFactory = (LifecycleFactory) FactoryFinder.getFactory(FactoryFinder.LIFECYCLE_FACTORY);
            m_facesContextFactory = (FacesContextFactory) FactoryFinder.getFactory(FactoryFinder.FACES_CONTEXT_FACTORY);
            m_lifecycle = lifecycleFactory.getLifecycle(LifecycleFactory.DEFAULT_LIFECYCLE);

        } catch (FacesException e) {
            System.out.println(e);
        }
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        try
        {
            // Dejamos solicitar documentos solamente si está logado
//            if (((String) request.getSession().getAttribute("iniciado")).equals("true")) 
            {
                ServletOutputStream out=null;
                try 
                {
                    out=response.getOutputStream();
                    response.setContentType("text/html;charset=UTF-8");
                    request.setCharacterEncoding("UTF-8");

                    // Recibo  un identificador para el fichero a firmar
                    // Sacamos el fichero de base de datos y lo devolvemos
                    // Ahora se lee de disco                    
                    //======================================================
                    //Obtener con qué fichero estamos trabajando (su index)
                    String sIndexFicherofichero = (String) request.getParameter("indexFichero");
                    if(sIndexFicherofichero == null || sIndexFicherofichero.length() == 0) return; //Error falta parámetro
                    Integer idxFichero = Integer.parseInt(sIndexFicherofichero);
                    
                    //Obtener la gestión, donde están los datos de los documentos firmados
                    FiltroFirmaDocumentos filtroFirmaDocumentos = (FiltroFirmaDocumentos)Session.getNamedBean("filtroFirmaDocumentos");
                    DatosUsuario datosUsuario = (DatosUsuario)Session.getNamedBean("datosUsuario");
                    if(filtroFirmaDocumentos == null) { 
                        String msg = "Bean filtroFirmaDocumentos no encontrado.";
                        LOG.error(msg);
                        throw new Exception("- " + msg);
                    }
                    
                    //Obtener el documento que hemos firmados
                    BdDDocumento bdDDocumento = filtroFirmaDocumentos.getDocumentoByIndex(idxFichero);
                    if(bdDDocumento == null) {
                        String msg = "Parámetro documento no encontrado";
                        LOG.error(msg);
                        throw new Exception("- " + "idxFichero -> " + idxFichero + ": " + msg);
                    }
                    
                    //Traza DEBUG
                    LOG.debug(String.format("Portafirmas: El usuario '%s' baja para firmar el documento con ID_DOCUMENTO = %d", datosUsuario.getBdTUsuario().getCoUsuario(), bdDDocumento.getIdDocumento()));

                    byte[] binDocumento = bdDDocumento.getBlDocumento(null);

                    if(binDocumento == null || binDocumento.length == 0)
                    {
                        //gestion.getListaErroresProcesoFirma().add(doc.getDESCRIPCION() + " - No existe el documento, consulte con soporte");
                        binDocumento = new byte[0];
                    }
                    else
                    {
                        boolean validarNIF = Boolean.valueOf(new Configuraciones(Session.getDatosUsuario()).recuperaConfiguracion("VALIDARNIF"));
                        boolean validarFirma = Boolean.valueOf(new Configuraciones(Session.getDatosUsuario()).recuperaConfiguracion("VALIDARFIRMA"));

                        if(validarNIF || validarFirma)
                        {
                            if (bdDDocumento.getCoExtension().equalsIgnoreCase("XSIG")) {
                                //Si el documento es XSIG, validar las firmas.
                                
                                // INICIO VALIDAR NIF Y FIRMA
                                AfirmaUtils afirmaUtils = new AfirmaUtils();
                                ResultadoValidacionFirmas resultadoValidacionFirmas = afirmaUtils.validarFirmas(binDocumento, true, true, true, true);
                                if (!resultadoValidacionFirmas.isBoValid()) {
                                    String msg = resultadoValidacionFirmas.getDsValidacion();
                                    LOG.error(msg);
                                    
                                    binDocumento = new byte[0];
                                    throw new Exception("- " + bdDDocumento.getCoDocumento() + ": " + msg);
                                }
                                // FIN VALIDAR NIF Y FIRMA
                            }
                        }
                    }

                    Base64 B64E2 = new Base64();
                    String ipAddress = request.getHeader("X-FORWARDED-FOR");
                    if (ipAddress == null) {
                        ipAddress = request.getRemoteAddr();
                    }
                    String idDocumentoOriginal = datosUsuario.getBdTUsuario().getCoUsuario() + "_" + ipAddress.replace(".", "_").replace(":", "_") + "_" + bdDDocumento.getIdDocumento();
                    String idDocumentoB64 = B64E2.encodeToString(idDocumentoOriginal.getBytes("UTF-8")).replace("\n","").replace("\r","");
                    //======================================================

                    // Grabo en temp/entrada el fichero con el nombre idOriginal
                    //======================================================
                    Files.write(Paths.get(AppInit.getRutaTempFicherosFirmados(), "entrada", idDocumentoOriginal), binDocumento);
                    //======================================================

                    // Escribo el ID en la salida
                    //======================================================
                    out.write(idDocumentoB64.getBytes("UTF-8"));
                    out.flush();
                    //======================================================
                }
                catch (Exception ex) 
                {
                    LOG.error(ex.getMessage(), ex);
                    if (out != null) {
                        response.getWriter().write("[ERROR] " + ex.getMessage());
                    }
                }
                finally 
                {
                    if (out!=null)
                    {
                        out.flush();                    
                        out.close();
                    }
                }
            }
//            else
//            {
//                // No se ha logado, le largamos
//            }
        }
        catch(IOException ex)
        {
            // No se ha logado, le largamos
            LOG.error(ex.getMessage(), ex);
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            processRequest(request, response);
        } catch (IOException | ServletException ex) {
            // No se ha logado, le largamos
            LOG.error(ex.getMessage(), ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            processRequest(request, response);
        } catch (IOException | ServletException ex) {
            // No se ha logado, le largamos
            LOG.error(ex.getMessage(), ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}

