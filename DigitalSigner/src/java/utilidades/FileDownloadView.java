package utilidades;

import java.io.ByteArrayInputStream;
import java.net.URLConnection;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author ihuegal
 */
@Named
@RequestScoped
public class FileDownloadView {

    private StreamedContent file;

    public FileDownloadView() {
        try {
            String filename = (String)Session.recuperarAtributo("filename");
            byte[] binDocumento = (byte[])Session.recuperarAtributo("binDocumento");
            if (filename != null && binDocumento != null) {
                String mimeType = null;
                if (filename.toLowerCase().endsWith(".pdf")) {
                    mimeType = "application/pdf";
                }
                else if (filename.toLowerCase().endsWith(".xsig")) {
                    mimeType = "application/octet-stream";
                }
                if (mimeType == null) {
                    try (ByteArrayInputStream bais = new ByteArrayInputStream(binDocumento)) {
                        mimeType = URLConnection.guessContentTypeFromStream(bais);
                    }
                }
                file = DefaultStreamedContent.builder()
                        .name(filename)
                        .contentType(mimeType)
                        .stream(() -> new ByteArrayInputStream(binDocumento))
                        .build();
            }
            else {
                file = null;
            }
        } catch (Exception ex) {
            Mensajes.showException(this.getClass(), ex);
        }
    }

    public StreamedContent getFile() {
        return file;
    }

    public void setFile(StreamedContent file) {
        this.file = file;
    }
}
