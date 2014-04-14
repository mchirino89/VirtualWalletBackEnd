/**
 *
 * IMPORTANTE: LEA ESTA NOTA CUIDADOSAMENTE. AL RECIBIR EL CÓDIGO FUENTE EL CLIENTE ACEPTA LOS TERMINOS Y
 * CONDICIONES DESCRITOS EN EL ACUERDO DE LICENCIAMIENTO. SI LA EMPRESA NO ESTÁ DE ACUERDO CON ESTE ACUERDO DE
 * LICENCIAMIENTO DEL CÓDIGO FUENTE, NO DESCARGUE, INSTALE, EJECUTE, COPIE, TRANSFIERA, O EN CUALQUIER CASO UTILICE
 * EL CÓDIGO FUENTE.
 *
 * Este ACUERDO DE LICENCIAMENTO DEL CÓDIGO FUENTE (en adelante, “EL ACUERDO”) se realiza entre Synergy Global
 * Business, C.A. (en adelante, “Synergy-GB”) y el Licenciatario (en adelante, “el Cliente”).
 *
 * Synergy-GB se reserva el derecho de modificar las condiciones descritas en EL ACUERDO en cualquier momento y sin
 * previo aviso. EL ACUERDO está descrito y accesible a través de la dirección siguiente:
 * http://www.synergy-gb.com/licenciamiento.pdf
 *
 */
package com.synergygb.billeteravirtual.notificacion.models.cache;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Billetera Virtual+ REST Web Services
 *
 * @author Synergy-GB
 * @author John Crespo <john.crespo@synergy-gb.com>
 * @version 1.0
 */
@XmlRootElement
public class UserSession implements Serializable {

    /**
     * Last activity timestamp
     */
    String lastUpdate;
    /**
     * Session start time
     */
    String sessionStart;

    public UserSession() {
        Date date = new Date();
        this.lastUpdate = String.valueOf(new Timestamp(date.getTime()));
        this.sessionStart = String.valueOf(new Timestamp(date.getTime()));
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public void invalidateSession() {
        long zero = 0;
        this.lastUpdate = String.valueOf(new Date(zero).getTime());
    }

    public String getSessionStart() {
        return sessionStart;
    }

    public void setSessionStart(String sessionStart) {
        this.sessionStart = sessionStart;
    }

    @Override
    public String toString() {
        return "UserSession{" + "lastUpdate=" + lastUpdate + '}';
    }
}
