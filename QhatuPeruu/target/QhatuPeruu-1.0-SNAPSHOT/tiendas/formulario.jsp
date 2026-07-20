<%-- 
    Document   : formulario
    Created on : 18 jul. 2026, 13:10:58
    Author     : USER
--%>

<%@ page import="com.mycompany.qhatuperuu.modelo.Tienda" %>
<%@ include file="../includes/header.jsp" %>

<%
    Tienda tienda = (Tienda) request.getAttribute("tienda");
    boolean esEdicion = (tienda != null);
    String mensajeError = (String) request.getAttribute("mensajeError");
%>

<h2><i class="bi bi-<%= esEdicion ? "pencil-square" : "plus-circle" %>"></i> <%= esEdicion ? "Editar Tienda" : "Nueva Tienda" %></h2>

<% if (mensajeError != null) { %>
    <div class="alert alert-danger"><i class="bi bi-exclamation-triangle"></i> <%= mensajeError %></div>
<% } %>

<div class="card">
    <div class="card-body">
        <form action="${pageContext.request.contextPath}/tiendas" method="post">
            <input type="hidden" name="accion" value="guardar">
            <% if (esEdicion) { %>
                <input type="hidden" name="codTienda" value="<%= tienda.getCodTienda() %>">
            <% } %>

            <div class="row g-3">
                <% if (esEdicion) { %>
                <div class="col-md-3">
                    <label class="form-label">Código</label>
                    <input type="text" class="form-control" value="<%= tienda.getCodTienda() %>" disabled>
                </div>
                <% } %>

                <div class="col-md-6">
                    <label class="form-label">Dirección</label>
                    <input type="text" class="form-control" name="direccion"
                           value="<%= esEdicion && tienda.getDireccion() != null ? tienda.getDireccion() : "" %>">
                </div>

                <div class="col-md-6">
                    <label class="form-label">Distrito</label>
                    <input type="text" class="form-control" name="distrito"
                           value="<%= esEdicion && tienda.getDistrito() != null ? tienda.getDistrito() : "" %>">
                </div>

                <div class="col-md-6">
                    <label class="form-label">Teléfono</label>
                    <input type="text" class="form-control" name="telefono"
                           value="<%= esEdicion && tienda.getTelefono() != null ? tienda.getTelefono() : "" %>">
                </div>

                <div class="col-md-6">
                    <label class="form-label">Fax</label>
                    <input type="text" class="form-control" name="fax"
                           value="<%= esEdicion && tienda.getFax() != null ? tienda.getFax() : "" %>">
                </div>

                <div class="col-12 text-center mt-4">
                    <button type="submit" class="btn btn-success">
                        <i class="bi bi-check-circle"></i> Guardar
                    </button>
                    <a href="${pageContext.request.contextPath}/tiendas" class="btn btn-secondary">
                        <i class="bi bi-x-circle"></i> Cancelar
                    </a>
                </div>
            </div>
        </form>
    </div>
</div>

<%@ include file="../includes/footer.jsp" %>