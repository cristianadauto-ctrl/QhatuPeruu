<%-- 
    Document   : dashboard
    Created on : 9 jul. 2026, 12:59:39
    Author     : Tec
--%>

<%@ page import="com.mycompany.qhatuperuu.modelo.Usuario" %>
<%@ page import="com.mycompany.qhatuperuu.modelo.OrdenCompra" %>
<%@ page import="com.mycompany.qhatuperuu.dao.UsuarioDAO" %>
<%@ page import="com.mycompany.qhatuperuu.dao.OrdenCompraDAO" %>
<%@ page import="com.mycompany.qhatuperuu.dao.RolDAO" %>
<%@ page import="com.mycompany.qhatuperuu.dao.ArticuloDAO" %>
<%@ page import="com.mycompany.qhatuperuu.dao.ProveedorDAO" %>
<%@ page import="com.mycompany.qhatuperuu.dao.GuiaEnvioDAO" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.sql.Timestamp" %>
<%@ include file="includes/header.jsp" %>

<%
    // Obtener los datos para el dashboard
    UsuarioDAO usuarioDAO = new UsuarioDAO();
    RolDAO rolDAO = new RolDAO();
    ArticuloDAO articuloDAO = new ArticuloDAO();
    ProveedorDAO proveedorDAO = new ProveedorDAO();
    OrdenCompraDAO ordenCompraDAO = new OrdenCompraDAO();
    GuiaEnvioDAO guiaEnvioDAO = new GuiaEnvioDAO();

    int totalUsuarios = usuarioDAO.contarUsuarios();
    int totalRoles = rolDAO.contarRoles();
    int usuariosActivos = usuarioDAO.contarActivos();
    int usuariosInactivos = usuarioDAO.contarInactivos();
    int totalArticulos = articuloDAO.contarArticulos();
    int totalProveedores = proveedorDAO.contarProveedores();
    int totalCompras = ordenCompraDAO.contarOrdenes();
    int totalGuias = guiaEnvioDAO.contarGuias();
    
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
%>

<div class="d-flex justify-content-between align-items-center mb-4 fade-in">
    <div>
        <h2 class="page-title"><i class="bi bi-speedometer2"></i> Dashboard</h2>
        <p class="text-muted mb-0">Bienvenido, ${sessionScope.nombreCompleto} · <i class="bi bi-calendar3"></i> <%= new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new java.util.Date()) %></p>
    </div>
    <div>
        <span class="badge bg-success"><i class="bi bi-check-circle"></i> Sistema Operativo</span>
    </div>
</div>

<!-- Tarjetas de resumen -->
<div class="row g-4 mb-4">

    <div class="col-xl-3 col-md-6">
        <div class="card card-dashboard text-white" style="background: linear-gradient(135deg, #6366f1, #4f46e5);">
            <div class="card-body">
                <div class="d-flex justify-content-between align-items-center">
                    <div>
                        <span class="label">Usuarios</span>
                        <h2 class="number"><%= totalUsuarios %></h2>
                        <small>
                            <i class="bi bi-person-check"></i> <%= usuariosActivos %> activos
                            <span class="mx-1">|</span>
                            <i class="bi bi-person-x"></i> <%= usuariosInactivos %> inactivos
                        </small>
                    </div>
                    <div class="icon-circle bg-white-20">
                        <i class="bi bi-people"></i>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="col-xl-3 col-md-6">
        <div class="card card-dashboard text-white" style="background: linear-gradient(135deg, #10b981, #059669);">
            <div class="card-body">
                <div class="d-flex justify-content-between align-items-center">
                    <div>
                        <span class="label">Artículos</span>
                        <h2 class="number"><%= totalArticulos %></h2>
                        <small><i class="bi bi-box-seam"></i> En inventario</small>
                    </div>
                    <div class="icon-circle bg-white-20">
                        <i class="bi bi-box-seam"></i>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="col-xl-3 col-md-6">
        <div class="card card-dashboard text-white" style="background: linear-gradient(135deg, #3b82f6, #2563eb);">
            <div class="card-body">
                <div class="d-flex justify-content-between align-items-center">
                    <div>
                        <span class="label">Proveedores</span>
                        <h2 class="number"><%= totalProveedores %></h2>
                        <small><i class="bi bi-truck"></i> Registrados</small>
                    </div>
                    <div class="icon-circle bg-white-20">
                        <i class="bi bi-truck"></i>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="col-xl-3 col-md-6">
        <div class="card card-dashboard text-white" style="background: linear-gradient(135deg, #f59e0b, #d97706);">
            <div class="card-body">
                <div class="d-flex justify-content-between align-items-center">
                    <div>
                        <span class="label">Roles</span>
                        <h2 class="number"><%= totalRoles %></h2>
                        <small><i class="bi bi-shield-lock"></i> Perfiles</small>
                    </div>
                    <div class="icon-circle bg-white-20">
                        <i class="bi bi-shield-lock"></i>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Segunda fila -->
