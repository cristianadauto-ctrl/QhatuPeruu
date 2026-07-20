/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qhatuperuu.dao;

import com.mycompany.qhatuperuu.modelo.GuiaEnvio;
import com.mycompany.qhatuperuu.modelo.GuiaDetalle;
import com.mycompany.qhatuperuu.util.ConexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GuiaEnvioDAO {

    public List<GuiaEnvio> listarTodos() {
        List<GuiaEnvio> lista = new ArrayList<>();
        String sql = "SELECT g.NumGuia, g.CodTienda, g.FechaSalida, g.CodTransportista, "
                   + "t.Direccion AS NomTienda, "
                   + "tr.NomTransportista "
                   + "FROM GUIA_ENVIO g "
                   + "INNER JOIN TIENDA t ON g.CodTienda = t.CodTienda "
                   + "INNER JOIN TRANSPORTISTA tr ON g.CodTransportista = tr.CodTransportista "
                   + "ORDER BY g.NumGuia DESC";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                GuiaEnvio g = new GuiaEnvio();
                g.setNumGuia(rs.getInt("NumGuia"));
                g.setCodTienda(rs.getInt("CodTienda"));
                g.setFechaSalida(rs.getTimestamp("FechaSalida").toLocalDateTime());
                g.setCodTransportista(rs.getInt("CodTransportista"));
                g.setNomTienda(rs.getString("NomTienda"));
                g.setNomTransportista(rs.getString("NomTransportista"));
                g.setTotalDetalles(contarDetallesPorGuia(g.getNumGuia()));
                lista.add(g);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar guías de envío: " + e.getMessage());
        }
        return lista;
    }

    public GuiaEnvio buscarPorId(int numGuia) {
        GuiaEnvio g = null;
        String sql = "SELECT g.NumGuia, g.CodTienda, g.FechaSalida, g.CodTransportista, "
                   + "t.Direccion AS NomTienda, "
                   + "tr.NomTransportista "
                   + "FROM GUIA_ENVIO g "
                   + "INNER JOIN TIENDA t ON g.CodTienda = t.CodTienda "
                   + "INNER JOIN TRANSPORTISTA tr ON g.CodTransportista = tr.CodTransportista "
                   + "WHERE g.NumGuia = ?";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, numGuia);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    g = new GuiaEnvio();
                    g.setNumGuia(rs.getInt("NumGuia"));
                    g.setCodTienda(rs.getInt("CodTienda"));
                    g.setFechaSalida(rs.getTimestamp("FechaSalida").toLocalDateTime());
                    g.setCodTransportista(rs.getInt("CodTransportista"));
                    g.setNomTienda(rs.getString("NomTienda"));
                    g.setNomTransportista(rs.getString("NomTransportista"));
                    g.setDetalles(cargarDetalles(numGuia));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar guía de envío por ID: " + e.getMessage());
        }
        return g;
    }

    public boolean crear(GuiaEnvio g) {
        String sql = "INSERT INTO GUIA_ENVIO (NumGuia, CodTienda, FechaSalida, CodTransportista) "
                   + "VALUES (?, ?, ?, ?)";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, g.getNumGuia());
            ps.setInt(2, g.getCodTienda());
            ps.setObject(3, g.getFechaSalida());
            ps.setInt(4, g.getCodTransportista());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al crear guía de envío: " + e.getMessage());
            return false;
        }
    }

    public boolean actualizar(GuiaEnvio g) {
        String sql = "UPDATE GUIA_ENVIO SET CodTienda = ?, FechaSalida = ?, CodTransportista = ? "
                   + "WHERE NumGuia = ?";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, g.getCodTienda());
            ps.setObject(2, g.getFechaSalida());
            ps.setInt(3, g.getCodTransportista());
            ps.setInt(4, g.getNumGuia());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al actualizar guía de envío: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(int numGuia) {
        // Primero eliminar los detalles (por FK)
        String sqlDetalle = "DELETE FROM GUIA_DETALLE WHERE NumGuia = ?";
        String sqlCabecera = "DELETE FROM GUIA_ENVIO WHERE NumGuia = ?";
        try (Connection con = ConexionDB.obtenerConexion()) {
            con.setAutoCommit(false);
            try (PreparedStatement psDetalle = con.prepareStatement(sqlDetalle)) {
                psDetalle.setInt(1, numGuia);
                psDetalle.executeUpdate();
            }
            try (PreparedStatement psCabecera = con.prepareStatement(sqlCabecera)) {
                psCabecera.setInt(1, numGuia);
                psCabecera.executeUpdate();
            }
            con.commit();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al eliminar guía de envío: " + e.getMessage());
            return false;
        }
    }

    public int contarGuias() {
        String sql = "SELECT COUNT(*) AS total FROM GUIA_ENVIO";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            System.out.println("Error al contar guías de envío: " + e.getMessage());
        }
        return 0;
    }

    public int obtenerSiguienteNumGuia() {
        return contarGuias() + 1;
    }

    private int contarDetallesPorGuia(int numGuia) {
        String sql = "SELECT COUNT(*) AS total FROM GUIA_DETALLE WHERE NumGuia = ?";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, numGuia);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al contar detalles de guía: " + e.getMessage());
        }
        return 0;
    }

    private List<GuiaDetalle> cargarDetalles(int numGuia) {
        List<GuiaDetalle> detalles = new ArrayList<>();
        String sql = "SELECT gd.NumGuia, gd.CodArticulo, gd.PrecioVenta, gd.CantidadEnviada, "
                   + "a.DescripcionArticulo, a.Presentacion "
                   + "FROM GUIA_DETALLE gd "
                   + "INNER JOIN ARTICULO a ON gd.CodArticulo = a.CodArticulo "
                   + "WHERE gd.NumGuia = ?";
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
                    detalles.add(d);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al cargar detalles de guía: " + e.getMessage());
        }
        return detalles;
    }
}