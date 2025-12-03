package com.itson.persistencia.dao;

import com.itson.persistencia.dominio.Pedido;
import com.itson.persistencia.exception.PersistenciaException;
import java.util.List;

public interface PedidoDAO {

    List<Pedido> consultarTodos() throws PersistenciaException;

    Pedido consultarPorId(String productoId) throws PersistenciaException;

    Pedido consultarPorNombre(String nombre) throws PersistenciaException;

    void agregar(Pedido pedido) throws PersistenciaException;

    void actualizar(String pedidoId, Pedido pedido) throws PersistenciaException;

    void eliminar(String pedidoId) throws PersistenciaException;
}
