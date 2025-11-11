package com.itson.presentacion.controlador;

import com.itson.presentacion.gui.IniciarSesionPantalla;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class InicioControlador {

    public void abrirSeleccionarPedido(JFrame ventanaActual) {
        JOptionPane.showMessageDialog(ventanaActual,
                "Abriendo pantalla de selección de pedido...",
                "Navegación",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public void abrirDashboard(JFrame ventanaActual) {
        JOptionPane.showMessageDialog(ventanaActual,
                "Abriendo Dashboard...",
                "Navegación",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public void cerrarSesion(JFrame ventanaActual) {
        int confirmacion = JOptionPane.showConfirmDialog(
                ventanaActual,
                "¿Deseas cerrar sesión?",
                "Confirmar",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmacion == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(ventanaActual,
                    "Sesión cerrada correctamente.");
            new IniciarSesionPantalla().setVisible(true);
            ventanaActual.dispose();
        }
    }

}
