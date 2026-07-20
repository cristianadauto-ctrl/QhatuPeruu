<%-- 
    Document   : header
    Created on : 9 jul. 2026, 13:04:02
    Author     : Tec
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>QhatuPerú - Sistema de Gestión</title>

    <!-- Bootstrap 5 -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Bootstrap Icons -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">
    <!-- Google Fonts -->
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700;800&display=swap" rel="stylesheet">
    <!-- Estilos personalizados -->
    <link href="${pageContext.request.contextPath}/resources/css/estilos.css" rel="stylesheet">
    
    <style>
        body {
            font-family: 'Inter', sans-serif;
        }
    </style>
</head>

<body>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark px-3 sticky-top">

    <a class="navbar-brand" href="${pageContext.request.contextPath}/dashboard">
        <i class="bi bi-shop"></i> QhatuPeru
    </a>

    <button class="navbar-toggler" type="button"
            data-bs-toggle="collapse"
            data-bs-target="#navbarMenu">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarMenu">

        <ul class="navbar-nav me-auto">

            <li class="nav-item">
                <a class="nav-link ${pageContext.request.servletPath == '/dashboard.jsp' ? 'active' : ''}" 
                   href="${pageContext.request.contextPath}/dashboard">
                    <i class="bi bi-speedometer2"></i> Dashboard
                </a>
            </li>

            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown">
                    <i class="bi bi-gear"></i> Mantenimiento
                </a>
                <ul class="dropdown-menu">
                    <li><a class="dropdown-item" href="${pageContext.request.contextPath}/usuarios">
                        <i class="bi bi-people"></i> Usuarios</a></li>
                    <li><a class="dropdown-item" href="${pageContext.request.contextPath}/roles">
                        <i class="bi bi-shield-lock"></i> Roles</a></li>
                    <li><hr class="dropdown-divider"></li>
                    <li><a class="dropdown-item" href="${pageContext.request.contextPath}/lineas">
                        <i class="bi bi-tags"></i> Líneas</a></li>
                    <li><a class="dropdown-item" href="${pageContext.request.contextPath}/proveedores">
                        <i class="bi bi-truck"></i> Proveedores</a></li>
                    <li><a class="dropdown-item" href="${pageContext.request.contextPath}/articulos">
                        <i class="bi bi-box-seam"></i> Artículos</a></li>
                    <li><hr class="dropdown-divider"></li>
                    <li><a class="dropdown-item" href="${pageContext.request.contextPath}/transportistas">
                        <i class="bi bi-truck-front"></i> Transportistas</a></li>
                    <li><a class="dropdown-item" href="${pageContext.request.contextPath}/tiendas">
                        <i class="bi bi-shop"></i> Tiendas</a></li>
                </ul>
            </li>

            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown">
                    <i class="bi bi-cart"></i> Operaciones
                </a>
                <ul class="dropdown-menu">
                    <li><a class="dropdown-item" href="${pageContext.request.contextPath}/compras">
                        <i class="bi bi-cart-plus"></i> Compras</a></li>
                    <li><a class="dropdown-item" href="${pageContext.request.contextPath}/guias">
                        <i class="bi bi-truck"></i> Guías de Envío</a></li>
                </ul>
            </li>

            <li class="nav-item">
                <a class="nav-link ${pageContext.request.servletPath == '/reportes/dashboard.jsp' ? 'active' : ''}" 
                   href="${pageContext.request.contextPath}/reportes">
                    <i class="bi bi-file-earmark-bar-graph"></i> Reportes
                </a>
            </li>

        </ul>

        <ul class="navbar-nav">

            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown">
                    <i class="bi bi-person-circle"></i>
                    ${sessionScope.nombreCompleto}
                </a>
                <ul class="dropdown-menu dropdown-menu-end">
                    <li><span class="dropdown-item-text">
                        <small class="text-muted">Rol: ${sessionScope.nomRol}</small>
                    </span></li>
                    <li><hr class="dropdown-divider"></li>
                    <li><a class="dropdown-item text-danger" href="${pageContext.request.contextPath}/logout">
                        <i class="bi bi-box-arrow-right"></i> Cerrar sesión
                    </a></li>
                </ul>
            </li>

        </ul>

    </div>

</nav>

<div class="container-fluid px-4 py-3">