<div class="row g-4 mb-4">

    <div class="col-xl-4 col-md-6">
        <div class="card card-dashboard text-white" style="background: linear-gradient(135deg, #6b7280, #4b5563);">
            <div class="card-body">
                <div class="d-flex justify-content-between align-items-center">
                    <div>
                        <span class="label">Órdenes de Compra</span>
                        <h2 class="number"><%= totalCompras %></h2>
                        <small><i class="bi bi-cart-plus"></i> Registradas</small>
                    </div>
                    <div class="icon-circle bg-white-20">
                        <i class="bi bi-cart-plus"></i>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="col-xl-4 col-md-6">
        <div class="card card-dashboard text-white" style="background: linear-gradient(135deg, #ef4444, #dc2626);">
            <div class="card-body">
                <div class="d-flex justify-content-between align-items-center">
                    <div>
                        <span class="label">Guías de Envío</span>
                        <h2 class="number"><%= totalGuias %></h2>
                        <small><i class="bi bi-truck"></i> Enviadas</small>
                    </div>
                    <div class="icon-circle bg-white-20">
                        <i class="bi bi-truck"></i>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="col-xl-4 col-md-6">
        <div class="card card-dashboard text-white" style="background: linear-gradient(135deg, #1a1a2e, #0f0c29);">
            <div class="card-body">
                <div class="d-flex justify-content-between align-items-center">
                    <div>
                        <span class="label">Sistema</span>
                        <h2 class="number" style="font-size: 1.8rem;">QhatuPeru</h2>
                        <small><i class="bi bi-check-circle"></i> Operativo</small>
                    </div>
                    <div class="icon-circle bg-white-20">
                        <i class="bi bi-shop"></i>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Tablas de resumen -->
