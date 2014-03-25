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
import com.synergygb.billeteravirtual.notificacion.models.cache.Operation;
import com.synergygb.billeteravirtual.notificacion.models.cache.TransactionStat;
import com.synergygb.billeteravirtual.notificacion.models.utils.OperationType;
import com.synergygb.billeteravirtual.notificacion.services.models.LoginParamsModel;
import com.synergygb.logformatter.WSLog;
import com.synergygb.logformatter.WSLogOrigin;
import java.util.Date;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

/**
 * Billetera Virtual+ REST Web Services
 * @author Synergy-GB
 * @author John Crespo John Crespo <john.crespo@synergy-gb.com>
 * @version 1.0
 */
public class LoginFailedStatsInsert implements Runnable {

    private static final Logger logger = Logger.getLogger(LoginFailedStatsInsert.class);
    WSLog wsLog = new WSLog("Handler Hilo Login Fallido");
    static ConsoleAppender conappender = new ConsoleAppender(new PatternLayout());

    String cookie;
    LoginParamsModel loginModel;
    GenericMemcachedConnector connector;

    public LoginFailedStatsInsert(String cookie, LoginParamsModel loginModel, GenericMemcachedConnector connector) {
        this.cookie = cookie;
        this.loginModel = loginModel;
        this.connector = connector;
    }

    @Override
    public void run() {
        logger.info(wsLog.setParams(WSLogOrigin.INTERNAL_WS, com.synergygb.billeteravirtual.core.models.config.ErrorID.NO_ERROR.getId(), "Almacenando estadísticas de Login fallido" + loginModel.getCi() + ", cookie: " + cookie));
        try {
            TransactionStat ts = new TransactionStat();
            long date = new Date().getTime();
            Operation operation = new Operation();
            operation.setOperationName(OperationType.LOGIN.toString());
            operation.setType(OperationType.LOGIN.toString());

            //ts.setApplication(Application.getApplicationFromParams(loginModel.getApp()));
            ts.setCookieId(cookie);
            ts.setOperation(operation);
            ts.setStatus("false");
            ts.setTimestamp(String.valueOf(date));
            
            Long transactionCount = (Long) connector.incr(CouchbaseKeyPrefix.TOTAL_TRANSACTION_COUNT);
            ts.setTransactionStatId(String.valueOf(transactionCount));
            
            String sessionStatId = (String) connector.get(CouchbaseKeyPrefix.USER_SESSION_STAT_ID, cookie);
            if (sessionStatId != null) {
                ts.setSessionStatId(String.valueOf(sessionStatId));
            }            
            connector.save(CouchbaseKeyPrefix.USER_PREFIX + CouchbaseKeyPrefix.USER_TRANSACTION_STAT + transactionCount,ts,cookie);
        } catch (CouchbaseOperationException ex) {
            logger.warn(wsLog.setParams(WSLogOrigin.INTERNAL_WS, com.synergygb.billeteravirtual.core.models.config.ErrorID.NO_ERROR.getId(), "No se pudo almacenar la estadistica fallida de transacción Login para el usuario" + loginModel.getCi() + ", cookie: " + cookie));
        } catch (Exception ex) {
            logger.warn(wsLog.setParams(WSLogOrigin.INTERNAL_WS, com.synergygb.billeteravirtual.core.models.config.ErrorID.NO_ERROR.getId(), "No se pudo almacenar la estadistica fallida de transacción Login para el usuario" + loginModel.getCi() + ", cookie: " + cookie));
        }
    }
}
