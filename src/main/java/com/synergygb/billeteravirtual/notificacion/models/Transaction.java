/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.synergygb.billeteravirtual.notificacion.models;


import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mauriciochirino
 */
@XmlRootElement
public class Transaction implements Serializable{
    private String date, description, amount;

    public Transaction() {
    }

    public Transaction(String date, String description, String amount) {
        this.date = date;
        this.description = description;
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
    
    @Override
    public String toString(){
        return "{\"description\": \""+this.description+"\", \"amount\": \""+this.amount+"\", \"date\": \""+this.date+"\"}";
    }
    
}
