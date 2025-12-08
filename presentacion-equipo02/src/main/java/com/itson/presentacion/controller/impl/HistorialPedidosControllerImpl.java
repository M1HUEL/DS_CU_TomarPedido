package com.itson.presentacion.controller.impl;

import com.itson.fachada.RestauranteFachada;
import com.itson.fachada.RestauranteFachadaImpl;
import com.itson.fachada.exception.RestauranteFachadaException;
import com.itson.persistencia.dominio.Pedido;
import com.itson.presentacion.controller.HistorialPedidosController;
import com.itson.presentacion.frame.InicioFrame;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;

public class HistorialPedidosControllerImpl implements HistorialPedidosController {

    private final JFrame frame;
    private final RestauranteFachada fachada;

    public HistorialPedidosControllerImpl(JFrame frame) {
        this.frame = frame;
        this.fachada = new RestauranteFachadaImpl();
    }

    @Override
    public List<Pedido> obtenerPedidosEnCurso() {
        try {
            return fachada.obtenerPedidosCocina();
        } catch (RestauranteFachadaException e) {
            System.err.println("Error al obtener historial: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public void regresarInicio() {
        if (frame != null) {
            frame.dispose();
        }
        new InicioFrame().setVisible(true);
    }
}
