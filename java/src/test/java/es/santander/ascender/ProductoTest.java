package es.santander.ascender;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ProductoTest {

    // Test del constructor y getter para asegurar la correcta creación de un producto
    @Test
    public void testConstructorYGetter() {
        Producto producto = new Producto(1, "Producto A", "Descripción A", 100.0f, 10);
        
        assertEquals(1, producto.getId(), "El ID del producto debería ser 1.");
        assertEquals("Producto A", producto.getNombre(), "El nombre del producto debería ser 'Producto A'.");
        assertEquals("Descripción A", producto.getDescripcion(), "La descripción del producto debería ser 'Descripción A'.");
        assertEquals(100.0f, producto.getPrecio(), "El precio del producto debería ser 100.0f.");
        assertEquals(10, producto.getCantidad(), "La cantidad del producto debería ser 10.");
    }

    // Test para el setter de precio con valor válido
    @Test
    public void testSetPrecioValido() {
        Producto producto = new Producto();
        producto.setPrecio(200.0f);

        assertEquals(200.0f, producto.getPrecio(), "El precio debería ser 200.0f.");
    }

    // Test para el setter de precio con valor negativo, debería lanzar excepción
    @Test
    public void testSetPrecioInvalido() {
        Producto producto = new Producto();
        
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            producto.setPrecio(-50.0f);
        });

        assertEquals("El precio no puede ser negativo.", exception.getMessage(), "El precio negativo debería lanzar una excepción.");
    }

    // Test para el setter de cantidad con valor válido
    @Test
    public void testSetCantidadValido() {
        Producto producto = new Producto();
        producto.setCantidad(20);

        assertEquals(20, producto.getCantidad(), "La cantidad debería ser 20.");
    }

    // Test para el setter de cantidad con valor negativo, debería lanzar excepción
    @Test
    public void testSetCantidadInvalido() {
        Producto producto = new Producto();
        
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            producto.setCantidad(-5);
        });

        assertEquals("La cantidad no puede ser negativa.", exception.getMessage(), "La cantidad negativa debería lanzar una excepción.");
    }

    // Test para el método toString()
    @Test
    public void testToString() {
        Producto producto = new Producto(1, "Producto A", "Descripción A", 100.0f, 10);
        String expectedToString = "Producto{id=1, nombre='Producto A', descripcion='Descripción A', cantidad=10, precio=100.0}";

        assertEquals(expectedToString, producto.toString(), "El método toString no devuelve el valor esperado.");
    }
}
