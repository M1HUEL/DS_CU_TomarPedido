package com.itson.presentacion.controller;

import com.itson.persistencia.dominio.Producto;
import java.util.List;

public interface SeleccionarPedidoController {

    List<Producto> obtenerProductos();

    void seleccionarProducto(Producto producto);

    void cancelarSeleccion();
}
