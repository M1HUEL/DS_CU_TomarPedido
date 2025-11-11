package com.itson.presentacion.controlador;

import com.itson.presentacion.gui.PersonalizarPedidoPantalla;
import javax.swing.JOptionPane;

public class SeleccionarPedidoControlador {

    public void mostrarPedidos() {
        System.out.println("Mostrando lista de pedidos disponibles...");
    }

    public void seleccionarPedido(int numeroPedido) {
        JOptionPane.showMessageDialog(null,
                "Has seleccionado el Pedido " + numeroPedido,
                "Pedido seleccionado",
                JOptionPane.INFORMATION_MESSAGE);
        new PersonalizarPedidoPantalla().setVisible(true);
    }
}
