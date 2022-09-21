/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package generarmantenimiento;

import static generarmantenimiento.GeneradorBase.getConexion;
import static generarmantenimiento.GeneradorBase.logger;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author epincid
 */
public class Utils {
    public static ArrayList<String> leerTablas(String tabla, String user, String pass, String url,LanzadorConfig config) {
        if(!tabla.contains("%")){
            ArrayList<String> tablas=new ArrayList<>();
            tablas.add(tabla);
            return tablas;
        }
        
        Connection conn = null;

        try {
            conn = getConexion(user, pass, url);
            Statement st = conn.createStatement();
            ResultSet result = st.executeQuery(
                    "select Table_name from INFORMATION_SCHEMA.TABLES where TABLE_CATALOG='DigitalSigner' AND TABLE_NAME LIKE '" + tabla + "' ESCAPE '\\'"
            );
      
            ArrayList<String> listaTablas = new ArrayList<>();
            while (result.next()) {
                               
                listaTablas.add(result.getString(1));
            }
            
            if (listaTablas.isEmpty()) {
                throw new IllegalArgumentException("No se han encontrado tablas para: " + tabla);
            }

//            conn.commit();
            return listaTablas;
        } catch (Exception ex) {
            Logger.getLogger(GeneradorJPABean.class.getName()).log(Level.SEVERE, "ERROR", ex);

            try {
                Thread.sleep(100); //Al escribir en err, lo que llegue después en out puede mezclarse
            } catch (Exception ex1) {

            }

            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException ex1) {
                Logger.getLogger(GeneradorJPABean.class.getName()).log(Level.WARNING, null, ex1);
            }

            return null;
        } finally {
            cierraConexion(conn,config);
        }
    }
    protected static void cierraConexion(Connection conn,LanzadorConfig config) {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (Exception ex) {
            logger.log(Level.WARNING, "ERROR al cerrar conexión: " + config, ex);
        }
    }
}
