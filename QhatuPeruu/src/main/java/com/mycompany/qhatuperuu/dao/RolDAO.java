/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qhatuperuu.dao;

import com.mycompany.qhatuperuu.modelo.Rol;
import com.mycompany.qhatuperuu.util.ConexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RolDAO {

    public List<Rol> listarTodos() {
        List<Rol> lista = new ArrayList<>();
        String sql = "SELECT CodRol, NomRol, Descripcion FROM ROL ORDER BY NomRol";

        try (Connection con = ConexionDB.obtenerConexion();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Rol r = new Rol();
                r.setCodRol(rs.getInt("CodRol"));
                r.setNomRol(rs.getString("NomRol"));
                r.setDescripcion(rs.getString("Descripcion"));
                lista.add(r);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar roles: " + e.getMessage());
        }
        return lista;
    }

    public Rol buscarPorId(int codRol) {
        Rol r = null;
        String sql = "SELECT CodRol, NomRol, Descripcion FROM ROL WHERE CodRol = ?";

        try (Connection con = ConexionDB.obtenerConexion();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, codRol);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    r = new Rol();
                    r.setCodRol(rs.getInt("CodRol"));
                    r.setNomRol(rs.getString("NomRol"));
                    r.setDescripcion(rs.getString("Descripcion"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar rol: " + e.getMessage());
        }
        return r;
    }

    public boolean crear(Rol r) {
        String sql = "INSERT INTO ROL (NomRol, Descripcion) VALUES (?, ?)";

        try (Connection con = ConexionDB.obtenerConexion();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, r.getNomRol());
            ps.setString(2, r.getDescripcion());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al crear rol: " + e.getMessage());
            return false;
        }
    }

    public boolean actualizar(Rol r) {
        String sql = "UPDATE ROL SET NomRol = ?, Descripcion = ? WHERE CodRol = ?";

        try (Connection con = ConexionDB.obtenerConexion();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, r.getNomRol());
            ps.setString(2, r.getDescripcion());
            ps.setInt(3, r.getCodRol());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al actualizar rol: " + e.getMessage());
            return false;
        }
    }

    public boolean tieneUsuariosAsignados(int codRol) {
        String sql = "SELECT COUNT(*) AS total FROM USUARIO WHERE CodRol = ?";

        try (Connection con = ConexionDB.obtenerConexion();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, codRol);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total") > 0;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al verificar usuarios del rol: " + e.getMessage());
        }
        return false;
    }

    public boolean eliminar(int codRol) {
        String sql = "DELETE FROM ROL WHERE CodRol = ?";

        try (Connection con = ConexionDB.obtenerConexion();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, codRol);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al eliminar rol: " + e.getMessage());
            return false;
        }
    }

    public int contarRoles() {
        String sql = "SELECT COUNT(*) AS total FROM ROL";
        try (Connection con = ConexionDB.obtenerConexion();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            System.out.println("Error al contar roles: " + e.getMessage());
        }
        return 0;
    }
}
