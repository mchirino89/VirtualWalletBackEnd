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
package com.synergygb.billeteravirtual.core;

import com.synergygb.webAPI.handlers.WebServiceStatus;
import com.synergygb.webAPI.layerCommunication.WebServiceResponse;
import com.synergygb.webAPI.parameters.ParametersMediaType;
import com.synergygb.webAPI.parameters.WSParamObject;
import com.synergygb.webAPI.parameters.WebServiceParametersFactory;
import org.apache.log4j.Logger;

/**
 * @author Synergy-GB
 * @author John Crespo <john.crespo@synergy-gb.com>
 * @version 1.0
 */
public class ServiceUtils {
    
    private static Logger logger = Logger.getLogger(ServiceUtils.class);
    
    /**
     * Adds an error status block in JSON to the web response in a the following format
     * 
     */
    public static void addErrorStatus(WebServiceStatus status, WebServiceResponse webResponse) {
        WSParamObject statusObject = WebServiceParametersFactory.buildWSObject(ParametersMediaType.APPLICATION_JSON);
        statusObject.setProperty("code", status.getStatusType().toString());
        statusObject.setProperty("msg", status.getStatusMessage());
        webResponse.addParam("status", statusObject);
    }

}
