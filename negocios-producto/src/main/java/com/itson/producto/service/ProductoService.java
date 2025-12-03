package com.itson.producto.service;

import com.itson.persistencia.dominio.Producto;
import com.itson.producto.exception.ProductoException;
import java.util.List;

public interface ProductoService {

    List<Producto> obtenerProductos() throws ProductoException;

    Producto obtenerProductoPorId(String id) throws ProductoException;

    Producto obtenerProductoPorNombre(String nombre) throws ProductoException;

    void agregarProducto(Producto producto) throws ProductoException;

    void actualizarProducto(String id, Producto producto) throws ProductoException;

    void eliminarProducto(String id) throws ProductoException;
}
