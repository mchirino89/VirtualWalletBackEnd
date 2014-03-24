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
package com.synergygb.billeteravirtual.core.config;

import com.synergygb.billeteravirtual.core.config.exceptions.InvalidAppIdException;

public enum ApplicationId {
    
    BLACKBERRY_PHONE("0","bb-phone"),
    ANDROID_PHONE("1","android-phone"), 
    IOS_PHONE("2","ios-phone"),
    BLACKBERRY_TABLET("3","bb-tablet"), 
    ANDROID_TABLET("4","android-tablet"),
    IOS_TABLET("5","ios-tablet"),
    WEB_MOBILE("6","web-mobile");
    
            
    private ApplicationId(String dbId, String externalId){
        this.dbId = dbId;
        this.externalId = externalId;
    }   
    private String dbId;
    private String externalId;

    public String getDbId() {
        return dbId;
    }

    public String getExternalId() {
        return externalId;
    }
    
    public static ApplicationId getAppIdFromExtId(String id) throws InvalidAppIdException {
        ApplicationId[] appIdValues = ApplicationId.values();
        for (ApplicationId app : appIdValues) {
            if (app.getExternalId().equals(id)) {
                return app;
            }
        }
        throw new InvalidAppIdException();
    }    
    
}
