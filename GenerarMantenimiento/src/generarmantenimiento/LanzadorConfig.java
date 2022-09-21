package generarmantenimiento;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Configuración del lanzador
 *
 * @author Óscar
 */
public class LanzadorConfig {

    private static final Logger logger = Logger.getLogger(LanzadorConfig.class.getName());

    /**
     * Configuración cargada, necesaria para guardarla después (no creo el
     * fichero desde cero, paso)
     */
    private Document documentXML = null;

    /**
     * Configuraciones de conexión a BBDD (cargadas del fichero de configuación,
     * indexado por nombre)
     */
    public final HashMap<String, ServidorConfig> servidores = new HashMap<>();

    /**
     * Estilo de las ventanas: Metal, Windows, etc...
     */
    public String lookAndFeel = null;

    /**
     * Si la ruta del fichero contiene alguna carpeta que se llame igual no se
     * ejecutará
     */
    public String[] carpetasBloqueadas = new String[]{"versiones"};
    /**
     * Para reemplazar '&sUsuario' y '&usuario' en los scripts
     */
    public String usuario = "";
    /**
     * Indica que debe usar la lista de RUTAS_BLOQUEADAS para no ejercutar desde
     * esas carpetas
     */
    public boolean bloquearCarpetas = true;
    /**
     * Indica si al fallar un script no debe continuar con el resto
     */
    public boolean pararSiFalla = false;

    /**
     * Nombres de las conexiones sobre las que lanzar los scripts
     */
    public String[] lanzarEn = new String[]{};
    /**
     * Se concatenará delante de las rutas a lanzar
     */
    public String rutaBase = "";
    /**
     * Rutas a los ficheros de scripts que se van a lanzar
     */
    public String[] rutas = new String[]{};

    private static File getFile(String file) {
        return new File(".", file);
    }

    private static InputStream getInputStream(String file) {
        InputStream inputStream = null;

        File fFile = getFile(file);
        if (fFile.exists() && fFile.canRead()) {
            try {
                inputStream = new FileInputStream(fFile);
            } catch (IOException e) {
                logger.log(Level.INFO, "ERROR al leer el fichero: " + file, e);
            }
        }

        if (inputStream == null) {
            //Si no existe el fichero, configuración por defecto
            System.out.println("Archivo por defecto para: " + file);
            inputStream = LanzadorConfig.class.getResourceAsStream(file);
        }

        return inputStream;
    }

    /**
     *
     * @param file
     */
    public void leerServidores(String file) {
        System.out.println("Leer servidores: " + file);
        InputStream inputStream = getInputStream(file);
        leerServidores(inputStream);
    }

    private void leerServidores(InputStream stream) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(stream);

            //optional, but recommended
            //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("server");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    ServidorConfig config = new ServidorConfig(eElement);
                    servidores.put(config.nombre, config);

