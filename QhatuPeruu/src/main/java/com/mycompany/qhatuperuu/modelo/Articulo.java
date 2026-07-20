/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qhatuperuu.modelo;

public class Articulo {

    private int codArticulo;
    private int codLinea;
    private int codProveedor;
    private String descripcionArticulo;
    private String presentacion;
    private double precioProveedor;
    private short stockActual;
    private short stockMinimo;
    private boolean descontinuado;

    // Para mostrar nombres en la lista (JOIN)
    private String nomLinea;
    private String nomProveedor;

    public Articulo() {
    }

    public int getCodArticulo() {
        return codArticulo;
    }

    public void setCodArticulo(int codArticulo) {
        this.codArticulo = codArticulo;
    }

    public int getCodLinea() {
        return codLinea;
    }

    public void setCodLinea(int codLinea) {
        this.codLinea = codLinea;
    }

    public int getCodProveedor() {
        return codProveedor;
    }

    public void setCodProveedor(int codProveedor) {
        this.codProveedor = codProveedor;
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

    public double getPrecioProveedor() {
        return precioProveedor;
    }

    public void setPrecioProveedor(double precioProveedor) {
        this.precioProveedor = precioProveedor;
    }

    public short getStockActual() {
        return stockActual;
    }

    public void setStockActual(short stockActual) {
        this.stockActual = stockActual;
    }

    public short getStockMinimo() {
        return stockMinimo;
    }

    public void setStockMinimo(short stockMinimo) {
        this.stockMinimo = stockMinimo;
    }

    public boolean isDescontinuado() {
        return descontinuado;
    }

    public void setDescontinuado(boolean descontinuado) {
        this.descontinuado = descontinuado;
    }

    public String getNomLinea() {
        return nomLinea;
    }

    public void setNomLinea(String nomLinea) {
        this.nomLinea = nomLinea;
    }

    public String getNomProveedor() {
        return nomProveedor;
    }

    public void setNomProveedor(String nomProveedor) {
        this.nomProveedor = nomProveedor;
    }

}