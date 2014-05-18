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
package com.synergygb.billeteravirtual.notificacion.communication.Transactions;

import com.synergygb.billeteravirtual.core.connector.cache.GenericMemcachedConnector;
import com.synergygb.billeteravirtual.core.exceptions.AuthenticationException;
import com.synergygb.billeteravirtual.core.exceptions.CouchbaseOperationException;
import com.synergygb.billeteravirtual.core.models.config.ErrorID;
import com.synergygb.billeteravirtual.notificacion.models.*;
import com.synergygb.billeteravirtual.notificacion.services.models.TransactionParamsModel;
import com.synergygb.logformatter.WSLog;
import com.synergygb.logformatter.WSLogOrigin;
import com.synergygb.webAPI.layerCommunication.DataLayerCommunication;
import com.synergygb.webAPI.layerCommunication.LayerDataObject;
import com.synergygb.webAPI.layerCommunication.exceptions.LayerCommunicationException;
import com.synergygb.webAPI.layerCommunication.exceptions.LayerDataObjectParseException;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import com.synergygb.billeteravirtual.params.GenericParams;
import com.synergygb.webAPI.layerCommunication.exceptions.LayerDataObjectToObjectParseException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;

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
public class TransactionsPOSTCommunication extends DataLayerCommunication {

    private static final Logger logger = Logger.getLogger(TransactionsPOSTCommunication.class);
    private GenericMemcachedConnector cacheConnector;
    WSLog wsLog = new WSLog("Communication TransactionsPOSTCommunication");
    static ConsoleAppender conappender = new ConsoleAppender(new PatternLayout());

    public TransactionsPOSTCommunication(GenericMemcachedConnector cacheConnector) {
        this.cacheConnector = cacheConnector;
        logger.addAppender(conappender);
    }

    @Override
    public LayerDataObject callRemoteLayer(LayerDataObject ldo) throws LayerCommunicationException {
        //------------------------------------------------------------------
        // Declaring parsing variables
        //------------------------------------------------------------------
        LayerDataObject ldoResponse;
        TransactionParamsModel transaction = null;

        try {
            transaction = (TransactionParamsModel) ldo.toObject(TransactionParamsModel.class);
            if (!checkCookie(transaction)) {
                throw new AuthenticationException("Sesion inválida. Por favor autentíquese e intente de nuevo");
            }
        } catch (LayerDataObjectToObjectParseException ex) {
            java.util.logging.Logger.getLogger(TransactionsPOSTCommunication.class.getName()).log(Level.SEVERE, "Ocurrio un problema con el parseo de la transacción ", ex);
            throw new LayerCommunicationException();
        }
        AddTransactionInfo(transaction);
        try {
            ldoResponse = LayerDataObject.buildFromObject("");
        } catch (LayerDataObjectParseException ex) {
            logger.debug(wsLog.setParams(WSLogOrigin.REMOTE_CLIENT, ErrorID.LDO_TO_OBJECT.getId(), "Ocurrio un error obteniendo el LDO "), ex);
            throw new LayerCommunicationException();
        }
        return ldoResponse;
    }

    //Verificando la validez de la cookie recibida
    private boolean checkCookie(TransactionParamsModel transaction) {
        try {
            Session auxiliar = (Session) cacheConnector.get(GenericParams.SESSION, transaction.getCookie());
            if (auxiliar.getCi().equals(transaction.getCi())) {
                return true;
            }
        } catch (CouchbaseOperationException ex) {
            logger.warn(wsLog.setParams(WSLogOrigin.INTERNAL_WS, ErrorID.NO_ERROR.getId(), "No se pudo verificar la validez del instrumento: " + transaction.getCardId()));
        }
        return false;
    }

    //------ Respuesta ---------
    private void AddTransactionInfo(TransactionParamsModel transaction) throws AuthenticationException {
        if (checkOTP(transaction.getOtp())) {
            try {
                Transactions history = (Transactions) cacheConnector.get(GenericParams.TRANSACTIONS, transaction.getCardId());
                if (history == null) {
                    history = new Transactions(new ArrayList<Transaction>());
                }
                String charge = NumberFormat.getCurrencyInstance().format(Double.parseDouble(transaction.getAmount().replace(",", "."))).replaceAll("F.", "F ");
                history.getTarjetas().add(new Transaction(new SimpleDateFormat("dd-MM-yyyy").format(new Date()), GenericParams.CHARGE + transaction.getEstablishment() ,charge));
                cacheConnector.save(GenericParams.TRANSACTIONS, history ,transaction.getCardId());
            } catch (CouchbaseOperationException ex) {
                logger.warn(wsLog.setParams(WSLogOrigin.INTERNAL_WS, ErrorID.NO_ERROR.getId(), "No se pudo consultar los movimientos de la tarjeta : " + transaction.getCardId()));
            }
        } else {
            throw new AuthenticationException("Código OTP no válido.");
        }
    }

    //------ Autenticación de código OTP -------
    private boolean checkOTP(String otp) {
        //Aqui falta la validación del código con el método de Gemalto
        return !otp.isEmpty() && otp.length() != 0;
    }
}
