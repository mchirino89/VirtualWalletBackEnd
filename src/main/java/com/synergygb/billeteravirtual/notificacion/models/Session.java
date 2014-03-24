/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.synergygb.billeteravirtual.notificacion.models;

import java.io.Serializable;

/**
 *
 * @author mauriciochirino
 */
public class Session implements Serializable{
    
    private String cookie;
    private int ci;

    public Session() {
    }

    public Session(String cookie, int ci) {
        this.cookie = cookie;
        this.ci = ci;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public int getCi() {
        return ci;
    }

    public void setCi(int ci) {
        this.ci = ci;
    }
    
    @Override
    public String toString() {
        return new StringBuffer("CI: ").append(this.ci).append("\n- Cookie: ").append(this.cookie).toString();
    }
    
}
