package es.santander.ascender.individual.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import es.santander.ascender.individual.model.Producto;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/productos")
public class ProductoController {

    // Uso de un HashMap como almacenamiento simulado de productos
    private Map<Long, Producto> productos = new HashMap<>();

    public ProductoController() {
        // Inicialización de productos con datos predeterminados
        productos.put(1L, new Producto(1, "Patatas", "Tubérculo", 0.3f, 10));
        productos.put(2L, new Producto(2, "Tomate", "Fruta antes de comer, Verdura después", 0.50f, 10));
    }

    @GetMapping("/{id}")
    public HttpEntity<Producto> get(@PathVariable("id") long id) {
        // Se utiliza Optional para evitar NPE si no se encuentra el producto
        return productos.containsKey(id) ? 
                ResponseEntity.ok().body(productos.get(id)) : 
                ResponseEntity.notFound().build();
    }

    @GetMapping
    public HttpEntity<Collection<Producto>> getAll() {
        // Se devuelve una colección de productos
        return ResponseEntity.ok().body(productos.values());
    }

    @PostMapping
    public ResponseEntity<Producto> create(@RequestBody Producto producto) {
        // Asignación de ID mediante un cálculo simple del ID máximo
        long newId = productos.keySet().stream().max(Long::compare).orElse(0L) + 1;
        producto.setId(newId);
        
        // Se agrega el nuevo producto al mapa
        productos.put(newId, producto);

        // Respuesta con el producto creado
        return ResponseEntity.status(HttpStatus.CREATED).body(producto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> update(@PathVariable Long id, @Valid @RequestBody Producto productoActualizado) {
        // Verificación de la existencia del producto
        Producto productoExistente = productos.get(id);

        if (productoExistente == null) {
            return ResponseEntity.notFound().build();
        }

        // Actualización de los campos del producto
        productoExistente.setNombre(productoActualizado.getNombre());
        productoExistente.setDescripcion(productoActualizado.getDescripcion());
        productoExistente.setPrecio(productoActualizado.getPrecio());
        productoExistente.setCantidad(productoActualizado.getCantidad());

        // Respuesta con el producto actualizado
        return ResponseEntity.ok(productoExistente);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        // Verificación de la existencia del producto
        if (productos.remove(id) == null) {
            return ResponseEntity.notFound().build();
        }

        // Respuesta de éxito tras la eliminación
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/compra")
    public ResponseEntity<String> comprarProducto(@PathVariable Long id) {
        // Verificación de la existencia del producto
        Producto producto = productos.get(id);

        if (producto == null) {
            return ResponseEntity.notFound().build();
        }

        // Verificación del stock disponible
        if (producto.getCantidad() <= 0) {
            return ResponseEntity.badRequest().body("Producto sin stock disponible.");
        }

        // Reducción de la cantidad del producto y respuesta de éxito
        producto.setCantidad(producto.getCantidad() - 1);
        return ResponseEntity.ok("Compra realizada con éxito. Producto: " + producto.getNombre());
    }

    // Métodos de acceso a los productos
    public Map<Long, Producto> getProductos() {
        return productos;
    }

    public void setProductos(Map<Long, Producto> productos) {
        this.productos = productos;
    }
}
