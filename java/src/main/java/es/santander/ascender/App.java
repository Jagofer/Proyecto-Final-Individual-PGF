package es.santander.ascender;

import java.util.Collection;
import java.util.Scanner;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        System.out.println("Hello World!");

        ProductoGestion productoGestion = new ProductoGestion();
        Scanner scanner = new Scanner(System.in);
        boolean salir = false;

        while (!salir) {
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

            int opcion = -1;

            try {
                opcion = Integer.parseInt(scanner.nextLine()); // Captura la entrada como cadena y la convierte
            } catch (NumberFormatException e) {
                System.out.println("Error: Por favor, introduce un número válido.");
                continue; // Regresa al inicio del bucle
            }

            switch (opcion) {
                case 1: // Listar todos los productos
                    System.out.println("=== Listado de Productos ===");
                    Collection<Producto> productos = productoGestion.getTodosLosProductos();
                    if (productos.isEmpty()) {
                        System.out.println("No hay productos disponibles.");
                    } else {
                        productos.forEach(producto -> System.out.println(producto));
                    }
                    break;

                case 2: // Ver producto por ID
                System.out.println("=== Ver Producto ===");
                System.out.print("Ingrese el ID: ");
                try {
                    long idVer = Long.parseLong(scanner.nextLine()); // Captura la entrada como cadena y la convierte
                    Producto producto = productoGestion.getProductoPorId(idVer);
                    if (producto == null) {
                        System.out.println("Producto no encontrado.");
                    } else {
                        System.out.println(producto);
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Error: ID no válido. Inténtalo de nuevo.");
                }
                    break;

                case 3: // Crear un nuevo producto
                    System.out.println("=== Crear Producto ===");
                    System.out.print("Ingrese el nombre del producto: ");
                    String nombre = scanner.nextLine();
                    System.out.print("Ingrese la descripción: ");
                    String descripcion = scanner.nextLine();
                    System.out.print("Ingrese el precio: ");
                     
                    float precio = -1;
                    int cantidad = -1;

                    try {
                        precio = Float.parseFloat(scanner.nextLine());
                        System.out.print("Cantidad: ");
                        cantidad = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Error: Precio o cantidad no válidos. Inténtalo de nuevo.");
                        break;
                    }

                    Producto nuevoProducto = new Producto(0, nombre, descripcion, precio, cantidad);
                    productoGestion.crearProducto(nuevoProducto);
                    System.out.println("Producto creado: " + nuevoProducto);
                    break;

                    case 4: // Actualizar un producto
                    System.out.print("Ingrese el ID del producto a actualizar: ");
                    long idActualizar = scanner.nextLong();
                    scanner.nextLine(); // Limpiar el buffer
                
                    // Validar si el producto con el ID existe antes de continuar
                    if (!productoGestion.getProductos().containsKey(idActualizar)) {
                        System.out.println("Producto no encontrado. No se puede actualizar.");
                        break; // Salir del caso si no existe el ID
                    }
                
                    // Continuar con la actualización solo si el ID es válido
                    System.out.print("Ingrese el nuevo nombre: ");
                    String nuevoNombre = scanner.nextLine();
                    System.out.print("Ingrese la nueva descripción: ");
                    String nuevaDescripcion = scanner.nextLine();
                    System.out.print("Ingrese el nuevo precio: ");
                    float nuevoPrecio = scanner.nextFloat();
                    System.out.print("Ingrese la nueva cantidad: ");
                    int nuevaCantidad = scanner.nextInt();
                
                    Producto productoActualizado = new Producto(idActualizar, nuevoNombre, nuevaDescripcion,
                            nuevoPrecio, nuevaCantidad);
                    Producto actualizado = productoGestion.actualizarProducto(idActualizar, productoActualizado);
                
                    // Confirmación de la actualización
                    System.out.println("Producto actualizado: " + actualizado);
                    break;

                case 5: // Eliminar un producto
                System.out.println("=== Eliminar Producto ===");
                System.out.print("Ingrese el ID del producto: ");
                try {
                    long idEliminar = Long.parseLong(scanner.nextLine());
                    boolean eliminado = productoGestion.eliminarProducto(idEliminar);
                    if (eliminado) {
                        System.out.println("Producto eliminado con éxito.");
                    } else {
                        System.out.println("Producto no encontrado.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Error: ID no válido. Inténtalo de nuevo.");
                }
                    break;

                    case 6: // Comprar un producto
                    System.out.println("=== Comprar Producto ===");
                    System.out.print("Ingrese el ID del producto: ");
                    try {
                        // Solicitar el ID del producto
                        System.out.print("Ingrese el ID del producto a comprar: ");
                        long idComprar = Long.parseLong(scanner.nextLine());
                
                        // Solicitar la cantidad a comprar
                        System.out.print("Ingrese la cantidad a comprar: ");
                        int cantidadComprar = Integer.parseInt(scanner.nextLine());
                
                        // Realizar la compra
                        String resultado = productoGestion.comprarProducto(idComprar, cantidadComprar);
                        System.out.println(resultado);
                    } catch (NumberFormatException e) {
                        System.out.println("Error: Entrada no válida. Asegúrese de ingresar números válidos.");
                    }
                    break;

                    case 7: // Reponer stock producto
                    System.out.println("=== Reponer Producto ===");
                    System.out.print("Ingrese el ID: ");
                    try {
                        // Solicitar el ID del producto
                        System.out.print("Ingrese el ID del producto a reponer: ");
                        long idReponer = Long.parseLong(scanner.nextLine());
                
                        // Solicitar la cantidad a comprar
                        System.out.print("Ingrese la cantidad a reponer: ");
                        int cantidadReponer = Integer.parseInt(scanner.nextLine());
                
                        // Realizar la compra
                        String resultado = productoGestion.reponerProducto(idReponer, cantidadReponer);
                        System.out.println(resultado);
                    } catch (NumberFormatException e) {
                        System.out.println("Error: Entrada no válida. Asegúrese de ingresar números válidos.");
                    }
                    break;

                case 0: // Salir
                    salir = true;
                    System.out.println("Saliendo del sistema. ¡Hasta pronto!");
                    break;

                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        }

        scanner.close();
    }
}
