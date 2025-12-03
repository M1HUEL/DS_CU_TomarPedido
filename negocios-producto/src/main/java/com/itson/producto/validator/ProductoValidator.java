package com.itson.producto.validator;

import com.itson.persistencia.dao.ProductoDAO;
import com.itson.persistencia.dominio.Producto;
import com.itson.persistencia.exception.PersistenciaException;
import com.itson.producto.exception.ProductoException;

public class ProductoValidator {

    private final ProductoDAO productoDAO;

    public ProductoValidator(ProductoDAO productoDAO) {
        this.productoDAO = productoDAO;
    }

    public void validarRegistro(Producto producto) throws ProductoException {
        validarDatosBasicos(producto);
        validarNombreUnico(producto.getNombre(), null);
    }

    public void validarActualizacion(String id, Producto producto) throws ProductoException {
        if (id == null || id.trim().isEmpty()) {
            throw new ProductoException("Se requiere el ID para actualizar un producto.");
        }
        validarDatosBasicos(producto);
        validarExistencia(id);
        validarNombreUnico(producto.getNombre(), id);
    }

    public void validarEliminacion(String id) throws ProductoException {
        if (id == null || id.trim().isEmpty()) {
            throw new ProductoException("El ID es obligatorio.");
        }
        validarExistencia(id);
    }

    private void validarDatosBasicos(Producto producto) throws ProductoException {
        if (producto == null) {
            throw new ProductoException("La información del producto no puede ser nula.");
        }
        if (producto.getNombre() == null || producto.getNombre().trim().isEmpty()) {
            throw new ProductoException("El nombre del producto es obligatorio.");
        }
        if (producto.getPrecio() < 0) {
            throw new ProductoException("El precio del producto no puede ser negativo.");
        }
    }

    private void validarNombreUnico(String nombre, String idExcluir) throws ProductoException {
        try {
            Producto existente = productoDAO.consultarPorNombre(nombre);
            if (existente != null) {
                if (idExcluir != null && existente.getId().equals(idExcluir)) {
                    return;
                }
                throw new ProductoException("Ya existe un producto registrado con el nombre: " + nombre);
            }
        } catch (PersistenciaException ex) {
            throw new ProductoException("Error al validar el nombre del producto.", ex);
        }
    }

    private void validarExistencia(String id) throws ProductoException {
        try {
            if (productoDAO.consultarPorId(id) == null) {
                throw new ProductoException("No se encontró el producto con ID: " + id);
            }
        } catch (PersistenciaException ex) {
            throw new ProductoException("Error al verificar la existencia del producto.", ex);
        }
    }
}
