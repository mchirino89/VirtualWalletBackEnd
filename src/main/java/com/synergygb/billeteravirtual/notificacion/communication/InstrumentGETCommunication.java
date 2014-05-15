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
package com.synergygb.billeteravirtual.notificacion.communication;

import com.synergygb.billeteravirtual.core.connector.cache.GenericMemcachedConnector;
import com.synergygb.billeteravirtual.core.exceptions.AuthenticationException;
import com.synergygb.billeteravirtual.core.exceptions.CouchbaseOperationException;
import com.synergygb.billeteravirtual.core.exceptions.NonExistingUser;
import com.synergygb.billeteravirtual.core.models.config.ErrorID;
import com.synergygb.billeteravirtual.notificacion.models.*;
import com.synergygb.billeteravirtual.notificacion.services.models.InstrumentParamsModel;
import com.synergygb.billeteravirtual.notificacion.services.models.LoginParamsModel;
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
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Random;
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
public class InstrumentGETCommunication extends DataLayerCommunication {

    private static final Logger logger = Logger.getLogger(InstrumentGETCommunication.class);
    private GenericMemcachedConnector cacheConnector;
    WSLog wsLog = new WSLog("Communcation Login");
    static ConsoleAppender conappender = new ConsoleAppender(new PatternLayout());
    private String instrumentId, cookie, ci;

    public InstrumentGETCommunication(String ci, String instrumentId, String cookie, GenericMemcachedConnector cacheConnector) {
        this.ci = ci;
        this.instrumentId = instrumentId;
        this.cookie = cookie;
        this.cacheConnector = cacheConnector;
        logger.addAppender(conappender);
    }

    @Override
    public LayerDataObject callRemoteLayer(LayerDataObject ldo) throws LayerCommunicationException {
        //------------------------------------------------------------------
        // Declaring parsing variables
        //------------------------------------------------------------------
        LayerDataObject ldoResponse;
        Transactions info = null;
        if (!checkCookie()) {
            throw new AuthenticationException("Sesion inválida. Por favor autentíquese e intente de nuevo");
        }
        //Parsing response
        info = initAddInstInfo();
        try {
            ldoResponse = LayerDataObject.buildFromObject(info);
        } catch (LayerDataObjectParseException ex) {
            logger.debug(wsLog.setParams(WSLogOrigin.REMOTE_CLIENT, ErrorID.LDO_TO_OBJECT.getId(), "Ocurrio un error obteniendo el LDO "), ex);
            throw new LayerCommunicationException();
        }
        return ldoResponse;
    }

    private boolean checkCookie() {
        try {
            Session auxiliar = (Session) cacheConnector.get(GenericParams.SESSION, this.cookie);
            if (auxiliar.getCi().equals(this.ci)) {
                return true;
            }
        } catch (CouchbaseOperationException ex) {
            logger.warn(wsLog.setParams(WSLogOrigin.INTERNAL_WS, ErrorID.NO_ERROR.getId(), "No se pudo consultar los movimientos de la tarjeta : " + this.instrumentId));
        }
        return false;
    }

    //------ Respuesta ---------
    private Transactions initAddInstInfo() {
        Transactions info = null;
        //Getting transactions' info
        try {
            info = (Transactions) cacheConnector.get(GenericParams.TRANSACTIONS, this.instrumentId);
            if (info != null) {
                int index = 0;
                for (Transaction auxiliar : info.getTarjetas()) {
                    info.getTarjetas().get(index).setAmount(NumberFormat.getCurrencyInstance().format(Double.parseDouble(auxiliar.getAmount().replace(",", "."))).replaceAll("F.", "F "));
                    index++;
                }
            }
            else{
                System.out.println("sin transacciones");
                info = new Transactions(new ArrayList<Transaction>());
            }
        } catch (CouchbaseOperationException ex) {
            logger.warn(wsLog.setParams(WSLogOrigin.INTERNAL_WS, ErrorID.NO_ERROR.getId(), "No se pudo consultar los movimientos de la tarjeta : " + this.instrumentId));
        }
        return info;
    }
}
