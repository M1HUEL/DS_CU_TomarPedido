package com.itson.persistencia.dao;

import com.itson.persistencia.dominio.Pedido;
import java.util.List;

public interface PedidoDAO {

    List<Pedido> consultarTodos();

    Pedido consultar(String id);

    boolean agregar(Pedido pedido);

    boolean actualizar(String id, Pedido pedido);

    boolean eliminar(String id);

}
