/**
 * 
 * IMPORTANTE: LEA ESTA NOTA CUIDADOSAMENTE. AL RECIBIR EL CÓDIGO FUENTE EL 
 * CLIENTE ACEPTA LOS TERMINOS Y CONDICIONES DESCRITOS EN EL ACUERDO DE LICENCIAMIENTO.
 * SI LA EMPRESA NO ESTÁ DE ACUERDO CON ESTE ACUERDO DE LICENCIAMIENTO DEL CÓDIGO 
 * FUENTE, NO DESCARGUE, INSTALE, EJECUTE, COPIE, TRANSFIERA, O EN CUALQUIER CASO
 * UTILICE EL CÓDIGO FUENTE.
 * 
 * Este ACUERDO DE LICENCIAMENTO DEL CÓDIGO FUENTE (en adelante, “EL ACUERDO”) 
 * se realiza entre Synergy Global Business, C.A. (en adelante, “Synergy-GB”) 
 * y el Licenciatario (en adelante, “el Cliente”).
 * 
 * Synergy-GB se reserva el derecho de modificar las condiciones descritas en 
 * EL ACUERDO en cualquier momento y sin previo aviso. 
 * EL ACUERDO está descrito y accesible a través de la dirección siguiente: 
 * http://www.synergy-gb.com/licenciamiento.pdf
 * 
 */
package com.synergygb.billeteravirtual.notificacion.services.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 * Asegura+ REST Web Services
 * 
 * Class defining a static Thread Pool for handling non-mandatory session data insertion
 * 
 * @author Synergy-GB
 * @author John Crespo John Crespo <john.crespo@synergy-gb.com>, Javier Fernandez
 * @version 1.0
 */
public class SessionThreadPool {
    /**
     * Pool of session data threads that will perform the session insertion work
     */
    public static ExecutorService sessionThreadPoolExecutor = Executors.newFixedThreadPool(8);
}
