package com.synergygb.billeteravirtual.notificacion.models;

import java.io.Serializable;
import com.synergygb.billeteravirtual.notificacion.models.cache.UserSession;
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * @author Synergy-GB
 * @author John Crespo <john.crespo@synergy-gb.com>
 */
@XmlRootElement
@XmlSeeAlso({Card.class, UserSession.class})
public class UserInfo implements Serializable {

    private String stime;
    private ArrayList<Card> instrumentos;
    private UserSession session;

    public UserSession getSession() {
        return session;
    }

    public void setSession(UserSession session) {
        this.session = session;
    }

    public ArrayList<Card> getInstrumentos() {
        return instrumentos;
    }

    public void setInstrumentos(ArrayList<Card> instrumentos) {
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
    //sobreescribir el metodo toString() para que produzca formato JSON
}
