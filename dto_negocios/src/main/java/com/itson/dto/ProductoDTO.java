package com.itson.dto;

import java.math.BigDecimal;
import java.util.List;

public class ProductoDTO {

    private String id;
    private String nombre;
    private List<IngredienteDTO> ingredientes;
    private List<ComplementoDTO> complementos;
    private List<ExtraDTO> extras;
    private BigDecimal precio;

    public ProductoDTO() {
    }

    public ProductoDTO(String id, String nombre, List<IngredienteDTO> ingredientes, List<ComplementoDTO> complementos, List<ExtraDTO> extras, BigDecimal precio) {
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

    public List<IngredienteDTO> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(List<IngredienteDTO> ingredientes) {
        this.ingredientes = ingredientes;
    }

    public List<ComplementoDTO> getComplementos() {
        return complementos;
    }

    public void setComplementos(List<ComplementoDTO> complementos) {
        this.complementos = complementos;
    }

    public List<ExtraDTO> getExtras() {
        return extras;
    }

    public void setExtras(List<ExtraDTO> extras) {
        this.extras = extras;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "ProductoDTO{" + "id=" + id + ", nombre=" + nombre + ", ingredientes=" + ingredientes + ", complementos=" + complementos + ", extras=" + extras + ", precio=" + precio + '}';
    }

}
