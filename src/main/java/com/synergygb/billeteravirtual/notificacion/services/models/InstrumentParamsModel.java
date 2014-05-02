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
package com.synergygb.billeteravirtual.notificacion.services.models;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Billetera Virtual+ REST Web Services
 *
 * @author Synergy-GB
 * @author Mauricio Chirino <mauricio.chirino@synergy-gb.com>
 */
@XmlRootElement
public class InstrumentParamsModel implements Serializable {

    private String cardHolderName;
    private String cardNumber;
    private String cardCVV;
    private String cardExpirationDate;
    
    public InstrumentParamsModel() {
    }

    public InstrumentParamsModel(String cardHolderName, String cardNumber, String cardCVV, String cardExpirationDate) {
        this.cardHolderName = cardHolderName;
        this.cardNumber = cardNumber;
        this.cardCVV = cardCVV;
        this.cardExpirationDate = cardExpirationDate;
    }
    
    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardCVV() {
        return cardCVV;
    }

    public void setCardCVV(String cardCVV) {
        this.cardCVV = cardCVV;
    }

    public String getCardExpirationDate() {
        return cardExpirationDate;
    }

    public void setCardExpirationDate(String cardExpirationDate) {
        this.cardExpirationDate = cardExpirationDate;
    }
    
    
}
