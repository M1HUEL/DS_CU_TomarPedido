package com.itson.persistencia.dominio;

import java.math.BigDecimal;

public class InventarioItem {

    private String id;
    private String nombre;
    private double cantidadDisponible;
    private BigDecimal precioUnidad;
    private InventarioTipo tipo;

    public InventarioItem() {
    }

    public InventarioItem(String nombre, double cantidadDisponible, BigDecimal precioUnidad, InventarioTipo tipo) {
        this.nombre = nombre;
        this.cantidadDisponible = cantidadDisponible;
        this.precioUnidad = precioUnidad;
        this.tipo = tipo;
    }

    public InventarioItem(String id, String nombre, double cantidadDisponible, BigDecimal precioUnidad, InventarioTipo tipo) {
        this.id = id;
        this.nombre = nombre;
        this.cantidadDisponible = cantidadDisponible;
        this.precioUnidad = precioUnidad;
        this.tipo = tipo;
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

    public double getCantidadDisponible() {
        return cantidadDisponible;
    }

    public void setCantidadDisponible(double cantidadDisponible) {
        this.cantidadDisponible = cantidadDisponible;
    }

    public BigDecimal getPrecioUnidad() {
        return precioUnidad;
    }

    public void setPrecioUnidad(BigDecimal precioUnidad) {
        this.precioUnidad = precioUnidad;
    }

    public InventarioTipo getTipo() {
        return tipo;
    }

    public void setTipo(InventarioTipo tipo) {
        this.tipo = tipo;
    }

}
