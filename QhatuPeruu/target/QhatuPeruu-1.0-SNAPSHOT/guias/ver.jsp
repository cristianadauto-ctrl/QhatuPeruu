<%-- 
    Document   : ver
    Created on : 18 jul. 2026, 12:13:47
    Author     : USER
--%>

<%@ page import="com.mycompany.qhatuperuu.modelo.GuiaEnvio" %>
<%@ page import="com.mycompany.qhatuperuu.modelo.GuiaDetalle" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ include file="../includes/header.jsp" %>

<%
    GuiaEnvio guia = (GuiaEnvio) request.getAttribute("guia");
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
%>

<h2><i class="bi bi-eye"></i> Detalle de Guía de Envío #<%= guia.getNumGuia() %></h2>

<div class="card">
    <div class="card-body">
        <div class="row">
            <div class="col-md-4">
                <p><strong>N° Guía:</strong> <%= guia.getNumGuia() %></p>
                <p><strong>Fecha Salida:</strong> <%= guia.getFechaSalida().format(formatter) %></p>
            </div>
            <div class="col-md-4">
                <p><strong>Tienda:</strong> <%= guia.getNomTienda() %></p>
            </div>
            <div class="col-md-4">
                <p><strong>Transportista:</strong> <%= guia.getNomTransportista() %></p>
            </div>
        </div>
        
        <hr>
        <h5><i class="bi bi-list-ul"></i> Artículos Enviados</h5>
        
        <div class="table-responsive">
            <table class="table table-hover">
                <thead>
                    <tr>
                        <th>Artículo</th>
                        <th>Presentación</th>
                        <th>Precio Venta</th>
                        <th>Cantidad</th>
                        <th>Subtotal</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        double total = 0;
                        if (guia.getDetalles() != null && !guia.getDetalles().isEmpty()) {
                            for (GuiaDetalle d : guia.getDetalles()) {
                                double subtotal = d.getPrecioVenta() * d.getCantidadEnviada();
                                total += subtotal;
                    %>
                    <tr>
                        <td><%= d.getDescripcionArticulo() %></td>
                        <td><%= d.getPresentacion() != null ? d.getPresentacion() : "-" %></td>
                        <td>S/. <%= d.getPrecioVenta() %></td>
                        <td><%= d.getCantidadEnviada() %></td>
                        <td>S/. <%= String.format("%.2f", subtotal) %></td>
                    </tr>
                    <%
                            }
                        } else {
                    %>
                    <tr>
                        <td colspan="5" class="text-center">No hay detalles para esta guía.</td>
                    </tr>
                    <% } %>
                </tbody>
                <tfoot>
                    <tr>
                        <th colspan="4" class="text-end">Total:</th>
                        <th>S/. <%= String.format("%.2f", total) %></th>
                    </tr>
                </tfoot>
            </table>
        </div>
        
        <div class="mt-3">
            <a href="${pageContext.request.contextPath}/guias" class="btn btn-secondary">
                <i class="bi bi-arrow-left"></i> Volver
            </a>
        </div>
    </div>
</div>

<%@ include file="../includes/footer.jsp" %>