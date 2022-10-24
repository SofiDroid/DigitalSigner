package jax.ws.services.interfaces;

import basedatos.servicios.StAAutusu;
import basedatos.servicios.StAConftipodoc;
import basedatos.servicios.StADocextra;
import basedatos.servicios.StADocfirma;
import basedatos.servicios.StADocobs;
import basedatos.servicios.StATokenusuario;
import basedatos.servicios.StAUniusu;
import basedatos.servicios.StDDocumento;
import basedatos.servicios.StDEntradaxml;
import basedatos.servicios.StTAutoridad;
import basedatos.servicios.StTSituaciondoc;
import basedatos.servicios.StTTipodocumento;
import basedatos.servicios.StTUnidad;
import basedatos.servicios.StTUsuario;
import basedatos.tablas.BdAAutusu;
import basedatos.tablas.BdAConftipodoc;
import basedatos.tablas.BdADocextra;
import basedatos.tablas.BdADocfirma;
import basedatos.tablas.BdADocobs;
import basedatos.tablas.BdATokenusuario;
import basedatos.tablas.BdAUniusu;
import basedatos.tablas.BdDDocumento;
import basedatos.tablas.BdTAutoridad;
import basedatos.tablas.BdTSituaciondoc;
import basedatos.tablas.BdTTipodocumento;
import basedatos.tablas.BdTUnidad;
import basedatos.tablas.BdTUsuario;
import excepciones.InvalidTokenException;
import excepciones.InvalidUnitException;
import excepciones.RegistryNotFoundException;
import excepciones.RequiredFieldException;
import init.AppInit;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import javax.jws.*;
import javax.xml.bind.JAXB;
import javax.xml.bind.annotation.XmlElement;
import jax.client.services.interfaces.mailservice.Destinatarios;
import jax.ws.services.types.AcuseReciboDocumentResponse;
import jax.ws.services.types.DocumentRequest;
import jax.ws.services.types.clases.Cabecera;
import jax.ws.services.types.clases.Documento;
import jax.ws.services.types.clases.Extra;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import seguridad.usuarios.DatosUsuario;
import tomcat.persistence.EntityManager;
import utilidades.Configuraciones;
import utilidades.Correo;

/**
 *
 * @author ihuegal
 */
@WebService(serviceName = "DocumentService")
public class DocumentServiceImpl {
    
    private DatosUsuario datosUsuario = null;
    /**
     * Web service operation
     * @param documentRequest
     * @return 
     */
    @WebMethod(operationName = "sendDocument")
    @XmlElement(required = true)
    public AcuseReciboDocumentResponse sendDocument(@XmlElement(required = true) @WebParam(name = "documentRequest") DocumentRequest documentRequest) {
        AcuseReciboDocumentResponse acuseReciboDocumentResponse = new AcuseReciboDocumentResponse();
        
        try {
            acuseReciboDocumentResponse = procesar(documentRequest);
        }
        catch (Exception ex) {
            Logger.getLogger(DocumentServiceImpl.class).error(ex.getMessage(), ex);
            acuseReciboDocumentResponse.getError().setErrorMessage(ex.getMessage());
        }
        
        return acuseReciboDocumentResponse;
    }
    
