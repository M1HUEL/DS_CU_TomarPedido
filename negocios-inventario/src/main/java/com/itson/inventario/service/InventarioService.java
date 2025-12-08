package com.itson.inventario.service;

import com.itson.inventario.exception.InventarioException;
import com.itson.persistencia.dominio.Insumo;
import java.util.List;

public interface InventarioService {

    List<Insumo> obtenerTodosLosInsumos() throws InventarioException;

    List<Insumo> obtenerInsumosConStockBajo() throws InventarioException;

    Insumo obtenerInsumoPorId(String id) throws InventarioException;

    Insumo obtenerInsumoPorCodigo(String codigo) throws InventarioException;

    void registrarInsumo(Insumo insumo) throws InventarioException;

    void actualizarInsumo(Insumo insumo) throws InventarioException;

    void eliminarInsumo(String id) throws InventarioException;

    void reabastecerStock(String idInsumo, Double cantidad) throws InventarioException;

    void consumirStock(String idInsumo, Double cantidad) throws InventarioException;
}
