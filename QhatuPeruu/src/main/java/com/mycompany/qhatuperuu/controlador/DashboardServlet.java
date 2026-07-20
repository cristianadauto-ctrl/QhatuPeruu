/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qhatuperuu.controlador;

import com.mycompany.qhatuperuu.dao.ArticuloDAO;
import com.mycompany.qhatuperuu.dao.GuiaEnvioDAO;
import com.mycompany.qhatuperuu.dao.OrdenCompraDAO;
import com.mycompany.qhatuperuu.dao.ProveedorDAO;
import com.mycompany.qhatuperuu.dao.RolDAO;
import com.mycompany.qhatuperuu.dao.UsuarioDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    private final RolDAO rolDAO = new RolDAO();
    private final ArticuloDAO articuloDAO = new ArticuloDAO();
    private final ProveedorDAO proveedorDAO = new ProveedorDAO();
    private final OrdenCompraDAO ordenCompraDAO = new OrdenCompraDAO();
    private final GuiaEnvioDAO guiaEnvioDAO = new GuiaEnvioDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        int totalUsuarios = usuarioDAO.contarUsuarios();
        int totalRoles = rolDAO.contarRoles();
        int usuariosActivos = usuarioDAO.contarActivos();
        int usuariosInactivos = usuarioDAO.contarInactivos();
        int totalArticulos = articuloDAO.contarArticulos();
        int totalProveedores = proveedorDAO.contarProveedores();
        int totalCompras = ordenCompraDAO.contarOrdenes();
        int totalGuias = guiaEnvioDAO.contarGuias();

        request.setAttribute("totalUsuarios", totalUsuarios);
        request.setAttribute("totalRoles", totalRoles);
        request.setAttribute("usuariosActivos", usuariosActivos);
        request.setAttribute("usuariosInactivos", usuariosInactivos);
        request.setAttribute("totalArticulos", totalArticulos);
        request.setAttribute("totalProveedores", totalProveedores);
        request.setAttribute("totalCompras", totalCompras);
        request.setAttribute("totalGuias", totalGuias);

        request.getRequestDispatcher("dashboard.jsp").forward(request, response);
    }
}