    public AcuseReciboDocumentResponse procesar(DocumentRequest documentRequest) throws Exception {
        AcuseReciboDocumentResponse acuseReciboDocumentResponse = new AcuseReciboDocumentResponse();

        validarCampos(documentRequest);
            
        validarToken(documentRequest.getCabecera());

        StringWriter sw = new StringWriter();
        JAXB.marshal(documentRequest, sw);
        String xmlString = sw.toString();

        StDEntradaxml stDEntradaxml = new StDEntradaxml(datosUsuario);
        Integer idEntradaXML = stDEntradaxml.grabarEntradaXML(xmlString);
        String md5Hex = DigestUtils.md5Hex(xmlString).toUpperCase();

        acuseReciboDocumentResponse.getAcuseReciboDocument().setIdEntradaXML(idEntradaXML);
        acuseReciboDocumentResponse.getAcuseReciboDocument().setHash(md5Hex);

        // INICIO TRANSACCION
        Integer idDocumento = null;
        try (EntityManager entityManager = AppInit.getEntityManager())
        {
            entityManager.getTransaction().begin();
            try {
                idDocumento = grabarDocumento(documentRequest.getDocumento(), entityManager);

                grabarExtras(documentRequest.getExtras(), idDocumento, entityManager);

                entityManager.getTransaction().commit();
            }
            catch (Exception ex) {
                entityManager.getTransaction().rollback();
                throw ex;
            }
        }
        catch (Exception ex) {
            Logger.getLogger(DocumentServiceImpl.class).error(ex.getMessage(), ex);
            stDEntradaxml.actualizarEntradaXML(idEntradaXML, idDocumento, "ERROR");
            throw ex;
        }
        // FIN TRANSACCION
        stDEntradaxml.actualizarEntradaXML(idEntradaXML, idDocumento, "PROCESADO");

        boolean enviarCorreoFirmante = Boolean.valueOf(new Configuraciones(datosUsuario).recuperaConfiguracion("ENVIOCORREOFIRMANTE"));
        if (enviarCorreoFirmante) {
            String dsAutoridad = null;
            ArrayList<Destinatarios> listaDestinatarios = new ArrayList<>();
            BdADocfirma filtroBdADocfirma = new BdADocfirma();
            filtroBdADocfirma.setIdDocumento(idDocumento);
            filtroBdADocfirma.setEnOrden(1);
            StADocfirma stADocfirma = new StADocfirma(datosUsuario);
            ArrayList<BdADocfirma> listaADocfirma = stADocfirma.filtro(filtroBdADocfirma, null);
            if (listaADocfirma != null && !listaADocfirma.isEmpty()) {
                Integer idAutoridad = listaADocfirma.get(0).getIdAutoridad();
                StTAutoridad stTAutoridad = new StTAutoridad(datosUsuario);
                BdTAutoridad itemBdTAutoridad = stTAutoridad.item(idAutoridad, null);
                if (itemBdTAutoridad != null) {
                    dsAutoridad = itemBdTAutoridad.getCoAutoridad() + " - " + itemBdTAutoridad.getDsAutoridad();

                    StAAutusu stAAutusu = new StAAutusu(datosUsuario);
                    BdAAutusu filtroBdAAutusu = new BdAAutusu();
                    filtroBdAAutusu.setIdAutoridad(idAutoridad);
                    ArrayList<BdAAutusu> listaBdAAutusu = stAAutusu.filtro(filtroBdAAutusu, null);
                    if (listaBdAAutusu != null && !listaBdAAutusu.isEmpty()) {
                        for (BdAAutusu itemBdAAutusu : listaBdAAutusu) {
                            BdTUsuario bdTUsuario = new StTUsuario(datosUsuario).item(itemBdAAutusu.getIdUsuario(), false, null);
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
            }

            boolean resultadoEmail = Correo.enviar("La autoridad " + dsAutoridad + ", tiene documentos pendientes de firma.", 
                                                "DigitalSigner: documentos pendientes de firma.", null, "admin@digitalsigner.com", 
                                                listaDestinatarios);
            if (!resultadoEmail) {
                Logger.getLogger(DocumentServiceImpl.class).error("No se ha podido notificar a los firmantes por correo.");
            }
        }
        
        return acuseReciboDocumentResponse;
    }
    
    private void validarToken(Cabecera cabecera) throws Exception {
        
        String tokenUsuario = cabecera.getTokenUsuario();
        String coUnidad = cabecera.getCoUnidad();
        
        // Validar el token y obtener el usuario asociado
        BdATokenusuario filtroBdATokenusuario = new BdATokenusuario();
        filtroBdATokenusuario.setDsToken(tokenUsuario);
        filtroBdATokenusuario.setFeAlta(new Date());
        filtroBdATokenusuario.setFeDesactivo(new Date());
        StATokenusuario stATokenusuario = new StATokenusuario(this.datosUsuario);
        ArrayList<BdATokenusuario> listaBdATokenusuario = stATokenusuario.filtro(filtroBdATokenusuario, null);
        if (listaBdATokenusuario == null || listaBdATokenusuario.isEmpty()) {
            throw new InvalidTokenException();
        }
        BdTUsuario itemBdTUsuario = new StTUsuario(this.datosUsuario).item(listaBdATokenusuario.get(0).getIdUsuario(), true, null);
        if (itemBdTUsuario == null) {
            throw new InvalidTokenException();
        }
        
        // Validar y obtener la unidad del usuario
        BdTUnidad filtroBdTUnidad = new BdTUnidad();
        filtroBdTUnidad.setCoUnidad(coUnidad);
        filtroBdTUnidad.setFeAlta(new Date());
        filtroBdTUnidad.setFeDesactivo(new Date());
        StTUnidad stTUnidad = new StTUnidad(datosUsuario);
        ArrayList<BdTUnidad> listaBdTUnidad = stTUnidad.filtro(filtroBdTUnidad, null);
        if (listaBdTUnidad == null || listaBdTUnidad.isEmpty()) {
            throw new InvalidUnitException();
        }
        BdAUniusu filtroBdAUniusu = new BdAUniusu();
        filtroBdAUniusu.setIdUsuario(itemBdTUsuario.getIdUsuario());
        filtroBdAUniusu.setIdUnidad(listaBdTUnidad.get(0).getIdUnidad());
        filtroBdAUniusu.setFeAlta(new Date());
        filtroBdAUniusu.setFeDesactivo(new Date());
        StAUniusu stAUniusu = new StAUniusu(datosUsuario);
        ArrayList<BdAUniusu> listaBdAUniusu = stAUniusu.filtro(filtroBdAUniusu, null);
        if (listaBdAUniusu == null || listaBdAUniusu.isEmpty()) {
            throw new InvalidUnitException();
        }
        
        datosUsuario = new DatosUsuario();
        datosUsuario.setBdTUsuario(itemBdTUsuario);
        datosUsuario.setBdTUnidad(listaBdTUnidad.get(0));
    }

    private Integer grabarDocumento(Documento documento, EntityManager entityManager) throws Exception {
        BdTTipodocumento filtroBdTTipodocumento = new BdTTipodocumento();
        filtroBdTTipodocumento.setCoTipodocumento(documento.getCoTipoDocumento());
        filtroBdTTipodocumento.setFeAlta(new Date());
        filtroBdTTipodocumento.setFeDesactivo(new Date());
        StTTipodocumento stTTipodocumento = new StTTipodocumento(datosUsuario);
        ArrayList<BdTTipodocumento> listaBdTTipodocumento = stTTipodocumento.filtro(filtroBdTTipodocumento, entityManager);
        if (listaBdTTipodocumento == null || listaBdTTipodocumento.isEmpty()) {
            throw new RegistryNotFoundException();
        }
                
        BdTSituaciondoc filtroBdTSituaciondoc = new BdTSituaciondoc();
        filtroBdTSituaciondoc.setCoSituaciondoc("NUEVO");
        filtroBdTSituaciondoc.setFeAlta(new Date());
        filtroBdTSituaciondoc.setFeDesactivo(new Date());
        StTSituaciondoc stTSituaciondoc = new StTSituaciondoc(datosUsuario);
        ArrayList<BdTSituaciondoc> listaBdTSituaciondoc = stTSituaciondoc.filtro(filtroBdTSituaciondoc, entityManager);
        if (listaBdTSituaciondoc == null || listaBdTSituaciondoc.isEmpty()) {
            throw new RegistryNotFoundException();
        }
        
        BdDDocumento bdDDocumento = new BdDDocumento();
        bdDDocumento.setCoFichero(documento.getCoFichero());
        bdDDocumento.setCoExtension(documento.getCoExtension());
        bdDDocumento.setBlDocumento(documento.getBlDocumento());
        bdDDocumento.setDsDocumento(documento.getDsDocumento());
        bdDDocumento.setFeAlta(new Date());
        bdDDocumento.setIdSituaciondoc(listaBdTSituaciondoc.get(0).getIdSituaciondoc());
        bdDDocumento.setIdTipodocumento(listaBdTTipodocumento.get(0).getIdTipodocumento());
        
        StDDocumento stDDocumento = new StDDocumento(datosUsuario);
        stDDocumento.alta(bdDDocumento, entityManager);
        
        BdAConftipodoc filtroBdAConftipodoc = new BdAConftipodoc();
        filtroBdAConftipodoc.setIdTipodocumento(listaBdTTipodocumento.get(0).getIdTipodocumento());
        filtroBdAConftipodoc.setFeAlta(new Date());
        filtroBdAConftipodoc.setFeDesactivo(new Date());
        StAConftipodoc stAConftipodoc = new StAConftipodoc(datosUsuario);
        ArrayList<BdAConftipodoc> listaBdAConftipodoc = stAConftipodoc.filtro(filtroBdAConftipodoc, entityManager);
        if (listaBdAConftipodoc == null || listaBdAConftipodoc.isEmpty()) {
            throw new RegistryNotFoundException();
        }
        
        boolean boTieneFirmas = false;
        for (BdAConftipodoc itemBdAConftipodoc : listaBdAConftipodoc) {
            BdADocfirma bdADocfirma = new BdADocfirma();
            bdADocfirma.setIdDocumento(bdDDocumento.getIdDocumento());
            bdADocfirma.setIdAutoridad(itemBdAConftipodoc.getIdAutoridad());
            bdADocfirma.setDiTipofirma(itemBdAConftipodoc.getDiTipofirma());
            bdADocfirma.setEnOrden(itemBdAConftipodoc.getEnOrden());
            bdADocfirma.setDsFirmaposx(itemBdAConftipodoc.getDsFirmaposx());
            bdADocfirma.setDsFirmaposy(itemBdAConftipodoc.getDsFirmaposy());
            bdADocfirma.setFeAlta(new Date());
            
            StADocfirma stADocfirma = new StADocfirma(datosUsuario);
            stADocfirma.alta(bdADocfirma, entityManager);
            boTieneFirmas = true;
        }
        
        if (boTieneFirmas) {
            filtroBdTSituaciondoc = new BdTSituaciondoc();
            filtroBdTSituaciondoc.setCoSituaciondoc("PENDIENTE_FIRMA");
            filtroBdTSituaciondoc.setFeAlta(new Date());
            filtroBdTSituaciondoc.setFeDesactivo(new Date());
            listaBdTSituaciondoc = stTSituaciondoc.filtro(filtroBdTSituaciondoc, entityManager);
            if (listaBdTSituaciondoc == null || listaBdTSituaciondoc.isEmpty()) {
                throw new RegistryNotFoundException();
            }
            bdDDocumento = stDDocumento.item(bdDDocumento.getIdDocumento(), entityManager);
            bdDDocumento.setIdSituaciondoc(listaBdTSituaciondoc.get(0).getIdSituaciondoc());
            stDDocumento.actualiza(bdDDocumento, entityManager);
        }
        
        if (documento.getDsObservaciones() != null && !documento.getDsObservaciones().isBlank()) {
            BdADocobs bdADocobs = new BdADocobs();
            bdADocobs.setDsObservaciones(documento.getDsObservaciones());
            bdADocobs.setIdDocumento(bdDDocumento.getIdDocumento());
            bdADocobs.setIdUsuario(datosUsuario.getBdTUsuario().getIdUsuario());
            bdADocobs.setFeAlta(new Date());
            
            StADocobs stADocobs = new StADocobs(datosUsuario);
            stADocobs.alta(bdADocobs, entityManager);
            
        }
        
        return bdDDocumento.getIdDocumento();
    }

    private void grabarExtras(ArrayList<Extra> extras, Integer idDocumento, EntityManager entityManager) throws Exception {
        if (extras != null && !extras.isEmpty()) {
            for (Extra itemExtra : extras) {
                BdADocextra bdADocextra = new BdADocextra();
                bdADocextra.setIdDocumento(idDocumento);
                bdADocextra.setCoFichero(itemExtra.getCoFichero());
                bdADocextra.setCoExtension(itemExtra.getCoExtension());
                bdADocextra.setBlFichero(itemExtra.getBlFichero());
                bdADocextra.setFeAlta(new Date());
                
                StADocextra stADocextra = new StADocextra(datosUsuario);
                stADocextra.alta(bdADocextra, entityManager);
            }
        }
    }

    private void validarCampos(DocumentRequest documentRequest) throws RequiredFieldException {
        documentRequest.getCabecera().getCoUnidad();
        documentRequest.getCabecera().getTokenUsuario();
        documentRequest.getDocumento().getBlDocumento();
        documentRequest.getDocumento().getCoExtension();
        documentRequest.getDocumento().getCoFichero();
        documentRequest.getDocumento().getCoTipoDocumento();
        documentRequest.getDocumento().getDsDocumento();
        documentRequest.getDocumento().getDsObservaciones();
        
        documentRequest.getExtras();
        if (documentRequest.getExtras() != null) {
            for (Extra itemExtra : documentRequest.getExtras()) {
                itemExtra.getBlFichero();
                itemExtra.getCoExtension();
                itemExtra.getCoFichero();
            }
        }
        
    }

    public DatosUsuario getDatosUsuario() {
        return datosUsuario;
    }

    public void setDatosUsuario(DatosUsuario datosUsuario) {
        this.datosUsuario = datosUsuario;
    }
}
