/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qhatuperuu.controlador;

import com.mycompany.qhatuperuu.dao.TiendaDAO;
import com.mycompany.qhatuperuu.modelo.Tienda;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/tiendas")
public class TiendaServlet extends HttpServlet {

    private final TiendaDAO tiendaDAO = new TiendaDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");
        if (accion == null) {
            accion = "listar";
        }

        switch (accion) {
            case "nuevo":
                request.getRequestDispatcher("tiendas/formulario.jsp").forward(request, response);
                break;
            case "editar":
                int idEditar = Integer.parseInt(request.getParameter("id"));
                Tienda tiendaEditar = tiendaDAO.buscarPorId(idEditar);
                if (tiendaEditar == null) {
                    request.setAttribute("mensajeError", "La tienda solicitada no existe.");
                    listarTiendas(request);
                    request.getRequestDispatcher("tiendas/lista.jsp").forward(request, response);
                    return;
                }
                request.setAttribute("tienda", tiendaEditar);
                request.getRequestDispatcher("tiendas/formulario.jsp").forward(request, response);
                break;
            case "listar":
            default:
                listarTiendas(request);
                request.getRequestDispatcher("tiendas/lista.jsp").forward(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String accion = request.getParameter("accion");

        if (accion == null) {
            response.sendRedirect(request.getContextPath() + "/tiendas");
            return;
        }

        switch (accion) {
            case "guardar":
                guardarTienda(request, response);
                break;
            case "eliminar":
                eliminarTienda(request, response);
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/tiendas");
                break;
        }
    }

    private void guardarTienda(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParam = request.getParameter("codTienda");
        String direccion = request.getParameter("direccion");
        String distrito = request.getParameter("distrito");
        String telefono = request.getParameter("telefono");
        String fax = request.getParameter("fax");

        // Validar que se haya ingresado al menos dirección o distrito
        if ((direccion == null || direccion.trim().isEmpty()) && 
            (distrito == null || distrito.trim().isEmpty())) {
            request.setAttribute("mensajeError", "Debe ingresar al menos la dirección o el distrito de la tienda.");
            request.getRequestDispatcher("tiendas/formulario.jsp").forward(request, response);
            return;
        }

        Tienda tienda = new Tienda();
        tienda.setDireccion(direccion != null ? direccion.trim() : "");
        tienda.setDistrito(distrito != null ? distrito.trim() : "");
        tienda.setTelefono(telefono != null ? telefono.trim() : "");
        tienda.setFax(fax != null ? fax.trim() : "");

        boolean exito;
        boolean esNuevo = (idParam == null || idParam.trim().isEmpty());
        if (esNuevo) {
            // Generar un nuevo ID (buscar el máximo + 1)
            int nuevoId = tiendaDAO.contarTiendas() + 1;
            tienda.setCodTienda(nuevoId);
            exito = tiendaDAO.crear(tienda);
        } else {
            tienda.setCodTienda(Integer.parseInt(idParam));
            exito = tiendaDAO.actualizar(tienda);
        }

        if (!exito) {
            request.setAttribute("mensajeError", "Ocurrió un error al guardar la tienda.");
            request.setAttribute("tienda", tienda);
            request.getRequestDispatcher("tiendas/formulario.jsp").forward(request, response);
            return;
        }
        response.sendRedirect(request.getContextPath() + "/tiendas");
    }

    private void eliminarTienda(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int codTienda = Integer.parseInt(request.getParameter("id"));
        if (tiendaDAO.tieneGuiasAsignadas(codTienda)) {
            request.setAttribute("mensajeError", "No se puede eliminar la tienda porque tiene guías de envío asignadas.");
            listarTiendas(request);
            request.getRequestDispatcher("tiendas/lista.jsp").forward(request, response);
            return;
        }
        tiendaDAO.eliminar(codTienda);
        response.sendRedirect(request.getContextPath() + "/tiendas");
    }

    private void listarTiendas(HttpServletRequest request) {
        request.setAttribute("listaTiendas", tiendaDAO.listarTodos());
    }
}