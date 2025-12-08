package com.itson.persistencia.dao;

import com.itson.persistencia.dominio.Producto;
import com.itson.persistencia.exception.PersistenciaException;
import java.util.List;

public interface ProductoDAO {

    List<Producto> consultarTodos() throws PersistenciaException;

    Producto consultarPorId(String productoId) throws PersistenciaException;

    Producto consultarPorNombre(String nombre) throws PersistenciaException;

    void agregar(Producto producto) throws PersistenciaException;

    void actualizar(String productoId, Producto producto) throws PersistenciaException;

    void eliminar(String productoId) throws PersistenciaException;
}
