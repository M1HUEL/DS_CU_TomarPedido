package com.itson.producto.servicio.impl;

import com.itson.persistencia.dao.ProductoDAO;
import com.itson.persistencia.dao.impl.ProductoDAOImpl;
import com.itson.persistencia.dominio.Producto;
import com.itson.persistencia.exception.PersistenciaException;
import com.itson.producto.exception.ProductoException;
import com.itson.producto.servicio.ProductoServicio;
import java.util.List;

public class ProductoServicioImpl implements ProductoServicio {

    private final ProductoDAO productoDAO;

    public ProductoServicioImpl() {
        this.productoDAO = new ProductoDAOImpl();
    }

    @Override
    public List<Producto> obtenerProductos() throws ProductoException {
        try {
            return productoDAO.consultarTodos();
        } catch (PersistenciaException ex) {
            throw new ProductoException("Error al obtener todos los productos", ex);
        }
    }

    @Override
    public Producto obtenerProductoPorId(String id) throws ProductoException {
        try {
            return productoDAO.consultarPorId(id);
        } catch (PersistenciaException ex) {
            throw new ProductoException("Error al obtener el producto por id: " + id, ex);
        }
    }

    @Override
    public Producto obtenerProductoPorNombre(String nombre) throws ProductoException {
        try {
            return productoDAO.consultarPorNombre(nombre);
        } catch (PersistenciaException ex) {
            throw new ProductoException("Error al obtener el producto por nombre: " + nombre, ex);
        }
    }

    @Override
    public void agregarProducto(Producto producto) throws ProductoException {
        try {
            productoDAO.agregar(producto);
        } catch (PersistenciaException ex) {
            throw new ProductoException("Error al agregar el producto", ex);
        }
    }

    @Override
    public void actualizarProducto(String id, Producto producto) throws ProductoException {
        try {
            productoDAO.actualizar(id, producto);
        } catch (PersistenciaException ex) {
            throw new ProductoException("Error al actualizar el producto con id: " + id, ex);
        }
    }

    @Override
    public void eliminarProducto(String id) throws ProductoException {
        try {
            productoDAO.eliminar(id);
        } catch (PersistenciaException ex) {
            throw new ProductoException("Error al eliminar el producto con id: " + id, ex);
        }
    }
}
