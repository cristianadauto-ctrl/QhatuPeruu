/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qhatuperuu.dao;

import com.mycompany.qhatuperuu.modelo.Tienda;
import com.mycompany.qhatuperuu.util.ConexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TiendaDAO {

    public List<Tienda> listarTodos() {
        List<Tienda> lista = new ArrayList<>();
        String sql = "SELECT CodTienda, Direccion, Distrito, Telefono, Fax "
                   + "FROM TIENDA ORDER BY CodTienda";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Tienda t = new Tienda();
                t.setCodTienda(rs.getInt("CodTienda"));
                t.setDireccion(rs.getString("Direccion"));
                t.setDistrito(rs.getString("Distrito"));
                t.setTelefono(rs.getString("Telefono"));
                t.setFax(rs.getString("Fax"));
                lista.add(t);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar tiendas: " + e.getMessage());
        }
        return lista;
    }

    public Tienda buscarPorId(int codTienda) {
        Tienda t = null;
        String sql = "SELECT CodTienda, Direccion, Distrito, Telefono, Fax "
                   + "FROM TIENDA WHERE CodTienda = ?";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, codTienda);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    t = new Tienda();
                    t.setCodTienda(rs.getInt("CodTienda"));
                    t.setDireccion(rs.getString("Direccion"));
                    t.setDistrito(rs.getString("Distrito"));
                    t.setTelefono(rs.getString("Telefono"));
                    t.setFax(rs.getString("Fax"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar tienda por ID: " + e.getMessage());
        }
        return t;
    }

    public boolean crear(Tienda t) {
        String sql = "INSERT INTO TIENDA (CodTienda, Direccion, Distrito, Telefono, Fax) "
                   + "VALUES (?, ?, ?, ?, ?)";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, t.getCodTienda());
            ps.setString(2, t.getDireccion());
            ps.setString(3, t.getDistrito());
            ps.setString(4, t.getTelefono());
            ps.setString(5, t.getFax());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al crear tienda: " + e.getMessage());
            return false;
        }
    }

    public boolean actualizar(Tienda t) {
        String sql = "UPDATE TIENDA SET Direccion = ?, Distrito = ?, Telefono = ?, Fax = ? "
                   + "WHERE CodTienda = ?";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, t.getDireccion());
            ps.setString(2, t.getDistrito());
            ps.setString(3, t.getTelefono());
            ps.setString(4, t.getFax());
            ps.setInt(5, t.getCodTienda());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al actualizar tienda: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(int codTienda) {
        String sql = "DELETE FROM TIENDA WHERE CodTienda = ?";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, codTienda);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al eliminar tienda: " + e.getMessage());
            return false;
        }
    }

    public boolean tieneGuiasAsignadas(int codTienda) {
        String sql = "SELECT COUNT(*) AS total FROM GUIA_ENVIO WHERE CodTienda = ?";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, codTienda);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total") > 0;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al verificar guías asignadas a la tienda: " + e.getMessage());
        }
        return false;
    }

    public int contarTiendas() {
        String sql = "SELECT COUNT(*) AS total FROM TIENDA";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            System.out.println("Error al contar tiendas: " + e.getMessage());
        }
        return 0;
    }
}