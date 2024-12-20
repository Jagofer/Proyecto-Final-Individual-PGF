$.get('http://localhost:1234/api/productos');

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

    function cargarProductos() {
        estado = EstadoPagina.CARGANDO;

        $.get(API_URL)
            .done(function (data) {
                estado = EstadoPagina.MOSTRANDO;
                console.log("Datos recibidos:", data); // Verificar respuesta de la API
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

    // Evento del botón "Comprar"
    $("#listado").on("click", ".btn-comprar", function () {
        const id = $(this).data("id");
        comprarProducto(id);
    });

    // Función para comprar un producto
    function comprarProducto(id) {
        $.ajax({
            url: `${API_URL}/${id}/compra`,
            method: "POST",
            success: function () {
                alert("Producto comprado con éxito.");
                cargarProductos();
            },
            error: function (xhr, status, error) {
                console.error("Error al comprar producto:", status, error);
                alert("Error al realizar la compra. Inténtalo de nuevo.");
            },
        });
    }

    function crearProducto() {
        estado = EstadoPagina.EDITANDO;
        $("#detalle").show();
        $("#registroSeleccionado").val("");
        $("#formDetalle")[0].reset();
    }

    function guardarProducto() {
        const id = $("#registroSeleccionado").val();
        const producto = {
            nombre: $("#nombre").val().trim(),
            descripcion: $("#descripcion").val().trim(),
            precio: parseFloat($("#precio").val()),
            cantidad: parseInt($("#cantidad").val(), 10),
        };

        // Validar los campos antes de enviar
        if (!producto.nombre || !producto.descripcion || isNaN(producto.precio) || isNaN(producto.cantidad)) {
            alert("Todos los campos son obligatorios y deben tener un formato válido.");
            return;
        }

        if (id) {
            // Actualización de producto existente (PUT)
            $.ajax({
                url: `${API_URL}/${id}`,
                method: "PUT",
                contentType: "application/json",
                data: JSON.stringify(producto),
                success: function () {
                    alert("Producto actualizado con éxito.");
                    $("#detalle").hide();
                    cargarProductos(); // Refrescar listado
                },
                error: function (xhr, status, error) {
                    console.error("Error al actualizar producto:", status, error);
                    alert("Error al actualizar el producto.");
                },
            });
        } else {
            // Creación de nuevo producto (POST)
            $.ajax({
                url: API_URL,
                method: "POST",
                contentType: "application/json",
                data: JSON.stringify(producto),
                success: function () {
                    alert("Producto creado con éxito.");
                    $("#detalle").hide();
                    cargarProductos(); // Refrescar listado
                },
                error: function (xhr, status, error) {
                    console.error("Error al crear producto:", status, error);
                    alert("Error al crear el producto.");
                },
            });
        }
    }

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

    cargarProductos(); // Cargar productos al inicio
});
