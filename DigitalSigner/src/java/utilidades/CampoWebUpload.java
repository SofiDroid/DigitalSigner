package utilidades;

import org.primefaces.model.file.UploadedFile;

/**
 *
 * @author ihuegal
 */
public class CampoWebUpload extends CampoWeb {
    public CampoWebUpload() {
        super(Tipo.Upload);
    }
    
    public void setValue(UploadedFile value) {
        super.setValue(value);
    }

    @Override
    public UploadedFile getValue() {
        return (UploadedFile)super.getValue();
    }
}
