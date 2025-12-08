package com.itson.presentacion.controller.impl;

import com.itson.fachada.RestauranteFachada;
import com.itson.fachada.RestauranteFachadaImpl;
import com.itson.fachada.exception.RestauranteFachadaException;
import com.itson.persistencia.dominio.EstadoPedido;
import com.itson.persistencia.dominio.Pedido;
import com.itson.presentacion.controller.CocinaController;
import com.itson.presentacion.frame.CocinaFrame;
import com.itson.presentacion.frame.InicioFrame;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class CocinaControllerImpl implements CocinaController {

    private final JFrame frame;
    private final RestauranteFachada fachada;

    public CocinaControllerImpl(JFrame frame) {
        this.frame = frame;
        this.fachada = new RestauranteFachadaImpl();
    }

    @Override
    public List<Pedido> obtenerPedidosPendientes() {
        try {
            List<Pedido> todos = fachada.obtenerPedidos();
            List<Pedido> activos = new ArrayList<>();

            for (Pedido p : todos) {
                if (p.getEstado() != EstadoPedido.ENTREGADO) {
                    activos.add(p);
                }
            }
            return activos;

        } catch (RestauranteFachadaException e) {
            System.err.println("Error al cargar pedidos: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public void avanzarEstado(Pedido pedido) {
        EstadoPedido siguienteEstado = null;

        switch (pedido.getEstado()) {
            case PENDIENTE ->
                siguienteEstado = EstadoPedido.EN_PREPARACION;
            case EN_PREPARACION ->
                siguienteEstado = EstadoPedido.LISTO;
            case LISTO ->
                siguienteEstado = EstadoPedido.ENTREGADO;
            default -> {
                return;
            }
        }

        try {
            fachada.actualizarEstadoPedido(pedido.getId(), siguienteEstado);
            pedido.setEstado(siguienteEstado);

            if (frame instanceof CocinaFrame cocinaFrame) {
                cocinaFrame.cargarPedidos();
            }

        } catch (RestauranteFachadaException e) {
            JOptionPane.showMessageDialog(frame, "Error al cambiar estado: " + e.getMessage());
        }
    }

    @Override
    public void regresarInicio() {
        frame.dispose();
        new InicioFrame().setVisible(true);
    }
}
