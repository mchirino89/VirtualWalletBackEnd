package com.synergygb.billeteravirtual.notificacion.models;




import java.io.Serializable;
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

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
@XmlSeeAlso({Transaction.class})
public class Transactions implements Serializable{
    private ArrayList<Transaction> transacciones;

    public Transactions() {
    }

    public Transactions(ArrayList<Transaction> transacciones) {
        this.transacciones = transacciones;
    }

    public ArrayList<Transaction> getTarjetas() {
        return transacciones;
    }

    public void setTarjetas(ArrayList<Transaction> transacciones) {
        this.transacciones = transacciones;
    }
    
    public String toString(){
        String imprime="";
        for(Transaction aux: this.transacciones){
            imprime+=aux.toString()+"\n";
        }
        return imprime;
    }
}
