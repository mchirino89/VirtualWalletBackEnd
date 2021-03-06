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
 * @author John Crespo John Crespo <john.crespo@synergy-gb.com>
 */
@XmlRootElement
public class LoginParamsModel implements Serializable {

    public LoginParamsModel() {
    }

    public LoginParamsModel(String ci, String pass, String otp) {
        this.ci = ci;
        this.pass = pass;
        this.otp = otp;
    }
    /**
     * User otp 
     */
    private String otp;
    /**
     * Document number
     */
    private String ci;
    /**
     * User password
     */
    private String pass;
    /**
     * @return the pass
     */
    public String getPass() {
        return pass;
    }
    /**
     * @param pass the pass to set
     */
    public void setPass(String pass) {
        this.pass = pass;
    }
    /**
     * @return the ci
     */
    public String getCi() {
        return ci;
    }
    /**
     * @param ci the ci to set
     */
    public void setCi(String ci) {
        this.ci = ci;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
    public String toString(){
        return "{\"ci\": \""+this.ci+", \"pass\":\""+this.pass+"}";
    }
}
