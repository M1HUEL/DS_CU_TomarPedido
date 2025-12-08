package com.itson.presentacion.controller.impl;

import com.itson.presentacion.controller.InicioController;
import com.itson.presentacion.frame.CocinaFrame;
import com.itson.presentacion.frame.IniciarSesionFrame;
import com.itson.presentacion.frame.SeleccionarPedidoFrame;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class InicioControllerImpl implements InicioController {

    private final JFrame frame;

    public InicioControllerImpl(JFrame frame) {
        this.frame = frame;
    }

    @Override
    public void crearPedido() {
        new SeleccionarPedidoFrame().setVisible(true);
        frame.dispose();
    }

    @Override
    public void verPedidos() {
        JOptionPane.showMessageDialog(frame, "Historial de ventas en construcción");
    }

    @Override
    public void verCocina() {
        new CocinaFrame().setVisible(true);
        frame.dispose();
    }

    @Override
    public void mostrarDashboard() {
        JOptionPane.showMessageDialog(frame, "Dashboard en construcción");
    }

    @Override
    public void configurar() {
        JOptionPane.showMessageDialog(frame, "Configuración en construcción");
    }

    @Override
    public void cerrarSesion() {
        int confirm = JOptionPane.showConfirmDialog(frame,
                "¿Seguro que deseas salir?", "Cerrar Sesión", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            frame.dispose();
            new IniciarSesionFrame().setVisible(true);
        }
    }
}
