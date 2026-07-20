/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qhatuperuu.modelo;

public class Transportista {
    private int codTransportista;
    private String nomTransportista;
    private String direccion;
    private String telefono;

    public Transportista() {
    }

    public Transportista(int codTransportista, String nomTransportista, String direccion, String telefono) {
        this.codTransportista = codTransportista;
        this.nomTransportista = nomTransportista;
        this.direccion = direccion;
        this.telefono = telefono;
    }

    // Getters y Setters
    public int getCodTransportista() {
        return codTransportista;
    }

    public void setCodTransportista(int codTransportista) {
        this.codTransportista = codTransportista;
    }

    public String getNomTransportista() {
        return nomTransportista;
    }

    public void setNomTransportista(String nomTransportista) {
        this.nomTransportista = nomTransportista;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}