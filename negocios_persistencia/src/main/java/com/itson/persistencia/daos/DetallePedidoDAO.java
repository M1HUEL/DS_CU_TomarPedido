package com.itson.persistencia.daos;

import com.itson.controlPedido.objetosNegocio.DetallePedido;
import java.util.List;

public interface DetallePedidoDAO {

    List<DetallePedido> obtenerTodos();

    DetallePedido obtenerPorId(int id);

    void agregar(DetallePedido detallePedido);

    boolean actualizar(int id, DetallePedido detallePedido);

    boolean eliminar(int id);

}
