package jax.ws.services.interfaces;

import basedatos.servicios.StATokenusuario;
import basedatos.servicios.StAUniusu;
import basedatos.servicios.StDDocumento;
import basedatos.servicios.StDEntradaxml;
import basedatos.servicios.StTSituaciondoc;
import basedatos.servicios.StTSituacionxml;
import basedatos.servicios.StTTipodocumento;
import basedatos.servicios.StTUnidad;
import basedatos.servicios.StTUsuario;
import basedatos.tablas.BdATokenusuario;
import basedatos.tablas.BdAUniusu;
import basedatos.tablas.BdDDocumento;
import basedatos.tablas.BdDEntradaxml;
import basedatos.tablas.BdTSituaciondoc;
import basedatos.tablas.BdTSituacionxml;
import basedatos.tablas.BdTTipodocumento;
import basedatos.tablas.BdTUnidad;
import basedatos.tablas.BdTUsuario;
import excepciones.InvalidTokenException;
import excepciones.InvalidUnitException;
import excepciones.RegistryNotFoundException;
import init.AppInit;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import javax.jws.*;
import javax.xml.bind.JAXB;
import javax.xml.bind.annotation.XmlElement;
import jax.ws.services.types.AcuseReciboDocumentResponse;
import jax.ws.services.types.DocumentRequest;
import jax.ws.services.types.clases.Cabecera;
import jax.ws.services.types.clases.Documento;
import jax.ws.services.types.clases.Extras;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import tomcat.persistence.EntityManager;
import utilidades.BaseDatos;
import utilidades.Session;

/**
 *
 * @author ihuegal
 */
@WebService(serviceName = "DocumentService")
public class DocumentServiceImpl {

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
            validarToken(documentRequest.getCabecera());
            
            StringWriter sw = new StringWriter();
            JAXB.marshal(documentRequest, sw);
            String xmlString = sw.toString();
            
            Integer idEntradaXML = grabarEntradaXML(xmlString);
            String md5Hex = DigestUtils.md5Hex(xmlString).toUpperCase();
            
            acuseReciboDocumentResponse.getAcuseReciboDocument().setIdEntradaXML(idEntradaXML);
            acuseReciboDocumentResponse.getAcuseReciboDocument().setHash(md5Hex);
            
