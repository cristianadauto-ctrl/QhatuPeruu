<%-- 
    Document   : lista
    Created on : 18 jul. 2026, 09:58:07
    Author     : USER
--%>

<%@ page import="java.util.List" %>
<%@ page import="com.mycompany.qhatuperuu.modelo.Articulo" %>

<%@ include file="../includes/header.jsp" %>

<div class="d-flex justify-content-between align-items-center mb-3">

    <h2>

        <i class="bi bi-box-seam"></i>

        Artículos

    </h2>

    <a href="${pageContext.request.contextPath}/articulos?accion=nuevo"
       class="btn btn-primary">

        <i class="bi bi-plus-circle"></i>

        Nuevo Artículo

    </a>

</div>

<%

String mensajeError=(String)request.getAttribute("mensajeError");

%>

<% if(mensajeError!=null){ %>

<div class="alert alert-danger">

    <i class="bi bi-exclamation-triangle"></i>

    <%= mensajeError %>

</div>

<% } %>

<div class="card">

<div class="card-body">

<table class="table table-hover align-middle">

<thead>

<tr>

<th>Línea</th>

<th>Proveedor</th>

<th>Artículo</th>

<th>Presentación</th>

<th>Precio</th>

<th>Stock</th>

<th>Estado</th>

<th class="text-center">Acciones</th>

</tr>

</thead>

<tbody>

<%

List<Articulo> lista=(List<Articulo>)request.getAttribute("listaArticulos");

if(lista!=null && !lista.isEmpty()){

for(Articulo a:lista){

%>

<tr>

<td><%= a.getNomLinea()%></td>

<td><%= a.getNomProveedor()%></td>

<td><%= a.getDescripcionArticulo()%></td>

<td><%= a.getPresentacion()%></td>

<td>S/. <%= a.getPrecioProveedor()%></td>

<td>

<%= a.getStockActual()%>

</td>

<td>

<% if(a.isDescontinuado()){ %>

<span class="badge bg-danger">

Descontinuado

</span>

<% }else{ %>

<span class="badge bg-success">

Activo

</span>

<% } %>

</td>

<td class="text-center">

<a class="btn btn-sm btn-outline-warning"

href="${pageContext.request.contextPath}/articulos?accion=editar&id=<%=a.getCodArticulo()%>">

<i class="bi bi-pencil"></i>

</a>

<button class="btn btn-sm btn-outline-danger"

onclick="confirmarEliminacion(<%=a.getCodArticulo()%>,'<%=a.getDescripcionArticulo()%>')">

<i class="bi bi-trash"></i>

</button>

</td>

</tr>

<%

}

}else{

%>

<tr>

<td colspan="8" class="text-center text-muted">

No existen artículos registrados.

</td>

</tr>

<%

}

%>

</tbody>

</table>

</div>

</div>

<form id="formEliminar"
      action="${pageContext.request.contextPath}/articulos"
      method="post"
      style="display:none;">

<input type="hidden"
       name="accion"
       value="eliminar">

<input type="hidden"
       id="idEliminar"
       name="id">

</form>

<script>

function confirmarEliminacion(id,nombre){

if(confirm('¿Eliminar el artículo "'+nombre+'"?')){

document.getElementById('idEliminar').value=id;

document.getElementById('formEliminar').submit();

}

}

</script>

<%@ include file="../includes/footer.jsp" %>