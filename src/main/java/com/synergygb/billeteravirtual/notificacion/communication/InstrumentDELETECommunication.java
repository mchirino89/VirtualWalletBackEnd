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
import com.synergygb.billeteravirtual.core.models.config.ErrorID;
import com.synergygb.billeteravirtual.notificacion.models.*;
import com.synergygb.billeteravirtual.notificacion.services.models.InstrumentParamsModel;
import com.synergygb.logformatter.WSLog;
import com.synergygb.logformatter.WSLogOrigin;
import com.synergygb.webAPI.layerCommunication.DataLayerCommunication;
import com.synergygb.webAPI.layerCommunication.LayerDataObject;
import com.synergygb.webAPI.layerCommunication.exceptions.LayerCommunicationException;
import com.synergygb.webAPI.layerCommunication.exceptions.LayerDataObjectParseException;
import com.synergygb.webAPI.layerCommunication.exceptions.LayerDataObjectToObjectParseException;
import java.util.logging.Level;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import com.synergygb.billeteravirtual.params.GenericParams;
import com.synergygb.webAPI.layerCommunication.dummies.DummyGenerator;
import java.util.ArrayList;
import java.util.Random;

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
public class InstrumentDELETECommunication extends DataLayerCommunication {

    private static final Logger logger = Logger.getLogger(InstrumentDELETECommunication.class);
    private GenericMemcachedConnector cacheConnector;
    WSLog wsLog = new WSLog("Communcation Login");
    static ConsoleAppender conappender = new ConsoleAppender(new PatternLayout());
    private String ci, instrumentId, cookie, CardRef;

    public InstrumentDELETECommunication(String ci, String instrumentId, String cookie, GenericMemcachedConnector cacheConnector) {
        this.ci = ci;
        this.instrumentId = instrumentId;
        this.cookie = cookie;
        this.cacheConnector = cacheConnector;
        logger.addAppender(conappender);
    }

    @Override
    public LayerDataObject callRemoteLayer(LayerDataObject ldo) throws LayerCommunicationException {
        LayerDataObject ldoResponse;
        //Parsing response
        try {
            ldoResponse = LayerDataObject.buildFromObject(disableCard());
        } catch (LayerDataObjectParseException ex) {
            logger.debug(wsLog.setParams(WSLogOrigin.REMOTE_CLIENT, ErrorID.LDO_TO_OBJECT.getId(), "Ocurrio un error obteniendo el LDO durante la desactivacion "), ex);
            throw new LayerCommunicationException();
        }
        return ldoResponse;
    }
    
    //------ Respuesta ---------
    private boolean disableCard() throws AuthenticationException {
        //Getting card's info
        try {
            if ((Session) cacheConnector.get(GenericParams.SESSION, this.cookie) != null) {
                Instruments instrumentList = (Instruments) cacheConnector.get(GenericParams.INSTRUMENTS, this.ci);
                for (Instrument tmp : instrumentList.getTarjetas()) {
                    Card auxiliar = (Card) cacheConnector.get(GenericParams.CARD, tmp.getExternalId());
                    if (auxiliar.getId().equals(instrumentId))// Cotejando tarjeta a desactivar
                    {
                        auxiliar.setActiva(GenericParams.DISABLE_CARD);
                        System.out.println(auxiliar);
                        cacheConnector.save(GenericParams.CARD, auxiliar, tmp.getExternalId());
                        break;
                    }
                }
                return true;
            }
            else{
                 throw new AuthenticationException("Sesion invalida. Por favor autentíquese e intente de nuevo");
            }
        } catch (CouchbaseOperationException ex) {
            logger.warn(wsLog.setParams(WSLogOrigin.INTERNAL_WS, ErrorID.NO_ERROR.getId(), "No se pudo desactivar el instrumento con el id: " + instrumentId));
        }
        return false;
    }
}
