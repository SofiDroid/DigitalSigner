package servlets.firmaelectronica;

import basedatos.servicios.StADocfirma;
import basedatos.servicios.StAHistdoc;
import basedatos.servicios.StDDocumento;
import basedatos.servicios.StTSituaciondoc;
import basedatos.tablas.BdADocfirma;
import basedatos.tablas.BdAHistdoc;
import basedatos.tablas.BdDDocumento;
import basedatos.tablas.BdTSituaciondoc;
import gestionDocumentos.firmaDocumentos.FiltroFirmaDocumentos;
import init.AppInit;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
                    //
                    //
                    //
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
                                if (itemBdADocfirma.getFeFirma() != null) {
                                    existenFirmasPendientes = true;
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