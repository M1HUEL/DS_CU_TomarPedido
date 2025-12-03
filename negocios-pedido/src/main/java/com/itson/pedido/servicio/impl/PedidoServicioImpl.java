package com.itson.pedido.servicio.impl;

import com.itson.pedido.exception.PedidoException;
import com.itson.pedido.servicio.PedidoServicio;
import com.itson.persistencia.dao.PedidoDAO;
import com.itson.persistencia.dao.impl.PedidoDAOImpl;
import com.itson.persistencia.dominio.Pedido;
import com.itson.persistencia.exception.PersistenciaException;
import java.util.List;

public class PedidoServicioImpl implements PedidoServicio {

    private final PedidoDAO pedidoDAO;

    public PedidoServicioImpl() {
        this.pedidoDAO = new PedidoDAOImpl();
    }

    @Override
    public List<Pedido> obtenerPedidos() throws PedidoException {
        try {
            return pedidoDAO.consultarTodos();
        } catch (PersistenciaException ex) {
            throw new PedidoException("Error al obtener todos los pedidos", ex);
        }
    }

    @Override
    public Pedido obtenerPedidoPorId(String pedidoId) throws PedidoException {
        try {
            return pedidoDAO.consultarPorId(pedidoId);
        } catch (PersistenciaException ex) {
            throw new PedidoException("Error al obtener el pedido por id: " + pedidoId, ex);
        }
    }

    @Override
    public Pedido obtenerPedidoPorNombre(String nombre) throws PedidoException {
        try {
            return pedidoDAO.consultarPorNombre(nombre);
        } catch (PersistenciaException ex) {
            throw new PedidoException("Error al obtener el pedido por nombre: " + nombre, ex);
        }
    }

    @Override
    public void agregarPedido(Pedido pedido) throws PedidoException {
        try {
            pedidoDAO.agregar(pedido);
        } catch (PersistenciaException ex) {
            throw new PedidoException("Error al agregar el pedido", ex);
        }
    }

    @Override
    public void actualizarPedido(String id, Pedido pedido) throws PedidoException {
        try {
            pedidoDAO.actualizar(id, pedido);
        } catch (PersistenciaException ex) {
            throw new PedidoException("Error al actualizar el pedido con id: " + id, ex);
        }
    }

    @Override
    public void eliminarPedido(String id) throws PedidoException {
        try {
            pedidoDAO.eliminar(id);
        } catch (PersistenciaException ex) {
            throw new PedidoException("Error al eliminar el pedido con id: " + id, ex);
        }
    }
}
