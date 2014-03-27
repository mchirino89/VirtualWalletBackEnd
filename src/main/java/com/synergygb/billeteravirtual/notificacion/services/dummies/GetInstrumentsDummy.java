/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.synergygb.billeteravirtual.notificacion.services.dummies;
import com.synergygb.billeteravirtual.core.models.config.ErrorID;
import com.synergygb.logformatter.WSLog;
import com.synergygb.logformatter.WSLogOrigin;
import com.synergygb.webAPI.layerCommunication.LayerDataObject;
import com.synergygb.webAPI.layerCommunication.dummies.DummyGenerator;
import com.synergygb.webAPI.layerCommunication.dummies.exceptions.UnsupportedDummyException;
import java.util.Random;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

/**
 *
 * @author mauriciochirino
 */
public class GetInstrumentsDummy extends DummyGenerator{

    private static final Logger logger = Logger.getLogger(RegisterUserDummy.class);
    private static WSLog wsLog = new WSLog("Dummy para obtener los instrumentos asociados a un usuario");
    static ConsoleAppender conappender = new ConsoleAppender(new PatternLayout());

    public GetInstrumentsDummy() {
        logger.addAppender(conappender);
    }

    @Override
    public LayerDataObject getDummy(LayerDataObject layerDataObject) throws UnsupportedDummyException {
        LayerDataObject ldo = layerDataObject;
        int randomPick = new Random().nextInt(10);
        
        if (randomPick < 8) {
            logger.warn(wsLog.setParams(WSLogOrigin.INTERNAL_WS, ErrorID.NO_ERROR.getId(), "Simulando llamada a capa remota exitosa"));
            return ldo;
        }
        else {
            logger.warn(wsLog.setParams(WSLogOrigin.INTERNAL_WS, ErrorID.NO_ERROR.getId(), "Simulando llamada a capa remota fallida"));
            throw new UnsupportedDummyException();
        }
              
    }
    
}
