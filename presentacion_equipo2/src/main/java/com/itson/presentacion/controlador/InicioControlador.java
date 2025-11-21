package com.itson.presentacion.controlador;

import com.itson.presentacion.frame.IniciarSesionFrame;
import com.itson.presentacion.frame.SeleccionarPedidoFrame;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class InicioControlador {

    public void abrirSeleccionarPedido(JFrame ventanaActual) {
        ventanaActual.dispose();
        new SeleccionarPedidoFrame().setVisible(true);
    }

    public void verPedidos(JFrame ventanaActual) {
        JOptionPane.showMessageDialog(
                ventanaActual,
                "Funcionalidad de ver pedidos aún no implementada.",
                "Información",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    public void abrirDashboard(JFrame ventanaActual) {
        JOptionPane.showMessageDialog(
                ventanaActual,
                "Funcionalidad de dashboard aún no implementada.",
                "Información",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    public void cerrarSesion(JFrame ventanaActual) {
        int opcion = JOptionPane.showConfirmDialog(
                ventanaActual,
                "¿Deseas cerrar sesión?",
                "Confirmar",
                JOptionPane.YES_NO_OPTION
        );

        if (opcion == JOptionPane.YES_OPTION) {
            ventanaActual.dispose();
            new IniciarSesionFrame().setVisible(true);
        }
    }
}
