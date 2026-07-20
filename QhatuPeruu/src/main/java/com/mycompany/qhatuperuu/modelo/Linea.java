/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qhatuperuu.modelo;

public class Linea {
    
    private int codLinea;
    private String nomLinea;
    private String descripcion;

    // Constructor vacío (necesario para cuando creamos instancias sin datos iniciales)
    public Linea() {
    }

    // Constructor con parámetros
    public Linea(int codLinea, String nomLinea, String descripcion) {
        this.codLinea = codLinea;
        this.nomLinea = nomLinea;
        this.descripcion = descripcion;
    }

    // Getters y Setters
    public int getCodLinea() {
        return codLinea;
    }

    public void setCodLinea(int codLinea) {
        this.codLinea = codLinea;
    }

    public String getNomLinea() {
        return nomLinea;
    }

    public void setNomLinea(String nomLinea) {
        this.nomLinea = nomLinea;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}