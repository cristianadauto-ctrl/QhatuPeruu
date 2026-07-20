/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qhatuperuu.dao;

import com.mycompany.qhatuperuu.modelo.OrdenDetalle;
import com.mycompany.qhatuperuu.util.ConexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrdenDetalleDAO {

    public List<OrdenDetalle> listarPorOrden(int numOrden) {
        List<OrdenDetalle> lista = new ArrayList<>();
        String sql = "SELECT od.NumOrden, od.CodArticulo, od.PrecioCompra, "
                   + "od.CantidadSolicitada, od.CantidadRecibida, od.Estado, "
                   + "a.DescripcionArticulo, a.Presentacion "
                   + "FROM ORDEN_DETALLE od "
                   + "INNER JOIN ARTICULO a ON od.CodArticulo = a.CodArticulo "
                   + "WHERE od.NumOrden = ? "
                   + "ORDER BY a.DescripcionArticulo";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, numOrden);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    OrdenDetalle d = new OrdenDetalle();
                    d.setNumOrden(rs.getInt("NumOrden"));
                    d.setCodArticulo(rs.getInt("CodArticulo"));
                    d.setPrecioCompra(rs.getDouble("PrecioCompra"));
                    d.setCantidadSolicitada(rs.getShort("CantidadSolicitada"));
                    d.setCantidadRecibida(rs.getShort("CantidadRecibida"));
                    d.setEstado(rs.getString("Estado"));
                    d.setDescripcionArticulo(rs.getString("DescripcionArticulo"));
                    d.setPresentacion(rs.getString("Presentacion"));
                    lista.add(d);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al listar detalles de orden: " + e.getMessage());
        }
        return lista;
    }

    public boolean crear(OrdenDetalle d) {
        String sql = "INSERT INTO ORDEN_DETALLE (NumOrden, CodArticulo, PrecioCompra, "
                   + "CantidadSolicitada, CantidadRecibida, Estado) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, d.getNumOrden());
            ps.setInt(2, d.getCodArticulo());
            ps.setDouble(3, d.getPrecioCompra());
            ps.setShort(4, d.getCantidadSolicitada());
            ps.setShort(5, d.getCantidadRecibida());
            ps.setString(6, d.getEstado());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al crear detalle de orden: " + e.getMessage());
            return false;
        }
    }

    public boolean actualizar(OrdenDetalle d) {
        String sql = "UPDATE ORDEN_DETALLE SET PrecioCompra = ?, CantidadSolicitada = ?, "
                   + "CantidadRecibida = ?, Estado = ? "
                   + "WHERE NumOrden = ? AND CodArticulo = ?";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDouble(1, d.getPrecioCompra());
            ps.setShort(2, d.getCantidadSolicitada());
            ps.setShort(3, d.getCantidadRecibida());
            ps.setString(4, d.getEstado());
            ps.setInt(5, d.getNumOrden());
            ps.setInt(6, d.getCodArticulo());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al actualizar detalle de orden: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(int numOrden, int codArticulo) {
        String sql = "DELETE FROM ORDEN_DETALLE WHERE NumOrden = ? AND CodArticulo = ?";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, numOrden);
            ps.setInt(2, codArticulo);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al eliminar detalle de orden: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarPorOrden(int numOrden) {
        String sql = "DELETE FROM ORDEN_DETALLE WHERE NumOrden = ?";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, numOrden);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al eliminar detalles de orden: " + e.getMessage());
            return false;
        }
    }
}