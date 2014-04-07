package com.synergygb.billeteravirtual.core.services.handler.utils;

import com.synergygb.billeteravirtual.core.services.params.GeneralParams;
import com.synergygb.webAPI.layerCommunication.DataLayerCommunicationType;
import com.synergygb.webAPI.parameters.WebServiceParameters;
import com.synergygb.webAPI.parameters.exceptions.MissingParameterException;

/**
 * Billetera Virtual+ REST Web Services
 *
 * @author Synergy-GB
 * @author John Crespo <john.crespo@synergy-gb.com>
 * @version 1.0
 */
public class HandlerUtils {
    public static DataLayerCommunicationType setCommunicationTypeFromParams(WebServiceParameters params) {
       DataLayerCommunicationType communicationType;
        try {
            if (params.getParameterAsString(GeneralParams.COMMUNICATION_TYPE).equals(GeneralParams.COMMUNICATION_DUMMY_TYPE_VALUE)) {
                communicationType = DataLayerCommunicationType.DUMMY;
            } else {
                communicationType = DataLayerCommunicationType.REAL;
            }
        } catch (MissingParameterException ex) {
            communicationType = DataLayerCommunicationType.REAL;
        }
        return communicationType;
    }

   public static String getStackStraceAsString(Exception e){
        String stackTrace = "";
        for(StackTraceElement element : e.getStackTrace()){
            stackTrace+=element.toString()+"\n";
        }
        return stackTrace;
    }

}