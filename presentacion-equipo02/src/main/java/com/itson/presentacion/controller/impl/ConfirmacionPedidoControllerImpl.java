package com.itson.presentacion.controller.impl;

import com.itson.fachada.RestauranteFachada;
import com.itson.fachada.RestauranteFachadaImpl;
import com.itson.persistencia.dominio.Pedido;
import com.itson.presentacion.frame.SeleccionarPedidoFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import com.itson.presentacion.controller.ConfirmacionPedidoController;

public class ConfirmacionPedidoControllerImpl implements ConfirmacionPedidoController {

    private final RestauranteFachada restauranteFachada;

    public ConfirmacionPedidoControllerImpl() {
        this.restauranteFachada = new RestauranteFachadaImpl();
    }

    @Override
    public void guardarPedido(Pedido pedido) {
        try {
            // 1. Call the Facade to save to Database
            restauranteFachada.crearPedido(pedido);

            // 2. Success Message
            JOptionPane.showMessageDialog(null,
                    "¡Tu pedido ha sido guardado con éxito!\n"
                    + "ID: " + pedido.getId() + "\n"
                    + // Assuming ID is generated and returned
                    "Por favor pasa a caja a pagar.",
                    "Pedido Confirmado",
                    JOptionPane.INFORMATION_MESSAGE
            );

            // 3. Return to Main Screen (Restart flow)
            SwingUtilities.invokeLater(() -> {
                new SeleccionarPedidoFrame().setVisible(true);
            });

        } catch (Exception e) {
            System.err.println("Error al guardar pedido: " + e.getMessage());
            JOptionPane.showMessageDialog(null,
                    "Hubo un error al guardar tu pedido.\n" + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