<div class="row g-4">

    <div class="col-xl-6">
        <div class="card">
            <div class="card-header bg-dark text-white d-flex justify-content-between align-items-center">
                <span><i class="bi bi-people"></i> Últimos Usuarios</span>
                <a href="${pageContext.request.contextPath}/usuarios" class="btn btn-sm btn-outline-light">
                    Ver todos <i class="bi bi-arrow-right"></i>
                </a>
            </div>
            <div class="card-body">
                <div class="table-responsive">
                    <table class="table table-hover align-middle">
                        <thead>
                            <tr>
                                <th>Usuario</th>
                                <th>Nombre</th>
                                <th>Rol</th>
                                <th>Estado</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                List<Usuario> ultimosUsuarios = usuarioDAO.listarTodos();
                                int count = 0;
                                if (ultimosUsuarios != null && !ultimosUsuarios.isEmpty()) {
                                    for (int i = ultimosUsuarios.size() - 1; i >= 0 && count < 5; i--) {
                                        Usuario u = ultimosUsuarios.get(i);
                                        count++;
                            %>
                            <tr>
                                <td><strong><%= u.getUsername() %></strong></td>
                                <td><%= u.getNombreCompleto() %></td>
                                <td><span class="badge bg-primary"><%= u.getNomRol() %></span></td>
                                <td>
                                    <% if (u.isEstado()) { %>
                                        <span class="badge badge-activo">Activo</span>
                                    <% } else { %>
                                        <span class="badge badge-inactivo">Inactivo</span>
                                    <% } %>
                                </td>
                            </tr>
                            <%
                                    }
                                } else {
                            %>
                            <tr>
                                <td colspan="4" class="text-center text-muted">No hay usuarios registrados.</td>
                            </tr>
                            <% } %>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

    <div class="col-xl-6">
        <div class="card">
            <div class="card-header bg-dark text-white d-flex justify-content-between align-items-center">
                <span><i class="bi bi-cart-plus"></i> Últimas Compras</span>
                <a href="${pageContext.request.contextPath}/compras" class="btn btn-sm btn-outline-light">
                    Ver todas <i class="bi bi-arrow-right"></i>
                </a>
            </div>
            <div class="card-body">
                <div class="table-responsive">
                    <table class="table table-hover align-middle">
                        <thead>
                            <tr>
                                <th>N° Orden</th>
                                <th>Fecha</th>
                                <th>Artículos</th>
                                <th>Estado</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                List<OrdenCompra> ultimasCompras = ordenCompraDAO.listarTodos();
                                int count2 = 0;
                                if (ultimasCompras != null && !ultimasCompras.isEmpty()) {
                                    for (OrdenCompra o : ultimasCompras) {
                                        if (count2 >= 5) break;
                                        count2++;
                            %>
                            <tr>
                                <td><strong>#<%= o.getNumOrden() %></strong></td>
                                <td><%= sdf.format(Timestamp.valueOf(o.getFechaOrden())) %></td>
                                <td><%= o.getTotalDetalles() %> artículo(s)</td>
                                <td>
                                    <% if (o.getFechaIngreso() != null) { %>
                                        <span class="badge badge-completado">Completada</span>
                                    <% } else { %>
                                        <span class="badge badge-pendiente">Pendiente</span>
                                    <% } %>
                                </td>
                            </tr>
                            <%
                                    }
                                } else {
                            %>
                            <tr>
                                <td colspan="4" class="text-center text-muted">No hay órdenes de compra.</td>
                            </tr>
                            <% } %>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Accesos rápidos -->
