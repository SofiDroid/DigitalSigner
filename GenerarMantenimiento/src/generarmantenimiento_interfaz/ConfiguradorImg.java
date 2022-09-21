/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package generarmantenimiento_interfaz;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

/**
 *
 * @author jconpad
 */
public class ConfiguradorImg {

    /**
     * Método que establece el tamaño de una imagen en un botón Swing.
     * @param button
     * @param ancho
     * @param alto
     */
    public void escalarImagenButton(JButton button, int ancho, int alto){
        ImageIcon iconoBoton3 = (ImageIcon) button.getIcon();
        ImageIcon iconoEscala = new ImageIcon(iconoBoton3.getImage().getScaledInstance(ancho, alto, java.awt.Image.SCALE_DEFAULT));
        button.setIcon(iconoEscala);
    }
    
    /**
     * Método que establece el tamaño de una imagen en una etiqueta Swing.
     * @param label
     * @param ancho
     * @param alto
     */
    public void escalarImagenLabel(JLabel label, int ancho, int alto){
        ImageIcon iconoBoton3 = (ImageIcon) label.getIcon();
        ImageIcon iconoEscala = new ImageIcon(iconoBoton3.getImage().getScaledInstance(ancho, alto, java.awt.Image.SCALE_DEFAULT));
        label.setIcon(iconoEscala);
    }
}
