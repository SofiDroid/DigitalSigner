package afirma;

import es.gob.afirma.core.signers.AOSimpleSignInfo;
import es.gob.afirma.core.util.tree.AOTreeModel;
import es.gob.afirma.core.util.tree.AOTreeNode;
import es.gob.afirma.signers.pades.AOPDFSigner;
import es.gob.afirma.signers.xml.Utils;
import es.gob.afirma.signers.xml.dereference.CustomUriDereferencer;
import es.gob.afirma.signvalidation.SignValidity;
import static es.gob.afirma.signvalidation.SignValidity.SIGN_DETAIL_TYPE.KO;
import excepciones.NoContentException;
import java.io.ByteArrayInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Key;
import java.security.KeyException;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import javax.xml.crypto.AlgorithmMethod;
import javax.xml.crypto.KeySelector;
import javax.xml.crypto.KeySelectorException;
import javax.xml.crypto.KeySelectorResult;
import javax.xml.crypto.MarshalException;
import javax.xml.crypto.XMLCryptoContext;
import javax.xml.crypto.XMLStructure;
import javax.xml.crypto.dom.DOMStructure;
import javax.xml.crypto.dsig.Reference;
import javax.xml.crypto.dsig.XMLObject;
import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureException;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMValidateContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyValue;
import javax.xml.crypto.dsig.keyinfo.X509Data;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.log4j.Logger;
import org.apache.tomcat.util.codec.binary.Base64;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import utilidades.Formateos;

/**
 *
 * @author ihuegal
 */
public class AfirmaUtils {
    
    public byte[] recuperarDocumentoXSIG(byte[] binDocumentoXSIG) throws Exception {
        final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        final Document doc = dbf.newDocumentBuilder().parse(new ByteArrayInputStream(binDocumentoXSIG));

        // Obtenemos el elemento Signature 
        final NodeList nl = doc.getElementsByTagName("CONTENT");
        if (nl.getLength() == 0) {
            throw new NoContentException();
        }
        
        return new Base64().decode(nl.item(0).getTextContent());
    }
    
    public ResultadoValidacionFirmas validarFirmas(String filename, boolean validarFirmas, boolean validarCertificados, boolean validarReferencias, boolean obtenerFirmantes) throws Exception {
        byte[] binFichero = Files.readAllBytes(Paths.get(filename));
        
        return validarFirmas(binFichero, validarFirmas, validarCertificados, validarReferencias, obtenerFirmantes);
    }
    
