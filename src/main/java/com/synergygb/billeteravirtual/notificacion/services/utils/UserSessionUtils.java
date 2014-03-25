
package com.synergygb.billeteravirtual.notificacion.services.utils;


import com.synergygb.billeteravirtual.core.config.AppXMLConfiguration;
import com.synergygb.billeteravirtual.core.connector.cache.CouchbasePool;
import com.synergygb.billeteravirtual.core.connector.cache.GenericMemcachedConnector;
import com.synergygb.billeteravirtual.core.connector.cache.models.CacheBucketType;
import com.synergygb.billeteravirtual.core.connector.cache.models.CouchbaseKeyPrefix;
import com.synergygb.billeteravirtual.core.exceptions.CouchbaseOperationException;
import com.synergygb.billeteravirtual.core.models.config.ErrorID;
import com.synergygb.billeteravirtual.notificacion.models.cache.UserSession;
import com.synergygb.logformatter.WSLog;
import com.synergygb.logformatter.WSLogOrigin;
import com.synergygb.webAPI.handlers.exceptions.DBAccessException;
import com.synergygb.webAPI.handlers.exceptions.SessionTimeoutException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import org.apache.log4j.Logger;
/**
 * Billetera Virtual+ REST Web Services
 * @author Synergy-GB
 * @author John Crespo John Crespo <john.crespo@synergy-gb.com>
 * @version 1.0
 */
public class UserSessionUtils {
    
    private static final Logger logger = Logger.getLogger(UserSessionUtils.class);
    static WSLog wsLog = new WSLog("Validar sesion");
    static GenericMemcachedConnector connector;
    
    public static UserSession validate(String cookie) throws SessionTimeoutException, DBAccessException, CouchbaseOperationException {
        //-----------------------------------------------------
        // Get User Session Info from DB
        //-----------------------------------------------------
        logger.info(wsLog.setParams(WSLogOrigin.INTERNAL_WS, ErrorID.NO_ERROR.getId(), "Obteniendo informacion de sesion del cache."));
        UserSession userSession = null;
        try {
            //------------------------------------------------------
            // Getting session from cache
            //------------------------------------------------------ 
            logger.info(wsLog.setParams(WSLogOrigin.INTERNAL_WS, ErrorID.NO_ERROR.getId(), "Buscando la sesion del usuario con cookie: "+cookie));
            connector = new GenericMemcachedConnector(AppXMLConfiguration.MODULE_CACHE_OBJECTS_SECS, CouchbasePool.getPool(CacheBucketType.SESSION_BUCKET));
            userSession = (UserSession) connector.getAndTouch(CouchbaseKeyPrefix.USER_SESSION_PREFIX, cookie);
            if (userSession == null) {
                logger.info(wsLog.setParams(WSLogOrigin.INTERNAL_WS, ErrorID.NO_ERROR.getId(), "Tiempo de sesion expirado. La sesion no fue encontrada en el cache."));
                throw new SessionTimeoutException();
            }
        } catch (CouchbaseOperationException ex) {
            logger.error(wsLog.setParams(WSLogOrigin.INTERNAL_WS, ErrorID.NO_ERROR.getId(), "Error obteniendo datos de sesion de couchbase. "),ex);
            throw new DBAccessException("Ha ocurrido un error en alguna operación de base de datos (SQL o Couchbase)");
        }

        // validating session
        if (isSessionOver(userSession.getLastUpdate())) {
            logger.info(wsLog.setParams(WSLogOrigin.INTERNAL_WS, ErrorID.NO_ERROR.getId(), "Tiempo de sesion expirado. "+userSession.getLastUpdate()+" vs "+new Date().toString()));
            throw new SessionTimeoutException();
        } else {
            // updating session time
            userSession.setLastUpdate(getCurrentSessionDate());
        }
        return userSession;
    }
    
    /**
     * Verifies session timeout limit
     */
    public static boolean isSessionOver(String lastUpdateTime){
        Date date = new Date();
        long actualTimeMillis = date.getTime();
        Date temp = null;
        try {
            temp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(lastUpdateTime);
        } catch (ParseException ex) {
            logger.info(wsLog.setParams(WSLogOrigin.INTERNAL_WS, ErrorID.NO_ERROR.getId(), "Tiempo de sesion expirado. "));
            java.util.logging.Logger.getLogger(UserSessionUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        //long lastUpdateMillis = Long.valueOf(lastUpdateTime);        
        long lastUpdateMillis = temp.getTime();
        if((Math.abs(actualTimeMillis-lastUpdateMillis)) > AppXMLConfiguration.MODULE_COUCHBASE_SESSION_TIMEOUT){
            return true;
        }else{
            return false;
        }    
    }    
    /**
     * Returns a valid String for session date
     */
    public static String getCurrentSessionDate() {
        Date date = new Date();
        return String.valueOf(date.getTime());
    }
}
