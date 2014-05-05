package com.synergygb.billeteravirtual.notificacion.models;




import java.io.Serializable;
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlRootElement;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author mauriciochirino
 */
@XmlRootElement
public class Instruments implements Serializable{
    private ArrayList<Instrument> tarjetas;

    public Instruments() {
    }

    public Instruments(ArrayList<Instrument> tarjetas) {
        this.tarjetas = tarjetas;
    }

    public ArrayList<Instrument> getTarjetas() {
        return tarjetas;
    }

    public void setTarjetas(ArrayList<Instrument> tarjetas) {
        this.tarjetas = tarjetas;
    }
    
    public String toString(){
        String imprime="";
        for(Instrument aux: this.tarjetas){
            imprime+=aux.toString()+"\n";
        }
        return imprime;
    }
}
