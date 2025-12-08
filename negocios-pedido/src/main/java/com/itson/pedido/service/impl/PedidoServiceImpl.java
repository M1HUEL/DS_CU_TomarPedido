package com.itson.pedido.service.impl;

import com.itson.inventario.exception.InventarioException;
import com.itson.inventario.service.InventarioService;
import com.itson.inventario.service.impl.InventarioServiceImpl;
import com.itson.pedido.exception.PedidoException;
import com.itson.pedido.service.PedidoService;
import com.itson.persistencia.dao.PedidoDAO;
import com.itson.persistencia.dao.impl.PedidoDAOImpl;
import com.itson.persistencia.dominio.*;
import com.itson.persistencia.exception.PersistenciaException;
import java.util.List;

public class PedidoServiceImpl implements PedidoService {

    private final PedidoDAO pedidoDAO;
    private final InventarioService inventarioServicio;

    public PedidoServiceImpl() {
        this.pedidoDAO = new PedidoDAOImpl();
        this.inventarioServicio = new InventarioServiceImpl();
    }

    @Override
    public List<Pedido> obtenerPedidos() throws PedidoException {
        try {
            return pedidoDAO.consultarTodos();
        } catch (PersistenciaException e) {
            throw new PedidoException("Error al obtener la lista de pedidos.", e);
        }
    }

    @Override
    public List<Pedido> obtenerPedidosPorEstado(List<String> estados) throws PedidoException {
        try {
            return pedidoDAO.consultarTodosPorEstado(estados);
        } catch (PersistenciaException e) {
            throw new PedidoException("Error al filtrar pedidos por estado.", e);
        }
    }

    @Override
    public List<Pedido> obtenerPedidosCompletados() throws PedidoException {
        try {
            return pedidoDAO.consultarTodosPorEstado(
                    java.util.Arrays.asList(EstadoPedido.ENTREGADO.name())
            );
        } catch (PersistenciaException e) {
            throw new PedidoException("Error al obtener historial", e);
        }
    }

    @Override
    public Pedido obtenerPedidoPorId(String pedidoId) throws PedidoException {
        try {
            return pedidoDAO.consultarPorId(pedidoId);
        } catch (PersistenciaException e) {
            throw new PedidoException("Error al buscar el pedido por ID: " + pedidoId, e);
        }
    }

    @Override
    public Pedido obtenerPedidoPorNombre(String nombre) throws PedidoException {
        try {
            return pedidoDAO.consultarPorNombre(nombre);
        } catch (PersistenciaException e) {
            throw new PedidoException("Error al buscar el pedido por nombre: " + nombre, e);
        }
    }

    @Override
    public void agregarPedido(Pedido pedido) throws PedidoException {
        if (pedido == null) {
            throw new PedidoException("No se puede registrar un pedido nulo.");
        }
        if (pedido.getProductos() == null || pedido.getProductos().isEmpty()) {
            throw new PedidoException("El pedido debe contener al menos un producto.");
        }

        try {
            pedidoDAO.agregar(pedido);

            if (pedido.getProductos() != null) {
                for (Producto prod : pedido.getProductos()) {
                    descontarLista(prod.getIngredientes());
                    descontarLista(prod.getComplementos());
                    descontarLista(prod.getExtras());
                }
            }

        } catch (PersistenciaException e) {
            throw new PedidoException("Error al guardar el pedido en BD.", e);
        } catch (InventarioException e) {
            throw new PedidoException("Error de inventario al procesar pedido: " + e.getMessage(), e);
        }
    }

    @Override
    public void actualizarPedido(String pedidoId, Pedido pedido) throws PedidoException {
        try {
            pedidoDAO.actualizar(pedidoId, pedido);
        } catch (PersistenciaException e) {
            throw new PedidoException("Error al actualizar el pedido.", e);
        }
    }

    @Override
    public void eliminarPedido(String pedidoId) throws PedidoException {
        try {
            pedidoDAO.eliminar(pedidoId);
        } catch (PersistenciaException e) {
            throw new PedidoException("Error al eliminar el pedido.", e);
        }
    }

    private void descontarLista(List<?> lista) throws InventarioException {
        if (lista == null) {
            return;
        }

        for (Object item : lista) {
            String insumoId = null;
            Double cantidad = 0.0;

            switch (item) {
                case Ingrediente ingrediente -> {
                    insumoId = ingrediente.getInsumoId();
                    cantidad = ingrediente.getCantidadNecesaria();
                }
                case Complemento complemento -> {
                    insumoId = complemento.getInsumoId();
                    cantidad = complemento.getCantidadNecesaria();
                }
                case Extra extra -> {
                    insumoId = extra.getInsumoId();
                    cantidad = extra.getCantidadNecesaria();
                }
                default -> {
                }
            }

            if (insumoId != null && cantidad > 0) {
                inventarioServicio.consumirStock(insumoId, cantidad);
            }
        }
    }
}
