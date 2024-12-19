package es.santander.ascender;

public class Producto {
    private long id;
    private String nombre;
    private String descripcion;
    private int cantidad;
    private float precio;

    // Constructor vacío (por defecto)
    public Producto() {
    }

    // Constructor con parámetros para inicializar un producto completo
    public Producto(long id, String nombre, String descripcion, float precio, int cantidad) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        setPrecio(precio);  // Usando el setter para aplicar validaciones si es necesario
        setCantidad(cantidad); // Usando el setter para asegurar la cantidad no sea negativa
    }

    // Getters y setters con validación donde sea necesario

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        // Validación para asegurarse de que la cantidad no sea negativa
        if (cantidad >= 0) {
            this.cantidad = cantidad;
        } else {
            throw new IllegalArgumentException("La cantidad no puede ser negativa.");
        }
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        // Validación para asegurarse de que el precio no sea negativo
        if (precio >= 0) {
            this.precio = precio;
        } else {
            throw new IllegalArgumentException("El precio no puede ser negativo.");
        }
    }

    // Sobrescribimos el método toString para proporcionar una representación más legible del objeto
    @Override
    public String toString() {
        return "Producto{" +
               "id=" + id +
               ", nombre='" + nombre + '\'' +
               ", descripcion='" + descripcion + '\'' +
               ", cantidad=" + cantidad +
               ", precio=" + precio +
               '}';
    }
}
