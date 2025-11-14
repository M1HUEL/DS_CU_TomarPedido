package com.itson.presentacion.controlador;

import com.itson.presentacion.gui.ConfirmacionPedidoPantalla;
import com.itson.presentacion.gui.InicioPantalla;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ConfirmacionPedidoControlador {

    public void confirmarPedido(JFrame ventanaActual) {
        JOptionPane.showMessageDialog(
                ventanaActual,
                "Pedido confirmado. Volviendo al inicio...",
                "Confirmación",
                JOptionPane.INFORMATION_MESSAGE
        );

        new InicioPantalla().setVisible(true);
        ventanaActual.dispose();
    }
}
