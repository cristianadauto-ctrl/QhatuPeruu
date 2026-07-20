/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qhatuperuu.dao;

import com.mycompany.qhatuperuu.modelo.GuiaDetalle;
import com.mycompany.qhatuperuu.util.ConexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GuiaDetalleDAO {

    public List<GuiaDetalle> listarPorGuia(int numGuia) {
        List<GuiaDetalle> lista = new ArrayList<>();
        String sql = "SELECT gd.NumGuia, gd.CodArticulo, gd.PrecioVenta, gd.CantidadEnviada, "
                   + "a.DescripcionArticulo, a.Presentacion "
                   + "FROM GUIA_DETALLE gd "
                   + "INNER JOIN ARTICULO a ON gd.CodArticulo = a.CodArticulo "
                   + "WHERE gd.NumGuia = ? "
                   + "ORDER BY a.DescripcionArticulo";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, numGuia);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    GuiaDetalle d = new GuiaDetalle();
                    d.setNumGuia(rs.getInt("NumGuia"));
                    d.setCodArticulo(rs.getInt("CodArticulo"));
                    d.setPrecioVenta(rs.getDouble("PrecioVenta"));
                    d.setCantidadEnviada(rs.getShort("CantidadEnviada"));
                    d.setDescripcionArticulo(rs.getString("DescripcionArticulo"));
                    d.setPresentacion(rs.getString("Presentacion"));
                    lista.add(d);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al listar detalles de guía: " + e.getMessage());
        }
        return lista;
    }

    public boolean crear(GuiaDetalle d) {
        String sql = "INSERT INTO GUIA_DETALLE (NumGuia, CodArticulo, PrecioVenta, CantidadEnviada) "
                   + "VALUES (?, ?, ?, ?)";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, d.getNumGuia());
            ps.setInt(2, d.getCodArticulo());
            ps.setDouble(3, d.getPrecioVenta());
            ps.setShort(4, d.getCantidadEnviada());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al crear detalle de guía: " + e.getMessage());
            return false;
        }
    }

    public boolean actualizar(GuiaDetalle d) {
        String sql = "UPDATE GUIA_DETALLE SET PrecioVenta = ?, CantidadEnviada = ? "
                   + "WHERE NumGuia = ? AND CodArticulo = ?";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDouble(1, d.getPrecioVenta());
            ps.setShort(2, d.getCantidadEnviada());
            ps.setInt(3, d.getNumGuia());
            ps.setInt(4, d.getCodArticulo());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al actualizar detalle de guía: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(int numGuia, int codArticulo) {
        String sql = "DELETE FROM GUIA_DETALLE WHERE NumGuia = ? AND CodArticulo = ?";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, numGuia);
            ps.setInt(2, codArticulo);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al eliminar detalle de guía: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarPorGuia(int numGuia) {
        String sql = "DELETE FROM GUIA_DETALLE WHERE NumGuia = ?";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, numGuia);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al eliminar detalles de guía: " + e.getMessage());
            return false;
        }
    }
}