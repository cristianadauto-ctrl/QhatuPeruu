<%-- 
    Document   : lista
    Created on : 9 jul. 2026, 13:02:22
    Author     : Tec
--%>

<%@ page import="com.mycompany.qhatuperuu.modelo.Rol" %>
<%@ page import="java.util.List" %>
<%@ include file="../includes/header.jsp" %>

<div class="d-flex justify-content-between align-items-center mb-3">
    <h2><i class="bi bi-shield-lock"></i> Roles</h2>
    <a href="${pageContext.request.contextPath}/roles?accion=nuevo" class="btn btn-primary">
        <i class="bi bi-plus-circle"></i> Nuevo Rol
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
                    <th>Nombre del Rol</th>
                    <th>Descripción</th>
                    <th class="text-center">Acciones</th>
                </tr>
            </thead>
            <tbody>
                <%
                    List<Rol> listaRoles = (List<Rol>) request.getAttribute("listaRoles");
                    if (listaRoles != null && !listaRoles.isEmpty()) {
                        for (Rol r : listaRoles) {
                %>
                <tr>
                    <td><%= r.getNomRol() %></td>
                    <td><%= r.getDescripcion() != null ? r.getDescripcion() : "-" %></td>
                    <td class="text-center">
                        <a href="${pageContext.request.contextPath}/roles?accion=editar&id=<%= r.getCodRol() %>"
                           class="btn btn-sm btn-outline-warning">
                            <i class="bi bi-pencil"></i>
                        </a>
                        <button type="button" class="btn btn-sm btn-outline-danger"
                                onclick="confirmarEliminacion(<%= r.getCodRol() %>, '<%= r.getNomRol() %>')">
                            <i class="bi bi-trash"></i>
                        </button>
                    </td>
                </tr>
                <%
                        }
                    } else {
                %>
                <tr>
                    <td colspan="3" class="text-center text-muted py-3">No hay roles registrados.</td>
                </tr>
                <% } %>
            </tbody>
        </table>
    </div>
</div>

<form id="formEliminar" action="${pageContext.request.contextPath}/roles" method="post" style="display:none;">
    <input type="hidden" name="accion" value="eliminar">
    <input type="hidden" name="id" id="idEliminar">
</form>

<script>
    function confirmarEliminacion(id, nombre) {
        if (confirm("¿Está seguro de eliminar el rol \"" + nombre + "\"?")) {
            document.getElementById('idEliminar').value = id;
            document.getElementById('formEliminar').submit();
        }
    }
</script>

<%@ include file="../includes/footer.jsp" %>
