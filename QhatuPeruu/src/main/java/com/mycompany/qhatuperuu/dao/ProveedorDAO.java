/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */package com.mycompany.qhatuperuu.dao;

import com.mycompany.qhatuperuu.modelo.Proveedor;
import com.mycompany.qhatuperuu.util.ConexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProveedorDAO {

    public List<Proveedor> listarTodos() {

        List<Proveedor> lista = new ArrayList<>();

        String sql = "SELECT CodProveedor, NomProveedor, Representante, Direccion, "
                + "Ciudad, Departamento, CodigoPostal, Telefono, Fax "
                + "FROM PROVEEDOR ORDER BY NomProveedor";

        try (Connection con = ConexionDB.obtenerConexion();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Proveedor p = new Proveedor();

                p.setCodProveedor(rs.getInt("CodProveedor"));
                p.setNomProveedor(rs.getString("NomProveedor"));
                p.setRepresentante(rs.getString("Representante"));
                p.setDireccion(rs.getString("Direccion"));
                p.setCiudad(rs.getString("Ciudad"));
                p.setDepartamento(rs.getString("Departamento"));
                p.setCodigoPostal(rs.getString("CodigoPostal"));
                p.setTelefono(rs.getString("Telefono"));
                p.setFax(rs.getString("Fax"));

                lista.add(p);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar proveedores: " + e.getMessage());
        }

        return lista;
    }

    public Proveedor buscarPorId(int codProveedor) {

        Proveedor p = null;

        String sql = "SELECT CodProveedor, NomProveedor, Representante, Direccion, "
                + "Ciudad, Departamento, CodigoPostal, Telefono, Fax "
                + "FROM PROVEEDOR WHERE CodProveedor = ?";

        try (Connection con = ConexionDB.obtenerConexion();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, codProveedor);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    p = new Proveedor();

                    p.setCodProveedor(rs.getInt("CodProveedor"));
                    p.setNomProveedor(rs.getString("NomProveedor"));
                    p.setRepresentante(rs.getString("Representante"));
                    p.setDireccion(rs.getString("Direccion"));
                    p.setCiudad(rs.getString("Ciudad"));
                    p.setDepartamento(rs.getString("Departamento"));
                    p.setCodigoPostal(rs.getString("CodigoPostal"));
                    p.setTelefono(rs.getString("Telefono"));
                    p.setFax(rs.getString("Fax"));
                }

            }

        } catch (SQLException e) {
            System.out.println("Error al buscar proveedor: " + e.getMessage());
        }

        return p;
    }

    public boolean crear(Proveedor p) {

        String sql = "INSERT INTO PROVEEDOR (NomProveedor, Representante, Direccion, "
                + "Ciudad, Departamento, CodigoPostal, Telefono, Fax) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = ConexionDB.obtenerConexion();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, p.getNomProveedor());
            ps.setString(2, p.getRepresentante());
            ps.setString(3, p.getDireccion());
            ps.setString(4, p.getCiudad());
            ps.setString(5, p.getDepartamento());
            ps.setString(6, p.getCodigoPostal());
            ps.setString(7, p.getTelefono());
            ps.setString(8, p.getFax());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al crear proveedor: " + e.getMessage());
            return false;
        }
    }

    public boolean actualizar(Proveedor p) {

        String sql = "UPDATE PROVEEDOR SET "
                + "NomProveedor = ?, "
                + "Representante = ?, "
                + "Direccion = ?, "
                + "Ciudad = ?, "
                + "Departamento = ?, "
                + "CodigoPostal = ?, "
                + "Telefono = ?, "
                + "Fax = ? "
                + "WHERE CodProveedor = ?";

        try (Connection con = ConexionDB.obtenerConexion();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, p.getNomProveedor());
            ps.setString(2, p.getRepresentante());
            ps.setString(3, p.getDireccion());
            ps.setString(4, p.getCiudad());
            ps.setString(5, p.getDepartamento());
            ps.setString(6, p.getCodigoPostal());
            ps.setString(7, p.getTelefono());
            ps.setString(8, p.getFax());
            ps.setInt(9, p.getCodProveedor());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al actualizar proveedor: " + e.getMessage());
            return false;
        }
    }

    public boolean tieneArticulosAsignados(int codProveedor) {

        String sql = "SELECT COUNT(*) AS total FROM ARTICULO WHERE CodProveedor = ?";

        try (Connection con = ConexionDB.obtenerConexion();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, codProveedor);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return rs.getInt("total") > 0;
                }

            }

        } catch (SQLException e) {
            System.out.println("Error al verificar artículos del proveedor: " + e.getMessage());
        }

        return false;
    }

    public boolean eliminar(int codProveedor) {

        String sql = "DELETE FROM PROVEEDOR WHERE CodProveedor = ?";

        try (Connection con = ConexionDB.obtenerConexion();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, codProveedor);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al eliminar proveedor: " + e.getMessage());
            return false;
        }
    }

    public int contarProveedores() {

        String sql = "SELECT COUNT(*) AS total FROM PROVEEDOR";

        try (Connection con = ConexionDB.obtenerConexion();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("total");
            }

        } catch (SQLException e) {
            System.out.println("Error al contar proveedores: " + e.getMessage());
        }

        return 0;
    }

}