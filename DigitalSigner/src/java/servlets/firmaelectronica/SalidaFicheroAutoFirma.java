package servlets.firmaelectronica;

import afirma.AfirmaUtils;
import afirma.ResultadoValidacionFirmas;
import basedatos.servicios.StAAutusu;
import basedatos.servicios.StADocfirma;
import basedatos.servicios.StAHistdoc;
import basedatos.servicios.StDDocumento;
import basedatos.servicios.StDSalidaxml;
import basedatos.servicios.StTAutoridad;
import basedatos.servicios.StTSituaciondoc;
import basedatos.servicios.StTTipodocumento;
import basedatos.servicios.StTUsuario;
import basedatos.tablas.BdAAutusu;
import basedatos.tablas.BdADocfirma;
import basedatos.tablas.BdAHistdoc;
import basedatos.tablas.BdDDocumento;
import basedatos.tablas.BdTAutoridad;
import basedatos.tablas.BdTSituaciondoc;
import basedatos.tablas.BdTTipodocumento;
import basedatos.tablas.BdTUsuario;
import gestionDocumentos.firmaDocumentos.FiltroFirmaDocumentos;
import init.AppInit;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import javax.faces.FacesException;
import javax.faces.FactoryFinder;
import javax.faces.context.FacesContextFactory;
import javax.faces.lifecycle.Lifecycle;
import javax.faces.lifecycle.LifecycleFactory;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXB;
import jax.client.services.interfaces.documentnotification.Cabecera;
import jax.client.services.interfaces.documentnotification.DocumentNotificationRequest;
import jax.client.services.interfaces.documentnotification.Documento;
import jax.client.services.interfaces.mailservice.Destinatarios;
import jax.ws.services.interfaces.DocumentServiceImpl;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.tomcat.util.codec.binary.Base64;
import seguridad.usuarios.DatosUsuario;
import tomcat.persistence.EntityManager;
import utilidades.Configuraciones;
import utilidades.Correo;
import utilidades.Session;

/**
 *
 * @author ihuegal
 */
