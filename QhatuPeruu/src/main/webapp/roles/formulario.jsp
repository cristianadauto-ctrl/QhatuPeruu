<%-- 
    Document   : formulario
    Created on : 9 jul. 2026, 13:02:32
    Author     : Tec
--%>

<%@ page import="com.mycompany.qhatuperuu.modelo.Rol" %>
<%@ include file="../includes/header.jsp" %>

<%
    Rol rol = (Rol) request.getAttribute("rol");
    boolean esEdicion = (rol != null);
    String mensajeError = (String) request.getAttribute("mensajeError");
%>

<h2><i class="bi bi-shield-<%= esEdicion ? "check" : "plus" %>"></i>
    <%= esEdicion ? "Editar Rol" : "Nuevo Rol" %>
</h2>

<% if (mensajeError != null) { %>
    <div class="alert alert-danger"><i class="bi bi-exclamation-triangle"></i> <%= mensajeError %></div>
<% } %>

<div class="card">
    <div class="card-body">
        <form action="${pageContext.request.contextPath}/roles" method="post">
            <input type="hidden" name="accion" value="guardar">
            <% if (esEdicion) { %>
                <input type="hidden" name="codRol" value="<%= rol.getCodRol() %>">
            <% } %>

            <div class="mb-3">
                <label class="form-label">Nombre del Rol</label>
                <input type="text" class="form-control" name="nomRol" required
                       value="<%= esEdicion ? rol.getNomRol() : "" %>">
            </div>

            <div class="mb-3">
                <label class="form-label">Descripción</label>
                <textarea class="form-control" name="descripcion" rows="3"><%= esEdicion && rol.getDescripcion() != null ? rol.getDescripcion() : "" %></textarea>
            </div>

            <div class="mt-4">
                <button type="submit" class="btn btn-primary">
                    <i class="bi bi-save"></i> Guardar
                </button>
                <a href="${pageContext.request.contextPath}/roles" class="btn btn-outline-secondary">
                    <i class="bi bi-x-circle"></i> Cancelar
                </a>
            </div>
        </form>
    </div>
</div>

<%@ include file="../includes/footer.jsp" %>
