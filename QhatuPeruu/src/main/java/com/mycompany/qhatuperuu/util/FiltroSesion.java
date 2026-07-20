/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qhatuperuu.util;

import java.io.IOException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebFilter(urlPatterns = {"/*"})
public class FiltroSesion implements Filter {

    // Rutas que NO requieren sesión activa
    private static final String[] RUTAS_PUBLICAS = {
        "/login", "/login.jsp", "/index.jsp", "/acceso-denegado.jsp",
        "/resources/"
    };

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String path = req.getRequestURI().substring(req.getContextPath().length());

        // Si es una ruta pública, dejamos pasar sin validar sesión
        for (String publica : RUTAS_PUBLICAS) {
            if (path.equals(publica) || path.startsWith(publica)) {
                chain.doFilter(request, response);
                return;
            }
        }

        HttpSession session = req.getSession(false);
        boolean sesionValida = (session != null && session.getAttribute("usuarioLogueado") != null);

        if (!sesionValida) {
            String destino = path;
            if (req.getQueryString() != null) {
                destino += "?" + req.getQueryString();
            }

            HttpSession nuevaSesion = req.getSession();
            nuevaSesion.setAttribute("urlDestino", destino);
            nuevaSesion.setAttribute("mensajeError", "Debe iniciar sesión para continuar.");
            res.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        // ============================================================
        // RESTRICCIÓN POR ROL: Solo Administrador puede acceder a estos módulos
        // ============================================================
        String nomRol = (String) session.getAttribute("nomRol");
        boolean esAdministrador = (nomRol != null && nomRol.equalsIgnoreCase("Administrador"));

        // Módulos que requieren rol de Administrador
        String[] modulosRestringidos = {
            "/dashboard",
            "/usuarios",
            "/roles",
            "/lineas",
            "/proveedores",
            "/articulos",
            "/transportistas",
            "/tiendas",          // ← AGREGADO
            "/compras",
            "/guias",
            "/reportes"
        };

        for (String modulo : modulosRestringidos) {
            if (path.equals(modulo) || path.startsWith(modulo + "?") || path.startsWith(modulo + "/")) {
                if (!esAdministrador) {
                    req.getRequestDispatcher("/acceso-denegado.jsp").forward(request, response);
                    return;
                }
                break;
            }
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}