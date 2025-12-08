package com.itson.pedido.service;

import com.itson.pedido.exception.PedidoException;
import com.itson.persistencia.dominio.Pedido;
import java.util.List;

public interface PedidoService {

    List<Pedido> obtenerPedidos() throws PedidoException;

    List<Pedido> obtenerPedidosPorEstado(List<String> estados) throws PedidoException;

    List<Pedido> obtenerPedidosCompletados() throws PedidoException;

    Pedido obtenerPedidoPorId(String id) throws PedidoException;

    Pedido obtenerPedidoPorNombre(String nombre) throws PedidoException;

    void agregarPedido(Pedido pedido) throws PedidoException;

    void actualizarPedido(String pedidoId, Pedido pedido) throws PedidoException;

    void eliminarPedido(String pedidoId) throws PedidoException;
}
