package afirma;

import es.gob.afirma.core.signers.AOSimpleSignInfo;
import es.gob.afirma.core.util.tree.AOTreeModel;
import es.gob.afirma.core.util.tree.AOTreeNode;
import es.gob.afirma.signers.pades.AOPDFSigner;
import es.gob.afirma.signvalidation.SignValidity;
import static es.gob.afirma.signvalidation.SignValidity.SIGN_DETAIL_TYPE.KO;
import excepciones.NoContentException;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.log4j.Logger;
import org.apache.tomcat.util.codec.binary.Base64;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import utilidades.Formateos;

/**
 *
 * @author ihuegal
 */
public class AfirmaUtils {
    
    Logger LOG = Logger.getLogger(AfirmaUtils.class);
    
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
            for (int i = 0; i < AOTreeModel.getChildCount(signersStructure.getRoot()); i++) {
                Firmante firmante = new Firmante();
                AOSimpleSignInfo firma = (AOSimpleSignInfo)((AOTreeNode) AOTreeModel.getChild(signersStructure.getRoot(), i)).getUserObject();
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
                firmante.setIsTimestamped(firma.isTimeStamped());
                if (firma.isTimeStamped()) {
                    firmante.setTimestamps(firma.getTimestampingTime());
                }
                resultadoValidacionFirmas.getListaFirmantes().add(firmante);
            }
        }
        
        return resultadoValidacionFirmas;
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
                firmante.setIsTimestamped(firma.isTimeStamped());
                if (firma.isTimeStamped()) {
                    firmante.setTimestamps(firma.getTimestampingTime());
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
    
    public byte[] sellarXAdES(byte[] binFichero) throws Exception {
        /**
         * tsaURL
         * tsaPolicy (Default "0.4.0.2023.1.1")
         * tsaHashAlgorithm (Default "SHA-512")
         * tsaRequireCert
         * tsaUsr
         * tsaPwd
         * tsaSslKeyStore
         * tsaSslKeyStorePassword
         * tsaSslKeyStoreType (Default "PKCS12")
         * tsaSslTrustStore
         * tsaSslTrustStorePassword
         * tsaSslTrustStoreType (Default "PKCS12")
         * verifyHostname (Default "True")
         */
        return es.gob.afirma.signers.xades.XAdESTspUtil.timestampXAdES(binFichero, cargarPropiedades());
    }
    
    /**
     * Función que carga las propiedades del fichero "config.properties"
     * 
     * @throws Exception 
     */
    private Properties cargarPropiedades() throws Exception {
        LOG.info("Cargando fichero de configuración...");

        String fs = File.separator;
        String rutaBaseLocal = null;
        String entConfig;

        // Obtenemos el contexto de nuestro entorno. La cadena
        InitialContext ctx = new InitialContext();
        Context envCtx = (Context) ctx.lookup("java:comp/env");

        entConfig = System.getenv("DigitalSignerTSA.properties");
        if ((entConfig == null) || ("".equals(entConfig)))
        {
            entConfig = System.getProperty ("DigitalSignerTSA.properties");
        }
        if ((entConfig == null) || ("".equals(entConfig)))
        {
            try {
                entConfig = (String) envCtx.lookup ("config.properties");
            } catch (Exception e) {
            }
        }

        if (entConfig != null)
        {
            // Hay variable de entorno, miro a ver si hay fichero
            final File configFile = new File(entConfig, "DigitalSignerTSA.properties").getCanonicalFile();
            if (configFile.isFile() && configFile.canRead())
            {
                rutaBaseLocal = entConfig + fs;
            }
        }

        if (rutaBaseLocal == null)
        {
            rutaBaseLocal = System.getenv("CATALINA_HOME") + fs + "conf" + fs;
        }

        LOG.info("Ruta ruta fichero configuración: " + rutaBaseLocal + "DigitalSignerTSA.properties");

        Properties prop = new Properties();
        prop.load(new FileInputStream(rutaBaseLocal + "DigitalSignerTSA.properties"));
        
        LOG.info("Fichero de configuración TSA cargado correctamente!");
        
        return prop;
    }
}
