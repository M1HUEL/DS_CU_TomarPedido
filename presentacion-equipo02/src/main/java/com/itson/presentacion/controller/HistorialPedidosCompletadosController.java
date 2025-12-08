package com.itson.presentacion.controller;

import com.itson.persistencia.dominio.Pedido;
import java.util.List;

public interface HistorialPedidosCompletadosController {

    List<Pedido> cargarVentas();

    void salir();

    void generarReportePDF();
}
