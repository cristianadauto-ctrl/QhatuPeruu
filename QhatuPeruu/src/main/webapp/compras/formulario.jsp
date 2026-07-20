<%-- 
    Document   : formulario
    Created on : 18 jul. 2026, 11:14:58
    Author     : USER
--%>
<%@ page import="com.mycompany.qhatuperuu.modelo.OrdenCompra" %>
<%@ page import="com.mycompany.qhatuperuu.modelo.OrdenDetalle" %>
<%@ page import="com.mycompany.qhatuperuu.modelo.Articulo" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ include file="../includes/header.jsp" %>

<%
    OrdenCompra orden = (OrdenCompra) request.getAttribute("orden");
    boolean esEdicion = (orden != null);
    Integer siguienteNumOrden = (Integer) request.getAttribute("siguienteNumOrden");
    List<Articulo> listaArticulos = (List<Articulo>) request.getAttribute("listaArticulos");
    String mensajeError = (String) request.getAttribute("mensajeError");
    
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
    String fechaOrdenStr = "";
    String fechaIngresoStr = "";
    
    if (esEdicion) {
        fechaOrdenStr = orden.getFechaOrden().format(formatter);
        if (orden.getFechaIngreso() != null) {
            fechaIngresoStr = orden.getFechaIngreso().format(formatter);
        }
    }
    
    int numOrden = esEdicion ? orden.getNumOrden() : (siguienteNumOrden != null ? siguienteNumOrden : 1);
%>

<h2><i class="bi bi-<%= esEdicion ? "pencil-square" : "plus-circle" %>"></i> 
    <%= esEdicion ? "Editar Orden de Compra #" + numOrden : "Nueva Orden de Compra" %>
</h2>

<% if (mensajeError != null) { %>
    <div class="alert alert-danger"><i class="bi bi-exclamation-triangle"></i> <%= mensajeError %></div>
<% } %>

