package com.itson.presentacion.controller;

import com.itson.persistencia.dominio.Pedido;
import java.util.List;

public interface CocinaController {

    List<Pedido> obtenerPedidosPendientes();

    void avanzarEstado(Pedido pedido);

    void regresarInicio();
}