                    System.out.println("  " + config);
                }
            }

            System.out.println();
        } catch (Exception e) {
            logger.log(Level.INFO, "ERROR al leer los servidores", e);
        }
    }

    /**
     *
     * @param file
     */
    public void leerConfiguracion(String file) {
        System.out.println("Leer configuración: " + file);
        InputStream inputStream = getInputStream(file);
        leerConfiguracion(inputStream);
    }

    private void leerConfiguracion(InputStream stream) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(stream);

            documentXML = doc;

            //optional, but recommended
            //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            doc.getDocumentElement().normalize();

            NodeList list = doc.getFirstChild().getChildNodes();
            for (int i = 0; i < list.getLength(); i++) {
                Node node = list.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    System.out.print("  " + element.getTagName() + ": ");

                    switch (element.getTagName()) {
                        case "look_and_feel":
                            lookAndFeel = element.getTextContent();
                            System.out.print(lookAndFeel);
                            break;

                        case "usuario":
                            usuario = element.getTextContent();
                            System.out.print(usuario);
                            break;

                        case "bloquear_carpetas":
                            bloquearCarpetas = Boolean.valueOf(element.getTextContent());
                            System.out.print(bloquearCarpetas);
                            break;

                        case "parar_si_falla":
                            pararSiFalla = Boolean.valueOf(element.getTextContent());
                            System.out.print(pararSiFalla);
                            break;

                        case "ruta_base":
                            rutaBase = element.getTextContent();
                            System.out.print(rutaBase);
                            break;

                        case "carpetas_bloqueadas": {
                            ArrayList<String> aux = leerListaHija(node);
                            carpetasBloqueadas = new String[aux.size()];
                            carpetasBloqueadas = aux.toArray(carpetasBloqueadas);
                            break;
                        }
                        case "lanzar_en": {
                            ArrayList<String> aux = leerListaHija(node);
                            lanzarEn = new String[aux.size()];
                            lanzarEn = aux.toArray(lanzarEn);
                            break;
                        }
                        case "rutas": {
                            ArrayList<String> aux = leerListaHija(node);
                            System.out.print(" (" + aux.size() + ")");
                            rutas = new String[aux.size()];
                            rutas = aux.toArray(rutas);
                            break;
                        }

                        default:
                            System.out.print("????? - " + element.getTextContent());
                            break;
                    }

                    System.out.println();
                }
            }

            System.out.println();
        } catch (Exception e) {
            logger.log(Level.INFO, "ERROR al leer la configuración", e);
        }
    }

    private static ArrayList<String> leerListaHija(Node node) throws DOMException {
        ArrayList<String> aux = new ArrayList<>();
        NodeList listaHija = node.getChildNodes();
        for (int j = 0; j < listaHija.getLength(); j++) {
            Node nodeHijo = listaHija.item(j);
            if (nodeHijo.getNodeType() == Node.ELEMENT_NODE) {
                Element elementHijo = (Element) nodeHijo;
                String textHijo = elementHijo.getTextContent();
                System.out.print(textHijo);
                System.out.print(" ");
                aux.add(textHijo);
            }
        }
        return aux;
    }

    private static void guardarListaHija(Document doc, Node node, String tagName, String[] lista) throws DOMException {
        //Limpiar la lista
        while (node.hasChildNodes()) {
            node.removeChild(node.getFirstChild());
        }

        for (String valor : lista) {
            System.out.print(valor);
            System.out.print(" ");

            Node nodoHijo = doc.createElement(tagName);
            nodoHijo.setTextContent(valor);
            node.appendChild(nodoHijo);
        }
    }

    /**
     *
     * @param file
     */
    public void guardarConfiguracion(String file) {
        OutputStream outputStream = null;
        try {
            System.out.println("Guardar configuración: " + file);
            outputStream = new FileOutputStream(getFile(file), false);
            guardarConfiguracion(outputStream);
        } catch (Exception ex) {
            Logger.getLogger(LanzadorConfig.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(LanzadorConfig.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void guardarConfiguracion(OutputStream stream) throws Exception {
        //Debo tener la configuración ya cargada de antes
        if (documentXML != null) {
            Document doc = documentXML;

            NodeList list = doc.getFirstChild().getChildNodes();
            for (int i = 0; i < list.getLength(); i++) {
                Node node = list.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    System.out.print("  " + element.getTagName() + ": ");

                    switch (element.getTagName()) {
                        case "look_and_feel":
                            element.setTextContent(lookAndFeel);
                            System.out.print(lookAndFeel);
                            break;

                        case "usuario":
                            element.setTextContent(usuario);
                            System.out.print(usuario);
                            break;

                        case "bloquear_carpetas":
                            element.setTextContent(bloquearCarpetas + "");
                            System.out.print(bloquearCarpetas);
                            break;

                        case "parar_si_falla":
                            element.setTextContent(pararSiFalla + "");
                            System.out.print(pararSiFalla);
                            break;

                        case "ruta_base":
                            element.setTextContent(rutaBase);
                            System.out.print(rutaBase);
                            break;

                        case "carpetas_bloqueadas":
                            guardarListaHija(doc, element, "carpeta", carpetasBloqueadas);
                            break;

                        case "lanzar_en":
                            guardarListaHija(doc, element, "servidor", lanzarEn);
                            break;

                        case "rutas":
                            guardarListaHija(doc, element, "ruta", rutas);
                            break;

                        default:
                            System.out.print("?????");
                            break;
                    }

                    System.out.println();
                }
            }

            Result output = new StreamResult(stream);
            Source input = new DOMSource(doc);

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

            transformer.transform(input, output);
        }
    }
}
