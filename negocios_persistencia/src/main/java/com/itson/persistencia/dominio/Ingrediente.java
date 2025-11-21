package com.itson.persistencia.dominio;

import java.math.BigDecimal;

public class Ingrediente {

    private String id;
    private String nombre;
    private BigDecimal precio;

    public Ingrediente() {
    }

    public Ingrediente(String id, String nombre, BigDecimal precio) {
        this.id = id;
        this.nombre = nombre;
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

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "Ingrediente{" + "id=" + id + ", nombre=" + nombre + ", precio=" + precio + '}';
    }

}