public class SalidaFicheroAutoFirma extends HttpServlet 
{
    private final Logger LOG = Logger.getLogger(SalidaFicheroAutoFirma.class);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    {
        FiltroFirmaDocumentos filtroFirmaDocumentos = null;
        try
        {
            // Dejamos solicitar documentos solamente si está logado
//            if (((String) request.getSession().getAttribute("iniciado")).equals("true")) 
            {
                PrintWriter out=null;
                
                //Obtener la gestión, donde están los datos de los documentos firmados
                filtroFirmaDocumentos = (FiltroFirmaDocumentos) Session.getNamedBean("filtroFirmaDocumentos");
                DatosUsuario datosUsuario = (DatosUsuario)Session.getNamedBean("datosUsuario");
                if(filtroFirmaDocumentos == null) { 
                    String msg = "Bean filtroFirmaDocumentos no encontrado.";
                    LOG.error(msg);
                    throw new Exception("- " + msg);
                }                
                
                BdDDocumento bdDDocumento;

                try 
                {
                    out = response.getWriter();
                    request.setCharacterEncoding("UTF-8");
                    
                    //Obtener con qué fichero estamos trabajando (su index)
                    String sIndexFicherofichero = (String) request.getParameter("indexFichero");
                    if(sIndexFicherofichero == null || sIndexFicherofichero.length() == 0) {
                        String msg = "Parámetro fichero no encontrado (¿el archivo firmado es demasiado grande?)";
                        LOG.error(msg);
                        throw new Exception("- " + "indexFichero: " + msg);
                    }
                    Integer idxFichero = Integer.parseInt(sIndexFicherofichero);
                    
                    //Obtener el documento que hemos firmados
                    bdDDocumento = filtroFirmaDocumentos.getDocumentoByIndex(idxFichero);
                    if(bdDDocumento == null) {
                        String msg = "Parámetro documento no encontrado";
                        LOG.error(msg);
                        throw new Exception("- " + "idxFichero -> " + idxFichero + ": " + msg);
                    } 
                    
                    //Obtener los datos que vamos a guardar (el documento firmado)
                    Base64 B64E2 = new Base64();
                    byte[] idDocumentoFirmado = B64E2.decode((String) request.getParameter("data2"));

                    if(idDocumentoFirmado == null || idDocumentoFirmado.length == 0) { 
                        String msg = "Parámetro entrada/data2 no encontrado (¿el archivo firmado es demasiado grande?)";
                        LOG.error(msg);
                        throw new Exception("- " + bdDDocumento.getCoDocumento() + ": " + msg);
                    }
                    
                    // Recupero de "temp/salida" el fichero firmado
                    //============================================================
                    byte[] binDocumentoFirmado = Files.readAllBytes(Paths.get(AppInit.getRutaTempFicherosFirmados(), "salida", new String(idDocumentoFirmado, "UTF-8")));

                    boolean validarFirma = Boolean.valueOf(new Configuraciones(Session.getDatosUsuario()).recuperaConfiguracion("VALIDARFIRMA"));

                    if (validarFirma) {
                        // INICIO VALIDAR NIF Y FIRMA
                        AfirmaUtils afirmaUtils = new AfirmaUtils();
                        ResultadoValidacionFirmas resultadoValidacionFirmas = afirmaUtils.validarFirmas(binDocumentoFirmado, true, true, true, true);
                        if (!resultadoValidacionFirmas.isBoValid()) {
                            //"Firma del documento no válida."
                            String msg = resultadoValidacionFirmas.getDsValidacion();
                            LOG.error(msg);
                            throw new Exception("- " + bdDDocumento.getCoDocumento() + ": " + msg);
                        }
                        // FIN VALIDAR NIF Y FIRMA
                    }
                    
                    boolean existenFirmasPendientes = false;
                    BdADocfirma itemBdADocfirmaSiguiente = null;
                    
                    // INICIO TRANSACCION
                    try (EntityManager entityManager = AppInit.getEntityManager()) {
                        entityManager.getTransaction().begin();
                        try {
                            //Actualizar la fecha de firma en BD_A_DOCFIRMA
                            StADocfirma stADocfirma = new StADocfirma(Session.getDatosUsuario());
                            Integer idDocfirma = filtroFirmaDocumentos.getDsResultado().getRows().get(idxFichero).getColumnName("ID_DOCFIRMA").getValueInteger();
                            BdADocfirma bdADocfirma = stADocfirma.item(idDocfirma, entityManager);
                            bdADocfirma.setFeFirma(new Date());
                            stADocfirma.actualiza(bdADocfirma, entityManager);
                            
                            //Insertar el documento original (el del ID_DOCUMENTO) en BD_A_HISTDOC
                            StAHistdoc stAHistdoc = new StAHistdoc(Session.getDatosUsuario());
                            BdAHistdoc bdAHistdoc = new BdAHistdoc();
                            bdAHistdoc.setIdDocumento(bdDDocumento.getIdDocumento());
                            bdAHistdoc.setIdSituaciondoc(bdDDocumento.getIdSituaciondoc());
                            bdAHistdoc.setFeAlta(new Date());
                            bdAHistdoc.setFeDesactivo(null);
                            bdAHistdoc.setDsRuta(null);
                            bdAHistdoc.setBlDocumento(bdDDocumento.getBlDocumento(null));
                            stAHistdoc.alta(bdAHistdoc, entityManager);

                            //Actualizar el documento en BD_D_DOCUMENTOS
                            StDDocumento stDDocumento = new StDDocumento(Session.getDatosUsuario());
                            bdDDocumento.setBlDocumento(binDocumentoFirmado); // BL_DOCUMENTO: con el firmado
                            bdDDocumento.setCoExtension("XSIG"); // CO_EXTENSION: "XSIG"
                            bdDDocumento.setCoFichero(bdDDocumento.getCoFichero() + ".xsig"); // CO_FICHERO: el que habia reemplazando la extension a ".xsig"
                            
                            //Mirar si es el ultimo firmante
                            BdADocfirma filtroBdADocfirma = new BdADocfirma();
                            filtroBdADocfirma.setIdDocumento(bdDDocumento.getIdDocumento());
                            ArrayList<BdADocfirma> firmasDocumento = stADocfirma.filtro(filtroBdADocfirma, entityManager);
                            for (BdADocfirma itemBdADocfirma : firmasDocumento) {
                                if (itemBdADocfirma.getFeFirma() == null) {
                                    existenFirmasPendientes = true;
                                    itemBdADocfirmaSiguiente = itemBdADocfirma;
                                    break;
                                }
                            }
                            
                            // ID_SITUACION: FIRMADO si no hay mas firmantes y PENDIENTE_FIRMA si hay mas firmantes
                            StTSituaciondoc stTSituaciondoc = new StTSituaciondoc(Session.getDatosUsuario());
                            BdTSituaciondoc filtroBdTSituaciondoc = new BdTSituaciondoc();
                            if (existenFirmasPendientes) {
                                filtroBdTSituaciondoc.setCoSituaciondoc("PENDIENTE_FIRMA");
                            }
                            else {
                                filtroBdTSituaciondoc.setCoSituaciondoc("FIRMADO");                                
                            }
                            Integer idSituaciondoc = stTSituaciondoc.filtro(filtroBdTSituaciondoc, entityManager).get(0).getIdSituaciondoc();
                            bdDDocumento.setIdSituaciondoc(idSituaciondoc);
                            stDDocumento.actualiza(bdDDocumento, entityManager);
                            
                            entityManager.getTransaction().commit();
                        }
                        catch (Exception ex) {
                            entityManager.getTransaction().rollback();
                            throw ex;
                        }
                    }
                    // FIN TRANSACCION
                    
                    StTTipodocumento stTTipodocumento = new StTTipodocumento(Session.getDatosUsuario());
                    BdTTipodocumento bdTTipodocumento = stTTipodocumento.item(bdDDocumento.getIdTipodocumento(), null);

                    StTSituaciondoc stTSituaciondoc = new StTSituaciondoc(Session.getDatosUsuario());
                    BdTSituaciondoc bdTSituaciondoc = stTSituaciondoc.item(bdDDocumento.getIdSituaciondoc(), null);

                    if (bdTSituaciondoc.getCoSituaciondoc().equalsIgnoreCase("FIRMADO")) {
                        DocumentNotificationRequest documentNotificationRequest = new DocumentNotificationRequest();
                        documentNotificationRequest.setCabecera(new Cabecera());
                        documentNotificationRequest.getCabecera().setCoUnidad(Session.getDatosUsuario().getBdTUnidad().getCoUnidad());
                        documentNotificationRequest.setDocumento(new Documento());
                        documentNotificationRequest.getDocumento().setCoSituacionDoc(bdTSituaciondoc.getCoSituaciondoc());
                        documentNotificationRequest.getDocumento().setCoTipoDocumento(bdTTipodocumento.getCoTipodocumento());
                        documentNotificationRequest.getDocumento().setCoFichero(bdDDocumento.getCoFichero());
                        documentNotificationRequest.getDocumento().setCoExtension(bdDDocumento.getCoExtension());
                        documentNotificationRequest.getDocumento().setBlDocumento(bdDDocumento.getBlDocumento(null));
                        documentNotificationRequest.getDocumento().setDsDocumento(bdDDocumento.getDsDocumento());
                        documentNotificationRequest.getDocumento().setDsObservaciones(null);

                        // DAR DE ALTA EL XML DE SALIRA POR SI FALLA PODER REPROCESAR LA SALIDA
                        StringWriter sw = new StringWriter();
                        JAXB.marshal(documentNotificationRequest, sw);
                        String xmlString = sw.toString();

                        StDSalidaxml stDSalidaxml = new StDSalidaxml(Session.getDatosUsuario());
                        Integer idSalidaXML = stDSalidaxml.grabarSalidaXML(xmlString, bdDDocumento.getIdDocumento(), null);

                        //Notificacion por WebService de que el documento se ha firmado por el ultimo firmante.
                        boolean boNotificarWebService = Boolean.valueOf(new Configuraciones(Session.getDatosUsuario()).recuperaConfiguracion("NOTIFICAWS"));
                        if (boNotificarWebService) {

                            String resultado = documentNotification(documentNotificationRequest);

                            if (resultado.equalsIgnoreCase("OK")) {
                                stDSalidaxml.actualizarSalidaXML(idSalidaXML, bdDDocumento.getIdDocumento(), "PROCESADO");
                            }
                            else {
                                //Error en la comunicación
                                LOG.error(resultado);
                                stDSalidaxml.actualizarSalidaXML(idSalidaXML, bdDDocumento.getIdDocumento(), "ERROR");
                            }
                        }
                        //Fin Notificacion por WebService
                        
                        //Notificacion por fichero XML en carpeta local/NFS
                        boolean boHiloGestorXML = Boolean.valueOf(new Configuraciones(Session.getDatosUsuario()).recuperaConfiguracion("HILOGESTORXML"));
                        if (boHiloGestorXML) {
                            try {
                                String sHiloGestorXML_Path = new Configuraciones(Session.getDatosUsuario()).recuperaConfiguracion("HILOGESTORXML_PATH");
                                if (sHiloGestorXML_Path == null || sHiloGestorXML_Path.isBlank()) {
                                    LOG.error("No se ha establecido el path de busqueda de XML, 'HILOGESTORXML_PATH'.");
                                    stDSalidaxml.actualizarSalidaXML(idSalidaXML, bdDDocumento.getIdDocumento(), "ERROR");
                                    return;
                                }

                                FileUtils.writeStringToFile(Paths.get(sHiloGestorXML_Path, "SALIDA", "SALIDA_" + idSalidaXML + ".xml").toFile(), xmlString, StandardCharsets.UTF_8);

                                stDSalidaxml.actualizarSalidaXML(idSalidaXML, bdDDocumento.getIdDocumento(), "PROCESADO");
                            }
                            catch (Exception ex) {
                                LOG.error(ex.getMessage(), ex);
                                stDSalidaxml.actualizarSalidaXML(idSalidaXML, bdDDocumento.getIdDocumento(), "ERROR");
                            }
                        }
                        //Fin Notificacion por fichero XML en carpeta local/NFS
                    }
                    
                    
                    //NOTIFICACION POR CORREO
                    boolean enviarCorreoFirmante = Boolean.valueOf(new Configuraciones(Session.getDatosUsuario()).recuperaConfiguracion("ENVIOCORREOFIRMANTE"));
                    if (enviarCorreoFirmante && existenFirmasPendientes && itemBdADocfirmaSiguiente != null) {
                        String dsAutoridad = null;
                        ArrayList<Destinatarios> listaDestinatarios = new ArrayList<>();
                        
                        Integer idAutoridad = itemBdADocfirmaSiguiente.getIdAutoridad();
                        StTAutoridad stTAutoridad = new StTAutoridad(Session.getDatosUsuario());
                        BdTAutoridad itemBdTAutoridad = stTAutoridad.item(idAutoridad, null);
                        if (itemBdTAutoridad != null) {
                            dsAutoridad = itemBdTAutoridad.getCoAutoridad() + " - " + itemBdTAutoridad.getDsAutoridad();

                            StAAutusu stAAutusu = new StAAutusu(Session.getDatosUsuario());
                            BdAAutusu filtroBdAAutusu = new BdAAutusu();
                            filtroBdAAutusu.setIdAutoridad(idAutoridad);
                            ArrayList<BdAAutusu> listaBdAAutusu = stAAutusu.filtro(filtroBdAAutusu, null);
                            if (listaBdAAutusu != null && !listaBdAAutusu.isEmpty()) {
                                for (BdAAutusu itemBdAAutusu : listaBdAAutusu) {
                                    BdTUsuario bdTUsuario = new StTUsuario(Session.getDatosUsuario()).item(itemBdAAutusu.getIdUsuario(), false, null);
                                    Destinatarios destinatario = new Destinatarios();
                                    // TODO: Añadir campo email a los usuarios.
                                    destinatario.setEmailAddress("fulgore1983@gmail.com"/*bdTUsuario.getEmail()*/);
                                    /**
                                     * Tipo de receptor:
                                     * - TO: Receptor normal (PARA)
                                     * - CC: Receptor en copia (CC)
                                     * - BCC: Receptor en copia oculta (CCO)
                                     */
                                    destinatario.setRecipientType("TO");
                                    listaDestinatarios.add(destinatario);
                                }
                            }
                        }

                        boolean resultadoEmail = Correo.enviar("La autoridad " + dsAutoridad + ", tiene documentos pendientes de firma.", 
                                                            "DigitalSigner: documentos pendientes de firma.", null, "admin@digitalsigner.com", 
                                                            listaDestinatarios);
                        if (!resultadoEmail) {
                            Logger.getLogger(DocumentServiceImpl.class).error("No se ha podido notificar a los firmantes por correo.");
                        }
                    }
                    //FIN NOTIFICACION POR CORREO
                    
                    //borro los temporales
                    String ipAddress = request.getHeader("X-FORWARDED-FOR");
                    if (ipAddress == null) {
                        ipAddress = request.getRemoteAddr();
                    }
                    String idDocumentoOriginal = datosUsuario.getBdTUsuario().getCoUsuario() + "_" + ipAddress.replace(".", "_").replace(":", "_") + "_" + bdDDocumento.getIdDocumento();
                    try
                    {
                        Files.deleteIfExists(Paths.get(AppInit.getRutaTempFicherosFirmados(), "entrada", idDocumentoOriginal));
                        Files.deleteIfExists(Paths.get(AppInit.getRutaTempFicherosFirmados(), "salida", new String(idDocumentoFirmado, "UTF-8")));
                    }
                    catch (IOException ex)
                    {
                        LOG.error(ex.getMessage(), ex);
                        // NADA, QUE LO HAGA EL PROCESO DESATENDIDO
                    }
                    
                    //Files.write(Paths.get("C:\\pruebas\\firmados", idDocumentoOriginal), binDocumentoFirmado);
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
    
    private static String documentNotification(jax.client.services.interfaces.documentnotification.DocumentNotificationRequest documentNotificationRequest) {
        jax.client.services.interfaces.documentnotification.NotificationReceiver service = new jax.client.services.interfaces.documentnotification.NotificationReceiver();
        jax.client.services.interfaces.documentnotification.NotificationReceiverImpl port = service.getNotificationReceiverImplPort();
        return port.documentNotification(documentNotificationRequest);
    }
}