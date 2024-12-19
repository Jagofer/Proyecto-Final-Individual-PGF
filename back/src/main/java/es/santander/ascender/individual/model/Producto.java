package es.santander.ascender.individual.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class Producto {
    private long id;

    // El nombre no puede ser nulo y debe tener un tamaño máximo de 30 caracteres
    @NotNull
    @Size(max = 30)
    private String nombre;
    
    // Descripción no puede ser nula, tamaño máximo 150 caracteres
    @NotNull
    @Size(max = 150)
    private String descripcion;
    
    // Cantidad mínima de 0
    @Min(value = 0)
    private int cantidad;
    
    // Precio mínimo de 0
    @Min(value = 0)
    private float precio;

    // Constructor vacío
    public Producto() {
    }

    // Constructor con parámetros
    public Producto(long id, @NotNull @Size(max = 30) String nombre, @NotNull @Size(max = 150) String descripcion,
                    @Min(0) float precio, @Min(0) int cantidad) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.precio = precio;
    }

    // Getters y Setters
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
        this.cantidad = cantidad;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

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
