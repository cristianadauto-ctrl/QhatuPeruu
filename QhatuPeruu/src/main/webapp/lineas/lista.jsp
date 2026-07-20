<%-- 
    Document   : lista
    Created on : 18 jul. 2026, 08:52:36
    Author     : USER
--%>

<%@ page import="com.mycompany.qhatuperuu.modelo.Linea" %>
<%@ page import="java.util.List" %>
<%@ include file="../includes/header.jsp" %>

<div class="d-flex justify-content-between align-items-center mb-3">
    <h2><i class="bi bi-tags"></i> Líneas de Producto</h2>
    <a href="${pageContext.request.contextPath}/lineas?accion=nuevo" class="btn btn-primary">
        <i class="bi bi-plus-circle"></i> Nueva Línea
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
                    <th>Nombre de la Línea</th>
                    <th>Descripción</th>
                    <th class="text-center">Acciones</th>
                </tr>
            </thead>
            <tbody>
                <%
                    List<Linea> listaLineas = (List<Linea>) request.getAttribute("listaLineas");
                    if (listaLineas != null && !listaLineas.isEmpty()) {
                        for (Linea l : listaLineas) {
                %>
                            <tr>
                                <td><%= l.getNomLinea() %></td>
                                <td><%= l.getDescripcion() != null ? l.getDescripcion() : "-" %></td>
                                <td class="text-center">
                                    <a href="${pageContext.request.contextPath}/lineas?accion=editar&id=<%= l.getCodLinea() %>" class="btn btn-sm btn-outline-warning">
                                        <i class="bi bi-pencil"></i>
                                    </a>
                                    <button type="button" class="btn btn-sm btn-outline-danger" onclick="confirmarEliminacion(<%= l.getCodLinea() %>, '<%= l.getNomLinea() %>')">
                                        <i class="bi bi-trash"></i>
                                    </button>
                                </td>
                            </tr>
                <%
                        }
                    } else {
                %>
                        <tr>
                            <td colspan="3" class="text-center text-muted py-3">No hay líneas registradas.</td>
                        </tr>
                <%  } %>
            </tbody>
        </table>
    </div>
</div>

<form id="formEliminar" action="${pageContext.request.contextPath}/lineas" method="post" style="display:none;">
    <input type="hidden" name="accion" value="eliminar">
    <input type="hidden" name="id" id="idEliminar">
</form>

<script>
    function confirmarEliminacion(id, nombre) {
        if (confirm("¿Está seguro de eliminar la línea \"" + nombre + "\"?")) {
            document.getElementById('idEliminar').value = id;
            document.getElementById('formEliminar').submit();
        }
    }
</script>

<%@ include file="../includes/footer.jsp" %>