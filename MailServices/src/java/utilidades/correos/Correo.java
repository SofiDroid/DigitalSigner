package utilidades.correos;

import init.AppInit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import main.RespuestaMail;
import org.apache.log4j.Logger;


public class Correo
{
    private static final Logger LOG = Logger.getLogger(Correo.class);
    
    public RespuestaMail enviarMails(String texto, String asunto, String from, ArrayList<DataFiles> adjuntos, HashMap<Message.RecipientType, String> destinatarios)
    {
        Transport t=null;
        RespuestaMail resultado = new RespuestaMail();
        
        try
        {
            Properties props = new Properties();
            props.setProperty("proxySet", "false");
            Session session = Session.getDefaultInstance(props);

            // cargamos los valores del servidor de correo
            String servidor=AppInit.getServidorCorreo();
            int puerto=AppInit.getPuertoServidorCorreo();
            String sender = (from != null ? from : AppInit.getSenderServidorCorreo());

            String usuario = AppInit.getUsuarioServidorCorreo();
            String password = (String) AppInit.getPasswordServidorCorreo();

            // Introducimos los valores, subject, texto, destinos, remitente, etc
            MimeMessage message = new MimeMessage(session);

            t = session.getTransport("smtp");

            // Remitente
            message.setFrom(new InternetAddress(sender));                

            //==================
            // Indicamos los destinatarios                
            if (destinatarios.get(Message.RecipientType.TO) != null) {
                String[] destinos = destinatarios.get(Message.RecipientType.TO).split(";");
                // Dimensionamos array de destinatarios
                Address[] direcciones = new Address[destinos.length];

                // Indicamos los destinatarios
                for (int i=0;i<destinos.length;i++)
                {
                    direcciones[i] = new InternetAddress(destinos[i]);
                }
                message.addRecipients(Message.RecipientType.TO, direcciones);
            }

            if (destinatarios.get(Message.RecipientType.CC) != null) {
                String[] destinos = destinatarios.get(Message.RecipientType.CC).split(";");
                // Dimensionamos array de destinatarios
                Address[] direcciones = new Address[destinos.length];

                // Indicamos los destinatarios
                for (int i=0;i<destinos.length;i++)
                {
                    direcciones[i] = new InternetAddress(destinos[i]);
                }
                message.addRecipients(Message.RecipientType.CC, direcciones);
            }

            if (destinatarios.get(Message.RecipientType.BCC) != null) {
                String[] destinos = destinatarios.get(Message.RecipientType.BCC).split(";");
                // Dimensionamos array de destinatarios
                Address[] direcciones = new Address[destinos.length];

                // Indicamos los destinatarios
                for (int i=0;i<destinos.length;i++)
                {
                    direcciones[i] = new InternetAddress(destinos[i]);
                }
                message.addRecipients(Message.RecipientType.BCC, direcciones);
            }

            // Indicamos el contenido del Email
            message.setSubject(asunto);

            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(texto, "text/html; charset=utf-8");
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            // ===== Tratamos todos los adjuntos
            if (adjuntos != null) {
                for(DataFiles item : adjuntos)
                {
                    String ContentType = item.getContentType();
                    String fileName = item.getFileName();

                    // Es binario (RutaFile viene vacio)
                    // ========================================
                        byte[] binaryData = item.getBinaryData();

                    // Enviamos ==============================
                        messageBodyPart = new MimeBodyPart();
                        messageBodyPart.setDataHandler(new DataHandler(new ByteArrayDataSource(binaryData, ContentType)));
                        messageBodyPart.setFileName(fileName);
                        multipart.addBodyPart(messageBodyPart);
                    // ========================================
                }
            }
            message.setContent(multipart);

            String lista_destinos = "";
            if (message.getRecipients(Message.RecipientType.TO) != null) {
                for (Address item : message.getRecipients(Message.RecipientType.TO)) {
                    if (!lista_destinos.isEmpty()) {
                        lista_destinos +=";";
                    }
                    lista_destinos += "(TO)" + item.toString();
                }
            }
            if (message.getRecipients(Message.RecipientType.CC) != null) {
                for (Address item : message.getRecipients(Message.RecipientType.CC)) {
                    if (!lista_destinos.isEmpty()) {
                        lista_destinos +=";";
                    }
                    lista_destinos += "(CC)" + item.toString();
                }
            }
            if (message.getRecipients(Message.RecipientType.BCC) != null) {
                for (Address item : message.getRecipients(Message.RecipientType.BCC)) {
                    if (!lista_destinos.isEmpty()) {
                        lista_destinos +=";";
                    }
                    lista_destinos += "(BCC)" + item.toString();
                }
            }
            LOG.info(String.format("Enviando correo -> Asunto: %s | From: %s | To: %s", message.getSubject(), message.getFrom()[0].toString(), lista_destinos));

            // ==== Conectamos y enviamos
            t.connect(servidor, puerto, usuario, password);
            t.sendMessage(message, message.getAllRecipients());
            t.close();
            
            resultado.setCodigo("0");
            resultado.setDescripcion("Correo enviado correctamente.");
            resultado.setObservaciones(String.format("Asunto: %s \n From: %s \n To: %s", message.getSubject(), message.getFrom()[0].toString(), lista_destinos));
        }
        catch(MessagingException e)
        {
            LOG.error(e.getMessage(), e);
            
            resultado.setCodigo("-1");
            resultado.setDescripcion(e.getMessage());
            resultado.setObservaciones("");
        }
        finally
        {
            try
            {
                if(t != null && t.isConnected())
                {
                    t.close();
                }
            }
            catch (MessagingException e) {
                LOG.error(e.getMessage(), e);
            }
        }
        
        return resultado;
    }
}    
