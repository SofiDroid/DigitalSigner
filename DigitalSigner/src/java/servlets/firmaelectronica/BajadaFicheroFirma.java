package servlets.firmaelectronica;

import afirma.AfirmaUtils;
import afirma.ResultadoValidacionFirmas;
import basedatos.tablas.BdDDocumento;
import gestionDocumentos.firmaDocumentos.FiltroFirmaDocumentos;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URLConnection;
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
public class BajadaFicheroFirma extends HttpServlet 
{
    private final Logger LOG = Logger.getLogger(BajadaFicheroFirma.class);

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
            //if (((String) request.getSession().getAttribute("iniciado")).equals("true")) 
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
                    if(sIndexFicherofichero == null || sIndexFicherofichero.length() == 0) {
                        String msg = "Parámetro fichero no encontrado (¿el archivo firmado es demasiado grande?)";
                        LOG.error(msg);
                        throw new Exception("- " + "indexFichero: " + msg);
                    }
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
                    
                    String extension = bdDDocumento.getCoExtension().toUpperCase();
                    String fileName = bdDDocumento.getCoFichero();
                    byte[] binDocumento = bdDDocumento.getBlDocumento(null);
                    
                    if(binDocumento == null || binDocumento.length == 0)
                    {
                        binDocumento = new byte[0];
                    }
                    else
                    {
                        boolean validarNIF = Boolean.valueOf(new Configuraciones().recuperaConfiguracion("VALIDARNIF"));
                        boolean validarFirma = Boolean.valueOf(new Configuraciones().recuperaConfiguracion("VALIDARFIRMA"));

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

                    String mimeType;
                    try (ByteArrayInputStream bais = new ByteArrayInputStream(binDocumento)) {
                        mimeType = URLConnection.guessContentTypeFromStream(bais);
                    }
                    
                    response.setContentType(mimeType);
                    switch (extension) {
                        case "PDF" -> //response.setContentType("application/pdf");
                            response.setHeader("Content-disposition", "inline; filename=salida.pdf");
                        case "XSIG" -> //response.setContentType("application/pdf");
                            response.setHeader("Content-disposition", "inline; filename=salida.xsig");
                        case "TIFF", "TIF" -> {
                            response.setContentType("image/tiff");
                            response.setHeader("Content-disposition", "inline; filename=salida.tiff");
                        }
                        case "DOC" -> {
                            response.setContentType("mso-application/Word.Document; charset=UTF-8");
                            response.setHeader("Content-disposition", "inline; filename=salida.xml");
                        }
                        case "XLS" -> {
                            response.setContentType("application/vnd.ms-excel");
                            response.setHeader("Content-disposition", "inline; filename=salida.xls");
                        }
                        case "TXT" -> {
                            response.setContentType("text/html");
                            response.setHeader("Content-disposition", "inline; filename=salida.txt");
                        }
                        case "XML" -> {
                            response.setContentType("text/xml");
                            response.setHeader("Content-disposition", "inline; filename=salida.xml");
                        }
                        case "DOCX" -> {
                            response.setContentType("application/msword");
                            response.setHeader("Content-disposition", "inline; filename=salida.docx");
                        }
                        case "XLSX" -> {
                            response.setContentType("application/vnd.ms-excel");
                            response.setHeader("Content-disposition", "inline; filename=salida.xlsx");
                        }
                        case "JPG" -> {
                            response.setContentType("image/jpeg");
                            response.setHeader("Content-disposition", "inline; filename=salida.jpg");
                        }
                        case "GIF" -> {
                            response.setContentType("image/gif");
                            response.setHeader("Content-disposition", "inline; filename=salida.gif");
                        }
                        case "BMP" -> {
                            response.setContentType("image/bmp");
                            response.setHeader("Content-dispositi   on", "inline; filename=salida.bmp");
                        }
                        case "PNG" -> {
                            response.setContentType("image/bmp");
                            response.setHeader("Content-disposition", "inline; filename=salida.png");
                        }
                        default -> {
                        }
                    }
                    
                    Base64 B64E2 = new Base64();
                    String binDocumentoB64 = B64E2.encodeToString(binDocumento).replace("\n","").replace("\r","");            
                    //======================================================
                    
                    // Preparo las cabeceras de salida del response
                    response.setHeader("Content-disposition", "inline; filename=" + fileName);
                    response.setHeader("Cache-Control", "max-age=30");
                    response.setHeader("Pragma", "No-cache");
                    response.setDateHeader("Expires", 0);
                    response.setHeader("Accept-Ranges", "bytes");
                    response.setContentLength(binDocumentoB64.length());
                    //======================================================

                    // Escribo el ficheor en la salida
                    //======================================================
                    out.write(binDocumentoB64.getBytes());
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
