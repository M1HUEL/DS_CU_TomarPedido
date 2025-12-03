package com.itson.presentacion.controller.impl;

import com.itson.fachada.RestauranteFachada;
import com.itson.fachada.RestauranteFachadaImpl;
import com.itson.fachada.exception.RestauranteFachadaException;
import com.itson.persistencia.dominio.Producto;
import com.itson.presentacion.controller.SeleccionarPedidoController;
import com.itson.presentacion.frame.PersonalizarPedidoFrame;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class SeleccionarPedidoControllerImpl implements SeleccionarPedidoController {

    private final RestauranteFachada restauranteFachada;
    private final JFrame frame; // Referencia a la vista que controla

    // Inyectamos el frame para poder cerrarlo
    public SeleccionarPedidoControllerImpl(JFrame frame) {
        this.frame = frame;
        this.restauranteFachada = new RestauranteFachadaImpl();
    }

    @Override
    public List<Producto> obtenerProductos() {
        try {
            return restauranteFachada.obtenerProductos();
        } catch (RestauranteFachadaException e) {
            System.err.println("Error fetching products: " + e.getMessage());
            JOptionPane.showMessageDialog(frame, "Error al conectar con la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
            return new ArrayList<>();
        }
    }

    @Override
    public void seleccionarProducto(Producto producto) {
        // 1. Cerrar ventana actual
        if (frame != null) {
            frame.dispose();
        }

        // 2. Abrir la siguiente ventana (Personalizar)
        // NOTA: Idealmente, también usarías un controlador para la siguiente ventana.
        // Por simplicidad, aquí instanciamos el frame directo, pero podrías usar un Factory.
        new PersonalizarPedidoFrame(producto).setVisible(true);
    }

    @Override
    public void cancelarSeleccion() {
        if (frame != null) {
            frame.dispose();
        }
        // Volver al inicio (Necesitas instanciarlo correctamente con su controller)
        // Por simplicidad aquí lo llamamos directo, pero revisa tu main de InicioFrame
        // new InicioFrame().setVisible(true); 
    }
}
