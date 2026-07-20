/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qhatuperuu.modelo;

import java.time.LocalDateTime;
import java.util.List;

public class GuiaEnvio {
    private int numGuia;
    private int codTienda;
    private LocalDateTime fechaSalida;
    private int codTransportista;
    
    // Para mostrar en listados (JOIN)
    private String nomTienda;
    private String nomTransportista;
    
    // Detalles de la guía
    private List<GuiaDetalle> detalles;
    private int totalDetalles;

    public GuiaEnvio() {
    }

    public GuiaEnvio(int numGuia, int codTienda, LocalDateTime fechaSalida, int codTransportista) {
        this.numGuia = numGuia;
        this.codTienda = codTienda;
        this.fechaSalida = fechaSalida;
        this.codTransportista = codTransportista;
    }

    // Getters y Setters
    public int getNumGuia() {
        return numGuia;
    }

    public void setNumGuia(int numGuia) {
        this.numGuia = numGuia;
    }

    public int getCodTienda() {
        return codTienda;
    }

    public void setCodTienda(int codTienda) {
        this.codTienda = codTienda;
    }

    public LocalDateTime getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(LocalDateTime fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public int getCodTransportista() {
        return codTransportista;
    }

    public void setCodTransportista(int codTransportista) {
        this.codTransportista = codTransportista;
    }

    public String getNomTienda() {
        return nomTienda;
    }

    public void setNomTienda(String nomTienda) {
        this.nomTienda = nomTienda;
    }

    public String getNomTransportista() {
        return nomTransportista;
    }

    public void setNomTransportista(String nomTransportista) {
        this.nomTransportista = nomTransportista;
    }

    public List<GuiaDetalle> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<GuiaDetalle> detalles) {
        this.detalles = detalles;
    }

    public int getTotalDetalles() {
        return totalDetalles;
    }

    public void setTotalDetalles(int totalDetalles) {
        this.totalDetalles = totalDetalles;
    }
}