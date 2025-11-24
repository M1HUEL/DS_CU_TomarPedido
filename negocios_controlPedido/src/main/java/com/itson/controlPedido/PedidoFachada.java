package com.itson.controlPedido;

import com.itson.dto.PedidoDTO;
import com.itson.persistencia.dominio.Pedido;
import java.util.List;

public interface PedidoFachada {

    List<PedidoDTO> consultarPedidosPorIdProducto(String id);

    List<PedidoDTO> consultarPedidosPorNombreProducto(String nombre);

    boolean crearPedido(PedidoDTO pedidoDTO);

}
