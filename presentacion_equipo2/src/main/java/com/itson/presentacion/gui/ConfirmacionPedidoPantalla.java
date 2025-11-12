package com.itson.presentacion.gui;

import javax.swing.SwingUtilities;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class ConfirmacionPedidoPantalla extends JFrame {

    private Font poppinsRegular;
    private Font poppinsBold;

    private final Color NARANJA = new Color(255, 140, 0);
    private final Color NARANJA_CLARO = new Color(255, 183, 77);
    private final Color NARANJA_MUY_CLARO = new Color(255, 250, 240);
    private final Color CREMA = new Color(254, 249, 239);
    private final Color BLANCO = Color.WHITE;
    private final Color GRIS_BORDE = new Color(220, 220, 220);

    public ConfirmacionPedidoPantalla() {
        cargarFuentePoppins();

        setTitle("Pantalla de Confirmación");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1440, 720);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(NARANJA);
        header.setPreferredSize(new Dimension(1440, 160));

        JPanel headerContenido = new JPanel(new GridLayout(2, 1));
        headerContenido.setBackground(NARANJA);
        headerContenido.setBorder(BorderFactory.createEmptyBorder(40, 100, 40, 100));

        JLabel lblTitulo = new JLabel("Pantalla de confirmación", SwingConstants.LEFT);
        lblTitulo.setFont(poppinsBold.deriveFont(Font.BOLD, 28f));
        lblTitulo.setForeground(BLANCO);

        JLabel lblSubtitulo = new JLabel("Elige las opciones disponibles y personaliza la orden", SwingConstants.LEFT);
        lblSubtitulo.setFont(poppinsRegular.deriveFont(Font.PLAIN, 16f));
        lblSubtitulo.setForeground(BLANCO);

        headerContenido.add(lblTitulo);
        headerContenido.add(lblSubtitulo);
        header.add(headerContenido, BorderLayout.CENTER);

        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(CREMA);

        JPanel panelMensaje = new JPanel(new BorderLayout());
        panelMensaje.setBackground(BLANCO);
        panelMensaje.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(GRIS_BORDE, 1),
                BorderFactory.createEmptyBorder(100, 60, 100, 60)
        ));

        JLabel lblMensaje = new JLabel("El pedido se encuentra en preparación!", SwingConstants.CENTER);
        lblMensaje.setFont(poppinsBold.deriveFont(Font.PLAIN, 22f));

        JLabel lblSubMensaje = new JLabel("Muchas gracias por comprar en este establecimiento.", SwingConstants.CENTER);
        lblSubMensaje.setFont(poppinsRegular.deriveFont(Font.PLAIN, 16f));

        JPanel panelTexto = new JPanel(new GridLayout(2, 1, 0, 10));
        panelTexto.setBackground(BLANCO);
        panelTexto.add(lblMensaje);
        panelTexto.add(lblSubMensaje);

        panelMensaje.add(panelTexto, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        panelBotones.setBackground(CREMA);

        JButton btnConfirmar = new JButton("Confirmar");
        btnConfirmar.setBackground(NARANJA);
        btnConfirmar.setForeground(BLANCO);
        btnConfirmar.setFont(poppinsRegular.deriveFont(15f));
        btnConfirmar.setFocusPainted(false);
        btnConfirmar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnConfirmar.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40));

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBackground(NARANJA);
        btnCancelar.setForeground(BLANCO);
        btnCancelar.setFont(poppinsRegular.deriveFont(15f));
        btnCancelar.setFocusPainted(false);
        btnCancelar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnCancelar.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40));

        panelBotones.add(btnConfirmar);
        panelBotones.add(btnCancelar);

        JPanel contenedorCentral = new JPanel(new BorderLayout());
        contenedorCentral.setBackground(CREMA);
        contenedorCentral.setBorder(BorderFactory.createEmptyBorder(60, 100, 100, 100));
        contenedorCentral.add(panelMensaje, BorderLayout.CENTER);
        contenedorCentral.add(panelBotones, BorderLayout.SOUTH);

        panelPrincipal.add(header, BorderLayout.NORTH);
        panelPrincipal.add(contenedorCentral, BorderLayout.CENTER);

        add(panelPrincipal);
    }

    private void cargarFuentePoppins() {
        try {
            InputStream regularStream = getClass().getResourceAsStream("/fonts/Poppins-Regular.ttf");
            InputStream boldStream = getClass().getResourceAsStream("/fonts/Poppins-Bold.ttf");

            if (regularStream != null) {
                poppinsRegular = Font.createFont(Font.TRUETYPE_FONT, regularStream);
            } else {
                poppinsRegular = new Font("SansSerif", Font.PLAIN, 14);
            }

            if (boldStream != null) {
                poppinsBold = Font.createFont(Font.TRUETYPE_FONT, boldStream);
            } else {
                poppinsBold = new Font("SansSerif", Font.BOLD, 14);
            }

            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(poppinsRegular);
            ge.registerFont(poppinsBold);

        } catch (FontFormatException | IOException e) {
            poppinsRegular = new Font("SansSerif", Font.PLAIN, 14);
            poppinsBold = new Font("SansSerif", Font.BOLD, 14);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ConfirmacionPedidoPantalla().setVisible(true));
    }
}
