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
    private String id;
    private String activa;

    public String getActiva() {
        return activa;
    }

    public void setActiva(String activa) {
        this.activa = activa;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Card() {
    }

    public Card(String ultDigito, String proveedor, String id, String activa) {
        this.ultDigito = ultDigito;
        this.proveedor = proveedor;
        this.id = id;
        this.activa = activa;
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
        return "{\"proveedor\": \""+this.proveedor+"\", \"ultDig\": \""+this.ultDigito+", \"id\": \""+this.id+", \"activa\": \""+this.activa+"\"};";
    }
    
}
