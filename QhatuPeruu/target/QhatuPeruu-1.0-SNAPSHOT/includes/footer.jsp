<%-- 
    Document   : footer
    Created on : 9 jul. 2026, 13:04:15
    Author     : Tec
--%>

</div> <!-- Cierra container-fluid -->

<footer class="text-center text-muted py-4 mt-4 border-top">
    <div class="container">
        <div class="row">
            <div class="col-md-6 text-md-start">
                <small>&copy; 2026 <strong>QhatuPeru</strong> - Sistema de Gestión Empresarial</small>
            </div>
            <div class="col-md-6 text-md-end">
                <small>
                    <i class="bi bi-person-check"></i> ${sessionScope.nombreCompleto} 
                    <span class="mx-2">|</span>
                    <i class="bi bi-clock"></i> <span id="horaActual"></span>
                </small>
            </div>
        </div>
    </div>
</footer>

<!-- Scripts -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/validaciones.js"></script>

<script>
    // Mostrar hora actual en el footer
    function actualizarHora() {
        const ahora = new Date();
        const hora = String(ahora.getHours()).padStart(2, '0');
        const minutos = String(ahora.getMinutes()).padStart(2, '0');
        const segundos = String(ahora.getSeconds()).padStart(2, '0');
        document.getElementById('horaActual').textContent = hora + ':' + minutos + ':' + segundos;
    }
    actualizarHora();
    setInterval(actualizarHora, 1000);
</script>

</body>
</html>