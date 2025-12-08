package com.itson.negocios.transaccion.service;

import com.itson.negocios.transaccion.exception.TransaccionException;
import com.itson.persistencia.dominio.Pago;
import java.util.List;

public interface TransaccionService {

    List<Pago> obtenerPagos() throws TransaccionException;

    // Ajuste: Devuelve un solo Pago, no una lista
    Pago obtenerPagoPorId(String id) throws TransaccionException;

    // Ajuste: Devuelve un solo Pago por Pedido (asumiendo 1 pago por pedido)
    Pago obtenerPagoPorPedidoId(String pedidoId) throws TransaccionException;

    // Ajuste: Todos lanzan TransaccionException para mantener las capas limpias
    void agregarPago(Pago pago) throws TransaccionException;

    void actualizarPago(String id, Pago pago) throws TransaccionException;

    void eliminarPago(String id) throws TransaccionException;
}
