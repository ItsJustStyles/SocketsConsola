/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.socketsconsola;

import java.io.Serializable;

/**
 *
 * @author lacay
 */
public class Personajes implements Serializable{
    private static final long serialVersionUID = 1L;
    
    private String nombre;
    private String tipo;
    private String imagen;

    public String getNombre() {
        return nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public String getImagen() {
        return imagen;
    }
    
}
