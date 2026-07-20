<%-- 
    Document   : formulario
    Created on : 9 jul. 2026, 13:01:02
    Author     : Tec
--%>

<%@ page import="com.mycompany.qhatuperuu.modelo.Usuario" %>
<%@ page import="com.mycompany.qhatuperuu.modelo.Rol" %>
<%@ page import="java.util.List" %>
<%@ include file="../includes/header.jsp" %>

<%
    Usuario usuario = (Usuario) request.getAttribute("usuario");
    boolean esEdicion = (usuario != null);
    List<Rol> listaRoles = (List<Rol>) request.getAttribute("listaRoles");
    String mensajeError = (String) request.getAttribute("mensajeError");
%>

<h2><i class="bi bi-person-<%= esEdicion ? "gear" : "plus" %>"></i>
    <%= esEdicion ? "Editar Usuario" : "Nuevo Usuario" %>
</h2>

<% if (mensajeError != null) { %>
    <div class="alert alert-danger"><i class="bi bi-exclamation-triangle"></i> <%= mensajeError %></div>
<% } %>

<div class="card">
    <div class="card-body">
        <form action="${pageContext.request.contextPath}/usuarios" method="post">
            <input type="hidden" name="accion" value="guardar">
            <% if (esEdicion) { %>
                <input type="hidden" name="codUsuario" value="<%= usuario.getCodUsuario() %>">
            <% } %>

            <div class="row g-3">
                <div class="col-md-6">
                    <label class="form-label">Usuario (login)</label>
                    <input type="text" class="form-control" name="username" required
                           value="<%= esEdicion ? usuario.getUsername() : "" %>">
                </div>

                <div class="col-md-6">
                    <label class="form-label">
                        Contraseña <% if (esEdicion) { %><small class="text-muted">(dejar en blanco para no cambiarla)</small><% } %>
                    </label>
                    <input type="password" class="form-control" name="clave"
                           <%= esEdicion ? "" : "required" %>>
                </div>

                <div class="col-md-6">
                    <label class="form-label">Nombres</label>
                    <input type="text" class="form-control" name="nombres" required
                           value="<%= esEdicion ? usuario.getNombres() : "" %>">
                </div>

                <div class="col-md-6">
                    <label class="form-label">Apellidos</label>
                    <input type="text" class="form-control" name="apellidos"
                           value="<%= esEdicion && usuario.getApellidos() != null ? usuario.getApellidos() : "" %>">
                </div>

                <div class="col-md-6">
                    <label class="form-label">Correo</label>
                    <input type="email" class="form-control" name="correo"
                           value="<%= esEdicion && usuario.getCorreo() != null ? usuario.getCorreo() : "" %>">
                </div>

                <div class="col-md-3">
                    <label class="form-label">Rol</label>
                    <select class="form-select" name="codRol" required>
                        <option value="">Seleccione...</option>
                        <%
                            if (listaRoles != null) {
                                for (Rol r : listaRoles) {
                                    boolean seleccionado = esEdicion && usuario.getCodRol() == r.getCodRol();
                        %>
                        <option value="<%= r.getCodRol() %>" <%= seleccionado ? "selected" : "" %>>
                            <%= r.getNomRol() %>
                        </option>
                        <%
                                }
                            }
                        %>
                    </select>
                </div>

                <div class="col-md-3">
                    <label class="form-label">Estado</label>
                    <select class="form-select" name="estado">
                        <option value="1" <%= (esEdicion && usuario.isEstado()) || !esEdicion ? "selected" : "" %>>Activo</option>
                        <option value="0" <%= (esEdicion && !usuario.isEstado()) ? "selected" : "" %>>Inactivo</option>
                    </select>
                </div>
            </div>

            <div class="mt-4">
                <button type="submit" class="btn btn-primary">
                    <i class="bi bi-save"></i> Guardar
                </button>
                <a href="${pageContext.request.contextPath}/usuarios" class="btn btn-outline-secondary">
                    <i class="bi bi-x-circle"></i> Cancelar
                </a>
            </div>
        </form>
    </div>
</div>

<%@ include file="../includes/footer.jsp" %>