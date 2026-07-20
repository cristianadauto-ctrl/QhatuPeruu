/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qhatuperuu.controlador;

import com.mycompany.qhatuperuu.dao.TransportistaDAO;
import com.mycompany.qhatuperuu.modelo.Transportista;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/transportistas")
public class TransportistaServlet extends HttpServlet {

    private final TransportistaDAO transportistaDAO = new TransportistaDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");
        if (accion == null) {
            accion = "listar";
        }

        switch (accion) {
            case "nuevo":
                request.getRequestDispatcher("transportistas/formulario.jsp").forward(request, response);
                break;
            case "editar":
                int idEditar = Integer.parseInt(request.getParameter("id"));
                Transportista transportistaEditar = transportistaDAO.buscarPorId(idEditar);
                if (transportistaEditar == null) {
                    request.setAttribute("mensajeError", "El transportista solicitado no existe.");
                    listarTransportistas(request);
                    request.getRequestDispatcher("transportistas/lista.jsp").forward(request, response);
                    return;
                }
                request.setAttribute("transportista", transportistaEditar);
                request.getRequestDispatcher("transportistas/formulario.jsp").forward(request, response);
                break;
            case "listar":
            default:
                listarTransportistas(request);
                request.getRequestDispatcher("transportistas/lista.jsp").forward(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String accion = request.getParameter("accion");

        if (accion == null) {
            response.sendRedirect(request.getContextPath() + "/transportistas");
            return;
        }

        switch (accion) {
            case "guardar":
                guardarTransportista(request, response);
                break;
            case "eliminar":
                eliminarTransportista(request, response);
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/transportistas");
                break;
        }
    }

    private void guardarTransportista(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParam = request.getParameter("codTransportista");
        String nomTransportista = request.getParameter("nomTransportista");
        String direccion = request.getParameter("direccion");
        String telefono = request.getParameter("telefono");

        if (nomTransportista == null || nomTransportista.trim().isEmpty()) {
            request.setAttribute("mensajeError", "El nombre del transportista es obligatorio.");
            request.getRequestDispatcher("transportistas/formulario.jsp").forward(request, response);
            return;
        }

        Transportista transportista = new Transportista();
        transportista.setNomTransportista(nomTransportista.trim());
        transportista.setDireccion(direccion != null ? direccion.trim() : "");
        transportista.setTelefono(telefono != null ? telefono.trim() : "");

        boolean exito;
        boolean esNuevo = (idParam == null || idParam.trim().isEmpty());
        if (esNuevo) {
            // Generar un nuevo ID (buscar el máximo + 1)
            int nuevoId = transportistaDAO.contarTransportistas() + 1;
            transportista.setCodTransportista(nuevoId);
            exito = transportistaDAO.crear(transportista);
        } else {
            transportista.setCodTransportista(Integer.parseInt(idParam));
            exito = transportistaDAO.actualizar(transportista);
        }

        if (!exito) {
            request.setAttribute("mensajeError", "Ocurrió un error al guardar el transportista.");
            request.setAttribute("transportista", transportista);
            request.getRequestDispatcher("transportistas/formulario.jsp").forward(request, response);
            return;
        }
        response.sendRedirect(request.getContextPath() + "/transportistas");
    }

    private void eliminarTransportista(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int codTransportista = Integer.parseInt(request.getParameter("id"));
        if (transportistaDAO.tieneGuiasAsignadas(codTransportista)) {
            request.setAttribute("mensajeError", "No se puede eliminar el transportista porque tiene guías de envío asignadas.");
            listarTransportistas(request);
            request.getRequestDispatcher("transportistas/lista.jsp").forward(request, response);
            return;
        }
        transportistaDAO.eliminar(codTransportista);
        response.sendRedirect(request.getContextPath() + "/transportistas");
    }

    private void listarTransportistas(HttpServletRequest request) {
        request.setAttribute("listaTransportistas", transportistaDAO.listarTodos());
    }
}