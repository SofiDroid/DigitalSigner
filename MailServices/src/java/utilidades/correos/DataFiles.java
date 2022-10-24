/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilidades.correos;

import init.AppInit;

/**
 *
 * @author UTE-04
 */
public class DataFiles {
    
    private String fileName="";
    private String contentType=""; 
    private byte[] binaryData=null;

    public DataFiles() {
        
    }
    
    /**
     * @return the contentType
     */
    public String getContentType() {
        return contentType;
    }
    
    /**
     * Función que devuelve el valor del atributo de igual nombre.
     * @param nombreFile 
     */
    private static String getContentType(String nombreFile)
    {
        nombreFile=nombreFile.toLowerCase();
        nombreFile = nombreFile.substring(nombreFile.lastIndexOf(".")+1);
        String salida;

        try
        {
            salida=AppInit.getContentType(nombreFile);
        }
        catch(Exception e)
        {
            salida=null;
        }
        return salida;
    }

    /**
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @param fileName the fileName to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
        
        // Calculo el contentType                
        this.contentType=getContentType("." + fileName);
    }

    public byte[] getBinaryData() {
        return binaryData;
    }

    public void setBinaryData(byte[] binaryData) {
        this.binaryData = binaryData;
    }
}