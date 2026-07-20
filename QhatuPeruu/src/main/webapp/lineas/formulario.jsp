<%-- 
    Document   : formulario
    Created on : 18 jul. 2026, 08:52:51
    Author     : USER
--%>

<%@ page import="com.mycompany.qhatuperuu.modelo.Linea" %>
<%@ include file="../includes/header.jsp" %>

<%
    Linea linea = (Linea) request.getAttribute("linea");
    boolean esEdicion = (linea != null);
    String mensajeError = (String) request.getAttribute("mensajeError");
%>

<h2><i class="bi bi-tag<%= esEdicion ? "-fill" : "" %>"></i>
    <%= esEdicion ? "Editar Línea de Producto" : "Nueva Línea de Producto" %>
</h2>

<% if (mensajeError != null) { %>
    <div class="alert alert-danger"><i class="bi bi-exclamation-triangle"></i> <%= mensajeError %></div>
<% } %>

<div class="card">
    <div class="card-body">
        <form action="${pageContext.request.contextPath}/lineas" method="post">
            <input type="hidden" name="accion" value="guardar">
            <% if (esEdicion) { %>
                <input type="hidden" name="codLinea" value="<%= linea.getCodLinea() %>">
            <% } %>

            <div class="mb-3">
                <label class="form-label">Nombre de la Línea</label>
                <input type="text" class="form-control" name="nomLinea" required
                       value="<%= esEdicion ? linea.getNomLinea() : "" %>">
            </div>

            <div class="mb-3">
                <label class="form-label">Descripción</label>
                <textarea class="form-control" name="descripcion" rows="3"><%= esEdicion && linea.getDescripcion() != null ? linea.getDescripcion() : "" %></textarea>
            </div>

            <div class="mt-4">
                <button type="submit" class="btn btn-primary">
                    <i class="bi bi-save"></i> Guardar
                </button>
                <a href="${pageContext.request.contextPath}/lineas" class="btn btn-outline-secondary">
                    <i class="bi bi-x-circle"></i> Cancelar
                </a>
            </div>
        </form>
    </div>
</div>

<%@ include file="../includes/footer.jsp" %>