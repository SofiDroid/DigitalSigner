package utilidades;

import afirma.ResultadoValidacionFirmas;

/**
 *
 * @author ihuegal
 */
public class VisorMedia {
    private String filename = "documento.pdf";
    private String extension = null;
    private String player = "pdf";
    private ResultadoValidacionFirmas resultadoValidacionFirmas = null;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public ResultadoValidacionFirmas getResultadoValidacionFirmas() {
        return resultadoValidacionFirmas;
    }

    public void setResultadoValidacionFirmas(ResultadoValidacionFirmas resultadoValidacionFirmas) {
        this.resultadoValidacionFirmas = resultadoValidacionFirmas;
    }
    
    public String getHeightdoc() {
        if (this.resultadoValidacionFirmas == null || this.resultadoValidacionFirmas.getListaFirmantes().isEmpty()) {
            return "530px";
        }
        else {
            return (530 - ((this.resultadoValidacionFirmas.getListaFirmantes().size() * 40) + 60)) + "px";
        }
    }
    public String getHeightfirmas() {
        if (this.resultadoValidacionFirmas == null || this.resultadoValidacionFirmas.getListaFirmantes().isEmpty()) {
            return "0px";
        }
        else {
            return ((this.resultadoValidacionFirmas.getListaFirmantes().size() * 40) + 60) + "px";
        }
    }

    public String getExtension() {
        if (extension == null) {
            if (this.filename.contains(".")) {
                extension = this.filename.substring(this.filename.lastIndexOf(".") + 1);
            }
        }
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }
}
