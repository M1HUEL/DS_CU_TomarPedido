package com.itson.presentacion.pantallas;

import com.itson.presentacion.controladores.SeleccionarMetodoPagoControlador;
import com.itson.presentacion.interfaces.ISeleccionarMetodoPago;
import com.itson.presentacion.pantallas.componentes.Boton;
import com.itson.presentacion.pantallas.componentes.Encabezado;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class SeleccionarMetodoPagoPantalla extends JFrame {

    public SeleccionarMetodoPagoPantalla() {
        setTitle("Seleccionar Método de Pago");
        setSize(600, 720);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        add(new Encabezado("Seleccionar Método de Pago"), BorderLayout.NORTH);

        ISeleccionarMetodoPago controlador = new SeleccionarMetodoPagoControlador(this);
        JPanel contenido = new JPanel(new GridLayout(0, 1, 0, 20));
        contenido.setBackground(new Color(255, 252, 245));
        contenido.setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));

        for (Boton boton : controlador.generarBotonesMetodoPago()) {
            contenido.add(boton);
        }

        add(contenido, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SeleccionarMetodoPagoPantalla pantalla = new SeleccionarMetodoPagoPantalla();
            pantalla.setVisible(true);
        });
    }
}
