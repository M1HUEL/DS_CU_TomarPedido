package com.itson.presentacion.controller;

import com.itson.persistencia.dominio.Pago;
import com.itson.persistencia.dominio.Pedido;

public interface ConfirmacionPagoController {

    void terminarProceso();
    
    void generarReciboPDF(Pedido pedido, Pago pago);

}
