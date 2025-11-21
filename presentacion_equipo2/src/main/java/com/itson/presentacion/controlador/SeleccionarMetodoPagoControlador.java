package com.itson.presentacion.controlador;

import com.itson.presentacion.frame.ConfirmacionPedidoFrame;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class SeleccionarMetodoPagoControlador {

    public void seleccionarMetodoPago(JFrame ventanaActual, String metodo) {

        JOptionPane.showMessageDialog(
                ventanaActual,
                "Método seleccionado: " + metodo + "\nRedirigiendo a confirmación...",
                "Método de pago",
                JOptionPane.INFORMATION_MESSAGE
        );

        ventanaActual.dispose();
        new ConfirmacionPedidoFrame().setVisible(true);
    }
}
