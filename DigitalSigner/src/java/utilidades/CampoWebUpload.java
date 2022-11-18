package utilidades;

import org.primefaces.model.file.UploadedFile;

/**
 *
 * @author ihuegal
 */
public class CampoWebUpload extends CampoWeb {
    
    protected String filename = "";
    protected String extension = "";
    protected byte[] binFichero = null;
    
    public CampoWebUpload() {
        super(Tipo.Upload);
    }
    
    public void setValue(UploadedFile value) {
        super.setValue(value);
        if (value != null) {
            this.filename = value.getFileName();
            this.extension = value.getFileName().substring(value.getFileName().lastIndexOf(".") + 1).toUpperCase();
            this.binFichero = value.getContent();
        }
    }

    @Override
    public UploadedFile getValue() {
        return (UploadedFile)super.getValue();
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public byte[] getBinFichero() {
        return binFichero;
    }

    public void setBinFichero(byte[] binFichero) {
        this.binFichero = binFichero;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }
}
