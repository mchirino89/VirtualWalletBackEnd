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
package com.synergygb.billeteravirtual.core.config.exceptions;

import com.synergygb.webAPI.layerCommunication.exceptions.LayerCommunicationException;

public class BackendException extends LayerCommunicationException {

    /**
     * Creates a new instance of <code>BackendErrorException</code> without detail message.
     */
    public BackendException() {
    }

    /**
     * Constructs an instance of <code>BackendErrorException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public BackendException(String msg) {
        super(msg);
    }
}
