package com.itson.presentacion.controlador.impl;

import com.itson.fachada.RestauranteFachada;
import com.itson.fachada.RestauranteFachadaImpl;
import com.itson.fachada.exception.RestauranteFachadaException;
import com.itson.persistencia.dominio.Producto;
import com.itson.presentacion.controlador.SeleccionarPedidoControlador;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class SeleccionarPedidoControladorImpl implements SeleccionarPedidoControlador {

    private final RestauranteFachada restauranteFachada;

    public SeleccionarPedidoControladorImpl() {
        // Initialize the connection to the Business Logic Layer
        this.restauranteFachada = new RestauranteFachadaImpl();
    }

    @Override
    public List<Producto> obtenerProductos() {
        try {
            // Call the facade which calls the service -> DAO -> Database
            return restauranteFachada.obtenerProductos();
        } catch (RestauranteFachadaException e) {
            System.err.println("Error fetching products: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
            return new ArrayList<>(); // Return empty list to prevent crash
        }
    }
}
