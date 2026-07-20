/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qhatuperuu.controlador;

import com.mycompany.qhatuperuu.dao.UsuarioDAO;
import com.mycompany.qhatuperuu.modelo.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String clave = request.getParameter("clave");

        if (username == null || username.trim().isEmpty()
                || clave == null || clave.trim().isEmpty()) {
            request.setAttribute("mensajeError", "Debe ingresar usuario y contraseña.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        Usuario usuario = usuarioDAO.buscarPorUsername(username.trim());

        if (usuario == null) {
            request.setAttribute("mensajeError", "Usuario o contraseña incorrectos.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        if (!usuario.isEstado()) {
            request.setAttribute("mensajeError", "Su cuenta se encuentra inactiva. Contacte al administrador.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

      if (!clave.equals(usuario.getClave())) {
    request.setAttribute("mensajeError", "Usuario o contraseña incorrectos.");
    request.getRequestDispatcher("login.jsp").forward(request, response);
    return;
}

        // Login exitoso: crear sesión
HttpSession session = request.getSession();
session.setAttribute("usuarioLogueado", usuario);
session.setAttribute("nombreCompleto", usuario.getNombreCompleto());
session.setAttribute("nomRol", usuario.getNomRol());

// Si el usuario intentaba entrar a una página específica antes de loguearse, lo llevamos ahí
String destino = (String) session.getAttribute("urlDestino");
session.removeAttribute("urlDestino");

if (destino != null && !destino.isEmpty()) {
    response.sendRedirect(request.getContextPath() + destino);
} else {
    response.sendRedirect(request.getContextPath() + "/dashboard");
}
    }
}