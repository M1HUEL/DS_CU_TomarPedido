package com.itson.presentacion.controller.impl;

import com.itson.fachada.RestauranteFachada;
import com.itson.fachada.RestauranteFachadaImpl;
import com.itson.fachada.exception.RestauranteFachadaException;
import com.itson.persistencia.dominio.EstadoPago;
import com.itson.persistencia.dominio.MetodoPago;
import com.itson.persistencia.dominio.Pago;
import com.itson.persistencia.dominio.Pedido;
import com.itson.presentacion.controller.SeleccionarMetodoPagoController;
import com.itson.presentacion.frame.ConfirmacionPagoFrame;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class SeleccionarMetodoPagoControllerImpl implements SeleccionarMetodoPagoController {

    private final JFrame frame;
    private final RestauranteFachada restauranteFachada;

    public SeleccionarMetodoPagoControllerImpl(JFrame frame) {
        this.frame = frame;
        this.restauranteFachada = new RestauranteFachadaImpl();
    }

    @Override
    public void procesarPago(Pedido pedido, String metodoPagoTexto) {
        try {
            System.out.println("--- INICIANDO TRANSACCIÓN ---");

            Pago nuevoPago = new Pago();
            nuevoPago.setMonto(pedido.getPrecio());
            nuevoPago.setMetodoPago(convertirMetodo(metodoPagoTexto));
            nuevoPago.setEstado(EstadoPago.APROBADO);

            restauranteFachada.realizarVentaCompleta(pedido, nuevoPago);

            System.out.println("Venta completada. Pedido ID: " + pedido.getId());

            if (frame != null) {
                frame.dispose();
            }

            SwingUtilities.invokeLater(() -> {
                new ConfirmacionPagoFrame(pedido, nuevoPago).setVisible(true);
            });

        } catch (RestauranteFachadaException e) {
            e.printStackTrace();
            System.err.println("Fallo en la transacción: " + e.getMessage());

            JOptionPane.showMessageDialog(frame,
                    "Error al procesar la transacción:\n" + e.getMessage(),
                    "Pago Rechazado",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private MetodoPago convertirMetodo(String texto) {
        if (texto == null) {
            return MetodoPago.EFECTIVO;
        }

        String t = texto.toLowerCase();

        if (t.contains("tarjeta")) {
            return MetodoPago.TARJETA_CREDITO;
        } else if (t.contains("paypal")) {
            return MetodoPago.PAYPAL;
        } else {
            return MetodoPago.EFECTIVO;
        }
    }
}
