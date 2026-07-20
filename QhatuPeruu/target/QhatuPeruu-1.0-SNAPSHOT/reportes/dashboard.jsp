<%-- 
    Document   : dashboard
    Created on : 18 jul. 2026, 12:45:56
    Author     : USER
--%>

<%@ include file="../includes/header.jsp" %>

<div class="d-flex justify-content-between align-items-center mb-4">
    <h2><i class="bi bi-file-earmark-bar-graph"></i> Reportes</h2>
    <span class="text-muted"><%= new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").format(new java.util.Date()) %></span>
</div>

<!-- Tarjetas de resumen -->
<div class="row g-4 mb-4">

    <div class="col-xl-3 col-md-6">
        <div class="card card-dashboard text-white bg-primary">
            <div class="card-body">
                <div class="d-flex justify-content-between align-items-center">
                    <div>
                        <h6 class="mb-0 text-white-50">Total Usuarios</h6>
                        <h2 class="mb-0">${totalUsuarios}</h2>
                        <small class="text-white-50">
                            <i class="bi bi-person-check"></i> ${usuariosActivos} activos
                            <span class="mx-1">|</span>
                            <i class="bi bi-person-x"></i> ${usuariosInactivos} inactivos
                        </small>
                    </div>
                    <i class="bi bi-people" style="font-size: 3rem; opacity: 0.6;"></i>
                </div>
            </div>
        </div>
    </div>

    <div class="col-xl-3 col-md-6">
        <div class="card card-dashboard text-white bg-success">
            <div class="card-body">
                <div class="d-flex justify-content-between align-items-center">
                    <div>
                        <h6 class="mb-0 text-white-50">Total Artículos</h6>
                        <h2 class="mb-0">${totalArticulos}</h2>
                        <small class="text-white-50">
                            <i class="bi bi-box-seam"></i> En inventario
                        </small>
                    </div>
                    <i class="bi bi-box-seam" style="font-size: 3rem; opacity: 0.6;"></i>
                </div>
            </div>
        </div>
    </div>

    <div class="col-xl-3 col-md-6">
        <div class="card card-dashboard text-white bg-info">
            <div class="card-body">
                <div class="d-flex justify-content-between align-items-center">
                    <div>
                        <h6 class="mb-0 text-white-50">Total Proveedores</h6>
                        <h2 class="mb-0">${totalProveedores}</h2>
                        <small class="text-white-50">
                            <i class="bi bi-truck"></i> Registrados
                        </small>
                    </div>
                    <i class="bi bi-truck" style="font-size: 3rem; opacity: 0.6;"></i>
                </div>
            </div>
        </div>
    </div>

    <div class="col-xl-3 col-md-6">
        <div class="card card-dashboard text-white bg-warning">
            <div class="card-body">
                <div class="d-flex justify-content-between align-items-center">
                    <div>
                        <h6 class="mb-0 text-white-50">Total Roles</h6>
                        <h2 class="mb-0">${totalRoles}</h2>
                        <small class="text-white-50">
                            <i class="bi bi-shield-lock"></i> Perfiles
                        </small>
                    </div>
                    <i class="bi bi-shield-lock" style="font-size: 3rem; opacity: 0.6;"></i>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="row g-4 mb-4">
    <div class="col-xl-6 col-md-6">
        <div class="card card-dashboard text-white bg-secondary">
            <div class="card-body">
                <div class="d-flex justify-content-between align-items-center">
                    <div>
                        <h6 class="mb-0 text-white-50">Órdenes de Compra</h6>
                        <h2 class="mb-0">${totalCompras}</h2>
                        <small class="text-white-50">
                            <i class="bi bi-cart-plus"></i> Registradas
                        </small>
                    </div>
                    <i class="bi bi-cart-plus" style="font-size: 3rem; opacity: 0.6;"></i>
                </div>
            </div>
        </div>
    </div>

    <div class="col-xl-6 col-md-6">
        <div class="card card-dashboard text-white bg-danger">
            <div class="card-body">
                <div class="d-flex justify-content-between align-items-center">
                    <div>
                        <h6 class="mb-0 text-white-50">Guías de Envío</h6>
                        <h2 class="mb-0">${totalGuias}</h2>
                        <small class="text-white-50">
                            <i class="bi bi-truck"></i> Enviadas
                        </small>
                    </div>
                    <i class="bi bi-truck" style="font-size: 3rem; opacity: 0.6;"></i>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Acceso a reportes detallados -->