<div class="row g-4 mt-2">
    <div class="col-12">
        <div class="card">
            <div class="card-header bg-dark text-white">
                <i class="bi bi-lightning"></i> Accesos Rápidos
            </div>
            <div class="card-body">
                <div class="row g-3 text-center">
                    <div class="col-md-2 col-4">
                        <a href="${pageContext.request.contextPath}/usuarios?accion=nuevo" class="quick-access-item">
                            <i class="bi bi-person-plus text-primary"></i>
                            <div class="label">Nuevo Usuario</div>
                        </a>
                    </div>
                    <div class="col-md-2 col-4">
                        <a href="${pageContext.request.contextPath}/articulos?accion=nuevo" class="quick-access-item">
                            <i class="bi bi-box-seam text-success"></i>
                            <div class="label">Nuevo Artículo</div>
                        </a>
                    </div>
                    <div class="col-md-2 col-4">
                        <a href="${pageContext.request.contextPath}/compras?accion=nuevo" class="quick-access-item">
                            <i class="bi bi-cart-plus text-info"></i>
                            <div class="label">Nueva Compra</div>
                        </a>
                    </div>
                    <div class="col-md-2 col-4">
                        <a href="${pageContext.request.contextPath}/guias?accion=nuevo" class="quick-access-item">
                            <i class="bi bi-truck text-warning"></i>
                            <div class="label">Nueva Guía</div>
                        </a>
                    </div>
                    <div class="col-md-2 col-4">
                        <a href="${pageContext.request.contextPath}/proveedores?accion=nuevo" class="quick-access-item">
                            <i class="bi bi-truck text-danger"></i>
                            <div class="label">Nuevo Proveedor</div>
                        </a>
                    </div>
                    <div class="col-md-2 col-4">
                        <a href="${pageContext.request.contextPath}/roles?accion=nuevo" class="quick-access-item">
                            <i class="bi bi-shield-lock text-secondary"></i>
                            <div class="label">Nuevo Rol</div>
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<style>
    .fade-in {
        animation: fadeIn 0.5s ease-in-out;
    }
    @keyframes fadeIn {
        from { opacity: 0; transform: translateY(20px); }
        to { opacity: 1; transform: translateY(0); }
    }
    .page-title {
        font-weight: 700;
        color: #1a1a2e;
        font-size: 1.8rem;
    }
    .page-title i {
        margin-right: 10px;
        color: #3b82f6;
    }
    .card-dashboard {
        border: none;
        border-radius: 16px;
        transition: all 0.3s ease;
    }
    .card-dashboard:hover {
        transform: translateY(-6px);
        box-shadow: 0 12px 40px rgba(0, 0, 0, 0.2);
    }
    .card-dashboard .card-body {
        padding: 1.8rem;
    }
    .card-dashboard .label {
        font-size: 0.8rem;
        opacity: 0.8;
        text-transform: uppercase;
        letter-spacing: 0.5px;
        font-weight: 600;
    }
    .card-dashboard .number {
        font-size: 2.5rem;
        font-weight: 700;
        line-height: 1.2;
    }
    .card-dashboard .icon-circle {
        width: 60px;
        height: 60px;
        border-radius: 50%;
        display: flex;
        align-items: center;
        justify-content: center;
        font-size: 2rem;
        background: rgba(255, 255, 255, 0.2);
    }
    .card-dashboard small {
        opacity: 0.8;
        font-size: 0.8rem;
    }
    .badge-activo {
        background: #10b981;
        color: #fff;
        padding: 6px 14px;
        border-radius: 50px;
        font-weight: 600;
    }
    .badge-inactivo {
        background: #6b7280;
        color: #fff;
        padding: 6px 14px;
        border-radius: 50px;
        font-weight: 600;
    }
    .badge-completado {
        background: #10b981;
        color: #fff;
        padding: 6px 14px;
        border-radius: 50px;
        font-weight: 600;
    }
    .badge-pendiente {
        background: #f59e0b;
        color: #fff;
        padding: 6px 14px;
        border-radius: 50px;
        font-weight: 600;
    }
    .quick-access-item {
        padding: 1.5rem 1rem;
        background: #f8fafc;
        border-radius: 12px;
        transition: all 0.3s ease;
        text-decoration: none;
        display: block;
        border: 2px solid transparent;
    }
    .quick-access-item:hover {
        border-color: #3b82f6;
        background: #eff6ff;
        transform: translateY(-4px);
        box-shadow: 0 8px 25px rgba(59, 130, 246, 0.15);
    }
    .quick-access-item i {
        font-size: 2.5rem;
        margin-bottom: 8px;
    }
    .quick-access-item .label {
        font-size: 0.8rem;
        font-weight: 500;
        color: #374151;
        margin-top: 4px;
    }
    .table thead th {
        background: #1a1a2e;
        color: #fff;
        font-weight: 600;
        text-transform: uppercase;
        font-size: 0.75rem;
        letter-spacing: 0.5px;
        padding: 12px 16px;
        border: none;
    }
    .table tbody td {
        padding: 12px 16px;
        vertical-align: middle;
        border-bottom: 1px solid #f0f0f0;
    }
    .table tbody tr:hover {
        background: #f8f9fa;
    }
    .card-header {
        font-weight: 600;
        border-bottom: none;
        padding: 16px 24px;
    }
    .card {
        border: none;
        border-radius: 16px;
        box-shadow: 0 4px 20px rgba(0, 0, 0, 0.06);
        transition: all 0.3s ease;
        overflow: hidden;
    }
    .card:hover {
        box-shadow: 0 8px 35px rgba(0, 0, 0, 0.1);
    }
</style>

<%@ include file="includes/footer.jsp" %>