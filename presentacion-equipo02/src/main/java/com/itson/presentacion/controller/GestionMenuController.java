package com.itson.presentacion.controller;

import com.itson.persistencia.dominio.Producto;
import java.util.List;

public interface GestionMenuController {

    List<Producto> cargarMenu();

    void agregarProducto();

    void editarProducto(Producto producto);

    void eliminarProducto(Producto producto);

    void regresar();
}
