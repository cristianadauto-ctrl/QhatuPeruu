/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qhatuperuu.modelo;

public class Tienda {
    private int codTienda;
    private String direccion;
    private String distrito;
    private String telefono;
    private String fax;

    public Tienda() {
    }

    public Tienda(int codTienda, String direccion, String distrito, String telefono, String fax) {
        this.codTienda = codTienda;
        this.direccion = direccion;
        this.distrito = distrito;
        this.telefono = telefono;
        this.fax = fax;
    }

    public int getCodTienda() {
        return codTienda;
    }

    public void setCodTienda(int codTienda) {
        this.codTienda = codTienda;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }
}