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
package com.synergygb.billeteravirtual.handlers;

import com.synergygb.asegura.core.config.AppXMLConfiguration;
import com.synergygb.asegura.core.config.exceptions.BackendErrorStatus;
import com.synergygb.asegura.core.config.exceptions.BackendException;
import com.synergygb.asegura.core.connector.cache.CouchbasePool;
import com.synergygb.asegura.core.connector.cache.GenericMemcachedConnector;
import com.synergygb.asegura.core.connector.cache.models.CacheBucketType;
import com.synergygb.asegura.core.connector.cache.models.CouchbaseKeyPrefix;
import com.synergygb.asegura.core.exceptions.AuthenticationException;
import com.synergygb.asegura.core.exceptions.CouchbaseOperationException;
import com.synergygb.asegura.core.models.config.ErrorID;
import com.synergygb.asegura.core.services.handler.utils.HandlerUtils;
import com.synergygb.asegura.core.services.params.utils.ParamProcessorUtil;
import com.synergygb.asegura.core.utils.CookieUtils;
import com.synergygb.asegura.core.validations.ParamsValidator;
import com.synergygb.asegura.notificacion.communication.utils.Communication;
import com.synergygb.asegura.notificacion.handlers.threads.LoginFailedStatsInsert;
import com.synergygb.asegura.notificacion.handlers.threads.LoginStatsInsert;
import com.synergygb.asegura.notificacion.services.models.LoginParamsModel;
import com.synergygb.asegura.notificacion.services.utils.ServicesParams;
import com.synergygb.asegura.notificacion.services.utils.SessionThreadPool;
import com.synergygb.asegura.notificacion.services.utils.StatsThreadPool;
import com.synergygb.billeteravirtual.UserInfo;

import com.synergygb.logformatter.WSLog;
import com.synergygb.logformatter.WSLogOrigin;
import com.synergygb.webAPI.handlers.WebServiceHandler;
import com.synergygb.webAPI.handlers.WebServiceStatus;
import com.synergygb.webAPI.handlers.WebServiceStatusType;
import com.synergygb.webAPI.handlers.exceptions.InvalidDataException;
import com.synergygb.webAPI.layerCommunication.DataLayerCommunicationType;
import com.synergygb.webAPI.layerCommunication.LayerDataObject;
import com.synergygb.webAPI.layerCommunication.WebServiceRequest;
import com.synergygb.webAPI.layerCommunication.WebServiceResponse;
import com.synergygb.webAPI.layerCommunication.exceptions.LayerCommunicationException;
import com.synergygb.webAPI.layerCommunication.exceptions.LayerDataObjectParseException;
import com.synergygb.webAPI.layerCommunication.exceptions.LayerDataObjectToObjectParseException;
import com.synergygb.webAPI.parameters.ParametersMediaType;
import com.synergygb.webAPI.parameters.WSParamObject;
import com.synergygb.webAPI.parameters.WebServiceParameters;
import com.synergygb.webAPI.parameters.WebServiceParametersFactory;
import com.synergygb.webAPI.parameters.exceptions.MissingParameterException;
import com.synergygb.billeteravirtual.params.LoginInputParams;
import java.util.Date;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

/**
 * Asegura+ REST Web Services
 *
 * @author Synergy-GB
 * @author John Crespo John Crespo <john.crespo@synergy-gb.com>
 * @author Alejandro Carpio <alejandro.carpio@synergy-gb.com>
 * @version 0.2
 */
public class LoginPOSTHandler extends WebServiceHandler {

    private static final Logger logger = Logger.getLogger(LoginPOSTHandler.class);
    WSLog wsLog = new WSLog("Handler servicio login");
    static ConsoleAppender conappender = new ConsoleAppender(new PatternLayout());

    public LoginPOSTHandler() {
        logger.addAppender(conappender);
    }

