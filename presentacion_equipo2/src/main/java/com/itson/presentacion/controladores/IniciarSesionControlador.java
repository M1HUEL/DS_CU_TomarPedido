package com.itson.presentacion.controladores;

import com.itson.presentacion.interfaces.IIniciarSesion;
import com.itson.presentacion.pantallas.InicioPantalla;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class IniciarSesionControlador implements IIniciarSesion {

    @Override
    public void iniciarSesion(String nombre, String contraseña, JFrame ventanaActual) {
        if (nombre.isEmpty() || contraseña.isEmpty()) {
            mostrarError("Por favor completa todos los campos.");
            return;
        }

        if (validarUsuario(nombre, contraseña)) {
            mostrarMensaje("¡Inicio de sesión correcto!");
            new InicioPantalla().setVisible(true);
            ventanaActual.dispose();
        } else {
            mostrarError("Usuario o contraseña incorrectos.");
        }
    }

    @Override
    public boolean validarUsuario(String nombre, String contraseña) {
        return nombre.equals("Miguel") && contraseña.equals("123");
    }

    @Override
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

}
