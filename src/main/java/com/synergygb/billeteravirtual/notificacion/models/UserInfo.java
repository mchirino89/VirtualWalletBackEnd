package com.synergygb.billeteravirtual.notificacion.models;

import java.io.Serializable;
import java.util.List;

/**
 * @author Synergy-GB
 * @author John Crespo <john.crespo@synergy-gb.com>
 */
public class UserInfo implements Serializable {

    private String stime;
    private List<Card> instrumentos;

    public List<Card> getInstrumentos() {
        return instrumentos;
    }

    public void setInstrumentos(List<Card> instrumentos) {
        this.instrumentos = instrumentos;
    }

    public UserInfo() {
    }

    public String getStime() {
        return stime;
    }

    public void setStime(String stime) {
        this.stime = stime;
    }

}
