package com.itson.persistencia.daos;

import com.itson.controlPedido.objetosNegocio.Cajero;
import java.util.List;

public interface CajeroDAO {

    List<Cajero> obtenerTodos();

    Cajero obtenerPorId(int id);

    void agregar(Cajero cajero);

    boolean actualizar(int id, Cajero cajero);

    boolean eliminar(int id);

}
