<%-- 
    Document   : compras
    Created on : 18 jul. 2026, 12:46:58
    Author     : USER
--%>

<%@ page import="com.mycompany.qhatuperuu.modelo.OrdenCompra" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.sql.Timestamp" %>
<%@ include file="../includes/header.jsp" %>

<%
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
%>

<div class="d-flex justify-content-between align-items-center mb-3">
    <h2><i class="bi bi-cart-plus"></i> Reporte de Compras</h2>
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
                        <th>N° Orden</th>
                        <th>Fecha Orden</th>
                        <th>Fecha Ingreso</th>
                        <th>Detalles</th>
                        <th>Estado</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        List<OrdenCompra> lista = (List<OrdenCompra>) request.getAttribute("listaCompras");
                        int contador = 1;
                        if (lista != null && !lista.isEmpty()) {
                            for (OrdenCompra o : lista) {
                    %>
                    <tr>
                        <td><%= contador++ %></td>
                        <td><strong>#<%= o.getNumOrden() %></strong></td>
                        <td><%= sdf.format(Timestamp.valueOf(o.getFechaOrden())) %></td>
                        <td>
                            <%= o.getFechaIngreso() != null ? sdf.format(Timestamp.valueOf(o.getFechaIngreso())) : "-" %>
                        </td>
                        <td><%= o.getTotalDetalles() %></td>
                        <td>
                            <% if (o.getFechaIngreso() != null) { %>
                                <span class="badge bg-success">Completada</span>
                            <% } else { %>
                                <span class="badge bg-warning">Pendiente</span>
                            <% } %>
                        </td>
                    </tr>
                    <%
                            }
                        } else {
                    %>
                    <tr>
                        <td colspan="6" class="text-center text-muted">No hay órdenes de compra.</td>
                    </tr>
                    <% } %>
                </tbody>
                <tfoot>
                    <tr>
                        <td colspan="6" class="text-end">
                            <strong>Total: <%= lista != null ? lista.size() : 0 %> órdenes</strong>
                        </td>
                    </tr>
                </tfoot>
            </table>
        </div>
    </div>
</div>

<%@ include file="../includes/footer.jsp" %>