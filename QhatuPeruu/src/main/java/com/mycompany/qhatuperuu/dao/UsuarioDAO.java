/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qhatuperuu.dao;

import com.mycompany.qhatuperuu.modelo.Usuario;
import com.mycompany.qhatuperuu.util.ConexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    private static final String SELECT_BASE =
        "SELECT u.CodUsuario, u.Username, u.Clave, u.Nombres, u.Apellidos, u.Correo, "
        + "u.Estado, u.CodRol, r.NomRol "
        + "FROM USUARIO u INNER JOIN ROL r ON u.CodRol = r.CodRol ";

    public List<Usuario> listarTodos() {
        List<Usuario> lista = new ArrayList<>();
        String sql = SELECT_BASE + "ORDER BY u.Nombres";

        try (Connection con = ConexionDB.obtenerConexion();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapearUsuario(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error al listar usuarios: " + e.getMessage());
        }
        return lista;
    }

    public Usuario buscarPorId(int codUsuario) {
        Usuario u = null;
        String sql = SELECT_BASE + "WHERE u.CodUsuario = ?";

        try (Connection con = ConexionDB.obtenerConexion();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, codUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    u = mapearUsuario(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar usuario: " + e.getMessage());
        }
        return u;
    }

    public Usuario buscarPorUsername(String username) {
        Usuario u = null;
        String sql = SELECT_BASE + "WHERE u.Username = ?";

        try (Connection con = ConexionDB.obtenerConexion();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    u = mapearUsuario(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar usuario por username: " + e.getMessage());
        }
        return u;
    }

    public boolean existeUsername(String username) {
        String sql = "SELECT COUNT(*) AS total FROM USUARIO WHERE Username = ?";
        try (Connection con = ConexionDB.obtenerConexion();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total") > 0;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al verificar username: " + e.getMessage());
        }
        return false;
    }

    public boolean crear(Usuario u) {
        String sql = "INSERT INTO USUARIO (Username, Clave, Nombres, Apellidos, Correo, Estado, CodRol) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = ConexionDB.obtenerConexion();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, u.getUsername());
            ps.setString(2, u.getClave());
            ps.setString(3, u.getNombres());
            ps.setString(4, u.getApellidos());
            ps.setString(5, u.getCorreo());
            ps.setBoolean(6, u.isEstado());
            ps.setInt(7, u.getCodRol());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al crear usuario: " + e.getMessage());
            return false;
        }
    }

    public boolean actualizarSinClave(Usuario u) {
        String sql = "UPDATE USUARIO SET Username = ?, Nombres = ?, Apellidos = ?, "
                + "Correo = ?, Estado = ?, CodRol = ? WHERE CodUsuario = ?";

        try (Connection con = ConexionDB.obtenerConexion();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, u.getUsername());
            ps.setString(2, u.getNombres());
            ps.setString(3, u.getApellidos());
            ps.setString(4, u.getCorreo());
            ps.setBoolean(5, u.isEstado());
            ps.setInt(6, u.getCodRol());
            ps.setInt(7, u.getCodUsuario());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al actualizar usuario: " + e.getMessage());
            return false;
        }
    }

    public boolean actualizarConClave(Usuario u) {
        String sql = "UPDATE USUARIO SET Username = ?, Clave = ?, Nombres = ?, Apellidos = ?, "
                + "Correo = ?, Estado = ?, CodRol = ? WHERE CodUsuario = ?";

        try (Connection con = ConexionDB.obtenerConexion();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, u.getUsername());
            ps.setString(2, u.getClave());
            ps.setString(3, u.getNombres());
            ps.setString(4, u.getApellidos());
            ps.setString(5, u.getCorreo());
            ps.setBoolean(6, u.isEstado());
            ps.setInt(7, u.getCodRol());
            ps.setInt(8, u.getCodUsuario());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al actualizar usuario con clave: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(int codUsuario) {
        String sql = "DELETE FROM USUARIO WHERE CodUsuario = ?";

        try (Connection con = ConexionDB.obtenerConexion();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, codUsuario);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al eliminar usuario: " + e.getMessage());
            return false;
        }
    }

    public int contarUsuarios() {
        return contarPorCondicion("SELECT COUNT(*) AS total FROM USUARIO");
    }

    public int contarActivos() {
        return contarPorCondicion("SELECT COUNT(*) AS total FROM USUARIO WHERE Estado = 1");
    }

    public int contarInactivos() {
        return contarPorCondicion("SELECT COUNT(*) AS total FROM USUARIO WHERE Estado = 0");
    }

    private int contarPorCondicion(String sql) {
        try (Connection con = ConexionDB.obtenerConexion();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            System.out.println("Error al contar usuarios: " + e.getMessage());
        }
        return 0;
    }

    private Usuario mapearUsuario(ResultSet rs) throws SQLException {
        Usuario u = new Usuario();
        u.setCodUsuario(rs.getInt("CodUsuario"));
        u.setUsername(rs.getString("Username"));
        u.setClave(rs.getString("Clave"));
        u.setNombres(rs.getString("Nombres"));
        u.setApellidos(rs.getString("Apellidos"));
        u.setCorreo(rs.getString("Correo"));
        u.setEstado(rs.getBoolean("Estado"));
        u.setCodRol(rs.getInt("CodRol"));
        u.setNomRol(rs.getString("NomRol"));
        return u;
    }
}