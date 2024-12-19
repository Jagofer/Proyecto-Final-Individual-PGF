package es.santander.ascender;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ProductoGestion {

    private Map<Long, Producto> productos = new HashMap<>();

    public ProductoGestion() {
        productos.put(1L, new Producto(1, "Producto A", "Descripción A", 100.0f, 10));
        productos.put(2L, new Producto(2, "Producto B", "Descripción B", 150.0f, 0));
    }

    // 1. Obtener un producto por ID
    public Producto getProductoPorId(long id) {
        if (!productos.containsKey(id)) {
            System.out.println("Producto no encontrado.");
            return null;
        }
        return productos.get(id);
    }

    // 2. Obtener todos los productos
    public Collection<Producto> getTodosLosProductos() {
        return productos.values();
    }

    // 3. Crear un nuevo producto
    public Producto crearProducto(Producto producto) {
        long maxId = productos.keySet().stream().mapToLong(k -> k).max().orElse(0);
        producto.setId(maxId + 1);
        productos.put(producto.getId(), producto);

        // System.out.println("Producto creado: " + producto.getNombre());
        return producto;
    }

    // 4. Actualizar un producto existente
    public Producto actualizarProducto(Long id, Producto productoActualizado) {
        if (!productos.containsKey(id)) { // Verificamos si el ID existe
            System.out.println("Producto no encontrado. No se puede actualizar.");
            return null; // Retornamos null si no existe el producto
        }

        // Obtenemos el producto existente y actualizamos sus atributos
        Producto productoExistente = productos.get(id);
        productoExistente.setNombre(productoActualizado.getNombre());
        productoExistente.setDescripcion(productoActualizado.getDescripcion());
        productoExistente.setPrecio(productoActualizado.getPrecio());
        productoExistente.setCantidad(productoActualizado.getCantidad());

        System.out.println("Producto actualizado: " + productoExistente.getNombre());
        return productoExistente;
    }

    // 5. Eliminar un producto
    public boolean eliminarProducto(Long id) {
        Producto productoExistente = productos.get(id);
        if (productoExistente == null) {
            System.out.println("Producto no encontrado.");
            return false;
        }
        productos.remove(id);
        System.out.println("Producto eliminado con ID: " + id);
        return true;
    }

    // 6. Comprar un producto (disminuye la cantidad según la compra)
    public String comprarProducto(Long id, int cantidad) {
        Producto producto = productos.get(id);

        if (producto == null) {
            return "Producto no encontrado.";
        }

        if (producto.getCantidad() < cantidad) {
            return "Stock insuficiente. Disponible: " + producto.getCantidad();
        }

        // Calcular el precio total
        float precioTotal = producto.getPrecio() * cantidad;

        // Actualizar el stock
        producto.setCantidad(producto.getCantidad() - cantidad);

        return "Compra realizada con éxito. Producto: " + producto.getNombre() +
                ", Cantidad: " + cantidad +
                ", Precio total: " + precioTotal +
                ", Stock restante: " + producto.getCantidad();
    }

    // 7. Reponer unidades de un producto (aumenta cantidad de stock)
    public String reponerProducto(Long id, int cantidad) {
        Producto producto = productos.get(id);

        if (producto == null) {
            return "Producto no encontrado.";
        }

        // Actualizar el stock
        producto.setCantidad(producto.getCantidad() + cantidad);

        return "Reponer stock repuesto  con éxito. Producto: " + producto.getNombre() +
                ", Cantidad: " + cantidad +
                ", Stock disponible: " + producto.getCantidad();
    }

    // Métodos auxiliares para depuración
    public Map<Long, Producto> getProductos() {
        return productos;
    }

    public void setProductos(Map<Long, Producto> productos) {
        this.productos = productos;
    }

}