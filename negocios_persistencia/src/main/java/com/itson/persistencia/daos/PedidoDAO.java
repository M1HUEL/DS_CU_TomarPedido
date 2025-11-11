package com.itson.persistencia.daos;

import com.itson.controlPedido.objetosNegocio.Pedido;
import java.util.List;

public interface PedidoDAO {

    List<Pedido> obtenerTodos();

    Pedido obtenerPorId(int id);

    void agregar(Pedido pedido);

    boolean actualizar(int id, Pedido pedido);

    boolean eliminar(int id);

}
