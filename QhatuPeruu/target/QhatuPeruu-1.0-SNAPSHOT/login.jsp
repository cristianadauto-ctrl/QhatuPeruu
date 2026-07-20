<%-- 
    Document   : login
    Created on : 9 jul. 2026, 12:59:18
    Author     : Tec
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Iniciar Sesión - QhatuPerú</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700;800&display=swap" rel="stylesheet">
    <style>
        * { font-family: 'Inter', sans-serif; }
        body {
            background: linear-gradient(135deg, #0f0c29, #302b63, #24243e);
            height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            margin: 0;
        }
        .login-card {
            max-width: 420px;
            width: 100%;
            border: none;
            border-radius: 24px;
            padding: 2.5rem;
            box-shadow: 0 20px 60px rgba(0,0,0,0.5);
            background: #ffffff;
            animation: fadeInUp 0.6s ease;
        }
        @keyframes fadeInUp {
            from { opacity: 0; transform: translateY(30px); }
            to { opacity: 1; transform: translateY(0); }
        }
        .login-card .logo {
            text-align: center;
            margin-bottom: 1.5rem;
        }
        .login-card .logo i {
            font-size: 4rem;
            color: #302b63;
            background: #f0f2ff;
            padding: 20px;
            border-radius: 50%;
        }
        .login-card .logo h3 {
            font-weight: 800;
            color: #1a1a2e;
            margin-top: 12px;
        }
        .login-card .logo p {
            color: #6b7280;
            font-size: 0.9rem;
            margin-top: -4px;
        }
        .login-card .form-control {
            border-radius: 12px;
            border: 2px solid #e5e7eb;
            padding: 12px 16px;
            font-size: 0.95rem;
            transition: all 0.3s ease;
        }
        .login-card .form-control:focus {
            border-color: #302b63;
            box-shadow: 0 0 0 4px rgba(48, 43, 99, 0.1);
        }
        .login-card .form-label {
            font-weight: 600;
            color: #374151;
            font-size: 0.85rem;
        }
        .login-card .btn-login {
            background: linear-gradient(135deg, #302b63, #24243e);
            border: none;
            border-radius: 12px;
            padding: 12px;
            font-weight: 700;
            font-size: 1rem;
            width: 100%;
            color: #fff;
            transition: all 0.3s ease;
        }
        .login-card .btn-login:hover {
            transform: translateY(-2px);
            box-shadow: 0 8px 25px rgba(48, 43, 99, 0.4);
        }
        .login-card .btn-login i {
            margin-right: 8px;
        }
        .login-card .alert {
            border-radius: 12px;
            border: none;
            padding: 12px 16px;
            font-size: 0.9rem;
        }
        .login-card .alert-danger {
            background: #fef2f2;
            color: #991b1b;
        }
        .login-card .footer-text {
            text-align: center;
            margin-top: 1.5rem;
            color: #9ca3af;
            font-size: 0.8rem;
        }
        .login-card .footer-text strong {
            color: #302b63;
        }
        .input-group-icon {
            position: relative;
        }
        .input-group-icon .form-control {
            padding-left: 44px;
        }
        .input-group-icon .icon {
            position: absolute;
            left: 14px;
            top: 50%;
            transform: translateY(-50%);
            color: #9ca3af;
            font-size: 1.2rem;
            z-index: 10;
        }
    </style>
</head>
<body>

<div class="login-card">
    <div class="logo">
        <i class="bi bi-shop"></i>
        <h3>QhatuPerú</h3>
        <p>Sistema de Gestión Empresarial</p>
    </div>

    <%
        String mensajeError = (String) request.getAttribute("mensajeError");
        if (mensajeError == null) {
            mensajeError = (String) session.getAttribute("mensajeError");
            if (mensajeError != null) {
                session.removeAttribute("mensajeError");
            }
        }
        if (mensajeError != null) {
    %>
    <div class="alert alert-danger">
        <i class="bi bi-exclamation-triangle-fill me-2"></i> <%= mensajeError %>
    </div>
    <% } %>

    <form action="${pageContext.request.contextPath}/login" method="post">
        <div class="mb-3">
            <label class="form-label">Usuario</label>
            <div class="input-group-icon">
                <span class="icon"><i class="bi bi-person"></i></span>
                <input type="text" class="form-control" name="username" placeholder="Ingresa tu usuario" required autofocus>
            </div>
        </div>

        <div class="mb-4">
            <label class="form-label">Contraseña</label>
            <div class="input-group-icon">
                <span class="icon"><i class="bi bi-lock"></i></span>
                <input type="password" class="form-control" name="clave" placeholder="Ingresa tu contraseña" required>
            </div>
        </div>

        <button type="submit" class="btn-login">
            <i class="bi bi-box-arrow-in-right"></i> Iniciar Sesión
        </button>
    </form>

    <div class="footer-text">
        &copy; 2026 <strong>QhatuPeru</strong> - Todos los derechos reservados
    </div>
</div>

</body>
</html>
