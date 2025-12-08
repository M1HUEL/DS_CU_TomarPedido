package com.itson.persistencia.dominio;

public class Ingrediente {

    private String id;
    private String nombre;
    private String descripcion;
    private double precio;
    private String insumoId;
    private Double cantidadNecesaria;

    public Ingrediente() {
    }

    public Ingrediente(String nombre, String descripcion, double precio) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
    }

    public Ingrediente(String nombre, String descripcion, double precio, String insumoId, Double cantidadNecesaria) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.insumoId = insumoId;
        this.cantidadNecesaria = cantidadNecesaria;
    }

    public Ingrediente(String id, String nombre, String descripcion, double precio, String insumoId, Double cantidadNecesaria) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.insumoId = insumoId;
        this.cantidadNecesaria = cantidadNecesaria;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getInsumoId() {
        return insumoId;
    }

    public void setInsumoId(String insumoId) {
        this.insumoId = insumoId;
    }

    public Double getCantidadNecesaria() {
        return cantidadNecesaria;
    }

    public void setCantidadNecesaria(Double cantidadNecesaria) {
        this.cantidadNecesaria = cantidadNecesaria;
    }

    @Override
    public String toString() {
        return "Ingrediente{" + "id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + ", precio=" + precio + ", insumoId=" + insumoId + ", cantidadNecesaria=" + cantidadNecesaria + '}';
    }

}
