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

    private int ultDigito;
    private String proveedor, id;

    public int getUltDigito() {
        return ultDigito;
    }

    public void setUltDigito(int ultDigito) {
        this.ultDigito = ultDigito;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
}
