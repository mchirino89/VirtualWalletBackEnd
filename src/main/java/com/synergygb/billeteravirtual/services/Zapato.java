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
public class Zapato {
    private int codigo;
    private String color;

    public Zapato() {
    }

    public Zapato(int codigo, String color) {
        this.codigo = codigo;
        this.color = color;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
    
    @Override
    public String toString() {
        return new StringBuffer(" Codigo: ").append(this.codigo).append(" Color: ").append(this.color).toString();
    }

}
