<%-- 
    Document   : articulos
    Created on : 18 jul. 2026, 12:46:40
    Author     : USER
--%>

<%@ page import="com.mycompany.qhatuperuu.modelo.Articulo" %>
<%@ page import="java.util.List" %>
<%@ include file="../includes/header.jsp" %>

<div class="d-flex justify-content-between align-items-center mb-3">
    <h2><i class="bi bi-box-seam"></i> Reporte de Artículos</h2>
    <div>
        <button onclick="window.print()" class="btn btn-outline-primary">
            <i class="bi bi-printer"></i> Imprimir
        </button>
        <a href="${pageContext.request.contextPath}/reportes" class="btn btn-secondary">
            <i class="bi bi-arrow-left"></i> Volver
        </a>
    </div>
</div>

<div class="card">
    <div class="card-body">
        <div class="table-responsive">
            <table class="table table-hover align-middle">
                <thead>
                    <tr>
                        <th>#</th>
                        <th>Línea</th>
                        <th>Proveedor</th>
                        <th>Descripción</th>
                        <th>Presentación</th>
                        <th>Precio</th>
                        <th>Stock</th>
                        <th>Estado</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        List<Articulo> lista = (List<Articulo>) request.getAttribute("listaArticulos");
                        int contador = 1;
                        if (lista != null && !lista.isEmpty()) {
                            for (Articulo a : lista) {
                    %>
                    <tr>
                        <td><%= contador++ %></td>
                        <td><%= a.getNomLinea() %></td>
                        <td><%= a.getNomProveedor() %></td>
                        <td><%= a.getDescripcionArticulo() %></td>
                        <td><%= a.getPresentacion() != null ? a.getPresentacion() : "-" %></td>
                        <td>S/. <%= a.getPrecioProveedor() %></td>
                        <td>
                            <%= a.getStockActual() %>
                            <% if (a.getStockActual() <= a.getStockMinimo()) { %>
                                <span class="badge bg-danger ms-1">Stock Bajo</span>
                            <% } %>
                        </td>
                        <td>
                            <% if (a.isDescontinuado()) { %>
                                <span class="badge bg-danger">Descontinuado</span>
                            <% } else { %>
                                <span class="badge bg-success">Activo</span>
                            <% } %>
                        </td>
                    </tr>
                    <%
                            }
                        } else {
                    %>
                    <tr>
                        <td colspan="8" class="text-center text-muted">No hay artículos registrados.</td>
                    </tr>
                    <% } %>
                </tbody>
                <tfoot>
                    <tr>
                        <td colspan="8" class="text-end">
                            <strong>Total: <%= lista != null ? lista.size() : 0 %> artículos</strong>
                        </td>
                    </tr>
                </tfoot>
            </table>
        </div>
    </div>
</div>

<%@ include file="../includes/footer.jsp" %>