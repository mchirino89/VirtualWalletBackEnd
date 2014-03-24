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
package com.synergygb.billeteravirtual.notificacion.models.cache;

import com.synergygb.asegura.notificacion.models.Application;
import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Asegura+ REST Web Services
 * @author Synergy-GB
 * @author John Crespo John Crespo <john.crespo@synergy-gb.com>
 * @version 1.0
 */
@XmlRootElement
public class TransactionStat implements Serializable {
    /**
     * User session unique identifier
     */
    String cookieId;
    
    /**
     * User identifier
     */
    String userId;
    
    /**
     * Incremental identifier of the user session (for stats crossing)
     */
    String sessionStatId;
    
    /**
     * Transaction Identifier
     */
    String transactionStatId;
    
    /**
     * User session start time
     */
    String timestamp;
    /**
     * Transaction operation type
     */
    private Operation operation;
    /**
     * Application that executed the transaction
     */
    Application application;
    
    /**
     * Transaction final status
     */
    String status;
    
    public TransactionStat(){
    
    }

    public String getSessionStatId() {
        return sessionStatId;
    }

    public void setSessionStatId(String sessionStatId) {
        this.sessionStatId = sessionStatId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTransactionStatId() {
        return transactionStatId;
    }

    public void setTransactionStatId(String transactionStatId) {
        this.transactionStatId = transactionStatId;
    }
    
    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public String getCookieId() {
        return cookieId;
    }

    public void setCookieId(String cookieId) {
        this.cookieId = cookieId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return the operation
     */
    public Operation getOperation() {
        return operation;
    }

    /**
     * @param operation the operation to set
     */
    public void setOperation(Operation operation) {
        this.operation = operation;
    }
    
    
}
