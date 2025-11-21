package com.itson.controlPedido.controlador;

import com.itson.persistencia.dao.PedidoDAO;
import com.itson.persistencia.dao.impl.PedidoDAOImpl;
import com.itson.persistencia.dominio.Pedido;
import java.util.List;

public class PedidoControlador {

    private final PedidoDAO pedidoDAO;

    public PedidoControlador() {
        this.pedidoDAO = new PedidoDAOImpl();
    }

    public List<Pedido> obtenerTodosLosPedidos() {
        return pedidoDAO.consultarTodos();
    }

    public Pedido obtenerPedidoPorId(String id) {
        return pedidoDAO.consultar(id);
    }

    public boolean crearPedido(Pedido pedido) {
        if (pedido == null) {
            System.out.println("Pedido nulo. No se puede crear.");
            return false;
        }
        return pedidoDAO.agregar(pedido);
    }

    public boolean actualizarPedido(String id, Pedido pedido) {
        if (pedido == null) {
            System.out.println("Pedido nulo. No se puede actualizar.");
            return false;
        }
        return pedidoDAO.actualizar(id, pedido);
    }

    public boolean eliminarPedido(String id) {
        return pedidoDAO.eliminar(id);
    }
}
