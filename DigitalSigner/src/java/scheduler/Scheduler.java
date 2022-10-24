package scheduler;

import basedatos.servicios.StTUnidad;
import basedatos.servicios.StTUsuario;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Timer;
import org.apache.log4j.Logger;
import scheduler.tasks.HiloGestorXML;
import seguridad.usuarios.DatosUsuario;
import utilidades.Configuraciones;

/**
 *
 * @author ihuegal
 */
public class Scheduler {

    protected final static Logger LOG_MONITOREO = Logger.getLogger(Scheduler.class);
    private Timer temporizador = null;
    
    public void start() throws Exception {
        LOG_MONITOREO.info("Inicio de Scheduler...");

        //Iniciamos usuario y unidad de proceso
        DatosUsuario datosUsuario = new DatosUsuario();
        datosUsuario.setBdTUnidad(new StTUnidad(datosUsuario).item(1, null));
        datosUsuario.setBdTUsuario(new StTUsuario(datosUsuario).item(1, false, null));
        
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.HALF_UP);

        //Instanciamos nuestra clase Timer
        temporizador = new Timer();
        
        //Instanciamos nuestra tarea programada
        LOG_MONITOREO.info("Recuperando tareas programadas...");

        Configuraciones config = new Configuraciones(datosUsuario);
        boolean boHiloGestorXML = Boolean.valueOf(config.recuperaConfiguracion("HILOGESTORXML"));
        if (boHiloGestorXML) {
            HiloGestorXML hiloGestorXML = new HiloGestorXML(LOG_MONITOREO, datosUsuario);
            LOG_MONITOREO.info("Programando tarea " + hiloGestorXML.getTaskName() + " para iniciar en " + df.format(hiloGestorXML.getDelay()/1000) + " segundos y repetir cada " + df.format(hiloGestorXML.getInterval()/1000) + " segundos.");
            temporizador.schedule(hiloGestorXML, hiloGestorXML.getDelay(), hiloGestorXML.getInterval()); 
        }        
        LOG_MONITOREO.info("Inicio de Scheduler: OK!");
    }
    
    public void cancel() {
        if (this.temporizador != null) {
            this.temporizador.cancel();
        }
        this.temporizador = null;
    }
}
