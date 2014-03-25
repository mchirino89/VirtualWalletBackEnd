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

package com.synergygb.billeteravirtual.notificacion.services.dummies;

import com.synergygb.billeteravirtual.core.models.config.ErrorID;
import com.synergygb.logformatter.WSLog;
import com.synergygb.logformatter.WSLogOrigin;
import com.synergygb.webAPI.layerCommunication.LayerDataObject;
import com.synergygb.webAPI.layerCommunication.dummies.DummyGenerator;
import com.synergygb.webAPI.layerCommunication.dummies.exceptions.UnsupportedDummyException;
import java.util.Random;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

/**
 * @author Synergy-GB
 * @author John Crespo <john.crespo@synergy-gb.com>
 */
public class RegisterUserDummy extends DummyGenerator {
    
    private static final Logger logger = Logger.getLogger(RegisterUserDummy.class);
    private static WSLog wsLog = new WSLog("Dummy para el registro de usuario");
    static ConsoleAppender conappender = new ConsoleAppender(new PatternLayout());

    public RegisterUserDummy() {
        logger.addAppender(conappender);
    }

    @Override
    public LayerDataObject getDummy(LayerDataObject layerDataObject) throws UnsupportedDummyException {
        LayerDataObject ldo = layerDataObject;
        
        Random randomGenerator = new Random();
        int randomPick = randomGenerator.nextInt(10);
        
        if (randomPick < 8) {
            logger.warn(wsLog.setParams(WSLogOrigin.INTERNAL_WS, ErrorID.NO_ERROR.getId(), "Simulando llamada a capa remota exitosa"));
            return ldo;
        }
        else {
            logger.warn(wsLog.setParams(WSLogOrigin.INTERNAL_WS, ErrorID.NO_ERROR.getId(), "Simulando llamada a capa remota fallida"));
            throw new UnsupportedDummyException();
        }
    }

}
