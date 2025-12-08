package com.itson.persistencia.dao;

import com.itson.persistencia.dominio.Pago;
import com.itson.persistencia.exception.PersistenciaException;
import java.util.List;

public interface PagoDAO {

    List<Pago> consultarTodos() throws PersistenciaException;

    Pago consultarPorId(String id) throws PersistenciaException;

    Pago consultarPorPedidoId(String pedidoId) throws PersistenciaException;

    void agregar(Pago pago) throws PersistenciaException;

    void actualizar(String id, Pago pago) throws PersistenciaException;

    void actualizarEstado(String id, String nuevoEstado) throws PersistenciaException;

    void eliminar(String id) throws PersistenciaException;
}