<div class="row g-4">
    <div class="col-12">
        <div class="card">
            <div class="card-header bg-dark text-white">
                <i class="bi bi-file-earmark-text"></i> Reportes Detallados
            </div>
            <div class="card-body">
                <div class="row g-3 text-center">
                    <div class="col-md-2 col-4">
                        <a href="${pageContext.request.contextPath}/reportes?accion=usuarios" class="text-decoration-none">
                            <div class="p-3 bg-light rounded-3 hover-shadow">
                                <i class="bi bi-people fs-1 text-primary"></i>
                                <p class="mb-0 small mt-1">Usuarios</p>
                            </div>
                        </a>
                    </div>
                    <div class="col-md-2 col-4">
                        <a href="${pageContext.request.contextPath}/reportes?accion=articulos" class="text-decoration-none">
                            <div class="p-3 bg-light rounded-3 hover-shadow">
                                <i class="bi bi-box-seam fs-1 text-success"></i>
                                <p class="mb-0 small mt-1">Artículos</p>
                            </div>
                        </a>
                    </div>
                    <div class="col-md-2 col-4">
                        <a href="${pageContext.request.contextPath}/reportes?accion=compras" class="text-decoration-none">
                            <div class="p-3 bg-light rounded-3 hover-shadow">
                                <i class="bi bi-cart-plus fs-1 text-info"></i>
                                <p class="mb-0 small mt-1">Compras</p>
                            </div>
                        </a>
                    </div>
                    <div class="col-md-2 col-4">
                        <a href="${pageContext.request.contextPath}/reportes?accion=guias" class="text-decoration-none">
                            <div class="p-3 bg-light rounded-3 hover-shadow">
                                <i class="bi bi-truck fs-1 text-warning"></i>
                                <p class="mb-0 small mt-1">Guías</p>
                            </div>
                        </a>
                    </div>
                    <div class="col-md-2 col-4">
                        <a href="${pageContext.request.contextPath}/reportes?accion=proveedores" class="text-decoration-none">
                            <div class="p-3 bg-light rounded-3 hover-shadow">
                                <i class="bi bi-truck fs-1 text-danger"></i>
                                <p class="mb-0 small mt-1">Proveedores</p>
                            </div>
                        </a>
                    </div>
                    <div class="col-md-2 col-4">
                        <a href="${pageContext.request.contextPath}/reportes" class="text-decoration-none">
                            <div class="p-3 bg-light rounded-3 hover-shadow">
                                <i class="bi bi-house fs-1 text-secondary"></i>
                                <p class="mb-0 small mt-1">Inicio</p>
                            </div>
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<style>
    .hover-shadow {
        transition: all 0.3s ease;
        cursor: pointer;
    }
    .hover-shadow:hover {
        transform: translateY(-5px);
        box-shadow: 0 8px 25px rgba(0,0,0,0.15);
        background-color: #e9ecef !important;
    }
    .card-dashboard {
        border: none;
        border-radius: 12px;
        box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
        transition: transform 0.2s;
    }
    .card-dashboard:hover {
        transform: translateY(-4px);
        box-shadow: 0 8px 25px rgba(0,0,0,0.2);
    }
    .card-dashboard .card-body {
        padding: 1.5rem;
    }
</style>

<%@ include file="../includes/footer.jsp" %>