package com.itson.presentacion.controlador;

import com.itson.presentacion.gui.SeleccionarMetodoPagoPantalla;
import com.itson.presentacion.gui.SeleccionarPedidoPantalla;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class PersonalizarPedidoControlador {

    public void confirmarPersonalizacion(JFrame ventanaActual) {
        JOptionPane.showMessageDialog(
                ventanaActual,
                "Personalización confirmada. Procediendo a seleccionar método de pago...",
                "Confirmación",
                JOptionPane.INFORMATION_MESSAGE
        );

        ventanaActual.dispose();
        new SeleccionarMetodoPagoPantalla().setVisible(true);
    }

    public void cancelarPersonalizacion(JFrame ventanaActual) {
        int opcion = JOptionPane.showConfirmDialog(
                ventanaActual,
                "¿Deseas cancelar la personalización y volver a seleccionar un pedido?",
                "Cancelar personalización",
                JOptionPane.YES_NO_OPTION
        );

        if (opcion == JOptionPane.YES_OPTION) {
            ventanaActual.dispose();
            new SeleccionarPedidoPantalla().setVisible(true);
        }
    }
}
