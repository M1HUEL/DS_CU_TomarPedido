package com.itson.presentacion.interfaces;

import javax.swing.JFrame;

public interface IIniciarSesion {

    void iniciarSesion(String nombre, String contraseña, JFrame ventanaActual);

    boolean validarUsuario(String nombre, String contraseña);

    void mostrarMensaje(String mensaje);

    void mostrarError(String mensaje);

}
