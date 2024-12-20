$.get('http://localhost:1234/api/productos'); // Verificación inicial de la API

const EstadoPagina = {
    CARGANDO: 'Cargando',
    MOSTRANDO: 'Mostrando',
    EDITANDO: 'Editando',
    BORRANDO: 'Borrando',
    INICIAL: 'Inicial',
};

let estado = EstadoPagina.INICIAL;

$(function () {
    const API_URL = "http://localhost:1234/api/productos";

    // Cargar y mostrar productos desde la API
    function cargarProductos() {
        estado = EstadoPagina.CARGANDO;

        $.get(API_URL)
            .done(function (data) {
                estado = EstadoPagina.MOSTRANDO;
                const filasHTML = data.map(producto => `
                    <tr data-id="${producto.id}">
                        <td>${producto.nombre}</td>
                        <td>${producto.descripcion}</td>
                        <td>${producto.precio.toFixed(2)} €</td>
                        <td>${producto.cantidad}</td>
                        <td>
                            <button class="btn btn-default btn-comprar" data-id="${producto.id}">Comprar</button>
                            <button class="btn btn-default btn-modificar" data-id="${producto.id}">Modificar</button>
                            <button class="btn btn-default btn-borrar" data-id="${producto.id}">Borrar</button>
                        </td>
                    </tr>
                `).join('');
                $("#tablaProductos tbody").html(filasHTML);
            })
            .fail(function () {
                estado = EstadoPagina.INICIAL;
                alert("Error al cargar productos.");
            });
    }

    // Comprar producto
    function comprarProducto(id) {
        $.ajax({
            url: `${API_URL}/${id}/compra`,
            method: "POST",
            success: function () {
                alert("Producto comprado con éxito.");
                cargarProductos();
            },
            error: function () {
                alert("Error al realizar la compra.");
            },
        });
    }

    // Crear nuevo producto
    function crearProducto() {
        estado = EstadoPagina.EDITANDO;
        $("#detalle").show();
        $("#registroSeleccionado").val("");
        $("#formDetalle")[0].reset();
    }

    // Guardar producto (nuevo o actualizado)
    function guardarProducto() {
        const id = $("#registroSeleccionado").val();
        const producto = {
            nombre: $("#nombre").val().trim(),
            descripcion: $("#descripcion").val().trim(),
            precio: parseFloat($("#precio").val()),
            cantidad: parseInt($("#cantidad").val(), 10),
        };

        if (!producto.nombre || !producto.descripcion || isNaN(producto.precio) || isNaN(producto.cantidad)) {
            alert("Todos los campos son obligatorios y deben tener un formato válido.");
            return;
        }

        const metodo = id ? "PUT" : "POST";
        const url = id ? `${API_URL}/${id}` : API_URL;

        $.ajax({
            url: url,
            method: metodo,
            contentType: "application/json",
            data: JSON.stringify(producto),
            success: function () {
                alert(`Producto ${id ? 'actualizado' : 'creado'} con éxito.`);
                $("#detalle").hide();
                cargarProductos();
            },
            error: function () {
                alert("Error al guardar el producto.");
            },
        });
    }

    // Borrar producto
    function borrarProducto(id) {
        if (confirm("¿Estás seguro de eliminar este producto?")) {
            $.ajax({
                url: `${API_URL}/${id}`,
                method: "DELETE",
                success: function () {
                    alert("Producto eliminado.");
                    cargarProductos();
                },
                error: function () {
                    alert("Error al eliminar el producto.");
                },
            });
        }
    }

    // Eventos de botones
    $("#botonRefrescar").click(cargarProductos);
    $("#botonNuevo").click(crearProducto);
    $("#botonGuardar").click(function (e) {
        e.preventDefault();
        guardarProducto();
    });
    $("#botonCancelar").click(function () {
        $("#detalle").hide();
        estado = EstadoPagina.INICIAL;
    });

    // Eventos de acciones en la tabla
    $("#listado").on("click", ".btn-borrar", function () {
        borrarProducto($(this).data("id"));
    });

    $("#listado").on("click", ".btn-modificar", function () {
        const id = $(this).data("id");
        estado = EstadoPagina.EDITANDO;
        $.get(`${API_URL}/${id}`, function (producto) {
            $("#registroSeleccionado").val(producto.id);
            $("#nombre").val(producto.nombre);
            $("#descripcion").val(producto.descripcion);
            $("#precio").val(producto.precio);
            $("#cantidad").val(producto.cantidad);
            $("#detalle").show();
        });
    });

    // Iniciar cargando productos
    cargarProductos();
});

