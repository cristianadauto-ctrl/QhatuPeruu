<%-- 
    Document   : ver
    Created on : 18 jul. 2026, 11:42:39
    Author     : USER
--%>

<%@ page import="com.mycompany.qhatuperuu.modelo.OrdenCompra" %>
<%@ page import="com.mycompany.qhatuperuu.modelo.OrdenDetalle" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ include file="../includes/header.jsp" %>

<%
    OrdenCompra orden = (OrdenCompra) request.getAttribute("orden");
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
%>

<h2><i class="bi bi-eye"></i> Detalle de Orden de Compra #<%= orden.getNumOrden() %></h2>

<div class="card">
    <div class="card-body">
        <div class="row">
            <div class="col-md-6">
                <p><strong>N° Orden:</strong> <%= orden.getNumOrden() %></p>
                <p><strong>Fecha Orden:</strong> <%= orden.getFechaOrden().format(formatter) %></p>
            </div>
            <div class="col-md-6">
                <p><strong>Fecha Ingreso:</strong> 
                    <%= orden.getFechaIngreso() != null ? orden.getFechaIngreso().format(formatter) : "Pendiente" %>
                </p>
            </div>
        </div>
        
        <hr>
        <h5><i class="bi bi-list-ul"></i> Artículos</h5>
        
        <table class="table table-hover">
            <thead>
                <tr>
                    <th>Artículo</th>
                    <th>Presentación</th>
                    <th>Precio</th>
                    <th>Cantidad</th>
                    <th>Estado</th>
                </tr>
            </thead>
            <tbody>
                <%
                    if (orden.getDetalles() != null && !orden.getDetalles().isEmpty()) {
                        for (OrdenDetalle d : orden.getDetalles()) {
                %>
                <tr>
                    <td><%= d.getDescripcionArticulo() %></td>
                    <td><%= d.getPresentacion() != null ? d.getPresentacion() : "-" %></td>
                    <td>S/. <%= d.getPrecioCompra() %></td>
                    <td><%= d.getCantidadSolicitada() %></td>
                    <td>
                        <span class="badge <%= "Completo".equals(d.getEstado()) ? "bg-success" : "bg-warning" %>">
                            <%= d.getEstado() %>
                        </span>
                    </td>
                </tr>
                <%
                        }
                    } else {
                %>
                <tr>
                    <td colspan="5" class="text-center">No hay detalles para esta orden.</td>
                </tr>
                <% } %>
            </tbody>
        </table>
        
        <div class="mt-3">
            <a href="${pageContext.request.contextPath}/compras" class="btn btn-secondary">
                <i class="bi bi-arrow-left"></i> Volver
            </a>
        </div>
    </div>
</div>

<%@ include file="../includes/footer.jsp" %>