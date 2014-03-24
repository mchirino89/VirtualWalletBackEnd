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

/**
 * This exception is a connector exception that encompasses the situations
 * where the parameters, given to request a method from the connector, are
 * invalids.
 * In a method from a connector can ocurr different types of exceptions cause
 * by giving invalid parameters. In order to identify every one of them, this
 * exception uses a numeric code, unique for each situation.
 * 
 * @author Synergy-GB
 * @author John Crespo <john.crespo@synergy-gb.com>
 */
public class InvalidParameterException extends CodifiedException{
    
    /**
     * Consrtuctor Class
     */
    public InvalidParameterException(int exceptionCode) {
        super(exceptionCode);
    }

}
