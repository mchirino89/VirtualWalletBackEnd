/**
 *
 * IMPORTANTE: LEA ESTA NOTA CUIDADOSAMENTE. AL RECIBIR EL CÓDIGO FUENTE EL
 * CLIENTE ACEPTA LOS TERMINOS Y CONDICIONES DESCRITOS EN EL ACUERDO DE
 * LICENCIAMIENTO. SI LA EMPRESA NO ESTÁ DE ACUERDO CON ESTE ACUERDO DE
 * LICENCIAMIENTO DEL CÓDIGO FUENTE, NO DESCARGUE, INSTALE, EJECUTE, COPIE,
 * TRANSFIERA, O EN CUALQUIER CASO UTILICE EL CÓDIGO FUENTE.
 *
 * Este ACUERDO DE LICENCIAMENTO DEL CÓDIGO FUENTE (en adelante, “EL ACUERDO”)
 * se realiza entre Synergy Global Business, C.A. (en adelante, “Synergy-GB”) y
 * el Licenciatario (en adelante, “el Cliente”).
 *
 * Synergy-GB se reserva el derecho de modificar las condiciones descritas en EL
 * ACUERDO en cualquier momento y sin previo aviso. EL ACUERDO está descrito y
 * accesible a través de la dirección siguiente:
 * http://www.synergy-gb.com/licenciamiento.pdf
 *
 */
package com.synergygb.billeteravirtual.notificacion.communication.utils;

import com.synergygb.billeteravirtual.core.connector.cache.GenericMemcachedConnector;
import com.synergygb.billeteravirtual.notificacion.models.UserInfo;
import com.synergygb.billeteravirtual.notificacion.communication.*;
import com.synergygb.billeteravirtual.notificacion.services.models.LoginParamsModel;
import com.synergygb.logformatter.WSLog;
import com.synergygb.webAPI.layerCommunication.DataLayerCommunicationType;
import com.synergygb.webAPI.layerCommunication.LayerDataObject;
import com.synergygb.webAPI.layerCommunication.exceptions.LayerCommunicationException;
import com.synergygb.webAPI.layerCommunication.exceptions.LayerDataObjectParseException;
import com.synergygb.webAPI.layerCommunication.exceptions.LayerDataObjectToObjectParseException;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

/**
 * Billetera Virtual+ REST Web Services
 *
 * @author Synergy-GB
 * @author John Crespo John Crespo <john.crespo@synergy-gb.com>
 * @author Alejandro Carpio <alejandro.carpio@synergy-gb.com>
 * @version 1.0
 */
public class Communication {

    private static final Logger logger = Logger.getLogger(Communication.class);
    private static WSLog wsLog = new WSLog("Communication");
    static ConsoleAppender conappender = new ConsoleAppender(new PatternLayout());

    public Communication() {
        logger.addAppender(conappender);
    }

    public static UserInfo postLoginData(DataLayerCommunicationType ct, LoginParamsModel loginInputParams, GenericMemcachedConnector cacheConnector) throws LayerCommunicationException, LayerDataObjectToObjectParseException, LayerDataObjectParseException {
        LayerDataObject loginInputLDO = LayerDataObject.buildFromObject(loginInputParams);
        LoginPOSTCommunication postLoginCommunication = new LoginPOSTCommunication(cacheConnector);

        UserInfo loginResult = (UserInfo) postLoginCommunication.communicate(ct, loginInputLDO).toObject(UserInfo.class);

        return loginResult;
    }
    /*
    public static void registerUser(DataLayerCommunicationType communicationType, RegisterParamsModel user) throws LayerDataObjectParseException, LayerCommunicationException {
        RegisterPOSTCommunication postRegisterCommunication = new RegisterPOSTCommunication();
        LayerDataObject registerInputLDO = LayerDataObject.buildFromObject(user);
        postRegisterCommunication.communicate(communicationType, registerInputLDO, new RegisterUserDummy());
    }
    */
}
