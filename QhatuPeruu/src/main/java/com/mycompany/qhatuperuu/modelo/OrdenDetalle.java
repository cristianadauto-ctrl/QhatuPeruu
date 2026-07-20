/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qhatuperuu.modelo;

public class OrdenDetalle {
    private int numOrden;
    private int codArticulo;
    private double precioCompra;
    private short cantidadSolicitada;
    private short cantidadRecibida;
    private String estado;
    
    // Para mostrar en la lista (JOIN)
    private String descripcionArticulo;
    private String presentacion;

    public OrdenDetalle() {
    }

    public OrdenDetalle(int numOrden, int codArticulo, double precioCompra, 
                        short cantidadSolicitada, short cantidadRecibida, String estado) {
        this.numOrden = numOrden;
        this.codArticulo = codArticulo;
        this.precioCompra = precioCompra;
        this.cantidadSolicitada = cantidadSolicitada;
        this.cantidadRecibida = cantidadRecibida;
        this.estado = estado;
    }

    // Getters y Setters
    public int getNumOrden() {
        return numOrden;
    }

    public void setNumOrden(int numOrden) {
        this.numOrden = numOrden;
    }

    public int getCodArticulo() {
        return codArticulo;
    }

    public void setCodArticulo(int codArticulo) {
        this.codArticulo = codArticulo;
    }

    public double getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(double precioCompra) {
        this.precioCompra = precioCompra;
    }

    public short getCantidadSolicitada() {
        return cantidadSolicitada;
    }

    public void setCantidadSolicitada(short cantidadSolicitada) {
        this.cantidadSolicitada = cantidadSolicitada;
    }

    public short getCantidadRecibida() {
        return cantidadRecibida;
    }

    public void setCantidadRecibida(short cantidadRecibida) {
        this.cantidadRecibida = cantidadRecibida;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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