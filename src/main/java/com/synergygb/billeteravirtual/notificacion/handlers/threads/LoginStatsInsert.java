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
import com.synergygb.billeteravirtual.notificacion.services.models.LoginParamsModel;
import com.synergygb.logformatter.WSLog;
import com.synergygb.logformatter.WSLogOrigin;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

/**
 * Asegura+ REST Web Services
 * @author Synergy-GB
 * @author John Crespo John Crespo <john.crespo@synergy-gb.com>
 */
public class LoginStatsInsert implements Runnable {

    private static final Logger logger = Logger.getLogger(LoginStatsInsert.class);
    WSLog wsLog = new WSLog("Handler Hilo Stats Login");
    static ConsoleAppender conappender = new ConsoleAppender(new PatternLayout());

    String cookie;
    LoginParamsModel loginModel;
    long totalServiceTime;
    GenericMemcachedConnector connector;

    public LoginStatsInsert(String cookie, LoginParamsModel loginModel, long totalServiceTime, GenericMemcachedConnector connector) {
        this.cookie = cookie;
        this.loginModel = loginModel;
        this.totalServiceTime = totalServiceTime;
        this.connector = connector;
    }

    @Override
    public void run() {
        logger.info(wsLog.setParams(WSLogOrigin.INTERNAL_WS, com.synergygb.billeteravirtual.core.models.config.ErrorID.NO_ERROR.getId(), "Activando hilo de almacenamiento estadistico " + loginModel.getCi() + ", cookie: " + cookie));
        //put Session Stat
        try {
            connector.save(CouchbaseKeyPrefix.USER_SESSION_PREFIX, loginModel, cookie);
        } catch (CouchbaseOperationException ex) {
            logger.warn(wsLog.setParams(WSLogOrigin.INTERNAL_WS, ErrorID.NO_ERROR.getId(), "No se pudo almacenar la estadistica de session SessionStat para el usuario: " + loginModel.getCi() + ", cookie: " + cookie + ". "));
        } catch (Exception ex) {
            logger.warn(wsLog.setParams(WSLogOrigin.INTERNAL_WS, ErrorID.NO_ERROR.getId(), "No se pudo almacenar la estadistica de session SessionStat para el usuario: " + loginModel.getCi() + ", cookie: " + cookie + ". "));
        }
        
        //increment Session Count
        try {
            connector.getAndTouch(CouchbaseKeyPrefix.USER_SESSION_PREFIX, cookie);
        } catch (CouchbaseOperationException ex) {
            logger.warn(wsLog.setParams(WSLogOrigin.INTERNAL_WS, ErrorID.NO_ERROR.getId(), "No se pudo almacenar la estadistica de session SessionStat para el usuario: " + loginModel.getCi() + ", cookie: " + cookie + ". "));
        } catch (Exception ex) {
            logger.warn(wsLog.setParams(WSLogOrigin.INTERNAL_WS, ErrorID.NO_ERROR.getId(), "No se pudo almacenar la estadistica de session SessionStat para el usuario: " + loginModel.getCi() + ", cookie: " + cookie + ". "));
        }
        //set Local Login AverageTime
        try {
            Long lastAverage = (Long) connector.get(CouchbaseKeyPrefix.SERVICE_AVERAGE_TIME + CouchbaseKeyPrefix.LOCAL_LOGIN_SERVICE, cookie);
            connector.incr(CouchbaseKeyPrefix.SERVICE_AVERAGE_TIME_COUNTER + CouchbaseKeyPrefix.LOCAL_LOGIN_SERVICE);
            Long currentAverage = null;
            if (lastAverage == null) {
                currentAverage = totalServiceTime;
            } else {
                currentAverage = (totalServiceTime + lastAverage);
            }
            connector.save(CouchbaseKeyPrefix.SERVICE_AVERAGE_TIME + CouchbaseKeyPrefix.LOCAL_LOGIN_SERVICE, currentAverage, cookie);
        } catch (CouchbaseOperationException ex) {
            logger.warn(wsLog.setParams(WSLogOrigin.INTERNAL_WS, ErrorID.NO_ERROR.getId(), "No se pudo almacenar el tiempo promedio local de Login "),ex);
        }
    }
}
