/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qhatuperuu.controlador;

import com.mycompany.qhatuperuu.dao.RolDAO;
import com.mycompany.qhatuperuu.dao.UsuarioDAO;
import com.mycompany.qhatuperuu.modelo.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/usuarios")
public class UsuarioServlet extends HttpServlet {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
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
                request.setAttribute("listaRoles", rolDAO.listarTodos());
                request.getRequestDispatcher("usuarios/formulario.jsp").forward(request, response);
                break;

            case "editar":
                int idEditar = Integer.parseInt(request.getParameter("id"));
                Usuario usuarioEditar = usuarioDAO.buscarPorId(idEditar);

                if (usuarioEditar == null) {
                    request.setAttribute("mensajeError", "El usuario solicitado no existe.");
                    listarUsuarios(request);
                    request.getRequestDispatcher("usuarios/lista.jsp").forward(request, response);
                    return;
                }

                request.setAttribute("usuario", usuarioEditar);
                request.setAttribute("listaRoles", rolDAO.listarTodos());
                request.getRequestDispatcher("usuarios/formulario.jsp").forward(request, response);
                break;

            case "listar":
            default:
                listarUsuarios(request);
                request.getRequestDispatcher("usuarios/lista.jsp").forward(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String accion = request.getParameter("accion");

        if (accion == null) {
            response.sendRedirect(request.getContextPath() + "/usuarios");
            return;
        }

        switch (accion) {
            case "guardar":
                guardarUsuario(request, response);
                break;

            case "eliminar":
                eliminarUsuario(request, response);
                break;

            default:
                response.sendRedirect(request.getContextPath() + "/usuarios");
                break;
        }
    }

    private void guardarUsuario(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("codUsuario");
        String username = request.getParameter("username");
        String clave = request.getParameter("clave");
        String nombres = request.getParameter("nombres");
        String apellidos = request.getParameter("apellidos");
        String correo = request.getParameter("correo");
        String estadoParam = request.getParameter("estado");
        String codRolParam = request.getParameter("codRol");

        boolean esNuevo = (idParam == null || idParam.trim().isEmpty());

        // Validaciones básicas
        if (username == null || username.trim().isEmpty()
                || nombres == null || nombres.trim().isEmpty()
                || codRolParam == null || codRolParam.trim().isEmpty()) {

            request.setAttribute("mensajeError", "Los campos usuario, nombres y rol son obligatorios.");
            request.setAttribute("listaRoles", rolDAO.listarTodos());
            request.getRequestDispatcher("usuarios/formulario.jsp").forward(request, response);
            return;
        }

        if (esNuevo && (clave == null || clave.trim().isEmpty())) {
            request.setAttribute("mensajeError", "La contraseña es obligatoria para un usuario nuevo.");
            request.setAttribute("listaRoles", rolDAO.listarTodos());
            request.getRequestDispatcher("usuarios/formulario.jsp").forward(request, response);
            return;
        }

        Usuario usuario = new Usuario();
        usuario.setUsername(username.trim());
        usuario.setNombres(nombres.trim());
        usuario.setApellidos(apellidos != null ? apellidos.trim() : "");
        usuario.setCorreo(correo != null ? correo.trim() : "");
        usuario.setEstado("1".equals(estadoParam));
        usuario.setCodRol(Integer.parseInt(codRolParam));

        boolean exito;

        if (esNuevo) {
    usuario.setClave(clave.trim());
    exito = usuarioDAO.crear(usuario);
} else {
    usuario.setCodUsuario(Integer.parseInt(idParam));

    if (clave != null && !clave.trim().isEmpty()) {
        usuario.setClave(clave.trim());
        exito = usuarioDAO.actualizarConClave(usuario);
    } else {
        exito = usuarioDAO.actualizarSinClave(usuario);
    }
}

        if (!exito) {
            request.setAttribute("mensajeError", "Ocurrió un error al guardar el usuario.");
            request.setAttribute("usuario", usuario);
            request.setAttribute("listaRoles", rolDAO.listarTodos());
            request.getRequestDispatcher("usuarios/formulario.jsp").forward(request, response);
            return;
        }

        response.sendRedirect(request.getContextPath() + "/usuarios");
    }

    private void eliminarUsuario(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int codUsuario = Integer.parseInt(request.getParameter("id"));
        usuarioDAO.eliminar(codUsuario);
        response.sendRedirect(request.getContextPath() + "/usuarios");
    }

    private void listarUsuarios(HttpServletRequest request) {
        request.setAttribute("listaUsuarios", usuarioDAO.listarTodos());
    }
}
