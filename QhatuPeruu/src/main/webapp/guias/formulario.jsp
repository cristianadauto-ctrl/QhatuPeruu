<%-- 
    Document   : formulario
    Created on : 18 jul. 2026, 12:13:26
    Author     : USER
--%>

<%@ page import="com.mycompany.qhatuperuu.modelo.GuiaEnvio" %>
<%@ page import="com.mycompany.qhatuperuu.modelo.GuiaDetalle" %>
<%@ page import="com.mycompany.qhatuperuu.modelo.Tienda" %>
<%@ page import="com.mycompany.qhatuperuu.modelo.Transportista" %>
<%@ page import="com.mycompany.qhatuperuu.modelo.Articulo" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ include file="../includes/header.jsp" %>

<%
    GuiaEnvio guia = (GuiaEnvio) request.getAttribute("guia");
    boolean esEdicion = (guia != null);
    Integer siguienteNumGuia = (Integer) request.getAttribute("siguienteNumGuia");
    List<Tienda> listaTiendas = (List<Tienda>) request.getAttribute("listaTiendas");
    List<Transportista> listaTransportistas = (List<Transportista>) request.getAttribute("listaTransportistas");
    List<Articulo> listaArticulos = (List<Articulo>) request.getAttribute("listaArticulos");
    String mensajeError = (String) request.getAttribute("mensajeError");
    
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
    String fechaSalidaStr = "";
    
    if (esEdicion) {
        fechaSalidaStr = guia.getFechaSalida().format(formatter);
    }
    
    int numGuia = esEdicion ? guia.getNumGuia() : (siguienteNumGuia != null ? siguienteNumGuia : 1);
%>

<h2><i class="bi bi-<%= esEdicion ? "pencil-square" : "plus-circle" %>"></i> 
    <%= esEdicion ? "Editar Guía de Envío #" + numGuia : "Nueva Guía de Envío" %>
</h2>

<% if (mensajeError != null) { %>
    <div class="alert alert-danger"><i class="bi bi-exclamation-triangle"></i> <%= mensajeError %></div>
<% } %>

<div class="card">
    <div class="card-body">
        <form action="${pageContext.request.contextPath}/guias" method="post" id="formGuia">
            <input type="hidden" name="accion" value="guardar">
            <input type="hidden" name="numGuia" value="<%= numGuia %>">

            <div class="row g-3">
                <div class="col-md-3">
                    <label class="form-label">N° Guía</label>
                    <input type="text" class="form-control" value="<%= numGuia %>" disabled>
                </div>

                <div class="col-md-3">
                    <label class="form-label">Fecha Salida</label>
                    <input type="datetime-local" class="form-control" name="fechaSalida"
                           value="<%= esEdicion ? fechaSalidaStr : "" %>" required>
                </div>

                <div class="col-md-3">
                    <label class="form-label">Tienda</label>
                    <select class="form-select" name="codTienda" required>
                        <option value="">Seleccione...</option>
                        <%
                            if (listaTiendas != null) {
                                for (Tienda t : listaTiendas) {
                                    boolean seleccionado = esEdicion && guia.getCodTienda() == t.getCodTienda();
                        %>
                        <option value="<%= t.getCodTienda() %>" <%= seleccionado ? "selected" : "" %>>
                            <%= t.getDireccion() != null ? t.getDireccion() : "Tienda " + t.getCodTienda() %>
                            <%= t.getDistrito() != null ? "(" + t.getDistrito() + ")" : "" %>
                        </option>
                        <%
                                }
                            }
                        %>
                    </select>
                </div>

                <div class="col-md-3">
                    <label class="form-label">Transportista</label>
                    <select class="form-select" name="codTransportista" required>
                        <option value="">Seleccione...</option>
                        <%
                            if (listaTransportistas != null) {
                                for (Transportista t : listaTransportistas) {
                                    boolean seleccionado = esEdicion && guia.getCodTransportista() == t.getCodTransportista();
                        %>
                        <option value="<%= t.getCodTransportista() %>" <%= seleccionado ? "selected" : "" %>>
                            <%= t.getNomTransportista() %>
                        </option>
                        <%
                                }
                            }
                        %>
                    </select>
                </div>
            </div>

            <hr class="my-4">
            <h5><i class="bi bi-list-ul"></i> Detalle de la Guía</h5>

            <div class="table-responsive mt-3">
                <table class="table table-bordered" id="tablaDetalle">
                    <thead>
                        <tr>
                            <th>Artículo</th>
                            <th>Precio Venta</th>
                            <th>Cantidad</th>
                            <th width="50">Acción</th>
                        </tr>
                    </thead>
                    <tbody id="detalleBody">
                        <%
                            List<GuiaDetalle> detalles = esEdicion ? guia.getDetalles() : null;
                            if (detalles != null && !detalles.isEmpty()) {
                                for (GuiaDetalle d : detalles) {
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
                                <input type="number" step="0.01" class="form-control" name="precioVenta[]"
                                       value="<%= d.getPrecioVenta() %>" required>
                            </td>
                            <td>
                                <input type="number" class="form-control" name="cantidadEnviada[]"
                                       value="<%= d.getCantidadEnviada() %>" required>
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
                                <input type="number" step="0.01" class="form-control" name="precioVenta[]" required>
                            </td>
                            <td>
                                <input type="number" class="form-control" name="cantidadEnviada[]" required>
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
                <a href="${pageContext.request.contextPath}/guias" class="btn btn-secondary">
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
        
        const nuevaFila = ultimaFila.cloneNode(true);
        
        const inputs = nuevaFila.getElementsByTagName('input');
        for (let i = 0; i < inputs.length; i++) {
            inputs[i].value = '';
        }
        
        const selects = nuevaFila.getElementsByTagName('select');
        for (let i = 0; i < selects.length; i++) {
            selects[i].selectedIndex = 0;
        }
        
        tbody.appendChild(nuevaFila);
    }

    function eliminarFila(boton) {
        const tbody = document.getElementById('detalleBody');
        const filas = tbody.getElementsByTagName('tr');
        
        if (filas.length <= 1) {
            alert('Debe haber al menos un artículo en la guía.');
            return;
        }
        
        const fila = boton.closest('tr');
        fila.remove();
    }
</script>

<%@ include file="../includes/footer.jsp" %>