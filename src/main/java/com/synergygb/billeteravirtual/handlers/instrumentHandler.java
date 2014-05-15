/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.synergygb.billeteravirtual.handlers;

import com.synergygb.billeteravirtual.core.config.exceptions.BackendErrorStatus;
import com.synergygb.billeteravirtual.core.config.exceptions.BackendException;
import com.synergygb.billeteravirtual.core.exceptions.AuthenticationException;
import com.synergygb.billeteravirtual.notificacion.communication.utils.Communication;
import com.synergygb.billeteravirtual.core.config.AppXMLConfiguration;
import com.synergygb.billeteravirtual.core.connector.cache.CouchbasePool;
import com.synergygb.billeteravirtual.core.connector.cache.models.CacheBucketType;
import com.synergygb.billeteravirtual.core.exceptions.CouchbaseOperationException;
import com.synergygb.billeteravirtual.core.models.config.ErrorID;
import com.synergygb.billeteravirtual.core.services.handler.utils.HandlerUtils;
import com.synergygb.billeteravirtual.core.connector.cache.GenericMemcachedConnector;
import com.synergygb.billeteravirtual.notificacion.models.Card;
import com.synergygb.billeteravirtual.notificacion.models.Transaction;
import com.synergygb.billeteravirtual.notificacion.models.Transactions;
import com.synergygb.billeteravirtual.notificacion.services.models.InstrumentParamsModel;
import com.synergygb.billeteravirtual.params.GenericParams;
import com.synergygb.logformatter.WSLog;
import com.synergygb.logformatter.WSLogOrigin;
import com.synergygb.webAPI.handlers.WebServiceHandler;
import com.synergygb.webAPI.handlers.WebServiceStatus;
import com.synergygb.webAPI.handlers.WebServiceStatusType;
import com.synergygb.webAPI.layerCommunication.DataLayerCommunicationType;
import com.synergygb.webAPI.layerCommunication.LayerDataObject;
import com.synergygb.webAPI.layerCommunication.WebServiceRequest;
import com.synergygb.webAPI.layerCommunication.WebServiceResponse;
import com.synergygb.webAPI.layerCommunication.exceptions.LayerCommunicationException;
import com.synergygb.webAPI.layerCommunication.exceptions.LayerDataObjectParseException;
import com.synergygb.webAPI.layerCommunication.exceptions.LayerDataObjectToObjectParseException;
import com.synergygb.webAPI.parameters.WebServiceParameters;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

/**
 *
 *  * @author Mauricio Chirino <mauricio.chirino@synergy-gb.com>
 */
public class instrumentHandler extends WebServiceHandler {

    private static final Logger logger = Logger.getLogger(instrumentHandler.class);
    WSLog wsLog = new WSLog("Handler servicio login");
    static ConsoleAppender conappender = new ConsoleAppender(new PatternLayout());
    private final int type;
    private String ci;
    private String cookie;
    private String instrumentId;

    public instrumentHandler(int type, String ci, String instrumentId, String cookie) {
        this.type = type;
        this.ci = ci;
        this.cookie = cookie;
        this.instrumentId = instrumentId;
        logger.addAppender(conappender);
    }

    @Override
    public WebServiceStatus run(WebServiceRequest request, WebServiceResponse response) {
        //-----------------------------------------------------
        // Declaring parsing variables
        //-----------------------------------------------------
        WebServiceParameters params = request.getRequestBody();
        DataLayerCommunicationType communicationType = null;
        InstrumentParamsModel instrumentModel = null;
        //-----------------------------------------------------
        // Declaring connector 
        //-----------------------------------------------------
        GenericMemcachedConnector cacheConnector = null;
        //-----------------------------------------------------
        // Filtering communication type
        //-----------------------------------------------------
        communicationType = HandlerUtils.setCommunicationTypeFromParams(params);
        LayerDataObject loginLdo = null;
        try {
            loginLdo = LayerDataObject.buildFromWSParams(params);
            instrumentModel = (InstrumentParamsModel) loginLdo.toObject(InstrumentParamsModel.class);
        } catch (LayerDataObjectToObjectParseException ex) {
            logger.error(wsLog.setParams(WSLogOrigin.INTERNAL_WS, ErrorID.LDO_TO_OBJECT.getId(), "Ocurrio un error en el parseo de los parametros de login " + loginLdo), ex);
            return WebServiceStatus.buildStatus(WebServiceStatusType.INVALID_PARAMETERS_CONTAINER_FORMAT);
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
        Card responseCard = null;
        Transactions responseTransactions = null;
        try {
            switch (type) {
                case GenericParams.INSTRUMENT_ADD: // Añadirlo
                    responseCard = Communication.postInstrumentData(communicationType, instrumentModel, this.ci, cacheConnector);
                    break;
                case GenericParams.INSTRUMENT_CHECK:// Chequearlo
                    responseTransactions = Communication.getInstrumentData(communicationType, instrumentModel,this.ci, this.instrumentId, this.cookie, cacheConnector);
                    break;
                case GenericParams.INSTRUMENT_REMOVE:// Eliminarlo
                    Communication.deleteInstrumentData(communicationType, instrumentModel, this.ci, this.instrumentId, this.cookie, cacheConnector);
                    break;
            }
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
        }
        LayerDataObject responseLoginLDO = null;
        switch (type) {
            case GenericParams.INSTRUMENT_ADD: // Añadirlo
                try {
                    responseLoginLDO = LayerDataObject.buildFromObject(responseCard);
                    response.addParamFromLDO(GenericParams.CARD_RESPONSE, responseLoginLDO);
                } catch (LayerDataObjectParseException ex) {
                    logger.error(wsLog.setParams(WSLogOrigin.INTERNAL_WS, ErrorID.LDO_TO_OBJECT.getId(), "Error de parseo. "), ex);
                    return WebServiceStatus.buildStatus(WebServiceStatusType.UNEXPECTED_ERROR);
                }
                break;
            case GenericParams.INSTRUMENT_CHECK:// Chequearlo
                try {
                    responseLoginLDO = LayerDataObject.buildFromObject(responseTransactions);
                    response.addParamFromLDO(GenericParams.TRANSACTIONS_RESPONSE, responseLoginLDO);
                } catch (LayerDataObjectParseException ex) {
                    logger.error(wsLog.setParams(WSLogOrigin.INTERNAL_WS, ErrorID.LDO_TO_OBJECT.getId(), "Error de parseo. "), ex);
                    return WebServiceStatus.buildStatus(WebServiceStatusType.UNEXPECTED_ERROR);
                }
                break;
        }
        return WebServiceStatus.buildStatus(WebServiceStatusType.OK);
    }
}
