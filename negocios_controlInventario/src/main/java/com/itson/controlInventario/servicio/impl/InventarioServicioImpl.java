package com.itson.controlInventario.servicio.impl;

import com.itson.controlInventario.servicio.InventarioServicio;
import com.itson.persistencia.dao.InventarioDAO;
import com.itson.persistencia.dao.impl.InventarioDAOImpl;
import com.itson.persistencia.dominio.Complemento;
import com.itson.persistencia.dominio.Extra;
import com.itson.persistencia.dominio.Ingrediente;
import com.itson.persistencia.dominio.InventarioItem;
import com.itson.persistencia.dominio.Pedido;
import com.itson.persistencia.dominio.Producto;

public class InventarioServicioImpl implements InventarioServicio {

    private final InventarioDAO inventarioDAO;

    public InventarioServicioImpl() {
        this.inventarioDAO = new InventarioDAOImpl();
    }

    @Override
    public boolean hayInventarioSuficiente(Pedido pedido) {
        for (Producto producto : pedido.getProductos()) {
            if (producto.getIngredientes() != null) {
                for (Ingrediente ing : producto.getIngredientes()) {
                    InventarioItem item = inventarioDAO.consultarPorId(ing.getInventarioItemId());
                    if (item == null || item.getCantidadDisponible() < ing.getCantidadRequerida()) {
                        return false;
                    }
                }
            }

            if (producto.getComplementos() != null) {
                for (Complemento comp : producto.getComplementos()) {
                    InventarioItem item = inventarioDAO.consultarPorId(comp.getInventarioItemId());
                    if (item == null || item.getCantidadDisponible() < comp.getCantidadRequerida()) {
                        return false;
                    }
                }
            }

            if (producto.getExtras() != null) {
                for (Extra ext : producto.getExtras()) {
                    InventarioItem item = inventarioDAO.consultarPorId(ext.getInventarioItemId());
                    if (item == null || item.getCantidadDisponible() < ext.getCantidadRequerida()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @Override
    public boolean descontarInventario(Pedido pedido) {
        for (Producto producto : pedido.getProductos()) {
            if (producto.getIngredientes() != null) {
                for (Ingrediente ing : producto.getIngredientes()) {
                    InventarioItem item = inventarioDAO.consultarPorId(ing.getInventarioItemId());
                    if (item == null) {
                        continue;
                    }

                    double nuevaCantidad = item.getCantidadDisponible() - ing.getCantidadRequerida();
                    item.setCantidadDisponible(Math.max(nuevaCantidad, 0));
                    inventarioDAO.actualizar(item.getId(), item);
                }
            }

            if (producto.getComplementos() != null) {
                for (Complemento comp : producto.getComplementos()) {
                    InventarioItem item = inventarioDAO.consultarPorId(comp.getInventarioItemId());
                    if (item == null) {
                        continue;
                    }

                    double nuevaCantidad = item.getCantidadDisponible() - comp.getCantidadRequerida();
                    item.setCantidadDisponible(Math.max(nuevaCantidad, 0));
                    inventarioDAO.actualizar(item.getId(), item);
                }
            }

            if (producto.getExtras() != null) {
                for (Extra ext : producto.getExtras()) {
                    InventarioItem item = inventarioDAO.consultarPorId(ext.getInventarioItemId());
                    if (item == null) {
                        continue;
                    }

                    double nuevaCantidad = item.getCantidadDisponible() - ext.getCantidadRequerida();
                    item.setCantidadDisponible(Math.max(nuevaCantidad, 0));
                    inventarioDAO.actualizar(item.getId(), item);
                }
            }
        }

        return true;
    }
}
