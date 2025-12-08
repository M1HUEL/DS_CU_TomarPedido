package com.itson.persistencia.dao;

import com.itson.persistencia.dominio.Insumo;
import com.itson.persistencia.exception.PersistenciaException;
import java.util.List;

public interface InsumoDAO {

    List<Insumo> consultarTodos() throws PersistenciaException;

    Insumo consultarPorId(String id) throws PersistenciaException;

    Insumo consultarPorCodigo(String codigo) throws PersistenciaException;

    void agregar(Insumo insumo) throws PersistenciaException;

    void actualizar(String id, Insumo insumo) throws PersistenciaException;

    void eliminar(String id) throws PersistenciaException;

    void aumentarStock(String id, Double cantidad) throws PersistenciaException;

    boolean descontarStock(String id, Double cantidad) throws PersistenciaException;
}
