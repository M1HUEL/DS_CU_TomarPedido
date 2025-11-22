package com.itson.presentacion.controlador;

import com.itson.presentacion.frame.IniciarSesionFrame;
import com.itson.presentacion.frame.SeleccionarPedidoFrame;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class InicioControlador {

    private final JFrame ventanaActual;

    public InicioControlador(JFrame ventanaActual) {
        this.ventanaActual = ventanaActual;
    }

    public void abrirSeleccionarPedido() {
        ventanaActual.dispose();
        new SeleccionarPedidoFrame().setVisible(true);
    }

    public void verPedidos() {
        JOptionPane.showMessageDialog(
                ventanaActual,
                "Funcionalidad de ver pedidos aún no implementada.",
                "Información",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    public void abrirDashboard() {
        JOptionPane.showMessageDialog(
                ventanaActual,
                "Funcionalidad de dashboard aún no implementada.",
                "Información",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    public void cerrarSesion() {
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
