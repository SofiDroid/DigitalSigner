package seguridad.utils;

import java.util.Date;
import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author ihuegal
 */
public class SegUtils {
    public String generarTokenRandom() {
        return DigestUtils.sha256Hex(String.valueOf(new Date().getTime()));
    }
}
