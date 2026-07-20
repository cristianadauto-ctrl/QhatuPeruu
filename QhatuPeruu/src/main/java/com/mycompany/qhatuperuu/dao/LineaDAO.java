/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qhatuperuu.dao;

import com.mycompany.qhatuperuu.modelo.Linea;
import com.mycompany.qhatuperuu.util.ConexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LineaDAO {

    public List<Linea> listarTodos() {
        List<Linea> lista = new ArrayList<>();
        String sql = "SELECT CodLinea, NomLinea, Descripcion FROM LINEA ORDER BY NomLinea";

        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Linea l = new Linea();
                l.setCodLinea(rs.getInt("CodLinea"));
                l.setNomLinea(rs.getString("NomLinea"));
                l.setDescripcion(rs.getString("Descripcion"));
                lista.add(l);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar líneas: " + e.getMessage());
        }
        return lista;
    }

    public boolean crear(Linea l) {
        String sql = "INSERT INTO LINEA (NomLinea, Descripcion) VALUES (?, ?)";

        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, l.getNomLinea());
            ps.setString(2, l.getDescripcion());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al crear línea: " + e.getMessage());
            return false;
        }
    }

    public boolean actualizar(Linea l) {
        String sql = "UPDATE LINEA SET NomLinea = ?, Descripcion = ? WHERE CodLinea = ?";

        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, l.getNomLinea());
            ps.setString(2, l.getDescripcion());
            ps.setInt(3, l.getCodLinea());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al actualizar línea: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(int codLinea) {
        String sql = "DELETE FROM LINEA WHERE CodLinea = ?";

        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, codLinea);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al eliminar línea: " + e.getMessage());
            return false;
        }
    }
    
    public Linea buscarPorId(int codLinea) {
        Linea l = null;
        String sql = "SELECT CodLinea, NomLinea, Descripcion FROM LINEA WHERE CodLinea = ?";

        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, codLinea);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    l = new Linea();
                    l.setCodLinea(rs.getInt("CodLinea"));
                    l.setNomLinea(rs.getString("NomLinea"));
                    l.setDescripcion(rs.getString("Descripcion"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar línea: " + e.getMessage());
        }
        return l;
    }
}