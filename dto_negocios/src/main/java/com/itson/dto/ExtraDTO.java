package com.itson.dto;

import java.math.BigDecimal;

public class ExtraDTO {

    private String id;
    private String nombre;
    private BigDecimal precio;

    public ExtraDTO() {
    }

    public ExtraDTO(String id, String nombre, BigDecimal precio) {
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
        return "ExtraDTO{" + "id=" + id + ", nombre=" + nombre + ", precio=" + precio + '}';
    }

}
