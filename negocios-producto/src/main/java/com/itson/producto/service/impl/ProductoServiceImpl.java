package com.itson.producto.service.impl;

import com.itson.persistencia.dao.ProductoDAO;
import com.itson.persistencia.dao.impl.ProductoDAOImpl;
import com.itson.persistencia.dominio.Producto;
import com.itson.persistencia.exception.PersistenciaException;
import com.itson.producto.exception.ProductoException;
import com.itson.producto.service.ProductoService;
import com.itson.producto.validator.ProductoValidator;
import java.util.List;

public class ProductoServiceImpl implements ProductoService {

    private final ProductoDAO productoDAO;
    private final ProductoValidator validador;

    public ProductoServiceImpl() {
        this.productoDAO = new ProductoDAOImpl();
        this.validador = new ProductoValidator(this.productoDAO);
    }

    @Override
    public List<Producto> obtenerProductos() throws ProductoException {
        try {
            return productoDAO.consultarTodos();
        } catch (PersistenciaException ex) {
            throw new ProductoException("Error al obtener el catálogo de productos.", ex);
        }
    }

    @Override
    public Producto obtenerProductoPorId(String id) throws ProductoException {
        try {
            return productoDAO.consultarPorId(id);
        } catch (PersistenciaException ex) {
            throw new ProductoException("Error al buscar el producto con ID: " + id, ex);
        }
    }

    @Override
    public Producto obtenerProductoPorNombre(String nombre) throws ProductoException {
        try {
            return productoDAO.consultarPorNombre(nombre);
        } catch (PersistenciaException ex) {
            throw new ProductoException("Error al buscar el producto: " + nombre, ex);
        }
    }

    @Override
    public void agregarProducto(Producto producto) throws ProductoException {
        validador.validarRegistro(producto);

        try {
            productoDAO.agregar(producto);
        } catch (PersistenciaException ex) {
            throw new ProductoException("Error al guardar el nuevo producto.", ex);
        }
    }

    @Override
    public void actualizarProducto(String id, Producto producto) throws ProductoException {
        validador.validarActualizacion(id, producto);

        try {
            productoDAO.actualizar(id, producto);
        } catch (PersistenciaException ex) {
            throw new ProductoException("Error al actualizar el producto.", ex);
        }
    }

    @Override
    public void eliminarProducto(String id) throws ProductoException {
        validador.validarEliminacion(id);

        try {
            productoDAO.eliminar(id);
        } catch (PersistenciaException ex) {
            throw new ProductoException("Error al eliminar el producto.", ex);
        }
    }
}
