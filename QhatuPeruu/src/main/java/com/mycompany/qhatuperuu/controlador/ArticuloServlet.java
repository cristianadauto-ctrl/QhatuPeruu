/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qhatuperuu.controlador;

import com.mycompany.qhatuperuu.dao.ArticuloDAO;
import com.mycompany.qhatuperuu.dao.LineaDAO;
import com.mycompany.qhatuperuu.dao.ProveedorDAO;
import com.mycompany.qhatuperuu.modelo.Articulo;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/articulos")
public class ArticuloServlet extends HttpServlet {

    private final ArticuloDAO articuloDAO = new ArticuloDAO();
    private final LineaDAO lineaDAO = new LineaDAO();
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

                request.setAttribute("listaLineas", lineaDAO.listarTodos());
                request.setAttribute("listaProveedores", proveedorDAO.listarTodos());

                request.getRequestDispatcher("articulos/formulario.jsp")
                        .forward(request, response);

                break;

            case "editar":

                int idEditar = Integer.parseInt(request.getParameter("id"));

                Articulo articuloEditar = articuloDAO.buscarPorId(idEditar);

                if (articuloEditar == null) {

                    request.setAttribute("mensajeError",
                            "El artículo solicitado no existe.");

                    listarArticulos(request);

                    request.getRequestDispatcher("articulos/lista.jsp")
                            .forward(request, response);

                    return;
                }

                request.setAttribute("articulo", articuloEditar);
                request.setAttribute("listaLineas", lineaDAO.listarTodos());
                request.setAttribute("listaProveedores", proveedorDAO.listarTodos());

                request.getRequestDispatcher("articulos/formulario.jsp")
                        .forward(request, response);

                break;

            case "listar":
            default:

                listarArticulos(request);

                request.getRequestDispatcher("articulos/lista.jsp")
                        .forward(request, response);

                break;

        }

    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String accion = request.getParameter("accion");

        if (accion == null) {

            response.sendRedirect(request.getContextPath() + "/articulos");
            return;

        }

        switch (accion) {

            case "guardar":

                guardarArticulo(request, response);

                break;

            case "eliminar":

                eliminarArticulo(request, response);

                break;

            default:

                response.sendRedirect(request.getContextPath() + "/articulos");

                break;

        }

    }

    private void guardarArticulo(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("codArticulo");

        String codLinea = request.getParameter("codLinea");
        String codProveedor = request.getParameter("codProveedor");
        String descripcion = request.getParameter("descripcionArticulo");
        String presentacion = request.getParameter("presentacion");
        String precio = request.getParameter("precioProveedor");
        String stockActual = request.getParameter("stockActual");
        String stockMinimo = request.getParameter("stockMinimo");
        String descontinuado = request.getParameter("descontinuado");

        boolean esNuevo = (idParam == null || idParam.trim().isEmpty());

        if (codLinea == null || codLinea.trim().isEmpty()
                || codProveedor == null || codProveedor.trim().isEmpty()
                || descripcion == null || descripcion.trim().isEmpty()) {

            request.setAttribute("mensajeError",
                    "La línea, proveedor y descripción son obligatorios.");

            request.setAttribute("listaLineas", lineaDAO.listarTodos());
            request.setAttribute("listaProveedores", proveedorDAO.listarTodos());

            request.getRequestDispatcher("articulos/formulario.jsp")
                    .forward(request, response);

            return;
        }

        Articulo articulo = new Articulo();

        articulo.setCodLinea(Integer.parseInt(codLinea));
        articulo.setCodProveedor(Integer.parseInt(codProveedor));
        articulo.setDescripcionArticulo(descripcion.trim());

        articulo.setPresentacion(
                presentacion != null ? presentacion.trim() : "");

        articulo.setPrecioProveedor(
                precio != null && !precio.isEmpty()
                ? Double.parseDouble(precio)
                : 0);

        articulo.setStockActual(
                stockActual != null && !stockActual.isEmpty()
                ? Short.parseShort(stockActual)
                : (short) 0);

        articulo.setStockMinimo(
                stockMinimo != null && !stockMinimo.isEmpty()
                ? Short.parseShort(stockMinimo)
                : (short) 0);

        articulo.setDescontinuado("1".equals(descontinuado));

        boolean exito;

        if (esNuevo) {

            exito = articuloDAO.crear(articulo);

        } else {

            articulo.setCodArticulo(Integer.parseInt(idParam));

            exito = articuloDAO.actualizar(articulo);

        }

        if (!exito) {

            request.setAttribute("mensajeError",
                    "Ocurrió un error al guardar el artículo.");

            request.setAttribute("articulo", articulo);
            request.setAttribute("listaLineas", lineaDAO.listarTodos());
            request.setAttribute("listaProveedores", proveedorDAO.listarTodos());

            request.getRequestDispatcher("articulos/formulario.jsp")
                    .forward(request, response);

            return;

        }

        response.sendRedirect(request.getContextPath() + "/articulos");

    }

    private void eliminarArticulo(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        int codArticulo = Integer.parseInt(request.getParameter("id"));

        if (articuloDAO.tieneDetallesOrden(codArticulo)
                || articuloDAO.tieneDetallesGuia(codArticulo)) {

            request.setAttribute("mensajeError",
                    "No se puede eliminar el artículo porque tiene movimientos registrados.");

            listarArticulos(request);

            request.getRequestDispatcher("articulos/lista.jsp")
                    .forward(request, response);

            return;

        }

        articuloDAO.eliminar(codArticulo);

        response.sendRedirect(request.getContextPath() + "/articulos");

    }

    private void listarArticulos(HttpServletRequest request) {

        request.setAttribute("listaArticulos",
                articuloDAO.listarTodos());

    }

}