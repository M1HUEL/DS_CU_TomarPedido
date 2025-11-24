package com.itson.controlPedido.servicio;

import com.itson.persistencia.dominio.Pedido;
import java.util.List;

public interface PedidoServicio {

    List<Pedido> obtenerTodosLosPedidos();

    Pedido obtenerPedidoPorId(String id);

    boolean crearPedido(Pedido pedido);

    boolean actualizarPedido(String id, Pedido pedido);

    boolean eliminarPedido(String id);

}
