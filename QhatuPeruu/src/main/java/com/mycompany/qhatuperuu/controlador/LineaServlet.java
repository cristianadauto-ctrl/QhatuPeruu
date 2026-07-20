/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qhatuperuu.controlador;

import com.mycompany.qhatuperuu.dao.LineaDAO;
import com.mycompany.qhatuperuu.modelo.Linea;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/lineas")
public class LineaServlet extends HttpServlet {

    private final LineaDAO lineaDAO = new LineaDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");
        if (accion == null) {
            accion = "listar";
        }

        switch (accion) {
            case "nuevo":
                request.getRequestDispatcher("lineas/formulario.jsp").forward(request, response);
                break;

            case "editar":
                int idEditar = Integer.parseInt(request.getParameter("id"));
                Linea lineaEditar = lineaDAO.buscarPorId(idEditar);

                if (lineaEditar == null) {
                    request.setAttribute("mensajeError", "La línea de producto solicitada no existe.");
                    listarLineas(request);
                    request.getRequestDispatcher("lineas/lista.jsp").forward(request, response);
                    return;
                }

                request.setAttribute("linea", lineaEditar);
                request.getRequestDispatcher("lineas/formulario.jsp").forward(request, response);
                break;

            case "listar":
            default:
                listarLineas(request);
                request.getRequestDispatcher("lineas/lista.jsp").forward(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String accion = request.getParameter("accion");

        if (accion == null) {
            response.sendRedirect(request.getContextPath() + "/lineas");
            return;
        }

        switch (accion) {
            case "guardar":
                guardarLinea(request, response);
                break;

            case "eliminar":
                eliminarLinea(request, response);
                break;

            default:
                response.sendRedirect(request.getContextPath() + "/lineas");
                break;
        }
    }

    private void guardarLinea(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("codLinea");
        String nomLinea = request.getParameter("nomLinea");
        String descripcion = request.getParameter("descripcion");

        boolean esNuevo = (idParam == null || idParam.trim().isEmpty());

        // Validaciones básicas
        if (nomLinea == null || nomLinea.trim().isEmpty()) {
            request.setAttribute("mensajeError", "El nombre de la línea es obligatorio.");
            request.getRequestDispatcher("lineas/formulario.jsp").forward(request, response);
            return;
        }

        Linea linea = new Linea();
        linea.setNomLinea(nomLinea.trim());
        linea.setDescripcion(descripcion != null ? descripcion.trim() : "");

        boolean exito;

        if (esNuevo) {
            exito = lineaDAO.crear(linea);
        } else {
            linea.setCodLinea(Integer.parseInt(idParam));
            exito = lineaDAO.actualizar(linea);
        }

        if (!exito) {
            request.setAttribute("mensajeError", "Ocurrió un error al guardar la línea.");
            request.setAttribute("linea", linea);
            request.getRequestDispatcher("lineas/formulario.jsp").forward(request, response);
            return;
        }

        response.sendRedirect(request.getContextPath() + "/lineas");
    }

    private void eliminarLinea(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int codLinea = Integer.parseInt(request.getParameter("id"));
        lineaDAO.eliminar(codLinea);
        response.sendRedirect(request.getContextPath() + "/lineas");
    }

    private void listarLineas(HttpServletRequest request) {
        request.setAttribute("listaLineas", lineaDAO.listarTodos());
    }
}