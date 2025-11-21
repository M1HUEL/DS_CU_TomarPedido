package com.itson.persistencia.dao;

import com.itson.persistencia.dominio.Producto;
import java.util.List;

public interface ProductoDAO {

    List<Producto> consultarTodos();

    Producto consultar(String id);

    boolean agregar(Producto producto);

    boolean actualizar(String id, Producto producto);

    boolean eliminar(String id);

}
