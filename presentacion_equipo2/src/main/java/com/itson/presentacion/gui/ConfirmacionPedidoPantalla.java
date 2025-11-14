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

    public JButton btnConfirmar;

    private ConfirmacionPedidoControlador controlador = new ConfirmacionPedidoControlador();

    public ConfirmacionPedidoPantalla() {
        cargarFuentePoppins();

        setTitle("Pantalla de Confirmación");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1440, 720);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(COLOR_NARANJA);
        header.setPreferredSize(new Dimension(1440, 160));

        JPanel headerContenido = new JPanel(new GridLayout(2, 1));
        headerContenido.setBackground(COLOR_NARANJA);
        headerContenido.setBorder(BorderFactory.createEmptyBorder(40, 100, 40, 100));

        JLabel lblTitulo = new JLabel("Pantalla de confirmación", SwingConstants.LEFT);
        lblTitulo.setFont(fuentePoppinsBold.deriveFont(28f));
        lblTitulo.setForeground(COLOR_BLANCO);

        JLabel lblSubtitulo = new JLabel("Elige las opciones disponibles y personaliza la orden", SwingConstants.LEFT);
        lblSubtitulo.setFont(fuentePoppinsRegular.deriveFont(16f));
        lblSubtitulo.setForeground(COLOR_BLANCO);

        headerContenido.add(lblTitulo);
        headerContenido.add(lblSubtitulo);
        header.add(headerContenido, BorderLayout.CENTER);

        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(COLOR_CREMA);

        JPanel panelMensaje = new JPanel(new BorderLayout());
        panelMensaje.setBackground(COLOR_BLANCO);
        panelMensaje.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_GRIS_BORDE, 1),
                BorderFactory.createEmptyBorder(100, 60, 100, 60)
        ));

        JLabel lblMensaje = new JLabel("El pedido se encuentra en preparación!", SwingConstants.CENTER);
        lblMensaje.setFont(fuentePoppinsBold.deriveFont(22f));

        JLabel lblSubMensaje = new JLabel("Muchas gracias por comprar en este establecimiento.", SwingConstants.CENTER);
        lblSubMensaje.setFont(fuentePoppinsRegular.deriveFont(16f));

        JPanel panelTexto = new JPanel(new GridLayout(2, 1, 0, 10));
        panelTexto.setBackground(COLOR_BLANCO);
        panelTexto.add(lblMensaje);
        panelTexto.add(lblSubMensaje);

        panelMensaje.add(panelTexto, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        panelBotones.setBackground(COLOR_CREMA);

        btnConfirmar = new JButton("Confirmar");
        btnConfirmar.setBackground(COLOR_NARANJA);
        btnConfirmar.setForeground(COLOR_BLANCO);
        btnConfirmar.setFont(fuentePoppinsRegular.deriveFont(15f));
        btnConfirmar.setFocusPainted(false);
        btnConfirmar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnConfirmar.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40));

        panelBotones.add(btnConfirmar);

        JPanel contenedorCentral = new JPanel(new BorderLayout());
        contenedorCentral.setBackground(COLOR_CREMA);
        contenedorCentral.setBorder(BorderFactory.createEmptyBorder(60, 100, 100, 100));
        contenedorCentral.add(panelMensaje, BorderLayout.CENTER);
        contenedorCentral.add(panelBotones, BorderLayout.SOUTH);

        panelPrincipal.add(header, BorderLayout.NORTH);
        panelPrincipal.add(contenedorCentral, BorderLayout.CENTER);

        add(panelPrincipal);

        btnConfirmar.addActionListener(e -> controlador.confirmarPedido(this));
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
        SwingUtilities.invokeLater(() -> new IniciarSesionPantalla().setVisible(true));
    }
}