            try {
                Integer idDocumento = grabarDocumento(documentRequest.getDocumento(), null);

                grabarExtras(documentRequest.getExtras(), null);
                
                actualizarEntradaXML(idEntradaXML, idDocumento, null);
            }
            catch (Exception ex) {
                Logger.getLogger(DocumentServiceImpl.class).error(ex.getMessage(), ex);
            }
        }
        catch (Exception ex) {
            Logger.getLogger(DocumentServiceImpl.class).error(ex.getMessage(), ex);
            acuseReciboDocumentResponse.getError().setErrorMessage(ex.getMessage());
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
        StATokenusuario stATokenusuario = new StATokenusuario();
        ArrayList<BdATokenusuario> listaBdATokenusuario = stATokenusuario.filtro(filtroBdATokenusuario, null);
        if (listaBdATokenusuario == null || listaBdATokenusuario.isEmpty()) {
            throw new InvalidTokenException();
        }
        BdTUsuario itemBdTUsuario = new StTUsuario().item(listaBdATokenusuario.get(0).getIdUsuario(), true, null);
        if (itemBdTUsuario == null) {
            throw new InvalidTokenException();
        }
        Session.getDatosUsuario().setBdTUsuario(itemBdTUsuario);
        
        // Validar y obtener la unidad del usuario
        BdTUnidad filtroBdTUnidad = new BdTUnidad();
        filtroBdTUnidad.setCoUnidad(coUnidad);
        filtroBdTUnidad.setFeAlta(new Date());
        filtroBdTUnidad.setFeDesactivo(new Date());
        StTUnidad stTUnidad = new StTUnidad();
        ArrayList<BdTUnidad> listaBdTUnidad = stTUnidad.filtro(filtroBdTUnidad, null);
        if (listaBdTUnidad == null || listaBdTUnidad.isEmpty()) {
            throw new InvalidUnitException();
        }
        BdAUniusu filtroBdAUniusu = new BdAUniusu();
        filtroBdAUniusu.setIdUsuario(itemBdTUsuario.getIdUsuario());
        filtroBdAUniusu.setIdUnidad(listaBdTUnidad.get(0).getIdUnidad());
        filtroBdAUniusu.setFeAlta(new Date());
        filtroBdAUniusu.setFeDesactivo(new Date());
        StAUniusu stAUniusu = new StAUniusu();
        ArrayList<BdAUniusu> listaBdAUniusu = stAUniusu.filtro(filtroBdAUniusu, null);
        if (listaBdAUniusu == null || listaBdAUniusu.isEmpty()) {
            throw new InvalidUnitException();
        }
        Session.getDatosUsuario().setBdTUnidad(listaBdTUnidad.get(0));
    }

    private Integer grabarDocumento(Documento documento, EntityManager em) throws Exception {
        BdTTipodocumento filtroBdTTipodocumento = new BdTTipodocumento();
        filtroBdTTipodocumento.setCoTipodocumento(documento.getCoTipoDocumento());
        filtroBdTTipodocumento.setFeAlta(new Date());
        filtroBdTTipodocumento.setFeDesactivo(new Date());
        StTTipodocumento stTTipodocumento = new StTTipodocumento();
        ArrayList<BdTTipodocumento> listaBdTTipodocumento = stTTipodocumento.filtro(filtroBdTTipodocumento, null);
        if (listaBdTTipodocumento == null || listaBdTTipodocumento.isEmpty()) {
            throw new RegistryNotFoundException();
        }
                
        BdTSituaciondoc filtroBdTSituaciondoc = new BdTSituaciondoc();
        filtroBdTSituaciondoc.setCoSituaciondoc("NUEVO");
        filtroBdTSituaciondoc.setFeAlta(new Date());
        filtroBdTSituaciondoc.setFeDesactivo(new Date());
        StTSituaciondoc stTSituaciondoc = new StTSituaciondoc();
        ArrayList<BdTSituaciondoc> listaBdTSituaciondoc = stTSituaciondoc.filtro(filtroBdTSituaciondoc, null);
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
        
        StDDocumento stDDocumento = new StDDocumento();
        Integer idDocumento = stDDocumento.alta(bdDDocumento, null);
        if (AppInit.TIPO_BASEDATOS == BaseDatos.SQLSERVER) {
            bdDDocumento.setIdDocumento(idDocumento);
        }
        
        return bdDDocumento.getIdDocumento();
    }

    private void grabarExtras(Extras extras, EntityManager em) {
        return;
    }

    private Integer grabarEntradaXML(String xmlString) throws Exception {
        BdTSituacionxml filtroBdTSituacionxml = new BdTSituacionxml();
        filtroBdTSituacionxml.setCoSituacionxml("NUEVO");
        filtroBdTSituacionxml.setFeAlta(new Date());
        filtroBdTSituacionxml.setFeDesactivo(new Date());
        StTSituacionxml stTSituacionxml = new StTSituacionxml();
        ArrayList<BdTSituacionxml> listaBdTSituacionxml = stTSituacionxml.filtro(filtroBdTSituacionxml, null);
        if (listaBdTSituacionxml == null || listaBdTSituacionxml.isEmpty()) {
            throw new RegistryNotFoundException();
        }
        
        BdDEntradaxml bdDEntradaxml = new BdDEntradaxml();
        bdDEntradaxml.setBlEntradaxml(xmlString.getBytes(StandardCharsets.UTF_8));
        bdDEntradaxml.setFeAlta(new Date());
        bdDEntradaxml.setIdSituacionxml(listaBdTSituacionxml.get(0).getIdSituacionxml());
        StDEntradaxml stDEntradaxml = new StDEntradaxml();
        Integer idEntradaxml = stDEntradaxml.alta(bdDEntradaxml, null);
        if (AppInit.TIPO_BASEDATOS == BaseDatos.SQLSERVER) {
            bdDEntradaxml.setIdEntradaxml(idEntradaxml);
        }
        
        return bdDEntradaxml.getIdEntradaxml();
    }

    private void actualizarEntradaXML(Integer idEntradaXML, Integer idDocumento, EntityManager em) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
