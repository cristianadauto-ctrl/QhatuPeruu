/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qhatuperuu.modelo;

import java.time.LocalDateTime;
import java.util.List;

public class OrdenCompra {
    private int numOrden;
    private LocalDateTime fechaOrden;
    private LocalDateTime fechaIngreso;
    
    // Para mostrar en listados (opcional)
    private List<OrdenDetalle> detalles;
    private int totalDetalles;

    public OrdenCompra() {
    }

    public OrdenCompra(int numOrden, LocalDateTime fechaOrden, LocalDateTime fechaIngreso) {
        this.numOrden = numOrden;
        this.fechaOrden = fechaOrden;
        this.fechaIngreso = fechaIngreso;
    }

    // Getters y Setters
    public int getNumOrden() {
        return numOrden;
    }

    public void setNumOrden(int numOrden) {
        this.numOrden = numOrden;
    }

    public LocalDateTime getFechaOrden() {
        return fechaOrden;
    }

    public void setFechaOrden(LocalDateTime fechaOrden) {
        this.fechaOrden = fechaOrden;
    }

    public LocalDateTime getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDateTime fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public List<OrdenDetalle> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<OrdenDetalle> detalles) {
        this.detalles = detalles;
    }

    public int getTotalDetalles() {
        return totalDetalles;
    }

    public void setTotalDetalles(int totalDetalles) {
        this.totalDetalles = totalDetalles;
    }
}