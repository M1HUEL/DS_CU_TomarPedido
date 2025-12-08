package com.itson.persistencia.dominio;

import java.util.List;

public class Pedido {

    private String id;
    private String nombre;
    private List<Producto> productos;
    private String comentario;
    private double precio;
    private EstadoPedido estado;

    public Pedido() {
    }

    public Pedido(String nombre, List<Producto> productos, String comentario, double precio, EstadoPedido estado) {
        this.nombre = nombre;
        this.productos = productos;
        this.comentario = comentario;
        this.precio = precio;
        this.estado = estado;
    }

    public Pedido(String id, String nombre, List<Producto> productos, String comentario, double precio, EstadoPedido estado) {
        this.id = id;
        this.nombre = nombre;
        this.productos = productos;
        this.comentario = comentario;
        this.precio = precio;
        this.estado = estado;
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

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public void setEstado(EstadoPedido estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Pedido{" + "id=" + id + ", nombre=" + nombre + ", productos=" + productos + ", comentario=" + comentario + ", precio=" + precio + ", estado=" + estado + '}';
    }

}
