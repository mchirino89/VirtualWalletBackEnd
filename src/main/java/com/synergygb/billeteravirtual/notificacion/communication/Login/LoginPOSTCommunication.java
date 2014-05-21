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
package com.synergygb.billeteravirtual.notificacion.communication.Login;

import com.synergygb.billeteravirtual.core.config.AppXMLConfiguration;
import com.synergygb.billeteravirtual.core.connector.cache.GenericMemcachedConnector;
import com.synergygb.billeteravirtual.core.exceptions.AuthenticationException;
import com.synergygb.billeteravirtual.core.exceptions.CouchbaseOperationException;
import com.synergygb.billeteravirtual.core.exceptions.NonExistingUser;
import com.synergygb.billeteravirtual.core.models.config.ErrorID;
import com.synergygb.billeteravirtual.notificacion.communication.exceptions.DisableWalletException;
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
import com.synergygb.billeteravirtual.params.GenericParams;
import java.util.ArrayList;

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
    private User respuesta;
    private String cookie;
    WSLog wsLog = new WSLog("Communication LoginPOSTCommunication");
    static ConsoleAppender conappender = new ConsoleAppender(new PatternLayout());

    public LoginPOSTCommunication(GenericMemcachedConnector cacheConnector,String cookie) {
        this.cacheConnector = cacheConnector;
        this.cookie = cookie;
        logger.addAppender(conappender);
    }

    @Override
    public LayerDataObject callRemoteLayer(LayerDataObject ldo) throws LayerCommunicationException {
        //------------------------------------------------------------------
        // Declaring parsing variables
        //------------------------------------------------------------------
        LoginParamsModel loginModel = null;
        respuesta = null;
        LayerDataObject ldoResponse;
        UserInfo info = null;
        //---------------------------------------------------------------------
        // Parsing the request parameters.
        //---------------------------------------------------------------------
        try {
            loginModel = (LoginParamsModel) ldo.toObject(LoginParamsModel.class);
            if(checkOTP(loginModel.getOtp())){
                throw new AuthenticationException("Problemas con la OTP generada por este dispositivo");
            }
            initInput(loginModel);
        } catch (LayerDataObjectToObjectParseException ex) {
            java.util.logging.Logger.getLogger(LoginPOSTCommunication.class.getName()).log(Level.SEVERE, "Ocurrio un problema con el parseo del login", ex);
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

    //------ Pregunta ----------
    private void initInput(LoginParamsModel loginModel) throws NonExistingUser {
        logger.info(wsLog.setParams(WSLogOrigin.INTERNAL_WS, ErrorID.NO_ERROR.getId(), "Consultando la existencia del usuario en la BD " + loginModel.getCi()));
        try {
            respuesta = (User) cacheConnector.get(GenericParams.USER, loginModel.getCi());
            if (respuesta == null) {
                throw new NonExistingUser("Usuario no registrado");
            } else {
                if (!respuesta.getPass().equals(loginModel.getPass())) {
                    throw new AuthenticationException("Password erroneo");
                }
                Wallet userWallet = (Wallet)cacheConnector.get(GenericParams.WALLET, loginModel.getCi());
                if (!userWallet.getFlag().equals("1")) {
                    throw new DisableWalletException("Billetera inactiva para este usuario");
                }
            }
        } catch (CouchbaseOperationException ex) {
            logger.warn(wsLog.setParams(WSLogOrigin.INTERNAL_WS, ErrorID.NO_ERROR.getId(), "No se pudo consultar el Login para el usuario " + loginModel.getCi()));
        } catch (AuthenticationException ex) {
            logger.warn(wsLog.setParams(WSLogOrigin.INTERNAL_WS, ErrorID.NO_ERROR.getId(), "Clave incorrecta para el usuario " + loginModel.getCi()));
            java.util.logging.Logger.getLogger(LoginPOSTCommunication.class.getName()).log(Level.SEVERE, null, "Clave incorrecta");
        } 
        catch (DisableWalletException ex) {
            logger.warn(wsLog.setParams(WSLogOrigin.INTERNAL_WS, ErrorID.NO_ERROR.getId(), "Billetera no activa para el usuario " + loginModel.getCi()));
            java.util.logging.Logger.getLogger(LoginPOSTCommunication.class.getName()).log(Level.SEVERE, null, "Billetera no activa");
        }
    }

    //------ Respuesta ---------
    private UserInfo initLoginInfo(LoginParamsModel loginModel) {
        UserInfo info = new UserInfo();
        //Getting card's info
        try {
            Instruments registradas = (Instruments) cacheConnector.get(GenericParams.INSTRUMENTS, loginModel.getCi());
            if (registradas == null) {
                info.setInstrumentos(new ArrayList<Card>());
            } else {
                ArrayList<Card> guardadas = new ArrayList<Card>();
                for (Instrument tmp : registradas.getTarjetas()) {
                    Card auxiliar = (Card) cacheConnector.get(GenericParams.CARD, tmp.getExternalId());
                    if(auxiliar.getActiva().equals("1"))// Mostrando sólo aquellas tarjetas activas.
                        guardadas.add(auxiliar);
                }
                info.setInstrumentos(guardadas);
            }
            cacheConnector.save(GenericParams.SESSION, new Session(loginModel.getCi()), this.cookie);
        } catch (CouchbaseOperationException ex) {
            logger.warn(wsLog.setParams(WSLogOrigin.INTERNAL_WS, ErrorID.NO_ERROR.getId(), "No se pudo consultar los instrumentos para el usuario: " + loginModel.getCi()));
        }
        //Parsing userInfo
        info.setStime(String.valueOf(AppXMLConfiguration.MODULE_COUCHBASE_SESSION_TIMEOUT));
        //returning response
        return info;
    }
    
    //------ Autenticación de código OTP -------
    private boolean checkOTP(String otp)
    {
        //Aqui falta la validación del código con el método de Gemalto
        return otp.isEmpty() || otp.length() == 0;
    }
}