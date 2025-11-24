package com.itson.dto;

import java.math.BigDecimal;

public class IngredienteDTO {

    private String id;
    private String nombre;
    private BigDecimal precio;
    private String inventarioItemId;
    private Double cantidadRequerida;

    public IngredienteDTO() {
    }

    public IngredienteDTO(String id, String nombre, BigDecimal precio, String inventarioItemId, Double cantidadRequerida) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.inventarioItemId = inventarioItemId;
        this.cantidadRequerida = cantidadRequerida;
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

    public String getInventarioItemId() {
        return inventarioItemId;
    }

    public void setInventarioItemId(String inventarioItemId) {
        this.inventarioItemId = inventarioItemId;
    }

    public Double getCantidadRequerida() {
        return cantidadRequerida;
    }

    public void setCantidadRequerida(Double cantidadRequerida) {
        this.cantidadRequerida = cantidadRequerida;
    }

}
