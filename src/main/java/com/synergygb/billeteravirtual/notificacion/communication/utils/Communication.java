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

import com.synergygb.asegura.core.models.config.ErrorID;
import com.synergygb.asegura.notificacion.communication.*;
import com.synergygb.asegura.notificacion.models.Map;
import com.synergygb.asegura.notificacion.models.Picture;
import com.synergygb.asegura.notificacion.models.Policy;
import com.synergygb.asegura.notificacion.models.SinisterDeclaration;
import com.synergygb.asegura.notificacion.models.UserInfo;
import com.synergygb.billeteravirtual.notificacion.models.cache.UserSession;
import com.synergygb.asegura.notificacion.services.dummies.NotificationDummy;
import com.synergygb.asegura.notificacion.services.dummies.RegisterUserDummy;
import com.synergygb.asegura.notificacion.services.dummies.StatesDummy;
import com.synergygb.billeteravirtual.notificacion.services.models.LoginParamsModel;
import com.synergygb.asegura.notificacion.services.models.NotificationParamsModel;
import com.synergygb.billeteravirtual.notificacion.services.models.RegisterParamsModel;
import com.synergygb.asegura.notificacion.services.models.UpdateParamsModel;
import com.synergygb.logformatter.WSLog;
import com.synergygb.logformatter.WSLogOrigin;
import com.synergygb.webAPI.layerCommunication.DataLayerCommunicationType;
import com.synergygb.webAPI.layerCommunication.LayerDataObject;
import com.synergygb.webAPI.layerCommunication.exceptions.LayerCommunicationException;
import com.synergygb.webAPI.layerCommunication.exceptions.LayerDataObjectParseException;
import com.synergygb.webAPI.layerCommunication.exceptions.LayerDataObjectToObjectParseException;
import com.synergygb.webAPI.parameters.WSParamElement;
import com.synergygb.webAPI.parameters.WSParamObject;
import java.util.List;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

/**
 * Asegura+ REST Web Services
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

    public static UserInfo postLoginData(DataLayerCommunicationType ct, LoginParamsModel loginInputParams) throws LayerCommunicationException, LayerDataObjectToObjectParseException, LayerDataObjectParseException {
        LayerDataObject loginInputLDO = LayerDataObject.buildFromObject(loginInputParams);
        LoginPOSTCommunication postLoginCommunication = new LoginPOSTCommunication();

        UserInfo loginResult = (UserInfo) postLoginCommunication.communicate(ct, loginInputLDO).toObject(UserInfo.class);

        return loginResult;
    }

    public static void registerUser(DataLayerCommunicationType communicationType, RegisterParamsModel user) throws LayerDataObjectParseException, LayerCommunicationException {
        RegisterPOSTCommunication postRegisterCommunication = new RegisterPOSTCommunication();
        LayerDataObject registerInputLDO = LayerDataObject.buildFromObject(user);

        postRegisterCommunication.communicate(communicationType, registerInputLDO, new RegisterUserDummy());

    }

    public static Map getStates(DataLayerCommunicationType communicationType) throws LayerCommunicationException, LayerDataObjectToObjectParseException{
        StatesGETCommunication getStatesCommunication = new StatesGETCommunication();
        
        Map result = (Map) getStatesCommunication.communicate(communicationType, null).toObject(Map.class);
        return result;
    }

    public static void updateUser(DataLayerCommunicationType communicationType, UpdateParamsModel updateModel) throws LayerCommunicationException, LayerDataObjectParseException {
        UpdatePOSTCommunication updateCommunication = new UpdatePOSTCommunication();
        LayerDataObject updateInputLDO = LayerDataObject.buildFromObject(updateModel);

        updateCommunication.communicate(communicationType, updateInputLDO);
    }

    public static void resetPassword(DataLayerCommunicationType communicationType, UpdateParamsModel updateModel) throws LayerCommunicationException, LayerDataObjectParseException {
        ResetPasswordPOSTCommunication resetCommunication = new ResetPasswordPOSTCommunication();
        LayerDataObject updateInputLDO = LayerDataObject.buildFromObject(updateModel);

        resetCommunication.communicate(communicationType, updateInputLDO);
    }
    
    public static SinisterDeclaration notificate(DataLayerCommunicationType communicationType, NotificationParamsModel notification) throws LayerDataObjectParseException, LayerCommunicationException, LayerDataObjectToObjectParseException {
        NotificationPOSTCommunication postNotificationCommunication = new NotificationPOSTCommunication();
        LayerDataObject notificationInputLDO = LayerDataObject.buildFromObject(notification);

        LayerDataObject communicationResult = postNotificationCommunication.communicate(DataLayerCommunicationType.REAL, notificationInputLDO);
        
        SinisterDeclaration result = (SinisterDeclaration) communicationResult.toObject(SinisterDeclaration.class);
        return result;

    }
    
    public static void savePicture(DataLayerCommunicationType communicationType, Picture pictureModel) throws LayerCommunicationException, LayerDataObjectParseException {
    
        PicturePOSTCommunication savePicture = new PicturePOSTCommunication();
        LayerDataObject pictureLDO = LayerDataObject.buildFromObject(pictureModel);
        
        savePicture.communicate(communicationType, pictureLDO);
    
    }
}
