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
public class InstrumentPOSTCommunication extends DataLayerCommunication {

    private static final Logger logger = Logger.getLogger(InstrumentPOSTCommunication.class);
    private GenericMemcachedConnector cacheConnector;
    WSLog wsLog = new WSLog("Communcation Login");
    static ConsoleAppender conappender = new ConsoleAppender(new PatternLayout());
    private String ci, CardRef;

    public InstrumentPOSTCommunication(String ci, GenericMemcachedConnector cacheConnector) {
        this.ci = ci;
        this.cacheConnector = cacheConnector;
        logger.addAppender(conappender);
    }

    @Override
    public LayerDataObject callRemoteLayer(LayerDataObject ldo) throws LayerCommunicationException {
        //------------------------------------------------------------------
        // Declaring parsing variables
        //------------------------------------------------------------------
        InstrumentParamsModel instrumentModel = null;
        LayerDataObject ldoResponse;
        Card info = null;
        //---------------------------------------------------------------------
        // Parsing the request parameters.
        //---------------------------------------------------------------------
        try {
            instrumentModel = (InstrumentParamsModel) ldo.toObject(InstrumentParamsModel.class);
            initInput(instrumentModel);
        } catch (LayerDataObjectToObjectParseException ex) {
            java.util.logging.Logger.getLogger(InstrumentPOSTCommunication.class.getName()).log(Level.SEVERE, "Ocurrio un problema con el parseo del login", ex);
            throw new LayerCommunicationException();
        }
        //Parsing response
        info = initAddInstInfo(instrumentModel);
        try {
            ldoResponse = LayerDataObject.buildFromObject(info);
        } catch (LayerDataObjectParseException ex) {
            logger.debug(wsLog.setParams(WSLogOrigin.REMOTE_CLIENT, ErrorID.LDO_TO_OBJECT.getId(), "Ocurrio un error obteniendo el LDO "), ex);
            throw new LayerCommunicationException();
        }
        return ldoResponse;
    }

    //------ Pregunta ----------
    private void initInput(InstrumentParamsModel instrumentModel) {
        logger.info(wsLog.setParams(WSLogOrigin.INTERNAL_WS, ErrorID.NO_ERROR.getId(), "Generando referencia en la BD para la tarjeta número " + instrumentModel.getCardNumber()));
        String CardId = generaCadena(GenericParams.RANDOM_CARD_ID);
        CardRef = generaCadena(GenericParams.RANDOM_CARD_REF);
        try {
            String proveedor = "";
            Session validez = (Session) cacheConnector.get(GenericParams.SESSION, instrumentModel.getCookie());
            if (validez != null) {
                switch (Integer.parseInt(String.valueOf(instrumentModel.getCardNumber().charAt(0)))) {
                    case 4:
                        proveedor = "Visa";
                        break;
                    case 5:
                        proveedor = "Master Card";
                        break;
                    case 3:
                        proveedor = "American Express";
                        break;
                    default:
                        proveedor = "Dinner Club";
                        break;
                }
                
                cacheConnector.save(GenericParams.CARD,
                        new Card(instrumentModel.getCardNumber().substring(instrumentModel.getCardNumber().length() - 4, instrumentModel.getCardNumber().length()),
                                proveedor, CardId, GenericParams.ACTIVICE_CARD), CardRef);
                Instruments registradas = (Instruments) cacheConnector.get(GenericParams.INSTRUMENTS, this.ci);
                ArrayList<Instrument> tarjetas = registradas.getTarjetas();
                tarjetas.add(new Instrument(CardId, CardRef));
                registradas.setTarjetas(tarjetas);
                cacheConnector.save(GenericParams.INSTRUMENTS, registradas, this.ci);
            }
            else{
                throw new SecurityException("cookie invalida");
            }

        } catch (CouchbaseOperationException ex) {
            logger.warn(wsLog.setParams(WSLogOrigin.INTERNAL_WS, ErrorID.NO_ERROR.getId(), "No se pudo cotejar la tarjeta con el número " + instrumentModel.getCardHolderName()));
        }
    }

    //------ Respuesta ---------
    private Card initAddInstInfo(InstrumentParamsModel instrumentModel) {
        Card info = null;
        //Getting card's info
        try {
            info = (Card) cacheConnector.get(GenericParams.CARD, CardRef);
            if (info == null) {
                throw new InstantiationError("Instrumento no registrado correctamente");
            }
        } catch (CouchbaseOperationException ex) {
            logger.warn(wsLog.setParams(WSLogOrigin.INTERNAL_WS, ErrorID.NO_ERROR.getId(), "No se pudo consultar el instrumento de con el numero de tarjeta: " + instrumentModel.getCardNumber()));
        }
        return info;
    }

    private String generaCadena(int longitud) {
        char[] chars = "abcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < longitud; i++) {
            sb.append(chars[random.nextInt(chars.length)]);
        }
        return sb.toString();
    }
}
