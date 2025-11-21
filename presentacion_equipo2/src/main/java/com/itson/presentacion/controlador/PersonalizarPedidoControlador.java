package com.itson.presentacion.controlador;

import com.itson.presentacion.frame.SeleccionarMetodoPagoFrame;
import com.itson.presentacion.frame.SeleccionarPedidoFrame;
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
        new SeleccionarMetodoPagoFrame().setVisible(true);
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
            new SeleccionarPedidoFrame().setVisible(true);
        }
    }
}
