package com.itson.controlPedido.servicio.impl;

import com.itson.controlInventario.servicio.InventarioServicio;
import com.itson.controlInventario.servicio.impl.InventarioServicioImpl;
import com.itson.controlPedido.servicio.PedidoServicio;
import com.itson.persistencia.dao.PedidoDAO;
import com.itson.persistencia.dao.impl.PedidoDAOImpl;
import com.itson.persistencia.dominio.Pedido;
import java.util.List;

public class PedidoServicioImpl implements PedidoServicio {

    private final PedidoDAO pedidoDAO;
    private final InventarioServicio inventarioServicio;

    public PedidoServicioImpl() {
        this.pedidoDAO = new PedidoDAOImpl();
        this.inventarioServicio = new InventarioServicioImpl();
    }

    @Override
    public List<Pedido> obtenerTodosLosPedidos() {
        return pedidoDAO.consultarTodos();
    }

    @Override
    public Pedido obtenerPedidoPorId(String id) {
        if (id == null || id.trim().isEmpty()) {
            System.out.println("ID nulo o vacío. No se puede consultar.");
            return null;
        }
        return pedidoDAO.consultar(id);
    }

    @Override
    public boolean crearPedido(Pedido pedido) {
        if (pedido == null) {
            System.out.println("Pedido nulo. No se puede crear.");
            return false;
        }
        if (!inventarioServicio.hayInventarioSuficiente(pedido)) {
            System.out.println("No hay inventario suficiente para crear el pedido.");
            return false;
        }
        boolean creado = pedidoDAO.agregar(pedido);
        if (!creado) {
            System.out.println("Error al registrar el pedido en la base de datos.");
            return false;
        }
        boolean descontado = inventarioServicio.descontarInventario(pedido);
        if (!descontado) {
            System.out.println("Error al descontar inventario después de crear el pedido.");
            return false;
        }
        System.out.println("Pedido creado correctamente y el inventario fue actualizado.");
        return true;
    }

    @Override
    public boolean actualizarPedido(String id, Pedido pedido) {
        if (id == null || id.trim().isEmpty()) {
            System.out.println("ID nulo o vacío. No se puede actualizar.");
            return false;
        }

        if (pedido == null) {
            System.out.println("Pedido nulo. No se puede actualizar.");
            return false;
        }

        return pedidoDAO.actualizar(id, pedido);
    }

    @Override
    public boolean eliminarPedido(String id) {
        if (id == null || id.trim().isEmpty()) {
            System.out.println("ID nulo o vacío. No se puede eliminar.");
            return false;
        }

        return pedidoDAO.eliminar(id);
    }
}
