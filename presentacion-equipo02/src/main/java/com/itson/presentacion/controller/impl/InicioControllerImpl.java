package com.itson.presentacion.controller.impl;

import com.itson.presentacion.controller.InicioController;
import com.itson.presentacion.frame.SeleccionarPedidoFrame;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class InicioControllerImpl implements InicioController {

    // Atributo global para manipular la ventana
    private final JFrame frame;

    // Inyectamos el frame en el constructor
    public InicioControllerImpl(JFrame frame) {
        this.frame = frame;
    }

    @Override
    public void crearPedido() {
        new SeleccionarPedidoFrame().setVisible(true);
        // Cerramos la ventana usando el atributo global
        frame.dispose();
    }

    @Override
    public void verPedidos() {
        JOptionPane.showMessageDialog(frame, "Función Ver Pedidos en construcción");
    }

    @Override
    public void mostrarDashboard() {
        JOptionPane.showMessageDialog(frame, "Función Dashboard en construcción");
    }

    @Override
    public void configurar() {
        JOptionPane.showMessageDialog(frame, "Función Configuración en construcción");
    }

    @Override
    public void cerrarSesion() {
        int confirm = JOptionPane.showConfirmDialog(frame,
                "¿Seguro que deseas salir?",
                "Cerrar Sesión",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
}
