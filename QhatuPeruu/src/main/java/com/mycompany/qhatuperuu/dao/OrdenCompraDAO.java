/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qhatuperuu.dao;

import com.mycompany.qhatuperuu.modelo.OrdenCompra;
import com.mycompany.qhatuperuu.modelo.OrdenDetalle;
import com.mycompany.qhatuperuu.util.ConexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrdenCompraDAO {

    public List<OrdenCompra> listarTodos() {
        List<OrdenCompra> lista = new ArrayList<>();
        String sql = "SELECT NumOrden, FechaOrden, FechaIngreso FROM ORDEN_COMPRA ORDER BY NumOrden DESC";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                OrdenCompra o = new OrdenCompra();
                o.setNumOrden(rs.getInt("NumOrden"));
                o.setFechaOrden(rs.getTimestamp("FechaOrden").toLocalDateTime());
                if (rs.getTimestamp("FechaIngreso") != null) {
                    o.setFechaIngreso(rs.getTimestamp("FechaIngreso").toLocalDateTime());
                }
                // Contar detalles
                o.setTotalDetalles(contarDetallesPorOrden(o.getNumOrden()));
                lista.add(o);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar órdenes de compra: " + e.getMessage());
        }
        return lista;
    }

    public OrdenCompra buscarPorId(int numOrden) {
        OrdenCompra o = null;
        String sql = "SELECT NumOrden, FechaOrden, FechaIngreso FROM ORDEN_COMPRA WHERE NumOrden = ?";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, numOrden);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    o = new OrdenCompra();
                    o.setNumOrden(rs.getInt("NumOrden"));
                    o.setFechaOrden(rs.getTimestamp("FechaOrden").toLocalDateTime());
                    if (rs.getTimestamp("FechaIngreso") != null) {
                        o.setFechaIngreso(rs.getTimestamp("FechaIngreso").toLocalDateTime());
                    }
                    // Cargar detalles
                    o.setDetalles(cargarDetalles(numOrden));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar orden de compra por ID: " + e.getMessage());
        }
        return o;
    }

    public boolean crear(OrdenCompra o) {
        String sql = "INSERT INTO ORDEN_COMPRA (NumOrden, FechaOrden, FechaIngreso) VALUES (?, ?, ?)";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, o.getNumOrden());
            ps.setObject(2, o.getFechaOrden());
            ps.setObject(3, o.getFechaIngreso());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al crear orden de compra: " + e.getMessage());
            return false;
        }
    }

    public boolean actualizar(OrdenCompra o) {
        String sql = "UPDATE ORDEN_COMPRA SET FechaOrden = ?, FechaIngreso = ? WHERE NumOrden = ?";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, o.getFechaOrden());
            ps.setObject(2, o.getFechaIngreso());
            ps.setInt(3, o.getNumOrden());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al actualizar orden de compra: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(int numOrden) {
        // Primero eliminar los detalles (por FK)
        String sqlDetalle = "DELETE FROM ORDEN_DETALLE WHERE NumOrden = ?";
        String sqlCabecera = "DELETE FROM ORDEN_COMPRA WHERE NumOrden = ?";
        try (Connection con = ConexionDB.obtenerConexion()) {
            con.setAutoCommit(false);
            try (PreparedStatement psDetalle = con.prepareStatement(sqlDetalle)) {
                psDetalle.setInt(1, numOrden);
                psDetalle.executeUpdate();
            }
            try (PreparedStatement psCabecera = con.prepareStatement(sqlCabecera)) {
                psCabecera.setInt(1, numOrden);
                psCabecera.executeUpdate();
            }
            con.commit();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al eliminar orden de compra: " + e.getMessage());
            return false;
        }
    }

    public int contarOrdenes() {
        String sql = "SELECT COUNT(*) AS total FROM ORDEN_COMPRA";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            System.out.println("Error al contar órdenes de compra: " + e.getMessage());
        }
        return 0;
    }

    public int obtenerSiguienteNumOrden() {
        return contarOrdenes() + 1;
    }

    private int contarDetallesPorOrden(int numOrden) {
        String sql = "SELECT COUNT(*) AS total FROM ORDEN_DETALLE WHERE NumOrden = ?";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, numOrden);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al contar detalles de orden: " + e.getMessage());
        }
        return 0;
    }

    private List<OrdenDetalle> cargarDetalles(int numOrden) {
        List<OrdenDetalle> detalles = new ArrayList<>();
        String sql = "SELECT od.NumOrden, od.CodArticulo, od.PrecioCompra, "
                   + "od.CantidadSolicitada, od.CantidadRecibida, od.Estado, "
                   + "a.DescripcionArticulo, a.Presentacion "
                   + "FROM ORDEN_DETALLE od "
                   + "INNER JOIN ARTICULO a ON od.CodArticulo = a.CodArticulo "
                   + "WHERE od.NumOrden = ?";
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
                    detalles.add(d);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al cargar detalles de orden: " + e.getMessage());
        }
        return detalles;
    }
}