<%-- 
    Document   : lista
    Created on : 18 jul. 2026, 09:20:56
    Author     : USER
--%>

<%@ page import="com.mycompany.qhatuperuu.modelo.Proveedor" %>
<%@ page import="java.util.List" %>
<%@ include file="../includes/header.jsp" %>

<div class="d-flex justify-content-between align-items-center mb-3">

    <h2><i class="bi bi-truck"></i> Proveedores</h2>

    <a href="${pageContext.request.contextPath}/proveedores?accion=nuevo"
       class="btn btn-primary">

        <i class="bi bi-plus-circle"></i>
        Nuevo Proveedor

    </a>

</div>

<%
    String mensajeError = (String) request.getAttribute("mensajeError");
%>

<% if (mensajeError != null) { %>

<div class="alert alert-danger">
    <i class="bi bi-exclamation-triangle"></i>
    <%= mensajeError %>
</div>

<% } %>

<div class="card">

    <div class="card-body">

        <table class="table table-hover align-middle">

            <thead>

                <tr>
                    <th>Proveedor</th>
                    <th>Representante</th>
                    <th>Ciudad</th>
                    <th>Teléfono</th>
                    <th class="text-center">Acciones</th>
                </tr>

            </thead>

            <tbody>

                <%
                    List<Proveedor> listaProveedores =
                            (List<Proveedor>) request.getAttribute("listaProveedores");

                    if (listaProveedores != null && !listaProveedores.isEmpty()) {

                        for (Proveedor p : listaProveedores) {
                %>

                <tr>

                    <td><%= p.getNomProveedor()%></td>

                    <td>
                        <%= p.getRepresentante() != null && !p.getRepresentante().isEmpty()
                                ? p.getRepresentante()
                                : "-"%>
                    </td>

                    <td>
                        <%= p.getCiudad() != null && !p.getCiudad().isEmpty()
                                ? p.getCiudad()
                                : "-"%>
                    </td>

                    <td>
                        <%= p.getTelefono() != null && !p.getTelefono().isEmpty()
                                ? p.getTelefono()
                                : "-"%>
                    </td>

                    <td class="text-center">

                        <a href="${pageContext.request.contextPath}/proveedores?accion=editar&id=<%= p.getCodProveedor()%>"
                           class="btn btn-sm btn-outline-warning">

                            <i class="bi bi-pencil"></i>

                        </a>

                        <button type="button"
                                class="btn btn-sm btn-outline-danger"
                                onclick="confirmarEliminacion(<%= p.getCodProveedor()%>, '<%= p.getNomProveedor()%>')">

                            <i class="bi bi-trash"></i>

                        </button>

                    </td>

                </tr>

                <%
                        }

                    } else {
                %>

                <tr>

                    <td colspan="5"
                        class="text-center text-muted py-3">

                        No hay proveedores registrados.

                    </td>

                </tr>

                <% }%>

            </tbody>

        </table>

    </div>

</div>

<form id="formEliminar"
      action="${pageContext.request.contextPath}/proveedores"
      method="post"
      style="display:none;">

    <input type="hidden" name="accion" value="eliminar">
    <input type="hidden" name="id" id="idEliminar">

</form>

<script>
    function confirmarEliminacion(id, nombre) {

        if (confirm("¿Está seguro de eliminar al proveedor \"" + nombre + "\"? Esta acción no se puede deshacer.")) {

            document.getElementById("idEliminar").value = id;
            document.getElementById("formEliminar").submit();

        }

    }
</script>

<%@ include file="../includes/footer.jsp" %>