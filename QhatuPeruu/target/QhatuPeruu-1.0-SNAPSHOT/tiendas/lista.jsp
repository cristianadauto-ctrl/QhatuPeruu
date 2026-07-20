<%-- 
    Document   : lista
    Created on : 18 jul. 2026, 13:10:37
    Author     : USER
--%>

<%@ page import="com.mycompany.qhatuperuu.modelo.Tienda" %>
<%@ page import="java.util.List" %>
<%@ include file="../includes/header.jsp" %>

<div class="d-flex justify-content-between align-items-center mb-3">
    <h2><i class="bi bi-shop"></i> Tiendas</h2>
    <a href="${pageContext.request.contextPath}/tiendas?accion=nuevo" class="btn btn-primary">
        <i class="bi bi-plus-circle"></i> Nueva Tienda
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
                        <th>Código</th>
                        <th>Dirección</th>
                        <th>Distrito</th>
                        <th>Teléfono</th>
                        <th>Fax</th>
                        <th class="text-center">Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        List<Tienda> listaTiendas = (List<Tienda>) request.getAttribute("listaTiendas");
                        if (listaTiendas != null && !listaTiendas.isEmpty()) {
                            for (Tienda t : listaTiendas) {
                    %>
                    <tr>
                        <td><strong><%= t.getCodTienda() %></strong></td>
                        <td><%= t.getDireccion() != null ? t.getDireccion() : "-" %></td>
                        <td><%= t.getDistrito() != null ? t.getDistrito() : "-" %></td>
                        <td><%= t.getTelefono() != null ? t.getTelefono() : "-" %></td>
                        <td><%= t.getFax() != null ? t.getFax() : "-" %></td>
                        <td class="text-center">
                            <a href="${pageContext.request.contextPath}/tiendas?accion=editar&id=<%= t.getCodTienda() %>"
                               class="btn btn-sm btn-outline-warning">
                                <i class="bi bi-pencil"></i>
                            </a>
                            <button type="button" class="btn btn-sm btn-outline-danger"
                                    onclick="confirmarEliminacion(<%= t.getCodTienda() %>, '<%= t.getDireccion() != null ? t.getDireccion() : "Tienda " + t.getCodTienda() %>')">
                                <i class="bi bi-trash"></i>
                            </button>
                        </td>
                    </tr>
                    <%
                            }
                        } else {
                    %>
                    <tr>
                        <td colspan="6" class="text-center text-muted py-3">No hay tiendas registradas.</td>
                    </tr>
                    <% } %>
                </tbody>
            </table>
        </div>
    </div>
</div>

<form id="formEliminar" action="${pageContext.request.contextPath}/tiendas" method="post" style="display:none;">
    <input type="hidden" name="accion" value="eliminar">
    <input type="hidden" name="id" id="idEliminar">
</form>

<script>
    function confirmarEliminacion(id, nombre) {
        if (confirm("¿Está seguro de eliminar la tienda \"" + nombre + "\"?")) {
            document.getElementById('idEliminar').value = id;
            document.getElementById('formEliminar').submit();
        }
    }
</script>

<%@ include file="../includes/footer.jsp" %>