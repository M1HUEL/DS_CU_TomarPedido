package com.itson.presentacion.pantallas.componentes;

import com.itson.presentacion.controladores.SeleccionarPedidoControlador;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class PedidoTarjeta extends JPanel {

    private final int numeroPedido;
    private final SeleccionarPedidoControlador controlador;

    public PedidoTarjeta(int numeroPedido, ImageIcon icono, SeleccionarPedidoControlador controlador) {
        this.numeroPedido = numeroPedido;
        this.controlador = controlador;

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(300, 380));

        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 4, 0, new Color(245, 156, 49)),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                setBackground(new Color(255, 245, 220));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                setBackground(Color.WHITE);
            }
        });

        JLabel imagenLabel = new JLabel(icono);
        imagenLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel nombreLabel = new JLabel("Pedido " + numeroPedido, SwingConstants.CENTER);
        nombreLabel.setFont(new Font("Poppins", Font.BOLD, 16));
        nombreLabel.setForeground(new Color(70, 40, 0));

        Boton btnSeleccionar = new Boton("Seleccionar");
        btnSeleccionar.addActionListener(e -> controlador.seleccionarPedido(numeroPedido));

        JPanel parteInferior = new JPanel(new BorderLayout());
        parteInferior.setOpaque(false);
        parteInferior.add(nombreLabel, BorderLayout.NORTH);
        parteInferior.add(Box.createVerticalStrut(10), BorderLayout.CENTER);
        parteInferior.add(btnSeleccionar, BorderLayout.SOUTH);

        add(imagenLabel, BorderLayout.CENTER);
        add(parteInferior, BorderLayout.SOUTH);
    }
}