    public ResultadoValidacionFirmas validarFirmas(byte[] binFichero, boolean validarFirmas, boolean validarCertificados, boolean validarReferencias, boolean obtenerFirmantes) throws Exception {
        ResultadoValidacionFirmas resultadoValidacionFirmas = new ResultadoValidacionFirmas();
        resultadoValidacionFirmas.setValidarFirmas(validarFirmas);
        resultadoValidacionFirmas.setCheckCertificates(validarCertificados);
        resultadoValidacionFirmas.setCheckCertReferences(validarReferencias);
        resultadoValidacionFirmas.setObtenerFirmantes(obtenerFirmantes);

        if (validarFirmas) {
            SignValidity signValidity = new es.gob.afirma.signvalidation.ValidateXMLSignature().validate(binFichero, validarCertificados);
            switch (signValidity.getValidity()) {
                case OK -> {
                    resultadoValidacionFirmas.setBoValid(true);
                    resultadoValidacionFirmas.setDsValidacion("Documento válido según las especificaciones establecidas.");
                }
                case UNKNOWN, KO -> {
                    resultadoValidacionFirmas.setBoValid(false);
                    String dsValidacion = obtenerMensajeErrorValidacion(signValidity.getError());
                    if (dsValidacion == null) {
                        dsValidacion = (signValidity.getErrorException() != null ? signValidity.getErrorException().getMessage() : null);
                    }
                    resultadoValidacionFirmas.setDsValidacion(dsValidacion);
                }
                default -> {
                }
            }
        }
        
        if (obtenerFirmantes) {
            AOTreeModel signersStructure = new es.gob.afirma.signers.xades.AOXAdESSigner().getSignersStructure(binFichero, true);
            for (int i = 0; i < signersStructure.getChildCount(signersStructure.getRoot()); i++) {
                Firmante firmante = new Firmante();
                AOSimpleSignInfo firma = (AOSimpleSignInfo)((AOTreeNode) signersStructure.getChild(signersStructure.getRoot(), i)).getUserObject();
                firmante.setFecha(Formateos.dateToString(firma.getSigningTime(), Formateos.TipoFecha.FECHA_HORA_SEGUNDOS));

                String subjectDN = firma.getCerts()[0].getSubjectX500Principal().toString();
                String nombreFirmante = subjectDN;
                if (subjectDN.contains("CN=")) {
                    nombreFirmante = subjectDN.substring(subjectDN.indexOf("CN=") + 3, subjectDN.indexOf(",", subjectDN.indexOf("CN=")));
                }
                firmante.setNombre(nombreFirmante);
                try{
                    firma.getCerts()[0].checkValidity();
                    firmante.setValid(true);
                }
                catch (CertificateExpiredException | CertificateNotYetValidException ce) {
                    firmante.setValid(false);
                    firmante.setDsValidacion(ce.getMessage());
                }
                resultadoValidacionFirmas.getListaFirmantes().add(firmante);
            }
        }
        
        return resultadoValidacionFirmas;

//        final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//        dbf.setNamespaceAware(true);
//        final Document doc = dbf.newDocumentBuilder().parse(new ByteArrayInputStream(binFichero));
//
//        // Obtenemos el elemento Signature 
//        final NodeList nl = doc.getElementsByTagNameNS(XMLSignature.XMLNS, "Signature");
//        if (nl.getLength() == 0) {
//            throw new NoSignatureException();
//        }
//
//        for (int f = 0; f < nl.getLength(); f++)
//        {
//            DOMValidateContext valContext = new DOMValidateContext(
//                new KeyValueKeySelector(),
//                nl.item(f)
//            );
//            resultadoValidacionFirmas.getListaFirmantes().add(obtenerFirmante(valContext, 
//                    resultadoValidacionFirmas.isValidarFirmas(), 
//                    resultadoValidacionFirmas.isCheckCertificates(), 
//                    resultadoValidacionFirmas.isCheckCertReferences(),
//                    resultadoValidacionFirmas.isObtenerFirmantes()));
//        }
//
//        resultadoValidacionFirmas.setBoValid(true);      
//        resultadoValidacionFirmas.setDsValidacion(/*Msg.getString("Afirma_valido")*/"Documento válido según las especificaciones establecidas.");
//        for (Firmante itemFirmante : resultadoValidacionFirmas.getListaFirmantes()) {
//            if (!itemFirmante.isValid()) {
//                resultadoValidacionFirmas.setBoValid(false);
//                resultadoValidacionFirmas.setDsValidacion(/*Msg.getString("Afirma_no_valido")*/"Documento no válido según las especificaciones establecidas.");
//                break;
//            }
//        }
//        
//        return resultadoValidacionFirmas;
    }
    
