package es.santander.ascender;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ProductoGestionTest {

    private ProductoGestion gestion;
    private Producto producto;

    @BeforeEach
    public void setup() {
        // Inicializamos ProductoGestion y Producto antes de cada test
        gestion = new ProductoGestion();
        producto = new Producto();
    }

    @Test
    public void testGetProductoPorId_ProductoExistente() {

        producto = gestion.getProductoPorId(1L);

        assertNotNull(producto, "El producto no debería ser nulo.");
        assertEquals(1L, producto.getId(), "El ID del producto no coincide.");
        assertEquals("Producto A", producto.getNombre(), "El nombre del producto no coincide.");
    }

    @Test
    public void testGetProductoPorId_ProductoNoExistente() {

        producto = gestion.getProductoPorId(99L); // ID que no existe

        assertNull(producto, "El producto debería ser nulo si no existe.");
    }

    @Test
    public void testGetTodosLosProductos() {

        Collection<Producto> productos = gestion.getTodosLosProductos();

        assertNotNull(productos, "La colección de productos no debería ser nula.");
        assertEquals(2, productos.size(), "La cantidad de productos iniciales debería ser 2.");

        // Verificar que contiene productos específicos
        boolean contieneProductoA = productos.stream()
                .anyMatch(p -> p.getNombre().equals("Producto A") && p.getId() == 1L);
        boolean contieneProductoB = productos.stream()
                .anyMatch(p -> p.getNombre().equals("Producto B") && p.getId() == 2L);

        assertTrue(contieneProductoA, "Debería contener el producto 'Producto A'.");
        assertTrue(contieneProductoB, "Debería contener el producto 'Producto B'.");
    }

    @Test
    public void testCrearProducto() {

        Producto nuevoProducto = new Producto(0, "Producto C", "Descripción C", 200.0f, 20);

        Producto productoCreado = gestion.crearProducto(nuevoProducto);

        assertNotNull(productoCreado, "El producto creado no debería ser nulo.");
        assertEquals(3, productoCreado.getId(), "El ID del producto creado debería ser 3.");
        assertEquals("Producto C", productoCreado.getNombre(), "El nombre del producto creado no coincide.");
        assertEquals("Descripción C", productoCreado.getDescripcion(),
                "La descripción del producto creado no coincide.");
        assertEquals(200.0f, productoCreado.getPrecio(), 0.01, "El precio del producto creado no coincide.");
        assertEquals(20, productoCreado.getCantidad(), "La cantidad del producto creado no coincide.");

        // Verificar que el producto está en el mapa
        assertTrue(gestion.getProductos().containsKey(3L), "El mapa debería contener el producto con ID 3.");
    }

    @Test
    public void testActualizarProducto_ProductoExistente() {

        Producto productoOriginal = new Producto(1, "Producto A", "Descripción A", 100.0f, 10);
        gestion.getProductos().put(1L, productoOriginal); // Agregar el producto manualmente

        Producto productoActualizado = new Producto(1L, "Producto A Actualizado", "Descripción Actualizada", 120.0f,
                15);

        Producto productoResultante = gestion.actualizarProducto(1L, productoActualizado);

        assertNotNull(productoResultante, "El producto actualizado no debería ser nulo.");
        assertEquals(1L, productoResultante.getId(), "El ID del producto no debería cambiar.");
        assertEquals("Producto A Actualizado", productoResultante.getNombre(), "El nombre del producto no coincide.");
        assertEquals("Descripción Actualizada", productoResultante.getDescripcion(), "La descripción no coincide.");
        assertEquals(120.0f, productoResultante.getPrecio(), 0.01, "El precio no coincide.");
        assertEquals(15, productoResultante.getCantidad(), "La cantidad no coincide.");
    }

    @Test
    public void testActualizarProducto_ProductoNoExistente() {

        Producto productoResultante = gestion.actualizarProducto(99L,
                new Producto(99L, "Producto No Existente", "Descripción", 50.0f, 5));

        assertNull(productoResultante, "El producto no encontrado debería retornar null.");
    }

    @Test
    public void testComprarProducto_Exito() {

        Producto productoOriginal = new Producto(1, "Producto A", "Descripción A", 100.0f, 10);
        gestion.getProductos().put(1L, productoOriginal); // Agregar el producto manualmente
        int cantidadComprar = 5;

        String resultado = gestion.comprarProducto(1L, cantidadComprar);

        assertNotNull(resultado, "El resultado de la compra no debería ser nulo.");
        assertEquals(
                "Compra realizada con éxito. Producto: Producto A, Cantidad: 5, Precio total: 500.0, Stock restante: 5",
                resultado);
        assertEquals(5, productoOriginal.getCantidad(), "El stock restante no coincide.");
    }

    @Test
    public void testComprarProducto_SinProducto() {

        String resultado = gestion.comprarProducto(99L, 5);

        assertEquals("Producto no encontrado.", resultado, "Debería retornar 'Producto no encontrado'.");
    }

    @Test
    public void testComprarProducto_SinStockSuficiente() {

        Producto productoOriginal = new Producto(2, "Producto B", "Descripción B", 150.0f, 0);
        gestion.getProductos().put(2L, productoOriginal); // Agregar el producto con stock 0

        String resultado = gestion.comprarProducto(2L, 5);

        assertEquals("Stock insuficiente. Disponible: 0", resultado, "Debería retornar 'Stock insuficiente'.");
    }

    @Test
    public void testEliminarProducto() {
       
        Producto productoOriginal = new Producto(1, "Producto A", "Descripción A", 100.0f, 10);
        gestion.getProductos().put(1L, productoOriginal); // Agregar el producto manualmente

         
        boolean resultado = gestion.eliminarProducto(1L);

         
        assertTrue(resultado, "El producto debería ser eliminado correctamente.");
        assertNull(gestion.getProductos().get(1L), "El producto con ID 1 debería haber sido eliminado.");
    }

     

    @Test
    void testReponerProducto() {

    }

     
   
}
