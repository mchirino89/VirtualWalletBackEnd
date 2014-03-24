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
public enum InternalServiceID {
    LOGIN("AA-00"),
    CLIENT("AA-01"),
    CONFIGURATION("AA-02"),
    USERS("AA-03"),
    PICTURES("AA-04"),
    DEVICES("AA-05"),
    ADJUSTMENTS("AA-06"),
    INSPECTION("AA-07"),
    PROVIDERS("AA-08");
    
    private String id;
    
    private InternalServiceID(String id){
        this.id = id;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

}
