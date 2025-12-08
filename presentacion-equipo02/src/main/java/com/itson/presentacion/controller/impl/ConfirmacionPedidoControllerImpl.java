package com.itson.presentacion.controller.impl;

import com.itson.persistencia.dominio.Pedido;
import com.itson.presentacion.controller.ConfirmacionPedidoController;
import com.itson.presentacion.frame.SeleccionarMetodoPagoFrame;
import javax.swing.SwingUtilities;

public class ConfirmacionPedidoControllerImpl implements ConfirmacionPedidoController {

    public ConfirmacionPedidoControllerImpl() {
    }

    @Override
    public void guardarPedido(Pedido pedido) {
        SwingUtilities.invokeLater(() -> {
            new SeleccionarMetodoPagoFrame(pedido).setVisible(true);
        });
    }
}
