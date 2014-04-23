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
public class Card implements Serializable {

    private String ultDigito;
    private String proveedor;

    public Card() {
    }

    public Card(String ultDigito, String proveedor) {
        this.ultDigito = ultDigito;
        this.proveedor = proveedor;
    }
    
    public String getUltDigito() {
        return ultDigito;
    }

    public void setUltDigito(String ultDigito) {
        this.ultDigito = ultDigito;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }
    
    @Override
    public String toString(){
        return "{\"proveedor\": \""+this.proveedor+"\", \"ultDig\": \""+this.ultDigito+"\"}";
    }
    
}
