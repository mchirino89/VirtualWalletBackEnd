/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.synergygb.billeteravirtual.services;

/**
 *
 * @author mauriciochirino
 */
public class User {

    private int ci, pass;
    
    public User() {
    }

    public User(int ci, int pass) {
        this.ci = ci;
        this.pass = pass;
    }
    
    public int getCi() {
        return ci;
    }

    public void setCi(int ci) {
        this.ci = ci;
    }

    public int getPass() {
        return pass;
    }

    public void setPass(int pass) {
        this.pass = pass;
    }
    
    @Override
    public String toString() {
        return new StringBuffer("CI: ").append(this.ci).append(" - Pass: ").append(this.pass).toString();
    }
}
