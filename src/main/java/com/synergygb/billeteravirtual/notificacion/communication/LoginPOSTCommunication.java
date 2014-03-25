/**
 *
 * IMPORTANTE: LEA ESTA NOTA CUIDADOSAMENTE. AL RECIBIR EL CÓDIGO FUENTE EL CLIENTE ACEPTA LOS TERMINOS Y
 * CONDICIONES DESCRITOS EN EL ACUERDO DE LICENCIAMIENTO. SI LA EMPRESA NO ESTÁ DE ACUERDO CON ESTE ACUERDO DE
 * LICENCIAMIENTO DEL CÓDIGO FUENTE, NO DESCARGUE, INSTALE, EJECUTE, COPIE, TRANSFIERA, O EN CUALQUIER CASO UTILICE
 * EL CÓDIGO FUENTE.
 *
 * Este ACUERDO DE LICENCIAMENTO DEL CÓDIGO FUENTE (en adelante, “EL ACUERDO”) se realiza entre Synergy Global
 * Business, C.A. (en adelante, “Synergy-GB”) y el Licenciatario (en adelante, “el Cliente”).
 *
 * Synergy-GB se reserva el derecho de modificar las condiciones descritas en EL ACUERDO en cualquier momento y sin
 * previo aviso. EL ACUERDO está descrito y accesible a través de la dirección siguiente:
 * http://www.synergy-gb.com/licenciamiento.pdf
 *
 */
package com.synergygb.billeteravirtual.notificacion.communication;

import com.synergygb.billeteravirtual.core.config.AppXMLConfiguration;
import com.synergygb.billeteravirtual.core.exceptions.AuthenticationException;
import com.synergygb.billeteravirtual.core.exceptions.NonExistingUser;
import com.synergygb.billeteravirtual.core.models.config.ErrorID;
import com.synergygb.billeteravirtual.notificacion.config.NotificationAppXMLConfiguration;
import com.synergygb.billeteravirtual.notificacion.models.*;
import com.synergygb.billeteravirtual.notificacion.models.cache.UserSession;
import com.synergygb.billeteravirtual.notificacion.services.models.LoginParamsModel;

import com.synergygb.billeteravirtual.notificacion.soap.stubs.AseguraNotificacionSOAPStub;
import com.synergygb.billeteravirtual.notificacion.soap.stubs.AseguraNotificacionSOAPStub.Login;
import com.synergygb.billeteravirtual.notificacion.soap.stubs.AseguraNotificacionSOAPStub.LoginE;
import com.synergygb.billeteravirtual.notificacion.soap.stubs.AseguraNotificacionSOAPStub.LoginResponseE;
import com.synergygb.billeteravirtual.notificacion.soap.stubs.AseguraNotificacionSOAPStub.Result;

import com.synergygb.logformatter.WSLog;
import com.synergygb.logformatter.WSLogOrigin;
import com.synergygb.webAPI.layerCommunication.DataLayerCommunication;
import com.synergygb.webAPI.layerCommunication.LayerDataObject;
import com.synergygb.webAPI.layerCommunication.exceptions.LayerCommunicationException;
import com.synergygb.webAPI.layerCommunication.exceptions.LayerDataObjectParseException;
import com.synergygb.webAPI.layerCommunication.exceptions.LayerDataObjectToObjectParseException;
import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.soap.SOAPFactory;
import org.apache.axis2.databinding.ADBException;
import org.apache.axis2.transport.http.HTTPConstants;
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
 * @author Mauricio Chirino <mauricio.chirino@synergy-gb.com>
 * @author John Crespo <john.crespo@synergy-gb.com>
 * @version 1.0
 */
public class LoginPOSTCommunication extends DataLayerCommunication {

    private static final Logger logger = Logger.getLogger(LoginPOSTCommunication.class);
    WSLog wsLog = new WSLog("Communcation Login");
    static ConsoleAppender conappender = new ConsoleAppender(new PatternLayout());

    public LoginPOSTCommunication() {
        logger.addAppender(conappender);
    }

