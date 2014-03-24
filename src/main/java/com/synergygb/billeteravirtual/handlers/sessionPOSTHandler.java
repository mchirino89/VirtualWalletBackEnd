/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.synergygb.billeteravirtual.handlers;

import com.synergygb.billeteravirtual.core.config.AppXMLConfiguration;
import com.synergygb.billeteravirtual.core.connector.cache.CouchbasePool;
import com.synergygb.billeteravirtual.core.connector.cache.models.CacheBucketType;
import com.synergygb.billeteravirtual.core.exceptions.CouchbaseOperationException;
import com.synergygb.billeteravirtual.core.models.config.ErrorID;
import com.synergygb.billeteravirtual.core.services.handler.utils.HandlerUtils;
import com.synergygb.billeteravirtual.core.connector.cache.GenericMemcachedConnector;
import com.synergygb.billeteravirtual.services.Session;
import com.synergygb.logformatter.WSLog;
import com.synergygb.logformatter.WSLogOrigin;
import com.synergygb.webAPI.handlers.WebServiceHandler;
import com.synergygb.webAPI.handlers.WebServiceStatus;
import com.synergygb.webAPI.handlers.WebServiceStatusType;
import com.synergygb.webAPI.layerCommunication.DataLayerCommunicationType;
import com.synergygb.webAPI.layerCommunication.WebServiceRequest;
import com.synergygb.webAPI.layerCommunication.WebServiceResponse;
import com.synergygb.webAPI.parameters.WebServiceParameters;
import java.util.Date;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

/**
 *
 * @author mauriciochirino
 */
public class sessionPOSTHandler extends WebServiceHandler {

    private static final Logger logger = Logger.getLogger(sessionPOSTHandler.class);
    WSLog wsLog = new WSLog("Handler servicio login");
    static ConsoleAppender conappender = new ConsoleAppender(new PatternLayout());

    public sessionPOSTHandler() {
        logger.addAppender(conappender);
    }

    @Override
    public WebServiceStatus run(WebServiceRequest request, WebServiceResponse response) {
        //-----------------------------------------------------
        // Declaring parsing variables
        //-----------------------------------------------------
        String cookie = null;
        WebServiceParameters params = request.getRequestBody();
        DataLayerCommunicationType communicationType;
        Session loginModel = null;
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
        //---------------------------------------------------------------------
        // Initializing stats connector
        //---------------------------------------------------------------------
        try {
            statsConnector = new GenericMemcachedConnector(AppXMLConfiguration.MODULE_STATS_OBJECTS_SECS, CouchbasePool.getPool(CacheBucketType.STATS_BUCKET));
        } catch (CouchbaseOperationException ex) {
            logger.warn(wsLog.setParams(WSLogOrigin.INTERNAL_WS, ErrorID.NO_ERROR.getId(), "No se pudo crear el cache connector"), ex);
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
        return WebServiceStatus.buildStatus(WebServiceStatusType.OK);
    }

}
