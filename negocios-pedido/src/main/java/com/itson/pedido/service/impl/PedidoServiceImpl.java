package com.itson.pedido.service.impl;

import com.itson.inventario.exception.InventarioException;
import com.itson.inventario.service.impl.InventarioServiceImpl;
import com.itson.pedido.exception.PedidoException;
import com.itson.persistencia.dao.PedidoDAO;
import com.itson.persistencia.dao.impl.PedidoDAOImpl;
import com.itson.persistencia.dominio.*;
import com.itson.persistencia.exception.PersistenciaException;
import java.util.List;
import com.itson.pedido.service.PedidoService;
import com.itson.inventario.service.InventarioService;

public class PedidoServiceImpl implements PedidoService {

    private final PedidoDAO pedidoDAO;
    private final InventarioService inventarioServicio;

    public PedidoServiceImpl() {
        this.pedidoDAO = new PedidoDAOImpl();
        // Inicializamos el servicio de inventario para poder descontar stock
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
        // 1. Validaciones básicas de negocio
        if (pedido == null) {
            throw new PedidoException("No se puede registrar un pedido nulo.");
        }
        if (pedido.getProductos() == null || pedido.getProductos().isEmpty()) {
            throw new PedidoException("El pedido debe contener al menos un producto.");
        }

        try {
            // 2. Guardar el pedido en la base de datos (Persistencia)
            pedidoDAO.agregar(pedido);

            // 3. Lógica de Inventario (Descontar stock)
            // Recorremos los productos para descontar sus componentes
            if (pedido.getProductos() != null) {
                for (Producto prod : pedido.getProductos()) {

                    // A. Descontar Ingredientes
                    descontarLista(prod.getIngredientes());

                    // B. Descontar Complementos
                    descontarLista(prod.getComplementos());

                    // C. Descontar Extras
                    descontarLista(prod.getExtras());
                }
            }

        } catch (PersistenciaException e) {
            throw new PedidoException("Error al guardar el pedido en la base de datos.", e);
        } catch (InventarioException e) {
            // Si falla el inventario (stock insuficiente), avisamos en la excepción del pedido
            throw new PedidoException("El pedido se guardó, pero hubo un error de inventario: " + e.getMessage(), e);
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

    // --- Método Auxiliar Privado para reutilizar lógica de descuento ---
    private void descontarLista(List<?> lista) throws InventarioException {
        if (lista == null) {
            return;
        }

        for (Object item : lista) {
            String insumoId = null;
            Double cantidad = 0.0;

            switch (item) {
                case Ingrediente i -> {
                    insumoId = i.getInsumoId();
                    cantidad = i.getCantidadNecesaria();
                }
                case Complemento c -> {
                    insumoId = c.getInsumoId();
                    cantidad = c.getCantidadNecesaria();
                }
                case Extra e -> {
                    insumoId = e.getInsumoId();
                    cantidad = e.getCantidadNecesaria();
                }
                default -> {
                }
            }

            // Llamada al servicio de inventario si hay datos válidos
            if (insumoId != null && cantidad > 0) {
                // Si esto falla, lanzará InventarioException hacia arriba
                inventarioServicio.consumirStock(insumoId, cantidad);
            }
        }
    }
}
