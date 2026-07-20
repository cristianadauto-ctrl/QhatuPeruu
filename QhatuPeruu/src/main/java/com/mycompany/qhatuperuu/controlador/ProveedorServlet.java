/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qhatuperuu.controlador;

import com.mycompany.qhatuperuu.dao.ProveedorDAO;
import com.mycompany.qhatuperuu.modelo.Proveedor;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/proveedores")
public class ProveedorServlet extends HttpServlet {

    private final ProveedorDAO proveedorDAO = new ProveedorDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        if (accion == null) {
            accion = "listar";
        }

        switch (accion) {

            case "nuevo":
                request.getRequestDispatcher("proveedores/formulario.jsp").forward(request, response);
                break;

            case "editar":

                int idEditar = Integer.parseInt(request.getParameter("id"));
                Proveedor proveedorEditar = proveedorDAO.buscarPorId(idEditar);

                if (proveedorEditar == null) {

                    request.setAttribute("mensajeError", "El proveedor solicitado no existe.");
                    listarProveedores(request);
                    request.getRequestDispatcher("proveedores/lista.jsp").forward(request, response);
                    return;

                }

                request.setAttribute("proveedor", proveedorEditar);
                request.getRequestDispatcher("proveedores/formulario.jsp").forward(request, response);
                break;

            case "listar":
            default:

                listarProveedores(request);
                request.getRequestDispatcher("proveedores/lista.jsp").forward(request, response);
                break;

        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String accion = request.getParameter("accion");

        if (accion == null) {
            response.sendRedirect(request.getContextPath() + "/proveedores");
            return;
        }

        switch (accion) {

            case "guardar":
                guardarProveedor(request, response);
                break;

            case "eliminar":
                eliminarProveedor(request, response);
                break;

            default:
                response.sendRedirect(request.getContextPath() + "/proveedores");
                break;

        }

    }

    private void guardarProveedor(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("codProveedor");
        String nomProveedor = request.getParameter("nomProveedor");
        String representante = request.getParameter("representante");
        String direccion = request.getParameter("direccion");
        String ciudad = request.getParameter("ciudad");
        String departamento = request.getParameter("departamento");
        String codigoPostal = request.getParameter("codigoPostal");
        String telefono = request.getParameter("telefono");
        String fax = request.getParameter("fax");

        boolean esNuevo = (idParam == null || idParam.trim().isEmpty());

        if (nomProveedor == null || nomProveedor.trim().isEmpty()) {

            request.setAttribute("mensajeError", "El nombre del proveedor es obligatorio.");
            request.getRequestDispatcher("proveedores/formulario.jsp").forward(request, response);
            return;

        }

        Proveedor proveedor = new Proveedor();

        proveedor.setNomProveedor(nomProveedor.trim());
        proveedor.setRepresentante(representante != null ? representante.trim() : "");
        proveedor.setDireccion(direccion != null ? direccion.trim() : "");
        proveedor.setCiudad(ciudad != null ? ciudad.trim() : "");
        proveedor.setDepartamento(departamento != null ? departamento.trim() : "");
        proveedor.setCodigoPostal(codigoPostal != null ? codigoPostal.trim() : "");
        proveedor.setTelefono(telefono != null ? telefono.trim() : "");
        proveedor.setFax(fax != null ? fax.trim() : "");

        boolean exito;

        if (esNuevo) {

            exito = proveedorDAO.crear(proveedor);

        } else {

            proveedor.setCodProveedor(Integer.parseInt(idParam));
            exito = proveedorDAO.actualizar(proveedor);

        }

        if (!exito) {

            request.setAttribute("mensajeError", "Ocurrió un error al guardar el proveedor.");
            request.setAttribute("proveedor", proveedor);
            request.getRequestDispatcher("proveedores/formulario.jsp").forward(request, response);
            return;

        }

        response.sendRedirect(request.getContextPath() + "/proveedores");

    }

    private void eliminarProveedor(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int codProveedor = Integer.parseInt(request.getParameter("id"));

        if (proveedorDAO.tieneArticulosAsignados(codProveedor)) {

            request.setAttribute("mensajeError",
                    "No se puede eliminar el proveedor porque tiene artículos asociados.");

            listarProveedores(request);

            request.getRequestDispatcher("proveedores/lista.jsp").forward(request, response);
            return;

        }

        proveedorDAO.eliminar(codProveedor);

        response.sendRedirect(request.getContextPath() + "/proveedores");

    }

    private void listarProveedores(HttpServletRequest request) {

        request.setAttribute("listaProveedores", proveedorDAO.listarTodos());

    }

}