package com.itson.presentacion.gui;

import com.itson.presentacion.controlador.ConfirmacionPedidoControlador;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.io.IOException;
import java.io.InputStream;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class ConfirmacionPedidoPantalla extends JFrame {

    private Font fuentePoppinsRegular;
    private Font fuentePoppinsBold;

    private final Color COLOR_NARANJA = new Color(255, 140, 0);
    private final Color COLOR_CREMA = new Color(254, 249, 239);
    private final Color COLOR_BLANCO = Color.WHITE;
    private final Color COLOR_GRIS_BORDE = new Color(220, 220, 220);

    public JButton btnConfirmar, btnCancelar;
    public JLabel lblNombrePedido, lblPrecio;
    public JPanel panelTexto, panelBotones, header, headerContenido, panelMensaje, contenedorCentral;

    private ConfirmacionPedidoControlador controlador = new ConfirmacionPedidoControlador();

    public ConfirmacionPedidoPantalla() {
        cargarFuentePoppins();

        setTitle("Pantalla de Confirmación");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1440, 720);
        setLocationRelativeTo(null);
        setResizable(false);

        header = new JPanel(new BorderLayout());
        header.setBackground(COLOR_NARANJA);
        header.setPreferredSize(new Dimension(1440, 160));

        headerContenido = new JPanel(new GridLayout(2, 1));
        headerContenido.setBackground(COLOR_NARANJA);
        headerContenido.setBorder(BorderFactory.createEmptyBorder(40, 100, 40, 100));

        lblNombrePedido = new JLabel("Combo Hamburguesa", SwingConstants.LEFT);
        lblNombrePedido.setFont(fuentePoppinsBold.deriveFont(28f));
        lblNombrePedido.setForeground(COLOR_BLANCO);

        lblPrecio = new JLabel("30$", SwingConstants.LEFT);
        lblPrecio.setFont(fuentePoppinsRegular.deriveFont(16f));
        lblPrecio.setForeground(COLOR_BLANCO);

        headerContenido.add(lblNombrePedido);
        headerContenido.add(lblPrecio);
        header.add(headerContenido, BorderLayout.CENTER);

        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(COLOR_CREMA);

        panelMensaje = new JPanel(new BorderLayout());
        panelMensaje.setBackground(COLOR_BLANCO);
        panelMensaje.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_GRIS_BORDE, 1),
                BorderFactory.createEmptyBorder(100, 60, 100, 60)
        ));

        panelTexto = new JPanel(new GridLayout(2, 1, 0, 10));
        panelTexto.setBackground(COLOR_BLANCO);
        panelTexto.add(lblNombrePedido);
        panelTexto.add(lblPrecio);

        panelMensaje.add(panelTexto, BorderLayout.CENTER);

        panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        panelBotones.setBackground(COLOR_CREMA);

        btnConfirmar = new JButton("Confirmar Pedido");
        btnConfirmar.setBackground(COLOR_NARANJA);
        btnConfirmar.setForeground(COLOR_BLANCO);
        btnConfirmar.setFont(fuentePoppinsRegular.deriveFont(15f));
        btnConfirmar.setFocusPainted(false);
        btnConfirmar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnConfirmar.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40));

        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBackground(COLOR_NARANJA);
        btnCancelar.setForeground(COLOR_BLANCO);
        btnCancelar.setFont(fuentePoppinsRegular.deriveFont(15f));
        btnCancelar.setFocusPainted(false);
        btnCancelar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnCancelar.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40));
        btnCancelar.setPreferredSize(btnConfirmar.getPreferredSize());

        panelBotones.add(btnConfirmar);
        panelBotones.add(btnCancelar);

        contenedorCentral = new JPanel(new BorderLayout());
        contenedorCentral.setBackground(COLOR_CREMA);
        contenedorCentral.setBorder(BorderFactory.createEmptyBorder(60, 100, 100, 100));
        contenedorCentral.add(panelMensaje, BorderLayout.CENTER);
        contenedorCentral.add(panelBotones, BorderLayout.SOUTH);

        panelPrincipal.add(header, BorderLayout.NORTH);
        panelPrincipal.add(contenedorCentral, BorderLayout.CENTER);

        add(panelPrincipal);

        btnConfirmar.addActionListener(e -> controlador.confirmarPedido(this));
        btnCancelar.addActionListener(e -> dispose());
    }

    private void cargarFuentePoppins() {
        try {
            InputStream regularStream = getClass().getResourceAsStream("/fonts/Poppins-Regular.ttf");
            InputStream boldStream = getClass().getResourceAsStream("/fonts/Poppins-Bold.ttf");

            fuentePoppinsRegular = Font.createFont(Font.TRUETYPE_FONT, regularStream);
            fuentePoppinsBold = Font.createFont(Font.TRUETYPE_FONT, boldStream);

            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(fuentePoppinsRegular);
            ge.registerFont(fuentePoppinsBold);

        } catch (FontFormatException | IOException e) {
            fuentePoppinsRegular = new Font("SansSerif", Font.PLAIN, 14);
            fuentePoppinsBold = new Font("SansSerif", Font.BOLD, 14);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ConfirmacionPedidoPantalla().setVisible(true));
    }
}
