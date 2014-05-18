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
package com.synergygb.billeteravirtual.notificacion.communication.utils;

import com.synergygb.billeteravirtual.notificacion.communication.Instrument.InstrumentPOSTCommunication;
import com.synergygb.billeteravirtual.notificacion.communication.Instrument.InstrumentDELETECommunication;
import com.synergygb.billeteravirtual.core.connector.cache.GenericMemcachedConnector;
import com.synergygb.billeteravirtual.notificacion.models.*;
import com.synergygb.billeteravirtual.notificacion.services.models.*;
import com.synergygb.webAPI.layerCommunication.DataLayerCommunicationType;
import com.synergygb.webAPI.layerCommunication.LayerDataObject;
import com.synergygb.webAPI.layerCommunication.exceptions.LayerCommunicationException;
import com.synergygb.webAPI.layerCommunication.exceptions.LayerDataObjectParseException;
import com.synergygb.webAPI.layerCommunication.exceptions.LayerDataObjectToObjectParseException;

/**
 * Billetera Virtual+ REST Web Services
 *
 * @author Synergy-GB
 * @author John Crespo John Crespo <john.crespo@synergy-gb.com>
 * @author Alejandro Carpio <alejandro.carpio@synergy-gb.com>
 * @version 1.0
 */
public class InstrumentCommunication extends Communication{

    public InstrumentCommunication() {
        
    }
    
    public static Card postInstrumentData(DataLayerCommunicationType ct, InstrumentParamsModel instrumentModel, String ci, GenericMemcachedConnector cacheConnector) throws LayerCommunicationException, LayerDataObjectToObjectParseException, LayerDataObjectParseException {
        LayerDataObject loginInputLDO = LayerDataObject.buildFromObject(instrumentModel);
        InstrumentPOSTCommunication postInstrumentCommunication = new InstrumentPOSTCommunication(ci,cacheConnector);
        LayerDataObject response = postInstrumentCommunication.communicate(ct, loginInputLDO);   
        return (Card) response.toObject(Card.class);
    }
    
    public static void deleteInstrumentData(DataLayerCommunicationType ct, InstrumentParamsModel instrumentModel, String ci, String instrumentId, String cookie, GenericMemcachedConnector cacheConnector) throws LayerCommunicationException, LayerDataObjectToObjectParseException, LayerDataObjectParseException {
        LayerDataObject loginInputLDO = LayerDataObject.buildFromObject(instrumentModel);
        InstrumentDELETECommunication deleteInstrumentCommunication = new InstrumentDELETECommunication(ci,instrumentId,cookie,cacheConnector);
        deleteInstrumentCommunication.communicate(ct,loginInputLDO);
    }
}
