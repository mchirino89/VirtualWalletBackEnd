/**
 * 
 * IMPORTANTE: LEA ESTA NOTA CUIDADOSAMENTE. AL RECIBIR EL CÓDIGO FUENTE EL 
 * CLIENTE ACEPTA LOS TERMINOS Y CONDICIONES DESCRITOS EN EL ACUERDO DE LICENCIAMIENTO.
 * SI LA EMPRESA NO ESTÁ DE ACUERDO CON ESTE ACUERDO DE LICENCIAMIENTO DEL CÓDIGO 
 * FUENTE, NO DESCARGUE, INSTALE, EJECUTE, COPIE, TRANSFIERA, O EN CUALQUIER CASO
 * UTILICE EL CÓDIGO FUENTE.
 * 
 * Este ACUERDO DE LICENCIAMENTO DEL CÓDIGO FUENTE (en adelante, “EL ACUERDO”) 
 * se realiza entre Synergy Global Business, C.A. (en adelante, “Synergy-GB”) 
 * y el Licenciatario (en adelante, “el Cliente”).
 * 
 * Synergy-GB se reserva el derecho de modificar las condiciones descritas en 
 * EL ACUERDO en cualquier momento y sin previo aviso. 
 * EL ACUERDO está descrito y accesible a través de la dirección siguiente: 
 * http://www.synergy-gb.com/licenciamiento.pdf
 * 
 */
package com.synergygb.billeteravirtual.notificacion.handlers.threads;

import com.synergygb.billeteravirtual.core.connector.cache.GenericMemcachedConnector;
import com.synergygb.billeteravirtual.core.connector.cache.models.CouchbaseKeyPrefix;
import com.synergygb.billeteravirtual.core.exceptions.CouchbaseOperationException;
import com.synergygb.billeteravirtual.core.models.config.ErrorID;
import com.synergygb.billeteravirtual.notificacion.models.cache.UserSession;
import com.synergygb.logformatter.WSLog;
import com.synergygb.logformatter.WSLogOrigin;
import org.apache.log4j.Logger;

/**
 * Billetera Virtual+ REST Web Services
 * @author Synergy-GB
 * @author John Crespo John Crespo <john.crespo@synergy-gb.com>
 * @version 1.0
 */
public class LoginPreviousAndActiveSessionThread implements Runnable {

    private static final Logger logger = Logger.getLogger(LoginPreviousAndActiveSessionThread.class);
    WSLog wsLog = new WSLog("Handler Hilo Session ActiveInactive Login");

    UserSession userSession;
    String cookie;
    GenericMemcachedConnector connector;
    
    public LoginPreviousAndActiveSessionThread(UserSession userSession, String cookie, GenericMemcachedConnector connector) {
        this.userSession = userSession;
        this.cookie = cookie;
        this.connector = connector;
    }

    @Override
    public void run(){
        //------------------------------------------------------
        // Closing previously opened session
        //------------------------------------------------------ 
        logger.info(wsLog.setParams(WSLogOrigin.INTERNAL_WS, ErrorID.NO_ERROR.getId(), "Eliminando sesion anterior"));
        try {            
            connector.remove(CouchbaseKeyPrefix.USER_SESSION_PREFIX, cookie);
        } catch (CouchbaseOperationException ex) {
            logger.warn(wsLog.setParams(WSLogOrigin.INTERNAL_WS, ErrorID.NO_ERROR.getId(), "No se pudo eliminar la sesion"));
        }

        //------------------------------------------------------
        // Setting user session as active
        //------------------------------------------------------ 
        logger.info(wsLog.setParams(WSLogOrigin.INTERNAL_WS, com.synergygb.billeteravirtual.core.models.config.ErrorID.NO_ERROR.getId(), "Creando sesion activa del usuario"));
        try {
            connector.save(CouchbaseKeyPrefix.USER_SESSION_PREFIX, userSession, cookie);
        } catch (CouchbaseOperationException ex) {
            logger.warn(wsLog.setParams(WSLogOrigin.INTERNAL_WS, com.synergygb.billeteravirtual.core.models.config.ErrorID.NO_ERROR.getId(), "No se pudo registrar la sesion del usuario como activa: "));
        }
        logger.debug(wsLog.setParams(WSLogOrigin.INTERNAL_WS, com.synergygb.billeteravirtual.core.models.config.ErrorID.NO_ERROR.getId(), "Finalizada modificacion de datos de sesion activa inactiva: "));
    }
    
}
