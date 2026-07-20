/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qhatuperuu.modelo;

public class GuiaDetalle {
    private int numGuia;
    private int codArticulo;
    private double precioVenta;
    private short cantidadEnviada;
    
    // Para mostrar en listados (JOIN)
    private String descripcionArticulo;
    private String presentacion;

    public GuiaDetalle() {
    }

    public GuiaDetalle(int numGuia, int codArticulo, double precioVenta, short cantidadEnviada) {
        this.numGuia = numGuia;
        this.codArticulo = codArticulo;
        this.precioVenta = precioVenta;
        this.cantidadEnviada = cantidadEnviada;
    }

    // Getters y Setters
    public int getNumGuia() {
        return numGuia;
    }

    public void setNumGuia(int numGuia) {
        this.numGuia = numGuia;
    }

    public int getCodArticulo() {
        return codArticulo;
    }

    public void setCodArticulo(int codArticulo) {
        this.codArticulo = codArticulo;
    }

    public double getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(double precioVenta) {
        this.precioVenta = precioVenta;
    }

    public short getCantidadEnviada() {
        return cantidadEnviada;
    }

    public void setCantidadEnviada(short cantidadEnviada) {
        this.cantidadEnviada = cantidadEnviada;
    }

    public String getDescripcionArticulo() {
        return descripcionArticulo;
    }

    public void setDescripcionArticulo(String descripcionArticulo) {
        this.descripcionArticulo = descripcionArticulo;
    }

    public String getPresentacion() {
        return presentacion;
    }

    public void setPresentacion(String presentacion) {
        this.presentacion = presentacion;
    }
}