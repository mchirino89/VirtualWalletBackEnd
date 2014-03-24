/**
 * 
 * IMPORTANTE: LEA ESTA NOTA CUIDADOSAMENTE. AL RECIBIR EL C√ìDIGO FUENTE EL 
 * CLIENTE ACEPTA LOS TERMINOS Y CONDICIONES DESCRITOS EN EL ACUERDO DE LICENCIAMIENTO.
 * SI LA EMPRESA NO EST√? DE ACUERDO CON ESTE ACUERDO DE LICENCIAMIENTO DEL C√ìDIGO 
 * FUENTE, NO DESCARGUE, INSTALE, EJECUTE, COPIE, TRANSFIERA, O EN CUALQUIER CASO
 * UTILICE EL C√ìDIGO FUENTE.
 * 
 * Este ACUERDO DE LICENCIAMENTO DEL C√ìDIGO FUENTE (en adelante, ‚ÄúEL ACUERDO‚Ä?) 
 * se realiza entre Synergy Global Business, C.A. (en adelante, ‚ÄúSynergy-GB‚Ä?) 
 * y el Licenciatario (en adelante, ‚Äúel Cliente‚Ä?).
 * 
 * Synergy-GB se reserva el derecho de modificar las condiciones descritas en 
 * EL ACUERDO en cualquier momento y sin previo aviso. 
 * EL ACUERDO est√° descrito y accesible a trav√©s de la direcci√≥n siguiente: 
 * http://www.synergy-gb.com/licenciamiento.pdf
 * 
 */

package com.synergygb.billeteravirtual.core.models.config;

/**
 *
 * @author John Crespo <john.crespo@synergy-gb.com>
 */
public enum ErrorID {
    
    NO_ERROR("EE-00"),
    UNEXPECTED_ERROR("EE-01"),
    MISSING_PARAMETER("EE-02"),
    INVALID_DATA("EE-03"),
    INVALID_PARAMETERS_FORMAT("EE-04"),
    DATABASE_OPERATION("EE-05"),
    LAYER_COMMUNICATION("EE-06"),
    LDO_TO_OBJECT("EE-07"),
    DATA_NOT_FOUND("EE-08"),
    CONFIGURATION_ERROR("EE-09");
    
    
    String id;
    
    private ErrorID(String id){
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
