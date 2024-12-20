package es.santander.ascender;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.Test;

public class AppTest {

    // Simula la entrada del usuario
    private void simularEntrada(String entrada) {
        System.setIn(new ByteArrayInputStream(entrada.getBytes()));
    }

    // Captura la salida de la consola
    private String capturarSalida() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(baos);
        System.setOut(printStream);
        return baos.toString();
    }

    @Test
    public void testMostrarMenu() {
        // Simulamos la entrada para que la aplicación muestre el menú
        simularEntrada("0\n"); // Elige la opción de salir
        String salida = capturarSalida();

        // Comprobamos que el menú es mostrado correctamente
        assertTrue(salida.contains("*** Gestión de Productos ***"));
        assertTrue(salida.contains("1. Listar todos los productos"));
        assertTrue(salida.contains("2. Ver producto por ID"));
        assertTrue(salida.contains("3. Crear un nuevo producto"));
        assertTrue(salida.contains("4. Actualizar un producto"));
        assertTrue(salida.contains("5. Eliminar un producto"));
        assertTrue(salida.contains("6. Comprar un producto"));
        assertTrue(salida.contains("7. Reponer un producto"));
        assertTrue(salida.contains("0. Salir"));
    }

    @Test
    public void testOpcionSalir() {
        // Simulamos la entrada de una opción de salida
        simularEntrada("0\n");
        
        // Ejecutamos el método main de la clase App (simulando una ejecución completa)
        String salida = capturarSalida();
        
        // Comprobamos que la salida contiene el mensaje de salida
        assertTrue(salida.contains("Saliendo del sistema. ¡Hasta pronto!"));
    }

    @Test
    public void testOpcionListaProductos() {
        // Simulamos la entrada de la opción 1 (Listar productos)
        simularEntrada("1\n0\n"); // Seleccionamos opción 1 para listar y luego 0 para salir
        
        // Capturamos la salida
        String salida = capturarSalida();
        
        // Verificamos que la salida contiene el listado de productos
        assertTrue(salida.contains("=== Listado de Productos ==="));
        assertTrue(salida.contains("Producto A")); // Producto de ejemplo en la gestión
    }

    @Test
    public void testOpcionVerProductoPorId() {
        // Simulamos la entrada para ver un producto por ID
        simularEntrada("2\n1\n0\n"); // Opción 2 (Ver por ID), ID 1, y luego salir

        // Capturamos la salida
        String salida = capturarSalida();

        // Verificamos que la salida contiene la información del producto con ID 1
        assertTrue(salida.contains("=== Ver Producto ==="));
        assertTrue(salida.contains("Producto A")); // Nombre del producto de ejemplo
    }

    @Test
    public void testOpcionCrearProducto() {
        // Simulamos la entrada para crear un nuevo producto
        simularEntrada("3\nNuevo Producto\nDescripción del Producto\n100.0\n10\n0\n");

        // Capturamos la salida
        String salida = capturarSalida();

        // Verificamos que la salida contiene el mensaje de producto creado
        assertTrue(salida.contains("Producto creado:"));
        assertTrue(salida.contains("Nuevo Producto")); // El nombre del nuevo producto
    }

    @Test
    public void testOpcionActualizarProducto() {
        // Simulamos la entrada para actualizar un producto (asumiendo que el producto con ID 1 existe)
        simularEntrada("4\n1\nProducto Actualizado\nNueva Descripción\n200.0\n15\n0\n");

        // Capturamos la salida
        String salida = capturarSalida();

        // Verificamos que la salida contiene el mensaje de producto actualizado
        assertTrue(salida.contains("Producto actualizado:"));
        assertTrue(salida.contains("Producto Actualizado")); // El nuevo nombre del producto
    }

    @Test
    public void testOpcionEliminarProducto() {
        // Simulamos la entrada para eliminar un producto (ID 1)
        simularEntrada("5\n1\n0\n");

        // Capturamos la salida
        String salida = capturarSalida();

        // Verificamos que la salida contiene el mensaje de producto eliminado
        assertTrue(salida.contains("Producto eliminado con éxito."));
    }

    @Test
    public void testOpcionComprarProducto() {
        // Simulamos la entrada para comprar un producto (ID 1)
        simularEntrada("6\n1\n5\n0\n");

        // Capturamos la salida
        String salida = capturarSalida();

        // Verificamos que la salida contiene el mensaje de compra realizada
        assertTrue(salida.contains("Compra realizada con éxito"));
    }

    @Test
    public void testOpcionReponerProducto() {
        // Simulamos la entrada para reponer un producto (ID 1)
        simularEntrada("7\n1\n5\n0\n");

        // Capturamos la salida
        String salida = capturarSalida();

        // Verificamos que la salida contiene el mensaje de reposición de stock
        assertTrue(salida.contains("Reposición de stock realizada con éxito"));
    }
}
