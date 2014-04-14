/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.synergygb.billeteravirtual.notificacion.communication.exceptions;

import com.synergygb.webAPI.layerCommunication.exceptions.LayerCommunicationException;

/**
 *
 * @author mauriciochirino
 */
public class DisableWalletException extends LayerCommunicationException {
    
    public static final String STATUS_CODE = "DISABLE_WALLET";
    
    public static final String STATUS_MSG = "Billetera no activa.";
    
    public DisableWalletException(){
    }
    
    public DisableWalletException(String msg) {
        super(msg);
    }
}
