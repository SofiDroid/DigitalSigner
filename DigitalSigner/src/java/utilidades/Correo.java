package utilidades;

import java.util.List;
import jax.client.services.interfaces.mailservice.DataFiles;
import jax.client.services.interfaces.mailservice.Destinatarios;
import jax.client.services.interfaces.mailservice.RespuestaMail;

/**
 *
 * @author ihuegal
 */
public class Correo {
    
    public static boolean enviar(String textoHTML, String asunto, List<DataFiles> adjuntos, String from, List<Destinatarios> destinatarios) {
        RespuestaMail respuestaMail = enviarMail(textoHTML, asunto, adjuntos, from, destinatarios);
        return respuestaMail.getCodigo().equalsIgnoreCase("OK"); 
        //throw new Exception(respuestaMail.getCodigo() + " - " + respuestaMail.getDescripcion());
    }
    
    private static RespuestaMail enviarMail(String textoHTML, String asunto, List<DataFiles> adjuntos, String from, List<Destinatarios> destinatarios) {
        jax.client.services.interfaces.mailservice.MailWebService_Service service = new jax.client.services.interfaces.mailservice.MailWebService_Service();
        jax.client.services.interfaces.mailservice.MailWebService port = service.getMailWebServicePort();
        return port.enviarMail(textoHTML, asunto, adjuntos, from, destinatarios);
    }
}
