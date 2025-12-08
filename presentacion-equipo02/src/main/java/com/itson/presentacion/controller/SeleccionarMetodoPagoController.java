package com.itson.presentacion.controller;

import com.itson.persistencia.dominio.Pedido;

public interface SeleccionarMetodoPagoController {

    void procesarPago(Pedido pedido, String metodoPago);

}
