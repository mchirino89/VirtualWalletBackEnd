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
package com.synergygb.billeteravirtual.notificacion.communication;

import com.synergygb.billeteravirtual.core.models.config.ErrorID;
import com.synergygb.billeteravirtual.core.config.AppXMLConfiguration;
import com.synergygb.billeteravirtual.core.connector.cache.GenericMemcachedConnector;
import com.synergygb.billeteravirtual.core.connector.cache.models.CouchbaseKeyPrefix;
import com.synergygb.billeteravirtual.core.exceptions.CouchbaseOperationException;
import com.synergygb.billeteravirtual.core.models.config.ErrorID;
import com.synergygb.billeteravirtual.notificacion.models.*;
import com.synergygb.billeteravirtual.notificacion.models.cache.UserSession;
import com.synergygb.billeteravirtual.notificacion.services.models.LoginParamsModel;
import com.synergygb.logformatter.WSLog;
import com.synergygb.logformatter.WSLogOrigin;
import com.synergygb.webAPI.layerCommunication.DataLayerCommunication;
import com.synergygb.webAPI.layerCommunication.LayerDataObject;
import com.synergygb.webAPI.layerCommunication.exceptions.LayerCommunicationException;
import com.synergygb.webAPI.layerCommunication.exceptions.LayerDataObjectParseException;
import com.synergygb.webAPI.layerCommunication.exceptions.LayerDataObjectToObjectParseException;
import java.util.logging.Level;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

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
public class LoginPOSTCommunication extends DataLayerCommunication {

    private static final Logger logger = Logger.getLogger(LoginPOSTCommunication.class);
    private GenericMemcachedConnector cacheConnector;
    WSLog wsLog = new WSLog("Communcation Login");
    static ConsoleAppender conappender = new ConsoleAppender(new PatternLayout());

    public LoginPOSTCommunication(GenericMemcachedConnector cacheConnector) {
        this.cacheConnector = cacheConnector;
        logger.addAppender(conappender);
    }

    @Override
    public LayerDataObject callRemoteLayer(LayerDataObject ldo) throws LayerCommunicationException {
        //------------------------------------------------------------------
        // Declaring parsing variables
        //------------------------------------------------------------------
        LoginParamsModel loginModel = null;
        LayerDataObject ldoResponse;
        UserInfo info = null;
        //---------------------------------------------------------------------
        // Parsing the request parameters.
        //---------------------------------------------------------------------
        try {
            loginModel = (LoginParamsModel) ldo.toObject(LoginParamsModel.class);
            if(!initInput(loginModel)){
                return LayerDataObject.buildFromObject(info);//retorno una respuesta vacía en caso que no exista el usuario en la BD
            }
        } catch (LayerDataObjectToObjectParseException ex) {
            java.util.logging.Logger.getLogger(LoginPOSTCommunication.class.getName()).log(Level.SEVERE, "Ocurrio un problema con el parseo del login", ex);
        } catch (LayerDataObjectParseException ex) {
            java.util.logging.Logger.getLogger(LoginPOSTCommunication.class.getName()).log(Level.SEVERE, "Ocurrio un error obteniendo el LDO del login vacío", ex);
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

    //------ Pregunta ----------
    private boolean initInput(LoginParamsModel loginModel) {
        logger.info(wsLog.setParams(WSLogOrigin.INTERNAL_WS, ErrorID.NO_ERROR.getId(), "Consultando la existencia del usuario en la BD" + loginModel.getCi() ));
        try {
            String user = (String) cacheConnector.get("user-"+loginModel.getCi());
            System.out.println("retornado: "+user);
            if(user.equals("{\"error\":\"not_found\",\"reason\":\"missing\"}") || user == null){
                return false;
            }
        } catch (CouchbaseOperationException ex) {
            logger.warn(wsLog.setParams(WSLogOrigin.INTERNAL_WS, ErrorID.NO_ERROR.getId(), "No se pudo consultar el Login para el usuario" + loginModel.getCi()));
        }
        return true;
    }

    //------ Respuesta ---------
    private UserInfo initLoginInfo(LoginParamsModel loginModel) {
        UserInfo info = new UserInfo();
        UserSession session = new UserSession();

        //Parsing session
        session.setPassword(loginModel.getPass());
        session.setLoginCi(loginModel.getCi());

        //Parsing userInfo
        //----- Consulta para traerme todas los instrumentos de un determinado usuario
        info.setSession(session);
        info.setStime(String.valueOf(AppXMLConfiguration.MODULE_COUCHBASE_SESSION_TIMEOUT));
        //returning response
        return info;
    }
    /*
     private void validateCommunicationStatus(LoginResponseE response) throws LayerCommunicationException {
     Result result = response.getLoginResponse().get_return().getResult();

     switch (result.getOperationCode()) {
     case 1:
     logger.debug(wsLog.setParams(WSLogOrigin.INTERNAL_WS, ErrorID.INVALID_DATA.getId(), "Ocurrio un error con el parametro tipo de documento"));
     throw new LayerCommunicationException();
     case 2:
     logger.debug(wsLog.setParams(WSLogOrigin.INTERNAL_WS, ErrorID.INVALID_DATA.getId(), "Ocurrio un error con el parametro ci"));
     throw new LayerCommunicationException();
     case 3:
     logger.debug(wsLog.setParams(WSLogOrigin.INTERNAL_WS, ErrorID.INVALID_DATA.getId(), "Ocurrio un error con el parametro password"));
     throw new LayerCommunicationException();
     case 4:
     logger.debug(wsLog.setParams(WSLogOrigin.INTERNAL_WS, ErrorID.INVALID_DATA.getId(), "contrasena invalida"));
     throw new AuthenticationException();
     case 5:
     logger.debug(wsLog.setParams(WSLogOrigin.INTERNAL_WS, ErrorID.INVALID_DATA.getId(), "El certificado no existe"));
     throw new LayerCommunicationException();
     case 6:
     logger.debug(wsLog.setParams(WSLogOrigin.INTERNAL_WS, ErrorID.INVALID_DATA.getId(), "El usuario no existe"));
     throw new NonExistingUser();
     case 99:
     logger.debug(wsLog.setParams(WSLogOrigin.INTERNAL_WS, ErrorID.INVALID_DATA.getId(), "Ocurrio un error inesperado"));
     throw new LayerCommunicationException();
     }
     }
     */
}
