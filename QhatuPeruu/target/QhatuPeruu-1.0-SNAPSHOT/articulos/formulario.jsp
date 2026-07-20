<%-- 
    Document   : formulario
    Created on : 18 jul. 2026, 09:58:13
    Author     : USER
--%>

<%@ page import="com.mycompany.qhatuperuu.modelo.Articulo" %>
<%@ page import="com.mycompany.qhatuperuu.modelo.Linea" %>
<%@ page import="com.mycompany.qhatuperuu.modelo.Proveedor" %>
<%@ page import="java.util.List" %>
<%@ include file="../includes/header.jsp" %>

<%
    Articulo articulo = (Articulo) request.getAttribute("articulo");
    boolean esEdicion = (articulo != null);

    List<Linea> listaLineas =
            (List<Linea>) request.getAttribute("listaLineas");

    List<Proveedor> listaProveedores =
            (List<Proveedor>) request.getAttribute("listaProveedores");

    String mensajeError =
            (String) request.getAttribute("mensajeError");
%>

<h2>
    <i class="bi bi-box-seam"></i>
    <%= esEdicion ? "Editar Artículo" : "Nuevo Artículo" %>
</h2>

<% if (mensajeError != null) { %>
<div class="alert alert-danger">
    <i class="bi bi-exclamation-triangle"></i>
    <%= mensajeError %>
</div>
<% } %>

<div class="card">
    <div class="card-body">

        <form action="${pageContext.request.contextPath}/articulos"
              method="post">

            <input type="hidden"
                   name="accion"
                   value="guardar">

            <% if(esEdicion){ %>

            <input type="hidden"
                   name="codArticulo"
                   value="<%= articulo.getCodArticulo() %>">

            <% } %>

            <div class="row g-3">

                <div class="col-md-6">

                    <label class="form-label">Línea</label>

                    <select class="form-select"
                            name="codLinea"
                            required>

                        <option value="">Seleccione...</option>

                        <%

                        if(listaLineas!=null){

                        for(Linea l : listaLineas){

                        boolean seleccionado =
                        esEdicion &&
                        articulo.getCodLinea()==l.getCodLinea();

                        %>

                        <option value="<%= l.getCodLinea()%>"
                                <%= seleccionado?"selected":"" %>>

                            <%= l.getNomLinea()%>

                        </option>

                        <% }} %>

                    </select>

                </div>

                <div class="col-md-6">

                    <label class="form-label">Proveedor</label>

                    <select class="form-select"
                            name="codProveedor"
                            required>

                        <option value="">Seleccione...</option>

                        <%

                        if(listaProveedores!=null){

                        for(Proveedor p : listaProveedores){

                        boolean seleccionado=
                        esEdicion &&
                        articulo.getCodProveedor()==p.getCodProveedor();

                        %>

                        <option value="<%= p.getCodProveedor()%>"
                                <%= seleccionado?"selected":"" %>>

                            <%= p.getNomProveedor()%>

                        </option>

                        <% }} %>

                    </select>

                </div>

                <div class="col-md-6">

                    <label class="form-label">
                        Descripción
                    </label>

                    <input
                        type="text"
                        class="form-control"
                        name="descripcionArticulo"
                        required
                        value="<%= esEdicion ? articulo.getDescripcionArticulo() : "" %>">

                </div>

                <div class="col-md-6">

                    <label class="form-label">
                        Presentación
                    </label>

                    <input
                        type="text"
                        class="form-control"
                        name="presentacion"
                        value="<%= esEdicion ? articulo.getPresentacion() : "" %>">

                </div>

                <div class="col-md-4">

                    <label class="form-label">
                        Precio Proveedor
                    </label>

                    <input
                        type="number"
                        step="0.01"
                        class="form-control"
                        name="precioProveedor"
                        value="<%= esEdicion ? articulo.getPrecioProveedor() : "" %>">

                </div>

                <div class="col-md-4">

                    <label class="form-label">
                        Stock Actual
                    </label>

                    <input
                        type="number"
                        class="form-control"
                        name="stockActual"
                        value="<%= esEdicion ? articulo.getStockActual() : "" %>">

                </div>

                <div class="col-md-4">

                    <label class="form-label">
                        Stock Mínimo
                    </label>

                    <input
                        type="number"
                        class="form-control"
                        name="stockMinimo"
                        value="<%= esEdicion ? articulo.getStockMinimo() : "" %>">

                </div>

                <div class="col-md-4">

                    <label class="form-label">
                        Estado
                    </label>

                    <select class="form-select"
                            name="descontinuado">

                        <option value="0"
                                <%= (!esEdicion || !articulo.isDescontinuado()) ? "selected":"" %>>

                            Activo

                        </option>

                        <option value="1"
                                <%= (esEdicion && articulo.isDescontinuado()) ? "selected":"" %>>

                            Descontinuado

                        </option>

                    </select>

                </div>

            </div>

            <div class="mt-4">

                <!-- CORREGIDO: btn-success en lugar de btn-primary -->
                <button class="btn btn-success">

                    <i class="bi bi-save"></i>

                    Guardar

                </button>

                <a href="${pageContext.request.contextPath}/articulos"
                   class="btn btn-secondary">

                    Cancelar

                </a>

            </div>

        </form>

    </div>
</div>

<%@ include file="../includes/footer.jsp" %>