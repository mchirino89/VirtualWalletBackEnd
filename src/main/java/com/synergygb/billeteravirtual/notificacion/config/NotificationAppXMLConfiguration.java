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

package com.synergygb.billeteravirtual.notificacion.config;

import com.synergygb.billeteravirtual.core.config.AppXMLConfiguration;

/**
 * @author Synergy-GB
 * @author John Crespo <john.crespo@synergy-gb.com>
 * @version 1.0
 */
public class NotificationAppXMLConfiguration {
    
    private static final String DUMMY_END_POINT_LABEL = "dummiesEndPoint";
    private static final String SOAP_END_POINT_LABEL = "soapEndPoint";    
    private static final String SO_TIMEOUT_LABEL = "soTimeOut";
    private static final String CONNECTION_TIMEOUT_LABEL = "connectionTimeOut";
    private static final String PICTURE_FOLDER_LABEL = "pictureFolder";
    private static final String BRANCH_SECS_LABEL = "branchSecs";
    
    public static final String DUMMY_END_POINT;
    public static final String SOAP_END_POINT;
    public static final String SO_TIMEOUT;
    public static final String CONNECTION_TIMEOUT;
    public static final String PICTURE_FOLDER;
    public static final int BRANCH_SECS;
    static{
        DUMMY_END_POINT = AppXMLConfiguration.CONFIG.getString(DUMMY_END_POINT_LABEL);
        SOAP_END_POINT = AppXMLConfiguration.CONFIG.getString(SOAP_END_POINT_LABEL);
        SO_TIMEOUT = AppXMLConfiguration.CONFIG.getString(SO_TIMEOUT_LABEL);
        CONNECTION_TIMEOUT = AppXMLConfiguration.CONFIG.getString(CONNECTION_TIMEOUT_LABEL);
        PICTURE_FOLDER = AppXMLConfiguration.CONFIG.getString(PICTURE_FOLDER_LABEL);
        BRANCH_SECS = AppXMLConfiguration.CONFIG.getInt(BRANCH_SECS_LABEL);
    }
    

}
