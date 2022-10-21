package servlets.firmaelectronica;

import afirma.AfirmaUtils;
import afirma.ResultadoValidacionFirmas;
import basedatos.servicios.StADocfirma;
import basedatos.servicios.StAHistdoc;
import basedatos.servicios.StAHistentxml;
import basedatos.servicios.StAHistsalxml;
import basedatos.servicios.StDDocumento;
import basedatos.servicios.StDEntradaxml;
import basedatos.servicios.StDSalidaxml;
import basedatos.servicios.StTSituaciondoc;
import basedatos.servicios.StTSituacionxml;
import basedatos.servicios.StTTipodocumento;
import basedatos.tablas.BdADocfirma;
import basedatos.tablas.BdAHistdoc;
import basedatos.tablas.BdAHistentxml;
import basedatos.tablas.BdAHistsalxml;
import basedatos.tablas.BdDDocumento;
import basedatos.tablas.BdDEntradaxml;
import basedatos.tablas.BdDSalidaxml;
import basedatos.tablas.BdTSituaciondoc;
import basedatos.tablas.BdTSituacionxml;
import basedatos.tablas.BdTTipodocumento;
import excepciones.RegistryNotFoundException;
import gestionDocumentos.firmaDocumentos.FiltroFirmaDocumentos;
import init.AppInit;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXB;
import jax.client.services.interfaces.Cabecera;
import jax.client.services.interfaces.DocumentNotificationRequest;
import jax.client.services.interfaces.Documento;
import jax.client.services.interfaces.NotificationReceiver;
import jax.client.services.interfaces.NotificationReceiverImpl;
import org.apache.log4j.Logger;
import org.apache.tomcat.util.codec.binary.Base64;
import tomcat.persistence.EntityManager;
import utilidades.Session;

/**
 *
 * @author ihuegal
 */
public class SubidaFicheroFirmado extends HttpServlet
{
    private final Logger LOG = Logger.getLogger(SubidaFicheroFirmado.class);
    
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
                //DatosUsuario datosUsuario = (DatosUsuario)Session.getNamedBean("datosUsuario");
                if(filtroFirmaDocumentos == null) { return; } //Error falta la Gestion
                
                BdDDocumento bdDDocumento;

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
                    byte[] binDocumentoFirmado = B64E2.decode((String) request.getParameter("data2"));

                    if(binDocumentoFirmado == null || binDocumentoFirmado.length == 0) { 
                        String msg = "Parámetro entrada/data2 no encontrado (¿el archivo firmado es demasiado grande?)";
                        LOG.error(msg);
                        //gestion.getListaErroresProcesoFirma().add(msg);
                        return;   //Error faltan los datos
                    }
                    
                    // INICIO VALIDAR NIF Y FIRMA
                    AfirmaUtils afirmaUtils = new AfirmaUtils();
                    ResultadoValidacionFirmas resultadoValidacionFirmas = afirmaUtils.validarFirmas(binDocumentoFirmado, true, true, true, true);
                    if (!resultadoValidacionFirmas.isBoValid()) {
                        LOG.error(resultadoValidacionFirmas.getDsValidacion());
                        return; //Error en la validación del documento firmado.
                    }
                    // FIN VALIDAR NIF Y FIRMA
                    
                    // INICIO TRANSACCION
                    try (EntityManager entityManager = AppInit.getEntityManager()) {
                        entityManager.getTransaction().begin();
                        try {
                            //Actualizar la fecha de firma en BD_A_DOCFIRMA
                            StADocfirma stADocfirma = new StADocfirma();
                            Integer idDocfirma = filtroFirmaDocumentos.getDsResultado().getRows().get(idxFichero).getColumnName("ID_DOCFIRMA").getValueInteger();
                            BdADocfirma bdADocfirma = stADocfirma.item(idDocfirma, entityManager);
                            bdADocfirma.setFeFirma(new Date());
                            stADocfirma.actualiza(bdADocfirma, entityManager);
                            
                            //Insertar el documento original (el del ID_DOCUMENTO) en BD_A_HISTDOC
                            StAHistdoc stAHistdoc = new StAHistdoc();
                            BdAHistdoc bdAHistdoc = new BdAHistdoc();
                            bdAHistdoc.setIdDocumento(bdDDocumento.getIdDocumento());
                            bdAHistdoc.setIdSituaciondoc(bdDDocumento.getIdSituaciondoc());
                            bdAHistdoc.setFeAlta(new Date());
                            bdAHistdoc.setFeDesactivo(null);
                            bdAHistdoc.setDsRuta(null);
                            bdAHistdoc.setBlDocumento(bdDDocumento.getBlDocumento(null));
                            stAHistdoc.alta(bdAHistdoc, entityManager);

                            //Actualizar el documento en BD_D_DOCUMENTOS
                            StDDocumento stDDocumento = new StDDocumento();
                            bdDDocumento.setBlDocumento(binDocumentoFirmado); // BL_DOCUMENTO: con el firmado
                            bdDDocumento.setCoExtension("XSIG"); // CO_EXTENSION: "XSIG"
                            bdDDocumento.setCoFichero(bdDDocumento.getCoFichero() + ".xsig"); // CO_FICHERO: el que habia reemplazando la extension a ".xsig"
                            
                            //Mirar si es el ultimo firmante
                            BdADocfirma filtroBdADocfirma = new BdADocfirma();
                            filtroBdADocfirma.setIdDocumento(bdDDocumento.getIdDocumento());
                            ArrayList<BdADocfirma> firmasDocumento = stADocfirma.filtro(filtroBdADocfirma, entityManager);
                            boolean existenFirmasPendientes = false;
                            for (BdADocfirma itemBdADocfirma : firmasDocumento) {
                                if (itemBdADocfirma.getFeFirma() == null) {
                                    existenFirmasPendientes = true;
                                    break;
                                }
                            }
                            
                            // ID_SITUACION: FIRMADO si no hay mas firmantes y PENDIENTE_FIRMA si hay mas firmantes
                            StTSituaciondoc stTSituaciondoc = new StTSituaciondoc();
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
                    
                    //Notificacion por WebService de que el documento se ha firmado por el ultimo firmante.
                    StTTipodocumento stTTipodocumento = new StTTipodocumento();
                    BdTTipodocumento bdTTipodocumento = stTTipodocumento.item(bdDDocumento.getIdTipodocumento(), null);

                    StTSituaciondoc stTSituaciondoc = new StTSituaciondoc();
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

                        StDSalidaxml stDSalidaxml = new StDSalidaxml();
                        Integer idSalidaXML = stDSalidaxml.grabarSalidaXML(xmlString, bdDDocumento.getIdDocumento(), null);

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
                    
                    //FALTA NOTIFICACION POR CORREO
                    //FIN NOTIFICACION POR CORREO
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

    private static String documentNotification(jax.client.services.interfaces.DocumentNotificationRequest documentNotificationRequest) {
        jax.client.services.interfaces.NotificationReceiver service = new jax.client.services.interfaces.NotificationReceiver();
        jax.client.services.interfaces.NotificationReceiverImpl port = service.getNotificationReceiverImplPort();
        return port.documentNotification(documentNotificationRequest);
    }
}