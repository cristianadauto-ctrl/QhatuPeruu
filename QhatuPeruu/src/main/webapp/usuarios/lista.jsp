<%-- 
    Document   : lista
    Created on : 9 jul. 2026, 13:00:48
    Author     : Tec
--%>

<%@ page import="com.mycompany.qhatuperuu.modelo.Usuario" %>
<%@ page import="java.util.List" %>
<%@ include file="../includes/header.jsp" %>

<div class="d-flex justify-content-between align-items-center mb-3">
    <h2><i class="bi bi-people"></i> Usuarios</h2>
    <a href="${pageContext.request.contextPath}/usuarios?accion=nuevo" class="btn btn-primary">
        <i class="bi bi-plus-circle"></i> Nuevo Usuario
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
                    <th>Nombre completo</th>
                    <th>Usuario</th>
                    <th>Correo</th>
                    <th>Rol</th>
                    <th>Estado</th>
                    <th class="text-center">Acciones</th>
                </tr>
            </thead>
            <tbody>
                <%
                    List<Usuario> listaUsuarios = (List<Usuario>) request.getAttribute("listaUsuarios");
                    if (listaUsuarios != null && !listaUsuarios.isEmpty()) {
                        for (Usuario u : listaUsuarios) {
                %>
                <tr>
                    <td><%= u.getNombreCompleto() %></td>
                    <td><%= u.getUsername() %></td>
                    <td><%= u.getCorreo() != null ? u.getCorreo() : "-" %></td>
                    <td><%= u.getNomRol() %></td>
                    <td>
                        <% if (u.isEstado()) { %>
                            <span class="badge badge-activo">Activo</span>
                        <% } else { %>
                            <span class="badge badge-inactivo">Inactivo</span>
                        <% } %>
                    </td>
                    <td class="text-center">
                        <a href="${pageContext.request.contextPath}/usuarios?accion=editar&id=<%= u.getCodUsuario() %>"
                           class="btn btn-sm btn-outline-warning">
                            <i class="bi bi-pencil"></i>
                        </a>
                        <button type="button" class="btn btn-sm btn-outline-danger"
                                onclick="confirmarEliminacion(<%= u.getCodUsuario() %>, '<%= u.getNombreCompleto() %>')">
                            <i class="bi bi-trash"></i>
                        </button>
                    </td>
                </tr>
                <%
                        }
                    } else {
                %>
                <tr>
                    <td colspan="6" class="text-center text-muted py-3">No hay usuarios registrados.</td>
                </tr>
                <% } %>
            </tbody>
        </table>
    </div>
</div>


<form id="formEliminar" action="${pageContext.request.contextPath}/usuarios" method="post" style="display:none;">
    <input type="hidden" name="accion" value="eliminar">
    <input type="hidden" name="id" id="idEliminar">
</form>

<script>
    function confirmarEliminacion(id, nombre) {
        if (confirm("¿Está seguro de eliminar al usuario \"" + nombre + "\"? Esta acción no se puede deshacer.")) {
            document.getElementById('idEliminar').value = id;
            document.getElementById('formEliminar').submit();
        }
    }
</script>

<%@ include file="../includes/footer.jsp" %>