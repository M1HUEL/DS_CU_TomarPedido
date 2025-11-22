package com.itson.presentacion.controlador;

import com.itson.presentacion.frame.InicioFrame;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ConfirmacionPedidoControlador {

    private final JFrame ventanaActual;

    public ConfirmacionPedidoControlador(JFrame ventanaActual) {
        this.ventanaActual = ventanaActual;
    }

    public void confirmarPedido() {
        JOptionPane.showMessageDialog(
                ventanaActual,
                "Pedido confirmado. Volviendo al inicio...",
                "Confirmación",
                JOptionPane.INFORMATION_MESSAGE
        );

        new InicioFrame().setVisible(true);
        ventanaActual.dispose();
    }
}