    @Override
    public LayerDataObject callRemoteLayer(LayerDataObject ldo) throws LayerCommunicationException {

        //------------------------------------------------------------------
        // Declaring parsing variables
        //------------------------------------------------------------------
        LoginParamsModel loginModel = null;
        //----- Reemplzar por las respuestas Rest ---------
        LoginE request = new LoginE();
        LoginResponseE response = null;
        //------------------------------------------------------------------
        LayerDataObject ldoResponse;
        UserInfo info = null;
        //---------------------------------------------------------------------
        // Parsing the request parameters.
        //---------------------------------------------------------------------
        try {
            //Initializing stub
            AseguraNotificacionSOAPStub stub = new AseguraNotificacionSOAPStub(NotificationAppXMLConfiguration.SOAP_END_POINT);
            // Set timeouts (developer option)
            stub._getServiceClient().getOptions().setProperty(HTTPConstants.SO_TIMEOUT, new Integer(NotificationAppXMLConfiguration.SO_TIMEOUT));
            stub._getServiceClient().getOptions().setProperty(HTTPConstants.CONNECTION_TIMEOUT, new Integer(NotificationAppXMLConfiguration.CONNECTION_TIMEOUT));
            //Initializing the request param
            loginModel = (LoginParamsModel) ldo.toObject(LoginParamsModel.class);
            initInput(request, loginModel);
            //Calling the remote layer
            SOAPFactory factory = OMAbstractFactory.getSOAP12Factory();
            logger.debug(wsLog.setParams(WSLogOrigin.INTERNAL_WS, ErrorID.NO_ERROR.getId(), "REQUEST" + request.getOMElement(null, factory)));
            response = stub.login(request);
            logger.debug(wsLog.setParams(WSLogOrigin.INTERNAL_WS, ErrorID.NO_ERROR.getId(), "RESPONSE" + response.getOMElement(null, factory)));
            //Validating the response
            validateCommunicationStatus(response);
        } catch (LayerDataObjectToObjectParseException ex) {
            logger.debug(wsLog.setParams(WSLogOrigin.INTERNAL_WS, ErrorID.LDO_TO_OBJECT.getId(), "Ocurrio un error obteniendo el objeto a partir del LDO"), ex);
            throw new LayerCommunicationException();
        } catch (ADBException ex) {
            logger.debug(wsLog.setParams(WSLogOrigin.INTERNAL_WS, ErrorID.UNEXPECTED_ERROR.getId(), "Ocurrio un error obteniendo logueando los datos del request o el response, omitiendo"));
        } catch (AuthenticationException ex) {
            logger.debug(wsLog.setParams(WSLogOrigin.REMOTE_CLIENT, ErrorID.LAYER_COMMUNICATION.getId(), "Contrasena invalida"), ex);
            throw new AuthenticationException();
        } catch (Exception ex) {
            logger.debug(wsLog.setParams(WSLogOrigin.REMOTE_CLIENT, ErrorID.LAYER_COMMUNICATION.getId(), "Ocurrió un error en el login"), ex);
            throw new LayerCommunicationException();
        }
        //Parsing response
        info = initLoginInfo(response, loginModel);
        try {
            ldoResponse = LayerDataObject.buildFromObject(info);
        } catch (LayerDataObjectParseException ex) {
            logger.debug(wsLog.setParams(WSLogOrigin.REMOTE_CLIENT, ErrorID.LDO_TO_OBJECT.getId(), "Ocurrio un error obteniendo el LDO "), ex);
            throw new LayerCommunicationException();
        }

        return ldoResponse;
    }

    private UserInfo initLoginInfo(LoginResponseE response, LoginParamsModel loginModel) {
        UserInfo info = new UserInfo();
        UserSession session = new UserSession();
        //Parsing session
        session.setPassword(loginModel.getPass());
        session.setLoginCi(loginModel.getCi());
        
        //Parsing userInfo
        info.setSession(session);
        info.setStime(String.valueOf(AppXMLConfiguration.MODULE_COUCHBASE_SESSION_TIMEOUT));
        //returning response
        return info;
    }

    private void initInput(LoginE request, LoginParamsModel loginModel) {
        
        Login loginObject = new Login();
        loginObject.setCi(loginModel.getCi());
        loginObject.setPass(loginModel.getPass());
        request.setLogin(loginObject);
    }

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
}
