package es.santander.ascender;

import java.util.Collection;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        System.out.println("Hello World!");

        ProductoGestion productoGestion = new ProductoGestion();
        Scanner scanner = new Scanner(System.in);
        boolean salir = false;

        while (!salir) {
            mostrarMenu();
            int opcion = obtenerOpcion(scanner);
            if (opcion == -1) continue;  // Si hay un error de entrada, volvemos al inicio.

            switch (opcion) {
                case 1:
                    listarProductos(productoGestion);
                    break;
                case 2:
                    verProductoPorId(scanner, productoGestion);
                    break;
                case 3:
                    crearProducto(scanner, productoGestion);
                    break;
                case 4:
                    actualizarProducto(scanner, productoGestion);
                    break;
                case 5:
                    eliminarProducto(scanner, productoGestion);
                    break;
                case 6:
                    comprarProducto(scanner, productoGestion);
                    break;
                case 7:
                    reponerProducto(scanner, productoGestion);
                    break;
                case 0:
                    salir = true;
                    System.out.println("Saliendo del sistema. ¡Hasta pronto!");
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        }

        scanner.close();
    }

    private static void mostrarMenu() {
        System.out.println("\n*** Gestión de Productos ***");
        System.out.println("1. Listar todos los productos");
        System.out.println("2. Ver producto por ID");
        System.out.println("3. Crear un nuevo producto");
        System.out.println("4. Actualizar un producto");
        System.out.println("5. Eliminar un producto");
        System.out.println("6. Comprar un producto");
        System.out.println("7. Reponer un producto");
        System.out.println("0. Salir");
        System.out.print("Seleccione una opción: ");
    }

    private static int obtenerOpcion(Scanner scanner) {
        int opcion = -1;
        try {
            opcion = Integer.parseInt(scanner.nextLine()); // Captura la entrada como cadena y la convierte
        } catch (NumberFormatException e) {
            System.out.println("Error: Por favor, introduce un número válido.");
        }
        return opcion;
    }

    private static void listarProductos(ProductoGestion productoGestion) {
        System.out.println("=== Listado de Productos ===");
        Collection<Producto> productos = productoGestion.getTodosLosProductos();
        if (productos.isEmpty()) {
            System.out.println("No hay productos disponibles.");
        } else {
            productos.forEach(System.out::println);
        }
    }

    private static void verProductoPorId(Scanner scanner, ProductoGestion productoGestion) {
        System.out.println("=== Ver Producto ===");
        System.out.print("Ingrese el ID: ");
        long idVer = obtenerId(scanner);
        if (idVer == -1) return;

        Producto producto = productoGestion.getProductoPorId(idVer);
        if (producto == null) {
            System.out.println("Producto no encontrado.");
        } else {
            System.out.println(producto);
        }
    }

    private static void crearProducto(Scanner scanner, ProductoGestion productoGestion) {
        System.out.println("=== Crear Producto ===");
        System.out.print("Ingrese el nombre del producto: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese la descripción: ");
        String descripcion = scanner.nextLine();
        System.out.print("Ingrese el precio: ");
        float precio = obtenerFloat(scanner);
        if (precio == -1) return;

        System.out.print("Cantidad: ");
        int cantidad = obtenerInt(scanner);
        if (cantidad == -1) return;

        Producto nuevoProducto = new Producto(0, nombre, descripcion, precio, cantidad);
        productoGestion.crearProducto(nuevoProducto);
        System.out.println("Producto creado: " + nuevoProducto);
    }

    private static void actualizarProducto(Scanner scanner, ProductoGestion productoGestion) {
        System.out.println("=== Actualizar Producto ===");
        System.out.print("Ingrese el ID del producto a actualizar: ");
        long idActualizar = obtenerId(scanner);
        if (idActualizar == -1) return;

        // Validación de existencia del producto
        if (!productoGestion.getProductos().containsKey(idActualizar)) {
            System.out.println("Producto no encontrado. No se puede actualizar.");
            return;
        }

        System.out.print("Ingrese el nuevo nombre: ");
        String nuevoNombre = scanner.nextLine();
        System.out.print("Ingrese la nueva descripción: ");
        String nuevaDescripcion = scanner.nextLine();
        System.out.print("Ingrese el nuevo precio: ");
        float nuevoPrecio = obtenerFloat(scanner);
        if (nuevoPrecio == -1) return;

        System.out.print("Ingrese la nueva cantidad: ");
        int nuevaCantidad = obtenerInt(scanner);
        if (nuevaCantidad == -1) return;

        Producto productoActualizado = new Producto(idActualizar, nuevoNombre, nuevaDescripcion, nuevoPrecio, nuevaCantidad);
        Producto actualizado = productoGestion.actualizarProducto(idActualizar, productoActualizado);

        System.out.println("Producto actualizado: " + actualizado);
    }

    private static void eliminarProducto(Scanner scanner, ProductoGestion productoGestion) {
        System.out.println("=== Eliminar Producto ===");
        System.out.print("Ingrese el ID del producto: ");
        long idEliminar = obtenerId(scanner);
        if (idEliminar == -1) return;

        boolean eliminado = productoGestion.eliminarProducto(idEliminar);
        if (eliminado) {
            System.out.println("Producto eliminado con éxito.");
        } else {
            System.out.println("Producto no encontrado.");
        }
    }

    private static void comprarProducto(Scanner scanner, ProductoGestion productoGestion) {
        System.out.println("=== Comprar Producto ===");
        long idComprar = obtenerId(scanner);
        if (idComprar == -1) return;

        System.out.print("Ingrese la cantidad a comprar: ");
        int cantidadComprar = obtenerInt(scanner);
        if (cantidadComprar == -1) return;

        String resultado = productoGestion.comprarProducto(idComprar, cantidadComprar);
        System.out.println(resultado);
    }

    private static void reponerProducto(Scanner scanner, ProductoGestion productoGestion) {
        System.out.println("=== Reponer Producto ===");
        long idReponer = obtenerId(scanner);
        if (idReponer == -1) return;

        System.out.print("Ingrese la cantidad a reponer: ");
        int cantidadReponer = obtenerInt(scanner);
        if (cantidadReponer == -1) return;

        String resultado = productoGestion.reponerProducto(idReponer, cantidadReponer);
        System.out.println(resultado);
    }

    // Métodos auxiliares para obtener entradas del usuario
    private static long obtenerId(Scanner scanner) {
        long id = -1;
        try {
            id = Long.parseLong(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Error: ID no válido. Inténtalo de nuevo.");
        }
        return id;
    }

    private static float obtenerFloat(Scanner scanner) {
        float valor = -1;
        try {
            valor = Float.parseFloat(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Error: Entrada no válida. Asegúrese de ingresar un número.");
        }
        return valor;
    }

    private static int obtenerInt(Scanner scanner) {
        int valor = -1;
        try {
            valor = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Error: Entrada no válida. Asegúrese de ingresar un número.");
        }
        return valor;
    }
}
