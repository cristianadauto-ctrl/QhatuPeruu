/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qhatuperuu.controlador;

import com.mycompany.qhatuperuu.dao.ArticuloDAO;
import com.mycompany.qhatuperuu.dao.OrdenCompraDAO;
import com.mycompany.qhatuperuu.dao.OrdenDetalleDAO;
import com.mycompany.qhatuperuu.dao.ProveedorDAO;
import com.mycompany.qhatuperuu.modelo.Articulo;
import com.mycompany.qhatuperuu.modelo.OrdenCompra;
import com.mycompany.qhatuperuu.modelo.OrdenDetalle;
import com.mycompany.qhatuperuu.modelo.Proveedor;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/compras")
public class OrdenCompraServlet extends HttpServlet {

    private final OrdenCompraDAO ordenCompraDAO = new OrdenCompraDAO();
    private final OrdenDetalleDAO ordenDetalleDAO = new OrdenDetalleDAO();
    private final ProveedorDAO proveedorDAO = new ProveedorDAO();
    private final ArticuloDAO articuloDAO = new ArticuloDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");
        if (accion == null) {
            accion = "listar";
        }

        switch (accion) {
            case "nuevo":
                request.setAttribute("listaProveedores", proveedorDAO.listarTodos());
                request.setAttribute("listaArticulos", articuloDAO.listarTodos());
                request.setAttribute("siguienteNumOrden", ordenCompraDAO.obtenerSiguienteNumOrden());
                request.getRequestDispatcher("compras/formulario.jsp").forward(request, response);
                break;
            case "editar":
                int idEditar = Integer.parseInt(request.getParameter("id"));
                OrdenCompra ordenEditar = ordenCompraDAO.buscarPorId(idEditar);
                if (ordenEditar == null) {
                    request.setAttribute("mensajeError", "La orden de compra solicitada no existe.");
                    listarCompras(request);
                    request.getRequestDispatcher("compras/lista.jsp").forward(request, response);
                    return;
                }
                request.setAttribute("orden", ordenEditar);
                request.setAttribute("listaArticulos", articuloDAO.listarTodos());
                request.getRequestDispatcher("compras/formulario.jsp").forward(request, response);
                break;
            case "ver":
                int idVer = Integer.parseInt(request.getParameter("id"));
                OrdenCompra ordenVer = ordenCompraDAO.buscarPorId(idVer);
                if (ordenVer == null) {
                    request.setAttribute("mensajeError", "La orden de compra solicitada no existe.");
                    listarCompras(request);
                    request.getRequestDispatcher("compras/lista.jsp").forward(request, response);
                    return;
                }
                request.setAttribute("orden", ordenVer);
                request.getRequestDispatcher("compras/ver.jsp").forward(request, response);
                break;
            case "listar":
            default:
                listarCompras(request);
                request.getRequestDispatcher("compras/lista.jsp").forward(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String accion = request.getParameter("accion");

        if (accion == null) {
            response.sendRedirect(request.getContextPath() + "/compras");
            return;
        }

        switch (accion) {
            case "guardar":
                guardarOrden(request, response);
                break;
            case "eliminar":
                eliminarOrden(request, response);
                break;
            case "agregarDetalle":
                agregarDetalle(request, response);
                break;
            case "eliminarDetalle":
                eliminarDetalle(request, response);
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/compras");
                break;
        }
    }

    private void guardarOrden(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParam = request.getParameter("numOrden");
        String fechaOrdenStr = request.getParameter("fechaOrden");
        String fechaIngresoStr = request.getParameter("fechaIngreso");

        OrdenCompra orden = new OrdenCompra();

        boolean esNuevo = (idParam == null || idParam.trim().isEmpty());
        if (esNuevo) {
            orden.setNumOrden(ordenCompraDAO.obtenerSiguienteNumOrden());
        } else {
            orden.setNumOrden(Integer.parseInt(idParam));
        }

        // Parsear fechas
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        if (fechaOrdenStr != null && !fechaOrdenStr.isEmpty()) {
            orden.setFechaOrden(LocalDateTime.parse(fechaOrdenStr, formatter));
        } else {
            orden.setFechaOrden(LocalDateTime.now());
        }

        if (fechaIngresoStr != null && !fechaIngresoStr.isEmpty()) {
            orden.setFechaIngreso(LocalDateTime.parse(fechaIngresoStr, formatter));
        }

        // Procesar detalles del formulario
        String[] codArticulos = request.getParameterValues("codArticulo[]");
        String[] precios = request.getParameterValues("precioCompra[]");
        String[] cantidades = request.getParameterValues("cantidadSolicitada[]");
        String[] estados = request.getParameterValues("estadoDetalle[]");

        List<OrdenDetalle> detalles = new ArrayList<>();
        if (codArticulos != null) {
            for (int i = 0; i < codArticulos.length; i++) {
                if (codArticulos[i] != null && !codArticulos[i].isEmpty()) {
                    OrdenDetalle d = new OrdenDetalle();
                    d.setNumOrden(orden.getNumOrden());
                    d.setCodArticulo(Integer.parseInt(codArticulos[i]));
                    d.setPrecioCompra(Double.parseDouble(precios[i]));
                    d.setCantidadSolicitada(Short.parseShort(cantidades[i]));
                    d.setCantidadRecibida(d.getCantidadSolicitada()); // Por defecto igual a solicitada
                    d.setEstado(estados != null && i < estados.length ? estados[i] : "Pendiente");
                    detalles.add(d);
                }
            }
        }

        boolean exito;
        if (esNuevo) {
            exito = ordenCompraDAO.crear(orden);
            if (exito) {
                // Guardar detalles
                for (OrdenDetalle d : detalles) {
                    ordenDetalleDAO.crear(d);
                }
            }
        } else {
            exito = ordenCompraDAO.actualizar(orden);
            if (exito) {
                // Eliminar detalles existentes y guardar nuevos
                ordenDetalleDAO.eliminarPorOrden(orden.getNumOrden());
                for (OrdenDetalle d : detalles) {
                    ordenDetalleDAO.crear(d);
                }
            }
        }

        if (!exito) {
            request.setAttribute("mensajeError", "Ocurrió un error al guardar la orden de compra.");
            request.setAttribute("orden", orden);
            request.setAttribute("listaArticulos", articuloDAO.listarTodos());
            request.getRequestDispatcher("compras/formulario.jsp").forward(request, response);
            return;
        }
        response.sendRedirect(request.getContextPath() + "/compras");
    }

    private void eliminarOrden(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int numOrden = Integer.parseInt(request.getParameter("id"));
        ordenCompraDAO.eliminar(numOrden);
        response.sendRedirect(request.getContextPath() + "/compras");
    }

    private void agregarDetalle(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Este método se usa para agregar una fila de detalle vía AJAX o similar
        // Por simplicidad, redirigimos al formulario
        response.sendRedirect(request.getContextPath() + "/compras?accion=nuevo");
    }

    private void eliminarDetalle(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int numOrden = Integer.parseInt(request.getParameter("numOrden"));
        int codArticulo = Integer.parseInt(request.getParameter("codArticulo"));
        ordenDetalleDAO.eliminar(numOrden, codArticulo);
        response.sendRedirect(request.getContextPath() + "/compras?accion=editar&id=" + numOrden);
    }

    private void listarCompras(HttpServletRequest request) {
        request.setAttribute("listaCompras", ordenCompraDAO.listarTodos());
    }
}