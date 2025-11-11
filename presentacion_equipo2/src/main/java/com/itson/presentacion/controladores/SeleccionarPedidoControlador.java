package com.itson.presentacion.controladores;

import com.itson.presentacion.interfaces.ISeleccionarPedido;
import com.itson.presentacion.pantallas.PersonalizarPedidoPantalla;
import javax.swing.JOptionPane;

public class SeleccionarPedidoControlador implements ISeleccionarPedido {

    @Override
    public void mostrarPedidos() {
        System.out.println("Mostrando lista de pedidos disponibles...");
    }

    @Override
    public void seleccionarPedido(int numeroPedido) {
        JOptionPane.showMessageDialog(null,
                "Has seleccionado el Pedido " + numeroPedido,
                "Pedido seleccionado",
                JOptionPane.INFORMATION_MESSAGE);
        new PersonalizarPedidoPantalla("Pedido " + numeroPedido).setVisible(true);
    }
}