<div class="card">
    <div class="card-body">
        <form action="${pageContext.request.contextPath}/compras" method="post" id="formOrden">
            <input type="hidden" name="accion" value="guardar">
            <input type="hidden" name="numOrden" value="<%= numOrden %>">

            <div class="row g-3">
                <div class="col-md-6">
                    <label class="form-label">N° Orden</label>
                    <input type="text" class="form-control" value="<%= numOrden %>" disabled>
                </div>

                <div class="col-md-6">
                    <label class="form-label">Fecha Orden</label>
                    <input type="datetime-local" class="form-control" name="fechaOrden"
                           value="<%= esEdicion ? fechaOrdenStr : "" %>" required>
                </div>

                <div class="col-md-6">
                    <label class="form-label">Fecha Ingreso</label>
                    <input type="datetime-local" class="form-control" name="fechaIngreso"
                           value="<%= esEdicion ? fechaIngresoStr : "" %>">
                </div>
            </div>

            <hr class="my-4">
            <h5><i class="bi bi-list-ul"></i> Detalle de la Orden</h5>

            <div class="table-responsive mt-3">
                <table class="table table-bordered" id="tablaDetalle">
                    <thead>
                        <tr>
                            <th>Artículo</th>
                            <th>Precio</th>
                            <th>Cantidad</th>
                            <th>Estado</th>
                            <th width="50">Acción</th>
                        </tr>
                    </thead>
                    <tbody id="detalleBody">
                        <%
                            List<OrdenDetalle> detalles = esEdicion ? orden.getDetalles() : null;
                            if (detalles != null && !detalles.isEmpty()) {
                                for (OrdenDetalle d : detalles) {
                        %>
                        <tr>
                            <td>
                                <select class="form-select" name="codArticulo[]" required>
                                    <option value="">Seleccione...</option>
                                    <%
                                        for (Articulo a : listaArticulos) {
                                            boolean seleccionado = (d.getCodArticulo() == a.getCodArticulo());
                                    %>
                                    <option value="<%= a.getCodArticulo() %>" <%= seleccionado ? "selected" : "" %>>
                                        <%= a.getDescripcionArticulo() %>
                                    </option>
                                    <%
                                        }
                                    %>
                                </select>
                            </td>
                            <td>
                                <input type="number" step="0.01" class="form-control" name="precioCompra[]"
                                       value="<%= d.getPrecioCompra() %>" required>
                            </td>
                            <td>
                                <input type="number" class="form-control" name="cantidadSolicitada[]"
                                       value="<%= d.getCantidadSolicitada() %>" required>
                            </td>
                            <td>
                                <select class="form-select" name="estadoDetalle[]">
                                    <option value="Pendiente" <%= "Pendiente".equals(d.getEstado()) ? "selected" : "" %>>Pendiente</option>
                                    <option value="Completo" <%= "Completo".equals(d.getEstado()) ? "selected" : "" %>>Completo</option>
                                    <option value="Parcial" <%= "Parcial".equals(d.getEstado()) ? "selected" : "" %>>Parcial</option>
                                </select>
                            </td>
                            <td>
                                <button type="button" class="btn btn-sm btn-outline-danger"
                                        onclick="eliminarFila(this)">
                                    <i class="bi bi-trash"></i>
                                </button>
                            </td>
                        </tr>
                        <%
                                }
                            } else {
                        %>
                        <!-- Fila por defecto para nueva orden -->
                        <tr>
                            <td>
                                <select class="form-select" name="codArticulo[]" required>
                                    <option value="">Seleccione...</option>
                                    <%
                                        if (listaArticulos != null) {
                                            for (Articulo a : listaArticulos) {
                                    %>
                                    <option value="<%= a.getCodArticulo() %>">
                                        <%= a.getDescripcionArticulo() %>
                                    </option>
                                    <%
                                            }
                                        }
                                    %>
                                </select>
                            </td>
                            <td>
                                <input type="number" step="0.01" class="form-control" name="precioCompra[]" required>
                            </td>
                            <td>
                                <input type="number" class="form-control" name="cantidadSolicitada[]" required>
                            </td>
                            <td>
                                <select class="form-select" name="estadoDetalle[]">
                                    <option value="Pendiente">Pendiente</option>
                                    <option value="Completo">Completo</option>
                                    <option value="Parcial">Parcial</option>
                                </select>
                            </td>
                            <td>
                                <button type="button" class="btn btn-sm btn-outline-danger"
                                        onclick="eliminarFila(this)">
                                    <i class="bi bi-trash"></i>
                                </button>
                            </td>
                        </tr>
                        <% } %>
                    </tbody>
                </table>
            </div>

            <div class="mt-3">
                <button type="button" class="btn btn-outline-primary" onclick="agregarFila()">
                    <i class="bi bi-plus-circle"></i> Agregar Artículo
                </button>
            </div>

            <div class="mt-4">
                <button type="submit" class="btn btn-success">
                    <i class="bi bi-check-circle"></i> Guardar
                </button>
                <a href="${pageContext.request.contextPath}/compras" class="btn btn-secondary">
                    <i class="bi bi-x-circle"></i> Cancelar
                </a>
            </div>
        </form>
    </div>
</div>

<script>
    function agregarFila() {
        const tbody = document.getElementById('detalleBody');
        const filas = tbody.getElementsByTagName('tr');
        const ultimaFila = filas[filas.length - 1];
        
        // Clonar la última fila
        const nuevaFila = ultimaFila.cloneNode(true);
        
        // Limpiar los valores de los inputs
        const inputs = nuevaFila.getElementsByTagName('input');
        for (let i = 0; i < inputs.length; i++) {
            inputs[i].value = '';
        }
        
        // Seleccionar la primera opción en los selects
        const selects = nuevaFila.getElementsByTagName('select');
        for (let i = 0; i < selects.length; i++) {
            selects[i].selectedIndex = 0;
        }
        
        tbody.appendChild(nuevaFila);
    }

    function eliminarFila(boton) {
        const tbody = document.getElementById('detalleBody');
        const filas = tbody.getElementsByTagName('tr');
        
        // No eliminar si solo queda una fila
        if (filas.length <= 1) {
            alert('Debe haber al menos un artículo en la orden.');
            return;
        }
        
        const fila = boton.closest('tr');
        fila.remove();
    }
</script>

<%@ include file="../includes/footer.jsp" %>