package com.itson.pedido.servicio;

import com.itson.pedido.exception.PedidoException;
import com.itson.persistencia.dominio.Pedido;
import java.util.List;

public interface PedidoServicio {

    List<Pedido> obtenerPedidos() throws PedidoException;

    Pedido obtenerPedidoPorId(String usuarioId) throws PedidoException;

    Pedido obtenerPedidoPorNombre(String nombre) throws PedidoException;

    void agregarPedido(Pedido pedido) throws PedidoException;

    void actualizarPedido(String pedidoId, Pedido pedido) throws PedidoException;

    void eliminarPedido(String pedidoId) throws PedidoException;
}
