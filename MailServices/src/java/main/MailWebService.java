/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.ArrayList;
import java.util.HashMap;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.mail.Message;
import javax.xml.ws.BindingType;
import utilidades.correos.Correo;
import utilidades.correos.DataFiles;
import utilidades.correos.Destinatarios;

/**
 *
 * @author ihuegal
 */
@WebService(serviceName = "MailWebService")
@BindingType(javax.xml.ws.soap.SOAPBinding.SOAP11HTTP_BINDING)
public class MailWebService {

    /**
     * This is a sample web service operation
     * @param textoHTML
     * @param asunto
     * @param adjuntos
     * @param from
     * @param destinatarios
     * @return 
     */
    @WebMethod(operationName = "enviarMail")
    public RespuestaMail enviarMail(@WebParam(name = "textoHTML") String textoHTML, @WebParam(name = "asunto") String asunto, 
            @WebParam(name = "adjuntos") ArrayList<DataFiles> adjuntos, @WebParam(name = "from") String from, 
            @WebParam(name = "destinatarios") ArrayList<Destinatarios> destinatarios)
    {
        // == Modifcar los parametros de entrada para que no sean tan complejos en el WSDL ==
        HashMap<Message.RecipientType, String> hm_destinatarios = new HashMap<>();
        for (Destinatarios item : destinatarios) {
            String emailAddress = hm_destinatarios.get(item.getRecipientTypeEnum());
            if (emailAddress != null && !emailAddress.isEmpty()) {
                emailAddress += (emailAddress.endsWith(";") ? "" : ";") + item.getEmailAddress();
            }
            else {
                emailAddress = item.getEmailAddress();
            }
            if (emailAddress != null && !emailAddress.isEmpty()) {
                hm_destinatarios.put(item.getRecipientTypeEnum(), emailAddress);
            }
        }
        // ==================================================================================

        return new Correo().enviarMails(textoHTML, asunto, from, adjuntos, hm_destinatarios);
    }
}
