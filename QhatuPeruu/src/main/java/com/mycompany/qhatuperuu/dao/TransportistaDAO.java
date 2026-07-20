/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qhatuperuu.dao;

import com.mycompany.qhatuperuu.modelo.Transportista;
import com.mycompany.qhatuperuu.util.ConexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransportistaDAO {

    public List<Transportista> listarTodos() {
        List<Transportista> lista = new ArrayList<>();
        String sql = "SELECT CodTransportista, NomTransportista, Direccion, Telefono "
                   + "FROM TRANSPORTISTA ORDER BY NomTransportista";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Transportista t = new Transportista();
                t.setCodTransportista(rs.getInt("CodTransportista"));
                t.setNomTransportista(rs.getString("NomTransportista"));
                t.setDireccion(rs.getString("Direccion"));
                t.setTelefono(rs.getString("Telefono"));
                lista.add(t);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar transportistas: " + e.getMessage());
        }
        return lista;
    }

    public Transportista buscarPorId(int codTransportista) {
        Transportista t = null;
        String sql = "SELECT CodTransportista, NomTransportista, Direccion, Telefono "
                   + "FROM TRANSPORTISTA WHERE CodTransportista = ?";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, codTransportista);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    t = new Transportista();
                    t.setCodTransportista(rs.getInt("CodTransportista"));
                    t.setNomTransportista(rs.getString("NomTransportista"));
                    t.setDireccion(rs.getString("Direccion"));
                    t.setTelefono(rs.getString("Telefono"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar transportista por ID: " + e.getMessage());
        }
        return t;
    }

    public boolean crear(Transportista t) {
        String sql = "INSERT INTO TRANSPORTISTA (CodTransportista, NomTransportista, Direccion, Telefono) "
                   + "VALUES (?, ?, ?, ?)";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, t.getCodTransportista());
            ps.setString(2, t.getNomTransportista());
            ps.setString(3, t.getDireccion());
            ps.setString(4, t.getTelefono());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al crear transportista: " + e.getMessage());
            return false;
        }
    }

    public boolean actualizar(Transportista t) {
        String sql = "UPDATE TRANSPORTISTA SET NomTransportista = ?, Direccion = ?, Telefono = ? "
                   + "WHERE CodTransportista = ?";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, t.getNomTransportista());
            ps.setString(2, t.getDireccion());
            ps.setString(3, t.getTelefono());
            ps.setInt(4, t.getCodTransportista());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al actualizar transportista: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(int codTransportista) {
        String sql = "DELETE FROM TRANSPORTISTA WHERE CodTransportista = ?";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, codTransportista);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al eliminar transportista: " + e.getMessage());
            return false;
        }
    }

    public boolean tieneGuiasAsignadas(int codTransportista) {
        String sql = "SELECT COUNT(*) AS total FROM GUIA_ENVIO WHERE CodTransportista = ?";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, codTransportista);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total") > 0;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al verificar guías asignadas al transportista: " + e.getMessage());
        }
        return false;
    }

    public int contarTransportistas() {
        String sql = "SELECT COUNT(*) AS total FROM TRANSPORTISTA";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            System.out.println("Error al contar transportistas: " + e.getMessage());
        }
        return 0;
    }
}
