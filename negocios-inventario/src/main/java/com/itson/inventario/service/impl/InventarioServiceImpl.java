package com.itson.inventario.service.impl;

import com.itson.inventario.exception.InventarioException;
import com.itson.persistencia.dao.InsumoDAO;
import com.itson.persistencia.dao.impl.InsumoDAOImpl;
import com.itson.persistencia.dominio.Insumo;
import com.itson.persistencia.exception.PersistenciaException;
import java.util.List;
import com.itson.inventario.service.InventarioService;

public class InventarioServiceImpl implements InventarioService {

    private final InsumoDAO insumoDAO;

    public InventarioServiceImpl() {
        this.insumoDAO = new InsumoDAOImpl();
    }

    @Override
    public List<Insumo> obtenerTodosLosInsumos() throws InventarioException {
        try {
            return insumoDAO.consultarTodos();
        } catch (PersistenciaException e) {
            throw new InventarioException("Error al obtener el listado de insumos.", e);
        }
    }

    @Override
    public Insumo obtenerInsumoPorId(String id) throws InventarioException {
        try {
            return insumoDAO.consultarPorId(id);
        } catch (PersistenciaException e) {
            throw new InventarioException("Error al buscar el insumo por ID: " + id, e);
        }
    }

    @Override
    public Insumo obtenerInsumoPorCodigo(String codigo) throws InventarioException {
        try {
            return insumoDAO.consultarPorCodigo(codigo);
        } catch (PersistenciaException e) {
            throw new InventarioException("Error al buscar el insumo por código: " + codigo, e);
        }
    }

    @Override
    public void registrarInsumo(Insumo insumo) throws InventarioException {
        if (insumo.getNombre() == null || insumo.getNombre().trim().isEmpty()) {
            throw new InventarioException("El nombre del insumo es obligatorio.");
        }
        if (insumo.getCodigo() == null || insumo.getCodigo().trim().isEmpty()) {
            throw new InventarioException("El código del insumo es obligatorio.");
        }
        if (insumo.getStockActual() < 0) {
            throw new InventarioException("El stock inicial no puede ser negativo.");
        }

        try {
            Insumo existente = insumoDAO.consultarPorCodigo(insumo.getCodigo());
            if (existente != null) {
                throw new InventarioException("Ya existe un insumo registrado con el código: " + insumo.getCodigo());
            }

            insumoDAO.agregar(insumo);
        } catch (PersistenciaException e) {
            throw new InventarioException("Error al registrar el nuevo insumo.", e);
        }
    }

    @Override
    public void actualizarInsumo(Insumo insumo) throws InventarioException {
        if (insumo.getId() == null) {
            throw new InventarioException("El insumo debe tener un ID para ser actualizado.");
        }

        try {
            insumoDAO.actualizar(insumo.getId(), insumo);
        } catch (PersistenciaException e) {
            throw new InventarioException("Error al actualizar el insumo.", e);
        }
    }

    @Override
    public void eliminarInsumo(String id) throws InventarioException {
        try {
            insumoDAO.eliminar(id);
        } catch (PersistenciaException e) {
            throw new InventarioException("Error al eliminar el insumo.", e);
        }
    }

    @Override
    public void reabastecerStock(String idInsumo, Double cantidad) throws InventarioException {
        if (cantidad <= 0) {
            throw new InventarioException("La cantidad a reabastecer debe ser mayor a cero.");
        }

        try {
            insumoDAO.aumentarStock(idInsumo, cantidad);
        } catch (PersistenciaException e) {
            throw new InventarioException("Error al intentar reabastecer el inventario.", e);
        }
    }

    @Override
    public void consumirStock(String idInsumo, Double cantidad) throws InventarioException {
        if (cantidad <= 0) {
            throw new InventarioException("La cantidad a consumir debe ser mayor a cero.");
        }

        try {
            boolean exito = insumoDAO.descontarStock(idInsumo, cantidad);

            if (!exito) {
                Insumo insumo = insumoDAO.consultarPorId(idInsumo);

                String nombreInsumo = "Desconocido";
                if (insumo != null) {
                    nombreInsumo = insumo.getNombre();
                }

                throw new InventarioException("Stock insuficiente para el insumo: " + nombreInsumo);
            }

        } catch (PersistenciaException e) {
            throw new InventarioException("Error crítico al procesar el consumo de stock.", e);
        }
    }
}
