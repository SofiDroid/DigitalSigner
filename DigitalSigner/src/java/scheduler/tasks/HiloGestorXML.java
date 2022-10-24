package scheduler.tasks;

import java.io.File;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.TimerTask;
import javax.xml.bind.JAXB;
import jax.ws.services.interfaces.DocumentServiceImpl;
import jax.ws.services.types.DocumentRequest;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import seguridad.usuarios.DatosUsuario;
import utilidades.Configuraciones;

/**
 *
 * @author ihuegal
 */
public class HiloGestorXML extends TimerTask {

    private Logger LOG_MONITOREO = null;
    private DatosUsuario datosUsuario = null;
    private long delay = 10000;
    private long interval = 60000;
    private boolean running = false;
    
    public HiloGestorXML(Logger LOG_MONITOREO, DatosUsuario datosUsuario) throws Exception {
        this.LOG_MONITOREO = LOG_MONITOREO;
        this.datosUsuario = datosUsuario;
        this.LOG_MONITOREO.info("Tarea: " + this.getClass().getSimpleName() + " instanciada correctamente.");
    }

    public String getTaskName() {
        return this.getClass().getSimpleName();
    }

    public long getDelay() {
        return delay;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }

    public long getInterval() {
        return interval;
    }

    public void setInterval(long interval) {
        this.interval = interval;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public DatosUsuario getDatosUsuario() {
        return datosUsuario;
    }

    public void setDatosUsuario(DatosUsuario datosUsuario) {
        this.datosUsuario = datosUsuario;
    }
    
    @Override
    public void run() {
        try {
            if (!this.isRunning()) {
                LOG_MONITOREO.info("Ejecutando tarea: " + this.getTaskName());
                String sHiloGestorXML_Path = new Configuraciones(datosUsuario).recuperaConfiguracion("HILOGESTORXML_PATH");
                if (sHiloGestorXML_Path == null || sHiloGestorXML_Path.isBlank()) {
                    LOG_MONITOREO.error(this.getTaskName() + ": No se ha establecido el path de busqueda de XML, 'HILOGESTORXML_PATH'.");
                    return;
                }
                
                Collection<File> listFilesEntrada = FileUtils.listFiles(Paths.get(sHiloGestorXML_Path, "ENTRADA").toFile(), new String[]{"xml"}, false);
                for (File itemFileEntrada : listFilesEntrada) {
                    try {
                        DocumentRequest documentRequest = JAXB.unmarshal(itemFileEntrada, DocumentRequest.class);
                        DocumentServiceImpl documentServiceImpl = new DocumentServiceImpl();
                        documentServiceImpl.setDatosUsuario(datosUsuario);
                        documentServiceImpl.procesar(documentRequest);
                        
                        if (!itemFileEntrada.delete()) {
                            LOG_MONITOREO.error(this.getTaskName() + ": No se puede eliminar un fichero de entrada XML -> " + itemFileEntrada.getAbsolutePath());
                            if (!itemFileEntrada.renameTo(new File(itemFileEntrada.getAbsolutePath() + ".procesado"))) {
                                LOG_MONITOREO.error(this.getTaskName() + ": No se puede renombrar un fichero de entrada XML -> " + itemFileEntrada.getAbsolutePath());
                            }
                        }
                    }
                    catch (Exception ex) {
                        LOG_MONITOREO.error(this.getTaskName() + ": No se puede procesar un fichero de entrada XML -> " + ex.getMessage(), ex);
                    }
                }
            }
            else {
                LOG_MONITOREO.info(this.getTaskName() + ": Tarea ya en ejecucion, esperaremos a la siguiente ejecucion programada.");
            }
        }
        catch (Exception ex) {
            LOG_MONITOREO.error(ex.getMessage(), ex);
        }
    }
}
