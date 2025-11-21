package com.itson.persistencia.dominio;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class Pedido {

    private String id;
    private String nombre;
    private List<Producto> productos;
    private String comentario;
    private BigDecimal precio;
    private LocalDateTime fechaPedido;

    public Pedido() {
    }

    public Pedido(String id, String nombre, List<Producto> productos, String comentario, BigDecimal precio, LocalDateTime fechaPedido) {
        this.id = id;
        this.nombre = nombre;
        this.productos = productos;
        this.comentario = comentario;
        this.precio = precio;
        this.fechaPedido = fechaPedido;
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

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public LocalDateTime getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(LocalDateTime fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    @Override
    public String toString() {
        return "Pedido{" + "id=" + id + ", nombre=" + nombre + ", productos=" + productos + ", comentario=" + comentario + ", precio=" + precio + ", fechaPedido=" + fechaPedido + '}';
    }

}
