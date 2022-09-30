/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilidades;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author ihuegal
 */
public class Validation {
    public static boolean isNullOrEmpty(Object value) {
        if (value == null) {
            return true;
        }
        
        if (value instanceof String valueString) {
            return valueString.isBlank();
        }
        else if (value instanceof Date valueDate) {
            return (valueDate.getTime() == 0);
        }
        
        return false;
    }
}
