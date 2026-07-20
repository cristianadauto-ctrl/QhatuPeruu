<%-- 
    Document   : usuarios
    Created on : 18 jul. 2026, 12:46:21
    Author     : USER
--%>

<%@ page import="com.mycompany.qhatuperuu.modelo.Usuario" %>
<%@ page import="java.util.List" %>
<%@ include file="../includes/header.jsp" %>

<div class="d-flex justify-content-between align-items-center mb-3">
    <h2><i class="bi bi-people"></i> Reporte de Usuarios</h2>
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
            <table class="table table-hover align-middle" id="reporteTable">
                <thead>
                    <tr>
                        <th>#</th>
                        <th>Usuario</th>
                        <th>Nombre Completo</th>
                        <th>Correo</th>
                        <th>Rol</th>
                        <th>Estado</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        List<Usuario> lista = (List<Usuario>) request.getAttribute("listaUsuarios");
                        int contador = 1;
                        if (lista != null && !lista.isEmpty()) {
                            for (Usuario u : lista) {
                    %>
                    <tr>
                        <td><%= contador++ %></td>
                        <td><%= u.getUsername() %></td>
                        <td><%= u.getNombreCompleto() %></td>
                        <td><%= u.getCorreo() != null ? u.getCorreo() : "-" %></td>
                        <td><%= u.getNomRol() %></td>
                        <td>
                            <% if (u.isEstado()) { %>
                                <span class="badge bg-success">Activo</span>
                            <% } else { %>
                                <span class="badge bg-secondary">Inactivo</span>
                            <% } %>
                        </td>
                    </tr>
                    <%
                            }
                        } else {
                    %>
                    <tr>
                        <td colspan="6" class="text-center text-muted">No hay usuarios registrados.</td>
                    </tr>
                    <% } %>
                </tbody>
                <tfoot>
                    <tr>
                        <td colspan="6" class="text-end">
                            <strong>Total: <%= lista != null ? lista.size() : 0 %> usuarios</strong>
                        </td>
                    </tr>
                </tfoot>
            </table>
        </div>
    </div>
</div>

<%@ include file="../includes/footer.jsp" %>