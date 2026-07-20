<%-- 
    Document   : formulario
    Created on : 18 jul. 2026, 10:37:50
    Author     : USER
--%>

<%@ page import="com.mycompany.qhatuperuu.modelo.Transportista" %>
<%@ include file="../includes/header.jsp" %>

<%
    Transportista transportista = (Transportista) request.getAttribute("transportista");
    boolean esEdicion = (transportista != null);
    String mensajeError = (String) request.getAttribute("mensajeError");
%>

<h2><i class="bi bi-<%= esEdicion ? "pencil-square" : "plus-circle" %>"></i> <%= esEdicion ? "Editar Transportista" : "Nuevo Transportista" %></h2>

<% if (mensajeError != null) { %>
    <div class="alert alert-danger"><i class="bi bi-exclamation-triangle"></i> <%= mensajeError %></div>
<% } %>

<div class="card">
    <div class="card-body">
        <form action="${pageContext.request.contextPath}/transportistas" method="post">
            <input type="hidden" name="accion" value="guardar">
            <% if (esEdicion) { %>
                <input type="hidden" name="codTransportista" value="<%= transportista.getCodTransportista() %>">
            <% } %>

            <div class="row g-3">
                <div class="col-md-6">
                    <label class="form-label">Nombre del Transportista</label>
                    <input type="text" class="form-control" name="nomTransportista" required
                           value="<%= esEdicion ? transportista.getNomTransportista() : "" %>">
                </div>

                <div class="col-md-6">
                    <label class="form-label">Teléfono</label>
                    <input type="text" class="form-control" name="telefono"
                           value="<%= esEdicion && transportista.getTelefono() != null ? transportista.getTelefono() : "" %>">
                </div>

                <div class="col-md-12">
                    <label class="form-label">Dirección</label>
                    <input type="text" class="form-control" name="direccion"
                           value="<%= esEdicion && transportista.getDireccion() != null ? transportista.getDireccion() : "" %>">
                </div>

                <div class="col-12 text-center mt-4">
                    <button type="submit" class="btn btn-success">
                        <i class="bi bi-check-circle"></i> Guardar
                    </button>
                    <a href="${pageContext.request.contextPath}/transportistas" class="btn btn-secondary">
                        <i class="bi bi-x-circle"></i> Cancelar
                    </a>
                </div>
            </div>
        </form>
    </div>
</div>

<%@ include file="../includes/footer.jsp" %>