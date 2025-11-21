package com.itson.presentacion.controlador;

import com.itson.presentacion.frame.ConfirmacionPedidoFrame;
import com.itson.presentacion.frame.InicioFrame;
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

        new InicioFrame().setVisible(true);
        ventanaActual.dispose();
    }
}
