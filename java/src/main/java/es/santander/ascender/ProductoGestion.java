package es.santander.ascender;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ProductoGestion {

    private Map<Long, Producto> productos = new HashMap<>();

    public ProductoGestion() {
        productos.put(1L, new Producto(1, "Producto A", "Descripción A", 100.0f, 10));
        productos.put(2L, new Producto(2L, "Producto B", "Descripción B", 150.0f, 0));
    }

    // 1. Obtener un producto por ID
    public Producto getProductoPorId(long id) {
        Producto producto = productos.get(id);
        if (producto == null) {
            System.out.println("Producto no encontrado con ID: " + id);
        }
        return producto;
    }

    // 2. Obtener todos los productos
    public Collection<Producto> getTodosLosProductos() {
        return productos.values();
    }

    // 3. Crear un nuevo producto
    public Producto crearProducto(Producto producto) {
        if (producto == null || producto.getNombre() == null || producto.getDescripcion() == null) {
            throw new IllegalArgumentException("El producto debe tener un nombre y una descripción válidos.");
        }

        long maxId = productos.keySet().stream().mapToLong(k -> k).max().orElse(0);
        producto.setId(maxId + 1);
        productos.put(producto.getId(), producto);

        return producto;
    }

    // 4. Actualizar un producto existente
    public Producto actualizarProducto(Long id, Producto productoActualizado) {
        if (productoActualizado == null || productoActualizado.getNombre() == null || productoActualizado.getDescripcion() == null) {
            throw new IllegalArgumentException("El producto a actualizar debe tener un nombre y una descripción válidos.");
        }

        Producto productoExistente = productos.get(id);
        if (productoExistente == null) {
            System.out.println("Producto con ID " + id + " no encontrado para actualizar.");
            return null; // Retornamos null si no existe el producto
        }

        productoExistente.setNombre(productoActualizado.getNombre());
        productoExistente.setDescripcion(productoActualizado.getDescripcion());
        productoExistente.setPrecio(productoActualizado.getPrecio());
        productoExistente.setCantidad(productoActualizado.getCantidad());

        System.out.println("Producto actualizado con éxito: " + productoExistente.getNombre());
        return productoExistente;
    }

    // 5. Eliminar un producto
    public boolean eliminarProducto(Long id) {
        Producto productoExistente = productos.get(id);
        if (productoExistente == null) {
            System.out.println("Producto con ID " + id + " no encontrado.");
            return false;
        }
        productos.remove(id);
        System.out.println("Producto eliminado con éxito. ID: " + id);
        return true;
    }

    // 6. Comprar un producto (disminuye la cantidad según la compra)
    public String comprarProducto(Long id, int cantidad) {
        Producto producto = productos.get(id);

        if (producto == null) {
            return "Producto con ID " + id + " no encontrado.";
        }

        if (producto.getCantidad() < cantidad) {
            return "Stock insuficiente para el producto " + producto.getNombre() + ". Disponible: " + producto.getCantidad();
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
            return "Producto con ID " + id + " no encontrado.";
        }

        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad a reponer debe ser mayor que 0.");
        }

        // Actualizar el stock
        producto.setCantidad(producto.getCantidad() + cantidad);

        return "Reposición de stock realizada con éxito. Producto: " + producto.getNombre() +
               ", Cantidad añadida: " + cantidad +
               ", Stock disponible: " + producto.getCantidad();
    }

    // Métodos auxiliares para depuración y control
    public Map<Long, Producto> getProductos() {
        return productos;
    }

    public void setProductos(Map<Long, Producto> productos) {
        this.productos = productos;
    }
}
