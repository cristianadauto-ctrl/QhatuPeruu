/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qhatuperuu.controlador;

import com.mycompany.qhatuperuu.dao.RolDAO;
import com.mycompany.qhatuperuu.modelo.Rol;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/roles")
public class RolServlet extends HttpServlet {

    private final RolDAO rolDAO = new RolDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");
        if (accion == null) {
            accion = "listar";
        }

        switch (accion) {
            case "nuevo":
                request.getRequestDispatcher("roles/formulario.jsp").forward(request, response);
                break;

            case "editar":
                int idEditar = Integer.parseInt(request.getParameter("id"));
                Rol rolEditar = rolDAO.buscarPorId(idEditar);

                if (rolEditar == null) {
                    request.setAttribute("mensajeError", "El rol solicitado no existe.");
                    listarRoles(request);
                    request.getRequestDispatcher("roles/lista.jsp").forward(request, response);
                    return;
                }

                request.setAttribute("rol", rolEditar);
                request.getRequestDispatcher("roles/formulario.jsp").forward(request, response);
                break;

            case "listar":
            default:
                listarRoles(request);
                request.getRequestDispatcher("roles/lista.jsp").forward(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String accion = request.getParameter("accion");

        if (accion == null) {
            response.sendRedirect(request.getContextPath() + "/roles");
            return;
        }

        switch (accion) {
            case "guardar":
                guardarRol(request, response);
                break;

            case "eliminar":
                eliminarRol(request, response);
                break;

            default:
                response.sendRedirect(request.getContextPath() + "/roles");
                break;
        }
    }

    private void guardarRol(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("codRol");
        String nomRol = request.getParameter("nomRol");
        String descripcion = request.getParameter("descripcion");

        if (nomRol == null || nomRol.trim().isEmpty()) {
            request.setAttribute("mensajeError", "El nombre del rol es obligatorio.");
            request.getRequestDispatcher("roles/formulario.jsp").forward(request, response);
            return;
        }

        Rol rol = new Rol();
        rol.setNomRol(nomRol.trim());
        rol.setDescripcion(descripcion != null ? descripcion.trim() : "");

        boolean exito;
        boolean esNuevo = (idParam == null || idParam.trim().isEmpty());

        if (esNuevo) {
            exito = rolDAO.crear(rol);
        } else {
            rol.setCodRol(Integer.parseInt(idParam));
            exito = rolDAO.actualizar(rol);
        }

        if (!exito) {
            request.setAttribute("mensajeError", "Ocurrió un error al guardar el rol.");
            request.setAttribute("rol", rol);
            request.getRequestDispatcher("roles/formulario.jsp").forward(request, response);
            return;
        }

        response.sendRedirect(request.getContextPath() + "/roles");
    }

    private void eliminarRol(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int codRol = Integer.parseInt(request.getParameter("id"));

        if (rolDAO.tieneUsuariosAsignados(codRol)) {
            request.setAttribute("mensajeError",
                    "No se puede eliminar el rol porque tiene usuarios asignados.");
            listarRoles(request);
            request.getRequestDispatcher("roles/lista.jsp").forward(request, response);
            return;
        }

        rolDAO.eliminar(codRol);
        response.sendRedirect(request.getContextPath() + "/roles");
    }

    private void listarRoles(HttpServletRequest request) {
        request.setAttribute("listaRoles", rolDAO.listarTodos());
    }
}
