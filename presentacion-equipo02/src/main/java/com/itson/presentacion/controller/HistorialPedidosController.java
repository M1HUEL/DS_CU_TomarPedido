package com.itson.presentacion.controller;

import com.itson.persistencia.dominio.Pedido;
import java.util.List;

public interface HistorialPedidosController {

    List<Pedido> obtenerPedidosEnCurso();

    void regresarInicio();
}
 