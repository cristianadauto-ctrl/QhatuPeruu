<%-- 
    Document   : guias
    Created on : 18 jul. 2026, 12:47:12
    Author     : USER
--%>

<%@ page import="com.mycompany.qhatuperuu.modelo.GuiaEnvio" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.sql.Timestamp" %>
<%@ include file="../includes/header.jsp" %>

<%
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
%>

<div class="d-flex justify-content-between align-items-center mb-3">
    <h2><i class="bi bi-truck"></i> Reporte de Guías de Envío</h2>
    <div>
        <button onclick="window.print()" class="btn btn-outline-primary">
            <i class="bi bi-printer"></i> Imprimir
        </button>
        <a href="${pageContext.request.contextPath}/reportes" class="btn btn-secondary">
            <i class="bi bi-arrow-left"></i> Volver
        </a>
    </div>
</div>

<div class="card">
    <div class="card-body">
        <div class="table-responsive">
            <table class="table table-hover align-middle">
                <thead>
                    <tr>
                        <th>#</th>
                        <th>N° Guía</th>
                        <th>Tienda</th>
                        <th>Transportista</th>
                        <th>Fecha Salida</th>
                        <th>Artículos</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        List<GuiaEnvio> lista = (List<GuiaEnvio>) request.getAttribute("listaGuias");
                        int contador = 1;
                        if (lista != null && !lista.isEmpty()) {
                            for (GuiaEnvio g : lista) {
                    %>
                    <tr>
                        <td><%= contador++ %></td>
                        <td><strong>#<%= g.getNumGuia() %></strong></td>
                        <td><%= g.getNomTienda() %></td>
                        <td><%= g.getNomTransportista() %></td>
                        <td><%= sdf.format(Timestamp.valueOf(g.getFechaSalida())) %></td>
                        <td><%= g.getTotalDetalles() %></td>
                    </tr>
                    <%
                            }
                        } else {
                    %>
                    <tr>
                        <td colspan="6" class="text-center text-muted">No hay guías de envío.</td>
                    </tr>
                    <% } %>
                </tbody>
                <tfoot>
                    <tr>
                        <td colspan="6" class="text-end">
                            <strong>Total: <%= lista != null ? lista.size() : 0 %> guías</strong>
                        </td>
                    </tr>
                </tfoot>
            </table>
        </div>
    </div>
</div>

<%@ include file="../includes/footer.jsp" %>