    private static Firmante obtenerFirmante(DOMValidateContext valContext, boolean validarFirmas, boolean checkCertificates, boolean checkCertReferences, boolean obtenerFirmantes) {
        Firmante firmante = new Firmante();
        try {
            if (obtenerFirmantes) {
                firmante.setNombre(obtenerNombreFirmante(valContext));
                firmante.setFecha(obtenerFechaFirma(valContext));
            }
            
            final XMLSignature signature = Utils.getDOMFactory().unmarshalXMLSignature(valContext);

            if (validarFirmas) {
                try {
                    if (!signature.validate(valContext)) {
                        firmante.setSignValid(false);
                        firmante.setDsSignValidacion(/*Msg.getString("firma_invalida")*/"La firma es inválida");
                    }
                } catch (Exception na) {
                    if (!signature.getSignatureValue().validate(valContext)) {
                        firmante.setSignValid(false);
                        firmante.setDsSignValidacion(/*Msg.getString("valor_firma_invalido")*/"El valor de la firma es inválido");
                    }
                }
            }
            
            if (checkCertificates) {
                final XMLSignatureFactory certs = XMLSignatureFactory.getInstance("DOM");
                final XMLSignature signature2 = certs.unmarshalXMLSignature(valContext);
                final KeyInfo keyInfo = signature2.getKeyInfo();
                final Iterator<?> iter = keyInfo.getContent().iterator();
                while (iter.hasNext()) {
                    final XMLStructure kiType = (XMLStructure) iter.next();
                    //Validamos la fecha de expiracion y emision de los certificados 
                    if (kiType instanceof X509Data xData) {
                        final List<?> x509DataContent = xData.getContent();
                        for (int i1 = 0; i1 < x509DataContent.size(); i1++) {
                            if (x509DataContent.get(i1) instanceof X509Certificate certImpl) {
                                try {
                                    certImpl.checkValidity();
                                } catch (final CertificateExpiredException expiredEx) {
                                    firmante.setCertValid(false);
                                    firmante.setDsCertValidacion(/*Msg.getString("certificado_caducado")*/"Certificado caducado" + ": " + expiredEx.getMessage());
                                    break;
                                } catch (final CertificateNotYetValidException notYetValidEx) {
                                    firmante.setCertValid(false);
                                    firmante.setDsCertValidacion(/*Msg.getString("certificado_no_valido")*/"El certificado ya no es válido" + ": " + notYetValidEx.getMessage());
                                    break;
                                }
                            }
                        }
                    }
                }
            }

            if (/*checkCertReferences*/ false) {
                // Ahora miramos las referencias una a una 
                final Iterator<?> i = signature.getSignedInfo().getReferences().iterator();
                for (int j = 0; i.hasNext(); j++) {
                    final Reference iNext = (Reference) i.next();
                    if (!iNext.validate(valContext)) {
                        firmante.setRefValid(false);
                        firmante.setDsRefValidacion("La referencia " + j + " de la firma es invalida");
                        break;
                    }
                }
            }

            if (firmante.isSignValid() && firmante.isCertValid() && firmante.isRefValid()) {
                firmante.setValid(true);
                firmante.setDsValidacion(/*Msg.getString("Afirma_valido")*/"Documento válido según las especificaciones establecidas.");
            }
            else {
                firmante.setValid(false);
                firmante.setDsValidacion(/*Msg.getString("Afirma_no_valido")*/"Documento no válido según las especificaciones establecidas.");
            }
        } catch (final MarshalException | XMLSignatureException e) {
            firmante.setValid(false);
            firmante.setDsValidacion("No se ha podido validar la firma: " + e);
        }
        return firmante;
    }
    
    
    private static String obtenerFechaFirma(DOMValidateContext valContext) throws MarshalException {
        String fechaFirma = null;

        final XMLSignatureFactory certs = XMLSignatureFactory.getInstance("DOM");
        final XMLSignature signature2 = certs.unmarshalXMLSignature(valContext);
        final Iterator<?> iter2 = signature2.getObjects().iterator();
        while (iter2.hasNext()) {
            final XMLObject nodoType = (XMLObject) iter2.next();
            final Iterator<?> iter3 = nodoType.getContent().iterator();
            while (iter3.hasNext()) {
                final XMLStructure obType = (XMLStructure) iter3.next();
                Node nodo =((DOMStructure)obType).getNode();
                if (nodo.getNodeName().equals("xades:QualifyingProperties"))
                {
                    for (int i = 0; i < nodo.getChildNodes().getLength(); i++)
                    {
                        nodo = nodo.getChildNodes().item(i);
                        if (nodo.getNodeName().equals("xades:SignedProperties"))
                        {
                            for (int j = 0; j < nodo.getChildNodes().getLength(); j++)
                            {
                                nodo = nodo.getChildNodes().item(j);
                                if (nodo.getNodeName().equals("xades:SignedSignatureProperties"))
                                {
                                    for (int k = 0; k < nodo.getChildNodes().getLength(); k++)
                                    {
                                        nodo = nodo.getChildNodes().item(k);
                                        if (nodo.getNodeName().equals("xades:SigningTime"))
                                        {
                                            GregorianCalendar calendar;
                                            try {
                                                calendar = javax.xml.datatype.DatatypeFactory.newInstance().newXMLGregorianCalendar(nodo.getTextContent().trim()).toGregorianCalendar();
                                                
                                                fechaFirma = Formateos.dateToString(calendar.getTime(), Formateos.TipoFecha.FECHA_HORA_SEGUNDOS);
                                            } catch (final Exception ex) {
                                                fechaFirma = nodo.getTextContent();
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        
        return fechaFirma;
    }
    
    private static String obtenerNombreFirmante(DOMValidateContext valContext) throws MarshalException {
        String nombreFirmante = null;

        final XMLSignatureFactory certs = XMLSignatureFactory.getInstance("DOM");
        final XMLSignature signature2 = certs.unmarshalXMLSignature(valContext);
        final KeyInfo keyInfo = signature2.getKeyInfo();
        final Iterator<?> iter = keyInfo.getContent().iterator();
        while (iter.hasNext()) {
            final XMLStructure kiType = (XMLStructure) iter.next();
            //Obtenemos el sujeto de los certificados  (firmante)
            if (kiType instanceof X509Data xData) {
                final List<?> x509DataContent = xData.getContent();
                for (int i1 = 0; i1 < x509DataContent.size(); i1++) {
                    if (x509DataContent.get(i1) instanceof X509Certificate certImpl) {
                        String subjectDN = certImpl.getSubjectX500Principal().toString();
                        nombreFirmante = subjectDN;
                        if (subjectDN.contains("CN=")) {
                            nombreFirmante = subjectDN.substring(subjectDN.indexOf("CN=") + 3, subjectDN.indexOf(",", subjectDN.indexOf("CN=")));
                            break;
                        }
                    }
                }
            }
        }
        
        return nombreFirmante;
    }
    
    public ResultadoValidacionFirmas validarFirmasPAdES(byte[] binFichero, boolean validarFirmas, boolean validarCertificados, boolean validarReferencias, boolean obtenerFirmantes) throws Exception {
        ResultadoValidacionFirmas resultadoValidacionFirmas = new ResultadoValidacionFirmas();
        resultadoValidacionFirmas.setValidarFirmas(validarFirmas);
        resultadoValidacionFirmas.setCheckCertificates(validarCertificados);
        resultadoValidacionFirmas.setCheckCertReferences(validarReferencias);
        resultadoValidacionFirmas.setObtenerFirmantes(obtenerFirmantes);

        if (validarFirmas) {
            SignValidity signValidity = new es.gob.afirma.signvalidation.ValidatePdfSignature().validate(binFichero, validarCertificados);
            switch (signValidity.getValidity()) {
                case OK -> {
                    resultadoValidacionFirmas.setBoValid(true);
                    resultadoValidacionFirmas.setDsValidacion("Documento válido según las especificaciones establecidas.");
                }
                case UNKNOWN, KO -> {
                    resultadoValidacionFirmas.setBoValid(false);
                    String dsValidacion = obtenerMensajeErrorValidacion(signValidity.getError());
                    if (dsValidacion == null) {
                        dsValidacion = (signValidity.getErrorException() != null ? signValidity.getErrorException().getMessage() : null);
                    }
                    resultadoValidacionFirmas.setDsValidacion(dsValidacion);
                }
                default -> {
                }
            }
        }
        
        if (obtenerFirmantes) {
            AOTreeModel signersStructure = new AOPDFSigner().getSignersStructure(binFichero, true);
            for (int i = 0; i < signersStructure.getChildCount(signersStructure.getRoot()); i++) {
                Firmante firmante = new Firmante();
                AOSimpleSignInfo firma = (AOSimpleSignInfo)((AOTreeNode) signersStructure.getChild(signersStructure.getRoot(), i)).getUserObject();
                firmante.setFecha(Formateos.dateToString(firma.getSigningTime(), Formateos.TipoFecha.FECHA_HORA_SEGUNDOS));

                String subjectDN = firma.getCerts()[0].getSubjectX500Principal().toString();
                String nombreFirmante = subjectDN;
                if (subjectDN.contains("CN=")) {
                    nombreFirmante = subjectDN.substring(subjectDN.indexOf("CN=") + 3, subjectDN.indexOf(",", subjectDN.indexOf("CN=")));
                }
                firmante.setNombre(nombreFirmante);
                try{
                    firma.getCerts()[0].checkValidity();
                    firmante.setValid(true);
                }
                catch (CertificateExpiredException | CertificateNotYetValidException ce) {
                    firmante.setValid(false);
                    firmante.setDsValidacion(ce.getMessage());
                }
                resultadoValidacionFirmas.getListaFirmantes().add(firmante);
            }
        }
        
        return resultadoValidacionFirmas;
    }

    private String obtenerMensajeErrorValidacion(SignValidity.VALIDITY_ERROR error) {
        if (error != null) {
            return switch (error) {
                case ALGORITHM_NOT_SUPPORTED -> "Algoritmo no soportado.";
                case CA_NOT_SUPPORTED -> "Emisor del certificado no válido.";
                case CERTIFICATE_EXPIRED -> "Certificado de firma caducado.";
                case CERTIFICATE_NOT_VALID_YET -> "El certificado de firma aun no es válido.";
                case CERTIFICATE_PROBLEM -> "No se pudo extraer el certificado.";
                case CORRUPTED_SIGN -> "Información de firma no consistente (firma corrupta).";
                case CRL_PROBLEM -> "Existe un problema con las CRLs incrustadas en la firma.";
                case NO_DATA -> "No se han encontrado datos firmados.";
                case NO_MATCH_DATA -> "La firma no se corresponde con los datos firmados.";
                case NO_SIGN -> "No se ha encontrado la firma dentro del documento.";
                case ODF_UNKOWN_VALIDITY -> "Se trata de una firma ODF.";
                case OOXML_UNKOWN_VALIDITY -> "Se trata de una firma OOXML.";
                case PDF_UNKOWN_VALIDITY -> "Se trata de una firma PDF.";
                case UNKOWN_ERROR -> "Firma inválida por error desconocido.";
                case UNKOWN_SIGNATURE_FORMAT -> "No se reconoce el tipo de firma.";
                default -> "ERROR DESCONOCIDO";
            };
        }
        
        return null;
    }
    
    static final class KeyValueKeySelector extends KeySelector {

        @Override
        public KeySelectorResult select(final KeyInfo keyInfo,
                final KeySelector.Purpose purpose,
                final AlgorithmMethod method,
                final XMLCryptoContext context) throws KeySelectorException {
            if (keyInfo == null) {
                throw new KeySelectorException("Objeto KeyInfo nulo");
            }
            final List<?> list = keyInfo.getContent();

            try {
                // Instalamos un dereferenciador nuevo que solo actua cuando falla el por defecto 
                context.setURIDereferencer(
                        new CustomUriDereferencer()
                );
            } catch (final Exception e) {
                Logger.getLogger(this.getClass()).warn("No se ha podido instalar un dereferenciador a medida: ", e);
            }

            for (int i = 0; i < list.size(); i++) {
                final XMLStructure xmlStructure = (XMLStructure) list.get(i);
                if (xmlStructure instanceof KeyValue keyValue) {
                    final PublicKey publicKey;
                    try {
                        publicKey = keyValue.getPublicKey();
                    } catch (final KeyException ke) {
                        throw new KeySelectorException(ke);
                    }
                    return new SimpleKeySelectorResult(publicKey);
                } // Si no hay KeyValue intentamos sacar la clave publica del primer Certificado 
                // que encontramos en X509Data 
                else if (xmlStructure instanceof X509Data x509Data) {
                    final List<?> x509DataObjects = x509Data.getContent();
                    for (final Object o : x509DataObjects) {
                        if (o instanceof Certificate certificate) {
                            return new SimpleKeySelectorResult(certificate.getPublicKey());
                        }
                    }
                }
            }
            throw new KeySelectorException("No se ha encontrado la clave publica dentro del XML firmado");
        }
    }

    private static final class SimpleKeySelectorResult implements KeySelectorResult {

        private final PublicKey pk;

        SimpleKeySelectorResult(final PublicKey pk) {
            this.pk = pk;
        }

        @Override
        public Key getKey() {
            return this.pk;
        }
    }
}
