package com.itson.persistencia.dominio;

import java.time.LocalDateTime;

public class Pago {

    private String id;
    private String pedidoId;
    private Double monto;
    private LocalDateTime fechaPago;
    private MetodoPago metodoPago;
    private EstadoPago estado;

    public Pago() {
    }

    public Pago(String pedidoId, Double monto, MetodoPago metodoPago, EstadoPago estado) {
        this.pedidoId = pedidoId;
        this.monto = monto;
        this.metodoPago = metodoPago;
        this.estado = estado;
        this.fechaPago = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(String pedidoId) {
        this.pedidoId = pedidoId;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public LocalDateTime getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(LocalDateTime fechaPago) {
        this.fechaPago = fechaPago;
    }

    public MetodoPago getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(MetodoPago metodoPago) {
        this.metodoPago = metodoPago;
    }

    public EstadoPago getEstado() {
        return estado;
    }

    public void setEstado(EstadoPago estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Pago{" + "id=" + id + ", pedidoId=" + pedidoId + ", monto=" + monto + ", fechaPago=" + fechaPago + ", metodo=" + metodoPago + ", estado=" + estado + '}';
    }
}
