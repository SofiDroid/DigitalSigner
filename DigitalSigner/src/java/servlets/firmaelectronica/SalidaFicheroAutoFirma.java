package servlets.firmaelectronica;

import basedatos.tablas.BdDDocumento;
import gestionDocumentos.firmaDocumentos.FiltroFirmaDocumentos;
import init.AppInit;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.faces.FacesException;
import javax.faces.FactoryFinder;
import javax.faces.context.FacesContextFactory;
import javax.faces.lifecycle.Lifecycle;
import javax.faces.lifecycle.LifecycleFactory;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.tomcat.util.codec.binary.Base64;
import seguridad.usuarios.DatosUsuario;
import utilidades.Session;

/**
 *
 * @author ihuegal
 */
public class SalidaFicheroAutoFirma extends HttpServlet 
{
    private final Logger LOG = Logger.getLogger(SalidaFicheroAutoFirma.class);

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
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    {
        try
        {
            // Dejamos solicitar documentos solamente si está logado
//            if (((String) request.getSession().getAttribute("iniciado")).equals("true")) 
            {
                PrintWriter out=null;
                
                //Obtener la gestión, donde están los datos de los documentos firmados
                FiltroFirmaDocumentos filtroFirmaDocumentos = (FiltroFirmaDocumentos) Session.getNamedBean("filtroFirmaDocumentos");
                DatosUsuario datosUsuario = (DatosUsuario)Session.getNamedBean("datosUsuario");
                if(filtroFirmaDocumentos == null) { return; } //Error falta la Gestion
                
                BdDDocumento bdDDocumento = null;

                try 
                {
                    out = response.getWriter();
                    request.setCharacterEncoding("UTF-8");
                    
                    //FacesContext ctx = m_facesContextFactory.getFacesContext(getServletContext(), request, response, m_lifecycle);
                    
                    //Obtener con qué fichero estamos trabajando (su index)
                    String sIndexFicherofichero = (String) request.getParameter("indexFichero");
                    if(sIndexFicherofichero == null || sIndexFicherofichero.length() == 0) {
                        String msg = "Parámetro fichero no encontrado (¿el archivo firmado es demasiado grande?)";
                        LOG.error(msg);
                        //gestion.getListaErroresProcesoFirma().add(msg);
                        return; //Error falta parámetro
                    }
                    Integer idxFichero = Integer.parseInt(sIndexFicherofichero);
                    
                    //Obtener el documento que hemos firmados
                    bdDDocumento = filtroFirmaDocumentos.getDocumentoByIndex(idxFichero);
                    if(bdDDocumento == null) {
                        String msg = "Parámetro documento no encontrado";
                        LOG.error(msg);
                        //gestion.getListaErroresProcesoFirma().add(msg);
                        return; //Error falta el documento
                    } 
                    
                    //Obtener los datos que vamos a guardar (el documento firmado)
                    Base64 B64E2 = new Base64();
                    byte[] idDocumentoEntrada = B64E2.decode((String) request.getParameter("data2"));

                    if(idDocumentoEntrada == null || idDocumentoEntrada.length == 0) { 
                        String msg = "Parámetro entrada/data2 no encontrado (¿el archivo firmado es demasiado grande?)";
                        LOG.error(msg);
                        //gestion.getListaErroresProcesoFirma().add(msg);
                        return;   //Error faltan los datos
                    }
                    
                    // Recupero de "temp/salida" el fichero firmado
                    //============================================================
                    //byte[] entrada = Files.readAllBytes(Paths.get(AppInit.getPropiedad("PORTAFIRMAS_RUTAFICHEROS", 0), "salida", new String(idEntrada, "UTF-8")));
                    byte[] binDocumentoFirmado = Files.readAllBytes(Paths.get(AppInit.getRutaTempFicherosFirmados(), "salida", new String(idDocumentoEntrada, "UTF-8")));
                    
                    String idDocumentoOriginal = datosUsuario.getBdTUsuario().getCoUsuario() + "_" + datosUsuario.getIpRemota().replace(".", "_") + "_" + bdDDocumento.getIdDocumento();
                    try
                    {
                        //Files.deleteIfExists(Paths.get(AppInit.getPropiedad("PORTAFIRMAS_RUTAFICHEROS", 0), "entrada", idDocumentoOriginal));
                        //Files.deleteIfExists(Paths.get(AppInit.getPropiedad("PORTAFIRMAS_RUTAFICHEROS", 0), "salida", new String(idDocumentoEntrada, "UTF-8")));
                        Files.deleteIfExists(Paths.get(AppInit.getRutaTempFicherosFirmados(), "entrada", idDocumentoOriginal));
                        Files.deleteIfExists(Paths.get(AppInit.getRutaTempFicherosFirmados(), "salida", new String(idDocumentoEntrada, "UTF-8")));
                    }
                    catch (IOException ex)
                    {
                        LOG.error(ex.getMessage(), ex);
                        // NADA, QUE LO HAGA EL PROCESO DESATENDIDO
                    }
                    //============================================================
                    
//                    LogicaPortaFirmas logica = new LogicaPortaFirmas();
//                    int respValidar = logica.validarNIF(doc, entrada, gestion.getNif(), gestion.getDatosLogon().getStr_usuario(), gestion.getBoAutoridadEspecial());
//
//                    if(respValidar == Respuesta.WARN_INFO
//                            && !gestion.getBoFirmaIndividual()
//                            && gestion.isBoUsuarioConReintentos())
//                    {
//                        //Caso reintentable, acumulo el documento para después y no da error
//                        gestion.acumularReintento(doc);
//                        return;
//                    }
//                    
//                    if(respValidar != Respuesta.OK)
//                    {
//                        gestion.getListaErroresProcesoFirma().add(doc.getDESCRIPCION() + " - " + doc.getERRORES());
//                        gestion.getListaErroresProcesoFirma().add("Proceso abortado");
//                        response.sendError(500);
//                        return;
//                    }

//                    if(logica.actualizarDocFirmando(doc, entrada, gestion.getIdTercero()) != Respuesta.OK)
//                    {
//                        if(doc.getERRORES() != null && doc.getERRORES().length() > 0)
//                        {
//                            String descr = doc.getDESCRIPCION();
//                            if(descr == null)
//                                descr = "";
//                            final int nChar = 60;
//                            String msgError = (descr.length() > nChar ? (descr.substring(0, nChar) + "...") : descr)
//                                    + " - " + doc.getERRORES();
//                            gestion.getListaErroresProcesoFirma().add(msgError);
//                        }
//                        else
//                        {
//                            gestion.getListaErroresProcesoFirma().add(doc.getDESCRIPCION());
//                        }
//                    }
                    Files.write(Paths.get("C:\\GIT\\DigitalSigner\\Documentos", idDocumentoOriginal), binDocumentoFirmado);
                }
                catch(IOException | NumberFormatException ex)
                {
                    LOG.error(ex.getMessage(), ex);
                }
                finally 
                {
                    if(out != null)
                        out.close();
                }
            }
//            else
//            {
//                // No se ha logado, le largamos
//            }
        }
        catch(Exception ex)
        {
            // No se ha logado, le largamos
            LOG.error(ex.getMessage(), ex);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        processRequest(request, response); // No se ha logado, le largamos
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        processRequest(request, response); // No se ha logado, le largamos
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}