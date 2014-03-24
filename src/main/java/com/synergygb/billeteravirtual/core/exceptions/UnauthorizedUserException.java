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
package com.synergygb.billeteravirtual.core.exceptions;
import com.synergygb.webAPI.layerCommunication.exceptions.LayerCommunicationException;

/**
 * Nuevo Mundo Mobile Car Insurance REST Web Services
 * @author Synergy-GB
 * @author John Crespo <john.crespo@synergy-gb.com>
 * @version 1.0
 */
public class UnauthorizedUserException extends LayerCommunicationException {

    public static String STATUS_CODE = "UNATHORIZED_USER";
    
    public static String STATUS_MSG = "Usuario no autorizado. Token invalido";
    
    /**
     * Creates a new instance of
     * <code>UnauthorizedUserException</code> without detail message.
     */
    public UnauthorizedUserException() {
    }

    /**
     * Constructs an instance of
     * <code>UnauthorizedUserException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public UnauthorizedUserException(String msg) {
        super(msg);
    }
}
