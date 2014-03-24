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
package com.synergygb.billeteravirtual.params;

/**
 * Asegura+ REST Web Services
 * @author Synergy-GB
 * @author John Crespo John Crespo <john.crespo@synergy-gb.com>
 * 
 */
public class LoginInputParams {
    /*
    public static final String APP_UPDATE_PARAM = "act";
    public static final String EXIST_UPDATE_PARAM = "exists";
    public static final String REQUIRED_UPDATE_PARAM = "req";
    */

    public static final String CI_PARAM = "ci";
    public static final String PASS_PARAM = "pass";
    /*
    public static final String APP_PARAM = "app";
    public static final String MOBILE_STATS_PARAM = "mobileStats";
    public static final String USR_STATS_PARAM = "usrStats";
    public static final String TYPE_PARAM = "type";
    public static final String BRANCHES_VERSION = "branchesVersion";
    */
    
    /**
     public static final String[] MANDATORY_LOGIN_FIELDS = {CI_PARAM, PASS_PARAM, APP_PARAM, TYPE_PARAM, BRANCHES_VERSION};
    public static final String[] MANDATORY_STATEMENT_FIELDS = {CI_PARAM,PASS_PARAM,TYPE_PARAM};
     */
    
    public static final String[] MANDATORY_LOGIN_FIELDS = {CI_PARAM, PASS_PARAM};
    public static final String[] MANDATORY_STATEMENT_FIELDS = {CI_PARAM,PASS_PARAM};
    
}
