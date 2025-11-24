package com.itson.persistencia.dominio;

import java.math.BigDecimal;

public class Extra {

    private String id;
    private String nombre;
    private BigDecimal precio;
    private String inventarioItemId;
    private double cantidadRequerida;

    public Extra() {
    }

    public Extra(String id, String nombre, BigDecimal precio, String inventarioItemId, double cantidadRequerida) {
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

    public double getCantidadRequerida() {
        return cantidadRequerida;
    }

    public void setCantidadRequerida(double cantidadRequerida) {
        this.cantidadRequerida = cantidadRequerida;
    }

    @Override
    public String toString() {
        return "Extra{" + "id=" + id + ", nombre=" + nombre + ", precio=" + precio + ", inventarioItemId=" + inventarioItemId + ", cantidadRequerida=" + cantidadRequerida + '}';
    }

}
