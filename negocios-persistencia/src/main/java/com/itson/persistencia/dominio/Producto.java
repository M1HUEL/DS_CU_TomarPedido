package com.itson.persistencia.dominio;

import java.util.List;

public class Producto {

    private String id;
    private String nombre;
    private List<Ingrediente> ingredientes;
    private List<Complemento> complementos;
    private List<Extra> extras;
    private double precio;

    public Producto() {
    }

    public Producto(String nombre, List<Ingrediente> ingredientes, List<Complemento> complementos, List<Extra> extras, double precio) {
        this.nombre = nombre;
        this.ingredientes = ingredientes;
        this.complementos = complementos;
        this.extras = extras;
        this.precio = precio;
    }

    public Producto(String id, String nombre, List<Ingrediente> ingredientes, List<Complemento> complementos, List<Extra> extras, double precio) {
        this.id = id;
        this.nombre = nombre;
        this.ingredientes = ingredientes;
        this.complementos = complementos;
        this.extras = extras;
        this.precio = precio;
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

    public List<Ingrediente> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(List<Ingrediente> ingredientes) {
        this.ingredientes = ingredientes;
    }

    public List<Complemento> getComplementos() {
        return complementos;
    }

    public void setComplementos(List<Complemento> complementos) {
        this.complementos = complementos;
    }

    public List<Extra> getExtras() {
        return extras;
    }

    public void setExtras(List<Extra> extras) {
        this.extras = extras;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "Producto{" + "id=" + id + ", nombre=" + nombre + ", ingredientes=" + ingredientes + ", complementos=" + complementos + ", extras=" + extras + ", precio=" + precio + '}';
    }

}
