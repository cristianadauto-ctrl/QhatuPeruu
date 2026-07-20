<%-- 
    Document   : formulario
    Created on : 18 jul. 2026, 09:21:03
    Author     : USER
--%>

<%@ page import="com.mycompany.qhatuperuu.modelo.Proveedor" %>
<%@ include file="../includes/header.jsp" %>

<%
    Proveedor proveedor = (Proveedor) request.getAttribute("proveedor");
    boolean esEdicion = (proveedor != null);
    String mensajeError = (String) request.getAttribute("mensajeError");
%>

<h2><i class="bi bi-truck<%= esEdicion ? "" : "" %>"></i>
    <%= esEdicion ? "Editar Proveedor" : "Nuevo Proveedor" %>
</h2>

<% if (mensajeError != null) { %>
<div class="alert alert-danger">
    <i class="bi bi-exclamation-triangle"></i>
    <%= mensajeError %>
</div>
<% } %>

<div class="card">
    <div class="card-body">

        <form action="${pageContext.request.contextPath}/proveedores" method="post">

            <input type="hidden" name="accion" value="guardar">

            <% if (esEdicion) { %>
            <input type="hidden" name="codProveedor" value="<%= proveedor.getCodProveedor()%>">
            <% } %>

            <div class="row g-3">

                <div class="col-md-6">
                    <label class="form-label">Nombre del proveedor</label>
                    <input type="text"
                           class="form-control"
                           name="nomProveedor"
                           required
                           maxlength="40"
                           value="<%= esEdicion ? proveedor.getNomProveedor() : ""%>">
                </div>

                <div class="col-md-6">
                    <label class="form-label">Representante</label>
                    <input type="text"
                           class="form-control"
                           name="representante"
                           maxlength="30"
                           value="<%= esEdicion && proveedor.getRepresentante()!=null ? proveedor.getRepresentante() : ""%>">
                </div>

                <div class="col-md-6">
                    <label class="form-label">Dirección</label>
                    <input type="text"
                           class="form-control"
                           name="direccion"
                           maxlength="60"
                           value="<%= esEdicion && proveedor.getDireccion()!=null ? proveedor.getDireccion() : ""%>">
                </div>

                <div class="col-md-3">
                    <label class="form-label">Ciudad</label>
                    <input type="text"
                           class="form-control"
                           name="ciudad"
                           maxlength="15"
                           value="<%= esEdicion && proveedor.getCiudad()!=null ? proveedor.getCiudad() : ""%>">
                </div>

                <div class="col-md-3">
                    <label class="form-label">Departamento</label>
                    <input type="text"
                           class="form-control"
                           name="departamento"
                           maxlength="15"
                           value="<%= esEdicion && proveedor.getDepartamento()!=null ? proveedor.getDepartamento() : ""%>">
                </div>

                <div class="col-md-3">
                    <label class="form-label">Código Postal</label>
                    <input type="text"
                           class="form-control"
                           name="codigoPostal"
                           maxlength="15"
                           value="<%= esEdicion && proveedor.getCodigoPostal()!=null ? proveedor.getCodigoPostal() : ""%>">
                </div>

                <div class="col-md-3">
                    <label class="form-label">Teléfono</label>
                    <input type="text"
                           class="form-control"
                           name="telefono"
                           maxlength="15"
                           value="<%= esEdicion && proveedor.getTelefono()!=null ? proveedor.getTelefono() : ""%>">
                </div>

                <div class="col-md-3">
                    <label class="form-label">Fax</label>
                    <input type="text"
                           class="form-control"
                           name="fax"
                           maxlength="15"
                           value="<%= esEdicion && proveedor.getFax()!=null ? proveedor.getFax() : ""%>">
                </div>

            </div>

            <div class="mt-4">

                <button type="submit" class="btn btn-primary">
                    <i class="bi bi-save"></i> Guardar
                </button>

                <a href="${pageContext.request.contextPath}/proveedores"
                   class="btn btn-outline-secondary">
                    <i class="bi bi-x-circle"></i> Cancelar
                </a>

            </div>

        </form>

    </div>
</div>

<%@ include file="../includes/footer.jsp" %>