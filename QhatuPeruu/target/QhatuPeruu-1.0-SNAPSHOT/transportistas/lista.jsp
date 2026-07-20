<%-- 
    Document   : lista
    Created on : 18 jul. 2026, 10:37:36
    Author     : USER
--%>

<%@ page import="com.mycompany.qhatuperuu.modelo.Transportista" %>
<%@ page import="java.util.List" %>
<%@ include file="../includes/header.jsp" %>

<div class="d-flex justify-content-between align-items-center mb-3">
    <h2><i class="bi bi-truck-front"></i> Transportistas</h2>
    <a href="${pageContext.request.contextPath}/transportistas?accion=nuevo" class="btn btn-primary">
        <i class="bi bi-plus-circle"></i> Nuevo Transportista
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
        <table class="table table-hover align-middle">
            <thead>
                <tr>
                    <th>Código</th>
                    <th>Transportista</th>
                    <th>Dirección</th>
                    <th>Teléfono</th>
                    <th class="text-center">Acciones</th>
                </tr>
            </thead>
            <tbody>
                <%
                    List<Transportista> listaTransportistas = (List<Transportista>) request.getAttribute("listaTransportistas");
                    if (listaTransportistas != null && !listaTransportistas.isEmpty()) {
                        for (Transportista t : listaTransportistas) {
                %>
                <tr>
                    <td><%= t.getCodTransportista() %></td>
                    <td><%= t.getNomTransportista() %></td>
                    <td><%= t.getDireccion() != null ? t.getDireccion() : "-" %></td>
                    <td><%= t.getTelefono() != null ? t.getTelefono() : "-" %></td>
                    <td class="text-center">
                        <a href="${pageContext.request.contextPath}/transportistas?accion=editar&id=<%= t.getCodTransportista() %>"
                           class="btn btn-sm btn-outline-warning">
                            <i class="bi bi-pencil"></i>
                        </a>
                        <button type="button" class="btn btn-sm btn-outline-danger"
                                onclick="confirmarEliminacion(<%= t.getCodTransportista() %>, '<%= t.getNomTransportista() %>')">
                            <i class="bi bi-trash"></i>
                        </button>
                    </td>
                </tr>
                <%
                        }
                    } else {
                %>
                <tr>
                    <td colspan="5" class="text-center text-muted py-3">No hay transportistas registrados.</td>
                </tr>
                <% } %>
            </tbody>
        </table>
    </div>
</div>

<form id="formEliminar" action="${pageContext.request.contextPath}/transportistas" method="post" style="display:none;">
    <input type="hidden" name="accion" value="eliminar">
    <input type="hidden" name="id" id="idEliminar">
</form>

<script>
    function confirmarEliminacion(id, nombre) {
        if (confirm("¿Está seguro de eliminar el transportista \"" + nombre + "\"?")) {
            document.getElementById('idEliminar').value = id;
            document.getElementById('formEliminar').submit();
        }
    }
</script>

<%@ include file="../includes/footer.jsp" %>