    @Override
    public WebServiceStatus run(WebServiceRequest request, WebServiceResponse response) {

        logger.info(wsLog.setParams(WSLogOrigin.INTERNAL_WS, ErrorID.NO_ERROR.getId(), "Inicio handler de login"));

        //-----------------------------------------------------
        // Declaring parsing variables
        //-----------------------------------------------------
        String cookie = null;
        WebServiceParameters params = request.getRequestBody();
        DataLayerCommunicationType communicationType;
        LoginParamsModel loginModel = null;
        long totalServiceTime = new Date().getTime();

        //-----------------------------------------------------
        // Declaring connector variables
        //-----------------------------------------------------
        GenericMemcachedConnector statsConnector = null;
        GenericMemcachedConnector cacheConnector = null;

        //-----------------------------------------------------
        // Filtering communication type
        //-----------------------------------------------------
        communicationType = HandlerUtils.setCommunicationTypeFromParams(params);

        //-----------------------------------------------------
        // Validating mandatory params
        //-----------------------------------------------------
        logger.info(wsLog.setParams(WSLogOrigin.INTERNAL_WS, ErrorID.NO_ERROR.getId(), "Inicio de parseo de parametros de request"));

        LayerDataObject loginLdo = null;
        try {
            ParamProcessorUtil.checkParamsExistence(params, LoginInputParams.MANDATORY_LOGIN_FIELDS);
            loginLdo = LayerDataObject.buildFromWSParams(params);
            loginModel = (LoginParamsModel) loginLdo.toObject(LoginParamsModel.class);
            ParamsValidator.validateLogin(loginModel.getCi(), loginModel.getPass(), loginModel.getApp().getPlatform(), loginModel.getType());
        } catch (MissingParameterException ex) {
            logger.error(wsLog.setParams(WSLogOrigin.INTERNAL_WS, ErrorID.MISSING_PARAMETER.getId(), "Ocurrio un error, faltan parametros de entrada " + loginLdo), ex);
            return WebServiceStatus.buildStatus(WebServiceStatusType.MISSING_PARAMETER);
        } catch (LayerDataObjectToObjectParseException ex) {
            logger.error(wsLog.setParams(WSLogOrigin.INTERNAL_WS, ErrorID.LDO_TO_OBJECT.getId(), "Ocurrio un error en el parseo de los parametros de login " + loginLdo), ex);
            return WebServiceStatus.buildStatus(WebServiceStatusType.INVALID_PARAMETERS_CONTAINER_FORMAT);
        } catch (InvalidDataException ex) {
            logger.error(wsLog.setParams(WSLogOrigin.INTERNAL_WS, ErrorID.INVALID_DATA.getId(), "Error en la validacion de parametros de entrada. Mensaje: "), ex);
            return WebServiceStatus.buildStatus(ex);
        }

        //---------------------------------------------------------------------
        // Initializing stats connector
        //---------------------------------------------------------------------
        try {
            statsConnector = new GenericMemcachedConnector(AppXMLConfiguration.MODULE_STATS_OBJECTS_SECS, CouchbasePool.getPool(CacheBucketType.STATS_BUCKET));
        } catch (CouchbaseOperationException ex) {
            logger.warn(wsLog.setParams(WSLogOrigin.INTERNAL_WS, ErrorID.NO_ERROR.getId(), "No se pudo crear el cache connector"), ex);
            return WebServiceStatus.buildStatus(WebServiceStatusType.DB_ACCESS_ERROR);
        }
        //---------------------------------------------------------------------
        // Initializing cache connector
        //---------------------------------------------------------------------
        try {
            cacheConnector = new GenericMemcachedConnector(AppXMLConfiguration.MODULE_CACHE_OBJECTS_SECS, CouchbasePool.getPool(CacheBucketType.SESSION_BUCKET));
        } catch (CouchbaseOperationException ex) {
            logger.warn(wsLog.setParams(WSLogOrigin.INTERNAL_WS, ErrorID.NO_ERROR.getId(), "No se pudo crear el cache connector"), ex);
            return WebServiceStatus.buildStatus(WebServiceStatusType.DB_ACCESS_ERROR);
        }
        //--------------------------------------------------------
        // Establishing Communication with remote layer for login
        //--------------------------------------------------------
        logger.info(wsLog.setParams(WSLogOrigin.INTERNAL_WS, ErrorID.NO_ERROR.getId(), "Iniciando comunicacion con la capa remota"));
        UserInfo responseLogin = null;
        boolean commOk = false;

        try {
            cookie = CookieUtils.calculateCookieId(loginModel.getCi());
            responseLogin = Communication.postLoginData(communicationType, loginModel);
            commOk = true;
        } catch (AuthenticationException ex) {
            logger.debug(wsLog.setParams(WSLogOrigin.REMOTE_CLIENT, ErrorID.LAYER_COMMUNICATION.getId(), "Contrasena invalida"), ex);
            return WebServiceStatus.buildStatus(WebServiceStatusType.AUTHENTICATION_ERROR);
        } catch (BackendException ex) {
            logger.error(wsLog.setParams(WSLogOrigin.INTERNAL_WS, ErrorID.LAYER_COMMUNICATION.getId(), "Error de backend "), ex);
            return WebServiceStatus.buildStatus(new BackendErrorStatus(BackendErrorStatus.STATUS_CODE + "_" + ex.getMessage()));
        } catch (LayerCommunicationException ex) {
            logger.error(wsLog.setParams(WSLogOrigin.INTERNAL_WS, ErrorID.LAYER_COMMUNICATION.getId(), "Error de comunicacion. "), ex);
            return WebServiceStatus.buildStatus(ex);
        } catch (LayerDataObjectToObjectParseException ex) {
            logger.error(wsLog.setParams(WSLogOrigin.INTERNAL_WS, ErrorID.LDO_TO_OBJECT.getId(), "Error de parseo. "), ex);
            return WebServiceStatus.buildStatus(WebServiceStatusType.LAYER_COMMUNICATION_ERROR);
        } catch (LayerDataObjectParseException ex) {
            logger.error(wsLog.setParams(WSLogOrigin.INTERNAL_WS, ErrorID.LDO_TO_OBJECT.getId(), "Error de parseo. "), ex);
            return WebServiceStatus.buildStatus(WebServiceStatusType.LAYER_COMMUNICATION_ERROR);
        } finally {
            if (!commOk) {
                //insercion de estadisticas!
                LoginFailedStatsInsert loginFailedThread = new LoginFailedStatsInsert(cookie, loginModel, statsConnector);
                SessionThreadPool.sessionThreadPoolExecutor.execute(loginFailedThread);
            }
        }
        //----------------------------------------------------------
        // Setting session in cache
        //----------------------------------------------------------
        try {
            cacheConnector.saveExpiration(CouchbaseKeyPrefix.USER_SESSION_PREFIX, responseLogin.getSession(), cookie,AppXMLConfiguration.MODULE_COUCHBASE_SESSION_TIMEOUT);
            logger.debug(wsLog.setParams(WSLogOrigin.INTERNAL_WS, ErrorID.NO_ERROR.getId(), "Sesion creada en el cache with sesion prefix: "+CouchbaseKeyPrefix.USER_SESSION_PREFIX+ " and cookie: "+cookie));
        } catch (CouchbaseOperationException ex) {
            logger.error(wsLog.setParams(WSLogOrigin.INTERNAL_WS, ErrorID.DATABASE_OPERATION.getId(), "Error insertando la sesion del usuario. "), ex);
            return WebServiceStatus.buildStatus(WebServiceStatusType.DB_ACCESS_ERROR);
        }

        //------------------------------------------------------
        // Asigning login response
        //------------------------------------------------------
        logger.info(wsLog.setParams(WSLogOrigin.INTERNAL_WS, ErrorID.NO_ERROR.getId(), "Asignando response de la capa remota a response"));
        LayerDataObject responseLoginLDO = null;
        try {
            responseLoginLDO = LayerDataObject.buildFromObject(responseLogin);
        } catch (LayerDataObjectParseException ex) {
            logger.error(wsLog.setParams(WSLogOrigin.INTERNAL_WS, ErrorID.LDO_TO_OBJECT.getId(), "Ocurrio un error construyendo LDO de respuesta"));
            return WebServiceStatus.buildStatus(WebServiceStatusType.UNEXPECTED_ERROR);
        }
        response.setBodyFromLDO(responseLoginLDO);

        WSParamObject act = WebServiceParametersFactory.buildWSObject(ParametersMediaType.APPLICATION_JSON);
        response.addProperty(ServicesParams.COOKIE_PARAM, cookie);

        //------------------------------------------------------
        // Adding service stats to CouchBase
        //------------------------------------------------------
        logger.info(wsLog.setParams(WSLogOrigin.INTERNAL_WS, ErrorID.NO_ERROR.getId(), "Activando hilo de almacenamiento estadistico" + loginModel.getCi() + ", cookie: " + cookie));
        totalServiceTime = new Date().getTime() - totalServiceTime;

        LoginStatsInsert loginStatsThread = new LoginStatsInsert(cookie, loginModel, totalServiceTime, statsConnector);
        StatsThreadPool.statsThreadPoolExecutor.execute(loginStatsThread);

        logger.info(wsLog.setParams(WSLogOrigin.INTERNAL_WS, ErrorID.NO_ERROR.getId(), "Finalizando handler de servicio" + loginModel.getCi() + ", cookie: " + cookie));
        return WebServiceStatus.buildStatus(WebServiceStatusType.OK);
    }
}
