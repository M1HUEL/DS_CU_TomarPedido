package com.itson.controlInventario.servicio;

import com.itson.persistencia.dominio.Pedido;

public interface InventarioServicio {

    boolean hayInventarioSuficiente(Pedido pedido);

    boolean descontarInventario(Pedido pedido);

}
