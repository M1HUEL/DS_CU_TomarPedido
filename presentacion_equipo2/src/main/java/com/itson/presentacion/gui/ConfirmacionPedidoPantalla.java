package com.itson.presentacion.gui;

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
    private final Color COLOR_NARANJA_CLARO = new Color(255, 183, 77);
    private final Color COLOR_NARANJA_MUY_CLARO = new Color(255, 250, 240);
    private final Color COLOR_CREMA = new Color(254, 249, 239);
    private final Color COLOR_BLANCO = Color.WHITE;
    private final Color COLOR_GRIS_BORDE = new Color(220, 220, 220);

    private JPanel header;
    private JPanel headerContenido;
    private JPanel panelPrincipal;
    private JPanel panelMensaje;
    private JPanel panelTexto;
    private JPanel panelBotones;
    private JPanel contenedorCentral;

    private JLabel lblTitulo;
    private JLabel lblSubtitulo;
    private JLabel lblMensaje;
    private JLabel lblSubMensaje;

    private JButton btnConfirmar;
    private JButton btnCancelar;

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

        lblTitulo = new JLabel("Pantalla de confirmación", SwingConstants.LEFT);
        lblTitulo.setFont(fuentePoppinsBold.deriveFont(Font.BOLD, 28f));
        lblTitulo.setForeground(COLOR_BLANCO);

        lblSubtitulo = new JLabel("Elige las opciones disponibles y personaliza la orden", SwingConstants.LEFT);
        lblSubtitulo.setFont(fuentePoppinsRegular.deriveFont(Font.PLAIN, 16f));
        lblSubtitulo.setForeground(COLOR_BLANCO);

        headerContenido.add(lblTitulo);
        headerContenido.add(lblSubtitulo);
        header.add(headerContenido, BorderLayout.CENTER);

        panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(COLOR_CREMA);

        panelMensaje = new JPanel(new BorderLayout());
        panelMensaje.setBackground(COLOR_BLANCO);
        panelMensaje.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_GRIS_BORDE, 1),
                BorderFactory.createEmptyBorder(100, 60, 100, 60)
        ));

        lblMensaje = new JLabel("El pedido se encuentra en preparación!", SwingConstants.CENTER);
        lblMensaje.setFont(fuentePoppinsBold.deriveFont(Font.PLAIN, 22f));

        lblSubMensaje = new JLabel("Muchas gracias por comprar en este establecimiento.", SwingConstants.CENTER);
        lblSubMensaje.setFont(fuentePoppinsRegular.deriveFont(Font.PLAIN, 16f));

        panelTexto = new JPanel(new GridLayout(2, 1, 0, 10));
        panelTexto.setBackground(COLOR_BLANCO);
        panelTexto.add(lblMensaje);
        panelTexto.add(lblSubMensaje);

        panelMensaje.add(panelTexto, BorderLayout.CENTER);

        panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        panelBotones.setBackground(COLOR_CREMA);

        btnConfirmar = new JButton("Confirmar");
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
    }

    private void cargarFuentePoppins() {
        try {
            InputStream regular = getClass().getResourceAsStream("/fonts/Poppins-Regular.ttf");
            InputStream bold = getClass().getResourceAsStream("/fonts/Poppins-Bold.ttf");

            fuentePoppinsRegular = regular != null
                    ? Font.createFont(Font.TRUETYPE_FONT, regular)
                    : new Font("SansSerif", Font.PLAIN, 14);

            fuentePoppinsBold = bold != null
                    ? Font.createFont(Font.TRUETYPE_FONT, bold)
                    : new Font("SansSerif", Font.BOLD, 14);

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
