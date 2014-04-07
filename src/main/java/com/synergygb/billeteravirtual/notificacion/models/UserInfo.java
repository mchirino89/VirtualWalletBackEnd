package com.synergygb.billeteravirtual.notificacion.models;

import java.io.Serializable;
import java.util.List;
import com.synergygb.billeteravirtual.notificacion.models.cache.UserSession;
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
    private List<Card> instrumentos;
    private UserSession session;

    public UserSession getSession() {
        return session;
    }

    public void setSession(UserSession session) {
        this.session = session;
    }

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
    //sobreescribir el metodo toString() para que produzca formato JSON
}
