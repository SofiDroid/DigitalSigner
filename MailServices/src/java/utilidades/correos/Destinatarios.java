/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilidades.correos;

import javax.mail.Message;

/**
 *
 * @author ihuegal
 */
public class Destinatarios {
    
    /**
     * Tipo de receptor:
     * - TO: Receptor normal (PARA)
     * - CC: Receptor en copia (CC)
     * - BCC: Receptor en copia oculta (CCO)
     */
    private String recipientType = "TO"; // (TO | CC | BCC)
    
    /**
     * Direcciones de email separadas por ";"
     */
    private String emailAddress = "";

    /**
     * Constructor vacío
     */
    public Destinatarios() {
    }

    /**
     * Constructor de destinatarios
     * 
     * @param recipientType
     * Tipo de receptor:
     * - TO: Receptor normal (PARA)
     * - CC: Receptor en copia (CC)
     * - BCC: Receptor en copia oculta (CCO)
     *
     * @param emailAddress
     * Direcciones de email separadas por ";"
     */
    public Destinatarios(String recipientType, String emailAddress) {
        this.recipientType = recipientType;
        this.emailAddress = emailAddress;
    }

    /**
     * @return
     * Tipo de receptor:
     * - "TO": Receptor normal (PARA)
     * - "CC": Receptor en copia (CC)
     * - "BCC": Receptor en copia oculta (CCO)
     */
    public String getRecipientType() {
        return recipientType;
    }

    public void setRecipientType(String recipientType) {
        this.recipientType = recipientType;
    }
    
    public Message.RecipientType getRecipientTypeEnum() {
        switch (this.recipientType)
        {
            case "TO":
                return Message.RecipientType.TO;
            case "CC":
                return Message.RecipientType.CC;
            case "BCC":
                return Message.RecipientType.BCC;
            default:
                return Message.RecipientType.TO;
        }
    }

    /**
     * @return 
     * Direcciones de email separadas por ";"
     */
    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}
