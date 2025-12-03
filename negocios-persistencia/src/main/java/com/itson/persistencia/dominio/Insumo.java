package com.itson.persistencia.dominio;

public class Insumo {

    private String id;
    private String nombre;
    private String codigo;
    private Double stockActual;
    private Double stockMinimo;
    private String unidadMedida;

    public Insumo() {
    }

    public Insumo(String nombre, String codigo, Double stockActual, Double stockMinimo, String unidadMedida) {
        this.nombre = nombre;
        this.codigo = codigo;
        this.stockActual = stockActual;
        this.stockMinimo = stockMinimo;
        this.unidadMedida = unidadMedida;
    }

    public Insumo(String id, String nombre, String codigo, Double stockActual, Double stockMinimo, String unidadMedida) {
        this.id = id;
        this.nombre = nombre;
        this.codigo = codigo;
        this.stockActual = stockActual;
        this.stockMinimo = stockMinimo;
        this.unidadMedida = unidadMedida;
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

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Double getStockActual() {
        return stockActual;
    }

    public void setStockActual(Double stockActual) {
        this.stockActual = stockActual;
    }

    public Double getStockMinimo() {
        return stockMinimo;
    }

    public void setStockMinimo(Double stockMinimo) {
        this.stockMinimo = stockMinimo;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    @Override
    public String toString() {
        return "Insumo{" + "id=" + id + ", nombre=" + nombre + ", codigo=" + codigo + ", stockActual=" + stockActual + ", stockMinimo=" + stockMinimo + ", unidadMedida=" + unidadMedida + '}';
    }

}
