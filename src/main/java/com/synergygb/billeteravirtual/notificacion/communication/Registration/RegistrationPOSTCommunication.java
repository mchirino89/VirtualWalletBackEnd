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
package com.synergygb.billeteravirtual.notificacion.communication.Registration;

import com.synergygb.billeteravirtual.notificacion.communication.Login.LoginPOSTCommunication;
import com.synergygb.billeteravirtual.core.config.AppXMLConfiguration;
import com.synergygb.billeteravirtual.core.connector.cache.GenericMemcachedConnector;
import com.synergygb.billeteravirtual.core.exceptions.CouchbaseOperationException;
import com.synergygb.billeteravirtual.core.models.config.ErrorID;
import com.synergygb.billeteravirtual.notificacion.communication.exceptions.PreexistingUserException;
import com.synergygb.billeteravirtual.notificacion.models.*;
import com.synergygb.billeteravirtual.notificacion.services.models.RegistrationParamsModel;
import com.synergygb.logformatter.WSLog;
import com.synergygb.logformatter.WSLogOrigin;
import com.synergygb.webAPI.layerCommunication.DataLayerCommunication;
import com.synergygb.webAPI.layerCommunication.LayerDataObject;
import com.synergygb.webAPI.layerCommunication.exceptions.LayerCommunicationException;
import com.synergygb.webAPI.layerCommunication.exceptions.LayerDataObjectParseException;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import com.synergygb.billeteravirtual.params.GenericParams;
import com.synergygb.webAPI.layerCommunication.exceptions.LayerDataObjectToObjectParseException;
import java.util.ArrayList;
import java.util.logging.Level;

/**
 * Billetera Virtual+ REST Web Services
 *
 * Class in charge of layer communication with Login SOAP services
 *
 * @author Synergy-GB
 * @author Alejandro Carpio
 * @author John Crespo <john.crespo@synergy-gb.com>
 * @author Mauricio Chirino <mauricio.chirino@synergy-gb.com>
 * @version 1.0
 */
public class RegistrationPOSTCommunication extends DataLayerCommunication {

    private static final Logger logger = Logger.getLogger(RegistrationPOSTCommunication.class);
    private GenericMemcachedConnector cacheConnector;
    WSLog wsLog = new WSLog("Communication RegistrationPOSTCommunication");
    static ConsoleAppender conappender = new ConsoleAppender(new PatternLayout());

    public RegistrationPOSTCommunication(GenericMemcachedConnector cacheConnector) {
        this.cacheConnector = cacheConnector;
        logger.addAppender(conappender);
    }

    @Override
    public LayerDataObject callRemoteLayer(LayerDataObject ldo) throws LayerCommunicationException {
        //------------------------------------------------------------------
        // Declaring parsing variables
        //------------------------------------------------------------------
        RegistrationParamsModel loginModel = null;
        LayerDataObject ldoResponse;
        UserInfo info = null;
        //---------------------------------------------------------------------
        // Parsing the request parameters.
        //---------------------------------------------------------------------
        try {
            loginModel = (RegistrationParamsModel) ldo.toObject(RegistrationParamsModel.class);
        } catch (LayerDataObjectToObjectParseException ex) {
            java.util.logging.Logger.getLogger(LoginPOSTCommunication.class.getName()).log(Level.SEVERE, "Ocurrio un problema con el parseo de los parametros ", ex);
            throw new LayerCommunicationException();
        }
        //Parsing response
        info = initLoginInfo(loginModel);
        try {
            ldoResponse = LayerDataObject.buildFromObject(info);
        } catch (LayerDataObjectParseException ex) {
            logger.debug(wsLog.setParams(WSLogOrigin.REMOTE_CLIENT, ErrorID.LDO_TO_OBJECT.getId(), "Ocurrio un error obteniendo el LDO "), ex);
            throw new LayerCommunicationException();
        }
        return ldoResponse;
    }

    //------ Respuesta ---------
    private UserInfo initLoginInfo(RegistrationParamsModel registrationModel) throws PreexistingUserException {
        UserInfo info = new UserInfo();
        try {
            User respuesta = (User) cacheConnector.get(GenericParams.USER, registrationModel.getCi());
            if (respuesta == null) {
                activaUser(registrationModel);
            } else {
                Wallet active = (Wallet) cacheConnector.get(GenericParams.WALLET, registrationModel.getCi());
                if (active.getFlag().equals("0")) {
                    activaUser(registrationModel);
                } else {
                    throw new PreexistingUserException("No puedes sobreescribir un usuario");
                }
            }
        } catch (CouchbaseOperationException ex) {
            logger.warn(wsLog.setParams(WSLogOrigin.INTERNAL_WS, ErrorID.NO_ERROR.getId(), "No se pudo consultar los instrumentos para el usuario: " + registrationModel.getCi()));
        }
        //Parsing userInfo
        info.setStime(String.valueOf(AppXMLConfiguration.MODULE_COUCHBASE_SESSION_TIMEOUT));
        info.setInstrumentos(new ArrayList<Card>());
        //returning response
        return info;
    }
    
    private void activaUser(RegistrationParamsModel registrationModel) throws CouchbaseOperationException{
        cacheConnector.save(GenericParams.USER, new User(registrationModel.getPass()), registrationModel.getCi());
        cacheConnector.save(GenericParams.WALLET, new Wallet(), registrationModel.getCi());
    }
}
