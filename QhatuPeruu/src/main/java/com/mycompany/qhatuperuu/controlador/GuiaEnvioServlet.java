/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qhatuperuu.controlador;

import com.mycompany.qhatuperuu.dao.ArticuloDAO;
import com.mycompany.qhatuperuu.dao.GuiaDetalleDAO;
import com.mycompany.qhatuperuu.dao.GuiaEnvioDAO;
import com.mycompany.qhatuperuu.dao.TiendaDAO;
import com.mycompany.qhatuperuu.dao.TransportistaDAO;
import com.mycompany.qhatuperuu.modelo.Articulo;
import com.mycompany.qhatuperuu.modelo.GuiaDetalle;
import com.mycompany.qhatuperuu.modelo.GuiaEnvio;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/guias")
public class GuiaEnvioServlet extends HttpServlet {

    private final GuiaEnvioDAO guiaEnvioDAO = new GuiaEnvioDAO();
    private final GuiaDetalleDAO guiaDetalleDAO = new GuiaDetalleDAO();
    private final TiendaDAO tiendaDAO = new TiendaDAO();
    private final TransportistaDAO transportistaDAO = new TransportistaDAO();
    private final ArticuloDAO articuloDAO = new ArticuloDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");
        if (accion == null) {
            accion = "listar";
        }

        switch (accion) {
            case "nuevo":
                request.setAttribute("listaTiendas", tiendaDAO.listarTodos());
                request.setAttribute("listaTransportistas", transportistaDAO.listarTodos());
                request.setAttribute("listaArticulos", articuloDAO.listarTodos());
                request.setAttribute("siguienteNumGuia", guiaEnvioDAO.obtenerSiguienteNumGuia());
                request.getRequestDispatcher("guias/formulario.jsp").forward(request, response);
                break;
            case "editar":
                int idEditar = Integer.parseInt(request.getParameter("id"));
                GuiaEnvio guiaEditar = guiaEnvioDAO.buscarPorId(idEditar);
                if (guiaEditar == null) {
                    request.setAttribute("mensajeError", "La guía de envío solicitada no existe.");
                    listarGuias(request);
                    request.getRequestDispatcher("guias/lista.jsp").forward(request, response);
                    return;
                }
                request.setAttribute("guia", guiaEditar);
                request.setAttribute("listaTiendas", tiendaDAO.listarTodos());
                request.setAttribute("listaTransportistas", transportistaDAO.listarTodos());
                request.setAttribute("listaArticulos", articuloDAO.listarTodos());
                request.getRequestDispatcher("guias/formulario.jsp").forward(request, response);
                break;
            case "ver":
                int idVer = Integer.parseInt(request.getParameter("id"));
                GuiaEnvio guiaVer = guiaEnvioDAO.buscarPorId(idVer);
                if (guiaVer == null) {
                    request.setAttribute("mensajeError", "La guía de envío solicitada no existe.");
                    listarGuias(request);
                    request.getRequestDispatcher("guias/lista.jsp").forward(request, response);
                    return;
                }
                request.setAttribute("guia", guiaVer);
                request.getRequestDispatcher("guias/ver.jsp").forward(request, response);
                break;
            case "listar":
            default:
                listarGuias(request);
                request.getRequestDispatcher("guias/lista.jsp").forward(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String accion = request.getParameter("accion");

        if (accion == null) {
            response.sendRedirect(request.getContextPath() + "/guias");
            return;
        }

        switch (accion) {
            case "guardar":
                guardarGuia(request, response);
                break;
            case "eliminar":
                eliminarGuia(request, response);
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/guias");
                break;
        }
    }

    private void guardarGuia(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParam = request.getParameter("numGuia");
        String codTienda = request.getParameter("codTienda");
        String codTransportista = request.getParameter("codTransportista");
        String fechaSalidaStr = request.getParameter("fechaSalida");

        if (codTienda == null || codTienda.trim().isEmpty()
                || codTransportista == null || codTransportista.trim().isEmpty()) {
            request.setAttribute("mensajeError", "La tienda y el transportista son obligatorios.");
            cargarDatosFormulario(request);
            request.getRequestDispatcher("guias/formulario.jsp").forward(request, response);
            return;
        }

        GuiaEnvio guia = new GuiaEnvio();

        boolean esNuevo = (idParam == null || idParam.trim().isEmpty());
        if (esNuevo) {
            guia.setNumGuia(guiaEnvioDAO.obtenerSiguienteNumGuia());
        } else {
            guia.setNumGuia(Integer.parseInt(idParam));
        }

        guia.setCodTienda(Integer.parseInt(codTienda));
        guia.setCodTransportista(Integer.parseInt(codTransportista));

        // Parsear fecha
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        if (fechaSalidaStr != null && !fechaSalidaStr.isEmpty()) {
            guia.setFechaSalida(LocalDateTime.parse(fechaSalidaStr, formatter));
        } else {
            guia.setFechaSalida(LocalDateTime.now());
        }

        // Procesar detalles
        String[] codArticulos = request.getParameterValues("codArticulo[]");
        String[] precios = request.getParameterValues("precioVenta[]");
        String[] cantidades = request.getParameterValues("cantidadEnviada[]");

        List<GuiaDetalle> detalles = new ArrayList<>();
        if (codArticulos != null) {
            for (int i = 0; i < codArticulos.length; i++) {
                if (codArticulos[i] != null && !codArticulos[i].isEmpty()) {
                    GuiaDetalle d = new GuiaDetalle();
                    d.setNumGuia(guia.getNumGuia());
                    d.setCodArticulo(Integer.parseInt(codArticulos[i]));
                    d.setPrecioVenta(Double.parseDouble(precios[i]));
                    d.setCantidadEnviada(Short.parseShort(cantidades[i]));
                    detalles.add(d);
                }
            }
        }

        if (detalles.isEmpty()) {
            request.setAttribute("mensajeError", "Debe agregar al menos un artículo a la guía.");
            cargarDatosFormulario(request);
            request.setAttribute("guia", guia);
            request.getRequestDispatcher("guias/formulario.jsp").forward(request, response);
            return;
        }

        boolean exito;
        if (esNuevo) {
            exito = guiaEnvioDAO.crear(guia);
            if (exito) {
                for (GuiaDetalle d : detalles) {
                    guiaDetalleDAO.crear(d);
                }
            }
        } else {
            exito = guiaEnvioDAO.actualizar(guia);
            if (exito) {
                guiaDetalleDAO.eliminarPorGuia(guia.getNumGuia());
                for (GuiaDetalle d : detalles) {
                    guiaDetalleDAO.crear(d);
                }
            }
        }

        if (!exito) {
            request.setAttribute("mensajeError", "Ocurrió un error al guardar la guía de envío.");
            cargarDatosFormulario(request);
            request.setAttribute("guia", guia);
            request.getRequestDispatcher("guias/formulario.jsp").forward(request, response);
            return;
        }
        response.sendRedirect(request.getContextPath() + "/guias");
    }

    private void eliminarGuia(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int numGuia = Integer.parseInt(request.getParameter("id"));
        guiaEnvioDAO.eliminar(numGuia);
        response.sendRedirect(request.getContextPath() + "/guias");
    }

    private void listarGuias(HttpServletRequest request) {
        request.setAttribute("listaGuias", guiaEnvioDAO.listarTodos());
    }

    private void cargarDatosFormulario(HttpServletRequest request) {
        request.setAttribute("listaTiendas", tiendaDAO.listarTodos());
        request.setAttribute("listaTransportistas", transportistaDAO.listarTodos());
        request.setAttribute("listaArticulos", articuloDAO.listarTodos());
    }
}