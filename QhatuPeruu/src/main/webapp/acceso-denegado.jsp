<%-- 
    Document   : acceso-denegado
    Created on : 10 jul. 2026, 20:44:00
    Author     : USER
--%>

<%@ include file="includes/header.jsp" %>

<div class="text-center py-5">
    <i class="bi bi-shield-exclamation" style="font-size: 4rem; color: #dc3545;"></i>
    <h2 class="mt-3">Acceso Denegado</h2>
    <p class="text-muted">Tu rol actual (<strong>${sessionScope.nomRol}</strong>) no tiene permisos para acceder a esta sección.</p>
    <a href="${pageContext.request.contextPath}/logout" class="btn btn-outline-danger mt-2">
        <i class="bi bi-box-arrow-right"></i> Cerrar sesión
    </a>
</div>

<%@ include file="includes/footer.jsp" %>
