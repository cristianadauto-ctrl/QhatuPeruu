<%-- 
    Document   : lista
    Created on : 18 jul. 2026, 11:14:36
    Author     : USER
--%>

<%@ page import="com.mycompany.qhatuperuu.modelo.OrdenCompra" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ include file="../includes/header.jsp" %>

<%
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
%>

<div class="d-flex justify-content-between align-items-center mb-3">
    <h2><i class="bi bi-cart-plus"></i> Órdenes de Compra</h2>
    <a href="${pageContext.request.contextPath}/compras?accion=nuevo" class="btn btn-primary">
        <i class="bi bi-plus-circle"></i> Nueva Orden
    </a>
</div>

<%
    String mensajeError = (String) request.getAttribute("mensajeError");
%>
<% if (mensajeError != null) { %>
    <div class="alert alert-danger"><i class="bi bi-exclamation-triangle"></i> <%= mensajeError %></div>
<% } %>

<div class="card">
    <div class="card-body">
        <div class="table-responsive">
            <table class="table table-hover align-middle">
                <thead>
                    <tr>
                        <th>N° Orden</th>
                        <th>Fecha Orden</th>
                        <th>Fecha Ingreso</th>
                        <th>Detalles</th>
                        <th class="text-center">Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        List<OrdenCompra> listaCompras = (List<OrdenCompra>) request.getAttribute("listaCompras");
                        if (listaCompras != null && !listaCompras.isEmpty()) {
                            for (OrdenCompra o : listaCompras) {
                    %>
                    <tr>
                        <td><strong>#<%= o.getNumOrden() %></strong></td>
                        <td><%= o.getFechaOrden().format(formatter) %></td>
                        <td>
                            <%= o.getFechaIngreso() != null ? o.getFechaIngreso().format(formatter) : "-" %>
                        </td>
                        <td><%= o.getTotalDetalles() %> artículo(s)</td>
                        <td class="text-center">
                            <a href="${pageContext.request.contextPath}/compras?accion=ver&id=<%= o.getNumOrden() %>"
   class="btn btn-sm btn-outline-info">
    <i class="bi bi-eye"></i>
</a>

<a href="${pageContext.request.contextPath}/compras?accion=editar&id=<%= o.getNumOrden() %>"
   class="btn btn-sm btn-outline-warning">
    <i class="bi bi-pencil"></i>
</a>

<button type="button" class="btn btn-sm btn-outline-danger"
        onclick="confirmarEliminacion(<%= o.getNumOrden() %>, 'Orden #<%= o.getNumOrden() %>')">
    <i class="bi bi-trash"></i>
</button>
                        </td>
                    </tr>
                    <%
                            }
                        } else {
                    %>
                    <tr>
                        <td colspan="5" class="text-center text-muted py-3">No hay órdenes de compra registradas.</td>
                    </tr>
                    <% } %>
                </tbody>
            </table>
        </div>
    </div>
</div>

<form id="formEliminar" action="${pageContext.request.contextPath}/compras" method="post" style="display:none;">
    <input type="hidden" name="accion" value="eliminar">
    <input type="hidden" name="id" id="idEliminar">
</form>

<script>
    function confirmarEliminacion(id, nombre) {
        if (confirm("¿Está seguro de eliminar la " + nombre + "?")) {
            document.getElementById('idEliminar').value = id;
            document.getElementById('formEliminar').submit();
        }
    }
</script>

<%@ include file="../includes/footer.jsp" %>