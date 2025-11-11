package com.itson.presentacion.pantallas;

import com.itson.presentacion.controladores.InicioControlador;
import com.itson.presentacion.interfaces.IInicio;
import com.itson.presentacion.pantallas.componentes.Boton;
import com.itson.presentacion.pantallas.componentes.Encabezado;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class InicioPantalla extends JFrame {

    public InicioPantalla() {
        setTitle("🍽️ Sistema Restaurante - Inicio");
        setSize(720, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(255, 250, 240));

        add(new Encabezado("Inicio"), BorderLayout.NORTH);

        JPanel contenedor = new JPanel(new GridBagLayout());
        contenedor.setBackground(new Color(255, 250, 240));

        JPanel tarjeta = new JPanel(new BorderLayout());
        tarjeta.setPreferredSize(new Dimension(400, 500));
        tarjeta.setBackground(Color.WHITE);
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true),
                BorderFactory.createEmptyBorder(40, 40, 40, 40)
        ));

        JPanel panelBotones = new JPanel(new GridLayout(4, 1, 0, 25));
        panelBotones.setBackground(Color.WHITE);

        Boton btnCrearPedido = new Boton("Crear Pedido");

        panelBotones.add(btnCrearPedido);

        tarjeta.add(panelBotones, BorderLayout.CENTER);

        JLabel footer = new JLabel("Versión 1.0.0", SwingConstants.CENTER);
        footer.setFont(new Font("Poppins", Font.PLAIN, 12));
        footer.setForeground(new Color(150, 120, 90));
        footer.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));
        tarjeta.add(footer, BorderLayout.SOUTH);

        contenedor.add(tarjeta);
        add(contenedor, BorderLayout.CENTER);

        IInicio controlador = new InicioControlador();

        btnCrearPedido.addActionListener(e -> controlador.abrirSeleccionarPedido(this));

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(InicioPantalla::new);
    }
}
