package servlets.firmaelectronica;

import basedatos.servicios.StTSituaciondoc;
import basedatos.tablas.BdDDocumento;
import basedatos.tablas.BdTSituaciondoc;
import gestionDocumentos.firmaDocumentos.FiltroFirmaDocumentos;
import java.io.IOException;
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
                    if(sIndexFicherofichero == null || sIndexFicherofichero.length() == 0) return; //Error falta parámetro
                    Integer idxFichero = Integer.parseInt(sIndexFicherofichero);
                    
                    //Obtener la gestión, donde están los datos de los documentos firmados
                    FiltroFirmaDocumentos filtroFirmaDocumentos = (FiltroFirmaDocumentos)Session.getNamedBean("filtroFirmaDocumentos");
                    DatosUsuario datosUsuario = (DatosUsuario)Session.getNamedBean("datosUsuario");
                    if(filtroFirmaDocumentos == null) { return; } //Error falta la Gestion

                    //Obtener el documento que hemos firmados
                    BdDDocumento bdDDocumento = filtroFirmaDocumentos.getDocumentoByIndex(idxFichero);
                    if(bdDDocumento == null) { return; } //Error falta el documento

                    //Traza DEBUG
                    LOG.debug(String.format("Portafirmas: El usuario '%s' baja para firmar el documento con ID_DOCUMENTO = %d", datosUsuario.getBdTUsuario().getCoUsuario(), bdDDocumento.getIdDocumento()));
                    
                    //FacesContext contexto = m_facesContextFactory.getFacesContext(getServletContext(), request, response, m_lifecycle);
                    
//                    UtilPSSDEF util = new UtilPSSDEF();
//                    
//                    StEscaner stScanner = new StEscaner(null, contexto);
//                    String id = doc.getID_DOCEXPTE().toString();
                    String extension = bdDDocumento.getCoExtension().toUpperCase();
                    String fileName = bdDDocumento.getCoFichero();
                    byte[] binDocumento = bdDDocumento.getBlDocumento();
                    
                    if(binDocumento == null || binDocumento.length == 0)
                    {
                        //gestion.getListaErroresProcesoFirma().add(doc.getDESCRIPCION() + " - No existe el documento, consulte con soporte");
                        binDocumento = new byte[0];
                    }
                    else
                    {
                        boolean validarNIF = true;//parsePropiedad("PORTAFIRMAS_VALIDARNIF");
                        boolean validarFirma = true;//parsePropiedad("PORTAFIRMAS_VALIDARFIRMA");

                        if(validarNIF || validarFirma)
                        {
                            StTSituaciondoc stTSituaciondoc = new StTSituaciondoc();
                            BdTSituaciondoc bdTSituaciondoc = stTSituaciondoc.item(bdDDocumento.getIdSituaciondoc(), null);
                            if(bdTSituaciondoc != null && bdTSituaciondoc.getCoSituaciondoc() != null 
                                    && bdTSituaciondoc.getCoSituaciondoc().equalsIgnoreCase("FIRMADO"))
                            {
                                //Si el documento está firmado, se puede validar que realmente sea un xml
//                                if(!UtilPSSDEF.validarQueEsXMLFirmado(documento))
//                                {
//                                    gestion.getListaErroresProcesoFirma().add(doc.getDESCRIPCION() + " - El documento está firmado, pero no parece ser un xml de firma");
//                                    documento = new byte[0];
//                                }

                                // AÑADIR PSSDEF Y CUANDO  SE VEA QUE NO DEVUELVE FIRMAS, MIRAR
                                // SI LA AUTORIDAD ACTUAL ESTÁ EN LA LISTA DE PERMITIDOS SIN FIRMAS ANTERIORES Y "ret=true;"
//                                ArrayList<FirmaBean> listaFirmas = null;
//                                try {
//                                    listaFirmas = util.verificaXadesByte(documento);
//                                } catch (Exception ex) {
//                                    //Si falla la PSSDEF, la lista se quedará vacía y mostrará un error
//                                    gestion.getListaErroresProcesoFirma().add(doc.getDESCRIPCION() + " - Problema con el servicio PSSDEF: " + ex.getMessage());
//                                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
//                                }
//
//                                if (listaFirmas == null || listaFirmas.isEmpty())
//                                {
//                                    //Si no encuentro firmas miro si está en la lista de permitidos la autoridad actual.
//                                    boolean boAutorizadoExpecial = gestion.getBoAutoridadEspecial();
//                                    //Solo realizo la validacion especial de autoridades si el documento firmado viene de IRIS.
//                                    if(!UtilPSSDEF.validarQueEsXMLFirmado(documento))
//                                        boAutorizadoExpecial = false;
//
//                                    if (!boAutorizadoExpecial)
//                                    {
//                                        gestion.getListaErroresProcesoFirma().add(doc.getDESCRIPCION() + " - El documento está firmado, pero no se pueden recuperar sus firmas");
//                                        documento = new byte[0];
//                                    }
//                                }
                            }
                        }
                    }

                    if (extension.equals("PDF"))
                    {
                        response.setContentType("application/pdf");
                        response.setHeader("Content-disposition", "inline; filename=salida.pdf");
                    }
                    else if (extension.equals("TIFF") || extension.equals("TIF"))
                    {
                        response.setContentType("image/tiff");
                        response.setHeader("Content-disposition", "inline; filename=salida.tiff");
                    }
                    else if (extension.equals("DOC"))
                    {
                        response.setContentType("mso-application/Word.Document; charset=UTF-8");
                        response.setHeader("Content-disposition", "inline; filename=salida.xml");
                    }
                    else if (extension.equals("XLS"))
                    {
                        response.setContentType("application/vnd.ms-excel");
                        response.setHeader("Content-disposition", "inline; filename=salida.xls");
                    }
                    else if (extension.equals("TXT"))
                    {
                        response.setContentType("text/html");
                        response.setHeader("Content-disposition", "inline; filename=salida.txt");
                    }
                    else if (extension.equals("XML"))
                    {
                        response.setContentType("text/xml");
                        response.setHeader("Content-disposition", "inline; filename=salida.xml");
                    }
                    else if (extension.equals("DOCX"))
                    {
                        response.setContentType("application/msword");
                        response.setHeader("Content-disposition", "inline; filename=salida.docx");
                    }
                    else if (extension.equals("XLSX"))
                    {
                        response.setContentType("application/vnd.ms-excel");
                        response.setHeader("Content-disposition", "inline; filename=salida.xlsx");
                    }
                    else if (extension.equals("JPG"))
                    {
                        response.setContentType("image/jpeg");
                        response.setHeader("Content-disposition", "inline; filename=salida.jpg");
                    }
                    else if (extension.equals("GIF"))
                    {
                        response.setContentType("image/gif");
                        response.setHeader("Content-disposition", "inline; filename=salida.gif");
                    }
                    else if (extension.equals("BMP"))
                    {
                        response.setContentType("image/bmp");
                        response.setHeader("Content-dispositi   on", "inline; filename=salida.bmp");
                    }
                    else if (extension.equals("PNG"))
                    {
                        response.setContentType("image/bmp");
                        response.setHeader("Content-disposition", "inline; filename=salida.png");
                    }
                    
                    Base64 B64E2 = new Base64();
                    String binDocumentoB64 = "";
                    binDocumentoB64=B64E2.encodeToString(binDocumento).replace("\n","").replace("\r","");            
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
