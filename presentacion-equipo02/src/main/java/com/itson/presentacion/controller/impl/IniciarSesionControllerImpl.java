package com.itson.presentacion.controller.impl;

import com.itson.fachada.RestauranteFachada;
import com.itson.fachada.RestauranteFachadaImpl;
import com.itson.fachada.exception.RestauranteFachadaException;
import com.itson.persistencia.dominio.Usuario;
import com.itson.presentacion.controller.IniciarSesionController;
import com.itson.presentacion.frame.InicioFrame;
import com.itson.util.sesion.Sesion;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class IniciarSesionControllerImpl implements IniciarSesionController {

    private final JFrame frame;
    private final RestauranteFachada fachada;

    public IniciarSesionControllerImpl(JFrame frame) {
        this.fachada = new RestauranteFachadaImpl();
        this.frame = frame;
    }

    @Override
    public void iniciarSesion(String nombre, String password) {
        try {
            Usuario usuario = fachada.login(nombre, password);

            JOptionPane.showMessageDialog(
                    frame,
                    "¡Bienvenido " + usuario.getNombre() + "!",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE
            );

            Sesion.getInstancia().setUsuarioLogueado(usuario);

            frame.dispose();

            new InicioFrame().setVisible(true);
        } catch (RestauranteFachadaException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(
                    frame,
                    "Ocurrió un error inesperado en el sistema.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
