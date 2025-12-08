package com.itson.presentacion.controller.impl;

import com.itson.fachada.RestauranteFachada;
import com.itson.fachada.RestauranteFachadaImpl;
import com.itson.fachada.exception.RestauranteFachadaException;
import com.itson.persistencia.dominio.Producto;
import com.itson.presentacion.controller.SeleccionarPedidoController;
import com.itson.presentacion.frame.InicioFrame;
import com.itson.presentacion.frame.PersonalizarPedidoFrame;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class SeleccionarPedidoControllerImpl implements SeleccionarPedidoController {

    private final JFrame frame;
    private final RestauranteFachada fachada;

    public SeleccionarPedidoControllerImpl(JFrame frame) {
        this.frame = frame;
        this.fachada = new RestauranteFachadaImpl();
    }

    @Override
    public List<Producto> obtenerProductos() {
        try {
            return fachada.obtenerProductos();
        } catch (RestauranteFachadaException e) {
            System.err.println("Error fetching products: " + e.getMessage());
            JOptionPane.showMessageDialog(frame, "Error al conectar con la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
            return new ArrayList<>();
        }
    }

    @Override
    public void seleccionarProducto(Producto producto) {
        if (frame != null) {
            frame.dispose();
        }
        new PersonalizarPedidoFrame(producto).setVisible(true);
    }

    @Override
    public void cancelarSeleccion() {
        if (frame != null) {
            frame.dispose();
        }

        new InicioFrame().setVisible(true);
    }
}
