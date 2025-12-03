package com.itson.dto;

import java.util.List;

public class PedidoDTO {

    private String id;
    private String nombre;
    private List<ProductoDTO> productos;
    private String comentario;
    private double precio;

    public PedidoDTO() {
    }

    public PedidoDTO(String id, String nombre, List<ProductoDTO> productos, String comentario, double precio) {
        this.id = id;
        this.nombre = nombre;
        this.productos = productos;
        this.comentario = comentario;
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

    public List<ProductoDTO> getProductos() {
        return productos;
    }

    public void setProductos(List<ProductoDTO> productos) {
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

    @Override
    public String toString() {
        return "PedidoDTO{" + "id=" + id + ", nombre=" + nombre + ", productos=" + productos + ", comentario=" + comentario + ", precio=" + precio + '}';
    }

}
