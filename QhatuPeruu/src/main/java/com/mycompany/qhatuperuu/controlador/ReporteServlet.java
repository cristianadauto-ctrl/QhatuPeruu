/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qhatuperuu.controlador;

import com.mycompany.qhatuperuu.dao.ArticuloDAO;
import com.mycompany.qhatuperuu.dao.OrdenCompraDAO;
import com.mycompany.qhatuperuu.dao.ProveedorDAO;
import com.mycompany.qhatuperuu.dao.UsuarioDAO;
import com.mycompany.qhatuperuu.dao.GuiaEnvioDAO;
import com.mycompany.qhatuperuu.dao.RolDAO;
import com.mycompany.qhatuperuu.modelo.Articulo;
import com.mycompany.qhatuperuu.modelo.OrdenCompra;
import com.mycompany.qhatuperuu.modelo.Proveedor;
import com.mycompany.qhatuperuu.modelo.Usuario;
import com.mycompany.qhatuperuu.modelo.GuiaEnvio;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/reportes")
public class ReporteServlet extends HttpServlet {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    private final RolDAO rolDAO = new RolDAO();
    private final ArticuloDAO articuloDAO = new ArticuloDAO();
    private final ProveedorDAO proveedorDAO = new ProveedorDAO();
    private final OrdenCompraDAO ordenCompraDAO = new OrdenCompraDAO();
    private final GuiaEnvioDAO guiaEnvioDAO = new GuiaEnvioDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");
        if (accion == null) {
            accion = "dashboard";
        }

        switch (accion) {
            case "usuarios":
                reporteUsuarios(request, response);
                break;
            case "articulos":
                reporteArticulos(request, response);
                break;
            case "compras":
                reporteCompras(request, response);
                break;
            case "guias":
                reporteGuias(request, response);
                break;
            case "proveedores":
                reporteProveedores(request, response);
                break;
            case "dashboard":
            default:
                reporteDashboard(request, response);
                break;
        }
    }

    private void reporteDashboard(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int totalUsuarios = usuarioDAO.contarUsuarios();
        int totalRoles = rolDAO.contarRoles();
        int totalArticulos = articuloDAO.contarArticulos();
        int totalProveedores = proveedorDAO.contarProveedores();
        int totalCompras = ordenCompraDAO.contarOrdenes();
        int totalGuias = guiaEnvioDAO.contarGuias();
        int usuariosActivos = usuarioDAO.contarActivos();
        int usuariosInactivos = usuarioDAO.contarInactivos();

        request.setAttribute("totalUsuarios", totalUsuarios);
        request.setAttribute("totalRoles", totalRoles);
        request.setAttribute("totalArticulos", totalArticulos);
        request.setAttribute("totalProveedores", totalProveedores);
        request.setAttribute("totalCompras", totalCompras);
        request.setAttribute("totalGuias", totalGuias);
        request.setAttribute("usuariosActivos", usuariosActivos);
        request.setAttribute("usuariosInactivos", usuariosInactivos);

        request.getRequestDispatcher("reportes/dashboard.jsp").forward(request, response);
    }

    private void reporteUsuarios(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Usuario> listaUsuarios = usuarioDAO.listarTodos();
        request.setAttribute("listaUsuarios", listaUsuarios);
        request.getRequestDispatcher("reportes/usuarios.jsp").forward(request, response);
    }

    private void reporteArticulos(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Articulo> listaArticulos = articuloDAO.listarTodos();
        request.setAttribute("listaArticulos", listaArticulos);
        request.getRequestDispatcher("reportes/articulos.jsp").forward(request, response);
    }

    private void reporteCompras(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<OrdenCompra> listaCompras = ordenCompraDAO.listarTodos();
        request.setAttribute("listaCompras", listaCompras);
        request.getRequestDispatcher("reportes/compras.jsp").forward(request, response);
    }

    private void reporteGuias(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<GuiaEnvio> listaGuias = guiaEnvioDAO.listarTodos();
        request.setAttribute("listaGuias", listaGuias);
        request.getRequestDispatcher("reportes/guias.jsp").forward(request, response);
    }

    private void reporteProveedores(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Proveedor> listaProveedores = proveedorDAO.listarTodos();
        request.setAttribute("listaProveedores", listaProveedores);
        request.getRequestDispatcher("reportes/proveedores.jsp").forward(request, response);
    }
}
