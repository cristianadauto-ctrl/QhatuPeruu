<%-- 
    Document   : lista
    Created on : 18 jul. 2026, 12:12:42
    Author     : USER
--%>

<%@ page import="com.mycompany.qhatuperuu.modelo.GuiaEnvio" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ include file="../includes/header.jsp" %>

<%
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
%>

<div class="d-flex justify-content-between align-items-center mb-3">
    <h2><i class="bi bi-truck"></i> Guías de Envío</h2>
    <a href="${pageContext.request.contextPath}/guias?accion=nuevo" class="btn btn-primary">
        <i class="bi bi-plus-circle"></i> Nueva Guía
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
        <div class="table-responsive">
            <table class="table table-hover align-middle">
                <thead>
                    <tr>
                        <th>N° Guía</th>
                        <th>Tienda</th>
                        <th>Transportista</th>
                        <th>Fecha Salida</th>
                        <th>Artículos</th>
                        <th class="text-center">Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        List<GuiaEnvio> listaGuias = (List<GuiaEnvio>) request.getAttribute("listaGuias");
                        if (listaGuias != null && !listaGuias.isEmpty()) {
                            for (GuiaEnvio g : listaGuias) {
                    %>
                    <tr>
                        <td><strong>#<%= g.getNumGuia() %></strong></td>
                        <td><%= g.getNomTienda() %></td>
                        <td><%= g.getNomTransportista() %></td>
                        <td><%= g.getFechaSalida().format(formatter) %></td>
                        <td><%= g.getTotalDetalles() %> artículo(s)</td>
                        <td class="text-center">
                            <a href="${pageContext.request.contextPath}/guias?accion=ver&id=<%= g.getNumGuia() %>"
                               class="btn btn-sm btn-outline-info">
                                <i class="bi bi-eye"></i>
                            </a>
                            <a href="${pageContext.request.contextPath}/guias?accion=editar&id=<%= g.getNumGuia() %>"
                               class="btn btn-sm btn-outline-warning">
                                <i class="bi bi-pencil"></i>
                            </a>
                            <button type="button" class="btn btn-sm btn-outline-danger"
                                    onclick="confirmarEliminacion(<%= g.getNumGuia() %>, 'Guía #<%= g.getNumGuia() %>')">
                                <i class="bi bi-trash"></i>
                            </button>
                        </td>
                    </tr>
                    <%
                            }
                        } else {
                    %>
                    <tr>
                        <td colspan="6" class="text-center text-muted py-3">No hay guías de envío registradas.</td>
                    </tr>
                    <% } %>
                </tbody>
            </table>
        </div>
    </div>
</div>

<form id="formEliminar" action="${pageContext.request.contextPath}/guias" method="post" style="display:none;">
    <input type="hidden" name="accion" value="eliminar">
    <input type="hidden" name="id" id="idEliminar">
</form>

<script>
    function confirmarEliminacion(id, nombre) {
        if (confirm("¿Está seguro de eliminar la " + nombre + "?")) {
            document.getElementById('idEliminar').value = id;
            document.getElementById('formEliminar').submit();
        }
    }
</script>

<%@ include file="../includes/footer.jsp" %>