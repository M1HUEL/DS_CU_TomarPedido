package com.itson.presentacion.controlador;

import com.itson.presentacion.frame.InicioFrame;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class IniciarSesionControlador {

    private final JFrame ventanaActual;

    public IniciarSesionControlador(JFrame ventanaActual) {
        this.ventanaActual = ventanaActual;
    }

    public void iniciarSesion(String nombre, String contraseña) {
        if (nombre.isEmpty() || contraseña.isEmpty()) {
            mostrarError("Por favor completa todos los campos.");
            return;
        }

        if (validarUsuario(nombre, contraseña)) {
            mostrarMensaje("¡Inicio de sesión correcto!");
            new InicioFrame().setVisible(true);
            ventanaActual.dispose();
        } else {
            mostrarError("Usuario o contraseña incorrectos.");
        }
    }

    public boolean validarUsuario(String nombre, String contraseña) {
        return nombre.equals("Miguel") && contraseña.equals("123");
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

}
