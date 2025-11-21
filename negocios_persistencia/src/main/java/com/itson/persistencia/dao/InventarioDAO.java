package com.itson.persistencia.dao;

import com.itson.persistencia.dominio.InventarioItem;
import com.itson.persistencia.dominio.InventarioTipo;
import java.util.List;

public interface InventarioDAO {

    List<InventarioItem> consultarTodos();

    InventarioItem consultarPorId(String id);

    InventarioItem consultarPorNombre(String nombre);

    List<InventarioItem> consultarPorTipo(InventarioTipo tipo);

    boolean agregar(InventarioItem item);

    boolean actualizar(String id, InventarioItem item);

    boolean eliminar(String id);

}
