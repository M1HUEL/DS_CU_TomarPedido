package com.itson.presentacion.controlador;

import com.itson.presentacion.gui.PersonalizarPedidoPantalla;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class SeleccionarPedidoControlador {

    public void seleccionarPedido(JFrame ventanaActual) {
        JOptionPane.showMessageDialog(
                ventanaActual,
                "Pedido seleccionado correctamente.",
                "Pedido",
                JOptionPane.INFORMATION_MESSAGE
        );

        ventanaActual.dispose();
        new PersonalizarPedidoPantalla().setVisible(true);
    }
}
