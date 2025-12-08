package com.itson.negocios.transaccion.service;

import com.itson.negocios.transaccion.exception.TransaccionException;
import com.itson.persistencia.dominio.Pago;
import java.util.List;

public interface TransaccionService {

    List<Pago> obtenerPagos() throws TransaccionException;

    Pago obtenerPagoPorId(String id) throws TransaccionException;

    Pago obtenerPagoPorPedidoId(String pedidoId) throws TransaccionException;

    void agregarPago(Pago pago) throws TransaccionException;

    void actualizarPago(String id, Pago pago) throws TransaccionException;

    void eliminarPago(String id) throws TransaccionException;
}
