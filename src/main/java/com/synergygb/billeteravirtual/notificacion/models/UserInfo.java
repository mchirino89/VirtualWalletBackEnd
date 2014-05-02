package com.synergygb.billeteravirtual.notificacion.models;

import java.io.Serializable;
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * @author Synergy-GB
 * @author John Crespo <john.crespo@synergy-gb.com>
 * @author Mauricio Chirino <mauricio.chirino@synergy-gb.com>
 * 
 */
@XmlRootElement
@XmlSeeAlso({Card.class})
public class UserInfo implements Serializable {

    private String stime;
    private ArrayList<Card> aliasList;

    public ArrayList<Card> getInstrumentos() {
        return aliasList;
    }

    public void setInstrumentos(ArrayList<Card> instrumentos) {
        this.aliasList = instrumentos;
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
