/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qhatuperuu.dao;

import com.mycompany.qhatuperuu.modelo.Articulo;
import com.mycompany.qhatuperuu.util.ConexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ArticuloDAO {

    public List<Articulo> listarTodos() {

        List<Articulo> lista = new ArrayList<>();

        String sql = "SELECT a.CodArticulo, a.CodLinea, a.CodProveedor, "
                + "a.DescripcionArticulo, a.Presentacion, "
                + "a.PrecioProveedor, a.StockActual, "
                + "a.StockMinimo, a.Descontinuado, "
                + "l.NomLinea, p.NomProveedor "
                + "FROM ARTICULO a "
                + "INNER JOIN LINEA l ON a.CodLinea = l.CodLinea "
                + "INNER JOIN PROVEEDOR p ON a.CodProveedor = p.CodProveedor "
                + "ORDER BY a.DescripcionArticulo";

        try (Connection con = ConexionDB.obtenerConexion();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Articulo a = new Articulo();

                a.setCodArticulo(rs.getInt("CodArticulo"));
                a.setCodLinea(rs.getInt("CodLinea"));
                a.setCodProveedor(rs.getInt("CodProveedor"));
                a.setDescripcionArticulo(rs.getString("DescripcionArticulo"));
                a.setPresentacion(rs.getString("Presentacion"));
                a.setPrecioProveedor(rs.getDouble("PrecioProveedor"));
                a.setStockActual(rs.getShort("StockActual"));
                a.setStockMinimo(rs.getShort("StockMinimo"));
                a.setDescontinuado(rs.getBoolean("Descontinuado"));

                a.setNomLinea(rs.getString("NomLinea"));
                a.setNomProveedor(rs.getString("NomProveedor"));

                lista.add(a);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar artículos: " + e.getMessage());
        }

        return lista;
    }

    public Articulo buscarPorId(int codArticulo) {

        Articulo a = null;

        String sql = "SELECT * FROM ARTICULO WHERE CodArticulo = ?";

        try (Connection con = ConexionDB.obtenerConexion();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, codArticulo);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    a = new Articulo();

                    a.setCodArticulo(rs.getInt("CodArticulo"));
                    a.setCodLinea(rs.getInt("CodLinea"));
                    a.setCodProveedor(rs.getInt("CodProveedor"));
                    a.setDescripcionArticulo(rs.getString("DescripcionArticulo"));
                    a.setPresentacion(rs.getString("Presentacion"));
                    a.setPrecioProveedor(rs.getDouble("PrecioProveedor"));
                    a.setStockActual(rs.getShort("StockActual"));
                    a.setStockMinimo(rs.getShort("StockMinimo"));
                    a.setDescontinuado(rs.getBoolean("Descontinuado"));

                }

            }

        } catch (SQLException e) {
            System.out.println("Error al buscar artículo: " + e.getMessage());
        }

        return a;
    }

    public boolean crear(Articulo a) {

        String sql = "INSERT INTO ARTICULO "
                + "(CodLinea, CodProveedor, DescripcionArticulo, "
                + "Presentacion, PrecioProveedor, StockActual, "
                + "StockMinimo, Descontinuado) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = ConexionDB.obtenerConexion();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, a.getCodLinea());
            ps.setInt(2, a.getCodProveedor());
            ps.setString(3, a.getDescripcionArticulo());
            ps.setString(4, a.getPresentacion());
            ps.setDouble(5, a.getPrecioProveedor());
            ps.setShort(6, a.getStockActual());
            ps.setShort(7, a.getStockMinimo());
            ps.setBoolean(8, a.isDescontinuado());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al crear artículo: " + e.getMessage());
            return false;
        }
    }

    public boolean actualizar(Articulo a) {

        String sql = "UPDATE ARTICULO SET "
                + "CodLinea = ?, "
                + "CodProveedor = ?, "
                + "DescripcionArticulo = ?, "
                + "Presentacion = ?, "
                + "PrecioProveedor = ?, "
                + "StockActual = ?, "
                + "StockMinimo = ?, "
                + "Descontinuado = ? "
                + "WHERE CodArticulo = ?";

        try (Connection con = ConexionDB.obtenerConexion();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, a.getCodLinea());
            ps.setInt(2, a.getCodProveedor());
            ps.setString(3, a.getDescripcionArticulo());
            ps.setString(4, a.getPresentacion());
            ps.setDouble(5, a.getPrecioProveedor());
            ps.setShort(6, a.getStockActual());
            ps.setShort(7, a.getStockMinimo());
            ps.setBoolean(8, a.isDescontinuado());
            ps.setInt(9, a.getCodArticulo());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al actualizar artículo: " + e.getMessage());
            return false;
        }
    }

    public boolean tieneDetallesOrden(int codArticulo) {

        String sql = "SELECT COUNT(*) AS total "
                + "FROM ORDEN_DETALLE "
                + "WHERE CodArticulo = ?";

        try (Connection con = ConexionDB.obtenerConexion();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, codArticulo);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return rs.getInt("total") > 0;
                }

            }

        } catch (SQLException e) {
            System.out.println("Error al verificar detalle de orden: " + e.getMessage());
        }

        return false;
    }

    public boolean tieneDetallesGuia(int codArticulo) {

        String sql = "SELECT COUNT(*) AS total "
                + "FROM GUIA_DETALLE "
                + "WHERE CodArticulo = ?";

        try (Connection con = ConexionDB.obtenerConexion();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, codArticulo);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return rs.getInt("total") > 0;
                }

            }

        } catch (SQLException e) {
            System.out.println("Error al verificar detalle de guía: " + e.getMessage());
        }

        return false;
    }

    public boolean eliminar(int codArticulo) {

        String sql = "DELETE FROM ARTICULO WHERE CodArticulo = ?";

        try (Connection con = ConexionDB.obtenerConexion();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, codArticulo);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al eliminar artículo: " + e.getMessage());
            return false;
        }
    }

    public int contarArticulos() {

        String sql = "SELECT COUNT(*) AS total FROM ARTICULO";

        try (Connection con = ConexionDB.obtenerConexion();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("total");
            }

        } catch (SQLException e) {
            System.out.println("Error al contar artículos: " + e.getMessage());
        }

        return 0;
    }

}